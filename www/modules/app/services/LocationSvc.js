// This file was generated from the service scaffold
// Copyright 2016

import {Injectable} from '@angular/core';
import Client from '../../api/lrs.api.v1.generated';
import UserSvc from './UserSvc';
import ObservableProperty from '../../shared/decorators/observableProperty';

/**
 * @example
 * let injector = Injector.resolveAndCreate([LocationSvc]);
 * let locationSvc = new injector.get(LocationSvc);
 * @example
 * class Component {
 * 		constructor(locationSvc:LocationSvc, locationSvc2:LocationSvc) {
 *			//injected via angular, a singleton by default in same injector context
 *			console.log(locationSvc === locationSvc2);
 *		}
 * }
 */
@Injectable()
export default class LocationSvc {

    /**
     * @type {Client}
     * */
    _client: Client;

    /**
     * @type {String}
     * */
    @ObservableProperty()
    hqLocationId: String = '';

    /**
     * @param {Client} client Instance of client services
     * @param {UserSvc} userSvc class
     * */
    constructor(client: Client, userSvc: UserSvc) {
        this._client = client;

        // if the user id changes after we get HQ location we try again
        userSvc.hudIdObservable.first().subscribe(() => {
            this._findHqLocation();
        });

        // try and get location of HQ (if we are logged in)
        if (userSvc.hudId) {
            this._findHqLocation();
        }
    }

    /**
     * Parse locations and locate HQ in order to compare with logged in users' location were applicable
     * @returns {null} no return
     * */
    _findHqLocation() {
        this._client.resources.locations.get().first().subscribe(locations => {
            for (let i = 0; i < locations.length; i++) {
                let location = locations[i];
                if (location.name.toUpperCase().indexOf('HQ') !== -1) {
                    this.hqLocationId = location.locationId;
                    break;
                }
            }
        });
    }

    getName(): string {
        return 'LocationSvc';
    }
}
