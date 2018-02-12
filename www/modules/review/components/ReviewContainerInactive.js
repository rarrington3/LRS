// This file was generated from the component scaffold
// Copyright 2016

import {Component, Input, Output, EventEmitter} from '@angular/core';
import {Router} from '@angular/router';
import template from './ReviewContainerInactive.html';
import styles from './ReviewContainerInactive.less';
import GlobalStateSvc from '../services/GlobalStateSvc';

@Component({
    template: template,
    styles: [styles],
    selector: 'review-container-inactive'
})
/**
 * @see https://angular.io/docs/ts/latest/api/core/Component-decorator.html
 * @example
 * <review-container-inactive name="ReviewContainerInactive" (change)="onChange($event)"></review-container-inactive>
 */
export default class ReviewContainerInactive {
    /**
     * An example input for this component
     * @see https://angular.io/docs/ts/latest/api/core/Input-var.html
     */
    @Input() name:string = 'ReviewContainerInactive';

    /**
     * An example output for this component
     * @see https://angular.io/docs/ts/latest/api/core/Output-var.html
     */
    @Output() change:EventEmitter = new EventEmitter();

    router:Router;
    globalState:GlobalStateSvc;
    constructor(router:Router, globalState:GlobalStateSvc) {
        this.router = router;
        this.globalState = globalState;
    }

    ngOnInit() {
        // if the user has a review in progress, navigate them to it
        if (this.globalState.currentReviewId) {
            this.router.navigate(['./review', this.globalState.currentReviewId]);
        }
    }
}
