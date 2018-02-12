// This file was generated from the component scaffold
// Copyright 2016

import {Component} from '@angular/core';
import {Router, ActivatedRoute} from '@angular/router';
import template from './ReviewDataSidebar.html';
import styles from './ReviewDataSidebar.less';
import ReviewService from '../services/ReviewService';
import GlobalStateSvc from '../services/GlobalStateSvc';
import ReviewDataSidebarBase from '../../shared/components/ReviewDataSidebarBase';
import DatePipe from '../../shared/pipes/DatePipe';
import {SERVER_ERROR} from '../../shared/constants';
import Utils from '../../shared/Utils';
import Client from '../../api/lrs.api.v1.generated';
let preloader = require('../../preloader/main');

export const LOAN_REVIEW_EXTERNAL_URL_TYPE = {
    E_CASE_BINDER: 'electronicCaseBinder',
    E_APPRAISAL: 'electronicAppraisal',
    TOTAL_SCORE_EMULATOR: 'totalScorecardEmulator',
    TOTAL_SCORE_EMULATOR_COMPARISON: 'totalScorecardEmulatorComparison'
};

@Component({
    selector: 'review-data-sidebar',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/api/core/Component-decorator.html
 * @example
 * <review-data-sidebar name="ReviewDataSidebar" (change)="onChange($event)"></review-data-sidebar>
 */
export default class ReviewDataSidebar extends ReviewDataSidebarBase {
    _globalStateSvc: GlobalStateSvc;

    /**
     * @type {Object} lenderSelfReport for self report detail display
     * */
    lenderSelfReport:Object = null;

    /**
     * @type {Array} defectAreas are a collection of defect areas for self report detail display
     * */
    defectAreas:Array = [];

    /**
     * @type {Array} fraudTypes are a collection of fraud types for self report detail display
     * */
    fraudTypes:Array = [];

    /**
     * @type {Array} fraudParticipants are a collection of fraud participants for self report detail display
     * */
    fraudParticipants:Array = [];

    constructor(route:ActivatedRoute, reviewService:ReviewService, router:Router, globalState:GlobalStateSvc, client:Client) {
        super(route, reviewService, router, client);
        this._globalStateSvc = globalState;
    }

    // Override for ngInit()
    refreshCurrentReview() {
        this.resetSubscriptions();

        this._subscriptions.push(this._reviewService.currentReviewObservable.subscribe( (change) => {
            this.currentReview = change.currentValue;

            this.getReviewLocations().subscribe(result => {
                this._locations = result || [];
                let reviewLocationId =  this.currentReview.currentReviewLevel.reviewLocationId || null;
                let reviewLocation =  this._locations.find(l => l.locationId === reviewLocationId);
                let levelTypePrefix = '';
                if (Utils.isReviewVetted(this.currentReview)) {
                    levelTypePrefix = 'Vetted - ';
                } else if (Utils.isReviewVetting(this.currentReview)) {
                    levelTypePrefix = 'Vetting - ';
                }

                let reviewLevel = levelTypePrefix + this.currentReview.currentReviewLevel.type;
                if (this.currentReview.currentReviewLevel.type !== 'Initial') {
                    reviewLevel += ' ' + this.currentReview.currentReviewLevel.iteration;
                }

                let datePipe = new DatePipe();

                this.displayedReviewFields = [
                    {name: 'Review ID', values: this.currentReview.reviewReferenceId},
                    {name: 'Selection Reason', values: this.currentReview.selectionReason},
                    {name: 'Selected Date', values: datePipe.transform(this.currentReview.selectedDate)},
                    {name: 'Binder Requested', values: this.currentReview.binderRequestDate ? datePipe.transform(this.currentReview.binderRequestDate) : 'N/A'},
                    {name: 'Binder Received', values: this.currentReview.binderRequestDate ? datePipe.transform(this.currentReview.binderReceivedDate) : 'N/A'},
                    {name: 'Review Type', values: this.currentReview.reviewType},
                    {name: 'Review Scope', values: this.currentReview.scope},
                    {name: 'Review Level', values: reviewLevel },
                    {name: 'Review Location', values: (reviewLocation) ? reviewLocation.name : ''},
                    {name: 'Review Status', values: this.currentReview.status}
                ];
            });

            // fetch the self report for the current view (if available)
            if (this.currentReview.selectionRequestId) {
                this._client.resources.lenderSelfReport.selectionRequestId(this.currentReview.selectionRequestId).get().first().subscribe(
                    result => {
                        this.lenderSelfReport = result;
                    },
                    response => {
                        if (response.status === SERVER_ERROR.GONE) {
                            // Suck up the 410, because this review doesn't have a Lender Self Report
                            console.log('No Lender Self Report for Review: ' + this.currentReview.caseNumber + ' (' + this.currentReview.reviewId + ')');
                        }
                        else {
                            throw new Error(response);
                        }
                    }
                );
            }

        }));

        // collection of dictionaries for Self Report related display on Review Data tab of side bar
        this._client.resources.dictionary.fraudTypes.get().first().subscribe(result => this.fraudTypes = result);
        this._client.resources.dictionary.fraudParticipants.get().first().subscribe(result => this.fraudParticipants = result);

    }

    // Override
    onGoToBatchSummaryClick() {
        this.router.navigate([`/workload/batches/location/${this.currentReview.currentReviewLevel.reviewLocationId}/batch/${this.currentReview.batchId}`]);
    }

    // Override
    onCaseNumberSelect(reviewId:String) {
        this.router.navigate([`/review/${reviewId}`]);
    }

    // Override
    ngOnDestroy() {
        super.ngOnDestroy();
    }

    openExternalURL(type: String) {
        let uiConfig = preloader.uiConfig || {};
        let key = LOAN_REVIEW_EXTERNAL_URL_TYPE[type];
        let url = uiConfig[key];
        let caseNumber = this._reviewService.currentReview.caseNumber.replace('-', ''); // External links do not work with hyphens
        if (url && url.length) {
            url = url.replace('{caseNumber}', caseNumber);
            window.open(url, '_blank');
        } else {
            console.log(`openExternalURL - constant ${type} is invalid or it does not exist in the returned config object.`);
        }
    }

    /**
     * @param {String} id of defect to find title for
     * @returns {String} title returned based on id
     * */
    findDefectArea(id) {
        let defectArea = this._globalStateSvc.defectAreasForCurrentReview.find(item => item.defectAreaId === id);
        return defectArea ? defectArea.title + ' (' + defectArea.defectAreaCode + ')' : '';
    }

    /**
     * @param {String} code of fraud type to find description for
     * @returns {String} description returned based on id
     * */
    findFraudType(code) {
        let fraudType = this.fraudTypes.find(item => item.code === code);
        return fraudType ? fraudType.description : '';
    }

    /**
     * @param {String} code of fraud participant to find description for
     * @returns {String} description returned based on id
     * */
    findFraudParticipant(code) {
        let fraudParticipant = this.fraudParticipants.find(item => item.code === code);
        return fraudParticipant ? fraudParticipant.description : '';
    }
}
