import { async, inject, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { WORKLOAD_PROVIDERS } from '../services/';
import { WORKLOAD_IMPORTS } from '../main';
import WorkloadProvider from './WorkloadProvider';

describe('workload/services/WorkloadProvider.js', () => {

    beforeEach(() => {
        TestBed.configureTestingModule({
            providers: WORKLOAD_PROVIDERS,
            imports: [ WORKLOAD_IMPORTS, RouterTestingModule ]
        });
    });

    it('should return WorkloadProvider instance', async(inject([WorkloadProvider], (workloadProvider:WorkloadProvider) => {
        expect(workloadProvider).toBeDefined();
    })));

});
