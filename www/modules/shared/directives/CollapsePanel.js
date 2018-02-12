// This file was generated from the directive scaffold
// Copyright 2017

import {Directive, ElementRef, Input} from '@angular/core';
import $ from 'jquery';

@Directive({
    selector: '[collapsePanel]',
    host: {
        '(click)': 'onClick($event)'
    }
})
/**
 * @see https://angular.io/docs/ts/latest/api/core/Directive-decorator.html
 * @example
 * <div [collapsePanel]="true"></div>
 */
export default class CollapsePanel {
    /**
     * An example input for this directive
     * @see https://angular.io/docs/ts/latest/api/core/Input-var.html
     */
    @Input('collapsePanel') value: boolean = false;

    @Input('collapsePanelParentSelector') parentSelector: string = '';

    _initialized: boolean = false;

    constructor(elementRef: ElementRef) {
        this._element = elementRef.nativeElement;
    }

    ngOnChanges() {
    }

    onClick() {
        let $root;
        if (this.parentSelector) {
            $root = $(this.parentSelector);
        } else {
            $root = $(this._element.ownerDocument.body);
        }

        // disable toggling, since it takes precedence over 'show' or 'hide' when collapse() the first time use
        // https://github.com/twbs/bootstrap/issues/5859
        if (!this._initialized) {
            $root.find('>.collapse').collapse({'toggle': false});
            this._initialized = true;
        }

        if (!this.value) {
            $root.find('>.collapse').collapse('show');
        }
        else {
            $root.find('>.collapse').collapse('hide');
        }
    }
}
