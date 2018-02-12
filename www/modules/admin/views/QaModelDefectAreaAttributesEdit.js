// This file was generated from the view scaffold
// Copyright 2016

import {Component} from '@angular/core';
import {ActivatedRoute}  from '@angular/router';
import template from './QaModelDefectAreaAttributesEdit.html';
import styles from './QaModelDefectAreaAttributesEdit.less';
import Client from '../../api/lrs.api.v1.generated';
import {SaveBarSvc, ModalSvc} from '../../app/services';
import QaModelDefectAreaBase from './QaModelDefectAreaBase';

@Component({
    selector: 'qa-model-defect-area-attributes-edit',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/guide/router.html
 */
export default class QaModelDefectAreaAttributesEdit extends QaModelDefectAreaBase {

    /**
     * Default name for the class, passed by reference via RouteParams
     */
    name: string = 'QaModelDefectAreaAttributesEdit';

    fields: Array = [];

    selectedFieldCode = '';

    _newAttribute = {
        entityName: '',
        fieldName: '',
        order: ''
    };

    constructor(route: ActivatedRoute, client: Client, saveBarSvc: SaveBarSvc, modalSvc: ModalSvc) {
        super(route, client, saveBarSvc, modalSvc);
    }

    ngOnInit() {
        super.init();

        this.subscriptions.push(this.route.params.subscribe(params => {
            this.name = params['name'];
        }));

        this.client.resources.admin.fields.get().first()
            .subscribe(fields => {
                this.fields = fields;
            });
    }

    ngOnDestroy() {
        super.destroy();
    }

    add() {
        let field = this.fields.find((f) => f.code === this.selectedFieldCode);
        let attributeTemplate = Object.assign({}, this._newAttribute, {
            entityName: field.entityName,
            fieldName: field.fieldName
        });
        this.selectedFieldCode = '';
        super.add(this.defectArea.loanAttributes, attributeTemplate);
    }
}
