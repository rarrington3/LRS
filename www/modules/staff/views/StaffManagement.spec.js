import {async, TestBed} from '@angular/core/testing';
import {RouterTestingModule} from '@angular/router/testing';
import {STAFF_VIEWS} from '../views/';
import {STAFF_COMPONENTS} from '../components/';
import {STAFF_PROVIDERS} from '../services/';
import {STAFF_IMPORTS} from '../main';
import StaffManagement from './StaffManagement';
import {STATUS_CODE} from '../../shared/constants';

describe('staff/views/StaffManagement.js', () => {

    let fixture;
    let component;
    let nativeElement;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [STAFF_COMPONENTS, STAFF_VIEWS],
            providers: STAFF_PROVIDERS,
            imports: [STAFF_IMPORTS, RouterTestingModule]
        }).compileComponents().then(() => {
            fixture = TestBed.createComponent(StaffManagement);
            component = fixture.componentInstance;
            nativeElement = fixture.debugElement.nativeElement;
        });
    }));

    it('should be creatable', async(() => {
        expect(fixture).toBeTruthy();
        expect(component).toBeTruthy();
        expect(nativeElement.innerHTML).toBeTruthy();
    }));

    it('should declare management types', () => {
        expect(component.mgmtTypes.length).toBeGreaterThan(1);
        let mgmtType = component.mgmtTypes[1];
        expect(mgmtType.value).toBeTruthy();
        expect(mgmtType.text).toBeTruthy();
        expect(mgmtType.valueField).toBeDefined();
        expect(mgmtType.textField).toBeDefined();
        expect(mgmtType.items).toBeDefined();
    });

    describe('ngOnInit()', () => {
        beforeEach(async(() => {
            component.ngOnInit();
        }));

        it('should get locations', () => {
            expect(component.locations.length).toBeGreaterThan(0);
            let location = component.locations[0];
            expect(location.locationId).toBeTruthy();
            expect(location.name).toBeTruthy();
        });

        it('should get review types', () => {
            expect(component.reviewTypes.length).toBeGreaterThan(0);
            let reviewType = component.reviewTypes[0];
            expect(reviewType.code).toBeTruthy();
            expect(reviewType.description).toBeTruthy();
        });

        it('should get product types', () => {
            expect(component.productTypes.length).toBeGreaterThan(0);
            let productType = component.productTypes[0];
            expect(productType.code).toBeTruthy();
            expect(productType.description).toBeTruthy();
        });

        it('should get selection reasons', () => {
            expect(component.selectionReasons.length).toBeGreaterThan(0);
            let selectionReason = component.selectionReasons[0];
            expect(selectionReason.code).toBeTruthy();
            expect(selectionReason.description).toBeTruthy();
        });

        it('should get review levels', () => {
            expect(component.reviewLevels.length).toBeGreaterThan(0);
            let reviewLevel = component.reviewLevels[0];
            expect(reviewLevel.code).toBeTruthy();
            expect(reviewLevel.description).toBeTruthy();
        });

        it('should get all reviewers', () => {
            expect(component.allReviewers.length).toBeGreaterThan(0);
            let reviewer = component.allReviewers[0];
            expect(reviewer.reviewerId).toBeTruthy();
            expect(reviewer.name).toBeTruthy();
            expect(reviewer.hudId).toBeTruthy();
        });

        it('should fill management types', () => {
            let mgmtType = component.mgmtTypes[1];
            expect(mgmtType.value).toBeTruthy();
            expect(mgmtType.text).toBeTruthy();
            expect(mgmtType.valueField).toBeTruthy();
            expect(mgmtType.textField).toBeTruthy();
            expect(mgmtType.items).toBeTruthy();
        });
    });

    describe('search()', () => {

        beforeEach(async(() => {
            component.ngOnInit();
        }));

        xit('should search of a reviewer', async(() => {
            let reviewer = component.allReviewers[0];
            component.filter.reviewerIds = [reviewer.reviewerId];
            component.search();
            expect(component.reviewers.length).toBe(1);
        }));

        it('should search of a reviewer with a review type', async(() => {
            component.filter.reviewTypeId = 'S';
            component.search();
            expect(component.reviewers.length).toBeGreaterThan(0);
        }));

        it('should search of a reviewer with a selection reason', async(() => {
            component.filter.selectionReasonId = 'LENDER_MONITORING';
            component.search();
            expect(component.reviewers.length).toBeGreaterThan(0);
        }));

        it('should search of a reviewer with a review level', async(() => {
            component.filter.reviewLevelId = 'INIT';
            component.search();
            expect(component.reviewers.length).toBeGreaterThan(0);
        }));

        it('should search of a reviewer with a loan type', async(() => {
            component.filter.loanTypeId = 'PRCHS';
            component.search();
            expect(component.reviewers.length).toBeGreaterThan(0);
        }));
    });

    describe('check()', () => {

        beforeEach(async(() => {
            component.ngOnInit();
        }));

        xit('should toggle a reviewer', async(() => {
            let reviewer = component.allReviewers[0];
            let mgmtType = component.mgmtTypes[1];
            let item = mgmtType.items[0];
            let initialLength = reviewer.reviewTypes.length;
            component.check(item, mgmtType, reviewer);
            expect(reviewer.reviewTypes.length).not.toBe(initialLength);
        }));
    });

    describe('deactivate()', () => {

        beforeEach(async(() => {
            component.ngOnInit();
        }));

        it('should deactivate a reviewer', async(() => {
            let reviewer = component.allReviewers[0];
            component.deactivate(reviewer);
            expect(reviewer.statusCode).toBe(STATUS_CODE.INACTIVE);
        }));
    });

    describe('activate()', () => {

        beforeEach(async(() => {
            component.ngOnInit();
        }));

        it('should activate a reviewer', async(() => {
            let reviewer = component.allReviewers[0];
            component.activate(reviewer);
            expect(reviewer.statusCode).toBe(STATUS_CODE.ACTIVE);
        }));
    });

    describe('saveAddMember()', () => {

        let _newReviewer = {
            locationId: '',
            reportsTo: '',
            hudId: ''
        };

        beforeEach(async(() => {
            component.ngOnInit();
        }));

        it('should add a reviewer', async(() => {
            component.addMemberData.reviewer = Object.assign({},
                _newReviewer,
                {
                    locationId: 'ATL_PUD',
                    reportsTo: '5CC07A7B-D2F8-4C4C-A355-87F1DADA2AFA',
                    hudId: 'H00291'
                });
            let count = component.reviewers.length;
            component.saveAddMember();
            expect(component.reviewers.length).toBe(count + 1); // one more
        }));
    });

    describe('resetAddMember()', () => {

        let emptyReviewer = {};

        let _newReviewer = {
            locationId: '',
            reportsTo: '',
            hudId: ''
        };

        beforeEach(async(() => {
            component.ngOnInit();
        }));

        it('should reset a reviewer', async(() => {
            component.addMemberData.reviewer = Object.assign({},
                _newReviewer,
                {
                    locationId: 'ATL_PUD',
                    reportsTo: '5CC07A7B-D2F8-4C4C-A355-87F1DADA2AFA',
                    hudId: 'H00291'
                });
            component.saveAddMember();
            component.resetAddMember();
            expect(component.addMemberData.reviewer).toEqual(emptyReviewer);
        }));
    });
});
