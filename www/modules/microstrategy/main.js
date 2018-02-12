// This file was generated from the module scaffold
// Copyright 2017

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule }  from '../shared/main';
import { MICROSTRATEGY_ROUTING } from './routes';
import { MICROSTRATEGY_COMPONENTS } from './components/';
import { MICROSTRATEGY_PROVIDERS } from './services/';
import { MICROSTRATEGY_VIEWS } from './views/';


export let MICROSTRATEGY_IMPORTS = [
    CommonModule,
    SharedModule
];

@NgModule({
    imports:      [ MICROSTRATEGY_IMPORTS, MICROSTRATEGY_ROUTING],
    declarations: [ MICROSTRATEGY_COMPONENTS, MICROSTRATEGY_VIEWS ],
    exports:      [ ],
    providers:    [ MICROSTRATEGY_PROVIDERS  ]
})
export class MicrostrategyModule { }
