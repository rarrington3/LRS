// This file was generated from the service scaffold
// Copyright 2016
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';

/**
 * @example
 * let injector = Injector.resolveAndCreate([ModalSvc]);
 * let modalSvc = new injector.get(ModalSvc);
 * @example
 * class Component {
 * 		constructor(modalSvc:ModalSvc, modalSvc2:ModalSvc) {
 *			//injected via angular, a singleton by default in same injector context
 *			console.log(modalSvc === modalSvc2);
 *		}
 * }
 */
@Injectable()
export default class ModalSvc {

    outlet: Object;

    constructor() { /* No constructor logic yet. */
    }

    hookupOutlet(outlet: Object): void {
        this.outlet = outlet;
    }

    askForConfirmation(body: string, header: string, confirmButtonText: string, rejectButtonText: string): Observable {
        return this.outlet.askForConfirmation(body, header, confirmButtonText, rejectButtonText);
    }

    acknowledge(body: string, header: string, confirmButtonText: string): Observable {
        return this.outlet.acknowledge(body, header, confirmButtonText);
    }
}
