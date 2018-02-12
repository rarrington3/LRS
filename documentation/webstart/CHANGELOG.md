# Recent Changes

## 2016-06-29

* Updated default port assignments from 90xx to 80xx which is not blocked in default firewall settings
* Mocks are now on port 8088 instead of 9999
* Bug fixes for eslint errors

## 2016-06-07

* Updated and reorganized all webstart related documentation to `documentation/webstart`.
* Updated the default destination for project generated documentation from `bin/documentation` to `documentation/generated`. If you
need to change this back for your existing project, please see [settings.json](../../settings.json).

## 2016-06-06

* ESLint is now run during builds, not just tests. This gives the user feedback during normal development via the console output.
* ESLint rules were made a bit stricter, specifically preferring `let` to `var` statements for new development. You may
need to update this rule for existing projects, please see [.eslintrc](../../ieslintrc).

## 2016-05-27

* Leveraging `exorcist` to make sourcemaps for source code external rather than embedded, reducing file sizes.
* Fixes for the karma-html-reporter to support reporting file and line numbers from the sourcemap rather than compiled files.

## 2016-04-05

* Updates to make the api directory configurable in `settings.json`
* Added a new scaffold for generating RAML files

## 2016-02-16

* Added new mock server running on port 9999 that is automatically generated from the RAML spec and example files. 
This server can be used for local mock testing and validating the construction of UI requests to the server. You can set 
the mock server in your gruntfile.js via the proxy and profile configurations, just like any other environment, like 
development or production.
