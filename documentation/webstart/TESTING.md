# Testing your Project

Please be sure to follow the instructions for [setting up](./SETUP.md) this project.

There are two primary forms of testing covered by Webstart. The first is Unit testing and the second is End-to-End
Testing. Both forms of testing are configured to leverage [Jasmine](http://jasmine.github.io/) as the testing 
expectation framework. 

## Unit Tests

Unit tests conducted tests on units of functionality in isolation from the rest of the application. The intension is
to test functionality at a micro level with as few dependencies and distractions as possible. For instance, a function
given A should return B but given B should return C.

Unit tests are executed via [Karma](https://karma-runner.github.io/0.13/index.html) and generates test results to 
either the console, HTML or JUnit XML files. The later is useful with your CI server which can scan the JUnit files
and report build failures if there are failed tests.

By default, Karma is configured to run against a headless [PhantomJS](http://phantomjs.org/). The default configurations 
for unit tests may be found in [build/tasks/test.js](../../build/tasks/test.js) and the defaults configuration options 
can be easily overwritten in your [gruntfile.js](../../gruntfile.js). Making changes in your gruntfile.js is preferrable 
to making changes directly in the task so that you have less chance of conflicts when you [update](./UPDATING.md) your 
project at a later time.

To run unit tests, run the grunt command and follow the prompts:

    grunt
    [?] What task would you like to run?
      Run the Scaffolding Tools to Build Out Project
      Run the Web Application
      Run the Cordova Application
      Run the API Designer
    ❯ Run the Test Suite
      Run the Documentation Generator
      Publish the Application for Deployment
      
Or simply run either `grunt server:test` or `grunt test`.

For more information on this, please see the [tasks](./TASKS.md) section.

## End-to-End Tests

Unlike unit tests, end-to-end tests will test the application in its entirety with all touch-points included. This 
requires interacting with the application as the user would via a browser by similating interactions with a device 
such as a mouse or keyboard. For example, you want to validate that the login button is not active until a username 
and password are provided and when login is processed if the credentials are incorrect that an appropriate message is
displayed to the user.

End-to-end tests are executed via [Protractor](http://www.protractortest.org/#/) which works with both Angular and 
non-Angular based applications. The advantage with Angular based applications is that Protractor knows when the Angular 
application is "at rest" and no further asynchronous actions are pending. This makes writing asynchronous tests very 
easily and can be written in a synchronous mannor. With non-Angular apps, you may have to write some custom wait 
functions to check if your tests are ready to proceed to the next test.

Similar to unit tests, this will generate reports to the console, HTML or JUnit XML files. The later is useful with 
your CI server which can scan the JUnit files and report build failures if there are failed tests. The HTML reports 
also include screen shots of the state of the application during each test.

By default, Protractor is configured to run against a Chrome browser, its not advisable, although capable, to run 
end-to-end tests against PhantomJS since it runs headless and doesn't truely mimick the user behaviour. The default 
configurations for end-to-end tests may be found in [build/tasks/e2e.js](../../build/tasks/e2e.js) and 
[protractor.config.js](../../protractor.config.js). The defaults configuration options can be easily overwritten in 
[protractor.config.js](../../protractor.config.js) or your [gruntfile.js](../../gruntfile.js). Making changes in your 
gruntfile.js is preferrable to making changes directly in the task so that you have less chance of conflicts when you 
[update](./UPDATING.md) your project at a later time.

To run end-to-end tests, run the grunt command and follow the prompts:

    grunt
    [?] What task would you like to run?
      Run the Scaffolding Tools to Build Out Project
      Run the Web Application
      Run the Cordova Application
      Run the API Designer
    ❯ Run the Test Suite
      Run the Documentation Generator
      Publish the Application for Deployment
      
Or simply run either `grunt server:e2e` or `grunt e2e`.

For more information on this, please see the [tasks](./TASKS.md) section.
