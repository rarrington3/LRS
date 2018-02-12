// This file was generated from the view scaffold
// Copyright 2016

import {Component, SimpleChange} from '@angular/core';
import {ActivatedRoute}  from '@angular/router';
import template from './StaffManagement.html';
import styles from './StaffManagement.less';
import {Observable} from 'rxjs/Observable';
import {HqAdminGuard, ReviewLocationAdminGuard} from '../../app/services/AuthGuards';
import {STATUS_CODE, SERVER_ERROR, HQ_ADMIN_ONLY_ASSIGNMENT_TYPES} from '../../shared/constants';
import {STAFF_MGMT_TYPES} from '../constants';
import {ReviewerFilter} from '../../app/models/ReviewerFilter';
import {ReviewerSvc, ModalSvc, UserSvc} from '../../app/services';
import LocationSvc from '../../app/services/LocationSvc';
import Client from '../../api/lrs.api.v1.generated';
import _ from 'lodash';
import moment from 'moment';

@Component({
    selector: 'staff-management',
    template: template,
    styles: [styles]
})

/**
 * @see https://angular.io/docs/ts/latest/guide/router.html
 */
export default class StaffManagement {

    _client: Client;
    _route: ActivatedRoute;
    _reviewerSvc: ReviewerSvc;
    _modalSvc: ModalSvc;

    /**
     * Service for fetching all locations
     * @type {LocationSvc} _locationSvc for checking logged in user location
     * */
    _locationSvc: LocationSvc;

    /**
     * Service for displaying modals
     * @type {UserSvc}
     * */
    _userSvc: UserSvc;

    /**
     * Used for determining which columns are for HQ Admin only
     * @type {HqAdminGuard} _hqAdminGuard for checking roles
     * */
    _hqAdminGuard: HqAdminGuard;

    /**
     * Used to determine if the location is preselected when users are adding staff
     * @type {ReviewLocationAdminGuard} _reviewLocationAdminGuard for check when adding staff
     * */
    _reviewLocationAdminGuard: ReviewLocationAdminGuard;

    /**
     * A separate call for HQ reviewers since the current reviewers API returns only reviewers for user's
     * location when logged in as a Location Admin. Added onto list if logged in user is Location Admin.
     * @type {Array}
     * */
    _hqReviewers: Array = [];

    /**
     * Used to map mgmt types with its items
     * @type {{}}
     * @private
     */
    _mgmtTypesDict = {}; // used to map mgmt types with its items

    /**
     * Used to map assignment types into checks
     * @type {{}}
     * @private
     */
    _matrixDict = {};

    /**
     * Used to return reviewers from reviewer id using its location
     * @type {{}}
     * @private
     */
    _reviewersLocByIdDict: {};

    _newAddMemberData = {
        isOpen: false,
        canSave: false,
        reviewers: [],
        locations: [],
        reviewer: {}
    };

    _newAvailabilityRangeData = {
        isOpen: false,
        canSave: false,
        reviewer: {},
        title: '',
        unavailabilities: []
    };

    /**
     * Default name for the class, passed by reference via RouteParams
     */
    name: string = 'StaffManagement';

    /**
     * Any new subscription will be added into this collection
     * @type {Array}
     */
    subscriptions: Array = [];

    reviewTypes: Array = [];

    productTypes: Array = [];

    selectionReasons: Array = [];

    reviewLevels: Array = [];

    locations: Array = [];

    allReviewers = []; // used for drop down available items

    reviewers: Array = [];

    reviewersLoading: boolean = false;

    inactiveReviewers: Array = [];

    inactiveReviewersLoading: boolean = false;

    filter: ReviewerFilter = new ReviewerFilter();

    addMemberData = _.cloneDeep(this._newAddMemberData);

    availabilityRangeData = _.cloneDeep(this._newAvailabilityRangeData);

    mgmtTypes: Array = [
        {
            value: 'general',
            text: 'General',
            valueField: null,
            textField: null,
            items: null
        },
        {
            value: STAFF_MGMT_TYPES.REVIEW_TYPE,
            text: 'Review Type',
            valueField: 'code',
            textField: 'description',
            items: null
        },
        {
            value: STAFF_MGMT_TYPES.PRODUCT_TYPE,
            text: 'Product Type',
            valueField: 'code',
            textField: 'description',
            items: null
        },
        {
            value: STAFF_MGMT_TYPES.SELECTION_REASONS,
            text: 'Selection Reasons',
            valueField: 'code',
            textField: 'description',
            items: null
        },
        {
            value: STAFF_MGMT_TYPES.REVIEW_LEVEL,
            text: 'Review Level',
            valueField: 'code',
            textField: 'description',
            items: null
        }];

    selectedMgmtType = null;

    staffMgmtTypes = STAFF_MGMT_TYPES;

    statusCode = STATUS_CODE;

    /**
     * Used for determining which columns are for HQ Admin only
     * @type {Boolean} userIsHqAdmin
     * */
    userIsHqAdmin: Boolean = false;

    /**
     * Used to determine if the location is preselected when users are adding staff
     * @type {Boolean} userIsLocationAdmin
     * */
    userIsLocationAdmin: Boolean = false;

    hqLocationId: string;

    /**
     * @param {ActivatedRoute} route Active route
     * @param {ReviewerSvc} reviewerSvc class
     * @param {ModalSvc} modalSvc class
     * @param {Client} client Instance of client services
     * @param {LocationSvc} locationSvc class
     * @param {UserSvc} userSvc class
     * @param {HqAdminGuard} hqAdminGuard class
     * @param {ReviewLocationAdminGuard} reviewLocationAdminGuard class
     * */
    constructor(route: ActivatedRoute, reviewerSvc: ReviewerSvc, modalSvc: ModalSvc, client: Client, locationSvc: LocationSvc, userSvc: UserSvc, hqAdminGuard: HqAdminGuard, reviewLocationAdminGuard: ReviewLocationAdminGuard) {
        this._client = client;
        this._route = route;
        this._reviewerSvc = reviewerSvc;
        this._modalSvc = modalSvc;
        this._ = _;
        this._locationSvc = locationSvc;
        this._userSvc = userSvc;
        this._hqAdminGuard = hqAdminGuard;
        this._reviewLocationAdminGuard = reviewLocationAdminGuard;

        this.mgmtTypes.forEach(type => {
            this._mgmtTypesDict[type.value] = type;
        });

        this.selectedMgmtType = this.mgmtTypes[0];
    }

    /**
     * Set up resources and fork results.
     *
     * Notice we get all reviewers which is based on the logged in user's location.
     * We also call to get all HQ users in a separate collection to be displayed in
     * the reportsTo and vettedBy drop down's for HQ users.
     *
     * When the locations are loaded when then trigger a search by setting the filter.locationId to that
     * of the logged in user to reduce the filtering and rendering of the reviewers and lighten the page load.
     * TODO: load reviewers by location end points and clean up this code significantly
     *
     * @returns {null} no return
     * */
    ngOnInit() {

        this.subscriptions.push(this._route.params.subscribe(params => {
            this.name = params['name'];
        }));

        this.userIsHqAdmin = this._hqAdminGuard.canActivate();
        this.userIsLocationAdmin = this._reviewLocationAdminGuard.canActivate();

        this._client.resources.locations.get().first()
            .subscribe(locations => {
                this.locations = locations;
            });

        let reviewTypesSource = this._client.resources.dictionary.reviewTypes.get().first();
        let productTypesSource = this._client.resources.dictionary.productTypes.get().first();
        let selectionReasonsSource = this._client.resources.dictionary.consolidatedSelectionReasons.get().first();
        let reviewLevelsSource = this._client.resources.admin.staffManagementReviewLevels.get().first();
        let reviewersSource = this._reviewerSvc.getReviewers().first();
        let reviewersHqSource = this.userIsLocationAdmin ?
            this._client.resources.reviewers.hq.get().first().map(reviewers => {
                return reviewers.map(reviewer => {
                    this._reviewerSvc.extendReviewer(reviewer);
                    return reviewer;
                });
            }) : Observable.of([]); // if user is location admin get the hq reviewers
        let hqLocationSource = this._locationSvc.hqLocationId ?
            Observable.of(new SimpleChange(null, this._locationSvc.hqLocationId)) :
            this._locationSvc.hqLocationIdObservable.first(); // either get the current hqLocationId or wait for it

        let sources = [
            hqLocationSource.do((change: SimpleChange) => {
                this.hqLocationId = change.currentValue;
            }),
            reviewersSource.do(reviewers => {
                this.allReviewers = reviewers;
            }),
            reviewersHqSource.do(reviewers => {
                this._hqReviewers = reviewers;
            }),
            reviewTypesSource.do(reviewTypes => {
                this.reviewTypes = reviewTypes;
                this._mgmtTypesDict[STAFF_MGMT_TYPES.REVIEW_TYPE].items = this.reviewTypes;
            }),
            productTypesSource.do(productTypes => {
                this.productTypes = productTypes;
                this._mgmtTypesDict[STAFF_MGMT_TYPES.PRODUCT_TYPE].items = this.productTypes;
            }),
            selectionReasonsSource.do(selectionReasons => {
                this.selectionReasons = selectionReasons;
                this._mgmtTypesDict[STAFF_MGMT_TYPES.SELECTION_REASONS].items = this.selectionReasons;
            }),
            reviewLevelsSource.do(reviewLevels => {
                if (!this.userIsHqAdmin) {
                    this.reviewLevels = reviewLevels.filter((level) => {
                        return level.code !== HQ_ADMIN_ONLY_ASSIGNMENT_TYPES.INDEM && level.code !== HQ_ADMIN_ONLY_ASSIGNMENT_TYPES.FORCED_INDEM;
                    });
                } else {
                    this.reviewLevels = reviewLevels;
                }

                this._mgmtTypesDict[STAFF_MGMT_TYPES.REVIEW_LEVEL].items = this.reviewLevels;
            })
        ];

        // we need the matrix used data to be loaded before setting the default location id
        Observable.forkJoin(...sources)
            .subscribe(() => {
                this._loadReviewersLocByIdDict(this.allReviewers); // load dictionary of reviewers for dropdowns, requires load of sources
                this.search(); // initial search
            });

        this.filter.locationId = this._userSvc.locationId;
    }

    ngOnDestroy() {
        this.subscriptions.forEach(subscription => {
            subscription.unsubscribe();
        });
    }

    /**
     * Performs a search based on the filter parameters built in the UI left panel drop downs
     * @returns {null} no return
     * */
    search() {
        this.reviewersLoading = true;
        this.inactiveReviewersLoading = true;
        this._reviewerSvc.searchReviewers(this.filter).first()
            .subscribe(reviewers => {
                this._loadReviewers(reviewers);
                this.reviewersLoading = false;
                this.inactiveReviewersLoading = false;
            });
    }

    isChecked(item, mgmtType, reviewer) {
        return this._getMatrixItem(item, mgmtType, reviewer);
    }

    check(item, mgmtType, reviewer) {
        let id = item[mgmtType.valueField];
        let checked = !this._getMatrixItem(item, mgmtType, reviewer);
        this._setMatrixItem(item, mgmtType, reviewer, checked);

        if (checked) {
            this._reviewerSvc.addAssignmentType(mgmtType, id, reviewer);
        }
        else {
            this._reviewerSvc.removeAssignmentType(mgmtType, id, reviewer);
        }
        this.save(reviewer);
    }

    reset() {
        this.filter = new ReviewerFilter();
        this.filter.locationId = this._userSvc.locationId;
        this.search();
    }

    /**
     * @param {api.v1.reviewer.Reviewer} reviewer to update
     * @returns {null} no return
     * */
    save(reviewer) {
        this._client.resources.reviewers.reviewerId(reviewer.reviewerId).put(reviewer).first()
            .subscribe();
    }

    /**
     * @param {api.v1.reviewer.Reviewer} reviewer to update
     * @returns {null} no return
     * */
    _debouncedSave = _.debounce((reviewer) => {
        this.save(reviewer);
    }, 500);

    /**
     *
     * @param {api.v1.reviewer.Reviewer} reviewer to update
     * @returns {null} no return
     * */
    debouncedSave(reviewer) {
        this._debouncedSave(reviewer);
    }

    /**
     * Sets reviewer statusCode to INACTIVE. If the attempt fails we role the statusCode back to ACTIVE.
     * @param {api.v1.reviewers.Reviewer} reviewer being deactivated
     * @returns {null} no return
     * */
    deactivate(reviewer) {
        reviewer.statusCode = STATUS_CODE.INACTIVE;
        this._client.resources.reviewers.reviewerId(reviewer.reviewerId).put(reviewer).first()
            .subscribe(() => {
                let index = this.reviewers.indexOf(reviewer);
                this.reviewers.splice(index, 1);
                this.inactiveReviewers.push(reviewer);
            }, response => {
                if (response.status === SERVER_ERROR.CONFLICT) {
                    reviewer.statusCode = STATUS_CODE.ACTIVE;

                    let errors = response.body;
                    let errorMessage = '';
                    if (errors && errors.length) {
                        errorMessage = errors
                            .map(e => {
                                return e.errorMessage;
                            }).join(', ');
                    }
                    else {
                        errorMessage = 'Cannot deactivate staff member';
                    }

                    this._modalSvc.acknowledge(errorMessage, 'Error').first().subscribe();
                }
                else {
                    throw new Error(response);
                }
            });
    }

    activate(reviewer) {
        reviewer.statusCode = STATUS_CODE.ACTIVE;
        this._client.resources.reviewers.reviewerId(reviewer.reviewerId).put(reviewer).first()
            .subscribe(() => {
                let index = this.inactiveReviewers.indexOf(reviewer);
                this.inactiveReviewers.splice(index, 1);
                this.reviewers.push(reviewer);
            });
    }

    remove(reviewer) {

        this._modalSvc.askForConfirmation('Are you sure you want to remove staff member: ' + reviewer.name + '?').first()
            .subscribe(
                () => {
                    this._client.resources.reviewers.reviewerId(reviewer.reviewerId).delete().first()
                        .subscribe(() => {
                            let index = this.inactiveReviewers.indexOf(reviewer);
                            this.inactiveReviewers.splice(index, 1);
                        }, response => {
                            if (response.status === SERVER_ERROR.CONFLICT) {
                                let errors = response.body;
                                let errorMessage = '';
                                if (errors && errors.length) {
                                    errorMessage = errors
                                        .map(e => {
                                            return e.errorMessage;
                                        }).join(', ');
                                }
                                else {
                                    errorMessage = 'Cannot remove staff member';
                                }

                                this._modalSvc.acknowledge(errorMessage, 'Error').first().subscribe();
                            }
                            else {
                                throw new Error(response);
                            }
                        });
                }, () => {
                }
            );
    }

    /**
     * We use moment to present the dates in a way that IE can use versus ISO strings
     * @param {api.v1.reviewers.Reviewer} reviewer to modify
     * @returns {null} no return
     * */
    openAvailability(reviewer) {
        this.availabilityRangeData.unavailabilities = reviewer.unavailabilities.map(u => {
            let unavailability = Object.assign({}, u);
            unavailability.from = moment.utc(u.from).format('l');
            unavailability.to = moment.utc(u.to).format('l');
            return unavailability;
        });
        this.availabilityRangeData.reviewer = reviewer;
        this.availabilityRangeData.title = 'Availability - ' + reviewer.nameFirst + ' ' + reviewer.nameLast;
        this.availabilityRangeData.isOpen = true;
    }

    /**
     * We convert dates back to ISO strings for contract with server
     * @returns {null} no return
     * */
    saveAvailability() {
        let reviewer = this.availabilityRangeData.reviewer;
        reviewer.unavailabilities = this.availabilityRangeData.unavailabilities
            .filter(u => u.from && u.to)
            .map(u => {
                let unavailability = Object.assign({}, u);
                unavailability.from = new Date(u.from).toISOString();
                unavailability.to = new Date(u.to).toISOString();
                return unavailability;
            });
        reviewer.isAvailable = this._reviewerSvc.isAvailable(reviewer);

        this._client.resources.reviewers.reviewerId(reviewer.reviewerId).put(reviewer).first()
            .subscribe(() => {
                // closes modal
                this.resetAvailability();
            });
    }

    resetAvailability() {
        // clears component data
        this.availabilityRangeData = _.cloneDeep(this._newAvailabilityRangeData);
    }


    /**
     * If logged in user is Location Admin, we should preset the location in the pop up to add staff member
     * @returns {null} no return
     * */
    openAddMember() {
        this.addMemberData.locations = this.locations;
        this.addMemberData.reviewer = {};

        if (this.userIsLocationAdmin) {
            let location = this.locations.find(loc => loc.locationId === this._userSvc.locationId);
            if (location) {
                this.addMemberData.reviewer.locationId = location.locationId;
            }
        }

        // if is location admin add hq users to the list for location admin's
        let reviewers = this.userIsLocationAdmin ? _.union(this.allReviewers, this._hqReviewers) : this.allReviewers;
        // only active
        reviewers = reviewers.filter(r => r.statusCode === STATUS_CODE.ACTIVE);
        this.addMemberData.reviewers = reviewers;

        this.addMemberData.isOpen = true;
    }

    saveAddMember() {
        let reviewer = this.addMemberData.reviewer;

        this._client.resources.reviewers.post(reviewer).first()
            .subscribe(reviewer => {
                this._reviewerSvc.extendReviewer(reviewer);

                this.reviewers.push(reviewer);
                this._loadMatrix(this.reviewers);

                // adding reviewer to all reviewers for dropdowns
                this.allReviewers.push(reviewer);
                this._loadReviewersLocByIdDict(this.allReviewers);

                // closes modal
                this.resetAddMember();
            });
    }

    resetAddMember() {
        // clears component data
        this.addMemberData = _.cloneDeep(this._newAddMemberData);
    }

    /**
     * Reviewers need to be filtered before they can be managed:
     * Reviewer's location is same as logged in user's location or logged in user is from HQ
     *
     * @param {api.v1.reviewers.Reviewer} reviewer to filter against
     * @returns {Array} array of reviewers that can reportedTo or vettedBy
     * */
    getEligibleReviewers(reviewer) {
        // Ensure we have our dictionary first...
        return this._reviewersLocByIdDict ? this._reviewersLocByIdDict[reviewer.reviewerId] : [];
    }

    /**
     *
     * @param {Array} reviewers is an array of api.v1.reviewers.Reviewer
     * @returns {null} no return
     * */
    _loadReviewers(reviewers) {
        this.reviewers = reviewers.filter(r => {
            return r.statusCode === STATUS_CODE.ACTIVE;
        });
        this.inactiveReviewers = reviewers.filter(r => {
            return r.statusCode === STATUS_CODE.INACTIVE;
        });

        this._loadMatrix(this.reviewers);
    }

    /**
     * Loads a matrix of assignment types for each type of mgmntTypes.
     * Eg. reviewTypes, productTypes, selectionReasons, or reviewLevels
     *
     * Loops through each assignment type for each reviewer to determine the checked status in the UI
     *
     * @param {Array} reviewers is a collection of api.v1.reviewers.Reviewer
     * @returns {null} no return
     * */
    _loadMatrix(reviewers) {
        let reviewerMatrix = {};
        reviewers.forEach(reviewer => {
            // generate a complete matrix dictionary to match the
            // assignment types
            let mtMatrix = {};
            this.mgmtTypes.forEach(mt => {
                let itemMatrix = {};
                if (mt.items) {
                    mt.items.forEach(item => {
                        itemMatrix[item[mt.valueField]] = false;
                    });
                }
                mtMatrix[mt.value] = itemMatrix;
            });

            let code;
            let itemMatrix;
            // let the matrix know about the assignment types for each mgmntType

            if (reviewer.reviewTypes) {
                reviewer.reviewTypes.forEach(at => {
                    code = at.assignmentTypeCode;
                    this.mgmtTypes.forEach(mt => {
                        itemMatrix = mtMatrix[mt.value];
                        if (itemMatrix[code] !== undefined) {
                            itemMatrix[code] = true;
                        }
                    });
                });
            }

            if (reviewer.productTypes) {
                reviewer.productTypes.forEach(at => {
                    code = at.assignmentTypeCode;
                    this.mgmtTypes.forEach(mt => {
                        itemMatrix = mtMatrix[mt.value];
                        if (itemMatrix[code] !== undefined) {
                            itemMatrix[code] = true;
                        }
                    });
                });
            }

            if (reviewer.selectionReasons) {
                reviewer.selectionReasons.forEach(at => {
                    code = at.assignmentTypeCode;
                    this.mgmtTypes.forEach(mt => {
                        itemMatrix = mtMatrix[mt.value];
                        if (itemMatrix[code] !== undefined) {
                            itemMatrix[code] = true;
                        }
                    });
                });
            }

            if (reviewer.reviewLevels) {
                reviewer.reviewLevels.forEach(at => {
                    code = at.assignmentTypeCode;
                    this.mgmtTypes.forEach(mt => {
                        itemMatrix = mtMatrix[mt.value];
                        if (itemMatrix[code] !== undefined) {
                            itemMatrix[code] = true;
                        }
                    });
                });
            }

            reviewerMatrix[reviewer.reviewerId] = mtMatrix;
        });
        this._matrixDict = reviewerMatrix;
    }

    _getMatrixItem(item, mgmtType, reviewer) {
        let id = item[mgmtType.valueField];
        return this._matrixDict[reviewer.reviewerId][mgmtType.value][id];
    }

    _setMatrixItem(item, mgmtType, reviewer, value) {
        let id = item[mgmtType.valueField];
        this._matrixDict[reviewer.reviewerId][mgmtType.value][id] = value;
    }

    _loadReviewersLocByIdDict(reviewers) {
        // if is location admin add hq users to the list for location admin's
        if (this.userIsLocationAdmin) {
            reviewers = _.union(reviewers, this._hqReviewers);
        }

        // only active
        reviewers = reviewers.filter(r => r.statusCode === STATUS_CODE.ACTIVE);

        // get the reviewers dictionary by the location of reviewer by the reviewer id
        let reviewersLocByIdDict = this._reviewerSvc.getReviewersLocDictById(reviewers, this.hqLocationId);

        // don't let a reviewer can assign to him self, filter here for each
        reviewers.forEach(reviewer => {
            reviewersLocByIdDict[reviewer.reviewerId] =
                reviewersLocByIdDict[reviewer.reviewerId]
                    .filter(r => r.reviewerId !== reviewer.reviewerId);

        });
        this._reviewersLocByIdDict = reviewersLocByIdDict;
    }
}
