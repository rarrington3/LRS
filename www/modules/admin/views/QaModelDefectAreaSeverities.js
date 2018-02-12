// This file was generated from the view scaffold
// Copyright 2017

import {Component} from '@angular/core';
import {ActivatedRoute}  from '@angular/router';
import template from './QaModelDefectAreaSeverities.html';
import styles from './QaModelDefectAreaSeverities.less';
import Client from '../../api/lrs.api.v1.generated';
import QaModelDefectAreaBase from './QaModelDefectAreaBase';

@Component({
    selector: 'qa-model-defect-area-severities',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/guide/router.html
 */
export default class QaModelDefectAreaSeverities extends QaModelDefectAreaBase {

    /**
     * Default name for the class, passed by reference via RouteParams
     */
    name:string = 'QaModelDefectAreaSeverities';

    constructor(route: ActivatedRoute, client: Client) {
        super(route, client);
    }

    ngOnInit() {
        super.init();

        this.subscriptions.push(this.route.params.subscribe(params => {
            this.name = params['name'];
        }));
    }

    ngOnDestroy() {
        super.destroy();
    }
}
