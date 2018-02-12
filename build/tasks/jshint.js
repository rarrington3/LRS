module.exports = function (grunt) {
    'use strict';

    grunt.registerTask('jshint', function (target) {
        grunt.log.warn('jshint is deprecated in favor of eslint, redirecting to eslint');
        grunt.task.run('eslint' + (target ? ':' + target : ''));
    });
};
