// This file was generated from the component scaffold
// Copyright 2017

import {async, TestBed} from '@angular/core/testing';
import {RouterTestingModule} from '@angular/router/testing';
import {APP_COMPONENTS} from '../components/';
import {APP_PROVIDERS} from '../services/';
import {SharedModule} from '../../shared/main';
import SaveBarOutlet from './SaveBarOutlet';

describe('app/components/SaveBarOutlet.js', () => {

    beforeEach(() => {
        TestBed.configureTestingModule({
            declarations: APP_COMPONENTS,
            providers: APP_PROVIDERS,
            imports: [SharedModule, RouterTestingModule]
        });
    });

    it('should be creatable', async(() => {
        TestBed.compileComponents().then(() => {
            const fixture = TestBed.createComponent(SaveBarOutlet);

            expect(fixture.componentInstance).toBeDefined();
            expect(fixture.debugElement.nativeElement.innerHTML).toBeTruthy();
        });
    }));

});
