// This file was generated from the service scaffold
// Copyright 2017

import {async, inject, TestBed} from '@angular/core/testing';
import {APP_PROVIDERS} from '../services/';
import SaveBarSvc from './SaveBarSvc';

describe('app/services/SaveBarSvc.js', () => {

    beforeEach(() => {
        TestBed.configureTestingModule({
            providers: APP_PROVIDERS
        });
    });

    it('should return SaveBarSvc instance', async(inject([SaveBarSvc], (saveBarSvc: SaveBarSvc) => {
        expect(saveBarSvc).toBeDefined();
    })));
});


