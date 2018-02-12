// This file was generated from the module scaffold
// Copyright 2016

import {StaffModule} from './main';

describe('staff/main.js', function () {

    let module;

    beforeEach(function () {
        module = new StaffModule();
    });

    it('should be creatable', function () {
        expect(module).toBeDefined();
    });

    afterEach(function () {
        module = null;
    });

});

