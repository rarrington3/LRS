// This file was generated from the component scaffold
// Copyright 2017

import {Component} from '@angular/core';
import { Router } from '@angular/router';
import template from './ReviewsCompletedContainer.html';
import styles from './ReviewsCompletedContainer.less';
import { ReviewerOrAdminGuard, AdminGuard, HqAdminGuard } from '../../app/services/AuthGuards';
import { UserSvc } from '../../app/services';
import CrossFilterBaseView from '../../shared/components/CrossFilterBaseView';
import WorkloadProvider from '../services/WorkloadProvider';
import Client from '../../api/lrs.api.v1.generated';

@Component({
    selector: 'reviews-completed-container',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/api/core/Component-decorator.html
 * @example
 * <reviews-completed-container name="ReviewsCompletedContainer" (change)="onChange($event)"></reviews-completed-container>
 */
export default class ReviewsCompletedContainer {

    myReviewDimensions: Array = [];
    constructor(client: Client, route: Router, userSvc: UserSvc,  hqAdminGuard: HqAdminGuard, adminGuard: AdminGuard, reviewerOrAdminGuard: ReviewerOrAdminGuard, workloadProvider: WorkloadProvider) {
        this._route = route;
        this._client = client;
        this._userSvc = userSvc;
        this._hqAdminGuard = hqAdminGuard;
        this._adminGuard = adminGuard;
        this._reviewerOrAdminGuard = reviewerOrAdminGuard;
        this.workloadProvider = workloadProvider;
    }

    ngOnInit() {
        this.initCrossFilters();
    }

    //Match proper tab from URL, this is neccesarry because of disabling child routes via auth guards
    isActive(path) {
        if (path === 'none') {
            return '';
        }
        else if (this._route.url.indexOf(path, 0) !== -1) {
            return 'active';
        }
        else {
            return '';
        }
    }

    initCrossFilters() {
        let buttonType = CrossFilterBaseView.CONTROL_TYPE.BUTTON;

        this.myReviewDimensions = [
            {
                title: 'Case Number Search',
                controlType: 'text',
                dimension: (review) => (review.caseNumber ? review.caseNumber.replace('-', '') : '')
            },
            {
                title: 'Final Rating',
                controlType: buttonType,
                dimension: (review) => review.rating,
                sort: CrossFilterBaseView.COMMON_DIMENSION_SORTS.RATING
            },
            {
                title: 'REVIEW TYPE',
                controlType: buttonType,
                dimension: (review) => review.reviewType || 'N/A'
            },
            {
                title: 'FINAL REVIEW LEVEL',
                controlType: buttonType,
                dimension: CrossFilterBaseView.DIMENSION_METHODS.FINAL_REVIEW_LEVEL,
                sort: CrossFilterBaseView.COMMON_DIMENSION_SORTS.REVIEW_LEVEL
            },
            {
                title: 'Selection Reason',
                controlType: buttonType,
                dimension: (review) => review.selectionReason
            }
        ];
    }

}
