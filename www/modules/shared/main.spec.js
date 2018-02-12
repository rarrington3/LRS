// This file was generated from the module scaffold
// Copyright 2016

import { SharedModule } from './main';

describe('shared/main.js', function () {

    let module;

    beforeEach(function () {
        module = new SharedModule();
    });

    it('should be creatable', function () {
        expect(module).toBeDefined();
    });

    afterEach(function () {
        module = null;
    });

});
