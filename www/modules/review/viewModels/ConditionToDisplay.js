// This file was generated from the exports scaffold
// Copyright 2016

/**
 * @example
 * import ConditionToDisplay from 'modules/review/viewModels/ConditionToDisplay';
 * let conditionToDisplay = new ConditionToDisplay();
 */

import _ from 'lodash';
import {QA_TREE_OPERATOR} from '../../shared/constants';

export default class ConditionToDisplay {
    conditionId: string;
    fieldName: string;
    operator: string;
    comparisonValues: Array = [];

    // All operator functions listed here should return a boolean type.
    static CONDITION_TO_DISPLAY_OPERATORS = {
        [QA_TREE_OPERATOR.IS_PRESENT.code]: (comparisonValue, answer) => comparisonValue && !_.isUndefined(answer) && !_.isNull(answer),
        [QA_TREE_OPERATOR.NOT_PRESENT.code]: (comparisonValue, answer) => comparisonValue && (_.isUndefined(answer) || _.isNull(answer)),
        [QA_TREE_OPERATOR.EQUAL.code]: (comparisonValue, answer) => {
            let _value = _.isString(comparisonValue) ? comparisonValue.toLowerCase() : comparisonValue,
                _answer = _.isString(answer) ? answer.toLowerCase() : answer;
            return !_.isNull(answer) && _.eq(_value, _answer);
        },
        [QA_TREE_OPERATOR.GREATER_THAN.code]: (comparisonValue, answer) => !_.isNull(answer) && _.gt(parseInt(answer, 10), parseInt(comparisonValue, 10)),
        [QA_TREE_OPERATOR.LOWER_THAN.code]: (comparisonValue, answer) => !_.isNull(answer) &&_.lt(parseInt(answer, 10), parseInt(comparisonValue, 10)),
        [QA_TREE_OPERATOR.NOT_EQUAL.code]: (comparisonValue, answer) => !ConditionToDisplay.CONDITION_TO_DISPLAY_OPERATORS['='](comparisonValue, answer),
        [QA_TREE_OPERATOR.IN.code]: (comparisonValues, answer) => _.includes(comparisonValues, answer),
        [QA_TREE_OPERATOR.NOT_IN.code]: (comparisonValues, answer) => !_.includes(comparisonValues, answer),
        [QA_TREE_OPERATOR.INTERSECTS.code]: (comparisonValues, answers) => !!_.intersection(comparisonValues, answers).length
    };

    constructor(data: Object) {
        _.extend(this, data);
    }


    /**
     * @description - Check the user's answer or loan attributes with the 'comparisonValues' array in a field.conditionsToDisplay item .
     * @param {any} fieldsOrAnswers - the fields to check.
     * @return {boolean} - passed = true?
     */
    compareFieldsOrAnswersWithComparisonValues(fieldsOrAnswers: any) {

        // if the field name exists, use it otherwise use the default 'answers' field.
        if (_.has(fieldsOrAnswers, this.fieldName)) {
            return this._compare(_.get(fieldsOrAnswers, this.fieldName), this.fieldName);

        } else if (_.has(fieldsOrAnswers, 'answers')) {

            // This a special case for the Question with the answerType 'multiple'. Here we compare an array of answers with the comparisonValues array.
            if (this.operator.toLowerCase() === QA_TREE_OPERATOR.INTERSECTS.code || fieldsOrAnswers.answers.length > 1) {
                return ConditionToDisplay.CONDITION_TO_DISPLAY_OPERATORS[QA_TREE_OPERATOR.INTERSECTS.code](this.comparisonValues, fieldsOrAnswers.answers.map((a) => a.code));
            }

            // The answers array should always have one answer at this point but just in case  we use every to iterate the array.
            return fieldsOrAnswers.answers.every((_answer) => this._compare(_answer.code, null));
        }

        return false;
    }

    /**
     * @description - Helper method to compare all comparison values with the answer or the Loan attribute value(s).
     * @param {*} answer string, number or array. The field.value of the field we're checking the comparisonValues against.
     * @param {string} fieldName - the conditionDisplay.fieldname property.
     * @returns {boolean} - the answer.
     * @private
     */
    _compare(answer: any, fieldName: string) {

        if (_.isUndefined(answer)) {
            return false;
        }

        // Always convert the answer to an array to support multiple answers in the form of a comma delimited string.
        let operatorLower = this.operator && this.operator.toLowerCase();
        let answers = (!_.isNull(answer) && !_.isArray(answer)) ? answer.split(',') : [answer];
        let operatorFunc = _.get(ConditionToDisplay.CONDITION_TO_DISPLAY_OPERATORS, operatorLower);

        if (operatorLower === QA_TREE_OPERATOR.IS_PRESENT.code || operatorLower === QA_TREE_OPERATOR.NOT_PRESENT.code) {
            return answers.every(a => operatorFunc(fieldName, a));
        } else if (operatorLower === QA_TREE_OPERATOR.IN.code || operatorLower === QA_TREE_OPERATOR.NOT_IN.code) {
            return answers.some(a => operatorFunc(this.comparisonValues, a));
        }

        return this.comparisonValues.every((value) => {

            return answers.every(a => operatorFunc(value, a));
        });
    }

    /**
     * @description - return true if all the Question.conditionsToDisplay objects pass the test.
     * @param {array} answers - The parent question's answer(s)
     * @param {QuestionNodeVM} node - the QuestionNodeVM
     * @returns {boolean}  return true or false.
     */
    static checkConditionsToDisplayForQuestionAnswer(answers: Array, node: Object) {
        if (!answers || answers.length === 0) {
            return false;
        }

        if (answers.length > 1) {
            node.conditionToDisplayQuestion.operator = QA_TREE_OPERATOR.INTERSECTS.code;
        }

        return node.conditionToDisplayQuestion.compareFieldsOrAnswersWithComparisonValues({answers: answers});
    }

    /**
     * @description - return true if all the Question.conditionsToDisplay objects pass the test.
     * @param {array} fields - attribute fields.
     * @param {QuestionNodeVM} node - the QuestionNodeVM
     * @returns {boolean} return true or false.
     */
    static checkConditionsToDisplayForLoanAttributes(fields: Object, node: Object) {
        if (_.keys(fields).length === 0) {
            return false;
        }
        return node.conditionsToDisplay.filter((condition) => !_.isUndefined(condition.fieldName) && !_.isNull(condition.fieldName))
            .every((condition) => condition.compareFieldsOrAnswersWithComparisonValues(fields));
    }

    static filterFieldsByConditionsToDisplay(fields: Array = []) {

        // Merge all the fields into one object.
        let mergedFields = {};
        for (const field of fields) {
            mergedFields[field.fieldId] = field.value;
        }

        return fields.filter(f => {
            if (!f.conditionsToDisplay || !f.conditionsToDisplay.length) {
                return true;
            }

            // Convert to the ConditionToDisplay UI model.
            f.conditionsToDisplay = f.conditionsToDisplay.map((c) => { return (c instanceof ConditionToDisplay) ? c : new ConditionToDisplay(c); });

            return f.conditionsToDisplay.some(condition => condition.compareFieldsOrAnswersWithComparisonValues(mergedFields) && condition.fieldName !== f.fieldId);
        });
    }
}
