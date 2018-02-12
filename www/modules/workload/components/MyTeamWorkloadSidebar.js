// This file was generated from the component scaffold
// Copyright 2017
import { ActivatedRoute, Router } from '@angular/router';
import {Component} from '@angular/core';
import template from './MyTeamWorkloadSidebar.html';
import styles from './MyTeamWorkloadSidebar.less';
import _ from 'lodash';
import CrossFilterBaseView from '../../shared/components/CrossFilterBaseView';
import WorkloadProvider from '../services/WorkloadProvider';
import {HqAdminGuard } from '../../app/services/AuthGuards';
import {UserSvc} from '../../app/services';
import Client from '../../api/lrs.api.v1.generated';
import CrossFilterService from '../../app/services/CrossFilterService';
import ObservableProperty from '../../shared/decorators/observableProperty';

@Component({
    selector: 'my-team-workload-sidebar',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/api/core/Component-decorator.html
 * @example
 * <reviews-completed-team-sidebar name="ReviewsCompletedTeamSidebar" (change)="onChange($event)"></reviews-completed-team-sidebar>
 */
export default class MyTeamWorkloadSidebar {
    _subscriptions: Array = [];
    workloadProvider: WorkloadProvider;
    hqAdminGuard: HqAdminGuard;
    _userSvc: UserSvc;
    _client: Client;
    isHQAdmin: Boolean = false;

    allLocations: Array = null;
    activeReviewers: Array = [];
    _selectedLocation: String = null;
    teamTabDimensions: Array = [];
    filteredTeamReviews: Array = [];
    _route: ActivatedRoute;
    _router: Router;
    _userQueryId: String = null;
    @ObservableProperty()
    filteredReviews = null;

    constructor(workloadProvider: WorkloadProvider,  hqAdminGuard:  HqAdminGuard, userSvc: UserSvc, client: Client, crossFilterService: CrossFilterService, route: ActivatedRoute, router: Router) {
        this.workloadProvider = workloadProvider;
        this.hqAdminGuard =  hqAdminGuard;
        this._userSvc = userSvc;
        this._client = client;
        this.crossFilterService = crossFilterService;
        this._route = route;
        this._router = router;
    }

    ngOnInit() {

        this.isHQAdmin = this.hqAdminGuard.canActivate();
        this._subscriptions = [];
        this.VIEW_ID = 'team';
        this._subscriptions.push( this.workloadProvider.teamReviewsObservable.subscribe(data => {
            if (data.currentValue) {
                let _activeReviewers = this.workloadProvider.activeReviewers.map(r => {
                    r.name = `${r.nameFirst} ${r.nameLast}`;
                    r._reviews = [];
                    return r;
                });

                this.workloadProvider.activeReviewers = _activeReviewers;
                this.activeReviewers = _activeReviewers;
                this.teamReviews = data.currentValue;
                this._client.resources.locations.get().first().subscribe(locations => {
                    this.allLocations = locations;
                    this.initCrossFilters();
                });

            }
        }));

        this._subscriptions.push(this._route.queryParams.subscribe(params => {
            if (this._router.url.indexOf(this.VIEW_ID) > -1) {
                this.userQuery = params['user'];
                this.userQueryLocationId = params['location'];
                this.initCrossFilters();
            } else {
                this._clearFilters();
            }

        }));

    }

    set selectedLocation(location: String) {
        this._selectedLocation = null;

        _.defer(() => {
            this._selectedLocation = location;
            if (location) {
                this.workloadProvider.filteredActiveReviewers = this.activeReviewers.filter(r => (r.locationId === location));
                this.generateDataSource();

                if (this.userQuery) {
                    this.userQuery = null;
                }
            }
        });
    }

    get selectedLocation() {
        return this._selectedLocation;
    }

    _clearFilters() {
        this.allLocations = null;
        this.activeReviewers = [];
        this._userQueryId = null;
        this.userQuery = null;
        this.workloadProvider.filteredActiveReviewers = [];
        this.filteredReviews = null;
    }

    generateDataSource() {

        let filteredReviews;
        let activeReviewers = this.workloadProvider.filteredActiveReviewers;
        filteredReviews = this.teamReviews.filter(r => (r.currentReviewLevel.reviewLocationId === this.selectedLocation));

        let reviewersWithoutReviews = activeReviewers.filter(r => !this.teamReviews.find(tr => tr.currentReviewLevel.reviewerId === r.reviewerId));

        // Add any reviewer without reviews wrapped by an empty review to the cross filter review data source in order to account for all active reviewers in the cross filter 'Reviewer' dimension.
        reviewersWithoutReviews = reviewersWithoutReviews.map( r => {
            return {reviewId: null, currentReviewLevel: {type: 'N/A', reviewerDateDue: null, status: 'N/A', reviewerId: r.reviewerId, reviewerName: r.name}};
        });

        // Reset the cross filter data source.
        if (reviewersWithoutReviews && reviewersWithoutReviews.length) {
            filteredReviews.push(...reviewersWithoutReviews);
        }

        this.filteredReviews = filteredReviews;
    }

    initCrossFilters() {

        if (this.allLocations  && this.allLocations.length && this.teamReviews) {
            let userQueryName = null;
            if (this.userQuery) {
                this.selectedLocation = this.userQueryLocationId|| 'all';

                userQueryName = this.activeReviewers.find(ar => ar.reviewerId === this.userQuery);
                if (userQueryName) {
                    userQueryName = userQueryName.name;
                }

            }  else {
                // set default location for reviewer
                let userLocation = this.allLocations.find(loc => loc.locationId === this._userSvc.locationId);
                this.selectedLocation = userLocation && userLocation.locationId || 'all';

            }

            let buttonType = CrossFilterBaseView.CONTROL_TYPE.BUTTON;
            let dropdownType = CrossFilterBaseView.CONTROL_TYPE.DROP_DOWN;

            this.teamTabDimensions = [
                {
                    title: 'Case Number Search',
                    ignoredKeys: [null],
                    controlType: CrossFilterBaseView.CONTROL_TYPE.TEXT,
                    dimension: (review) => (review.caseNumber ? review.caseNumber.replace('-', '') : '')
                },

                {
                    title: 'REVIEW DEADLINE',
                    controlType: buttonType,
                    ignoredKeys: [null, 'N/A'],
                    dimension: CrossFilterBaseView.DIMENSION_METHODS.REVIEW_DEADLINES,
                    sort: (group) => CrossFilterBaseView.createSortFunctionFromArray([
                        CrossFilterBaseView.DEADLINE_KEYS.PAST_DUE,
                        CrossFilterBaseView.DEADLINE_KEYS.DUE_TODAY,
                        CrossFilterBaseView.DEADLINE_KEYS.DUE_NEXT_WEEK,
                        CrossFilterBaseView.DEADLINE_KEYS.DUE_LATER])(group),
                    style: (key) => {
                        switch (key) {
                            case CrossFilterBaseView.DEADLINE_KEYS.DUE_TODAY:
                                return 'due-today';
                            case CrossFilterBaseView.DEADLINE_KEYS.PAST_DUE:
                                return 'past-due';
                            default:
                                return null;
                        }
                    }
                },
                {
                    title: 'REVIEWER',
                    controlType: dropdownType,
                    hideEmptyGroups: false,
                    ignoredKeys: [null, 'N/A'],
                    initialDefault: userQueryName || null,
                    dimension: (reviewOrReviewer) =>  reviewOrReviewer.currentReviewLevel.reviewerName || 'N/A'
                },
                {
                    title: 'MANAGER',
                    controlType: dropdownType,
                    ignoredKeys: [null, 'N/A'],
                    dimension: (review) => review._supervisorName || 'N/A'
                },
                {
                    title: 'REVIEW TYPE',
                    controlType: 'button',
                    ignoredKeys: [null, 'N/A'],
                    dimension: (review) => review.reviewType || 'N/A'
                }
            ];
        }
    }

    ngOnDestroy() {
        this._subscriptions.forEach((subscription) => {
            if (subscription.unsubscribe) {
                subscription.unsubscribe();
            }
        });
    }
}
