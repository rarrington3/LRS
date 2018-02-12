// This file was generated from the exports scaffold
// Copyright 2016

/**
 * @example
 * import Utils from 'modules/shared/Utils';
 */

import $ from 'jquery';
import { ReflectiveInjector } from '@angular/core';
import UserSvc from '../app/services/UserSvc';
import { REVIEW_LEVEL_STATUS, REVIEW_LEVEL_TYPE, REVIEW_TYPES, REVIEW_STATUS, CASE_SELECTION_REASONS } from './constants';

let userSvc: UserSvc = null;
export default class Utils {
    constructor() {

    }

    /**
     * @description - Jump to an anchor link and bypass Angular's router.
     * @param {Object} $event - mouse event
     * @param {Object} elementRef - Angular elementRef
     * @param {String} anchorName - the anchor name defined in the template
     * @return {void}
     */
    static handleAnchorClick($event, elementRef, anchorName) {
        if (anchorName && $event && elementRef && elementRef.nativeElement) {
            $event.preventDefault();
            let targetOffset = $(elementRef.nativeElement).find(`a[name='${anchorName}']`).offset();
            let targetTop = targetOffset.top;
            $('html, body').animate({ scrollTop: targetTop }, 500);

        } else {
            throw new Error('Invalid Mouse Event, anchor name or Angular elementRef object');
        }
    }

    static isReviewCompleted(review: Object) {
        if (!review) {
            return false;
        }

        return (review.status.toLowerCase() === REVIEW_STATUS.COMPLETED);
    }

    static isCurrentReviewLevelCompleted(review: Object) {
        if (!review) {
            return false;
        }

        return (review.currentReviewLevel.status.toLowerCase() === REVIEW_LEVEL_STATUS.COMPLETED);
    }

    static isReviewInProgress(review: Object) {
        return !!(review && review.currentReviewLevel && (
            review.currentReviewLevel.status.toLowerCase() === REVIEW_LEVEL_STATUS.ASSIGNED ||
            review.currentReviewLevel.status.toLowerCase() === REVIEW_LEVEL_STATUS.IN_PROGRESS));
    }

    /**
     * @description - Check whether a Review instance is the 'initial' review.
     * @param {Object} review - the Review DTO
     * @return {Boolean} - is readonly
     */
    static isInitialReview(review: Object) {
        if (!review || !review.currentReviewLevel) {
            return false;
        }

        return (review.currentReviewLevel.type.toLowerCase() === REVIEW_LEVEL_TYPE.INITIAL);
    }

    static isInitialReviewAndNotCompleted(review: Object) {
        if (!review || !review.currentReviewLevel) {
            return false;
        }

        return (Utils.isInitialReview(review) && !Utils.isCurrentReviewLevelCompleted(review));
    }


    static isIndemReview(review: Object) {
        if (!review || !review.currentReviewLevel) {
            return false;
        }

        return (review.currentReviewLevel.type.toLowerCase() === REVIEW_LEVEL_TYPE.INDEMNIFICATION);
    }

    static isForcedIndemReview(review: Object) {
        if (!review || !review.currentReviewLevel) {
            return false;
        }

        return (review.currentReviewLevel.type.toLowerCase() === REVIEW_LEVEL_TYPE.FORCED_INDEMNIFICATION);
    }

    // General check used in both Lender and Review modules.
    static isLevelIndemnified(review: Object) {
        return (
            review.currentReviewLevel.type.toLowerCase() === REVIEW_LEVEL_TYPE.INDEMNIFICATION ||
            review.currentReviewLevel.type.toLowerCase() === REVIEW_LEVEL_TYPE.FORCED_INDEMNIFICATION);
    }


    /**
     * @description - Check whether a Review Level is assigned to the current user.
     * @param {Object} review - the Review DTO
     * @return {Boolean} - assigned reviewer
     */
    static isCurrentUserAssignedToReviewLevel(review: Object) {
        if (!review) {
            return false;
        }

        if (!userSvc) {
            let injector = ReflectiveInjector.resolveAndCreate([UserSvc]);
            userSvc = injector.get(UserSvc);
        }

        return (review.currentReviewLevel.reviewerId === userSvc.personnelId);
    }

    //Is this review of type operational or comprehensive
    static isReviewLevelOperational(review: Object) {
        return (review.reviewType.toLowerCase() === REVIEW_TYPES.OPERATIONAL);
    }

    /**
     * @description - Check whether a Review has been vetted .
     * @param {Object} review - the Review DTO
     * @return {Boolean} - has the review being vetted?
     */
    static isReviewVetted(review: Object) {
        if (!review) {
            return false;
        }

        return (Utils.isReviewVetting(review) && (
            review.currentReviewLevel.status.toLowerCase() === REVIEW_LEVEL_STATUS.VETTING_COMPLETED ||
            review.currentReviewLevel.status.toLowerCase() === REVIEW_LEVEL_STATUS.VETTING_COMPLETED_EXCEPTION));
    }

    static isReviewPendingVetting(review: Object) {
        return (Utils.isReviewVetting(review) && (
            review.currentReviewLevel.status.toLowerCase() === REVIEW_LEVEL_STATUS.PENDING_VETTING));
    }

    /**
     * @description - Check whether a Review is in a vetting state. Vetter is reviewing the review.
     * @param {Object} review - the Review DTO
     * @return {Boolean} - is the review in the vetting state.
     */
    static isReviewVetting(review: Object) {
        if (!review) {
            return false;
        }

        return (review.currentReviewLevel.vettingInd === 'Y');
    }

    /**
     * @description - Check whether a Review is Readonly.
     * @param {Object} review - the Review DTO
     * @return {Boolean} - assigned reviewer
     */
    static isReviewReadOnly(review: Object) {
        if (!review) {
            return true;
        }

        let isReviewVetted = Utils.isReviewVetted(review);
        return review.currentReviewLevel.status.toLowerCase() === REVIEW_LEVEL_STATUS.EXCEPTION || isReviewVetted || review.currentReviewLevel.status.toLowerCase() === REVIEW_LEVEL_STATUS.COMPLETED || !Utils.isCurrentUserAssignedToReviewLevel(review);
    }

    static isQuestionTreeReadOnly(review) {
        if (!review) {
            return true;
        }

        // Only the assigned user can edit the Review.
        if (!Utils.isCurrentUserAssignedToReviewLevel(review)) {
            return true;
        }

        // If a review is vetted the qa tree, loan attributes, and finding details are in readonly mode.
        if (Utils.isReviewVetted(review)) {
            return true;
        }

        if (!Utils.isInitialReview(review) && !Utils.isReviewVetting(review)) {
            return true;
        }

        // If this reveiw level is completed, we are in read-only mode
        if (review.currentReviewLevel.status.toLowerCase() === REVIEW_LEVEL_STATUS.COMPLETED) {
            return true;
        }

        // The user can edit the review if it's in the initial review or it's vetting.
        return false;
    }

    /**
     * @description - Check whether a Review is a read only for a lender.
     * @param {Object} review - the Review DTO
     * @return {Boolean} - Is review read only for lender
     */
    static isLenderReviewReadOnly(review: Object) {
        if (!review) {
            return true;
        }

        return !Utils.isLenderLevelValid(review.currentReviewLevel) ||
            review.status.toLowerCase() === REVIEW_STATUS.COMPLETED ||
            review.currentReviewLevel.status.toLowerCase() === REVIEW_LEVEL_STATUS.VETTING_COMPLETED_EXCEPTION;
    }

    static getErrorMessages(response: any): Array {
        if (response._body) {
            let body;
            try {
                body = JSON.parse(response._body);
            } catch (ex) {
                console.error(ex);
            }
            if (body && body.errorMessages && body.errorMessages.length) {
                return body.errorMessages;
            }
        }

        return [];
    }

    /**
     * @description - Strip the duplicated current review level if found
     *  For non-vetted, this happens when a review level is completed, the services returns the same current review level AND the last completed review level
     *  For vetting, the matching completed review level is the vettee's and needs to be discarded
     * @param {Object} review - the current Review DTO
     * @param {Boolean} checkPendingAcknowledgement - Verify that we don't blow away duplicate vetted levels if pending vettee acknowledgement
     * @param {Boolean} lenderValid - set to true if completed levels need to be lender safe
     * @return {Array} - the filtered completedLevels array.
     */

    static filterCompletedLevels(review, checkPendingAcknowledgement: Boolean = false, lenderValid: Boolean = false) {
        let completedLevels = review.completedReviewLevels || [];



        // First, always filter out identical comompleted levels, because we never want them
        completedLevels = completedLevels.filter(level => level.reviewLevelId !== review.currentReviewLevel.reviewLevelId);

        // Next, filter out identical vetted levels (if permitted & vetting) and make sure we are valid for lender as requested
        return completedLevels.filter((level) => {
            let allowLevel = true;

            // 1. Make sure we don't filter out identical vetted levels if pending vettee acknowledgment (need to compare original vs vetted)
            // 2. Otherwise, filter out the identical vetted levels if we are vetting
            if ((checkPendingAcknowledgement && !Utils.isReviewVetted(review)) || (!checkPendingAcknowledgement && Utils.isReviewVetting(review))) {
                allowLevel = !(level.type === review.currentReviewLevel.type && level.iteration === review.currentReviewLevel.iteration);
            }

            // If this level is currently allowed, check to see if we need to validate for lender too
            return allowLevel && lenderValid ? Utils.isLenderLevelValid(level) : allowLevel;
        });
    }

    /*
     * @description - Check whether a Review Level is valid for a lender to see.  NOTE: Review level status of 'Completed' or 'Vetting Completed means the FHA reviewer is done, and the lender can respond
     * @param {Object} level - the ReviewLevel DTO
     * @return {Boolean} - True if lender is allowed to see the review level
     */
    static isLenderLevelValid(level: Object) {
        return (
            level.status.toLowerCase() === REVIEW_LEVEL_STATUS.COMPLETED ||
            level.status.toLowerCase() === REVIEW_LEVEL_STATUS.VETTING_COMPLETED ||
            level.status.toLowerCase() === REVIEW_LEVEL_STATUS.VETTING_COMPLETED_EXCEPTION);
    }

    /**
     * @description return the most recent level that  has been completed and assigned to the lender.
     * @param{object} review the current review.
     * @returns {*} null or the found object.
     */
    static findLenderAuthorizedLevel(review: Object) {
        let lenderAuthorizedLevel = null;
        if (Utils.isLenderLevelValid(review.currentReviewLevel)) {
            lenderAuthorizedLevel = review.currentReviewLevel;
        }
        else if (review.completedReviewLevels) {
            // First filter out completed levels lender isn't authorized to see (e.g. Vettee's completed level)
            let completedLevels = Utils.filterCompletedLevels(review, false, true);
            let levels = completedLevels.slice(0).reverse();
            lenderAuthorizedLevel = levels.find((l) => Utils.isLenderLevelValid(l));
        }
        return lenderAuthorizedLevel;
    }

    static isQCreview(review: Object) {
        if (!review || !review.currentReviewLevel) {
            return false;
        }

        return (review.originalQcReviewId && review.originalQcFinalReviewLevelId);
    }

    static isQCmitigatedReview(review: Object) {
        if (!review || !review.currentReviewLevel) {
            return false;
        }

        return (Utils.isQCreview(review) && review.currentReviewLevel.type.toLowerCase() === REVIEW_LEVEL_TYPE.MITIGATION && review.currentReviewLevel.iteration < 2);
    }

    /**
     * Check to see if this case can be submitted
     * We don't allow submitting a case if there is any activity w/o a review (i.e. a selection is present)
     * We also don't allow submitting for any existing review that is not cancelled or completed
     * We also take the opportunity to mark each caseActivty if it is the one blocking the submission of the case
     * @param {object} caseToSubmit The case object we are checking
     * @param {string} reviewReason (optional) THe review reason used for this case selection
     * @returns {boolean} Returns true if we are allowed to submit
     */
    static canSubmitCaseSelection(caseToSubmit, reviewReason) {
        let canSubmit = true;
        if (caseToSubmit.caseActivity && caseToSubmit.caseActivity.length) {
            caseToSubmit.caseActivity.forEach(function (caseActivity) {
                // Don't allow selection if there is only a pending selection that isn't cancelled (NOTE: if there is no review, there is a selection)
                if (!caseActivity.reviewStatus) {

                    // Check to make sure this isn't cancelled (if it is, we allow another swing at it)
                    if (caseActivity.selectionStatus !== 'Cancelled') {
                        canSubmit = false;
                        caseActivity.blocksSubmit = true;
                    }

                    // Used to display status
                    caseActivity.displayStatus = 'Pending Review (' + caseActivity.selectionStatus + ')';
                } else {

                    // Check for cancelled/completed once
                    let isCancelled = caseActivity.reviewStatus.toLowerCase() === REVIEW_STATUS.CANCELLED;
                    let isCompleted = caseActivity.reviewStatus.toLowerCase() === REVIEW_STATUS.COMPLETED;

                    // Used to display status
                    caseActivity.displayStatus = isCancelled ? 'Review Cancelled' :
                        isCompleted ? 'Review Completed' :
                            caseActivity.reviewStatus.toLowerCase() === REVIEW_STATUS.UNDER_REVIEW ? 'Under Review (In Progress)' : 'Under Review (' + caseActivity.reviewStatus + ')';

                    // If attempting a QC review, don't allow submitting when there is not a completed review
                    if (reviewReason === CASE_SELECTION_REASONS.REVIEW_LOCATION_QC && !isCompleted) {
                        canSubmit = false;
                        caseActivity.blocksSubmit = true;
                    }
                    // Otherwise, don't allow submitting if we have an active review (i.e. not cancelled or completed)
                    else if (!isCancelled && !isCompleted) {
                        canSubmit = false;
                        caseActivity.blocksSubmit = true;
                    }
                }
            });
        }
        return canSubmit;
    }

    static sortCaseActivty(caseToSort) {
        if (caseToSort.caseActivity && caseToSort.caseActivity.length) {
            caseToSort.caseActivity = caseToSort.caseActivity.sort((a, b) => {
                return new Date(a.selectionDate) - new Date(b.selectionDate);
            });
        }
    }

    static getReviewersByLocation(reviewers: Array, reviewLocationId: String) {
        return reviewers.filter(reviewer => {
            return (reviewer.locationId && reviewLocationId &&
                reviewer.locationId === reviewLocationId);
        });
    }

    static getFHAReviewLevelStatus(review: Object, currentReviewLevel: Object = null) {
        let level = currentReviewLevel || review.currentReviewLevel;
        return (level.status.toLowerCase() === REVIEW_LEVEL_STATUS.COMPLETED && review.status.toLowerCase() !== REVIEW_STATUS.COMPLETED) ? 'Pending Lender Response' : level.status;
    }

    static isReviewPendingLenderResponse(review: Object, isLender: Boolean = false) {

        // Show vetting compelted level in 'Response Requests' in lender.
        let status = review.status.toLowerCase();
        if (isLender) {
            return status === REVIEW_STATUS.PENDING_LENDER_RESPONSE;
        }

        return !Utils.isReviewVetted(review) && status === REVIEW_STATUS.PENDING_LENDER_RESPONSE;
    }

    static isReviewFHAactive(review: Object, isLender: Boolean = false) {

        // Don't show vetting completed level under Under Review by FHA in lender.
        let status = review.status.toLowerCase();
        if (isLender) {
            return status !== REVIEW_STATUS.PENDING_LENDER_RESPONSE;
        }

        return Utils.isReviewVetted(review) || status !== REVIEW_STATUS.PENDING_LENDER_RESPONSE;
    }

    static addForceEscalationWarning(review: Object, isLender: Boolean = false) {

        //Add Force Escalation  warning message to display in UI.
        let allLevels = [...review.completedReviewLevels, review.currentReviewLevel];
        let totalLevels = allLevels.length - 1;
        allLevels.forEach((l, i) => {
            l._warning = null;
            let nextLevel = (i < totalLevels) ? allLevels[i + 1] : null;
            if (l.forceEscalationDate && (!isLender || (isLender && nextLevel && Utils.isLenderLevelValid(nextLevel))) ) {
                l._warning = (isLender) ? 'Review Level not completed due to Forced Escalation by FHA' : `Review Level not completed due to Forced Escalation by ${l.updatedById}`;
            }
        });
    }
}
