// This file was generated from the service scaffold
// Copyright 2016

import { Injectable } from '@angular/core';
import Client from '../../api/lrs.api.v1.generated';
import ObservableProperty from '../../shared/decorators/observableProperty';
import { RATINGS } from '../../review/constants';
import Utils from '../../shared/Utils';
import {Observable} from 'rxjs';
import UserSvc from './UserSvc';

/**
 * @example
 * let injector = Injector.resolveAndCreate([BatchService]);
 * let batchService = new injector.get(BatchService);
 * @example
 * class Component {
 * 		constructor(batchService:BatchService, batchService2:BatchService) {
 *			//injected via angular, a singleton by default in same injector context
 *			console.log(batchService === batchService2);
 *		}
 * }
 */
@Injectable()
export default class BatchService {

    @ObservableProperty()
    batches = null;

    @ObservableProperty()
    activeBatch = null;

    @ObservableProperty()
    activeFilteredReviews = null;

    @ObservableProperty()
    batchReviewData = null;

    @ObservableProperty()
    batchedDefectAreas = null;

    @ObservableProperty()
    operationalDefectAreas = null;

    @ObservableProperty()
    batchOwner = null;

    @ObservableProperty()
    operationalReview = null;

    @ObservableProperty()
    lenderBatchCollection = null;

    @ObservableProperty()
    activeLenderBatch = null;

    @ObservableProperty()
    lenderBatchSubmit = false;

    _client: Client;

    // Current authenticated user's role(s).
    isHqAdmin: Boolean = false;
    isReviewLocationAdmin: Boolean = false;
    isReviewer: Boolean = false;
    locationId: String = null;

    @ObservableProperty()
    allLocations: Array = null;

    _userSvc: UserSvc;

    constructor(client: Client, userSvc: UserSvc) {
        this._client = client;
        this._userSvc = userSvc;
        this.operationalDefectAreas = [];
        this.operationalReview = [];
        this.defectAreas = [];
        this.operationalDefectAreas = [];
        this.activeReviews = [];
    }

    getBatches(activeBatchId: String = null, locationId: String = null, showCompletedBatches: Boolean = false) {
        // if the user is an admin, always provide a default location id to load batches.
        let getDefaultLocationId = (locationId: String = null, allLocations: Array) => {

            if (locationId) {
                return locationId;
            } else if (this.isHqAdmin || this.isReviewLocationAdmin) {

                let userLocation = allLocations.find(loc => loc.locationId === this._userSvc.locationId);
                return userLocation && userLocation.locationId || null;
            }
            return null;
        };

        let getBatchSvc = (locationId: String = null) => {
            if (showCompletedBatches) {
                return (locationId) ? this.getCompletedBatchedReviewsByLocation : this.getCompletedBatchedReviews;
            }

            return (locationId) ? this.getBatchedReviewsByLocation : this.getBatchedReviews;
        };

        if (!this.allLocations) {

            this.getAllLocations().subscribe(() => {
                locationId = getDefaultLocationId(locationId,  this.allLocations);
                getBatchSvc(locationId).call(this, {activeBatchId: activeBatchId, locationId: locationId});
            });

        } else {
            locationId = getDefaultLocationId(locationId,  this.allLocations);
            getBatchSvc(locationId).call(this, {activeBatchId: activeBatchId, locationId: locationId});
        }
    }

    _getBatchedReviewsHandler(batches: Array = null, activeBatchId: String = null) {
        this.batches = batches;
        if (batches && batches.length) {
            let _activeBatch = null;
            if (activeBatchId) {
                _activeBatch = batches.find(b => b.batchId === activeBatchId);
            }
            _activeBatch = _activeBatch || batches[0];
            this.activeFilteredReviews = _activeBatch.reviews;

            this.setActiveBatch(_activeBatch);
        } else {
            this.activeBatch = null;
            this.activeFilteredReviews = null;
        }
    }

    getBatchedReviewsByLocation(data: Object) {
        if (!data.locationId) {
            throw new Error('getBatchedReviewsByLocation - loation id is required.');
        }
        let fetch = this._client.resources.reviews.batches.location.locationId(data.locationId).get().first();
        fetch.subscribe((batches) => {
            this._getBatchedReviewsHandler(batches, data.activeBatchId);
        });
        return fetch;
    }

    /**
     * Retrieve Reviews from batches endpoint
     * @param {data} data default active batch
     * @returns {void}
     */
    getBatchedReviews(data: Object) {
        let fetch = this._client.resources.reviews.batches.get().first();
        fetch.subscribe((batches) => {
            this._getBatchedReviewsHandler(batches, data.activeBatchId);
        });
        return fetch;
    }

    getCompletedBatchedReviewsByLocation(data: Object) {
        if (!data.locationId) {
            throw new Error('getBatchedReviewsByLocation - loation id is required.');
        }
        let fetch = this._client.resources.reviews.my.completed.batches.location.locationId(data.locationId).get().first();
        fetch.subscribe((batches) => {
            this._getBatchedReviewsHandler(batches, data.activeBatchId);
        });
        return fetch;
    }

    getCompletedBatchedReviews(data: Object) {
        let fetch = this._client.resources.reviews.my.completed.batches.get().first();
        fetch.subscribe((batches) => {
            this._getBatchedReviewsHandler(batches, data.activeBatchId);
        });
        return fetch;
    }

    submitBatch(batchId) {
        return this._client.resources.reviews.batches.batchId(batchId).submit.post().first();
    }

    cancelBatch(batchId) {
        return this._client.resources.reviews.batches.batchId(batchId).cancel.put().first();
    }

    submitLenderBatch(batchId) {
        return this._client.resources.reviews.batches.batchId(batchId).submit.lender.post().first();
    }

    getBatchByBatchId(batchId: String) {
        if (!this.batches) {
            this._client.resources.reviews.batches.batchId(batchId).get().first().subscribe((batch) => {
                this.setActiveBatch(batch);
            });
        }

        else {
            let activeBatch = this.batches.find((batch) => {
                return batch.batchId === batchId;
            });
            this.setActiveBatch(activeBatch);
        }
    }

    refreshBatch(batchId: String, lender: Boolean = false) {
        if (lender) {
            this._client.resources.reviews.batches.batchId(batchId).lenderByBatchId.get().first().subscribe((batch) => {
                this.setActiveBatch(batch, lender);
            });
        }
        else {
            this._client.resources.reviews.batches.batchId(batchId).get().first().subscribe((batch) => {
                this.setActiveBatch(batch, lender);
            });
        }
    }

    _refreshOperationalReview(batch: Object) {
        if (batch.operationalReview && Object.keys(batch.operationalReview).length) {
            this.operationalReview = [];
            this.operationalDefectAreas = [];
            batch.operationalReview.qaTrees.forEach((area) => {
                this.operationalDefectAreas.push(area.defectAreaCode);
            });

            //Use a collection so we can use the defect Table component
            this.operationalReview.push(batch.operationalReview);

        }
    }

    /**
     * Load Findings associated with current Reviews, add collection of finding data to each review.
     * @param {Boolean} forLender Flag to indicate if this is for a lender (lenders have slightly different API endpoints)
     * @returns {void}
     */
    loadFindings(forLender: Boolean) {
        //Build a list of all defect area codes contained in this batch
        let reviews = this.activeReviews;

        let totalCompletedUnacceptableFindings = 0;
        let totalUnacceptableFindingsCount = 0;

        this.lenderBatchSubmit = false;

        //Wait to set active reviews until after object is built
        this.lazyReviews = [];
        reviews.forEach((review) => {
            let lenderAction = null;
            let completedUnacceptableFindings = 0;
            let unacceptableFindings = 0;

            review.findingsData = [];
            review.rating = RATINGS.CONFORMING.description.toLowerCase();

            this.batchedDefectAreas.forEach((area) => {
                //Add a collection of Findings data to the review
                let defectObj = {};
                defectObj.code = area;
                defectObj.count = -1;
                defectObj.rating = RATINGS.CONFORMING.code;
                if (review.qaTrees) {
                    review.qaTrees.find((defect) => {
                        if (defect.defectAreaCode === area) {
                            defectObj.count = 0;
                        }
                    });
                }
                review.findingsData.push(defectObj);
            });

            if (review.reviewId !== '---') {
                let fetch;
                if (forLender) {
                    let validReviewLevel = Utils.findLenderAuthorizedLevel(review);
                    if (validReviewLevel) {
                        fetch = this._client.resources.reviews.reviewId(review.reviewId).level.reviewLevelId(validReviewLevel.reviewLevelId).findings.lender.get().first();
                    } else {
                        console.log('Batch Service: No valid review level for lender: ' + review.lenderId + ', review: ' + review.caseNumber + ', review status: ' + review.status);
                    }
                } else {
                    fetch = this._client.resources.reviews.reviewId(review.reviewId).level.reviewLevelId(review.currentReviewLevel.reviewLevelId).findings.get().first();
                }

                //Build a collection of findings for the review;
                if (fetch) {
                    fetch.subscribe((findings) => {
                        unacceptableFindings = findings.filter(finding => finding.ratingCode === RATINGS.UNACCEPTABLE.code);
                        totalUnacceptableFindingsCount += unacceptableFindings.length;

                        // If we have at least one unacceptable finding, we are pending lender action (ensures harvey ball shows)
                        if (unacceptableFindings.length > 0) {
                            lenderAction = 'pending';
                        }

                        findings.forEach((finding) => {
                            let findingData = review.findingsData.find((area) => {
                                return area.code === finding.defectAreaCode;
                            });

                            //Add the lender actions object for Harvey icons
                            if (finding.ratingCode === RATINGS.UNACCEPTABLE.code && ((finding.lenderResponseComment && finding.lenderResponseComment.length) || (finding.lenderResponseDocuments && finding.lenderResponseDocuments.length))) {
                                lenderAction = 'initiated';
                                completedUnacceptableFindings++;
                                totalCompletedUnacceptableFindings++;
                                if (completedUnacceptableFindings === unacceptableFindings.length) {
                                    lenderAction = 'completed';
                                    this.lenderBatchSubmit = totalCompletedUnacceptableFindings === totalUnacceptableFindingsCount;
                                }
                            }

                            review.lenderAction = lenderAction;
                            //If there is a finding with this review and defectArea, set count and rating;
                            if (findingData) {
                                findingData.count++;
                                if (findingData.rating !== RATINGS.UNACCEPTABLE.code) {
                                    if (finding.ratingCode === RATINGS.DEFICIENT.code) {
                                        review.rating = RATINGS.DEFICIENT.description.toLowerCase();
                                        findingData.rating = RATINGS.DEFICIENT.code;
                                    }
                                    else if (finding.ratingCode === RATINGS.UNACCEPTABLE.code) {
                                        review.rating = RATINGS.UNACCEPTABLE.description.toLowerCase();
                                        findingData.rating = RATINGS.UNACCEPTABLE.code;
                                    }
                                }
                            }
                        });
                    });
                }
            }
            this.lazyReviews.push(review);
        });

        this.unfilteredReviews = this.lazyReviews;
        this.activeFilteredReviews = this.lazyReviews;
    }

    /**
     * Load findings for the operational review.
     * @param {Boolean} forLender Set to true if being used for lender (APIs differ)
     * @returns {void}
     */
    loadOperationalFindings(forLender: Boolean) {
        if (this.operationalReview.length) {
            let review = this.operationalReview[0];
            let fetch = null;
            review.rating = RATINGS.CONFORMING.description.toLowerCase();
            review.findingsData = [];

            review.qaTrees.forEach((area) => {
                let defectObj = {};
                defectObj.code = area.defectAreaCode;
                defectObj.count = 0;
                defectObj.rating = RATINGS.CONFORMING.code;
                review.findingsData.push(defectObj);
            });

            if (forLender) {
                let lenderReviewLevel = Utils.findLenderAuthorizedLevel(review);

                // No need to fetch any findings, since there are non that we are authorized to see yet
                if (!lenderReviewLevel) {
                    return;
                }

                fetch = this._client.resources.reviews.reviewId(review.reviewId).level.reviewLevelId(lenderReviewLevel.reviewLevelId).findings.lender.get().first();
            } else {
                fetch = this._client.resources.reviews.reviewId(review.reviewId).level.reviewLevelId(review.currentReviewLevel.reviewLevelId).findings.get().first();
            }

            //Build a collection of findings for the review;
            fetch.subscribe((findings) => {
                findings.forEach((finding) => {
                    review.findingsData.forEach((area) => {
                        //If there is a finding with this review and defectArea, set count and rating;

                        if (area.code === finding.defectAreaCode) {
                            area.count++;
                            if (area.rating !== RATINGS.UNACCEPTABLE.code) {
                                if (finding.ratingCode === RATINGS.DEFICIENT.code) {
                                    review.rating = RATINGS.DEFICIENT.description.toLowerCase();
                                    area.rating = RATINGS.DEFICIENT.code;
                                }
                                else if (finding.ratingCode === RATINGS.UNACCEPTABLE.code) {
                                    review.rating = RATINGS.UNACCEPTABLE.description.toLowerCase();
                                    area.rating = RATINGS.UNACCEPTABLE.code;
                                }
                            }
                        }
                    });
                });
            });
            this.operationalReview[0] = review;
        }
    }

    /**
     * Load reviews and findings for the current selected batch
     * @param {batch} batch - a batch object
     * @param {Boolean} forLender - Set active batch for lender
     * @returns {void}
     */
    setActiveBatch(batch, forLender: Boolean = false) {
        this.operationalReview = [];
        this.defectAreas = [];
        this.operationalDefectAreas = [];
        this.activeReviews = batch.reviews;

        if (forLender) {
            this.activeLenderBatch = batch;
        } else {
            this.activeBatch = batch;
        }

        let batchBinderRequests = [];

        batch.outstandingCaseActivity.forEach((caseActivity) => {
            let obj = {
                'reviewId': '---',
                'caseNumber': caseActivity.caseNumber,
                'selectionReason': '---',
                'rating': '---',
                'currentReviewLevel': {
                    'status': caseActivity.selectionStatus,
                    'ratingCode': '---',
                    'reviewerName': '---',
                    'type': '---'
                }
            };
            batchBinderRequests.push(obj);
        });

        this.activeReviews = batch.reviews.concat(batchBinderRequests);

        this._refreshOperationalReview(batch);

        if (batch.reviews && batch.reviews.length) {
            batch.reviews.forEach((review) => {
                review.qaTrees.forEach((defectArea) => {
                    //Build an array of all defect areas in this batch
                    if (this.defectAreas.indexOf(defectArea.defectAreaCode, 0) === -1) {
                        this.defectAreas.push(defectArea.defectAreaCode);
                    }
                });
            });
        }
        this.batchedDefectAreas = this.defectAreas;
        //Load findings from reviews in batch
        this.loadFindings(forLender);

        //Load findings from operational review
        if (batch.operationalReview && Object.keys(batch.operationalReview).length) {
            this.loadOperationalFindings(forLender);
        }

        if (!forLender) {
            //Load info about batch owner
            this.getBatchOwner(batch.batchOwner);
        }
    }

    getLenderBatches(showCompletedBatches: Boolean = false) {
        this.lenderBatchCollection = [];

        let fetch = (showCompletedBatches) ?  this._client.resources.reviews.my.completed.batches.lender.get().first() : this._client.resources.reviews.batches.lender.get().first();
        fetch.subscribe((batches) => {
            if (batches && batches.length) {
                this.lenderBatchCollection = batches;
                //If this is the first review in the list, set it as default
                this.setActiveBatch(batches[0], true);
            }
        });
    }

    getBatchOwner(batchOwner) {
        let fetch = this._client.resources.reviewers.reviewerId(batchOwner).get().first();
        fetch.subscribe((owner) => {
            this.batchOwner = owner;
        });
    }

    submitBatchDocuments() {
        return this._client.resources.reviews.batches.batchId(this.activeLenderBatch.batchId).submitOperationalDocuments.post().first();
    }

    removeFile(document) {
        return this._client.resources.reviews.batches.batchId(this.activeLenderBatch.batchId).operationalDocuments.documentId(document.documentId).delete().first();
    }

    getAllLocations() {
        return this._client.resources.locations.get().share().first().switchMap(locations => {
            this.allLocations = locations;
            return Observable.of(locations);
        });
    }

    resetData() {

        // These collections used by the workload and completed batches views.
        this.batches = null;
        this.activeBatch = null;
        this.activeFilteredReviews = null;

        this.lenderBatchCollection = null;
        this.activeLenderBatch = null;
    }

}
