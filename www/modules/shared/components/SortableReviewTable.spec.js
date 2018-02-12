// This file was generated from the component scaffold
// Copyright 2016

import {async, TestBed} from '@angular/core/testing';
import {RouterTestingModule} from '@angular/router/testing';
import {SHARED_DIRECTIVES} from '../directives/';
import {SHARED_COMPONENTS} from '../components/';
import {SHARED_PIPES} from '../pipes/';
import {SHARED_IMPORTS} from '../main';
import SortableReviewTable from './SortableReviewTable';

describe('shared/components/SortableReviewTable.js', () => {

    let fixture;
    let component;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [SHARED_DIRECTIVES, SHARED_COMPONENTS, SHARED_PIPES],
            imports: [SHARED_IMPORTS, RouterTestingModule]
        }).compileComponents().then(() => {
            fixture = TestBed.createComponent(SortableReviewTable);
            component = fixture.componentInstance;
        })
    }));

    it('should be creatable', async(() => {
        expect(fixture.componentInstance).toBeDefined();
        expect(fixture.debugElement.nativeElement.innerHTML).toBeTruthy();
    }));

    describe('addColor()', () => {

        it('should add proper color for due today and past due reviews', async(() => {
            let addedClass = component.addColor('');
            expect(addedClass).toEqual('');
        }));
    });

    describe('addHarvey()', () => {

        it('should add none Harvey ball icon', async(() => {
            let emptyClass = component.addHarvey('');
            expect(emptyClass).toEqual('harvey-none');
        }));

        it('should add empty Harvey ball icon', async(() => {
            let emptyClass = component.addHarvey({'lenderAction': 'pending'});
            expect(emptyClass).toEqual('harvey-empty');
        }));

        it('should add half Harvey ball icon', async(() => {
            let initiatedClass = component.addHarvey({'lenderAction': 'initiated'});
            expect(initiatedClass).toEqual('harvey-half');
        }));

        it('should add full Harvey ball icon', async(() => {
            let fullClass = component.addHarvey({'lenderAction': 'completed'});
            expect(fullClass).toEqual('harvey-full');
        }));

    });

});
