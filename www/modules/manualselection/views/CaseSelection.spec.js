// This file was generated from the view scaffold
// Copyright 2016

import { async, TestBed} from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { MANUALSELECTION_VIEWS } from '../views/';
import { MANUALSELECTION_COMPONENTS } from '../components/';
import { MANUALSELECTION_PROVIDERS } from '../services/';
import { MANUALSELECTION_IMPORTS } from '../main';
import CaseSelection from './CaseSelection';

describe('manualselection/components/CaseSelection.js', () => {

    beforeEach(() => {
        TestBed.configureTestingModule({
             declarations: [ MANUALSELECTION_COMPONENTS, MANUALSELECTION_VIEWS ],
             providers: MANUALSELECTION_PROVIDERS,
             imports: [ MANUALSELECTION_IMPORTS, RouterTestingModule ]
        });
    });

    it('should be creatable', async(() => {
        TestBed.compileComponents().then(() => {
            const fixture = TestBed.createComponent(CaseSelection);

            expect(fixture.componentInstance).toBeDefined();
            expect(fixture.debugElement.nativeElement.innerHTML).toBeTruthy();
        });
    }));

});
