// This file was generated from the service scaffold
// Copyright 2016

import {async, inject, TestBed} from '@angular/core/testing';
import {APP_PROVIDERS} from '../services/';
import ModalSvc from './ModalSvc';

describe('app/services/ModalSvc.js', () => {

    beforeEach(() => {
        TestBed.configureTestingModule({
            providers: APP_PROVIDERS
        });
    });

    it('should return ModalSvc instance', async(inject([ModalSvc], (modalSvc: ModalSvc) => {
        expect(modalSvc).toBeDefined();
    })));

});

