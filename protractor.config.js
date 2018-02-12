var HtmlReporter = require('protractor-jasmine2-html-reporter'),
    settings = require(__dirname + '/settings.json'),
    fs = require('fs');

module.exports.config = {
    // Capabilities to be passed to the webdriver instance.
    capabilities: {
        'browserName': 'chrome',
        'chromeOptions': {
            'args': ['disable-extensions']
        }        
    },
    directConnect: true,

    // Options to be passed to Jasmine-node.
    jasmineNodeOpts: {
        showColors: true,
        defaultTimeoutInterval: 30000
    },

    onPrepare: function () {
        var reporters = require('jasmine-reporters');
        // setup the jasmine reporters
        jasmine.getEnv().addReporter(new HtmlReporter({
            savePath: settings.dir.temp + '/test-reports/e2e/',
            filePrefix: 'report'
        }));
        jasmine.getEnv().addReporter(new reporters.JUnitXmlReporter({
            consolidateAll: true,
            savePath: settings.dir.temp + '/test-reports/e2e',
            filePrefix: 'junit'
        }));
    }
};