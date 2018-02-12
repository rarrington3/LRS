// This file was generated from the view scaffold
// Copyright 2016

import {Component} from '@angular/core';
import template from './EditFindingSourceCause.html';
import styles from './EditFindingBase.less';
import EditFindingBase from './EditFindingBase';
import _ from 'lodash';
import FindingUtils from '../FindingUtils';
import QATreeUtils from '../QATreeUtils';
import QuestionNodeVM from '../viewModels/QuestionNodeVM';

@Component({
    selector: 'edit-finding-source-cause',
    template: template,
    styles: [styles],
    directives: []
})
/**
 * @see https://angular.io/docs/ts/latest/guide/router.html
 */
export default class EditFindingSourceCause extends EditFindingBase {

    _questionId:string = null;
    updatingFinding:Boolean = false;

    ngOnInit() {
        super.ngOnInit();

    }

    ngOnDestroy() {
        super.ngOnDestroy();
    }

    /**
     * @description - Override method.
     * @param {object} params parameters
     * @return {void}
     */
    onRouteChanged(params) {
        this._questionId = params.questionId;
        super.onRouteChanged(params);
    }

    /*
     * @description - Override method.
     * @return {void}
     */
    refreshFinding() {

        // Find a Finding by question ID
        this.finding = FindingUtils.findFindingByQuestionId(this._questionId, this.findingsService.currentFindings) || {};

        // TEST CODE
        //this.finding = {};
        console.log('Edit Finding Source Cause View - Found existing finding ', this.finding);

        super.refreshFinding();
    }

    /*
     * @description - Override method.
     * @return {void}
     */
    updateQuestionsAndAnswers() {

        // Build the related questions.
        if (this.reviewService.questionsForCurrentArea && this.reviewService.answersForCurrentArea) {

            // Find the associated questions and build a question tree so we can easily render the question tree in this component's teamplate.
            this.question = this.reviewService.questionsForCurrentArea.find((q) => q.questionId === this._questionId) || {};
            let parentQuestions = QATreeUtils.findQuestionParents(this.question, this.reviewService.questionsForCurrentArea);
            this.questions = QATreeUtils.buildNodes(this.reviewService, this.findingSource, [...parentQuestions, this.question], this.reviewService.answersForCurrentArea);
            this.filterSourcesAndCausesByQuestion(this.question);
        }
    }

    disableSubmit(form) {

        // Don't disable the form if we have an existing finding.
        if (this.finding.findingId) {
            return false;
        }

        return !(!_.isUndefined(form.value.defectCauseCode) && !_.isUndefined(form.value.defectSourceCode));
    }

    saveProgress(newOrExistingFinding:Object, nextAction:Object) {
        if (!newOrExistingFinding || !nextAction) {
            throw ( new Error('saveProgress - a new or existing finding and the nextAction callback are required!'));
        }
        // Make sure the added question id does not exist in other existing Findings. If it does, remove the question id from the other existing finding.
        let findingsContainQuestionId = FindingUtils.findAllFindingsByQuestionId(this._questionId, true, newOrExistingFinding.findingId, this.findingsService.currentFindings);

        // PUT or POST the affect new or existing Finding
        // TODO: Show activity indicator here in case there's a long delay?
        this.reviewService.saveOrDeleteFindings([...findingsContainQuestionId, newOrExistingFinding]).subscribe((findings) => {

            // Grab the updated finding which should be available in the findings collection.
            let finding = FindingUtils.findFindingByQuestionId(this._questionId, findings);
            nextAction(finding);
        });
    }

    onNext(form) {
        this.updatingFinding = true;

        let nextAction = (_finding) => {

            let questionTreeRoute = this.router.url.slice(0, this.router.url.indexOf('/question'));

            // Navigate to the finding detail view (screen 2b) after we had saved progress.
            this.router.navigate([`${questionTreeRoute}/finding/${_finding.findingId}/initialdetails`]);
        };

        // Find an existing finding based on the source and cause selections or create a new finding.
        let newOrExistingFinding = FindingUtils.findFindingBySourceAndCause(form.value.defectSourceCode, form.value.defectCauseCode, this.defectAreaCode, this.findingsService.currentFindings);

        // Simply go to Finding Details without saving any progress if the form is not dirty and there's an existing finding.
        if (!form.dirty && newOrExistingFinding && newOrExistingFinding.findingId) {
            nextAction(newOrExistingFinding);
            return;
        }

        if (!newOrExistingFinding) {
            newOrExistingFinding = FindingUtils.createNewFinding(this.globalService.currentReviewLevelId, this.globalService.currentDefectAreaCode);

            // Update the source and cause codes
            newOrExistingFinding.selectedSourceCode = form.value.defectSourceCode;
            newOrExistingFinding.selectedCauseCode = form.value.defectCauseCode;
        }

        let preSaveProgress = (delayDuplicateFindingModal:Boolean = false) => {

            // Add the current question id to the new or existing finding.
            newOrExistingFinding.questionIds = _.unique([...newOrExistingFinding.questionIds, this._questionId]);

            let currentQuestions = this.reviewService.questionsForCurrentArea;
            let currentAnswers = this.reviewService.answersForCurrentArea;

            // If we have a finding associated with the current question then we don't need to launch the 'Duplicate Finding..'' modal since we already launched the 'Edit Finding' modal.
            let hasQuestionFindingId = this.finding.findingId;

            // Show the duplicate source/cause alert if there's an existing finding with matching source and cause codes.
            if (newOrExistingFinding.findingId && currentAnswers && currentQuestions && !hasQuestionFindingId) {

                // Build the question and answer text string to be displayed in the duplicate alert modal.
                let displayedQuestionIds = newOrExistingFinding.questionIds.filter(qId => qId !== this._questionId);
                let displayedQuestionsAndAnswers = displayedQuestionIds.map((q) => {
                    let foundQuestion = currentQuestions.find(cq => cq.questionId === q);
                    let foundAnswer = currentAnswers.find(a => a.questionId === q);
                    if (foundQuestion && foundAnswer) {
                        let answerValues = foundAnswer.answers.map(a => a.description).join(',');
                        return `<p class="modal-highlight"><span class="modal-bold">${foundQuestion.questionReference}</span> <span>${foundQuestion.questionText}</span></br>Your Answer: <span class="modal-bold">${answerValues.toUpperCase()}</span> </p>`;
                    }
                    return null;
                });

                let questionTxt = (displayedQuestionsAndAnswers.length) ? 'questions' : 'question';
                let display = () => {
                    this.modalSvc.askForConfirmation(
                        `<p>You have already logged a finding with the same source and cause to the following ${questionTxt}: ${displayedQuestionsAndAnswers.join('')} Please update the comment and change the severity if necessary</p>`,
                        'DUPLICATE FINDING FOUND').first().subscribe(
                        () => this.saveProgress(newOrExistingFinding, nextAction),
                        () => this.updatingFinding = false);
                };

                // Delay showing this modal, in case we had already launched another modal, so the last modal completes the fade out transition.
                if (delayDuplicateFindingModal) {
                    _.delay(display, 600);
                } else {
                    display();
                }
            } else {
                this.saveProgress(newOrExistingFinding, nextAction);
            }
        };

        /**
         * NOTE: 'this.finding' is the Finding model that is tied to the current question id.
         * 'newOrExistingFinding' could be an existing finding that has the same user selected source and cause codes.
         */
        if (this.finding && this.finding.findingId && (form.value.defectSourceCode !== this.finding.selectedSourceCode || form.value.defectCauseCode !== this.finding.selectedCauseCode)) {

            this.modalSvc.askForConfirmation(
                '<p>Do you want to modify the source and/or cause for this question`s finding? If so, the tier & comment text currently associated with the finding might be removed </p>',
                'Edit Finding').first().subscribe(
                () => preSaveProgress(true),
                () => {

                    // Reset to the original source and cause codes if we have them
                    if (this.finding && this.finding.findingId) {
                        form.setValue({
                            defectSourceCode: this.finding.selectedSourceCode,
                            defectCauseCode: this.finding.selectedCauseCode
                        });
                    }
                    this.updatingFinding = false;
                });

        } else {
            preSaveProgress();
        }
    }

    onCancelFinding() {

        // Cancel will delete the answer that triggered the question. NOTE: The cancel button will be hidden if we have an existing finding.
        this.modalSvc.askForConfirmation(
            `<p>Cancel Finding will remove the answer for this question.</p> ${this.question.questionReference}`,
            'Edit Finding')
            .first().subscribe(() => {
            // The FindingUtils only accepts the QuestionVM type because it's better this way.
                let questionVM = QuestionNodeVM.parse(this.question, this.reviewService.answersForCurrentArea);
                FindingUtils.deleteIncompleteQuestionAnswerAndFinding(questionVM, this.reviewService, this.findingsService).subscribe((/*complete*/) => this.onRevisitQuestion());
            }, () => {});
    }

}
