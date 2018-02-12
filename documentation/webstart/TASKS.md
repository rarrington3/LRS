# Leveraging the Grunt Tasks

Please be sure to follow the instructions for [setting up](./SETUP.md) this project.

[Grunt](http://gruntjs.com/) was installed for you automatically along with a variety of custom and public tasks. For most users, you simply
need to issue the `grunt` command from your project directory and you will be prompted with all the available options.

    grunt
    [?] What task would you like to run? (Use arrow keys)
    ❯ Run the Scaffolding Tools to Build Out Project
      Run the Web Application
      Run the Cordova Application
      Run the API Designer
      Run the Test Suite
      Publish the Application for Deployment

## Run the Web Application

This option will compile the application, spawn a web server and open your browser to run the web application. It will
then watch for changes and reload the page automatically when changes are detected.

Just run the `grunt` command and choose the web application option.

    [?] What task would you like to run?
      Run the Scaffolding Tools to Build Out Project
    ❯ Run the Web Application
      Run the Cordova Application
      Run the API Designer
      Run the Test Suite
      Publish the Application for Deployment

You will then be prompted with some followup questions for how to run the server.

    [?] What task would you like to run? Run the Web Application
    [?] Which server target do you want to run? (Use arrow keys)
    ❯ Development (minimal concatination)
      Production (concatination and minification

You can also bypass these prompts by using the `grunt server` and `grunt server:dist` commands.

For more information about building your application, see [here](./BUILDING.md).

## Run the Scaffolding Tools to Build Out Project

No default scaffolds are included in the base project. It is intended that the base webstart project will be forked for
each target platform to add platform specific functionality and scaffolding tasks.

Just run `grunt` from the command line and choose the scaffolding option.

    grunt
    [?] What task would you like to run? (Use arrow keys)
    ❯ Run the Scaffolding Tools to Build Out Project
      Run the Web Application
      Run the Cordova Application
      Run the API Designer
      Run the Test Suite
      Run the Documentation Generator
      Publish the Application for Deployment

You will then be prompted with some followup questions:

    [?] Which scaffolding task would you like to run? test
    [?] What action do you want to perform? create
    [?] What is the name of the component? mycomponent
    [?] What is the name of the module for the component? (main) mymodule

You can bypass the initial prompts by running `grunt scaffold`. You can also pass in the answers to the questions using
the target and a comma delimited list:

    grunt scaffold:test,create,mycomponent,mymodule

For more information about building your application, see [here](./SCAFFOLDING.md).

## Run the Cordova Application

!!!This functionality may require some updating to the support the latest versions of Cordova, please help us [here](./CONTRIBUTING.md)!!!

Support is also included for building Cordova based applications. During the initial setup or your project, you
were asked if you want to use Cordova for your application. If you selected this option, you were presented with a
few questions to setup the Cordova project:

    [?] What is the title to give your application? webstart
    [?] What is the identifier to give your application? com.webstart
    [?] What platforms do you need to support? (Press <space> to select)
    ❯⬢ ios
     ⬡ android
     ⬡ amazon-fireos
     ⬡ blackberry10
     ⬡ firefoxos
     ⬡ wp8
     ⬡ windows8

This will setup and install all the required cordova dependencies into the current project directory. The build system
will check for all platforms/*/www directory references and will compile each of these separately with your merged
platform changes in platforms/*/platform_www. The build process includes hooks with Cordova so that post prepare after
Cordova has merged the root www with each platforms/*/platform_www into platforms/*/www, the build system will compile
each of these platforms separately and publish the results back into platforms/*/www.

Note that Cordova and related dependencies are installed in the project directory, not at the global location.  Assuming
you are using your main Mac OS X to work on your Cordova project, you'll need to add this line to your bash profile (it may be at
~/.profile location) so that when Grunt or your Cordova command line entries look for Cordova's executable it looks for it
in the project directory. 

export PATH=./node_modules/.bin:$PATH

If for any reason you skipped this step during the initial setup, you will be prompted again the first time
you try to build Cordova application using the `grunt` task:

    grunt
    [?] What task would you like to run?
      Run the Scaffolding Tools to Build Out Project
      Run the Web Application
    ❯ Run the Cordova Application
      Run the API Designer
      Run the Test Suite
      Run the Documentation Generator
      Publish the Application for Deployment

This will prompt you how to run the Cordova application:

    [?] What task would you like to run? Run the Cordova Application
    [?] Hod do you want to run the Cordova Application? (Use arrow keys)
    ❯ Run in Platform Emulator
      Run on Platform Device

You can also bypass the prompt by simply running either `grunt cordova:emulate` or `grunt cordova:run`.

You can also use cordova commands directly to build, run and emulate. The hooks are still in place for grunt to
build the appropriate files. If you use this grunt command, it will also include livereload support by spawning an
internal web server to host the application for each platform and update the config.xml file to point to this server.

## Run the API Designer

The starter includes support for defining API specifications using [RAML](http://raml.org/). The included task here will
launch the RAML Designer tool which provides support for editing and previewing the definitions and documentation.

This will also generate some client side code used for implementing the API and providing mock data for testing
purposes.

The API designer is an optional tool, you are free to edit the RAML files using any IDE and 

You can simply run `grunt` from the command line and choose one of the testing options.

    grunt
    [?] What task would you like to run?
      Run the Scaffolding Tools to Build Out Project
      Run the Web Application
      Run the Cordova Application
    ❯ Run the API Designer
      Run the Test Suite
      Run the Documentation Generator
      Publish the Application for Deployment

You can also bypass the prompt by simply running either `grunt server:apidesigner`.

For more information about available API options in this project, see [api](api).

## Run the Test Suite

The starter includes support for both unit and integration tests. Unit tests are executed with [Karma](http://karma-runner.github.io/0.12/index.html) while
integration tests are executed by [Protractor](http://angular.github.io/protractor/#/). Unit tests are authored using Jasmine in the `/specs` folder for each
module. Integration tests are also authored using Jasmine in the `/e2e` folder for each module.

Specs are compiled by browserify so `require(...)` and other NodeJS primitives may be used in specs just as with your other modules.

When tests are executed, app code is first instrumented via Istanbul in order to collect code coverage data.
Currently, all tests are executed within PhantomJS rather than real browsers though this is an opportunity for future enhancement.

You can simply run `grunt` from the command line and choose one of the testing options.

    grunt
    [?] What task would you like to run?
      Run the Scaffolding Tools to Build Out Project
      Run the Web Application
      Run the Cordova Application
      Run the API Designer
    ❯ Run the Test Suite
      Run the Documentation Generator
      Publish the Application for Deployment

You will then be prompted with the following options:

    [?] What task would you like to run? Run the Test Suite
    [?] What tests would do you want to run? (Use arrow keys)
    ❯ Run Unit Tests, Code Coverage and ESLint
      Run End-to-End Integration Tests

You can also bypass the prompt by simply running either `grunt server:test` or `grunt server:e2e`.

When running tests in the browser, you can use browser dev tools to set breakpoints and diagnose issues.
Open http://localhost:8082/debug.html in a browser then open the dev console on this page.  (Tests execute here,
not within the page that displays the test reults).  The actual running code is instrumented for code
coverage tracking and then compiled by browserify so you will see some files listed.  Fortunately,
sourcemaps are in place so the browser dev tools will also make your *original* source files
available - set your breakpoints in these files and ignore the build-generated files.

## Run the Documentation Generator

This will generate HTML documentation based on the RAML service specifications in /api and run the
[JSDoc](http://usejsdoc.org/) engine against the current code base. The scaffolding tasks automatically generate code that
includes comments in the JSDoc format that you can use as your template when writing your own code.

By default, all documentation is output to the `bin/documentation`. The server option will watch for changes to
your source code and automatically rebuild and reload the documentation changes in the browser.

The default template engine is using [DocStrap](https://github.com/terryweiss/docstrap) with the
[spacelab](http://terryweiss.github.io/docstrap/themes/spacelab/) theme. You can customize this via the `jsdoc.conf.json`
file in the root of the project.

Simply run the `grunt` command and run documentation option.

    grunt
    [?] What task would you like to run?
      Run the Scaffolding Tools to Build Out Project
      Run the Web Application
      Run the Cordova Application
      Run the API Designer
      Run the Test Suite
    ❯ Run the Documentation Generator
      Publish the Application for Deployment

Then you will be prompted with the following options:

    [?] What task would you like to run? Run the Documentation Generator
    [?] Where do you want to publish documentation to? (Use arrow keys)
    ❯ Run in Browser (livereload)
      Just Export to bin/documentation

You can also bypass the prompt by simply running `grunt server:docs` or `grunt docs`.

## Publish the Application for Deployment

This will run all the build tasks and publish a final compressed output of your application to a directory named `bin`.
This will also zip up the final contents of the dist directory and create a zip and war file for you automatically which
can be used for deployment to a web server.

Simply run the `grunt` command and select the publish option.

    grunt
    [?] What task would you like to run?
      Run the Scaffolding Tools to Build Out Project
      Run the Web Application
      Run the Cordova Application
      Run the API Designer
      Run the Test Suite
      Run the Documentation Generator
    ❯ Publish the Application for Deployment

You can also bypass the prompt by simply running `grunt publish`.
