// This file was generated from the module scaffold
// Copyright 2016

import {NgModule}      from '@angular/core';
import {CommonModule}  from '@angular/common';
import {FormsModule} from '@angular/forms';
import {SharedModule}  from '../shared/main';
import {STAFF_ROUTING} from './routes';
import {STAFF_COMPONENTS} from './components/';
import {STAFF_PROVIDERS} from './services/';
import {STAFF_VIEWS} from './views';

export let STAFF_IMPORTS = [
    CommonModule,
    SharedModule,
    FormsModule
];

@NgModule({
    imports: [STAFF_IMPORTS, STAFF_ROUTING],
    declarations: [STAFF_COMPONENTS, STAFF_VIEWS],
    exports: [],
    providers: STAFF_PROVIDERS
})
export class StaffModule {}
