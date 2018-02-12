// This file was generated from the component scaffold
// Copyright 2017

import { Component, Input, Output, EventEmitter } from '@angular/core';
import styles from './CrossFilterBaseView.less';
import { Observable } from 'rxjs';
import crossfilter from 'crossfilter2';
import CrossFilterService from '../../app/services/CrossFilterService';
import _ from 'lodash';
import Utils from '../Utils';
import template from './CrossFilterBaseView.html';
import moment from 'moment';
import { REVIEW_LEVEL_STATUS } from '../constants';

@Component({
    selector: 'cross-filter-view',
    template: template,
    styles: [styles]
})

export default class CrossFilterBaseView {

    static DIMENSION_METHODS = {
        FINAL_REVIEW_LEVEL: (review) => {
            if (review.currentReviewLevel.type.toLowerCase().indexOf('escalation') > -1) {
                return 'Escalation';
            } else { return review.currentReviewLevel.type; }
        },

        REVIEW_DEADLINES: (review, isLender = false) => {

            if (!review.reviewId || (!isLender && Utils.isReviewPendingLenderResponse(review, isLender)) || (isLender && Utils.isReviewFHAactive(review, isLender))) {
                return 'N/A';
            }

            let compareDate = moment().startOf('day');
            let week = (startDate) => moment().startOf('day').date(startDate.date() + 7);
            let isCompleted = (review.currentReviewLevel.status.toLowerCase() === REVIEW_LEVEL_STATUS.COMPLETED);

            // Cache this value for the Sort Table Component to display the correct status color.
            review.dueDateClass = '';
            let dueDate = (isLender || isCompleted) ? 'dateResponseDueFromLender' : 'reviewerDateDue';

            dueDate = review.currentReviewLevel[dueDate];

            if (!dueDate) {
                return (!review.reviewId) ? null : CrossFilterBaseView.DEADLINE_KEYS.DUE_LATER;
            }

            dueDate = moment(dueDate).startOf('day');

            // Don't apply the red or yelow highlight to lender pending reviews in my workload or FhA reviews in lender active reviews;
            let useDueDateHighlight = ((isLender && isCompleted) || (!isLender && !isCompleted));

            if (dueDate.isSame(compareDate)) {
                review.dueDateClass = (useDueDateHighlight) ? 'due-today' : '';
                return CrossFilterBaseView.DEADLINE_KEYS.DUE_TODAY;
            } else if (compareDate.isBefore(dueDate) && dueDate.isBefore(week(compareDate)) && dueDate.isAfter(compareDate)) {
                return CrossFilterBaseView.DEADLINE_KEYS.DUE_NEXT_WEEK;
            } else if (dueDate.isBefore(compareDate)) {
                review.dueDateClass = (useDueDateHighlight) ? 'past-due' : '';
                return CrossFilterBaseView.DEADLINE_KEYS.PAST_DUE;
            }

            return CrossFilterBaseView.DEADLINE_KEYS.DUE_LATER;
        }
    };


    static createSortFunctionFromArray(array) {
        return (group) => {
            return (array.indexOf(group.key) + 1) || 9999;
        };
    }

    static COMMON_DIMENSION_SORTS = {
        RATING: (group) => CrossFilterBaseView.createSortFunctionFromArray(['Conforming', 'Mitigated', 'Remediated', 'Unacceptable'])(group),
        REVIEW_LEVEL: (group) => CrossFilterBaseView.createSortFunctionFromArray(['Initial', 'Mitigation', 'Escalation', 'Indeminification', 'Forced Indemnification'])(group)
    };

    static COMMON_FILTERS = {
    };

    static CONTROL_TYPE = {
        DROP_DOWN: 'dropdown',
        BUTTON: 'button',
        TEXT: 'text'
    };

    static DEADLINE_KEYS = {
        PAST_DUE: 'Past Due',
        DUE_TODAY: 'Due Today',
        DUE_NEXT_WEEK: 'Due in the Next Week',
        DUE_LATER: 'Over 1 Week'
    }

    static getActivePendingReviews = (reviews, isLender: Boolean = false) => {
        let filteredActiveReviews = reviews.filter((review) => {
            return Utils.isReviewFHAactive(review, isLender);
        });

        let filteredPendingReviews = reviews.filter((review) => {
            return Utils.isReviewPendingLenderResponse(review, isLender);
        });

        return { filteredActiveReviews, filteredPendingReviews };
    }

    _observableSubscription: Observable;
    _dataSource: Observable;
    _dimensionGroups: Array;
    unfilteredRecords: Array = [];
    filteredRecords: Array = [];

    trackByKey(index, item) {
        return item.key;
    }

    @Output() resultsChanged: EventEmitter = new EventEmitter();

    selections: Object = {};
    textSelections: Object = {};
    dropdownSelections: Object = {};

    cf: crossfilter = null;

    crossFilterService: CrossFilterService = null;

    constructor(crossFilterService: CrossFilterService) {
        this.crossFilterService = crossFilterService;
    }

    ngOnInit() { }

    @Input()
    set dataSource(data: Observable) {
        this._resetObservable();
        this._dataSource = data;
        this._observableSubscription = data.subscribe((stream) => {
            this.unfilteredRecords = Array.isArray(stream) ? stream : (stream.currentValue || []);

            // Data source is loaded, initiate the Crossfilter object.
            this.resetCF();
            this.cf = crossfilter(this.unfilteredRecords);
            this.initDimensions();
        });
    }
    get dataSource() {
        return this._dataSource;
    }

    @Input()
    // Filter custom configuration.
    set dimensions(dimensions: Array) {
        this._dimensions = dimensions;

        // Convert the dimension value to actual cross filter dimension.
        this.initDimensions();
    }

    get dimensions() {
        return this._dimensions;
    }


    ngOnDestroy() {
        this._resetObservable();
    }

    _resetObservable() {

        if (this._observableSubscription) {
            this._observableSubscription.unsubscribe();
        }

        this._observableSubscription = null;
    }

    applyDefaults() {
        this.dimensions.forEach(dimension => {
            let _default = dimension.initialDefault || dimension.default;
            if (_default) {
                // TODO: this is currently only built/tested for the dropdown control type
                this.dropdownSelections[dimension.dimensionId] = _default;
                this.selections[dimension.dimensionId] = { dimension: dimension, key: _default };
            }
        });
    }

    removeTemporaryDefaults() {
        this.dimensions.forEach(dimension => {
            if (dimension.initialDefault) {
                dimension.initialDefault = null;
            }
        });
    }

    onButtonFilterSelect(dimension: Object = null, key: Object = null) {
        if (!(this.selections[dimension.dimensionId] && this.selections[dimension.dimensionId].key === key)) {
            this.selections[dimension.dimensionId] = { dimension: dimension, key: key };
        } else {
            // the user has un-toggled this key.  show everyting.
            dimension.cfDimension.filterAll();
            delete this.selections[dimension.dimensionId];
        }
        this.filterRecords();
    }

    onDropdownFilterSelect(dimension: Object = null, key: Object = null) {

        if (key === '(All)') {
            dimension.cfDimension.filterAll();
            delete this.selections[dimension.dimensionId];
            delete this.dropdownSelections[dimension.dimensionId];
        } else {
            this.selections[dimension.dimensionId] = { dimension: dimension, key: key };
        }
        this.filterRecords();
    }

    searchByText(textDimension, value) { //eslint-disable-line
        if (textDimension) {
            let cleanedValue = value.replace('-', '').toLowerCase();

            // We need a custom dimension filter method for case number search.
            textDimension.filter = (key) => {
                return cleanedValue ? !!(key.toLowerCase().startsWith(cleanedValue)) : true;
            };

            this.selections[textDimension.dimensionId] = { dimension: textDimension, key: cleanedValue };
        }
        this.filterRecords();
    }

    // Filter records with multiple selected dimensions
    filterRecords() {

        _.forOwn(this.selections, (selection) => {

            // Filter another dimension.keys for display
            let filterSibling = selection.dimension.filterSiblingDimensionInfo;
            if (filterSibling && filterSibling.filter) {
                let siblingDimension = this.dimensions.find(d => d.title.toLowerCase() === filterSibling.siblingDimensionTitle.toLowerCase());
                if (siblingDimension && siblingDimension.keys) {
                    siblingDimension.keys = filterSibling.filter(selection.key, siblingDimension);
                }
            }

            // Magic happens here.
            let dimensionWrapper = selection.dimension;
            if (_.isFunction(selection.dimension.filter)) {
                dimensionWrapper.cfDimension.filter(dimensionWrapper.filter);
            } else {
                dimensionWrapper.cfDimension.filter(selection.key);
            }
        });

        let firstDimension = this.dimensions[0].cfDimension;
        this.filteredRecords = firstDimension.top(Infinity);
        this.resultsChanged.next(this.filteredRecords);
        this.crossFilterService.resultsChanged.next(this.filteredRecords);
        this.crossFilterService.resultsChangedWithSelections.next({ filteredRecords: this.filteredRecords, selections: this.selections });

        this.dimensions.forEach(d => {
            this.overrideKeyValues(d);
        });

        // because of a bug in select2, we need to clear the dropdown selections and then reset them on the next tick
        // otherwise the dropdowns will often display as if no value is selected
        let temp = this.dropdownSelections;
        this.dropdownSelections = {};
        _.defer(() => this.dropdownSelections = temp);
    }

    // Manually override the key.value generated by crossfilter.
    overrideKeyValues(d: Object) {
        if (_.isFunction(d.parseKeyValue) && d.keys) {
            d.keys.map(k => {
                if (k) {
                    k.value = d.parseKeyValue(k);
                }

                return k;
            });
        }
    }
    initDimensions() {
        if (this.dimensions && this.dimensions.length && this.cf) {

            this.dimensions.forEach((d, index) => {

                // Initiate all the the required Crossfilter dimensions. NOTE: Crossfilter only supports a limited number of dimensions. 32?
                d.cfDimension = this.cf.dimension(r => d.dimension(r));

                d.dimensionId = `d${index}`;

                // Generate the displayed keys to render the filter UI.
                d.keys = d.cfDimension.group().reduceCount().all();

                // Manually override the cross filter generated key value.
                this.overrideKeyValues(d);

                // default to sorting options by how many records they match in total
                d.sort = d.sort || ((g) => g.key);

                // apply the sort.  crossfilter will continue updating the 'value' property of the objects in this new, sorted array
                d.keys = _.sortBy(d.keys, d.sort);

                if (d.ignoredKeys && d.ignoredKeys.length) {
                    d.keys = d.keys.filter(v => !_.includes(d.ignoredKeys, v.key));
                }
            });


            // if we havent done filtering before or filters have us at a point where
            // there are 0 results now, reset all the filters.  this includes
            // setting things to the default values if a default was provided
            if (!this.filteredRecords || !this.filteredRecords.length) {
                this.clearAllFilters();
            } else {
                this.filterRecords(); // re-apply any existing filters
            }
        }
    }

    clearAllFilters() {
        this.dimensions.forEach(dimension => {
            dimension.cfDimension.filterAll();
        });

        this.textSelections = {};
        this.selections = {};
        this.dropdownSelections = {};
        this.applyDefaults();
        this.filterRecords();
    }

    onShowAllClick() {
        this.removeTemporaryDefaults();
        this.clearAllFilters();
    }

    isDimensionKeyActive(dimension: Object, key: Object) {
        let selection = this.selections[dimension.dimensionId];
        return (selection && selection.key === key);
    }

    resetCF() {
        if (this.cf) {
            this.onShowAllClick();
            this.cf.remove();
        }
    }
}
