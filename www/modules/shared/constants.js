// This file was generated from the exports scaffold
// Copyright 2016

export const STATUS_CODE = {
    ACTIVE: 'A',
    INACTIVE: 'I',
    TERMINATED: 'T'
};

export const REVIEW_STATUS = {
    AWAITING_ASSIGNMENT: 'awaiting assignment',
    ASSIGNED: 'assigned',
    UNDER_REVIEW:	'under review',
    PENDING_LENDER_RESPONSE: 'pending lender response',
    COMPLETED: 'completed',
    EXCEPTION: 'exception',
    CANCELLED: 'cancelled'
};

export const REVIEW_LEVEL_TYPE = {
    INITIAL: 'initial',
    ESCALATION: 'escalation',
    INDEMNIFICATION: 'indemnification',
    FORCED_INDEMNIFICATION: 'forced indemnification',
    HQ_ESCALATION: 'hq escalation',
    MITIGATION: 'mitigation',
    BINDER_DELIVERY: 'binder delivery'
};

export const REVIEW_LEVEL_STATUS = {
    AWAITING_ASSIGNMENT: 'awaiting assignment',
    ASSIGNED: 'assigned',
    IN_PROGRESS: 'in progress',
    PENDING_BATCH_REVIEW: 'pending batch review',
    COMPLETED: 'completed',
    EXCEPTION: 'exception',
    CANCELLED: 'cancelled',
    VETTING_COMPLETED: 'vetting completed',
    VETTING_COMPLETED_EXCEPTION: 'vetting exception',
    PENDING_VETTING: 'pending vetting'
};

export const EXCEPTION_TYPE = {
    LOAN_SELECTION_DISTRIBUTION: 'LOAN_SELECTION_DISTRIBUTION',
    BATCH_DISTRIBUTION: 'BATCH_DISTRIBUTION',
    BATCH_ASSIGNMENT: 'BATCH_ASSIGNMENT',
    REVIEW_LEVEL_ASSIGNMENT: 'REVIEW_LEVEL_ASSIGNMENT',
    BINDER_REQUEST_PAST_DUE: 'BINDER_REQUEST_PAST_DUE',
    BINDER_REQUEST_ERROR: 'BINDER_REQUEST_ERROR',
    HQ_ESCALATION: 'HQ_ESCALATION',
    VETTING_ACKNOWLEDGEMENT: 'VETTING_ACKNOWLEDGEMENT'
};

export const SERVER_ERROR = {
    BAD_REQUEST: 400,
    UNAUTHORIZED: 401,
    FORBIDDEN: 403,
    NOT_FOUND: 404,
    CONFLICT: 409,
    GONE: 410,
    EXCEPTION: 500
};

export const QA_TREE_OPERATOR = {
    GREATER_THAN: {
        code: '>',
        text: 'Greater than'
    },
    LOWER_THAN: {
        code: '<',
        text: 'Lower than'
    },
    EQUAL: {
        code: '=',
        text: 'Is'
    },
    NOT_EQUAL: {
        code: '!=',
        text: 'Is not'
    },
    IN: {
        code: 'in',
        text: 'Includes one of'
    },
    NOT_IN: {
        code: 'not in',
        text: 'Not includes one of'
    },
    INTERSECTS: {
        code: 'intersects',
        text: 'Intersects'
    },
    IS_PRESENT: {
        code: 'is present',
        text: 'Is present'
    },
    NOT_PRESENT: {
        code: 'not present',
        text: 'Not present'
    }
};

export const QA_TREE_OUTCOME = {
    EXPAND: {
        code: 'EXPAND',
        text: 'Expand Question Tree'
    },
    FINDING: {
        code: 'FINDING',
        text: 'Finding'
    },
    NOTHING: {
        code: 'NOTHING',
        text: 'Nothing'
    }
};

export const QA_TREE_ANSWER_TYPE = {
    SINGLE: {
        code: 'Single',
        text: 'Single'
    },
    MULTIPLE: {
        code: 'Single',
        text: 'Multiple'
    }
};

/**
 *  In certain views there are specific assignment types required
 *  in order for a user to perform certain actions.
 *
 *  Eg. Manual Case Selection or Lender Monitoring requires that assigned batch owners (reviewers)
 *      must have a review type of 'Operation Reviews' in their allowedReviewTypes property collection
 * */

export const REVIEWER_REVIEW_TYPES = {
    UNDERWRITING: 'U',
    SERVICING: 'S',
    OPERATIONAL: 'O',
    COMPREHENSIVE: 'C'
};


/**
 * The list of approved selection reasons when user is performing a manual case selection
 * */
export const CASE_SELECTION_REASONS = {
    TEST_CASE: 'TEST_CASE',
    FHA_MANUAL: 'FHA_MANUAL',
    LENDER_SELF_REPORT: 'LENDER_SELF_REPORT',
    OIG_AUDIT: 'OIG_AUDIT',
    REVIEW_LOCATION_QC: 'REVIEW_LOCATION_QC',
    LENDER_MONITORING: 'LENDER_MONITORING'
};

/**
 * Common operation
 * @type {{ADD: string, UPDATE: string, REMOVE: string}}
 */
export const COMMON_OPERATION = {
    ADD: 'ADD',
    UPDATE: 'UPDATE',
    REMOVE: 'REMOVE'
};

/**
 * Some assignment types in Staff Management view can only be adjusted by HQ Admin
 * Eg. Review Levels Indemnification and Forced Indemnification
 * */
export const HQ_ADMIN_ONLY_ASSIGNMENT_TYPES = {
    'INDEM': 'INDM',
    'FORCED_INDEM': 'FRCE'
};

export const REVIEW_TYPES = {
    UNDERWRITING: 'underwriting',
    SERVICING: 'servicing',
    OPERATIONAL: 'operational',
    COMPREHENSIVE: 'comprehensive'
};
