// This file was generated from the exports scaffold
// Copyright 2016

import QuestionNodeVM from './viewModels/QuestionNodeVM';
import ConditionToDisplay from './viewModels/ConditionToDisplay';
import FindingUtils from './FindingUtils';
import { Observable } from 'rxjs';
import _ from 'lodash';

/**
 * @example
 * import QATreeUtils from 'modules/review/QATreeUtils';
 * let qATreeUtils = new QATreeUtils();
 */

export default class QATreeUtils {

    constructor() {
    }

    /**
     * @description - Convert the returned questions DTO tied to a specific qa tree into a hierarchical data structure made of the custom QuestionNode type. Added persisted answers to the associated question.
     * @param {object} reviewService - The singleton instance of the ReviewService
     * @param {object} findingService - The singleton instance of the FindingService
     * @param {array} questions - questions in.
     * @param {array} answers - answers in.
     * @param {array} loanAttributes - loan attribute fields.
     * @param {array} qaTree - the current qa tree that's being converted to the hierarchical tree.
     * @return {array} - the nodes out.
     */
    static buildNodes(reviewService: Object, findingService: Object, questions: Array<any> = [], answers: Array<any> = [], loanAttributes:Array<any> = null, qaTree:Object = null) {
        questions = questions.filter(q => q && q.questionId);

        // Create a lookup hash to help in mapping an answer to the related question.
        let answerLookUp = _.zipObject(_.pluck(answers, 'questionId'), _.values(answers)),
            _questions = questions || [];

        if (_questions.length) {

            // Convert all questions to the QuestionNodeVM type.
            let allNodes = _questions.map((q) => {
                let node = QuestionNodeVM.parse(q),
                    answer = _.get(answerLookUp, node.questionId);
                // Restore the answer
                node.answer = answer;
                return node;
            });

            let orphanedQuestions = [];

            // Always filter the questions based on the current loan attributes.
            if (loanAttributes && loanAttributes.length) {
                allNodes = QATreeUtils.filterNodeByLoanAttributes(allNodes, loanAttributes, orphanedQuestions);
            }

            // Silently delete orphaned question answers assuming user has been notified in the UI.
            QATreeUtils.deleteOrphanedQuestionsAnswer(reviewService, findingService, orphanedQuestions, qaTree);

            let build = (_nodes, depth) => {

                // Loop through an array of nodes and check for the parentId.
                _nodes.forEach((currentNode) => {

                    // A node with the undefined parentQuestionId should have the depth == 0.
                    if (!currentNode.parentQuestionId) {
                        depth = 0;
                        currentNode.depth = depth;
                    } else if (currentNode.parentNode) {
                        let parentDepth = currentNode.parentNode.depth || 0;
                        currentNode.depth = parentDepth + 1;
                    }

                    // The current node's id.
                    let currentNodeId = currentNode.questionId;

                    // Step 2 - Return all nodes that are children of the currentNode.
                    let childNodes = allNodes.filter((quesstionNode) => {
                        let found = (currentNode.questionId !== currentNode.parentQuestionId && quesstionNode.parentQuestionId && quesstionNode.parentQuestionId === currentNodeId);
                        if (found) {
                            quesstionNode.parentNode = currentNode;
                        }
                        return found;
                    });

                    // Assign the child nodes to the current node if they exist.
                    if (childNodes && childNodes.length) {
                        currentNode.nodes = [...childNodes];

                        // Reveal the child questions based on the current question's answer.
                        currentNode.displayChildNodes();
                        depth++;

                        // Recursively check all the child nodes for children.
                        build(childNodes, depth);
                    }
                });

                return _nodes;
            };

            // Remove the root level nodes with 'parentQuestionId' since those have been moved to another level or filtered out.

            let nodes = build(allNodes, 0).filter((n) => !(n.parentQuestionId && questions.find((q) => q.questionId === n.parentQuestionId)));
            return nodes;
        }

        return [];
    }

    static filterNodeByLoanAttributes(nodes: Array = [], attributeFields: Array = [], orphanedQuestions: Array) {

        // Merge all the fields into one object.
        let mergedFields = {};
        for (const field of attributeFields) {
            mergedFields[field.fieldId] = field.value;
        }

        // Track all the orphaned questions.
        for (let question of nodes) {

            // Include this question in the tree if it passes the conditionsToDisplay test.
            let isApplicable = ConditionToDisplay.checkConditionsToDisplayForLoanAttributes(mergedFields, question);
            // Orphaned question found.
            if (!isApplicable) {

                console.log('Orphaned question found!', question);
                orphanedQuestions.push(question);

                // Recursively cascade to all the child nodes.
                orphanedQuestions.push(...QATreeUtils.findOrphanedChildNodes(question, nodes));
            }
        }

        // Just in case...
        orphanedQuestions = _.uniq(orphanedQuestions, 'questionId');

        //  Hide the orphaned questions
        return nodes.filter((node) => !orphanedQuestions.find((o) => o.questionId === node.questionId));
    }

    static findOrphanedChildNodes(parent: QuestionNodeVM, allNodes, foundNodes: Array = []) {
        for (let node of allNodes) {
            if (node.parentQuestionId === parent.questionId) {
                console.log('Child of orphaned question found!', node);
                foundNodes.push(node);
                QATreeUtils.findOrphanedChildNodes(node, allNodes, foundNodes);
            }
        }
        return foundNodes;
    }

    // This method is called automatically when loan summary fields are edited.
    static deleteOrphanedQuestionsAnswer(reviewService: Object, findingService: Object, orphanedQuestions: Array, qaTree:Object) {
        let _qaTree = qaTree || {};

        // The qa tree that requires the delete orphaned question answers action.
        let currentDefectAreaTarget = reviewService.deleteOrphanedQuestionsAnswers.find(t => t.defectAreaCode === _qaTree.defectAreaCode);

        if (currentDefectAreaTarget && orphanedQuestions.length) {
            let $saveDeleteFindings = Observable.of(null);
            let $deleteAnswers = Observable.of(null);

            // Delete or update existing findings tied to orphaned questions.
            if (findingService.currentFindings) {
                let affectedFindings = [];
                for (let q of orphanedQuestions) {
                    if (q) {
                        affectedFindings.push(...FindingUtils.findAllFindingsByQuestionId(q.questionId, true, null, findingService.currentFindings));
                    }
                }

                if (affectedFindings.length) {
                    affectedFindings = _.unique(affectedFindings, (f => f.findingId));
                    console.log('deleteOrphanedQuestionsAnswer - found Findings associated with orphaned questions: ', affectedFindings);
                    $saveDeleteFindings = reviewService.saveOrDeleteFindings(affectedFindings).first();
                }
            }

            let answers = orphanedQuestions
                .map((q) => q.answer)
                .filter((a) => a && a.answerId);

            if (answers && answers.length) {
                $deleteAnswers = reviewService.deleteAnswers(answers).first();
            }

            Observable.concat($saveDeleteFindings, $deleteAnswers).subscribe(() => {
                currentDefectAreaTarget = reviewService.deleteOrphanedQuestionsAnswers.find(t => t.defectAreaCode === _qaTree.defectAreaCode);
                let allTargetsCompleted = reviewService.deleteOrphanedQuestionsAnswers.every(t => t.isCompleted === true);
                if (allTargetsCompleted) {

                    // Disable the delete ophaned question answers/findings action.
                    reviewService.deleteOrphanedQuestionsAnswers = [];
                } else if (currentDefectAreaTarget.defectAreaCode === _qaTree.defectAreaCode) {
                    currentDefectAreaTarget.isCompleted = true;
                }
            });
        }
    }

    static findQuestionParents(question: Object, questions: Array, parents: Array = []) {
        if (question.parentQuestionId) {
            let parent = questions.find((q) => q.questionId === question.parentQuestionId);
            if (parent) {
                parents.push(parent);
                QATreeUtils.findQuestionParents(parent, questions, parents);
            }
        }

        return parents;
    }

    static findAllChildAnsweredQuestions(question: QuestionNodeVM, foundQuestions: Array = []) {
        if (question.nodes && question.nodes.length) {
            let answeredQuestions = question.nodes.filter((q) => q.answer && q.answer.answerId);
            if (answeredQuestions.length) {
                foundQuestions.push(...answeredQuestions);
            }

            // Recursively search all levels.
            question.nodes.forEach((q => QATreeUtils.findAllChildAnsweredQuestions(q, foundQuestions)));
        }

        return foundQuestions;
    }


    // Update a collection of questions in a sequence.
    static deleteQuestionsAnswerAndFindingSequence(questionNodes: QuestionNodeVM, findingUtils: Object, reviewService: Object, findingsService: Object) {
        if (!questionNodes || !questionNodes.length) {
            return Observable.of(false);
        }
        return Observable.concat(...questionNodes.map(n => findingUtils.deleteIncompleteQuestionAnswerAndFinding(n, reviewService, findingsService)));
    }
}
