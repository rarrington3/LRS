// This file was generated from the component scaffold
// Copyright 2016

import {Component, Input, Output, EventEmitter, SimpleChanges, SimpleChange} from '@angular/core';
import template from './DropDown.html';
import styles from './DropDown.less';

let nextId = 0;

@Component({
    selector: 'drop-down',
    template: template,
    styles: [styles],
    directives: []
})
export default class DropDown {
    @Input() id = `drop-down-${nextId++}`;

    @Input() items: Array = [];

    @Input() textField: string = null;

    @Input() valueField: string = null;

    @Input() text: string = null;

    @Input() label: string = null;

    @Input() selectedItem = null;

    @Input() selectedValue = null;

    @Input() leftMenuVisible: boolean = false;

    @Input() allVisible: boolean = false;

    @Input() allText: string = null;

    @Output() selectedItemChange: EventEmitter = new EventEmitter();

    @Output() selectedValueChange: EventEmitter = new EventEmitter();

    constructor() {

    }

    selectItem(item) {
        this.selectedItem = item;
        this._onSelectedItemChanged(this.selectedItem);

        this.selectedItemChange.emit(this.selectedItem);
        this.selectedValueChange.emit(this.selectedValue);
    }

    _onSelectedItemChanged(selectedItem) {
        if (this.valueField) {
            if (selectedItem) {
                this.selectedValue = selectedItem[this.valueField];
            }
            else {
                this.selectedValue = null;
            }
        } else {
            this.selectedValue = null;
        }
    }

    _onSelectedValueChanged(selectedValue) {
        if (this.valueField) {
            let that = this;
            let items = this.items.filter(function (item) {
                return item[that.valueField] === selectedValue;
            });

            if (items.length === 1) {
                this.selectedItem = items[0];
            }
        }
    }

    ngOnChanges(changes: SimpleChanges) {
        let selectedItemChange: SimpleChange = changes['selectedItem'];
        let selectedValueChange: SimpleChange = changes['selectedValue'];
        let itemsChange: SimpleChange = changes['items'];

        if (selectedItemChange) {
            this._onSelectedItemChanged(selectedItemChange.currentValue);
        }

        if (selectedValueChange) {
            this._onSelectedValueChanged(selectedValueChange.currentValue);
        }

        if (itemsChange) {
            if (this.allVisible && itemsChange.currentValue) {
                let all = '';
                let textField = this.allText || 'All';
                if (this.valueField || this.textField) {
                    all = {};
                    if (this.valueField) {
                        all[this.valueField] = null;
                    }
                    if (this.textField) {
                        all[this.textField] = textField;
                    }
                } else {
                    all = textField;
                }
                this.items = [all, ...itemsChange.currentValue];
                if (!this.selectedItem) {
                    this.selectedItem = all;
                    this._onSelectedItemChanged(this.selectedItem);
                }
            }
        }
    }
}
