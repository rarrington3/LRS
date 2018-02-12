// This file was generated from the component scaffold
// Copyright 2016

import {Component, Input} from '@angular/core';
import template from './LoanSummaryField.html';
import styles from './LoanSummaryField.less';
import {FormGroup} from '@angular/forms';
import DatePipe from '../../shared/pipes/DatePipe';


@Component({
    selector: 'loan-summary-field',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/api/core/Component-decorator.html
 * @example
 * <loan-summary-field name="LoanSummaryField" (change)="onChange($event)"></loan-summary-field>
 */
export default class LoanSummaryField {
    @Input()
    field: Object = {};

    @Input()
    form: FormGroup = {};

    constructor() {
    }

    ngOnInit() {
        this.formatValue();
    }

    formatValue() {
        if (this.field && this.field.value && this.field.type) {
            if (this.field.type.toLowerCase() === 'date') {
                let datePipe = new DatePipe();
                this.field.value = datePipe.transform(this.field.value);
            }
            if (!this.field.isEditable && this.field.type.toLowerCase() === 'boolean') {
                if (this.field.value.toLowerCase() === 'y') {
                    this.field.value = 'Yes';
                }
                else if (this.field.value.toLowerCase() === 'n') {
                    this.field.value = 'No';
                }
                else {
                    return null;
                }
            }
        }
        return null;
    }

}
