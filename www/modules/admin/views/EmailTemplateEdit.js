// This file was generated from the view scaffold
// Copyright 2017

import {Component} from '@angular/core';
import {ActivatedRoute}  from '@angular/router';
import template from './EmailTemplateEdit.html';
import styles from './EmailTemplateEdit.less';
import Client from '../../api/lrs.api.v1.generated';

@Component({
    selector: 'email-template-edit',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/guide/router.html
 */
export default class EmailTemplateEdit {

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
    name:string = 'EmailTemplateEdit';

    /**
     * Any new subscription will be added into this collection
     * @type {Array}
     */
    subscriptions:Array = [];

    /**
     * Template Id for the template to edit, passed by reference via RouteParams
     */
    templateId:string = '';

    /**
     * @type {Object} selectedTemplate - api.v1.admin.EmailTemplate
     * */
    selectedTemplate:Object;

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
            if (params['templateId']) {
                this.templateId = params['templateId'];
                this._loadEmailTemplate(this.templateId);
            }
        }));
    }

    ngOnDestroy() {
        this.subscriptions.forEach(subscription => {
            subscription.unsubscribe();
        });
    }

    /**
     * Load email template to edit
     * @param {String} templateId - id of template to fetch
     * @return {null} no return
     * */
    _loadEmailTemplate(templateId) {
        this._client.resources.admin.emails.templateId(templateId).get().first().subscribe(result => {
            this.selectedTemplate = result;
        });
    }
}
