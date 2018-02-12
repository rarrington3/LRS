// This file was generated from the view scaffold
// Copyright 2016

import {Component} from '@angular/core';
import {ActivatedRoute}  from '@angular/router';
import template from './lenderReviewBatch.html';
import styles from './lenderReviewBatch.less';

@Component({
    selector: 'lender-review-batch',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/guide/router.html
 */
export default class LenderReviewBatch {

    route:ActivatedRoute;

    /**
     * Default name for the class, passed by reference via RouteParams
     */
    name:string = 'LenderReviewBatch';

    /**
     * Any new subscription will be added into this collection
     * @type {Array}
     */
    subscriptions:Array = [];

    constructor(route:ActivatedRoute) {
        this.route = route;
    }

    ngOnInit() {
       /* this.subscriptions.push(this.route.params.subscribe(params => {
            this.name = params['name'];
        }));*/
        this.name = 'Lender Review Batch';
    }

    ngOnDestroy() {
        this.subscriptions.forEach(subscription => {
            subscription.unsubscribe();
        });
    }
}
