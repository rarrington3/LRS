// This file was generated from the view scaffold
// Copyright 2017

import {Component} from '@angular/core';
import {ActivatedRoute}  from '@angular/router';
import template from './microstrategy.html';
import styles from './microstrategy.less';
let preloader = require('../../preloader/main');

@Component({
    selector: 'microstrategy',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/guide/router.html
 */
export default class Microstrategy {

    route:ActivatedRoute;

    /**
     * Default name for the class, passed by reference via RouteParams
     */
    name:string = 'Microstrategy';

    /**
     * Any new subscription will be added into this collection
     * @type {Array}
     */
    subscriptions:Array = [];

    constructor(route:ActivatedRoute) {
        this.route = route;
        this.microstrategyUrl = preloader.uiConfig ? preloader.uiConfig.microstrategyUrl : '';
    }

    ngOnInit() {
        this.subscriptions.push(this.route.params.subscribe(params => {
            this.name = params['name'];
        }));
    }

    ngOnDestroy() {
        this.subscriptions.forEach(subscription => {
            subscription.unsubscribe();
        });
    }
}
