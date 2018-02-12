import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule }  from '../shared/main';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MANUALSELECTION_ROUTING } from './routes';
import { MANUALSELECTION_COMPONENTS } from './components/';
import { MANUALSELECTION_PROVIDERS } from './services/';
import { MANUALSELECTION_VIEWS} from './views';

export let MANUALSELECTION_IMPORTS = [
    CommonModule,
    SharedModule,
    FormsModule,
    ReactiveFormsModule
];

@NgModule({
    imports:      [ MANUALSELECTION_IMPORTS, MANUALSELECTION_ROUTING],
    declarations: [ MANUALSELECTION_COMPONENTS, MANUALSELECTION_VIEWS ],
    exports:      [ ],
    providers:    [ MANUALSELECTION_PROVIDERS  ]
})
export class ManualselectionModule { }
