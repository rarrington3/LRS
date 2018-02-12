// This file was generated from the view scaffold
// Copyright 2016

import {Component} from '@angular/core';
import {ActivatedRoute}  from '@angular/router';
import Client from '../../api/lrs.api.v1.generated';
import {ModalSvc} from '../../app/services';
import template from './EditReviewLocation.html';
import styles from './EditReviewLocation.less';

@Component({
    selector: 'edit-review-location',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/guide/router.html
 */
export default class EditReviewLocation {
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
     * Default name for the class, passed by reference via RouteParams
     */
    name: string = 'EditReviewLocation';

    /**
     * Service for displaying modals
     * @type {ModalSvc} Service for selection
     * */
    _modalSvc: ModalSvc;

    /**
     * Any new subscription will be added into this collection
     * @type {Array}
     */
    subscriptions: Array = [];

    /**
     * Default name for the class, passed by reference via RouteParams
     */
    locationId: string;

    /**
     * If editing or adding a new location
     * @type {api.v1.Location}
     * */
    locationToEdit: Object = {};

    /**
     * @type {Array}
     * */
    selectionReasons: Array = [];

    /**
     * @type {Array}
     * */
    reviewTypes: Array = [];

    /**
     * @type {Array}
     * */
    productTypes: Array = [];

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
     * Get locations to possibly update.
     * Get states to assign to locations.
     * Get selection reasons, review types, and product types to assign to locations.
     * @returns {null} no return
     * */
    ngOnInit() {
        this.subscriptions.push(this.route.params.subscribe(params => {
            this.name = params['name'];
        }));

        this.locationId = this.route.snapshot.params['locationId'];

        this._client.resources.locations.id(this.locationId).get().first()
            .subscribe(result => {
                this.locationToEdit = result;
            });

        this._client.resources.dictionary.selectionReasons.get().first().subscribe(result => this.selectionReasons = result);
        this._client.resources.dictionary.reviewTypes.get().first().subscribe(result => this.reviewTypes = result);
        this._client.resources.dictionary.productTypes.get().first().subscribe(result => this.productTypes = result);
    }

    ngOnDestroy() {
        this.subscriptions.forEach(subscription => {
            subscription.unsubscribe();
        });
    }

    /**
     * Save or update location
     * @returns {null} no return
     * */
    saveLocation() {

        // create some scoped variables.
        let self = this,
            actionTxt;

        // define a function to handle the results.
        function handleResults(result) {
            self.locationToEdit = result;
            return self._modalSvc.acknowledge(`${result.name} was successfully ${actionTxt}.`, 'SAVE SUCCESSFUL')
                .first()
                .subscribe(
                    () => {
                    }, () => {
                    }
                );
        }

        // if the location is new do a POST call...
        if (!this.locationToEdit.locationId) {
            actionTxt = 'created';
            this._client.resources.locations
                .post(this.locationToEdit)
                .first()
                .subscribe(handleResults);
        }
        // ... otherwise, do a PUT with the ID.
        else {
            actionTxt = 'updated';
            this._client.resources.locations
                .id(this.locationToEdit.locationId)
                .put(this.locationToEdit)
                .first()
                .subscribe(handleResults);
        }
    }
}
