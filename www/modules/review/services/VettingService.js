// This file was generated from the service scaffold
// Copyright 2016

import {Injectable} from '@angular/core';
import Client from '../../api/lrs.api.v1.generated';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import FindingUtils from '../FindingUtils';
import _ from 'lodash';

@Injectable()
export default class VettingService {
    _client: Client = null;
    _router: Router;
    _originalFindingsObservable: Observable;
    constructor(client: Client, router: Router) {
        this._client = client;
        this._router = router;
    }

    confirmVetting(reviewId: string, reviewLevelId:string) {
        this._client.resources.reviews.reviewId(reviewId).level.reviewLevelId(reviewLevelId).confirmVetting.post({Acknowledgement_Required: 'Y'}).first().subscribe(() => {
            this._router.navigate(['/workload/summary']);
        });

    }

    static calculateVettingChanges(_vetteeFindings: Array = [], _vettedFindings: Array = [], findingSources: Array = null, findingCauses: Array = null, isQC: Boolean = false) {
        let changedFindings = [];
        let unchangedFindings = [];

        // Vetted added.
        let totalAddedFindings = 0;

        // Vetter removed.
        let totalRemovedFindings = 0;

        // Vetter updated comment to lender
        let totalCommentChanges = 0;

        // Vetter updated Sevirity Tier
        let totalSeverityChanges = 0;

        let addSourceCauseObjects = (finding: Object) => {
            if (findingSources && findingCauses) {
                finding.selectedSource = findingSources.find((s) => s.defectSourceCode === finding.selectedSourceCode);
                finding.selectedCause = findingCauses.find((c) => c.defectCauseCode === finding.selectedCauseCode);
            }
            return finding;
        };

        // Clone the finding because we are injecting temporary fields for UI binding.
        let vetteeFindings = _vetteeFindings.map(vf => {
            let clonedFinding = _.clone(vf);
            clonedFinding = addSourceCauseObjects(clonedFinding);
            return clonedFinding;
        });

        let vettedFindings =  _vettedFindings.map(of => {

            // Add the source and cause objects for binding.
            let clonedFinding = _.clone(of);
            clonedFinding = addSourceCauseObjects(clonedFinding);

            return clonedFinding;
        });


        // Set the originalSelectedTierCode value required for UI binding.
        vetteeFindings.forEach((of) => {
            of.originalSelectedTierCode = of.selectedTierCode;
            let vettedFinding =  FindingUtils.findFindingBySourceAndCause(of.selectedSourceCode, of.selectedCauseCode, of.defectAreaCode, vettedFindings);
            if (vettedFinding) {
                vettedFinding.originalSelectedTierCode = of.selectedTierCode;
            }
        });

        // Find the added findings. If a vetted finding is not found in the original findings then it was added.
        let addedFindings = vettedFindings.filter((vf) => {
            let added = !FindingUtils.findFindingBySourceAndCause(vf.selectedSourceCode, vf.selectedCauseCode, vf.defectAreaCode, vetteeFindings);
            if (added) {
                vf.changed = true;
                vf.added = true;
                totalAddedFindings++;
                vf.descriptionChanged = true;
            }
            return added;
        });


        // Find the removed Findings. If an original finding is not found in the vetted findings then it was removed.
        let removedFidnings = vetteeFindings.filter((of) => {

            let removed = !FindingUtils.findFindingBySourceAndCause(of.selectedSourceCode, of.selectedCauseCode, of.defectAreaCode, vettedFindings);

            if (removed) {
                of.changed = true;
                of.removed = true;
                totalRemovedFindings++;
            }
            return removed;
        });


        // Find the description and severity changes.
        vetteeFindings.filter((of) => !of.changed).forEach((filteredF) => {
            let found = FindingUtils.findFindingBySourceAndCause(filteredF.selectedSourceCode, filteredF.selectedCauseCode, filteredF.defectAreaCode, vettedFindings);
            if (found) {
                found.originalSelectedTierCode = filteredF.selectedTierCode;
                found.descriptionChageCount = 0;

                if (!isQC && found.commentToLender !== filteredF.commentToLender) {
                    found.changed = true;
                    found.descriptionChanged = true;
                    totalCommentChanges++;
                }

                if (found.selectedTierCode !== filteredF.selectedTierCode) {
                    found.changed = true;
                    found.severityChanged = true;
                    totalSeverityChanges++;

                }
            }
        });

        let allFindings = _.uniq([...vettedFindings, ...vetteeFindings, ...addedFindings, ...removedFidnings], (f) => f.defectAreaCode + f.selectedSourceCode + f.selectedCauseCode);
        let hasChangedFindings = allFindings.find((f) => f.changed);

        if (!hasChangedFindings) {

            // vetted unchanged findings
            changedFindings = [];
            unchangedFindings = allFindings;
        } else {
            allFindings = _.partition(allFindings, (f) => f.changed);

            // Vetted finding with changes
            changedFindings = allFindings[0];

            // Unchanged vetted findings
            unchangedFindings = allFindings[1];
        }



        return {
            changedFindings: changedFindings,
            unchangedFindings: unchangedFindings,
            totalAddedFindings: totalAddedFindings,
            totalRemovedFindings: totalRemovedFindings,
            totalCommentChanges: totalCommentChanges,
            totalSeverityChanges: totalSeverityChanges,
            totalChanges: totalAddedFindings + totalRemovedFindings + totalCommentChanges + totalSeverityChanges
        };
    }

}
