import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { SharedModule }  from '../shared/main';
import { DEV_ROUTING } from './routes';
import { DEV_COMPONENTS } from './components/';
import { DEV_PROVIDERS } from './services/';

export let DEV_IMPORTS = [
    CommonModule,
    SharedModule,
    FormsModule
];

@NgModule({
    imports: [ DEV_IMPORTS, DEV_ROUTING ],
    declarations: DEV_COMPONENTS,
    exports: [],
    providers: [ DEV_PROVIDERS ]
})
export class DevModule {}
