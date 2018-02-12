// This file was generated from the service scaffold
// Copyright 2016

import {Injectable} from '@angular/core';
import Client from '../../api/lrs.api.v1.generated';
/**
 * @example
 * let injector = Injector.resolveAndCreate([DictionarySvc]);
 * let dictionarySvc = new injector.get(DictionarySvc);
 * @example
 * class Component {
 *      constructor(dictionarySvc:DictionarySvc, dictionarySvc2:DictionarySvc) {
 *          //injected via angular, a singleton by default in same injector context
 *          console.log(dictionarySvc === dictionarySvc2);
 *      }
 * }
 */
@Injectable()
export default class DictionarySvc {

    constructor(client: Client) {
        this._client = client;
    }

    getFindingRatingCodes() {
        // TODO: results should be cached? VT 1/10/17
        return this._client.resources.dictionary.ratingCodes.get().first();
    }

    getRemediationTypes(qaModelId: string, qaTreeId: string) {
        return this._client.resources.reviews.dictionary.qaModels.qaModelId(qaModelId).defectAreas.qaTreeId(qaTreeId).remediationTypes.get().first();
    }

    getDefectAreaCauses(qaModelId: string, defectAreaCode: string = null) {
        let defectAreaCauses = this._client.resources.reviews.dictionary.qaModels.qaModelId(qaModelId).defectAreaCauses.get().share().first();

        if (defectAreaCode) {
            return defectAreaCauses.map((causes) => {
                causes = causes.filter(cause => {
                    return cause.defectAreaCode.toLowerCase() === defectAreaCode.toLowerCase();
                });

                if (!causes.length) {
                    throw new Error(`0 finding cause found for defect area: ${defectAreaCode}`);
                }

                return causes;
            });
        }

        return defectAreaCauses;
    }

    getDefectAreaSources(qaModelId: string, defectAreaCode: string = null) {
        let defectAreaSources = this._client.resources.reviews.dictionary.qaModels.qaModelId(qaModelId).defectAreaSources.get().share().first();

        if (defectAreaCode) {
            return defectAreaSources.map((sources) => {
                sources = sources.filter(source => source.defectAreaCode.toLowerCase() === defectAreaCode.toLowerCase());
                if (!sources.length) {
                    throw new Error(`0 finding source found for defect area: ${defectAreaCode}`);
                }
                return sources;
            });
        }

        return defectAreaSources;
    }

    getReassignmentReasons() {
        if (!this._reassignmentReasons) {

            this._reassignmentReasons = this._client.resources.dictionary.reassignmentReasons.get().share().first();
            return this._reassignmentReasons;
        }

        return this._reassignmentReasons;
    }

    getreviewCancelReasons() {
        if (!this._reviewCancelReasons) {
            this._reviewCancelReasons = this._client.resources.dictionary.reviewCancelReasons.get().share().first();
            return this._reviewCancelReasons;
        }

        return this._reviewCancelReasons;
    }

    getDefectAreaSeverities(qaModelId: string, defectAreaCode: string = null) {
        let defectAreaSeverities = this._client.resources.reviews.dictionary.qaModels.qaModelId(qaModelId).defectAreaSeverities.get().share().first();

        if (defectAreaCode) {
            return defectAreaSeverities.map((severities) => {
                severities = severities.filter(severity => severity.defectAreaCode.toLowerCase() === defectAreaCode.toLowerCase());
                if (!severities.length) {
                    throw new Error(`No defect area serverity found for defect area: ${defectAreaCode}`);
                }

                severities = severities.sort((a, b) => {
                    return parseInt(a.defectSeverityTierCode, 10) < parseInt(b.defectSeverityTierCode, 10) ? -1 : 1;
                });

                return severities;
            });
        }

        // return all the available severities
        return defectAreaSeverities;
    }

}
