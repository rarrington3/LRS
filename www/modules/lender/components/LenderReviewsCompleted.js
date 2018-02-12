// This file was generated from the component scaffold
// Copyright 2017

import { Component } from '@angular/core';
import template from './LenderReviewsCompleted.html';
import styles from './LenderReviewsCompleted.less';
import LenderService from '../services/LenderService';
import WorkloadProvider from '../../workload/services/WorkloadProvider';
import CrossFilterService from '../../app/services/CrossFilterService';
import ReviewsCompleted from '../../workload/components/ReviewsCompleted';

@Component({
    selector: 'lender-completed-reviews',
    template: template,
    styles: [styles]
})

export default class LenderReviewsCompleted extends ReviewsCompleted{
    lenderSvc: LenderService;
    constructor(workloadProvider: WorkloadProvider, crossFilterService: CrossFilterService, lenderService: LenderService) {
        super(workloadProvider, crossFilterService);
        this.lenderSvc = lenderService;
    }

    // Override
    ngOnInit() {
        this.lender = true;
        this.initCategories();
        this.lenderSvc.getCompletedLenderReviews();
        this.filteredRecordsSubscription = this.crossFilterService.resultsChanged.subscribe((reviews) => {
            this.onFilterChange(reviews || []);
        });
    }

    // Override
    onFilterChange(filteredRecords: Array) {
        this.filteredReviews = filteredRecords;
    }
}
