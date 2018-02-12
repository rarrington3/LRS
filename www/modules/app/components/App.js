// This file was generated from the component scaffold
// Copyright 2016

import {Component} from '@angular/core';
import template from './App.html';
import styles from './App.less';
import LrsApiV1 from '../../api/lrs.api.v1.generated';
import UserSvc from '../services/UserSvc';
import LocationSvc from '../services/LocationSvc';
import ModalSvc from '../services/ModalSvc';
import _ from 'lodash';
import { WORKLOAD_ROUTES } from '../../workload/routes';
import { REVIEW_ROUTES } from '../../review/routes';
import { STAFF_ROUTES } from '../../staff/routes';
import { LENDER_ROUTES } from '../../lender/routes';
import { MANUALSELECTION_ROUTES } from '../../manualselection/routes';
import { LENDERMONITORING_ROUTES } from '../../lenderMonitoring/routes';
import { EXCEPTIONS_ROUTES } from '../../exceptions/routes';
import { DEV_ROUTES } from '../../dev/routes';
import { ADMIN_ROUTES } from '../../admin/routes';
import { MICROSTRATEGY_ROUTES } from '../../microstrategy/routes';
let errorReporter =  require('modules/preloader/errorreporter');
let compiledConfig = require('config');

const allRoutes = _.union(
    WORKLOAD_ROUTES,
    REVIEW_ROUTES,
    STAFF_ROUTES,
    LENDER_ROUTES,
    MANUALSELECTION_ROUTES,
    LENDERMONITORING_ROUTES,
    EXCEPTIONS_ROUTES,
    ADMIN_ROUTES,
    MICROSTRATEGY_ROUTES,
    DEV_ROUTES
);

/**
 * @example
 * <app></app>
 */
@Component({
    selector: 'app',
    template: template,
    styles: [styles]
})
export default class App {
    name:string = 'HUD Loan Review System';

    mainLinks:Array = [];
    api:LrsApiV1;
    uiConfig:Object = {};
    userSvc:UserSvc;
    modalSvc:ModalSvc;
    activeHttpRequestCount = 0;

    constructor(api:LrsApiV1, userSvc:UserSvc, locationSvc:LocationSvc, modalSvc:ModalSvc) {
        this.api = api;
        this.userSvc = userSvc;
        this.locationSvc = locationSvc;
        this.modalSvc = modalSvc;
        this.buildNavigationEntriesBasedOnCurrentUserAuthorizations();
        this._ = _;
    }

    // hook into the browser's XHR API to
    // 1) log the start/end of all service calls
    // 2) display the loading screen while any service calls are in progress
    _trackAllHttpTraffic() {
        let origOpen = XMLHttpRequest.prototype.open;
        let overlay = document.getElementById('GlobalLoadingOverlay');
        let self = this;
        XMLHttpRequest.prototype.open = function(method, url) {
            console.log(`${method} ${url} started`);
            let timestamp = new Date().getTime();
            self.activeHttpRequestCount++;
            if (self.activeHttpRequestCount === 1) {
                overlay.className = 'unremoved waiting';
            }
            this.addEventListener('loadend', function() {
                self.activeHttpRequestCount--;
                console.log(`${method} ${url} completed in ${(new Date().getTime())-timestamp}ms`);
                if (self.activeHttpRequestCount === 0) {
                    overlay.className = 'removed';
                }
            });
            origOpen.apply(this, arguments);
        };
    }

    gatherDiagnostics() {
        let commit = compiledConfig.build.commit.substring(0, 7);
        let branch = compiledConfig.build.branch;
        let timestamp = new Date(compiledConfig.build.timestamp);
        let server = compiledConfig.server;

        return [
            `Source: ${branch} (${commit})`,
            `Built: ${timestamp}`,
            `API: ${server}`,
            `Now: ${(new Date())}`,
            `Agent: ${window.navigator.userAgent}`,
            `User: ${this.userSvc.hudId}`,
            `URL: ${window.location}`
        ];
    }

    ngOnInit() {

        // override the default error reporting behavior defined in preloader to use an
        // angular-specific approach based on our code-gen'd RAML API wrapper
        let api = this.api;
        errorReporter.send = (function(error) {

            error.logHistory = error.logHistory || [];

            this.gatherDiagnostics().forEach( (d) => {
                error.logHistory.push('[Error Diagnostic] ' + d);
            });

            api.resources.errors.post(error).first().subscribe();

        }).bind(this);

        this._trackAllHttpTraffic();
    }

    showVersionInfo() {
        // pop up a modal with build details to be copy/pasted into an email or bug report
        this.modalSvc.acknowledge(this.gatherDiagnostics().join('<br/>'),
             'Diagnostic Details').subscribe(_.noop, _.noop);
    }

    buildNavigationEntriesBasedOnCurrentUserAuthorizations() {

        let mainLinks = [];

        // Loop through every route definition.
        for (let i=0, l=allRoutes.length; i<l; i++) {

            // If the route has a menu item defined...
            if (allRoutes[i].navigationBarEntry) {

                // ... check if it passes all authentication setup for this route...
                let passesAuth = false;
                for (let guard, j=0, jl=allRoutes[i].canActivate.length; j<jl; j++) {
                    guard = new allRoutes[i].canActivate[j](this.userSvc);
                    if (guard.canActivate()) {
                        passesAuth = true;
                        break;
                    }
                }

                // ... and add the menu item if it passes.
                if (passesAuth) {
                    mainLinks.push({
                        name: allRoutes[i].navigationBarEntry,
                        link: [allRoutes[i].path]
                    });
                }
            }
        }

        this.mainLinks = mainLinks;
    }

    isAuthorized(authorities:Array<string>):boolean {
        return !!_.intersection(authorities, this.userSvc.authorities).length;
    }
}
