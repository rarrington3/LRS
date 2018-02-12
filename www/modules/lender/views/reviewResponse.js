// This file was generated from the view scaffold
// Copyright 2016

import {Component, ElementRef} from '@angular/core';
import {ActivatedRoute, Router}  from '@angular/router';
import template from './reviewResponse.html';
import styles from './reviewResponse.less';
import LenderService from '../services/LenderService';
import {Observable} from 'rxjs';
import ReviewService from '../../review/services/ReviewService';
import DictionarySvc from '../../app/services/DictionarySvc';
import Client from '../../api/lrs.api.v1.generated';
import FindingsService from '../../review/services/FindingsService';
import ReviewLevelFindingRenderer from '../../shared/components/ReviewLevelFindingRenderer';
import Utils from '../../shared/Utils';
import FindingUtils from '../../review/FindingUtils';
import ModalSvc from '../../app/services/ModalSvc';
import {ResponseCoordinatorGuard} from '../../app/services/AuthGuards';

let compiledConfig = require('config');
// ResponseCoordinatorGuard
@Component({
    selector: 'review-response',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/guide/router.html
 */
export default class ReviewResponse {

    route:ActivatedRoute;
    finding:Object = {};
    status:String = 'normal';
    _lenderService:LenderService;
    _review:Object;
    _reviewService:ReviewService;
    findingSourceDescription:String;
    findingCauseDescription:String;
    formActive:Boolean = false;
    selectedTierDescription:String;
    _allRatingCodes:Array;
    _defectAreaSeverities:Array;
    _router:Router;
    reviewDocuments:Array = [];
    completedReviewLevelsData:Array = [];
    //fileuploadadd, fileuploaddone, fileuploadfail
    fileUploadStatus:String = null;

    failedFile:Object = {};
    reviewLevelType:String = '';

    isReadOnly: Boolean = false;

    /**
     * Any new subscription will be added into this collection
     * @type {Array}
     */
    subscriptions:Array = [];

    selectedUploadedDocumentId:String = '';
    postURL:String = null;
    _client:Client;
    _findingsService:FindingsService;
    _findingId:String = null;
    _elementRef:ElementRef;
    requireLenderResponse: Boolean = false;
    isPostInitial: Boolean = false;
    _modalSvc: ModalSvc;
    _responseCoordinatorGuard: ResponseCoordinatorGuard;

    constructor(route:ActivatedRoute, lenderService:LenderService, reviewService:ReviewService, dictionaryService:DictionarySvc, router:Router, client:Client, findingsService:FindingsService, elementRef: ElementRef, modalSvc: ModalSvc, responseCoordinatorGuard: ResponseCoordinatorGuard) {
        this.route = route;
        this._router = router;
        this._lenderService = lenderService;
        this._reviewService = reviewService;
        this._dictionaryService = dictionaryService;
        this._client = client;
        this._findingsService = findingsService;
        this._elementRef = elementRef;
        this._modalSvc = modalSvc;
        this._responseCoordinatorGuard = responseCoordinatorGuard;
    }

    ngOnInit() {
        this.subscriptions.push(this.route.params.subscribe(params => {
            this._findingId = params.findingId;

            this._lenderService.getReview(this._lenderService.activeReviewId).first().subscribe(review => {
                this._review = review;
                this.isPostInitial = (review.currentReviewLevel.type.toLowerCase() !== 'initial');
                this.reviewDocuments = this._review.documents || [];
                this.reviewLevelType = this._review.currentReviewLevel.type || '';

                Utils.addForceEscalationWarning(this._review, true);

                if (this.isPostInitial) {
                    this.reviewLevelType += ' ' + this._review.currentReviewLevel.iteration;
                }

                Observable.forkJoin(
                    this._lenderService.getFinding(this._review.reviewId, this._review.currentReviewLevel.reviewLevelId, this._findingId),
                    this._dictionaryService.getFindingRatingCodes()
                ).subscribe(([finding, codes]) => {
                    this.finding = finding;
                    this._allRatingCodes = codes;
                    this._refreshFinding();
                });
            });
        }));
    }

    _refreshFinding() {

        if (!this.finding || !this.finding.findingId || !this._allRatingCodes) {
            return;
        }

        this.requireLenderResponse = FindingUtils.requireLenderResponse(this.finding);
        this.isReadOnly = Utils.isLenderReviewReadOnly(this._review) || !this._responseCoordinatorGuard.canActivate();

        /**
         *  NOTE: We call loadFindingsForAllCompletedLevels again here which is also being called in the base class but I think it's ok because we cache the request Observable.
         *  We can refactor to use the results from the base class.
         */
        this._lenderService.selectedFindingId = this.finding.findingId;
        this.status = this._allRatingCodes.find(r => r.ratingCode === this.finding.ratingCode).description.toLowerCase();
        Observable.forkJoin(
            this._dictionaryService.getDefectAreaSources(this._review.qaModelId, this.finding.defectAreaCode),
            this._dictionaryService.getDefectAreaCauses(this._review.qaModelId, this.finding.defectAreaCode),
            this._dictionaryService.getDefectAreaSeverities(this._review.qaModelId, this.finding.defectAreaCode),
            this._findingsService.loadFindingsForAllCompletedLevels(this._review, this.finding.defectAreaCode, true, true)
        ).subscribe(([sources, causes, severities, completedFindings]) => {
            this.findingSourceDescription = sources.find(s => s.defectSourceCode === this.finding.selectedSourceCode).description || '';
            this.findingCauseDescription = causes.find(c => c.defectCauseCode === this.finding.selectedCauseCode).description || '';
            this.formActive = true;
            this._defectAreaSeverities = severities;
            let severity = this._defectAreaSeverities.filter(t => t.defectSeverityTierCode === this.finding.selectedTierCode);
            this.selectedTierDescription = (severity && severity.length) ? severity[0].description : this.finding.selectedTierCode;

            // Build the completed level card data.
            this._buildDataForCompletedLevels(completedFindings);
        });
        this.postURL = `${compiledConfig.server}/api/v1/reviews/${this._review.reviewId}/level/${this._review.currentReviewLevel.reviewLevelId}/findings/${this.finding.findingId}/lenderResponseDocuments`;
    }

    ngOnDestroy() {
        this._resetSubscriptions();
    }

    onSubmit() {
        // Remove the temporary 'isNew' flag we use to track adding existing document to the current fidning.
        this.finding.lenderResponseDocuments = this.finding.lenderResponseDocuments.map(d => {
            let c = {fileName: d.fileName, documentId: d.documentId};
            if (d.documentType) {
                c.documentTyp = d.documentTyp;
            }

            return c;
        });
        // NOTE: Save response and Upload management operates independently in the UI. Save response just saves the text response at the moment. Upload or delete wil update the Finding automatically on the server side.
        this._lenderService.saveFinding(this.finding, this._review.reviewId, this._review.currentReviewLevel.reviewLevelId).subscribe(() => {

            // Refresh all the findings so the finding sidebar displays updated value/status correctly.
            this._findingsService.loadAllFindings(this._review.reviewId, this._review.currentReviewLevel.reviewLevelId, true, true,  this._review.qaModelId).subscribe(() => {
                this._goToNextUnrespondedFinding();
            });
        });
    }

    _resetSubscriptions() {
        this.subscriptions.forEach(subscription => {
            subscription.unsubscribe();
        });

        this.subscriptions = [];
    }

    removeDocument(document:Object) {
        if (document.isNew) {
            this.finding.lenderResponseDocuments = this.finding.lenderResponseDocuments.filter(d => d.documentId !== document.documentId);
        } else {
            this._removeFiles(document);
        }
    }

    _removeFiles(document) {
        this._client.resources.reviews.reviewId(this._review.reviewId).level.reviewLevelId(this._review.currentReviewLevel.reviewLevelId).findings.findingId(this.finding.findingId).lenderResponseDocuments.documentId(document.documentId).delete().first().subscribe(
            (data) => {
                let deletedDocument = (data && data.documentId) ? data : document;
                this.finding.lenderResponseDocuments = this.finding.lenderResponseDocuments.filter(d => deletedDocument.documentId !== d.documentId);
            }
        );
    }

    onSelectChange(value:String) {
        let existingDocument =  this.reviewDocuments.find(d => d.documentId === value);
        if (existingDocument && !this.finding.lenderResponseDocuments.find(d => d.documentId === existingDocument.documentId)) {
            this.finding.lenderResponseDocuments.push({fileName: existingDocument.fileName, documentId: existingDocument.documentId, isNew: true});
        }
    }

    _buildDataForCompletedLevels(filteredFindings) {
        let completedLevels = this._review.completedReviewLevels;

        // Remove the 'current review level' if it exists from review.completedReviewLevels
        // NOTE: Don't filter for a review that's completed (Template only shows completed collection in read-only view)
        if (!Utils.isReviewCompleted(this._review)) {
            completedLevels = Utils.filterCompletedLevels(this._review, false, true);
        }

        let data = [];
        completedLevels.forEach((level) => {

            // Finding the finding tied to this compelted level
            let finding = filteredFindings.filter((f) => f.selectedCauseCode === this.finding.selectedCauseCode && f.selectedSourceCode === this.finding.selectedSourceCode && f.reviewLevelId === level.reviewLevelId);
            if (finding && finding.length) {
                data.push( ReviewLevelFindingRenderer.buildRendererData(this._defectAreaSeverities, this._review, level, finding[0]) );
            }
        });

        this.completedReviewLevelsData = [...data];
    }

    gotoFindingsOverview() {
        this._router.navigate([`lender/activeReviews/review/${this._review.reviewId}/findingsOverview`]);
    }

    _goToNextUnrespondedFinding() {
        let findingGroups =  this._lenderService.defectAreaFindingGroups || [];

        // Search from left to right for an un responded finding.
        let nextFinding = null;
        let group = findingGroups.find(g => g.findings.find(f => {
            let isUnresponded = !FindingUtils.isFindingRespondedByLender(f);
            if (isUnresponded) {
                nextFinding = f;
            }
            return isUnresponded;
        }));

        if (group && nextFinding && nextFinding.findingId) {
            this._router.navigate([`/lender/activeReviews/review/${this._review.reviewId}/finding/${nextFinding.findingId}/reviewResponse`]);
        } else {
            this.gotoFindingsOverview();
        }
    }

    /**
     * File Upload Callbacks
     */

    onFileUploadAdd({event/*, file*/}) {
        this.fileUploadStatus = event.type;
    }

    onFileUploadDone({event, result}) {
        this.fileUploadStatus = event.type;
        if (result &&  result.documentId) {
            this.finding.lenderResponseDocuments.push({fileName: result.fileName, documentId: result.documentId});
        }
    }

    onFileUploadFail({event, file}) {
        this.fileUploadStatus = event.type;
        this.failedFile = file;

        if (event.type === 'invalid') {
            this._modalSvc.acknowledge(
                '<p>Please choose a PDF, JPG, or TIFF file type.</p>',
                'Invalid File Type!').subscribe();
        }
    }

    onGotoResponse($evt, name: string) {
        Utils.handleAnchorClick($evt, this._elementRef, name);
    }

    disableSaveResponse(finding) {
        return this.isReadOnly || ((!finding.lenderResponseDocuments || !finding.lenderResponseDocuments.length) && (!finding.lenderResponseComment || !finding.lenderResponseComment.length));
    }
}
