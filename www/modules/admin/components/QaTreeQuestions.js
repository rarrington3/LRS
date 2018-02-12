// This file was generated from the component scaffold
// Copyright 2017

import {Component, Input, SimpleChanges, SimpleChange} from '@angular/core';
import template from './QaTreeQuestions.html';
import styles from './QaTreeQuestions.less';
import {ModalSvc} from '../../app/services';
import ArrayUtils from '../../shared/utils/ArrayUtils';
import {QA_TREE_OPERATOR, QA_TREE_OUTCOME, QA_TREE_ANSWER_TYPE} from '../../shared/constants';

@Component({
    selector: 'qa-tree-questions',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/api/core/Component-decorator.html
 * @example
 * <qa-tree-questions name="QaTreeQuestions" (change)="onChange($event)"></qa-tree-questions>
 */
export default class QaTreeQuestions {

    _operatorsDict: {} = {};

    _causesDict: {} = {};

    _sourcesDict: {} = {};

    modalSvc: ModalSvc;

    operators: Array = [];

    outcomes: Array = [];

    qaTreeOutcome = QA_TREE_OUTCOME;

    @Input() name: string = 'QaTreeQuestions';

    @Input() code: string = '';

    @Input() questions: Array = [];

    @Input() parentQuestion: any = null;

    @Input() loanAttributes: Array = [];

    @Input() sources: Array = [];

    @Input() causes: Array = [];

    @Input() nest: number = 0;

    @Input() indexes: string = '';

    constructor(modalSvc: ModalSvc) {
        this.modalSvc = modalSvc;

        this.operators = ArrayUtils.toArray(QA_TREE_OPERATOR);
        this.outcomes = ArrayUtils.toArray(QA_TREE_OUTCOME);

        this._operatorsDict = ArrayUtils.toDictionary(this.operators, 'code');
    }

    ngOnChanges(changes: SimpleChanges) {
        let sourcesChange: SimpleChange = changes['sources'];
        let causesChange: SimpleChange = changes['causes'];
        let questionsChange: SimpleChange = changes['questions'];

        if (sourcesChange) {
            let sources = sourcesChange.currentValue;
            if (sources && sources.length) {
                this._sourcesDict = ArrayUtils.toDictionary(sources, 'code');
            }
        }

        if (causesChange) {
            let causes = causesChange.currentValue;
            if (causes && causes.length) {
                this._causesDict = ArrayUtils.toDictionary(causes, 'code');
            }
        }

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
                        let operator = this._operatorsDict[condition.operator];
                        if (operator) {
                            condition._operatorText = operator.text;
                        }
                        if (condition.comparisonValues) {
                            condition._comparisonValuesText = condition.comparisonValues.join();
                        }
                    });

                    question._allowableSources = [];
                    question.allowableSourceCodes.forEach(code => {
                        let source = this._sourcesDict[code];
                        if (source) {
                            question._allowableSources.push(source);
                        }
                    });

                    question._allowableCauses = [];
                    question.allowableCauseCodes.forEach(code => {
                        let cause = this._causesDict[code];
                        if (cause) {
                            question._allowableCauses.push(cause);
                        }
                    });

                    // set default to nothing
                    question.potentialAnswers.forEach(answer => {
                        answer._outcomeCode = QA_TREE_OUTCOME.NOTHING.code;
                        answer._outcomeText = QA_TREE_OUTCOME.NOTHING.text;
                    });

                    // if contains finding
                    if (question.answerToTriggerFinding) {
                        let answer = question.potentialAnswers.find(answer => answer.code === question.answerToTriggerFinding);
                        if (answer) {
                            answer._outcomeCode = QA_TREE_OUTCOME.FINDING.code;
                            answer._outcomeText = QA_TREE_OUTCOME.FINDING.text;
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
}
