// This file was generated from the view scaffold
// Copyright 2017

import {Component} from '@angular/core';
import {ActivatedRoute, Router}  from '@angular/router';
import template from './QaModel.html';
import styles from './QaModel.less';
import Client from '../../api/lrs.api.v1.generated';
import {Observable} from 'rxjs/Observable';
import {ModalSvc} from '../../app/services';
import _ from 'lodash';
import {QA_MODEL_STATUS_CODE} from '../constants';
import {REVIEWER_REVIEW_TYPES} from '../../shared/constants';

@Component({
    selector: 'qa-model',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/guide/router.html
 */
export default class QaModel {

    _router: Router;
    _route: ActivatedRoute;
    _client: Client;
    _modalSvc: ModalSvc;

    /**
     * Default name for the class, passed by reference via RouteParams
     */
    name: string = 'QaModelEdit';

    /**
     * Any new subscription will be added into this collection
     * @type {Array}
     */
    subscriptions: Array = [];

    reviewTypes: Array = [];

    qaModel: {} = {
        defectAreas: []
    };

    qaModelLoading = false;

    defectAreaDict = {};

    qaModelStatusCode = QA_MODEL_STATUS_CODE;

    constructor(route: ActivatedRoute, client: Client, modalSvc: ModalSvc, router: Router) {
        this._route = route;
        this._client = client;
        this._modalSvc = modalSvc;
        this._router = router;
    }

    ngOnInit() {
        this.subscriptions.push(this._route.params.subscribe(params => {
            this.name = params['name'];
            let qaModelId = params['qaModelId'];
            if (qaModelId) {
                let reviewTypesSource = this._client.resources.dictionary.reviewTypes.get().first()
                    .map(reviewTypes => {
                        return reviewTypes.filter(rt => {
                            return rt.code !== REVIEWER_REVIEW_TYPES.COMPREHENSIVE;
                        });
                    });
                let qaModelSource = this._client.resources.admin.qaModels.qaModelId(qaModelId).get().first();

                this.qaModelLoading = true;
                Observable.forkJoin(
                    reviewTypesSource,
                    qaModelSource
                        .map(qaModel => {
                            qaModel.defectAreas = _.sortBy(qaModel.defectAreas, 'order');
                            return qaModel;
                        }))
                    .subscribe(t => {
                        this.reviewTypes = t[0];
                        this.qaModel = t[1];
                        this._loadDefectAreaDict();
                        this.qaModelLoading = false;
                    });
            }
        }));
    }

    ngOnDestroy() {
        this.subscriptions.forEach(subscription => {
            subscription.unsubscribe();
        });
    }

    isDefectAreaChecked(defectArea, reviewTypeCode) {
        return this.defectAreaDict[defectArea.code][reviewTypeCode];
    }

    _loadDefectAreaDict() {
        this.defectAreaDict = {};
        this.qaModel.defectAreas.forEach(da => {
            let reviewTypesDict = {};
            this.reviewTypes.forEach(rt => {
                reviewTypesDict[rt.code] = da.reviewTypeCodes.indexOf(rt.code) >= 0;
            });
            this.defectAreaDict[da.code] = reviewTypesDict;
        });
    }
}
