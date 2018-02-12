// This file was generated from the directive scaffold
// Copyright 2016

import {Component} from '@angular/core';
import {async, TestBed} from '@angular/core/testing';
import {RouterTestingModule} from '@angular/router/testing';
import {SHARED_COMPONENTS} from '../components/';
import {SHARED_DIRECTIVES} from '../directives/';
import {SHARED_PIPES} from '../pipes/';
import {SHARED_IMPORTS} from '../main';

describe('shared/directives/Datepicker.js', () => {

    let SHARED_TEST_COMPONENTS = [];

    @Component({
        template: '<input data-provide="datepicker">'
    })
    class TestComponent1 {

    }

    SHARED_TEST_COMPONENTS.push(TestComponent1);

    beforeEach(() => {
        TestBed.configureTestingModule({
            declarations: [SHARED_COMPONENTS, SHARED_DIRECTIVES, SHARED_PIPES, SHARED_TEST_COMPONENTS],
            providers: [],
            imports: [SHARED_IMPORTS, RouterTestingModule]
        });
    });

    it('should initialize default value', async(() => {
        TestBed.compileComponents().then(() => {
            const fixture = TestBed.createComponent(TestComponent1);

            fixture.detectChanges();
            expect(fixture.nativeElement.querySelector('[data-provide=datepicker]').classList.contains('datepicker')).toBe(false);
        });
    }));


    // TODO: Create meaningful unit tests
});

