// This file was generated from the component scaffold
// Copyright 2016

import { Component } from '@angular/core';
import template from './DefectAreaWrapUp.html';
import styles from './DefectAreaWrapUp.less';
import FindingsService from '../services/FindingsService';
import GlobalStateSvc from '../services/GlobalStateSvc';
import ReviewService from '../services/ReviewService';
import { Router } from '@angular/router';
import FindingUtils from '../FindingUtils';
import Utils from '../../shared/Utils';
import VettingService from '../services/VettingService';
import DictionarySvc from '../../app/services/DictionarySvc';
import {Observable} from 'rxjs';
import {RATINGS} from '../constants';
import QCService from '../services/QCService';

@Component({
    selector: 'defect-area-wrap-up',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/api/core/Component-decorator.html
 * @example
 * <defect-area-wrap-up name="DefectAreaWrapUp" (change)="onChange($event)"></defect-area-wrap-up>
 */
export default class DefectAreaWrapUp {
    _subscriptions: Array = [];
    displayedFindings: Array = [];
    displayedUnchangedFindings: Array = [];
    defectAreas: Array = [];
    _router: Router;
    _vettingService: VettingService;
    _defectAreaCauses: Array;
    _defectAreaSources: Array;
    _allRatings: Array;
    _dictionaryService: DictionarySvc;
    isVetted: Boolean = false;
    currentReviewLevelRating:Object = null;
    originalReviewLevelRating:Object = null;
    _qcService: QCService = null;
    isQC:Boolean = false;
    isVettedOrQC: Boolean = false;


    /**
     * The _originalLevelFindings are the reviewer's original changes if the review is vetted.
     * This object is only useed when the review is vetted to compare with the vetting finding changes.
     */
    _originalFindings:Array = null;

    constructor(findingSvc: FindingsService, globalStateSvc: GlobalStateSvc, reviewService: ReviewService, router: Router, vettingService: VettingService, dictionaryService: DictionarySvc, qcService: QCService) {
        this._findingSvc = findingSvc;
        this._globalStateSvc = globalStateSvc;
        this._reviewService = reviewService;
        this._router = router;
        this._vettingService = vettingService;
        this._dictionaryService = dictionaryService;
        this._qcService = qcService;
    }

    ngOnInit() {

        this._resetSubscriptions();
        this._subscriptions.push(this._findingSvc.findingsForCurrentDefectAreaObservable.subscribe(() => {
            this.findingsLoaded();
        }));

        this.findingsLoaded();
    }

    cancelDefect() {
        this._reviewService.hideWrapUp();
    }

    findingsLoaded() {
        if (this._findingSvc.findingsForCurrentDefectArea) {
            this.originalReviewLevelRating = null;
            this.currentReviewLevelRating = null;
            // Load more data dependencies.
            this._subscriptions.push(
                Observable.forkJoin(
                    this._dictionaryService.getDefectAreaSources(this._globalStateSvc.currentQaModelId, this._globalStateSvc.currentDefectAreaCode),
                    this._dictionaryService.getDefectAreaCauses(this._globalStateSvc.currentQaModelId, this._globalStateSvc.currentDefectAreaCode),
                    this._dictionaryService.getFindingRatingCodes()
                ).first().subscribe(([sources, causes, ratings]) => {
                    this._defectAreaCauses = causes;
                    this._defectAreaSources = sources;
                    this._allRatings = ratings;
                    this.buildDisplayedFindings();
                }));
        }
    }

    _updateCurrentReviewRating() {
        if (this._allRatings  && this.displayedFindings && this.displayedFindings.length) {
            this.currentReviewLevelRating =  this.displayedFindings[0].rating;
        } else {
            this.currentReviewLevelRating = RATINGS.CONFORMING;
        }
    }

    buildDisplayedFindings() {

        if (this._findingSvc.findingsForCurrentDefectArea &&  this._allRatings) {
            this.isVetted = Utils.isReviewVetted(this._reviewService.currentReview);
            this.isQC = Utils.isQCmitigatedReview(this._reviewService.currentReview);
            this.isVettedOrQC = this.isVetted || this.isQC;

            if (!this.isVetted && !this.isQC) {
                this.displayedFindings = this._findingSvc.findingsForCurrentDefectArea;
                this._updateCurrentReviewRating();
            } else if (this.isVetted) {

                this._findingSvc.loadFindingsForAllCompletedLevels(this._reviewService.currentReview).subscribe((previousFindings) => {
                    let _previousFindingGroups = FindingUtils.getFindingGroupsFromPreviousLevel(this._reviewService.currentReview, previousFindings, true);
                    let originalFindings = _previousFindingGroups[this._globalStateSvc.currentDefectAreaCode] || [];

                    // Sort the original/vettee findings by rating rank order.
                    originalFindings.forEach(f => {
                        f._rating = this._allRatings.find(r => r.ratingCode === f.ratingCode);
                    });

                    this._sortFindingsByRankOrder(originalFindings);

                    let vettedFindings =  this._findingSvc.findingsForCurrentDefectArea;

                    // Set vettee and vetter's finding rating status.
                    this.currentReviewLevelRating = (vettedFindings && vettedFindings.length) ?  vettedFindings[0].rating : RATINGS.CONFORMING;
                    this.originalReviewLevelRating = (originalFindings && originalFindings.length) ?  originalFindings[0]._rating : RATINGS.CONFORMING;

                    this.parseCurrentAndOriginalFindings(originalFindings, vettedFindings);
                });
            } else {
                this._qcService.getOriginalReview(this._reviewService.currentReview.originalQcReviewId).subscribe((review) => {

                    this._qcService.getOriginalInitialFindings(review).subscribe( findings => {
                        if (findings) {
                            let qcInitialFindings = findings.filter(f => f.defectAreaCode === this._globalStateSvc.currentDefectAreaCode);
                            let currentFindings =  this._findingSvc.findingsForCurrentDefectArea;

                            this._sortFindingsByRankOrder(qcInitialFindings);
                            this._sortFindingsByRankOrder(currentFindings);

                            // Set QC original and current finding rating status.
                            this.currentReviewLevelRating = (currentFindings && currentFindings.length) ?  currentFindings[0].rating : RATINGS.CONFORMING;
                            this.originalReviewLevelRating = (qcInitialFindings && qcInitialFindings.length) ?  qcInitialFindings[0].rating : RATINGS.CONFORMING;

                            this.parseCurrentAndOriginalFindings(qcInitialFindings, currentFindings);
                        }
                    });

                });
            }
        }
    }

    parseCurrentAndOriginalFindings(originalFindings: Array, vettedFindings: Array) {
        let changes = VettingService.calculateVettingChanges(originalFindings, vettedFindings, this._defectAreaSources,  this._defectAreaCauses, this.isQC) || {};
        this.displayedFindings = changes.changedFindings;
        this.displayedUnchangedFindings = changes.unchangedFindings;

    }

    navigateToFinding(finding) {
        FindingUtils.navigateToFindingDetail(this._router, this._globalStateSvc, this._reviewService, finding);
    }

    _resetSubscriptions() {
        this._subscriptions.forEach((subscription) => {
            if (subscription.unsubscribe) {
                subscription.unsubscribe();
            }
        });

        this._subscriptions = [];
    }

    ngOnDestroy() {
        this._resetSubscriptions();
    }

    _sortFindingsByRankOrder(findings: Array = []) {
        findings.sort(function(a, b) {
            let aRating = a._rating || a.rating;
            let bRating = b._rating || b.rating;
            if (!aRating || !bRating) {
                return 0;
            }
            return (aRating.rankOrder) - (bRating.rankOrder);
        });

    }
}
