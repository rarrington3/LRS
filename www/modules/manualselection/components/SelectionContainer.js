// This file was generated from the component scaffold
// Copyright 2016

import {Component, Input, Output, EventEmitter} from '@angular/core';
import template from './SelectionContainer.html';
import styles from './SelectionContainer.less';

@Component({
    selector: 'selection-container',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/api/core/Component-decorator.html
 * @example
 * <selection-container name="SelectionContainer" (change)="onChange($event)"></selection-container>
 */
export default class SelectionContainer {
    /**
     * An example input for this component
     * @see https://angular.io/docs/ts/latest/api/core/Input-var.html
     */
    @Input() name:string = 'SelectionContainer';

    /**
     * An example output for this component
     * @see https://angular.io/docs/ts/latest/api/core/Output-var.html
     */
    @Output() change:EventEmitter = new EventEmitter();



    constructor() {

    }
}
