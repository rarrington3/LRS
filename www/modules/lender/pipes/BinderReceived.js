// This file was generated from the pipe scaffold
// Copyright 2016

import {Pipe} from '@angular/core';

@Pipe({
    name: 'binderReceived',
    pure: true
})
/**
 * Simple filter for binders being received or not. This filtering may
 * become more complex later when we know more about 'receiving' a binder.
 *
 * @see https://angular.io/docs/ts/latest/guide/pipes.html
 * @example
 * <div>{{inputExpression | binderReceived}}</div>
 */
export default class BinderReceived {
    constructor() {

    }

    transform(input:any, isReceived: Boolean) {

        if (isReceived) {
            return input.filter(item => item.binderReceived);
        } else {
            return input.filter(item => !item.binderReceived);
        }
    }
}
