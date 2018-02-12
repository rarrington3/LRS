// This file was generated from the component scaffold
// Copyright 2016

import {Component, Input, ElementRef} from '@angular/core';
import template from './LRSPDFViewer.html';
import styles from './LRSPDFViewer.less';
import {PDFJS} from '../../../libs/pdfjs-dist/build/pdf.combined.js';
import $ from 'jquery';


@Component({
    selector: 'lrs-pdf-viewer',
    template: template,
    styles: [styles]
})
/**
 * @see https://angular.io/docs/ts/latest/api/core/Component-decorator.html
 * @example
 * <lrs-pdf-viewer[source]="url"></lrs-pdf-viewer>
 */
export default class LRSPDFViewer {

    _source:String = null;
    $container:Object = null;

    constructor(elementRef: ElementRef) {
        this.elementRef = elementRef;
    }

    @Input()
    set source(source:String) {
        this._source = source;
        this._loadPDF(this._source);
    }

    get source() {
        return this._source;
    }

    _loadPDF(source) {
        this.$container = $(this.elementRef.nativeElement).find('.container-inner');
        this._reset();
        if (source && source.url) {
            PDFJS.getDocument(source).then((pdf) => {
                if (pdf.numPages) {
                    for (let page = 1; page <= pdf.numPages; page++) {
                        this._renderPage(page, pdf);
                    }
                }
            });
        }
    }

    _reset() {
        this.$container.empty();
    }

    _renderPage(page:Number, pdf) {

        pdf.getPage(page).then((page) => {
            let scale = 1.0;
            let $pdfContainer = this.$container;
            let viewport = page.getViewport(scale);
            let canvas =  document.createElement('canvas');
            $pdfContainer.append(canvas);

            let context = canvas.getContext('2d');
            canvas.height = viewport.height;
            canvas.width = viewport.width;

            let renderContext = {
                canvasContext: context,
                viewport: viewport
            };

            page.render(renderContext);
        });

    }
}
