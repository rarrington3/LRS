export let LENDER_PROVIDERS = [];

// NOTE: expose any services from this module using the following pattern:
//      export SomeService from './SomeService';
//      LENDER_PROVIDERS.push(exports.SomeService);


export LenderService from './LenderService';
LENDER_PROVIDERS.push(exports.LenderService);