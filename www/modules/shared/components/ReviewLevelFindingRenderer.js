// This file was generated from the component scaffold
// Copyright 2016

import {Component, Input} from '@angular/core';
import template from './ReviewLevelFindingRenderer.html';
import styles from './ReviewLevelFindingRenderer.less';
let compiledConfig = require('config');

@Component({
    selector: 'review-level-finding-renderer',
    template: template,
    styles: [styles]
})
/**
 * @description diplays a review level's info and the related finding(filtered by defect area)  upload response documents.
 * @example
 * <review-level-renderer [data]='myData'></review-level-renderer>
 */
export default class ReviewLevelFindingRenderer {

    @Input() data: Object = null;

    constructor() {}
    getResponseDocumentURL(document) {
        return  `${compiledConfig.server}/api/v1/reviews/${this.data.review.reviewId}/level/${this.data.level.reviewLevelId}/findings/${this.data.finding.findingId}/lenderResponseDocuments/${document.documentId}`;
    }

    static buildRendererData(defectAreaSeverities: Array, review:Object,  level: Object, finding: Object) {
        let data = null;
        if (review && level && finding && defectAreaSeverities) {
            let isPostInitial = (level.type.toLowerCase() !== 'initial');
            let severity = defectAreaSeverities.filter(t => t.defectSeverityTierCode ===  finding.selectedTierCode);
            let selectedTierDescription = (severity && severity.length) ? severity[0].description : finding.selectedTierCode;
            let levelType = level.type;

            if (isPostInitial) {
                levelType += ' ' + level.iteration;
            }

            data = {selectedTierDescription: selectedTierDescription, levelType: levelType, finding: finding, review: review, level: level, isPostInitial: isPostInitial, warning: level._warning};
        } else {
            throw new Error('The review level, defectAreaSeverities, and finding objects are required.');
        }

        return data;
    }
}
