// This file was generated from the component scaffold
// Copyright 2016

import { async, TestBed} from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { MANUALSELECTION_COMPONENTS } from '../components/';
import { MANUALSELECTION_PROVIDERS } from '../services/';
import { MANUALSELECTION_IMPORTS } from '../main';
import SelectionContainer from './SelectionContainer';

describe('manualselection/components/SelectionContainer.js', () => {

    beforeEach(() => {
        TestBed.configureTestingModule({
             declarations: MANUALSELECTION_COMPONENTS,
             providers: MANUALSELECTION_PROVIDERS,
             imports: [ MANUALSELECTION_IMPORTS, RouterTestingModule ]
        });
    });

    it('should be creatable', async(() => {
        TestBed.compileComponents().then(() => {
            const fixture = TestBed.createComponent(SelectionContainer);

            expect(fixture.componentInstance).toBeDefined();
            expect(fixture.debugElement.nativeElement.innerHTML).toBeTruthy();
        });
    }));

    it('should initialize default name to heading', async(() => {
        TestBed.compileComponents().then(() => {
            const fixture = TestBed.createComponent(SelectionContainer);

            fixture.detectChanges();
            expect(fixture.debugElement.nativeElement.querySelector('h1').innerText).toBe('SelectionContainer');
        });
    }));

    it('should initialize custom name to heading', async(() => {
        TestBed.compileComponents().then(() => {
            const fixture = TestBed.createComponent(SelectionContainer);
            fixture.componentInstance.name = 'TEST';

            fixture.detectChanges();
            expect(fixture.debugElement.nativeElement.querySelector('h1').innerText).toBe('TEST');
        });
    }));

});
