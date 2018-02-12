// This file was generated from the service scaffold
// Copyright 2017

import {Injectable, EventEmitter} from '@angular/core';

@Injectable()
export default class CrossFilterService {
    resultsChanged:EventEmitter = new EventEmitter();
    resultsChangedWithSelections:EventEmitter = new EventEmitter();
    constructor() {
    }

}
