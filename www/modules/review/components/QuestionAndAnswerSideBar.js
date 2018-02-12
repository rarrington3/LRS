// This file was generated from the component scaffold
// Copyright 2016

import {Component} from '@angular/core';
import template from './QuestionAndAnswerSideBar.html';
import styles from './QuestionAndAnswerSideBar.less';
import GlobalStateSvc from '../services/GlobalStateSvc';
import ReviewService from '../services/ReviewService';
import ModalSvc from '../../app/services/ModalSvc';
import LoanAttributeBase from './LoanAttributesBase';
import {FormBuilder } from '@angular/forms';
import Utils from '../../shared/Utils';

@Component({
    selector: 'question-and-answer-side-bar',
    template: template,
    styles: [styles]
})
export default class QuestionAndAnswerSideBar extends LoanAttributeBase {
    wrapupTitle: string = 'Review Level Wrap-Up';
    /**
     * Area defects filtered by review id.
     */
    defectAreas: Array = [];
    _subscriptions = [];

    constructor(globalState: GlobalStateSvc, reviewService: ReviewService, formBuilder: FormBuilder, modalSvc: ModalSvc) {
        super(formBuilder, reviewService, globalState);
        this._modalSvc = modalSvc;
    }

    ngOnInit() {
        super.ngOnInit();

        this._resetSubscriptions();
        this._subscriptions.push(this.globalStateService.defectAreasForCurrentReviewObservable.subscribe((change) => {
            this.defectAreas = change.currentValue;
            if (Utils.isReviewVetted(this._reviewService.currentReview)) {
                this.wrapupTitle = 'Vetting Review';
            }
        }));

        this._subscriptions.push(this._reviewService.summaryFieldsForCurrentDefectAreaObservable.subscribe((change) => {
            this.reviewFields = change.currentValue;

        }));
    }

    ngOnDestroy() {
        super.ngOnDestroy();
        this._resetSubscriptions();
    }

    _resetSubscriptions() {
        this._subscriptions.forEach((subscription) => {
            if (subscription.unsubscribe) {
                subscription.unsubscribe();
            }
        });

        this._subscriptions = [];
    }

    isSelected(defectArea) {
        if (defectArea && defectArea.defectAreaCode && this.reviewFields) {
            return this.reviewFields.length && defectArea.defectAreaCode === this.globalStateService.currentDefectAreaCode;
        }
        if (defectArea === 'wrapUp'){
            return this.globalStateService.reviewWrapUp;
        }
        else {
            return this.globalStateService.loanSummarySelected;
        }
    }

    cancelDefect() {
        this._reviewService.hideWrapUp();
    }

    showWrapUp(){
        this._reviewService.displayWrapUp();
        this.globalStateService.reviewWrapUp=true;
    }

    isCompleted(defectArea) {
        if (this._reviewService.defectAreasCompleted && defectArea.defectAreaCode) {
            return this._reviewService.defectAreasCompleted[defectArea.defectAreaCode];
        } else {
            return false;
        }
    }

    onSubmit() {
        this._confirmAffectedQuestions();
    }

    trackField(field: Object) {
        return field.fieldId;
    }

    trackDefectArea(defectArea: Object) {
        return defectArea.defectAreaId;
    }

}
