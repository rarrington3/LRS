// This file was generated from the component scaffold
// Copyright 2016

import { Component } from '@angular/core';

import BatchService from '../../app/services/BatchService';
import template from './lenderBatchesSidebar.html';
import styles from '../../shared/components/FilterSidebar.less';
import SortableReviewTable from '../../shared/components/SortableReviewTable';

@Component({
    selector: 'lender-batches-sidebar',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/api/core/Component-decorator.html
 * @example
 * <lender-batches-sidebar name="LenderBatchesSidebar" (change)="onChange($event)"></lender-batches-sidebar>
 * NOTE: extended by the LenderCompletedBatchesSidebar class.
 */
export default class LenderBatchesSidebar  {
    _batchSvc: BatchService;
    _subscriptions = [];
    lenderBatchCollection: Array;
    activeBatch: Object;
    activeCategory: String;

    constructor(batchSvc: BatchService) {
        this._batchSvc = batchSvc;
    }

    ngOnInit() {
        this._subscriptions.push(this._batchSvc.lenderBatchCollectionObservable.subscribe((change) => {
            this.lenderBatchCollection = change.currentValue;
        }));

        this._subscriptions.push(this._batchSvc.activeLenderBatchObservable.subscribe((change) => {
            this.activeBatch = change.currentValue;
            this._updateBatchesAfterRefresh();
        }));

        this.activeBatch = this._batchSvc.activeLenderBatch;

        let sortedBatches = this._batchSvc.lenderBatchCollection || [];
        this.lenderBatchCollection = SortableReviewTable.sortItems(sortedBatches, 'Creation Date', 'creationDate', SortableReviewTable.ASCENDING);
    }

    getBatchInfo(batch) {
        this._batchSvc.setActiveBatch(batch, true);
        this.activeCategory = '';
    }

    _updateBatchesAfterRefresh() {
        if (this.activeBatch && this.lenderBatchCollection) {
            // Update the batches collection.
            let activeBatchIndex = this.lenderBatchCollection.findIndex(b => b.batchId === this.activeBatch.batchId);
            if (activeBatchIndex >= 0) {
                this.lenderBatchCollection[activeBatchIndex] = this.activeBatch;
            }

        }
    }

    ngOnDestroy() {
        this._subscriptions.forEach((subscription) => {
            if (subscription.unsubscribe) {
                subscription.unsubscribe();
            }
        });
    }
}
