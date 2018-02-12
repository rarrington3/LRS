// This file was generated from the component scaffold
// Copyright 2017

import {Component, Input, Output, EventEmitter} from '@angular/core';
import {Router} from '@angular/router';
import ModalSvc from '../../app/services/ModalSvc';
import Client from '../../api/lrs.api.v1.generated';
import template from './ReviewIndemConfirm.html';
import styles from './ReviewIndemConfirm.less';
import {REVIEWER_INDEM_STATE} from './ReviewIndem';
import Utils from '../../shared/Utils';
import moment from 'moment';
import {HqAdminGuard} from '../../app/services/AuthGuards';
import _ from 'lodash';

@Component({
    selector: 'review-indem-confirm',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/api/core/Component-decorator.html
 * @example
 * <review-indem-confirm name="ReviewIndemConfirm" (change)="onChange($event)"></review-indem-confirm>
 */
export default class ReviewIndemConfirm {
    @Output()
    indemStateChanged:EventEmitter = new EventEmitter();

    @Output()
    confirmChanged:EventEmitter = new EventEmitter();


    _review: Object = null;

    _modalSvc: ModalSvc;
    _client: Client;
    _hqAdminGuard: HqAdminGuard;
    _router: Router;

    reviewLocations: Array = [];
    selectedReviewLocationId: string = null;
    selectedIndemTransferrable: Boolean = false;
    allowReviewLocationSelection: Boolean = false;

    @Input()
    isReadOnly: Boolean = false;

    /**
     * Any new subscription will be added into this collection
     * @type {Array}
     */
    subscriptions:Array = [];


    constructor(modalSvc:ModalSvc, client:Client, hqAdminGuard: HqAdminGuard, router: Router) {
        this._modalSvc = modalSvc;
        this._client = client;
        this._hqAdminGuard = hqAdminGuard;
        this._router = router;
    }

    ngOnInit() {
        this.agreementDate = moment.utc().format('l');
    }

    @Input()
    set review(review: Object) {
        this._review = review;
        this._loadDependencyData();
    }

    get review() {
        return this._review;
    }

    _loadDependencyData() {
        if (this.review && !this.reviewLocations.length) {
            this._client.resources.locations.get().first().subscribe(result => {
                this.reviewLocations = result;
                this.selectedReviewLocationId = null;
                _.defer(() => {
                    this.selectedReviewLocationId = this.review.currentReviewLevel.reviewLocationId || null;
                    this.allowReviewLocationSelection = this._hqAdminGuard.canActivate();
                });
            });
        }
    }

    ngOnDestroy() {
        this.subscriptions.forEach(subscription => {
            subscription.unsubscribe();
        });
    }

    onNext() {
        if (this.selectedReviewLocationId) {
            // Capture the selected values in the parent view.
            this.confirmChanged.emit({reviewLocationId: this.selectedReviewLocationId, indemTransferrable: this.selectedIndemTransferrable});
            this.indemStateChanged.emit(REVIEWER_INDEM_STATE.REVIEWER_SIGN);
        }
    }

    onCancel() {
        this.indemStateChanged.emit(REVIEWER_INDEM_STATE.REVIEWER_CANCELED);
    }


    isForcedIndem() {
        return Utils.isForcedIndemReview(this.review);
    }

    onViewIndem() {
        this._router.navigate([`/review/${this.review.reviewId}/reviewIndemnification`]);
    }

}
