// This file was generated from the view scaffold
// Copyright 2017

import {Component} from '@angular/core';
import {ActivatedRoute}  from '@angular/router';
import template from './QaModelDefectAreaRemediation.html';
import styles from './QaModelDefectAreaRemediation.less';
import QaModelDefectAreaBase from './QaModelDefectAreaBase';
import Client from '../../api/lrs.api.v1.generated';

@Component({
    selector: 'qa-model-defect-area-remediation',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/guide/router.html
 */
export default class QaModelDefectAreaRemediation extends QaModelDefectAreaBase  {

    /**
     * Default name for the class, passed by reference via RouteParams
     */
    name:string = 'QaModelDefectAreaRemediation';

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
