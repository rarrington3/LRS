// This file was generated from the component scaffold
// Copyright 2016

import { Component } from '@angular/core';
import LenderService from '../services/LenderService';
import styles from './lenderWorkload.less';
import template from './LenderWorkload.html';
import _ from 'lodash';
import CrossFilterService from '../../app/services/CrossFilterService';
import CrossFilterBaseView from '../../shared/components/CrossFilterBaseView';

@Component({
    selector: 'lender-workload',
    styles: [styles],
    template: template
})
    /**
     * @see https://angular.io/docs/ts/latest/api/core/Component-decorator.html
     * @example
     * <lender-workload name="LenderWorkload" (change)="onChange($event)"></lender-workload>
     */
export default class LenderWorkload {
    _subscriptions: Array = [];
    crossFilterService: CrossFilterService;
    filteredReviews: Array = [];
    filteredPendingReviews: Array = [];
    constructor(lenderService: LenderService, crossFilterService: CrossFilterService) {
        this.lenderService = lenderService;
        this.lenderService.isLender = true;
        this.crossFilterService = crossFilterService;
    }

    ngOnInit() {

        /** The array of categories.
         * name: is the displayed category
         * value: the key we will filter by
         * reviewLevel: false if the filter is on currentReviewLevel, true if on reviewLevel
         */
        this.categories = [
            { 'name': 'Borrower Name', 'value': 'borrowerName', 'isDate': false },
            { 'name': 'Selection Reason', 'value': 'selectionReason', 'isDate': false },
            { 'name': 'Review Level', 'value': 'currentReviewLevel.type', 'isDate': false },
            { 'name': 'Request Date', 'value': 'currentReviewLevel.dateRequestSentToLender', 'isDate': true },
            { 'name': 'Response Due', 'value': 'currentReviewLevel.dateResponseDueFromLender', 'isDate': true },
            { 'name': 'Last Action By', 'value': 'lastLenderName', 'isDate': false }
        ];

        // The "Under Review by FHA" categoires match the lender's categories except the date assigned and due are slightly different
        this.lenderCategories =  this.categories.map((c) => {
            let clone = _.clone(c);
            if (clone.name === 'Request Date') {
                clone.value = 'currentReviewLevel.reviewerDateAssigned';
            }
            return clone;
        });

        // Lenders don't want to see Response Due
        let responseDueCategory = this.lenderCategories.find(category => category.name === 'Response Due');
        let responseDueIndex = this.lenderCategories.indexOf(responseDueCategory);
        this.lenderCategories.splice(responseDueIndex, 1);

        this._subscriptions.push(this.crossFilterService.resultsChanged.subscribe((reviews) => {
            this.onCrossFilterChange(reviews || []);
        }));

        this.lenderService.getReviews();
    }
    ngOnDestroy() {
        this._subscriptions.forEach((subscription) => {
            if (subscription.unsubscribe) {
                subscription.unsubscribe();
            }
        });
    }

    onCrossFilterChange(filteredRecords: Array) {
        let {filteredActiveReviews, filteredPendingReviews} = CrossFilterBaseView.getActivePendingReviews(filteredRecords, true);
        this.filteredReviews = filteredActiveReviews;
        this.filteredPendingReviews = filteredPendingReviews;


    }

}
