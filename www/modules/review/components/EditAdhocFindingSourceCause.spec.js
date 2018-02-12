// This file was generated from the component scaffold
// Copyright 2017

import { async, TestBed} from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { REVIEW_COMPONENTS } from '../components/';
import { REVIEW_PROVIDERS } from '../services/';
import { REVIEW_IMPORTS } from '../main';
import EditAdhocFindingSourceCause from './EditAdhocFindingSourceCause';

describe('review/components/EditAdhocFindingSourceCause.js', () => {

    beforeEach(() => {
        TestBed.configureTestingModule({
             declarations: REVIEW_COMPONENTS,
             providers: REVIEW_PROVIDERS,
             imports: [ REVIEW_IMPORTS, RouterTestingModule ]
        });
    });

    it('should be creatable', async(() => {
        TestBed.compileComponents().then(() => {
            const fixture = TestBed.createComponent(EditAdhocFindingSourceCause);

            expect(fixture.componentInstance).toBeDefined();
            expect(fixture.debugElement.nativeElement.innerHTML).toBeTruthy();
        });
    }));
});