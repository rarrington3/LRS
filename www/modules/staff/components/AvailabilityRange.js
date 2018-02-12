// This file was generated from the component scaffold
// Copyright 2016

import {Component, Input, Output, EventEmitter, SimpleChanges, SimpleChange, ViewChild} from '@angular/core';
import {NgForm}  from '@angular/forms';
import template from './AvailabilityRange.html';
import styles from './AvailabilityRange.less';

import _ from 'lodash';

@Component({
    selector: 'availability-range',
    template: template,
    styles: [styles]
})
/**
 * Availability Range
 * @example
 * <availability-range></availability-range>
 */
export default class AvailabilityRange {

    _removed: boolean = false;

    form: NgForm;

    @Input() unavailabilities: Array<any> = [];

    @Output() canSaveChange: EventEmitter = new EventEmitter();

    @ViewChild('staffAvailabilityForm') currentForm: NgForm;

    constructor() {
    }

    ngAfterViewChecked() {
        this.onFormChanged();
    }

    ngOnChanges(changes: SimpleChanges) {
        let unavailabilitiesChange: SimpleChange = changes['unavailabilities'];

        if (unavailabilitiesChange) {
            this.reset(_.isEmpty(unavailabilitiesChange.currentValue));
        }
    }

    reset(resetForm) {
        this._removed = false;

        if (resetForm && this.form) {
            this.form.resetForm();
        }

        this.checkCanSave();
    }

    add() {
        this.unavailabilities.push({
            from: '',
            to: ''
        });
    }

    remove(unavailability) {
        let index = this.unavailabilities.indexOf(unavailability);
        this.unavailabilities.splice(index, 1);

        this._removed = true;
        this.checkCanSave();
    }

    checkCanSave() {
        if (this.form){
            let canSave = !!(this.form && this.form.dirty && this.form.valid) || this._removed;
            this.canSaveChange.emit(canSave);
        }
    }

    /**
     * Checks on form change, pattern that can be used to verify form's changes
     * @returns {void}
     */
    onFormChanged() {
        if (this.currentForm === this.form) {
            return;
        }

        this.form = this.currentForm;

        if (!this.form) {
            return;
        }

        this.form.valueChanges.subscribe(() => {
            //trigger check
            this.checkCanSave();
        });
    }
}
