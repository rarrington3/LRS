// This file was generated from the view scaffold
// Copyright 2016

import {Component, Input, Output, EventEmitter } from '@angular/core';
import {Router}  from '@angular/router';
import template from './ReviewIndemSign.html';
import styles from './ReviewIndemSign.less';
import ModalSvc from '../../app/services/ModalSvc';
import Client from '../../api/lrs.api.v1.generated';
import {REVIEWER_INDEM_STATE} from './ReviewIndem';
import Utils from '../../shared/Utils';
import {SERVER_ERROR} from '../../shared/constants';

let compiledConfig = require('config');

@Component({
    selector: 'review-indem-sign',
    template: template,
    styles: [styles]
})


export default class ReviewIndemSign {
    @Output()
    indemStateChanged:EventEmitter = new EventEmitter();

    @Input()
    review: Object;

    @Input()
    indemAcceptData: Object = null;

    @Input()
    readOnly: Boolean = false;

    @Input()
    reviewIsCompleted: Boolean = false;

    _indemState: string = null;
    _modalSvc: ModalSvc;
    indemSigned: Boolean = false;
    _client: Client;
    _router: Router;
    STATE: Object = REVIEWER_INDEM_STATE;


    /**
     * Any new subscription will be added into this collection
     * @type {Array}
     */
    subscriptions:Array = [];
    document:Object = {};

    constructor(modalSvc:ModalSvc, client:Client, router:Router) {
        this._modalSvc = modalSvc;
        this._client = client;
        this._router = router;
    }

    @Input()
    set indemState(state: String) {
        this._indemState = state;
        this._updateState();
    }

    get indemState() {
        return this._indemState;
    }

    ngOnInit() {
    }

    _updateState() {

        // set the PDF document url based on the current state.
        if (this.indemState === REVIEWER_INDEM_STATE.REVIEWER_SIGN || this.indemState === REVIEWER_INDEM_STATE.REVIEWER_SIGNED) {
            let forcedIndemPresigned = {url:`${compiledConfig.server}/api/v1/reviews/${this.review.reviewId}/level/${this.review.currentReviewLevel.reviewLevelId}/indemnification/preSigned/reviewer`, withCredentials: true};
            this.document = (this.isForcedIndem()) ?  forcedIndemPresigned : {url:`${compiledConfig.server}/api/v1/reviews/${this.review.reviewId}/level/${this.review.currentReviewLevel.reviewLevelId}/indemnification/preSigned/both`, withCredentials: true};
        } else {
            // Step 1
            let forcedIndemPrePopulated = {url:`${compiledConfig.server}/api/v1/reviews/${this.review.reviewId}/level/${this.review.currentReviewLevel.reviewLevelId}/indemnification/prePopulated`, withCredentials: true};
            this.document = (this.isForcedIndem()) ? forcedIndemPrePopulated : {url:`${compiledConfig.server}/api/v1/reviews/${this.review.reviewId}/level/${this.review.currentReviewLevel.reviewLevelId}/indemnification/preSigned/lender`, withCredentials: true};
        }
    }

    ngOnDestroy() {
        this.subscriptions.forEach(subscription => {
            subscription.unsubscribe();
        });
    }

    _navigateToWorkload() {
        this._router.navigate(['/workload/summary']);
    }

    onSubmit() {
        if (this.indemState === REVIEWER_INDEM_STATE.REVIEWER_SIGN && this.indemAcceptData) {
            this._client.resources.reviews.reviewId(this.review.reviewId).level.reviewLevelId(this.review.currentReviewLevel.reviewLevelId).indemnification.reviewerAccept.put(this.indemAcceptData).first().subscribe(() => {
                this.indemStateChanged.emit(this.STATE.REVIEWER_SIGNED);
                this._navigateToWorkload();
            }, error => {
                if (error.status === SERVER_ERROR.EXCEPTION) {
                    let errorMessage;
                    let errorMessages = Utils.getErrorMessages(error);
                    if (errorMessages && errorMessages.length) {
                        errorMessage = errorMessages[0];
                    } else {
                        throw error;
                    }
                    this._modalSvc.acknowledge(errorMessage, 'Issue Submitting Indemnification').first().subscribe();
                } else {
                    throw error;
                }
            });
        }
    }

    onReject() {
        this._modalSvc.askForConfirmation(
            `<p>You are about to reject an Indemnification for Case ${this.review.caseNumber} </p><p>Are you sure you want to reject this Indemnification?</p>`,
            'Confirm Indemnification', 'Confirm', 'Cancel')
            .first().subscribe(() => {
                this._client.resources.reviews.reviewId(this.review.reviewId).level.reviewLevelId(this.review.currentReviewLevel.reviewLevelId).indemnification.reviewerReject.put().first().subscribe(() => {
                    this._navigateToWorkload();
                });
            },
            () => {
                this.indemStateChanged.emit(this.STATE.LENDER_SIGNED);
            });
    }

    onNext() {
        this.indemStateChanged.emit(this.STATE.REVIEWER_CONFIRM);
    }

    onCancel() {
        this.indemStateChanged.emit(this.STATE.REVIEWER_CANCELED);
    }

    isForcedIndem() {
        return Utils.isForcedIndemReview(this.review);
    }

    onViewIndemInfo() {
        this._router.navigate([`/review/${this.review.reviewId}/reviewIndemnification`], {queryParams: {showConfirmingData: true}});
    }

}
