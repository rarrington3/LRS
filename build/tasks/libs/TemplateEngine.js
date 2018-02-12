var isImportStyleSheet = /_.*\.(css|less|sass|scss)$/,
    isStyleSheet = /\.(css|less|sass|scss)$/,
    isHandlebarsTemplate = /\.hbs$/,
    importrgx = /@import\s+(url\()?['"]?([^'"\(\)]*)['"]?[\)]?;/gi,
    pathvarrgx = /\[([A-Za-z0-9_\\(\).]+)\]/,
    expand = require('glob-expand'),
    path = require('path'),
    fs = require('fs-extra');

/**
 * This is the default base Engine to be used for scaffolding tasks.
 * This uses 'class.extend', so you can extend this Engine with your
 * own functionality.
 *
 * @example
 * var TemplateEngine = require('libs/TemplateEngine');
 * var MyTemplateEngine = TemplateEngine.extend({ myFunction: function () {...} });
 *
 * var engine = new MyTemplateEngine(context, styleformat);
 *
 * @class TemplateEngine
 */
module.exports = require('class.extend').extend({

    init: function (context, styleformat) {
        this.styleformat = styleformat || 'css';
        this.context = context;
        this.srcFiles = expand({filter: 'isFile'}, [
            'build/scaffolds/' + context.scaffold.task + '/**/*.*',
            '!build/scaffolds/' + context.scaffold.task + '/.hbs-helpers/**/*.*',
            '!build/scaffolds/' + context.scaffold.task + '/scaffold.js'
        ]);
        this.dstFiles = this.getDestinationFiles(this.srcFiles, context);
    },

    isMasterStyleSheet: function (path) {
        return isStyleSheet.test(path) && !isImportStyleSheet.test(path);
    },

    /**
     * Resolve the source template file paths with the destination path.
     * @function TemplateEngine#getDestinationFiles
     * @param {string[]} srcFiles
     * @param {object} data
     */
    getDestinationFiles: function (srcFiles, data) {
        var self = this,
            dstFiles = [],
            path = require('path'),
            srcBase = 'build/scaffolds/' + data.scaffold.task + '/';

        srcFiles.forEach(function (srcPath) {
            var key, match,
                destPath = srcPath.replace(srcBase, '');

            //modules are optional so if no module start at root of scripts, if is module then modules/modulename/
            destPath = destPath.replace('[scaffold.module]', data.scaffold.module || '.');

            while ((match = pathvarrgx.exec(destPath))) {
                with(data) {
                    destPath = destPath.replace(match[0], eval(match[1]));
                }
            }

            dstFiles.push(destPath);
        });

        return dstFiles;
    },

    /**
     * Scans all master stylesheets in same directory
     * as forSheet and adds an import to forSheet to
     * each master stylesheet if one doesn't already exist.
     * @function TemplateEngine#addStyleImport
     * @param forSheet
     */
    addStyleImport: function (forSheet) {
        var self = this,
            dir = path.dirname(forSheet),
            masterSheets = expand({filter: self.isMasterStyleSheet}, dir + '/{.,..}/*');

        masterSheets.forEach(function (toSheet) {
            var relative = path.relative(path.dirname(toSheet), forSheet),
                css = fs.readFileSync(toSheet, {encoding: 'utf8'}),
                match;

            if (self.styleformat !== 'css') {
                relative = relative.replace('.' + self.styleformat, '');
            }

            while ((match = importrgx.exec(css))) {
                if (match[2] === relative) {
                    return;//import already exists
                }
            }

            if (self.styleformat === 'css') {
                css = '@import url("' + relative + '");\n' + css;
            }
            else {
                css = '@import "' + relative + '";\n' + css;
            }

            console.log('creating import to ' + relative + ' in ' + toSheet);
            fs.writeFileSync(toSheet, css, {encoding: 'utf8'});
        });
    },

    /**
     * Scans all master stylesheets in same directory
     * as forSheet and removes an import to forSheet from
     * each master stylesheet if one already exist.
     * @function TemplateEngine#removeStyleImport
     * @param forSheet
     */
    removeStyleImport: function (forSheet) {
        var self = this,
            dir = path.dirname(forSheet),
            masterSheets = expand({filter: self.isMasterStyleSheet}, dir + '/{.,..}/*');

        masterSheets.forEach(function (fromSheet) {
            var relative = path.relative(path.dirname(fromSheet), forSheet),
                css = fs.readFileSync(fromSheet),
                match;

            if (self.styleformat !== 'css') {
                relative = relative.replace('.' + self.styleformat, '');
            }

            while ((match = importrgx.exec(css))) {
                if (match[2] === relative) {
                    css = css.replace(match[0], '');
                    console.log('removing import to ' + relative + ' from ' + fromSheet);
                    fs.writeFileSync(fromSheet, css, {encoding: 'utf8'});
                    break;
                }
            }
        });
    },

    /**
     * Copies contents of templatesDir out to the project root directory in
     * same folder structure as templateDir. Uses keys in data to resolve
     * named parameters and for the lodash or handlebars template for file contents.
     *
     * Within the /scaffolds/* folders, the extension on each file will determine
     * whether it uses the JST (from lodash) or Handlebars engine. Files ending with .HBS will
     * use handlebars. Files ending with .JST will use JST. Files using neither of
     * those extensions will fallback to JST for backwards compatibility. These
     * extensions will be dropped from the generated file
     * (foo.js.jst --> foo.js, bar.js.hbs --> bar.js, old.js --> old.js)
     *
     * @function TemplateEngine#create
     */
    create: function () {
        var self = this;
        var _ = require('lodash');
        var hbs = require('handlebars');

        // take each function from 'context' and register it as a handlebars helper
        _.each(_.functions(self.context), function(key) {
            hbs.registerHelper(key, self.context[key]);
        });

        // take each js file under .hbs-helpers and register it's exported functions as handlebars helpers
        expand({filter: 'isFile'}, ['build/scaffolds/.hbs-helpers/**.js', 'build/scaffolds/' + self.context.scaffold.task + '/.hbs-helpers/**.js']).forEach(function(fileName) {
            var exportedHelpers = require('../../../' + fileName);
            _.each(_.functions(exportedHelpers), function(key) {
                hbs.registerHelper(key, exportedHelpers[key]);
            });
        });

        // take each handlebars partial file and register with with handlebars
        expand({filter: 'isFile'}, ['build/scaffolds/.hbs-helpers/**.hbs', 'build/scaffolds/' + self.context.scaffold.task + '/.hbs-helpers/**/*.hbs']).forEach(function(fileName) {
            var contents = fs.readFileSync(fileName, { encoding: 'utf8' });
            var name = self.context.headlessCamelCase(_.last(fileName.split('/')).replace('.hbs', '').replace('.', ' '));
            hbs.registerPartial(name, contents);
        });

        self.dstFiles.forEach(function (dstPath, index) {
            try {
                // add support for Handlebars templates
                var fileName = self.srcFiles[index];

                var contents = fs.readFileSync(fileName, { encoding: 'utf8' });
                var templateFn;

                if (isHandlebarsTemplate.test(fileName)) {
                    templateFn = hbs.compile(contents);
                    dstPath = dstPath.replace(/\.hbs$/, '');
                } else {
                    templateFn = _.template(contents);
                    dstPath = dstPath.replace(/\.jst$/, '');
                }


                if (templateFn !== null) {
                    self.context.templateFn = templateFn;  // adding the template itself to the context to enable recursion
                    contents = templateFn(self.context);
                    console.log('GENERATED: ' + fileName + ' => ' + dstPath);
                }
            } catch (ex) {
                console.log('FAILED: ' + fileName + '\n' + ex.stack);
                throw ex;
            }
            fs.ensureFileSync(dstPath);
            fs.writeFileSync(dstPath, contents, {encoding: 'utf8'});
        });

    },

    /**
     * Removes contents of templatesDir out to the project root directory in
     * same folder structure as templateDir. Uses keys in data to resolve
     * named parameters and for the lodash temlate for file contents.
     * @function TemplateEngine#remove
     */
    remove: function () {
        var self = this;
        self.dstFiles.forEach(function (dstPath) {
            if (fs.existsSync(dstPath)) {
                console.log('removing ' + dstPath);
                fs.unlinkSync(dstPath);
            }
        });
    }
});
