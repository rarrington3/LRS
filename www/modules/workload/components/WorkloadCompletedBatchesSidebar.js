// This file was generated from the component scaffold
// Copyright 2017

import { Component } from '@angular/core';
import { Router } from '@angular/router';
import template from './WorkloadBatchesSidebar.html';
import styles from './WorkloadBatchesSidebar.less';
import BatchService from '../../app/services/BatchService';
import Client from '../../api/lrs.api.v1.generated';
import { HqAdminGuard, ReviewerGuard, ReviewLocationAdminGuard } from '../../app/services/AuthGuards';
import {UserSvc} from '../../app/services';
import WorkloadBatchesSidebar from './WorkloadBatchesSidebar';

@Component({
    selector: 'workload-completed-batches-sidebar',
    template: template,
    styles: [styles]
})

export default class WorkloadCompletedBatchesSidebar extends WorkloadBatchesSidebar {

    constructor(batchService: BatchService, client: Client, router: Router, hqAdminGuard: HqAdminGuard, reviewerGuard: ReviewerGuard, reviewLocationAdminGuard: ReviewLocationAdminGuard, userSvc: UserSvc) {
        super(batchService, client, router, hqAdminGuard, reviewerGuard, reviewLocationAdminGuard, userSvc);
        this.batchesLocationRoute = '/reviews/complete/batches/location/';
        this.batchesRoute = 'reviews/complete/batches/';
        this._batchSvc.resetData();


    }

}
