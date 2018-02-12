/**
 * @module preloader/errorcodes
 * @example
 * var errorCodes = require('modules/preloader/errorcodes');
 */
module.exports = {
    /**
     * @constant
     * @type {number}
     */
    UNKNOWN_ERROR: 0,

    /**
     * @constant
     * @type {number}
     */
    UNHANDLED_EXCEPTION: 1000,

    /**
     * @constant
     * @type {number}
     */
    ANGULAR_CAUGHT_EXCEPTION: 2000,

    NO_CONFIG: 3000,

    NO_USER: 4000,

    UNAUTHORIZED_USER: 5000
};
