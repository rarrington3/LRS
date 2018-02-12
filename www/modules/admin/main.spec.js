// This file was generated from the module scaffold
// Copyright 2016

import { AdminModule } from './main';

describe('admin/main.js', function () {

    let module;

    beforeEach(function () {
        module = new AdminModule();
    });

    it('should be creatable', function () {
        expect(module).toBeDefined();
    });

    afterEach(function () {
        module = null;
    });

});
