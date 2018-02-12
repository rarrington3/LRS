// This file was generated from the view scaffold
// Copyright 2016

import {Component} from '@angular/core';
import {ActivatedRoute}  from '@angular/router';
import {LenderIdPipe} from '../pipes/lenderIdPipe';
import template from './LenderIncreasedSelection.html';
import styles from './LenderIncreasedSelection.less';
import Client from '../../api/lrs.api.v1.generated';
import {SERVER_ERROR} from '../../shared/constants';
import {ModalSvc} from '../../app/services';
import SortableReviewTable from '../../shared/components/SortableReviewTable';

@Component({
    selector: 'lender-increased-selection',
    template: template,
    styles: [styles],
    pipes: [LenderIdPipe]
})
/**
 * @see https://angular.io/docs/ts/latest/guide/router.html
 */
export default class LenderIncreasedSelection {

    /**
     * Establish a client in order to use http services
     * @type {Client}
     * */
    _client:Client;

    /**
     * @type {ActivatedRoute}
     * */
    route:ActivatedRoute;

    /**
     * Service for displaying modals
     * @type {ModalSvc} Service for selection
     * */
    _modalSvc:ModalSvc;

    /**
     * Default name for the class, passed by reference via RouteParams
     */
    name:string = 'LenderSuppression';

    /**
     * Any new subscription will be added into this collection
     * @type {Array}
     */
    subscriptions:Array = [];

    /**
     * This view supports editing lenders and underwriting lenders
     * so the lenderType is passed into the view with param data
     * @type {String}
     */
    lenderType:string = null;

    /**
     * Lender Id used when adding a new lender
     * @type {string}
     */
    addedLenderId:string = null;

    /**
     * Lender collection
     * @type {Array}
     */
    lenders:Array = [];

    /**
     * Lender Id used when adding a new lender
     * @type {string}
     */
    addedLenderId:string = null;

    /**
     * Enumerated collection of properties to sort on
     * @type {Object}
     */
    SORT_PROPS:Object;

    /**
     * Enumerated collection of lender types
     * @type {Object}
     */
    LENDER_TYPES:Object;

    /**
     * @param {Client} client Instance of client services
     * @param {ActivatedRoute} route Active route
     * @param {ModalSvc} modalSvc class
     * */
    constructor(client:Client, route:ActivatedRoute, modalSvc:ModalSvc) {
        this._client = client;
        this.route = route;
        this._modalSvc = modalSvc;
    }

    ngOnInit() {
        this.subscriptions.push(this.route.params.subscribe(params => {
            this.name = params['name'];
        }));

        this.subscriptions.push(this.route.data.subscribe(result => {
            this.lenderType = result['lenderType'];
        }));

        this.LENDER_TYPES = {
            LENDER: 'lender',
            UNDERWRITER: 'underwriter'
        };

        this.SORT_PROPS = {
            ID: 'lender.lenderId',
            NAME: 'lender.name',
            PERCENT_TO_REVIEW: 'percentToReview',
            START_DATE: 'startDate'
        };

        this.loadLenders();
    }


    ngOnDestroy() {
        this.subscriptions.forEach(subscription => {
            subscription.unsubscribe();
        });
    }

    /**
     * @param {string} lenderId of lender searched for
     * @returns {void} no return
     * */
    _displayAddLenderMessage(lenderId) {
        this._modalSvc.acknowledge(lenderId + ' not found.', 'Error').first().subscribe(() => this.addedLenderId = null);
    }

    /**
     * Loads lender list based on the type of view being viewed
     * Eg. Lender or Underwriter
     *
     * @returns {Null} no return
     */
    loadLenders() {
        if (this.lenderType === this.LENDER_TYPES.LENDER) {
            this._client.resources.admin.lenderSelectionIncreases.get().first().subscribe(result => {
                this.lenders = result;
                this.sortLenders(this.SORT_PROPS.START_DATE);
            });
        } else {
            this._client.resources.admin.underwriterSelectionIncreases.get().first().subscribe(result => {
                this.lenders = result;
                this.sortLenders(this.SORT_PROPS.START_DATE);
            });
        }
    }

    /**
     * Sorts lender list by property param
     * @param {prop} prop name to sort on
     * @returns {Null} no return
     */
    sortLenders(prop) {
        this.lenders = SortableReviewTable.sortItems(this.lenders, prop, prop);
    }

    /**
     * Adds lender to list
     * @returns {null} no return
     * */
    addLender() {
        if (this.lenderType === this.LENDER_TYPES.LENDER) {
            this._client.resources.admin.lenderSelectionIncreases.lenderId(this.addedLenderId).post().first().subscribe(() => {
                this._client.resources.admin.lenderSelectionIncreases.get().first().subscribe((result) => this.lenders = result);
                this.addedLenderId = null;
            }, result => {
                // handle failure
                if (result.status === SERVER_ERROR.GONE) {
                    this._displayAddLenderMessage(this.addedLenderId);
                }
            });
        } else {
            this._client.resources.admin.underwriterSelectionIncreases.lenderId(this.addedLenderId).post().first().subscribe(() => {
                this._client.resources.admin.underwriterSelectionIncreases.get().first().subscribe((result) => this.lenders = result);
                this.addedLenderId = null;
            }, result => {
                // handle failure
                if (result.status === SERVER_ERROR.GONE) {
                    this._displayAddLenderMessage(this.addedLenderId);
                }
            });
        }
    }

    /**
     * Removes lender from list
     * @param {string} lenderId of lender to delete
     * @returns {null} no return
     * */
    removeLender(lenderId) {
        if (this.lenderType === this.LENDER_TYPES.LENDER) {
            this._client.resources.admin.lenderSelectionIncreases.lenderId(lenderId).delete().first().subscribe(() => {
                this._client.resources.admin.lenderSelectionIncreases.get().first().subscribe(result => this.lenders = result);
            });
        } else {
            this._client.resources.admin.underwriterSelectionIncreases.lenderId(lenderId).delete().first().subscribe(() => {
                this._client.resources.admin.underwriterSelectionIncreases.get().first().subscribe(result => this.lenders = result);
            });
        }
    }

    /**
     * Changes lender percentage of loans to review
     * @param {api.v1.lender.LiteLender} lender to modify percentage to review
     * @returns {null} no return
     * */
    changePercentage(lender) {
        if (this.lenderType === this.LENDER_TYPES.LENDER) {
            this._client.resources.admin.lenderSelectionIncreases.lenderId(lender.lender.lenderId).put(lender).first().subscribe(() => {
                this._client.resources.admin.lenderSelectionIncreases.get().first().subscribe(result => this.lenders = result);
            });
        } else {
            this._client.resources.admin.underwriterSelectionIncreases.lenderId(lender.lender.lenderId).put(lender).first().subscribe(() => {
                this._client.resources.admin.underwriterSelectionIncreases.get().first().subscribe(result => this.lenders = result);
            });
        }

    }
}
