// This file was generated from the view scaffold
// Copyright 2016

import {Component} from '@angular/core';
import {ActivatedRoute, Router, NavigationEnd}  from '@angular/router';
import template from './QaModelDefectArea.html';
import styles from './QaModelDefectArea.less';
import Client from '../../api/lrs.api.v1.generated';
import {QA_MODEL_STATUS_CODE} from '../constants';

@Component({
    selector: 'qa-model-defect-area',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/guide/router.html
 */
export default class QaModelDefectArea {

    _route: ActivatedRoute;
    _router: Router;
    _client: Client;

    /**
     * Default name for the class, passed by reference via RouteParams
     */
    name: string = 'QaModelDefectArea';

    /**
     * Any new subscription will be added into this collection
     * @type {Array}
     */
    subscriptions: Array = [];

    qaModel = {
        defectAreas: []
    };

    defectArea = {};

    qaModelLoading: boolean = false;

    mode: string = 'view';

    tabs: Array = [
        {
            name: 'attributes',
            desc: 'Defect Area Attributes',
            route: 'attributes/'
        },
        {
            name: 'sources',
            desc: 'Sources & Causes',
            route: 'sources/'
        },
        {
            name: 'severities',
            desc: 'Severity & Guidance',
            route: 'severities/'
        },
        {
            name: 'qaTree',
            desc: 'Q&A Tree',
            route: 'qaTree/'
        },
        {
            name: 'preQual',
            desc: 'Pre-Qual',
            route: 'preQual/'
        },
        {
            name: 'remediation',
            desc: 'Remediation',
            route: 'remediation/'
        }
    ];

    selectedTab = this.tabs[0];

    qaModelStatusCode = QA_MODEL_STATUS_CODE;

    constructor(route: ActivatedRoute, router: Router, client: Client) {
        this._route = route;
        this._router = router;
        this._client = client;
    }

    ngOnInit() {

        this.subscriptions.push(this._route.params.subscribe(params => {
            this.name = params['name'];

            let qaModelId = params['qaModelId'];
            let defectAreaId = params['defectAreaId'];

            if (qaModelId) {
                this.qaModelLoading = true;
                this._client.resources.admin.qaModels.qaModelId(qaModelId).get().first()
                    .subscribe(qaModel => {
                        this.qaModel = qaModel;
                        this.defectArea = this.qaModel.defectAreas.find(defectArea => {
                            return defectArea.defectAreaId === defectAreaId;
                        });
                        this.qaModelLoading = false;
                    });
            }
        }));

        this.subscriptions.push(this._route.data.subscribe(result => {
            this.mode = result['mode'];
        }));

        // selects first node child from route to detect selected tab
        this._router.events.subscribe(e => {
            if (e instanceof NavigationEnd){
                let tabName = this._route.firstChild.snapshot.data['tab'];
                this.selectedTab = this.tabs.find(t => t.name === tabName);
            }
        });
    }

    ngOnDestroy() {
        this.subscriptions.forEach(subscription => {
            subscription.unsubscribe();
        });
    }

    getPrevTabAvailable() {
        let index = this.tabs.indexOf(this.selectedTab);
        return index > 0;
    }

    getPrevTab() {
        if (this.getPrevTabAvailable()) {
            let index = this.tabs.indexOf(this.selectedTab);
            return this.tabs[index - 1];
        }
        return this.tabs[this.tabs.length - 1];
    }

    getNextTabAvailable() {
        let index = this.tabs.indexOf(this.selectedTab);
        return index < (this.tabs.length - 1);
    }

    getNextTab() {
        if (this.getNextTabAvailable()) {
            let index = this.tabs.indexOf(this.selectedTab);
            return this.tabs[index + 1];
        }
        return this.tabs[0];
    }
}
