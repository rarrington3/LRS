// This file was generated from the component scaffold
// Copyright 2016
import { Component } from '@angular/core';
import template from './ReviewWrapUp.html';
import styles from './ReviewWrapUp.less';
import FindingsService from '../services/FindingsService';
import ReviewService from '../services/ReviewService';
import GlobalStateSvc from '../services/GlobalStateSvc';
import { RATINGS, INDEMNIFICATION_TYPES } from '../constants';
import { DictionarySvc, ModalSvc } from '../../app/services';
import Utils from '../../shared/Utils';
import VettingService from '../services/VettingService';
import FindingUtils, { REVIEWER_RESPONSE_TYPE } from '../FindingUtils';
import { Router } from '@angular/router';
import ArrayUtils from '../../shared/utils/ArrayUtils';
import _ from 'lodash';
import QCService from '../services/QCService';

@Component({
    selector: 'review-wrap-up',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/api/core/Component-decorator.html
 * @example
 * <review-wrap-up name='ReviewWrapUp' (change)='onChange($event)'></review-wrap-up>
 */
export default class ReviewWrapUp {
    selectedIndemTerm: String = '';

    findingsLoaded = false;
    _dictionaryService: DictionarySvc;
    _modalSvc: ModalSvc;

    // The _currentLevelFindings are vetting changes if the review is vetted.
    currentReviewLevelRating: Object = null;

    // vetteee or qc original review initial level rating.
    originalReviewLevelRating: Object = null;
    _allRatings: Array = null;
    findingsInfo: Array = null;

    showAddAdHocFinding: Boolean = false;
    addAdhocFindingModal: Object = { isOpen: false, defectAreas: [], selectedDefectAreaId: null };
    _router: Router;

    displayFindingOutcomes: Boolean = false;
    responseTypeTotals: Object = { uTotal: 0, mTotal: 0, rTotal: 0, aTotal: 0 };

    // Previous findings grouped by defectAreaCode
    _previousFindingGroups: Object = null;

    _subscriptions: Array = [];
    viewTitle: String = null;
    submitTitle: String = null;
    isVetted: Boolean = false;
    _vettingService: VettingService;
    vettingChangesTotal: Number = 0;

    indemnificationTypes: Array = [];
    allowIndemTermSelection: Boolean = false;

    _qcService: QCService = null;
    totalQCchanges: Number = 0;
    isQC: Boolean = false;
    selectedQCoutcome: String = null;

    previousRatingLabel:String = 'PREVIOUS RATING';
    updatedRatingLabel:String = 'UPDATED RATING';
    showQCoutcome: Boolean = false;

    constructor(findingSvc: FindingsService, globalStateSvc: GlobalStateSvc, reviewService: ReviewService, dictionarySvc: DictionarySvc, router: Router, vettingService: VettingService, qcService: QCService, modalSvc:ModalSvc) {
        this._findingSvc = findingSvc;
        this.globalStateService = globalStateSvc;
        this._reviewService = reviewService;
        this._dictionaryService = dictionarySvc;
        this._vettingService = vettingService;
        this._router = router;
        this._qcService = qcService;
        this._modalSvc = modalSvc;

        this.indemnificationTypes = ArrayUtils.toArray(INDEMNIFICATION_TYPES);
    }

    ngOnInit() {
        this._resetSubscriptions();
        //Is the current review level 'Pending Batch Review'
        this.pendingBatchReview = false;

        // We need to subscibe to the  defectAreasCompletedObservable when deep linking or refreshing the browser.
        this._subscriptions.push(this._reviewService.defectAreasCompletedObservable.subscribe((change) => {
            if (change.currentValue) {
                this._checkDefects();
            }
        }));
        this._subscriptions.push(this._findingSvc.findingsTotalsObservable.subscribe((change) => {
            this.findingsLoaded = false;
            this.findingsInfo = change.currentValue;
            this.createTotals();
            this._loadPreviousFindings();
        }));

        this._dictionaryService.getFindingRatingCodes().subscribe(codes => {
            this._allRatings = codes;
            this._updateReviewRating();
            this._addQCchanges();
        });

        this._subscriptions.push(this._reviewService.currentReviewObservable.subscribe(() => {
            this._setDisplayFindingOutcomes();
            this._setIndemType();

            // Load previous findings so we can check for the 'Adjusted' status in a finding when checking wheter a finding has been responded by the reviewer in mitigation.
            let review = this._reviewService.currentReview;
            if (review && review.currentReviewLevel.type.toLowerCase() !== 'initial') {

                // Load previous findings so we can set the 'Adjusted' status in a finding.
                this._loadPreviousFindings();

            } else {
                this._checkDefects();
            }
        }));

        // Check review wrap-up again when a finding has been updated.
        this._subscriptions.push(this._findingSvc.currentFindingsObservable.subscribe((data) => {
            if (data.currentValue) {
                this._checkDefects();
            }
        }));

        this._setDisplayFindingOutcomes();
        this.createTotals();
        this._checkDefects();
        this._loadPreviousFindings();
        this._updateReviewRating();
        this._setIndemType();
    }

    _setIndemType() {
        if (this._reviewService.currentReview) {

            // Set indemnificationTypeCode which would have been saved from the last review level
            this.selectedIndemTerm = this._reviewService.currentReview.currentReviewLevel.indemnificationTypeCode;
            this.selectedQCoutcome = this._reviewService.currentReview.currentReviewLevel.qcOutcomeCode || null;
        }
    }

    _checkForRespondedFindingInMitigation() {
        if (!this._reviewService.currentReview || !this._previousFindingGroups || !this._findingSvc.currentFindings) {
            return true;
        }

        return this._findingSvc.currentFindings.every(f => FindingUtils.isFindingRespondedByReviewer(f, this._previousFindingGroups, this._reviewService.currentReview));
    }

    _checkDefects() {
        if (this._reviewService.currentReview && this._reviewService.currentReview.currentReviewLevel && this._reviewService.currentReview.currentReviewLevel.status.toLowerCase() === 'pending batch review') {
            this.pendingBatchReview = true;
        }
        else {
            this.pendingBatchReview = false;
        }
        let isEditable = this._reviewService.currentReview && !Utils.isReviewReadOnly(this._reviewService.currentReview);
        let isInitial = Utils.isInitialReviewAndNotCompleted(this._reviewService.currentReview);
        if (isEditable && isInitial && this._reviewService.defectAreasCompleted && this._reviewService.checkDefects()) {
            this.completeWrapUp = true;
        }
        else if (isEditable && !isInitial && this._checkForRespondedFindingInMitigation()) {
            this.completeWrapUp = true;
        }
        else {
            // Vetted review exception
            this.isVetted = Utils.isReviewVetted(this._reviewService.currentReview);
            this.completeWrapUp = this.isVetted;
        }

        if (this._reviewService.currentReview) {
            this.viewTitle = (this.isVetted) ? 'VETTING OVERVIEW' : 'REVIEW LEVEL WRAP-UP';
            this.submitTitle = (this.isVetted) ? 'Acknowledge' : 'Complete Wrap-Up';
            this.allowIndemTermSelection = (!Utils.isReviewLevelOperational(this._reviewService.currentReview) && !this.isVetted && !this.isReviewConformedOrDeficient());

            // QC
            this.isQC = Utils.isQCmitigatedReview(this._reviewService.currentReview);
            let review = this._reviewService.currentReview;
            this.showQCoutcome = Utils.isQCreview(review) && !Utils.isInitialReview(review);
        }
    }

    createTotals() {
        let severityTotals = [];
        let findingsTotal = 0;
        this.findingsInfo = this._findingSvc.findingsTotals;

        /**
         * In a post initial level, the _previousFindingGroups collection is required
         * to calculate the severity tier heat map since we have to use the findings from the last competed level.
         */

        if (this.findingsInfo && this._reviewService.currentReview && (this._reviewService.currentReview.currentReviewLevel.type.toLowerCase() === 'initial' || (this._reviewService.currentReview.currentReviewLevel.type.toLowerCase() !== 'initial' && this._previousFindingGroups))) {

            this.findingsInfo.forEach((defectArea) => {
                findingsTotal += defectArea.totalFindings;

                defectArea.severityFindings.forEach((severity) => {

                    let severityObj = severityTotals.find((totals) => {
                        return totals.tierCode === severity.defectSeverityTierCode;
                    });
                    if (!severityObj) {
                        severityObj = {
                            'amount': 0,
                            'tierCode': severity.defectSeverityTierCode,
                            'rating': RATINGS.CONFORMING.code
                        };
                        severityTotals.push(severityObj);
                    }
                    if (severity.amount) {
                        severityObj.amount += severity.amount;
                        if (severity.rating !== RATINGS.CONFORMING.code && severityObj.rating !== RATINGS.UNACCEPTABLE.code) {
                            severityObj.rating = severity.rating;
                        }
                    }
                });
            });
            severityTotals.sort(function (a, b) {
                return (a.tierCode) - (b.tierCode);
            });
            this.findingsTotal = findingsTotal;
            this.severityTotals = severityTotals;
            this._updateReviewRating();

            this.findingsLoaded = true;
            this.showAddAdHocFinding = !Utils.isReviewReadOnly(this._reviewService.currentReview) && FindingUtils.allowPostInitialFinding(this._reviewService.currentReview);
            this.addAdhocFindingModal.isOpen = false;
        }
    }

    ratingColor(rating) {
        if (rating.amount > 0) {
            if (rating.rating === RATINGS.UNACCEPTABLE.code || rating.rating === RATINGS.MITIGATED.code) {
                return RATINGS.UNACCEPTABLE.description.toLowerCase();
            } else if (rating.rating === RATINGS.DEFICIENT.code) {
                return RATINGS.DEFICIENT.description.toLowerCase();
            } else {
                return RATINGS.CONFORMING.description.toLowerCase();
            }
        } else {
            return RATINGS.CONFORMING.description.toLowerCase();
        }
    }

    submitWrapUp() {
        if (this.isVetted) {
            let reviewId = this.globalStateService.currentReviewId,
                reviewLevelId = this.globalStateService.currentReviewLevelId;
            this._vettingService.confirmVetting(reviewId, reviewLevelId);
        } else {

            this._modalSvc.askForConfirmation(
                `<p>You are about to submit this review level${this.currentReviewLevelRating ? ' with a rating of ' + this.currentReviewLevelRating.description.toLowerCase() : ''}.</p><p>Are you sure you want to continue?</p>`,
                'Confirm Review Level Submit', 'Confirm', 'Cancel')
                .first().subscribe(() => {
                    if (this.allowIndemTermSelection || this.showQCoutcome) {
                        let reviewLevelSubmit = {};

                        if (this.allowIndemTermSelection) {
                            reviewLevelSubmit.indemnificationTypeCode = this.selectedIndemTerm;
                        }

                        if (this.showQCoutcome) {
                            reviewLevelSubmit.qcOutcomeCd = this.selectedQCoutcome;
                        }

                        this._reviewService.submitReviewWrapUp(reviewLevelSubmit);
                    }
                    else {
                        this._reviewService.submitReviewWrapUp();
                    }
                },
                () => {});
        }
    }

    saveIndemType() {
        this._reviewService.saveIndemType({ indemnificationTypeCode: this.selectedIndemTerm });
    }

    totalsColor(severity) {
        if (severity.amount > 0) {
            if (severity.rating === RATINGS.UNACCEPTABLE.code) {
                return RATINGS.UNACCEPTABLE.description.toLowerCase();
            } else if (severity.rating === RATINGS.DEFICIENT.code) {
                return RATINGS.DEFICIENT.description.toLowerCase();
            } else if (severity.rating === RATINGS.MITIGATED.code) {
                return RATINGS.MITIGATED.description.toLowerCase();
            } else {
                return RATINGS.CONFORMING.description.toLowerCase();
            }
        } else {
            return RATINGS.CONFORMING.description.toLowerCase();
        }
    }

    addIcon(rating) {
        if (rating === RATINGS.UNACCEPTABLE.description) {
            return 'glyphicon glyphicon-warning-sign unacceptable-icon';
        } else if (rating === RATINGS.DEFICIENT.description) {
            return 'glyphicon glyphicon-warning-sign deficient-icon';
        } else {
            return 'glyphicon glyphicon-ok-circle conforming-icon';
        }
    }

    _updateReviewRating() {
        if (this._allRatings && this._allRatings.length && this._reviewService.currentReview) {
            this.currentReviewLevelRating = this._allRatings.find(c => c.ratingCode === this._reviewService.currentReview.currentReviewLevel.ratingCode);
            let completedLevels = this._reviewService.currentReview.completedReviewLevels;
            this.isVetted = Utils.isReviewVetted(this._reviewService.currentReview);

            // Remove the 'current review level' if it exists in review.completedReviewLevels
            completedLevels = completedLevels.filter((l => l.reviewLevelId !== this._reviewService.currentReview.currentReviewLevel.reviewLevelId));
            if (this.isVetted) {
                let rating;
                if (completedLevels.length) {
                    let previousVetteeLevel = _.last(completedLevels);
                    rating = this._allRatings.find(c => c.ratingCode === previousVetteeLevel.ratingCode);
                } else {
                    this._allRatings.find(c => c.ratingCode === RATINGS.CONFORMING.code);
                }

                this.originalReviewLevelRating = rating;
            }
        }
    }

    _resetSubscriptions() {
        this._subscriptions.forEach((subscription) => {
            if (subscription.unsubscribe) {
                subscription.unsubscribe();
            }
        });
    }

    addAdHocFinding() {

        // Defect areas should have already been loaded at this point.
        this.addAdhocFindingModal.defectAreas = this.globalStateService.defectAreasForCurrentReview;
        if (this.addAdhocFindingModal.defectAreas.length) {
            this.addAdhocFindingModal.selectedDefectAreaId = this.addAdhocFindingModal.defectAreas[0].defectAreaId;
        }

        this.addAdhocFindingModal.isOpen = true;
    }

    navigateToAddAdhocFinding() {
        if (this.addAdhocFindingModal.selectedDefectAreaId) {
            let defectArea = this.addAdhocFindingModal.defectAreas.find(da => da.defectAreaId === this.addAdhocFindingModal.selectedDefectAreaId);
            this._router.navigate([`/review/${this.globalStateService.currentReviewId}/tree/${defectArea.defectAreaCode}/sourcecause`]);
        }
    }

    showWrapUp() {
        this._reviewService.displayWrapUp();
    }

    ngOnDestroy() {
        this._resetSubscriptions();
    }

    _loadPreviousFindings() {

        // Load the previous findings so we can set the 'Adjusted' status in the Finding Outcomes columns and compute data for the severity tier heat map.
        if (this._reviewService.currentReview && this.findingsInfo && (Utils.isReviewVetted(this._reviewService.currentReview) || this.displayFindingOutcomes)) {

            this._findingSvc.loadFindingsForAllCompletedLevels(this._reviewService.currentReview, null, true).subscribe((previousFindings) => {
                this._previousFindingGroups = FindingUtils.getFindingGroupsFromPreviousLevel(this._reviewService.currentReview, previousFindings, true);
                if (!Utils.isInitialReviewAndNotCompleted(this._reviewService.currentReview)) {
                    this._updateFindingInfoSeverity(this.findingsInfo, this._previousFindingGroups);

                    // Call createTotals again because it needs the _previousFindingGroups in a post initial level.
                    this.createTotals();
                    this._checkDefects();
                    this._addFindingOutcomes();
                }

                // Always calculate vetting changes even in initial .
                this._addVettingChanges();

                // Load the original review's findings if this is a qc review at MIT 1.
                if (this.isQC) {
                    this.updatedRatingLabel = 'QC RATING';
                    this.previousRatingLabel = 'ORIGINAL RATING';

                    this._qcService.getOriginalReview(this._reviewService.currentReview.originalQcReviewId).subscribe((review) => {
                        this._qcService.getOriginalInitialFindings(review).subscribe( (/*initialFindings*/) => {
                            this._addQCchanges();
                        });
                    });
                }
            });
        }
    }

    _addFindingOutcomes() {

        if (this.displayFindingOutcomes && this.findingsInfo && this._reviewService.currentReview && this._findingSvc.currentFindings && this._previousFindingGroups) {
            this.responseTypeTotals = { uTotal: 0, mTotal: 0, rTotal: 0, aTotal: 0 };

            // Add all the reviewer finding response types for binding.
            this.findingsInfo.forEach((defectArea) => {
                defectArea.adjustedFindings = 0;
                defectArea.remediatedFindings = 0;
                defectArea.mitigatedFindings = 0;
                defectArea.unaceptableFindings = 0;
            });

            // Calculate the total findings for each reviewer response type.
            this._findingSvc.currentFindings.forEach((f) => {
                let defectAreaGroup = this.findingsInfo.find(fi => fi.defectAreaCode === f.defectAreaCode);

                if (defectAreaGroup) {
                    let type = FindingUtils.getReviewerFindingResponseAction(f, this._previousFindingGroups) || {};
                    if (f.isAdhoc || (!type.code && f.ratingCode === RATINGS.UNACCEPTABLE.code)) {
                        type = RATINGS.UNACCEPTABLE;
                    }

                    switch (type.code) {
                        case RATINGS.MITIGATED.code:
                            defectAreaGroup.mitigatedFindings++;
                            this.responseTypeTotals.mTotal++;
                            break;
                        case RATINGS.REMEDIATED.code:
                            defectAreaGroup.remediatedFindings += 1;
                            this.responseTypeTotals.rTotal++;
                            break;
                        case REVIEWER_RESPONSE_TYPE.ADJUSTED.code:
                            defectAreaGroup.adjustedFindings++;
                            this.responseTypeTotals.aTotal++;
                            break;
                        case RATINGS.UNACCEPTABLE.code:
                        case REVIEWER_RESPONSE_TYPE.NOT_MITIGATED.code:
                            defectAreaGroup.unaceptableFindings++;
                            this.responseTypeTotals.uTotal++;
                            break;
                        default:
                        // Do nothing.
                    }
                } else {
                    console.log(`ReviewWrapUp Finding Outcomes - a finding, ${f.findingId}, with orphaned defect area, ${f.defectAreaCode}, is found!`);
                }
            });
        }
    }

    _addVettingChanges() {
        if (Utils.isReviewVetted(this._reviewService.currentReview) && this.findingsInfo && this._reviewService.currentReview && this._findingSvc.currentFindings && this._previousFindingGroups) {
            this.vettingChangesTotal = 0;

            this.findingsInfo.forEach((findingInfo) => {
                let defectAreaCode = findingInfo.defectAreaCode;
                let vetterFindings = this._findingSvc.currentFindings.filter(f => f.defectAreaCode === defectAreaCode);
                let vetteeFindings = this._previousFindingGroups[defectAreaCode] || [];

                findingInfo.vettingChange = VettingService.calculateVettingChanges(vetteeFindings, vetterFindings).totalChanges || 0;
                this.vettingChangesTotal += findingInfo.vettingChange;
            });
        }
    }

    _updateFindingInfoSeverity(findingGroupInfo: Array = [], previousFindingGroup: Object = {}) {

        // Update the severity tier data in the heat map using findings from the last completed level.
        findingGroupInfo.forEach((group) => {
            let findingsByDefectAreaCode = previousFindingGroup[group.defectAreaCode] || [];
            group.severityFindings = FindingUtils.computeFindingSeverityForDefectArea({ defectAreaCode: group.defectAreaCode }, findingsByDefectAreaCode, this._findingSvc.defectAreaSeverities, RATINGS);
        });
    }

    _setDisplayFindingOutcomes() {
        let review = this._reviewService.currentReview;
        if (review && review.currentReviewLevel.type.toLowerCase() !== 'initial') {
            this.displayFindingOutcomes = true;
        } else {
            this.displayFindingOutcomes = false;
        }
    }

    isReviewConformedOrDeficient() {
        if (this._reviewService.currentReview) {
            let ratingCode = this._reviewService.currentReview.currentReviewLevel.ratingCode;
            return (ratingCode === RATINGS.CONFORMING.code || ratingCode === RATINGS.DEFICIENT.code);
        }

        return false;
    }

    _addQCchanges() {

        if (this.isQC && this._qcService.originalInitialFindings && this.findingsInfo && this._reviewService.currentReview && this._findingSvc.currentFindings && this._allRatings && this._allRatings.length) {

            let initialLevelRating = this._qcService.findInitialLevel(this._qcService.currentQCreview).ratingCode;
            this.originalReviewLevelRating =  this._allRatings.find(c => c.ratingCode === initialLevelRating)|| this._allRatings.find(c => c.ratingCode === RATINGS.CONFORMING.code);
            let totalChanges = 0;

            // Generate values for the 'QC' original rating  and 'changes' columns. The current review and the original review should have the same defect areas.
            let originalReviewFindingGroups = FindingUtils.buildFindingsTotals(this._qcService.originalInitialFindings,  this.globalStateService.defectAreasForCurrentReview, this._findingSvc.defectAreaSeverities, RATINGS) || [];

            this.findingsInfo.forEach((findingInfo) => {
                let originalQCGroup = originalReviewFindingGroups.find((g) =>  g.defectAreaCode === findingInfo.defectAreaCode);
                findingInfo.qcChange = 0;

                if (originalQCGroup) {
                    findingInfo.originalRating = originalQCGroup.rating;
                    let defectAreaCode = findingInfo.defectAreaCode;
                    let currentQCFindings = this._findingSvc.currentFindings.filter(f => f.defectAreaCode === defectAreaCode);

                    // original initial findings.
                    let originalQCFindings = this._qcService.originalInitialFindings.filter((f) =>  f.defectAreaCode === defectAreaCode);

                    // Use the same vetting logic for calculating finding changes.
                    findingInfo.qcChange = VettingService.calculateVettingChanges(currentQCFindings, originalQCFindings, null, null, true).totalChanges || 0;

                    totalChanges += findingInfo.qcChange;
                }
            });

            this.totalQCchanges = totalChanges;
        }
    }
}
