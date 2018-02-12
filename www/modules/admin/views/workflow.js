// This file was generated from the component scaffold
// Copyright 2017

import {Component, Input, Output, EventEmitter} from '@angular/core';
import LrsApiV1 from '../../api/lrs.api.v1.generated';
import ModalSvc from '../../app/services/ModalSvc';
import {HqAdminGuard} from '../../app/services/AuthGuards';
import template from './workflow.html';
import styles from './workflow.less';

@Component({
    selector: 'workflow',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/api/core/Component-decorator.html
 * @example
 * <workflow name="Workflow" (change)="onChange($event)"></workflow>
 */
export default class Workflow {
    api:LrsApiV1;
    modalSvc:ModalSvc;
    _hqAdminGuard: HqAdminGuard;

    /**
     * An example input for this component
     * @see https://angular.io/docs/ts/latest/api/core/Input-var.html
     */
    @Input() name:string = 'Workflow';

    /**
     * An example output for this component
     * @see https://angular.io/docs/ts/latest/api/core/Output-var.html
     */
    @Output() change:EventEmitter = new EventEmitter();

    endorsementRange = {};
    earlyClaimRange = {};
    earlyPaymentDefaultRange = {};
    nationalQcDefaultRange = {};
    jobs = [];
    binderRequestThrottling = true;
    canRunJobs = false;

    ngOnInit() {
        this.endorsementRange.startDate = new Date();
        this.endorsementRange.endDate = new Date();
        this.endorsementRange.startDate.setDate( this.endorsementRange.startDate.getDate() - 60 );
        this.endorsementRange.endDate.setDate( this.endorsementRange.endDate.getDate() - 30 );

        this.earlyClaimRange.startDate = new Date();
        this.earlyClaimRange.endDate = new Date();
        this.earlyClaimRange.startDate.setDate( this.earlyClaimRange.startDate.getDate() - 60 );
        this.earlyClaimRange.endDate.setDate( this.earlyClaimRange.endDate.getDate() - 30 );

        this.earlyPaymentDefaultRange.startDate = new Date();
        this.earlyPaymentDefaultRange.endDate = new Date();
        this.earlyPaymentDefaultRange.startDate.setDate( this.earlyPaymentDefaultRange.startDate.getDate() - 60 );
        this.earlyPaymentDefaultRange.endDate.setDate( this.earlyPaymentDefaultRange.endDate.getDate() - 30 );

        this.nationalQcDefaultRange.startDate = new Date();
        this.nationalQcDefaultRange.endDate = new Date();
        this.nationalQcDefaultRange.startDate.setDate( this.nationalQcDefaultRange.startDate.getDate() - 60 );
        this.nationalQcDefaultRange.endDate.setDate( this.nationalQcDefaultRange.endDate.getDate() - 30 );

        this.api.resources.jobs.get().first().subscribe((jobs) => {
            this.jobs = jobs;
        });
    }

    parseDate(dateString: string): Date {
        if (dateString) {
            return new Date(dateString);
        } else {
            return null;
        }
    }
    constructor(api:LrsApiV1, modalSvc:ModalSvc, hqAdminGuard: HqAdminGuard) {
        this.api = api;
        this.modalSvc = modalSvc;
        this._hqAdminGuard = hqAdminGuard;
        this.canRunJobs = this._hqAdminGuard.canActivate();
    }

    runEndorsementSelections() {
        this.api.resources.jobs.endorsementSelection.post(this.endorsementRange).first().subscribe((job) => {
            this.modalSvc.acknowledge('Job ' + job.jobId + ' started', 'Job started').first().subscribe(() => {});
        });
    }

    runEarlyClaimSelections() {
        this.api.resources.jobs.earlyClaimSelection.post(this.earlyClaimRange).first().subscribe((job) => {
            this.modalSvc.acknowledge('Job ' + job.jobId + ' started', 'Job started').first().subscribe(() => {});
        });
    }

    runEarlyPaymentDefaultSelections() {
        this.api.resources.jobs.earlyPaymentDefaultSelection.post(this.earlyPaymentDefaultRange).first().subscribe((job) => {
            this.modalSvc.acknowledge('Job ' + job.jobId + ' started', 'Job started').first().subscribe(() => {});
        });
    }

    runNationalQcSelections() {
        this.api.resources.jobs.nationalQcSelection.post(this.nationalQcDefaultRange).first().subscribe((job) => {
            this.modalSvc.acknowledge('Job ' + job.jobId + ' started', 'Job started').first().subscribe(() => {});
        });
    }

    runSelectionAggregation() {
        this.api.resources.jobs.aggregation.post().first().subscribe((job) => {
            this.modalSvc.acknowledge('Job ' + job.jobId + ' started', 'Job started').first().subscribe(() => {});
        });
    }

    runDistribution() {
        this.api.resources.jobs.distribution.post().first().subscribe((job) => {
            this.modalSvc.acknowledge('Job ' + job.jobId + ' started', 'Job started').first().subscribe(() => {});
        });
    }

    runBinderRequest() {
        let request = this.binderRequestThrottling ?
            this.api.resources.jobs.binderRequest.throttled.post().first() :
            this.api.resources.jobs.binderRequest.post().first();

        request.subscribe((job) => {
            this.modalSvc.acknowledge('Job ' + job.jobId + ' started', 'Job started').first().subscribe(() => {});
        });
    }

    runBinderReceipt() {
        this.api.resources.jobs.binderReceipt.post().first().subscribe((job) => {
            this.modalSvc.acknowledge('Job ' + job.jobId + ' started', 'Job started').first().subscribe(() => {});
        });
    }

    runLateBinders() {
        this.api.resources.jobs.lateBinders.post().first().subscribe((job) => {
            this.modalSvc.acknowledge('Job ' + job.jobId + ' started', 'Job started').first().subscribe(() => {});
        });
    }

    runLateLenderRequests() {
        this.api.resources.jobs.lateLenderRequests.post().first().subscribe((job) => {
            this.modalSvc.acknowledge('Job ' + job.jobId + ' started', 'Job started').first().subscribe(() => {});
        });
    }

    runLenderMonitoringEmail() {
        this.api.resources.jobs.lenderMonitoringEmail.post().first().subscribe((job) => {
            this.modalSvc.acknowledge('Job ' + job.jobId + ' started', 'Job started').first().subscribe(() => {});
        });
    }

    runDailyCombinedLenderEmail() {
        this.api.resources.jobs.dailyCombinedLenderEmail.post().first().subscribe((job) => {
            this.modalSvc.acknowledge('Job ' + job.jobId + ' started', 'Job started').first().subscribe(() => {});
        });
    }

    runSendEmail() {
        this.api.resources.jobs.sendEmail.post().first().subscribe(() => {
            this.modalSvc.acknowledge('Triggered sending all pending emails. There is no job record for this, so it will not appear in the table below.', 'Job started').first().subscribe(() => {});
        });
    }

    runRefreshLenders() {
        this.api.resources.jobs.refreshLenders.post().first().subscribe(() => {
            this.modalSvc.acknowledge('Triggered refreshing lenders. There is no job record for this, so it will not appear in the table below.', 'Job started').first().subscribe(() => {});
        });
    }

    runPendingJobs() {
        this.api.resources.jobs.runPending.post().first().subscribe(() => {
            this.modalSvc.acknowledge('Running all pending jobs. There is no job record for this, so it will not appear in the table below.', 'Job started').first().subscribe(() => {});
        });
    }
}
