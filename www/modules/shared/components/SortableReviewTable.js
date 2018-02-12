// This file was generated from the component scaffold
// Copyright 2016

import { Component, Input, Output, EventEmitter } from '@angular/core';
import template from './SortableReviewTable.html';
import styles from './SortableReviewTable.less';
import _ from 'lodash';
import Utils from '../Utils';
import DatePipe from '../pipes/DatePipe';
import { REVIEW_TYPES, REVIEW_LEVEL_TYPE, REVIEW_STATUS } from '../../shared/constants';

let counter = 0;
let _currentSort = {};

@Component({
    selector: 'sortable-table',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/api/core/Component-decorator.html
 * @example
 * <sortable-table name="SortableReviewTable" (change)="onChange($event)"></sortable-table>
 */
export default class SortableReviewTable {

    static ASCENDING = 'ascending';
    static DESCENDING = 'descending';
    static sortItems(items: Array = [], columnName: String, path: Object, direction: String = null) {
        let sort = {};

        sort[columnName] = !_currentSort[columnName];
        _currentSort = sort;

        if (!direction) {
            direction = (_currentSort[columnName]) ? SortableReviewTable.ASCENDING : SortableReviewTable.DESCENDING;
        } else if (direction === SortableReviewTable.DESCENDING) {
            _currentSort[columnName] = false;
        }

        let sortedItems = _.sortBy(items, (item) => {
            let resolvedPath = _.isFunction(path) ? path(item, item.currentReviewLevel) : _.get(item, path, null);
            return resolvedPath;
        });

        if (direction === SortableReviewTable.DESCENDING) {
            sortedItems = sortedItems.reverse();
        }

        return sortedItems;
    }

    constructor() {
        this.paginatorId = 'SortableReviewTable' + counter++;
        this.pageNumber = 1;
    }

    @Input()
    items: Array = [];

    @Input() categories: Array = [];

    @Input() title: String = null;

    @Input() dueToday: Array = [];

    @Input() pastDue: Array = [];

    @Input() id: string = null;

    @Input() activeFilter: string = null;

    //The route to link to the review
    @Input() reviewRoute: string = null;

    @Input() postInitialReviewRouteSegment: string = null;

    //The icon type for this review
    @Input() icon: string = null;

    //The findings for this review
    @Input() findings: Array = [];

    //Is this review a placeholder?
    @Input() placeHolder: boolean;

    //Is this view from a Lender?
    @Input() lender: boolean;

    //Status of batch for lender views
    @Input() batchLevel: string = null;

    //Batch Status for lender views
    @Input() batchStatus: string = null;

    //Does this table need an editable team member?
    @Input() noTeamMember: boolean;

    @Output() onEditReviewClick = new EventEmitter();

    @Input() sortTableIndex = null;

    @Input() activeBatch = null;




    /**
     * Add Color for reviews due today or past due
     * @param {Object} value  - review object
     * @returns {string} - class to be added
     */
    addColor(value) {
        let addedClass = '';

        // Handle a special case where we have already cached a due date css class.
        if (value.dueDateClass) {
            return value.dueDateClass;
        }

        if (value.reviewId && this.dueToday) { //don't sort blank reviews
            if (this.dueToday.find(function (review) {
                return review === value;
            })) {
                addedClass = 'due-today';
            } else if (this.pastDue.find(function (review) {
                return review === value;
            })) {
                addedClass = 'past-due';
            }
        }
        return addedClass;
    }

    //Add harvey icon based on Findings responded to
    addHarvey(review) {
        let addedClass = '';
        if (this.addColor(review) === 'past-due') {
            addedClass = 'past-due-icon';
        } else if (review.lenderAction === 'initiated') {
            addedClass = 'harvey-half';
        } else if (review.lenderAction === 'completed') {
            addedClass = 'harvey-full';
        } else if (review.lenderAction === 'pending') {
            addedClass = 'harvey-empty';
        } else {
            addedClass = 'harvey-none';
        }
        return addedClass;
    }

    routerLinkText(review: Object) {
        if (review.caseNumber) {
            return review.caseNumber;
        }
        else {
            return 'View/Edit';
        }
    }

    allowReassignment(review: Object) {
        return Utils.isReviewInProgress(review);
    }

    //Is this a lender batch that hasnt been submitted by reviewer yet?
    isInitialLender(review) {

        // Check for operational review level and status independently.
        let isOperational = (review.reviewType && review.reviewType.toLowerCase() === REVIEW_TYPES.OPERATIONAL);
        let isOperationalInitialReview = (isOperational && review.currentReviewLevel.status.toLowerCase() !== REVIEW_STATUS.COMPLETED && review.currentReviewLevel.type.toLowerCase() === REVIEW_LEVEL_TYPE.INITIAL);

        if (this.batchStatus) {
            let _batchStatus = this.batchStatus.toLowerCase();
            return this.lender && (_batchStatus !== 'completed' && (isOperationalInitialReview || (!isOperational && this.batchLevel && this.batchLevel.toLowerCase() === REVIEW_LEVEL_TYPE.INITIAL && _batchStatus !== 'pending lender response')));
        }
        else {
            return this.lender && this.batchLevel && this.batchLevel.toLowerCase() === 'initial';
        }
    }

    //Return the value of our table cell contents
    getVal(review, category, isDate) {

        //Split on a dot, indicating we need to use a subCategory
        let subCategory = !_.isFunction(category) ? category.split('.') : null;
        let returnValue = '';
        let validReviewLevel = this.lender ? Utils.findLenderAuthorizedLevel(review) : review.currentReviewLevel;
        if (_.isFunction(category)) {
            returnValue = category(review, validReviewLevel);
        }
        else if (category === 'currentReviewLevel.type' && validReviewLevel && validReviewLevel['type'] !== 'Initial') {
            returnValue = validReviewLevel['type'] + ' ' + validReviewLevel.iteration;
        }
        else if (category === 'completedReviewLevels[0].reviewerName') {
            returnValue = (review.completedReviewLevels.length) ? review.completedReviewLevels[0].reviewerName : '';
        }
        else if (category === 'currentReviewLevel.reviewerName') {
            returnValue = review.currentReviewLevel.reviewerName;
        }
        else if (!(this.isInitialLender(review) && category === 'rating')) {
            if (subCategory.length > 1) {
                returnValue = validReviewLevel ? validReviewLevel[subCategory[1]] : '';
            }
            else {
                returnValue = review[category];
            }

        }

        if (isDate) {
            returnValue = this.formatDate(returnValue);
        }
        return returnValue;
    }

    formatDate(date) {
        let datePipe = new DatePipe();
        return datePipe.transform(date);
    }

    /**
     * Sort the active workload items by category.
     * @param {string} category category we are fitlering by
     * @param {boolean} direction  sort direction
     * @returns {void}
     */
    sortActiveByCategory(category, direction = null) {
        let isObject = _.isObject(category);
        let value = (isObject) ? category.value : category;
        let name = (isObject) ? category.name : category;
        if (this.sortTableIndex) {
            name = `${this.sortTableIndex}-${name}`;
        }
        this.items = SortableReviewTable.sortItems(this.items, name, value, direction);
        this.activeReviews = this.items;
    }

    reviewCount() {
        if (this.items) {
            return this.items.length;
        } else {
            return '';
        }
    }

    setFindings(review, defectArea: string) {
        let count = '';
        if (review.findings) {
            review.findings.forEach((finding) => {
                if (finding.defectAreaCode === defectArea) {
                    count += 1;
                }
                else if (review.defectAreaCodes.indexOf(defectArea, 0)) {
                    count = 0;
                }
            });
        }
        return count;
    }
    onEditClick(review) {
        this.onEditReviewClick.emit(review);
    }

    getRouterLink(review: Object) {
        let reviewType = review.currentReviewLevel.type.toLowerCase().replace(/\s+/g, '');
        if (!Utils.isInitialReviewAndNotCompleted(review) && this.postInitialReviewRouteSegment && this.postInitialReviewRouteSegment[reviewType]) {
            return [this.reviewRoute, review.reviewId, this.postInitialReviewRouteSegment[reviewType]];
        }
        return [this.reviewRoute, review.reviewId];
    }
}
