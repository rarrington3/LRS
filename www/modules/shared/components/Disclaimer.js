// This file was generated from the component scaffold
// Copyright 2017

import {Component} from '@angular/core';
import template from './Disclaimer.html';
import styles from './Disclaimer.less';
import ModalSvc from '../../app/services/ModalSvc';

@Component({
    selector: 'disclaimer',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/api/core/Component-decorator.html
 * @example
 * <disclaimer name="Disclaimer" (change)="onChange($event)"></disclaimer>
 */
export default class Disclaimer {
    _modalSvc: ModalSvc;
    constructor(modalSvc: ModalSvc) {
        this._modalSvc = modalSvc;
    }

    displayOMBStatement() {
        this._modalSvc.acknowledge(
            '<p>The information collection requirements contained in this system have been approved by the Office of Management and Budget (OMB) under the Paperwork Reduction Act of 1995 (44 U.S.C. 3501-3520) and assigned OMB control numbers 2502-0005, 2502-0059 and 2502-0600. In accordance with the Paperwork Reduction Act, HUD may not conduct or sponsor, and a person is not required to respond to, a collection of information unless the collection displays a currently valid OMB control number.</p>',
            'PRA Info/OMB Control Numbers', 'OK').first()
            .subscribe(
                () => {}, () => {}
            );
    }
}
