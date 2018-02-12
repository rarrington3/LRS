// This file was generated from the view scaffold
// Copyright 2016

import {Component} from '@angular/core';
import {ActivatedRoute, Router}  from '@angular/router';
import template from './QaModelEdit.html';
import styles from './QaModelEdit.less';
import Client from '../../api/lrs.api.v1.generated';
import {Observable} from 'rxjs/Observable';
import {SaveBarSvc, ModalSvc} from '../../app/services';
import _ from 'lodash';
import ArrayUtils from '../../shared/utils/ArrayUtils';
import {QA_MODEL_STATUS_CODE} from '../constants';
import {REVIEWER_REVIEW_TYPES, SERVER_ERROR} from '../../shared/constants';
import Utils from '../../shared/Utils';

@Component({
    selector: 'qa-model-edit',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/guide/router.html
 */
export default class QaModelEdit {

    _router: Router;
    _route: ActivatedRoute;
    _client: Client;
    _saveBarSvc: SaveBarSvc;
    _modalSvc: ModalSvc;

    _newDefectAreaData = {
        isOpen: false,
        defectArea: {}
    };

    _newDefectArea = {
        code: '',
        description: '',
        order: '',
        reviewTypeCodes: []
    };

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

    defectAreaData = Object.assign({}, this._newDefectAreaData);

    qaModelStatusCode = QA_MODEL_STATUS_CODE;

    constructor(route: ActivatedRoute, client: Client, saveBarSvc: SaveBarSvc, modalSvc: ModalSvc, router: Router) {
        this._route = route;
        this._client = client;
        this._saveBarSvc = saveBarSvc;
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

                let qaModelSource = this._client.resources.admin.qaModels.qaModelId(qaModelId).get().first()
                    .map(qaModel => this._mapQaModel(qaModel));

                this.qaModelLoading = true;
                Observable.forkJoin(
                    reviewTypesSource,
                    qaModelSource)
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

    load() {
        this._client.resources.admin.qaModels.qaModelId(this.qaModel.qaModelId).get().first()
            .map(qaModel => this._mapQaModel(qaModel))
            .subscribe(qaModel => {
                this.qaModel = qaModel;
                this._loadDefectAreaDict();
            });
    }

    save() {
        this._client.resources.admin.qaModels.qaModelId(this.qaModel.qaModelId).put(this.qaModel).first()
            .map(qaModel => this._mapQaModel(qaModel))
            .subscribe(qaModel => {
                this.qaModel = qaModel;
                this._loadDefectAreaDict();
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

    add(defectArea) {
        defectArea = ArrayUtils.add(this.qaModel.defectAreas, defectArea);

        this._loadDefectAreaDict();
        this.resetData();

        this.askForSave();
        return defectArea;
    }

    open() {
        this.defectAreaData.defectArea = Object.assign({}, this._newDefectArea);
        this.defectAreaData.isOpen = true;
    }

    resetData() {
        this.defectAreaData = Object.assign({}, this._newDefectAreaData);
    }

    activate() {
        this._client.resources.admin.qaModels.qaModelId(this.qaModel.qaModelId).activate.put().first()
            .subscribe(() => {
                this._router.navigate(['/admin/modelManagement/qaModels']);
            }, error => {
                if (error.status === SERVER_ERROR.CONFLICT) {
                    let errorMessage;
                    let errorMessages = Utils.getErrorMessages(error);
                    if (errorMessages && errorMessages.length) {
                        errorMessage = errorMessages[0];
                    } else {
                        errorMessage = 'Cannot activate the model.';
                    }
                    this._modalSvc.acknowledge(errorMessage, 'Error').first().subscribe();
                } else {
                    throw error;
                }
            });
    }

    checkDefectArea(defectArea, reviewTypeCode) {
        this.defectAreaDict[defectArea.code][reviewTypeCode] = !this.defectAreaDict[defectArea.code][reviewTypeCode];

        let index = defectArea.reviewTypeCodes.indexOf(reviewTypeCode);
        if (index >= 0) {
            defectArea.reviewTypeCodes.splice(index, 1);
        }

        if (this.defectAreaDict[defectArea.code][reviewTypeCode]) {
            defectArea.reviewTypeCodes.push(reviewTypeCode);
        }

        this.askForSave();
    }

    isDefectAreaChecked(defectArea, reviewTypeCode) {
        return this.defectAreaDict[defectArea.code][reviewTypeCode];
    }

    removeDefectArea(defectArea) {
        let message = 'Are you sure you want to remove the item?';
        this._modalSvc.askForConfirmation(message).first()
            .subscribe(
                () => {
                    ArrayUtils.remove(this.qaModel.defectAreas, defectArea);
                    this.askForSave();
                }, () => {
                }
            );
    }

    sortDefectAreaUp(defectArea) {
        if (ArrayUtils.sortUp(this.qaModel.defectAreas, defectArea)) {
            this.askForSave();
        }
    }

    sortDefectAreaDown(defectArea) {
        if (ArrayUtils.sortDown(this.qaModel.defectAreas, defectArea)) {
            this.askForSave();
        }
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

    _mapQaModel(qaModel) {
        qaModel.defectAreas = _.sortBy(qaModel.defectAreas, 'order');
        return qaModel;
    }
}
