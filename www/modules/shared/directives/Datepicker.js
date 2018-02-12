// This file was generated from the directive scaffold
// Copyright 2016

import {Directive, ElementRef, Input, Output, EventEmitter, SimpleChanges, SimpleChange, forwardRef} from '@angular/core';
import {NG_VALUE_ACCESSOR} from '@angular/forms';
import $ from 'jquery';

export const DATEPICKER_VALUE_ACCESSOR: any = {
    // using ng value accessor from Template Driven Forms
    provide: NG_VALUE_ACCESSOR,
    /* eslint-disable */
    useExisting: forwardRef(() => Datepicker),
    /* eslint-enable */
    multi: true
};

@Directive({
    // enhances default bootstrap-datepicker
    selector: 'input[data-provide=datepicker]',
    // directive overrides default provider for ngModel,
    // using NG-VALUE-ACCESSOR to make this happen: https://angular.io/docs/ts/latest/api/forms/index/NG_VALUE_ACCESSOR-let.html
    // more info https://github.com/angular/angular/issues/9146
    providers: [DATEPICKER_VALUE_ACCESSOR]
})
/**
 * @see https://angular.io/docs/ts/latest/api/core/Directive-decorator.html
 * @example
 * <div [datepicker]="true"></div>
 */
export default class Datepicker {
    _element: any;
    _initialized: boolean = false;
    _prev: any;

    $element: any = null;

    // placeholders for the callbacks which are later provided by the @angular/forms ControlValueAccessor
    _onTouchedCallback: () => {} = $.noop;
    _onChangeCallback: (_: any) => {} = $.noop;

    @Input() isOpen: boolean = false;

    @Output() isOpenChange: EventEmitter = new EventEmitter();

    constructor(elementRef: ElementRef) {
        this._element = elementRef.nativeElement;
        this.$element = $(this._element);
    }

    ngOnInit() {
        let that = this;
        this.$element.datepicker()
            .on('show', function () {
                if (!that.isOpen) {
                    that.isOpen = true;
                    that.isOpenChange.emit(that.isOpen);
                }
            })
            .on('hide', function (e) {
                if (that.isOpen) {
                    that.isOpen = false;
                    that.isOpenChange.emit(that.isOpen);
                }
                e.stopPropagation(); // prevents parent modals from closing
            })
            .on('changeDate', function () {
                let current = that.$element.val();

                // trigger if changed
                if (that._prev !== current) {
                    that._onChangeCallback(current); // triggers ngModel change
                    that._prev = current;
                }
            });
        this._initialized = true;
    }

    ngOnDestroy() {
        if (this._initialized) {
            this.$element.datepicker()
                .off('show')
                .off('hide')
                .off('changeDate');
            this.$element.datepicker('destroy');
        }
    }

    ngOnChanges(changes: SimpleChanges) {
        let isOpenChange: SimpleChange = changes['isOpen'];

        if (isOpenChange) {
            if (isOpenChange.currentValue) {
                this.$element.datepicker('show');
            }
            else {
                this.$element.datepicker('hide');
            }
        }
    }

    /**
     * Responsible to inform datepicker when a ngModel change is made,
     * implementing from @angular/forms ControlValueAccessor interface
     * @param {any} value The value.
     * @returns {void}
     */
    writeValue(value: any) {
        let current = this.$element.val();
        current = current || '';
        value = value || '';

        // trigger if changed
        if (current !== value) {
            let date = new Date(value);
            this.$element.datepicker('update', date); // triggers datepicker change
        }
    }


    /**
     * implementing from @angular/forms ControlValueAccessor interface
     * @param {any} fn register on change function
     * @returns {void}
     */
    registerOnChange(fn: (_: any) => {}) {
        this._onChangeCallback = fn;
    }

    /**
     * implementing from @angular/forms ControlValueAccessor interface
     * @param {any} fn register on touched function
     * @returns {void}
     */
    registerOnTouched(fn: () => {}) {
        this._onTouchedCallback = fn;
    }
}
