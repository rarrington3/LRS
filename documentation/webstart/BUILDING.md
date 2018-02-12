# Building and Configuring your Project

Please be sure to follow the instructions for [setting up](./SETUP.md) this project.

The build system uses [browserify](http://browserify.org/) for the packaging system. By default, you are not required to 
use require syntax (CommonJS or ES6 modules) in your code. You can simply write code like normal and each file will be 
wrapped and executed in an isolated scope as to not accidentally impact the global namespace. You may however use 
CommonJS/ES6 module statements if you wish and this will all be supported. You can use require statements to control 
load ordering and dependencies, or even to pipe in different options like including style references in your DOM using 
cssify.

All build related task code can be found in [build.js](../../build/tasks/build.js). There are build configuration 
defaults set there that can easily be overwritten in your [gruntfile.js](../../gruntfile.js). Some settings also 
derive from [settings.json](../../settings.json).

## Running the Build

Running the build is executed via the [Grunt tasks](./TASKS.md) generally via one of the following commands:

* `grunt server` - This is the most typical scenario, build the application and host it in a server, watch for changes and recompile.
* `grunt publish` - This is generally used when you are ready to deploy your application or to be integrated with a CI server.

For more information on this, please see the [tasks](./TASKS.md) section.

## Plugins and Transforms

While the packaging and module resolvement is done via Browserify, there are sever plugins and transforms already 
included in Webstart to handle the following:

* Babilify - Uses [Babel](https://babeljs.io/) to transpile advanced features of ES6/2015 and ES7+ to ES5 that runs in all of today's browsers.
See `.babelrc` for more configuration options.
* Tsify - (Optional) Uses [TypeScript](http://www.typescriptlang.org/) compiler to convert TypeScript code to ES6 code which is then transpiled to ES5 via
Babel. To use this feature, you must install tsify and optionally a specific version of typescript to use
(`npm install tsify typescript@beta --save-dev`). See `tsconfig.json` for more configuration options.
* Styleify - A custom transform that allows importing CSS, LESS, SASS and SCSS files into your code. LESS/SASS/SCSS is compiled
down to CSS and all CSS is then packaged with [autoprefixer](https://github.com/postcss/autoprefixer). There are different
settings in `package.json` for how stylesheets are returned from a require statement.
** external - Compiles the stylesheet to an external document and automatically inserts a link element and adds it to the DOM.
** embed - Compiles the stylesheet to a string embeded in the JavaScript and inserts a style element and adds it to the DOM.
** return - Compiles the stylesheet to a string embeded in the JavaScript and returns the string value.
* Stringify - Allows requiring any type of text file and embedding it into JavaScript as a string value. The default is
configured in `package.json` to support *.txt and *.html files.
* Browserify-Shim - Allows you to configure external global references for require statements that are not bundled by
browserify. By default, this is configured for items installed by bower that produce global references since they are
not installed via npm and packaged. Example, `var $ = require('jquery')'` where jquery package was installed via bower
rather than npm.

For more information on other transforms available for Browserify see 
[here](https://github.com/substack/node-browserify/wiki/list-of-transforms) and 
[here](https://www.npmjs.com/browse/keyword/browserify-transform).

You can install additional transforms via `npm` and configure those transforms in [package.json](../../package.json).

## ES5 (CommonJS) vs ES6 Module Syntax

```
//ES5+
var $ = require('jquery');					//import the default export into a local reference called template
var data = require('./data.json');			//import the JSON file as a JavaScript object and return
require('./styles.scss');					//compile and import the stylesheet, insert into the DOM (configuration "external" or "embed")
var styles = require('./styles.scss');		//compile and import the stylesheet, return string value (configuration "return")
var template = require('./template.html');	//import the HTML template into a local reference called template
var bar = require('./foo').bar;				//import only bar from package foo into a local reference called bar
var foo = require('./foo');					//import everything from package foo into a local reference called foo

module.exports = function Foo(){};			//exports a single default constructor called Foo
module.exports = {foo: 'foo', bar: 'bar'};	//exports foo and bar

```

```
//ES6+
import $ from 'jquery';						//import the default export into a local reference called template
import data from './data.json';				//import the JSON file as a JavaScript object and return
import './styles.scss';						//compile and import the stylesheet, insert into the DOM (configuration "external" or "embed")
import styles from './styles.scss';			//compile and import the stylesheet, return string value (configuration "return")
import template from './template.html';		//import the HTML template into a local reference called template
import {bar} from './foo';					//import only bar from package foo into a local reference called bar
import * as foo from './foo';				//import everything from package foo into a local reference called foo

export default class Foo {}					//exports a single default class called Foo
export foo; export bar;						//exports named modules foo and bar

```

For more information about ES6 Module syntax, see [here](http://www.2ality.com/2014/09/es6-modules-final.html).

## Bundling

To make development easier, the default build tasks will automatically generate several bundles for you so that you
do not need to spend time manually managing a bunch of script and style tags or the complexities of using RequireJS.
Just start adding files to the designated folders and the bundles are generated for you automatically and are referenced
in the default `index.html` file.

Any files named `index.js` or `main.js` will take first execution priority over other files, likewise files higher
in the directory structure will take priority. This will still obey any require/import statements for those priorities.

All bundle files are generated to a temporary directory (`.temp`) which when you run the server commands in grunt will
be mapped at the same location as your application and test directories.

#### libs

The first bundle created is `libs/libs.js`. This file is generated during a build by watching
changes in bower.json and following all dependency trees (not development dependencies) to concatenate a single bundle.
The bower.json file for each component should include a "main" property indicating the main files of the module that
should be included in the bundle.

This bundle is NOT run through browserify but instead simply concatenated. This allows you to access global variables like
$, _, angular, Backbone...without require statements and creating shims for components that don't support AMD out of
the box. It is still prefered to reference these dependencies in your code using require/import statements and 
creating a Browserify Shim in [package.json](../../package.json).

Please use NPM to install dependencies that are CommonJS/UMD formatted by default and require/import them as needed
directly. This avoids using the global space and/or the need to create the Browserify Shims.

#### modules

The second bundle created is `modules/modules.js`. This is built using all the files matching the pattern `modules/*/scripts/**/*.{js,jsx,ts,tsx}`.
This is for convenience to bundling everything together into 1 file that is automatically referenced in
the `index.html` file by default. This generated file will be placed in the `.temp` directory.

You should use the require statements for any CSS, LESS, SCSS, SASS or HTML files you want to process and include in the
modules bundled output.

    require('modules/mymodule/styles/main.less');

###### Isolate Modules

You can exclude certain modules from being included in this bundle by declaring them as isolate in 
[settings.json](../../settings.json). By default, the preloader module is excluded since its inlined in the top of the 
page. These modules may be loaded either in separate HTML files or dynamically at runtime as needed.

<pre>
    "isolateModules": [
        "preloader",
        "foo"
    ]
</pre>

Each isolate modules directory will be bundled individually. For example, if you have `modules/foo`,
then the build task will watch for changes to `modules/foo/scripts/**/*.js` to concatenate a single bundle named
`modules/foo.js`. This generated file will be placed in the `.temp` directory.

###### Module Specs

The contents of the `*.spec.*` files in each module are only included during test builds and are not normally bundled
or included in the published output. You can also launch any grunt task that executes build with the `--specs` option
to enable this. 

###### Module Mocks

The contents of the `*.mock.*` files in each module are included by default in all builds if they exist. This is intended
to include all end-to-end mocks for services when no backend yet exists. You can also launch any grunt task that
executes build with the `--nomocks` option to disable this. You can change the default in [gruntfile.js](../../gruntfile.js):

<pre>
    grunt.initConfig({
        build: {
            options: {
                nomocks: true
            }
        }
    });
</pre>

Note that in the latest changes to Webstart, mocks are served up from a dynamically generated web server on port 8088
during a build. This is good for validating the UI's requests, but static mock files may still be better suited 
for working more easily with unit tests. 

See the [RAML](./RAML.md) section for more information on mock data and working with the RAML API.

#### sprites and font icons

Spritesheet and font icon support is also baked in. By default, the build process will generate a spritesheet for any directory that
contains PNG files in it and font icons for any directory that contains SVG files in it. You can choose whether or not
to use these generated spritesheet and font icons in your project or not.

Example: If you have `www/modules/mymodule/images/*.png` then the following files will be generated:
`www/modules/mymodule/images/_images.generated.png` and `www/modules/mymodule/images/_images.generated.css` (or less or scss or sass).

Example: If you have `www/modules/mymodule/icons/*.svg` then the following files will be generated:
`www/modules/mymodule/icons/_icons.generated.{woff,eot,ttf,svg}` and `www/modules/mymodule/icons/_icons.generated.css` (or less or scss or sass).

The generated stylesheets can be imported into your master stylesheet and referenced like normal. The less and sass
processors will resolve loading local references both from the source directory and the temporary directory automatically,
so simply import like normal `@import "www/modules/mymodule/images/_images.generated.less"` or `@import "../icons/_icons.generated.scss"`.

*NOTE:* The font generator assumes that SVG's have a size of 512px. If this is not the case, the build can compensate for this if you
include the size in pixels in the name of the containing directory, ie "icons_48px/*.svg". It will set the font size to
base on 48px rather than the default 512px.

## Profile Based Configurations

In most applications, you will need to need some profile based configuration options. Lets say for instance that your
3rd party services endpoint needs to configured differently for each published profile. Or you may want to capture something
like the current version number of the application and pass in some suffix value from a CI server.

Open `gruntfile.js` and set the config options for `profiles`

<pre>
    var apiProxy = [
            {
                context: '/proxy-dev',
                host: '10.39.3.25',
                port: '8080',
                rewrite: {
                    '/proxy-dev': ''
                }
            },
            {
                context: '/proxy-qa',
                host: '10.39.3.25',
                port: '8081',
                rewrite: {
                    '/proxy-qa': ''
                }
            },
            {
                context: '/proxy-iis',
                host: 'localhost',
                port: '53037',
                rewrite: {
                    '/proxy-iis': ''
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
</pre>

<pre>
    profiles: {
        //TODO: register your default base configuration options
        default: {
            //this is the base configuration that specific profiles will merge into
            config: {
                version: '<%= package.version %>.' + (grunt.option('buildnum') || 'local'),
                server: '',//locally resolve API
                mocks: false
            }
        },
        mock: {
            //these settings will merge into default if using the --profile=mock option when running grunt
            config: {
                mocks: true,//force all mocks enabled
                server: '/mock/'//use the 'mock' proxy server which may map to port 8088
            }
        },
        dev: {
            //these settings will merge into default if using the --profile=dev option when running grunt
            config: {
                server: '/proxy-dev/'//use the 'proxy-dev' proxy server which may map cross domain to another server or port
            }
        },
        qa: {
            //these settings will merge into default if using the --profile=qa option when running grunt
            config: {
                server: '/proxy-qa/'
            }
        },
        iis: {
            //these settings will merge into default if using the --profile=iis option when running grunt
            config: {
                server: '/proxy-iis/'
            }
        }
    }
</pre>

Now, you can run any grunt task with the following options:

    grunt build --profile=dev --buildnum=${bamboo.buildNumber}

Now you can access these merged configuration options using `require('config')`:

    var config = require('config');

    console.log(config.version);// 1.0.0.312
    console.log(config.server);// /proxy-dev/

You can customize this config object in `gruntfile.js` to whatever your application needs.

Note that these server configurations work in tandom with the api proxy in gruntfile.js. You can use this
to bypass CORS and enable your application to reach a remote API server during local development.
