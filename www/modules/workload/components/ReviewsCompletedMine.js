// This file was generated from the component scaffold
// Copyright 2017

import {Component} from '@angular/core';
import template from './ReviewsCompletedMine.html';
import styles from './ReviewsCompletedMine.less';
import ReviewsCompleted from './/ReviewsCompleted';
import WorkloadProvider from '../../workload/services/WorkloadProvider';
import CrossFilterService from '../../app/services/CrossFilterService';
import SortableReviewTable from '../../shared/components/SortableReviewTable';

@Component({
    selector: 'reviews-completed-mine',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/api/core/Component-decorator.html
 * @example
 * <reviews-completed-mine name="ReviewsCompletedMine" (change)="onChange($event)"></reviews-completed-mine>
 */
export default class ReviewsCompletedMine extends ReviewsCompleted{

    constructor(workloadProvider: WorkloadProvider, crossFilterService: CrossFilterService) {
        super(workloadProvider, crossFilterService);
    }

    // Override
    ngOnInit() {
        this.initCategories();
        this.workloadProvider.getCompletedReviews().subscribe();
        this.filteredRecordsSubscription = this.crossFilterService.resultsChanged.subscribe((reviews) => {
            this.onFilterChange(reviews || []);
        });
    }

    // Override
    onFilterChange(filteredRecords: Array = []) {
        filteredRecords = SortableReviewTable.sortItems(filteredRecords, 'Close Date', 'rvwCompltdDt', SortableReviewTable.DESCENDING);
        this.filteredReviews = filteredRecords;
    }
}
