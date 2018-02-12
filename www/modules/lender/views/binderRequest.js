// This file was generated from the view scaffold
// Copyright 2016

import {Component, Input} from '@angular/core';
import {BinderReceived, CaseNumber} from '../pipes/BinderReceived';
import {ActivatedRoute}  from '@angular/router';
import {ModalSvc, UserSvc} from '../../app/services';
import {HqAdminGuard, ResponseCoordinatorGuard, BinderRequestGuard} from '../../app/services/AuthGuards';
import Client from '../../api/lrs.api.v1.generated';
import template from './binderRequest.html';
import styles from './binderRequest.less';
import FileSaver from 'file-saver';
import {SERVER_ERROR} from '../../shared/constants';
import ObservableProperty from '../../shared/decorators/observableProperty';
import CrossFilterBaseView from '../../shared/components/CrossFilterBaseView';
import CrossFilterService from '../../app/services/CrossFilterService';
import moment from 'moment';
import SortableReviewTable from '../../shared/components/SortableReviewTable';

@Component({
    selector: 'binder-request',
    template: template,
    styles: [styles],
    pipes: [BinderReceived, CaseNumber]
})
/**
 * @see https://angular.io/docs/ts/latest/guide/router.html
 */
export default class BinderRequest {

    route: ActivatedRoute;
    binderRequestsFhaPageNumber: Number = 0;
    binderRecieptsFhaPageNumber: Number = 0;

    /**
     * Establish a client in order to use http services
     * @type {Client}
     * */
    _client: Client;

    /**
     * Service for displaying modals
     * @type {ModalSvc}
     * */
    _modalSvc: ModalSvc;

    /**
     * Service for displaying modals
     * @type {UserSvc}
     * */
    _userSvc: UserSvc;

    /**
     * Any new subscription will be added into this collection
     * @type {Array}
     */
    subscriptions: Array = [];

    /**
     * The type of user logged in determines what components are displayed in the view
     * Eg. 'lender' or 'FHA'
     *
     * @type {string}
     * */
    currentUserRole: string;

    /**
     * The name of the location for the current user (used to set the default filter value)
     *
     * @type {string}
     * */
    currentUserLocation: string;

    /**
     * Collection of binder requests (source for filtering)
     * @type {Array}
     */
    @ObservableProperty()
    binders: Array = [];


    /**
     * Filtered collection of not yet received binder requests based on user input
     * @type {Array}
     */
    @Input()
    binderRequests: Array = [];

    /**
     * Filtered collection of receieved binder requests based on user input
     * @type {Array}
     */
    @Input()
    recentRecipients: Array = [];


    /**
     * Enums for filter types of users logged in
     * @type {Object}
     */
    USER_ROLES: Object;

    /**
     * Enums for category types when sorting list
     * @type {Object}
     */
    CASE_CATS: Object;

    /**
     * Reference for inline modal to be displayed or not
     * @type {Boolean}
     */
    receiveModalIsOpen = false;

    /**
     * Reference of selected binder when presenting modal to receive a binder
     * @type {api.v1.Binder}
     */
    binderToReceive = null;


    /**
     * Reference for which tab is selected which determines wich counts to show in filter lists
     * @type {Boolean}
     */
    selectedTab: Number;

    /**
     * If the reviewer's location is not HQ then they cannot filter by location
     * @type {Boolean}
     */
    mayChangeLocation: Boolean = true;

    _hqAdminGuard: HqAdminGuard;
    _responseCoordinatorGuard: ResponseCoordinatorGuard;
    _binderRequestGuard: BinderRequestGuard;
    crossFilterService: CrossFilterService;

    binderDimensions:Array = [];

    /**
     * Note: This view is used for both lenders and FHA users. The API calls are forked where applicable.
     *
     * @param {Client} client Instance of client services
     * @param {ActivatedRoute} route Active route
     * @param {ModalSvc} modalSvc class
     * @param {UserSvc} userSvc class
     * @param {HqAdminGuard} hqAdminGuard class
     * @param {ResponseCoordinatorGuard} responseCoordinatorGuard class
     * @param {BinderRequestGuard} binderRequestGuard class
     * @param {CrossFilterService} crossFilterService class
     * */
    constructor(client: Client, route: ActivatedRoute, modalSvc: ModalSvc, userSvc: UserSvc, hqAdminGuard: HqAdminGuard, responseCoordinatorGuard: ResponseCoordinatorGuard, binderRequestGuard: BinderRequestGuard, crossFilterService: CrossFilterService) {
        this._client = client;
        this.route = route;
        this._modalSvc = modalSvc;
        this._userSvc = userSvc;
        this._hqAdminGuard = hqAdminGuard;
        this._responseCoordinatorGuard = responseCoordinatorGuard;
        this._binderRequestGuard = binderRequestGuard;
        this.crossFilterService = crossFilterService;
    }

    ngOnInit() {
        // vars for types of user
        this.USER_ROLES = {
            FHA: 'fha',
            LENDER: 'lender'
        };

        this.selectedTab = 0;


        // build const vars for sorting list
        this.CASE_CATS = {
            CASE_NUM: 'caseNumber',
            BORROWER: 'borrowerName',
            LENDER: 'lenderName',
            REVIEW_TYPE: 'reviewType',
            REQUEST_DATE: 'requestedDate',
            DUE_DATE: 'dueDate',
            REQUESTED_FROM: 'requestedFrom',
            ASSIGNED_TO: 'assignedTo',
            SENT_DATE: 'sentDate',
            RECEIVED_DATE: 'receivedDate',
            LOCATION: 'locationName',
            STATUS: 'status'
        };

        this.currentUserRole = this._determineUserRole();


        this._client.resources.locations.get().first().subscribe(locations => {
            // set default location for reviewer
            let userLocation = locations.find(loc => loc.locationId === this._userSvc.locationId);
            this.currentUserLocation = userLocation && userLocation.name || '';

            this._fetchBinders();

            this.initCrossFilters();
        });

        this.subscriptions.push(this.crossFilterService.resultsChanged.subscribe((records) => {
            this.onCrossFilterChange(records || []);
        }));
    }

    ngOnDestroy() {
        this.subscriptions.forEach(subscription => {
            subscription.unsubscribe();
        });
    }

    /**
     * Process to fetch binders based on user role
     * @returns {null} no return
     */
    _fetchBinders() {
        if (this.currentUserRole === this.USER_ROLES.LENDER) {
            this._client.resources.binders.lender.get().first().subscribe(result => {

                this._handleBinderList(result);
            });
        } else {
            this._client.resources.binders.fha.get().first().subscribe(result => {

                this._handleBinderList(result);
            });
        }
    }

    /**
     * Process initial binder load
     * @param {result} result is Array of api.v1.Binder
     * @returns {null} no return
     */
    _handleBinderList(result) {
        // set source for filtering
        this.binders = result;

    }

    /**
     * Evaluates current user authority to determine what components to display
     * @returns {string} returns role as a string
     */
    _determineUserRole() {
        let role;

        if (this._responseCoordinatorGuard.canActivate()) {
            role = this.USER_ROLES.LENDER;
        } else if (this._binderRequestGuard.canActivate()) {
            role = this.USER_ROLES.FHA;
        }

        return role;
    }

    /**
     * Updates the transient 'status' value on each binder based on dates and if the binder was downloaded
     * Only used when in lender view mode
     * @param {Array} arr incoming array to update
     * @returns {Null} returns nothing
     */
    _updateStatuses() {
        this.binderRequests.forEach(binder => {
            if (binder.requestDate && !binder.sendDate) {
                binder.status = 'Requested';
            } else if (binder.requestDate && binder.sendDate) {
                binder.status = 'Shipped';
            } else if (binder.receivedDate) {
                binder.status = 'Received';
            }
        });
    }

    /**
     * Sorts binderRequests collection by cat parameter
     * @param {string} cat category name to sort on
     * @param {Boolean} useRecentRecipient use the recentRecipients
     * @returns {Null} no return
     */
    sortCasesByCategory(cat, useRecentRecipient = false) {
        if (useRecentRecipient) {
            this.recentRecipients = SortableReviewTable.sortItems(this.recentRecipients, cat, cat);
        } else {
            this.binderRequests = SortableReviewTable.sortItems(this.binderRequests, cat, cat);
        }
    }


    /**
     * Display a modal to confirm receipt of a binder and store a reference to the selected binder
     *
     * @param {api.v1.Binder} selectedBinder selected in view
     * @returns {Null} no return
     */
    receiveBinder(selectedBinder) {
        this.receiveModalIsOpen = true;
        this.binderToReceive = selectedBinder;
    }

    /**
     * The response will return an object with the assignedTo property filled out.
     * Find the binder in binder source list and in the filtered list.
     * We then switch the view to the recent recipients list.
     *
     * @returns {Null} no return
     */
    confirmReceipt() {

        if (this.currentUserRole === this.USER_ROLES.LENDER) {
            this._client.resources.binders.lender.binderId(this.binderToReceive.binderId).send.put().first().subscribe(result => {
                this._handleBinderReceipt(result);
            });
        } else {
            this._client.resources.binders.fha.binderId(this.binderToReceive.binderId).receive.put().first().subscribe(
                result => {
                    this._handleBinderReceipt(result);
                },
                response => {
                    if (response.status === SERVER_ERROR.CONFLICT) {
                        // If there was an issue receiving this binder (409), let the user know
                        this.receiveModalIsOpen = false;
                        this.binderToReceive = null;
                        this._modalSvc.acknowledge('There was an issue receiving this binder. Please try again later.', 'Binder Receive Error').first().subscribe();
                    }
                    else {
                        throw new Error(response);
                    }
                }
            );
        }
    }

    /**
     * Process binder receipt by updating assignedTo property on binder and then fetch binders again
     * to update the view
     *
     * @param {result} result is Array of api.v1.Binder
     * @returns {null} no return
     */
    _handleBinderReceipt(result) {
        let foundBinder = this.binders.find(binder => binder.binderId === result.binderId);
        foundBinder = this.binderRequests.find(binder => binder.binderId === result.binderId);

        if (foundBinder) {
            foundBinder.assignedTo = result.assignedTo;
        }

        this.receiveModalIsOpen = false;
        this.binderToReceive = null;


        this._modalSvc.acknowledge('Binder confirmed to receive.', 'CONFIRMED').first()
            .subscribe(
                () => {
                    this._fetchBinders();
                }, () => {
                }
            );
    }


    /**
     * Listener for updates to sendDate column date controls.
     * Find the binder in binder source list and in the filtered list
     *
     * @param {api.v1.Binder} selectedBinder selected to update
     * @returns {Null} returns nothing
     */
    updateBinder(selectedBinder) {
        if (this.currentUserRole === this.USER_ROLES.LENDER) {
            this._client.resources.binders.lender.binderId(selectedBinder.binderId).send.put(selectedBinder).first().subscribe(result => {
                this._handleBinderUpdate(result);
            });
        } else {
            this._client.resources.binders.fha.binderId(selectedBinder.binderId).put(selectedBinder).first().subscribe(result => {
                this._handleBinderUpdate(result);
            });
        }

    }

    /**
     * Process binder update by updating the sendDate
     * @param {result} result is api.v1.Binder
     * @returns {null} no return
     */
    _handleBinderUpdate(result) {
        let foundBinder = this.binders.find(binder => binder.binderId === result.binderId);
        if (foundBinder) {
            foundBinder.sendDate = result.sendDate;
        }
        foundBinder = this.binderRequests.find(binder => binder.binderId === result.binderId);
        if (foundBinder) {
            foundBinder.sendDate = result.sendDate;
        }
    }

    /**
     * Downloads a list of all outstanding binders that have been requested from the records center and match the filters that have been applied
     *
     * @returns {Null} returns nothing
     */
    downloadBinders() {
        let cases = '';
        let dataString;
        let binderCount = this.binders.length - 1;

        // using this.binderRequests which is filtered rather than this.binders which is everything
        this.binderRequests.forEach(function (binder, i) {
            if (binder.requestedFrom === 'Record Center' && !binder.receivedDate) {
                dataString = binder.caseNumber;
                cases += i < binderCount ? dataString + '\n' : dataString;
            }
        });

        let blob = new Blob([cases], {type: 'text/plain'});
        FileSaver.saveAs(blob, 'binders.csv');
    }

    _filterBinders(result) {

        // if in lender view mode show all binders
        if (this.currentUserRole === this.USER_ROLES.LENDER) {
            this.binderRequests = SortableReviewTable.sortItems(result, this.CASE_CATS.DUE_DATE, 'dueDate');
        } else {
            this.binderRequests = SortableReviewTable.sortItems(result.filter(item => !item.receivedDate), this.CASE_CATS.DUE_DATE, 'dueDate');
        }

        // set received collection for view
        this.recentRecipients = SortableReviewTable.sortItems(result.filter(item => item.receivedDate), 'Received Date', 'receivedDate', SortableReviewTable.DESCENDING);

        // statuses for reviewer view
        this._updateStatuses();

    }
    onCrossFilterChange(filteredRecords: Array) {
        this._filterBinders(filteredRecords);
    }

    initCrossFilters() {

        this.binderDimensions = [
            {
                title: 'CASE NUMBER SEARCH',
                controlType: CrossFilterBaseView.CONTROL_TYPE.TEXT,
                dimension: (binder) => (binder.caseNumber ? binder.caseNumber.replace('-', '') : '')
            }
        ];

        if (this.currentUserRole === this.USER_ROLES.FHA) {
            this.binderDimensions.push({
                title: 'LOCATION',
                controlType:  CrossFilterBaseView.CONTROL_TYPE.DROP_DOWN,
                dimension: (binder) => binder.locationName,
                disabled: !this._hqAdminGuard.canActivate(),
                default: this.currentUserLocation
            });
        }
        this.binderDimensions.push(
            {
                title: 'BINDER DEADLINES',
                controlType:  CrossFilterBaseView.CONTROL_TYPE.BUTTON,
                dimension: (binder) => {
                    let compareDate = moment().startOf('day');
                    let week = (startDate) => moment().startOf('day').date(startDate.date() + 7);

                    let dueDate = moment(binder.dueDate).startOf('day');

                    if (dueDate.isSame(compareDate)) {
                        return CrossFilterBaseView.DEADLINE_KEYS.DUE_TODAY;
                    } else if (compareDate.isBefore(dueDate) && dueDate.isBefore(week(compareDate)) && dueDate.isAfter(compareDate)) {
                        return CrossFilterBaseView.DEADLINE_KEYS.DUE_NEXT_WEEK;
                    } else if (dueDate.isBefore(compareDate)) {
                        return CrossFilterBaseView.DEADLINE_KEYS.PAST_DUE;
                    }

                    return CrossFilterBaseView.DEADLINE_KEYS.DUE_LATER;
                },
                sort: (group) => CrossFilterBaseView.createSortFunctionFromArray([
                    CrossFilterBaseView.DEADLINE_KEYS.PAST_DUE,
                    CrossFilterBaseView.DEADLINE_KEYS.DUE_TODAY,
                    CrossFilterBaseView.DEADLINE_KEYS.DUE_NEXT_WEEK,
                    CrossFilterBaseView.DEADLINE_KEYS.DUE_LATER])(group),
                style: (key) => {
                    switch (key) {
                        case CrossFilterBaseView.DEADLINE_KEYS.DUE_TODAY:
                            return 'due-today';
                        case CrossFilterBaseView.DEADLINE_KEYS.PAST_DUE:
                            return 'past-due';
                        default:
                            return null;
                    }
                }
            }
        );
        if (this.currentUserRole === this.USER_ROLES.FHA) {
            this.binderDimensions.push(
                {
                    title: 'REQUESTED FROM',
                    controlType: CrossFilterBaseView.CONTROL_TYPE.BUTTON,
                    dimension: (binder) => binder.requestedFrom
                }, {
                    title: 'BINDER TYPE',
                    controlType: CrossFilterBaseView.CONTROL_TYPE.BUTTON,
                    dimension: (binder) => { return binder.isElectronic ? 'Electronic' : 'Paper'; }
                }
            );
        }

        this.binderDimensions.push(
            {
                title: 'REVIEW TYPE',
                controlType: 'button',
                dimension: (binder) => binder.reviewType || 'N/A'
            }
        );
    }

}
