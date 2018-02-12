// This file was generated from the component scaffold
// Copyright 2016

import {Component} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import template from './QuestionAndAnswerTree.html';
import styles from './QuestionAndAnswerTree.less';
import ReviewService from '../services/ReviewService';
import FindingsService from '../services/FindingsService';
import GlobalStateSvc from '../services/GlobalStateSvc';
import QATreeUtils from '../QATreeUtils';
import FindingUtils from '../FindingUtils';
import ModalSvc from '../../app/services/ModalSvc';
import Utils from '../../shared/Utils';

@Component({
    selector: 'question-and-answer-tree',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/api/core/Component-decorator.html
 * @example
 * <question-and-answer-tree name="QuestionAndAnswerTree" (change)="onChange($event)"></question-and-answer-tree>
 */
export default class QuestionAndAnswerTree {
    // set via methods
    defectArea: Object = null;
    questions: Array = [];
    subscriptions: Array = [];

    // injected to constructor
    _route: ActivatedRoute;
    reviewService: ReviewService;
    findingsService: FindingsService;
    globalStateSvc: GlobalStateSvc;
    _router: Router;
    _modal: ModalSvc;

    isReadOnly:Boolean = false;

    constructor(route: ActivatedRoute, reviewService: ReviewService, findingsService: FindingsService, globalStateSvc: GlobalStateSvc, router: Router, modalSvc: ModalSvc) {
        this._route = route;
        this.reviewService = reviewService;
        this.findingsService = findingsService;
        this.globalStateSvc = globalStateSvc;
        this._router = router;
        this._modal = modalSvc;
    }

    ngOnInit() {
        this.subscriptions = [];

        // Keep track of the current defect area id.  When it changes, reload the Q&A tree
        this.subscriptions.push(this._route.params.subscribe(params => {
            if (params.area) {
                this.globalStateSvc.currentDefectAreaCode = params.area;
            }
        }));

        this.subscriptions.push(this.reviewService.questionsForCurrentAreaObservable.subscribe(() => this.updateQuestionsAndAnswers()));
        this.subscriptions.push(this.reviewService.answersForCurrentAreaObservable.subscribe(() => this.updateQuestionsAndAnswers()));
        this.subscriptions.push(this.reviewService.summaryFieldsForCurrentReviewLevelObservable.subscribe(() => this.updateQuestionsAndAnswers()));

        this.updateQuestionsAndAnswers();
        this.subscriptions.push(this.reviewService.currentDefectAreaObservable.subscribe(() => this.updateDefectArea()));
        this.updateDefectArea();

        this.subscriptions.push(this.reviewService.showWrapUpObservable.subscribe((value) => {
            this.showWrapUp = value.currentValue;
        }));
        this.showWrapUp = this.reviewService.showWrapUp;
    }

    trackByQuestionId(index, node) {
        return node.questionId;
    }

    updateQuestionsAndAnswers() {
        this.defectArea = this.reviewService.currentDefectArea;
        if (this.reviewService.defectAreasCompleted && this.reviewService.questionsForCurrentArea && this.reviewService.answersForCurrentArea && this.reviewService.unfilteredSummaryFieldsForCurrentReviewLevel && this.defectArea) {

            this.questions = QATreeUtils.buildNodes(this.reviewService, this.findingsService, this.reviewService.questionsForCurrentArea, this.reviewService.answersForCurrentArea, this.reviewService.unfilteredSummaryFieldsForCurrentReviewLevel, {defectAreaCode: this.globalStateSvc.currentDefectAreaCode});

            this.isReadOnly = Utils.isQuestionTreeReadOnly(this.reviewService.currentReview);

            // Check all the visible question's answer.
            let { allQuestionsCompleted, questionWithIncompleteFinding } = FindingUtils.checkAllQuestionAnswers(this.questions, this.findingsService.findingsForCurrentDefectArea, this.isReadOnly);

            // Prompt the user to complete a Finding.
            if (questionWithIncompleteFinding && questionWithIncompleteFinding.questionId) {
                FindingUtils.navigateToFindingSourceCause(this._router, this.globalStateSvc, this.reviewService, this.findingsService, questionWithIncompleteFinding, this._modal);
            }

            if (this.reviewService.defectAreasCompleted[this.defectArea.defectAreaCode] !== allQuestionsCompleted && this.questions.length) {
                this.reviewService.defectAreasCompleted[this.defectArea.defectAreaCode] = allQuestionsCompleted;
            }
        }
    }

    updateDefectArea() {
        this.defectArea = this.reviewService.currentDefectArea;
    }

    ngOnDestroy() {
        this.subscriptions.forEach((subscription) => {
            if (subscription.unsubscribe) {
                subscription.unsubscribe();
            }
        });
    }
}
