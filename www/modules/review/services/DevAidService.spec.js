// This file was generated from the service scaffold
// Copyright 2017

import { async, inject, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { REVIEW_PROVIDERS } from '../services/';
import { REVIEW_IMPORTS } from '../main';
import DevAidService from './DevAidService';

describe('review/services/DevAidService.js', () => {

    beforeEach(() => {
        TestBed.configureTestingModule({
            providers: REVIEW_PROVIDERS,
            imports: [ REVIEW_IMPORTS, RouterTestingModule ]
        });
    });

	it('should return DevAidService instance', async(inject([DevAidService], (devAidService:DevAidService) => {
        expect(devAidService).toBeDefined();
    })));

});


