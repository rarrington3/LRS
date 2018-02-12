// This file was generated from the component scaffold
// Copyright 2017

import {Component} from '@angular/core';
import {ActivatedRoute, Router}  from '@angular/router';
import template from './ReviewIndem.html';
import styles from './ReviewIndem.less';
import ModalSvc from '../../app/services/ModalSvc';
import Client from '../../api/lrs.api.v1.generated';
import ReviewService from '../services/ReviewService';
import Utils from '../../shared/Utils';

export const REVIEWER_INDEM_STATE = {
    LENDER_SIGNED: 'step_1_lender_signed',
    REVIEWER_CONFIRM: 'step_2_confirm',
    REVIEWER_SIGN: 'step_3_reviewer_sign',
    REVIEWER_SIGNED: 'step_3_reviewer_signed',
    REVIEWER_REJECTED: 'reviewer_rejected',
    REVIEWER_CANCELED: 'reviewer_canceled'
};

@Component({
    selector: 'review-indem',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/api/core/Component-decorator.html
 * @example
 * <review-indem name="ReviewIndem" (change)="onChange($event)"></review-indem>
 */
export default class ReviewIndem {
    indemState = null;
    readonly: Boolean = true
    STATE = REVIEWER_INDEM_STATE;
    subscriptions: Array = [];
    _reviewService: ReviewService;
    review: Object;

    indemAcceptData: Object = null;
    showConfirmingData: Boolean = false;
    reviewIsCompleted: Boolean = false;
    constructor(reviewService: ReviewService, modalSvc:ModalSvc, client:Client, route: ActivatedRoute, router: Router) {
        this._modalSvc = modalSvc;
        this._client = client;
        this._reviewService = reviewService;
        this._route = route;
        this._router = router;
    }

    ngOnInit() {
        this._resetSubscriptions();

        this.subscriptions.push(this._route.queryParams.subscribe((params = {}) => {
            this.showConfirmingData = params.showConfirmingData;

            this.subscriptions.push(this._reviewService.currentReviewObservable.subscribe((data) => {
                this._resetState(data.currentValue);
            }));

            if (this._reviewService.currentReview) {
                this._resetState(this._reviewService.currentReview);
            }
        }));
    }
    _resetState(review: Object) {
        if (review) {
            this.review = review;
            this.reviewIsCompleted = Utils.isReviewCompleted(review);
            let currentReviewLevelCompleted = Utils.isCurrentReviewLevelCompleted(this.review);

            this.readOnly =
                !Utils.isCurrentUserAssignedToReviewLevel(this.review) ||
                (!Utils.isIndemReview(this.review) && !Utils.isForcedIndemReview(this.review)) ||
                this.review.currentReviewLevel.status.toLowerCase() === 'canceled' || currentReviewLevelCompleted;

            if (this.reviewIsCompleted && this.showConfirmingData) {
                this.indemState = REVIEWER_INDEM_STATE.REVIEWER_CONFIRM;
            }
            else if (currentReviewLevelCompleted) {
                this.indemState = REVIEWER_INDEM_STATE.REVIEWER_SIGNED;
            } else {
                this.indemState = REVIEWER_INDEM_STATE.LENDER_SIGNED;
            }
        }
    }

    _resetSubscriptions() {
        this.subscriptions.forEach(subscription => {
            subscription.unsubscribe();
        });

        this.subscriptions = [];
    }

    onStateChanged(state: string) {

        // emit from the child view.
        if (state === REVIEWER_INDEM_STATE.REVIEWER_CANCELED) {
            this._router.navigate(['/workload/summary']);

        } else {
            this.indemState = state;
        }
    }

    onIndemConfirmChanged(data: Object) {

        // Capture the selected confirm data for the next view. Emit from the child view.
        this.indemAcceptData = data;
    }

    ngOnDestroy() {
        this._resetSubscriptions();
    }
}
