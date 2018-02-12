// This file was generated from the component scaffold
// Copyright 2017

import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import template from './ReviewsCompletedTeamSidebar.html';
import styles from './ReviewsCompletedTeamSidebar.less';
import _ from 'lodash';
import CrossFilterBaseView from '../../shared/components/CrossFilterBaseView';
import WorkloadProvider from '../services/WorkloadProvider';
import { HqAdminGuard } from '../../app/services/AuthGuards';
import { UserSvc } from '../../app/services';
import Client from '../../api/lrs.api.v1.generated';
import CrossFilterService from '../../app/services/CrossFilterService';
import moment from 'moment';

@Component({
    selector: 'reviews-completed-team-sidebar',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/api/core/Component-decorator.html
 * @example
 * <reviews-completed-team-sidebar name="ReviewsCompletedTeamSidebar" (change)="onChange($event)"></reviews-completed-team-sidebar>
 */
export default class ReviewsCompletedTeamSidebar {
    subscriptions: Array = [];
    teamReviewDimensions: Array = [];
    workloadProvider: WorkloadProvider;
    hqAdminGuard: HqAdminGuard;
    userSvc: UserSvc;
    client: Client;
    _currentUserLocation: String = '';
    locations: Array = null;
    reviewers: Array = null;
    isHQAdmin: Boolean = false;
    crossFilterService: CrossFilterService;
    dateRangeModel = { startDate: null, endDate: null };
    startIsOpened: Boolean = false;
    DAY_RANGE = 90;
    REQUEST_DATE_FORMAT = 'YYYY-MM-DD';

    constructor(workloadProvider: WorkloadProvider, hqAdminGuard: HqAdminGuard, userSvc: UserSvc, client: Client, crossFilterService: CrossFilterService) {
        this.workloadProvider = workloadProvider;
        this.hqAdminGuard = hqAdminGuard;
        this.userSvc = userSvc;
        this.client = client;
        this.crossFilterService = crossFilterService;
        this._initDefaultDates();
    }

    ngOnInit() {

        this.isHQAdmin = this.hqAdminGuard.canActivate();
        Observable.forkJoin(
            this.client.resources.locations.get().first(),
            this.client.resources.reviewers.get().first()
        ).subscribe(([locations, reviewers]) => {

            this.reviewers = reviewers;

            // set default location for reviewer
            this.locations = locations;
            let userLocation = locations.find(loc => loc.locationId === this.userSvc.locationId);
            this.currentUserLocation = userLocation.locationId;

            this.initCrossFilters();
        });

    }

    set currentUserLocation(location: String) {
        this._currentUserLocation = location;
        if (location) {
            _.defer(() => {
                this.workloadProvider.selectedLocation = location;
            });

        }
    }

    get currentUserLocation() {
        return this._currentUserLocation;
    }

    initCrossFilters() {
        let buttonType = CrossFilterBaseView.CONTROL_TYPE.BUTTON;
        let dropDownType = CrossFilterBaseView.CONTROL_TYPE.DROP_DOWN;

        this.teamReviewDimensions = [
            {
                title: 'Case Number Search',
                controlType: 'text',
                dimension: (review) => (review.caseNumber ? review.caseNumber.replace('-', '') : '')
            },
            {
                title: 'FINAL RATING',
                controlType: 'button',
                dimension: (review) => review.rating,
                sort: CrossFilterBaseView.COMMON_DIMENSION_SORTS.RATING
            },
            {
                title: 'REVIEW TYPE',
                controlType: buttonType,
                dimension: (review) => review.reviewType || 'N/A'
            },
            {
                title: 'FINAL REVIEW LEVEL',
                controlType: buttonType,
                dimension: CrossFilterBaseView.DIMENSION_METHODS.FINAL_REVIEW_LEVEL,
                sort: CrossFilterBaseView.COMMON_DIMENSION_SORTS.REVIEW_LEVEL
            },
            {
                title: 'Selection Reason',
                controlType: buttonType,
                dimension: (review) => review.selectionReason
            },
            {
                title: 'INITIAL REVIEWER',
                controlType: dropDownType,
                hideEmptyGroups: true,
                dimension: (review) => {
                    let initialLevel = review.completedReviewLevels[0];
                    let reviewer = this.reviewers.find(r => r.reviewerId === initialLevel.reviewerId);
                    initialLevel._modifiedReviewerName = initialLevel.reviewerName + this._getReviewerStatus(reviewer);
                    return initialLevel._modifiedReviewerName;
                }
            },
            {
                title: 'MANAGER OF INITIAL REVIEWER',
                controlType: dropDownType,
                hideEmptyGroups: true,
                dimension: (review) => {
                    let reviewer = this.reviewers.find(r => r.reviewerId === review.completedReviewLevels[0].reviewerId);
                    if (reviewer) {
                        let supervisor = this.reviewers.find(r => r.reviewerId === reviewer.reportsTo);
                        return supervisor ? (supervisor.nameFirst + ' ' + supervisor.nameLast) : '(N/A)';
                    }

                    return '(N/A)';
                }
            },
            {
                title: 'FINAL REVIEWER',
                controlType: dropDownType,
                hideEmptyGroups: true,
                dimension: (review) => {
                    let currentLevel = review.currentReviewLevel;
                    let reviewer = this.reviewers.find(r => r.reviewerId === currentLevel.reviewerId);
                    if (reviewer) {
                        currentLevel._modifiedReviewerName = currentLevel.reviewerName + this._getReviewerStatus(reviewer);
                        return currentLevel._modifiedReviewerName;
                    }

                    return '(N/A)';
                }
            },
            {
                title: 'MANAGER OF FINAL REVIEWER',
                controlType: dropDownType,
                hideEmptyGroups: true,
                dimension: (review) => {
                    let reviewer = this.reviewers.find(r => r.reviewerId === review.currentReviewLevel.reviewerId);
                    if (reviewer) {
                        let supervisor = this.reviewers.find(r => r.reviewerId === reviewer.reportsTo);
                        return supervisor ? (supervisor.nameFirst + ' ' + supervisor.nameLast) : '(N/A)';
                    }

                    return '(N/A)';
                }
            }
        ];
    }

    _getReviewerStatus(reviewer: Object) {
        return (reviewer && reviewer.statusCode && reviewer.statusCode.toLowerCase() === 'i') ? ' (Deactivated)' : '';
    }

    _initDefaultDates() {
        this.dateRangeModel.startDate = moment().utc().subtract(this.DAY_RANGE, 'days');
        this.onStartDateChange(this.dateRangeModel.startDate, false);
    }

    onStartDateChange(startDate: String, refreshData: Boolean = true) {
        this.dateRangeModel.endDate = moment(startDate).add(this.DAY_RANGE, 'days');
        this.workloadProvider.selectedDateRange = {
            startDate: moment(startDate).format(this.REQUEST_DATE_FORMAT),
            endDate: moment(this.dateRangeModel.endDate).format(this.REQUEST_DATE_FORMAT)
        };

        if (refreshData) {

            // Reset the selected location to trigger the completed reviews service call.
            this.workloadProvider.selectedLocation = this._currentUserLocation;
        }
    }

    ngOnDestroy() {
        this.subscriptions.forEach((subscription) => {
            if (subscription.unsubscribe) {
                subscription.unsubscribe();
            }
        });
    }
}
