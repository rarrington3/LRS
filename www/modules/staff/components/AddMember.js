// This file was generated from the component scaffold
// Copyright 2016

import {Component, Input, Output, EventEmitter, SimpleChanges, SimpleChange, ViewChild} from '@angular/core';
import {NgForm}  from '@angular/forms';
import template from './AddMember.html';
import styles from './AddMember.less';

import {ReviewerSvc} from '../../app/services';
import {SERVER_ERROR} from '../../shared/constants';
import Client from '../../api/lrs.api.v1.generated';

import _ from 'lodash';

@Component({
    selector: 'add-member',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/api/core/Component-decorator.html
 * @example
 * <add-member name="AddMember" (change)="onChange($event)"></add-member>
 */
export default class AddMember {

    _client: Client;

    _reviewerSvc: ReviewerSvc;

    _reviewersByLocDict = {};

    _reviewerByIdDict = {};

    _newReviewer = {
        locationId: '',
        reportsTo: '',
        hudId: ''
    };

    staffId: string = '';

    notFound: boolean = false;

    form: NgForm;

    @Input() locations: Array<any> = [];

    @Input() reviewers: Array<any> = [];

    @Input() reviewersByLoc: Array<any> = [];

    @Input() reviewer: any = {};

    @Input() userIsLocationAdmin: Boolean<any> = false;

    @Input() hqLocationId: string = '';

    @Output() canSaveChange: EventEmitter = new EventEmitter();

    @ViewChild('staffAddMemberForm') currentForm: NgForm;

    constructor(reviewerSvc: ReviewerSvc, client: Client) {
        this._reviewerSvc = reviewerSvc;
        this._client = client;
    }

    ngAfterViewChecked() {
        this.onFormChanged();
    }

    ngOnChanges(changes: SimpleChanges) {
        let reviewersChange: SimpleChange = changes['reviewers'];
        let reviewerChange: SimpleChange = changes['reviewer'];

        if (reviewersChange) {
            this._loadReviewersByLocDict(reviewersChange.currentValue);
            this._loadReviewersDict(reviewersChange.currentValue);
            this._loadReviewersByLoc();
        }

        if (reviewerChange) {
            // Unless the modal gets opened with a reviewer we rest to defaults
            this.reset(_.isEmpty(reviewerChange.currentValue));
        }
    }

    reset(resetForm: boolean) {
        this.staffId = '';
        this.notFound = false;
        let reviewer = {};
        Object.assign(reviewer, this._newReviewer, this.reviewer);
        Object.assign(this.reviewer, reviewer);

        if (resetForm && this.form) {
            this.form.resetForm();
        }

        this.checkCanSave();
    }

    checkCanSave() {
        if (this.form) {
            let canSave = !!(this.form && this.form.dirty && this.form.valid) && !!(this.reviewer && this.reviewer.hudId);
            this.canSaveChange.emit(canSave);
        }
    }

    search() {
        this._client.resources.fhaConnection.hudId(this.staffId).get()
            .subscribe(
                reviewer => {
                    Object.assign(this.reviewer, reviewer);
                    this.notFound = false;
                    // trigger check
                    this.checkCanSave();
                },
                response => {
                    if (response.status === SERVER_ERROR.GONE) {
                        this.notFound = true;
                        Object.assign(this.reviewer, this._newReviewer);

                        // trigger check
                        this.checkCanSave();
                    }
                    else {
                        throw new Error(response);
                    }
                });
    }

    /**
     * Checks on form change, pattern that can be used to verify form's changes
     * @returns {void}
     */
    onFormChanged() {
        if (this.currentForm === this.form) {
            return;
        }

        this.form = this.currentForm;

        if (!this.form) {
            return;
        }

        this.form.valueChanges.subscribe(() => {
            //trigger check
            this.checkCanSave();
        });
    }

    onReportsToChanged(reviewerId) {
        // when no location set, we use the selected reviewer location
        if (reviewerId && !this.reviewer.locationId) {
            this.reviewer.locationId = this._reviewerByIdDict[reviewerId].locationId;
        }
    }

    onLocationChanged(locationId) {
        this._loadReviewersByLoc(locationId);
        // when selected reviewer not part of available reviewers for selected location, clearing it out
        if (locationId && this.reviewer.reportsTo) {
            let reviewer = this._reviewerByIdDict[this.reviewer.reportsTo];
            if (!this.reviewersByLoc.length) {
                this.reviewer.reportsTo = '';
            } else {
                if (this.reviewersByLoc.indexOf(reviewer) < 0) {
                    this.reviewer.reportsTo = '';
                }
            }
        }
    }

    _loadReviewersByLoc(locationId) {
        locationId = locationId || '';
        let reviewers = this._reviewersByLocDict[locationId];
        reviewers = reviewers ? reviewers : [];
        this.reviewersByLoc = reviewers;
    }

    _loadReviewersByLocDict(reviewers) {
        this._reviewersByLocDict = this._reviewerSvc.getReviewersDictByLoc(reviewers, this.hqLocationId);
    }

    _loadReviewersDict(reviewers) {
        this._reviewerByIdDict = this._reviewerSvc.getReviewerDictById(reviewers);
    }
}
