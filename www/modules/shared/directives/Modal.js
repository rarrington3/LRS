// This file was generated from the directive scaffold
// Copyright 2016

import {Directive, ElementRef, Input, Output, EventEmitter, SimpleChanges, SimpleChange} from '@angular/core';
import $ from 'jquery';

@Directive({
    selector: 'modal'
})
/**
 * @see https://angular.io/docs/ts/latest/api/core/Directive-decorator.html
 * @example
 * <div [modal]="true"></div>
 */
export default class Modal {
    _element: any = null;
    _initialized: boolean = null;
    _modal: any = null;

    $element: any = null;

    @Input() isOpen: boolean = false;

    @Output() isOpenChange: EventEmitter = new EventEmitter();

    constructor(elementRef: ElementRef) {
        this._element = elementRef.nativeElement;
        this.$element = $(this._element);
    }

    ngOnInit() {
        this._modal = this.$element.find('.modal');
        this._modal.on('hide.bs.modal', () => {
            this.isOpen = false;
            this.isOpenChange.emit(this.isOpen);
        });
        this._initialized = true;
    }

    ngOnDestroy() {
        if (this._initialized){
            this._modal.off('hide.bs.modal');
        }
    }

    ngOnChanges(changes: SimpleChanges) {
        let isOpenChange: SimpleChange = changes['isOpen'];

        if (isOpenChange && this._modal) {
            if (isOpenChange.currentValue) {
                this._modal.modal('show');

            } else {
                this._modal.modal('hide');
            }
        }
    }
}
