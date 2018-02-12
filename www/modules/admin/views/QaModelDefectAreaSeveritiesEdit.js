// This file was generated from the view scaffold
// Copyright 2017

import {Component} from '@angular/core';
import {ActivatedRoute}  from '@angular/router';
import template from './QaModelDefectAreaSeveritiesEdit.html';
import styles from './QaModelDefectAreaSeveritiesEdit.less';
import Client from '../../api/lrs.api.v1.generated';
import {SaveBarSvc, ModalSvc} from '../../app/services';
import QaModelDefectAreaBase from './QaModelDefectAreaBase';

@Component({
    selector: 'qa-model-defect-area-severities-edit',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/guide/router.html
 */
export default class QaModelDefectAreaSeveritiesEdit extends QaModelDefectAreaBase {

    /**
     * Default name for the class, passed by reference via RouteParams
     */
    name:string = 'QaModelDefectAreaSeveritiesEdit';

    constructor(route: ActivatedRoute, client: Client, saveBarSvc: SaveBarSvc, modalSvc: ModalSvc) {
        super(route, client, saveBarSvc, modalSvc);
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
