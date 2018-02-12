// This file was generated from the component scaffold
// Copyright 2016

import {Component, Input} from '@angular/core';
import template from './tools.html';
import styles from './tools.less';

@Component({
    template: template,
    styles: [styles],
    selector: 'tools'
})
/**
 * @see https://angular.io/docs/ts/latest/api/core/Component-decorator.html
 * @example
 * <tools name="Tools"></tools>
 */
export default class Tools {

    /**
     * An example input for this component
     * @see https://angular.io/docs/ts/latest/api/core/Input-var.html
     */
    @Input() name:string = 'Dev & Test Tools';

    constructor() {

    }

}
