// This file was generated from the view scaffold
// Copyright 2016

import {Component} from '@angular/core';
import template from './NonInitialFindingDetails.html';
import styles from './NonInitialFindingDetails.less';
import EditFindingBase from './EditFindingBase';
import ReviewLevelFindingRenderer from '../../shared/components/ReviewLevelFindingRenderer';
import FindingUtils, {REVIEWER_RESPONSE_TYPE} from '../FindingUtils';
import {Observable} from 'rxjs/Observable';
import { RATINGS } from '../constants';
import _ from 'lodash';
import Utils from '../../shared/Utils';
import { REVIEW_LEVEL_STATUS } from '../../shared/constants';

@Component({
    selector: 'non-initial-finding-details',
    template: template,
    styles: [styles]
})
/**
 * @descripton extends the EditFindingBase to leverage the funcitonality that loads most of the required data.
 * @see https://angular.io/docs/ts/latest/guide/router.html
 */
export default class NonInitialFindingDetails extends EditFindingBase {

    status:String = 'normal';
    findingSourceDescription:String;
    findingCauseDescription:String;

    reviewLevelType:String = '';
    _allRatingCodes:Array = null;

    // The current review level data bound to the curren review level card.
    currentReviewLevelData: Object = {};

    // The completed levels data bound to the completed level cards.
    completedReviewLevelsData:Array = [];

    // The current reviwer response status for this Finding
    reviewerResponseAction:String = null;

    // Adjust Severity selection
    adjustSeverityItems:Array = [];
    selectedAdjustSeverityValue:String = null;

    // Finantial Remediation selection
    financialRemediationItems:Array = [];
    selectedRemediationTypeCode:String = null;
    selectedRemediationAmount:String = null;

    // Reviewer response constants
    RESPONSE_TYPE = REVIEWER_RESPONSE_TYPE;
    RATING_TYPE = RATINGS;

    allLevelFindings: Array = null;

    commentToLender: String = null;

    _currentFindings: Array = null;
    _currentOrOriginalReview: Object = null;
    isQC: Boolean = false;
    isInitialQCoriginalReview: Boolean = false;

    activeActions: Array = [];

    ngOnInit() {
        super.ngOnInit();

    }

    // Override to Load additional data for the reviewer response actions UI.
    _loadDictionaryData() {

        if (this.reviewService.currentReview && this.globalService.currentQaModelId && !this.defectAreaCausesSource && this.globalService.currentDefectAreaCode) {
            let qaTreeId = this.reviewService.getDefectArea(this.globalService.currentDefectAreaCode).qaTreeId;

            this.subscriptions.push(Observable.forkJoin(
                this.dictionaryService.getDefectAreaSources(this.globalService.currentQaModelId, this.defectAreaCode),
                this.dictionaryService.getDefectAreaCauses(this.globalService.currentQaModelId, this.defectAreaCode),
                this.dictionaryService.getDefectAreaSeverities(this.globalService.currentQaModelId, this.defectAreaCode),
                this.dictionaryService.getFindingRatingCodes(),
                this.dictionaryService.getRemediationTypes(this.globalService.currentQaModelId, qaTreeId)
            ).first().subscribe(([sources, causes, severities, codes, remediationTypes]) => {
                this.defectAreaCausesSource = causes;
                this.defectAreaSourcesSource = sources;
                this.defectAreaSeverities = severities;

                this._allRatingCodes = codes;
                this.financialRemediationItems = remediationTypes;
                this.selectedRemediationTypeCode = remediationTypes && remediationTypes.length && remediationTypes[0].code;

                this.refreshFinding();
            }));
        }
    }

    ngOnDestroy() {
        super.ngOnDestroy();
    }

    /*
     * @description - Override method. We use the onRouteChanged method to reset data because the view's state can change based on the selected defect area code
     * passed in the sub route after the view has initialized.
     * @param {object} param - parameters
     * @return {void}
     */
    onRouteChanged(params) {
        this.findingId = params.findingId;
        this.defectAreaCausesSource = null;
        this.defectAreaSourcesSource = null;
        this.defectAreaSeverities = null;
        this.currentReviewLevelData = null;
        this.finding = {};
        super.onRouteChanged(params);
    }

    /*
     * @description - Override method.
     * @return {void}
     */
    updateQuestionsAndAnswers() {
        // Do nothing since we don't use the question or answer data here.
    }

    /*
     * @description - Override method.
     * @return {void}
     */
    refreshFinding() {

        let _process = () => {
            this.finding = FindingUtils.findFindingById(this.findingId, this._currentFindings);
            this._setTitleStatus();
            this.findingSourceDescription = this.defectAreaSourcesSource.find(s => s.defectSourceCode === this.finding.selectedSourceCode).description || '';
            this.findingCauseDescription = this.defectAreaCausesSource.find(c => c.defectCauseCode === this.finding.selectedCauseCode).description || '';

            // For the current review level.
            this.currentReviewLevelData = ReviewLevelFindingRenderer.buildRendererData(this.defectAreaSeverities, this._currentOrOriginalReview, this._currentOrOriginalReview.currentReviewLevel, this.finding);

            // Build the completed level card data.
            this.findingsService.loadFindingsForAllCompletedLevels(this._currentOrOriginalReview, this.finding.defectAreaCode).subscribe((findings) => {
                this.allLevelFindings = findings;
                this._buildDataForCompletedLevels(findings);
                this._refreshCurrentResponseAction();
                this._updateReviewReadOnly();
            });

            /*Select control binding bug fix. Don't reset the this.adjustSeverityItems array again if it has been populated or
             'the selectedAdjustSeverityValue' won't bind correctly if it's the same as the previous selection from a different finding.*/
            if (!this.adjustSeverityItems || !this.adjustSeverityItems.length) {
                this.adjustSeverityItems = [...this.defectAreaSeverities, {ratingCode:RATINGS.CONFORMING.code, description: RATINGS.CONFORMING.description}];
            }

            this._updateRemediatedData();

            this.commentToLender = this.finding.commentToLender;

            if (this.finding.isAdhoc === true) {
                this.isAdhoc = true;
            }

            this.selectedAdjustSeverityValue = (this.finding.ratingCode === RATINGS.CONFORMING.code) ? RATINGS.CONFORMING.code : this.finding.selectedTierCode;
            this._updateReviewReadOnly();
        };

        if (!this.currentReviewLevelData && this.reviewService.currentReview && this._allRatingCodes && this.findingsService.currentFindings  && this.defectAreaCausesSource && this.defectAreaSourcesSource && this.defectAreaSeverities) {
            this.isQC = Utils.isQCreview(this.reviewService.currentReview);
            if (this.originalfinding && this.isQC ) {

                // Load all qc original review data.
                this.qcService.getOriginalReview(this.reviewService.currentReview.originalQcReviewId).subscribe((review) => {
                    this._currentOrOriginalReview = review;

                    Observable.forkJoin(
                        this.qcService.getOriginalFindingsInCurrentLevel(review),
                        this.qcService.getAllOriginalFindingsInCompletedLevels(review)
                    ).first().subscribe(([finalFindings, allFindings]) => {
                        this._currentFindings = finalFindings;
                        this.allLevelFindings = allFindings;
                        _process();
                    });
                });

            } else {
                this._currentOrOriginalReview =  this.reviewService.currentReview;
                this._currentFindings = this.findingsService.currentFindings;
                this.originalFinding = false;
                _process();
            }
        }
    }

    // Override.
    _updateReviewReadOnly() {
        if (this.completedReviewLevelsData && this.completedReviewLevelsData.length) {
            let previousFinding = _.last(this.completedReviewLevelsData).finding;
            let isExceptionReview = (this.reviewService.currentReview.currentReviewLevel.status.toLowerCase() === REVIEW_LEVEL_STATUS.EXCEPTION);
            this.activeActions = (this.originalfinding || isExceptionReview) ? [] :FindingUtils.getActiveResponseActions(this.finding, previousFinding, this.reviewService.currentReview);
            this.isReviewReadOnly = (this.activeActions.length === 0) || isExceptionReview;
        }
    }

    _updateRemediatedData() {
        this.selectedRemediationTypeCode = null;
        this.selectedRemediationAmount =  null;
        if (this.finding && this.finding.findingId && this.finding.reviewerResponseRemediated) {
            this.selectedRemediationTypeCode = this.finding.reviewerResponseRemediated.remediationTypeCode;
            this.selectedRemediationAmount =  this.finding.reviewerResponseRemediated.remediationAmount;
        }
    }

    _refreshCurrentResponseAction() {
        if (this.allLevelFindings) {
            let action = FindingUtils.getReviewerFindingResponseAction(this.finding, FindingUtils.getFindingGroupsFromPreviousLevel(this._currentOrOriginalReview, this.allLevelFindings)) || {};
            this.reviewerResponseAction = action.code;
        }
    }

    _buildDataForCompletedLevels(filteredFindings) {
        if (!this.defectAreaSeverities) {
            return;
        }

        this.isInitialQCoriginalReview = this.isQC && this.originalfinding && Utils.isInitialReview(this._currentOrOriginalReview);
        let completedLevels = (this.isInitialQCoriginalReview) ? this._currentOrOriginalReview.completedReviewLevels || [] : Utils.filterCompletedLevels(this._currentOrOriginalReview);
        let data = [];
        completedLevels.forEach((level) => {

            // Finding the finding tied to this completed level
            let finding = filteredFindings.filter((f) => f.selectedCauseCode === this.finding.selectedCauseCode && f.selectedSourceCode === this.finding.selectedSourceCode && f.reviewLevelId === level.reviewLevelId);
            if (finding && finding.length) {
                data.push(ReviewLevelFindingRenderer.buildRendererData(this.defectAreaSeverities, this._currentOrOriginalReview, level, finding[0]));
            } else {

                // Note: an ad hoc finding might not exist in all completed levels.
                console.log(`Unable to find a finding(ad hoc?) for the completed review level: ${level.reviewLevelId} defect area: ${this.finding.defectAreaCode}`);
            }
        });

        this.completedReviewLevelsData = [...data];
    }

    onReviewResponseUpdate(code: String = null) {
        this.reviewerResponseAction = code;
    }

    _setTitleStatus() {
        if (this.finding.commentToLender && this.finding.commentToLender.length) {
            this.status = REVIEWER_RESPONSE_TYPE.NOT_MITIGATED.description;
        } else {
            this.status = this._allRatingCodes.find(r => r.ratingCode === this.finding.ratingCode).description;
        }
    }

    onSubmit(/*form*/) {
        if (this.originalfinding) {
            throw (new Error('Saving a finding from an original review of a QC review is not allowed!'));
        }

        FindingUtils.resetReviewerResponseFindingStatus(this.finding);

        if (this.reviewerResponseAction === REVIEWER_RESPONSE_TYPE.ADJUSTED.code) {
            this.finding.reviewerResponseAdjusted = this.selectedAdjustSeverityValue;
        } else if (this.reviewerResponseAction === RATINGS.REMEDIATED.code)  {
            this.finding.reviewerResponseRemediated = { remediationTypeCode: this.selectedRemediationTypeCode, remediationAmount: this.selectedRemediationAmount };
        } else if (this.reviewerResponseAction === RATINGS.MITIGATED.code) {
            this.finding.reviewerResponseMitigated = true;
        } else if (this.reviewerResponseAction === REVIEWER_RESPONSE_TYPE.NOT_MITIGATED.code) {
            this.finding.commentToLender = this.commentToLender;
        }

        // Save the finding.
        this.findingsService.saveFinding(this.finding, this.reviewService.currentReview.reviewId, this.reviewService.currentReview.currentReviewLevel.reviewLevelId).subscribe((finding) => {
            if (finding && finding.findingId) {

                /**
                 * Refreshing the findings after submitting a reviewer's response action is the simplest solution  to update all the bindings including the Finding sidebar.
                 * The other solution is to update a single finding but we'll have to figure out which transient fields in the finding we need to rebuild and trigger bindings in all views
                 **/
                this.findingsService.loadAllFindings(this.reviewService.currentReview.reviewId, this.reviewService.currentReview.currentReviewLevel.reviewLevelId, true).subscribe((findings) => {
                    this.finding = findings.find(f => f.findingId === this.findingId);

                    // Update the current review level rating.
                    this.reviewService.refreshCurrentReview().subscribe(() => {
                        this._setTitleStatus();
                        this._refreshCurrentResponseAction();
                    });

                });
            }
        });
    }

    isActionActive(code: String) {
        return !!(this.activeActions.find(a => a === code));
    }

}
