// This file was generated from the component scaffold
// Copyright 2016

import { Component, ElementRef } from '@angular/core';
import template from './LoanSummary.html';
import styles from './LoanSummary.less';
import GlobalStateSvc from '../services/GlobalStateSvc';
import ReviewService from '../services/ReviewService';
import _ from 'lodash';
import LoanAttributeBase from './LoanAttributesBase';
import { FormBuilder } from '@angular/forms';
import ModalSvc from '../../app/services/ModalSvc';
import DatePipe from '../../shared/pipes/DatePipe';
import Utils from '../../shared/Utils';

@Component({
    selector: 'loan-summary',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/api/core/Component-decorator.html
 * @example
 * <loan-summary name="LoanSummary" (change)="onChange($event)"></loan-summary>
 */
export default class LoanSummary extends LoanAttributeBase {
    globalStateSvc: GlobalStateSvc;
    _reviewService: ReviewService;
    fieldGroups: Array = [];
    _subscriptions: Array = [];

    constructor(globalStateSvc: GlobalStateSvc, reviewService: ReviewService, elementRef: ElementRef, formBuilder: FormBuilder, modalSvc: ModalSvc) {
        super(formBuilder, reviewService, globalStateSvc);
        this._elementRef = elementRef;
        this._modalSvc = modalSvc;
    }

    ngOnInit() {
        super.ngOnInit();

        // if this component is being initialized, that means the user is not looking at any defect area
        this.globalStateService.loanSummarySelected = true;
        this._subscriptions = [];
        this._subscriptions.push(this._reviewService.summaryFieldsForCurrentReviewLevelObservable.subscribe((result) => this.updateFieldGroups(result.currentValue)));
        this.updateFieldGroups(this._reviewService.summaryFieldsForCurrentReviewLevel);
    }

    updateFieldGroups(fields) {

        // Group the fields by 'loanAttributeGroup'.
        if (_.isArray(fields)) {
            let groups = _.groupBy(fields, (f) => {
                return f.loanAttributeGroup;
            });

            let filteredGroups = {};

            //filter out fileds with loanAttributeGroup==null. Null made string by _.groupBy
            _.forEach(groups, (value, key) => {
                if (key !== 'null') {
                    filteredGroups[key] = value;
                }
            });

            this.reviewFields = fields;
            this.fieldGroups = _.keys(filteredGroups).map((key) => { return { label: key, fields: filteredGroups[key] }; }).sort((a, b) => { return a.label > b.label ? 1 : -1; });
        }
    }

    ngOnDestroy() {
        super.ngOnDestroy();

        this.globalStateService.loanSummarySelected = false;
        this._subscriptions.forEach((subscription) => {
            subscription.unsubscribe();
        });
    }

    /**
     * @description - Jump to an anchor link. I'm using jquery as a last resort because there's no easy way to link to an anchor link via href and bypass angular's router.
     * @param {object} $evt - Mouse event.
     * @param {string} name - Anchor link.
     * @return {void}
     */
    onAnchorClick($evt, name: string) {
        Utils.handleAnchorClick($evt, this._elementRef, name);
    }

    onSubmit() {
        this._confirmAffectedQuestions();
    }

    getArrayValues(values: Array, type: string, selectChoices: Array = null) {
        if (type === 'DATE') {
            let formattedDates = [];
            let datePipe = new DatePipe();

            values.forEach((value) => {
                formattedDates.push(datePipe.transform(value));
            });
            return formattedDates;
        }
        else if (selectChoices && selectChoices.length) {

            // Map values with full descriptions if found.
            let fullValues = [];
            let fullValue = null;

            values.forEach((value) => {
                fullValue = selectChoices.find(choice => choice.code === value);
                if (fullValue) {
                    fullValues.push(fullValue.description + ' (' + fullValue.code + ')');
                }
            });

            if (fullValues && fullValues.length) {
                return fullValues.toString();
            }
        }

        return values ? values.toString() : '';
    }

}
