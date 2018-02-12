// This file was generated from the component scaffold
// Copyright 2016

import {Component, Input} from '@angular/core';
import styles from './ReviewDataSidebarBase.less';
import ReviewService from '../../review/services/ReviewService';
import {Router, ActivatedRoute} from '@angular/router';
import Client from '../../api/lrs.api.v1.generated';
import { Observable } from 'rxjs';

@Component({
    selector: 'review-data-sidebar-base',
    template: '',
    styles: [styles]
})

export default class ReviewDataSidebarBase {
    _parentSelectedTab: String = null;
    casesInBatch: Array = [];
    selectedCaseReviewId: String = null;
    currentReview: Object = null;
    displayedReviewFields: Array = [];
    _subscriptions: Array = [];
    router: Router = null;
    _locations: Array = null;
    _client: Client;

    constructor(route: ActivatedRoute, reviewService: ReviewService, router: Router, client:Client) {
        this._reviewService = reviewService;
        this.route = route;
        this.router = router;
        this._client = client;
    }

    ngOnInit() {

        // Hook to load the current Review in a sub class.
        this.refreshCurrentReview();
    }

    refreshCurrentReview() {
        // Override by a sub class.
    }

    @Input()
    set selectedTab(tab: String) {
        this._parentSelectedTab = tab;
        if (tab === 'reviewData') {
            this.loadActiveReviewsAndBatch();
        }
    }

    get selectedTab() {
        return this._parentSelectedTab;
    }

    loadActiveReviewsAndBatch() {
        if (!this.currentReview) {
            throw (new Error('ReviewDataSidebarBase - The currentReview is required.'));
        }

        if (this.currentReview.batchId) {
            this.getBatchById().subscribe((reviewBatch) => {

                // Build the cases in batch collection.
                this.casesInBatch = reviewBatch.reviews;

                // Select the first case as the default selection in the Cases in Batch Select.
                this.selectedCaseReviewId = this.casesInBatch && this.casesInBatch.length ? this.casesInBatch[0].reviewId : null;
            });
        }
    }

    getBatchById() {
        // Override me if different.
        return this._reviewService.getBatchById(this.currentReview.batchId);
    }

    onGoToBatchSummaryClick() {
        // Override me.
    }

    onCaseNumberSelect() {
        // Override me.
    }

    resetSubscriptions() {
        this._subscriptions.forEach(subscription => {
            subscription.unsubscribe();
        });

        this._subscriptions = [];
    }

    ngOnDestroy() {
        this.resetSubscriptions();
    }

    getReviewLocations() {
        if (this._locations) {
            return Observable.of(this._locations);
        }

        return this._client.resources.locations.get().first();
    }
}