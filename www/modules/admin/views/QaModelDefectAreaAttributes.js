// This file was generated from the view scaffold
// Copyright 2016

import {Component} from '@angular/core';
import {ActivatedRoute}  from '@angular/router';
import template from './QaModelDefectAreaAttributes.html';
import styles from './QaModelDefectAreaAttributes.less';
import Client from '../../api/lrs.api.v1.generated';
import QaModelDefectAreaBase from './QaModelDefectAreaBase';

@Component({
    selector: 'qa-model-defect-area-attributes',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/guide/router.html
 */
export default class QaModelDefectAreaAttributes extends QaModelDefectAreaBase {

    /**
     * Default name for the class, passed by reference via RouteParams
     */
    name: string = 'QaModelDefectAreaAttributes';

    fields: Array = [];

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
