// This file was generated from the component scaffold
// Copyright 2016
import { Component } from '@angular/core';
import template from './MyTeamWorkload.html';
import styles from './MyTeamWorkload.less';
import {Observable} from 'rxjs';
import DictionarySvc from '../../app/services/DictionarySvc';
import CrossFilterService from '../../app/services/CrossFilterService';
import WorkloadProvider from '../services/WorkloadProvider';
import _ from 'lodash';
import { REVIEW_STATUS } from '../../shared/constants';
import SortableReviewTable from '../../shared/components/SortableReviewTable';
import Utils from '../../shared/Utils';

@Component({
    selector: 'my-team-workload',
    template: template,
    styles: [styles]
})

/**
 * @see https://angular.io/docs/ts/latest/api/core/Component-decorator.html
 * @example
 * <my-team-totalReviews name="MyTeamWorkload" (change)="onChange($event)"></my-team-workload>
 */
export default class MyTeamWorkload {
    _subscriptions: Array = [];

    reassignCaseModal:Object = {review: null, isOpen: false, selectedTeamMemberId: null, reassigmentReasonId: null, cancellationReasonId: null};
    reassignAllCasesModal:Object = {isOpen: false, reviewers: null, allCasesReassignmentReasonId: null};
    cancelCaseModal:Object = {review: null, isOpen: false, cancellationReasonId: null};

    _dictSvc:DictionarySvc;
    reviewersByLocation:Array = [];

    reassignmentReasons:Array = [];
    cancellationReasons:Array = [];

    reviewerCountsLoading = false;
    reviewerCounts = [];
    totalReviews = 0;
    totalActiveReviews = 0;

    workloadProvider: WorkloadProvider;
    crossFilterService: CrossFilterService;

    categories: Array = [];

    constructor(dictSvc: DictionarySvc, workloadProvider: WorkloadProvider, crossFilterService: CrossFilterService) {
        this._dictSvc = dictSvc;
        this.workloadProvider = workloadProvider;
        this.crossFilterService = crossFilterService;
    }
    ngOnInit() {
        this._subscriptions = [];

        this.categories = [
            { 'name': 'Selection Reason', 'value': 'selectionReason', 'isDate': false },
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


        this.getTeamReviews();

        Observable.forkJoin(
            this._dictSvc.getReassignmentReasons(),
            this._dictSvc.getreviewCancelReasons()
        ).first().subscribe(([reassignmentReasons, cancellationReasons]) => {
            this.reassignmentReasons = reassignmentReasons;
            this.cancellationReasons = cancellationReasons;

            // Set the default selections
            this.cancelCaseModal.cancellationReasonId = this.cancellationReasons[0].code;
            this.reassignCaseModal.reassigmentReasonId  = this.reassignAllCasesModal.allCasesReassignmentReasonId = this.reassignmentReasons[0].code;
        });


        this._subscriptions.push(this.crossFilterService.resultsChangedWithSelections.subscribe(({filteredRecords, selections}) => {
            this.onFilterChange(filteredRecords, selections);
        }));

    }

    getTeamReviews() {
        this.reviewerCountsLoading = true;
        this.workloadProvider.getTeamReviews().subscribe(() => {
            this.reviewerCountsLoading = false;
        });
    }

    onReassign(review: Object) {
        this.workloadProvider.reassignReview(review, this.reassignCaseModal.selectedTeamMemberId, this.reassignCaseModal.reassigmentReasonId).subscribe(() => {
            this.reassignCaseModal.isOpen = false;
            this.getTeamReviews();
        });
    }

    onReassignAllCases(reviewCounts:Array) {
        let allReviews = [];

        reviewCounts.forEach(reviewCount => { allReviews.push(...reviewCount._reviews); });
        this.workloadProvider.reassignReviews(allReviews, this.reassignCaseModal.reassigmentReasonId).subscribe(() => {
            this.reassignAllCasesModal.isOpen = false;
            this.getTeamReviews();
        });
    }

    onConfirmCancelReview(review: Object) {
        this.reassignCaseModal.isOpen = false;
        this.cancelCaseModal.review = review;
        this.cancelCaseModal.isOpen = true;
    }

    onCancelReview(review: Object) {
        this.workloadProvider.cancelReview(review, this.cancelCaseModal.cancellationReasonId).subscribe(() => {
            this.cancelCaseModal.isOpen = false;
            this.getTeamReviews();
        });
    }

    showEditReviewModal(review: Object) {
        this.workloadProvider.getQualifiedReviewers(review.reviewId).subscribe((reviewers) => {
            this.reviewersByLocation = reviewers;
            this.reassignCaseModal.selectedTeamMemberId = this.reviewersByLocation && this.reviewersByLocation.length && this.reviewersByLocation[0].reviewerId;
            this.reassignCaseModal.review = review;
            this.reassignCaseModal.isOpen = true;
        });
    }

    showReassignAllCasesModal(reviewers: Array) {
        this.reassignAllCasesModal.reviewers = reviewers;
        this.reassignAllCasesModal.isOpen = true;
    }

    onFilterChange(filteredRecords: Array = [], selections: Array = []) {
        filteredRecords =  filteredRecords.filter(r => !_.isNull(r.reviewId));
        let _reviewerCounts = [];
        let showAllReviewers = (_.keys(selections).length === 0);
        if (showAllReviewers) {
            _reviewerCounts =  this.workloadProvider.filteredActiveReviewers;
            // reset reviews.
            _reviewerCounts.forEach(r => {
                r._reviews = [];
            });
        }

        let category =   this.categories.find(c => c.name === 'Date Due');

        _.forOwn(_.groupBy(filteredRecords, (r) => r.currentReviewLevel.reviewerName), (value, key) => {

            if (category) {

                // Default Sort
                value = SortableReviewTable.sortItems(value, category.name, category.value, SortableReviewTable.ASCENDING);
            }

            if (!showAllReviewers) {
                _reviewerCounts.push({name: key, _reviews: value});
            } else {
                let foundReviewer = _reviewerCounts.find(r => r.name === key);
                if (foundReviewer) {
                    foundReviewer._reviews = value;
                }
            }
        });

        this.reviewerCounts = _reviewerCounts;
        this.totalActiveReviews = filteredRecords.length;
    }

    ngOnDestroy() {
        this._subscriptions.forEach((subscription) => {
            if (subscription.unsubscribe) {
                subscription.unsubscribe();
            }
        });
    }
}
