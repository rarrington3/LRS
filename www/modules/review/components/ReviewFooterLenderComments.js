// This file was generated from the component scaffold
// Copyright 2016

import {Component} from '@angular/core';
import template from './ReviewFooterLenderComments.html';
import styles from './ReviewFooterLenderComments.less';
import ReviewFooter from './ReviewFooter';
import {FormGroup} from '@angular/forms';
import {Observable} from 'rxjs';
import _ from 'lodash';
import FindingUtils from '../FindingUtils';
import DatePipe from '../../shared/pipes/DatePipe';

@Component({
    selector: 'review-footer-lender-comments',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/api/core/Component-decorator.html
 * @example
 * <review-bottom-bar-lender-comments name="ReviewFooterLenderComments" (change)="onChange($event)"></review-bottom-bar-lender-comments>
 */
export default class ReviewFooterLenderComments extends ReviewFooter {

    findingsWithComment:Array = [];
    form:FormGroup;
    allSourceCauseCollection:Array = [];
    _allSourceCauseCollectionSource:Array = [];
    _previousDefectAreaFilter:String = null;
    showFindingFilter:Boolean = false;

    ngOnInit() {
        super.ngOnInit();
        this.subscriptions.push(this.findingsService.currentFindingsObservable.subscribe((data) => {
            if (data.currentValue) {
                this.filterAndSortComments();
            }

        }));

        this.subscriptions.push(this.globalStateService.defectAreasForCurrentReviewObservable.subscribe((data) => {
            if (data.currentValue) {
                this.filterAndSortComments();
                _.defer(() => {
                    this.form.controls.defectAreaFilter.setValue('all');
                });
            }
        }));

        this.form = this.formBuilder.group({
            defectAreaFilter: 'all',
            findingFilter: 'all',
            sortBy: 'defectAreaCode'
        });

        this.subscriptions.push(this.form.valueChanges.subscribe(() => this.filterAndSortComments()));

        this.filterAndSortComments();

        this.subscriptions.push(this.globalStateService.currentQaModelIdObservable.subscribe((data) => {
            if (data.currentValue) {
                Observable.forkJoin(
                        this.dictionaryService.getDefectAreaSources(this.globalStateService.currentQaModelId),
                        this.dictionaryService.getDefectAreaCauses(this.globalStateService.currentQaModelId)).first()
                        .subscribe(([sources, causes]) => {
                            this._allSourceCauseCollectionSource = [...sources, ...causes].map(code => {
                                if (code.defectCauseCode) {
                                    return {
                                        defectAreaCode: code.defectAreaCode,
                                        value: `${code.defectAreaCode}.${code.defectCauseCode}`,
                                        label: `Cause ${code.defectAreaCode}.${code.defectCauseCode}`
                                    };
                                }
                                return {
                                    defectAreaCode: code.defectAreaCode,
                                    value: `${code.defectAreaCode}.${code.defectSourceCode}`,
                                    label: `Source ${code.defectAreaCode}.${code.defectSourceCode}`
                                };


                            });
                        });
            }
        }));
    }

    ngOnDestroy() {
        super.ngOnDestroy();
    }

    filterAndSortComments() {
        let findings = this.findingsService.currentFindings;
        let defectAreas = this.globalStateService.defectAreasForCurrentReview;

        let getSortKey = (selectedSortBy, finding) =>  {
            if (selectedSortBy === 'defectAreaCauseSource') {
                return  finding.selectedCauseCode + finding.selectedSourceCode + finding.selectedTierCode;
            }

            return finding[selectedSortBy];
        };

        if (findings && defectAreas && defectAreas.length) {
            let {defectAreaFilter, findingFilter, sortBy} = this.form.value;

            if (defectAreaFilter !== this._previousDefectAreaFilter) {
                this.form.controls.findingFilter.reset('all', {onlySelf:true});
            }

            if (defectAreaFilter !== 'all') {
                this.allSourceCauseCollection = this._allSourceCauseCollectionSource.filter(code => code.defectAreaCode === defectAreaFilter);
                this.showFindingFilter = true;
            } else {
                this.showFindingFilter = false;
            }

            this._previousDefectAreaFilter = defectAreaFilter;
            this.findingsWithComment = findings.filter(f => {

                if (!f.commentToLender || !f.commentToLender.length){
                    return false;
                }

                let filterCause = `${f.defectAreaCode}.${f.selectedCauseCode}`;
                let filterSource = `${f.defectAreaCode}.${f.selectedSourceCode}`;

                return (defectAreaFilter === 'all' || f.defectAreaCode === defectAreaFilter) &&  (findingFilter === 'all' || (filterCause  === findingFilter || filterSource === findingFilter));
            }).sort((a, b) => {
                a = getSortKey(sortBy, a);
                b = getSortKey(sortBy, b);
                return a < b ? -1 : 1;
            });

        }
    }

    navigateToFinding(finding:Object) {
        FindingUtils.navigateToFindingDetail(this.router, this.globalStateService, this.reviewService, finding);
    }

    formatDate(date) {
        let datePipe = new DatePipe();
        return datePipe.transform(date);
    }

}
