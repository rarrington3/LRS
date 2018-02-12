// This file was generated from the view scaffold
// Copyright 2016

import {Component} from '@angular/core';
import Client from '../../api/lrs.api.v1.generated';
import {ActivatedRoute}  from '@angular/router';
import template from './ReviewLocations.html';
import styles from './ReviewLocations.less';

@Component({
    selector: 'review-locations',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/guide/router.html
 */
export default class ReviewLocations {

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
     * Default name for the class, passed by reference via RouteParams
     */
    name:string = 'ReviewLocations';

    /**
     * Any new subscription will be added into this collection
     * @type {Array}
     */
    subscriptions:Array = [];

    /**
     * Locations collection to add/edit
     * @type {Array}
     */
    reviewLocations:Array = [];

    /**
     * @param {Client} client Instance of client services
     * @param {ActivatedRoute} route Active route
     * */
    constructor(client:Client, route:ActivatedRoute) {
        this._client = client;
        this.route = route;
    }

    ngOnInit() {
        this.subscriptions.push(this.route.params.subscribe(params => {
            this.name = params['name'];
        }));

        this._client.resources.locations.get().first().subscribe(locations => {
            this.reviewLocations = locations;
        });
    }

    ngOnDestroy() {
        this.subscriptions.forEach(subscription => {
            subscription.unsubscribe();
        });
    }
}
