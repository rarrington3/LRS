// This file was generated from the module scaffold
// Copyright 2016

import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {SharedModule}  from '../shared/main';
import {ADMIN_ROUTING} from './routes';
import {ADMIN_COMPONENTS} from './components/';
import {ADMIN_PIPES} from './pipes/';
import {ADMIN_PROVIDERS} from './services/';
import {ADMIN_VIEWS} from './views/';
import {FormsModule}   from '@angular/forms';

export let ADMIN_IMPORTS = [
    CommonModule,
    SharedModule,
    FormsModule
];

@NgModule({
    imports: [ADMIN_IMPORTS, ADMIN_ROUTING],
    declarations: [ADMIN_COMPONENTS, ADMIN_VIEWS, ADMIN_PIPES],
    exports: [],
    providers: [ADMIN_PROVIDERS]
})
export class AdminModule {
}
