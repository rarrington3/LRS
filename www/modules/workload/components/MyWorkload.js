// This file was generated from the component scaffold
// Copyright 2016

import { Component } from '@angular/core';
import template from './MyWorkload.html';
import styles from './MyWorkload.less';
import _ from 'lodash';
import CrossFilterBaseView from '../../shared/components/CrossFilterBaseView';
import CrossFilterService from '../../app/services/CrossFilterService';
import WorkloadProvider from '../services/WorkloadProvider';
import { REVIEW_STATUS } from '../../shared/constants';
import SortableReviewTable from '../../shared/components/SortableReviewTable';
import Utils from '../../shared/Utils';
@Component({
    selector: 'my-workload',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/api/core/Component-decorator.html
 * @example
 * <my-workload name='MyWorkload' (change)='onChange($event)'></my-workload>
 */
export default class MyWorkload {
    _subscriptions = [];
    crossFilterService: CrossFilterService;
    workloadProvider: WorkloadProvider;
    categories: Array = [];

    constructor(crossFilterService: CrossFilterService, workloadProvider:WorkloadProvider) {
        this.crossFilterService = crossFilterService;
        this.workloadProvider = workloadProvider;
    }

    ngOnInit() {

        /** The array of categories.
         * name: is the displayed category
         * value: the key we will filter by
         * reviewLevel: false if the filter is on currentReviewLevel, true if on reviewLevel
         */
        this.categories = [
            { 'name': 'Selection Reason', 'value': 'selectionReason', 'isDate': false},
            { 'name': 'Review Type', 'value': 'reviewType', 'isDate': false},
            { 'name': 'Review Level', 'value': 'currentReviewLevel.type', 'isDate': false },
            { 'name': 'Batch', 'value': 'batchReferenceId', 'isDate': false, createLink: (review) => { return `../../workload/batches/location/${review.currentReviewLevel.reviewLocationId}/batch/${review.batchId}`; }
            },
            { 'name': 'Status', 'value': (review, currentReviewLevel) => {
                return Utils.getFHAReviewLevelStatus(review, currentReviewLevel);
            }, 'isDate': false},
            { 'name': 'Date Assigned', 'value': (review, currentReviewLevel) => {
                return (currentReviewLevel.status.toLowerCase() === REVIEW_STATUS.COMPLETED) ? currentReviewLevel.dateRequestSentToLender : currentReviewLevel.reviewerDateAssigned;
            }, 'isDate': true },
            { 'name': 'Date Due', 'value': (review, currentReviewLevel) => {
                return (currentReviewLevel.status.toLowerCase() === REVIEW_STATUS.COMPLETED) ? currentReviewLevel.dateResponseDueFromLender : currentReviewLevel.reviewerDateDue;
            }, 'isDate': true }
        ];

        // The "Pending Lender Responses" categoires match the reviewer's categories except the date assigned and due are slightly different
        this.lenderCategories =  this.categories.map((c) => {
            let clone = _.clone(c);
            if (clone.name === 'Date Assigned') {
                clone.name = 'Date Sent';
                clone.value = 'currentReviewLevel.dateRequestSentToLender';
            }

            if (clone.name === 'Date Due') {
                clone.name = 'Response Due';
                clone.value = 'currentReviewLevel.dateResponseDueFromLender';
            }
            return clone;
        });


        this._subscriptions = [];

        this._subscriptions.push(this.crossFilterService.resultsChanged.subscribe((reviews) => {
            this.onFilterChange(reviews || []);
        }));

        this.workloadProvider.getMyReviews().subscribe();
    }

    ngOnDestroy() {
        this._subscriptions.forEach((subscription) => {
            if (subscription.unsubscribe) {
                subscription.unsubscribe();
            }
        });
    }

    onFilterChange(filteredRecords: Array) {
        let {filteredActiveReviews, filteredPendingReviews} = CrossFilterBaseView.getActivePendingReviews(filteredRecords);

        // Default Sort
        let category = this.categories.find(c => c.name === 'Date Due');
        let lenderCategory = this.lenderCategories.find(c => c.name === 'Response Due');
        if (category && lenderCategory) {
            filteredActiveReviews = SortableReviewTable.sortItems(filteredActiveReviews, `0-${category.name}`, category.value);
            filteredPendingReviews = SortableReviewTable.sortItems(filteredPendingReviews, `1-${lenderCategory.name}`, lenderCategory.value);
        }

        this.activeReviews = filteredActiveReviews;
        this.pendingReviews = filteredPendingReviews;
    }

}
