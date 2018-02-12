// This file was generated from the service scaffold
// Copyright 2016

import { async, inject, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { APP_PROVIDERS } from '../services/';
import BatchService from './BatchService';

describe('workload/services/BatchService.js', () => { 
    
    beforeEach(() => {
        TestBed.configureTestingModule({
            providers: APP_PROVIDERS,
            imports: [ RouterTestingModule ]
        });
    });

	it('should return BatchService instance', async(inject([BatchService], (batchService:BatchService) => {
        expect(batchService).toBeDefined();
    })));

});
