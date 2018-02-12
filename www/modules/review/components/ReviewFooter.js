// This file was generated from the component scaffold
// Copyright 2016
import {Component} from '@angular/core';
import template from './ReviewFooter.html';
import styles from './ReviewFooter.less';
import GlobalStateSvc from '../services/GlobalStateSvc';
import ReviewService from  '../services/ReviewService';
import FindingsService from '../services/FindingsService';
import DictionarySvc from '../../app/services/DictionarySvc';
import {FormBuilder} from '@angular/forms';
import {Router} from '@angular/router';

@Component({
    selector: 'review-footer',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/api/core/Component-decorator.html
 * @example
 * <view-bottom-bar name="ViewFooter" (change)="onChange($event)"></view-bottom-bar>
 */
export default class ReviewFooter {

    reviewService:ReviewService;
    findingsService: FindingsService;
    globalStateService:GlobalStateSvc;
    subscriptions:Array = [];
    expanded:Boolean = false;

    constructor(globalState:GlobalStateSvc, reviewService:ReviewService, findingsService: FindingsService, formBuilder:FormBuilder, router:Router, dictionaryService:DictionarySvc) {
        this.dictionaryService = dictionaryService;
        this.reviewService = reviewService;
        this.findingsService = findingsService;
        this.globalStateService = globalState;
        this.formBuilder = formBuilder;
        this.router = router;
    }

    ngOnInit() {
        this._resetSubscriptions();
        this.subscriptions.push(this.globalStateService.currentDefectAreaCodeObservable.subscribe(() => {
            this.onDefectAreaChanged();
        }));
    }

    ngOnDestroy() {
        this._resetSubscriptions();
    }

    _resetSubscriptions() {
        this.subscriptions.forEach(subscription => {
            subscription.unsubscribe();
        });
        this.subscriptions = [];
    }

    onDefectAreaChanged() {
        // Collapse the footer whenever the review route changes.
        this.expanded = false;
    }

    toggleFooterSlider() {
        this.expanded = !this.expanded;
    }

    expandSlider() {
        if (!this.expanded) {
            this.expanded = true;
        }
    }

    getDefectAreaTitleByCode(code:String) {
        let defectAreaObject = this.globalStateService.defectAreasForCurrentReview.find(a => a.defectAreaCode === code);
        return (defectAreaObject) ? defectAreaObject.title : code;
    }
}
