//these prereqs are required to be bundled with app before everything else

//IE11 doesn't support Map very much
import 'babel-polyfill';

//import 'zone.js';

//https://github.com/angular/angular/issues/5632
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/toPromise';
import 'rxjs/add/observable/fromPromise';

import 'reflect-metadata';
import 'zone.js/dist/zone';
import 'zone.js/dist/long-stack-trace-zone';
