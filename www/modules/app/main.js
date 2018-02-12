import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {RouterModule} from '@angular/router';
import {FormsModule} from '@angular/forms';
import {HttpModule} from '@angular/http';
import DEFAULT_PROVIDERS from './providers';
import {App, APP_COMPONENTS} from './components/';
import {SharedModule} from '../shared/main';

// Individual modules.
import {ReviewModule} from '../review/main';
import {DevModule} from '../dev/main';
import {WorkloadModule} from '../workload/main';
import {StaffModule} from '../staff/main';
import {ManualselectionModule} from '../manualselection/main';
import {LenderModule} from '../lender/main';
import {LenderMonitoringModule} from '../lenderMonitoring/main';
import {ExceptionsModule} from '../exceptions/main';
import {AdminModule} from '../admin/main';
import {MicrostrategyModule} from '../microstrategy/main';
import {Ng2PaginationModule} from 'ng2-pagination';

@NgModule({
    declarations: APP_COMPONENTS,
    imports: [
        BrowserModule,
        HttpModule,
        RouterModule.forRoot([]),
        FormsModule,
        Ng2PaginationModule,
        SharedModule,
        ReviewModule,
        DevModule,
        WorkloadModule,
        StaffModule,
        ManualselectionModule,
        LenderModule,
        LenderMonitoringModule,
        ExceptionsModule,
        AdminModule,
        MicrostrategyModule
    ],
    providers: DEFAULT_PROVIDERS, // singleton services at least used once in the module and in any child module referenced in imports
    bootstrap: [App]
})
export class AppModule {}
