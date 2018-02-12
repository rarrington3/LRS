// This file was generated from the component scaffold
// Copyright 2016

import {ActivatedRoute} from '@angular/router';
import {Component} from '@angular/core';
import template from './FindingsSideBar.html';
import styles from './FindingsSideBar.less';
import ReviewService from '../services/ReviewService';
import FindingsService from '../services/FindingsService';
import GlobalStateSvc from '../services/GlobalStateSvc';
import DictionarySvc from '../../app/services/DictionarySvc';
import _ from 'lodash';
import FindingUtils  from '../FindingUtils';
import Utils from '../../shared/Utils';
@Component({
    selector: 'findings-side-bar',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/api/core/Component-decorator.html
 * @example
 * <findings-side-bar name="FindingsSideBar"></findings-side-bar>
 */
export default class FindingsSideBar {
    subscriptions:Array = [];
    reviewService:ReviewService;

    findingsService:FindingsService;
    globalStateSvc:GlobalStateSvc;
    findingGroups:Array = [];
    _findings:Array = null;
    totalFindings:Number = 0;
    _allRatingCodes:Array;
    route:ActivatedRoute;

    // All defect areas for the current review.
    _defectAreas: Array = null;

    previousFindingGroups: Object = {};
    isLender: Boolean = false;
    wrapupTitle: string = 'Review Level Wrap-Up';

    _currentFindings:Array = null;
    isQC:Boolean = false;

    constructor(reviewService:ReviewService, route:ActivatedRoute, findingsService: FindingsService, globalStateSvc:GlobalStateSvc,  dictionaryService: DictionarySvc) {
        this.route = route;
        this.reviewService = reviewService;
        this.dictionaryService = dictionaryService;
        this.findingsService = findingsService;
        this.globalStateSvc = globalStateSvc;
        this.isLender = false;
    }

    ngOnInit() {
        this._resetSubscriptions();

        this.subscriptions.push(this.findingsService.currentFindingsObservable.subscribe((data) => {
            if (data.currentValue) {
                this.refreshCurrentFindings(data.currentValue);
            }
        }));


        this.dictionaryService.getFindingRatingCodes().subscribe( codes => {
            this._allRatingCodes = codes;
            this._createFindingGroups();
        });

        this.subscriptions.push(this.globalStateSvc.defectAreasForCurrentReviewObservable.subscribe((change) => {
            if (change.currentValue) {
                this._defectAreas = change.currentValue;
                let review = this.getCurrentReview();
                let reviewLevel = this.getCurrentReviewLevel(review);
                if (Utils.isReviewVetted(review)) {
                    this.wrapupTitle = 'Vetting Review';
                }

                if (review && reviewLevel && reviewLevel.type.toLowerCase() !== 'initial') {
                    this.loadAllCompletedFindings();
                } else {
                    this._createFindingGroups();
                }
            }
        }));
        this._createFindingGroups();
    }

    ngOnDestroy() {
        this._resetSubscriptions();
    }

    _resetSubscriptions() {
        this.subscriptions.forEach(subscription => {
            subscription.unsubscribe();
        });

        this.subscriptions = [];
    }

    getCurrentReview() {
        return this.reviewService.currentReview;
    }

    getCurrentReviewLevel(review: Object = null) {
        return (review) ? review.currentReviewLevel : null;
    }

    _createFindingGroups() {
        let review = this.getCurrentReview();
        let reviewLevel =  this.getCurrentReviewLevel(review);
        let responseAction;

        if (this._allRatingCodes && this._currentFindings && review && reviewLevel && this._defectAreas) {
            this._findings = this._currentFindings.filter(f => f.reviewLevelId === reviewLevel.reviewLevelId);
            this.totalFindings = this._findings.length;

            // Inject the mitigated action in each finding to speed up binding.
            if (review && reviewLevel.type.toLowerCase() !== 'initial' && this.previousFindingGroups) {
                this._findings.forEach( f => {
                    responseAction = FindingUtils.getReviewerFindingResponseAction(f, this.previousFindingGroups);
                    f.mitigatedAction =  responseAction && responseAction.description || null;
                    f.isNewAdhoc = (f.isAdhoc === true && f.reviewLevelId === f.originalReviewLevelId);
                });
            }

            // Group findings by defect area.
            let groups = _.groupBy(this._findings, f => f.defectAreaCode);

            // Convert the groups to an Array.
            this.findingGroups = _.keys(groups).map(groupKey => {


                return this._calculateGroupStatus({name: groupKey, findings: groups[groupKey]});
            }).sort((a, b) => {
                return parseInt(a.order, 10) < parseInt(b.order, 10) ? -1 : 1;
            });
        }
    }

    _calculateGroupStatus(group:Object) {

        // Sort finding by rank order.
        group.findings = group.findings.sort((a, b) => {
            return parseInt(a.rating.rankOrder, 10) < parseInt(b.rating.rankOrder, 10) ? -1 : 1;
        });

        group.headerId = `heading${group.name}`;

        // Set the group status by using the finding with the lowest rating rank.
        let groupRating = group.findings[0].rating || {};

        group.rating = groupRating;
        group.status = groupRating.description;
        group.rank = groupRating.rankOrder;
        let foundDefectArea = this._defectAreas.find(d => d.defectAreaCode === group.name);
        group.order = (foundDefectArea) ? foundDefectArea.order : (this._defectAreas.length - 1);
        return group;
    }

    getURL(finding:Object) {
        let currentReview =  this.reviewService.currentReview;

        // If the current review level type is not 'initial' route the user to the /noninitialdetails
        let detailsRoute = (FindingUtils.isInitialFinding(finding, currentReview) || FindingUtils.isInitialAdhocFinding(finding, currentReview)) ? 'initialdetails' : 'noninitialdetails';
        return `tree/${finding.defectAreaCode}/finding/${finding.findingId}/${detailsRoute}`;
    }

    trackGroup(group: Object) {
        return group.name &&  group.rating &&  group.status;
    }

    trackFinding(finding: Object) {
        if (finding.rating) {
            return finding.rating.rankOrder && finding.mitigatedAction;
        }
        return finding.mitigatedAction;
    }

    showWrapUp(){
        this.reviewService.displayWrapUp();
        this.globalStateSvc.reviewWrapUp = true;
    }

    refreshCurrentFindings(findings: Array){

        if (findings) {
            this._currentFindings = findings;
            this._createFindingGroups();
        }

    }

    loadAllCompletedFindings() {

        // Load previous findings so we can set the 'Adjusted' status in a finding.
        let review = this.getCurrentReview();
        if (review) {
            this.findingsService.loadFindingsForAllCompletedLevels(review, null, false, this.isLender).subscribe((findings) => {
                this.previousFindingGroups = FindingUtils.getFindingGroupsFromPreviousLevel(review, findings);
                this._createFindingGroups();
            });
        }
    }

    getGroupId(group:Object) {
        return group.name;
    }

    getGroupHeaderId(group:Object) {
        return group.headerId;
    }

    getAccordionId() {
        return 'accordion';
    }

}
