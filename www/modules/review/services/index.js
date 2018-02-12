export let REVIEW_PROVIDERS = [];

export GlobalStateSvc from './GlobalStateSvc';
REVIEW_PROVIDERS.push(exports.GlobalStateSvc);

export ReviewService from './ReviewService';
REVIEW_PROVIDERS.push(exports.ReviewService);

export FindingsService from './FindingsService';
REVIEW_PROVIDERS.push(exports.FindingsService);

export VettingService from './VettingService';
REVIEW_PROVIDERS.push(exports.VettingService);

export QCService from './QCService';
REVIEW_PROVIDERS.push(exports.QCService);

export DevAidService from './DevAidService';
REVIEW_PROVIDERS.push(exports.DevAidService);
