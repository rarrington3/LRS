// This file was generated from the service scaffold
// Copyright 2016

import { Injectable } from '@angular/core';
import ObservableProperty from '../../shared/decorators/observableProperty';
import Client from '../../api/lrs.api.v1.generated';
import UserSvc from '../../app/services/UserSvc';
import { Observable } from 'rxjs';
import { RATINGS } from '../../review/constants';

/**
 * @example
 * let injector = Injector.resolveAndCreate([WorkloadService]);
 * let workloadService = new injector.get(WorkloadService);
 * @example
 * class Component {
 *      constructor(workloadService:WorkloadService, workloadService2:WorkloadService) {
 *          //injected via angular, a singleton by default in same injector context
 *          console.log(workloadService === workloadService2);
 *      }
 * }
 */

@Injectable()
export default class LenderService  {
    _client: Client;

    @ObservableProperty()
    allReviews = [];

    activeReviewId: String;

    @ObservableProperty()
    selectedFindingId: String = null;

    @ObservableProperty()
    completedLenderReviews = null;

    loadedReviewObservable: Observable = null;

    defectAreaDictionaryObservable: Observable = null;

    // Parsed and sorted finding groups organized by defect area code.
    defectAreaFindingGroups: Array = null;


    isLender: Boolean = false;

    constructor(client: Client, userSvc: UserSvc) {
        this._client = client;
        this._userSvc = userSvc;
    }

    getReviews() {
        this._client.resources.reviews.my.lender.get().first().subscribe((reviews) => {
            this.allReviews = reviews;

            this.unfilteredActiveReviews = this.allReviews.filter(function (review) { //we need to keep the OG active reviews for filtering by Date
                return review.status !== 'Pending Lender Response';
            });


            /*
             HUDLRS-3689. Temporarily disable the harvey balls and then in post-release we will re-engineer them for better performance. 4/26
             let _findings;
             reviews.forEach((review) => {
             this._client.resources.reviews.reviewId(review.reviewId).level.reviewLevelId(review.currentReviewLevel.reviewLevelId).findings.lender.get().first().subscribe((findings) => {
             _findings = findings || [];
             _findings = _findings.filter(f => f.ratingCode === RATINGS.UNACCEPTABLE.code);
             review.lenderAction = null;
             if (_findings.every(f => FindingUtils.isFindingRespondedByLender(f))) {
             review.lenderAction = 'completed';
             } else if (_findings.some(f => FindingUtils.isFindingRespondedByLender(f))) {
             review.lenderAction = 'initiated';
             } else if(TODO: check to see if at least 1 finding...) {
             // If we have at least one unacceptable finding, we are pending lender action (ensures harvey ball shows)
             review.lenderAction = 'pending';
             }
             });
             });*/

        });

    }

    getCompletedLenderReviews() {
        this._client.resources.reviews.my.completed.lender.get().share().first().subscribe((reviews) => {
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

            this.completedLenderReviews = reviews;

        });
    }

    getReview(reviewId: String) {
        this.defectAreaDictionaryObservable = null;
        if (!this.loadedReviewObservable) {

            // Start a new Observable stream for a different Review.
            this.loadedReviewObservable = this._client.resources.reviews.reviewId(reviewId).lender.get().share();
        }

        return this.loadedReviewObservable.first();
    }

    getFinding(reviewId: String, reviewLevelId: String, findingId: String) {
        return this._client.resources.reviews.reviewId(reviewId).level.reviewLevelId(reviewLevelId).findings.findingId(findingId).lender.get().first();
    }


    saveFinding(finding: Object, _reviewId: String = null, _reviewLevelId: String = null) {
        let reviewId = (_reviewId) ? _reviewId : this._globalStateSvc.currentReviewId,
            reviewLevelId = (_reviewLevelId) ? _reviewLevelId : this._globalStateSvc.currentReviewLevelId;

        if (!reviewId || !reviewLevelId || !finding.findingId) {
            throw new Error('saveFinding error - reviewId, reviewLevelId or findingId is undefined.');
        }

        return this._client.resources.reviews.reviewId(reviewId).level.reviewLevelId(reviewLevelId).findings.findingId(finding.findingId).lender.put(finding).first();
    }

    getDefectAreasByReview(review: Object) {
        if (!review) {
            throw new Error('getDefectAreasByReview - Review is undefined.');
        }

        if (!this.defectAreaDictionaryObservable) {
            this.defectAreaDictionaryObservable = this._client.resources.reviews.dictionary.qaModels.qaModelId(review.qaModelId).defectAreas.get().first();
        }

        return this.defectAreaDictionaryObservable.map((defectAreas) => {
            return defectAreas.sort((a, b) => a.order - b.order).filter(da => {
                return review.defectAreaIds.find((id) => da.defectAreaId === id);
            });
        });
    }

}
