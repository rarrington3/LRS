// This file was generated from the service scaffold
// Copyright 2016

import { async, inject, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { REVIEW_PROVIDERS } from '../services/';
import { REVIEW_IMPORTS } from '../main';
import FindingsService from './FindingsService';

describe('review/services/FindingsService.js', () => { 
    
    beforeEach(() => {
        TestBed.configureTestingModule({
            providers: REVIEW_PROVIDERS,
            imports: [ REVIEW_IMPORTS, RouterTestingModule ]
        });
    });

	it('should return FindingsService instance', async(inject([FindingsService], (findingsService:FindingsService) => {
        expect(findingsService).toBeDefined();
    })));
});


