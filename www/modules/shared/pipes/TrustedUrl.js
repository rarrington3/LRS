// This file was generated from the pipe scaffold
// Copyright 2017

import {
    Pipe,
    PipeTransform
} from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';

@Pipe({
    name: 'trustedUrl',
    pure: true
})
/**
 * Disables Angular's built-in sanitization for the value passed in. Carefully check and audit all
 * values and code paths going into this call. Make sure any user data is appropriately escaped for
 * this security context.
 * @see https://angular.io/docs/ts/latest/guide/pipes.html
 * @see https://angular.io/docs/ts/latest/api/platform-browser/index/DomSanitizer-class.html
 * @example
 * <div>{{inputExpression | trustedUrl}}</div>
 */
export default class TrustedUrl implements PipeTransform {
    constructor(sanitizer: DomSanitizer) {
        this.sanitizer = sanitizer;
    }

    transform(url:any) {
        return this.sanitizer.bypassSecurityTrustResourceUrl(url);
    }
}
