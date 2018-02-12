// This file was generated from the directive scaffold
// Copyright 2016

import {Component} from '@angular/core';
import {async, TestBed} from '@angular/core/testing';
import {RouterTestingModule} from '@angular/router/testing';
import {SHARED_COMPONENTS} from '../components/';
import {SHARED_DIRECTIVES} from '../directives/';
import {SHARED_PIPES} from '../pipes/';
import {SHARED_IMPORTS} from '../main';

describe('shared/directives/Display.js', () => {

    let SHARED_TEST_COMPONENTS = [];

    @Component({
        template: '<div display></div>'
    })
    class TestComponent1 {

    }

    SHARED_TEST_COMPONENTS.push(TestComponent1);

    @Component({
        template: '<div display="true"></div>'
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
            expect(fixture.nativeElement.querySelector('[display]').classList.contains('display')).toBe(false);
        });
    }));

    it('should initialize custom value', async(() => {
        TestBed.compileComponents().then(() => {
            const fixture = TestBed.createComponent(TestComponent2);

            fixture.detectChanges();
            expect(fixture.nativeElement.querySelector('[display]').classList.contains('display')).toBe(true);
        });
    }));
});
