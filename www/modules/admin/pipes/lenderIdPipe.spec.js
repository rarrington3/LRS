// This file was generated from the pipe scaffold
// Copyright 2016

import LenderIdPipe from './lenderIdPipe';

describe('admin/lenderIdPipe.js', () => {

    let lenderIdPipe:LenderIdPipe;

    beforeEach(() => {
        lenderIdPipe = new LenderIdPipe();
    });

    it('should return formatted value', () => {
        expect(lenderIdPipe.transform('foo')).toBe('foo');
    });

});
