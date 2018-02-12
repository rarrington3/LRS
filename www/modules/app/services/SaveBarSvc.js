// This file was generated from the service scaffold
// Copyright 2017

import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import ModalSvc from './ModalSvc';

/**
 * @example
 * let injector = Injector.resolveAndCreate([SaveBarSvc]);
 * let saveBarSvc = new injector.get(SaveBarSvc);
 * @example
 * class Component {
 * 		constructor(saveBarSvc:SaveBarSvc, saveBarSvc2:SaveBarSvc) {
 *			//injected via angular, a singleton by default in same injector context
 *			console.log(saveBarSvc === saveBarSvc2);
 *		}
 * }
 */
@Injectable()
export default class SaveBarSvc {

    outlet: Object;

    modalSvc: ModalSvc;

    constructor(modalSvc: ModalSvc) {
        this.modalSvc = modalSvc;
    }

    hookupOutlet(outlet: Object): void {
        this.outlet = outlet;
    }

    askForSave(): Observable {
        return this.outlet.askForSave();
    }

    isOpen(): boolean {
        return this.outlet.isOpen;
    }

    close(): void {
        this.outlet.isOpen = false;
        this.outlet.cancel();
    }

    askForClose(): Observable | boolean {
        if (this.isOpen()) {
            return this.modalSvc.askForConfirmation('You have unsaved changes. Do you want to leave this page and discard your changes?')
                .map(() => {
                    this.close();
                    return true;
                })
                .catch(() => {
                    return Observable.of(false);
                });
        }
        return true;
    }
}
