// This file was generated from the component scaffold
// Copyright 2016

import { Component } from '@angular/core';
import template from './ReviewContainer.html';
import styles from './ReviewContainer.less';
import { ActivatedRoute, Router } from '@angular/router';
import GlobalStateSvc from '../services/GlobalStateSvc';
import ReviewService from '../services/ReviewService';
import { HqAdminGuard, ReviewLocationAdminGuard, ReviewerOrAdminGuard } from '../../app/services/AuthGuards';
import ModalSvc from '../../app/services/ModalSvc';
import DictionarySvc from '../../app/services/DictionarySvc';
import Utils from '../../shared/Utils';
import { REVIEW_LEVEL_TYPE } from '../../shared/constants';
import DatePipe from '../../shared/pipes/DatePipe';
import _ from 'lodash';
import { INDEMNIFICATION_TYPES } from '../constants';
import DevAidService from '../services/DevAidService';
import ArrayUtils from '../../shared/utils/ArrayUtils';


@Component({
    selector: 'review-container',
    template: template,
    styles: [styles]
})

export default class ReviewContainer {
    _subscriptions = [];
    route: ActivatedRoute;
    _router: Router;
    globalStateSvc: GlobalStateSvc;
    _dictSvc: DictionarySvc;
    currentReview: Object = null;
    reviewActions: Object = {
        showMgmtDecision: false,
        forceEscalation: false,
        forceIndemnification: false,
        referCase: false,
        cancelCase: false,
        initiateMRB: false,
        showIndemnification: false,
        showIndemInfo: false
    };
    showMgmtDecision: Boolean = false;
    _modal: ModalSvc;
    selectedTab: String = null;
    _hqAdminGuard: HqAdminGuard;
    _reviewLocationAdminGuard: ReviewLocationAdminGuard;
    openForceIndem: Boolean = false;
    selectedIndemTerm: String = '';
    _devAid: DevAidService = null;
    isOperationalReview: Boolean = false;
    indemnificationTypes: Array = [];
    selectedCancelReason: String = null;
    cancellationReasons: Array = [];
    _reviewerOrAdminGuard: ReviewerOrAdminGuard;

    isQCPostInitial: Boolean = false;
    constructor(
        route: ActivatedRoute,
        globalState: GlobalStateSvc,
        reviewService: ReviewService,
        dictSvc: DictionarySvc,
        hqAdminGuard: HqAdminGuard,
        modal: ModalSvc,
        router: Router,
        reviewLocationAdminGuard: ReviewLocationAdminGuard,
        devAidService: DevAidService,
        reviewerOrAdminGuard: ReviewerOrAdminGuard) {

        this.route = route;
        this.globalStateSvc = globalState;
        this._dictSvc = dictSvc;
        this.reviewService = reviewService;
        this._modal = modal;
        this._router = router;
        this._hqAdminGuard = hqAdminGuard;
        this._reviewerOrAdminGuard = reviewerOrAdminGuard;
        //Keep track of referral info for UI
        this.localReferral = { 'hudNotes': true, 'externalNotes': true, 'oigNotes': true };
        this._reviewLocationAdminGuard = reviewLocationAdminGuard;
        this._devAid = devAidService;
        this.indemnificationTypes = ArrayUtils.toArray(INDEMNIFICATION_TYPES);
    }

    ngOnInit() {

        // keep track of the most recent review the user has accessed in this session
        // so that we can redirect them back to it if they later access the /review route without
        // the reviewId parameter populated (see ReviewContainerInactive which does this redirection)
        this.resetSubscriptions();
        this._subscriptions.push(this.route.params.subscribe(params => {
            this.globalStateSvc.currentReviewId = params.reviewId;
        }));

        this._subscriptions.push(this.reviewService.currentReviewObservable.subscribe((change) => {
            this.currentReview = change.currentValue;

            this._enableReviewActions();

            if (!this.isReviewReadOnly(this.currentReview)) {
                this.reviewService.getReferral();
            }

            this.isQCPostInitial =  Utils.isQCreview(this.currentReview) && !Utils.isInitialReview(this.currentReview);
        }));


        this._subscriptions.push(this.reviewService.currentReferralObservable.subscribe((change) => {
            this.referral = change.currentValue;
            this.setInitialReferallProperties();
        }));

        // Activate the dev aide
        this._devAid.activateCommandLine();
    }

    onTabSelect(tab: String) {
        this.selectedTab = tab;
    }

    _showReviewActionModal(title: String = '', message: String = '', confirmHandler: Object = () => { }) {
        this._modal.askForConfirmation(
            `<p>${message}</p>`, title)
            .first().subscribe(
            confirmHandler,
            () => {
            }
            );
    }

    formatDate(date) {
        let datePipe = new DatePipe();
        return datePipe.transform(date);
    }

    isReviewReadOnly(review: Object) {
        return Utils.isReviewReadOnly(review);
    }

    isReviewIndem(review: Object) {
        return Utils.isIndemReview(review);
    }

    onReviewActionClick(action) {
        let gotoSummary = () => {
            this._router.navigate(['/']);
        };

        switch (action) {
            case 'showIndemnification':
                this._router.navigate([`/review/${this.currentReview.reviewId}/reviewIndemnification`]);
                break;
            case 'referCase':
                this.openReferCase = true;
                break;
            case 'mgmtDecision':
                this._showReviewActionModal('MANAGEMENT DECISION', 'Click Confirm to close the Review and log a Managment Decision.', () => {
                    this.reviewService.postManagementDecision(this.globalStateSvc.currentReviewId).subscribe(gotoSummary);
                });
                break;
            case 'forceIndemnification':
                this.openForceIndem = true;
                break;
            case 'forceEscalation':
                this._showReviewActionModal('FORCE ESCALATION', 'Click Confirm to end this review level and escalate the review.', () => {
                    this.reviewService.postForceEscalation(this.globalStateSvc.currentReviewId).subscribe(gotoSummary);
                });
                break;
            case 'initiateMRB':
                this._showReviewActionModal('INITIATE MRB CASE', '<p>Clicking Confirm will initiate a Case with the Mortgage Review Board.</p><p>Please confirm that you want to continue, or click Cancel to exit.</p>', () => {
                    this.reviewService.initiateMRB(this.globalStateSvc.currentReviewId).subscribe(gotoSummary);
                });
                break;
            case 'cancelCase':
                this._openCancelCase();
                break;
            case 'showIndemInfo':
                this._router.navigate([`/review/${this.currentReview.reviewId}/reviewIndemnification`], {queryParams: {showConfirmingData: true}});
                break;
            default:
                throw (new Error('Invalid review action type!'));
        }
    }

    showReviewActions() {
        return _.keys(_.pick(this.reviewActions, (value) => value)).length;
    }

    setReferalType(type) {
        this.localReferral[type] = !this.localReferral[type];
    }

    setInitialReferallProperties() {
        _.forEach(this.referral, (value, key) => {
            if (value === null) {
                this.localReferral[key] = false;
            }
        });
    }

    saveCase() {
        _.forEach(this.localReferral, (value, key) => {
            if (value === false) {
                this.referral[key] = null;
            }
        });
        this.referCase();
    }

    referCase() {
        this.reviewService.postReferral(this.referral);
    }

    resetSubscriptions() {
        this._subscriptions.forEach((subscription) => {
            subscription.unsubscribe();
        });
        this._subscriptions = [];
    }

    ngOnDestroy() {
        this.resetSubscriptions();
        this._devAid.deactivateCommandLine();
        if (this.reviewService) {
            this.reviewService.currentReview = null;
        }
    }

    _enableReviewActions() {

        // Disable all top menu options if the review is QC
        if (this.currentReview && !Utils.isQCmitigatedReview(this.currentReview)) {
            // Enable or disable Review Actions based on role.
            let type = this.currentReview.currentReviewLevel.type.toLowerCase();
            let isAnyEscalation = (type === REVIEW_LEVEL_TYPE.ESCALATION || type === REVIEW_LEVEL_TYPE.HQ_ESCALATION);
            let isHQEscalation = type === REVIEW_LEVEL_TYPE.HQ_ESCALATION;

            this.isOperationalReview = Utils.isReviewLevelOperational(this.currentReview);
            this.reviewActions.showMgmtDecision = isHQEscalation && this._hqAdminGuard.canActivate();
            this.reviewActions.initiateMRB = isHQEscalation && this._hqAdminGuard.canActivate() && !this.isOperationalReview;
            this.reviewActions.forceIndemnification = isAnyEscalation && (this._hqAdminGuard.canActivate() || this._reviewLocationAdminGuard.canActivate()) && !this.isOperationalReview;
            this.reviewActions.showIndemnification = Utils.isLevelIndemnified(this.currentReview) && !this.isOperationalReview && !Utils.isReviewCompleted(this.currentReview);
            this.reviewActions.showIndemInfo =  Utils.isLevelIndemnified(this.currentReview) && !this.isOperationalReview && Utils.isReviewCompleted(this.currentReview);
            this.reviewActions.cancelCase = (this._hqAdminGuard.canActivate() || this._reviewLocationAdminGuard.canActivate()) && !this.isOperationalReview;
            this.reviewActions.forceEscalation = (type !== 'initial' && !isHQEscalation);
            this.reviewActions.referCase = this._reviewerOrAdminGuard.canActivate();
        }
    }


    isInitial() {
        if (!this.currentReview) {
            return true;
        }

        return (this.currentReview.currentReviewLevel.type.toLowerCase() === REVIEW_LEVEL_TYPE.INITIAL);
    }

    _openCancelCase() {
        this._dictSvc.getreviewCancelReasons().subscribe((cancellationReasons) => {
            this.cancellationReasons = cancellationReasons;
            this.selectedCancelReason = null;
            this.openCancelCase = true;
        });
    }

    cancelCase() {
        if (this.selectedCancelReason) {
            this.openCancelCase = false;
            this.reviewService.cancelCase(this.currentReview, this.selectedCancelReason).subscribe(() => {
                this._router.navigate(['/workload/summary']);
            });
        }
    }

    confirmForceIndem() {
        this.openForceIndem = false;
        this.reviewService.postForceIndemnification(this.globalStateSvc.currentReviewId, { indemnificationTypeCode: this.selectedIndemTerm }).subscribe((data) => {
            if (data.currentValue) {
                // The review data has been refreshed, navigate to indem step 1.
                this._router.navigate([`/review/${this.globalStateSvc.currentReviewId}/reviewIndemnification`]);
            }
        });

    }
}
