module.exports = function (grunt) {

    grunt.event.on('scaffold.component.executed', function () {
        var Context = require('../../tasks/libs/TemplateContext'),
            context = new Context(),
            scaffold = grunt.config('scaffold'),
            settings = grunt.config('settings'),
            path = require('path'),
            name = context.camelCase(scaffold.module).toUpperCase() + '_COMPONENTS',
            indexFile = path.resolve(settings.dir.app, 'modules', scaffold.module, 'components', 'index.js'),
            indexContents = 'export let ' + name + ' = [];',
            indexImportLine = 'export ' + context.camelCase(scaffold.name) + ' from \'./' + scaffold.name + '\';',
            indexExportsLine = name + '.push(exports.' + context.camelCase(scaffold.name) + ');';

        if (grunt.file.exists(indexFile)) {
            indexContents = grunt.file.read(indexFile);
        }

        switch(scaffold.action) {
        case 'create':
            indexContents += '\n\n' + indexImportLine + '\n' + indexExportsLine;
            break;
        case 'remove':
            indexContents = indexContents.replace(indexImportLine, '').replace(indexExportsLine, '');
            break;
        }

        grunt.file.write(indexFile, indexContents);
        grunt.log.warn(('A reference to this service has been updated in "' + indexFile + '". You may modify this if you need.').yellow);
    });

};