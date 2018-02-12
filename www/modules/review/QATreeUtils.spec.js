// This file was generated from the exports scaffold
// Copyright 2016

import {async, inject, TestBed} from '@angular/core/testing';
import {RouterTestingModule} from '@angular/router/testing';
import {HttpModule, Http} from '@angular/http';
import {REVIEW_PROVIDERS} from './services/';
import {REVIEW_IMPORTS} from './main';
import QATreeUtils from './QATreeUtils';
import ReviewService from './services/ReviewService';
import FindingsService from './services/FindingsService';
import Client from '../api/lrs.api.v1.generated';
import QuestionNodeVM from './viewModels/QuestionNodeVM';
import {Observable} from 'rxjs';

describe('review/QATreeUtils.js', () => {
    let qATreeUtils = new QATreeUtils();

    beforeEach(() => {
        TestBed.configureTestingModule({
            providers: REVIEW_PROVIDERS,
            imports: [REVIEW_IMPORTS, RouterTestingModule, HttpModule]
        });
    });

    it('should return QATreeUtils instance', () => {
        expect(qATreeUtils).toBeDefined();
    });

    describe('when using the QATreeUtils methods', () => {
        let client;
        let fakeQuestions;
        let fakeAnswers;
        let fakeLoanFields;
        let questionNodes;
        let reviewSrv;
        let findingSrv;

        beforeEach(inject([Http, ReviewService, FindingsService], (http:Http, reviewService:ReviewService, findingsService:FindingsService) => {
            client = new Client(http);
            reviewSrv = reviewService;
            findingSrv = findingsService;

            Observable.forkJoin([
                client.resources.reviews.qaTree.qaTreeId('123').questions.get(),
                client.resources.reviews.reviewId('123').level.reviewLevelId('123').qaTree.qaTreeId('123').answers.get(),
                client.resources.reviews.reviewId('123').level.reviewLevelId('123').fields.get()
            ]).first().subscribe(([questions, answers, fields]) => {
                fakeQuestions = questions;
                fakeAnswers = answers;
                fakeLoanFields = fields;
            });

        }));

        it('should return a Client instance',  () => {
            expect(client).toBeDefined();
        });

        it('should have all fake data collections', () => {
            expect(fakeQuestions instanceof Array).toBe(true);
            expect(fakeAnswers instanceof Array).toBe(true);
            expect(fakeLoanFields instanceof Array).toBe(true);
        });

        it('should create an array of QuestionNodeVM models when the buildNodes method is invoked.', ()  => {
            questionNodes = QATreeUtils.buildNodes(reviewSrv, findingSrv, fakeQuestions, fakeAnswers, fakeLoanFields);
            expect(questionNodes instanceof Array).toBe(true);
            expect(questionNodes.length).not.toEqual(0);
            expect(questionNodes[0] instanceof QuestionNodeVM).toBe(true);
        });

        it('should filter nodes by loan attributes', ()  => {
            let filteredNodes = QATreeUtils.filterNodeByLoanAttributes(questionNodes, fakeLoanFields);
            expect(questionNodes instanceof Array).toBe(true);
            expect(questionNodes.length).not.toEqual(0);
            expect(questionNodes[0] instanceof QuestionNodeVM).toBe(true);
        });

        it('should return the correct parent questions when findQuestionParents is invoked', ()  => {
            let fakeData = [{questionId:'root'}, {questionId:'a_1', parentQuestionId: 'root'}, {questionId:'a_2', parentQuestionId: 'a_1'}];
            let parents = QATreeUtils.findQuestionParents(fakeData[2], fakeData);
            expect(parents instanceof Array).toBe(true);
            expect(parents.length).toEqual(2);
            expect(parents).toContain({questionId:'root'});
            expect(parents).toContain({questionId:'a_1', parentQuestionId: 'root'});
        });

    });

});

