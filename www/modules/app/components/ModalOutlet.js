// This file was generated from the component scaffold
// Copyright 2016

import {Component} from '@angular/core';
import {Observable} from 'rxjs';
import template from './ModalOutlet.html';
import styles from './ModalOutlet.less';
import ModalSvc from '../services/ModalSvc';
import _ from 'lodash';

@Component({
    selector: 'modal-outlet',
    template: template,
    styles: [styles]
})
export default class ModalOutlet {

    constructor(modalSvc:ModalSvc) {

        // Reference this component into the corresponding service.
        modalSvc.hookupOutlet(this);
    }

    isOpen:boolean;
    body: string;
    header: string;
    confirmButtonText: string;
    rejectButtonText: string;
    displayedModal: string;
    hasCloseButton: boolean = true;
    noop = _.noop; /* template call */
    confirm = _.noop; /* Blank/noop function. Will be overridden later. */
    finalize = _.noop; /* Blank/noop function. Will be overridden later. */

    askForConfirmation (
        body:string = '<h3>Are you sure you want to do that?</h3>',
        header:string = 'Please confirm',
        confirmButtonText:string = 'Confirm',
        rejectButtonText:string = 'Cancel',

    ):Observable {

        // Set the modal type.
        this.displayedModal = 'confirmation';

        // Update the UI/template.
        this.hasCloseButton = true;

        return this._openBasicModal(body, header, confirmButtonText, rejectButtonText);
    }

    acknowledge(
        body:string = '<h3>Before continuing, you must acknowledge what\'s about to happen.</h3>',
        header:string = 'Please acknowledge',
        confirmButtonText:string = 'OK'
    ):Observable {

        // Set the modal type.
        this.displayedModal = 'acknowledge';

        // Update the UI/template.
        this.hasCloseButton = false;

        // Start by opening the base modal.
        return this._openBasicModal(body, header, confirmButtonText);

    }

    _openBasicModal(body:string, header:string, confirmButtonText:string, rejectButtonText:string):Observable {
        // Update the UI/template.
        this.header = header;
        this.body = body;
        this.confirmButtonText = confirmButtonText;
        this.rejectButtonText = rejectButtonText;

        // Create an observable we can complete later based on user input.
        return Observable.create(observer => {

            // Override this components confirm function.
            this.confirm = () => {
                observer.next(this.confirmButtonText);
                observer.complete();
            };

            // Override this components finalize function.
            this.finalize = () => {
                observer.error(this.rejectButtonText);
                observer.complete();
            };

            this.isOpen = true;

            // Dispose
            return () => {
                this.confirm = _.noop;
                this.finalize = _.noop;
            };
        });
    }
}
