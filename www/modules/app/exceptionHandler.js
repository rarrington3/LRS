let errorHandler = require('modules/preloader/errorhandler');
let errorCodes = require('modules/preloader/errorcodes');
import {SERVER_ERROR} from '../shared/constants';
import Utils from '../shared/Utils';

/**
 * Custom version of Angular's built in ExceptionHandler which delegates into
 * our framework-neutral error handler defined in the preloader module.
 *
 * @class AppExceptionHandler
 * @extends {ErrorHandler}
 */
export default class AppExceptionHandler {

    name: string = 'AppExceptionHandler';

    handleError(error) {

        let message;

        // log exception so it's visible in dev tools
        console.error(error);

        // replace error from server
        if (error.status === SERVER_ERROR.BAD_REQUEST ||
            error.status === SERVER_ERROR.NOT_FOUND ||
            error.status === SERVER_ERROR.UNAUTHORIZED ||
            error.status === SERVER_ERROR.FORBIDDEN ||
            error.status === SERVER_ERROR.CONFLICT ||
            error.status === SERVER_ERROR.GONE ||
            error.status === SERVER_ERROR.EXCEPTION) {

            // parsing the server error message
            let errorMessages = Utils.getErrorMessages(error);
            if (errorMessages && errorMessages.length) {
                message = errorMessages
                    .map(errorMessage => {
                        return `${errorMessage}`;
                    })
                    .join('\n');
            }
        }

        // pass the error into preloader's error handling/reporting mechanism
        errorHandler.handleError(
            errorCodes.ANGULAR_CAUGHT_EXCEPTION,
            `Angular exception handler triggered: ${error.toString()}`,
            message);
    }
}
