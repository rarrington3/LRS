// This file was generated from the view scaffold
// Copyright 2016

import { async, TestBed} from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { LENDER_VIEWS } from '../views/';
import { LENDER_COMPONENTS } from '../components/';
import { LENDER_PROVIDERS } from '../services/';
import { LENDER_IMPORTS } from '../main';
import { LENDER_PIPES } from '../pipes';
import ActiveReviews from './activeReviews';

describe('lender/views/activeReviews.js', () => {

    beforeEach(() => {
        TestBed.configureTestingModule({
             declarations: [LENDER_COMPONENTS, LENDER_VIEWS, LENDER_PIPES],
             providers: LENDER_PROVIDERS,
             imports: [ LENDER_IMPORTS, RouterTestingModule ]
        });
    });

    it('should be creatable', async(() => {
        TestBed.compileComponents().then(() => {
            const fixture = TestBed.createComponent(ActiveReviews);

            expect(fixture.componentInstance).toBeDefined();
            expect(fixture.debugElement.nativeElement.innerHTML).toBeTruthy();
        });
    }));

});
