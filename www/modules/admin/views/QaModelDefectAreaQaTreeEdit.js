// This file was generated from the view scaffold
// Copyright 2017

import {Component} from '@angular/core';
import {ActivatedRoute}  from '@angular/router';
import template from './QaModelDefectAreaQaTreeEdit.html';
import styles from './QaModelDefectAreaQaTreeEdit.less';
import QaModelDefectAreaBase from './QaModelDefectAreaBase';
import Client from '../../api/lrs.api.v1.generated';
import {ModalSvc, SaveBarSvc} from '../../app/services';
import ArrayUtils from '../../shared/utils/ArrayUtils';
import {COMMON_OPERATION} from '../../shared/constants';

@Component({
    selector: 'qa-model-defect-area-qa-tree-edit',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/guide/router.html
 */
export default class QaModelDefectAreaQaTreeEdit extends QaModelDefectAreaBase {

    /**
     * Default name for the class, passed by reference via RouteParams
     */
    name: string = 'QaModelDefectAreaQaTreeEdit';

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

    askForSave(args) {
        if (args && args.length) {
            let operation = args[0];
            let item = args.length > 1 ? args[1] : null;
            if (operation === COMMON_OPERATION.REMOVE) {
                // remove all occurrence of the references in the pre qual options to prevent constraint issues.
                this.defectArea.preQualifyQuestion.optionsQuestions.forEach(oq => {
                    ArrayUtils.remove(oq.options, item.questionReference);
                });
            }
            if (operation === COMMON_OPERATION.UPDATE) {
                // if we are updating and we have a change in the question number, we need to re assign the new
                if (item && item._previousQuestionNumber) {
                    this.defectArea.preQualifyQuestion.optionsQuestions.forEach(oq => {
                        oq.options.forEach((option, i) => {
                            if (option === `${this.defectArea.code}.${item._previousQuestionNumber}`) {
                                oq.options.splice(i, 1, `${this.defectArea.code}.${item.questionNumber}`); // replace with new ref
                            }
                        });
                    });
                    delete item._previousQuestionNumber;
                }
            }
        }
        super.askForSave();
    }
}
