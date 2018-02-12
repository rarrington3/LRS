// This file was generated from the view scaffold
// Copyright 2016

import {Component} from '@angular/core';
import template from './InitialFindingDetails.html';
import styles from './EditFindingBase.less';
import EditFindingBase from './EditFindingBase';
import FindingUtils from '../FindingUtils';
import QATreeUtils from '../QATreeUtils';
import _ from 'lodash';

@Component({
    selector: 'edit-finding-details',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/guide/router.html
 */
export default class InitialFindingDetails extends EditFindingBase {

    selectedTierCode: String = null;
    findingId: string = null;

    // This flag is used to improve UX wihout it you'll see the empty textarea with error briefly before data is loaded.
    formActive: Boolean = false;

    ngOnInit() {
        super.ngOnInit();

    }

    ngOnDestroy() {
        super.ngOnDestroy();
    }


    /*
     * @description - Override method.
     * @param {object} param - parameters
     * @return {void}
     */
    onRouteChanged(params) {
        this.findingId = params.findingId;
        super.onRouteChanged(params);
    }


    /*
     * @description - Override method.
     * @return {void}
     */
    updateQuestionsAndAnswers() {

        // Build the related questions.
        if (this.reviewService.questionsForCurrentArea && this.reviewService.answersForCurrentArea) {
            let questions = [];
            let questionIds = (this.finding && this.finding.questionIds) ? this.finding.questionIds : [];

            for (let questionId of questionIds) {
                let foundQuestion = this.reviewService.questionsForCurrentArea.find(q => q.questionId === questionId);
                if (foundQuestion) {
                    questions.push(foundQuestion);

                    // Also add the parent questions
                    let foundParentQuestions = QATreeUtils.findQuestionParents(foundQuestion, this.reviewService.questionsForCurrentArea);
                    if (foundParentQuestions) {
                        questions.push(...foundParentQuestions);
                    }
                }
            }

            // Remove duplicate parent  questions.
            questions = _.uniq(questions, q => q.questionId);

            // Build the question node tree so we can easily render the question tree in this component's teamplate.
            this.questions = QATreeUtils.buildNodes(this.reviewService, this.findingSource, questions, this.reviewService.answersForCurrentArea);
            this.formActive = true;
        }
    }

    /*
     * @description - Override method.
     * @return {void}
     */
    refreshFinding() {
        this.finding = FindingUtils.findFindingById(this.findingId, this.findingsService.currentFindings) || {};
        if (this.finding && this.finding.isAdhoc === true) {
            this.isAdhoc = true;
            this.updateReadonly();
        }

        this.updateQuestionsAndAnswers();
        super.refreshFinding();

        // Using _.defer to fix an issue where ocasionally the custom select will not bind properly when the view is re rendered.
        this.selectedTierCode = null;
        _.defer(() => {
            this.selectedTierCode = this.finding.selectedTierCode;
        });
    }

    onCancelNewAdhocFinding() {
        if (this.question) {
            throw (new Error('Cancel Adhoc Finding - An Adhoc Finding should not be associated with any question!'));
        }

        this.finding.questionIds = [];

        this.modalSvc.askForConfirmation(
            '<p>Cancel Finding will remove this Adhoc Finding.</p>',
            'Cancel Adhoc Finding')
            .first().subscribe(() => {

                this.reviewService.deleteFinding(this.finding.findingId).subscribe(() => {
                    this.findingsService.loadAllFindings(this.reviewService.currentReview.reviewId, this.reviewService.currentReview.currentReviewLevel.reviewLevelId, true).subscribe( () => {
                        this.router.navigate([`/review/${this.reviewService.currentReview.reviewId}/wrapup`]);
                    });
                });
            }, () => {});
    }

    onSaveFinding(/*form*/) {
        if (this.selectedTierCode) {
            this.finding.selectedTierCode = this.selectedTierCode;
        }

        // Save the finding.
        this.reviewService.saveOrDeleteFindings([this.finding]).subscribe(() => {

            // Refresh the current review so we get the updated ratingCode of the current level. This should not preload question and answer data.
            this.reviewService.refreshCurrentReview().subscribe(() => {
                this.onRevisitQuestion();
            });
        });
    }
}
