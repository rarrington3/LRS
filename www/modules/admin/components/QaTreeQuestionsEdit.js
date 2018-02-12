// This file was generated from the component scaffold
// Copyright 2017

import {Component, Input, Output, EventEmitter, SimpleChanges, SimpleChange} from '@angular/core';
import template from './QaTreeQuestionsEdit.html';
import styles from './QaTreeQuestionsEdit.less';
import ArrayUtils from '../../shared/utils/ArrayUtils';
import {ModalSvc} from '../../app/services';
import {QA_TREE_OPERATOR, QA_TREE_OUTCOME, QA_TREE_ANSWER_TYPE, COMMON_OPERATION} from '../../shared/constants';

@Component({
    selector: 'qa-tree-questions-edit',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/api/core/Component-decorator.html
 * @example
 * <qa-tree-edit-questions name="QaTreeQuestionsEdit" (change)="onChange($event)"></qa-tree-edit-questions>
 */
export default class QaTreeQuestionsEdit {

    _modalSvc: ModalSvc;

    newCondition = {
        _comparisonValuesText: '',
        attributeId: '',
        entityName: '',
        fieldName: '',
        operator: '',
        comparisonValues: []
    };

    newQuestion = {
        questionId: '',
        questionNumber: '',
        questionReference: '',
        order: '',
        questionText: '',
        parentQuestionId: '',
        parentQuestionConditionAnswers: [],
        answerType: QA_TREE_ANSWER_TYPE.SINGLE.code,
        potentialAnswers: [
            {
                code: 'YES',
                description: 'YES'
            },
            {
                code: 'NO',
                description: 'NO'
            }
        ],
        answerToTriggerFinding: '',
        conditionsToDisplay: [],
        allowableSourceCodes: [],
        allowableCauseCodes: [],
        allowedSeverityCodes: [],
        questions: []
    };

    operators: Array = [];

    outcomes: Array = [];

    qaTreeOutcome = QA_TREE_OUTCOME;

    @Input() name: string = 'QaTreeQuestionsEdit';

    @Output() changed: EventEmitter = new EventEmitter();

    @Input() code: string = '';

    @Input() questions: Array = [];

    @Input() parentQuestion: any = null;

    @Input() loanAttributes: Array = [];

    @Input() sources: Array = [];

    @Input() causes: Array = [];

    @Input() nest: number = 0;

    @Input() indexes: string = '';

    constructor(modalSvc: ModalSvc) {
        this._modalSvc = modalSvc;
        this.operators = ArrayUtils.toArray(QA_TREE_OPERATOR);
        this.outcomes = ArrayUtils.toArray(QA_TREE_OUTCOME);
    }

    ngOnChanges(changes: SimpleChanges) {
        let questionsChange: SimpleChange = changes['questions'];

        if (questionsChange) {
            let questions = questionsChange.currentValue;
            if (questions && questions.length) {
                questions.forEach(question => {

                    let answerToTriggerExpand = '';

                    // conditions to display sometimes are empty
                    if (!Array.isArray(question.conditionsToDisplay)) {
                        question.conditionsToDisplay = [];
                    }

                    question.conditionsToDisplay.forEach(condition => {
                        if (condition.comparisonValues) {
                            condition._comparisonValuesText = condition.comparisonValues.join(',');
                        }
                    });

                    // set default to nothing
                    question.potentialAnswers.forEach(answer => {
                        answer._outcomeCode = QA_TREE_OUTCOME.NOTHING.code;
                    });

                    // if contains finding
                    if (question.answerToTriggerFinding) {
                        let answer = question.potentialAnswers.find(answer => answer.code === question.answerToTriggerFinding);
                        if (answer) {
                            answer._outcomeCode = QA_TREE_OUTCOME.FINDING.code;
                        }
                    }

                    // if contains children check if answer trigger to expand
                    if (question.questions && question.questions.length) {
                        let childQuestion = question.questions[0];

                        if (question.answerType === QA_TREE_ANSWER_TYPE.SINGLE.code) {
                            if (childQuestion.parentQuestionConditionAnswers &&
                                childQuestion.parentQuestionConditionAnswers.length) {
                                answerToTriggerExpand = childQuestion.parentQuestionConditionAnswers[0];
                            }
                        }
                    }

                    // if contains expansion
                    if (answerToTriggerExpand) {
                        let answer = question.potentialAnswers.find(answer => answer.code === answerToTriggerExpand);
                        if (answer) {
                            answer._outcomeCode = QA_TREE_OUTCOME.EXPAND.code;

                            question._answerToExpand = answer.code;
                            question._answerToExpandText = answer.description;
                        }
                    }
                });
            }
        }
    }

    change(operation: COMMON_OPERATION, item: any) {
        operation = operation || COMMON_OPERATION.UPDATE;
        this.changed.emit([operation, item]);
    }

    changeQuestionNumber(question, newQuestionNumber){
        question._previousQuestionNumber = question.questionNumber;
        question.questionNumber = newQuestionNumber;
        this.change(COMMON_OPERATION.UPDATE, question);
    }

    sortUp(items: Array, item: any) {
        if (ArrayUtils.sortUp(items, item, 'order')) {
            this.change();
        }
    }

    sortDown(items: Array, item: any) {
        if (ArrayUtils.sortDown(items, item, 'order')) {
            this.change();
        }
    }

    remove(items: Array, item: any, message) {
        message = message || 'Are you sure you want to remove the item?';
        this._modalSvc.askForConfirmation(message).first()
            .subscribe(
                () => {
                    ArrayUtils.remove(items, item);
                    this.change(COMMON_OPERATION.REMOVE, item);
                }, () => {
                }
            );
    }

    add(items: Array, newItemTemplate: any) {
        let item = ArrayUtils.add(items, newItemTemplate);
        this.change(COMMON_OPERATION.ADD, item);
    }

    addQuestion() {
        let question = Object.assign({}, this.newQuestion);

        // we need to add the parent question wiring format
        if (this.parentQuestion) {
            question.parentQuestionId = this.parentQuestion.questionId;
            question.parentQuestionConditionAnswers = [
                this.parentQuestion._answerToExpand
            ];
        }

        this.add(this.questions, question);
    }

    changeAnswer(question, answer) {

        let answerFinding = question.potentialAnswers.find(answer => answer.code === question.answerToTriggerFinding);
        let answerExpand = question.potentialAnswers.find(answer => answer.code === question._answerToExpand);

        if (answer._outcomeCode === QA_TREE_OUTCOME.FINDING.code) { // if finding

            // remove any previous selected, only one selection possible
            if (answerFinding) {
                answerFinding._outcomeCode = QA_TREE_OUTCOME.NOTHING.code;
            }

            question.answerToTriggerFinding = answer.code;
        }
        else if (answer._outcomeCode === QA_TREE_OUTCOME.EXPAND.code) { // if expand

            // remove any previous selected, only one selection possible
            if (answerExpand) {
                answerExpand._outcomeCode = QA_TREE_OUTCOME.NOTHING.code;
            }

            // initialize if empty
            if (!question.questions) {
                question.questions = [];
            }

            question._answerToExpand = answer.code;
            question._answerToExpandText = answer.description;
        }
        else if (answer._outcomeCode === QA_TREE_OUTCOME.NOTHING.code) { // if nothing

            // clear the previous selected outcome to see findings
            if (answerFinding && answerFinding.code === answer.code) {
                question.answerToTriggerFinding = '';

                question.alowableSources = []; // clear the findings sources and causes
                question.alowableCauses = [];
            }

            // clear the previous selected outcome to expand questions
            if (answerExpand && answerExpand.code === answer.code) {
                question._answerToExpand = '';
                question._answerToExpandText = '';

                question.questions = []; // clear child questions, no orphans allowed
            }
        }

        this.change();
    }

    trackBy(index) {
        return index;
    }

    changeAttribute(condition) {
        // sync field name & entity name
        let attribute = this.loanAttributes.find(la => la.id === condition.attributeId);
        if (attribute) {
            condition.fieldName = attribute.fieldName;
            condition.entityName = attribute.entityName;
        }
        this.change();
    }

    changeComparisonValue(condition) {
        condition.comparisonValues = condition._comparisonValuesText.split(',');
        this.change();
    }
}
