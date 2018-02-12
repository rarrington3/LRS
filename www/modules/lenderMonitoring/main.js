// This file was generated from the module scaffold
// Copyright 2016

import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {SharedModule}  from '../shared/main';
import {LENDERMONITORING_ROUTING} from './routes';
import {LENDERMONITORING_COMPONENTS} from './components/';
import {LENDERMONITORING_PROVIDERS} from './services/';
import {LENDERMONITORING_VIEWS} from './views';

export let LENDERMONITORING_IMPORTS = [
    CommonModule,
    SharedModule,
    FormsModule
];

@NgModule({
    imports: [LENDERMONITORING_IMPORTS, LENDERMONITORING_ROUTING],
    declarations: [LENDERMONITORING_COMPONENTS, LENDERMONITORING_VIEWS],
    exports: [],
    providers: [LENDERMONITORING_PROVIDERS]
})
export class LenderMonitoringModule {
}
