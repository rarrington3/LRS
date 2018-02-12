// This file was generated from the exports scaffold
// Copyright 2016

/**
 * @example
 * import FindingUtils from 'modules/review/FindingUtils';
 * let findingUtils = new FindingUtils();
 */
import {QA_TREE_ANSWER_STATUS, RATINGS} from './constants';
import QuestionNodeVM from './viewModels/QuestionNodeVM';
import {Observable} from 'rxjs';
import Utils from '../shared/Utils';
import _ from 'lodash';

export const REVIEWER_RESPONSE_TYPE = {
    NOT_MITIGATED: {code: 'notmitigated', description: 'Not Mitigated'},
    ADJUSTED: { code: 'adjusted', description: 'Adjusted' }
};

export default class FindingUtils {

    constructor() {
    }

    /**
     * Finding Helper methods.
     */

    static findFindingByQuestionId(questionId: String, findings: Array = null) {
        if (!findings) {
            return null;
        }

        return findings.find(f => {
            let questionIds = f.questionIds || [];
            return questionIds.find(qId => qId === questionId);
        });
    }

    static findAllFindingsByQuestionId(questionId: String, removeDuplicateQuestionId: Boolean = false, ignoreFindingId: String = null, findings: Array = null) {
        let foundFindings = [];

        if (findings) {
            findings.forEach(f => {
                let questionIds = f.questionIds || [];
                if (f.findingId !== ignoreFindingId && questionIds.find(qId => qId === questionId)) {
                    foundFindings.push(f);
                }

                if (removeDuplicateQuestionId && f.findingId !== ignoreFindingId) {
                    f.questionIds = questionIds.filter(qId => qId !== questionId);
                }
            });
        }

        return foundFindings;
    }

    static findFindingById(findingId: String, findings: Array = null) {
        if (!findings) {
            return null;
        }

        return findings.find((f) => f.findingId === findingId);
    }

    static findFindingBySourceAndCause(sourceCode: String, causeCode: String, defectAreaCode: String, findings: Array = null) {
        if (!findings) {
            return null;
        }
        return findings.find(f => (f.selectedSourceCode === sourceCode && f.selectedCauseCode === causeCode && f.defectAreaCode === defectAreaCode));
    }

    static findAdhocFindingBySourceAndCause(sourceCode: String, causeCode: String, defectAreaCode: String, findings: Array = null) {
        if (!findings) {
            return null;
        }
        return findings.find(f => (f.selectedSourceCode === sourceCode && f.selectedCauseCode === causeCode && f.defectAreaCode === defectAreaCode && f.isAdhoc === true));
    }

    static createNewFinding(reviewLevelId: String, defectAreaCode: String) {
        if (!reviewLevelId || !defectAreaCode) {
            throw new Error('createNewFinding - invalid reviewLevelId or defectAreaCode.');
        }

        return {
            reviewLevelId: reviewLevelId,
            defectAreaCode: defectAreaCode,
            questionIds: [],
            selectedSourceCode: null,
            selectedCauseCode: null,
            selectedTierCode: null,
            commentToLender: null
        };
    }

    static deleteIncompleteQuestionAnswerAndFinding(questionNode: QuestionNodeVM, reviewService: Object, findingsService: Object) {
        if (!(questionNode instanceof QuestionNodeVM)) {
            throw new Error('The parameter questionNode is not of type QuestionNodeVM!');
        }

        return Observable.create(observer => {
            let complete = (_observer) => {
                _observer.next(true);
                _observer.complete();
            };

            if (questionNode.answer && questionNode.answer.answerId) {
                reviewService.deleteAnswers([questionNode.answer]).first().subscribe(() => {

                    // Reset the answer to the 'unasnwered' status which should trigger UI changes.
                    questionNode.answer = null;

                    // Technically there's no existing finding but for safety, iterate the finding and perform any necessary updates.
                    let finding = FindingUtils.findFindingByQuestionId(questionNode.questionId, findingsService.currentFindings);

                    //TEST CODE
                    //finding = null;

                    if (finding && finding.findingId) {
                        console.log(`deleteIncompleteQuestionAnswerAndFinding warning. Deleting or updating an existing finding for question ${questionNode.questionId}`);
                        finding.questionIds = finding.questionIds.filter((q) => q !== questionNode.questionId);
                        reviewService.saveOrDeleteFindings([finding]).subscribe(() => {
                            complete(observer);
                        });
                    } else {
                        complete(observer);
                    }
                });
            } else {
                complete(observer);
            }
        });
    }

    static navigateToFindingSourceCause(router: Object, globalStateService: Object, reviewService: Object, findingsService: Object, questionNode: QuestionNodeVM, modal: Object = null) {
        if (!(questionNode instanceof QuestionNodeVM)) {
            throw new Error('The parameter questionNode is not of type QuestionNodeVM!');
        }

        let route = `/review/${globalStateService.currentReviewId}/tree/${globalStateService.currentDefectAreaCode}/question/${questionNode.questionId}/sourcecause`;

        if (modal) {
            modal.askForConfirmation(
                `<p>To continue, you must create a finding for ${questionNode.questionReference} </p><p>Select Cancel will delete the answer for this question.</p>`,
                'Incomplete Finding Found', 'Continue', 'Cancel Finding')
                .first().subscribe(() => router.navigate([route]), () => {
                    FindingUtils.deleteIncompleteQuestionAnswerAndFinding(questionNode, reviewService, findingsService).subscribe(() => {});
                });
        } else {
            router.navigate([route]);
        }
    }

    //Navigate via a finding rather than a question
    static navigateToFindingSourceCauseByFinding(router: Object, globalStateService: Object, reviewService: Object, finding) {

        let route;
        // If this is an ad hoc finding we need to pass the selected cause and code in the route.
        if (finding.isAdhoc)  {
            route = `/review/${globalStateService.currentReviewId}/tree/${globalStateService.currentDefectAreaCode}/sourcecause`;
            router.navigate([route, {selectedCauseCode: finding.selectedCauseCode, selectedSourceCode: finding.selectedSourceCode}]);
        } else {
            let questionID = finding.questionIds[0];

            if (questionID) {
                route = `/review/${globalStateService.currentReviewId}/tree/${globalStateService.currentDefectAreaCode}/question/${questionID}/sourcecause`;
                router.navigate([route]);
            }
        }
    }

    static navigateToFindingDetail(router: Object, globalStateService: Object, reviewService: Object, finding:Object) {
        let detailsRoute = (FindingUtils.isInitialFinding(finding, reviewService.currentReview) || FindingUtils.isInitialAdhocFinding(finding, reviewService.currentReview)) ? 'initialdetails' : 'noninitialdetails';
        router.navigate([`review/${globalStateService.currentReviewId}/tree/${finding.defectAreaCode}/finding/${finding.findingId}/${detailsRoute}`]);
    }

    /**
     * @description - check all the visible questions in a defect area for complete answer and finding.
     * @param {Array} questionNodes - the question tree in the current Defect Area.
     * @param {Array} findings - all findings in the current level.
     * @param {Boolean} readOnly - Set to true when checking for answers in read only mode (will not set questionWithIncompleteFinding)
     * @return {Object} - return the allQuestionsCompleted and questionWithIncompleteFinding variables.
     */
    static checkAllQuestionAnswers(questionNodes: Array, findings: Array, readOnly: Boolean = false) {
        let allQuestionsCompleted = true;
        let questionWithIncompleteFinding = null;
        let find = (nodes) => {

            for (let node of nodes) {
                if (node.hidden) {
                    continue;
                }

                if (node.answerStatus === QA_TREE_ANSWER_STATUS.UNANSWERED) {
                    allQuestionsCompleted = false;
                } else if (!readOnly && node.answerStatus === QA_TREE_ANSWER_STATUS.FINDING_TRIGGERED) {
                    let finding = FindingUtils.findFindingByQuestionId(node.questionId, findings);

                    // Test Code
                    // finding = null

                    if (!finding || !finding.findingId || !finding.selectedTierCode || !finding.commentToLender) {
                        questionWithIncompleteFinding = node;
                        allQuestionsCompleted = false;
                        break;
                    }
                }

                // Walk through all the child nodes.
                if (node.nodes && node.nodes.length) {
                    find(node.nodes);
                }
            }
        };

        find(questionNodes);

        return { allQuestionsCompleted, questionWithIncompleteFinding };
    }

    static getLenderFindingResponseProgress(finding: Object) {
        let comment = finding.lenderResponseComment;
        let documents = finding.lenderResponseDocuments;

        if (comment && documents && documents.length) {
            return 'complete';
        } else if (comment || (documents && documents.length)) {
            return 'incomplete';
        }

        return 'empty';
    }

    /**
     * @param {string} finding - the Finding model
     * @param {array} previousFindingGroup - findings from the last level organized by defect area code. We need this to  check for 'adjusted' status.
     * @description determine the current reviewer response, Not Mitigated, Mitigated, Financial Remediation, or Adjust Severity, for a saved Finding
     * @return {string} REVIEWER_RESPONSE_TYPE or null
     */
    static getReviewerFindingResponseAction(finding:Object, previousFindingGroup: Object = {}) {
        let previousFindings = previousFindingGroup[finding.defectAreaCode] || [];
        let previousFinding = previousFindings.find(f => f.selectedCauseCode === finding.selectedCauseCode && f.selectedSourceCode === finding.selectedSourceCode);
        if (finding.ratingCode === RATINGS.MITIGATED.code) {
            return RATINGS.MITIGATED;
        } else if (finding.ratingCode === RATINGS.REMEDIATED.code) {
            return RATINGS.REMEDIATED;
        } else if (previousFinding && (previousFinding.selectedTierCode !== finding.selectedTierCode || previousFinding.ratingCode !== finding.ratingCode)) {
            return REVIEWER_RESPONSE_TYPE.ADJUSTED;
        } else if (finding.commentToLender && finding.commentToLender.length) {
            return REVIEWER_RESPONSE_TYPE.NOT_MITIGATED;
        }

        return null;
    }

    static resetReviewerResponseFindingStatus(finding:Object) {
        finding.reviewerResponseRemediated = null;
        finding.reviewerResponseMitigated = false;
        finding.reviewerResponseAdjusted = null;
        finding.commentToLender = null;
    }

    static isInitialAdhocFinding(finding: Object, currentReview: Object) {
        return (finding.isAdhoc === true && finding.originalReviewLevelId === currentReview.currentReviewLevel.reviewLevelId);
    }

    static isInitialFinding(finding: Object, currentReview: Object) {
        return (finding.isAdhoc === false && (currentReview.currentReviewLevel.type.toLowerCase() === 'initial' || finding.originalReviewLevelId === currentReview.currentReviewLevel.reviewLevelId));
    }

    /**
     * @description check whether a reviewer is allowed to add new finding (ad hoc) in a mitigation steop.
     * @param {Object} review - the current review.
     * @returns {boolean} - boolean
     */
    static allowPostInitialFinding(review: Object) {
        let isReviewVetted = Utils.isReviewVetted(review);
        return !isReviewVetted && !Utils.isInitialReviewAndNotCompleted(review) && review.currentReviewLevel.status.toLowerCase() !== 'completed' && Utils.isCurrentUserAssignedToReviewLevel(review);
    }

    static getActiveResponseActions(finding: Object, previousFinding: Object = null, review: Object) {
        let availableActions = [];
        let previousRating = previousFinding.ratingCode;
        if (FindingUtils.allowPostInitialFinding(review) && !FindingUtils.isInitialAdhocFinding(finding, review) && previousFinding ) {
            availableActions = (previousRating === RATINGS.DEFICIENT.code || previousRating === RATINGS.CONFORMING.code) ? [REVIEWER_RESPONSE_TYPE.ADJUSTED.code] : [RATINGS.MITIGATED.code, RATINGS.REMEDIATED.code, REVIEWER_RESPONSE_TYPE.ADJUSTED.code, REVIEWER_RESPONSE_TYPE.NOT_MITIGATED.code];
        }

        return availableActions;
    }

    static isFindingRespondedByLender(finding: Object) {
        return finding.ratingCode !== 'U' || FindingUtils.getLenderFindingResponseProgress(finding) !== 'empty';
    }

    /**
     * @description check whether the reviewer has responded (not mitigated, mitigated, remediated, or adjusted) to a finding addresed by a lender in mitigation 1+.
     * @param {Object} finding - the current finding.
     * @param {Object} previousFindingGroup - a hash object of all previous findings grouped by the defectAreaCode key.
     * @param {Object} review - the current review.
     * @returns {boolean} - boolean
     */
    static isFindingRespondedByReviewer(finding: Object, previousFindingGroup: Object = {}, review: Object) {

        // If the review is still in 'initial' just return true since we only concern with 'mitigation' review.
        if (Utils.isInitialReviewAndNotCompleted(review) || finding.ratingCode !== RATINGS.UNACCEPTABLE.code || finding.mitigatedAction !== null) {
            return true;
        }

        let respondedAction = FindingUtils.getReviewerFindingResponseAction(finding, previousFindingGroup);
        return  !!respondedAction;
    }

    /**
     * @description return fidnings from the previous level organized by defect area code.
     * @param {Object} review the current review.
     * @param {Array} findingsFromAllCompletedLevels all findings in a review.
     * @returns {Object} defect area object.
     */

    static getFindingGroupsFromPreviousLevel(review: Object, findingsFromAllCompletedLevels: Array = [], checkPendingAcknowledgement: Boolean = false) {
        let findingGroups = {};

        // Standard filter to remove duplicates for vetting or non vetting review.
        let completedReviewLevels = Utils.filterCompletedLevels(review, checkPendingAcknowledgement);
        let previousLevel =  _.last(completedReviewLevels);
        if (previousLevel && previousLevel.reviewLevelId) {
            // Grouped by defect area.
            findingGroups = _.groupBy(findingsFromAllCompletedLevels.filter(f => f.reviewLevelId ===  previousLevel.reviewLevelId), ff => ff.defectAreaCode);
        }
        return findingGroups;
    }

    static requireLenderResponse(finding: Object) {
        return (finding.ratingCode === 'U');
    }

    static computeFindingSeverityForDefectArea(defectArea: Object, findingsByDefectArea: Array, defectAreaSeverities: Array, _RATINGS: Object) {

        let severityFindings = [];
        let severitiesForDefectArea = defectAreaSeverities.filter((severity) => severity.defectAreaCode === defectArea.defectAreaCode);

        severitiesForDefectArea.forEach((severity) => {
            let clone = _.clone(severity);
            clone.amount = 0;
            clone.rating = _RATINGS.CONFORMING.code;
            clone.tier = 0;
            severityFindings.push(clone);
        });

        findingsByDefectArea.forEach((finding) => {
            let severityObject = severityFindings.find((severity) => {
                return (severity.defectSeverityTierCode === finding.selectedTierCode) && (finding.defectAreaCode === severity.defectAreaCode);
            });
            if (severityObject) {
                severityObject.tier = finding.selectedTierCode;
                severityObject.amount += 1;
                severityObject.rating = finding.ratingCode;
            }
        });

        severityFindings.sort(function(a, b) {
            return (a.defectSeverityTierCode) - (b.defectSeverityTierCode);
        });

        return severityFindings;
    }

    //Create a collection of Objects for each defect area
    //Example { defectAreaCode:'BA', rating:'Unacceptable', severityFindings: Array[], title: "Borrower Assets", totalFindings:1}
    //Example { defectAreaCode:'BA', rating:'Unacceptable', severityFindings: Array[], title: "Borrower Assets", totalFindings:1}
    static buildFindingsTotals(currentFindings:[] = null, defectAreasForCurrentReview:[] = null, _defectAreaSeverities:[] = [], _RATINGS:Object) {
        if (!defectAreasForCurrentReview|| !_defectAreaSeverities || !currentFindings) {
            throw new Error('buildFindingsTotals - all parameters are required.');
        }

        let defectAreas = defectAreasForCurrentReview;
        let findingsCollection = [];

        defectAreas.forEach((area) => {

            //blank defect area object
            let rating = _RATINGS.CONFORMING.description;
            let findingsByDefectArea = currentFindings.filter((finding) => finding.defectAreaCode === area.defectAreaCode);


            // Sort finding by rank order.
            findingsByDefectArea = findingsByDefectArea.sort((a, b) => {
                return parseInt(a.rating.rankOrder, 10) < parseInt(b.rating.rankOrder, 10) ? -1 : 1;
            });

            // The  ring of each defect area group is determined by the finding with the lowest rating.rankOrder
            if (findingsByDefectArea.length) {
                rating = findingsByDefectArea[0].rating.description;
            }

            let defectFindingsObj = {};
            defectFindingsObj.title = area.title;
            defectFindingsObj.defectAreaCode = area.defectAreaCode;
            defectFindingsObj.totalFindings = findingsByDefectArea.length;

            defectFindingsObj.severityFindings = FindingUtils.computeFindingSeverityForDefectArea(area, findingsByDefectArea, _defectAreaSeverities, _RATINGS);
            defectFindingsObj.rating = rating;
            findingsCollection.push(defectFindingsObj);
        });

        return findingsCollection;
    }

}
