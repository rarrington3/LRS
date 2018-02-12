// This file was generated from the service scaffold
// Copyright 2016

import { Injectable } from '@angular/core';
import ObservableProperty from '../../shared/decorators/observableProperty';

/**
 *Use self to reference to this GlobalStateSvc in the ObservableProperty decorator handlers because I couldn't figure out how to pass the correct scope
 *back from the ObservableProperty decorator.
 */
let _self = null;
/**
 * @example
 * let injector = Injector.resolveAndCreate([GlobalStateSvc]);
 * let globalStateSvc = new injector.get(GlobalStateSvc);
 * @example
 * class Component {
 *      constructor(globalStateSvc:GlobalStateSvc, globalStateSvc2:GlobalStateSvc) {
 *          //injected via angular, a singleton by default in same injector context
 *          console.log(globalStateSvc === globalStateSvc2);
 *      }
 * }
 */
@Injectable()
export default class GlobalStateSvc {
    @ObservableProperty()
    loanAttributesEdited: Boolean = false;

    @ObservableProperty()
    defectAreasForCurrentReview: Array = [];

    @ObservableProperty(
        // if we've changed reviews, we need to reset the review level too
        () => {
            _self.currentReviewLevelId = null;
            _self.currentDefectAreaCode = null;
            _self.loanSummarySelected = false;
            _self.reviewWrapUp = false;
            _self.currentQaModelId = null;
        }
    )
    currentReviewId: string = null;

    @ObservableProperty()
    currentQaModelId: string = null;

    @ObservableProperty(
        // set loanSummarySelected to false if a defect area is selected to show the QA tree.
        (result) => {
            if (result.currentValue) {
                _self.loanSummarySelected = false;
                _self.loanAttributesEdited = false;
                _self.reviewWrapUp = false;
            }
        }
    )
    currentDefectAreaCode: string = null;

    @ObservableProperty()
    currentReviewLevelId: string = null;

    @ObservableProperty()
    initialReviewLevelId: string = null;

    @ObservableProperty(
        // set defect area code to null if the Loan Summary view is displayed
        (result) => {
            if (result.currentValue) {
                _self.currentDefectAreaCode = null;
                _self.reviewWrapUp = false;

            }
        })
    loanSummarySelected: boolean = false;

    @ObservableProperty(
        // set defect area code to null if the Loan Summary view is displayed
        (result) => {
            if (result.currentValue) {
                _self.currentDefectAreaCode = null;
                _self.loanSummarySelected = false;
            }
        })
    reviewWrapUp: boolean = false;

    constructor() {
        _self = this;
    }
}
