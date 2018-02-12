var _ = require('lodash');
var Handlebars = require('handlebars')
var pathToRegExp = require('path-to-regexp');

function runHandlebarsLoop(dataArray, hbsOptions) {
    var ret = '';
    _.each(dataArray, function(item){
        ret +=  hbsOptions.fn(item);
    });
    return ret;
}

function jsonSchemaToDTO(schema){
  if(schema) {
    if(schema.type === 'array') {
        return new Handlebars.SafeString('List<LrsApiV1DTOs.' + (schema.items.type || schema.items['$ref']) + 'DTO>');
    }
    return 'LrsApiV1DTOs.' + schema.id.replace('http://LRS/', '') + 'DTO'; // TODO: figure out how to make this dynamic instead of hardcoding the prefix
  }
  return 'String';
}


function primaryResponseForMethod(method) {
    var primaryResponse;

    // look at all responses defined for this method.  find the first one with an http status code between 200 and 298 (299 is reserved for mocks).  return the name of this schema
    _.each(method.responses, function(response, httpStatusCode) {
        if(primaryResponse) return; // already found a schema to use

        httpStatusCode = parseInt(httpStatusCode, 10);

        if (
            httpStatusCode >= 200 &&
            httpStatusCode < 299 &&
            response &&
            response.body &&
            response.body['application/json'] &&
            response.body['application/json'].schema
        ) {
            primaryResponse = response;
        }
    });
    return primaryResponse;
}

module.exports = {

    safe: function(str) {
        return new Handlebars.SafeString(str);
    },

    regexpUrl: function(resource) {
        // figure out the full url for this resource
        var fullUri = Handlebars.helpers.fullUri(resource);

        // convert our usual /foo/{param}/bar syntax to /foo/:param/bar as required by pathToRegExp
        fullUri = fullUri.replace(/\{/g, ':').replace(/}/g, '');

        // convert this url to a robust regexp
        return pathToRegExp(fullUri);
    },

    isMockEnabled: function(methodOrResource, rootContext){
        if(rootContext && rootContext.settings && rootContext.settings.mocks) {
            return true;
        }
        var isMock = methodOrResource.is && _.contains(methodOrResource.is, 'mock');
        isMock = isMock || ( methodOrResource._resource && methodOrResource._resource.is && _.contains(methodOrResource._resource.is, 'mock') );
        return isMock;
    },

    isConstant: function(methodOrResource) {
        var isConstant = methodOrResource.is && _.contains(methodOrResource.is, 'constant');
        isConstant = isConstant || ( methodOrResource._resource && methodOrResource._resource.is && _.contains(methodOrResource._resource.is, 'constant') );
        return isConstant;
    },

    extractSchemas: function(api) {
        var schemas = _.map(api.schemas[0], function(value, key) {
            return {schema:JSON.parse(value), key:key}
        });
        return schemas;
    },

    exampleForResponse: function(response) {
        if(response && response.body && response.body['application/json']) {
            return response.body['application/json'].example;
        }
        return null;
    },

    responseSchemaNameForMethod: function(method) {
        var primaryResponse = primaryResponseForMethod(method);
        var schemaToUse;
        if(primaryResponse) {
            schemaToUse = JSON.parse(primaryResponse.body['application/json'].schema);
        }

        return jsonSchemaToDTO(schemaToUse);
    },

    mockResponseForMethod: function(method) {
        var primaryResponse = primaryResponseForMethod(method);
        if(primaryResponse) {
            return primaryResponse.body['application/json'].example;
        } else return '{}';
    },


    jsonTypeToJavaType: function(jsonProperty) {

        // if this property is a reference to a DTO, jump directly to using that
        if(jsonProperty['$ref']) {
            return jsonProperty['$ref'] + 'DTO';
        }

        var jsonType = jsonProperty.type,
            jsonFormat = jsonProperty.format,
        	type = jsonType;

        if (jsonType == 'string' && (jsonFormat == 'date' || jsonFormat == 'date-time')) {
            type = 'Date';
        } else {
            switch (type) {
                case 'integer':
                    type = 'Integer';
                    break;
                case 'number':
                    type = 'BigDecimal';
                    break;
                case 'boolean':
                    type = 'Boolean';
                    break;
                case 'array':
                    type = 'List<' +  Handlebars.helpers.jsonTypeToJavaType(jsonProperty.items) + '>';
                    break;
                case 'string':
                    type = 'String';
                    break;
            }
        }
        return new Handlebars.SafeString(type);
    },

    forEachParameter: function(method, hbsOptions) {
        return module.exports.forEachHeaderParameter(method, hbsOptions) +
                module.exports.forEachQueryStringParameter(method, hbsOptions) +
                module.exports.forEachUriParameter(method, hbsOptions) +
                module.exports.forEachBodyParameter(method, hbsOptions);
    },

    forEachHeaderParameter: function(method, hbsOptions){
        var headers = _.map(method.headers, function (param) {
            param.type = param.type || '';
            param.required = 'required' in param ? param.required : true;
            return param;
        }) || [];

        return runHandlebarsLoop(headers, hbsOptions);
    },

    allQueryStringParameters: function(method) {
        var queryStringParameters = _.map(method.queryParameters, function (param) {
                param.location = 'querystring';
                param.type = param.type || '';
                param.required = !!param.required;
                return param;
            }) || [];
        return queryStringParameters;
    },

    allParameters: function(method) {
        var combined = [].concat(Handlebars.helpers.allUriParameters(method), Handlebars.helpers.allQueryStringParameters(method));

        if (method.body && method.body['application/json']) {
            combined.push({
                location: 'body',
                type: 'Object', // TODO analyze the JSON schema to make this more specific
                required: true,
                displayName: 'data',
                description: 'JSON payload',
                dto: jsonSchemaToDTO( method.body['application/json'].schema && JSON.parse(method.body['application/json'].schema) ),
                schema: method.body['application/json'].schema,
                example: method.body['application/json'].example
            });
        }

        return combined;
    },

    forEachQueryStringParameter: function(method, hbsOptions){
        var queryStringParameters = Handlebars.helpers.allQueryStringParameters(method);
        return runHandlebarsLoop(queryStringParameters, hbsOptions);
    },

    allUriParameters: function(method) {
        var aggregatedUriParameters = {},
            parent = method._resource;

        while(parent) {
            aggregatedUriParameters = _.extend({}, parent.baseUriParameters, parent.uriParameters, aggregatedUriParameters);
            parent = parent._parentResource;
        }

        return _.map(aggregatedUriParameters, function (param) {
                param.location = 'uri';
                param.type = param.type || '';
                param.required = 'required' in param ? param.required : true;
                return param;
            }) || [];
    },

    forEachUriParameter: function(method, hbsOptions){
        var aggregatedUriParameters = Handlebars.helpers.allUriParameters(method);
        return runHandlebarsLoop(aggregatedUriParameters, hbsOptions);
    },

    forEachBodyParameter: function(method, hbsOptions){

        var bodyParameters = [];

        if (method.body && method.body['application/x-www-form-urlencoded'] && method.body['application/x-www-form-urlencoded'].formParameters) {
            bodyParameters = _.map(method.body['application/x-www-form-urlencoded'].formParameters, function (param) {
                    param.type = param.type || '';
                    param.required = !!param.required;
                    return param;
                }) || [];
        }
        if (method.body && method.body['application/json']) {
            bodyParameters = [{
                type: 'Object', // TODO analyze the JSON schema to make this more specific
                required: true,
                displayName: 'data',
                description: 'JSON payload',
                schema: method.body['application/json'].schema,
                example: method.body['application/json'].example
            }];
        }

        return runHandlebarsLoop(bodyParameters, hbsOptions);
    },

    hasJsonBodyParameter: function(method) {
        return method.body && method.body['application/json'];
    },

    methodNameForHttpVerb: function(verb) {
        var methodMap = {
            get: 'get',
            put: 'update',
            post: 'create',
            delete: 'remove'
        };
        return methodMap[verb];
    },

    fullUri: function(method) {
        var parent = method._resource,
            fullUri = '';
        while(parent) {
            fullUri = (parent.baseUri||'') + (parent.relativeUri||'') + fullUri;
            parent = parent._parentResource;
        }
        return fullUri;
    },

    // if provided value is an array, return it as-is.  otherwise, wrap it in an array
    asArray(object) {
        return _.isArray(object) ? object : [object];
    },

    // returns the list of security schemes applied to a method
    securedBySchemesExcept(method, schemeToIgore) {
        var result = _.map(method.securedBy, s => {
            if(_.isString(s)) {
                return s;
            }
            return _.keys(s)[0];
        });

        if(result.length === 0) {
            console.warn('WARNING: No security defined for: ' + method.method.toUpperCase() + ' ' + module.exports.fullUri(method) );
        }

        return _.without(result, schemeToIgore);
    },

    // return true if the method includes a parameter of the requested name
    containsParameter(method, paramName) {
        return _.any(module.exports.allParameters(method), {displayName: paramName})
    }
};
