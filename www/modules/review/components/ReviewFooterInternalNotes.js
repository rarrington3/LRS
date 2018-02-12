// This file was generated from the component scaffold
// Copyright 2016

import {Component} from '@angular/core';
import template from './ReviewFooterInternalNotes.html';
import styles from './ReviewFooterInternalNotes.less';
import ReviewFooter from './ReviewFooter';
import {FormGroup} from '@angular/forms';
import _ from 'lodash';

@Component({
    selector: 'review-footer-internal-notes',
    template: template,
    styles: [styles]
})

export default class ReviewFooterInternalNotes extends ReviewFooter {
    filterForm:FormGroup;
    defectAreas:Array = [];
    allReviewNotes:Array = [];
    reviewNotes:Array = [];
    _allDefectAreas:Array = [];
    newNote:String = null;

    ngOnInit() {
        super.ngOnInit();

        this.filterForm = this.formBuilder.group({
            noteDefectAreaFilter:'all',
            sortBy: 'sortByDate'
        });

        this.subscriptions.push(this.filterForm.valueChanges.subscribe(() => this._filterAndSortNotes()));

        this._refreshReviewNotes();

        this.subscriptions.push( this.globalStateService.defectAreasForCurrentReviewObservable.subscribe( data => {
            if (data.currentValue) {
                this.defectAreas = data.currentValue;
                this._updateSelectedDefectArea();
            }
        }));
    }

    onDefectAreaChanged() {
        super.onDefectAreaChanged();
        this._updateSelectedDefectArea();
    }

    _updateSelectedDefectArea() {
        //Not sure why the defect areas select's default selections has stopped working. (May be related to dynamic data)
        //Use the 'defer' trick to force the defect areas select to select 'all' the first time.
        _.defer(() => {
            let selectedDefectArea = this.globalStateService.currentDefectAreaCode ? this.globalStateService.currentDefectAreaCode : 'all';
            this.filterForm.controls.noteDefectAreaFilter.setValue(selectedDefectArea);
        });
    }

    _refreshReviewNotes() {
        this.reviewService.getAllReviewNotes(this.globalStateService.currentReviewId).subscribe(notes => {

            if (notes) {
                this.allReviewNotes = notes;
                this._filterAndSortNotes();
            }
        });
    }

    _filterAndSortNotes() {
        let {noteDefectAreaFilter, sortBy} = this.filterForm.value;
        this.reviewNotes = this.allReviewNotes.filter(n => {
            if (noteDefectAreaFilter  === 'all') {
                return true;
            }

            return (noteDefectAreaFilter ===  n.defectAreaCode);
        }).sort(function(a, b) {
            if (sortBy === 'sortByReviewers') {
                return a.nameLast < b.nameLast ? -1 : 1;
            }
            return a.date > b.date ? -1 : 1;
        });
    }

    onPostNewNote() {
        if (this.newNote && this.newNote.length) {
            let associatedDefectArea = this.filterForm.value.noteDefectAreaFilter === 'all' ? null : this.filterForm.value.noteDefectAreaFilter;
            this.reviewService.postReviewNote(this.globalStateService.currentReviewId, this.newNote, associatedDefectArea).subscribe(() => {
                this.newNote = null;
                this._refreshReviewNotes();
            });
        }
    }
}
