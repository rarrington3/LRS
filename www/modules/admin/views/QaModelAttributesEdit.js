// This file was generated from the view scaffold
// Copyright 2017

import {Component} from '@angular/core';
import {ActivatedRoute}  from '@angular/router';
import template from './QaModelAttributesEdit.html';
import styles from './QaModelAttributesEdit.less';
import Client from '../../api/lrs.api.v1.generated';
import {SaveBarSvc, ModalSvc} from '../../app/services';
import {QA_MODEL_STATUS_CODE} from '../constants';
import ArrayUtils from '../../shared/utils/ArrayUtils';
import _ from 'lodash';

@Component({
    selector: 'qa-model-attributes-edit',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/guide/router.html
 */
export default class QaModelAttributesEdit {

    _route: ActivatedRoute;
    _client: Client;
    _saveBarSvc: SaveBarSvc;
    _modalSvc: ModalSvc;
    _sortBy = 'order';

    _newAttribute = {
        entityName: '',
        fieldName: '',
        order: 0
    };

    /**
     * Default name for the class, passed by reference via RouteParams
     */
    name: string = 'QaModelAttributesEdit';

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

    fields: Array = [];

    newCategory = {
        _selectedFieldCode: '',
        description: '',
        order: 0,
        loanAttributes: []
    };

    constructor(route: ActivatedRoute, client: Client, saveBarSvc: SaveBarSvc, modalSvc: ModalSvc) {
        this._route = route;
        this._client = client;
        this._saveBarSvc = saveBarSvc;
        this._modalSvc = modalSvc;
    }

    ngOnInit() {
        this.subscriptions.push(this._route.params.subscribe(params => {
            this.name = params['name'];
            let qaModelId = params['qaModelId'];
            if (qaModelId) {

                this.qaModelLoading = true;
                this._client.resources.admin.qaModels.qaModelId(qaModelId).get().first()
                    .map(qaModel => this._map(qaModel))
                    .subscribe(qaModel => {
                        this.qaModel = qaModel;
                        this.qaModelLoading = false;
                    });

                this._client.resources.admin.fields.get().first()
                    .subscribe(fields => {
                        this.fields = fields;
                    });
            }
        }));
    }

    ngOnDestroy() {
        this.subscriptions.forEach(subscription => {
            subscription.unsubscribe();
        });
    }

    load() {
        this._client.resources.admin.qaModels.qaModelId(this.qaModel.qaModelId).get().first()
            .map(qaModel => this._map(qaModel))
            .subscribe(qaModel => {
                this.qaModel = qaModel;
            });
    }

    save() {
        this._client.resources.admin.qaModels.qaModelId(this.qaModel.qaModelId).put(this.qaModel).first()
            .map(qaModel => this._map(qaModel))
            .subscribe(qaModel => {
                this.qaModel = qaModel;
            });
    }

    askForSave() {
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

    sortUp(items, item) {
        if (ArrayUtils.sortUp(items, item)) {
            this.askForSave();
        }
    }

    sortDown(items, item) {
        if (ArrayUtils.sortDown(items, item)) {
            this.askForSave();
        }
    }

    add(items: Array, newItemTemplate: any) {
        let item = ArrayUtils.add(items, newItemTemplate);
        this.askForSave();
        return item;
    }

    remove(items: Array, item: any, message: string) {
        message = message || 'Are you sure you want to remove the item?';
        this._modalSvc.askForConfirmation(message).first()
            .subscribe(
                () => {
                    ArrayUtils.remove(items, item);
                    this.askForSave();
                }, () => {
                }
            );
    }

    addAttribute(category) {
        let field = this.fields.find((f) => f.code === category._selectedFieldCode);
        let attributeTemplate = Object.assign({}, this._newAttribute, {
            entityName: field.entityName,
            fieldName: field.fieldName
        });
        category._selectedFieldCode = '';
        this.add(category.loanAttributes, attributeTemplate);
    }

    _map(qaModel) {
        let sortBy = this._sortBy;
        qaModel.categories = _.sortBy(qaModel.categories, sortBy);
        qaModel.categories.forEach(category => {
            category._selectedFieldCode = '';
            category.loanAttributes = _.sortBy(category.loanAttributes, sortBy);
        });
        return qaModel;
    }
}
