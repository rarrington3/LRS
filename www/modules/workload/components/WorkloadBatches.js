// This file was generated from the component scaffold
// Copyright 2016

import { Component } from '@angular/core';
import BatchService from '../../app/services/BatchService';
import WorkloadProvider from '../services/WorkloadProvider';
import template from './WorkloadBatches.html';
import styles from './WorkloadBatches.less';
import { ActivatedRoute, Router } from '@angular/router';
import {Observable} from 'rxjs';
import UserSvc from '../../app/services/UserSvc';
import {ModalSvc} from '../../app/services';
import DictionarySvc from '../../app/services/DictionarySvc';
import CrossFilterService from '../../app/services/CrossFilterService';
import { HqAdminGuard, AdminGuard, ReviewLocationAdminGuard } from '../../app/services/AuthGuards';
import SortableReviewTable from '../../shared/components/SortableReviewTable';
import Utils from '../../shared/Utils';
import { REVIEW_LEVEL_STATUS } from '../../shared/constants';

let compiledConfig = require('config');

@Component({
    selector: 'workload-batches',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/api/core/Component-decorator.html
 * @example
 * <workload-batches name="WorkloadBatches" (change)="onChange($event)"></workload-batches>
 * NOTE: This class is extended by WorkloadCompletedBatches
 */
export default class WorkloadBatches {
    _subscriptions: Array = [];
    _hqAdminGuard: HqAdminGuard;
    _locationAdminGuard: ReviewLocationAdminGuard;
    _modalSvc: ModalSvc;
    reassignCaseModal: Object = { review: null, isOpen: false, selectedTeamMemberId: null, reassigmentReasonId: null };
    cancelCaseModal:Object = {review: null, isOpen: false, cancellationReasonId: null};
    reviewersByLocation: Array = [];
    crossFilterService: CrossFilterService;
    allReviewers: Array = null;

    batchId: String = null;

    constructor(batchService: BatchService, route: ActivatedRoute, router: Router, workloadProvider: WorkloadProvider, dictSvc: DictionarySvc, userSvc: UserSvc, crossFilterService: CrossFilterService, hqAdminGuard: HqAdminGuard, reviewLocationAdminGuard: ReviewLocationAdminGuard, modalSvc: ModalSvc, adminGuard: AdminGuard) {
        this._batchSvc = batchService;
        this._workloadProvider = workloadProvider;
        this.route = route;
        this._router = router;
        this._dictSvc = dictSvc;
        this._userSvc = userSvc;
        this.crossFilterService = crossFilterService;
        this._adminGuard = adminGuard;
        this._hqAdminGuard = hqAdminGuard;
        this._locationAdminGuard = reviewLocationAdminGuard;
        this._modalSvc = modalSvc;
    }

    ngOnInit() {
        let activeReviewSortId = 'Active Review Date Due';
        let dateDueSortValue = 'currentReviewLevel.reviewerDateDue';

        this._subscriptions = [];

        //Route to navigate to review detail from sortableTable
        this.batchRoute = '/review';

        this.batchHeaders = [{ 'title': 'Selection Reason' }, { 'title': 'Review Type' }, { 'title': 'Date Opened' }, { 'title': 'Owner' }, { 'title': 'Level' }, { 'title': 'Completed' }];


        this.categories = [
            { 'name': 'Review Level Status', 'value': (review, currentReviewLevel) => {
                return Utils.getFHAReviewLevelStatus(review, currentReviewLevel);
            }
            },
            { 'name': 'Rating', 'value': 'currentReviewLevel.ratingCode' }
        ];

        this.placeHolderReview = [{
            'reviewId': '---',
            'caseNumber': '---',
            'selectionReason': '---',
            'rating': '---',
            'currentReviewLevel': {
                'status': '---',
                'ratingCode': '---',
                'reviewerName': '---',
                'type': '---'
            }
        }];

        this._subscriptions.push(this._batchSvc.activeBatchObservable.subscribe((change) => {
            this.activeBatch = change.currentValue;
        }));

        this._subscriptions.push(this._batchSvc.batchOwnerObservable.subscribe((change) => {
            this.batchOwner = change.currentValue;
        }));

        this._subscriptions.push(this._batchSvc.operationalReviewObservable.subscribe((change) => {
            this.operationalReview = change.currentValue;
        }));

        this._subscriptions.push(this._batchSvc.activeFilteredReviewsObservable.subscribe((change) => {
            let activeReviews = change.currentValue || [];
            activeReviews = activeReviews.filter(r => r.reviewId !== null);

            // For reviews that are pending batch review, we want to show the original user that completed  the review level, not the batch owner
            //(which is done in the services so the batch owner has permission to make any final changes)
            activeReviews.forEach(review => {
                if (review.currentReviewLevel.status.toLowerCase() === REVIEW_LEVEL_STATUS.PENDING_BATCH_REVIEW) {
                    review.currentReviewLevel.reviewerName = review.currentReviewLevel.originalReviewerName;
                    review.currentReviewLevel.reviewerId = review.currentReviewLevel.originalReviewerId;
                }
            });

            this.activeReviews = SortableReviewTable.sortItems(activeReviews, activeReviewSortId, dateDueSortValue, SortableReviewTable.ASCENDING);
        }));

        this._subscriptions.push(this._batchSvc.batchedDefectAreasObservable.subscribe((change) => {
            this.defectAreas = change.currentValue;
        }));

        this._subscriptions.push(this._batchSvc.operationalDefectAreasObservable.subscribe((change) => {
            this.operationalDefectAreas = change.currentValue;
        }));

        this._subscriptions.push(this.route.params.subscribe(params => {
            this.batchId = null;
            this._batchSvc.locationId = null;

            if (params.batchId) {
                this.batchId = params.batchId;
            }

            // We only load batches by location for HQ admin and Location Admin (if it's their location). Ignore location id otherwise.
            if (params.locationId && (this.checkHQAdmin() || (this.checkLocationAdmin() && this._userSvc.locationId === params.locationId))) {
                this._batchSvc.locationId = params.locationId || null;
            }

            this.loadAllBatches(this.batchId, this._batchSvc.locationId);

        }));

        Observable.forkJoin(
            this._dictSvc.getReassignmentReasons(),
            this._dictSvc.getreviewCancelReasons()
        ).first().subscribe(([reassignmentReasons, cancellationReasons]) => {
            this.reassignmentReasons = reassignmentReasons;
            this.cancellationReasons = cancellationReasons;

            // Set the default selections
            this.cancelCaseModal.cancellationReasonId = this.cancellationReasons[0].code;
            this.reassignCaseModal.reassigmentReasonId = this.reassignmentReasons[0].code;
        });

        this.operationalDefectAreas = this._batchSvc.operationalDefectAreas;
        this.defectAreas = this._batchSvc.batchedDefectAreas;
        this.activeBatch = this._batchSvc.activeBatch;
        this.batchOwner = this._batchSvc.batchOwner;
        this.activeReviews = SortableReviewTable.sortItems(this._batchSvc.activeReviews, activeReviewSortId, dateDueSortValue, SortableReviewTable.ASCENDING);
        this.operationalReview = this._batchSvc.operationalReview;


        this._subscriptions.push(this.crossFilterService.resultsChanged.subscribe((reviews) => {
            this.onCrossFilterChange(reviews || []);
        }));

    }

    batchReadyToReview() {
        if (this.activeBatch && this.activeBatch.status && this.activeBatch.status.toLowerCase() === 'under batch review' && this.activeBatch.outstandingCaseActivity.length === 0) {
            return true;
        }
        else {
            return false;
        }
    }

    batchSubmit() {
        this._batchSvc.submitBatch(this.activeBatch.batchId).subscribe(() => {
            this._router.navigate(['/workload/summary']);
        });
    }

    batchCancel() {
        this._modalSvc.askForConfirmation('Are you sure you want to cancel this batch? This will cancel all pending and active reviews including the operational review. Any outstanding binder requests will also be canceled.').first()
            .subscribe(
                () => {
                    this._batchSvc.cancelBatch(this.activeBatch.batchId).subscribe(() => {
                        this._router.navigate(['/workload/summary']);
                    });
                }, () => {
                }
            );
    }

    getResponseDocumentURL(document) {
        return `${compiledConfig.server}/api/v1/reviews/batches/${this.activeBatch.batchId}/operationalDocuments/${document.documentId}`;
    }

    onReassign(review: Object) {
        this._workloadProvider.reassignReview(review, this.reassignCaseModal.selectedTeamMemberId, this.reassignCaseModal.reassigmentReasonId).subscribe(() => {
            this.reassignCaseModal.isOpen = false;
            this._batchSvc.refreshBatch(this.activeBatch.batchId, false);
        });
    }

    //Is current user owner of this batch
    checkBatchOwner() {
        return this._userSvc.personnelId && (this._userSvc.personnelId === this.activeBatch.batchOwner);
    }

    isLocationOrHQAdmin() {
        return this._adminGuard.canActivate();
    }

    checkLocationAdmin() {
        return this._locationAdminGuard.canActivate();
    }

    checkHQAdmin() {
        return this._hqAdminGuard.canActivate();
    }

    showEditReviewModal(review: Object, isOperationalReview: Boolean = false) {
        let requests = [ this._workloadProvider.getQualifiedReviewers(review.reviewId)];
        if (isOperationalReview){

            // Cache the allReviewers collection.
            let getReviewers = (this.allReviewers) ? Observable.of(this.allReviewers) : this._workloadProvider.getAllReviewers();
            requests.push(getReviewers);
        }

        Observable.forkJoin(
            [...requests]
        ).first().subscribe(([qualifiedReviewers, allReviewers]) => {
            if (isOperationalReview && allReviewers) {
                qualifiedReviewers = qualifiedReviewers.filter(qr => {
                    let foundFullReviewer = allReviewers.find(ar => ar.reviewerId === qr.reviewerId);
                    return foundFullReviewer && foundFullReviewer.reviewTypes && foundFullReviewer.reviewTypes.find(rt => rt.assignmentTypeCode.toLowerCase() === 'o');
                } );

                this.allReviewers = allReviewers;
            }


            this.reviewersByLocation = Utils.getReviewersByLocation(qualifiedReviewers, review.currentReviewLevel.reviewLocationId);
            this.reassignCaseModal.selectedTeamMemberId = this.reviewersByLocation && this.reviewersByLocation.length && this.reviewersByLocation[0].reviewerId;
            this.reassignCaseModal.review = review;
            this.reassignCaseModal.isOpen = true;
        });
    }

    onCrossFilterChange(filteredRecords: Array) {
        this._batchSvc.activeFilteredReviews = filteredRecords.reverse();
    }

    loadAllBatches(activeBatchId: String = null, locationId: String = null) {
        this._batchSvc.getBatches(activeBatchId, locationId);
    }

    onCancelReview(review: Object) {
        this.cancelCaseModal.isOpen = false;
        this._workloadProvider.cancelReview(review, this.cancelCaseModal.cancellationReasonId).subscribe(() => {
            this.cancelCaseModal.isOpen = false;
            if (this.batchId &&  this._batchSvc.locationId) {
                this._batchSvc.getBatches(this.batchId, this._batchSvc.locationId);
            }
        });
    }

    onConfirmCancelReview(review: Object) {
        this.reassignCaseModal.isOpen = false;
        this.cancelCaseModal.review = review;
        this.cancelCaseModal.isOpen = true;
    }

    ngOnDestroy() {
        this._subscriptions.forEach((subscription) => {
            if (subscription.unsubscribe) {
                subscription.unsubscribe();
            }
        });
    }

}
