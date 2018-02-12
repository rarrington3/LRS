// This file was generated from the view scaffold
// Copyright 2016

import {Component} from '@angular/core';
import {ActivatedRoute}  from '@angular/router';
import {ModalSvc} from '../../app/services';
import template from './ModelAdminEdit.html';
import styles from './ModelAdminEdit.less';
import Client from '../../api/lrs.api.v1.generated';
import {SERVER_ERROR} from '../../shared/constants';
import Utils from '../../shared/Utils';
import _ from 'lodash';

@Component({
    selector: 'model-admin-edit',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/guide/router.html
 */
export default class ModelAdminEdit {

    /**
     * Establish a client in order to use http services
     * @type {Client}
     * */
    _client: Client;

    /**
     * @type {ActivatedRoute}
     * */
    route: ActivatedRoute;

    /**
     * Service for displaying modals
     * @type {ModalSvc} Service for selection
     * */
    _modalSvc: ModalSvc;

    /**
     * Default name for the class, passed by reference via RouteParams
     */
    name: string = 'EditSelectionModel';

    /**
     * Any new subscription will be added into this collection
     * @type {Array}
     */
    subscriptions: Array = [];

    /**
     * Service API endpoint depending on the type of model being managed
     * @type {Array}
     */
    adminModelService: Object;

    /**
     * Type of model being managed, passed by reference via RouteParams
     * @type {string}
     */
    adminModelType: string;

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
     * An enumeration of statuses for selection models to group by after modelCategory
     * @type {Object}
     * */
    MODEL_STATUSES: Object;

    /**
     * An enumeration of scopes for selection models
     * @type {Object}
     * */
    MODEL_SCOPE_CODE: Object;

    /**
     * Factors to choose from when editing selection models
     * @type {Array}
     */
    modelFactorSource: Array = null;

    /**
     * Factors to display after sorting when editing selection models
     * @type {Array}
     */
    modelFactors: Array = null;

    /**
     * Selected factor id in factor drop down. Auto added to selected selection model factors when item is selected.
     * @type {string}
     * */
    selectedModelFactorId: string = '';

    /**
     * Review types to choose from when editing selection models
     * @type {Array}
     */
    reviewTypes: Array = null;

    /**
     * ID of selection model being edited -  passed by reference via RouteParams
     */
    modelId: string;

    /**
     * If editing or copying a selection model
     * Ex: api.v1.SelectionModel, or api.v1.DistributionModel, od api.v1.AssignmentModel
     *
     * @type {Object}
     * */
    selectedModel: Object = {};

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
     * All changes trigger a save but when activating we'll display a modal to confirm
     * @type {Boolean}
     * */
    _isActivating: Boolean = false;

    /**
     * @param {Client} client Instance of client services
     * @param {ActivatedRoute} route Active route
     * @param {ModalSvc} modalSvc class
     * */
    constructor(client: Client, route: ActivatedRoute, modalSvc: ModalSvc) {
        this._client = client;
        this.route = route;
        this._modalSvc = modalSvc;
    }

    /**
     * Establish service end point based on model
     * Load factors
     * Load review types
     * Fetch model to manage
     *
     * @returns {null} no return
     * */
    ngOnInit() {
        this.subscriptions.push(this.route.params.subscribe(params => {
            this.name = params['name'];
            this.adminModelType = params['modelType'];
            this.modelId = params['modelId'];
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

        this.MODEL_SCOPE_CODE = {
            FULL: 'F',
            LIMITED: 'L'
        };

        this._setServiceForModelType();


        this._client.resources.dictionary.factors.get().first().subscribe(result => {
            this.modelFactorSource = result;
        });

        this._client.resources.dictionary.reviewTypes.get().first().subscribe(reviewTypes => {
            this.reviewTypes = reviewTypes;
        });

        if (this.modelId) {
            this.adminModelService.id(this.modelId).get().first().subscribe(result => {
                this.selectedModel = result;
                // for display only
                this.selectedModel.label = this.selectedModel.name ? this.selectedModel.name : this._lookUpCategoryName(this.selectedModel) + ' v' + this.selectedModel.version;
                // filter duplicate model factors so they can't be added twice
                this._filterModelFactors();
            });
        }
    }

    ngOnDestroy() {
        this.subscriptions.forEach(subscription => {
            subscription.unsubscribe();
        });
    }

    /**
     * Filters model factors so that no factor can be used more than once on a model
     * @returns {null} no return
     * */
    _filterModelFactors() {
        let newFactors = [];

        if (this.selectedModel && this.modelFactorSource) {
            this.modelFactorSource.forEach((factor) => {
                if (!this.selectedModel.factors.some(baseFactor => factor.id === baseFactor.id)) {
                    newFactors.push(factor);
                }
            });
        }

        this.modelFactors = newFactors;

    }

    /**
     * Set up model API service based on the type of model being managed:
     *
     * api.v1.SelectionModel, or api.v1.DistributionModel, od api.v1.AssignmentModel

     * @returns {null} no return
     * */
    _setServiceForModelType() {
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
        });
    }

    /**
     * Simple helper look up category display name by category code
     *
     * @param {Object} model is a selection model json blob
     * @returns {string} friendly name of model category
     * */
    _lookUpCategoryName(model) {
        let adminCat = this.adminModelCategory;
        let cat = this.modelCategories.find(cat => cat.code === model[adminCat]);

        return cat ? cat.description : null;
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
     * Simple helper build a display name for the model based on version
     *
     * @param {Object} model is a model json blob
     * @returns {string} friendly name
     * */
    getSelectionModelName(model) {
        return model.name ? model.name : this._lookUpCategoryName(model) + ' v' + model.version;
    }

    /**
     * Deletes model and reloads the list of models.
     *
     * @returns {null} no return
     * */
    deleteModel() {
        this.adminModelService.id(this.selectedModel.id).delete().first().subscribe(() => {
            //TODO: go back to model view list?
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
     * Duplicates model and closes the modal.
     * @returns {null} no return
     * */
    duplicateModel() {
        this.adminModelService
            .id(this.modelToDuplicate.id)
            .duplicate.put(
            {
                name: this.modelToDuplicate.name,
                defaultReviewType: this.modelToDuplicate.defaultReviewType
            }).first()
            .subscribe(result => {
                // Copy in the new name they entered in the duplicate modal.
                result.name = this.modelToDuplicate.name;
                result.defaultReviewType = this.modelToDuplicate.defaultReviewType;
                this.selectedModel = result;
                this.copyModelOpen = false;
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

    /**
     * Handles the delete factor buttons
     * Find factor in selectedModel.factors and delete it then auto save
     * @param {factorId} factorId is id of object ro remove
     * @returns {null} no return
     * */
    deleteFactor(factorId) {
        let oldFactor = this.selectedModel.factors.find(factor => factor.id === factorId);
        let idx = this.selectedModel.factors.indexOf(oldFactor);
        this.selectedModel.factors.splice(idx, 1);

        this._filterModelFactors();

        this.saveChanges();
    }

    /**
     * Handles the 'Add model factor' button next to drop down in UI
     * Find factor in dictionary and add it to the selected model's factor list then clear reference to added factor
     *
     * @returns {null} no return
     * */
    addModelFactor() {
        let newFactor = this.modelFactors.find(factor => factor.id === this.selectedModelFactorId);

        if (newFactor) {
            newFactor.weight = 0;
            this.selectedModel.factors.push(newFactor);
            this._filterModelFactors();
        }
    }

    /**
     * Sets the current model status to active and confirms transaction.
     *
     * @returns {null} no return
     * */
    activateModel() {
        this.selectedModel.status = this.MODEL_STATUSES.ACTIVE;
        this._isActivating = true;
        this.saveChanges();
    }

    /**
     * All changes auto save when the selected model is adjusted. If the model's stus is changing
     * we display a pop up.
     *
     * @returns {null} no return
     * */
    saveChanges() {

        // Ensure model default values are populated without mutating the UI-bound object.
        let requestBody = _.defaults(
            _.clone(this.selectedModel),
            {
                selectionThreshold: 0,
                allocationPercentage: 0,
                status: 'draft',
                scope: 'limited'
            }
        );

        // Make the http request.
        this.adminModelService.id(this.selectedModel.id).put(requestBody).first().subscribe(() => {
            if (this._isActivating) {
                let modelName = this.selectedModel ? this.selectedModel.name : this.selectedModel.label;
                this._modalSvc.acknowledge(modelName + ' is now active.', 'Changes Saved').first()
                    .subscribe(
                        () => {
                            this._isActivating = false;
                        }
                    );
            }

        });
    }

    /**
     * Check for valid number and min/max weight before saving.
     *
     * @param {Object} factor - api.v1.admin.DistributionModel
     * @returns {null} no return
     * */
    checkWeight(factor) {
        if (!_.isNumber(factor.weight)) {
            factor.weight = 0;
        } else if (factor.weight > 99999) {
            factor.weight = 99999;
        } else if (factor.weight < -99999) {
            factor.weight = -99999;
        }

        this.saveChanges();
    }
}
