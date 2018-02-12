// This file was generated from the directive scaffold
// Copyright 2017

import {Directive, ElementRef} from '@angular/core';

@Directive({
    selector: 'input[type=text],textarea'
})
/**
 * Global input text handler.
 *
 * Unless spell check is overridden, we enforce spell check.
 *
 * There is a requirement that all LRS textareas have a max character limit of 2000
 * unless the maxlimit attribute is overridden.
 *
 * @see https://angular.io/docs/ts/latest/api/core/Directive-decorator.html
 * @example
 * <div [text]="true"></div>
 */
export default class Text {

    constructor(elementRef: ElementRef) {
        this.$element = elementRef.nativeElement;
    }

    ngOnInit() {
        if (!this.$element.getAttribute('spellcheck')) {
            this.$element.setAttribute('spellcheck', true);
        }

        if (this.$element.tagName && this.$element.tagName === 'TEXTAREA' && !this.$element.getAttribute('maxlength')) {
            this.$element.setAttribute('maxlength', 2000);
        }
    }
}
