/**
 * This file invokes the /build/scaffolds/_api scaffold based on parsed RAML files
 */
module.exports = function (grunt) {
    grunt.config.merge({
        clean: {
            apicodegen: [
                '<%= settings.dir.app %>/**/*.generated*.js'
            ]
        },
        watch: {
            apicodegen: {
                files: ['<%= settings.dir.apiroot %>/**/*.raml', 'build/scaffolds/.api/**/*.*', 'build/scaffolds/.api/.hbs-helpers/**/*.*'],
                tasks: ['apicodegen'],
                options: {
                    spawn: false
                }
            }
        }
    });
    grunt.registerTask('apicodegen', ['clean:apicodegen','validateraml','_apicodegen']);
    grunt.registerTask('_apicodegen', function(){
        var path = require('path'),
            fs = require('fs-extra'),
            raml = require('raml-parser'),
            jsGenerator = require('raml-javascript-generator'),
            _ = require('lodash'),
            files = grunt.file.expand(`${grunt.config('settings.dir.apiroot')}/*.raml`),
            pluralize = require('pluralize')
            Context = require('./libs/TemplateContext'),
            Engine = require('./libs/TemplateEngine');

        if (!files.length) {
            return;
        }

        var done = this.async(),
            doneCount = 0,
            fileDone = function(){
                doneCount++;
                if(doneCount >= files.length) {
                    done();
                }
            };

        _.each(files, function (file) {
            raml.loadFile(file).then(
                function(data) {
                    try {
                        var output = jsGenerator(data);
                        //create a new node_module for each RAML file for the default js generator
                        _.forEach(output.files, function (contents, name) {
                            grunt.file.write(path.resolve('node_modules', path.basename(file, '.raml'), name), contents);
                        });

                        // this seems bad, but I don't know of another way to Context's utility functions from here
                        var ctx = new Context();

                        data.controllers = [];

                        // traverse the RAML tree and add parent links, controllers, and action names
                        function traverse(resource) {
                            // add parent links to child resources
                            _.each(resource.resources, function(childResource) {
                                childResource._parentResource = resource;
                            });

                            // build full URI and segment array
                            resource.uriPathSegments = resource.relativeUriPathSegments || [];
                            if (resource._parentResource) {
                                resource.uriPathSegments = resource._parentResource.uriPathSegments.concat(resource.uriPathSegments);
                            }

                            // skip this whole block for the root
                            if (resource._parentResource) {
                                var uri = resource.uriPathSegments.join("/"); 

                                var objects = _.filter(resource.uriPathSegments, function(s) { return s.indexOf("{") < 0; });
                                if (objects.length == 0) {
                                    throw uri + ": no object parameters; the URI consists entirely of parameters...is this intentional?";
                                }
                                var firstObjects = objects.slice(0, objects.length - 1);
                                var firstObjectsSingular = _.map(firstObjects, function(o) { return pluralize(o, 1); });

                                var lastObject = objects[objects.length - 1];
                                var lastObjectSingular = pluralize(lastObject, 1);
                                var lastObjectPlural = pluralize(lastObject);

                                var combinedObject = firstObjectsSingular.concat(lastObject).join("_");
                                var combinedObjectSingular = firstObjectsSingular.concat(lastObjectSingular).join("_");
                                var combinedObjectPlural = firstObjectsSingular.concat(lastObjectPlural).join("_");

                                var lastObjectIndex = _.findLastIndex(resource.uriPathSegments, function(s) { return s.indexOf("{") < 0; });
                                var parameters = _.map(resource.uriPathSegments.slice(lastObjectIndex + 1), function(p) { return p.substring(1, p.length - 1); });
                                var hasParameters = parameters.length > 0;

                                var serviceController = objects[0];

                                // collect a list of all controllers to unique-ify later
                                var mock = (resource.is && _.contains(resource.is, 'mock')); 
                                var hasMethods = (resource.methods && resource.methods.length > 0);

                                // we need the controller if *any* of the endpoints are not mocked
                                // skip anything without methods, typically root-level objects 
                                if (!mock && hasMethods) {
                                    data.controllers.push(serviceController);
                                }

                                // add resource links, service controllers, and service actions to child methods
                                _.each(resource.methods, function(method) {
                                    var verb = method.method.toLowerCase();
                                    if (verb === "get") {
                                        if (hasParameters) {
                                            method.serviceAction = "get_" + combinedObjectSingular + "_by_" + parameters.join("_and_");
                                        } else {
                                            method.serviceAction = "get_" + combinedObject;
                                        }
                                    } else if (verb == "put") {
                                        method.serviceAction = "put_" + combinedObjectSingular;
                                    } else if (verb == "post") {
                                        method.serviceAction = "post_" + combinedObjectSingular;
                                    } else if (verb == "delete") {
                                        method.serviceAction = "delete_" + combinedObjectSingular;
                                    } else {
                                        throw uri + ": unsupported method: " + method.method; 
                                    }

                                    method._resource = resource;
                                    method.serviceController = serviceController;
                                    method.serviceAction = ctx.headlessCamelCase(method.serviceAction);
                                    method.fullServiceAction = method.serviceController + "_" + method.serviceAction;
                                    method.uri = uri;
                                });
                            }

                            _.each(resource.resources, traverse);
                        }

                        traverse(data);

                        data.controllers = _.sortBy(_.uniq(data.controllers), function (controller) { return controller.toLowerCase(); });

                        data._isApiRoot = true;

                        var settings = _.extend({}, grunt.config('settings'));
                        if (grunt.option('profile')) {
                            settings = _.extend(settings, grunt.config('profiles.' + grunt.option('profile') + '.config.settings'));
                        }

                        var context = new Context({
                                filename: path.basename(file, '.raml'),
                                api: data,
                                settings: settings,
                                scaffold: {task: '.api'},
                                methodMap: {
                                    get: 'get',
                                    put: 'update',
                                    post: 'create',
                                    delete: 'remove'
                                },
								getSchemaNameFromMethod: function (method, fallback) {
									var schema = this.getResponseBodySchema(method);
									if (!schema || !schema.id) {
										schema = this.getRequestBodySchema(method);
									}
									var name = schema && schema.id ? schema.id.substr(schema.id.lastIndexOf('/') + 1) : (fallback || 'Object');
									return schema.items && schema.items.$ref ? schema.items.$ref : name;
								},
                                //Process schemas and return collection of enumerators
                                processAllEnums: function(api) {
                                    var enums = {};
                                    self = this;
                                    _.each(api.schemas[0], function (schema, key) {
                                        var schema = JSON.parse(schema);
                                        _.each(schema.properties, function (meta, name) {
                                            if (meta.enum) {
                                                var ename = meta['x-kpmg-enumName'] ? meta['x-kpmg-enumName'] : self.getEnumName(key, name);
												if (meta.options) {
													enums[ename] = _.map(meta.options, function (option) {
														return option.label + ' = ' + JSON.stringify(option.value);
													});
												}
												else {
													enums[ename] = meta.enum;
												}
                                            }
                                        });
                                    });

                                    return enums;
                                },
                                //Returns name of enum
                                getEnumName: function(className, propertyName) {
                                    return this.camelCase(className + propertyName + "Enum");
                                },
                                //Generates enum items
                                generateEnumMembers: function (enumData) {
                                    var result = '';
                                    _.each(enumData, function (meta, index) {
                                        if (result !== '') {
                                            result += ',\n\t'
                                        }
                                        result += '///<summary>' + (meta.description || meta) + '</summary>\n\t' + meta;
                                    });

                                    return result;
                                },
                                //Builds name of the object class
                                getClassName: function(schemaKey) {
                                    return this.camelCase(schemaKey);
                                },
                                //Builds parent object name for class
                                getParentClassName: function(jsonSchema) {
                                    return !jsonSchema.extends ? 'object' : this.getClassName(jsonSchema.extends) ;
                                },
                                getDotNetParams: function (resource, method, parentUriParameters, justNames) {
                                    var self = this,
                                        params = self.extractParams(resource, method, parentUriParameters),
                                        output = '',
                                        names = '';
                                    _.each(params.uri, function (param) {
                                        if (output) output += ', ';
                                        output += self.getPrimitiveType(param, null, null, false, true) + ' ' + param.displayName;
                                        if (names) names += ', ';
                                        names += param.displayName;
                                    });
                                    _.each(params.json, function (param) {
                                        if (output) output += ', ';
                                        var type = JSON.parse(param.schema).id;
                                        output += self.getPrimitiveType(_.extend({$ref: type.substr(type.lastIndexOf('/')+1)}, param), null, null, !param.required, param.required) + ' ' + param.displayName;
                                        if (names) names += ', ';
                                        names += param.displayName;
                                    });
                                    var queryParams = _.sortBy(params.query, function (param) {
										var hasDefault = param.hasOwnProperty('default') || param.type === 'string' || param.type === 'boolean';
                                        return param.required ? -1 : hasDefault ? 1 : 0;
                                    });
                                    _.each(queryParams, function (param) {
                                        if (output) output += ', ';
										var type = self.getPrimitiveType(param, null, null, !hasDefault, param.required),
											hasDefault = param.hasOwnProperty('default') || (!param.required && type === 'string');
										if (!hasDefault && type === 'bool') {
											hasDefault = true;
											param.default = false;
										}
                                        output += self.getPrimitiveType(param, null, null, !hasDefault, param.required) + ' ' + param.displayName + (hasDefault ? ' = ' + ('default' in param ? JSON.stringify(param.default) : 'null') : '');
                                        if (names) names += ', ';
                                        names += param.displayName;
                                    });
                                    return justNames ? names : output;
                                },
                                //Generates object properties
                                getDotNetProperties: function(jsonSchema, schemaKey){
                                    var result = '',
                                        self = this,
                                        requiredElements = jsonSchema.required;

                                    _.each(jsonSchema.properties, function(meta, name){
										result += '\n\t\t/// <summary>\n\t\t/// ' + (meta.description || name) + '\n\t\t/// </summary>';
                                        result += '\n\t\t[DataMember]';
                                        var isRequired = requiredElements && requiredElements.indexOf(name) > -1;
										var type = self.getPrimitiveType(meta, schemaKey, name, true, isRequired);
                                        if (isRequired) {
                                            result += '\n\t\t[Required]';
                                        }
										_.forEach(meta['x-kpmg-dotnet-attributes'], function (attribute) {
											result += '\n\t\t' + attribute;
										});
										if (meta.minLength) {
											result += '\n\t\t[MinLength(' + meta.minLength + ')]';
										}
										if (meta.maxLength) {
											result += '\n\t\t[MaxLength(' + meta.maxLength + ')]';
										}
										if (meta.minimum || meta.maximum ) {
											result += '\n\t\t[Range(' + (meta.minimum || 0) + ',' + (meta.maximum || Number.MAX_VALUE) +')]';
										}
										switch(meta.format) {
										case 'email':
											result += '\n\t\t[EmailAddress]';
											break;
										case 'phone':
											result += '\n\t\t[Phone]';
											break;
										case 'uri':
											result += '\n\t\t[Url]';
											break;
										}
                                        switch(type) {
                                            case 'array':
                                                if (!meta.items.type || meta.items.type.match(new RegExp('object', 'gi')) !== null) {
                                                    //TODO: Support is limited by $ref
                                                    type = 'List<' + self.getClassName(meta.items.$ref) + '>';
                                                } else {
                                                    type = 'List<' + self.getPrimitiveType(meta.items, schemaKey, name, false, isRequired) + '>';
                                                }
                                                break;
                                            default:
                                                if(meta.$ref) {
                                                    type = self.getClassName(meta.$ref);
                                                } /* else  {
                                         type = self.getPrimitiveType(meta, schemaKey, name, true, isRequired);
                                         }

                                         break;*/
                                        }

                                        result += '\n\t\tpublic ' + type + ' ' + self.camelCase(name) + ' {get; set;}\n';
                                    });
                                    return result;
                                },
                                getPrimitiveType: function (meta, className, propertyName, nullable, isRequired) {
                                    var questionmark = (nullable && !isRequired) ? '?' : '';
                                    if (meta.enum) {
                                        type = meta['x-kpmg-enumName'] ? meta['x-kpmg-enumName'] : this.getEnumName(className, propertyName);
                                        type = type;
                                    }
                                    else if (meta.$ref) {
                                        type = meta.$ref;
                                    }
                                    else {
                                        var type = meta.type;
                                        var format = meta.format;

                                        if (type == 'string' && (format == 'date' || format == 'date-time')) {
                                            type = 'DateTime';
                                        }
                                        else {
                                            switch (type) {
                                                case 'integer':
                                                    type = 'int';
                                                    break;
                                                case 'number':
                                                    type = 'decimal';
                                                    break;
                                                case 'boolean':
                                                    type = 'bool';
                                                    break;
												case 'array':
													type = 'List<' + this.getPrimitiveType(meta.items, className, propertyName, false, true) + '>';
													questionmark = '';
													break;
												case 'string':
													questionmark = '';
													break;
                                            }
                                        }
                                    }
                                    return type + questionmark;
                                },
                                getSchemas: function (object) {
                                    return [];//TODO: need to get all schema definitions at all levels
                                    var schemas = [], self = this;
                                    _.forEach(object, function (child, property) {
                                        if (property === 'schema') {
                                            try {
                                                schemas.push(JSON.parse(child));
                                            }
                                            catch (error) {
                                                //silent
                                            }
                                        }
                                        else if (property === 'schemas') {
                                            schemas = schemas.concat(child);
                                            child.forEach(function (schema) {
                                                try {
                                                    schemas.push(JSON.parse(schema));
                                                }
                                                catch (error) {
                                                    //silent
                                                }
                                            });
                                        }
                                        else if (typeof(child) === 'object') {
                                            schemas = schemas.concat(self.getSchemas(child));
                                        }
                                    });
                                    return schemas;
                                },
                                getActionName: function (method, resource, isCollection) {
                                    var isResourceMemberOfCollection = this.isResourceMemberOfCollection(resource),
                                        action = !isResourceMemberOfCollection && method.method === 'get' ? 'query' : this.methodMap[method.method],
                                        suffix = isCollection || isResourceMemberOfCollection ? '' : this.camelCase(resource.displayName);
                                    if (!isResourceMemberOfCollection && isCollection && method.method === 'delete') {
                                        suffix = 'All';
                                    }
									if (_.contains(resource.is, 'action')) {
										suffix = this.headlessCamelCase(resource.displayName);
										action = resource.methods.length === 1 ? '' : action;
									}
                                    return action + suffix;
                                },
                                getAngularUrl: function (url) {
                                    return url.split('{').join(':').split('}').join('');
                                },
                                getRequestBodySchema: function (method) {
                                    var json = {};
                                    if (method.body && method.body['application/json'] && method.body['application/json'].schema) {
                                        json = method.body['application/json'].schema;
                                        try {
                                            json = JSON.parse(json);
                                        }
                                        catch (error) {
											console.log('error parsing: ' + json + '\n' + error);
                                        }
                                    }
                                    return json;
                                },
								getResponseBodySchema: function (method) {
									var json = {};
									for (var code in method.responses) {
										if (code.indexOf('20') === 0) {
											return this.getRequestBodySchema(method.responses[code]);
										}
									}
									return json;
								},
                                //determine if this is /entity/{id}
                                isResourceMemberOfCollection: function (resource) {
                                    if (resource.uriParameters) {
                                        for (var name in resource.uriParameters) {
                                            if ('/{' + name + '}' === resource.relativeUri) {
                                                return true;
                                            }
                                        }
                                    }
                                    return _.contains(resource.is, 'member');
                                },
                                //change resource/{resourceId}/child into resource\/.*\/child
                                regexUrl: function (url) {
                                    url = url.replace(/\{[^\}]*\}/g, '.*');
                                    url = url.replace(/\//g, '\\/');
                                    return '/' + url + '/';
                                },
                                getResponseExample: function (method) {
                                    for (var code in method.responses) {
                                        if (code.indexOf('20') === 0) {
                                            return ((method.responses[code] && method.responses[code].body && method.responses[code].body['application/json'] && method.responses[code].body['application/json'].example) || '""').trim();
                                        }
                                    }
                                    return '""';
                                },
                                //flattends json schema for member so we can do @property {string} [root.child.grandchild] - Definition
                                getMemberProperties: function (schema, path) {
                                    if (!schema) return [];
                                    path = path || '';
                                    var items = [], self = this;
                                    if (path) {
                                        items.push({
                                            displayName: path,
                                            required: schema.required,
                                            type: schema.type,
                                            description: schema.description
                                        });
                                    }
                                    if (schema.properties) {
                                        path = path ? path + '.' : '';
                                        _.each(schema.properties, function (item, name) {
                                            items = items.concat(self.getMemberProperties(item, path + name));
                                        });
                                    }
                                    return items;
                                },
                                typeDoc: function (definition, pathPrefix) {
                                    pathPrefix = pathPrefix || '';
                                    return '{' + definition.type + '} ' + this.optionalize(pathPrefix + definition.displayName, definition.required) + ' - ' + definition.description;
                                },
                                optionalize: function (name, required) {
                                    var optionalPrefix = required ? '' : '[',
                                        optionalSuffix = required ? '' : ']';
                                    return optionalPrefix + name + optionalSuffix;
                                },
                                hasRequiredFields: function (fields) {
                                    if (!fields || !fields.length) return false;
                                    for (var i = 0; i < fields.length; i++) {
                                        if (fields[i].required) return true;
                                    }
                                    return false;
                                },
                                extractParams: function (resource, method, parentUriParameters) {
                                    var params = {all: [], url: [], form: [], json: []},
                                        uriParams = _.extend({}, parentUriParameters || {}, resource.uriParameters);

                                    params.query = _.map(method.queryParameters, function (param) {
                                        param.type = param.type || '';
                                        param.required = !!param.required;
                                        params.all.push(param);
                                        params.url.push(param);
                                        return param;
                                    }) || [];

                                    params.uri = _.map(uriParams, function (param) {
                                        param.type = param.type || '';
                                        param.required = 'required' in param ? param.required : true;
                                        params.all.push(param);
                                        params.url.push(param);
                                        return param;
                                    }) || [];

                                    params.headers = _.map(method.headers, function (param) {
                                        param.type = param.type || '';
                                        param.required = 'required' in param ? param.required : true;
                                        params.all.push(param);
                                        return param;
                                    }) || [];

                                    if (method.body && method.body['application/x-www-form-urlencoded'] && method.body['application/x-www-form-urlencoded'].formParameters) {
                                        params.form = _.map(method.body['application/x-www-form-urlencoded'].formParameters, function (param) {
                                            param.type = param.type || '';
                                            param.required = !!param.required;
                                            params.all.push(param);
                                            return param;
                                        }) || [];
                                    }

                                    if (method.body && method.body['application/json']) {
                                        // TODO analyze the JSON schema to make this more specific
                                        var description = 'JSON payload';
                                        var p = {
                                            type: 'Object',
                                            required: true,
                                            displayName: 'data',
                                            description: 'JSON payload',
                                            schema: method.body['application/json'].schema,
                                            example: method.body['application/json'].example
                                        };
                                        params.json = [p];
                                        params.all.push(p);
                                    }

                                    return params;
                                }
                            }),
                            engine = new Engine(context, 'css');

                        engine.create();
                        fileDone();

                    } catch(ex){
                        console.log("exception: " + ex);
                        fileDone();
                    }

                },
                function(error) {
                    console.log("error: " + JSON.stringify(error, null, '\t'));
                    fileDone();
                }
            );
        });

    });
};
