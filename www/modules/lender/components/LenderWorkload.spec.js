// This file was generated from the component scaffold
// Copyright 2016

import { async, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { LENDER_COMPONENTS } from '../components/';
import { LENDER_PROVIDERS } from '../services/';
import { LENDER_IMPORTS } from '../main';
import LenderWorkload from './LenderWorkload';

describe('lender/components/LenderWorkload.js', () => {

    beforeEach(() => {
        TestBed.configureTestingModule({
            declarations: LENDER_COMPONENTS,
            providers: LENDER_PROVIDERS,
            imports: [LENDER_IMPORTS, RouterTestingModule]
        });
    });

    it('should be creatable', async(() => {
        TestBed.compileComponents().then(() => {
            const fixture = TestBed.createComponent(LenderWorkload);

            expect(fixture.componentInstance).toBeDefined();
            expect(fixture.debugElement.nativeElement.innerHTML).toBeTruthy();
        });
    }));
});
