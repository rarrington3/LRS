// This file was generated from the view scaffold
// Copyright 2016

import {Component} from '@angular/core';
import {ActivatedRoute}  from '@angular/router';
import template from './QaModelDefectAreaSourcesEdit.html';
import styles from './QaModelDefectAreaSourcesEdit.less';
import Client from '../../api/lrs.api.v1.generated';
import {SaveBarSvc, ModalSvc} from '../../app/services';
import QaModelDefectAreaBase from './QaModelDefectAreaBase';
import ArrayUtils from '../../shared/utils/ArrayUtils';

@Component({
    selector: 'qa-model-defect-area-sources-edit',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/guide/router.html
 */
export default class QaModelDefectAreaSourcesEdit extends QaModelDefectAreaBase {
    /**
     * Default name for the class, passed by reference via RouteParams
     */
    name: string = 'QaModelDefectAreaSourcesEdit';

    _newThreshold = {
        sourceCode: '',
        causeCode: '',
        severityCode: ''
    };

    thresholdDict = {};

    constructor(route: ActivatedRoute, client: Client, saveBarSvc: SaveBarSvc, modalSvc: ModalSvc) {
        super(route, client, saveBarSvc, modalSvc);
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

    addSource() {
        super.add(this.defectArea.sources, this.newOrderedItem);
    }

    updateSource(source, newCode) {
        // re link with new edited allowable source
        this.defectArea.questions.forEach(question => {
            question.allowableSourceCodes.forEach((code, i) => {
                if (code === source.code) {
                    question.allowableSourceCodes.splice(i, 1, newCode); // replace with new code
                }
            });
        });

        // re link with new edited thresholds
        this.defectArea.thresholds.forEach((threshold) => {
            if (threshold.sourceCode === source.code) {
                threshold.sourceCode = newCode;
            }
        });

        // sync dictionary
        this.loadThresholdDict();

        // set the new code
        source.code = newCode;

        super.askForSave();
    }

    removeSource(source) {
        let message = 'Are you sure you want to remove the item?';
        this.modalSvc.askForConfirmation(message).first()
            .subscribe(
                () => {
                    // removing from questions allowable source codes
                    this.defectArea.questions.forEach(question => {
                        question.allowableSourceCodes =
                            question.allowableSourceCodes.filter(code => {
                                return code !== source.code;
                            });
                    });

                    // remove from thresholds
                    this.defectArea.thresholds = this.defectArea.thresholds.filter(threshold => {
                        return threshold.sourceCode !== source.code;
                    });

                    // sync dictionary
                    this.loadThresholdDict();

                    // removing from sources
                    ArrayUtils.remove(this.defectArea.sources, source);

                    // save
                    super.askForSave();
                }, () => {
                }
            );
    }

    addCause() {
        super.add(this.defectArea.causes, this.newOrderedItem);
    }

    updateCause(cause, newCode) {
        // re link with new edited allowable cause
        this.defectArea.questions.forEach(question => {
            question.allowableCauseCodes.forEach((code, i) => {
                if (code === cause.code) {
                    question.allowableCauseCodes.splice(i, 1, newCode); // replace with new code
                }
            });
        });

        // re link with new edited thresholds
        this.defectArea.thresholds.forEach((threshold) => {
            if (threshold.causeCode === cause.code) {
                threshold.causeCode = newCode;
            }
        });

        // sync dictionary
        this.loadThresholdDict();

        // set the new code
        cause.code = newCode;

        super.askForSave();
    }

    removeCause(cause) {
        let message = 'Are you sure you want to remove the item?';
        this.modalSvc.askForConfirmation(message).first()
            .subscribe(
                () => {
                    // removing from questions allowable cause codes
                    this.defectArea.questions.forEach(question => {
                        question.allowableCauseCodes =
                            question.allowableCauseCodes.filter(code => {
                                return code !== cause.code;
                            });
                    });

                    // remove from thresholds
                    this.defectArea.thresholds = this.defectArea.thresholds.filter(threshold => {
                        return threshold.causeCode !== cause.code;
                    });

                    // sync dictionary
                    this.loadThresholdDict();

                    // removing from causes
                    ArrayUtils.remove(this.defectArea.causes, cause);

                    // save
                    this.askForSave();
                }, () => {
                }
            );
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

    saveThreshold(source, cause, severityCode) {
        let causeDict = this.thresholdDict[source.code];
        causeDict = causeDict || {};
        let threshold = causeDict[cause.code];
        threshold = threshold ||
            Object.assign(
                {}, this._newThreshold,
                {
                    sourceCode: source.code,
                    causeCode: cause.code
                });
        threshold.severityCode = severityCode;
        causeDict[cause.code] = threshold;
        this.thresholdDict[threshold.sourceCode] = causeDict;

        if (!threshold.id) { // is new
            this.defectArea.thresholds.push(threshold);
        }

        // save
        this.askForSave();
    }
}
