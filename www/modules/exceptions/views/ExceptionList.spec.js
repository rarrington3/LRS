// This file was generated from the view scaffold
// Copyright 2016

import { async, TestBed} from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { EXCEPTIONS_VIEWS } from '../views/';
import { EXCEPTIONS_COMPONENTS } from '../components/';
import { EXCEPTIONS_PROVIDERS } from '../services/';
import { WORKLOAD_PROVIDERS } from '../../workload/services/';
import { EXCEPTIONS_IMPORTS } from '../main';
import ExceptionList from './ExceptionList';

describe('exceptions/components/ExceptionList.js', () => {

    beforeEach(() => {
        TestBed.configureTestingModule({
             declarations: [EXCEPTIONS_COMPONENTS, EXCEPTIONS_VIEWS],
             providers: [ EXCEPTIONS_PROVIDERS, WORKLOAD_PROVIDERS ],
             imports: [ EXCEPTIONS_IMPORTS, RouterTestingModule ]
        });
    });

    it('should be creatable', async(() => {
        TestBed.compileComponents().then(() => {
            const fixture = TestBed.createComponent(ExceptionList);

            expect(fixture.componentInstance).toBeDefined();
            expect(fixture.debugElement.nativeElement.innerHTML).toBeTruthy();
        });
    }));

});
