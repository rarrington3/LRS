// This file was generated from the service scaffold
// Copyright 2017

import { async, inject, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { REVIEW_PROVIDERS } from '../services/';
import { REVIEW_IMPORTS } from '../main';
import QCService from './QCService';

describe('review/services/QCService.js', () => { 
    
    beforeEach(() => {
        TestBed.configureTestingModule({
            providers: REVIEW_PROVIDERS,
            imports: [ REVIEW_IMPORTS, RouterTestingModule ]
        });
    });

	it('should return QCService instance', async(inject([QCService], (qCService:QCService) => {
        expect(qCService).toBeDefined();
    })));
});


