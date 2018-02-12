// This file was generated from the component scaffold
// Copyright 2017

import { Component } from '@angular/core';
import template from './ReviewsCompleted.html';
import styles from './ReviewsCompleted.less';
import WorkloadProvider from '../services/WorkloadProvider';
import {Observable} from 'rxjs';
import CrossFilterBaseView from '../../shared/components/CrossFilterBaseView';
import CrossFilterService from '../../app/services/CrossFilterService';

@Component({
    selector: 'reviews-completed',
    template: template,
    styles: [styles]
})
/**
 * NOTE: This class is shared by the Lender Completed Reviews and the 'Mine' completed reviews tab in Workload.
 * @see https://angular.io/docs/ts/latest/api/core/Component-decorator.html
 * @example
 * <reviews-completed name="ReviewsCompleted" (change)="onChange($event)"></reviews-completed>
 */
export default class ReviewsCompleted {
    allCompletedReviews: Observable;
    filteredReviews: Array = [];
    lender: Boolean = false;

    // Sort table columns;
    sortTableCategories: Array = [];

    // Crossfilter dimensions
    filterDimensionGroups: Array = [];
    crossFilterService: CrossFilterService;
    filteredRecordsSubscription: Observable;

    constructor(workloadProvider: WorkloadProvider, crossFilterService: CrossFilterService) {
        this.workloadProvider = workloadProvider;
        this.allCompletedReviews = this.workloadProvider.completedReviewsObservable;
        this.crossFilterService = crossFilterService;
    }

    ngOnInit() {
        this.initCategories();
        this.workloadProvider.getCompletedReviews().subscribe();
        this.filteredRecordsSubscription = this.crossFilterService.resultsChanged.subscribe((reviews) => {
            this.onFilterChange(reviews || []);
        });
    }

    initCategories() {
        this.sortTableCategories = [
            { 'name': 'Selection Reason', 'value': 'selectionReason', 'isDate': false },
            { 'name': 'Final Review Level', 'value': 'currentReviewLevel.type', 'isDate': false },
            { 'name': 'Final Rating', 'value': 'rating', 'isDate': false },
            { 'name': 'Close Date', 'value': 'rvwCompltdDt', 'isDate': true }
        ];

        if (this.lender) {
            this.sortTableCategories.push({ 'name': 'Last Action By', 'value': 'lastLenderName', 'isDate': false });
        } else {
            this.sortTableCategories.push({ 'name': 'Initial Review By', 'value': 'completedReviewLevels[0].reviewerName', 'isDate': false });
            this.sortTableCategories.push({ 'name': 'Completed By', 'value': 'currentReviewLevel.reviewerName', 'isDate': false });
        }
    }

    onFilterChange(filteredRecords: Array) {
        let {filteredActiveReviews} = CrossFilterBaseView.getActivePendingReviews(filteredRecords);
        this.filteredReviews = filteredActiveReviews;

    }

    getRoute() {
        if (this.lender) {
            return '../../activeReviews/review';
        }
        else {
            return '../../../review';
        }
    }

    ngOnDestroy() {
        if (this.filteredRecordsSubscription) {
            this.filteredRecordsSubscription.unsubscribe();
        }
    }
}
