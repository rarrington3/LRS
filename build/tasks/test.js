/**
 * Performs unit tests on project from a command line and
 * outputs results to screen and junit xml files.
 *
 * DO NOT MODIFY THIS FILE IN YOUR PROJECT! INSTEAD, YOU CAN COPY
 * THE CONFIGURATION DATA AND PLACE THAT INTO YOUR gruntfile.js
 * THEN MODIFY ONLY THE PORTIONS YOU NEED. THIS WILL ALLOW YOUR
 * PROJECT TO BE UPDATED WITH CONFLICT TO NEWER VERSIONS.
 */
module.exports = function (grunt) {
    'use strict';

    var htmlCoverageReporter = {
            dir: 'test-reports',
            reporters: [
                {
                    type: 'html',
                    subdir: function() { return 'coverage'; }
                },
                {
                    type: 'cobertura',
                    file: 'coverage.cobertura.xml',
                    subdir: function() { return 'coverage'; }
                },
                {
                    type: 'clover',
                    file: 'coverage.clover.xml',
                    subdir: function() { return 'coverage'; }
                }
            ]
        },
        textCoverageReporter = {
            type: 'text',
            dir: 'test-reports',
            subdir: function() { return 'coverage'; }
        };


    grunt.config.merge({
        clean: {
            test: ['<%= settings.dir.temp %>/test','<%= settings.dir.temp %>/test-reports']
        },
        copy: {
            test: {
                files: [
                    {
                        src: __dirname + '/test.html',
                        dest: '<%= settings.dir.temp %>/test-reports/index.html'
                    },
                    {
                        src: __dirname + '/testinprogress.html',
                        dest: '<%= settings.dir.temp %>/test-reports/results/index.html'
                    },
                    {
                        src: __dirname + '/testinprogress.html',
                        dest: '<%= settings.dir.temp %>/test-reports/coverage/index.html'
                    }
                ]
            }
        },
        shell: {
            'karma-phantom-server': {
                command: 'grunt karma:phantom-server --force --dest=' + grunt.config('settings.dir.temp'),
                options: {
                    async: true,
                    stdout: true,
                    stderr: true,
                    failOnError: false
                }
            },
            'karma-electron-server': {
                command: 'grunt karma:electron-server --force --dest=' + grunt.config('settings.dir.temp'),
                options: {
                    async: true,
                    stdout: true,
                    stderr: true,
                    failOnError: false
                }
            }
        },
        karma: {
            options: {
                basePath: '<%= settings.dir.temp %>',
                files: [
                    //see https://github.com/ariya/phantomjs/issues/10522
                    '../node_modules/phantomjs-polyfill/bind-polyfill.js',
                    '../node_modules/jasmine-jquery/vendor/jquery/jquery.js',
                    'test/<%= bowerrc.directory %>/libs.js',
                    '../node_modules/jasmine-jquery/lib/jasmine-jquery.js',
                    'test/<%= settings.dir.app %>/modules/preloader.js',//must load first
                    'test/<%= settings.dir.app %>/modules/*.js',
                    'test/platforms/*/<%= bowerrc.directory %>/libs.js',
                    'test/platforms/*/<%= settings.dir.app %>/modules/preloader.js',//must load first
                    'test/platforms/*/<%= settings.dir.app %>/modules/*.js'
                ],
                captureTimeout: 2000,
                browserDisconnectTimeout: 2000,
                browserDisconnectTolerance: 3,
                browserNoActivityTimeout: 180000, // Fix for "Disconnected (1 times)" errors
                frameworks: ['jasmine'],
                port: 8081,
                colors: true,
                logLevel: 'INFO',
                singleRun: true,
                // PhantomJS issue resolving localhost very slow on windows
                hostname: '127.0.0.1',
                // Karma serves places files under '/base' by default and our content is under settings.dir.app
                //urlRoot: '/base/test/<%= settings.dir.app %>/',
                //some time between karma 12.x and 13.x urlRoot change functionality and broke our ability to load assets at runtime from base URL
                //custom middlewares is a workaround for this so we can load our assets at runtime without changing base URL's
                middleware: [
                    'temp-assets',
                    'www-assets'
                ],
                preprocessors: {
                    '**/*.js': ['sourcemap']
                },
                plugins: [
                    'karma-*',
                    {
                        'middleware:temp-assets': ['factory', function () {
                            var settings = require('../../settings.json'),
                                serveStatic = require('serve-static'),
                                path = require('path');
                            return serveStatic(path.resolve(settings.dir.temp, 'test', settings.dir.app));
                        }]
                    },
                    {
                        'middleware:www-assets': ['factory', function () {
                            var settings = require('../../settings.json'),
                                serveStatic = require('serve-static'),
                                path = require('path');
                            return serveStatic(path.resolve(settings.dir.app));
                        }]
                    }
                ],
                junitReporter: {
                    outputDir: 'test-reports/results',
                    outputFile: 'junit.xml',
                    useBrowserName: false
                },
                htmlReporter: {
                    outputDir: '<%= settings.dir.temp %>/test-reports',
                    middlePathDir : 'results',
                    templatePath: __dirname+'/jasmine_template.html'
                }
            },
            'phantom-console': {
                browsers: ['PhantomJS'],
                reporters: ['progress', 'coverage', 'junit'],
                coverageReporter: textCoverageReporter
            },
            'electron-console': {
                browsers: ['Electron'],
                reporters: ['progress', 'coverage', 'junit'],
                coverageReporter: textCoverageReporter
            },
            'phantom-html': {
                browsers: ['PhantomJS'],
                reporters: ['progress', 'coverage', 'html', 'junit'],
                coverageReporter: htmlCoverageReporter
            },
            'electron-html': {
                browsers: ['Electron'],
                reporters: ['progress', 'coverage', 'html', 'junit'],
                coverageReporter: htmlCoverageReporter
            },
            'phantom-server': {
                browsers: ['PhantomJS'],
                singleRun: false,
                autoWatch: true,
                reporters: ['progress', 'coverage', 'html', 'junit'],
                coverageReporter: htmlCoverageReporter
            },
            'electron-server': {
                browsers: ['Electron'],
                singleRun: false,
                autoWatch: true,
                reporters: ['progress', 'coverage', 'html', 'junit'],
                coverageReporter: htmlCoverageReporter
            }

            // TODO: another config which uses real browsers.
            // what's the best way to show these results in a browser?
            // generate an index page that summarizes and links to the
            // results from each browser?  not clear how to achieve that
        }
    });

    grunt.registerTask('test', function (target) {

        // Set environment flag which is used by Babel to enable select plugins
        process.env.NODE_ENV = 'test';

        target = target || 'html';

        var karmaTask = (target == 'server' ? 'shell:karma-phantom-' : 'karma:phantom-') + target;
        if (grunt.option('electron')) {
            karmaTask = karmaTask.replace('phantom', 'electron');
        }

        grunt.config.merge({
            watch: {
                test: {
                    files: [
                        '{.,platforms/*}/<%= settings.dir.app %>/modules/**/*.js'
                    ],
                    tasks: ['eslint'],
                    options: {
                        spawn: false
                    }
                }
            },
            build: {
                options: {
                    specs: true,
                    temp: '<%= settings.dir.temp %>/test'
                }
            }
        });

        grunt.task.run([
            'clean:test',
            'build',//build all platforms
            'copy:test',
            'eslint:junit',
            'eslint:' + (target === 'console' ? 'console' : 'html'),
            karmaTask
        ]);
    });

};
