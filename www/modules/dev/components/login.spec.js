import {async, TestBed} from '@angular/core/testing';
import {RouterTestingModule} from '@angular/router/testing';
import {DEV_COMPONENTS} from '../components/';
import {DEV_PROVIDERS} from '../services/';
import {DEV_IMPORTS} from '../main';
import Login from './login';

describe('dev/components/login.js', () => {

    beforeEach(() => {
        TestBed.configureTestingModule({
            declarations: DEV_COMPONENTS,
            providers: DEV_PROVIDERS,
            imports: [ DEV_IMPORTS, RouterTestingModule ]
        });
    });

    it('should be creatable', async(() => {
        TestBed.compileComponents().then(() => {
            const fixture = TestBed.createComponent(Login);

            expect(fixture.componentInstance).toBeDefined();
            expect(fixture.debugElement.nativeElement.innerHTML).toBeTruthy();
        });
    }));

    it('should automatically fetch a list of accounts', async(() => {
        TestBed.compileComponents().then(() => {
            const fixture = TestBed.createComponent(Login);
            fixture.componentInstance.ngOnInit();
            fixture.whenStable().then(() => {
                expect(fixture.componentInstance.accounts.length).toBeGreaterThan(0);
            });
        });
    }));

});