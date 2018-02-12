// This file was generated from the module scaffold
// Copyright 2016

import { Routes, RouterModule } from '@angular/router';
import {
    ActiveReviews,
    BinderRequest,
    SelfReport,
    LenderReviewContainer,
    FindingsOverview,
    ReviewResponse,
    IndemnificationRequest,
    LenderReviewBatch
} from './views';

import { LenderWorkload, LenderBatchesWorkload, LenderReviewsCompleted, LenderReviewsCompletedContainer, LenderCompletedBatches } from './components';
import {ResponseCoordinatorGuard, BinderRequestGuard, LenderGuard} from '../app/services/AuthGuards';

export const LENDER_ROUTES: Routes = [{
    path: 'lender',
    redirectTo: 'lender/activeReviews',
    pathMatch: 'full',
    canActivate: [LenderGuard]
},
    {
        path: 'lender/activeReviews',
        component: ActiveReviews,
        children: [{
            path: '',
            redirectTo: 'summary',
            pathMatch: 'full'
        },
            {
                path: 'summary',
                component: LenderWorkload
            },
            {
                path: 'batches',
                pathMatch: 'full',
                component: LenderBatchesWorkload
            },

            { path: 'batches/:batchId',
                component: LenderBatchesWorkload
            }],
        canActivate: [LenderGuard],
        navigationBarEntry: 'Active Reviews'
    },
    {
        path: 'lender/activeReviews/review/:reviewId',
        component: LenderReviewContainer,
        children: [{
            path: '',
            redirectTo: 'findingsOverview'
        }, {
            path: 'findingsOverview',
            component: FindingsOverview
        }, {
            path: 'finding/:findingId/reviewResponse',
            component: ReviewResponse
        }, {
            path: 'indemnificationRequest',
            component: IndemnificationRequest
        }]
    },
    {
        path: 'lender/binderRequest',
        component: BinderRequest,
        canActivate: [ResponseCoordinatorGuard],
        navigationBarEntry: 'Binder Request'
    },
    {
        path: 'binderRequest',
        component: BinderRequest,
        canActivate: [BinderRequestGuard],
        navigationBarEntry: 'Binder Request'
    },
    {
        path: 'lender/selfReport',
        component: SelfReport,
        canActivate: [ResponseCoordinatorGuard],
        navigationBarEntry: 'Create Self-Report'
    },

    {
        path: 'lender/batch/:batchId',
        component: LenderReviewBatch,
        canActivate: [ResponseCoordinatorGuard]
    },

    {
        path: 'lender/complete',
        navigationBarEntry: 'Completed Reviews',
        component: LenderReviewsCompletedContainer,
        canActivate: [LenderGuard],
        children: [
            {   path: '',
                redirectTo: 'reviews',
                pathMatch: 'full'
            },
            {
                path: 'reviews',
                component: LenderReviewsCompleted,
                canActivate: [LenderGuard],
                navigationBarEntry: 'Completed Reviews'
            },

            { path: 'batches',
                component: LenderCompletedBatches,
                canActivate: [LenderGuard] },
            { path: 'batches/:batchId',
                component: LenderCompletedBatches,
                canActivate: [LenderGuard]
            }

        ]
    }


];
export const LENDER_ROUTING = RouterModule.forChild(LENDER_ROUTES);
