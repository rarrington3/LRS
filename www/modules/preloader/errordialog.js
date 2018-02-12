/**
 * This default implementation leverages the modal API from Twitter Bootstrap
 * but can be replaced with an alternate implementation without modifying other
 * parts of the error handling mechansim
 *
 * @module preloader/errordialog
 */

module.exports = {
    reload: function () {
        window.location.reload();
    },
    /**
     * Displays a message to the user in a way that blocks further user actions.
     * Once the user has acknowledged the message, the page reloads to return the app to a known-good state.
     * @param {object} error The error object to show
     * @param {string} error.id a unique id value to display to users which corresponds to a value logged to the server
     * @param {string} error.title a localized string to display to users
     * @param {string} error.message a localized string to display to users
     * @param {string} error.messageHeadline a localized string to display to users above the detailed message
     * @returns {void}
     */
    show: function(error) {
        let html = require('./errordialog.html');
        let $ = require('jquery');
        let title = error.title || 'Unexpected Error';
        let msg = error.messageHeadline || 'Sorry, the application has encountered an unexpected problem. Please reload the page and try again.';

        try {
            // Add the dialog template to the end of <body>

            $(document.body).append(typeof(html) === 'function' ? html() : html);//could be template function

            let $dialog = $('#errorDialogModal'),
                $errorId = $dialog.find('.error-id'),
                $errorTitle = $dialog.find('#errorDialogTitle'),
                $errorMessage = $dialog.find('.error-message'),
                $errorMessageDetail = $dialog.find('.error-message-detail');

            $errorTitle.html(title);
            $errorMessage.text(msg);
            $errorMessageDetail.text(error.message);
            $errorId.text(`If this problem continues, please contact tech support and reference error #${error.id}`);

            // handle the dialog being dismissed by reloading the page so we return to a known-good state
            $dialog.on('hide.bs.modal', function () {
                // TODO: consider also cleaning localstorage, etc if cached data could prevent being in a known-good state
                module.exports.reload();
            });

            $dialog.modal(); // show the modal

        } catch (ex) {
            // We'll end up here if an error occurs before Bootstrap has loaded. Fall back to a confirmation
            // which devs can click 'cancel' on and open up a debugger

            // eslint-disable-next-line no-alert
            if (window.confirm(`${msg}\n\nError ID: ${error.id}`)) {
                module.exports.reload();
            }
        }
    }
};
