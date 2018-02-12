// This file was generated from the service scaffold
// Copyright 2017

import { ApplicationRef } from '@angular/core';
import ReviewService from './ReviewService';
import ModalSvc from '../../app/services/ModalSvc';
import _ from 'lodash';
import {QA_TREE_ANSWER_STATUS} from '../constants';
import { Observable } from 'rxjs';

/**
 * Used to aid testers with various automated features.
 */

export default class DevAidService {

    constructor(reviewService: ReviewService, modalSvc: ModalSvc, applicationRef: ApplicationRef) {
        this._reviewService = reviewService;
        this._modal = modalSvc;
        this._applicationRef = applicationRef;
    }

    _saveAllQuestions() {
        // Build answer requests for each unanswered question.
        let answerRequests = [];
        let saveAllAnswers = (newAnswers: Array) => {
            let validAnswers = newAnswers.filter(a => !a.answer.answerId);
            let requests = validAnswers.map((a) => {
                return this._reviewService._client.resources.reviews.reviewId(this._reviewService.currentReview.reviewId).level.reviewLevelId(this._reviewService.currentReview.currentReviewLevel.reviewLevelId).qaTree.qaTreeId(a.treeId).answers.post(a.answer);
            });
            Observable.forkJoin(...requests).subscribe(() => {
                // Reload to ensure user is back in testable state
                window.location.reload();
            });

        };

        let answerQuestions = (questionNodes: Object, newAnswers: Array = [], currentTree: Object) => {
            questionNodes.forEach((n) => {
                if (n.answerStatus === QA_TREE_ANSWER_STATUS.UNANSWERED && (n.hidden === false || !n.parentNode)) {

                    let answers = n.potentialAnswers.filter(a => !n.questionDTO.answerToTriggerFinding || a.code !== n.questionDTO.answerToTriggerFinding);
                    n.answer  = {answerId: null, questionId: n.questionId, answers: [_.last(answers)]};

                    console.log('------------Auto answer questions for Defect Area: ', n.questionReference);
                    console.log('parent: ', n.questionDTO.parentQuestionId);
                    console.log('Answer type', n.answerType);
                    console.log('Filtered potential answers: ', answers);
                    console.log('Selected answer: ', n.answer.answers);
                    console.log('------------');

                    newAnswers.push({treeId: currentTree.qaTreeId, answer: n.answer});
                    if (n.nodes && n.nodes.length) {
                        answerQuestions(n.nodes, newAnswers, currentTree);
                    }
                } else if (n.answerStatus === QA_TREE_ANSWER_STATUS.ANSWERED && n.nodes && n.nodes.length) {
                    answerQuestions(n.nodes, newAnswers, currentTree);
                }
            });

        };


        let trees = this._reviewService.currentReviewQATrees;
        trees.forEach((tree) => {
            if (!tree.completed) {
                answerQuestions(tree.parsedQuestions, answerRequests, tree);
            }
        });


        saveAllAnswers(answerRequests);
    }

    _showUI(context: Object) {
        context._modal.askForConfirmation(
            '<p>Automatically answer all questions for this review?</p>', 'Answer All Questions', 'Groovy!')
            .first().subscribe(
            () => { context._saveAllQuestions.apply(context); },
            () => { }
        );
    }

    _answerAllQuestions() {
        this._showUI(this);
    }

    _answerAllQuestionsFromCommandLine() {
        window.lrsAid.aaqContext._showUI(window.lrsAid.aaqContext);
        // Since this came from the command line, nudge Angular to display the modal...
        window.lrsAid.aaqContext._applicationRef.tick();
    }

    activateCommandLine() {
        window.lrsAid = {
            aaqContext: this,
            aaq: this._answerAllQuestionsFromCommandLine
        };
    }

    deactivateCommandLine() {
        window.lrsAid = null;
    }
}
