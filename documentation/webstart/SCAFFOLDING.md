# Running and Creating Scaffolds

Please be sure to follow the instructions for [setting up](./SETUP.md) this project.

All of the scaffolding tasks are accessible from the default grunt task prompt and selecting the scaffolding
option ('Run the Scaffolding Tools to Build Out Project').

Just run `grunt` from the command line and choose the scaffolding option.

    grunt
    [?] What task would you like to run? (Use arrow keys)
    ❯ Run the Scaffolding Tools to Build Out Project
      Run the Web Application
      Run the Cordova Application
      Run the Test Suite
      Run the Documentation Generator
      Publish the Application for Deployment

You will then be prompted with some followup questions that will vary per scaffold task:

    [?] Which scaffolding task would you like to run? module
    [?] What action do you want to perform? create
    [?] What is the name of the component? mymodule

You can bypass the initial prompts by running `grunt scaffold`. You can also pass in the answers to the questions using
the target and a comma delimited list:

    grunt scaffold:module,create,mymodule

##component

The `component` scaffold task will stub out a basic Angular2 component in your application into the path
`www/modules/[scaffold.module]/components`. A component is a special form of a directive which targets an 
element rather than an attribute and includes a template to render the view into.

* **[scaffold.name].js** - The main component JavaScript file which utilizes ES6+ syntax. 
* **[scaffold.name].spec.js** - The spec test JavaScript file for the component that targets Jasmine and Karma for unit tests.
* **[scaffold.name].[settings.styleformat]** - The stylesheet for this component where rules are scoped to the attached 
host component. These styles are injected into the components `styles` property.
* **[scaffold.name].html** - The template for this component that defines the view. This template is injected into the 
components `template` property.

A reference to this generated file is also appended into `www/modules/[scaffold.module]/components/index.js` for
easily importing all components from this package into another module. This also includes a reference to
`[scaffold.module]_COMPONENTS` (ie MYMODULE_COMPONENTS) which is an array of all component definitions so you
can easily add all module components to another components `directives` property.

##directive

The `directive` scaffold task will stub out a basic Angular2 directive in your application into the path
`www/modules/[scaffold.module]/directives`. A directive is a generally matched against a target via an 
attribute and modifies the targets behavior rather than layout.

* **[scaffold.name].js** - The main component JavaScript file which utilizes ES6+ syntax. 
* **[scaffold.name].spec.js** - The spec test JavaScript file for the component that targets Jasmine and Karma for unit tests.

A reference to this generated file is also appended into `www/modules/[scaffold.module]/directives/index.js` for
easily importing all components from this package into another module. This also includes a reference to
`[scaffold.module]_DIRECTIVES` (ie MYMODULE_DIRECTIVES) which is an array of all component definitions so you
can easily add all module directives to another components `directives` property.

##pipe

The `pipe` scaffold task will stub out a basic Angular2 pipe in your application into the path
`www/modules/[scaffold.module]/pipes`. A service is something that is instantiated by Angular2 and its instance
then injected into the constructor of a target.

* **[scaffold.name].js** - The main service JavaScript file which utilizes ES6+ syntax. 
* **[scaffold.name].spec.js** - The spec test JavaScript file for the component that targets Jasmine and Karma for unit tests.

A reference to this generated file is also appended into `www/modules/[scaffold.module]/pipes/index.js` for
easily importing all components from this package into another module. This also includes a reference to
`[scaffold.module]_PIPES` (ie MYMODULE_PIPES) which is an array of all pipe definitions so you
can easily add all module pipes to another components `pipes` property.

##service

The `service` scaffold task will stub out a basic Angular2 injectable service in your application into the path
`www/modules/[scaffold.module]/services`. A service is something that is instantiated by Angular2 and its instance
then injected into the constructor of a target.

* **[scaffold.name].js** - The main service JavaScript file which utilizes ES6+ syntax. 
* **[scaffold.name].spec.js** - The spec test JavaScript file for the component that targets Jasmine and Karma for unit tests.

A reference to this generated file is also appended into `www/modules/[scaffold.module]/services/index.js` for
easily importing all components from this package into another module. This also includes a reference to
`[scaffold.module]_SERVICES` (ie MYMODULE_SERVICES) which is an array of all pipe definitions so you
can easily add all module services into an injector for unit tests. This is also automatically appended to 
`www/app/providers.js` so that the newly created service is automatically registered as a provider in your application.

##view

The `view` scaffold task will stub out a basic Angular2 routed view in your application into the path
`www/modules/[scaffold.module]/views`. A view is a special form of a component which has reference to routing
defintions both for itself and optionally its children.

* **[scaffold.name].js** - The main component JavaScript file which utilizes ES6+ syntax. 
* **[scaffold.name].spec.js** - The spec test JavaScript file for the component that targets Jasmine and Karma for unit tests.
* **[scaffold.name].e2e.js** - Similar to the spec file for unit tests, however this leverages Jasmine and Protractor for end-to-end tests.
* **[scaffold.name].[settings.styleformat]** - The stylesheet for this component where rules are scoped to the attached 
host component. These styles are injected into the components `styles` property.
* **[scaffold.name].html** - The template for this component that defines the view. This template is injected into the 
components `template` property.

A reference to this generated file is also appended into `www/modules/[scaffold.module]/directives/index.js` for
easily importing all views from this package into another module. This also includes a reference to
`[scaffold.module]_VIEWS` (ie MYMODULE_VIEWS) which is an array of all view definitions so you
can easily add all module views to another components route definition.

## raml

The `raml` scaffold task will stub out a new RAML REST API Endpoint to `api/[scaffold.path]`.
This will include the following files:

* **api/[scaffold.path]/[scaffold.name_plural].raml** - A RAML resource defintion file with definitions for top-level collection and sub-
resource for a containing member.
* **api/[scaffold.path]/[scaffold.name_plural]-example.json** - An example JSON file for the collection.
* **api/[scaffold.path]/[scaffold.name_plural]-schema.json** - An schema JSON file for the collection.
* **api/[scaffold.path]/[scaffold.name_singular]-example.json** - An example JSON file for the member.
* **api/[scaffold.path]/[scaffold.name_singular]-schema.json** - An schema JSON file for the member.

This scaffold will also modify the root level RAML definition file to add reference to the new
schemas and resource endpoint. This root file is also read in to figure out the default baseUri
to use as the default resource path for the given raml name. 

    Running "prompt:scaffold" (prompt) task
    [?] Which scaffolding task would you like to run? raml 
    [?] What is the name of the raml? users
    [?] Which raml file do you want to add to? api.v1.raml
    [?] What is the path of the raml resource? /api/v1/users
    
    GENERATED: build/scaffolds/raml/api/[scaffold.path]/[scaffold.name_plural]-example.json.hbs => api/api/v1/users/users-example.json
    GENERATED: build/scaffolds/raml/api/[scaffold.path]/[scaffold.name_plural]-schema.json.hbs => api/api/v1/users/users-schema.json
    GENERATED: build/scaffolds/raml/api/[scaffold.path]/[scaffold.name_plural].raml.hbs => api/api/v1/users/users.raml
    GENERATED: build/scaffolds/raml/api/[scaffold.path]/[scaffold.name_singular]-example.json.hbs => api/api/v1/users/user-example.json
    GENERATED: build/scaffolds/raml/api/[scaffold.path]/[scaffold.name_singular]-schema.json.hbs => api/api/v1/users/user-schema.json
    >> api/api.v1.raml has been updated with reference to these new files.

# Building your own Scaffolds

## Overview
You can create your own scaffolding tasks very easily without writing any code. You just need to create your template
files (using the default [lodash.template](http://lodash.com/docs#template) format) into a specific structure. You can
use a preexisting scaffold as a basis for your new scaffold.

* Create a new folder under `build/scaffolds`. The name of this folder you create is the name of the task presented to the
user when you using the scaffolding option from the `grunt scaffold` command.
* Anything in this folder will be resolved and copied to the projects root directory when the scaffolding task is completed.
* The default context file used in the templates is a mix of data collected from the scaffolding questions and the project
settings:

<pre>
    {
        "settings": {
            "dir": {
                "temp": ".temp",
                "app": "www",
                "test": "test",
                "dist": "dist",
                "cordova": "."
              },
              "styleformat": "css"
        },
        "scaffold": {
            "task": "controller",
            "action": "create",
            "name" : "MyController",
            "module": "main"
        }
    }
</pre>

* The context object also includes helper methods: camelCase, headlessCamelCase, hyphenCase, concat. These can be used
in conjunction with the values from the context object in your template files.

    <%= camelCase(scaffold.name) %>

* File and folder names use a simplified version of the templating. Just wrap your property names in brackets.

    [scaffold.name]

* Finally, any template stylesheet you include that begins with `_` will automatically be added as an import to any
existing stylesheet in the same destination directory that does not begin with `_`.

Check out the `module` scaffold that is in the current project for reference.

## Advanced Options
You can customize the functionality of the scaffold by simply creating a `scaffold.js` file in your scaffold task directory.
This file will expected to be formatted like a standard grunt file (`module.exports = function (grunt) {...};`). If the file
`build/scaffolds/*/scaffold.js` exists, it will be executed as soon as the scaffold task is selected at the user prompt.

In your `scaffold.js` file, you can add custom tasks, config settings and listen for special events to hook into the scaffolding
system. By doing this, you can prompt for additional input from the user and/or perform additional pre and post scaffold processing.

Events are emitted using a format of `scaffold.[scaffold.task].[stage]`. There are 4 stages: `prompt`, `prompted`, `execute` and `executed`.
From the event listener, you can use `grunt.config('scaffold')` to access the current scaffold options data collected during the prompts.

Example:
<pre>
    module.exports = function (grunt) {

        'use strict';

        grunt.event.on('scaffold.module.prompt', function (event) {
            //scaffold will start prompting questions for "module" task
            //event.preventDefault() would cancel this
            //grunt.task.run('moduleprompt') would run 'moduleprompt' before prompting begins
        });

        grunt.event.on('scaffold.module.prompted', function (event) {
            //scaffold has finished prompting questions for "module" task
            //event.preventDefault() does nothing
            //grunt.task.run('moduleprompted') would run 'moduleprompted' after prompting ends
        });

        grunt.event.on('scaffold.module.execute', function (event) {
            //scaffold will start executing "module" task using the information collecting during prompt
            //event.preventDefault() would cancel this
            //grunt.task.run('moduleexecute') would run 'moduleexecute' before execution begins
        });

        grunt.event.on('scaffold.module.prompted', function (event) {
            //scaffold has finished executing "module" task
            //event.preventDefault() does nothing
            //grunt.task.run('moduleexecuted') would run 'moduleexecuted' after execution ends
        });

        grunt.config.merge({
            //add/modify config options
        });

        grunt.registerTask('moduleprompt', function () {
            //do something
        });

        grunt.registerTask('moduleprompted', function () {
            //do something
        });

        grunt.registerTask('moduleexecute', function () {
            //do something
        });

        grunt.registerTask('moduleexecuted', function () {
            //do something
        });

    };
</pre>
