// This file was generated from the pipe scaffold
// Copyright 2017

import { Pipe } from '@angular/core';
import moment from 'moment';

@Pipe({
    name: 'datePipe',
    pure: true
})
/**
 * @see https://angular.io/docs/ts/latest/guide/pipes.html
 * @example
 * <div>{{inputExpression | datePipe}}</div>
 */
export default class DatePipe {
    constructor() {
        //TODO: inject any required dependencies
    }
    transform(dateString: String) {
        return dateString ? moment.utc(dateString).format('l') : null;
    }
}
