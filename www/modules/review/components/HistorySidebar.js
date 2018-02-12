// This file was generated from the component scaffold
// Copyright 2017

import {Component} from '@angular/core';
import {Router, ActivatedRoute} from '@angular/router';
import template from './HistorySidebar.html';
import styles from './HistorySidebar.less';

import ReviewService from '../services/ReviewService';
import GlobalStateSvc from '../services/GlobalStateSvc';
import ReviewDataSidebarBase from '../../shared/components/ReviewDataSidebarBase';
import {REVIEW_LEVEL_TYPE, REVIEW_LEVEL_STATUS, REVIEW_STATUS} from '../../shared/constants';
import DictionarySvc from '../../app/services/DictionarySvc';

import Utils from '../../shared/Utils';
import Client from '../../api/lrs.api.v1.generated';

@Component({
    selector: 'history-sidebar',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/api/core/Component-decorator.html
 * @example
 * <history-sidebar name="HistorySidebar" (change)="onChange($event)"></history-sidebar>
 */
export default class HistorySidebar extends ReviewDataSidebarBase{

    _globalStateSvc: GlobalStateSvc;
    _allRatingCodes: Array = [];
    allLevels: Array = [];
    dictionaryService: DictionarySvc;
    PENDING_BATCH_REVIEW_REVIEW_LEVEL_STATUS = REVIEW_LEVEL_STATUS.PENDING_BATCH_REVIEW;
    COMPLETED_REVIEW_LEVEL_STATUS = REVIEW_LEVEL_STATUS.COMPLETED;

    constructor(route:ActivatedRoute, reviewService:ReviewService, router:Router, globalState:GlobalStateSvc, client:Client,  dictionaryService: DictionarySvc) {
        super(route, reviewService, router, client);
        this._globalStateSvc = globalState;
        this.dictionaryService = dictionaryService;
    }

    // Override for ngInit()
    refreshCurrentReview() {
        this.resetSubscriptions();

        this._subscriptions.push(this._reviewService.currentReviewObservable.subscribe( (change) => {

            this.dictionaryService.getFindingRatingCodes().subscribe( codes => {
                this._allRatingCodes = codes;
                this.currentReview = change.currentValue;
                let levelCards = Utils.filterCompletedLevels(this.currentReview);
                levelCards.push(this.currentReview.currentReviewLevel);
                this.allLevels = levelCards;
            });
        }));
    }

    getRatingDescription(code: String) {
        let rating = this._allRatingCodes.find((r) => code === r.ratingCode );
        return rating && rating.description;
    }

    getLevelTitle(level: Object) {
        let title = (
            level.type.toLowerCase() !== REVIEW_LEVEL_TYPE.INITIAL &&
            level.type.toLowerCase().indexOf(REVIEW_LEVEL_TYPE.INDEMNIFICATION) === -1) ? `${level.type} ${level.iteration}` : level.type;

        // Display status if review is in progress and this card is the current review level
        if (this.currentReview.status.toLowerCase() !== REVIEW_STATUS.COMPLETED && level.reviewLevelId === this.currentReview.currentReviewLevel.reviewLevelId) {
            title += ' (' + Utils.getFHAReviewLevelStatus(this.currentReview, level) + ')';
        }

        return title;
    }

}
