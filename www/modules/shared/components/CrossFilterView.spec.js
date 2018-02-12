// This file was generated from the component scaffold
// Copyright 2017

import { async, TestBed} from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { SHARED_IMPORTS } from '../main';
import {SHARED_DIRECTIVES} from '../directives/';
import {SHARED_COMPONENTS} from '../components/';
import {SHARED_PIPES} from '../pipes/';
import CrossFilterBaseView from './CrossFilterBaseView';

describe('shared/components/CrossFilterBaseView.js', () => {

    beforeEach(() => {
        TestBed.configureTestingModule({
            declarations: [SHARED_DIRECTIVES, SHARED_COMPONENTS, SHARED_PIPES],
            imports: [ SHARED_IMPORTS, RouterTestingModule ]
        });
    });

    it('should be creatable', async(() => {
        TestBed.compileComponents().then(() => {
            const fixture = TestBed.createComponent(CrossFilterBaseView);

            expect(fixture.componentInstance).toBeDefined();
            expect(fixture.debugElement.nativeElement.innerHTML).toBeTruthy();
        });
    }));

});
