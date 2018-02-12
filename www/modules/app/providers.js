import AppExceptionHandler from './exceptionHandler';
import LrsApiV1 from '../api/lrs.api.v1.generated';
import {
    LocationStrategy,
    HashLocationStrategy
} from '@angular/common';
import {ErrorHandler} from '@angular/core';

/**
 * Default list of providers that can be used for bootstrap. The contents
 * of this file are automatically modified during scaffolding of services
 * for convenience to auto register them.
 */
let DEFAULT_PROVIDERS = [
    LrsApiV1,
    {provide: ErrorHandler, useClass: AppExceptionHandler},
    //TODO: remove below if you want HTML5 base strategy (default, requires server configuration)
    {provide: LocationStrategy, useClass: HashLocationStrategy}
];

import {APP_PROVIDERS} from './services';
DEFAULT_PROVIDERS.push(APP_PROVIDERS);

export default DEFAULT_PROVIDERS;