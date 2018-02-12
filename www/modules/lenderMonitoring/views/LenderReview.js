// This file was generated from the view scaffold
// Copyright 2016

import {Component, ViewChild} from '@angular/core';
import {ActivatedRoute}  from '@angular/router';
import {NgForm}  from '@angular/forms';
import template from './LenderReview.html';
import styles from './LenderReview.less';
import {HqAdminGuard} from '../../app/services/AuthGuards';

import {ReviewerSvc, ModalSvc, UserSvc} from '../../app/services';
import {REVIEWER_REVIEW_TYPES, CASE_SELECTION_REASONS, STATUS_CODE} from '../../shared/constants';

import Client from '../../api/lrs.api.v1.generated';

@Component({
    selector: 'lender-review',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/guide/router.html
 */
export default class LenderReview {

    _route: ActivatedRoute;
    _client: Client;
    _reviewerSvc: ReviewerSvc;

    /**
     * Service for displaying modals
     * @type {ModalSvc} Service for selection
     * */
    _modalSvc: ModalSvc;

    /**
     * Service for displaying modals
     * @type {UserSvc}
     * */
    _userSvc: UserSvc;

    /**
     * Default name for the class, passed by reference via RouteParams
     */
    name: string = 'LenderReview';

    /**
     * Used for determining which controls are for HQ Admin only
     * @type {HqAdminGuard} _hqAdminGuard for checking roles
     * */
    _hqAdminGuard:HqAdminGuard;

    userIsAdmin:Boolean = false;

    /**
     * Any new subscription will be added into this collection
     * @type {Array}
     */
    subscriptions: Array = [];

    _newReview = {
        lenderId: '',
        locationId: '',
        reviewTypeId: '',
        requestFromId: '',
        dateOfSiteVisit: '',
        loanTypeId: '',
        caseCount: '',
        endorsementStartDate: '',
        endorsementEndDate: '',
        operationalReview: false,
        secondaryId: '',
        requestOperationalDocuments: false,
        operationalReviewGuidance: '',
        batchOwner: '',
        batchTeamMembers: []
    };

    _newLenderReviewConfirmData = {
        isOpen: false,
        review: {},
        loanTypeName: ''
    };

    review = Object.assign({}, this._newReview);

    reviewTypes: Array = [];

    loanTypes: Array = [];

    documentLocations: Array = [];

    locations: Array = [];

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

    lenderReviewConfirmData = Object.assign({}, this._newLenderReviewConfirmData);

    @ViewChild('reviewForm') currentForm: NgForm;

    /**
     * @param {ActivatedRoute} route Active route
     * @param {Client} client Instance of client services
     * @param {ReviewerSvc} reviewerSvc class
     * @param {ModalSvc} modalSvc class
     * @param {UserSvc} userSvc class
     * @param {HqAdminGuard} hqAdminGuard class
     *
     * */
    constructor(route: ActivatedRoute, client: Client, reviewerSvc: ReviewerSvc, modalSvc: ModalSvc, userSvc: UserSvc, hqAdminGuard: HqAdminGuard) {
        this._route = route;
        this._client = client;
        this._reviewerSvc = reviewerSvc;
        this._modalSvc = modalSvc;
        this._userSvc = userSvc;
        this._hqAdminGuard = hqAdminGuard;
    }

    ngOnInit() {
        this.subscriptions.push(this._route.params.subscribe(params => {
            this.name = params['name'];
        }));

        this._client.resources.dictionary.reviewTypes.get().first()
            .subscribe(reviewTypes => {
                this.reviewTypes = reviewTypes.filter((rt) => { return rt.code !== REVIEWER_REVIEW_TYPES.OPERATIONAL; });
            });

        this._client.resources.dictionary.loanTypes.get().first()
            .subscribe(loanTypes => {
                this.loanTypes = loanTypes;
            });

        this._client.resources.dictionary.documentLocations.get().first()
            .subscribe(documentLocations => {
                this.documentLocations = documentLocations;
            });

        this._client.resources.locations.get().first()
            .subscribe(locations => {
                this.locations = locations;
            });

        this._reviewerSvc.getReviewers().first()
            .map(reviewers => {
                return reviewers.filter(r => r.statusCode === STATUS_CODE.ACTIVE);
            })
            .subscribe(reviewers => {
                this.reviewers = reviewers;
                this.loadBatchReviewers();
            });

        // Finally we set the location drop down
        // and disable it unless user is not HQ Admin
        this.userIsAdmin = this._hqAdminGuard.canActivate();
        this.review.locationId = this._userSvc.locationId;
    }

    ngOnDestroy() {
        this.subscriptions.forEach(subscription => {
            subscription.unsubscribe();
        });
    }

    addToRangeEnd() {
        if (this.review.endorsementEndDate) {
            return;
        }

        if (this.review.endorsementStartDate) {
            let endDate = new Date(this.review.endorsementStartDate);
            endDate.setFullYear(endDate.getFullYear() + 2);
            this.review.endorsementEndDate = endDate;
        }
    }

    cancel() {
        if (this.currentForm) {
            this.currentForm.resetForm();
        }

        this.review = Object.assign({}, this._newReview);
    }

    submit() {
        this.lenderReviewConfirmData.loanTypeName = '';
        if (this.review.loanTypeId) {
            let loanType = this.loanTypes.find(lt => lt.code === this.review.loanTypeId);

            if (loanType) {
                this.lenderReviewConfirmData.loanTypeName = loanType.description;
            }
        }

        this.review.dateOfSiteVisit = new Date(this.review.dateOfSiteVisit);
        this.review.endorsementStartDate = new Date(this.review.endorsementStartDate);
        this.review.endorsementEndDate = new Date(this.review.endorsementEndDate);

        this.lenderReviewConfirmData.review = this.review;
        this.lenderReviewConfirmData.isOpen = true;
    }

    confirm() {
        this._client.resources.lenderMonitoring.post(this.review).first()
            .subscribe(() => {
                // closes confirmation modal
                this.lenderReviewConfirmData.isOpen = false;

                // confirms submission and clear form
                this._modalSvc.acknowledge('This lender has been submitted for review.', 'Submission Successful').first()
                    .subscribe(
                        () => {
                            this.cancel();
                        }, () => {
                        }
                    );
            }, () => {
                // close confirmation modal
                this.lenderReviewConfirmData.isOpen = false;
                // show modal here about problem finding this.review.lenderId
                this._modalSvc.acknowledge('There was a problem locating lender ID: ' + this.review.lenderId + '.', 'Review Error').first()
                    .subscribe(
                        () => {
                            this.cancel();
                        }, () => {
                        }
                    );
            });
    }

    resetLenderReviewConfirmData() {
        this.lenderReviewConfirmData = Object.assign({}, this._newLenderReviewConfirmData);
    }

    /**
     * To find reviewers who can be batch owners we need to filter all reviewers for
     * Reviewers who have the Operational Review assignmentTypeCode included in their allowedReviewTypes collection
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
     * Load the batch team members with the lender monitoring selection reason
     * @returns {null} no return
     */
    loadTeamMembers() {
        let reviewersByLoc = this._getReviewersByLocation(this.reviewers);
        this.batchTeamMembers = reviewersByLoc.filter(reviewer => {
            return reviewer.selectionReasons && reviewer.selectionReasons.length &&
                reviewer.selectionReasons.some((sr) => sr.assignmentTypeCode === CASE_SELECTION_REASONS.LENDER_MONITORING);
        });
    }

    _getReviewersByLocation(reviewers) {
        return reviewers.filter(reviewer => {
            return reviewer.locationId && this.review.locationId &&
                reviewer.locationId === this.review.locationId;
        });
    }
}
