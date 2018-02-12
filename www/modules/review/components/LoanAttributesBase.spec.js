import {async, TestBed} from '@angular/core/testing';
import {RouterTestingModule} from '@angular/router/testing';
import {REVIEW_COMPONENTS} from '../components/';
import {REVIEW_PROVIDERS} from '../services/';
import {REVIEW_IMPORTS} from '../main';
import LoanAttributesBase from './LoanAttributesBase';

describe('review/components/LoanAttributesBase.js', () => {

    beforeEach(() => {
        TestBed.configureTestingModule({
            declarations: REVIEW_COMPONENTS,
            providers: REVIEW_PROVIDERS,
            imports: [ REVIEW_IMPORTS, RouterTestingModule ]
        });
    });

    it('should be creatable but have an empty template', async(() => {
        TestBed.compileComponents().then(() => {
            const fixture = TestBed.createComponent(LoanAttributesBase);

            expect(fixture.componentInstance).toBeDefined();
            expect(fixture.debugElement.nativeElement.innerHTML).toBe('');
        });
    }));
});