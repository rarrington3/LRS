// This file was generated from the view scaffold
// Copyright 2016

import {Component} from '@angular/core';
import {ActivatedRoute}  from '@angular/router';
import template from './QaModels.html';
import styles from './QaModels.less';
import Client from '../../api/lrs.api.v1.generated';
import {ModalSvc} from '../../app/services';
import {QA_MODEL_STATUS_CODE} from '../constants';
import {SERVER_ERROR} from '../../shared/constants';
import Utils from '../../shared/Utils';
import SortableReviewTable from '../../shared/components/SortableReviewTable';

@Component({
    selector: 'qa-models',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/guide/router.html
 */
export default class QaModels {

    _route: ActivatedRoute;
    _client: Client;
    _modalSvc: ModalSvc;

    _newDuplicateModel = {
        name: ''
    };

    _newDuplicateData = {
        isOpen: false,
        qaModel: {},
        duplicateQaModel: {}
    };


    /**
     * Default name for the class, passed by reference via RouteParams
     */
    name: string = 'QaModels';

    /**
     * Any new subscription will be added into this collection
     * @type {Array}
     */
    subscriptions: Array = [];

    qaModels: Array = [];

    qaModelsLoading = false;

    qaModelStatusCode = QA_MODEL_STATUS_CODE;

    duplicateData = Object.assign({}, this._newDuplicateData);

    constructor(route: ActivatedRoute, client: Client, modalSvc: ModalSvc) {
        this._route = route;
        this._client = client;
        this._modalSvc = modalSvc;
    }

    ngOnInit() {
        this.subscriptions.push(this._route.params.subscribe(params => {
            this.name = params['name'];
        }));

        this.qaModelsLoading = true;
        this._client.resources.admin.qaModels.get().first()
            .map(qaModels => {

                // default sort.
                let column = 'status';
                return SortableReviewTable.sortItems(qaModels, column, column, SortableReviewTable.ASCENDING);
            })
            .subscribe(qaModels => {
                this.qaModels = qaModels;
                this.qaModelsLoading = false;
                this.sort('modifiedDate');
            });
    }

    ngOnDestroy() {
        this.subscriptions.forEach(subscription => {
            subscription.unsubscribe();
        });
    }

    sort(columnName) {

        // client side only
        this.qaModels = SortableReviewTable.sortItems(this.qaModels, columnName, columnName);
    }

    duplicate(qaModel, duplicateQaModel) {
        this._client.resources.admin.qaModels.qaModelId(qaModel.qaModelId).duplicate.put(duplicateQaModel).first()
            .subscribe(qaModel => {
                this.qaModels.push(qaModel);
            }, error => {
                if (error.status === SERVER_ERROR.BAD_REQUEST) {
                    let errorMessage;
                    let errorMessages = Utils.getErrorMessages(error);
                    if (errorMessages && errorMessages.length) {
                        errorMessage = errorMessages[0];
                    } else {
                        errorMessage = 'Cannot duplicate the qa model.';
                    }
                    this._modalSvc.acknowledge(errorMessage, 'Error').first().subscribe();
                } else {
                    throw error;
                }
            });

        this.resetDuplicateData();
    }

    remove(qaModel) {
        this._modalSvc.askForConfirmation('Are you sure you want to remove Q&A Model with version: ' + qaModel.version + '?').first()
            .subscribe(
                () => {
                    this._client.resources.admin.qaModels.qaModelId(qaModel.qaModelId).delete().first()
                        .subscribe(() => {
                            let index = this.qaModels.indexOf(qaModel);
                            this.qaModels.splice(index, 1);
                        });
                }, () => {
                }
            );
    }

    openDuplicate(qaModel) {
        let newDuplicateModel = Object.assign({}, this._newDuplicateModel);
        newDuplicateModel.name = qaModel.name;
        this.duplicateData.qaModel = qaModel;
        this.duplicateData.duplicateQaModel = newDuplicateModel;
        this.duplicateData.isOpen = true;
    }

    resetDuplicateData() {
        this.duplicateData = Object.assign({}, this._newDuplicateData);
    }


}
