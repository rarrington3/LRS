// This file was generated from the exports scaffold
// Copyright 2016
import {async, inject, TestBed} from '@angular/core/testing';
import FindingUtils from './FindingUtils';
import {REVIEW_IMPORTS} from './main';
import {REVIEW_PROVIDERS} from './services/';
import {RouterTestingModule} from '@angular/router/testing';
import Client from '../api/lrs.api.v1.generated';
import ReviewService from './services/ReviewService';
import FindingsService from './services/FindingsService';
import {Observable} from 'rxjs';
import QuestionNodeVM from './viewModels/QuestionNodeVM';
import {QA_TREE_ANSWER_STATUS} from './constants';
describe('review/FindingUtils.js', () => {
    let findingUtils = new FindingUtils();

    beforeEach(() => {
        TestBed.configureTestingModule({
            providers: REVIEW_PROVIDERS,
            imports: [REVIEW_IMPORTS, RouterTestingModule]
        });

        findingUtils = new FindingUtils();

    });


    it('should return FindingUtils instance', () => {
        expect(findingUtils).toBeDefined();
    });

    describe('when using theFindingUtils methods', () => {
        let client;
        let fakeQuestions;
        let fakeFindings;
        let reviewSrv;
        let findingSrv;

        beforeEach(async(inject([Client, ReviewService, FindingsService], (apiClient:Client, reviewService:ReviewService, findingsService:FindingsService) => {
            client = apiClient;
            reviewSrv = reviewService;
            findingSrv = findingsService;

            Observable.forkJoin([
                client.resources.reviews.qaTree.qaTreeId('123').questions.get(),
                client.resources.reviews.reviewId('123').level.reviewLevelId('123').findings.get()
            ]).first().subscribe(([questions, findings]) => {
                fakeQuestions = questions;
                fakeFindings = findings;
            });

        })));

        it('should return a Client instance for retrieving mock data',  () => {
            expect(client).toBeDefined();
        });

        it('should have questions and findings mock data', () => {
            expect(fakeFindings).toBeDefined();
            expect(fakeQuestions).toBeDefined();
            expect(fakeQuestions.length).toBeGreaterThan(0);
            expect(fakeFindings.length).toBeGreaterThan(0);
        });

        it('should find a finding by question Id',  () => {
            let testFinding = fakeFindings.find(f => {
                if (f.questionIds && f.questionIds.length) {
                    return f;
                }

                return null;
            })
            expect(testFinding).toBeDefined();
            let questionId = testFinding.questionIds[0];
            let foundFinding = FindingUtils.findFindingByQuestionId(questionId, fakeFindings);
            expect(foundFinding).toBeDefined();
            expect(foundFinding.questionIds[0]).toEqual(questionId);
        });

        it('should find a finding by finding id', () => {
            let testFindingId = fakeFindings[0].findingId;
            let foundFinding = FindingUtils.findFindingById(testFindingId, fakeFindings);
            expect(foundFinding).toBeDefined();
            expect(foundFinding.findingId).toEqual(testFindingId);
        });

        it('should find a finding by source and cause', () => {
            let testFinding = fakeFindings[0];
            let foundFinding = FindingUtils.findFindingBySourceAndCause(testFinding.selectedSourceCode, testFinding.selectedCauseCode, testFinding.defectAreaCode, fakeFindings);
            expect(foundFinding).toBeDefined();
            expect(foundFinding.findingId).toEqual(testFinding.findingId);
        });

        it('should create a new Finding', () => {
            let newFinding = FindingUtils.createNewFinding('123', '123');
            expect(newFinding).toBeDefined();
            expect(newFinding.findingId).not.toBeDefined();
        });

        it('should delete an incomplete question answer without finding', () => {
            let incompleteQuestion = fakeQuestions[0];
            incompleteQuestion = QuestionNodeVM.parse(incompleteQuestion);
            incompleteQuestion.answerStatus = QA_TREE_ANSWER_STATUS.FINDING_TRIGGERED;
            FindingUtils.deleteIncompleteQuestionAnswerAndFinding(incompleteQuestion, reviewSrv).subscribe((complete) => {
                expect(complete).toBe(true);
            });

        });

        it('should navigate to the FindingSourceCause view', () => {
            let incompleteQuestion = fakeQuestions[0];
            incompleteQuestion = QuestionNodeVM.parse(incompleteQuestion);
            incompleteQuestion.answerStatus = QA_TREE_ANSWER_STATUS.FINDING_TRIGGERED;

            let routerStub = {navigate: (route)=> { }};
            let globalServiceStub = {currentReviewId: '123', currentDefectAreaCode: 'BA'};
            let testRoute = `/review/${globalServiceStub.currentReviewId}/tree/${globalServiceStub.currentDefectAreaCode}/question/${incompleteQuestion.questionId}/sourcecause`;

            spyOn(routerStub, 'navigate');
            FindingUtils.navigateToFindingSourceCause(routerStub, globalServiceStub, reviewSrv, findingSrv, incompleteQuestion);
            expect(routerStub.navigate).toHaveBeenCalled();
            expect(routerStub.navigate.calls.argsFor(0)[0]).toEqual([testRoute]);

        });

        it('should recursively check all questions with incomplete finding', () => {
            let parentQuestion = fakeQuestions[0];
            let childQuestion = fakeQuestions[1];
            childQuestion.questionId = '1234';
            parentQuestion.questionId = '123';
            childQuestion = QuestionNodeVM.parse(childQuestion);
            childQuestion.answerStatus = QA_TREE_ANSWER_STATUS.FINDING_TRIGGERED;
            parentQuestion = QuestionNodeVM.parse(parentQuestion);
            parentQuestion.answerStatus = QA_TREE_ANSWER_STATUS.ANSWERED;
            parentQuestion.nodes = [childQuestion];
            let {allQuestionsCompleted, questionWithIncompleteFinding} = FindingUtils.checkAllQuestionAnswers([parentQuestion], fakeFindings);
            expect(questionWithIncompleteFinding).toBeDefined();
            expect(questionWithIncompleteFinding.questionId).toEqual(childQuestion.questionId);
        });
    });

});

