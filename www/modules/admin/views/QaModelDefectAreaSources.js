// This file was generated from the view scaffold
// Copyright 2016

import {Component} from '@angular/core';
import {ActivatedRoute}  from '@angular/router';
import template from './QaModelDefectAreaSources.html';
import styles from './QaModelDefectAreaSources.less';
import Client from '../../api/lrs.api.v1.generated';
import QaModelDefectAreaBase from './QaModelDefectAreaBase';

@Component({
    selector: 'qa-model-defect-area-sources',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/guide/router.html
 */
export default class QaModelDefectAreaSources extends QaModelDefectAreaBase {
    /**
     * Default name for the class, passed by reference via RouteParams
     */
    name: string = 'QaModelDefectAreaSources';

    thresholdDict = {};

    constructor(route: ActivatedRoute, client: Client) {
        super(route, client);
    }

    ngOnInit() {
        super.init();

        this.subscriptions.push(this.route.params.subscribe(params => {
            this.name = params['name'];
        }));

        this.subscriptions.push(this.defectAreaLoaded.subscribe(() => {
            this.loadThresholdDict();
        }));
    }

    ngOnDestroy() {
        super.destroy();
    }

    loadThresholdDict() {
        this.thresholdDict = {};
        this.defectArea.thresholds.forEach(threshold => {
            let causeDict = this.thresholdDict[threshold.sourceCode];
            causeDict = causeDict || {};
            causeDict[threshold.causeCode] = threshold;
            this.thresholdDict[threshold.sourceCode] = causeDict;
        });
    }

    getThreshold(source, cause) {
        let threshold = {};
        let causeDict = this.thresholdDict[source.code];
        if (causeDict) {
            threshold = causeDict[cause.code] ? causeDict[cause.code] : {};
        }
        return threshold;
    }
}
