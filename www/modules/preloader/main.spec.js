describe('preloader/main.js', function() {

    var main = require('modules/preloader/main');

    it('should remove preloader after modules', function(){
        main.afterModules();
        expect($('.preload.spinner').length).toBe(0);
    });

});
