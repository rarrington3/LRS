import {async, TestBed} from '@angular/core/testing';
import {RouterTestingModule} from '@angular/router/testing';
import {REVIEW_COMPONENTS} from '../components/';
import {REVIEW_PROVIDERS} from '../services/';
import {REVIEW_IMPORTS} from '../main';
import QuestionAndAnswerTree from './QuestionAndAnswerTree';
import QuestionNode from './QuestionNode';
import {By} from '@angular/platform-browser';
import {DebugElement} from '@angular/core';
import ModalSvc from '../../app/services/ModalSvc';


describe('review/components/QuestionAndAnswerTree.js', () => {
    let fixture;
    let comp;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: REVIEW_COMPONENTS,
            providers:[ModalSvc, REVIEW_PROVIDERS],
            imports: [REVIEW_IMPORTS, RouterTestingModule ]
        }).compileComponents().then(() => {
            fixture = TestBed.createComponent(QuestionAndAnswerTree);
            comp = fixture.componentInstance;

            // Start loading fake data.
            comp.globalStateSvc.currentReviewId = '123';
            comp.globalStateSvc.currentDefectAreaCode = 'BA';
        });
    }));

    it('should be creatable', () => {
        expect(fixture).toBeDefined();
        expect(fixture.debugElement.nativeElement.innerHTML).toBeTruthy();
    });

    it('should have fake questions for testing', () => {
        expect(comp.reviewService.questionsForCurrentArea).toBeDefined();
        expect(comp.reviewService.questionsForCurrentArea && comp.reviewService.questionsForCurrentArea.length).toBeGreaterThan(0);
    });

    it('should populate with questions', async(() => {
        fixture.detectChanges();
        fixture.whenStable().then(() => {
            let questionList = fixture.debugElement.queryAll(By.css('.list-group .list-group-item.node-wrapper')).map(item => item.nativeElement);
            expect(questionList.length).toBeGreaterThan(0);
            expect(comp.questions.length).toBeGreaterThan(0);
        });
    }));

    it('should contain the correct Defect Area title', async(() => {
        fixture.detectChanges();
        fixture.whenStable().then(() => {
            expect(fixture.debugElement.nativeElement.querySelector('.view-title').innerText).toBe(comp.defectArea.title.toUpperCase() + " (" + comp.defectArea.defectAreaCode.toUpperCase() + ")");
        });
    }));
});