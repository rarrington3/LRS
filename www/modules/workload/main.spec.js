// This file was generated from the module scaffold
// Copyright 2016

import {WorkloadModule} from './main';

describe('workload/main.js', function () {

    let module;

    beforeEach(function () {
        module = new WorkloadModule();
    });

    it('should be creatable', function () {
        expect(module).toBeDefined();
    });

    afterEach(function () {
        module = null;
    });

});
