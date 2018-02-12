// This file was generated from the service scaffold
// Copyright 2016

import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import Client from '../../api/lrs.api.v1.generated';
import {ReviewerFilter} from '../models/ReviewerFilter';
import {STAFF_MGMT_TYPES} from '../../staff/constants';

import moment from 'moment';

/**
 * @example
 * let injector = Injector.resolveAndCreate([ReviewerSvc]);
 * let reviewerSvc = new injector.get(ReviewerSvc);
 * @example
 * class Component {
 * 		constructor(reviewerSvc:ReviewerSvc, reviewerSvc2:ReviewerSvc) {
 *			//injected via angular, a singleton by default in same injector context
 *			console.log(reviewerSvc === reviewerSvc2);
 *		}
 * }
 */
@Injectable()
export default class ReviewerSvc {

    _client: Client;

    /**
     * @type {String} staffMgmtTypes enumeration of types for adding/removing assignments
     * */
    staffMgmtTypes: Object;

    /**
     * @param {Client} client Instance of client services
     *
     * */
    constructor(client: Client) {
        this._client = client;
        this.staffMgmtTypes = STAFF_MGMT_TYPES;
    }

    /**
     * Extends reviewer.
     * @param {any} reviewer The reviewer.
     * @returns {void}
     */
    extendReviewer(reviewer) {
        reviewer.name = reviewer.nameLast + ', ' + reviewer.nameFirst;
        reviewer.isAvailable = this.isAvailable(reviewer);
    }

    /**
     * Get all reviewers.
     * @returns {Observable<any>} The reviewers.
     */
    getReviewers(): Observable<any> {
        return this._client.resources.reviewers.get().map(reviewers => {
            return reviewers.map(reviewer => {
                this.extendReviewer(reviewer);
                return reviewer;
            });
        });
    }

    /**
     * Search for reviewers by appropriate fields applied within the filter
     * Eg. reviewLevels, reviewTypes, productTypes, or selectionReasons
     *
     * @param {ReviewerFilter} filter The filter.
     * @returns {Observable<any>} The reviewers.
     */
    searchReviewers(filter: ReviewerFilter): Observable<any> {

        let findMatchingAssignments = (reviewer) => {

            let applyFilter = filter.reviewTypeId || filter.loanTypeId || filter.selectionReasonId || filter.reviewLevelId;

            let assignmentsMatched = [];

            // loop through each "assignment type" collection for reviewer

            let foundReviewLevel = filter.reviewLevelId !== '' && reviewer.reviewLevels ? reviewer.reviewLevels.some(at => {
                return at.assignmentTypeCode === filter.reviewLevelId;
            }) : true;

            let foundReviewType = filter.reviewTypeId !== '' && reviewer.reviewTypes ? reviewer.reviewTypes.some(at => {
                return at.assignmentTypeCode === filter.reviewTypeId;
            }) : true;

            let foundProductType = filter.loanTypeId !== '' && reviewer.productTypes ? reviewer.productTypes.some(at => {
                return at.assignmentTypeCode === filter.loanTypeId;
            }) : true;

            let foundSelectionReason = filter.selectionReasonId !== '' && reviewer.selectionReasons ? reviewer.selectionReasons.some(at => {
                return at.assignmentTypeCode === filter.selectionReasonId;
            }) : true;

            if (foundReviewLevel && foundReviewType && foundProductType && foundSelectionReason) {
                assignmentsMatched.push(reviewer);
            }

            return applyFilter ? assignmentsMatched.length > 0 : true;

        };

        return this.getReviewers()
            .map(reviewers => {
                return reviewers
                    .filter(reviewer => {
                        return (
                            (filter.locationId ? reviewer.locationId === filter.locationId : true ) &&
                            (filter.statusCode ? reviewer.statusCode === filter.statusCode : true ) &&
                            (filter.reviewerIds && filter.reviewerIds.length ? filter.reviewerIds.indexOf(reviewer.reviewerId) >= 0 : true) &&
                            findMatchingAssignments(reviewer)
                        );
                    });
            });
    }


    /**
     * Gets the assignment types for a reviewer.
     *
     * @param {string} mgmtType is assignment type - Eg. review_level, review_type, product_type, or selection_reasons
     * @param {string} code the assignment type code.
     * @param {any} reviewer The reviewer.
     * @returns {any} The assignment type.
     */
    getAssignmentType(mgmtType, code, reviewer): boolean {
        let results = [];

        switch (mgmtType.value) {
            case this.staffMgmtTypes.REVIEW_LEVEL:
                if (reviewer.reviewLevels && reviewer.reviewLevels.length) {
                    results = reviewer.reviewLevels.filter(at => {
                        return at.assignmentTypeCode === code;
                    });
                }
                break;

            case this.staffMgmtTypes.REVIEW_TYPE:
                if (reviewer.reviewTypes && reviewer.reviewTypes.length) {
                    results = reviewer.reviewTypes.filter(at => {
                        return at.assignmentTypeCode === code;
                    });
                }
                break;

            case this.staffMgmtTypes.PRODUCT_TYPE:
                if (reviewer.productTypes && reviewer.productTypes.length) {
                    results = reviewer.productTypes.filter(at => {
                        return at.assignmentTypeCode === code;
                    });
                }
                break;

            case this.staffMgmtTypes.SELECTION_REASONS:
                if (reviewer.selectionReasons && reviewer.selectionReasons.length) {
                    results = reviewer.selectionReasons.filter(at => {
                        return at.assignmentTypeCode === code;
                    });
                }
                break;

            default:
                break;
        }


        if (results.length > 0) {
            if (results.length === 1) {
                return results[0];
            }
            else {
                throw new Error('Invalid amount of assignment types for ' + mgmtType.text + ' for reviewer');
            }
        }
        else {
            return null;
        }
    }

    /**
     * Adds the assignment type for a reviewer.
     *
     * @param {string} mgmtType is assignment type - Eg. review_level, review_type, product_type, or selection_reasons
     * @param {string} code The assignment type code
     * @param {any} reviewer The reviewer
     * @returns {void}
     */
    addAssignmentType(mgmtType, code, reviewer) {
        let assignmentType = this.getAssignmentType(mgmtType, code, reviewer);
        if (!assignmentType) {
            let at;

            switch (mgmtType.value) {
                case this.staffMgmtTypes.REVIEW_LEVEL:
                    if (!reviewer.reviewLevels) {
                        reviewer.reviewLevels = [];
                    }
                    at = {
                        assignmentTypeCode: code
                    };
                    reviewer.reviewLevels.push(at);
                    break;

                case this.staffMgmtTypes.REVIEW_TYPE:
                    if (!reviewer.reviewTypes) {
                        reviewer.reviewTypes = [];
                    }
                    at = {
                        assignmentTypeCode: code
                    };
                    reviewer.reviewTypes.push(at);
                    break;

                case this.staffMgmtTypes.PRODUCT_TYPE:
                    if (!reviewer.productTypes) {
                        reviewer.productTypes = [];
                    }
                    at = {
                        assignmentTypeCode: code
                    };
                    reviewer.productTypes.push(at);
                    break;

                case this.staffMgmtTypes.SELECTION_REASONS:
                    if (!reviewer.selectionReasons) {
                        reviewer.selectionReasons = [];
                    }
                    at = {
                        assignmentTypeCode: code
                    };
                    reviewer.selectionReasons.push(at);
                    break;

                default:
                    break;

            }
        }
    }

    /**
     * Removes the assignment type for a reviewer.
     *
     * @param {string} mgmtType is assignment type - Eg. review_level, review_type, product_type, or selection_reasons
     * @param {string} code The assignment type code
     * @param {any} reviewer The reviewer
     * @returns {void}
     */
    removeAssignmentType(mgmtType, code, reviewer) {
        let assignmentType = this.getAssignmentType(mgmtType, code, reviewer);
        if (assignmentType) {

            let index;

            switch (mgmtType.value) {
                case this.staffMgmtTypes.REVIEW_LEVEL:
                    index = reviewer.reviewLevels.indexOf(assignmentType);
                    reviewer.reviewLevels.splice(index, 1);
                    break;

                case this.staffMgmtTypes.REVIEW_TYPE:
                    index = reviewer.reviewTypes.indexOf(assignmentType);
                    reviewer.reviewTypes.splice(index, 1);
                    break;

                case this.staffMgmtTypes.PRODUCT_TYPE:
                    index = reviewer.productTypes.indexOf(assignmentType);
                    reviewer.productTypes.splice(index, 1);
                    break;

                case this.staffMgmtTypes.SELECTION_REASONS:
                    index = reviewer.selectionReasons.indexOf(assignmentType);
                    reviewer.selectionReasons.splice(index, 1);
                    break;

                default:
                    break;

            }
        }
    }

    /**
     * Returns whether reviewer is available.
     * @param {any} reviewer The reviewer.
     * @returns {boolean} Whether reviewer is available.
     */
    isAvailable(reviewer) {

        if (reviewer.unavailabilities) {
            let currentDate = moment().utc();
            let unavailable = reviewer.unavailabilities.some(u => {
                let from = moment.utc(u.from);
                let to = moment.utc(u.to).add(1, 'd');
                return currentDate.isAfter(from, 'd') && currentDate.isBefore(to, 'd');
            });
            return !unavailable;
        }

        return true;
    }

    /**
     * Gets a dictionary where a location id matches a list of reviewers within that location.
     * @param {Array} reviewers The reviewers.
     * @param {string} hqLocationId the hq location id in case we want hq reviewers to be aggregated into each key
     * @returns {{}} The dictionary where a location id matches a list of reviewers within that location.
     */
    getReviewersDictByLoc(reviewers: Array, hqLocationId: string) {
        let locReviewerDict = {};

        // give all when no key given
        locReviewerDict[''] = [...reviewers];

        // load HQ reviewers
        let hqReviewers = hqLocationId ? reviewers.filter(r => {
            return r.locationId === hqLocationId;
        }) : [];

        reviewers.forEach(reviewer => {
            if (!locReviewerDict[reviewer.locationId]) {
                // initial load of hq reviewers which are included always
                locReviewerDict[reviewer.locationId] = [...hqReviewers];
            }
            if (!hqLocationId || reviewer.locationId !== hqLocationId) {
                // push any reviewer identified in this location not HQ (already added)
                locReviewerDict[reviewer.locationId].push(reviewer);
            }
        });

        return locReviewerDict;
    }

    /**
     * Gets a dictionary where for a reviewer id matches a reviewer for that id.
     * @param {Array} reviewers The reviewers
     * @returns {{}} The dictionary where for a reviewer id matches a reviewer for that id.
     */
    getReviewerDictById(reviewers: Array) {
        let reviewerDict = {};
        reviewers.forEach(reviewer => {
            reviewerDict[reviewer.reviewerId] = reviewer;
        });
        return reviewerDict;
    }

    /**
     * Gets a dictionary where for a reviewer id matches a list of reviewers inside same location.
     * @param {Array} reviewers The reviewers
     * @param {string} hqLocationId the hq location id in case we want hq reviewers to be aggregated into each key
     * @returns {{}} The dictionary where for a reviewer id matches a list of reviewers inside same location.
     */
    getReviewersLocDictById(reviewers: Array, hqLocationId: string) {
        let reviewersByLocByIdDict = {};
        let reviewersByLocDict = this.getReviewersDictByLoc(reviewers, hqLocationId);
        reviewers.forEach(reviewer => {
            reviewersByLocByIdDict[reviewer.reviewerId] = reviewersByLocDict[reviewer.locationId];
        });
        return reviewersByLocByIdDict;
    }
}
