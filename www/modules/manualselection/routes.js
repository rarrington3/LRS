// This file was generated from the module scaffold
// Copyright 2016

import { Routes, RouterModule } from '@angular/router';
import {
    CaseSelection
} from './views';
import { ReviewerOrAdminGuard } from '../app/services/AuthGuards';

export const MANUALSELECTION_ROUTES: Routes = [
    {
        path: 'selection',
        component: CaseSelection,
        canActivate: [ReviewerOrAdminGuard],
        navigationBarEntry: 'Case Selection'
    }
];

export const MANUALSELECTION_ROUTING = RouterModule.forChild(MANUALSELECTION_ROUTES);
