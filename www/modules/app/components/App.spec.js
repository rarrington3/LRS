// This file was generated from the component scaffold
// Copyright 2016

import {async, TestBed} from '@angular/core/testing';
import {RouterTestingModule} from '@angular/router/testing';
import {APP_COMPONENTS} from '../components/';
import {APP_PROVIDERS} from '../services/';
import {SharedModule} from '../../shared/main';
import {Observable} from 'rxjs';
import App from './App';
import info from '../../../../package.json';

let errorReporter =  require('modules/preloader/errorreporter');

describe('app/components/App.js', () => {
    beforeEach(() => {
        TestBed.configureTestingModule({
            declarations: APP_COMPONENTS,
            providers: APP_PROVIDERS,
            imports: [SharedModule, RouterTestingModule]
        });
    });

    it('should return module name', async(() => {
        TestBed.compileComponents().then(() => {
            const fixture = TestBed.createComponent(App);
            expect(fixture.componentInstance.name).toBe(info.description);
        });
    }));

    it('should wire up error reporting to use the codegenerated api wrapper', async(() => {
        TestBed.compileComponents().then(() => {
            const fixture = TestBed.createComponent(App);
            spyOn(fixture.componentInstance.api.resources.errors, 'post').and.returnValue( Observable.from('test') );
            fixture.componentInstance.ngOnInit();
            expect(fixture.componentInstance.api.resources.errors.post).not.toHaveBeenCalled();
            errorReporter.send({ logHistory: [] });
            expect(fixture.componentInstance.api.resources.errors.post).toHaveBeenCalled();
        });
    }));

    it('should add dev tool links when running in dev mode', async(() => {
        TestBed.compileComponents().then(() => {
            const fixture = TestBed.createComponent(App);
            var linkCount = fixture.componentInstance.mainLinks.length;
            fixture.componentInstance.uiConfig = {};
            expect(fixture.componentInstance.mainLinks.length).toEqual(linkCount);
        });
    }));

});
