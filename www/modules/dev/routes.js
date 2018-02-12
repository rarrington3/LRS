import { Routes, RouterModule } from '@angular/router';
import {
    Tools,
    Login
} from './components';
import { DevModeGuard } from '../app/services/AuthGuards';

export const DEV_ROUTES:Routes = [
    {
        path: 'dev',
        component: Tools,
        children: [
            { path: '', redirectTo: 'login', pathMatch: 'full' },
            { path: 'login', component: Login }
        ],
        canActivate: [ DevModeGuard ],
        navigationBarEntry: 'Dev & Test Tools'
    }
];

export const DEV_ROUTING = RouterModule.forRoot(DEV_ROUTES);
