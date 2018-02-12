import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule }  from '../shared/main';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { REVIEW_ROUTING } from './routes';
import { REVIEW_COMPONENTS } from './components/';
import { REVIEW_PROVIDERS } from './services/';

export let REVIEW_IMPORTS = [
    CommonModule,
    SharedModule,
    FormsModule,
    ReactiveFormsModule
];

@NgModule({
    imports: [ REVIEW_IMPORTS, REVIEW_ROUTING ],
    declarations: REVIEW_COMPONENTS,
    exports: [ ],
    providers: REVIEW_PROVIDERS
})
export class ReviewModule {}
