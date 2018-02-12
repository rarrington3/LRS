// This file was generated from the directive scaffold
// Copyright 2016

import {
    Directive,
    ElementRef,
    forwardRef,
    Input,
    SimpleChanges,
    SimpleChange,
    OnChanges
} from '@angular/core';
import {
    NG_VALUE_ACCESSOR,
    ControlValueAccessor,
    NG_VALIDATORS,
    Validator,
    ValidatorFn,
    AbstractControl
} from '@angular/forms';
import cssify from 'cssify';
import $ from 'jquery';

// include select2 lib
require('../../../libs/select2/dist/js/select2.full');
cssify(require('../../../libs/select2/dist/css/select2.css'));

// custom css
cssify(require('./Select.less'));

export const SELECT_VALUE_ACCESSOR: any = {
    // using ng value accessor from Template Driven Forms
    provide: NG_VALUE_ACCESSOR,
    /* eslint-disable */
    useExisting: forwardRef(() => Select),
    /* eslint-enable */
    multi: true
};

export const SELECT_VALUE_VALIDATOR: any = {
    provide: NG_VALIDATORS,
    /* eslint-disable */
    useExisting: forwardRef(() => Select),
    /* eslint-enable */
    multi: true
};

@Directive({
    selector: 'select',
    // directive overrides default provider for ngModel,
    // using NG-VALUE-ACCESSOR to make this happen: https://angular.io/docs/ts/latest/api/forms/index/NG_VALUE_ACCESSOR-let.html
    // more info https://github.com/angular/angular/issues/9146
    providers: [SELECT_VALUE_ACCESSOR, SELECT_VALUE_VALIDATOR]
})
/**
 * Select
 * @see https://angular.io/docs/ts/latest/api/core/Directive-decorator.html
 * @example
 * <select></select>
 */
export default class Select implements ControlValueAccessor, Validator, OnChanges {

    _element: ElementRef = null;
    _initialized: boolean = false;
    _prev: any;
    _temp: any;
    _valFn = null;

    // placeholders for the callbacks which are later provided by the @angular/forms ControlValueAccessor
    _onTouchedCallback: () => void = $.noop;
    _onChangeCallback: (_: any) => void = $.noop;

    _defaultOptions = {
        width: '100%'
    };

    $element: any = null;

    /**
     * whether disabled.
     * @type {boolean}
     */
    @Input() disabled: boolean = false;

    /**
     * whether required.
     * @type {boolean}
     */
    @Input() required: boolean = false;

    /**
     * whether the change event will be trigger from data changes (programmatically) or only user changes.
     * @type {boolean}
     */
    @Input() dataTriggered: boolean = false;

    @Input() alwaysTriggerSelectChange: boolean = false;

    constructor(elementRef: ElementRef) {
        this._element = elementRef.nativeElement;
        this.$element = $(this._element);
    }

    ngOnInit() {
        let $modal = this.$element.closest('.modal');
        let options = Object.assign({}, this._defaultOptions);
        if ($modal.length) {
            options.dropdownParent = $modal;
        }

        this.$element.select2(options);

        // subscribe to select2 events: https://select2.github.io/examples.html#programmatic-control
        this.$element
            .on('select2:select', () => {
                this._change(true); // always trigger ngModel from user selections
            })
            .on('select2:unselect', () => {
                this._change(true); // always trigger ngModel from user selections
            })
            .on('select2:close', () => {
                this.$element.focus();
            });

        this._initialized = true;
    }

    ngOnDestroy() {
        if (this._initialized) {
            this.$element
                .off('select2:select')
                .off('select2:unselect')
                .off('select2:close');
            this.$element.select2('destroy');
        }
    }

    ngAfterContentChecked() {
        let value = this._temp;

        // if there are any value waiting, need to resolve it to select pending items.
        if (value) {
            // all non empty options
            let options = this._getOptions(true);
            // if there are options trigger
            if (options.length) {
                this._trigger(value, options);
                // clear
                this._temp = null;
            }
        }
    }

    /**
     * Implementing from @angular/forms ControlValueAccessor interface
     * responsible to inform select2 when a ngModel change is made
     * @param {any} value the value to write
     * @returns {void}
     */
    writeValue(value: any) {
        // if there is a value we need to make sure we have options already loaded,
        // for select2 to pick the change
        if (value) {
            // all non empty options
            let options = this._getOptions(true);
            // if there are options trigger
            if (options.length) {
                this._trigger(value, options);
            } else { // otherwise need to check at later time in cycle ngAfterContentChecked
                this._temp = value; // store the value for later time
            }
        } else { // otherwise we trigger
            this._trigger(value);
        }
    }

    /**
     * implementing from @angular/forms ControlValueAccessor interface
     * @param {any} fn handler to set for on change callback
     * @returns {void}
     */
    registerOnChange(fn: any) {
        this._onChangeCallback = fn;
    }

    /**
     * implementing from @angular/forms ControlValueAccessor interface
     * @param {any} fn handler to set for on touched callback
     * @returns {void}
     */
    registerOnTouched(fn: any) {
        this._onTouchedCallback = fn;
    }

    /**
     * implementing from @angular/forms Validator interface
     * @param {AbstractControl} control the control to validate
     * @returns {any} { [key: string]: any }
     */
    validate(control: AbstractControl): { [key: string]: any } {
        return this._valFn ? this._valFn(control) : null;
    }

    // changes
    ngOnChanges(changes: SimpleChanges) {
        let disabled: SimpleChange = changes['disabled'];
        let required: SimpleChange = changes['required'];

        if (disabled) {
            let val = this._isPresent(disabled.currentValue);
            this.$element.prop('disabled', val);
        }

        if (required) {
            let val = this._isPresent(required.currentValue);
            if (val) {
                this._valFn = this._requiredValidator();
            } else {
                this._valFn = null;
            }
        }
    }

    /**
     * Responsible to inform ngModel when a select2 change is made
     * @param {boolean} triggerNgModel trigger ngModel
     * @returns {void}
     * @private
     */
    _change(triggerNgModel) {
        let current = this.$element.val();

        // for select multiple we need to map the value from the formatted value first
        // (angular maps ngModel value into format [{index: 'value'}] for select multiple)
        if (this._isMultiple()) {
            this._prev = (this._prev && Array.isArray(this._prev)) ? this._prev : [];
            current = current && Array.isArray(current) ? current : [];

            // selected items
            let data = this.$element.select2('data');

            // maps to the value from format [{index: 'value'}]
            if (data && Array.isArray(data)) {
                current = data.map(item => {
                    // ng-reflected-value holds the value, otherwise fall back to value
                    let value = item.element.attributes['ng-reflect-value'] ?
                        item.element.attributes['ng-reflect-value'].value :
                        item.element.value;
                    return value;
                });
            }

            // trigger if changed
            if (!this._isSame(this._prev, current)) {
                if (triggerNgModel) {
                    this._onChangeCallback(current); // triggers ngModel change
                }
                this._prev = current;
            }
        }
        else {
            this._prev = this._prev || '';
            current = current || '';

            // trigger if changed
            if (this._prev !== current || this.alwaysTriggerSelectChange) {
                if (triggerNgModel) {
                    this._onChangeCallback(current); // triggers ngModel change
                }
                this._prev = current;
            }
        }
    }

    /**
     * Responsible to inform select2 when a ngModel change is made
     * @param {any} value the value
     * @param {Array} options the options
     * @returns {void}
     * @private
     */
    _trigger(value: any, options: Array) {

        let current = this.$element.val();

        // for select multiple we need to map the value to the formatted value first
        // (angular maps ngModel value into format [{index: 'value'}] for select multiple)
        if (this._isMultiple()) {
            current = (current && Array.isArray(current)) ? current : [];
            value = (value && Array.isArray(value)) ? value : [];

            // maps to the value in format [{index: 'value'}]
            let optionsDict = this._getOptionsDict(options);
            value = value.map((item) => {
                return optionsDict[item] && optionsDict[item].value;
            });

            // trigger if changed
            if (!this._isSame(current, value)) {
                this.$element.val(value).trigger('change'); // triggers select2 value change
                this._change(this.dataTriggered); // trigger manual change event
            }
        }
        else {
            current = current || '';
            value = value || '';

            // trigger if changed
            if (current !== value) {
                this.$element.val(value).trigger('change'); // triggers select2 value change
                this._change(this.dataTriggered); // trigger manual change event
            }
        }
    }

    _getOptions(noEmpty: boolean) {
        let options = this.$element.find('option');
        return noEmpty ?
            options.filter((i, option) => {
                // ng-reflected-value holds the value, otherwise fall back to value
                let value = option.attributes['ng-reflect-value'] ?
                    option.attributes['ng-reflect-value'].value :
                    option.value;
                return !!value;
            }) : options;
    }

    _getOptionsDict(options: Array) {
        let optionsDict = {};
        if (options && options.length) {
            options.each((i, option) => {
                // ng-reflected-value holds the value, otherwise fall back to value
                let value = option.attributes['ng-reflect-value'] ?
                    option.attributes['ng-reflect-value'].value :
                    option.value;
                optionsDict[value] = {
                    index: option.index,
                    value: option.value // value holds the ngModel formatted value [{index: 'value'}]
                };
            });
        }
        return optionsDict;
    }

    _isSame(array1: Array, array2: Array) {
        return array1 === array2 ||
            (
                array1 && array2 &&
                array1.length === array2.length &&
                array1.every((element, index) => {
                    return element === array2[index];
                })
            );
    }

    _isPresent(value): boolean {
        let noPresent = (
            value === undefined ||
            value === null ||
            value === false ||
            (typeof value === 'string' && value.toLowerCase() === 'false')
        );
        return !noPresent;
    }

    _isMultiple(): boolean {
        return this.$element.length && this.$element[0].attributes['multiple'] !== undefined;
    }

    _requiredValidator(): ValidatorFn {
        return (control: AbstractControl): { [key: string]: any } => {
            let valid = true;
            if (control.value && Array.isArray(control.value)) { // if is an array
                valid = control.value.length > 0; // at least an item
            }
            else {
                valid = !!control.value; // otherwise at least a value
            }

            return !valid ? {'required': true} : null;
        };
    }
}
