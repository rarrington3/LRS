// This file was generated from the view scaffold
// Copyright 2016

import {Component} from '@angular/core';
import {ActivatedRoute, Router}  from '@angular/router';
import template from './IndemnificationRequest.html';
import styles from './IndemnificationRequest.less';
import LenderService from '../services/LenderService';
import ModalSvc from '../../app/services/ModalSvc';
import Client from '../../api/lrs.api.v1.generated';
import Utils from '../../shared/Utils';

let compiledConfig = require('config');

@Component({
    selector: 'indemnification-request',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/guide/router.html
 */
export default class IndemnificationRequest {

    route:ActivatedRoute;
    _router:Router;
    _lenderService:LenderService;
    _review:Object;
    _currentReviewLevelId:String;
    _modalSvc:ModalSvc;
    caseNumber:String = '';
    indemSigned:Boolean = false;
    _client:Client;
    isReadOnly:Boolean = false;
    /**
     * Any new subscription will be added into this collection
     * @type {Array}
     */
    subscriptions:Array = [];
    document:Object = {};

    constructor(route:ActivatedRoute, router:Router, lenderService:LenderService, modalSvc:ModalSvc, client:Client) {
        this.route = route;
        this._router = router;
        this._lenderService = lenderService;
        this._modalSvc = modalSvc;
        this._client = client;
    }

    ngOnInit() {
        this.subscriptions.push(this.route.params.subscribe(params => {
            let reviewId = params.reviewId || this._lenderService.activeReviewId;
            this._lenderService.getReview(reviewId).subscribe((review) => {
                this._review = review;
                this._currentReviewLevelId = review.currentReviewLevel.reviewLevelId;
                this.caseNumber = this._review.caseNumber || '';
                this.document = {url:`${compiledConfig.server}/api/v1/reviews/${this._review.reviewId}/level/${this._review.currentReviewLevel.reviewLevelId}/indemnification/prePopulated`, withCredentials: true};
                this.indemSigned = Utils.isLevelIndemnified(review);
                this.isReadOnly = Utils.isLenderReviewReadOnly(review);
            });
        }));
    }

    ngOnDestroy() {
        this.subscriptions.forEach(subscription => {
            subscription.unsubscribe();
        });
    }

    onSign() {
        this.document = {url:`${compiledConfig.server}/api/v1/reviews/${this._review.reviewId}/level/${this._review.currentReviewLevel.reviewLevelId}/indemnification/preSigned/lender`, withCredentials: true};
        this.indemSigned = true;
    }

    onSubmit() {
        this._modalSvc.askForConfirmation(
                `<p>You are about to submit an indemnification for Case ${this.caseNumber} </p><p>Are you sure you want to submit this Indemnification?</p>`,
                'Confirm Indemnification', 'Confirm', 'Cancel')
                .first().subscribe(() => {
                    this._client.resources.reviews.reviewId(this._review.reviewId).level.reviewLevelId(this._review.currentReviewLevel.reviewLevelId).indemnification.lenderSubmit.put().first().subscribe(() => {
                        this._router.navigate(['/lender/activeReviews']);
                    });
                },
                () => {});
    }

}
