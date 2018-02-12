import './index';

import 'zone.js/dist/proxy.js';
import 'zone.js/dist/sync-test.js';
import 'zone.js/dist/jasmine-patch.js';
import 'zone.js/dist/async-test.js';
import 'zone.js/dist/fake-async-test.js';

import {BrowserDynamicTestingModule, platformBrowserDynamicTesting} from '@angular/platform-browser-dynamic/testing';
import {TestBed, MockApplicationRef} from '@angular/core/testing';
import {ApplicationRef} from '@angular/core';
import {Http, BaseRequestOptions} from '@angular/http';
import {MockBackend} from '@angular/http/testing';
import DEFAULT_PROVIDERS from './app/providers';

TestBed.initTestEnvironment(BrowserDynamicTestingModule, platformBrowserDynamicTesting([
    DEFAULT_PROVIDERS,
    MockBackend,
    BaseRequestOptions,
    {provide: Http, useFactory: (backend, options) => { return new Http(backend, options); }, deps: [MockBackend, BaseRequestOptions]},
    {provide: ApplicationRef, useClass: MockApplicationRef}
]));