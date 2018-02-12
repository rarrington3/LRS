// This file was generated from the module scaffold
// Copyright 2016

import {Routes, RouterModule} from '@angular/router';
import {LenderReview} from './views';
import { LenderMonitoringGuard } from '../app/services/AuthGuards';

export const LENDERMONITORING_ROUTES: Routes = [
    {
        path: 'lenderReview',
        component: LenderReview,
        canActivate: [LenderMonitoringGuard],
        navigationBarEntry: 'Lender Monitoring'
    }
];

export const LENDERMONITORING_ROUTING = RouterModule.forChild(LENDERMONITORING_ROUTES);
