// This file was generated from the module scaffold
// Copyright 2017

import { Routes, RouterModule } from '@angular/router';
import { MicrostrategyGuard } from '../app/services/AuthGuards';
import { Microstrategy } from './views';

export const MICROSTRATEGY_ROUTES: Routes = [
    {
        path: 'reports',
        component: Microstrategy,
        canActivate: [MicrostrategyGuard],
        navigationBarEntry: 'Reports'
    }
];

export const MICROSTRATEGY_ROUTING = RouterModule.forChild(MICROSTRATEGY_ROUTES);
