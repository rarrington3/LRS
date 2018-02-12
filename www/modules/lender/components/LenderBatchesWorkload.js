// This file was generated from the component scaffold
// Copyright 2016

import { Component } from '@angular/core';
import { Router } from '@angular/router';
import BatchService from '../../app/services/BatchService';
import template from './LenderBatchesWorkload.html';
import styles from './LenderBatchesWorkload.less';
import SortableReviewTable from '../../shared/components/SortableReviewTable';
let compiledConfig = require('config');


@Component({
    selector: 'lender-batches-workload',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/api/core/Component-decorator.html
 * @example
 * <lender-batches-workload name="LenderBatchesWorkload" (change)="onChange($event)"></lender-batches-workload>
 */
export default class LenderBatchesWorkload {

    constructor(batchSvc: BatchService, router: Router) {
        this._batchSvc = batchSvc;
        this._router = router;
        this._batchSvc.resetData();
    }

    ngOnInit() {
        this._subscriptions = [];
        this.submitBatch = false;
        this.showDocuments = false;
        this.allowUploadDocuments = false;
        this.operationalDocsUploaded = false;
        this.loanReviewTableHeader = 'Loan Reviews';
        this.isPendingLenderResponse = false;

        this.categories = [
            { 'name': 'Property Address', 'value': 'propertyStreetAddress' },
            { 'name': 'Rating', 'value': 'currentReviewLevel.ratingCode' }
        ];

        this.placeHolderReview = [{
            'caseNumber': '---',
            'propertyStreetAddress': '---',
            'currentReviewLevel': {
                'ratingCode': '---',
                'type': '---'
            }
        }];

        let activeReviewSortId = 'Active Review Date Due';
        let dateDueSortValue = 'currentReviewLevel.dateResponseDueFromLender';

        this._subscriptions.push(this._batchSvc.activeLenderBatchObservable.subscribe((change) => {
            this._setActiveBatch(change.currentValue);
        }));

        this._subscriptions.push(this._batchSvc.activeFilteredReviewsObservable.subscribe((change) => {
            let reviews = change.currentValue || [];
            this.activeReviews = SortableReviewTable.sortItems(reviews, activeReviewSortId, dateDueSortValue, SortableReviewTable.ASCENDING);
        }));

        this._subscriptions.push(this._batchSvc.batchedDefectAreasObservable.subscribe((change) => {
            this.defectAreas = change.currentValue;
        }));

        this._subscriptions.push(this._batchSvc.lenderBatchSubmitObservable.subscribe((change) => {
            this.submitBatch = change.currentValue;
        }));

        this._subscriptions.push(this._batchSvc.operationalDefectAreasObservable.subscribe((change) => {
            this.operationalDefectAreas = change.currentValue;
        }));

        this._subscriptions.push(this._batchSvc.operationalReviewObservable.subscribe((change) => {
            this.operationalReview = change.currentValue;

        }));

        this.operationalReview = this._batchSvc.operationalReview;

        this.defectAreas = this._batchSvc.batchedDefectAreas;
        this.operationalDefectAreas = this._batchSvc.operationalDefectAreas;
        this._setActiveBatch(this._batchSvc.activeLenderBatch);
        this.activeReviews = SortableReviewTable.sortItems(this._batchSvc.activeFilteredReviews, activeReviewSortId, dateDueSortValue, SortableReviewTable.ASCENDING);
        this.getLenderBatches();
    }

    getLenderBatches() {
        this._batchSvc.getLenderBatches();
    }

    _setActiveBatch(activeBatch) {
        this.activeBatch = activeBatch;
        this.operationalDocsUploaded = false;

        if (this.activeBatch) {
            this.postURL = `${compiledConfig.server}/api/v1/reviews/batches/${this.activeBatch.batchId}/operationalDocuments`;

            this.showDocuments = (this.activeBatch && (
                (this.activeBatch.operationalDocuments && this.activeBatch.operationalDocuments.length) ||
                this.activeBatch.requestOperationalDocuments === true));

            this.allowUploadDocuments = (this.showDocuments && this.activeBatch.operationalReview === null);
            this.operationalDocsUploaded = (this.activeBatch.operationalDocuments && this.activeBatch.operationalDocuments.length);
            this.isPendingLenderResponse = this.activeBatch.status.toLowerCase() === 'pending lender response';
            this.loanReviewTableHeader = this.isPendingLenderResponse ? 'Loan Reviews - Awaiting Lender Response' : 'Loan Reviews';
        }
    }

    canLenderSubmit() {
        return this._batchSvc.lenderBatchSubmit;
    }

    getResponseDocumentURL(document) {
        return `${compiledConfig.server}/api/v1/reviews/batches/${this.activeBatch.batchId}/operationalDocuments/${document.documentId}`;
    }

    removeFile(event, document) {
        event.preventDefault();
        this._batchSvc.removeFile(document).subscribe(() => {
            this._batchSvc.refreshBatch(this.activeBatch.batchId, true);
        });
    }

    submitBatchDocuments() {
        this._batchSvc.submitBatchDocuments().subscribe(() => {
            this._batchSvc.refreshBatch(this.activeBatch.batchId, true);
        });
    }

    /**
     * File Upload Callbacks
     */

    onFileUploadAdd({ event }) {
        this.fileUploadStatus = event.type;
    }

    onFileUploadDone({ event }) {
        this.fileUploadStatus = event.type;
        this._batchSvc.refreshBatch(this.activeBatch.batchId, true);
    }

    onFileUploadFail({ event, file }) {
        this.fileUploadStatus = event.type;
        this.failedFile = file;
    }

    submitBatchForReview() {
        this._batchSvc.submitLenderBatch(this.activeBatch.batchId).subscribe(() => {
            this._batchSvc.refreshBatch(this.activeBatch.batchId, true);
        });
    }

    getLenderDate(batch: Object, type: string) {
        batch = batch || {};
        let firstReviewLevel = (batch.reviews && batch.reviews.length) ? batch.reviews[0].currentReviewLevel : {};
        return  (type === 'dateRequested') ? firstReviewLevel.dateRequestSentToLender : firstReviewLevel.dateResponseDueFromLender;
    }
}
