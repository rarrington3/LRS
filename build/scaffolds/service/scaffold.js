module.exports = function (grunt) {

    grunt.event.on('scaffold.service.executed', function () {
        var Context = require('../../tasks/libs/TemplateContext'),
            context = new Context(),
            scaffold = grunt.config('scaffold'),
            settings = grunt.config('settings'),
            path = require('path'),
            appendProviders = false,
            name = context.camelCase(scaffold.module).toUpperCase() + '_PROVIDERS',
            indexFile = path.resolve(settings.dir.app, 'modules', scaffold.module, 'services', 'index.js'),
            providerFile = path.resolve(settings.dir.app, 'modules', 'app', 'providers.js'),
            providerContents = grunt.file.read(providerFile),
            providerImportLine = 'import {' + name + '} from \'../' + scaffold.module + '/services\';',
            providerExportLine = 'DEFAULT_PROVIDERS.push(' + name + ');',
            indexContents = 'export let ' + name + ' = [];',
            indexImportLine = 'export ' + context.camelCase(scaffold.name) + ' from \'./' + scaffold.name + '\';',
            indexExportsLine = name + '.push(exports.' + context.camelCase(scaffold.name) + ');';

        if (grunt.file.exists(indexFile)) {
            indexContents = grunt.file.read(indexFile);
        }
        else {
            appendProviders = true;
        }

        switch(scaffold.action) {
        case 'create':
            indexContents += '\n\n' + indexImportLine + '\n' + indexExportsLine;
            if (appendProviders) {
                providerContents += '\n\n' + providerImportLine + '\n' + providerExportLine;
            }
            break;
        case 'remove':
            indexContents = indexContents.replace(indexImportLine, '').replace(indexExportsLine, '');
            providerContents = providerContents.replace(providerImportLine, '').replace(providerExportLine, '');
            break;
        }

        grunt.file.write(providerFile, providerContents);
        grunt.file.write(indexFile, indexContents);
        grunt.log.warn(('A reference to this service has been updated in "' + indexFile + '". You may modify this if you need.').yellow);
        grunt.log.warn('A reference to this service has been updated in "app/providers.js". You may modify this if you need.'.yellow);
    });

};
