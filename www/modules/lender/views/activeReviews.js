// This file was generated from the view scaffold
// Copyright 2016

import { Component } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import template from './activeReviews.html';
import UserSvc from '../../app/services/UserSvc';
import styles from './activeReviews.less';
import CrossFilterBaseView from '../../shared/components/CrossFilterBaseView';
import LenderService from '../services/LenderService';

@Component({
    selector: 'active-reviews',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/guide/router.html
 */
export default class ActiveReviews {
    route: ActivatedRoute;
    subscriptions: Array = [];

    requestsTabDimensions: Array = [];
    lenderService: LenderService;

    constructor(router: Router, route: ActivatedRoute, userSvc: UserSvc, lenderService: LenderService) {
        this.router = router;
        this.route = route;
        this.userSvc = userSvc;
        this.lenderService = lenderService;
    }

    ngOnInit() {
        this.initCrossFilters();
    }

    ngOnDestroy() {
        this.subscriptions.forEach(subscription => {
            subscription.unsubscribe();
        });
    }

    //Match proper tab from URL, this is neccesarry because of disabling child routes via auth guards
    isActive(path) {
        if (path === 'none') {
            return '';
        }
        else if (this.router.url.indexOf(path, 0) !== -1) {
            return 'active';
        }
        else {
            return '';
        }
    }

    initCrossFilters() {
        let buttonType = CrossFilterBaseView.CONTROL_TYPE.BUTTON;
        this.requestsTabDimensions = [
            {
                title: 'CASE NUMBER SEARCH',
                controlType: CrossFilterBaseView.CONTROL_TYPE.TEXT,
                dimension: (review) => (review.caseNumber ? review.caseNumber.replace('-', '') : '')
            },
            {
                title: 'LAST ACTION BY',
                controlType: buttonType,
                dimension: (review) => {

                    // Inject the due date highlights.
                    let getReviewDeadline = CrossFilterBaseView.DIMENSION_METHODS.REVIEW_DEADLINES;
                    getReviewDeadline(review, true);

                    let authenticatedLenderName = this.userSvc.lastName + ', ' +  this.userSvc.firstName;
                    if (review.lastLenderName === authenticatedLenderName) {
                        return '(Me) ' + review.lastLenderName;
                    } else {
                        return review.lastLenderName || '(None)';
                    }
                }
            },
            {
                title: 'REVIEW LEVEL',
                controlType: buttonType,
                dimension: CrossFilterBaseView.DIMENSION_METHODS.FINAL_REVIEW_LEVEL,
                sort: CrossFilterBaseView.COMMON_DIMENSION_SORTS.REVIEW_LEVEL
            },
            {
                title: 'SELECTION REASON',
                controlType: 'button',
                dimension: (review) => review.selectionReason
            },
            {
                title: 'BATCH MEMBERS',
                controlType: buttonType,
                dimension: (review) => {
                    return review.batchId ? 'In Batch' : 'Not In Batch';
                }
            }
        ];

    }
}
