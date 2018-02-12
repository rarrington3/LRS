// This file was generated from the module scaffold
// Copyright 2016

import { ExceptionsModule } from './main';

describe('exceptions/main.js', function () {

    let module;

    beforeEach(function () {
        module = new ExceptionsModule();
    });

    it('should be creatable', function () {
        expect(module).toBeDefined();
    });

    afterEach(function () {
        module = null;
    });

});
