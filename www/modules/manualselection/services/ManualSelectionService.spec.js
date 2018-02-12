// This file was generated from the service scaffold
// Copyright 2016

import { async, inject, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { MANUALSELECTION_PROVIDERS } from '../services/';
import { MANUALSELECTION_IMPORTS } from '../main';
import ManualSelectionService from './ManualSelectionService';

describe('manualselection/services/ManualSelectionService.js', () => {

    beforeEach(() => {
        TestBed.configureTestingModule({
            providers: MANUALSELECTION_PROVIDERS,
            imports: [ MANUALSELECTION_IMPORTS, RouterTestingModule ]
        });
    });

	it('should return ManualSelectionService instance', async(inject([ManualSelectionService], (manualSelectionService:ManualSelectionService) => {
        expect(manualSelectionService).toBeDefined();
    })));

    it('should return name', async(inject([ManualSelectionService], (manualSelectionService:ManualSelectionService) => {
        expect(manualSelectionService.getName()).toBe('ManualSelectionService');
    })));
});


