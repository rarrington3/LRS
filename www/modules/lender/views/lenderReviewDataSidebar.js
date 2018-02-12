// This file was generated from the view scaffold
// Copyright 2016

import {Component} from '@angular/core';
import {Router, ActivatedRoute} from '@angular/router';
import ReviewService from '../../review/services/ReviewService';
import ReviewDataSidebarBase from '../../shared/components/ReviewDataSidebarBase';
import template from './lenderReviewDataSidebar.html';
import styles from './lenderReviewDataSidebar.less';
import LenderService from '../services/LenderService';
import Client from '../../api/lrs.api.v1.generated';
import {SERVER_ERROR} from '../../shared/constants';
import {Observable} from 'rxjs';
@Component({
    selector: 'lender-review-data-sidebar',
    template: template,
    styles: [styles]
})

export default class LenderReviewDataSidebar extends ReviewDataSidebarBase {

    _lenderService:LenderService;

    defectAreas:Array = [];
    lenderSelfReport:Object = null;
    fraudTypes:Array = [];
    fraudParticipants:Array = [];

    constructor(route:ActivatedRoute, reviewService:ReviewService, router:Router, lenderService:LenderService, client:Client) {
        super(route, reviewService, router, client);
        this._lenderService = lenderService;
    }

    // Override
    refreshCurrentReview() {
        this.resetSubscriptions();
        this._subscriptions.push(this.route.params.subscribe(params => {
            let reviewId = params.reviewId || this._lenderService.activeReviewId;

            // Manually load the Review object based on the route's review id.
            this._lenderService.getReview(reviewId).subscribe(review => {
                if (review) {
                    this.currentReview = review;

                    this.getReviewLocations().subscribe(result => {
                        this._locations = result || [];
                        let reviewLocationId =  this.currentReview.currentReviewLevel.reviewLocationId || null;
                        let reviewLocation =  this._locations.find(l => l.locationId === reviewLocationId);
                        let reviewLevel = this.currentReview.currentReviewLevel.type;
                        if (this.currentReview.currentReviewLevel.type !== 'Initial') {
                            reviewLevel += ' ' + this.currentReview.currentReviewLevel.iteration;
                        }
                        this.displayedReviewFields = [
                            {name: 'Review ID', values: this.currentReview.reviewReferenceId},
                            {name: 'Selection Reason', values: this.currentReview.selectionReason},
                            {name: 'Review Type', values: this.currentReview.reviewType},
                            {name: 'Review Scope', values: this.currentReview.scope},
                            {name: 'Review Level', values: reviewLevel},
                            {name: 'Review Location', values: (reviewLocation) ? reviewLocation.name : ''}
                        ];

                        // Go ahead and trigger the loading of all the active reviews and build the cases in batch data since the first tab in lender/activeReviews/summary is the 'Review Data' tab.
                        this.selectedTab = 'reviewData';
                    });

                    // fetch the self report for the current view (if available)
                    if (this.currentReview.selectionRequestId) {
                        Observable.forkJoin(
                            this._client.resources.dictionary.fraudTypes.get().first().share(),
                            this._client.resources.dictionary.fraudParticipants.get().first().share(),
                            this._client.resources.lenderSelfReport.selectionRequestId(this.currentReview.selectionRequestId).get().first(),
                            this._lenderService.getDefectAreasByReview(review).share()
                        ).subscribe(([fraudTypes, fraudParticipants, lenderSelfReport, defectAreas]) => {
                            this.fraudTypes = fraudTypes;
                            this.fraudParticipants = fraudParticipants;
                            this.defectAreas = defectAreas;
                            this.lenderSelfReport = lenderSelfReport;
                        }, response => {
                            if (response.status === SERVER_ERROR.GONE) {
                                // Suck up the 410, because this review doesn't have a Lender Self Report
                                console.log('No Lender Self Report for Review: ' + this.currentReview.caseNumber + ' (' + this.currentReview.reviewId + ')');
                            }
                            else {
                                throw new Error(response);
                            }
                        });
                    }
                }
            });
        }));
    }

    // Override
    getBatchById() {
        return this._reviewService.getBatchById(this.currentReview.batchId, true);
    }

    // Override
    onGoToBatchSummaryClick() {
        this.router.navigate(['/lender/activeReviews/batches']); // `/lender/batch/${this.currentReview.batchId}`
    }

    // Override
    onCaseNumberSelect(reviewId:String) {
        this.router.navigate([`/lender/activeReviews/review/${reviewId}`]);
    }

    // Override
    ngOnDestroy() {
        super.ngOnDestroy();
    }

    /**
     * @param {String} id of defect to find title for
     * @returns {String} title returned based on id
     * */
    findDefectArea(id) {
        let defectArea = this.defectAreas.find(item => item.defectAreaId === id);
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
