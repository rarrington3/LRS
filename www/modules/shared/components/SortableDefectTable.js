// This file was generated from the component scaffold
// Copyright 2016
import { Component } from '@angular/core';
import template from './SortableDefectTable.html';
import styles from './SortableDefectTable.less';
import SortableReviewTable from '../../shared/components/SortableReviewTable';
import Utils from '../Utils';
import { RATINGS } from '../../review/constants';
import UserSvc from '../../app/services/UserSvc';
import { AdminGuard } from '../../app/services/AuthGuards';


@Component({
    selector: 'sortable-defect-table',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/api/core/Component-decorator.html
 * @example
 * <sortable-defect-table name="SortableDefectTable" (change)="onChange($event)"></sortable-defect-table>
 */
export default class SortableDefectTable extends SortableReviewTable {

    constructor(adminGuard: AdminGuard, userSvc: UserSvc) {
        super();

        this._adminGuard = adminGuard;
        this._userSvc = userSvc;
        this.hasTeamMember();
        this.isPlaceHolder();
    }

    totalsColor(severity, review) {

        if (this.lender && this.isInitialLender(review)) {
            return 'no-findings';
        }
        else if (severity.count >= -1) {
            if (severity.rating === RATINGS.UNACCEPTABLE.code) {
                return RATINGS.UNACCEPTABLE.description.toLowerCase();
            } else if (severity.rating === RATINGS.DEFICIENT.code) {
                return RATINGS.DEFICIENT.description.toLowerCase();
            } else {
                return 'conforming';
            }
        } else {
            return 'no-findings';
        }

    }

    _checkBatchOwner() {
        return this.activeBatch && this._userSvc.personnelId && (this._userSvc.personnelId === this.activeBatch.batchOwner);
    }

    _isLocationOrHQAdmin() {
        return this._adminGuard.canActivate();
    }

    allowReassignment(review: Object) {
        return (this._checkBatchOwner() || this._isLocationOrHQAdmin()) && Utils.isReviewInProgress(review);
    }

    //Add bold if rating is Unnaceptable
    boldRating(review, category) {
        if (category === 'rating') {
            if (review[category] === 'unacceptable') {
                return 'bold';
            }
        }
        return '';

    }

    //Dynamically remove team member collumn
    hasTeamMember() {
        return !!this.noTeamMember;
    }

    //Dynamically remove edit icon
    isPlaceHolder() {
        if (this.placeHolder === 'true') {
            return true;
        }
        else {
            return false;
        }
    }

    onEditClick(review) {
        super.onEditClick(review);
    }

    getDefectVal(amount, review) {
        if ((this.lender && this.isInitialLender(review)) || amount < 0) {
            return '';
        }
        else {
            return amount;
        }
    }

    outstandingBinder(review: Object) {
        return !this.lender && review.reviewId === '---';
    }
}
