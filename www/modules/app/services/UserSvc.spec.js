// This file was generated from the service scaffold
// Copyright 2016

import { async, inject, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import {APP_PROVIDERS} from '../services/';
import UserSvc from './UserSvc';

describe('app/services/UserSvc.js', () => {

    beforeEach(() => {
        TestBed.configureTestingModule({
            providers: APP_PROVIDERS,
            imports: [ RouterTestingModule ]
        });
    });

	it('should return UserSvc instance', async(inject([UserSvc], (userSvc:UserSvc) => {
        expect(userSvc).toBeDefined();
    })));
});
