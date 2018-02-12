// This file was generated from the component scaffold
// Copyright 2017

import {Component} from '@angular/core';
import template from './LenderReviewsCompletedContainer.html';
import styles from './LenderReviewsCompletedContainer.less';
import { Router } from '@angular/router';
@Component({
    selector: 'lender-reviews-completed-container',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/api/core/Component-decorator.html
 * @example
 * <lender-reviews-completed-container name="LenderReviewsCompletedContainer" (change)="onChange($event)"></lender-reviews-completed-container>
 */
export default class LenderReviewsCompletedContainer {

    _router: Router;
    constructor(router: Router) {
        this._router = router;
    }

    //Match proper tab from URL, this is neccesarry because of disabling child routes via auth guards
    isActive(path) {
        if (path === 'none') {
            return '';
        }
        else if (this._router.url.indexOf(path, 0) !== -1) {
            return 'active';
        }
        else {
            return '';
        }
    }
}
