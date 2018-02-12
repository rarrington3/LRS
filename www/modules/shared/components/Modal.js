// This file was generated from the component scaffold
// Copyright 2016

import {Component, Input, Output, EventEmitter} from '@angular/core';
import template from './Modal.html';
import styles from './Modal.less';

let nextId = 0;

@Component({
    selector: 'modal',
    template: template,
    styles: [styles]
})
/**
 * Modal
 * @example
 * <modal></modal>
 */
export default class Modal {

    labelId = `modal-label-${nextId++}`;

    @Input() id = `modal-${nextId++}`;

    @Input() hasCloseButton: boolean = true;

    @Input() hasCancelButton: boolean = true;

    @Input() hasConfirmButton: boolean = true;

    @Input() title: string = '';

    @Input() cancelText: string = 'Cancel';

    @Input() confirmText: string = 'Confirm';

    @Input() closeOnSave: boolean = true;

    @Input() canSave: boolean = true;

    @Output() confirmChange: EventEmitter = new EventEmitter();

    constructor() {
    }

    confirm() {
        this.confirmChange.emit();
    }
}
