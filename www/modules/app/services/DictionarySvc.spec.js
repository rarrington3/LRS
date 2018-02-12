// This file was generated from the service scaffold
// Copyright 2016

import { async, inject, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { APP_PROVIDERS } from '../services/';
import DictionarySvc from './DictionarySvc';

describe('app/services/DictionarySvc.js', () => { 
    
    beforeEach(() => {
        TestBed.configureTestingModule({
            providers: APP_PROVIDERS,
            imports: [ RouterTestingModule ]
        });
    });

	it('should return DictionarySvc instance', async(inject([DictionarySvc], (dictionarySvc:DictionarySvc) => {
        expect(dictionarySvc).toBeDefined();
    })));
});


