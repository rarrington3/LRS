module.exports = function (grunt) {
    'use strict';

    //TODO: create proxies to bypass CORS and configure app to reach different server endpoints
    //use the context prefix here with the server property in the profiles section below
    var apiProxy = [
        {
            context: '/proxy-qa',
            host: '10.39.3.25',
            port: '8081',
            rewrite: {
                '/proxy-qa': ''
            }
        },
        {
            context: '/mock',
            host: 'localhost',
            port: '8088',
            rewrite: {
                '/mock': ''
            }
        }
    ];

    grunt.loadTasks('build/tasks');

    grunt.config.merge({
        //TODO: place your custom grunt config options and overrides here
        connect: {
            www: {
                proxies: apiProxy
            },
            e2e: {
                proxies: apiProxy
            },
            test: {
                proxies: apiProxy
            },
            coverage: {
                proxies: apiProxy
            },
            dist: {
                proxies: apiProxy
            }
        },
        profiles: {
            //TODO: register your default base configuration options
            default: {
                //this is the base configuration that specific profiles will merge into
                config: {
                    version: '<%= package.version %>.' + (grunt.option('buildnum') || 'local'),
                    server: 'http://localhost:8000/LrsServices',//locally resolve API
                    settings: {
                        mocks: false
                    }
                }
            },
            mock: {
                //these settings will merge into default if using the --profile=mock option when running grunt
                config: {
                    settings: {
                        mocks: true//force all mocks enabled
                    }
                }
            },
            dev: {
                //these settings will merge into default if using the --profile=dev option when running grunt
                config: {
                    server: 'https://entptest.hud.gov/LrsDev/LrsServices'
                }
            },
            dev_prod: {
                //these settings will merge into default if using the --profile=test option when running grunt
                config: {
                    server: 'https://entp.hud.gov/Lrs/LrsServices'
                }
            },
                        dev_test: {
                //these settings will merge into default if using the --profile=test option when running grunt
                config: {
                    server: 'https://entptest.hud.gov/LrsTest/LrsServices'
                }
            },
            dev_staging: {
                //these settings will merge into default if using the --profile=test option when running grunt
                config: {
                    server: 'https://entptest.hud.gov/LrsStaging/LrsServices'
                }
            },
            devSpringBoot: {
                //these settings will merge into default if using the --profile=devSpringBoot option when running grunt
                config: {
                    server: 'http://localhost:8444/LrsServices' // spring boot uses 8444 to not conflict with jboss on 8000
                }
            },
            qa: {
                //these settings will merge into default if using the --profile=qa option when running grunt
                config: {
                    server: '/proxy-qa'
                }
            },
            release: {
                //these settings will merge into default if using the --profile=release option when running grunt
                config: {
                    server: 'LrsServices'
                }
            }
        }
    });

    //TODO: place your custom grunt tasks and overrides here

    grunt.registerTask('mytask', function (target) {
        grunt.log.writeln('Running ' + 'mytask'.red + ' with target ' + (target || '[empty]').blue);
    });

};
