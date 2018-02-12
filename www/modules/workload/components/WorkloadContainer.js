// This file was generated from the component scaffold
// Copyright 2016

import { Component } from '@angular/core';
import { Router } from '@angular/router';
import template from './WorkloadContainer.html';
import styles from './WorkloadContainer.less';
import { ReviewerOrAdminGuard, AdminGuard, HqAdminGuard } from '../../app/services/AuthGuards';
import CrossFilterBaseView from '../../shared/components/CrossFilterBaseView';
import WorkloadProvider from '../services/WorkloadProvider';

@Component({
    selector: 'workload-container',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/api/core/Component-decorator.html
 * @example
 * <workload-container name="WorkloadContainer" (change)="onChange($event)"></workload-container>
 */
export default class WorkloadContainer {

    _subscriptions = [];
    _reviewerOrAdminGuard: ReviewerOrAdminGuard;
    _adminGuard: AdminGuard;
    _hqAdminGuard: HqAdminGuard;
    _subscriptions: Array;

    workloadTabDimensions: Array = [];
    teamTabDimensions: Array = [];
    batchesDimensions: Array = [];
    workloadProvider: WorkloadProvider;

    constructor(router: Router, hqAdminGuard: HqAdminGuard, adminGuard: AdminGuard, reviewerOrAdminGuard: ReviewerOrAdminGuard, workloadProvider: WorkloadProvider) {
        this._router = router;
        this._hqAdminGuard = hqAdminGuard;
        this._adminGuard = adminGuard;
        this._reviewerOrAdminGuard = reviewerOrAdminGuard;
        this.workloadProvider = workloadProvider;
    }

    //Used to clear filters when tabs are changed
    clearFilters() {
        // do nothing for now.
    }

    isLocationOrHQAdmin() {
        return this._adminGuard.canActivate();
    }

    isBatchReviewer(){
        return this._reviewerOrAdminGuard.canActivate();
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

    ngOnInit() {
        this.initCrossFilters();
    }

    initCrossFilters() {
        let buttonType = CrossFilterBaseView.CONTROL_TYPE.BUTTON;
        this.workloadTabDimensions = [
            {
                title: 'REVIEW DEADLINE',
                controlType: buttonType,
                ignoredKeys: ['N/A'],
                dimension:  CrossFilterBaseView.DIMENSION_METHODS.REVIEW_DEADLINES,
                sort: (group) => CrossFilterBaseView.createSortFunctionFromArray([
                    CrossFilterBaseView.DEADLINE_KEYS.PAST_DUE,
                    CrossFilterBaseView.DEADLINE_KEYS.DUE_TODAY,
                    CrossFilterBaseView.DEADLINE_KEYS.DUE_NEXT_WEEK,
                    CrossFilterBaseView.DEADLINE_KEYS.DUE_LATER])(group),
                style: (key) => {
                    switch (key) {
                        case CrossFilterBaseView.DEADLINE_KEYS.DUE_TODAY:
                            return 'due-today';
                        case CrossFilterBaseView.DEADLINE_KEYS.PAST_DUE:
                            return 'past-due';
                        default:
                            return null;
                    }
                }
            },
            {
                title: 'FINAL REVIEW LEVEL',
                controlType: buttonType,
                dimension: CrossFilterBaseView.DIMENSION_METHODS.FINAL_REVIEW_LEVEL,
                sort: CrossFilterBaseView.COMMON_DIMENSION_SORTS.REVIEW_LEVEL
            },
            {
                title: 'REVIEW TYPE',
                controlType: 'button',
                dimension: (review) => review.reviewType || 'N/A'
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

    ngOnDestroy() {
        this._subscriptions.forEach((subscription) => {
            if (subscription.unsubscribe) {
                subscription.unsubscribe();
            }
        });
    }
}
