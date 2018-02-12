import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SHARED_COMPONENTS } from './components/';
import { SHARED_DIRECTIVES } from './directives/';
import { SHARED_PIPES } from './pipes/';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Ng2PaginationModule } from 'ng2-pagination';
import cssify from 'cssify';

export let SHARED_IMPORTS = [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    Ng2PaginationModule
];

@NgModule({
    imports: [SHARED_IMPORTS, RouterModule],
    declarations: [SHARED_COMPONENTS, SHARED_DIRECTIVES, SHARED_PIPES],
    exports: [SHARED_COMPONENTS, SHARED_DIRECTIVES, SHARED_PIPES],
    providers: [] // this shared module should not have any providers. see https://angular.io/docs/ts/latest/cookbook/ngmodule-faq.html#!#q-why-bad
})
export class SharedModule { }

cssify(require('./main.less'));
