// This file was generated from the service scaffold
// Copyright 2016

import {async, inject, TestBed} from '@angular/core/testing';
import {APP_PROVIDERS} from '../services/';
import ReviewerSvc from './ReviewerSvc';
import {ReviewerFilter} from '../models/ReviewerFilter';
import {STATUS_CODE} from '../../shared/constants';

describe('app/services/ReviewerSvc.js', () => {

    beforeEach(()=>{
        TestBed.configureTestingModule({
            providers: APP_PROVIDERS
        });
    });

    it('should return ReviewerSvc instance', async(inject([ReviewerSvc], (reviewerSvc:ReviewerSvc) => {
        expect(reviewerSvc).toBeDefined();
    })));

    it('should retrieve a list of reviewers and augment them', async(inject([ReviewerSvc], (reviewerSvc:ReviewerSvc) => {

        let reviewers = reviewerSvc.getReviewers().first().subscribe(reviewers => {
            expect(reviewers).toBeDefined();
            expect(reviewers[0].name).toBeDefined();
        });
    })));

    it('should return 4 reviewer when searching for inactive', async(inject([ReviewerSvc], (reviewerSvc:ReviewerSvc) => {
        let filter = new ReviewerFilter();
        filter.statusCode = STATUS_CODE.INACTIVE;
        reviewerSvc.searchReviewers(filter).first().subscribe(reviewers => {
            expect(reviewers.length).toBe(4);
        });
    })));

    it('should return 56 reviewers when searching for active', async(inject([ReviewerSvc], (reviewerSvc:ReviewerSvc) => {
        let filter = new ReviewerFilter();
        filter.statusCode = STATUS_CODE.ACTIVE;
        reviewerSvc.searchReviewers(filter).first().subscribe(reviewers => {
            expect(reviewers.length).toBe(56);
        });
    })));

});
