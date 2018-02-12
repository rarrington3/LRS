// This file was generated from the exports scaffold
// Copyright 2016

/**
 * @example
 * import QuestionNodeVM from 'modules/review/viewModels/QuestionNodeVM';
 * let questionNode = new QuestionNode();
 */
import {QA_TREE_ANSWER_STATUS} from '../constants';
import ConditionToDisplay from './ConditionToDisplay';

export default class QuestionNodeVM {
    nodes: Array = [];
    answerType: string = 'single';
    depth: number;
    parentNode: QuestionNodeVM;
    hidden: Boolean = true;
    questionId: string;
    parentQuestionId: string;
    questionReference: string;
    potentialAnswers: string;
    questionText: string;
    answerStatus: string = QA_TREE_ANSWER_STATUS.UNANSWERED;
    _answer: Object;
    _questionDTO: Object;
    _conditionsToDisplay: Array = [];
    conditionToDisplayQuestion: ConditionToDisplay = null;

    constructor(questionDTO: Object) {
        if (questionDTO) {
            this.questionDTO = questionDTO || {};
        }
    }

    get questionDTO() {
        return this._questionDTO;
    }

    /**
     * @description - set the returned questionDTO.
     * @param {Object} questionDTO - question DTO.
     */
    set questionDTO(questionDTO: Object) {
        this._questionDTO = questionDTO;
        this.questionId = questionDTO.questionId;
        this.parentQuestionId = questionDTO.parentQuestionId;
        this.questionReference = questionDTO.questionReference;
        this.potentialAnswers = questionDTO.potentialAnswers;
        this.questionText = questionDTO.questionText;
        this.answerType = questionDTO.answerType;

        // Convert the 'conditionsToDisplay' Object to the typed 'ConditionsToDisplay' so we can easily debug and test conditions later.
        let conditions = questionDTO.conditionsToDisplay || [];

        // Loan attribute fields.
        this.conditionsToDisplay = conditions.map((c) => { return (c instanceof ConditionToDisplay) ? c : new ConditionToDisplay(c); });

        // This object is used to compare  the 'parentQuestionConditionAnswers' with the parent quesiton answer(s)
        let comparisonValues = questionDTO.parentQuestionConditionAnswers || [];
        let defaultOperator = 'in';
        this.conditionToDisplayQuestion = new ConditionToDisplay({operator: defaultOperator, comparisonValues: comparisonValues});

        if (this.isChildNode()) {
            this.hidden = true;
        } else {
            this.hidden = false;
        }

    }

    isChildNode() {
        return this.parentQuestionId;
    }

    hasChildNodes() {
        return (this.nodes && this.nodes.length);
    }

    _checkAllParentAnswers(node:QuestionNodeVM, displayChildren:Boolean = false) {
        if (!node.parentNode || ConditionToDisplay.checkConditionsToDisplayForQuestionAnswer(node.parentNode.answer.answers, node)) {
            displayChildren = true;
        }
        else if (node.parentNode.answerStatus === QA_TREE_ANSWER_STATUS.UNANSWERED) {
            return false;
        }

        if (node.parentNode) {
            this._checkAllParentAnswers(node.parentNode, displayChildren);
        }

        return displayChildren;
    }
    /**
     * @description - Drill down and display the next question(s) in the UI.
     * @return {void}
     */
    displayChildNodes() {
        let canDisplayChildNodes = this._checkAllParentAnswers(this);
        if (canDisplayChildNodes && this.hasChildNodes()) {

            this.nodes.forEach((questionNode) => {
                questionNode.hidden = !ConditionToDisplay.checkConditionsToDisplayForQuestionAnswer(this.answer.answers, questionNode);
            });
        }
    }

    set answer(answer: Object) {

        // Make a placeholder answer.
        if (!answer) {
            answer = { answerId: null, questionId: this.questionId, answers: [] };
        }

        this._answer = answer;
        this.answerStatus = this._setAnswerStatus(answer);


    }
    get answer() {
        return this._answer;
    }

    /**
     * @description - set node's answer status. This flag is used to display different question states in the UI.
     * @param {string} answer - the selected answer.
     * @return {string} node status.
     * @private
     */
    _setAnswerStatus(answer: Object) {
        let answerToTriggerFinding = this.questionDTO.answerToTriggerFinding;

        if (answerToTriggerFinding && (!this.hasChildNodes() && this.findAnswerByCode(answerToTriggerFinding, answer.answers))) {
            return QA_TREE_ANSWER_STATUS.FINDING_TRIGGERED;
        } else if (answer.answers.length) {
            this.displayChildNodes();
            return QA_TREE_ANSWER_STATUS.ANSWERED;
        }

        return QA_TREE_ANSWER_STATUS.UNANSWERED;
    }

    findAnswerByCode(code: string, answers: Array) {
        answers = answers || this.answer.answers;
        return answers.find((_answer) => _answer.code.toLowerCase() === code.toLowerCase());
    }

    static parse(questionDTO: Object, answers: Array = null) {
        let parsedVM = (questionDTO instanceof QuestionNodeVM) ? questionDTO : new QuestionNodeVM(questionDTO);

        if (answers && answers.length) {
            let answer = answers.find(a => a.questionId === questionDTO.questionId);
            if (answer) {
                parsedVM.answer = answer;
            }

        }
        return parsedVM;
    }

}
