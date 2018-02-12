// This file was generated from the view scaffold
// Copyright 2016

import {Component} from '@angular/core';
import {LenderIdPipe} from '../pipes/lenderIdPipe';
import {ActivatedRoute}  from '@angular/router';
import template from './LenderSuppression.html';
import styles from './LenderSuppression.less';
import Client from '../../api/lrs.api.v1.generated';
import {SERVER_ERROR} from '../../shared/constants';
import {ModalSvc} from '../../app/services';
import SortableReviewTable from '../../shared/components/SortableReviewTable';

@Component({
    selector: 'lender-suppression',
    template: template,
    styles: [styles],
    pipes: [LenderIdPipe]
})
/**
 * @see https://angular.io/docs/ts/latest/guide/router.html
 */
export default class LenderSuppression {

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
    _modalSvc: ModalSvc;

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
     * Lender collection
     * @type {Array}
     */
    lenders:Array = [];

    /**
     * Lender Id used when adding a new lender
     * @type {string}
     */
    addedLenderId: string = null;

    /**
     * Enumerated collection of properties to sort on
     * @type {Object}
     */
    SORT_PROPS:Object;


    /**
     * @param {Client} client Instance of client services
     * @param {ActivatedRoute} route Active route
     * @param {ModalSvc} modalSvc class
     * */
    constructor(client:Client, route:ActivatedRoute, modalSvc: ModalSvc) {
        this._client = client;
        this.route = route;
        this._modalSvc = modalSvc;
    }

    ngOnInit() {
        this.subscriptions.push(this.route.params.subscribe(params => {
            this.name = params['name'];
        }));

        this.SORT_PROPS = {
            ID: 'lender.lenderId',
            NAME: 'lender.name',
            ACTIVE_REVIEWS: 'activeReviews',
            START_DATE: 'startDate'
        };

        this._client.resources.admin.lenderSuppressions.get().first().subscribe(result => {
            this.lenders = result;
            this.sortLenders(this.SORT_PROPS.START_DATE);
        });
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
        this._client.resources.admin.lenderSuppressions.lenderId(this.addedLenderId).post().first().subscribe(() => {
            this._client.resources.admin.lenderSuppressions.get().first().subscribe((result) => {
                this.lenders = result;
                this.sortLenders(this.SORT_PROPS.START_DATE);
            });
            this.addedLenderId = null;
        }, result => {
            // handle failure
            if (result.status === SERVER_ERROR.GONE) {
                this._displayAddLenderMessage(this.addedLenderId);
            }
        });
    }

    /**
     * Removes lender from list
     * @param {string} lenderId of lender to delete
     * @returns {null} no return
     * */
    removeLender(lenderId) {
        this._client.resources.admin.lenderSuppressions.lenderId(lenderId).delete().first().subscribe(() => {
            this._client.resources.admin.lenderSuppressions.get().first().subscribe(result => {
                this.lenders = result;
                this.sortLenders(this.SORT_PROPS.START_DATE);
            });
        });
    }
}
