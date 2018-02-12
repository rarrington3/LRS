// This file was generated from the directive scaffold
// Copyright 2017

import {Component} from '@angular/core';
import {async, TestBed} from '@angular/core/testing';
import {RouterTestingModule} from '@angular/router/testing';
import {SHARED_COMPONENTS} from '../components/';
import {SHARED_DIRECTIVES} from '../directives/';
import {SHARED_PIPES} from '../pipes/';
import {SHARED_IMPORTS} from '../main';

describe('shared/directives/CollapsePanel.js', () => {

    let SHARED_TEST_COMPONENTS = [];

    @Component({
        template: '<div collapsePanel></div>'
    })
    class TestComponent1 {

    }

    SHARED_TEST_COMPONENTS.push(TestComponent1);

    @Component({
        template: '<div collapsePanel="true"></div>'
    })
    class TestComponent2 {

    }

    SHARED_TEST_COMPONENTS.push(TestComponent2);

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
            expect(fixture.nativeElement.querySelector('[collapsePanel]').classList.contains('collapsePanel')).toBe(false);
        });
    }));

    // TODO: Create meaningful unit tests
});
