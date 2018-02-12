// This file was generated from the module scaffold
// Copyright 2016

import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {SharedModule}  from '../shared/main';
import { Ng2PaginationModule } from 'ng2-pagination';
import {EXCEPTIONS_ROUTING} from './routes';
import {EXCEPTIONS_COMPONENTS} from './components/';
import {EXCEPTIONS_VIEWS} from './views/';
import {EXCEPTIONS_PROVIDERS} from './services/';

export let EXCEPTIONS_IMPORTS = [
    CommonModule,
    SharedModule,
    FormsModule,
    Ng2PaginationModule
];

@NgModule({
    imports: [EXCEPTIONS_IMPORTS, EXCEPTIONS_ROUTING],
    declarations: [EXCEPTIONS_COMPONENTS, EXCEPTIONS_VIEWS],
    exports: [],
    providers: [EXCEPTIONS_PROVIDERS]
})
export class ExceptionsModule {
}
