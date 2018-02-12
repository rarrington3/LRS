// This file was generated from the pipe scaffold
// Copyright 2016

import {Pipe} from '@angular/core';

@Pipe({
    name: 'lenderIdPipe',
    pure: true
})
/**
 * This filter can be used when viewing any list of lenders. See LenderSuppression.html for use. In this case
 * example we pass in a string the user types into an input.
 * Eg:
 *  <tr *ngFor="let item of lenders | lenderIdPipe:listFilter.value">
 *      <td class="lender-td" scope="row">{{item.lender.lenderId}}</td>
 *      <td class="lender-name">{{item.lender.name}}</td>
 *       <td class="lender-td">{{item.activeReviews}}</td>
 *       <td class="lender-td">{{item.startDate}}</td>
 *       <td class="lender-td"><button class="btn btn-primary" [attr.aria-label]="'Remove ' + item.lender.name" (click)="removeLender(item.lender.lenderId)">Remove</button></td>
 *  </tr>
 *
 * @see https://angular.io/docs/ts/latest/guide/pipes.html
 * @example
 * <div>{{inputExpression | lenderIdPipe}}</div>
 */
export default class LenderIdPipe {
    constructor() {
        //TODO: inject any required dependencies
    }

    /**
     * This method searches each lender item lenderId and name for a match of the args string passed in
     *
     * @param {input} input is an array of items to filter on
     * @param {args} args is a string to match within each item in the array passed in
     * @returns {Array} Array of filtered items
     * */
    transform(input:any, args:Array) {
        return args ? input.filter(item => item.lender.lenderId.indexOf(args) !== -1 || item.lender.name.indexOf(args) !== -1) : input;
    }
}
