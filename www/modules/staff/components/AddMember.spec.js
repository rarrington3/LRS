// This file was generated from the component scaffold
// Copyright 2016

//import $ from 'jquery';
import {SimpleChange} from '@angular/core';
import {async, inject, TestBed} from '@angular/core/testing';
import {RouterTestingModule} from '@angular/router/testing';
import {STAFF_COMPONENTS} from '../components/';
import {STAFF_PROVIDERS} from '../services/';
import {STAFF_IMPORTS} from '../main';
import AddMember from './AddMember';
import Client from '../../api/lrs.api.v1.generated';
import {ReviewerSvc} from '../../app/services';


describe('staff/components/AddMember.js', () => {

    let fixture;
    let component;
    let nativeElement;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: STAFF_COMPONENTS,
            providers: STAFF_PROVIDERS,
            imports: [STAFF_IMPORTS, RouterTestingModule]
        }).compileComponents().then(() => {
            fixture = TestBed.createComponent(AddMember);
            component = fixture.componentInstance;
            nativeElement = fixture.debugElement.nativeElement;
        });
    }));

    beforeEach(async(inject([Client, ReviewerSvc], (client: Client, reviewerSvc: ReviewerSvc) => {
        client.resources.locations.get().first()
            .subscribe(locations => {
                if (component) {
                    component.locations = locations
                }
            });
        reviewerSvc.getReviewers().first()
            .subscribe(reviewers => {
                if (component) {
                    component.reviewers = reviewers
                }
            });
    })));

    it('should be creatable', async(() => {
        expect(fixture).toBeTruthy();
        expect(component).toBeTruthy();
        expect(nativeElement.innerHTML).toBeTruthy();
    }));

    describe('ngOnChanges()', () => {

        it('should load reviewers by location', () => {

            let simpleChange = new SimpleChange(null, component.reviewers);
            component.ngOnChanges({'reviewers': simpleChange});
            expect(component.reviewersByLoc.length).toBeGreaterThan(0);
        })
    });

    describe('reset()', () => {

        beforeEach(() => {
            spyOn(component.canSaveChange, 'emit');

            component.ngAfterViewChecked(); // angular cycles are not triggering from in code html changes...
        });

        it('should not be able save when reset', () => {
            component.reset();
            expect(component.canSaveChange.emit.calls.mostRecent().args[0]).toBe(false);
        });
    });

    describe('checkCanSave()', () => {

        beforeEach(() => {
            spyOn(component.canSaveChange, 'emit');

            component.ngAfterViewChecked(); // angular cycles are not triggering from in code html changes...
        });

        it('should be able to save with changes', async(() => {

            fixture.detectChanges();

            fixture.whenStable().then(() => {

                // TODO : Following is not changing the form state

                /*
                 let $element = $(nativeElement.querySelector('#staffAddMemberId'));
                 $element.val('H34490');
                 $element.trigger('change');
                 $element = $(nativeElement.querySelector('#staffAddMemberLocationId'));
                 $element.val('ATL_PUD');
                 $element.trigger('change');
                 $element = $(nativeElement.querySelector('#staffAddMemberReportsTo'));
                 $element.val('H01822');
                 $element.trigger('change');
                 */

                // TODO : Following forces the form change state

                let control = component.form.form.get('staffAddMemberId');
                control.setValue('H34490');
                control.updateValueAndValidity();
                control.markAsDirty();
                expect(control.dirty).toEqual(true);
                expect(control.valid).toEqual(true);

                expect(component.locations.length).toBeGreaterThan(0);
                control = component.form.form.get('staffAddMemberLocationId');
                control.setValue(component.locations[0].locationId);
                control.updateValueAndValidity();
                control.markAsDirty();
                expect(control.dirty).toEqual(true);
                expect(control.valid).toEqual(true);

                expect(component.reviewers.length).toBeGreaterThan(0);
                control = component.form.form.get('staffAddMemberReportsTo');
                control.setValue(component.reviewers[0].reviewerId);
                control.updateValueAndValidity();
                control.markAsDirty();
                expect(control.dirty).toEqual(true);
                expect(control.valid).toEqual(true);

                expect(component.form.dirty).toEqual(true);
                expect(component.form.valid).toEqual(true);

                component.staffId = 'H34490';
                component.search();

                // checking
                component.checkCanSave();
                expect(component.canSaveChange.emit.calls.mostRecent().args[0]).toBe(true);
            });
        }));

        it('should not be able save with no changes', () => {
            component.checkCanSave();
            expect(component.canSaveChange.emit).toHaveBeenCalledWith(false);
        });
    });

    describe('search()', () => {
        it('should search from a hud id', async(() => {
            let hudId = 'H34490';
            component.staffId = hudId;
            component.search();
            expect(component.reviewer.hudId).toBe(hudId);
        }));
    });
});
