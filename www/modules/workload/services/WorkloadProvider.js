// This file was generated from the service scaffold
// Copyright 2017

import {Injectable} from '@angular/core';
import Client from '../../api/lrs.api.v1.generated';
import {UserSvc} from '../../app/services';
import {HqAdminGuard} from '../../app/services/AuthGuards';
import ObservableProperty from '../../shared/decorators/observableProperty';
import {Observable} from 'rxjs';
import { RATINGS } from '../../review/constants';
import _ from 'lodash';

@Injectable()
export default class WorkloadProvider {

    @ObservableProperty()
    completedReviews = null;

    @ObservableProperty()
    myReviews = null;

    @ObservableProperty()
    teamReviews = null;

    activeReviewers: Array = null;

    _client: Client;
    _hqAdminGuard: HqAdminGuard;

    @ObservableProperty()
    completedReviewsByLocation = null;

    @ObservableProperty()
    selectedLocation  = null;

    selectedDateRange: Object = null;

    selectedActiveReviewer: String = 'all';
    filteredActiveReviewers: Array = [];
    /**
     * Service for displaying modals
     * @type {UserSvc}
     * */
    _userSvc: UserSvc;

    constructor(client: Client, hqAdminGuard: HqAdminGuard, userSvc: UserSvc) {
        this._client = client;
        this._hqAdminGuard = hqAdminGuard;
        this._userSvc = userSvc;
        this.locationId = this._userSvc.locationId;
    }

    _addRating(reviews: Array = []) {
        reviews.forEach((review) => {
            switch (review.currentReviewLevel.ratingCode) {
                case RATINGS.CONFORMING.code:
                    review.rating = RATINGS.CONFORMING.description;
                    break;
                case RATINGS.UNACCEPTABLE.code:
                    review.rating = RATINGS.UNACCEPTABLE.description;
                    break;
                case RATINGS.MITIGATED.code:
                    review.rating = RATINGS.MITIGATED.description;
                    break;
                case RATINGS.DEFICIENT.code:
                    review.rating = RATINGS.DEFICIENT.description;
                    break;
                case RATINGS.REMEDIATED.code:
                    review.rating = RATINGS.REMEDIATED.description;
                    break;
                default:
                    break;
            }
        });
    }

    getCompletedReviews() {
        return this._client.resources.reviews.my.completed.get().share().first().switchMap((reviews) => {
            this._addRating(reviews);

            this.completedReviews = reviews;
            return Observable.of(reviews);
        });
    }

    getMyReviews() {
        return this._client.resources.reviews.my.get().share().first().switchMap((reviews) => {
            this.myReviews = reviews;
            return Observable.of(reviews);
        });
    }


    getTeamReviews() {

        return Observable.create(observer => {
            Observable.forkJoin(
                (this._hqAdminGuard.canActivate() ?
                        this._client.resources.reviews : this._client.resources.reviews.team
                ).get().share().first(),
                this._client.resources.reviewers.active.get().first()
            ).subscribe(([reviews, reviewers]) => {

                reviews.forEach( (review) => {
                    let reviewer = reviewers.find( r => r.reviewerId === review.currentReviewLevel.reviewerId );
                    if (reviewer) {
                        let supervisor = reviewers.find( r => r.reviewerId === reviewer.reportsTo );
                        review._supervisorName = supervisor ? (supervisor.nameFirst + ' ' + supervisor.nameLast) : '(N/A)';
                    }
                });

                this.activeReviewers = reviewers;
                this.teamReviews = reviews;
                observer.next(reviews);
                observer.complete();
            });
        });
    }

    getLocations() {
        return this._client.resources.locations.get().share().first();
    }

    getActiveReviewers() {
        return  this._client.resources.reviewers.active.get().share().first();
    }

    getQualifiedReviewers(reviewId: String) {
        return  this._hqAdminGuard.canActivate() ?
            this._client.resources.reviewers.qualifiedToReview.reviewId(reviewId).get().first() :
            this._client.resources.reviewers.qualifiedToReview.reviewId(reviewId).myLocation.get().first();
    }

    cancelReview(review: Object, reasonCode: String) {
        return this._client.resources.reviews.reviewId(review.reviewId).level.reviewLevelId(review.currentReviewLevel.reviewLevelId).cancel.put({ reasonCode: reasonCode }).first();
    }

    reassignReview(review: Object, reviewerId: String, reasonCode: String) {
        return this._client.resources.reviews.reviewId(review.reviewId).level.reviewLevelId(review.currentReviewLevel.reviewLevelId).reassign.put({ selectionId: reviewerId, reasonCode: reasonCode }).first();
    }

    reassignReviews(reviews: Array, reasonCode: String) {
        let requests = reviews.map(review => this._client.resources.reviews.reviewId(review.reviewId).level.reviewLevelId(review.currentReviewLevel.reviewLevelId).reassign.put({ reasonCode: reasonCode }));
        return Observable.forkJoin(requests).first();
    }

    getCompletedReviewsByLocation(locationId: String) {
        let request = this.selectedDateRange || {};
        return this._client.resources.reviews.my.completed.location.locationId(locationId).get(request).share().first().switchMap((reviews) => {
            this._addRating(reviews);

            // IE fix.
            this.completedReviewsByLocation = null;
            _.defer(() => {
                this.completedReviewsByLocation = reviews;
            });

            return Observable.of(reviews);
        });
    }

    getAllReviewers() {
        return  this._client.resources.reviewers.get().first();
    }
}
