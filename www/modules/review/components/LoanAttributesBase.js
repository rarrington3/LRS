// This file was generated from the component scaffold
// Copyright 2016

import {Component} from '@angular/core';
import _ from 'lodash';
import {FormBuilder,
    FormGroup,
    FormControl,
    Validators} from '@angular/forms';

import ReviewService from '../services/ReviewService';
import GlobalStateSvc from '../services/GlobalStateSvc';
import Utils from '../../shared/Utils';
import moment from 'moment';

@Component({
    selector: 'loan-attributes-base',
    template: '',
    styles: []
})
/**
 * @description This component is to be extended. It has no associated template since it just contains sharable code for
 * handling the FormGroup/FormControl biding, form validations, and form submit for Fields displayed in Loan Attributes and Defect Area Attributes(QuestionAndAnswerSideBar.js).
 * @see https://angular.io/docs/ts/latest/api/core/Component-decorator.html
 * @example
 * <loan-attributes-base name="LoanAttributesBase" (change)="onChange($event)"></loan-attributes-base>
 */
export default class LoanAttributesBase {

    _reviewFields: Array = [];
    _sourceReviewFields: Array = [];
    _subscriptions: Array = [];
    form: FormGroup;
    isReadOnly:Boolean = true;

    constructor(formBuilder: FormBuilder, reviewService: ReviewService, globalStateService: GlobalStateSvc) {
        this._formBuilder = formBuilder;
        this._reviewService = reviewService;
        this.globalStateService = globalStateService;
    }

    ngOnInit() {
        this.form = this._formBuilder.group({});
    }

    ngOnDestroy() {
        this._subscriptions.forEach((subscription) => {
            subscription.unsubscribe();
        });
        this.globalStateService.loanAttributesEdited = false;
    }

    set reviewFields(values: Array) {

        this._reviewFields = _.isArray(values) ? values : [];

        // Clone a copy so we can reset the defect area summary form.
        this._sourceReviewFields = _.cloneDeep(this._reviewFields);
        this._createSummaryFormGroup();

    }

    get reviewFields() {
        return this._reviewFields;
    }

    onSubmit() {
        // Override
    }

    /**
     * @description - Create or rebuild a custom FormGroup component that we bind to the form in the template.
     * @return {void}
     * @private
     */
    _createSummaryFormGroup() {
        this.isReadOnly = Utils.isQuestionTreeReadOnly(this._reviewService.currentReview);
        let group = {};
        this.reviewFields.forEach((field) => {
            let validator,
                value = field.value;

            if (field.type && field.type.toLowerCase() === 'multiselect') {

                // The FormControl's select multiple value accessor requires the value to be an array.
                let savedValues = field.value || '';
                value = _.isArray(savedValues) ? savedValues : savedValues.split(',');
            } else {
                validator = this._getValidatorByType(field.type);
            }

            group[field.fieldId] = new FormControl(value || '', validator);

            // Disable all input, radio, and select controls in the QA sidebar and loan attributes if the review is not initial.
            if (this.isReadOnly) {
                group[field.fieldId].disable();
            }

        });

        this.form = this._formBuilder.group(group);

        this._subscriptions.push(this.form.valueChanges.subscribe(() => {
            this.globalStateService.loanAttributesEdited = true;

        }));
    }

    _getValidatorByType(/*type:string*/) {
        return Validators.nullValidator;
    }

    getAffectedDefectAreas(qaTrees: Array = []) {

        // Find questions have conditions tied to the modified fields. Group found questions by defect area code.
        let affectedDefectAreas = [];
        for (let tree of qaTrees) {
            let questions = tree.questions || [];
            let defectArea = this.globalStateService.defectAreasForCurrentReview.find((defectArea) => defectArea.defectAreaCode === tree.defectAreaCode);

            let affectedQuestions = questions.filter((q) => {
                if (!q.conditionsToDisplay) {
                    return false;
                }
                return q.conditionsToDisplay.some((condition) => condition.fieldName && this.form.controls[condition.fieldName] && this.form.controls[condition.fieldName].dirty);

            });

            if (defectArea && affectedQuestions.length) {
                affectedDefectAreas.push({ defectArea: defectArea, questions: affectedQuestions });
            }
        }

        return affectedDefectAreas;
    }

    _confirmAffectedQuestions() {
        let affectedAreasAndQuestions = this.getAffectedDefectAreas(this._reviewService.currentReviewQATrees) || [];
        let list = affectedAreasAndQuestions.map((area) => {
            let questionList = area.questions.map((q) => `<li>${q.questionReference} - ${q.questionText}</li>`).join('');
            return `<li class="check-icon">${area.defectArea.title}<ul>${questionList}</ul></li>`;
        }).join('');

        let saveData = () => {

            // the qaTree/defectarea codes that the delete orphaned question action will target.
            let qaTreeTargets = affectedAreasAndQuestions.map(obj => { return {defectAreaCode: obj.defectArea.defectAreaCode, isCompleted: false}; });
            this._reviewService.deleteOrphanedQuestionsAnswers = qaTreeTargets;
            this._reviewService.saveFilteredSummaryFields(this.buildRequestPayload());
        };

        if (list && list.length) {
            // Open a modal to confirm
            this._modalSvc.askForConfirmation(
                `<p>The data you have modified requires you to review these defect areas for changes</p><div class="list-container"><ul class="list-group no-shadow">${list}</ul></div>`,
                'UPDATE LOAN ATTRIBUTES').first().subscribe(
                    () => {
                        saveData();
                    },
                    () => {
                        this.reset();
                    }
                );
        } else {
            // No affected questions, go ahead and save...
            saveData();
        }
    }

    buildRequestPayload(saveAll: Boolean = false) {
        let formValues = this.form.value;
        let editedFields = (saveAll) ? this.reviewFields : this.reviewFields.filter((field) => this.form.controls[field.fieldId].dirty);

        // Build the request playload based on the current Form values. We have to manually build the request data this way to handle the Radio and multiselect Input controls.
        return editedFields.map((field) => {

            // Grab the answer updated via binding.
            let currentValue = _.isArray(formValues[field.fieldId]) ? formValues[field.fieldId].join(',') : formValues[field.fieldId];
            if (field.type === 'multiselect') {
                currentValue = _.isArray(currentValue) ? currentValue.split(',') : currentValue;
            }

            if (field.type.toLowerCase() === 'date') {
                //Fix for IE, and date must be in UTC, otherwise a 500 occurs
                let date = moment.utc(currentValue);
                currentValue = date;
            }

            field.value = currentValue;
            return field;
        });
    }

    reset() {

        // Rebuild the form.
        this.reviewFields = _.cloneDeep(this._sourceReviewFields);
        this._createSummaryFormGroup();
        this.globalStateService.loanAttributesEdited = false;
    }

}
