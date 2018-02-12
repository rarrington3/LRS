// This file was generated from the component scaffold
// Copyright 2016

import {Router} from '@angular/router';
import {Component} from '@angular/core';
import template from './login.html';
import styles from './login.less';
import LrsApiV1 from '../../api/lrs.api.v1.generated';
import ModalSvc from '../../app/services/ModalSvc';
import UserSvc from '../../app/services/UserSvc';
const compiledConfig = require('config');

@Component({
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/api/core/Component-decorator.html
 */
export default class Login {
    api:LrsApiV1;
    router:Router;
    modalSvc:ModalSvc;
    _subscriptions:Array = [];

    constructor(api:LrsApiV1, router:Router, modalSvc:ModalSvc, userSvc:UserSvc) {
        this.router = router;
        this.api = api;
        this.modalSvc = modalSvc;
        this.userSvc = userSvc;
    }

    ngOnInit() {
        this.api.resources.devLogin.get().first().subscribe( accounts => this.accounts = accounts );
    }

    ngOnDestroy() {
        this._subscriptions.forEach((subscription) => {
            subscription.unsubscribe();
        });
    }

    loginAs(user:Object) {

        // Open a modal to confirm before continuing.
        this.modalSvc.askForConfirmation(
            `<h4>
                Are you sure you want to use <span class="text-info">${user.userId}</span>?
            </h4>`
        ).first().subscribe(
            () => {
                return this.api.resources.devLogin.post({userId:user.userId}).first().subscribe(() => {
                    console.log(`Fake logged in as ${user.userId}`);

                    // If we're running on mocks...
                    if (compiledConfig.settings.mocks){

                        // Save the requested user data to local storage where it will be read by the preloader.
                        window.localStorage.setItem('user', JSON.stringify(user));
                    }

                    return document.location.reload();
                });
            },
            answer => answer
        );
    }

    clearLogin() {
        // Open a modal to confirm before continuing.
        this.modalSvc.askForConfirmation(
            `<h4>
                Are you sure you want to clear your current user selection?
            </h4>`
        ).first().subscribe(
            () => {
                window.localStorage.removeItem('user');
                return document.location.reload();
            },
            answer => answer
        );
    }
}