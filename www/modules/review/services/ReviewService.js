// This file was generated from the service scaffold
// Copyright 2016

import { Injectable } from '@angular/core';
import Client from '../../api/lrs.api.v1.generated';
import GlobalStateSvc from './GlobalStateSvc';
import ObservableProperty from '../../shared/decorators/observableProperty';
import { Observable } from 'rxjs';
import _ from 'lodash';
import ConditionToDisplay from '../viewModels/ConditionToDisplay';
import FindingsService from './FindingsService';
import QATreeUtils from '../QATreeUtils';
import Utils from '../../shared/Utils';
import FindingUtils from '../FindingUtils';
import { Router } from '@angular/router';

/**
 * @example
 * let injector = Injector.resolveAndCreate([ReviewService]);
 * let reviewDataService = new injector.get(ReviewService);
 * @example
 * class Component {
 *      constructor(reviewDataService:ReviewService, reviewDataService2:ReviewService) {
 *          //injected via angular, a singleton by default in same injector context
 *          console.log(reviewDataService === reviewDataService2);
 *      }
 * }
 */
@Injectable()
export default class ReviewService {

    @ObservableProperty()
    questionsForCurrentArea: Array = null;

    @ObservableProperty()
    answersForCurrentArea: Array = null;

    @ObservableProperty()
    summaryFieldsForCurrentDefectArea: Array = null;

    @ObservableProperty()
    currentDefectArea = null;

    @ObservableProperty()
    currentReview = null;

    @ObservableProperty()
    activeReviews = null;

    @ObservableProperty()
    showWrapUp: Boolean = false;

    @ObservableProperty()
    summaryFieldsForCurrentReviewLevel: Array = null;
    unfilteredSummaryFieldsForCurrentReviewLevel: Array = null;
    @ObservableProperty()
    currentReferral = null;

    _reviewBatchRequest: Observable = null;
    _allReviewsRequest: Observable = null;

    /**
     * This flag should be set from the UI to delete an orphaned question's answer when we rebuild
     * the QA tree based on Defect Area Attributes.
     * An array of defect area codes  the automated delete orphaned question action will target.
     */
    deleteOrphanedQuestionsAnswers: Array = [];

    _findingRatingCodes: Observable = null;

    // set in constructor
    _globalStateSvc: GlobalStateSvc;
    _client: Client;
    _allDefectAreaSeverityCodes: Observable = null;
    _defectAreasDictionaryObservable: Observable = null;

    @ObservableProperty()
    defectAreasCompleted: Object = null;

    currentReviewQATrees: Array = null;
    _defectAreaFindingSources: Observable = null;
    _defectAreaFindingCauses: Observable = null;
    _findingsService: FindingsService = null;


    constructor(client: Client, globalStateSvc: GlobalStateSvc, findingsService: FindingsService, router: Router) {

        this._client = client;
        this._globalStateSvc = globalStateSvc;
        this._findingsService = findingsService;
        this._router = router;

        // when a review is selected, load all the data for this review
        this._globalStateSvc.currentReviewIdObservable.subscribe((result) => {
            console.log('Loading data for review: ' + result.currentValue + ', (old reveiw was ' + result.previousValue + ')');
            this.currentReviewQATrees = null;
            this._loadDataForCurrentReview();
        });

        // when a defect area is selected, load the data for that area (summary fields, questions, answers, findings, ...)
        this._globalStateSvc.currentDefectAreaCodeObservable.subscribe((result) => {
            if (result.currentValue) {
                this._loadDataForCurrentDefectArea();
            }
        });
    }

    //This is accessed from defectArea and From QATree
    hideWrapUp() {
        this.showWrapUp = false;
    }

    displayWrapUp() {
        this.showWrapUp = true;
    }

    //When a defect area is completed, check if all defects in Review have been completed
    checkDefects() {
        let count = 0;
        let value = false;

        //Account for when defectArea is loaded directly from URL
        if (Object.keys(this.defectAreasCompleted).length > 0) {
            for (let key in this.defectAreasCompleted) {
                if (this.defectAreasCompleted[key] === true) {
                    count++;
                }
            }

            //Is the review scope full and all defect areas have been completed
            if (count === Object.keys(this.defectAreasCompleted).length && this.reviewScope === 'F') {
                value = true;
            }

            //Is review scope limited and has 1 defect area been completed
            else if (count >= 1 && this.reviewScope === 'L') {
                value = true;
            } else {
                console.log('Review Service CheckDefects - Invalid review Scope');
            }
        }

        return value;
    }

    getDefectArea(defectAreaCode: string) {
        let qaTrees = this.currentReview.qaTrees || [];
        return qaTrees.find(tree => tree.defectAreaCode === defectAreaCode);
    }

    postReferral(referral) {
        let reviewId = this._globalStateSvc.currentReviewId;
        this._client.resources.reviews.reviewId(reviewId).referral.put(referral).first().subscribe();
    }

    getReferral() {
        let reviewId = this._globalStateSvc.currentReviewId;
        this._client.resources.reviews.reviewId(reviewId).referral.get().first().subscribe(referral => {
            this.currentReferral = referral;
        });
    }

    _loadDataForCurrentReview() {
        let reviewId = this._globalStateSvc.currentReviewId;

        this._client.resources.reviews.reviewId(reviewId).get().share().first()
            .subscribe(review => {

                this.reviewScope = review.scope;
                this.currentReview = review;

                Utils.addForceEscalationWarning(this.currentReview);

                // default to the current review level of this review
                this._globalStateSvc.currentReviewLevelId = review.currentReviewLevel.reviewLevelId;
                this._globalStateSvc.initialReviewLevelId = !Utils.isReviewPendingVetting(review) && review.completedReviewLevels && review.completedReviewLevels.length ? review.completedReviewLevels[0].reviewLevelId : review.currentReviewLevel.reviewLevelId;

                // default to the current qa model of this review
                this._globalStateSvc.currentQaModelId = review.qaModelId;

                this._client.resources.reviews.dictionary.qaModels.qaModelId(this._globalStateSvc.currentQaModelId).defectAreas.get().first()
                    .subscribe(defectAreas => {

                        // extract from the defect area dictionary the subset that are associated with this review
                        this._globalStateSvc.defectAreasForCurrentReview = defectAreas.sort((a, b) => a.order - b.order).filter((da) =>
                            this.currentReview.defectAreaIds.find(id => da.defectAreaId === id));

                        // Now that we have everything for the Review, go get the data for the current review level
                        this._loadDataForReviewLevel();
                    });
            });
    }

    _loadDataForCurrentDefectArea() {
        let reviewId = this._globalStateSvc.currentReviewId;
        let initialReviewLevelId = this._globalStateSvc.initialReviewLevelId;
        let defectAreaCode = this._globalStateSvc.currentDefectAreaCode;
        this.summaryFieldsForCurrentDefectArea = null;
        this.currentDefectArea = null;
        this._defectAreaFindingSources = null;
        this._defectAreaFindingCauses = null;

        // reset answer and questions
        this.questionsForCurrentArea = null;
        this.answersForCurrentArea = null;

        if (reviewId && initialReviewLevelId && defectAreaCode) {
            let defectAreaQATreeId = this.getDefectArea(defectAreaCode).qaTreeId;

            // Use the cached questions in the loaded qa trees if possible.

            let cachedTrees = this.currentReviewQATrees || [];
            let foundTree = cachedTrees.find((tree) => tree.qaTreeId === defectAreaQATreeId);
            if (foundTree) {
                this.questionsForCurrentArea = foundTree.questions;
            }

            // load the list of answers
            this.getAnswersForCurrentArea();

            // Operational reviews do not have fields, just set defect area
            if (this.currentReview.reviewType.toLowerCase() === 'operational') {
                this._setCurrentDefectArea();
            } else {
                if (!this.summaryFieldsForCurrentReviewLevel) {
                    this._client.resources.reviews.reviewId(reviewId).level.reviewLevelId(initialReviewLevelId).fields.get().first().subscribe((fields) => {
                        this.unfilteredSummaryFieldsForCurrentReviewLevel = fields;
                        this.summaryFieldsForCurrentReviewLevel = ConditionToDisplay.filterFieldsByConditionsToDisplay(fields);
                        this._filterSummaryFieldsByDefectArea();
                    });
                } else {
                    this._filterSummaryFieldsByDefectArea();
                }
            }
        }
    }

    getAnswersForCurrentArea() {
        let reviewId = this._globalStateSvc.currentReviewId;
        let initialReviewLevelId = this._globalStateSvc.initialReviewLevelId;
        let defectAreaCode = this._globalStateSvc.currentDefectAreaCode;
        let defectAreaQATreeId = this.getDefectArea(defectAreaCode).qaTreeId;

        this._client.resources.reviews.reviewId(reviewId).level.reviewLevelId(initialReviewLevelId).qaTree.qaTreeId(defectAreaQATreeId).answers.get().first().subscribe((answers) => {
            this.answersForCurrentArea = answers;

            // Update our reference to these answers and re-verify our completeness
            let qaTreeWrapper = this.currentReviewQATrees.find((qaTreeWrapper) => qaTreeWrapper.defectAreaCode === defectAreaCode);
            if (qaTreeWrapper) {
                qaTreeWrapper.answers = answers;
                // Go ahead and re-verify
                this._verifyReviewQATrees();
            }
        });
    }

    _loadDataForReviewLevel() {
        let reviewId = this._globalStateSvc.currentReviewId;
        let reviewLevelId = this._globalStateSvc.currentReviewLevelId;
        let initialReviewLevelId = this._globalStateSvc.initialReviewLevelId;
        this.summaryFieldsForCurrentReviewLevel = null;
        this.unfilteredSummaryFieldsForCurrentReviewLevel = null;

        if (reviewId && reviewLevelId && initialReviewLevelId) {
            // Grab all findings first
            this._findingsService.loadAllFindings(reviewId, reviewLevelId, true).subscribe(() => {

                // Operational reviews do not have fields
                if (this.currentReview.reviewType.toLowerCase() === 'operational') {
                    this.unfilteredSummaryFieldsForCurrentReviewLevel = [];
                    this._loadAllQuestionTreesForCurrentReview();
                } else {
                    // Now grab fields
                    this._client.resources.reviews.reviewId(reviewId).level.reviewLevelId(initialReviewLevelId).fields.get().first().subscribe((fields) => {
                        this.unfilteredSummaryFieldsForCurrentReviewLevel = fields;
                        this.summaryFieldsForCurrentReviewLevel = ConditionToDisplay.filterFieldsByConditionsToDisplay(fields);

                        // And finally build up all question trees
                        this._loadAllQuestionTreesForCurrentReview();
                    });
                }
            });
        }
    }

    _setCurrentDefectArea() {
        if (this._globalStateSvc.currentDefectAreaCode) {
            this.currentDefectArea = this._globalStateSvc.defectAreasForCurrentReview.find(d => d.defectAreaCode === this._globalStateSvc.currentDefectAreaCode);
        }
    }

    _filterSummaryFieldsByDefectArea() {

        this._setCurrentDefectArea();

        if (this.currentDefectArea) {

            // Filter the summary fields based on the current defect area.
            this.summaryFieldsForCurrentDefectArea = this.unfilteredSummaryFieldsForCurrentReviewLevel.filter((field) => {
                let displayInDefectAreas = field.displayInDefectAreas || [];
                return displayInDefectAreas.find((defectAreaId) => defectAreaId === this.currentDefectArea.defectAreaId);
            });
        }
    }

    _loadAllQuestionTreesForCurrentReview() {
        let reviewId = this._globalStateSvc.currentReviewId;
        let initialReviewLevelId = this._globalStateSvc.initialReviewLevelId;
        let requestTotal = 0;

        // Reset, which will be populated by _verifyReviewQATrees()
        this.defectAreasCompleted = null;
        this.currentReviewQATrees = [];

        this.currentReview.qaTrees.map((tree) => {
            Observable.forkJoin(
                this._client.resources.reviews.qaTree.qaTreeId(tree.qaTreeId).questions.get().first(),
                this._client.resources.reviews.reviewId(reviewId).level.reviewLevelId(initialReviewLevelId).qaTree.qaTreeId(tree.qaTreeId).answers.get().first()
            ).first().subscribe(([questions, answers]) => {

                let qaTreeWrapper = {
                    defectAreaCode: tree.defectAreaCode,
                    qaTreeId: tree.qaTreeId,
                    questions: questions,
                    answers: answers,
                    tree: tree };

                this.currentReviewQATrees.push(qaTreeWrapper);

                requestTotal++;
                if (requestTotal === this.currentReview.qaTrees.length) {
                    this._loadDataForCurrentDefectArea();
                    this._verifyReviewQATrees();
                }
            });
        });
    }

    _verifyReviewQATrees() {

        if (!this.currentReviewQATrees) {
            return;
        }

        let isReadOnly = Utils.isQuestionTreeReadOnly(this.currentReview);
        let _defectAreasCompleted = [];

        this.currentReviewQATrees.forEach((qaTreeWrapper) => {

            let questionNodes = QATreeUtils.buildNodes(this,
                this._findingsService,
                qaTreeWrapper.questions,
                qaTreeWrapper.answers,
                this.unfilteredSummaryFieldsForCurrentReviewLevel,
                qaTreeWrapper.tree);

            // Capture the parsed visible question tree;
            qaTreeWrapper.parsedQuestions = questionNodes;

            let { allQuestionsCompleted } = FindingUtils.checkAllQuestionAnswers(
                qaTreeWrapper.parsedQuestions,
                this._findingsService.currentFindings,
                isReadOnly);

            // Use our current object if defined, otherwise use a temporary and replace all at once at the end
            if (this.defectAreasCompleted) {
                this.defectAreasCompleted[qaTreeWrapper.defectAreaCode] = allQuestionsCompleted;
            } else {
                _defectAreasCompleted[qaTreeWrapper.defectAreaCode] = allQuestionsCompleted;
            }

            qaTreeWrapper.completed = allQuestionsCompleted;
        });

        if (!this.defectAreasCompleted) {
            this.defectAreasCompleted = _defectAreasCompleted;
        }
    }

    getBatchById(batchId: String, lender: Boolean = false) {
        if (!this._reviewBatchRequest || (this._reviewBatchRequest.batchId !== batchId)) {
            this._reviewBatchRequest = lender ?
                this._client.resources.reviews.batches.batchId(batchId).lenderByBatchId.get() :
                this._client.resources.reviews.batches.batchId(batchId).get();
            this._reviewBatchRequest.batchId = batchId;
        }

        return this._reviewBatchRequest.first();
    }

    /**
     * PUT or POST the user's answer
     * @param {Object} answer - the full Answer object defined in RAML.
     * @return {Observable} - a Observable that completes once the response is received from the service.
     */
    saveQuestionAnswer(answer: Object): Observable {
        let reviewId = this._globalStateSvc.currentReviewId,
            reviewLevelId = this._globalStateSvc.currentReviewLevelId,
            defectAreaCode = this._globalStateSvc.currentDefectAreaCode,
            requestData = answer,
            request,
            defectAreaQATreeId = this.getDefectArea(defectAreaCode).qaTreeId;

        if (!reviewId || !reviewLevelId || !defectAreaCode) {
            throw new Error('saveQuestionAnswer reviewId, reviewLevelId or defectAreaCode is null.');
        } else if (!requestData.answerId) {
            request = this._client.resources.reviews.reviewId(reviewId).level.reviewLevelId(reviewLevelId).qaTree.qaTreeId(defectAreaQATreeId).answers.post(requestData);
        } else {
            request = this._client.resources.reviews.reviewId(reviewId).level.reviewLevelId(reviewLevelId).qaTree.qaTreeId(defectAreaQATreeId).answers.answerId(requestData.answerId).put(requestData);
        }

        return request;
    }

    deleteAnswers(answers: Array) {
        if (!answers || answers.length === 0) {
            throw Error('deleteAnswers - answers array is empty or undefined');
        }

        let defectAreaQATreeId = (this._globalStateSvc.currentDefectAreaCode) ? this.getDefectArea(this._globalStateSvc.currentDefectAreaCode).qaTreeId : null;
        let requests = answers.map((answer) => {

            if (answer.qaTreeId) {
                defectAreaQATreeId = answer.qaTreeId;
            }

            return this._client.resources.reviews.reviewId(this._globalStateSvc.currentReviewId).level.reviewLevelId(
                this._globalStateSvc.currentReviewLevelId).qaTree.qaTreeId(defectAreaQATreeId).answers.answerId(answer.answerId).delete();
        });

        return Observable.forkJoin(requests);
    }


    saveFilteredSummaryFields(updatedFields: Array) {
        let reviewId = this._globalStateSvc.currentReviewId,
            reviewLevelId = this._globalStateSvc.currentReviewLevelId;
        if (!reviewId || !reviewLevelId || !updatedFields) {
            throw new Error('saveFilteredSummaryFields reviewId, reviewLevelId or request data is null.');
        }

        // Generate a PUT request for each field.
        let requests = updatedFields.map((field) =>
            this._client.resources.reviews.reviewId(reviewId).level.reviewLevelId(reviewLevelId).fields.updateField.put(field)
        );

        Observable.forkJoin(requests).first().subscribe(() => {
            this._globalStateSvc.loanAttributesEdited = false;

            // Refresh all loan attributes and filter the QA tree.
            this._loadDataForReviewLevel();
        });
    }

    submitReviewWrapUp(reviewLevelSubmit: Object = null) {
        let reviewId = this._globalStateSvc.currentReviewId,
            reviewLevelId = this._globalStateSvc.currentReviewLevelId;
        let requestData = reviewLevelSubmit || {};
        this._client.resources.reviews.reviewId(reviewId).level.reviewLevelId(reviewLevelId).submit.post(requestData).first().subscribe(() => {
            this._router.navigate(['/workload/summary']);
        });
    }

    saveIndemType(body: Object = null) {
        let reviewId = this._globalStateSvc.currentReviewId,
            reviewLevelId = this._globalStateSvc.currentReviewLevelId;
        let requestData = body || {};
        this._client.resources.reviews.reviewId(reviewId).level.reviewLevelId(reviewLevelId).indemnificationType.put(requestData).first().subscribe(() => {});
    }

    submitLenderReviewWrapUp(reviewId, reviewLevelId) {
        this._client.resources.reviews.reviewId(reviewId).level.reviewLevelId(reviewLevelId).lenderSubmit.put().first().subscribe(() => {
            this._router.navigate(['/lender/activeReviews/summary']);
        });
    }


    /**
     * @description - PUT, POST, or DELETE Finding.
     * @param {Array} findings - finding list.
     * @param {String} _reviewId - review id.
     * @param {String} _reviewLevelId - review level id.
     * @return {Observable} - Observable
     */
    saveOrDeleteFindings(findings: Array, _reviewId: String = null, _reviewLevelId: String = null) {
        let reviewId = (_reviewId) ? _reviewId : this._globalStateSvc.currentReviewId,
            reviewLevelId = (_reviewLevelId) ? _reviewLevelId : this._globalStateSvc.currentReviewLevelId;

        if (!reviewId || !reviewLevelId) {
            throw new Error('saveOrDeleteFindings error - reviewId or reviewLevelId is null.');

        }
        // DELETE any finding that has empty questionIds.
        let deletedFindings = _.unique(findings.filter(f => f.findingId && (f.questionIds && f.questionIds.length === 0) && !f.isAdhoc), ff => ff.findingId);

        // POST new finding.
        let newFindings = findings.filter(f => ((f.questionIds && f.questionIds.length > 0) || f.isAdhoc) && !f.findingId);

        // PUT existing findings.
        let updatedFindings = _.unique(findings.filter(f => ((f.questionIds && f.questionIds.length > 0) || f.isAdhoc) && f.findingId), ff => ff.findingId);

        // Build out the requests accordingly.
        let deleteRequests = deletedFindings.map(f => this._client.resources.reviews.reviewId(reviewId).level.reviewLevelId(reviewLevelId).findings.findingId(f.findingId).delete());
        let postRequests = newFindings.map(f => this._client.resources.reviews.reviewId(reviewId).level.reviewLevelId(reviewLevelId).findings.post(f));
        let putRequests = updatedFindings.map(f => this._client.resources.reviews.reviewId(reviewId).level.reviewLevelId(reviewLevelId).findings.findingId(f.findingId).put(f));
        let requestGroups = [postRequests, putRequests, deleteRequests].filter(g => g.length);

        // Execute all the requests in the order of post, put, and delete. Refresh all findings for the current level.
        return Observable.create(observer => {
            let resIndex = 0;

            let resfreshFindings = () => {

                // Refresh the finding list for the current review level
                this._findingsService.loadAllFindings(this._globalStateSvc.currentReviewId, this._globalStateSvc.currentReviewLevelId, true).subscribe((findings) => {
                    observer.next(findings);
                    observer.complete();
                });
            };

            function callRequestGroup(group: Array) {
                Observable.forkJoin(group).first().subscribe(() => {
                    resIndex++;
                    if (resIndex >= requestGroups.length) {
                        resfreshFindings();
                    } else {
                        callRequestGroup(requestGroups[resIndex]);
                    }
                });
            }

            // Start sending requests.
            if (requestGroups.length) {
                callRequestGroup(requestGroups[resIndex]);
            } else {
                resfreshFindings();
            }
        });
    }

    postManagementDecision(reviewId: String) {
        return this._client.resources.reviews.reviewId(reviewId).managementDecision.post().first();
    }

    postForceIndemnification(reviewId: String, reviewLevelInfo: Object) {
        if (!reviewId || !_.isObject(reviewLevelInfo)) {
            throw (new Error('postForceIndemnificatio - The creviewId and reviewLevelInfo are required!'));
        }

        return this._client.resources.reviews.reviewId(reviewId).level.reviewLevelId(this._globalStateSvc.currentReviewLevelId).indemnification.reviewerForceSubmit.post(reviewLevelInfo).first()
            .switchMap(() => {

            // Reload ALL the review data because force indem will create a new review level for the current review.
                this._loadDataForCurrentReview();

            /**
             * Wait for all the review data to reutn. We leverage the this.defectAreasCompletedObservable here to determine when all the review and current level data have been processed.
             * If this is taking too long  it might be ok to just let the data refresh in the background and return Observable.of('some data') right away.
             */
                return this.defectAreasCompletedObservable;
            });
    }

    postForceEscalation(reviewId: String) {
        return this._client.resources.reviews.reviewId(reviewId).forceEscalation.post().first();
    }

    initiateMRB(reviewId: String) {
        return this._client.resources.reviews.reviewId(reviewId).mrbReferral.post().first();
    }

    getAllReviewNotes(reviewId: String) {
        return this._client.resources.reviews.reviewId(reviewId).notes.get().first();
    }

    postReviewNote(reviewId: String, text: String, defectAreaCode: String) {
        let request = { text: text, defectAreaCode: defectAreaCode };
        return this._client.resources.reviews.reviewId(reviewId).notes.post(request).first();
    }

    cancelCase(review: Object, reason: String) {
        return this._client.resources.reviews.reviewId(review.reviewId).level.reviewLevelId(review.currentReviewLevel.reviewLevelId).cancel.put({reasonCode: reason}).first();
    }

    /**
     * @description - refresh the current review independently without preloading the associated data sources.
     * @return {Observable} - Observable
     */
    refreshCurrentReview() {
        if (!this.currentReview || !this.currentReview.reviewId) {
            throw (new Error('refreshCurrentReview - The currentReview is required!'));
        }

        let refreshReviewObservable = this._client.resources.reviews.reviewId(this.currentReview.reviewId).get().share().first();
        refreshReviewObservable.subscribe((review) => {
            if (review) {
                this.currentReview = review;
                Utils.addForceEscalationWarning(this.currentReview);
            }
        });

        return refreshReviewObservable;
    }

    deleteFinding(findingId: String, _reviewId: String = null, _reviewLevelId: String = null) {
        if (!findingId) {
            throw (new Error('Delete Finding - invalid Finding Id!'));
        }

        let reviewId = (_reviewId) ? _reviewId : this._globalStateSvc.currentReviewId,
            reviewLevelId = (_reviewLevelId) ? _reviewLevelId : this._globalStateSvc.currentReviewLevelId;

        return this._client.resources.reviews.reviewId(reviewId).level.reviewLevelId(reviewLevelId).findings.findingId(findingId).delete();
    }
}
