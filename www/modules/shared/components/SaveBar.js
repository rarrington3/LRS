// This file was generated from the component scaffold
// Copyright 2017

import {Component, Input, Output, EventEmitter} from '@angular/core';
import template from './SaveBar.html';
import styles from './SaveBar.less';

@Component({
    selector: 'save-bar',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/api/core/Component-decorator.html
 * @example
 * <save-bar name="SaveBar" (change)="onChange($event)"></save-bar>
 */
export default class SaveBar {

    @Input() isOpen: boolean = false;

    @Output() isOpenChange: EventEmitter = new EventEmitter();

    @Input() canSave: boolean = true;

    @Output() saveChange: EventEmitter = new EventEmitter();

    constructor() {
    }

    cancel() {
        this.isOpen = false;
        this.isOpenChange.emit(this.isOpen);
    }

    save() {
        this.saveChange.emit();
        this.isOpen = false;
        this.isOpenChange.emit(this.isOpen);
    }
}
