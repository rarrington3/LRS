// This file was generated from the view scaffold
// Copyright 2017

import {Component} from '@angular/core';
import {ActivatedRoute}  from '@angular/router';
import template from './EmailTemplateList.html';
import styles from './EmailTemplateList.less';
import Client from '../../api/lrs.api.v1.generated';

@Component({
    selector: 'email-template-list',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/guide/router.html
 */
export default class EmailTemplateList {

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
    name:string = 'EmailTemplateList';

    /**
     * Any new subscriptions will be added into this collection
     * @type {Array}
     */
    subscriptions:Array = [];

    /**
     * @type {Array} emailGroups is a collection of email templates grouped by category
     * */
    emailGroups:Array;

    /**
     * @param {Client} client Instance of client services
     * @param {ActivatedRoute} route Active route
     * */
    constructor(client: Client, route:ActivatedRoute) {
        this._client = client;
        this.route = route;
    }

    ngOnInit() {
        this.subscriptions.push(this.route.params.subscribe(params => {
            this.name = params['name'];
        }));

        // Make an AJAX call for the email templates.
        this._loadEmailTemplates();
    }


    /**
     * Load email templates and display them grouped by category
     * @return {null} no return
     * */
    _loadEmailTemplates() {
        this._client.resources.admin.emails.get().first().subscribe(result => {
            this.emailGroups = result;
        });
    }

    ngOnDestroy() {
        this.subscriptions.forEach(subscription => {
            subscription.unsubscribe();
        });
    }

    /**
     * Duplicates template and reloads the list of templates.
     *
     * @param {template} template - api.v1.admin.EmailTemplate
     * @returns {null} no return
     * */
    duplicateTemplate(template) {
        this._client.resources.admin.emails.templateId(template.id).duplicate.put().first().subscribe(() => {
            this._loadEmailTemplates();
        });
    }
}
