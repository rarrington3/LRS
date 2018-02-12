// This file was generated from the module scaffold
// Copyright 2016

import { LenderModule } from './main';

describe('lender/main.js', function () {

    let module;

    beforeEach(function () {
        module = new LenderModule();
    });

    it('should be creatable', function () {
        expect(module).toBeDefined();
    });

    afterEach(function () {
        module = null;
    });

});
