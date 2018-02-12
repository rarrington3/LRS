// Include external references.
let compiledConfig = require('config');
let uiConfigMock = require('../../../raml/api/v1/config/config.example.json');
let currentUserMock = require('../../../raml/api/v1/currentuser/currentuser-example.json');
let errorHandler = require('./errorhandler');
let errorCodes = require('./errorcodes');
let dialog = require('./errordialog');

// Create the object that this module will export.
let preloader = {
    /**
     * Timestamp reference to when page execution first starts.
     * @type {date}
     * @example
     * preloader.timestamp = new Date();
     */
    timestamp: window.timestamp,

    /**
     * Run after modules have loaded, will remove the loading screen.
     * @returns {void}
     * @example {@lang xml}
     * <script src='modules.js'></script>
     * <script>preloader.afterModules();</script>
     */
    afterModules: function() {},

    initializationCallbacks: [],

    isLoaded: false,

    uiConfig: {},

    onceUserInfoIsLoaded: function (callback) {
        if (!preloader.isLoaded) {
            preloader.initializationCallbacks.push(callback);
        }
        else {
            return callback();
        }
        return true;
    },

    removeLoadingOverlay: function () {
        // This timeout provides our ng2 application to render before we remove the overlay.
        window.setTimeout(function () {
            let overlay = document.getElementById('GlobalLoadingOverlay');
            if (overlay.className.indexOf('waiting') === -1) {
                overlay.className = 'removed';
            }
        }, 80);
    }

};

// Run some additional, inline logic.
(function getCurrentUserInfo () {

    // Boolean to track if the currentUser call failed.
    let currentUserCallFailed = false;
    let currentUserCallResponse = null;

    // If we're on mocks...
    if (compiledConfig.settings.mocks) {

        // utilize the mock ui config data.
        preloader.uiConfig = uiConfigMock;

        // get the current user from local storage or mock.
        let localUserStr = window.localStorage.getItem('user');
        preloader.currentUser = (localUserStr)
            ? JSON.parse(localUserStr)
            : currentUserMock;

        // finish initializing the preloader.
        runPostInitialization();
    }

    // ...otherwise, make the first request to the API. This will return user info.
    else {

        let xhrUiConfig = new XMLHttpRequest();
        let xhrCurrentUser = new XMLHttpRequest();

        // Simple boolean + function to handle the race condition between these two calls.
        let lapped = false;
        function initializeOrDont () {
            if (lapped) {
                runPostInitialization();
            }
            else {
                lapped = true;
            }
        }

        function onCurrentUserXhrResponse () {
            // Handle success.
            if (xhrCurrentUser.status >= 200 && xhrCurrentUser.status < 400) {
                preloader.currentUser = JSON.parse(xhrCurrentUser.responseText);
            }

            // Handle errors returned by the server.
            else {
                currentUserCallFailed = true;
                currentUserCallResponse = xhrCurrentUser.responseText !== null && xhrCurrentUser.responseText.length ? JSON.parse(xhrCurrentUser.responseText) : {};
            }

            // Initialize or don't regardless.
            initializeOrDont();

            // Cleanup.
            xhrCurrentUser.removeEventListener('error', onCurrentUserXhrError);
            return xhrCurrentUser.removeEventListener('load', onCurrentUserXhrResponse);
        }

        function onCurrentUserXhrError () {
            currentUserCallFailed = true;
            currentUserCallResponse = xhrCurrentUser.responseText !== null && xhrCurrentUser.responseText.length ? JSON.parse(xhrCurrentUser.responseText) : {};
            initializeOrDont();
            xhrCurrentUser.removeEventListener('load', onCurrentUserXhrResponse);
            return xhrCurrentUser.removeEventListener('error', onCurrentUserXhrError);
        }

        function onUiConfigXhrResponse () {

            // Handle success.
            if (xhrUiConfig.status >= 200 && xhrUiConfig.status < 400) {
                preloader.uiConfig = JSON.parse(xhrUiConfig.responseText);
                initializeOrDont();
            }
            else {
                errorHandler.handleError(errorCodes.NO_CONFIG, 'The config http service returned an error.', 'We\'ve experienced an error.');
            }

            // Cleanup.
            return xhrUiConfig.removeEventListener('load', onUiConfigXhrResponse);
        }

        // Request the UI Config data.
        xhrUiConfig.open('GET', `${compiledConfig.server}/api/v1/config`, true);
        xhrUiConfig.withCredentials = true;
        xhrUiConfig.addEventListener('load', onUiConfigXhrResponse);
        xhrUiConfig.send('banana=yellow');

        // Request the UI Config data.
        xhrCurrentUser.open('GET', `${compiledConfig.server}/api/v1/currentUser`, true);
        xhrCurrentUser.withCredentials = true;
        xhrCurrentUser.addEventListener('load', onCurrentUserXhrResponse);
        xhrCurrentUser.addEventListener('error', onCurrentUserXhrError);
        xhrCurrentUser.send('banana');

    }

    function runPostInitialization () {

        // Throw an error if the current user called failed and we're NOT in development mode.
        if (currentUserCallFailed) {

            if (preloader.uiConfig.lrsUiDev) {
                window.location.hash = '/dev';
            }
            else {
                let errorMessage;
                let errorMessages = currentUserCallResponse !== null && currentUserCallResponse.errorMessages ? currentUserCallResponse.errorMessages : null;
                if (errorMessages && errorMessages.length) {
                    errorMessage = errorMessages[0];

                    dialog.show({
                        id: errorCodes.UNAUTHORIZED_USER,
                        message: errorMessage,
                        title: 'Authentication Issue',
                        messageHeadline: 'Sorry, there is a problem with your authentication.'
                    });
                } else {
                    errorHandler.handleError(errorCodes.NO_USER, 'The currentUser http service returned an error.', 'We\'ve experienced an authentication error.  Make sure your account has been granted LRS roles in FHA Connection and that your supervisor has configured your account in the LRS staff management screen.');
                }
            }
        }

        // Mark that the preloader is done loading.
        preloader.isLoaded = true;

        // Call any callbacks that were registered for once the preloader is finished.
        preloader.initializationCallbacks.forEach(callback => callback());
        preloader.initializationCallbacks = [];
    }

})();

// Export the preloader so that it can be utilized as a dependency.
module.exports = preloader;
