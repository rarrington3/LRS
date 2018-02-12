// This file was generated from the component scaffold
// Copyright 2017

import { Component } from '@angular/core';
import { Router } from '@angular/router';
import template from './WorkloadBatchesSidebar.html';
import styles from './WorkloadBatchesSidebar.less';
import BatchService from '../../app/services/BatchService';
import _ from 'lodash';
import ObservableProperty from '../../shared/decorators/observableProperty';
import CrossFilterBaseView from '../../shared/components/CrossFilterBaseView';
import SortableReviewTable from '../../shared/components/SortableReviewTable';
import Client from '../../api/lrs.api.v1.generated';
import { HqAdminGuard, ReviewerGuard, ReviewLocationAdminGuard } from '../../app/services/AuthGuards';
import {UserSvc} from '../../app/services';

@Component({
    selector: 'workload-batches-sidebar',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/api/core/Component-decorator.html
 * @example
 * <workload-batches-sidebar name="WorkloadBatchesSidebar" (change)="onChange($event)"></workload-batches-sidebar>
 * NOTE: This class is extended by WorkloadCompletedBatchesSidebar
 */
export default class WorkloadBatchesSidebar {
    _batchSvc: BatchService;
    _subscriptions: Array = [];
    reviewDimensions: Array = [];
    _client: Client;

    locationRevieweers: Array = null;

    @ObservableProperty()
    activeBatchReviews;

    _router: Router = null;

    hqAdminGuard: HqAdminGuard;
    reviewerGuard: ReviewerGuard;
    reviewLocationAdminGuard: ReviewLocationAdminGuard;
    isHqAdmin: Boolean = false;
    isReviewLocationAdmin: Boolean = false;
    isReviewer: Boolean = false;
    _selectedLocation: String = null;
    _userSvc: UserSvc = null;

    batchesLocationRoute: String = '/workload/batches/location/';
    batchesRoute: String = '/workload/batches/'

    constructor(batchService: BatchService, client: Client, router: Router, hqAdminGuard: HqAdminGuard, reviewerGuard: ReviewerGuard, reviewLocationAdminGuard: ReviewLocationAdminGuard, userSvc: UserSvc) {
        this._batchSvc = batchService;
        this._client = client;
        this._router = router;
        this.hqAdminGuard = hqAdminGuard;
        this.reviewerGuard = reviewerGuard;
        this.reviewLocationAdminGuard = reviewLocationAdminGuard;
        this._userSvc = userSvc;

        // Set the user svc instance in the batch svc. For some reason, I couldn't inject user svc directly to batch svc.
        this._batchSvc.userSvc = this._userSvc;
        this._batchSvc.resetData();
    }

    ngOnInit() {
        this._batchSvc.isHqAdmin = this.hqAdminGuard.canActivate();
        this._batchSvc.isReviewLocationAdmin = this.reviewLocationAdminGuard.canActivate();
        this._batchSvc.isReviewer = this.reviewerGuard.canActivate();

        this._subscriptions = [];
        this._subscriptions.push(this._batchSvc.batchesObservable.subscribe((change) => {
            let sortedBatches = change.currentValue || [];
            this.refreshBatches(sortedBatches);
        }));

        this._subscriptions.push(this._batchSvc.activeBatchObservable.subscribe((change) => {
            this.activeBatch = change.currentValue;

            if (this.locationReviewers) {
                _.defer(() => {
                    this.refreshBatchReviews();
                });
            } else {
                this._client.resources.reviewers.active.get().share().first().subscribe(data => {
                    this.locationReviewers = data || [];
                    this.refreshBatchReviews();
                });
            }
        }));

        this.refreshBatches(this._batchSvc.batches);
        this.refreshBatchReviews();
    }

    refreshLocation() {

    }

    set selectedLocation(location: String) {
        this._selectedLocation = null;
        this._selectedLocation = location;
        this._router.navigate([`${this.batchesLocationRoute}${location}`]);
    }

    get selectedLocation() {
        return this._selectedLocation;
    }

    refreshBatches(batches: Array = null) {
        if (batches) {

            //  The locations collection should have been loaded if it's required. Batches are loaded when the main view, WorkloadBatches, is loaded.
            if (this._batchSvc.allLocations) {
                _.defer(() => {
                    let userLocation = this._batchSvc.allLocations.find(loc => loc.locationId === this._userSvc.locationId);
                    this._selectedLocation = (this._batchSvc.locationId) ? this._batchSvc.locationId : userLocation && userLocation.locationId || 'all';
                });
            }

            this.batches = SortableReviewTable.sortItems(batches, 'Creation Date', 'creationDate', SortableReviewTable.ASCENDING);
        }
    }

    refreshBatchReviews() {

        if (this.locationReviewers && this.activeBatch) {
            let _activeBatchReviews = (this._batchSvc.activeReviews) ? [ ...this._batchSvc.activeReviews] : [];

            // Display all team members without reviews.
            let batchTeamMembersWithoutReviews = this.activeBatch.batchTeamMembers.filter(tm => !this.activeBatch.reviews.find(r => r.currentReviewLevel.reviewerId === tm));
            batchTeamMembersWithoutReviews = batchTeamMembersWithoutReviews.map(tm => {
                let reviewerObj = this.locationReviewers.find( r => r.reviewerId === tm);
                // Generate a dummy review for each reviewer without a batch review.
                let _reviewerName = (reviewerObj) ? `${reviewerObj.nameFirst} ${reviewerObj.nameLast}` : 'NA';
                let _reviewerId = reviewerObj && reviewerObj.reviewerId;
                return {reviewId: null, status: 'NA', currentReviewLevel: {type: 'N/A', reviewerDateDue: null, status: 'NA', reviewerId: _reviewerId || null, reviewerName: _reviewerName}};
            });

            if (batchTeamMembersWithoutReviews && batchTeamMembersWithoutReviews.length) {
                _activeBatchReviews.push(...batchTeamMembersWithoutReviews);
            }

            this.activeBatchReviews = _activeBatchReviews.filter(r => r.reviewType !== 'Operational');

            this._updateBatchesAfterReassignment();
            this.initBatchReviewFilters();
        }
    }

    initBatchReviewFilters() {
        if (this.locationReviewers) {
            this.reviewDimensions = [

                {
                    title: 'REVIEW STATUS',
                    ignoredKeys: ['NA'],
                    controlType: CrossFilterBaseView.CONTROL_TYPE.BUTTON,
                    dimension: (review) => { return (review.reviewId === '---') ? 'Outstanding Request' : review.currentReviewLevel.status; }
                },
                {
                    title: 'TEAM MEMBERS',
                    controlType: CrossFilterBaseView.CONTROL_TYPE.BUTTON,
                    hideEmptyGroups: false,
                    ignoredKeys: ['NA', null],
                    parseKeyValue: (key) => {
                        let hasValidBatchReview = this._batchSvc.activeReviews.find(r => {
                            if (r && r.currentReviewLevel) {
                                return (r.currentReviewLevel.reviewerName === key.key);
                            }

                            return false;
                        });

                        if (!hasValidBatchReview) {
                            return 0;
                        }

                        return  key.value;
                    },
                    dimension: (review) => { return (review.reviewId !== '---' && review.currentReviewLevel.reviewerName) ? review.currentReviewLevel.reviewerName : 'NA'; }
                }
            ];
        }
    }

    getBatchInfo(batch) {
        // User a shorter route for ROLE_REVIEWER although either route below should work fine.
        if (this.selectedLocation && (this._batchSvc.isHqAdmin || this._batchSvc.isReviewLocationAdmin)) {
            this._router.navigate([`${this.batchesLocationRoute}${this.selectedLocation}/batch/${batch.batchId}`]);
        } else {
            this._router.navigate([`${this.batchesRoute}${batch.batchId}`]);
        }
    }

    batchedReviewsLength(batch) {
        return (batch.reviews) ? batch.reviews.length : 0;
    }

    ngOnDestroy() {
        this._subscriptions.forEach((subscription) => {
            if (subscription.unsubscribe) {
                subscription.unsubscribe();
            }
        });
    }

    _updateBatchesAfterReassignment() {
        if (this.activeBatch && this.batches) {

            // Update the batches collection.
            let activeBatchIndex = this.batches.findIndex(b => b.batchId === this.activeBatch.batchId);
            if (activeBatchIndex >= 0) {
                this.batches[activeBatchIndex] = this.activeBatch;
            }

        }
    }
}
