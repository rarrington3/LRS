// This file was generated from the service scaffold
// Copyright 2016

import {async, inject, TestBed} from '@angular/core/testing';
import DEFAULT_PROVIDERS from './providers';
import AppExceptionHandler from './exceptionHandler';
import {ErrorHandler} from '@angular/core';

let preloaderErrorHandler =  require('modules/preloader/errorhandler');


describe('app/services/exceptionHandler.js', () => {

    beforeEach(()=>{
        TestBed.configureTestingModule({
            providers: DEFAULT_PROVIDERS
        });        
    });

    it('should replace the default ErrorHandler', async(inject([ErrorHandler], (errorHandler:ErrorHandler) => {
        expect(errorHandler.name).toBe('AppExceptionHandler');
    })));

    it('should handle errors by logging to the console and invokimg the preloader errorhandler', async(inject([ErrorHandler], (errorHandler:ErrorHandler) => {
        spyOn(console, 'error');
        spyOn(preloaderErrorHandler, 'handleError');

        errorHandler.handleError('fake error');

        expect(console.error).toHaveBeenCalled();
        expect(preloaderErrorHandler.handleError).toHaveBeenCalled();
    })));

    // it('should retrieve a list of reviewers and augment them', async(inject([ReviewerSvc], (reviewerSvc:ReviewerSvc) => {

    //     let reviewers = reviewerSvc.getReviewers().first().subscribe(reviewers => {
    //         expect(reviewers).toBeDefined();
    //         expect(reviewers[0].name).toBeDefined();
    //     });
    // })));

    // it('should return 1 reviewer when searching for inactive', async(inject([ReviewerSvc], (reviewerSvc:ReviewerSvc) => {
    //     let filter = new ReviewerFilter();
    //     filter.statusCode = STATUS_CODE.INACTIVE;
    //     reviewerSvc.searchReviewers(filter).first().subscribe(reviewers => {
    //         expect(reviewers.length).toBe(1);
    //     });
    // })));

    // it('should return 5 reviewers when searching for active', async(inject([ReviewerSvc], (reviewerSvc:ReviewerSvc) => {
    //     let filter = new ReviewerFilter();
    //     filter.statusCode = STATUS_CODE.ACTIVE;
    //     reviewerSvc.searchReviewers(filter).first().subscribe(reviewers => {
    //         expect(reviewers.length).toBe(5);
    //     });
    // })));

});
