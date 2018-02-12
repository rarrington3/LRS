// This file was generated from the view scaffold
// Copyright 2017

import { async, TestBed} from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { MICROSTRATEGY_VIEWS } from '../views/';
import { MICROSTRATEGY_COMPONENTS } from '../components/';
import { MICROSTRATEGY_PROVIDERS } from '../services/';
import { MICROSTRATEGY_IMPORTS } from '../main';
import Microstrategy from './microstrategy';

describe('microstrategy/components/microstrategy.js', () => {

    beforeEach(() => {
        TestBed.configureTestingModule({
             declarations: [MICROSTRATEGY_COMPONENTS, MICROSTRATEGY_VIEWS],
             providers: MICROSTRATEGY_PROVIDERS,
             imports: [ MICROSTRATEGY_IMPORTS, RouterTestingModule ]
        });
    });

    it('should be creatable', async(() => {
        TestBed.compileComponents().then(() => {
            const fixture = TestBed.createComponent(Microstrategy);

            expect(fixture.componentInstance).toBeDefined();
            expect(fixture.debugElement.nativeElement.innerHTML).toBeTruthy();
        });
    }));

});
