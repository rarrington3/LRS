// This file was generated from the view scaffold
// Copyright 2016

import {Component} from '@angular/core';
import Client from '../../api/lrs.api.v1.generated';
import {ActivatedRoute}  from '@angular/router';
import {ModalSvc} from '../../app/services';
import {REVIEWER_REVIEW_TYPES} from '../../shared/constants';
import Utils from '../../shared/Utils';
import template from './selfReport.html';
import styles from './selfReport.less';
import _ from 'lodash';


@Component({
    selector: 'self-report',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/guide/router.html
 */
export default class SelfReport {

    route:ActivatedRoute;

    /**
     * Establish a client in order to use http services
     * @type {Client}
     * */
    _client:Client;

    /**
     * Default name for the class, passed by reference via RouteParams
     */
    name:string = 'SelfReport';

    /**
     * Any new subscription will be added into this collection
     * @type {Array}
     */
    subscriptions:Array = [];

    /**
     * @type {String} holds the collection of comma delimited case numbers to search for
     * */
    searchCaseNumbers:String = null;

    /**
     * @type {String} The ID of the type of review being performed.
     * */
    selectedReviewType:String = null

    /**
     * @type {Array} holds the collection of cases searched for by case number
     * */
    resultingCases:Array = [];

    /**
     * @type {Boolean} true whenever a search is done and no results are found for case number(s)
     * */
    noSearchResults:Boolean = false;

    /**
     * @type {String} error messages returned from server
     * */
    searchErrorMessage:string = null;

    /**
     * @type {Number} determines which template to show in view
     * */
    caseStep:Number = 1;

    /**
     * Holds all the values to submit the report
     * @type {Object} simple object to hold parameters
     * */
    reportDTO:Object = null;

    /**
     * @type {Array} holds the collection of defect areas
     * */
    defectAreas:Array = null;

    /**
     * @type {Array} holds the collection of defect areas based on selected reviewType for report
     * */
    filteredDefectAreas:Array = null;

    /**
     * @type {Array} holds the collection of types of fraud for ui drop down
     * */
    fraudTypes:Array = null;

    /**
     * @type {Array} holds the collection of members who committed fraud for ui drop down
     * */
    fraudParticipants:Array = null;

    /**
     * Since there may be cases with both review types (underwritingReviewProhibited AND servicingReviewProhibited)
     * we track the state of the checkbox independently
     * @type {Boolean}
     * */
    serviceReviewChecked:Boolean;

    /**
     * Since there may be cases with both review types (underwritingReviewProhibited AND servicingReviewProhibited)
     * we track the state of the checkbox independently
     * @type {Boolean}
     * */
    underwritingReviewChecked:Boolean;

    /**
     * @type {Array}
     * */
    reviewTypes: Array = [];

    qaModels: Array = [];

    submitableCases: Array = [];

    /**
     * @param {Client} client Instance of client services
     * @param {ActivatedRoute} route Active route
     * @param {ModalSvc} modalSvc class
     * */
    constructor(client:Client, route:ActivatedRoute, modalSvc:ModalSvc) {
        this._client = client;
        this.route = route;
        this._modalSvc = modalSvc;
    }

    ngOnInit() {
        this.subscriptions.push(this.route.params.subscribe(params => {
            this.name = params['name'];
        }));

        this.reportDTO = {
            defectAreas: []
        };

        // load up dictionary types for the view (exclude operational reviews)
        this._client.resources.dictionary.reviewTypes.get().first()
            .subscribe(reviewTypes => {
                this.reviewTypes = reviewTypes.filter((rt) => {
                    return rt.code !== REVIEWER_REVIEW_TYPES.OPERATIONAL &&
                    /* temporarily preventing comprehensive reviews */ rt.code !== REVIEWER_REVIEW_TYPES.COMPREHENSIVE;
                });
            });

        this._client.resources.reviews.dictionary.qaModels.activeDefectAreas.get().first().subscribe(result => {
            this.defectAreas = result;
            this.defectAreas.forEach(defectArea => defectArea.checked = false);
        });

        this._client.resources.dictionary.fraudTypes.get().first().subscribe(result => this.fraudTypes = result);
        this._client.resources.dictionary.fraudParticipants.get().first().subscribe(result => this.fraudParticipants = result);
    }

    ngOnDestroy() {
        this.subscriptions.forEach(subscription => {
            subscription.unsubscribe();
        });
    }

    /**
     * Look up cases by case number(s) and selected reviewType bound to the text input in the view.
     * @returns {null} updates the current resultingCases collection
     * */
    lookUpCases() {

        if (!this.searchCaseNumbers || !this.selectedReviewType) {
            return;
        }

        let caseNums = this.searchCaseNumbers.split(/[\s,]+/);
        caseNums = caseNums.filter((caseNumber) => caseNumber.length && caseNumber !== '');
        // split case numbers by commas or spaces and send query

        this._client.resources.cases.selfReportSearch.post({
            caseNumbers: caseNums,
            reviewType: this.selectedReviewType
        }).first().subscribe(result => {

            // Add some metadata to help display if each case can be submitted
            result.cases.forEach(function (caseResult) {
                caseResult.canSubmit = Utils.canSubmitCaseSelection(caseResult);
                Utils.sortCaseActivty(caseResult);
            });

            let existingCases = this.resultingCases;
            let mergedCases = existingCases.concat(result.cases);

            // get all cases by lender id and case number
            let uniqCases = _.uniq(mergedCases, function (c) {
                return String(c.lender.lenderId) && String(c.caseNumber);
            });

            this.resultingCases = uniqCases;

            // Build second collection for only submittable
            this.submitableCases = this.resultingCases.filter(resultingCase => {
                return resultingCase.canSubmit;
            });

            // determine error messaging
            if (result.errors && result.errors.length) {
                this.noSearchResults = true;
                this.searchErrorMessage = result.errors.join('<br/>');
            }
        });
    }


    /**
     * @returns {null} no return
     * */
    addCaseDetails() {
        this.filterDefectAreas();
        this.caseStep = 2;
    }

    /**
     * Find case in our collection to remove locally
     * @param {api.vi.cases.Case} selectedCase case to be deleted
     * @returns {null} deletes case from group this.resultingCases
     * */
    deleteCase(selectedCase) {
        this._modalSvc.askForConfirmation('Are you sure you want to remove case: ' + selectedCase.caseNumber + '?').first()
            .subscribe(
                () => {
                    let found = this.resultingCases.find(c => {
                        return String(selectedCase.caseNumber) === String(c.caseNumber);
                    });

                    if (found) {
                        let index = this.resultingCases.indexOf(found);
                        this.resultingCases.splice(index, 1);
                    }

                    // Re-build second collection for only submittable
                    this.submitableCases = this.resultingCases.filter(resultingCase => {
                        return resultingCase.canSubmit;
                    });

                    // if they remove all cases, we're resetting the view
                    if (this.resultingCases.length < 1) {
                        this.resetCasesForReview();
                        this.resetReportDetails();
                        this.caseStep = 1;
                    }

                }, () => {
                }
            );
    }

    _resetSearchResults() {
        this.resultingCases = [];
        this.submitableCases = [];
        this.searchCaseNumbers = null;
        this.noSearchResults = false;
        this.searchErrorMessage = null;
    }

    /**
     * Resets the view
     * @returns {null} resets case groups with no return
     * */
    resetCasesForReview() {
        this._resetSearchResults();
        this.selectedReviewType = null;
        this.serviceReviewChecked = false;
        this.underwritingReviewChecked = false;
        this.servicingUnderReview = null;
        this.underwritingUnderReview = null;
        this.filteredDefectAreas = null;
    }

    /**
     * Resets the report details
     * @returns {null} resets report details with no return
     * */
    resetReportDetails() {
        this.defectAreas.forEach(defectArea => defectArea.checked = false);
        this.reportDTO = {
            defectAreas: []
        };
        this.resetCasesForReview();
        this.caseStep = 1;
    }

    /**
     * Submit report with details
     * @returns {null} submits report with no return
     * */
    submitReport() {

        this.reportDTO.cases = _.pluck(this.submitableCases, 'caseNumber');

        let defectCodes = [];

        this.defectAreas.forEach(function (defectArea) {
            if (defectArea.checked) {
                defectCodes.push(defectArea.defectAreaId);
            }
        });

        this.reportDTO.defectAreas = defectCodes;

        this.reportDTO.reviewType = this.selectedReviewType;

        this._client.resources.lenderSelfReport.post(this.reportDTO).first().subscribe(() => {
            this._modalSvc.acknowledge('The selected cases have been submitted for review.', 'Submission Successful').first()
                .subscribe(
                    () => {
                        this.resetReportDetails();
                    }, () => {
                    }
                );
        });
    }

    /**
     * Called when review type is changed
     * @returns {null} no return
     * */
    setReviewType() {
        if (this.resultingCases && this.resultingCases.length) {
            this._modalSvc.askForConfirmation('Changing the Review Type clears entered cases. Are you sure you want to remove cases?', 'CLEAR ENTERED CASES').first()
                .subscribe(() => {
                    this._resetSearchResults();
                });
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
     * Check an array for an object
     * @param {Array} arr Array to scan
     * @param {Object} val Value to find
     * @returns {boolean} returns true or false
     */
    _defectIsInArray(arr, val) {
        return arr.some(function (arrVal) {
            return val === arrVal;
        });
    }

    /**
     * Manages defect area codes sent with report DTO payload
     * @param {string} defectCode code to look for in existing selected defects
     * @returns {null} submits case groups with no return
     * */
    checkBoxChange(defectCode) {
        if (this._defectIsInArray(this.reportDTO.defectAreas, defectCode)) {
            this.reportDTO.defectAreas.splice(this.reportDTO.defectAreas.indexOf(defectCode), 1);
        } else if (!this._defectIsInArray(this.reportDTO.defectAreas, defectCode)) {
            this.reportDTO.defectAreas.push(defectCode);
        }
    }

    /**
     * Filters defect areas based on the selected review type for the report.
     * @returns {null} no return
     * */
    filterDefectAreas() {
        let reviewTypeCode = this.selectedReviewType;
        let filteredAreas = this.defectAreas.sort((a, b) => a.order - b.order).filter(area => {
            return area.reviewTypeCodes && area.reviewTypeCodes.some(c => {
                return c === reviewTypeCode;
            });
        });

        this.filteredDefectAreas = filteredAreas;
    }

}
