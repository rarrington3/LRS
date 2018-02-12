import {async, inject, TestBed} from "@angular/core/testing";
import {RouterTestingModule} from "@angular/router/testing";
import {REVIEW_COMPONENTS} from "../components/";
import {REVIEW_PROVIDERS} from "../services/";
import {REVIEW_IMPORTS} from "../main";
import LoanSummary from "./LoanSummary";
import LoanSummaryField from "./LoanSummaryField";
import {By} from "@angular/platform-browser";
import Client from "../../api/lrs.api.v1.generated";
import {HttpModule, Http} from "@angular/http";

describe('review/components/LoanSummary.js', () => {

    let fixture;
    let comp;
    let fakeFields;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: REVIEW_COMPONENTS,
            providers: REVIEW_PROVIDERS,
            imports: [REVIEW_IMPORTS, RouterTestingModule]
        }).compileComponents().then(() => {
            fixture = TestBed.createComponent(LoanSummary);
            comp = fixture.componentInstance;
        });
    }));

    beforeEach(async(inject([Http], (http: Http) => {
        let client = new Client(http);
        client.resources.reviews.reviewId('123').level.reviewLevelId('123').fields.get().first().subscribe(fields => {
            fakeFields = fields;
        });
    })));

    it('should be creatable', () => {
        expect(fixture).toBeDefined();
        expect(fixture.debugElement.nativeElement.innerHTML).toBeTruthy();
    });

    it('should have fake fields', () => {
        expect(fakeFields instanceof Array).toBe(true);
        expect(fakeFields.length).toBeGreaterThan(0);
    });

    xit('should populate with the LoanSummaryField directive instances', async(() => {
        comp._reviewService.summaryFieldsForCurrentReviewLevel = fakeFields;
        fixture.detectChanges();
        fixture.whenStable().then(() => {
            fixture.detectChanges();
            let loanSummaryFieldDirective = fixture.debugElement.query(By.directive(LoanSummaryField));
            let fieldGroups = fixture.debugElement.queryAll(By.css('table')).map(item => item.nativeElement);
            expect(fieldGroups).toBeDefined();
            expect(fieldGroups.length).toBeGreaterThan(0);
        });
    }));
});
