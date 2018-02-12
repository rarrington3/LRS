// This file was generated from the module scaffold
// Copyright 2017

import { MicrostrategyModule } from './main';

describe('microstrategy/main.js', function () {

    let module;

    beforeEach(function () {
        module = new MicrostrategyModule();
    });

    it('should be creatable', function () {
        expect(module).toBeDefined();
    });

    afterEach(function () {
        module = null;
    });

});
