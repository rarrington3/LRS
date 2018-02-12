// This file was generated from the module scaffold
// Copyright 2016

import {Routes, RouterModule} from '@angular/router';
import {ExceptionList} from './views';
import {ExceptionsGuard} from '../app/services/AuthGuards';

export const EXCEPTIONS_ROUTES: Routes = [
    {
        path: 'exceptionList',
        component: ExceptionList,
        canActivate: [ExceptionsGuard],
        navigationBarEntry: 'Exceptions'
    }
];

export const EXCEPTIONS_ROUTING = RouterModule.forChild(EXCEPTIONS_ROUTES);
