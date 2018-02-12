//any file with the name "bootstrap" will not be bundled during spec tests, see settings.json

import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { AppModule } from './main';
let preloader = require('../preloader/main');

preloader.onceUserInfoIsLoaded(function bootstrapApplication () {
    platformBrowserDynamic()
        .bootstrapModule(AppModule)
        .then(preloader.removeLoadingOverlay);
});
