let compiledConfig = require('config');


/**
 * @module preloader/reporter
 */
module.exports = {

    /**
     * Sends the error to teh server
     * @param {object} error The error object to send
     * @returns {void}
     */
    send: function(error) {
        let $ = require('jquery');
        console.log('Reporting error to server: ' + JSON.stringify(error, null, '\t'));

        $.ajax(compiledConfig.server + '/api/v1/errors', {
            data : JSON.stringify(error),
            contentType : 'application/json',
            type : 'POST'
        });
    }

};
