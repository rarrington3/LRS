// This file was generated from the service scaffold
// Copyright 2016

import { async, inject, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { APP_PROVIDERS } from './';
import LocationSvc from './LocationSvc';

describe('app/services/LocationSvc.js', () => {

    beforeEach(() => {
        TestBed.configureTestingModule({
            providers: APP_PROVIDERS,
            imports: [ RouterTestingModule ]
        });
    });

	it('should return LocationSvc instance', async(inject([LocationSvc], (locationSvc:LocationSvc) => {
        expect(locationSvc).toBeDefined();
    })));

    it('should return name', async(inject([LocationSvc], (locationSvc:LocationSvc) => {
        expect(locationSvc.getName()).toBe('LocationSvc');
    })));
});


