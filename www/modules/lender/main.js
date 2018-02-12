// This file was generated from the module scaffold
// Copyright 2016

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule }  from '../shared/main';
import { WorkloadModule } from '../workload/main';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Ng2PaginationModule } from 'ng2-pagination';
import { LENDER_ROUTING } from './routes';
import { LENDER_COMPONENTS } from './components/';
import { LENDER_PIPES } from './pipes/';
import { LENDER_PROVIDERS } from './services/';
import { LENDER_VIEWS } from './views/';

export let LENDER_IMPORTS = [
    CommonModule,
    SharedModule,
    FormsModule,
    ReactiveFormsModule,
    Ng2PaginationModule,
    WorkloadModule
];

@NgModule({
    imports:      [ LENDER_IMPORTS, LENDER_ROUTING],
    declarations: [ LENDER_VIEWS, LENDER_COMPONENTS, LENDER_PIPES ],
    exports:      [ ],
    providers:    [ LENDER_PROVIDERS  ]
})
export class LenderModule { }
