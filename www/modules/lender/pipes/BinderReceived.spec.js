// This file was generated from the pipe scaffold
// Copyright 2016

import BinderReceived from './BinderReceived';

describe('lender/pipes/BinderReceived.js', () => {

    let binderReceived:BinderReceived;

    beforeEach(() => {
        binderReceived = new BinderReceived();
    });

    it('should return formatted value', () => {
        let binders = [{id: 1, binderReceived: true}, {id: 2, binderReceived: false }];
        let result = binderReceived.transform(binders, true);
        expect(result.length).toEqual(1);
        expect(result[0].id).toEqual(1);

        result = binderReceived.transform(binders, false);
        expect(result.length).toEqual(1);
        expect(result[0].id).toEqual(2);
    });

});
