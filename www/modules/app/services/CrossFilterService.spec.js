// This file was generated from the service scaffold
// Copyright 2017

import { async, inject, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { APP_PROVIDERS } from '../services/';
import CrossFilterService from './CrossFilterService';

describe('app/services/CrossFilterService.js', () => {

    beforeEach(() => {
        TestBed.configureTestingModule({
            providers: APP_PROVIDERS,
            imports: [ RouterTestingModule ]
        });
    });

	it('should return CrossFilterService instance', async(inject([CrossFilterService], (crossFilterService:CrossFilterService) => {
        expect(crossFilterService).toBeDefined();
    })));

});


