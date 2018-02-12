// This file was generated from the service scaffold
// Copyright 2017

import { Injectable } from '@angular/core';
import Client from '../../api/lrs.api.v1.generated';
import ObservableProperty from '../../shared/decorators/observableProperty';
import { Observable } from 'rxjs';
import FindingsService from './FindingsService';
import { REVIEW_LEVEL_TYPE } from '../../shared/constants';
import Utils from '../../shared/Utils';

/**
 * @example
 * let injector = Injector.resolveAndCreate([QCService]);
 * let qCService = new injector.get(QCService);
 * @example
 * class Component {
 * 		constructor(qCService:QCService, qCService2:QCService) {
 *			//injected via angular, a singleton by default in same injector context
 *			console.log(qCService === qCService2);
 *		}
 * }
 */
@Injectable()
export default class QCService {
    _client: Client;

    _findingsService: FindingsService;

    @ObservableProperty()
    currentQCreview = null;

    // Used in review wrap up and defect area summary views.
    @ObservableProperty()
    originalInitialFindings: Array = null;

    // Used in the original findings tab view.
    @ObservableProperty()
    originalCurrentFindings = null;

    // Used in the  non inital finding details and original findings tab views.
    @ObservableProperty()
    originalCompletedFindings = null;

    constructor(client: Client, findingsService: FindingsService) {
        this._client = client;
        this._findingsService = findingsService;
    }

    getOriginalReview(reviewId: String) {
        if (this.currentQCreview) {
            return Observable.of(this.currentQCreview);
        }
        let reviewObservable = this._client.resources.reviews.reviewId(reviewId).get().share().first();
        reviewObservable.subscribe((review) => {
            this.currentQCreview = review;
        });
        return reviewObservable;
    }

    findInitialLevel(review) {
        if (review.currentReviewLevel.type.toLowerCase() === REVIEW_LEVEL_TYPE.INITIAL ) {
            return review.currentReviewLevel;
        }

        return review.completedReviewLevels.find(l => l.type.toLowerCase() === REVIEW_LEVEL_TYPE.INITIAL);
    }

    getAllOriginalFindingsInCompletedLevels(review) {
        if (this.originalCompletedFindings) {
            return Observable.of(this.originalCompletedFindings);
        }

        let completeLevels = Utils.filterCompletedLevels(review);
        completeLevels = [];
        if (!completeLevels.length) {
            return Observable.of([]);
        }

        let $requests = Observable.concat(...completeLevels.map(l => this._getFindings(review.reviewId, l.reviewLevelId, true)));
        $requests.subscribe((findings => {
            this.originalCompletedFindings = findings;
        }));

        return $requests;
    }

    getOriginalInitialFindings(review: Object) {
        if (this.originalInitialFindings) {
            return Observable.of(this.originalInitialFindings);
        }

        // Find initial findings
        let reviewLevelId = this.findInitialLevel(review).reviewLevelId;

        let request = this._getFindings(review.reviewId, reviewLevelId, true);
        request.subscribe(findings => {
            this.originalInitialFindings = findings;
        });

        return request;
    }

    getOriginalFindingsInCurrentLevel(review: Object) {
        if (this.originalCurrentFindings) {
            return Observable.of(this.originalCurrentFindings);
        }

        let request = this._getFindings(review.reviewId, review.currentReviewLevel.reviewLevelId, true);
        request.subscribe(findings => {
            this.originalCurrentFindings = findings;
        });

        return request;
    }

    _getFindings(reviewId:String, reviewLevelId:String, addRating:Boolean = false) {
        return  this._client.resources.reviews.reviewId(reviewId).level.reviewLevelId(reviewLevelId).findings.get().share().first().switchMap( findings => {
            if (addRating) {
                this._addRatings(findings);
            }

            return Observable.of(findings);
        });
    }

    _addRatings(findings: Array) {
        if (findings &&  this._findingsService._ratingCodesDictionary) {
            findings.forEach((finding) => {

                /**
                 * Inject the Rating object to each finding because because this is required by the
                 * FindingUtils.buildFindingsTotals which we'll call to compute additional data.
                 */
                this._findingsService._ratingCodesDictionary.find((rating) => {
                    if (finding.ratingCode === rating.ratingCode) {
                        finding.rating = rating;
                    }
                });
            });
        }

    }

}
