// This file was generated from the directive scaffold
// Copyright 2016

import {Directive, ElementRef, Input} from '@angular/core';
import $ from 'jquery';
import _ from 'lodash';

@Directive({
    selector: '[display]'
})
/**
 * @see https://angular.io/docs/ts/latest/api/core/Directive-decorator.html
 * @example
 * <div [display]="true"></div>
 */
export default class Display {
    /**
     * Similar to [hidden] but it adds a display class and
     * waits an amount of ms after applying display-after class for
     * css transitions to work
     * An example input for this directive
     * @see https://angular.io/docs/ts/latest/api/core/Input-var.html
     */
    @Input('display') value: boolean = false;

    @Input('onDisplayTimer') onTimer: number = 100;

    @Input('offDisplayTimer') offTimer: number = 250;

    _setDynamicHeight: boolean = false;

    _setDynamicHeightSelector: string = null;

    _timer: Object = null;

    constructor(elementRef: ElementRef) {
        this.$element = elementRef.nativeElement;
    }

    ngOnChanges() {

        // check to see if we have additional arguments
        if (this.value && _.isObject(this.value)) {
            this._setDynamicHeight = this.value['setDynamicHeight'];
            this._setDynamicHeightSelector = this.value['setDynamicHeightSelector'];
            this.value = this.value['display'];
        }

        this._startTimer();
    }

    _startTimer() {
        this._clearTimer();
        let $elem = $(this.$element);
        let onTimer = this.onTimer;
        let offTimer = this.offTimer;

        if (this.value) {
            $elem.addClass('display');
            this._timer = setTimeout(() => {
                if (this._setDynamicHeight && this._setDynamicHeightSelector && this._setDynamicHeightSelector.length) {
                    let height = $elem.find(`>${this._setDynamicHeightSelector}`).height();
                    if (height > 0) {
                        $elem.css('height', `${height}px`);
                    }
                }

                $elem.addClass('display-after');
            }, onTimer);
        }
        else {
            $elem.removeClass('display-after');
            this._timer = setTimeout(() => {
                $elem.removeClass('display');
                $elem.css('height', null);
            }, offTimer);
        }
    }
    _clearTimer() {
        if (this._timer) {
            clearTimeout(this._timer);
        }
    }
}
