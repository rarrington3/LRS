// This file was generated from the view scaffold
// Copyright 2016

import {Component} from '@angular/core';
import {ActivatedRoute}  from '@angular/router';
import template from './ModelAdminList.html';
import styles from './ModelAdminList.less';
import Client from '../../api/lrs.api.v1.generated';
import {ModalSvc} from '../../app/services';
import {SERVER_ERROR} from '../../shared/constants';
import Utils from '../../shared/Utils';
import _ from 'lodash';

@Component({
    selector: 'model-admin-list',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/guide/router.html
 */
export default class ModelAdminList {
    /**
     * Establish a client in order to use http services
     * @type {Client}
     * */
    _client: Client;

    _modalSvc: ModalSvc;

    /**
     * @type {ActivatedRoute}
     * */
    route: ActivatedRoute;

    /**
     * Default name for the class, passed by reference via RouteParams
     */
    name: string = 'ModelAdminList';

    /**
     * Any new subscription will be added into this collection
     * @type {Array}
     */
    subscriptions: Array = [];

    /**
     * Type of model being managed, passed by reference via RouteParams
     * @type {string}
     */
    adminModelType: string;

    /**
     * Service API endpoint depending on the type of model being managed
     * @type {Array}
     */
    adminModelService: Object;

    /**
     * Categories to choose from when looking up models by category
     * @type {Array}
     */
    adminModelCategories: Array = null;

    /**
     * Category to use when grouping models together.
     * Eg: selectionModelCategory, assignmentModelCategory, or DistributionModelCategory
     * @type {Array}
     */
    adminModelCategory: string = null;

    /**
     * Categories to choose from once the model type is established
     * @type {Array}
     */
    modelCategories: Array = null;

    /**
     * An enumeration of model types depending on the type of model being managed
     * @type {Object}
     * */
    MODEL_TYPES: Object;

    /**
     * An enumeration of statuses for selection models to group by after category
     * @type {Object}
     * */
    MODEL_STATUSES: Object;

    /**
     * Data structure for selectionModelMenu element
     * @type {Array}
     */
    modelData: Array = null;

    /**
     * List of menu and child menu items for the selectionModelMenu element
     * @type {Array}
     */
    modelItems: Array = null;

    /**
     * Reference for inline modal to be displayed or not
     * @type {Boolean}
     */
    copyModelOpen = false;

    /**
     * Reference of selection model to be duplicated when modal is shown
     * @type {Boolean}
     */
    modelToDuplicate: Object = {};

    /**
     * @param {Client} client Instance of client services
     * @param {ActivatedRoute} route Active route
     * @param {ModalSvc} modalSvc Modal Service
     * */
    constructor(client: Client, route: ActivatedRoute, modalSvc: ModalSvc) {
        this._client = client;
        this._modalSvc = modalSvc;
        this.route = route;
    }

    /**
     * @returns {null} no return
     * */
    ngOnInit() {
        this.subscriptions.push(this.route.params.subscribe(params => {
            this.name = params['name'];
        }));

        this.subscriptions.push(this.route.data.subscribe(result => {
            this.adminModelType = result['modelType'];
        }));

        this.MODEL_TYPES = {
            SELECTION: 'selection',
            DISTRIBUTION: 'distribution',
            ASSIGNMENT: 'assignment'
        };

        this.MODEL_STATUSES = {
            ACTIVE: 'active',
            DRAFT: 'draft',
            ARCHIVED: 'archived'
        };


        this._setServiceAndCategories();

    }

    /**
     * Set up model API service and categories based on the type of model being managed:
     *
     * api.v1.SelectionModel, or api.v1.DistributionModel, od api.v1.AssignmentModel
     *
     * Load the models once we know the categories.
     * @returns {null} no return
     * */
    _setServiceAndCategories() {
        switch (this.adminModelType) {
            case this.MODEL_TYPES.SELECTION:
                this.adminModelService = this._client.resources.admin.selectionModels;
                this.adminModelCategory = 'selectionModelCategory';
                this.adminModelCategories = this._client.resources.dictionary.selectionModelCategories;
                break;
            case this.MODEL_TYPES.DISTRIBUTION:
                this.adminModelService = this._client.resources.admin.distributionModels;
                this.adminModelCategory = 'distributionModelCategory';
                this.adminModelCategories = this._client.resources.dictionary.distributionModelCategories;
                break;
            case this.MODEL_TYPES.ASSIGNMENT:
                this.adminModelService = this._client.resources.admin.assignmentModels;
                this.adminModelCategory = 'assignmentModelCategory';
                this.adminModelCategories = this._client.resources.dictionary.assignmentModelCategories;
                break;
            default:
                break;
        }

        this.adminModelCategories.get().first().subscribe(result => {
            this.modelCategories = result;
            this._loadModels();
        });
    }

    /**
     * Fetch data for model lists to display.
     * 1. Group elements by this.adminModelCategory then sub-group by model category with version number.
     * 2. Group each sup-group by 'Active', 'Draft', 'Archived' status.
     *
     * @returns {null} no return
     * */
    _loadModels() {
        this.adminModelService.get().first().subscribe(result => {
            let cat = this.adminModelCategory;
            this.modelData = result;
            this.modelItems = [];
            let uniqModels = _.uniq(this.modelData, function (model) {
                return model[cat];
            });

            let active,
                drafts,
                archived;

            uniqModels.forEach(model => {
                active = this._findActiveModel(model);
                drafts = this._findDraftModels(model);
                archived = this._findArchivedModels(model);

                this.modelItems.push({
                    label: this._lookUpCategoryName(model),
                    active: active,
                    drafts: drafts,
                    archived: archived
                });
            });
        });
    }

    /**
     * Simple helper look up category display name by category code
     *
     * @param {Object} model is a selection model json blob
     * @returns {string} friendly name of model category
     * */
    _lookUpCategoryName(model) {
        let cat = this.modelCategories.find(cat => cat.code === model[this.adminModelCategory]);

        return cat ? cat.description : null;
    }

    /**
     * Simple helper look up models who share the same this.adminModelCategory and are in active status
     * We then check for a display name and if not we create one based on the category name and version
     * Note: There will always only be on model set to active status
     *
     * @param {Object} model is a selection model json blob
     * @returns {Object} model
     * */
    _findActiveModel(model) {
        let cat = this.adminModelCategory;
        let items = this.modelData.filter(item => item[cat] === model[cat] && item.status === this.MODEL_STATUSES.ACTIVE);
        items.forEach(item => {
            item.label = item.name ? item.name : this._lookUpCategoryName(item) + ' v' + item.version;
        });

        return items;
    }

    /**
     * Simple helper look up models who share the same this.adminModelCategory and are in draft status
     * We then check for a display name and if not we create one based on the category name and version
     *
     * @param {Object} model is a selection model json blob
     * @returns {Array} group of models
     * */
    _findDraftModels(model) {
        let cat = this.adminModelCategory;
        let items = this.modelData.filter(item => item[cat] === model[cat] && item.status === this.MODEL_STATUSES.DRAFT);
        items.forEach(item => {
            item.label = item.name ? item.name : this._lookUpCategoryName(item) + ' v' + item.version;
        });

        return items;
    }

    /**
     * Simple helper look up models who share the same this.adminModelCategory and are in archived status
     * We then check for a display name and if not we create one based on the category name and version
     *
     * @param {Object} model is a selection model json blob
     * @returns {Array} group of models
     * */
    _findArchivedModels(model) {
        let cat = this.adminModelCategory;
        let items = this.modelData.filter(item => item[cat] === model[cat] && item.status === this.MODEL_STATUSES.ARCHIVED);
        items.forEach(item => {
            item.label = item.name ? item.name : this._lookUpCategoryName(item) + ' v' + item.version;
        });

        return items;
    }

    ngOnDestroy() {
        this.subscriptions.forEach(subscription => {
            subscription.unsubscribe();
        });
    }

    /**
     * Simple helper build a display name for the model based on version
     *
     * @param {Object} model is a model json blob
     * @returns {string} friendly name
     * */
    getModelName(model) {
        return model.name ? model.name : this._lookUpCategoryName(model) + ' v' + model.version;
    }

    /**
     * Deletes model and reloads the list of models.
     *
     * @param {Object} model to delete
     * @returns {null} no return
     * */
    deleteModel(model) {
        this.adminModelService.id(model.id).delete().first().subscribe(() => {
            this._loadModels();
        });
    }

    /**
     * Displays model duplicate popup.
     * @param {Object} model to duplicate
     * @returns {null} no return
     * */
    displayCopyModal(model) {
        this.copyModelOpen = true;
        this.modelToDuplicate = {
            id: model.id,
            name: null,
            defaultReviewType: model.defaultReviewType
        };
        this.placeholderName = this.getModelName(model);
    }

    /**
     * Duplicates model and reloads the list of models.
     * @returns {null} no return
     * */
    duplicateModel() {
        this.adminModelService
            .id(this.modelToDuplicate.id)
            .duplicate.put(
            {
                name: this.modelToDuplicate.name,
                defaultReviewType: this.modelToDuplicate.defaultReviewType
            })
            .first()
            .subscribe(() => {
                this.copyModelOpen = false;
                this._loadModels();
            }, error => {
                if (error.status === SERVER_ERROR.BAD_REQUEST) {
                    let errorMessage;
                    let errorMessages = Utils.getErrorMessages(error);
                    if (errorMessages && errorMessages.length) {
                        errorMessage = errorMessages[0];
                    } else {
                        errorMessage = 'Cannot duplicate the model.';
                    }
                    this._modalSvc.acknowledge(errorMessage, 'Error').first().subscribe();
                } else {
                    throw error;
                }
            });
    }
}
