// This file was generated from the service scaffold
// Copyright 2016

import { Injectable } from '@angular/core';
import GlobalStateSvc from './GlobalStateSvc';
import ObservableProperty from '../../shared/decorators/observableProperty';
import Client from '../../api/lrs.api.v1.generated';
import { Observable } from 'rxjs';
import DictionarySvc from '../../app/services/DictionarySvc';
import FindingUtils from '../FindingUtils';
import Utils from '../../shared/Utils';
import { RATINGS } from '../constants';
import _ from 'lodash';

/**
 * @example
 * let injector = Injector.resolveAndCreate([FindingsService]);
 * let findingsService = new injector.get(FindingsService);
 * @example
 * class Component {
 *      constructor(findingsService:FindingsService, findingsService2:FindingsService) {
 *          //injected via angular, a singleton by default in same injector context
 *          console.log(findingsService === findingsService2);
 *      }
 * }
 */
@Injectable()
export default class FindingsService {

    @ObservableProperty()
    currentFindings: Array = null;

    @ObservableProperty()
    findingsForCurrentDefectArea: Array = null;

    @ObservableProperty()
    findingsTotals: Array = null;

    // This property is used to prevent the findings endpoint from getting called repeatedly when 'loadAllFindings' is triggered multiple times from UI.
    allFindingsRequestObservable: Observable = null;

    _client: Client = null;
    _globalStateSvc: GlobalStateSvc = null;
    _ratingCodesDictionary: Object = null;
    _defectAreaSources: Object = [];
    _defectAreaCauses: Object = [];
    defectAreaSeverities: Object = [];
    _allFindingsForCompletedLevels: Observable = null;

    constructor(client: Client, globalStateSvc: GlobalStateSvc, dictionarySvc: DictionarySvc) {
        this._client = client;
        this._globalStateSvc = globalStateSvc;
        this._dictionarySvc = dictionarySvc;

        this._globalStateSvc.currentDefectAreaCodeObservable.subscribe(() => {
            this._filterFindingsByDefectArea();
        });
    }

    _loadRequiredDataAndModifyFindings(currentQaModelId: String, findings: [] ) {
        if (!currentQaModelId || !findings) {
            throw (new Error('loadFindingRequiredData - currentQaModelId and findings are required.'));
        }
        return Observable.create(observer => {
            Observable.forkJoin(
                this._dictionarySvc.getDefectAreaSources(currentQaModelId),
                this._dictionarySvc.getDefectAreaCauses(currentQaModelId),
                this._dictionarySvc.getDefectAreaSeverities(currentQaModelId),
                this._client.resources.dictionary.ratingCodes.get()
            ).subscribe(([sources, causes, severities, ratingCodes]) => {
                this._defectAreaSources = sources;
                this._defectAreaCauses = causes;
                this.defectAreaSeverities = severities;
                this._ratingCodesDictionary = ratingCodes;

                // All the required data are loaded, now we can process the Finding objects.

                // Add rating, source, and cause info to Findings to be used in the template(s)
                if (findings.length) {

                    findings.forEach((finding) => {
                        this._ratingCodesDictionary.find((rating) => {
                            if (finding.ratingCode === rating.ratingCode) {
                                finding.rating = rating;
                            }
                        });
                        this._defectAreaSources.find((source) => {
                            if (finding.selectedSourceCode === source.defectSourceCode && finding.defectAreaCode === source.defectAreaCode) {
                                finding.selectedSource = source;
                            }
                        });
                        this._defectAreaCauses.find((cause) => {
                            if (finding.selectedCauseCode === cause.defectCauseCode && finding.defectAreaCode === cause.defectAreaCode) {
                                finding.selectedCause = cause;
                            }
                        });
                    });
                }

                // Save all findings for all to use
                // Note: this also emits this.currentFindingsObservable stream.
                this.currentFindings = findings;

                // In case this is a refresh, re-filter by defect area
                this._filterFindingsByDefectArea();

                // Now create the collection for review level wrap-up
                this.findingsTotals = FindingUtils.buildFindingsTotals(this.currentFindings, this._globalStateSvc.defectAreasForCurrentReview, this.defectAreaSeverities, RATINGS);
                observer.next(this.currentFindings);
                observer.complete();
            });
        });
    }

    loadAllFindings(reviewId: String, reviewLevelId: String, refresh: Boolean = false, forLender: Boolean = false, currentQaModelId: String = this._globalStateSvc.currentQaModelId) {
        if (!this.allFindingsRequestObservable || refresh) {
            this.currentFindings = null;
            this.allFindingsRequestObservable = forLender ?
                this._client.resources.reviews.reviewId(reviewId).level.reviewLevelId(reviewLevelId).findings.lender.get().share().first() :
                this._client.resources.reviews.reviewId(reviewId).level.reviewLevelId(reviewLevelId).findings.get().share().first();
        } else {

            // Set the currentFindings again to trigger the this.currentFindingsObservable to stream the cached findings again.
            let currentFindings = this.currentFindings;
            this.currentFindings = currentFindings;
            return this.allFindingsRequestObservable;
        }

        return this.allFindingsRequestObservable.switchMap((findings) => {
            return  this._loadRequiredDataAndModifyFindings(currentQaModelId, findings);
        });
    }

    _filterFindingsByDefectArea() {
        let findings = [];
        let defectAreaCode = this._globalStateSvc.currentDefectAreaCode;
        this.findingsForCurrentDefectArea = null;
        if (this.currentFindings && defectAreaCode) {
            this.currentFindings.forEach((finding) => {
                if (finding.defectAreaCode === defectAreaCode) {
                    findings.push(finding);
                }
            });
            findings.sort(function(a, b) {
                return (a.rating.rankOrder) - (b.rating.rankOrder);
            });
            this.findingsForCurrentDefectArea = findings;
        }
    }

    loadFindingsForAllCompletedLevels(review: Object, defectAreaCode: String = null, refresh: Boolean = false, isLender: Boolean = false) {

        let _createRequestsObservable = () => {
            let completedLevels = review.completedReviewLevels;

            // For Lenders, remove the 'current review level' if it exists from review.completedReviewLevels (and review isn't completed)
            if (isLender && !Utils.isReviewCompleted(review)) {
                completedLevels = completedLevels.filter((l => l.reviewLevelId !== review.currentReviewLevel.reviewLevelId));
            }

            let requests = [];
            if (completedLevels && completedLevels.length) {
                requests = completedLevels.map(l => {
                    if (isLender) {
                        return  this._client.resources.reviews.reviewId(review.reviewId).level.reviewLevelId(l.reviewLevelId).findings.lender.get().share().first();
                    }

                    return this._client.resources.reviews.reviewId(review.reviewId).level.reviewLevelId(l.reviewLevelId).findings.get().share().first();
                });
            }

            return Observable.create(observer => {
                if (requests.length) {
                    Observable.forkJoin(requests).first().subscribe((findings) => {
                        let mergedFindings = _.flatten(findings);
                        observer.next(mergedFindings);
                        observer.complete();
                    });
                } else {
                    observer.next([]);
                    observer.complete();
                }
            });
        };

        if (this._allFindingsForCompletedLevels && this._allFindingsForCompletedLevels.reviewId && this._allFindingsForCompletedLevels.reviewId !== review.reviewId) {
            refresh = true;
        }

        if (!this._allFindingsForCompletedLevels || refresh) {
            this._allFindingsForCompletedLevels = _createRequestsObservable();
            this._allFindingsForCompletedLevels.reviewId = review.reviewId;
        }

        // Optionally filter the findings by defect area using the 'map' operator
        if (defectAreaCode) {
            return  this._allFindingsForCompletedLevels.map((findings) => {
                findings = findings.filter(f => f.defectAreaCode.toLowerCase() === defectAreaCode.toLowerCase());
                if (!findings.length) {
                    console.log(`0 completed review level findings found for defect area ${defectAreaCode} in review ${review.reviewI}.`);
                }

                return findings;
            });
        }

        return  this._allFindingsForCompletedLevels;
    }

    saveFinding(finding:Object, reviewId:String, reviewLevelId:String) {

        if (!reviewId || !reviewLevelId || !finding || !finding.findingId) {
            throw new Error('saveFinding error - reviewId, reviewLevelId or findingId is undefined.');
        }
        return this._client.resources.reviews.reviewId(reviewId).level.reviewLevelId(reviewLevelId).findings.findingId(finding.findingId).put(finding).first();
    }

}
