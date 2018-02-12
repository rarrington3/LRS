// This file was generated from the view scaffold
// Copyright 2016

import {EventEmitter} from '@angular/core';
import {ActivatedRoute}  from '@angular/router';
import Client from '../../api/lrs.api.v1.generated';
import {SaveBarSvc, ModalSvc} from '../../app/services';
import _ from 'lodash';
import ArrayUtils from '../../shared/utils/ArrayUtils';

export default class QaModelDefectAreaBase {

    _sortBy = 'order';

    newOrderedItem = {
        code: '',
        description: '',
        order: 0
    };

    route: ActivatedRoute;
    client: Client;
    saveBarSvc: SaveBarSvc;
    modalSvc: ModalSvc;

    /**
     * Any new subscription will be added into this collection
     * @type {Array}
     */
    subscriptions: Array = [];

    qaModelId;

    defectArea = {
        loanAttributes: [],
        sources: [],
        causes: [],
        questions: [],
        remediationTypes: [],
        preQualifyQuestion: {
            optionsQuestions: []
        }
    };

    defectAreaLoading: boolean = false;

    defectAreaLoaded: EventEmitter = new EventEmitter();

    constructor(route: ActivatedRoute, client: Client, saveBarSvc: SaveBarSvc, modalSvc: ModalSvc) {
        this.route = route;
        this.client = client;
        this.saveBarSvc = saveBarSvc;
        this.modalSvc = modalSvc;
    }

    init() {
        this.subscriptions.push(this.route.parent.params.subscribe(params => {
            this.qaModelId = params['qaModelId'];
            let defectAreaId = params['defectAreaId'];
            if (this.qaModelId && defectAreaId) {
                this.defectAreaLoading = true;
                this.client.resources.admin.qaModels.qaModelId(this.qaModelId).defectAreas.defectAreaId(defectAreaId).get().first()
                    .map(defectArea => this._map(defectArea))
                    .subscribe(defectArea => {
                        this.defectArea = defectArea;
                        this.defectAreaLoading = false;
                        this.defectAreaLoaded.emit(defectArea);
                    });
            }
        }));
    }

    destroy() {
        this.subscriptions.forEach(subscription => {
            subscription.unsubscribe();
        });
    }

    load() {
        this.client.resources.admin.qaModels.qaModelId(this.qaModelId).defectAreas.defectAreaId(this.defectArea.defectAreaId).get().first()
            .map(defectArea => this._map(defectArea))
            .subscribe(defectArea => {
                this.defectArea = defectArea;
                this.defectAreaLoaded.emit(defectArea);
            });
    }

    save() {
        this.client.resources.admin.qaModels.qaModelId(this.qaModelId).defectAreas.defectAreaId(this.defectArea.defectAreaId).put(this.defectArea).first()
            .map(defectArea => this._map(defectArea))
            .subscribe(defectArea => {
                this.defectArea = defectArea;
                this.defectAreaLoaded.emit(defectArea);
            });
    }

    askForSave() {
        if (!this.saveBarSvc.isOpen()) { // if it is already open we don't need to ask for save
            this.saveBarSvc.askForSave()
                .subscribe(
                    () => {
                        this.save();
                    }, () => {
                        this.load();
                    }
                );
        }
    }

    sortUp(items: Array, item: any) {
        if (ArrayUtils.sortUp(items, item, this._sortBy)) {
            this.askForSave();
        }
    }

    sortDown(items: Array, item: any) {
        if (ArrayUtils.sortDown(items, item, this._sortBy)) {
            this.askForSave();
        }
    }

    remove(items: Array, item: any, message: string) {
        message = message || 'Are you sure you want to remove the item?';
        this.modalSvc.askForConfirmation(message).first()
            .subscribe(
                () => {
                    ArrayUtils.remove(items, item);
                    this.askForSave();
                }, () => {
                }
            );
    }

    add(items: Array, newItemTemplate: any, sortBy: string): any {
        sortBy = sortBy || this._sortBy;
        let item = ArrayUtils.add(items, newItemTemplate, sortBy);
        this.askForSave();
        return item;
    }

    _map(defectArea) {
        let sortBy = this._sortBy;
        let mapQuestion = question => {
            if (question.questions) {
                question.questions = _.sortBy(question.questions, sortBy);
                question.questions.forEach(question => {
                    mapQuestion(question);
                });
            }
            if (!question.allowableSourceCodes) {
                question.allowableSourceCodes = [];
            }
            if (!question.allowableCauseCodes) {
                question.allowableCauseCodes = [];
            }
        };

        defectArea.loanAttributes = _.sortBy(defectArea.loanAttributes, sortBy);
        defectArea.sources = _.sortBy(defectArea.sources, sortBy);
        defectArea.causes = _.sortBy(defectArea.causes, sortBy);
        defectArea.questions = _.sortBy(defectArea.questions, sortBy);
        defectArea.questions.forEach(question => {
            mapQuestion(question);
        });
        defectArea.severities = _.sortBy(defectArea.severities, sortBy);
        defectArea.remediationTypes = _.sortBy(defectArea.remediationTypes, sortBy);
        defectArea.preQualifyQuestion = defectArea.preQualifyQuestion || {optionsQuestions: []};
        defectArea.preQualifyQuestion.optionsQuestions = _.sortBy(defectArea.preQualifyQuestion.optionsQuestions, option => parseInt(option.code, 10));

        return defectArea;
    }
}
