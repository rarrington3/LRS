// This file was generated from the component scaffold
// Copyright 2017

import { Component } from '@angular/core';
import BatchService from '../../app/services/BatchService';
import WorkloadProvider from '../services/WorkloadProvider';
import template from './WorkloadCompletedBatches.html';
import styles from './WorkloadBatches.less';
import { ActivatedRoute, Router } from '@angular/router';
import UserSvc from '../../app/services/UserSvc';
import {ModalSvc} from '../../app/services';
import DictionarySvc from '../../app/services/DictionarySvc';
import CrossFilterService from '../../app/services/CrossFilterService';
import { HqAdminGuard, AdminGuard, ReviewLocationAdminGuard } from '../../app/services/AuthGuards';
import WorkloadBatches from './WorkloadBatches';

@Component({
    selector: 'workload-completed-batches',
    template: template,
    styles: [styles]
})

export default class WorkloadCompletedBatches extends WorkloadBatches {

    constructor(batchService: BatchService, route: ActivatedRoute, router: Router, workloadProvider: WorkloadProvider, dictSvc: DictionarySvc, userSvc: UserSvc, crossFilterService: CrossFilterService, hqAdminGuard: HqAdminGuard, reviewLocationAdminGuard: ReviewLocationAdminGuard, modalSvc: ModalSvc, adminGuard: AdminGuard) {
        super(batchService, route, router, workloadProvider, dictSvc, userSvc, crossFilterService, hqAdminGuard, reviewLocationAdminGuard, modalSvc, adminGuard);
    }

    ngOnInit() {
        super.ngOnInit();

    }

    // Override
    loadAllBatches(activeBatchId: String = null, locationId: String = null) {
        this._batchSvc.getBatches(activeBatchId, locationId, true);
    }

}
