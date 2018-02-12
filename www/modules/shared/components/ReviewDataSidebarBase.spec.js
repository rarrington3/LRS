// This file was generated from the component scaffold
// Copyright 2016

import {async, TestBed} from '@angular/core/testing';
import {RouterTestingModule} from '@angular/router/testing';
import {SHARED_DIRECTIVES} from '../directives/';
import {SHARED_COMPONENTS} from '../components/';
import {SHARED_PIPES} from '../pipes/';
import {SHARED_IMPORTS} from '../main';
import {REVIEW_PROVIDERS} from '../../review/services/';
import ReviewDataSidebarBase from './ReviewDataSidebarBase';

describe('shared/components/ReviewDataSidebarBase.js', () => {

    beforeEach(() => {
        TestBed.configureTestingModule({
            declarations: [SHARED_DIRECTIVES, SHARED_COMPONENTS, SHARED_PIPES],
            providers: REVIEW_PROVIDERS,
            imports: [SHARED_IMPORTS, RouterTestingModule]
        });
    });

    it('should be creatable', async(() => {
        TestBed.compileComponents().then(() => {
            const fixture = TestBed.createComponent(ReviewDataSidebarBase);
            expect(fixture.componentInstance).toBeDefined();
        });
    }));

});
