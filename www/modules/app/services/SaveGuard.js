// This file was generated from the service scaffold
// Copyright 2016

import {Injectable} from '@angular/core';
import {CanDeactivate} from '@angular/router';
import SaveBarSvc from './SaveBarSvc';

@Injectable()
export default class SaveGuard implements CanDeactivate {

    saveBarSvc: SaveBarSvc;

    constructor(saveBarSvc: SaveBarSvc) {
        this.saveBarSvc = saveBarSvc;
    }

    canDeactivate() {
        return this.saveBarSvc.askForClose();
    }
}
