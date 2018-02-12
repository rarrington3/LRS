import {async, TestBed} from '@angular/core/testing';
import {RouterTestingModule} from '@angular/router/testing';
import {WORKLOAD_COMPONENTS} from '../components/';
import {WORKLOAD_PROVIDERS} from '../services/';
import {WORKLOAD_IMPORTS} from '../main';
import WorkloadContainer from './WorkloadContainer';

describe('workload/components/WorkloadContainer.js', () => {

    beforeEach(() => {
        TestBed.configureTestingModule({
            declarations: WORKLOAD_COMPONENTS,
            providers: WORKLOAD_PROVIDERS,
            imports: [ WORKLOAD_IMPORTS, RouterTestingModule ]
        });
    });

    it('should be creatable', async(() => {
        TestBed.compileComponents().then(() => {
            const fixture = TestBed.createComponent(WorkloadContainer);

            expect(fixture.componentInstance).toBeDefined();
            expect(fixture.debugElement.nativeElement.innerHTML).toBeTruthy();
        });
    }));
});