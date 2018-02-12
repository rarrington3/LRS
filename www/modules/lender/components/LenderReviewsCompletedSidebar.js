// This file was generated from the component scaffold
// Copyright 2017

import {Component} from '@angular/core';
import template from './LenderReviewsCompletedSidebar.html';
import styles from './LenderReviewsCompletedSidebar.less';
import CrossFilterBaseView from '../../shared/components/CrossFilterBaseView';
import LenderService from '../services/LenderService';

@Component({
    selector: 'lender-reviews-completed-sidebar',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/api/core/Component-decorator.html
 * @example
 * <lender-reviews-completed-sidebar name="LenderReviewsCompletedSidebar" (change)="onChange($event)"></lender-reviews-completed-sidebar>
 */
export default class LenderReviewsCompletedSidebar {
    lenderSvc: LenderService;
    filterDimensions: Array = null;
    constructor(lenderService: LenderService) {
        this.lenderSvc = lenderService;
    }

    ngOnInit() {
        this.initFilters();
    }

    initFilters() {
        this.filterDimensions = [
            {
                title: 'Case Number Search',
                controlType: 'text',
                dimension: (review) => (review.caseNumber ? review.caseNumber.replace('-', '') : '')
            }, {
                title: 'Final Rating',
                controlType: 'button',
                dimension: (review) => review.rating,
                sort: CrossFilterBaseView.COMMON_DIMENSION_SORTS.RATING
            }, {
                title: 'Final Review Level',
                controlType: 'button',
                dimension: CrossFilterBaseView.DIMENSION_METHODS.FINAL_REVIEW_LEVEL,
                sort: CrossFilterBaseView.COMMON_DIMENSION_SORTS.REVIEW_LEVEL
            }, {
                title: 'Selection Reason',
                controlType: 'button',
                dimension: (review) => review.selectionReason
            }
        ];
    }

}
