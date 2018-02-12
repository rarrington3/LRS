import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../shared/main';
import { WORKLOAD_ROUTING } from './routes';
import { WORKLOAD_COMPONENTS } from './components/';
import { WORKLOAD_PROVIDERS } from './services/';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

export let WORKLOAD_IMPORTS = [
    CommonModule,
    SharedModule,
    FormsModule,
    ReactiveFormsModule
];

@NgModule({
    imports: [WORKLOAD_IMPORTS, WORKLOAD_ROUTING],
    declarations: WORKLOAD_COMPONENTS,
    exports: [],
    providers: WORKLOAD_PROVIDERS
})
export class WorkloadModule { }
