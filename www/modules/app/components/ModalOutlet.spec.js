// This file was generated from the component scaffold
// Copyright 2017

import {async, TestBed} from '@angular/core/testing';
import {RouterTestingModule} from '@angular/router/testing';
import {APP_COMPONENTS} from '../components/';
import {APP_PROVIDERS} from '../services/';
import {SharedModule} from '../../shared/main';
import ModalOutlet from './ModalOutlet';

describe('app/components/ModalOutlet.js', () => {

    beforeEach(() => {
        TestBed.configureTestingModule({
            declarations: APP_COMPONENTS,
            providers: APP_PROVIDERS,
            imports: [SharedModule, RouterTestingModule]
        });
    });

    it('should be creatable', async(() => {
        TestBed.compileComponents().then(() => {
            const fixture = TestBed.createComponent(ModalOutlet);

            expect(fixture.componentInstance).toBeDefined();
            expect(fixture.debugElement.nativeElement.innerHTML).toBeTruthy();
        });
    }));

    it('should have a helper function for acknowledgement prompts', async(() => {
        TestBed.compileComponents().then(() => {
            const fixture = TestBed.createComponent(ModalOutlet);

            spyOn(fixture.componentInstance, '_openBasicModal').and.callThrough();

            fixture.componentInstance.acknowledge('b', 'h', 'c').subscribe(()=>{});

            expect(fixture.componentInstance.isOpen).toEqual(true);
            expect(fixture.componentInstance.displayedModal).toEqual('acknowledge');
            expect(fixture.componentInstance._openBasicModal).toHaveBeenCalled();
        });
    }));

    it('should have a helper function for confirmation prompts', async(() => {
        TestBed.compileComponents().then(() => {
            const fixture = TestBed.createComponent(ModalOutlet);

            spyOn(fixture.componentInstance, '_openBasicModal').and.callThrough();

            fixture.componentInstance.askForConfirmation('b', 'h', 'c', 'r').subscribe(()=>{});

            expect(fixture.componentInstance.isOpen).toEqual(true);
            expect(fixture.componentInstance.displayedModal).toEqual('confirmation');
            expect(fixture.componentInstance._openBasicModal).toHaveBeenCalled();
        });
    }));

    it('should complete the observable when the modal is confirmed', async(() => {
        TestBed.compileComponents().then(() => {
            const fixture = TestBed.createComponent(ModalOutlet);

            var yay = jasmine.createSpy('yay');
            var nay = jasmine.createSpy('nay');

            fixture.componentInstance.acknowledge('b', 'h', 'c').subscribe(yay, nay);
            fixture.componentInstance.confirm();

            expect(yay).toHaveBeenCalled();
            expect(nay).not.toHaveBeenCalled();
        });
    }));

    it('should send an error to the observable when the modal is rejected', async(() => {
        TestBed.compileComponents().then(() => {
            const fixture = TestBed.createComponent(ModalOutlet);

            var yay = jasmine.createSpy('yay');
            var nay = jasmine.createSpy('nay');

            fixture.componentInstance.acknowledge('b', 'h', 'c').subscribe(yay, nay);
            fixture.componentInstance.finalize();

            expect(nay).toHaveBeenCalled();
            expect(yay).not.toHaveBeenCalled();
        });
    }));

});
