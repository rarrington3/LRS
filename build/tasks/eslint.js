'use strict';

module.exports = function (grunt) {

    let files = [
        '{.,platforms/*}/<%= settings.dir.app %>/**/*.{js,jsx,es,esx,ts,tsx}',
        '!{.,platforms/*}/<%= settings.dir.app %>/**/*.{generated,mock,spec,e2e,d}.*',
        '!{.,platforms/*}/<%= settings.dir.app %>/libs/**/*'
        ];

    grunt.config.merge({
        eslint: {
            options: {
                configFile: '.eslintrc',
                fix: true
            },
            console: {
                files: {
                    src: files
                },
                options: {
                    format: 'stylish'
                }
            },
            junit: {
                files: {
                    src: files
                },
                options: {
                    force: true,
                    quite: true,
                    format: 'junit',
                    outputFile: '<%= settings.dir.temp %>/test-reports/eslint/junit.xml'
                }
            },
            html: {
                files: {
                    src: files
                },
                options: {
                    format: 'html',
                    outputFile: '<%= settings.dir.temp %>/test-reports/eslint/index.html'
                }
            }
        }
    });
};
