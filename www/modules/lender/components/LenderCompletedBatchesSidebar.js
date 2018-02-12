// This file was generated from the component scaffold
// Copyright 2017

import {Component} from '@angular/core';
import template from './lenderBatchesSidebar.html';
import styles from '../../shared/components/FilterSidebar.less';
import LenderBatchesSidebar from './lenderBatchesSidebar';
import BatchService from '../../app/services/BatchService';

@Component({
    selector: 'lender-completed-batches-sidebar',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/api/core/Component-decorator.html
 * @example
 * <lender-completed-batches-sidebar name="LenderCompletedBatchesSidebar" (change)="onChange($event)"></lender-completed-batches-sidebar>
 */
export default class LenderCompletedBatchesSidebar  extends LenderBatchesSidebar {

    constructor(batchSvc: BatchService) {
        super(batchSvc);
    }

}
