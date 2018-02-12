// This file was generated from the service scaffold
// Copyright 2016

import {Injectable} from '@angular/core';
import _ from 'lodash';
let preloader = require('../../preloader/main');
import ObservableProperty from '../../shared/decorators/observableProperty';

/**
 * @example
 * let injector = Injector.resolveAndCreate([UserSvc]);
 * let userSvc = new injector.get(UserSvc);
 * @example
 * class Component {
 *         constructor(userSvc:UserSvc, userSvc2:UserSvc) {
 *            //injected via angular, a singleton by default in same injector context
 *            console.log(userSvc === userSvc2);
 *        }
 * }
 */
@Injectable()
export default class UserSvc {

    @ObservableProperty()
    personnelId: String = null;

    @ObservableProperty()
    hudId: String = null;

    constructor() {

        // Specifically set the IDs to appease jshint.
        this.personnelId = (preloader.currentUser) ? preloader.currentUser.personnelId : null;
        this.hudId = (preloader.currentUser) ? preloader.currentUser.hudId : null;

        // Copy all of the current user's info from the preloader into this Angular2 Service.
        _.forEach(preloader.currentUser, (value, propertyName) => {
            this[propertyName] = value;
        });
    }
}
