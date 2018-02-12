import {async, TestBed} from '@angular/core/testing';
import {RouterTestingModule} from '@angular/router/testing';
import {WORKLOAD_COMPONENTS} from '../components/';
import {WORKLOAD_PROVIDERS} from '../services/';
import {WORKLOAD_IMPORTS} from '../main';
import MyWorkload from './MyWorkload';

describe('workload/components/MyWorkload.js', () => {

    let fixture;
    let component;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: WORKLOAD_COMPONENTS,
            providers: WORKLOAD_PROVIDERS,
            imports: [WORKLOAD_IMPORTS, RouterTestingModule]
        }).compileComponents().then(() => {
            fixture = TestBed.createComponent(MyWorkload);
            component = fixture.componentInstance;
        })
    }));

    it('should be creatable', async(() => {
        expect(fixture.componentInstance).toBeDefined();
        expect(fixture.debugElement.nativeElement.innerHTML).toBeTruthy();
    }));

    describe('ngOnInit()', () => {
        beforeEach(async(() => {
            component.ngOnInit();
        }));

        it('should declare categories for filtering', () => {
            expect(component.categories.length).toBeGreaterThan(1);
        })
    })

});
