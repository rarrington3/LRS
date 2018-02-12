import {async, inject, TestBed} from '@angular/core/testing';
import {RouterTestingModule} from '@angular/router/testing';
import {REVIEW_COMPONENTS} from '../components/';
import {REVIEW_PROVIDERS} from '../services/';
import {REVIEW_IMPORTS} from '../main';
import QuestionAndAnswerSideBar from './QuestionAndAnswerSideBar';
import {By} from '@angular/platform-browser';
import {HttpModule, Http} from '@angular/http';
import {Observable} from 'rxjs';

describe('review/components/QuestionAndAnswerSideBar.js', () => {

    let fixture;
    let comp;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: REVIEW_COMPONENTS,
            providers: REVIEW_PROVIDERS,
            imports: [ REVIEW_IMPORTS, RouterTestingModule ]
        }).compileComponents().then(() => {
            fixture = TestBed.createComponent(QuestionAndAnswerSideBar);
            comp = fixture.componentInstance;
        });
    }));

    it('should be creatable', () => {
        expect(fixture).toBeDefined();
        expect(fixture.debugElement.nativeElement.innerHTML).toBeTruthy();
    });

    it('should populate Defect Area items', () => {
        comp.globalStateService.currentReviewId = '123';
        comp.globalStateService.currentDefectAreaCode = 'BA';
        fixture.detectChanges();
        fixture.whenStable().then(() => {
            fixture.detectChanges();
            let items = fixture.debugElement.queryAll(By.css('.list-group-item')).map(item => item.nativeElement);
            expect(items.length).toBeGreaterThan(0);
        });
    });

    // TODO: This test is not working at the moment. I need to figure out how to force the template to select a defect area in order for the fields to display.
    /*it('should populate with the DefectAreaSummaryFieldDirective instances', async(() => {
     comp.globalStateService.currentReviewId = '123';
     comp.globalStateService.currentDefectAreaCode = 'BA';
     fixture.detectChanges();
     fixture.whenStable().then(() => {
     fixture.detectChanges();
     let selectedFieldGroup2 = fixture.debugElement.queryAll(By.css('.summary-fields')).map(item => item.nativeElement);
     expect(selectedFieldGroup.length).toBeGreaterThan(0);

     });
     }));*/
});
