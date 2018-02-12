// This file was generated from the service scaffold
// Copyright 2016

import { async, inject, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { LENDER_PROVIDERS } from '../services/';
import { LENDER_IMPORTS } from '../main';
import LenderService from './LenderService';

describe('lender/services/lenderService.js', () => {
    
    beforeEach(() => {
        TestBed.configureTestingModule({
            providers: LENDER_PROVIDERS,
            imports: [ LENDER_IMPORTS, RouterTestingModule ]
        });
    });

	it('should return LenderService instance', async(inject([LenderService], (lenderService:LenderService) => {
        expect(lenderService).toBeDefined();
    })));

});


