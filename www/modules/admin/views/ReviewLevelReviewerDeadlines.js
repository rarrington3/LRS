// This file was generated from the view scaffold
// Copyright 2016

import {Component} from '@angular/core';
import {ActivatedRoute}  from '@angular/router';
import template from './ReviewLevelReviewerDeadlines.html';
import styles from './ReviewLevelReviewerDeadlines.less';
import Client from '../../api/lrs.api.v1.generated';
import {SaveBarSvc} from '../../app/services';
import {Observable} from 'rxjs/Observable';
import {COMMON_OPERATION} from '../../shared/constants';
import _ from 'lodash';

@Component({
    selector: 'review-level-reviewer-deadlines',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/guide/router.html
 */
export default class ReviewLevelReviewerDeadlines {

    _route: ActivatedRoute;
    _client: Client;
    _saveBarSvc: SaveBarSvc;
    _saveQueue: Array = [];

    /**
     * Any new subscription will be added into this collection
     * @type {Array}
     */
    subscriptions: Array = [];

    reviewLevelTimeLimits: Array = [];

    reviewLevelTimeLimitsLoading: boolean = false;

    selectionReasons: Array = [];

    constructor(route: ActivatedRoute, client: Client, saveBarSvc: SaveBarSvc) {
        this._route = route;
        this._client = client;
        this._saveBarSvc = saveBarSvc;
    }

    ngOnInit() {
        let selectionReasonsSource = this._client.resources.dictionary.consolidatedSelectionReasons.get().first()
            .map(selectionReasons => {
                selectionReasons = _.sortBy(selectionReasons, function (sr) {
                    return sr.code;
                });
                return selectionReasons;
            });

        let reviewerTimeLimitsSource = this._client.resources.admin.reviewLevel.reviewerTimeLimits.get().first()
            .map(reviewLevelTimeLimits => this._mapTimeLimits(reviewLevelTimeLimits));

        this.reviewLevelTimeLimitsLoading = true;
        Observable.forkJoin(
            selectionReasonsSource,
            reviewerTimeLimitsSource)
            .subscribe(t => {
                this.selectionReasons = t[0];
                this.reviewLevelTimeLimits = t[1];
                this.reviewLevelTimeLimitsLoading = false;
            });
    }

    ngOnDestroy() {
        this.subscriptions.forEach(subscription => {
            subscription.unsubscribe();
        });
    }

    load() {
        this._client.resources.admin.reviewLevel.reviewerTimeLimits.get().first()
            .map(reviewLevelTimeLimits => this._mapTimeLimits(reviewLevelTimeLimits))
            .subscribe((reviewLevelTimeLimits) => {
                this.reviewLevelTimeLimits = reviewLevelTimeLimits;
            });
    }

    save() {
        let sources = this._saveQueue.map(entry => {
            switch (entry.operation) {
                case COMMON_OPERATION.UPDATE:
                    return this._client.resources.admin.reviewLevel.reviewerTimeLimits
                        .reviewLevelCode(entry.reviewLevelTimeLimit.reviewLevelCode)
                        .put(entry.timeLimit).first();
                default:
                    return Observable.empty();
            }
        });

        Observable.concat(...sources)
            .subscribe(() => {
                this._saveQueue = []; // clear the queue
            });
    }

    askForSave(timeLimit, reviewLevelTimeLimit) {
        if (!timeLimit.days) {
            timeLimit.days = 0;
        }

        if (!this._saveQueue.find(entry => entry.timeLimit === timeLimit)) { // add to save queue if not already there
            this._saveQueue.push({
                operation: COMMON_OPERATION.UPDATE,
                timeLimit: timeLimit,
                reviewLevelTimeLimit: reviewLevelTimeLimit
            });
        }

        if (!this._saveBarSvc.isOpen()) { // if it is already open we don't need to ask for save
            this._saveBarSvc.askForSave()
                .subscribe(
                    () => {
                        this.save();
                    }, () => {
                        this.load();
                    }
                );
        }
    }

    _mapTimeLimits(reviewLevelTimeLimits) {
        reviewLevelTimeLimits.forEach(tl => {
            tl.selectionReasons = _.sortBy(tl.selectionReasons, function (sr) {
                return sr.selectionReasonCode;
            });
        });
        return reviewLevelTimeLimits;
    }
}
