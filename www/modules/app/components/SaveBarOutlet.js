// This file was generated from the component scaffold
// Copyright 2017

import {Component} from '@angular/core';
import template from './SaveBarOutlet.html';
import styles from './SaveBarOutlet.less';
import {Observable} from 'rxjs';
import SaveBarSvc from '../services/SaveBarSvc';
import _ from 'lodash';

@Component({
    selector: 'save-bar-outlet',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/api/core/Component-decorator.html
 * @example
 * <save-bar-outlet name="SaveBarOutlet" (change)="onChange($event)"></save-bar-outlet>
 */
export default class SaveBarOutlet {

    isOpen: boolean;

    save = _.noop;

    cancel = _.noop;

    noop = _.noop;

    _openSaveBarSource: Observable;

    constructor(saveBarSvc: SaveBarSvc) {
        // Reference this component into the corresponding service.
        saveBarSvc.hookupOutlet(this);
    }

    askForSave(): Observable {
        return this._openSaveBar();
    }

    _openSaveBar(): Observable {

        if (!this._openSaveBarSource) {

            // Create an observable we can complete later based on user input.
            this._openSaveBarSource = Observable.create(observer => {

                // Override this components save function.
                this.save = () => {
                    observer.next('save');
                    observer.complete();
                };

                // Override this components finalize function.
                this.cancel = () => {
                    observer.error('cancel');
                    observer.complete();
                };

                this.isOpen = true;

                // Dispose
                return () => {
                    this.save = _.noop;
                    this.cancel = _.noop;
                };
            })
                .share(); // mark this observable as shared, to share the save bar execution context to any subscribers
        }

        return this._openSaveBarSource;
    }
}
