// This file was generated from the view scaffold
// Copyright 2016

import { async, TestBed} from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { LENDERMONITORING_VIEWS } from '../views/';
import { LENDERMONITORING_COMPONENTS } from '../components/';
import { LENDERMONITORING_PROVIDERS } from '../services/';
import { LENDERMONITORING_IMPORTS } from '../main';
import LenderReview from './LenderReview';

describe('lenderMonitoring/components/LenderReview.js', () => {

    beforeEach(() => {
        TestBed.configureTestingModule({
             declarations: [LENDERMONITORING_COMPONENTS, LENDERMONITORING_VIEWS],
             providers: LENDERMONITORING_PROVIDERS,
             imports: [ LENDERMONITORING_IMPORTS, RouterTestingModule ]
        });
    });

    it('should be creatable', async(() => {
        TestBed.compileComponents().then(() => {
            const fixture = TestBed.createComponent(LenderReview);

            expect(fixture.componentInstance).toBeDefined();
            expect(fixture.debugElement.nativeElement.innerHTML).toBeTruthy();
        });
    }));

});
