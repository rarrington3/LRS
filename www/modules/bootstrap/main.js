/*
 * Defines a custom build of Twitter Bootstrap.
 *
 * To customize the CSS, edit ./styles/variables.less
 * To remove unused CSS components, edit ./styles/bootstrap.less
 * To remove unused JS components, edit this file
 */
let styles = require('./bootstrap.less');
require('cssify')(styles);

/*
 * The list of files and their order of inclusion was copied from the 'concat:bootstrap'
 * task configuration in /libs/bootstrap/gruntfile.js.
 *
 * When updating to new versions of Bootstrap, this file may need to be manually updated to
 * reflect added/removed/renamed/reordered source files.
 */
require('../../libs/bootstrap/js/transition.js');
require('../../libs/bootstrap/js/alert.js');
require('../../libs/bootstrap/js/button.js');
require('../../libs/bootstrap/js/carousel.js');
require('../../libs/bootstrap/js/collapse.js');
require('../../libs/bootstrap/js/dropdown.js');
require('../../libs/bootstrap/js/modal.js');
require('../../libs/bootstrap/js/tooltip.js');
require('../../libs/bootstrap/js/popover.js');
require('../../libs/bootstrap/js/scrollspy.js');
require('../../libs/bootstrap/js/tab.js');
require('../../libs/bootstrap/js/affix.js');

// include overrides from this paypal lib which adds better keyboard and aria support to bootstrap components
require('../../libs/bootstrap-accessibility-plugin/src/js/functions.js');
require('../../libs/bootstrap-accessibility-plugin/src/js/alert.js');
require('../../libs/bootstrap-accessibility-plugin/src/js/modal.js');
require('../../libs/bootstrap-accessibility-plugin/src/js/dropdown.js');
require('../../libs/bootstrap-accessibility-plugin/src/js/tab.js');
require('../../libs/bootstrap-accessibility-plugin/src/js/collapse.js');
require('../../libs/bootstrap-accessibility-plugin/src/js/carousel.js');

// include datepicker
require('../../libs/bootstrap-datepicker/js/bootstrap-datepicker.js');
