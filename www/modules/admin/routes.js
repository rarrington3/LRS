// This file was generated from the module scaffold
// Copyright 2016

import {Routes, RouterModule} from '@angular/router';
import {AdminViewGuard, HqAdminGuard} from '../app/services/AuthGuards';
import SaveGuard from '../app/services/SaveGuard';
import {
    BaseView,
    ReviewLevelLenderResponses,
    ReviewLevelReviewerDeadlines,
    EditReviewLocation,
    LenderIncreasedSelection,
    ReviewLocations,
    ModelAdminList,
    ModelAdminEdit,
    LenderSuppression,
    QaModels,
    QaModel,
    QaModelDefectArea,
    QaModelAttributes,
    QaModelDefectAreaAttributes,
    QaModelDefectAreaSources,
    QaModelDefectAreaSeverities,
    QaModelDefectAreaQaTree,
    QaModelDefectAreaPreQual,
    QaModelDefectAreaRemediation,
    QaModelEdit,
    QaModelAttributesEdit,
    QaModelDefectAreaAttributesEdit,
    QaModelDefectAreaSourcesEdit,
    QaModelDefectAreaSeveritiesEdit,
    QaModelDefectAreaQaTreeEdit,
    QaModelDefectAreaPreQualEdit,
    QaModelDefectAreaRemediationEdit,
    Workflow,
    EmailTemplateList,
    EmailTemplateEdit
} from './views';

export const ADMIN_ROUTES: Routes = [
    {
        path: 'admin',
        component: BaseView,
        canActivate: [AdminViewGuard],
        navigationBarEntry: 'Admin',
        children: [
            {
                path: '',
                redirectTo: 'workflow'
            },
            {
                path: 'reviewLevels',
                redirectTo: 'reviewLevels/lenderResponses',
                pathMatch: 'full',
                canDeactivate: [SaveGuard]
            },
            {
                path: 'reviewLevels/lenderResponses',
                component: ReviewLevelLenderResponses,
                canActivate: [HqAdminGuard],
                canDeactivate: [SaveGuard]
            },
            {
                path: 'reviewLevels/reviewerDeadlines',
                component: ReviewLevelReviewerDeadlines,
                canActivate: [HqAdminGuard],
                canDeactivate: [SaveGuard]
            },
            {
                path: 'reviewLocations',
                component: ReviewLocations,
                canActivate: [HqAdminGuard]
            },
            {
                path: 'editReviewLocation/:locationId',
                component: EditReviewLocation,
                canActivate: [HqAdminGuard]
            },
            {
                path: 'addReviewLocation',
                component: EditReviewLocation,
                canActivate: [HqAdminGuard]
            },
            {
                path: 'modelManagement/selectionModels',
                component: ModelAdminList,
                canActivate: [HqAdminGuard],
                data: {modelType: 'selection'}
            },
            {
                path: 'modelManagement/assignmentModels',
                component: ModelAdminList,
                canActivate: [HqAdminGuard],
                data: {modelType: 'assignment'}
            },
            {
                path: 'modelManagement/distributionModels',
                component: ModelAdminList,
                canActivate: [HqAdminGuard],
                data: {modelType: 'distribution'}
            },
            {
                path: 'modelManagement/editModel',
                component: ModelAdminEdit,
                canActivate: [HqAdminGuard]
            },
            {
                path: 'modelManagement/qaModels',
                component: QaModels,
                canActivate: [HqAdminGuard]
            },
            {
                path: 'modelManagement/qaModel/:qaModelId',
                component: QaModel,
                canActivate: [HqAdminGuard]
            },
            {
                path: 'modelManagement/qaModelAttributes/:qaModelId',
                component: QaModelAttributes,
                canActivate: [HqAdminGuard]
            },
            {
                path: 'modelManagement/qaModelDefectArea/:qaModelId/:defectAreaId',
                component: QaModelDefectArea,
                canActivate: [HqAdminGuard],
                data: {mode: 'view'},
                children: [
                    {
                        path: 'attributes',
                        component: QaModelDefectAreaAttributes,
                        data: {tab: 'attributes'}
                    },
                    {
                        path: 'sources',
                        component: QaModelDefectAreaSources,
                        data: {tab: 'sources'}
                    },
                    {
                        path: 'severities',
                        component: QaModelDefectAreaSeverities,
                        data: {tab: 'severities'}
                    },
                    {
                        path: 'qaTree',
                        component: QaModelDefectAreaQaTree,
                        data: {tab: 'qaTree'}
                    },
                    {
                        path: 'preQual',
                        component: QaModelDefectAreaPreQual,
                        data: {tab: 'preQual'}
                    },
                    {
                        path: 'remediation',
                        component: QaModelDefectAreaRemediation,
                        data: {tab: 'remediation'}
                    }
                ]
            },
            {
                path: 'modelManagement/editQaModel/:qaModelId',
                component: QaModelEdit,
                canActivate: [HqAdminGuard],
                canDeactivate: [SaveGuard]
            },
            {
                path: 'modelManagement/editQaModelAttributes/:qaModelId',
                component: QaModelAttributesEdit,
                canActivate: [HqAdminGuard]
            },
            {
                path: 'modelManagement/editQaModelDefectArea/:qaModelId/:defectAreaId',
                component: QaModelDefectArea,
                canActivate: [HqAdminGuard],
                data: {mode: 'edit'},
                children: [
                    {
                        path: 'attributes',
                        component: QaModelDefectAreaAttributesEdit,
                        canDeactivate: [SaveGuard],
                        data: {tab: 'attributes'}
                    },
                    {
                        path: 'sources',
                        component: QaModelDefectAreaSourcesEdit,
                        canDeactivate: [SaveGuard],
                        data: {tab: 'sources'}
                    },
                    {
                        path: 'severities',
                        component: QaModelDefectAreaSeveritiesEdit,
                        canDeactivate: [SaveGuard],
                        data: {tab: 'severities'}
                    },
                    {
                        path: 'qaTree',
                        component: QaModelDefectAreaQaTreeEdit,
                        canDeactivate: [SaveGuard],
                        data: {tab: 'qaTree'}
                    },
                    {
                        path: 'preQual',
                        component: QaModelDefectAreaPreQualEdit,
                        canDeactivate: [SaveGuard],
                        data: {tab: 'preQual'}
                    },
                    {
                        path: 'remediation',
                        component: QaModelDefectAreaRemediationEdit,
                        canDeactivate: [SaveGuard],
                        data: {tab: 'remediation'}
                    }
                ]
            },
            {
                path: 'lenderIncreaseSelection',
                component: LenderIncreasedSelection,
                canActivate: [HqAdminGuard],
                data: {lenderType: 'lender'}
            },
            {
                path: 'underWriterIncreaseSelection',
                component: LenderIncreasedSelection,
                canActivate: [HqAdminGuard],
                data: {lenderType: 'underwriter'}
            },
            {
                path: 'lenderSuppression',
                component: LenderSuppression,
                canActivate: [HqAdminGuard]
            },
            {
                path: 'workflow',
                component: Workflow
            },
            {
                path: 'emailTemplates',
                component: EmailTemplateList,
                canActivate: [HqAdminGuard]
            },
            {
                path: 'editEmailTemplate',
                component: EmailTemplateEdit,
                canActivate: [HqAdminGuard]
            }
        ]
    }
];

export const ADMIN_ROUTING = RouterModule.forChild(ADMIN_ROUTES);
