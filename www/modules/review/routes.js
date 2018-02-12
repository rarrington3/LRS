import { Routes, RouterModule } from '@angular/router';
import {
        ReviewContainer,
        ReviewContainerInactive,
        LoanSummary,
        QuestionAndAnswerTree,
        EditFindingSourceCause,
        InitialFindingDetails,
        EditAdhocFindingSourceCause,
        NonInitialFindingDetails,
        ReviewWrapUp,
        ReviewIndem

} from './components';
import { ReviewerOrAdminGuard } from '../app/services/AuthGuards';

export const REVIEW_ROUTES: Routes = [
    {
        path: '',
        redirectTo: 'workload',
        pathMatch: 'full'
    },
    {
        path: 'review',
        component: ReviewContainerInactive,
        canActivate: [ReviewerOrAdminGuard],
        navigationBarEntry: 'Loan Review'
    },
    {
        path: 'review/:reviewId',
        component: ReviewContainer,
        canActivate: [ReviewerOrAdminGuard],
        children: [
            {
                path: '',
                redirectTo: 'summary',
                pathMatch: 'full'
            },
            {
                path: 'summary',
                component: LoanSummary
            },
            {
                path: 'tree/:area',
                component: QuestionAndAnswerTree
            },
            {
                path: 'tree/:area/question/:questionId/sourcecause',
                component: EditFindingSourceCause
            },
            {
                path: 'tree/:area/finding/:findingId/initialdetails',
                component: InitialFindingDetails
            },
            {
                path: 'tree/:area/finding/:findingId/noninitialdetails',
                component: NonInitialFindingDetails
            },
            {
                path: 'tree/:area/sourcecause',
                component: EditAdhocFindingSourceCause
            },

            {
                path: 'wrapup',
                component: ReviewWrapUp
            },
            {
                path: 'reviewIndemnification',
                component: ReviewIndem
            }
        ]
    }
];

export const REVIEW_ROUTING = RouterModule.forRoot(REVIEW_ROUTES);
