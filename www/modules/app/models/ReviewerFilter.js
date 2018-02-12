export class ReviewerFilter {

    /**
     * The location id
     */
    locationId: string = '';

    /**
     * The review type id
     */
    reviewTypeId: string = '';

    /**
     * The loan type id
     */
    loanTypeId: string = '';

    /**
     * The selection reasons id
     */
    selectionReasonId: string = '';

    /**
     * The review level id
     */
    reviewLevelId: string = '';

    /**
     * The reviewer ids
     */
    reviewerIds: Array = [];

    /**
     * The status code
     * @see STATUS_CODE
     */
    statusCode: string = '';
}
