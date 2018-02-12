let reporter = require('./errorreporter'),
    dialog = require('./errordialog'),
    compiledConfig = require('config'),
    errorCodes = require('./errorcodes');

let logHistory = [],
    maxHistory = 20;

/**
 * @module preloader/errorhandler
 * @example
 * let main = require('preloader/errorhandler');
 */
module.exports = {

    /**
     * Report an error
     * @param {Number} errorCode - one of the values from errorcodes.js
     * @param {string} messageToLog - message to send to server
     * @param {string?} messageToDisplay - message to show to users. If not provided, the user will not be made aware of the error
     * @returns {void}
     */
    handleError: function(errorCode: Number, messageToLog: string, messageToDisplay: string) {

        // Generate a unique value to be used as the ID for this error report.
        // This ID can be stored on the server and then displayed to the user
        // so that if a user seeks tech support, the specific error they
        // encountered can be easily located within server logs
        let errorId =
            (compiledConfig.build.commit.substring(0, 7) + '-' +
             compiledConfig.build.branch + '-' +
             (Date.now().toString(16).substring(4) + '-' + Math.random().toString(16).substring(2, 10))
            ).toUpperCase();

        reporter.send({
            errorId,
            errorCode,
            message: messageToLog,
            url: window.location.toString(),
            logHistory: logHistory.slice(0)
        });
        logHistory = [];

        dialog.show({
            id: errorId,
            message: messageToDisplay
        });
    }

};

function interceptLogs() {

    window.console = window.console || /* istanbul ignore next */ {};
    let orginal = window.console.log || /* istanbul ignore next */ function(){};
    window.console.log = function (message) {
        orginal.apply(this, arguments);
        logHistory.push('[' + Date.now() +'] ' + message);
        if (logHistory.length > maxHistory) {
            logHistory.shift();
        }
    };
}

function interceptUnhandledExceptions() {

    window.onerror = function (message, file, line, col, error) {
        error = { message: message, file: file, line: line, col: col, error: error };

        let errorString = 'Unhandled exception: ' + JSON.stringify(error);

        if (file.indexOf('chrome://') === 0) {
            console.log('Suppressing error from browser extension: ' + errorString);
            return;
        }

        module.exports.handleError( errorCodes.UNHANDLED_EXCEPTION, errorString,
            'Sorry, an unexpected problem has occurred.  Please reload the page and try again.');
    };
}

interceptLogs();
interceptUnhandledExceptions();
