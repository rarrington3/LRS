// This file was generated from the view scaffold
// Copyright 2016

import {Component} from '@angular/core';
import {ActivatedRoute, Router, NavigationEnd}  from '@angular/router';
import template from './baseView.html';
import styles from './baseView.less';
import {HqAdminGuard} from '../../app/services/AuthGuards';

@Component({
    selector: 'base-view',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/guide/router.html
 */
export default class BaseView {

    _route: ActivatedRoute;
    _router: Router;
    _hqAdminGuard: HqAdminGuard;

    /**
     * Any new subscription will be added into this collection
     * @type {Array}
     */
    subscriptions: Array = [];

    /**
     * Hardcoded list of menu and child menu items for the mainMenu element
     * @type {Array}
     */
    menuItems: Array = null;

    activeRoute: string = null;

    constructor(route: ActivatedRoute, router: Router, hqAdminGuard: HqAdminGuard) {
        this._route = route;
        this._router = router;
        this._hqAdminGuard = hqAdminGuard;
    }

    ngOnInit() {
        this.menuItems = [
            {
                label: 'Review Levels', route: '',
                items: [
                    {label: 'Lender Responses', route: 'reviewLevels/lenderResponses'},
                    {label: 'Reviewer Deadlines', route: 'reviewLevels/reviewerDeadlines'}
                ],
                display: this._hqAdminGuard.canActivate()
            },
            {label: 'Lender Suppression', route: 'lenderSuppression', display: this._hqAdminGuard.canActivate()},
            {
                label: 'Model Management', route: '',
                items: [
                    {label: 'Selection Models', route: 'modelManagement/selectionModels'},
                    {label: 'Assignment Models', route: 'modelManagement/assignmentModels'},
                    {label: 'Distribution Models', route: 'modelManagement/distributionModels'},
                    {label: 'Q&A Models', route: 'modelManagement/qaModels'}
                ],
                display: this._hqAdminGuard.canActivate()
            },
            {label: 'Review Locations', route: 'reviewLocations', display: this._hqAdminGuard.canActivate()},
            {label: 'Email Templates', route: 'emailTemplates', display: this._hqAdminGuard.canActivate()},
            {label: 'Lender Increased Selection', route: 'lenderIncreaseSelection', display: this._hqAdminGuard.canActivate()},
            {label: 'Underwriter Increased Selection', route: 'underWriterIncreaseSelection', display: this._hqAdminGuard.canActivate()},
            {label: 'Workflow QA/Troubleshooting', route: 'workflow', display: true}
        ];

        // selects first node child from route to detect selected tab
        this._router.events.subscribe(e => {
            if (e instanceof NavigationEnd) {
                let route = this._route.firstChild.snapshot.routeConfig.path;
                this.select(route);
                //this.selectedTab = this.tabs.find(t => t.name === tabName);
            }
        });
    }

    ngOnDestroy() {
    }

    /**
     * Listener for menu to control focus and keyboard accessibility and display purposes.
     *
     * @param {string} route the selected menu route
     * @returns {null} returns nothing
     * */
    select(route) {
        // nulls previously selected child
        this.activeRoute = route;
    }
}
