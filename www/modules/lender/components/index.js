export let LENDER_COMPONENTS = [];

// NOTE: expose any components from this module using the following pattern:
//      export SomeComponent from './SomeComponent';
//      LENDER_COMPONENTS.push(exports.SomeComponent);

export LenderWorkload from './LenderWorkload';
LENDER_COMPONENTS.push(exports.LenderWorkload);


export LenderBatchesSidebar from './lenderBatchesSidebar';
LENDER_COMPONENTS.push(exports.LenderBatchesSidebar);

export LenderBatchesWorkload from './LenderBatchesWorkload';
LENDER_COMPONENTS.push(exports.LenderBatchesWorkload);

export LenderReviewsCompleted from './LenderReviewsCompleted';
LENDER_COMPONENTS.push(exports.LenderReviewsCompleted);


export LenderReviewsCompletedContainer from './LenderReviewsCompletedContainer';
LENDER_COMPONENTS.push(exports.LenderReviewsCompletedContainer);

export LenderReviewsCompletedSidebar from './LenderReviewsCompletedSidebar';
LENDER_COMPONENTS.push(exports.LenderReviewsCompletedSidebar);

export LenderCompletedBatchesSidebar from './LenderCompletedBatchesSidebar';
LENDER_COMPONENTS.push(exports.LenderCompletedBatchesSidebar);

export LenderCompletedBatches from './LenderCompletedBatches';
LENDER_COMPONENTS.push(exports.LenderCompletedBatches);