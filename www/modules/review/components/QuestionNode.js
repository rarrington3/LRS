// This file was generated from the component scaffold
// Copyright 2016

import {Component, Input} from '@angular/core';
import {Router}  from '@angular/router';
import template from './QuestionNode.html';
import styles from './QuestionNode.less';
import {QA_TREE_ANSWER_STATUS} from '../constants';
import QuestionNodeVM from '../viewModels/QuestionNodeVM';
import ReviewService from '../services/ReviewService';
import FindingsService from '../services/FindingsService';
import GlobalStateSvc from '../services/GlobalStateSvc';
import {FormBuilder,
    FormGroup,
    FormControl} from '@angular/forms';
import ModalSvc from '../../app/services/ModalSvc';
import FindingUtils from '../FindingUtils';
import Utils from '../../shared/Utils';
import QATreeUtils from '../QATreeUtils';
import ConditionToDisplay from '../viewModels/ConditionToDisplay';
import _ from 'lodash';

@Component({
    selector: 'question-node-view',
    template: template,
    styles: [styles]
})

/**
 * @see https://angular.io/docs/ts/latest/api/core/Component-decorator.html
 * @example
 * <question-node-view name="QuestionNodeView" (change)="onChange($event)"></question-node-view>
 */
export default class QuestionNode {
    _node: Object = {};

    /** The Form Group instance for the question with checkboxes (Multiple) answerType.
     * We are using Form Group to easily manage and bind data for a question with checkbox inputs.
     */
    multipleTypeFormGroup: FormGroup;

    _existingFinding: Object = null;

    isFindingTriggerReadonly:Boolean = false;


    isDepth(depth) {
        return (depth === this.node.depth);
    }

    /**
     * The isReadOnly value is inherited from the parent node. Readonly mode is used when the Review
     * is not initial or when the question tree is used in the Finding Source/Cause view.
     */
    @Input()
    isReadOnly: Boolean = false;

    @Input()
    showEditFinding: Boolean = true;

    constructor(reviewService: ReviewService, findingsService: FindingsService, globalStateSvc: GlobalStateSvc, formBuilder: FormBuilder, router: Router, modalSvc: ModalSvc) {
        this.reviewService = reviewService;
        this.findingsService = findingsService;
        this.globalStateService = globalStateSvc;
        this._formBuilder = formBuilder;
        this._router = router;
        this._modalSvc = modalSvc;
    }

    ngOnInit() {
        this._existingFinding = FindingUtils.findFindingByQuestionId(this.node.questionId, this.findingsService.findingsForCurrentDefectArea);
        this._addFormControls(this.node);

        // The edit finding feature only editable when the Review is 'initial' and not 'completed'.
        this.isFindingTriggerReadonly = Utils.isQuestionTreeReadOnly(this.reviewService.currentReview);
    }

    trackByQuestionId(index, node) {
        return node.questionId;
    }

    /**
     * @description - Set the QuestionNodeVM instance this view is bound to.
     * @param {QuestionNodeVM} node
     */
    @Input()
    set node(node: QuestionNodeVM) {
        this._node = node;
    }

    get node() {
        return this._node;
    }

    _addFormControls(node) {
        if (node.answerType === 'Multiple') {
            let group = {};
            let savedValues = node.answer.answers || [];
            let formControl = new FormControl(savedValues.map(v => v.code));
            group[node.questionId] = formControl;

            if (this.formSubscription) {
                this.formSubscription.unsubscribe();
            }

            this.multipleTypeFormGroup = this._formBuilder.group(group);
            this.formSubscription = this.multipleTypeFormGroup.valueChanges.subscribe((data) => {
                if (data) {
                    this.onAnswerClick(null);
                }
            });
        }
    }

    /**
     * @description - Set the clicked state on the answer button.
     * @param {string} code - the 'answer' in from the template. (yes or no).
     * @return {boolean} - a boolean representing if the button is selected or not.
     */
    isBtnSelected(code: string) {
        return !!(this.node.findAnswerByCode(code));
    }

    /**
     * @description - question save handler.
     * @param {object} answer  - the Answer DTO.
     * @return {void}
     */
    onAnswerClick(answer: Object) {

        // Ignore if the user selects the  same answer again.
        let userAnswerCode = (answer) ? answer.code.toLowerCase() : {};
        let hasAnswer = (this.node.answer.answers.find((a) => a.code.toLowerCase() === userAnswerCode));
        if (hasAnswer) {
            // Do nothing
            return;
        }

        let answeredChildNodes = QATreeUtils.findAllChildAnsweredQuestions(this.node);

        if (answeredChildNodes && answeredChildNodes.length) {
            _.defer(() => this._showCascadeDeleteModal(answeredChildNodes, answer));
        } else {
            this._processAnswerClick(answer);
        }
    }

    onEditFindingClick() {
        if (!this.isFindingTriggerReadonly) {
            this.navigateToSourceCause();
        }
    }

    onReset() {
        this._addFormControls(this.node);
    }

    getAllAnswerValues() {
        return this.node.answer.answers.map((a) => a.description).join(',');
    }

    showRemoveQuestionFromFindingWarning() {
        let additionalMessage = (!this._existingFinding.questionIds.length) ? '<p>Because this finding is not associated with any other questions, it will be removed completely</p>' : '';
        return this._modalSvc.askForConfirmation(
            `<p>Changing this answer will remove the finding associated with this question.</p> ${additionalMessage}`,
            'Edit Finding');
    }

    navigateToSourceCause() {
        FindingUtils.navigateToFindingSourceCause(this._router, this.globalStateService, this.reviewService, this.findingsService, this.node);
    }

    _saveAnswer(singleAnswer: Object) {

        if (singleAnswer) {
            this.node.answer.answers = [singleAnswer];
            this._existingFinding = FindingUtils.findFindingByQuestionId(this.node.questionId, this.findingsService.findingsForCurrentDefectArea);
        }

        // Save the current Answer;
        this.reviewService.saveQuestionAnswer(this.node.answer).first().subscribe((returnedAnswer) => {

            //  TEST CODE to test with mock data. Just assign the same answer again to trigger the status change.
            // this.node.answer = _.defaults({}, this.node.answer);

            if (returnedAnswer && returnedAnswer.questionId) {
                this.node.answer = returnedAnswer;
            }

            // Re-load the list of answers once save is complete
            this.reviewService.getAnswersForCurrentArea();

            // Automatically navigate the user to the Finding Source/Cause view if the question has triggered a finding.
            // We do this to prevent a scenario where we might end up with many instances abandoned finding triggered question.

            // TEST CODE
            //this._existingFinding = null;

            if (this.node.answerStatus === QA_TREE_ANSWER_STATUS.FINDING_TRIGGERED && !this._existingFinding) {
                this.navigateToSourceCause();
                return;
            }

            // Update the UI.
            if (this.node.answerType === 'Multiple') {
                this.onReset();
            }
        });
    }

    _showCascadeDeleteModal(answeredChildNodes: Array = [], selectedAnswer: Object) {

        let filteredNodes = answeredChildNodes;

        if (this.node.answerType === 'Multiple') {
            let selectedCodes = this.multipleTypeFormGroup.controls[this.node.questionId].value || [];
            let selectedAnswers = selectedCodes.map((code) => this.node.potentialAnswers.find((pAnswer) => pAnswer.code.toLowerCase() === code.toLowerCase()));

            // The top level displayed nodes that needs to be hidden based on the current choices.
            filteredNodes = answeredChildNodes.filter((n) => n.parentQuestionId === this.node.questionId && !ConditionToDisplay.checkConditionsToDisplayForQuestionAnswer(selectedAnswers, n));
        }

        if (filteredNodes.length ) {
            this._modalSvc.askForConfirmation(
                'Changing this answer will cause children questions to be removed from finding(s) and potentially findings(s) to be removed. Proceed?',
                'Delete Child Question Answers and Findings').first().subscribe(
                () => {

                    // Find all child nodes and delete any existing answers.
                    let allAssociatedNodes = [];
                    filteredNodes.forEach( (n => {

                        allAssociatedNodes.push(n);
                        let childQuestions = QATreeUtils.findAllChildAnsweredQuestions(n, []);
                        if (childQuestions.length) {
                            allAssociatedNodes.push(...childQuestions);
                        }
                    }));

                    QATreeUtils.deleteQuestionsAnswerAndFindingSequence(allAssociatedNodes, FindingUtils,   this.reviewService, this.findingsService).subscribe(
                        (data) => { console.log('Deleted child question answer!', data); },
                        (/*error*/) => {
                        },
                        (/*complete*/) => {
                            console.log('Completed deleting all orphaned answers and findings');

                            // Update the current review level rating.
                            this.reviewService.refreshCurrentReview().subscribe(() => {

                                // Update the parent question.
                                this._processAnswerClick(selectedAnswer);
                            });
                        });
                },
                () => {

                    // Restore the answer on cancel
                    if (this.node.answerType === 'Multiple' ) {
                        this.onReset();
                    }
                });
        } else {

            // Update the parent question.
            this._processAnswerClick(selectedAnswer);
        }
    }

    _processAnswerClick(answer: Object) {
        // Handle a special case where a question has multi select answers.
        if (this.node.answerType === 'Multiple') {
            let selectedCodes = this.multipleTypeFormGroup.controls[this.node.questionId].value;
            this.node.answer.answers = selectedCodes.map((code) => this.node.potentialAnswers.find((pAnswer) => pAnswer.code.toLowerCase() === code.toLowerCase()));
            this._saveAnswer();
        } else {

            if (this.node.answerStatus === QA_TREE_ANSWER_STATUS.FINDING_TRIGGERED && this._existingFinding) {

                // Show a warning if the user answers a question possibly associated with a finding.
                this.showRemoveQuestionFromFindingWarning().first().subscribe(
                    () => {
                        this.node.answer.answers = [answer];
                        if (this._existingFinding) {

                            // Remove the question from the existing finding.
                            let questionIds = this._existingFinding.questionIds;
                            this._existingFinding.questionIds = questionIds.filter(q => q !== this.node.questionId);
                            this.reviewService.saveOrDeleteFindings([this._existingFinding]).first().subscribe(() => {

                                // Update the current review level rating.
                                this.reviewService.refreshCurrentReview().subscribe();

                                this._existingFinding = null;
                            });
                        }

                        this._saveAnswer(answer);
                    },
                    () => { });
            } else {
                this._saveAnswer(answer);
            }
        }
    }
}
