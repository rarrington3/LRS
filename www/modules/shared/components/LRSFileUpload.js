// This file was generated from the component scaffold
// Copyright 2016

import {Component, Input, Output, ElementRef, EventEmitter} from '@angular/core';
import template from './LRSFileUpload.html';
import styles from './LRSFileUpload.less';
import $ from 'jquery';
import _ from 'lodash';
require('../../../libs/jquery-file-upload/js/jquery.fileupload.js');

// This file is required for browsers that don't support ajax POST request.
require('../../../libs/jquery-file-upload/js/jquery.iframe-transport.js');

@Component({
    selector: 'lrs-file-upload',
    template: template,
    styles: [styles]
})
/**
 * @see https://github.com/blueimp/jQuery-File-Upload/wiki/API
 * @example
 * 	<lrs-file-upload [dataURL]="'blah/blah.com'">
 *   <!-- Provide a custom upload button and label with an optional icon here -->
 *           <button type="button" class="btn clear-btn lrs-file-input-button">
 *             <!--<i class="glyphicon glyphicon-plus"></i>-->
 *             <span class="lrs-file-input-button-label">Choose File...</span>
 *       </button>
 * </lrs-file-upload>
 */
export default class LRSFileUpload {

    _elementRef:ElementRef;
    _dataURL:String = '';

    @Output() onFileAdd = new EventEmitter();
    @Output() onFileDone = new EventEmitter();
    @Output() onFileFail = new EventEmitter();
    @Output() onFileStop = new EventEmitter();
    @Output() onFileProgress = new EventEmitter();

    constructor(elementRef: ElementRef) {
        this._elementRef = elementRef;

    }

    @Input()
    set dataURL(url:String) {
        this._dataURL = url;
        if (url && url.length) {
            _.defer(() => {
                this._setup();
            });
        }
    }

    get dataURL() {
        return this._dataURL;
    }


    _setup() {
        // Hide the file input inside the custom upload button.
        let $input = $(this._elementRef.nativeElement).find('.lrs-file-upload-input');
        let $customUploadButton = $input.siblings();
        if ($customUploadButton.length && !$customUploadButton.find('.lrs-file-upload-input').length) {
            $input.appendTo($customUploadButton);
        }

        // Wire the jquery upload plugin to our custom UI and emit events to the world.
        $input.fileupload({
            options: {
                singleFileUploads: true,
                sequentialUploads: true,
                dataType: 'json',
                maxChunkSize: 1000000 // 1MB
            },
            done: (e, data) => {
                console.log('LRS File Upload complete: ', this._getFileName(data));
                this.onFileDone.emit({event: e, result: data.result});

            },
            add: (e, data) => {
                let acceptFileTypes =  /(.|\/)(pdf|jpe?g|tiff)$/i;
                let file = data.files[0];
                let type = file.type;
                //Explicitly set the url since the plugin does not dynamically update it
                data.url = this._dataURL;
                if (!acceptFileTypes.test(type)) {

                    console.log(`LRS File Upload Failed for ${this._getFileName(data)} with status: `, 'invalid file type: ' + type);
                    e.type = 'invalid';
                    this.onFileFail.emit({event: e, file: file});
                } else {
                    this.onFileAdd.emit({event: e, file: file});
                    data.process().done(function () {
                        data.submit();
                    });
                }
            },
            fail: (e, data) => {
                console.log(`LRS File Upload Failed for ${this._getFileName(data)} with status: `, data.textStatus);
                this.onFileFail.emit({event: e, file:data.files[0]});

            },
            stop: (e) => {
                console.log('LRS File Upload stopped: ', this.dataURL);
                this.onFileStop.emit(e);

            },
            progress: (e, data) => {
                let progress = parseInt(data.loaded / data.total * 100, 10);
                console.log(`LRS File Upload ${this._getFileName(data)}  progress: `, progress);
                this.onFileProgress.emit(progress);
            }
        });

        $input.fileupload('option', {
            xhrFields: {
                withCredentials: true
            }});
    }

    _getFileName(data:Object = {}) {
        return (data.files && data.files.length) ? data.files[0].name : null;
    }

}
