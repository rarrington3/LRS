// This file was generated from the component scaffold
// Copyright 2016

import {Component, Input} from '@angular/core';
import template from './DefectAreaSummaryField.html';
import styles from './DefectAreaSummaryField.less';
import {FormGroup} from '@angular/forms';
import DatePipe from '../../shared/pipes/DatePipe';

@Component({
    selector: 'defect-area-summary-field',
    template: template,
    styles: [styles]
})
/**
 * @description This component doesn't do much. It was created so we can encapsulate the logic to dynamically display
 * an Input type such as select, radio, multiselect or checkboxes via ngSwitch in its template.
 * @see https://angular.io/docs/ts/latest/api/core/Component-decorator.html
 * @example
 * <defect-area-summary-summaryField name="DefectAreaSummaryField" (change)="onChange($event)"></defect-area-summary-field>
 */

export default class DefectAreaSummarysummaryField {
    @Input()
    summaryField: Object = {};

    @Input()
    form: FormGroup = {};

    constructor() {
    }

    ngOnInit() {
        this.formatValue();
    }

    formatValue() {
        if (this.summaryField && this.summaryField.value && this.summaryField.type) {
            if (this.summaryField.type.toLowerCase() === 'date') {
                let datePipe = new DatePipe();
                this.summaryField.value = datePipe.transform(this.summaryField.value);
            }
            if (!this.summaryField.isEditable && this.summaryField.type.toLowerCase() === 'boolean') {
                if (this.summaryField.value.toLowerCase() === 'y') {
                    this.summaryField.value = 'Yes';
                }
                else if (this.summaryField.value.toLowerCase() === 'n') {
                    this.summaryField.value = 'No';
                }
                else {
                    return null;
                }
            }
        }
        return null;
    }

}
