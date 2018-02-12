// This file was generated from the module scaffold
// Copyright 2016

import {Routes, RouterModule} from '@angular/router';
import {StaffManagement} from './views';
import {
    AdminGuard
} from '../app/services/AuthGuards';

export const STAFF_ROUTES: Routes = [
    {
        path: 'staff',
        component: StaffManagement,
        canActivate: [AdminGuard],
        navigationBarEntry: 'Staff Management'
    }
];

export const STAFF_ROUTING = RouterModule.forChild(STAFF_ROUTES);
