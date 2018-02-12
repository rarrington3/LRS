// This file was generated from the view scaffold
// Copyright 2017

import {Component} from '@angular/core';
import {ActivatedRoute}  from '@angular/router';
import template from './QaModelDefectAreaPreQualEdit.html';
import styles from './QaModelDefectAreaPreQualEdit.less';
import QaModelDefectAreaBase from './QaModelDefectAreaBase';
import Client from '../../api/lrs.api.v1.generated';
import {SaveBarSvc, ModalSvc} from '../../app/services';

@Component({
    selector: 'qa-model-defect-area-pre-qual-edit',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/guide/router.html
 */
export default class QaModelDefectAreaPreQualEdit extends QaModelDefectAreaBase {

    /**
     * Default name for the class, passed by reference via RouteParams
     */
    name: string = 'QaModelDefectAreaPreQualEdit';

    newOptionsQuestion = {
        code: '',
        description: '',
        order: '',
        options: []
    };

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
