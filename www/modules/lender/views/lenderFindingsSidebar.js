// This file was generated from the view scaffold
// Copyright 2016

import {Component, ElementRef} from '@angular/core';
import {ActivatedRoute}  from '@angular/router';
import template from './lenderFindingsSidebar.html';
import styles from './lenderFindingsSidebar.less';
import FindingsSideBar from '../../review/components/FindingsSideBar';
import ReviewService from '../../review/services/ReviewService';
import LenderService from '../services/LenderService';
import FindingsService from '../../review/services/FindingsService';
import FindingUtils from '../../review/FindingUtils';
import GlobalStateSvc from '../../review/services/GlobalStateSvc';
import DictionarySvc from '../../app/services/DictionarySvc';
import $ from 'jquery';
import Utils from '../../shared/Utils';

@Component({
    selector: 'lender-findings-sidebar',
    template: template,
    styles: [styles]
})

export default class LenderFindingsSidebar extends FindingsSideBar {
    _lenderService:LenderService;
    _review:Object = {};
    _elementRef:ElementRef;

    lenderAuthorizedLevel: Object = null;
    constructor(reviewService:ReviewService, route:ActivatedRoute, lenderService:LenderService, elementRef: ElementRef, findingsService: FindingsService, globalStateSvc:GlobalStateSvc, dictSvc:DictionarySvc ) {
        super(reviewService, route, findingsService, globalStateSvc, dictSvc);
        this._lenderService = lenderService;
        this._elementRef = elementRef;
    }

    ngOnInit() {
        // Override
        this.isLender = true;

        super.ngOnInit();
        this.subscriptions.push(this.route.params.subscribe(params => {
            let reviewId = params.reviewId;
            this._lenderService.getReview(reviewId).subscribe(review => {
                if (review && review.reviewId) {
                    this._review = review;

                    // Lender is only allowed to see finding data from the assigned level. Previously lender could see findings from a review level under review by FHA.
                    this.lenderAuthorizedLevel = this.getCurrentReviewLevel(review);

                    // Load all findings for the last lender addressed review level and trigger data parsing for findings.
                    if (this.lenderAuthorizedLevel) {
                        this.findingsService.loadAllFindings(reviewId, this.lenderAuthorizedLevel.reviewLevelId, true, true, this._review.qaModelId).subscribe();

                        // The defect areas are used to sort the defect area group by order
                        this._lenderService.getDefectAreasByReview(review).subscribe( defectAreas => {
                            if (defectAreas) {
                                this.globalStateSvc.defectAreasForCurrentReview = defectAreas;
                            }
                        });
                    }

                }
            });
        }));

        this.subscriptions.push(this._lenderService.selectedFindingIdObservable.subscribe( data => {
            if (data.currentValue) {
                this.selectFindingsTab();
            }
        }));
    }

    ngAfterViewChecked() {
        let $element = $(this._elementRef.nativeElement);
        let activeGroupId = $($element.find('a.btn.active').closest('.panel-collapse')).attr('id');
        if (activeGroupId) {
            this.selectFindingsTab();
        }
    }

    ngOnDestroy() {
        super.ngOnDestroy();
    }

    selectFindingsTab() {

        let $element = $(this._elementRef.nativeElement);
        let activeGroupId = $($element.find('a.btn.active').closest('.panel-collapse')).attr('id');
        let $activeHeaderBtn = $element.find(`[data-target="#${activeGroupId}"]`);

        // Expand the finding group that contains the selected link.
        if ($activeHeaderBtn && $activeHeaderBtn.hasClass('collapsed')) {
            $activeHeaderBtn.trigger('click');
        }

        // Switch to the Findings tab
        let $findingsTab = $element.closest('.left-side-bar').find('[data-target="#findings-sidebar"]');
        if (!$findingsTab.attr('aria-selected')) {
            $findingsTab.trigger('click');
        }
    }

    // Override
    getCurrentReview() {
        return this._review;
    }

    // Override
    getCurrentReviewLevel(review: Object = null) {
        return (review && review.reviewId) ? Utils.findLenderAuthorizedLevel(review) : null;
    }

    // Override
    getURL(finding:Object) {
        return `/lender/activeReviews/review/${this._review.reviewId}/finding/${finding.findingId}/reviewResponse`;
    }

    getResponseProgress(finding:Object) {
        return  FindingUtils.getLenderFindingResponseProgress(finding);
    }

    // Override
    _createFindingGroups() {
        super._createFindingGroups();
        if (this.findingGroups && this.findingGroups.length) {

            // Share the sorted finding defect area groups with other views.
            this._lenderService.defectAreaFindingGroups = this.findingGroups;
        }
    }

}
