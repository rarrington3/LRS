// This file was generated from the view scaffold
// Copyright 2016

import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import template from './lenderReviewContainer.html';
import styles from './lenderReviewContainer.less';
import LenderService from '../services/LenderService';
import FindingsService from '../../review/services/FindingsService';
import { IndemnifierGuard } from '../../app/services/AuthGuards';
import Utils from '../../shared/Utils';

@Component({
    selector: 'lender-review-container',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/guide/router.html
 */
export default class LenderReviewContainer {

    _route: ActivatedRoute;
    _lenderService: LenderService;
    _router: Router;
    _indemnifierGuard: IndemnifierGuard;
    isOperationalReview: Boolean = false;

    /**
     * Any new subscription will be added into this collection
     * @type {Array}
     */
    subscriptions: Array = [];

    showIndemnification: Boolean = false;

    constructor(route: ActivatedRoute, lenderService: LenderService, findingService: FindingsService, router: Router, indemnifierGuard: IndemnifierGuard) {
        this._route = route;
        this._lenderService = lenderService;
        this._router = router;
        this._indemnifierGuard = indemnifierGuard;

        // Reset the allFindingsRequestObservable in the review service so we can fetch findings for the new selected review id in Lender.
        // Currently we are using the same service instance in the Review module to leverage all the dictionary and finding data sources.
        findingService.currentFindings = null;
        findingService.allFindingsRequestObservable = null;

        this._lenderService.loadedReviewObservable = null;
    }

    ngOnInit() {

        this.subscriptions.push(this._route.params.subscribe(params => {
            this._lenderService.activeReviewId = params.reviewId;

            this._lenderService.getReview(params.reviewId).subscribe(review => {

                this.currentReview = review;
                this.showIndemnification = Utils.isLevelIndemnified(review) || (this._indemnifierGuard.canActivate() && !Utils.isLenderReviewReadOnly(review) && !Utils.isReviewLevelOperational(review));
                this.isOperationalReview = Utils.isReviewLevelOperational(review);

            });
        }));

    }

    ngOnDestroy() {
        this.subscriptions.forEach(subscription => {
            subscription.unsubscribe();
        });
    }

    onIndemnificationClick() {

        this._router.navigate([`/lender/activeReviews/review/${this._lenderService.activeReviewId}/indemnificationRequest`]);

    }
}
