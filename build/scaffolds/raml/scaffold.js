module.exports = function (grunt) {

    var Type = require('js-yaml/lib/js-yaml/type'),
        FailSafeSchema = require('js-yaml/lib/js-yaml/schema/failsafe'),
        IncludeType = new Type('!include', {
          kind: 'scalar',
          construct: function (data) {
              return data !== null ? '!include ' + data : '';
          }
        }),
        Schema = require('js-yaml/lib/js-yaml/schema'),
        RamlSchema = new Schema({
            include: [FailSafeSchema],
            explicit: [IncludeType]
        });

    function ramlParse(file) {
        var contents = grunt.file.read(file),
            YAML = require('js-yaml');

        return YAML.load(contents, {
            filename: file,
            schema: RamlSchema
        });
    }

    function ramlSerialize(data) {
        var YAML = require('js-yaml');
        return '#%RAML 0.8\n' + YAML.dump(data, {
            indent: 4,
            noCompatMode: true,
            schema: RamlSchema
        })
        .replace(/'!include(.*)'/g, function (match, include) {
            //need to unquote these lines
            return '!include' + include;
        });
    }

    grunt.event.on('scaffold.raml.prompt', function () {
        //set some defaults that we don't use to skip these questions
        grunt.config('scaffold.module', 'raml');
        grunt.config('scaffold.action', 'create');
    });

    grunt.event.on('scaffold.raml.prompted', function () {
        //run our custom questions
        grunt.task.run('prompt:scaffoldraml');
    });

    grunt.event.on('scaffold.raml.executed', function () {
        var Context = require('../../tasks/libs/TemplateContext'),
            context = new Context(),
            nameSingular = grunt.config('scaffold.name_singular'),
            namePlural = grunt.config('scaffold.name_plural'),
            file = grunt.config('scaffold.raml'),
            raml = ramlParse(file),
            path = require('path'),
            directory = grunt.config('scaffold.path');

        if (!raml.schemas) {
            raml.schemas = [{}];
        }

        var schemas = raml.schemas[0];

        schemas[context.camelCase(namePlural)] = '!include ' + path.join(directory, namePlural + '-schema.json');
        schemas[context.camelCase(nameSingular)] = '!include ' + path.join(directory, nameSingular + '-schema.json');

        var baseUri = raml.baseUri;
        if (baseUri.charAt(0) === '/') {
            baseUri = baseUri.substr(1);
        }
        raml[directory.replace(baseUri, '')] = '!include ' + path.join(directory, namePlural + '.raml');

        grunt.file.write(file, ramlSerialize(raml));
        grunt.log.warn((file + ' has been updated with reference to these new files.').yellow);
    });

    grunt.config.merge({
        prompt: {
            scaffoldraml: {
                options: {
                    questions: [
                        {
                            config: 'scaffold.raml',
                            message: 'Which ' + 'raml'.cyan + ' file do you want to add to?',
                            type: 'list',
                            choices: grunt.file.expand({cwd: grunt.config('settings.dir.apiroot')}, '*.raml'),
                            filter: function (raml) {
                                var path = require('path');
                                return path.join(grunt.config('settings.dir.apiroot'), raml);
                            }
                        },
                        {
                            config: 'scaffold.path',
                            message: 'What is the ' + 'path'.cyan + ' of the ' + 'raml'.cyan + ' resource?',
                            type: 'input',
                            default: function (answers) {
                                var file = answers['scaffold.raml'] || grunt.config('scaffold.raml'),
                                    pluralize = require('pluralize'),
                                    path = require('path'),
                                    name = pluralize(grunt.config('scaffold.name')),
                                    raml = ramlParse(file);
                                return path.join(raml.baseUri, name);
                            },
                            filter: function (input) {
                                if (input.charAt(0) === '/') {
                                    input = input.substr(1);
                                }
                                if (input.charAt(input.length-1) === '/') {
                                    input = input.substr(0, input.length-2);
                                }
                                return input.trim();
                            }
                        }
                    ],
                    then: function (answers) {
                        var pluralize = require('pluralize'),
                            name = grunt.config('scaffold.name');
                        grunt.config('scaffold.name_plural', pluralize(name, 2));
                        grunt.config('scaffold.name_singular', pluralize(name, 1));
                    }
                }
            }
        }
    });

};
