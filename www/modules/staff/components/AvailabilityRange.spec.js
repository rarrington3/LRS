// This file was generated from the component scaffold
// Copyright 2016

//import $ from 'jquery';
import {SimpleChange} from '@angular/core';
import {async, inject, TestBed} from '@angular/core/testing';
import {RouterTestingModule} from '@angular/router/testing';
import {STAFF_COMPONENTS} from '../components/';
import {STAFF_PROVIDERS} from '../services/';
import {STAFF_IMPORTS} from '../main';
import AvailabilityRange from './AvailabilityRange';
import Client from '../../api/lrs.api.v1.generated';
import {ReviewerSvc} from '../../app/services';

describe('staff/components/AvailabilityRange.js', () => {

    let fixture;
    let component;
    let nativeElement;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: STAFF_COMPONENTS,
            providers: STAFF_PROVIDERS,
            imports: [STAFF_IMPORTS, RouterTestingModule]
        }).compileComponents().then(() => {
            fixture = TestBed.createComponent(AvailabilityRange);
            component = fixture.componentInstance;
            nativeElement = fixture.debugElement.nativeElement;
        });
    }));

    beforeEach(async(inject([Client, ReviewerSvc], (client: Client, reviewerSvc: ReviewerSvc) => {
        reviewerSvc.getReviewers().first()
            .subscribe(reviewers => {
                if (component && reviewers.length) {
                    component.unavailabilities = reviewers[0].unavailabilities;
                }
            });
    })));

    it('should be creatable', async(() => {
        expect(fixture).toBeTruthy();
        expect(component).toBeTruthy();
        expect(nativeElement.innerHTML).toBeTruthy();
    }));

    describe('ngOnChanges()', () => {

        beforeEach(() => {
            spyOn(component, 'reset').and.callThrough();
        });

        it('should reset when getting new unavailabilities', () => {

            let simpleChange = new SimpleChange(null, component.unavailabilities);
            component.ngOnChanges({'unavailabilities': simpleChange});
            expect(component.reset).toHaveBeenCalled();
        })
    });

    describe('add()', () => {

        it('should add an availability', () => {

            let count = component.unavailabilities.length;
            component.add();
            expect(component.unavailabilities.length).toBe(count + 1);
        })
    });

    describe('remove()', () => {

        it('should remove an unavailability', () => {

            expect(component.unavailabilities.length).toBeGreaterThan(0);

            let count = component.unavailabilities.length;
            let unavailability = component.unavailabilities[0];
            component.remove(unavailability);
            expect(component.unavailabilities.length).toBe(count - 1);
            expect(component.unavailabilities).not.toContain(unavailability);
        })
    });

    describe('reset()', () => {

        beforeEach(() => {
            spyOn(component.canSaveChange, 'emit');

            component.ngAfterViewChecked();
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
                 let $element = $(nativeElement.querySelector('#staffUnavailableFrom0'));
                 $element.val('2016-10-29');
                 $element.trigger('change');
                 $element = $(nativeElement.querySelector('#staffUnavailableTo0'));
                 $element.val('2016-11-29');
                 $element.trigger('change');
                 */

                // TODO : Following forces the form change state
                component.form.form.updateValueAndValidity();
                component.form.form.markAsDirty();
                expect(component.form.dirty).toEqual(true);
                expect(component.form.valid).toEqual(true);

                // check
                component.checkCanSave();
                expect(component.canSaveChange.emit.calls.mostRecent().args[0]).toBe(true);

            });
        }));

        it('should not be able save with no changes', () => {
            component.checkCanSave();
            expect(component.canSaveChange.emit).toHaveBeenCalledWith(false);
        });
    });

});
