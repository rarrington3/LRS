// This file was generated from the pipe scaffold
// Copyright 2017

import DatePipe from './DatePipe';

describe('shared/DatePipe.js', () => {

    let datePipe:DatePipe;

    beforeEach(() => {
        datePipe = new DatePipe();
    });

    it('should return formatted value', () => {
        expect(datePipe.transform('2016-03-23T00:00:00.0')).toBe('3/23/2016');
    });

});
