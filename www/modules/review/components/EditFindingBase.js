// This file was generated from the component scaffold
// Copyright 2016

import {Component} from '@angular/core';
import {ActivatedRoute, Router}  from '@angular/router';
import styles from './EditFindingBase.less';
import ReviewService from '../services/ReviewService';
import FindingsService from '../services/FindingsService';
import GlobalStateSvc from '../services/GlobalStateSvc';
import {Observable} from 'rxjs';
import ModalSvc from '../../app/services/ModalSvc';
import DictionarySvc from '../../app/services/DictionarySvc';
import Utils from '../../shared/Utils';
import FindingUtils from '../FindingUtils';
import QCService from '../services/QCService';

@Component({
    selector: 'edit-finding-base',
    template: '',
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/api/core/Component-decorator.html
 * @example
 * <edit-finding-base name="EditFindingBase" (change)="onChange($event)"></edit-finding-base>
 */
export default class EditFindingBase {

    /**
     * Any new subscription will be added into this collection
     * @type {Array}
     */
    subscriptions: Array = [];

    // Used in finding detail view.
    questions: Array = [];

    // Used in finding source/cause view
    question: Object = null;

    defectAreaCode: string = null;
    defectAreaCauses: Array = [];
    defectAreaSources: Array = [];
    defectAreaCausesSource: Array = null;
    defectAreaSourcesSource: Array = null;
    defectAreaSeverities: Array = null;
    route: ActivatedRoute;
    finding: Object = {};
    findingCause: Object = {};
    findingSource: Object = {};
    modalSvc: ModalSvc;
    isReviewReadOnly:Boolean = true;
    isAdhoc: Boolean = false;
    qcService: QCService = null;
    originalfinding:Boolean = false;

    constructor(route: ActivatedRoute, router: Router, reviewService: ReviewService, findingsService: FindingsService, globalStateService: GlobalStateSvc, modalSvc: ModalSvc, dictionarySvc: DictionarySvc, qcService: QCService) {
        this.route = route;
        this.router = router;
        this.reviewService = reviewService;
        this.dictionaryService = dictionarySvc;
        this.findingsService = findingsService;
        this.globalService = globalStateService;
        this.modalSvc = modalSvc;
        this.qcService = qcService;
    }

    ngOnInit() {
        this.subscriptions.push(this.route.params.subscribe(params => this.onRouteChanged(params)));
    }

    ngOnDestroy() {
        this._resetSubscriptions();
    }

    _resetSubscriptions() {
        this.subscriptions.forEach(subscription => {
            subscription.unsubscribe();
        });
        this.subscriptions = [];
    }

    onRouteChanged(params) {
        this.originalfinding = (params.originalfinding === 'true');
        this._resetSubscriptions();
        this.defectAreaCode = params.area;

        // Re-set if defect area code changes
        if (this.globalService.currentDefectAreaCode !== this.defectAreaCode) {
            this.defectAreaCausesSource = null;
            this.defectAreaSourcesSource = null;
        }

        //Set the defect area code to load all the questions and answers for the selected Defect Area.
        this.globalService.currentDefectAreaCode = this.defectAreaCode;

        // Wait for the qaModelId because the dictionary service requests require the qaModelId. This subscription is required for deep linking/browser refresh.
        this.subscriptions.push( this.globalService.currentQaModelIdObservable.subscribe((data) => {
            if (data.currentValue) {
                this._loadDictionaryData();
            }
        }));

        // Trigger _loadDictionaryData if the user navigates from another view via the Router and the qaModelId might have been loaded.
        this._loadDictionaryData();

        this.subscriptions.push(this.reviewService.questionsForCurrentAreaObservable.subscribe(() => this.updateQuestionsAndAnswers()));
        this.subscriptions.push(this.reviewService.answersForCurrentAreaObservable.subscribe(() => this.updateQuestionsAndAnswers()));
        this.subscriptions.push(this.findingsService.currentFindingsObservable.subscribe((data) => {
            if (data.currentValue) {
                this.refreshFinding();
            }
        }));

        this.subscriptions.push(this.reviewService.currentReviewObservable.subscribe(() => {
            this.updateReadonly();
        }));

        if (this.findingsService.currentFindings) {
            this.refreshFinding();
        }

        this.updateReadonly();
    }

    _loadDictionaryData() {

        if (this.reviewService.currentReview && this.globalService.currentQaModelId && !this.defectAreaCausesSource) {
            this.subscriptions.push(Observable.forkJoin(
                this.dictionaryService.getDefectAreaSources(this.globalService.currentQaModelId, this.defectAreaCode),
                this.dictionaryService.getDefectAreaCauses(this.globalService.currentQaModelId, this.defectAreaCode),
                this.dictionaryService.getDefectAreaSeverities(this.globalService.currentQaModelId, this.defectAreaCode)
            ).first().subscribe(([sources, causes, severities]) => {
                this.defectAreaCausesSource = causes;
                this.defectAreaSourcesSource = sources;
                this.defectAreaSeverities = severities;
                this.refreshFinding();
                this.updateQuestionsAndAnswers();
            }));
        }
    }

    refreshFinding() {
        if (this.reviewService.currentReview && this.finding.selectedSourceCode && this.defectAreaCausesSource && this.defectAreaSourcesSource && this.defectAreaSeverities) {
            this.findingSource = this.defectAreaSourcesSource.find(s => s.defectSourceCode === this.finding.selectedSourceCode);
            this.findingCause = this.defectAreaCausesSource.find(c => c.defectCauseCode === this.finding.selectedCauseCode);

            if (!this.findingSource || !this.findingCause) {
                console.log(`Associated Source or Cause object is not found for Finding ${this.finding.findingId}`);
            }

            this.filterSourcesAndCausesByQuestion(this.question);
        }
    }

    updateReadonly() {
        if (this.reviewService.currentReview) {
            if (this.originalfinding) {
                this.isReviewReadOnly = true;
            }
            else if (this.isAdhoc) {
                this.isReviewReadOnly = !FindingUtils.allowPostInitialFinding(this.reviewService.currentReview);
            } else {
                this.isReviewReadOnly = Utils.isReviewReadOnly(this.reviewService.currentReview);

            }
        }
    }

    updateQuestionsAndAnswers() {
        // This method is to be subclassed.
    }

    filterSourcesAndCausesByQuestion(question: Object) {

        // Filter sources and causes
        if (question && this.defectAreaSourcesSource && this.defectAreaCausesSource) {
            let allowableSourceCodes = question.allowableSourceCodes || [];
            let allowableCauseCodes = question.allowableCauseCodes || [];
            this.defectAreaSources = allowableSourceCodes.map(s => {
                let foundSource = this.defectAreaSourcesSource.find(ss => ss.defectSourceCode === s);
                if (!foundSource) {
                    throw new Error(`Unable to find the dictionary object for the question allowable source code: ${s}`);
                }
                return foundSource;

            }).sort((a, b) => { return a.defectCauseCode > b.defectCauseCode ? 1 : -1; });


            this.defectAreaCauses = allowableCauseCodes.map(c => {
                let foundCause =  this.defectAreaCausesSource.find(cc => cc.defectCauseCode === c);
                if (!foundCause) {
                    throw new Error(`Unable to find the dictionary object for the question allowable cause code: ${c}`);
                }
                return foundCause;

            }).sort((a, b) => { return a.defectCauseCode > b.defectCauseCode ? 1 : -1; });
        }
    }

    onRevisitQuestion() {
        this.reviewService.hideWrapUp();
        let treeRoute = this.router.url.slice(0, this.router.url.indexOf('/tree'));
        if (this.finding && this.finding.findingId && this.finding.isAdhoc === true) {
            this.router.navigate([`${treeRoute}/wrapup`]);
        } else {
            this.router.navigate([`${treeRoute}/tree/${this.defectAreaCode}`]);
        }

    }
}
