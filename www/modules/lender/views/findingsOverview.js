// This file was generated from the view scaffold
// Copyright 2016

import {Component} from '@angular/core';
import {ActivatedRoute, Router}  from '@angular/router';
import template from './findingsOverview.html';
import styles from './findingsOverview.less';
import FindingsSideBar from '../../review/components/FindingsSideBar';
import ReviewService from '../../review/services/ReviewService';
import LenderService from '../services/LenderService';
import FindingsService from '../../review/services/FindingsService';
import FindingUtils from '../../review/FindingUtils';
import GlobalStateSvc from '../../review/services/GlobalStateSvc';
import { DictionarySvc, ModalSvc } from '../../app/services';
import _ from 'lodash';
import Utils from '../../shared/Utils';
import {ResponseCoordinatorGuard} from '../../app/services/AuthGuards';

@Component({
    selector: 'findings-overview',
    template: template,
    styles: [styles]
})

/**
 * NOTE: This class extends the FindingSideBar class because FindingSideBar class contains all the functionality needed to organize findings by defect area and calculate rating and status values.
 */

/**
 * @see https://angular.io/docs/ts/latest/guide/router.html
 */
export default class FindingsOverview extends FindingsSideBar {
    review: Object;
    _lenderService: LenderService;
    severityTotals: Array = [];
    findingsRating: Object = {};
    _router: Router;
    _review: Object;
    _allSeverityCodes: Array = null;
    allowCompleteWrapup: Boolean = false;
    isLenderReviewReadOnly: Boolean = false;
    isBatchedReview = false;
    _responseCoordinatorGuard: ResponseCoordinatorGuard;
    isInitialReview: Boolean = false;
    lenderAuthorizedLevel: Object = null;
    _modalSvc: ModalSvc;

    constructor(reviewService: ReviewService, route: ActivatedRoute, lenderService: LenderService, router: Router, findingsService: FindingsService, globalStateSvc: GlobalStateSvc, dictionaryService: DictionarySvc, responseCoordinatorGuard: ResponseCoordinatorGuard, modalSvc:ModalSvc) {
        super(reviewService, route, findingsService, globalStateSvc, dictionaryService);
        this._lenderService = lenderService;
        this._router = router;
        this._responseCoordinatorGuard = responseCoordinatorGuard;
        this._modalSvc = modalSvc;
    }

    ngOnInit() {
        // Override
        this.isLender = true;
        super.ngOnInit();

        this.subscriptions.push(this.route.params.subscribe(params => {
            let reviewId = params.reviewId || this._lenderService.activeReviewId;
            this._lenderService.getReview(reviewId)
                .switchMap((review) => {
                    this._review = review;
                    this.checkForBatchedReview();
                    this.isLenderReviewReadOnly = Utils.isLenderReviewReadOnly(this._review);

                    // Lender is only allowed to see findings from the lender assigned level. Previously lender could see findings from a review level under review by FHA.
                    this.lenderAuthorizedLevel = this.getCurrentReviewLevel(review);

                    if (this.lenderAuthorizedLevel) {
                        this.findingsService.loadAllFindings(reviewId, this.lenderAuthorizedLevel.reviewLevelId, true, true, this._review.qaModelId).subscribe();

                        this.dictionaryService.getDefectAreaSeverities(this._review.qaModelId)
                            .subscribe(severities => {
                                this._allSeverityCodes = _.unique(severities, s => s.defectSeverityTierCode)
                                    .map(s => s.defectSeverityTierCode)
                                    .sort((a, b) => {
                                        return parseInt(a, 10) < parseInt(b, 10) ? -1 : 1;
                                    });

                                this.refreshView();
                            });
                    }

                    return this._lenderService.getDefectAreasByReview(review);
                })
                .subscribe(filteredDefectAreas => {
                    this.globalStateSvc.defectAreasForCurrentReview = filteredDefectAreas;
                    this.refreshView();
                });
        }));
    }

    // Override
    getCurrentReview() {
        return this._review;
    }

    // Override
    getCurrentReviewLevel(review: Object = null) {
        return (review && review.reviewId) ? Utils.findLenderAuthorizedLevel(review) : null;
    }
    _createFindingGroups() {

        // Override method.
        super._createFindingGroups();
        this.refreshView();
    }

    checkForBatchedReview() {
        this.isBatchedReview = this._review.batchId && this._review.batchId.length && this._review.reviewType.toLowerCase() !== 'operational';
    }

    refreshView() {
        if (this.findingGroups && this.findingGroups.length && this._allSeverityCodes && this._defectAreas) {

            // Inject the defect area name and calculate the number of findings(in each defect area) associated with each severity tier.
            this.findingGroups = this.findingGroups.map(g => {
                g.severityFindings = this._allSeverityCodes.map(s => {

                    // NOTE: We might have to use findings from the last level here when building the severityFindings hash.
                    // Use this.previousFindingGroups[g.name] which returns the findings by defect area.
                    let foundFindings = g.findings.filter(f => f.selectedTierCode === s);
                    let hasUnacceptableFinding = foundFindings.filter(f => f.ratingCode === 'U');
                    let cellColor = (hasUnacceptableFinding.length) ? 'danger' : (foundFindings.length) ? 'warning' : 'normal';
                    return { amount: foundFindings.length, color: cellColor, defectSeverityTierCode: s };
                });

                g.displayedName = this._defectAreas.find(defectArea => defectArea.defectAreaCode === g.name).title;
                return g;
            });

            // The total of findings in all Defect Areas associated with each severity tier.
            this.severityTotals = this._allSeverityCodes.map((s, index) => {
                let total = 0;
                let cellColors = [];
                this.findingGroups.forEach(g => {
                    cellColors.push(g.severityFindings[index].color);
                    total += g.severityFindings[index].amount;
                });

                let hasUnacceptableFinding = cellColors.filter(c => c === 'danger');
                let cellColor = (hasUnacceptableFinding.length) ? 'danger' : (total > 0) ? 'warning' : 'normal';
                return { amount: total, color: cellColor, defectSeverityTierCode: s };
            });

            let groupRatings = this.findingGroups.map(g => g.rating);

            // The status for all findings in this review. Sort by rank order.
            this.findingsRating = groupRatings.sort((a, b) => {
                return parseInt(a.rankOrder, 10) < parseInt(b.rankOrder, 10) ? -1 : 1;
            })[0];

            this.allowCompleteWrapup = (this._findings.every(f => FindingUtils.isFindingRespondedByLender(f)) && this._responseCoordinatorGuard.canActivate());
        }
    }

    getRatingForAllDefectAreas() {
        let description = this.findingsRating.description || '';
        return description.toUpperCase();
    }

    addressFindings() {

        // Sort all finding groups by rank order
        let sortedGroups = this.findingGroups.sort((a, b) => {
            return parseInt(a.rank, 10) < parseInt(b.rank, 10) ? -1 : 1;
        });

        // Navigate to the finding with the lowest rank.
        let targetFinding = sortedGroups[0].findings[0];
        this._router.navigate([`/lender/activeReviews/review/${this._review.reviewId}/finding/${targetFinding.findingId}/reviewResponse`]);
    }

    completeWrapup() {
        this._modalSvc.askForConfirmation(
            `<p>You are about to submit this review level${this.findingsRating.description ? ' with a rating of ' + this.findingsRating.description.toLowerCase() : ''}.</p><p>Are you sure you want to continue?</p>`,
            'Confirm Review Level Submit', 'Confirm', 'Cancel')
            .first().subscribe(() => {
                this.reviewService.submitLenderReviewWrapUp(this._review.reviewId, this._review.currentReviewLevel.reviewLevelId);
            },
            () => {});
    }

    ngOnDestroy() {
        super.ngOnDestroy();
    }
}
