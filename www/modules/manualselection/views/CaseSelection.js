import {Component} from '@angular/core';
import {ActivatedRoute}  from '@angular/router';
import template from './CaseSelection.html';
import styles from './CaseSelection.less';
import Client from '../../api/lrs.api.v1.generated';
import ManualSelectionService from '../services/ManualSelectionService';
import {ReviewerSvc, ModalSvc, UserSvc} from '../../app/services';
import LocationSvc from '../../app/services/LocationSvc';
import {REVIEWER_REVIEW_TYPES, CASE_SELECTION_REASONS, STATUS_CODE} from '../../shared/constants';
import Utils from '../../shared/Utils';
import {HqAdminGuard} from '../../app/services/AuthGuards';
import {Observable} from 'rxjs/Observable';
import _ from 'lodash';

@Component({
    selector: 'case-selection',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/guide/router.html
 */
export default class CaseSelection {

    /**
     * Establish a client in order to use http services
     * @type {Client}
     * */
    _client: Client;

    /**
     * Service for all selections for review
     * @type {ManualSelectionService} Service for selection
     * */
    _selectionService: ManualSelectionService;

    /**
     * Service for displaying modals
     * @type {UserSvc}
     * */
    _userSvc: UserSvc;

    /**
     * Service for fetching all locations
     * @type {LocationSvc}
     * */
    _locationSvc: LocationSvc;

    /**
     * Service for batch information from reviewers
     * @type {ReviewerSvc} service
     * */
    _reviewerSvc: ReviewerSvc;

    /**
     * @type {HqAdminGuard} _hqAdminGuard for checking logged in user
     * */
    _hqAdminGuard:HqAdminGuard;

    /**
     * @type {Array} flattened collection of all reviewers
     * */
    reviewers: Array = [];

    /**
     * Bound reviewers in view batch owner drop down
     * @type {Array} flattened collection of all reviewers
     * */
    batchOwners: Array = [];

    /**
     * Bound reviewers in view batch team member drop down
     * @type {Array} flattened collection of all reviewers
     * */
    batchTeamMembers: Array = [];

    /**
     * Service for displaying modals
     * @type {ModalSvc} Service for selection
     * */
    _modalSvc: ModalSvc;

    /**
     * @type {ActivatedRoute}
     * */
    route: ActivatedRoute;

    /**
     * Default name for the class, passed by reference via RouteParams
     * @type {String}
     */
    name: string = 'CaseSelection';

    /**
     * Any new subscription to services will be added into this collection
     * @type {Array}
     */
    subscriptions: Array = [];

    /**
     * @type {String} holds the collection of comma delimited case numbers to search for
     * */
    searchCaseNumbers: String = null;

    /**
     * @type {Array} holds the collection of cases that aren't grouped
     * */
    _sourceCases: Array = [];

    /**
     * @type {Array} holds an array of cases searched and grouped by lender
     * */
    resultingCases: Array = [];

    /**
     * @type {Boolean} true whenever a search is done and no results are found for case number(s)
     * */
    noSearchResults: Boolean = false;

    /**
     * @type {String} error messages returned from server
     * */
    searchErrorMessage: string = null;

    /**
     * @type {Object} holds the filters for the current search
     * */
    caseFilter: Object = {};

    /* Filters for the drop downs in the view
     * */
    selectionReasons: Array = [];

    selectionSubReasons: Array = [];

    reviewTypes: Array = [];

    reviewLocations: Array = [];

    /**
     * If the reviewer's location is not HQ then they cannot change the review location
     * @type {Boolean}
     */
    userIsHqAdmin: Boolean = true;

    /**
     * Review types to restrict batch owner list
     * @type {Object} _reviewerReviewTypes enumeration
     * */
    _reviewerReviewTypes: Object = REVIEWER_REVIEW_TYPES;

    _approvedSelectionReasons: Object = CASE_SELECTION_REASONS;

    /**
     * @type {Boolean} subReasonDisabled
     * Maybe disabled based on selection reason
     * */
    subReasonDisabled: Boolean = false;

    /**
     * @type {Boolean} reviewTypeDisabled
     * Maybe disabled based on selection reason
     * */
    reviewTypeDisabled: Boolean = false;

    resultingCases: Array = [];

    SELECTION_REASONS: Object = CASE_SELECTION_REASONS;

    /**
     * @param {Client} client Instance of client services
     * @param {ActivatedRoute} route Active route
     * @param {ModalSvc} modalSvc class
     * @param {ReviewerSvc} reviewerSvc class
     * @param {ManualSelectionService} selectionService Service for selection
     * @param {UserSvc} userSvc class
     * @param {LocationSvc} locationSvc class
     * @param {HqAdminGuard} hqAdminGuard class
     * */
    constructor(client: Client, route: ActivatedRoute, modalSvc: ModalSvc, reviewerSvc: ReviewerSvc, selectionService: ManualSelectionService, userSvc: UserSvc, locationSvc: LocationSvc, hqAdminGuard: HqAdminGuard) {
        this._client = client;
        this._selectionService = selectionService;
        this._modalSvc = modalSvc;
        this._reviewerSvc = reviewerSvc;
        this._userSvc = userSvc;
        this._locationSvc = locationSvc;
        this.route = route;
        this._hqAdminGuard = hqAdminGuard;

        // persist filters settings on service
        this.caseFilter = this._selectionService.caseFilter;

    }

    /**
     * Retrieve data for filtering dropdowns.
     * Persist state of drop downs for reading when view is tabbed away from.
     * Locate the user's location and try to set it as the selected location in the UI.
     * Adjust drop downs based on selection reason.
     *
     * @returns {null} no return
     * */
    ngOnInit() {
        this.subscriptions.push(this.route.params.subscribe(params => {
            this.name = params['name'];
        }));

        this.subscriptions.push(this._selectionService.batchWasSubmittedObservable.subscribe(() => this.confirmBatchSubmission()));

        this._client.resources.dictionary.reviewTypes.get().first()
            .subscribe(reviewTypes => {
                this.reviewTypes = reviewTypes.filter((rt) => { return rt.code !== REVIEWER_REVIEW_TYPES.OPERATIONAL; });
            });

        this._client.resources.dictionary.selectionReasons.get().first().subscribe(reasons => {
            this.selectionReasons = reasons.filter(reason => { return this._reasonIsApproved(reason); });
        });

        this._client.resources.dictionary.selectionSubreasons.get().first().subscribe(selectionSubReasons => {
            this.selectionSubReasons = selectionSubReasons;
        });

        this._client.resources.locations.get().first()
            .subscribe(locations => {
                this.reviewLocations = locations;
            });

        this._reviewerSvc.getReviewers().first()
            .map(reviewers => {
                return reviewers.filter(r => r.statusCode === STATUS_CODE.ACTIVE);
            })
            .subscribe(reviewers => {
                this.reviewers = reviewers;
                this.loadBatchReviewers();
            });

        this.userIsHqAdmin = this._hqAdminGuard.canActivate();

        this.caseFilter.reviewLocation = this._userSvc.locationId;

        this._adjustCaseControls();
    }


    ngOnDestroy() {
        this.subscriptions.forEach(subscription => {
            subscription.unsubscribe();
        });
    }

    /**
     * Take the case look up results and make a collection of cases that are grouped by lender.
     * Each case's reviews (if any) and present the most recent one.
     * Assign an isBatch transient variable to each group (batch) for submitting for review.
     * Determine error messaging if any.
     *
     * @param {Object} caseResults results from case look up
     * @returns {null} updates this.resultingCases
     * */
    _sortAndPrepareCases(caseResults) {
        let self = this;

        // Add some metadata to help display if each case can be submitted
        caseResults.cases.forEach(function (caseResult) {
            caseResult.canSubmit = Utils.canSubmitCaseSelection(caseResult, self.caseFilter.reviewReason);
            Utils.sortCaseActivty(caseResult);
        });

        let existingCases = this._sourceCases;
        let mergedCases = existingCases.concat(caseResults.cases);

        // get all cases by lender id and case number
        this._sourceCases = _.uniq(mergedCases, function (c) {
            return String(c.lender.lenderId) && String(c.caseNumber);
        });

        // collect unique lenders by id to group by
        let uniqueLenders = _.uniq(this._sourceCases, function (c) {
            return c.lender.lenderId;
        });

        // build out new shallow array of cases grouped by lenders

        let newCaseGroups = [];
        existingCases = this._sourceCases;

        uniqueLenders.forEach(function (obj) {
            let group = {
                lenderId: obj.lender.lenderId,
                lenderName: obj.lender.name,
                cases: existingCases.filter(c => c.lender.lenderId === obj.lender.lenderId),
                // add a transient variable to each group of cases to track for submitting in batches
                isBatch: false,
                batchInfo: {
                    requestOperationalReview: false
                }
            };

            newCaseGroups.push(group);
        });

        // add new results to existing resultingCases for grouped display
        this.resultingCases = newCaseGroups;

        // clear case numbers searched for so it's less confusing
        this.searchCaseNumbers = null;

        // determine error messaging
        if (caseResults.errors && caseResults.errors.length) {
            this.noSearchResults = true;
            this.searchErrorMessage = caseResults.errors.join('<br/>');
        }
    }

    /**
     * Look up cases by case number(s) and selected reviewType bound to the text input in the view.
     * @returns {null} updates the current resultingCases collection
     * */
    lookUpCases() {
        let caseNums = this.searchCaseNumbers.split(/[\s,]+/);
        caseNums = caseNums.filter((caseNumber) => caseNumber.length && caseNumber !== '');
        // split case numbers by commas or spaces and send query

        this._client.resources.cases.search.post({
            caseNumbers: caseNums,
            reviewType: this.caseFilter.reviewType,
            selectionReason: this.caseFilter.reviewReason
        }).first().subscribe(result => this._sortAndPrepareCases(result));
    }

    /**
     * Find case in our collection to of lenders and their cases then remove locally
     * If deleting the case results in no more cases for that lender then
     * we remove the lender from the list in the UI
     *
     * @param {String} lenderId of lender with a case to be deleted
     * @param {api.vi.cases.Case} selectedCase case to be deleted
     * @returns {null} deletes case from group this.resultingCases
     * */
    deleteCase(lenderId, selectedCase) {
        this._modalSvc.askForConfirmation('Are you sure you want to remove case: ' + selectedCase.caseNumber + '?').first()
            .subscribe(
                () => {
                    let lenderIdx;
                    let caseIdx;
                    let lender = this.resultingCases.find(c => {
                        return c.lenderId === lenderId;
                    });

                    if (lender) {
                        caseIdx = lender.cases.indexOf(selectedCase);
                        if (caseIdx > -1) {
                            lender.cases.splice(caseIdx, 1);
                        }

                        // we also remove the case from the local un-grouped source case collection

                        caseIdx = this._sourceCases.indexOf(selectedCase);
                        if (caseIdx > -1) {
                            this._sourceCases.splice(caseIdx, 1);
                        }
                    }

                    if (lender && lender.cases.length < 1) {
                        this.resultingCases.forEach(function (l, i) {
                            if (l.lenderId === lenderId) {
                                lenderIdx = i;
                            }
                        });

                        if (lenderIdx > -1) {
                            this.resultingCases.splice(lenderIdx, 1);
                        }
                    }

                }, () => {
                }
            );
    }

    /**
     * Resets the view and clears the stored drop down data
     * @returns {null} resets case groups with no return
     * */
    resetCasesForReview() {
        this._sourceCases = [];
        this.resultingCases = [];
        this.searchCaseNumbers = null;
        this.noSearchResults = false;
        this.searchErrorMessage = null;
    }

    /**
     * Submit cases by for review in batches (1 or more) bound to the checkboxes in the view
     * @returns {null} submits case groups with no return
     * */
    submitCasesForReview() {
        // Filter out unsubmitable cases from each group
        this.resultingCases.forEach(function(resultingCase) {
            resultingCase.cases = resultingCase.cases.filter(caseObj => {
                return caseObj.canSubmit;
            });
        });
        // Remove any groups that have no cases left after filtering
        this.resultingCases = this.resultingCases.filter(resultingCase => {
            return resultingCase.cases.length;
        });
        this._selectionService.submitBatchReviews(this.resultingCases);
        this.resetCasesForReview();
    }

    /**
     * Confirm submission to user and reset view
     * @returns {null} no return
     * */
    confirmBatchSubmission() {
        if (this._selectionService.batchWasSubmitted) {
            this._modalSvc.acknowledge('The selected cases have been submitted for review.', 'Submission Successful').first()
                .subscribe(
                    () => {
                        this.resetCasesForReview();
                    }, () => {
                    }
                );
        }
    }

    /**
     * Perform case look up when Enter key is pressed
     * @param {Event} e key press event
     * @returns {null} submits case groups with no return
     * */
    keyEventHandler(e) {
        if (e.keyCode === 13) {
            this.lookUpCases();
        }
    }

    /**
     * Add Color for reviews that are in progress
     * @param {Object} item review object
     * @returns {string} class name
     */
    checkClass(item) {
        let foundStatus = '';
        if (item.reviews && item.reviews.length && item.reviews[0].status) {
            let status = item.reviews[0].status.toLowerCase();
            foundStatus = status.split(' ').join('-');
        }

        return foundStatus;
    }

    /**
     * Determines if case numbers can be submitted for searching.
     * Selection Reason and Review Type are required.
     *
     * @returns {Boolean} returns true or false
     * */
    cannotAddCases() {
        return !this.searchCaseNumbers || !this.caseFilter.reviewReason || !this.caseFilter.reviewType;
    }

    /**
     * Order is important
     * @returns {null} no return
     * */
    setSelectionReason() {
        if (this.caseFilter.reviewReason === this.caseFilter._prevReviewReason) { // check not coming from reset to avoid loop
            return;
        }

        this._checkForCaseReset()
            .subscribe(() => {
                this.caseFilter._prevReviewReason = this.caseFilter.reviewReason; // set previous
                this._adjustCaseControls(); // adjust controls for selection reason
                this.loadTeamMembers(); // load team members
            }, () => {
                this.caseFilter.reviewReason = this.caseFilter._prevReviewReason; // reset to previous
            });


    }

    /**
     * Order is important
     * @returns {null} no return
     * */
    setReviewType() {
        if (this.caseFilter.reviewType === this.caseFilter._prevReviewType) { // check not coming from reset to avoid loop
            return;
        }

        this._checkForCaseReset()
            .subscribe(() => {
                this.caseFilter._prevReviewType = this.caseFilter.reviewType; // set previous
            }, () => {
                this.caseFilter.reviewType = this.caseFilter._prevReviewType; // reset to previous
            });
    }

    /**
     * Reset case numbers whenever user changes selection reason or review type if there were existing search results
     * If the user cancels we reset the selection reason to the previous selected reason for review.
     *
     * @returns {Observable} check for cases observable
     * */
    _checkForCaseReset(): Observable {
        if (this.resultingCases && this.resultingCases.length) {
            return this._modalSvc.askForConfirmation('Changing the Selection Reason or Review Type clears entered cases. Are you sure you want to remove cases?', 'CLEAR ENTERED CASES').first()
                .do(() => {
                    this.resetCasesForReview();
                });
        } else {
            return Observable.of(true);
        }
    }

    /**
     * The choice of selection reason will determine the status and editability of the other controls when
     * performing case selection
     *
     * @returns {void} no return
     * */
    _adjustCaseControls() {
        if (this.caseFilter.reviewReason === this._approvedSelectionReasons.TEST_CASE) {
            this.subReasonDisabled = true;
            this.reviewTypeDisabled = true;
            this.caseFilter.reviewSubReason = null;
            this.caseFilter.reviewType = this._reviewerReviewTypes.UNDERWRITING;
        } else if (this.caseFilter.reviewReason === this._approvedSelectionReasons.OIG_AUDIT) {
            this.subReasonDisabled = true;
            this.caseFilter.reviewSubReason = null;
            this.caseFilter.reviewType = null;
            this.reviewTypeDisabled = false;
        } else if (this.caseFilter.reviewReason === this._approvedSelectionReasons.REVIEW_LOCATION_QC) {
            this.subReasonDisabled = true;
            this.caseFilter.reviewSubReason = null;
        } else {
            this.subReasonDisabled = false;
            this.caseFilter.reviewSubReason = null;
            this.caseFilter.reviewType = null;
            this.reviewTypeDisabled = false;
        }
    }

    /**
     * Determines if batch selection of cases can be submitted and the resulting cases
     * All batch fields are required except Secondary ID. There must be at least one Batch Team Member.
     *
     * @returns {Boolean} canSubmit is true or false
     * */
    canSubmit() {

        let hasBatchErrors = false;
        let hasSubmitableCases = false;

        this.resultingCases.forEach(group => {
            if (group.isBatch && this._checkBatchInfo(group.batchInfo)) {
                hasBatchErrors = true;
            }
            group.cases.forEach(function(caseObj) {
                if (caseObj.canSubmit) {
                    hasSubmitableCases = true;
                }
            });
        });

        return !hasBatchErrors && hasSubmitableCases;
    }

    /**
     * Helper to check all required fields for batch selection
     *
     * @param {Object} batchInfo is batch information for case selection
     * @returns {Boolean} hasError is true or false
     * */
    _checkBatchInfo(batchInfo) {
        let hasError = false;

        if (batchInfo.requestOperationalReview && batchInfo.operationalReviewGuidance === null) {
            hasError = true;
        } else if (batchInfo.requestOperationalDocuments === null) {
            hasError = true;
        } else if (!batchInfo.batchOwner) {
            hasError = true;
        } else if (batchInfo.batchTeamMembers && !batchInfo.batchTeamMembers.length) {
            hasError = true;
        }

        return hasError;
    }

    /**
     * To find reviewers who can be batch owners we need to filter all reviewers for
     * viewers who have the Operational Review assignmentTypeCode included in their allowedReviewTypes collection
     *
     * @returns {null} no return
     * */
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
     * Load the batch team members with the current selection reason
     * @returns {null} no return
     */
    loadTeamMembers() {
        let reviewersByLoc = this._getReviewersByLocation(this.reviewers);
        this.batchTeamMembers = reviewersByLoc.filter(reviewer => {
            return reviewer.selectionReasons && reviewer.selectionReasons.length && this.caseFilter.reviewReason &&
                reviewer.selectionReasons.some((sr) => sr.assignmentTypeCode === this.caseFilter.reviewReason);
        });
    }

    _getReviewersByLocation(reviewers) {
        return Utils.getReviewersByLocation(reviewers, this.caseFilter.reviewLocation);
    }

    /**
     * Current set of approved reasons for manual case selection
     * @param {api.v1.SelectionReasonDictionary} reason code to look at
     * @returns {Boolean} true of false
     * */
    _reasonIsApproved(reason) {
        return reason.code === this._approvedSelectionReasons.TEST_CASE ||
            reason.code === this._approvedSelectionReasons.FHA_MANUAL ||
            reason.code === this._approvedSelectionReasons.OIG_AUDIT ||
            reason.code === this._approvedSelectionReasons.REVIEW_LOCATION_QC;
    }
}
