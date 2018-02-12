// RAML Validator
// Validate raml code and file structure

module.exports = function (grunt) {
	grunt.registerTask('validateraml', function(){
		var raml = require('raml-parser'),
			fs = require('fs'),
			path = require('path'),
			Validator = require('jsonschema').Validator,
			_ = require('lodash'),
			jsonSchema,
			resultErrors = [],
			apiFiles = grunt.file.expand(grunt.config('settings.dir.apiroot') + '/*.raml'),
			v = new Validator();

        grunt.config('watch.validateraml', {
            files: grunt.config('settings.dir.apiroot') + '/**/*.*',
            tasks: 'validateraml'
        });


		// Cache schema errors
		function cacheErrors(file, resource, method, schemaType, schemaErrors) {
			if (schemaErrors && schemaErrors.length > 0) {
				resultErrors.push({file: file, resource: resource, method: method, schemaType: schemaType, errors: schemaErrors });
			}
		}

		// Output errors to console
		function outputErrors()
		{
			if (resultErrors.length == 0) {
				grunt.log.ok('validateraml done, without errors.');
				return;
			}

			_.each(resultErrors, function(resultError) {
				var tab = '\t';
				var cleanError = 'Invalid ' + resultError.schemaType + tab;

				if (resultError.file) {
					cleanError += resultError.file + tab;
				}
				if (resultError.resource) {
					cleanError += resultError.resource;
				}
                if (resultError.method) {
                    cleanError += ' (' + resultError.method + ')';
                }

                grunt.log.error('');
				grunt.log.error(cleanError);

				var schemaErrors = resultError.errors;
				_.each(schemaErrors,function(schemaError) {
		    		grunt.log.error('     ' + schemaError.stack);
				});
			});
		}

		// Load schema and attach to validator
		function loadDefaultSchema() {
			jsonSchema = grunt.file.readJSON('build/tasks/jsonschema-draft-o4.json');
			v.addSchema(jsonSchema);
		}

		// Get body element from json
		function getBodyObject(method) {
			var resultBody = method.body;
			if (!resultBody) {
				var response = method.responses;
				if (response) {
					for (var state in response) {
						var stateObj = response[state];
						if (stateObj) {
							resultBody = stateObj.body;
							if (resultBody)
								break;
						}
					}
				}
			}
			if (resultBody)
				return resultBody['application/json'];

			return resultBody;
		}

		// Validate resource (service)
		function loadSchema(filename, name, schema) {
			var validateSchemaResults;
			try {
				var schemaObj = JSON.parse(schema)
				validateSchemaResults = v.validate(schemaObj, jsonSchema);
				v.addSchema(schemaObj);
			}
			catch(err) {
				validateSchemaResults = {errors: [{ stack: err.message}] };
			}

			cacheErrors(filename, name, '', 'Schema', validateSchemaResults.errors);
		}

		// Validate resource (service)
		function processResource(filename, resource, parentResourceName) {
			var methods = resource.methods,
				resourceName = resource.displayName;

			// If this is a subresource, include the parent name
			if (parentResourceName) {
				resourceName = parentResourceName + ' -> ' + resource.displayName;
			}

			if (methods) {
				_.each(methods, function(method) {
					var body = getBodyObject(method),
						methodName = method.method;
					if (body && body.schema) {

						var schemaObj = {},
                            exampleObj = {},
                            validateSchemaResults,
                            validateExampleResults;

						// Validate Schema
						try {
                            schemaObj = JSON.parse(body.schema);
                            validateSchemaResults = v.validate(schemaObj, jsonSchema);
						}
						catch(err) {
							validateSchemaResults = {errors: [{ stack: err.message}] };
						}
						cacheErrors(filename, resourceName, methodName, 'Schema', validateSchemaResults.errors);

						// Validate Example
						try {
                            exampleObj = JSON.parse(body.example);
							validateExampleResults = v.validate(exampleObj, schemaObj);
						}
						catch(err) {
							validateExampleResults = {errors: [{ stack: err.message}] };
						}
						cacheErrors(filename, resourceName, methodName, 'Example', validateExampleResults.errors);
					}
				});
			}

			// Process resources in this resource
			var subResources = resource.resources;
			if (subResources) {
				_.each(subResources, function(subResource) {
			    	processResource(filename, subResource, resourceName);
				});
			}
		}

		// Load default schema
		loadDefaultSchema();

        var done = this.async(),
        doneCount = 0,
        fileDone = function(){
            doneCount++;
			if(doneCount >= apiFiles.length) {
				done();

				outputErrors();
            }
        };

		// Load and validate primary raml file
		apiFiles.forEach(function (file) {
			raml.loadFile(file).then(
                function(data) {
					try {
						// Load schemas from resources
						var schemas = data.schemas;
						_.each(schemas, function(schemaObj) {
							_.each(schemaObj, function(schema, key) {
								loadSchema(file, key, schema);
							});
						});

						// Validate schemas and examples
						var resources = data.resources;
						_.each(resources, function(resource) {
					    	processResource(file, resource, '');
						});

						fileDone();
					}
					catch(error) {
                        grunt.log.error('Error processing ' + file + ': ' +  error + ' ' +  error.stack);
                        fileDone();
					}
				},
				function(error) {
                    grunt.log.error('Error parsing ' + file + ': ' +  JSON.stringify(error, null, '\t'));
                    fileDone();
                }
			);
		});
	});
};
