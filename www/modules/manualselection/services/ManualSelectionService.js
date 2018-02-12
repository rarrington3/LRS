// This file was generated from the service scaffold
// Copyright 2016

import {Injectable} from '@angular/core';
import Client from '../../api/lrs.api.v1.generated';
import ObservableProperty from '../../shared/decorators/observableProperty';


/**
 * Service for operations when selecting a case for review.
 *
 * @example
 * let injector = Injector.resolveAndCreate([ManualSelectionService]);
 * let manualSelectionService = new injector.get(ManualSelectionService);
 * @example
 * class Component {
 * 		constructor(manualSelectionService:ManualSelectionService, manualSelectionService2:ManualSelectionService) {
 *			//injected via angular, a singleton by default in same injector context
 *			console.log(manualSelectionService === manualSelectionService2);
 *		}
 * }
 */
@Injectable()
export default class ManualSelectionService {

    /**
     * Establish a client in order to use http services
     * @type {Client}
     * */
    _client: Client;

    /**
     * Case filter params: selectionReason, selectionSubReason, reviewType, and reviewLocation
     * @type {Object} holds the filters for the current search in Case Selection view
     * */
    caseFilter: Object;

    /**
     * Flag to watch for batch submission
     * @type {Boolean} holds a simple value that changes
     * */
    @ObservableProperty()
    batchWasSubmitted: Boolean;

    /**
     * Construct client instance
     * @param {Client} client Instance of client services
     * */
    constructor(client: Client) {
        this._client = client;
        this.caseFilter = {
            reviewReason: null,
            reviewSubReason: null,
            reviewType: null,
            reviewLocation: null
        };
    }

    getName():string {
        return 'ManualSelectionService';
    }

    /**
     * Create case reviews and create batch info where applicable
     *
     * @param {Array} caseGroups a collection of cases
     * @returns {null} no return
     * */
    submitBatchReviews(caseGroups) {
        let manualSelectionDTO = {};
        let selectionObj;

        // set review params
        manualSelectionDTO.selectionReason = this.caseFilter.reviewReason;
        manualSelectionDTO.selectionSubReason = this.caseFilter.reviewSubReason;
        manualSelectionDTO.reviewType = this.caseFilter.reviewType;
        manualSelectionDTO.reviewLocation = this.caseFilter.reviewLocation;

        manualSelectionDTO.casesForReviewByLender = [];

        caseGroups.forEach(group => {
            selectionObj = {};

            selectionObj.lenderId = group.lenderId;
            selectionObj.cases = [];
            group.cases.forEach(c => {
                if (c.caseNumber !== '') {
                    selectionObj.cases.push(c.caseNumber);
                }
            });

            selectionObj.batchInfo = group.isBatch ? group.batchInfo : null;

            manualSelectionDTO.casesForReviewByLender.push(selectionObj);
        });

        this._client.resources.manualSelection.post(manualSelectionDTO).first().subscribe(() => {
            this.batchWasSubmitted = true;
        });
    }

}
