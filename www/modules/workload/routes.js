import { Routes, RouterModule } from '@angular/router';
import {
    WorkloadContainer,
    MyTeamWorkload,
    MyWorkload,
    WorkloadBatches,
    WorkloadCompletedBatches,
    ReviewsCompletedContainer,
    ReviewsCompletedMine,
    ReviewsCompletedTeam
} from './components';
import { ReviewerOrAdminGuard, AdminGuard  } from '../app/services/AuthGuards';

export const WORKLOAD_ROUTES: Routes = [
    {
        path: 'workload',
        component: WorkloadContainer,
        children: [
            { path: '',
                redirectTo: 'summary',
                pathMatch: 'full'},
            { path: 'summary',
                component: MyWorkload },
            { path: 'team',
                component: MyTeamWorkload,
                canActivate: [AdminGuard] },
            { path: 'batches',
                component: WorkloadBatches,
                canActivate: [ReviewerOrAdminGuard] },
            { path: 'batches/:batchId',
                component: WorkloadBatches,
                canActivate: [ReviewerOrAdminGuard]},
            { path: 'batches/location/:locationId',
                component: WorkloadBatches,
                canActivate: [ReviewerOrAdminGuard] },
            { path: 'batches/location/:locationId/batch/:batchId',
                component: WorkloadBatches,
                canActivate: [ReviewerOrAdminGuard] }
        ],
        canActivate: [ReviewerOrAdminGuard],
        navigationBarEntry: 'My Workload'},
    {
        path: 'reviews/complete',
        navigationBarEntry: 'Completed Reviews',
        component: ReviewsCompletedContainer,
        canActivate: [ReviewerOrAdminGuard],
        children: [
            { path: '',
                redirectTo: 'myreviews',
                pathMatch: 'full'},
            { path: 'myreviews',
                component: ReviewsCompletedMine,
                canActivate: [ReviewerOrAdminGuard]},
            { path: 'team',
                component: ReviewsCompletedTeam,
                canActivate: [ReviewerOrAdminGuard] },
            { path: 'batches',
                component: WorkloadCompletedBatches,
                canActivate: [ReviewerOrAdminGuard] },
            { path: 'batches/:batchId',
                component: WorkloadCompletedBatches,
                canActivate: [ReviewerOrAdminGuard]},
            { path: 'batches/location/:locationId',
                component: WorkloadCompletedBatches,
                canActivate: [ReviewerOrAdminGuard] },
            { path: 'batches/location/:locationId/batch/:batchId',
                component: WorkloadCompletedBatches,
                canActivate: [ReviewerOrAdminGuard] }
        ]
    }
];

export const WORKLOAD_ROUTING = RouterModule.forRoot(WORKLOAD_ROUTES);
