// This file was generated from the view scaffold
// Copyright 2016

import {EventEmitter, SimpleChange, Input, Component} from '@angular/core';
import {ActivatedRoute, Router}  from '@angular/router';
import template from './ExceptionList.html';
import styles from './ExceptionList.less';
import Client from '../../api/lrs.api.v1.generated';
import {ReviewerSvc, ModalSvc, UserSvc, HqAdminGuard} from '../../app/services';
import WorkloadProvider from '../../workload/services/WorkloadProvider';
import CrossFilterBaseView from '../../shared/components/CrossFilterBaseView';
import CrossFilterService from '../../app/services/CrossFilterService';
import SortableReviewTable from '../../shared/components/SortableReviewTable';

import {
    EXCEPTION_TYPE,
    SERVER_ERROR,
    STATUS_CODE,
    REVIEWER_REVIEW_TYPES,
    CASE_SELECTION_REASONS
} from '../../shared/constants';
import {Observable} from 'rxjs/Observable';
import Utils from '../../shared/Utils';
import _ from 'lodash';
import { INDEMNIFICATION_TYPES } from '../../review/constants';
import ArrayUtils from '../../shared/utils/ArrayUtils';

const ESCALATION_PATHS = {
    MGMT: 'managementDecision',
    INDEM: 'forcedIndem',
    MRB: 'initiateMrb'
};

const BULK_CANCEL_MAX = 2000;

@Component({
    selector: 'exception-list',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/guide/router.html
 */
export default class ExceptionList {
    ESCALATION_PATHS = ESCALATION_PATHS;
    _route: ActivatedRoute;
    _client: Client;
    _reviewerSvc: ReviewerSvc;
    _hqAdminGuard: HqAdminGuard;
    _userSvc: UserSvc;
    _modalSvc: ModalSvc;
    _crossFilterService: CrossFilterService;
    _workloadProvider: WorkloadProvider;

    _newDistributeData = {
        isOpen: false,
        exception: {},
        locations: []
    };

    _newBatchInfoData = {
        isOpen: false,
        exception: {batchInfo: {}},
        batchOwners: [],
        batchTeamMembers: []
    };

    _newAssignmentData = {
        isOpen: false,
        exception: {},
        reviewers: []
    };

    _newRequestErrorData = {
        isOpen: false,
        exception: {}
    };

    _newEscalationData = {
        isOpen: false,
        exception: {}
    };

    /**
     * Default name for the class, passed by reference via RouteParams
     */
    name: string = 'ExceptionList';

    /**
     * Any new subscription will be added into this collection
     * @type {Array}
     */
    subscriptions: Array = [];

    selectionReasons: Array = [];

    exceptionTypes: Array = [];

    locations: Array = null;

    locationsCapacities: Array = null;

    reviewers: Array = null;

    batchOwners: Array = [];

    batchTeamMembers: Array = [];

    exceptions: Array = [];
    exceptionsObservable: Observable = null;
    exceptionsEventEmitter: EventEmitter = null;

    filteredExceptions: Array = [];
    sortedFilteredExceptions: Array = [];

    distributeData = Object.assign({}, this._newDistributeData);

    batchInfoData = Object.assign({}, this._newBatchInfoData);

    assignmentData = Object.assign({}, this._newAssignmentData);

    requestErrorData = Object.assign({}, this._newRequestErrorData);

    escalationData = Object.assign({}, this._newEscalationData);

    selectedIndemTerm: String = '';

    _router: Router;

    exceptionType = EXCEPTION_TYPE;

    indemnificationTypes: Array = [];

    escalationPaths = ESCALATION_PATHS;

    canBulkCancel: Boolean = false;
    bulkCancelInProgress: Boolean = false;

    @Input() pageSize: Number = 100;
    pageNumber = 1;


    @Input() exceptions: Array = [];

    disableForceIndem: Boolean = false;


    initCrossFilters() {

        this.filterDimensions = [
            {
                title: 'Case Number Search',
                controlType: 'text',
                dimension: (review) => (review.caseNumber ? review.caseNumber.replace('-', '') : '')
            }, {
                title: 'EXCEPTION TYPE',
                controlType: 'button',
                dimension: (exception) => exception.exceptionType
            }, {
                title: 'SELECTION REASON',
                controlType: 'button',
                dimension: (exception) => exception.selectionReason
            }, {
                title: 'REVIEW TYPE',
                controlType: 'button',
                dimension: (exception) => exception.reviewType || 'N/A'
            }, {
                title: 'AGE',
                controlType: 'button',
                dimension: (exception) => {
                    if ( exception.daysOnList <= 3 ) {
                        return '3 Days';
                    } else if ( exception.daysOnList <= 7 ) {
                        return '4-7 Days';
                    } else {
                        return 'Over 1 Week';
                    }
                },
                sort: CrossFilterBaseView.createSortFunctionFromArray(['3 Days', '4-7 Days', 'Over 1 Week'])
            }
        ];
    }


    /**
     * @param {ActivatedRoute} route Active route
     * @param {Client} client Instance of client services
     * @param {ReviewerSvc} reviewerSvc class
     * @param {UserSvc} userSvc class
     * @param {HqAdminGuard} hqAdminGuard class
     * @param {ModalSvc} modalSvc class
     * @param {CrossFilterService} crossfilterService class
     * @param {Router} router class
     * @param {WorkloadProvider} workloadProvider class
     * */
    constructor(route: ActivatedRoute, client: Client, reviewerSvc: ReviewerSvc, userSvc: UserSvc, hqAdminGuard: HqAdminGuard, modalSvc: ModalSvc, crossfilterService: CrossFilterService, router: Router, workloadProvider: WorkloadProvider) {
        this._route = route;
        this._client = client;
        this._reviewerSvc = reviewerSvc;
        this._modalSvc = modalSvc;
        this._crossFilterService = crossfilterService;
        this._router = router;
        this.indemnificationTypes = ArrayUtils.toArray(INDEMNIFICATION_TYPES);
        this.exceptionsEventEmitter = new EventEmitter();
        this.exceptionsObservable = this.exceptionsEventEmitter.asObservable();
        this._workloadProvider = workloadProvider;
        this._hqAdminGuard = hqAdminGuard;
        this._userSvc = userSvc;
    }

    ngOnInit() {
        this.subscriptions.push(this._route.params.subscribe(params => {
            this.name = params['name'];
        }));

        this._client.resources.dictionary.selectionReasons.get().first()
            .subscribe(selectionReasons => {
                this.selectionReasons = selectionReasons;
            });

        this._client.resources.dictionary.exceptionTypes.get().first()
            .subscribe(exceptionTypes => {
                this.exceptionTypes = exceptionTypes;
            });

        this.initCrossFilters();
        this.subscriptions.push(this._crossFilterService.resultsChanged.subscribe((exceptions) => {
            this.filteredExceptions = exceptions;

            // Default sort
            this.sort('daysOnList', SortableReviewTable.DESCENDING);

            // determine if it should be allowable to bulk-cancel this filtered set of exceptions.
            // to be allowed to bulk-cancel, everything needs to have the same exception tyoe
            // and that exception type neds to be in our whitelist of things we're ok not forcing
            // a human to look at before cancelling the review
            let exceptionTypes = _.unique(_.pluck(this.filteredExceptions, 'exceptionTypeCode'));
            if (exceptionTypes.length === 1 && this._hqAdminGuard.canActivate() ) {
                this.canBulkCancel = [
                    EXCEPTION_TYPE.BINDER_REQUEST_ERROR,
                    EXCEPTION_TYPE.LOAN_SELECTION_DISTRIBUTION
                ].indexOf(exceptionTypes[0]) !== -1;
            } else {
                this.canBulkCancel = false;
            }

        }));

        this._client.resources.exceptions.get().first().subscribe( (result) => {
            this.exceptionsEventEmitter.emit(new SimpleChange(this.exceptions, result));
            this.exceptions = result;
        });
    }

    ngOnDestroy() {
        this.subscriptions.forEach(subscription => {
            subscription.unsubscribe();
        });
    }

    sort(columnName, direction = null) {
        this.sortedFilteredExceptions = SortableReviewTable.sortItems(this.filteredExceptions, columnName, columnName, direction);
    }

    /**
     * Determine which modal to open and manage the exception
     * @param {api.v1.exeptions.Exception} exception to manage
     * @returns {null} no return
     * */
    resolve(exception) {
        switch (exception.exceptionTypeCode) {
            case EXCEPTION_TYPE.LOAN_SELECTION_DISTRIBUTION:
                // this should open up a model with a list of review locations to assign to
                this.openDistribute(exception);
                break;
            case EXCEPTION_TYPE.BATCH_DISTRIBUTION:
                // this should do the same thing as above: a modal with review locations
                this.openDistribute(exception);
                break;
            case EXCEPTION_TYPE.BATCH_ASSIGNMENT:
                this.openBatchInfo(exception);
                break;
            case EXCEPTION_TYPE.REVIEW_LEVEL_ASSIGNMENT:
                this.openAssignment(exception);
                break;
            case EXCEPTION_TYPE.HQ_ESCALATION:
                this.openEscalation(exception);
                break;
            case EXCEPTION_TYPE.BINDER_REQUEST_ERROR:
            case EXCEPTION_TYPE.BINDER_REQUEST_PAST_DUE:
                this.openRequestError(exception);
                break;
            case EXCEPTION_TYPE.VETTING_ACKNOWLEDGEMENT:
                // TODO: vetting is just displaying that the review level needs vetting ack; no actions
                break;
            default:
                break;
        }
    }

    _hideAllModals() {
        // closes modal
        this.resetDistributeData();
        this.batchInfoData.isOpen = false;
        this.assignmentData.isOpen = false;
        this.requestErrorData.isOpen = false;
        this.escalationData.isOpen = false;
    }

    cancel(exception) {
        this._hideAllModals();
        let cancelObjectType = exception.batchInfo !== null ? 'Batch' : exception.reviewId !== null ? 'Review' : 'Selection';

        this._modalSvc.askForConfirmation(
            'Are you sure you want to cancel this ' + cancelObjectType + '?',
            'Confirm Cancellation'
        ).subscribe( () => {
            this.onCancelException(exception);
        }, _.noop);
    }

    onCancelException(exception) {
        this._client.resources.exceptions.exceptionId(exception.exceptionId).delete().first().subscribe();
        this._remove(exception);
    }

    bulkCancel() {

        if (!this.canBulkCancel) {
            throw new Error('Bulk resolve not allowed in this state');
        }

        let exceptionsToCancel = this.sortedFilteredExceptions.length <= BULK_CANCEL_MAX ?
            this.sortedFilteredExceptions : this.sortedFilteredExceptions.slice(0, BULK_CANCEL_MAX);
        let total =  exceptionsToCancel.length;

        this._hideAllModals();

        this._modalSvc.askForConfirmation(
            'This cannot be undone and may take a while to complete.',
            `Bulk Cancel These ${total} Reviews?`
        ).subscribe(
            () => {
                this.bulkCancelInProgress = true;
                console.log('Beginning bulk cancellation of exceptions');
                let count = 0;

                [...exceptionsToCancel].forEach( (ex) => {
                    _.defer(() => { // defer so the browser can show the service-call-in-progress loading screen and not seem hung in a tight loop
                        console.log(`Cancelling ${++count} of ${total}`);
                        this.onCancelException(ex);

                        if (count === total) {
                            this.bulkCancelInProgress = false;
                            console.log('Done!');
                            this.exceptionsEventEmitter.emit(new SimpleChange([], this.exceptions)); // update the filters (which will in turn update the grid)
                        }

                    });
                });

            }, _.noop);
    }

    save(exception) {
        this._client.resources.exceptions.exceptionId(exception.exceptionId).put(exception).first()
            .subscribe();

        this._remove(exception);

        // closes modal
        this._hideAllModals();
    }

    openDistribute(exception) {
        let locationsSource = this.locations ?
            Observable.of(this.locations) :
            this._client.resources.locations.get().first();

        let locationsCapacitySource = this._client.resources.capacity.locations.get().first();

        Observable.forkJoin(
            locationsSource,
            locationsCapacitySource)
            .subscribe(t => {
                this.locations = t[0];
                this.locationsCapacities = t[1];

                let locationsCapacitiesDict = {};
                this.locationsCapacities.forEach(lc => {
                    locationsCapacitiesDict[lc.id] = lc;
                });

                this.distributeData.locations = this.locations
                    .map(location => {
                        let locationCapacity = locationsCapacitiesDict[location.locationId];
                        return Object.assign({}, location, {
                            remainingCapacity: locationCapacity ? locationCapacity.availableCapacity : 0
                        });
                    });
                this.distributeData.exception = exception;
                this.distributeData.distributeType = exception.batchInfo !== null ? 'Batch' : 'Selection';
                this.distributeData.isOpen = true;
            });
    }

    resetDistributeData() {
        this.distributeData = Object.assign({}, this._newDistributeData);
    }

    openBatchInfo(exception) {
        let reviewersSource = this.reviewers ?
            Observable.of(this.reviewers) :
            this._reviewerSvc.getReviewers().first()
                .map(reviewers => {
                    return reviewers.filter(r => r.statusCode === STATUS_CODE.ACTIVE);
                });

        reviewersSource
            .subscribe(reviewers => {
                this.reviewers = reviewers;
                this.loadBatchReviewers();

                this.batchInfoData.batchOwners = this.batchOwners;
                this.batchInfoData.batchTeamMembers = this.batchTeamMembers;
                this.batchInfoData.exception = exception;
                this.batchInfoData.exception.batchInfo = this.batchInfoData.exception.batchInfo || {};
                this.batchInfoData.isOpen = true;
            });
    }

    resetBatchInfoData() {
        this.batchInfoData = Object.assign({}, this._newBatchInfoData);
    }

    openAssignment(exception) {
        this._workloadProvider.getQualifiedReviewers(exception.reviewId)
            .subscribe(reviewers => {
                this.assignmentData.reviewers = reviewers;
                this.assignmentData.exception = exception;
                this.assignmentData.isOpen = true;
            }, error => {
                if (error.status === SERVER_ERROR.GONE) {
                    let errors = Utils.getErrorMessages(error);
                    let errorMessage = '';
                    if (errors && errors.length) {
                        errorMessage = errors
                            .map(e => {
                                return e;
                            }).join(', ');
                    }
                    else {
                        errorMessage = 'No active review level for reviewId ' + exception.reviewId;
                    }

                    this._modalSvc.acknowledge(errorMessage, 'Error').first().subscribe();
                }
                else {
                    throw error;
                }
            });
    }

    resetAssignmentData() {
        this.assignmentData = Object.assign({}, this._newAssignmentData);
    }

    /**
     * The Exception Type will be HQ Escalation and the pop up for the HQ Admin will include
     * the options to execute Final Decisions (MGMT Decision, Force Indem - only for non operational review, Initiate MRB).
     *
     * @param {api.v1.exceptions.Exception} exception to escalate
     * @returns {null} no return
     * */
    openEscalation(exception) {
        this.disableForceIndem = Utils.isReviewLevelOperational(exception);
        this.escalationData.exception = exception;
        this.escalationData.isOpen = true;
    }

    /**
     * Choose course for escalation and close escalation modal
     * @param {path} path to take with exception (see above)
     * @param {api.v1.exceptions.Exception} exception to escalate
     * @returns {null} no return
     * */
    manageEscalation(path, exception) {
        if (path === ESCALATION_PATHS.MGMT) {
            this._remove(exception);
            this._client.resources.reviews.reviewId(exception.reviewId).managementDecision.post().first()
                .subscribe();
        } else if (path === ESCALATION_PATHS.INDEM) {
            let reviewLevelInfo = {
                indemnificationTypeCode: this.selectedIndemTerm
            };
            this._client.resources.reviews.reviewId(exception.reviewId).level.reviewLevelId(exception.currentReviewLevel.reviewLevelId).indemnification.reviewerForceSubmit.post(reviewLevelInfo).first()
                .subscribe(() => {
                    this._router.navigate([`/review/${exception.reviewId}/reviewIndemnification`]);
                });
        } else if (path === ESCALATION_PATHS.MRB) {
            this._remove(exception);
            this._client.resources.reviews.reviewId(exception.reviewId).mrbReferral.post().first()
                .subscribe();
        }

        this.escalationData.isOpen = false;
    }

    openRequestError(exception) {
        this.requestErrorData.exception = exception;
        this.requestErrorData.isOpen = true;
    }

    resetRequestErrorData() {
        this.requestErrorData = Object.assign({}, this._newRequestErrorData);
    }

    loadBatchReviewers() {
        this.loadBatchOwners();
        this.loadTeamMembers();
    }

    /**
     * Load the batch owners with an operation review type
     * @returns {null} no return
     */
    loadBatchOwners() {
        let reviewersByLoc = this._getReviewersByLocation(this.reviewers);
        this.batchOwners = reviewersByLoc.filter(reviewer => {
            return reviewer.reviewTypes && reviewer.reviewTypes.length &&
                reviewer.reviewTypes.some((rt) => rt.assignmentTypeCode === REVIEWER_REVIEW_TYPES.OPERATIONAL);
        });
    }

    /**
     * Load the batch team members with the lender self report reason
     * @returns {null} no return
     */
    loadTeamMembers() {
        let reviewersByLoc = this._getReviewersByLocation(this.reviewers);
        this.batchTeamMembers = reviewersByLoc.filter(reviewer => {
            return reviewer.selectionReasons && reviewer.selectionReasons.length &&
                reviewer.selectionReasons.some((sr) => sr.assignmentTypeCode === CASE_SELECTION_REASONS.LENDER_SELF_REPORT);
        });
    }

    _remove(exception) {
        let index;

        index = this.exceptions.findIndex(e => e.exceptionId === exception.exceptionId);
        this.exceptions.splice(index, 1);

        // for performance, if we're in the middle of doing a bulk-cancel, don't broadcast each change.
        // the bulkCancel function will broadcast a single change once it is done
        if (!this.bulkCancelInProgress) {
            this.exceptionsEventEmitter.emit(new SimpleChange([], this.exceptions));
        }
    }

    _getReviewersByLocation(reviewers) {
        // hq admins are not restricted by location
        if (this._hqAdminGuard.canActivate()) {
            return reviewers;
        }

        return reviewers.filter(reviewer => reviewer.locationId === this._userSvc.locationId);
    }
}
