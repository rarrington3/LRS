// This file was generated from the component scaffold
// Copyright 2017

import {Component} from '@angular/core';
import template from './ReviewsCompletedTeam.html';
import styles from './ReviewsCompletedTeam.less';
import CrossFilterService from '../../app/services/CrossFilterService';
import WorkloadProvider from '../services/WorkloadProvider';
import SortableReviewTable from '../../shared/components/SortableReviewTable';

@Component({
    selector: 'reviews-completed-team',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/api/core/Component-decorator.html
 * @example
 * <reviews-completed-team name="ReviewsCompletedTeam" (change)="onChange($event)"></reviews-completed-team>
 */
export default class ReviewsCompletedTeam {
    workloadProvider: WorkloadProvider;
    crossFilterService: CrossFilterService;
    isLoading: Boolean = true;
    _subscriptions: Array = [];
    filteredReviews: Array = [];

    constructor( workloadProvider: WorkloadProvider, crossFilterService: CrossFilterService) {

        this.workloadProvider = workloadProvider;
        this.crossFilterService = crossFilterService;
    }

    ngOnInit() {
        this._subscriptions = [];

        this.categories = [
            { 'name': 'Selection Reason', 'value': 'selectionReason', 'isDate': false },
            { 'name': 'Final Review Level', 'value': 'currentReviewLevel.type', 'isDate': false },
            { 'name': 'Final Rating', 'value': 'rating', 'isDate': false },
            { 'name': 'Close Date', 'value': 'rvwCompltdDt', 'isDate': true },
            { 'name': 'Initial Review By', 'value': 'completedReviewLevels[0].reviewerName', 'isDate': false },
            { 'name': 'Completed By', 'value': 'currentReviewLevel.reviewerName', 'isDate': false }
        ];

        this._subscriptions.push(this.workloadProvider.selectedLocationObservable.subscribe((change) => {
            this.onLocationChange(change.currentValue);
        }));

        this._subscriptions.push(this.crossFilterService.resultsChanged.subscribe((reviews) => {
            this.onFilterChange(reviews || []);
        }));


        if (this.workloadProvider.selectedLocation) {
            this.onLocationChange(this.workloadProvider.selectedLocation);
        }

    }

    onLocationChange(locationId: String) {
        if (locationId) {
            this.isLoading = true;
            this.workloadProvider.getCompletedReviewsByLocation(locationId).subscribe();
        }
    }

    onFilterChange(filteredRecords: Array) {
        this.isLoading = false;
        this.filteredReviews = SortableReviewTable.sortItems(filteredRecords, 'Close Date', 'rvwCompltdDt', SortableReviewTable.DESCENDING);
    }

    ngOnDestroy() {
        this._subscriptions.forEach((subscription) => {
            if (subscription.unsubscribe) {
                subscription.unsubscribe();
            }
        });
    }
}
