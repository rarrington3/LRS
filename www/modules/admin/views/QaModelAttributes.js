// This file was generated from the view scaffold
// Copyright 2017

import {Component} from '@angular/core';
import {ActivatedRoute}  from '@angular/router';
import template from './QaModelAttributes.html';
import styles from './QaModelAttributes.less';
import Client from '../../api/lrs.api.v1.generated';
import {QA_MODEL_STATUS_CODE} from '../constants';
import _ from 'lodash';

@Component({
    selector: 'qa-model-attributes',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/guide/router.html
 */
export default class QaModelAttributes {

    _route: ActivatedRoute;
    _client: Client;

    /**
     * Default name for the class, passed by reference via RouteParams
     */
    name: string = 'QaModelAttributes';

    /**
     * Any new subscription will be added into this collection
     * @type {Array}
     */
    subscriptions: Array = [];

    qaModel: {} = {
        categories: []
    };

    qaModelLoading = false;

    qaModelStatusCode = QA_MODEL_STATUS_CODE;

    constructor(route: ActivatedRoute, client: Client) {
        this._route = route;
        this._client = client;
    }

    ngOnInit() {
        this.subscriptions.push(this._route.params.subscribe(params => {
            this.name = params['name'];
            let qaModelId = params['qaModelId'];
            if (qaModelId) {

                this.qaModelLoading = true;

                this._client.resources.admin.qaModels.qaModelId(qaModelId).get().first()
                    .map(qaModel => {
                        qaModel.categories = _.sortBy(qaModel.categories, 'order');
                        return qaModel;
                    })
                    .subscribe(qaModel => {
                        this.qaModel = qaModel;
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
}
