// This file was generated from the component scaffold
// Copyright 2017
import {Component} from '@angular/core';
import {ActivatedRoute}  from '@angular/router';
import FindingsSideBar from './FindingsSideBar';
import ReviewService from '../services/ReviewService';
import FindingsService from '../services/FindingsService';
import FindingUtils from '../FindingUtils';
import GlobalStateSvc from '../services/GlobalStateSvc';
import DictionarySvc from '../../app/services/DictionarySvc';
import Utils from '../../shared/Utils';
import template from './FindingsSideBar.html';
import styles from './FindingsSideBar.less';
import QCService from '../services/QCService';
import {Observable} from 'rxjs/Observable';

@Component({
    selector: 'qc-findings-side-bar',
    template: template,
    styles: [styles]
})

export default class QCFindingsSideBar extends FindingsSideBar {

    _qcService: QCService = null;
    _originalReview: Object = null;
    constructor(reviewService:ReviewService, route:ActivatedRoute, findingsService: FindingsService, globalStateSvc:GlobalStateSvc, dictSvc:DictionarySvc,  qcService: QCService ) {
        super(reviewService, route, findingsService, globalStateSvc, dictSvc);

        this._qcService = qcService;
    }

    ngOnInit() {
        super.ngOnInit();
        this.isQC = true;

    }

    ngOnDestroy() {
        super.ngOnDestroy();
    }

    // Override
    getCurrentReview() {
        return this._originalReview;
    }

    _loadQCData() {

        if (this.reviewService.currentReview && Utils.isQCreview(this.reviewService.currentReview)) {
            this._qcService.getOriginalReview(this.reviewService.currentReview.originalQcReviewId).subscribe((review) => {
                this._originalReview = review;
                Observable.forkJoin(
                    this._qcService.getOriginalFindingsInCurrentLevel(review),
                    this._qcService.getAllOriginalFindingsInCompletedLevels(review)
                ).first().subscribe(([finalFindings, allFindings]) => {
                    this._currentFindings = finalFindings;
                    this.previousFindingGroups = FindingUtils.getFindingGroupsFromPreviousLevel(review, allFindings);
                    this._createFindingGroups();
                });
            });
        }
    }
    // Override
    refreshCurrentFindings() {
        this._loadQCData();
    }

    // Override
    loadAllCompletedFindings() {
        // Do nothing.
    }

    // Override
    getGroupId(group:Object) {
        return group.name + '_qc';
    }

    // Override
    getGroupHeaderId(group:Object) {
        return group.headerId + '_qc';
    }

    // Override
    getAccordionId() {
        return 'accordion_qc';
    }


}
