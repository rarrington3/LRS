// This file was generated from the component scaffold
// Copyright 2017

import {Component} from '@angular/core';
import { Router } from '@angular/router';
import template from './LenderCompletedBatches.html';
import styles from './LenderBatchesWorkload.less';
import LenderBatchesWorkload from './LenderBatchesWorkload';
import BatchService from '../../app/services/BatchService';

@Component({
    selector: 'lender-completed-batches',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/api/core/Component-decorator.html
 * @example
 * <lender-completed-batches name="LenderCompletedBatches" (change)="onChange($event)"></lender-completed-batches>
 */
export default class LenderCompletedBatches extends LenderBatchesWorkload {

    constructor(batchSvc: BatchService, router: Router) {
        super(batchSvc, router);
        this.isLender = true;
    }

    //Override
    getLenderBatches() {
        this._batchSvc.getLenderBatches(true);
    }
}
