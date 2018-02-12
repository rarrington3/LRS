// This file was generated from the component scaffold
// Copyright 2017

import {Component} from '@angular/core';
import template from './EditAdhocFindingSourceCause.html';
import styles from './EditAdhocFindingSourceCause.less';
import EditFindingBase from './EditFindingBase';
import FindingUtils from '../FindingUtils';

@Component({
    selector: 'edit-adhoc-finding-source-cause',
    template: template,
    styles: [styles]
})



export default class EditAdhocFindingSourceCause extends EditFindingBase {

    existingSourceCause: Object = {selectedCauseCode: null, selectedSourceCode: null};
    _treeRoute: String;

    ngOnInit() {
        this._treeRoute = this.router.url.slice(0, this.router.url.indexOf('/sourcecause'));
        this.isAdhoc = true;
        super.ngOnInit();
    }

    ngOnDestroy() {

        super.ngOnDestroy();
    }

    /*
     * @description - Override method.
     * @param {object} param - parameters
     * @return {void}
     */
    onRouteChanged(params) {
        /**
         *  For an exisiting ad hoc finding, the source and cause codes are passed in the route.
         *  Selecred source and cause are used to find an existing finding.
         */

        this.existingSourceCause = {selectedCauseCode: params.selectedCauseCode, selectedSourceCode: params.selectedSourceCode};

        // TEST CODE
        //  this.existingSourceCause.selectedCauseCode = 'B';
        // this.existingSourceCause.selectedSourceCode = '1';

        super.onRouteChanged(params);
    }


    /*
     * @description - Override method.
     * @return {void}
     */
    updateQuestionsAndAnswers() {
        // Do nothing since an ad hoc finding is not tied to any question.
    }

    /*
     * @description - Override method.
     * @return {void}
     */
    refreshFinding() {

        // Find a Finding by source and cause codes.
        if (this.existingSourceCause.selectedCauseCode && this.findingsService.currentFindings) {

            // TEST CODE
            // this.finding = {};
            this.finding = FindingUtils.findFindingBySourceAndCause(this.existingSourceCause.selectedSourceCode, this.existingSourceCause.selectedCauseCode, this.defectAreaCode, this.findingsService.currentFindings);
            console.log('Edit Ad hoc Finding Source Cause View - Found existing ad hoc finding ', this.finding);
        }

        super.refreshFinding();
    }

    navigateToDetails(finding: Object) {
        let isInitialAdhoc = FindingUtils.isInitialAdhocFinding(finding, this.reviewService.currentReview);
        if (isInitialAdhoc) {
            this.router.navigate([`${this._treeRoute}/finding/${finding.findingId}/initialdetails`]);
        } else {
            this.router.navigate([`${this._treeRoute}/finding/${finding.findingId}/noninitialdetails`]);
        }
    }

    onNext(form) {
        this.updatingFinding = true;

        // Find an existing finding based on the source and cause selections or create a new finding.
        let newOrExistingFinding = FindingUtils.findFindingBySourceAndCause(form.value.defectSourceCode, form.value.defectCauseCode, this.defectAreaCode, this.findingsService.currentFindings);

        // Simply go to Finding Details without saving any progress if the form is not dirty and there's an existing finding.
        if (!form.dirty && newOrExistingFinding && newOrExistingFinding.findingId) {
            this.router.navigate([`${this._treeRoute}/finding/${newOrExistingFinding.findingId}/noninitialdetails`]);
            return;
        }

        if (!newOrExistingFinding) {
            newOrExistingFinding = FindingUtils.createNewFinding(this.globalService.currentReviewLevelId, this.globalService.currentDefectAreaCode);

            // Update the source and cause codes
            newOrExistingFinding.selectedSourceCode = form.value.defectSourceCode;
            newOrExistingFinding.selectedCauseCode = form.value.defectCauseCode;

            // Manually set isAdhoc to true since this form is for creating ad hoc finding.
            newOrExistingFinding.isAdhoc = true;
        }

        let saveProgress = (isNewFinding: Boolean = true) => {

            // If we find a previously created finding that matches, and they click confirm, that we simply take them to the details view, and don't update the finding
            if (!isNewFinding && newOrExistingFinding.findingId) {
                this.navigateToDetails(newOrExistingFinding);
            } else {

                // PUT or POST the  new Finding
                this.reviewService.saveOrDeleteFindings([newOrExistingFinding]).subscribe((findings) => {

                    // Grab the updated finding which should be available in the findings collection.
                    let finding = FindingUtils.findFindingBySourceAndCause(newOrExistingFinding.selectedSourceCode, newOrExistingFinding.selectedCauseCode, this.defectAreaCode, findings);
                    if (!finding || !finding.findingId) {
                        throw (new Error(`Add Ad Hoc Finding - Unable to retrieve the saved ad hoc finding with defectSourceCode: ${newOrExistingFinding.selectedSourceCode} and defectCauseCode: ${newOrExistingFinding.selectedCauseCode} from findings!`));
                    }

                    this.navigateToDetails(finding);
                });
            }
        };

        if (form.dirty && newOrExistingFinding && newOrExistingFinding.findingId) {

            this.modalSvc.askForConfirmation(
                '<p>A finding for this source & cause already exists.  Would you like to edit it?</p>',
                'Edit Finding').first().subscribe(
                () => saveProgress(false),
                () => this.updatingFinding = false);
        } else {
            saveProgress();
        }
    }

    onCancelFinding() {
        this.router.navigate([`/review/${this.reviewService.currentReview.reviewId}/wrapup`]);
    }

}
