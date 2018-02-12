// This file was generated from the module scaffold
// Copyright 2016


import {DevModule} from './main';

describe('dev/main.js', function () {

    let module;

    beforeEach(function () {
        module = new DevModule();
    });

    it('should be creatable', function () {
        expect(module).toBeDefined();
    });

    afterEach(function () {
        module = null;
    });


});

