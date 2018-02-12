// This file was generated from the component scaffold
// Copyright 2017

import { async, TestBed} from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { DEV_COMPONENTS } from '../components/';
import { DEV_PROVIDERS } from '../services/';
import { DEV_IMPORTS } from '../main';
import Workflow from './workflow';

xdescribe('admin/components/workflow.js', () => {

    beforeEach(() => {
        TestBed.configureTestingModule({
             declarations: DEV_COMPONENTS,
             providers: DEV_PROVIDERS,
             imports: [ DEV_IMPORTS, RouterTestingModule ]
        });
    });

    it('should be creatable', async(() => {
        TestBed.compileComponents().then(() => {
            const fixture = TestBed.createComponent(Workflow);

            expect(fixture.componentInstance).toBeDefined();
            expect(fixture.debugElement.nativeElement.innerHTML).toBeTruthy();
        });
    }));

    it('should initialize default name to heading', async(() => {
        TestBed.compileComponents().then(() => {
            const fixture = TestBed.createComponent(Workflow);

            fixture.detectChanges();
            expect(fixture.debugElement.nativeElement.querySelector('h1').innerText).toBe('Workflow');
        });
    }));

    it('should initialize custom name to heading', async(() => {
        TestBed.compileComponents().then(() => {
            const fixture = TestBed.createComponent(Workflow);
            fixture.componentInstance.name = 'TEST';

            fixture.detectChanges();
            expect(fixture.debugElement.nativeElement.querySelector('h1').innerText).toBe('TEST');
        });
    }));

});
