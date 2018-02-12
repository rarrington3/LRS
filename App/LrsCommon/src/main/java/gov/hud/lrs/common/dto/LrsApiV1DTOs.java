// THIS FILE IS AUTO-GENERATED. DO NOT MODIFY.  RUN 'grunt server:apidesigner' TO EDIT THE SPECIFICATION
package gov.hud.lrs.common.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Date;
import java.math.BigDecimal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


/**
 * This class contains all DTOs for the HUD Loan Review System Web Services API.
 * Due to the way our RAML-->code generator works, all DTOs must be output to a single file.
 * Java requires 1 class per file so we emit a single class within which there is an inner class per-DTO
 */
@SuppressWarnings("serial")
public class LrsApiV1DTOs {
    // This class is not intended to be used directly, only the inner classes it contains.
    private LrsApiV1DTOs() {}

// TODO: emit java enums

    // Binder DTO
    public static class BinderDTO implements Serializable {

        /**
         * The identifier of the binder.
         */
        private String binderId;

        /**
         * The case number for the loan.
         */
        private String caseNumber;

        /**
         * The name of the borrower associated with this loan
         */
        private String borrowerName;

        /**
         * The name of the lender associated with this loan
         */
        private String lenderName;

        /**
         * The street address for the property associated with this loan
         */
        private String propertyStreetAddress;

        /**
         * Where the binder request came from (e.g. &#x27;Records Center&#x27;, &#x27;Review Location&#x27;, &#x27;Lender&#x27;)
         */
        private String requestedFrom;

        /**
         * The date when the binder request for this loan was sent to the lender
         */
        private Date requestedDate;

        /**
         * The due date when the binder should be received from the lender
         */
        private Date dueDate;

        /**
         * The date that the binder was sent to the lender for review
         */
        private Date sentDate;

        /**
         * The date that the binder was confirmed received from the lender
         */
        private Date receivedDate;

        /**
         * The person the binder is assigned to after it is received from the lender
         */
        private String assignedTo;

        /**
         * The review location id for binder
         */
        private String reviewLocationId;

        /**
         * The review location name for binder
         */
        private String locationName;

        /**
         * Indicates if this binder is electronic.
         */
        private Boolean isElectronic;

        /**
         * Review Type this binder
         */
        private String reviewType;

        /**
         * The binder&#x27;s status code (e.g. &#x27;REQUESTED&#x27;, &#x27;RECEIVED&#x27;, &#x27;CANCELLED&#x27;, or &#x27;EXCEPTION&#x27;)
         */
        private String statusCode;


        /**
         * Creates a new instance of BinderDTO
         */
        public BinderDTO() {}

        /**
         * Get The identifier of the binder.
         * @return The identifier of the binder.
         */
        public String getBinderId() {
            return binderId;
        }

        /**
         * Set The identifier of the binder.
         * @param binderId
         *            The identifier of the binder.
         */
        public void setBinderId(String binderId) {
            this.binderId = binderId;
        }

        /**
         * Get The case number for the loan.
         * @return The case number for the loan.
         */
        public String getCaseNumber() {
            return caseNumber;
        }

        /**
         * Set The case number for the loan.
         * @param caseNumber
         *            The case number for the loan.
         */
        public void setCaseNumber(String caseNumber) {
            this.caseNumber = caseNumber;
        }

        /**
         * Get The name of the borrower associated with this loan
         * @return The name of the borrower associated with this loan
         */
        public String getBorrowerName() {
            return borrowerName;
        }

        /**
         * Set The name of the borrower associated with this loan
         * @param borrowerName
         *            The name of the borrower associated with this loan
         */
        public void setBorrowerName(String borrowerName) {
            this.borrowerName = borrowerName;
        }

        /**
         * Get The name of the lender associated with this loan
         * @return The name of the lender associated with this loan
         */
        public String getLenderName() {
            return lenderName;
        }

        /**
         * Set The name of the lender associated with this loan
         * @param lenderName
         *            The name of the lender associated with this loan
         */
        public void setLenderName(String lenderName) {
            this.lenderName = lenderName;
        }

        /**
         * Get The street address for the property associated with this loan
         * @return The street address for the property associated with this loan
         */
        public String getPropertyStreetAddress() {
            return propertyStreetAddress;
        }

        /**
         * Set The street address for the property associated with this loan
         * @param propertyStreetAddress
         *            The street address for the property associated with this loan
         */
        public void setPropertyStreetAddress(String propertyStreetAddress) {
            this.propertyStreetAddress = propertyStreetAddress;
        }

        /**
         * Get Where the binder request came from (e.g. &#x27;Records Center&#x27;, &#x27;Review Location&#x27;, &#x27;Lender&#x27;)
         * @return Where the binder request came from (e.g. &#x27;Records Center&#x27;, &#x27;Review Location&#x27;, &#x27;Lender&#x27;)
         */
        public String getRequestedFrom() {
            return requestedFrom;
        }

        /**
         * Set Where the binder request came from (e.g. &#x27;Records Center&#x27;, &#x27;Review Location&#x27;, &#x27;Lender&#x27;)
         * @param requestedFrom
         *            Where the binder request came from (e.g. &#x27;Records Center&#x27;, &#x27;Review Location&#x27;, &#x27;Lender&#x27;)
         */
        public void setRequestedFrom(String requestedFrom) {
            this.requestedFrom = requestedFrom;
        }

        /**
         * Get The date when the binder request for this loan was sent to the lender
         * @return The date when the binder request for this loan was sent to the lender
         */
        public Date getRequestedDate() {
            return requestedDate;
        }

        /**
         * Set The date when the binder request for this loan was sent to the lender
         * @param requestedDate
         *            The date when the binder request for this loan was sent to the lender
         */
        public void setRequestedDate(Date requestedDate) {
            this.requestedDate = requestedDate;
        }

        /**
         * Get The due date when the binder should be received from the lender
         * @return The due date when the binder should be received from the lender
         */
        public Date getDueDate() {
            return dueDate;
        }

        /**
         * Set The due date when the binder should be received from the lender
         * @param dueDate
         *            The due date when the binder should be received from the lender
         */
        public void setDueDate(Date dueDate) {
            this.dueDate = dueDate;
        }

        /**
         * Get The date that the binder was sent to the lender for review
         * @return The date that the binder was sent to the lender for review
         */
        public Date getSentDate() {
            return sentDate;
        }

        /**
         * Set The date that the binder was sent to the lender for review
         * @param sentDate
         *            The date that the binder was sent to the lender for review
         */
        public void setSentDate(Date sentDate) {
            this.sentDate = sentDate;
        }

        /**
         * Get The date that the binder was confirmed received from the lender
         * @return The date that the binder was confirmed received from the lender
         */
        public Date getReceivedDate() {
            return receivedDate;
        }

        /**
         * Set The date that the binder was confirmed received from the lender
         * @param receivedDate
         *            The date that the binder was confirmed received from the lender
         */
        public void setReceivedDate(Date receivedDate) {
            this.receivedDate = receivedDate;
        }

        /**
         * Get The person the binder is assigned to after it is received from the lender
         * @return The person the binder is assigned to after it is received from the lender
         */
        public String getAssignedTo() {
            return assignedTo;
        }

        /**
         * Set The person the binder is assigned to after it is received from the lender
         * @param assignedTo
         *            The person the binder is assigned to after it is received from the lender
         */
        public void setAssignedTo(String assignedTo) {
            this.assignedTo = assignedTo;
        }

        /**
         * Get The review location id for binder
         * @return The review location id for binder
         */
        public String getReviewLocationId() {
            return reviewLocationId;
        }

        /**
         * Set The review location id for binder
         * @param reviewLocationId
         *            The review location id for binder
         */
        public void setReviewLocationId(String reviewLocationId) {
            this.reviewLocationId = reviewLocationId;
        }

        /**
         * Get The review location name for binder
         * @return The review location name for binder
         */
        public String getLocationName() {
            return locationName;
        }

        /**
         * Set The review location name for binder
         * @param locationName
         *            The review location name for binder
         */
        public void setLocationName(String locationName) {
            this.locationName = locationName;
        }

        /**
         * Get Indicates if this binder is electronic.
         * @return Indicates if this binder is electronic.
         */
        public Boolean getIsElectronic() {
            return isElectronic;
        }

        /**
         * Set Indicates if this binder is electronic.
         * @param isElectronic
         *            Indicates if this binder is electronic.
         */
        public void setIsElectronic(Boolean isElectronic) {
            this.isElectronic = isElectronic;
        }

        /**
         * Get Review Type this binder
         * @return Review Type this binder
         */
        public String getReviewType() {
            return reviewType;
        }

        /**
         * Set Review Type this binder
         * @param reviewType
         *            Review Type this binder
         */
        public void setReviewType(String reviewType) {
            this.reviewType = reviewType;
        }

        /**
         * Get The binder&#x27;s status code (e.g. &#x27;REQUESTED&#x27;, &#x27;RECEIVED&#x27;, &#x27;CANCELLED&#x27;, or &#x27;EXCEPTION&#x27;)
         * @return The binder&#x27;s status code (e.g. &#x27;REQUESTED&#x27;, &#x27;RECEIVED&#x27;, &#x27;CANCELLED&#x27;, or &#x27;EXCEPTION&#x27;)
         */
        public String getStatusCode() {
            return statusCode;
        }

        /**
         * Set The binder&#x27;s status code (e.g. &#x27;REQUESTED&#x27;, &#x27;RECEIVED&#x27;, &#x27;CANCELLED&#x27;, or &#x27;EXCEPTION&#x27;)
         * @param statusCode
         *            The binder&#x27;s status code (e.g. &#x27;REQUESTED&#x27;, &#x27;RECEIVED&#x27;, &#x27;CANCELLED&#x27;, or &#x27;EXCEPTION&#x27;)
         */
        public void setStatusCode(String statusCode) {
            this.statusCode = statusCode;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "BinderDTO [ " + 
                "binderId = " + binderId + ", " + 
                "caseNumber = " + caseNumber + ", " + 
                "borrowerName = " + borrowerName + ", " + 
                "lenderName = " + lenderName + ", " + 
                "propertyStreetAddress = " + propertyStreetAddress + ", " + 
                "requestedFrom = " + requestedFrom + ", " + 
                "requestedDate = " + requestedDate + ", " + 
                "dueDate = " + dueDate + ", " + 
                "sentDate = " + sentDate + ", " + 
                "receivedDate = " + receivedDate + ", " + 
                "assignedTo = " + assignedTo + ", " + 
                "reviewLocationId = " + reviewLocationId + ", " + 
                "locationName = " + locationName + ", " + 
                "isElectronic = " + isElectronic + ", " + 
                "reviewType = " + reviewType + ", " + 
                "statusCode = " + statusCode + 
                " ]";
        }
    }

    // Config DTO
    public static class ConfigDTO implements Serializable {

        /**
         * Determines if the application is running in dev mode.
         */
        private Boolean lrsUiDev;

        /**
         * Url for microstrategy
         */
        private String microstrategyUrl;

        /**
         * Url for accessing ecase binder.  {caseNumber} in the string is a placeholder for inserting an actual case number
         */
        private String electronicCaseBinder;

        /**
         * Url for eAppraisals.  {caseNumber} in the string is a placeholder for inserting an actual case number
         */
        private String electronicAppraisal;

        /**
         * Url for accessing TOTAL scorecard emulator.  {caseNumber} in the string is a placeholder for inserting an actual case number
         */
        private String totalScorecardEmulator;

        /**
         * Url for accessing TOTAL scorecard emulator comparison.  {caseNumber} in the string is a placeholder for inserting an actual case number
         */
        private String totalScorecardEmulatorComparison;


        /**
         * Creates a new instance of ConfigDTO
         */
        public ConfigDTO() {}

        /**
         * Get Determines if the application is running in dev mode.
         * @return Determines if the application is running in dev mode.
         */
        public Boolean getLrsUiDev() {
            return lrsUiDev;
        }

        /**
         * Set Determines if the application is running in dev mode.
         * @param lrsUiDev
         *            Determines if the application is running in dev mode.
         */
        public void setLrsUiDev(Boolean lrsUiDev) {
            this.lrsUiDev = lrsUiDev;
        }

        /**
         * Get Url for microstrategy
         * @return Url for microstrategy
         */
        public String getMicrostrategyUrl() {
            return microstrategyUrl;
        }

        /**
         * Set Url for microstrategy
         * @param microstrategyUrl
         *            Url for microstrategy
         */
        public void setMicrostrategyUrl(String microstrategyUrl) {
            this.microstrategyUrl = microstrategyUrl;
        }

        /**
         * Get Url for accessing ecase binder.  {caseNumber} in the string is a placeholder for inserting an actual case number
         * @return Url for accessing ecase binder.  {caseNumber} in the string is a placeholder for inserting an actual case number
         */
        public String getElectronicCaseBinder() {
            return electronicCaseBinder;
        }

        /**
         * Set Url for accessing ecase binder.  {caseNumber} in the string is a placeholder for inserting an actual case number
         * @param electronicCaseBinder
         *            Url for accessing ecase binder.  {caseNumber} in the string is a placeholder for inserting an actual case number
         */
        public void setElectronicCaseBinder(String electronicCaseBinder) {
            this.electronicCaseBinder = electronicCaseBinder;
        }

        /**
         * Get Url for eAppraisals.  {caseNumber} in the string is a placeholder for inserting an actual case number
         * @return Url for eAppraisals.  {caseNumber} in the string is a placeholder for inserting an actual case number
         */
        public String getElectronicAppraisal() {
            return electronicAppraisal;
        }

        /**
         * Set Url for eAppraisals.  {caseNumber} in the string is a placeholder for inserting an actual case number
         * @param electronicAppraisal
         *            Url for eAppraisals.  {caseNumber} in the string is a placeholder for inserting an actual case number
         */
        public void setElectronicAppraisal(String electronicAppraisal) {
            this.electronicAppraisal = electronicAppraisal;
        }

        /**
         * Get Url for accessing TOTAL scorecard emulator.  {caseNumber} in the string is a placeholder for inserting an actual case number
         * @return Url for accessing TOTAL scorecard emulator.  {caseNumber} in the string is a placeholder for inserting an actual case number
         */
        public String getTotalScorecardEmulator() {
            return totalScorecardEmulator;
        }

        /**
         * Set Url for accessing TOTAL scorecard emulator.  {caseNumber} in the string is a placeholder for inserting an actual case number
         * @param totalScorecardEmulator
         *            Url for accessing TOTAL scorecard emulator.  {caseNumber} in the string is a placeholder for inserting an actual case number
         */
        public void setTotalScorecardEmulator(String totalScorecardEmulator) {
            this.totalScorecardEmulator = totalScorecardEmulator;
        }

        /**
         * Get Url for accessing TOTAL scorecard emulator comparison.  {caseNumber} in the string is a placeholder for inserting an actual case number
         * @return Url for accessing TOTAL scorecard emulator comparison.  {caseNumber} in the string is a placeholder for inserting an actual case number
         */
        public String getTotalScorecardEmulatorComparison() {
            return totalScorecardEmulatorComparison;
        }

        /**
         * Set Url for accessing TOTAL scorecard emulator comparison.  {caseNumber} in the string is a placeholder for inserting an actual case number
         * @param totalScorecardEmulatorComparison
         *            Url for accessing TOTAL scorecard emulator comparison.  {caseNumber} in the string is a placeholder for inserting an actual case number
         */
        public void setTotalScorecardEmulatorComparison(String totalScorecardEmulatorComparison) {
            this.totalScorecardEmulatorComparison = totalScorecardEmulatorComparison;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "ConfigDTO [ " + 
                "lrsUiDev = " + lrsUiDev + ", " + 
                "microstrategyUrl = " + microstrategyUrl + ", " + 
                "electronicCaseBinder = " + electronicCaseBinder + ", " + 
                "electronicAppraisal = " + electronicAppraisal + ", " + 
                "totalScorecardEmulator = " + totalScorecardEmulator + ", " + 
                "totalScorecardEmulatorComparison = " + totalScorecardEmulatorComparison + 
                " ]";
        }
    }

    // Batch DTO
    public static class BatchDTO implements Serializable {

        /**
         * The unique batch ID (GUID) that can be used to update the batch
         */
        private String batchId;

        /**
         * The human readable batch number to be displayed in the UI
         */
        private String batchNumber;

        /**
         * The name of the lender associated with this batch
         */
        private String lenderName;

        /**
         * The ID of the lender associated with this batch
         */
        private String lenderId;

        /**
         * Short name of the business rule that triggered this batch.
         */
        private String selectionReason;

        /**
         * Flag indicating whether or not an operational review exists for this batch.
         */
        private ReviewDTO operationalReview;

        /**
         * Flag indicating whether or not an operational review has been requested for this batch.
         */
        private Boolean requestOperationalReview;

        /**
         * Review type of all reviews in this batch, except for the operational review if any
         */
        private String reviewType;

        /**
         * The level of review being performed.  For example: &#x27;Initial&#x27;, &#x27;Mitigation 1&#x27;
         */
        private String reviewLevel;

        /**
         * Op doc due date
         */
        private Date operationalDocumentsDueDate;

        /**
         * A collection of files that are uploaded for the operational review
         */
        private List<DocumentDTO> operationalDocuments;

        /**
         * A flag to trigger a request for operational documents.
         */
        private Boolean requestOperationalDocuments;

        /**
         * An optional secondary ID to assign to this batch.
         */
        private String secondaryId;

        /**
         * Additional guidance for the operational review.
         */
        private String operationalReviewGuidance;

        /**
         * ID of the reviewer who will be the owner of this batch.
         */
        private String batchOwner;

        /**
         * A collection of reviewer IDs for those assigned to this batch.
         */
        private List<String> batchTeamMembers;

        /**
         * The collection of review objects that belong to this batch once created
         */
        private List<ReviewDTO> reviews;

        /**
         * The number of loan selection cases that belong to this batch (can be used before reviews created)
         */
        private BigDecimal caseCount;

        /**
         * The date when the batch was sent to the lender
         */
        private Date lenderDateReceived;

        /**
         * The date when all batch responses are due from the lender
         */
        private Date lenderDateDue;

        /**
         * The current status of the batch, ex:  &#x27;Selected&#x27;, &#x27;Cancelled&#x27;, &#x27;Assigned&#x27;, &#x27;Pending Lender Response&#x27;, &#x27;Completed&#x27;, &#x27;Under Batch Review&#x27;, &#x27;Under Review&#x27;, &#x27;Distributed&#x27;
         */
        private String status;

        /**
         * The date this batch was created
         */
        private Date creationDate;

        /**
         * Iteration number for the current batch
         */
        private String iteration;

        /**
         * Information regarding loan selections that are not yet reviews so that the user can see all cases in the batch at all times.
         */
        private List<CaseActivityDTO> outstandingCaseActivity;


        /**
         * Creates a new instance of BatchDTO
         */
        public BatchDTO() {}

        /**
         * Get The unique batch ID (GUID) that can be used to update the batch
         * @return The unique batch ID (GUID) that can be used to update the batch
         */
        public String getBatchId() {
            return batchId;
        }

        /**
         * Set The unique batch ID (GUID) that can be used to update the batch
         * @param batchId
         *            The unique batch ID (GUID) that can be used to update the batch
         */
        public void setBatchId(String batchId) {
            this.batchId = batchId;
        }

        /**
         * Get The human readable batch number to be displayed in the UI
         * @return The human readable batch number to be displayed in the UI
         */
        public String getBatchNumber() {
            return batchNumber;
        }

        /**
         * Set The human readable batch number to be displayed in the UI
         * @param batchNumber
         *            The human readable batch number to be displayed in the UI
         */
        public void setBatchNumber(String batchNumber) {
            this.batchNumber = batchNumber;
        }

        /**
         * Get The name of the lender associated with this batch
         * @return The name of the lender associated with this batch
         */
        public String getLenderName() {
            return lenderName;
        }

        /**
         * Set The name of the lender associated with this batch
         * @param lenderName
         *            The name of the lender associated with this batch
         */
        public void setLenderName(String lenderName) {
            this.lenderName = lenderName;
        }

        /**
         * Get The ID of the lender associated with this batch
         * @return The ID of the lender associated with this batch
         */
        public String getLenderId() {
            return lenderId;
        }

        /**
         * Set The ID of the lender associated with this batch
         * @param lenderId
         *            The ID of the lender associated with this batch
         */
        public void setLenderId(String lenderId) {
            this.lenderId = lenderId;
        }

        /**
         * Get Short name of the business rule that triggered this batch.
         * @return Short name of the business rule that triggered this batch.
         */
        public String getSelectionReason() {
            return selectionReason;
        }

        /**
         * Set Short name of the business rule that triggered this batch.
         * @param selectionReason
         *            Short name of the business rule that triggered this batch.
         */
        public void setSelectionReason(String selectionReason) {
            this.selectionReason = selectionReason;
        }

        /**
         * Get Flag indicating whether or not an operational review exists for this batch.
         * @return Flag indicating whether or not an operational review exists for this batch.
         */
        public ReviewDTO getOperationalReview() {
            return operationalReview;
        }

        /**
         * Set Flag indicating whether or not an operational review exists for this batch.
         * @param operationalReview
         *            Flag indicating whether or not an operational review exists for this batch.
         */
        public void setOperationalReview(ReviewDTO operationalReview) {
            this.operationalReview = operationalReview;
        }

        /**
         * Get Flag indicating whether or not an operational review has been requested for this batch.
         * @return Flag indicating whether or not an operational review has been requested for this batch.
         */
        public Boolean getRequestOperationalReview() {
            return requestOperationalReview;
        }

        /**
         * Set Flag indicating whether or not an operational review has been requested for this batch.
         * @param requestOperationalReview
         *            Flag indicating whether or not an operational review has been requested for this batch.
         */
        public void setRequestOperationalReview(Boolean requestOperationalReview) {
            this.requestOperationalReview = requestOperationalReview;
        }

        /**
         * Get Review type of all reviews in this batch, except for the operational review if any
         * @return Review type of all reviews in this batch, except for the operational review if any
         */
        public String getReviewType() {
            return reviewType;
        }

        /**
         * Set Review type of all reviews in this batch, except for the operational review if any
         * @param reviewType
         *            Review type of all reviews in this batch, except for the operational review if any
         */
        public void setReviewType(String reviewType) {
            this.reviewType = reviewType;
        }

        /**
         * Get The level of review being performed.  For example: &#x27;Initial&#x27;, &#x27;Mitigation 1&#x27;
         * @return The level of review being performed.  For example: &#x27;Initial&#x27;, &#x27;Mitigation 1&#x27;
         */
        public String getReviewLevel() {
            return reviewLevel;
        }

        /**
         * Set The level of review being performed.  For example: &#x27;Initial&#x27;, &#x27;Mitigation 1&#x27;
         * @param reviewLevel
         *            The level of review being performed.  For example: &#x27;Initial&#x27;, &#x27;Mitigation 1&#x27;
         */
        public void setReviewLevel(String reviewLevel) {
            this.reviewLevel = reviewLevel;
        }

        /**
         * Get Op doc due date
         * @return Op doc due date
         */
        public Date getOperationalDocumentsDueDate() {
            return operationalDocumentsDueDate;
        }

        /**
         * Set Op doc due date
         * @param operationalDocumentsDueDate
         *            Op doc due date
         */
        public void setOperationalDocumentsDueDate(Date operationalDocumentsDueDate) {
            this.operationalDocumentsDueDate = operationalDocumentsDueDate;
        }

        /**
         * Get A collection of files that are uploaded for the operational review
         * @return A collection of files that are uploaded for the operational review
         */
        public List<DocumentDTO> getOperationalDocuments() {
            return operationalDocuments;
        }

        /**
         * Set A collection of files that are uploaded for the operational review
         * @param operationalDocuments
         *            A collection of files that are uploaded for the operational review
         */
        public void setOperationalDocuments(List<DocumentDTO> operationalDocuments) {
            this.operationalDocuments = operationalDocuments;
        }

        /**
         * Get A flag to trigger a request for operational documents.
         * @return A flag to trigger a request for operational documents.
         */
        public Boolean getRequestOperationalDocuments() {
            return requestOperationalDocuments;
        }

        /**
         * Set A flag to trigger a request for operational documents.
         * @param requestOperationalDocuments
         *            A flag to trigger a request for operational documents.
         */
        public void setRequestOperationalDocuments(Boolean requestOperationalDocuments) {
            this.requestOperationalDocuments = requestOperationalDocuments;
        }

        /**
         * Get An optional secondary ID to assign to this batch.
         * @return An optional secondary ID to assign to this batch.
         */
        public String getSecondaryId() {
            return secondaryId;
        }

        /**
         * Set An optional secondary ID to assign to this batch.
         * @param secondaryId
         *            An optional secondary ID to assign to this batch.
         */
        public void setSecondaryId(String secondaryId) {
            this.secondaryId = secondaryId;
        }

        /**
         * Get Additional guidance for the operational review.
         * @return Additional guidance for the operational review.
         */
        public String getOperationalReviewGuidance() {
            return operationalReviewGuidance;
        }

        /**
         * Set Additional guidance for the operational review.
         * @param operationalReviewGuidance
         *            Additional guidance for the operational review.
         */
        public void setOperationalReviewGuidance(String operationalReviewGuidance) {
            this.operationalReviewGuidance = operationalReviewGuidance;
        }

        /**
         * Get ID of the reviewer who will be the owner of this batch.
         * @return ID of the reviewer who will be the owner of this batch.
         */
        public String getBatchOwner() {
            return batchOwner;
        }

        /**
         * Set ID of the reviewer who will be the owner of this batch.
         * @param batchOwner
         *            ID of the reviewer who will be the owner of this batch.
         */
        public void setBatchOwner(String batchOwner) {
            this.batchOwner = batchOwner;
        }

        /**
         * Get A collection of reviewer IDs for those assigned to this batch.
         * @return A collection of reviewer IDs for those assigned to this batch.
         */
        public List<String> getBatchTeamMembers() {
            return batchTeamMembers;
        }

        /**
         * Set A collection of reviewer IDs for those assigned to this batch.
         * @param batchTeamMembers
         *            A collection of reviewer IDs for those assigned to this batch.
         */
        public void setBatchTeamMembers(List<String> batchTeamMembers) {
            this.batchTeamMembers = batchTeamMembers;
        }

        /**
         * Get The collection of review objects that belong to this batch once created
         * @return The collection of review objects that belong to this batch once created
         */
        public List<ReviewDTO> getReviews() {
            return reviews;
        }

        /**
         * Set The collection of review objects that belong to this batch once created
         * @param reviews
         *            The collection of review objects that belong to this batch once created
         */
        public void setReviews(List<ReviewDTO> reviews) {
            this.reviews = reviews;
        }

        /**
         * Get The number of loan selection cases that belong to this batch (can be used before reviews created)
         * @return The number of loan selection cases that belong to this batch (can be used before reviews created)
         */
        public BigDecimal getCaseCount() {
            return caseCount;
        }

        /**
         * Set The number of loan selection cases that belong to this batch (can be used before reviews created)
         * @param caseCount
         *            The number of loan selection cases that belong to this batch (can be used before reviews created)
         */
        public void setCaseCount(BigDecimal caseCount) {
            this.caseCount = caseCount;
        }

        /**
         * Get The date when the batch was sent to the lender
         * @return The date when the batch was sent to the lender
         */
        public Date getLenderDateReceived() {
            return lenderDateReceived;
        }

        /**
         * Set The date when the batch was sent to the lender
         * @param lenderDateReceived
         *            The date when the batch was sent to the lender
         */
        public void setLenderDateReceived(Date lenderDateReceived) {
            this.lenderDateReceived = lenderDateReceived;
        }

        /**
         * Get The date when all batch responses are due from the lender
         * @return The date when all batch responses are due from the lender
         */
        public Date getLenderDateDue() {
            return lenderDateDue;
        }

        /**
         * Set The date when all batch responses are due from the lender
         * @param lenderDateDue
         *            The date when all batch responses are due from the lender
         */
        public void setLenderDateDue(Date lenderDateDue) {
            this.lenderDateDue = lenderDateDue;
        }

        /**
         * Get The current status of the batch, ex:  &#x27;Selected&#x27;, &#x27;Cancelled&#x27;, &#x27;Assigned&#x27;, &#x27;Pending Lender Response&#x27;, &#x27;Completed&#x27;, &#x27;Under Batch Review&#x27;, &#x27;Under Review&#x27;, &#x27;Distributed&#x27;
         * @return The current status of the batch, ex:  &#x27;Selected&#x27;, &#x27;Cancelled&#x27;, &#x27;Assigned&#x27;, &#x27;Pending Lender Response&#x27;, &#x27;Completed&#x27;, &#x27;Under Batch Review&#x27;, &#x27;Under Review&#x27;, &#x27;Distributed&#x27;
         */
        public String getStatus() {
            return status;
        }

        /**
         * Set The current status of the batch, ex:  &#x27;Selected&#x27;, &#x27;Cancelled&#x27;, &#x27;Assigned&#x27;, &#x27;Pending Lender Response&#x27;, &#x27;Completed&#x27;, &#x27;Under Batch Review&#x27;, &#x27;Under Review&#x27;, &#x27;Distributed&#x27;
         * @param status
         *            The current status of the batch, ex:  &#x27;Selected&#x27;, &#x27;Cancelled&#x27;, &#x27;Assigned&#x27;, &#x27;Pending Lender Response&#x27;, &#x27;Completed&#x27;, &#x27;Under Batch Review&#x27;, &#x27;Under Review&#x27;, &#x27;Distributed&#x27;
         */
        public void setStatus(String status) {
            this.status = status;
        }

        /**
         * Get The date this batch was created
         * @return The date this batch was created
         */
        public Date getCreationDate() {
            return creationDate;
        }

        /**
         * Set The date this batch was created
         * @param creationDate
         *            The date this batch was created
         */
        public void setCreationDate(Date creationDate) {
            this.creationDate = creationDate;
        }

        /**
         * Get Iteration number for the current batch
         * @return Iteration number for the current batch
         */
        public String getIteration() {
            return iteration;
        }

        /**
         * Set Iteration number for the current batch
         * @param iteration
         *            Iteration number for the current batch
         */
        public void setIteration(String iteration) {
            this.iteration = iteration;
        }

        /**
         * Get Information regarding loan selections that are not yet reviews so that the user can see all cases in the batch at all times.
         * @return Information regarding loan selections that are not yet reviews so that the user can see all cases in the batch at all times.
         */
        public List<CaseActivityDTO> getOutstandingCaseActivity() {
            return outstandingCaseActivity;
        }

        /**
         * Set Information regarding loan selections that are not yet reviews so that the user can see all cases in the batch at all times.
         * @param outstandingCaseActivity
         *            Information regarding loan selections that are not yet reviews so that the user can see all cases in the batch at all times.
         */
        public void setOutstandingCaseActivity(List<CaseActivityDTO> outstandingCaseActivity) {
            this.outstandingCaseActivity = outstandingCaseActivity;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "BatchDTO [ " + 
                "batchId = " + batchId + ", " + 
                "batchNumber = " + batchNumber + ", " + 
                "lenderName = " + lenderName + ", " + 
                "lenderId = " + lenderId + ", " + 
                "selectionReason = " + selectionReason + ", " + 
                "operationalReview = " + operationalReview + ", " + 
                "requestOperationalReview = " + requestOperationalReview + ", " + 
                "reviewType = " + reviewType + ", " + 
                "reviewLevel = " + reviewLevel + ", " + 
                "operationalDocumentsDueDate = " + operationalDocumentsDueDate + ", " + 
                "operationalDocuments = " + operationalDocuments + ", " + 
                "requestOperationalDocuments = " + requestOperationalDocuments + ", " + 
                "secondaryId = " + secondaryId + ", " + 
                "operationalReviewGuidance = " + operationalReviewGuidance + ", " + 
                "batchOwner = " + batchOwner + ", " + 
                "batchTeamMembers = " + batchTeamMembers + ", " + 
                "reviews = " + reviews + ", " + 
                "caseCount = " + caseCount + ", " + 
                "lenderDateReceived = " + lenderDateReceived + ", " + 
                "lenderDateDue = " + lenderDateDue + ", " + 
                "status = " + status + ", " + 
                "creationDate = " + creationDate + ", " + 
                "iteration = " + iteration + ", " + 
                "outstandingCaseActivity = " + outstandingCaseActivity + 
                " ]";
        }
    }

    // CommonDetail DTO
    public static class CommonDetailDTO implements Serializable {

        /**
         * The display text for this detail object.
         */
        private String description;

        /**
         * The unique code for this detail object.
         */
        private String code;


        /**
         * Creates a new instance of CommonDetailDTO
         */
        public CommonDetailDTO() {}

        /**
         * Get The display text for this detail object.
         * @return The display text for this detail object.
         */
        public String getDescription() {
            return description;
        }

        /**
         * Set The display text for this detail object.
         * @param description
         *            The display text for this detail object.
         */
        public void setDescription(String description) {
            this.description = description;
        }

        /**
         * Get The unique code for this detail object.
         * @return The unique code for this detail object.
         */
        public String getCode() {
            return code;
        }

        /**
         * Set The unique code for this detail object.
         * @param code
         *            The unique code for this detail object.
         */
        public void setCode(String code) {
            this.code = code;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "CommonDetailDTO [ " + 
                "description = " + description + ", " + 
                "code = " + code + 
                " ]";
        }
    }

    // OrderedItem DTO
    public static class OrderedItemDTO implements Serializable {

        /**
         * The id for this item.
         */
        private String id;

        /**
         * The unique code for this item.
         */
        private String code;

        /**
         * The display text for this detail object.
         */
        private String description;

        /**
         * The order of the item
         */
        private BigDecimal order;


        /**
         * Creates a new instance of OrderedItemDTO
         */
        public OrderedItemDTO() {}

        /**
         * Get The id for this item.
         * @return The id for this item.
         */
        public String getId() {
            return id;
        }

        /**
         * Set The id for this item.
         * @param id
         *            The id for this item.
         */
        public void setId(String id) {
            this.id = id;
        }

        /**
         * Get The unique code for this item.
         * @return The unique code for this item.
         */
        public String getCode() {
            return code;
        }

        /**
         * Set The unique code for this item.
         * @param code
         *            The unique code for this item.
         */
        public void setCode(String code) {
            this.code = code;
        }

        /**
         * Get The display text for this detail object.
         * @return The display text for this detail object.
         */
        public String getDescription() {
            return description;
        }

        /**
         * Set The display text for this detail object.
         * @param description
         *            The display text for this detail object.
         */
        public void setDescription(String description) {
            this.description = description;
        }

        /**
         * Get The order of the item
         * @return The order of the item
         */
        public BigDecimal getOrder() {
            return order;
        }

        /**
         * Set The order of the item
         * @param order
         *            The order of the item
         */
        public void setOrder(BigDecimal order) {
            this.order = order;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "OrderedItemDTO [ " + 
                "id = " + id + ", " + 
                "code = " + code + ", " + 
                "description = " + description + ", " + 
                "order = " + order + 
                " ]";
        }
    }

    // OptionsItem DTO
    public static class OptionsItemDTO implements Serializable {

        /**
         * The unique code for this item.
         */
        private String code;

        /**
         * The description for this item.
         */
        private String description;

        /**
         * The multiple options
         */
        private List<String> options;


        /**
         * Creates a new instance of OptionsItemDTO
         */
        public OptionsItemDTO() {}

        /**
         * Get The unique code for this item.
         * @return The unique code for this item.
         */
        public String getCode() {
            return code;
        }

        /**
         * Set The unique code for this item.
         * @param code
         *            The unique code for this item.
         */
        public void setCode(String code) {
            this.code = code;
        }

        /**
         * Get The description for this item.
         * @return The description for this item.
         */
        public String getDescription() {
            return description;
        }

        /**
         * Set The description for this item.
         * @param description
         *            The description for this item.
         */
        public void setDescription(String description) {
            this.description = description;
        }

        /**
         * Get The multiple options
         * @return The multiple options
         */
        public List<String> getOptions() {
            return options;
        }

        /**
         * Set The multiple options
         * @param options
         *            The multiple options
         */
        public void setOptions(List<String> options) {
            this.options = options;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "OptionsItemDTO [ " + 
                "code = " + code + ", " + 
                "description = " + description + ", " + 
                "options = " + options + 
                " ]";
        }
    }

    // Document DTO
    public static class DocumentDTO implements Serializable {

        /**
         * Unique document ID (GUID)
         */
        private String documentId;

        /**
         * Mitigation upload, operational doc, indem letter, etc. This is *not* the file type.
         */
        private String documentType;

        /**
         * The name of the file (e.g. Mit_1_Uploaded_file_1.pdf)
         */
        private String fileName;


        /**
         * Creates a new instance of DocumentDTO
         */
        public DocumentDTO() {}

        /**
         * Get Unique document ID (GUID)
         * @return Unique document ID (GUID)
         */
        public String getDocumentId() {
            return documentId;
        }

        /**
         * Set Unique document ID (GUID)
         * @param documentId
         *            Unique document ID (GUID)
         */
        public void setDocumentId(String documentId) {
            this.documentId = documentId;
        }

        /**
         * Get Mitigation upload, operational doc, indem letter, etc. This is *not* the file type.
         * @return Mitigation upload, operational doc, indem letter, etc. This is *not* the file type.
         */
        public String getDocumentType() {
            return documentType;
        }

        /**
         * Set Mitigation upload, operational doc, indem letter, etc. This is *not* the file type.
         * @param documentType
         *            Mitigation upload, operational doc, indem letter, etc. This is *not* the file type.
         */
        public void setDocumentType(String documentType) {
            this.documentType = documentType;
        }

        /**
         * Get The name of the file (e.g. Mit_1_Uploaded_file_1.pdf)
         * @return The name of the file (e.g. Mit_1_Uploaded_file_1.pdf)
         */
        public String getFileName() {
            return fileName;
        }

        /**
         * Set The name of the file (e.g. Mit_1_Uploaded_file_1.pdf)
         * @param fileName
         *            The name of the file (e.g. Mit_1_Uploaded_file_1.pdf)
         */
        public void setFileName(String fileName) {
            this.fileName = fileName;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "DocumentDTO [ " + 
                "documentId = " + documentId + ", " + 
                "documentType = " + documentType + ", " + 
                "fileName = " + fileName + 
                " ]";
        }
    }

    // SimpleSelection DTO
    public static class SimpleSelectionDTO implements Serializable {

        /**
         * An optional simple selection ID that can be used for various API calls that only need to know an ID.
         */
        private String selectionId;

        /**
         * An optional reason code for the selection.
         */
        private String reasonCode;


        /**
         * Creates a new instance of SimpleSelectionDTO
         */
        public SimpleSelectionDTO() {}

        /**
         * Get An optional simple selection ID that can be used for various API calls that only need to know an ID.
         * @return An optional simple selection ID that can be used for various API calls that only need to know an ID.
         */
        public String getSelectionId() {
            return selectionId;
        }

        /**
         * Set An optional simple selection ID that can be used for various API calls that only need to know an ID.
         * @param selectionId
         *            An optional simple selection ID that can be used for various API calls that only need to know an ID.
         */
        public void setSelectionId(String selectionId) {
            this.selectionId = selectionId;
        }

        /**
         * Get An optional reason code for the selection.
         * @return An optional reason code for the selection.
         */
        public String getReasonCode() {
            return reasonCode;
        }

        /**
         * Set An optional reason code for the selection.
         * @param reasonCode
         *            An optional reason code for the selection.
         */
        public void setReasonCode(String reasonCode) {
            this.reasonCode = reasonCode;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "SimpleSelectionDTO [ " + 
                "selectionId = " + selectionId + ", " + 
                "reasonCode = " + reasonCode + 
                " ]";
        }
    }

    // LiteLender DTO
    public static class LiteLenderDTO implements Serializable {

        /**
         * The identifier of this lender
         */
        private String lenderId;

        /**
         * The human readable name of the lender
         */
        private String name;


        /**
         * Creates a new instance of LiteLenderDTO
         */
        public LiteLenderDTO() {}

        /**
         * Get The identifier of this lender
         * @return The identifier of this lender
         */
        public String getLenderId() {
            return lenderId;
        }

        /**
         * Set The identifier of this lender
         * @param lenderId
         *            The identifier of this lender
         */
        public void setLenderId(String lenderId) {
            this.lenderId = lenderId;
        }

        /**
         * Get The human readable name of the lender
         * @return The human readable name of the lender
         */
        public String getName() {
            return name;
        }

        /**
         * Set The human readable name of the lender
         * @param name
         *            The human readable name of the lender
         */
        public void setName(String name) {
            this.name = name;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "LiteLenderDTO [ " + 
                "lenderId = " + lenderId + ", " + 
                "name = " + name + 
                " ]";
        }
    }

    // DefectAreaCauseDictionary DTO
    public static class DefectAreaCauseDictionaryDTO implements Serializable {

        /**
         * The unique 2 char identifier of this defect area. This code can be displayed to users as well as used to reference defect areas between related entities.
         */
        private String defectAreaCode;

        /**
         * The defect cause code of this defect area
         */
        private String defectCauseCode;

        /**
         * The description for the defect cause code of this defect area
         */
        private String description;


        /**
         * Creates a new instance of DefectAreaCauseDictionaryDTO
         */
        public DefectAreaCauseDictionaryDTO() {}

        /**
         * Get The unique 2 char identifier of this defect area. This code can be displayed to users as well as used to reference defect areas between related entities.
         * @return The unique 2 char identifier of this defect area. This code can be displayed to users as well as used to reference defect areas between related entities.
         */
        public String getDefectAreaCode() {
            return defectAreaCode;
        }

        /**
         * Set The unique 2 char identifier of this defect area. This code can be displayed to users as well as used to reference defect areas between related entities.
         * @param defectAreaCode
         *            The unique 2 char identifier of this defect area. This code can be displayed to users as well as used to reference defect areas between related entities.
         */
        public void setDefectAreaCode(String defectAreaCode) {
            this.defectAreaCode = defectAreaCode;
        }

        /**
         * Get The defect cause code of this defect area
         * @return The defect cause code of this defect area
         */
        public String getDefectCauseCode() {
            return defectCauseCode;
        }

        /**
         * Set The defect cause code of this defect area
         * @param defectCauseCode
         *            The defect cause code of this defect area
         */
        public void setDefectCauseCode(String defectCauseCode) {
            this.defectCauseCode = defectCauseCode;
        }

        /**
         * Get The description for the defect cause code of this defect area
         * @return The description for the defect cause code of this defect area
         */
        public String getDescription() {
            return description;
        }

        /**
         * Set The description for the defect cause code of this defect area
         * @param description
         *            The description for the defect cause code of this defect area
         */
        public void setDescription(String description) {
            this.description = description;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "DefectAreaCauseDictionaryDTO [ " + 
                "defectAreaCode = " + defectAreaCode + ", " + 
                "defectCauseCode = " + defectCauseCode + ", " + 
                "description = " + description + 
                " ]";
        }
    }

    // DefectAreaSeverityDictionary DTO
    public static class DefectAreaSeverityDictionaryDTO implements Serializable {

        /**
         * The unique 2 char identifier of this defect area. This code can be displayed to users as well as used to reference defect areas between related entities.
         */
        private String defectAreaCode;

        /**
         * The defect severity tier code of this defect area
         */
        private String defectSeverityTierCode;

        /**
         * The description for the defect severity tier code of this defect area
         */
        private String description;


        /**
         * Creates a new instance of DefectAreaSeverityDictionaryDTO
         */
        public DefectAreaSeverityDictionaryDTO() {}

        /**
         * Get The unique 2 char identifier of this defect area. This code can be displayed to users as well as used to reference defect areas between related entities.
         * @return The unique 2 char identifier of this defect area. This code can be displayed to users as well as used to reference defect areas between related entities.
         */
        public String getDefectAreaCode() {
            return defectAreaCode;
        }

        /**
         * Set The unique 2 char identifier of this defect area. This code can be displayed to users as well as used to reference defect areas between related entities.
         * @param defectAreaCode
         *            The unique 2 char identifier of this defect area. This code can be displayed to users as well as used to reference defect areas between related entities.
         */
        public void setDefectAreaCode(String defectAreaCode) {
            this.defectAreaCode = defectAreaCode;
        }

        /**
         * Get The defect severity tier code of this defect area
         * @return The defect severity tier code of this defect area
         */
        public String getDefectSeverityTierCode() {
            return defectSeverityTierCode;
        }

        /**
         * Set The defect severity tier code of this defect area
         * @param defectSeverityTierCode
         *            The defect severity tier code of this defect area
         */
        public void setDefectSeverityTierCode(String defectSeverityTierCode) {
            this.defectSeverityTierCode = defectSeverityTierCode;
        }

        /**
         * Get The description for the defect severity tier code of this defect area
         * @return The description for the defect severity tier code of this defect area
         */
        public String getDescription() {
            return description;
        }

        /**
         * Set The description for the defect severity tier code of this defect area
         * @param description
         *            The description for the defect severity tier code of this defect area
         */
        public void setDescription(String description) {
            this.description = description;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "DefectAreaSeverityDictionaryDTO [ " + 
                "defectAreaCode = " + defectAreaCode + ", " + 
                "defectSeverityTierCode = " + defectSeverityTierCode + ", " + 
                "description = " + description + 
                " ]";
        }
    }

    // DefectAreaSourceDictionary DTO
    public static class DefectAreaSourceDictionaryDTO implements Serializable {

        /**
         * The unique 2 char identifier of this defect area. This code can be displayed to users as well as used to reference defect areas between related entities.
         */
        private String defectAreaCode;

        /**
         * The defect source code of this defect area
         */
        private String defectSourceCode;

        /**
         * The description for the defect source code of this defect area
         */
        private String description;


        /**
         * Creates a new instance of DefectAreaSourceDictionaryDTO
         */
        public DefectAreaSourceDictionaryDTO() {}

        /**
         * Get The unique 2 char identifier of this defect area. This code can be displayed to users as well as used to reference defect areas between related entities.
         * @return The unique 2 char identifier of this defect area. This code can be displayed to users as well as used to reference defect areas between related entities.
         */
        public String getDefectAreaCode() {
            return defectAreaCode;
        }

        /**
         * Set The unique 2 char identifier of this defect area. This code can be displayed to users as well as used to reference defect areas between related entities.
         * @param defectAreaCode
         *            The unique 2 char identifier of this defect area. This code can be displayed to users as well as used to reference defect areas between related entities.
         */
        public void setDefectAreaCode(String defectAreaCode) {
            this.defectAreaCode = defectAreaCode;
        }

        /**
         * Get The defect source code of this defect area
         * @return The defect source code of this defect area
         */
        public String getDefectSourceCode() {
            return defectSourceCode;
        }

        /**
         * Set The defect source code of this defect area
         * @param defectSourceCode
         *            The defect source code of this defect area
         */
        public void setDefectSourceCode(String defectSourceCode) {
            this.defectSourceCode = defectSourceCode;
        }

        /**
         * Get The description for the defect source code of this defect area
         * @return The description for the defect source code of this defect area
         */
        public String getDescription() {
            return description;
        }

        /**
         * Set The description for the defect source code of this defect area
         * @param description
         *            The description for the defect source code of this defect area
         */
        public void setDescription(String description) {
            this.description = description;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "DefectAreaSourceDictionaryDTO [ " + 
                "defectAreaCode = " + defectAreaCode + ", " + 
                "defectSourceCode = " + defectSourceCode + ", " + 
                "description = " + description + 
                " ]";
        }
    }

    // DefectAreaDictionary DTO
    public static class DefectAreaDictionaryDTO implements Serializable {

        /**
         * The unique identifier (GUID) of this defect area.
         */
        private String defectAreaId;

        /**
         * The unique 2 char identifier of this defect area. This code can be displayed to users as well as used to reference defect areas between related entities.
         */
        private String defectAreaCode;

        /**
         * The title of this defect area that can be displayed to users
         */
        private String title;

        /**
         * When a list of defect areas are displayed in the UI, they will be sorted based on this value
         */
        private Integer order;

        /**
         * The codes of review types assigned to the defect area for the qa model.
         */
        private List<String> reviewTypeCodes;


        /**
         * Creates a new instance of DefectAreaDictionaryDTO
         */
        public DefectAreaDictionaryDTO() {}

        /**
         * Get The unique identifier (GUID) of this defect area.
         * @return The unique identifier (GUID) of this defect area.
         */
        public String getDefectAreaId() {
            return defectAreaId;
        }

        /**
         * Set The unique identifier (GUID) of this defect area.
         * @param defectAreaId
         *            The unique identifier (GUID) of this defect area.
         */
        public void setDefectAreaId(String defectAreaId) {
            this.defectAreaId = defectAreaId;
        }

        /**
         * Get The unique 2 char identifier of this defect area. This code can be displayed to users as well as used to reference defect areas between related entities.
         * @return The unique 2 char identifier of this defect area. This code can be displayed to users as well as used to reference defect areas between related entities.
         */
        public String getDefectAreaCode() {
            return defectAreaCode;
        }

        /**
         * Set The unique 2 char identifier of this defect area. This code can be displayed to users as well as used to reference defect areas between related entities.
         * @param defectAreaCode
         *            The unique 2 char identifier of this defect area. This code can be displayed to users as well as used to reference defect areas between related entities.
         */
        public void setDefectAreaCode(String defectAreaCode) {
            this.defectAreaCode = defectAreaCode;
        }

        /**
         * Get The title of this defect area that can be displayed to users
         * @return The title of this defect area that can be displayed to users
         */
        public String getTitle() {
            return title;
        }

        /**
         * Set The title of this defect area that can be displayed to users
         * @param title
         *            The title of this defect area that can be displayed to users
         */
        public void setTitle(String title) {
            this.title = title;
        }

        /**
         * Get When a list of defect areas are displayed in the UI, they will be sorted based on this value
         * @return When a list of defect areas are displayed in the UI, they will be sorted based on this value
         */
        public Integer getOrder() {
            return order;
        }

        /**
         * Set When a list of defect areas are displayed in the UI, they will be sorted based on this value
         * @param order
         *            When a list of defect areas are displayed in the UI, they will be sorted based on this value
         */
        public void setOrder(Integer order) {
            this.order = order;
        }

        /**
         * Get The codes of review types assigned to the defect area for the qa model.
         * @return The codes of review types assigned to the defect area for the qa model.
         */
        public List<String> getReviewTypeCodes() {
            return reviewTypeCodes;
        }

        /**
         * Set The codes of review types assigned to the defect area for the qa model.
         * @param reviewTypeCodes
         *            The codes of review types assigned to the defect area for the qa model.
         */
        public void setReviewTypeCodes(List<String> reviewTypeCodes) {
            this.reviewTypeCodes = reviewTypeCodes;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "DefectAreaDictionaryDTO [ " + 
                "defectAreaId = " + defectAreaId + ", " + 
                "defectAreaCode = " + defectAreaCode + ", " + 
                "title = " + title + ", " + 
                "order = " + order + ", " + 
                "reviewTypeCodes = " + reviewTypeCodes + 
                " ]";
        }
    }

    // SelectionReasonDictionary DTO
    public static class SelectionReasonDictionaryDTO implements Serializable {

        /**
         * The identifier of the object
         */
        private String code;

        /**
         * The display name for this selection reason.
         */
        private String description;

        /**
         * When or how often is the processing performed.
         */
        private String processingCycle;


        /**
         * Creates a new instance of SelectionReasonDictionaryDTO
         */
        public SelectionReasonDictionaryDTO() {}

        /**
         * Get The identifier of the object
         * @return The identifier of the object
         */
        public String getCode() {
            return code;
        }

        /**
         * Set The identifier of the object
         * @param code
         *            The identifier of the object
         */
        public void setCode(String code) {
            this.code = code;
        }

        /**
         * Get The display name for this selection reason.
         * @return The display name for this selection reason.
         */
        public String getDescription() {
            return description;
        }

        /**
         * Set The display name for this selection reason.
         * @param description
         *            The display name for this selection reason.
         */
        public void setDescription(String description) {
            this.description = description;
        }

        /**
         * Get When or how often is the processing performed.
         * @return When or how often is the processing performed.
         */
        public String getProcessingCycle() {
            return processingCycle;
        }

        /**
         * Set When or how often is the processing performed.
         * @param processingCycle
         *            When or how often is the processing performed.
         */
        public void setProcessingCycle(String processingCycle) {
            this.processingCycle = processingCycle;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "SelectionReasonDictionaryDTO [ " + 
                "code = " + code + ", " + 
                "description = " + description + ", " + 
                "processingCycle = " + processingCycle + 
                " ]";
        }
    }

    // ConsolidatedSelectionReasonDictionary DTO
    public static class ConsolidatedSelectionReasonDictionaryDTO implements Serializable {

        /**
         * The identifier of the object
         */
        private String code;

        /**
         * The display name for this selection reason.
         */
        private String description;


        /**
         * Creates a new instance of ConsolidatedSelectionReasonDictionaryDTO
         */
        public ConsolidatedSelectionReasonDictionaryDTO() {}

        /**
         * Get The identifier of the object
         * @return The identifier of the object
         */
        public String getCode() {
            return code;
        }

        /**
         * Set The identifier of the object
         * @param code
         *            The identifier of the object
         */
        public void setCode(String code) {
            this.code = code;
        }

        /**
         * Get The display name for this selection reason.
         * @return The display name for this selection reason.
         */
        public String getDescription() {
            return description;
        }

        /**
         * Set The display name for this selection reason.
         * @param description
         *            The display name for this selection reason.
         */
        public void setDescription(String description) {
            this.description = description;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "ConsolidatedSelectionReasonDictionaryDTO [ " + 
                "code = " + code + ", " + 
                "description = " + description + 
                " ]";
        }
    }

    // RatingCodeDictionary DTO
    public static class RatingCodeDictionaryDTO implements Serializable {

        /**
         * The one char letter representing the rating (e.g. &#x27;C&#x27;, &#x27;D&#x27;, &#x27;M&#x27;, etc.)
         */
        private String ratingCode;

        /**
         * The displayable string for this rating code (e.g. &#x27;Conforming&#x27;, &#x27;Deficient&#x27;, &#x27;Mitigated&#x27;, etc.)
         */
        private String description;

        /**
         * The rank of how low the rating is (e.g. 5, 4, 3, etc.). NOTE: The rating with the lowest rankOrder _is_ the rating for that group of items
         */
        private BigDecimal rankOrder;


        /**
         * Creates a new instance of RatingCodeDictionaryDTO
         */
        public RatingCodeDictionaryDTO() {}

        /**
         * Get The one char letter representing the rating (e.g. &#x27;C&#x27;, &#x27;D&#x27;, &#x27;M&#x27;, etc.)
         * @return The one char letter representing the rating (e.g. &#x27;C&#x27;, &#x27;D&#x27;, &#x27;M&#x27;, etc.)
         */
        public String getRatingCode() {
            return ratingCode;
        }

        /**
         * Set The one char letter representing the rating (e.g. &#x27;C&#x27;, &#x27;D&#x27;, &#x27;M&#x27;, etc.)
         * @param ratingCode
         *            The one char letter representing the rating (e.g. &#x27;C&#x27;, &#x27;D&#x27;, &#x27;M&#x27;, etc.)
         */
        public void setRatingCode(String ratingCode) {
            this.ratingCode = ratingCode;
        }

        /**
         * Get The displayable string for this rating code (e.g. &#x27;Conforming&#x27;, &#x27;Deficient&#x27;, &#x27;Mitigated&#x27;, etc.)
         * @return The displayable string for this rating code (e.g. &#x27;Conforming&#x27;, &#x27;Deficient&#x27;, &#x27;Mitigated&#x27;, etc.)
         */
        public String getDescription() {
            return description;
        }

        /**
         * Set The displayable string for this rating code (e.g. &#x27;Conforming&#x27;, &#x27;Deficient&#x27;, &#x27;Mitigated&#x27;, etc.)
         * @param description
         *            The displayable string for this rating code (e.g. &#x27;Conforming&#x27;, &#x27;Deficient&#x27;, &#x27;Mitigated&#x27;, etc.)
         */
        public void setDescription(String description) {
            this.description = description;
        }

        /**
         * Get The rank of how low the rating is (e.g. 5, 4, 3, etc.). NOTE: The rating with the lowest rankOrder _is_ the rating for that group of items
         * @return The rank of how low the rating is (e.g. 5, 4, 3, etc.). NOTE: The rating with the lowest rankOrder _is_ the rating for that group of items
         */
        public BigDecimal getRankOrder() {
            return rankOrder;
        }

        /**
         * Set The rank of how low the rating is (e.g. 5, 4, 3, etc.). NOTE: The rating with the lowest rankOrder _is_ the rating for that group of items
         * @param rankOrder
         *            The rank of how low the rating is (e.g. 5, 4, 3, etc.). NOTE: The rating with the lowest rankOrder _is_ the rating for that group of items
         */
        public void setRankOrder(BigDecimal rankOrder) {
            this.rankOrder = rankOrder;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "RatingCodeDictionaryDTO [ " + 
                "ratingCode = " + ratingCode + ", " + 
                "description = " + description + ", " + 
                "rankOrder = " + rankOrder + 
                " ]";
        }
    }

    // Error DTO
    public static class ErrorDTO implements Serializable {

        /**
         * The identifier of the object
         */
        private String errorId;

        /**
         * 
         */
        private Integer errorCode;

        /**
         * diagnostic error message
         */
        private String message;

        /**
         * full URL the user was viewing when the error occurred
         */
        private String url;

        /**
         * most recent client-side logging messages
         */
        private List<String> logHistory;


        /**
         * Creates a new instance of ErrorDTO
         */
        public ErrorDTO() {}

        /**
         * Get The identifier of the object
         * @return The identifier of the object
         */
        public String getErrorId() {
            return errorId;
        }

        /**
         * Set The identifier of the object
         * @param errorId
         *            The identifier of the object
         */
        public void setErrorId(String errorId) {
            this.errorId = errorId;
        }

        /**
         * Get 
         * @return 
         */
        public Integer getErrorCode() {
            return errorCode;
        }

        /**
         * Set 
         * @param errorCode
         *            
         */
        public void setErrorCode(Integer errorCode) {
            this.errorCode = errorCode;
        }

        /**
         * Get diagnostic error message
         * @return diagnostic error message
         */
        public String getMessage() {
            return message;
        }

        /**
         * Set diagnostic error message
         * @param message
         *            diagnostic error message
         */
        public void setMessage(String message) {
            this.message = message;
        }

        /**
         * Get full URL the user was viewing when the error occurred
         * @return full URL the user was viewing when the error occurred
         */
        public String getUrl() {
            return url;
        }

        /**
         * Set full URL the user was viewing when the error occurred
         * @param url
         *            full URL the user was viewing when the error occurred
         */
        public void setUrl(String url) {
            this.url = url;
        }

        /**
         * Get most recent client-side logging messages
         * @return most recent client-side logging messages
         */
        public List<String> getLogHistory() {
            return logHistory;
        }

        /**
         * Set most recent client-side logging messages
         * @param logHistory
         *            most recent client-side logging messages
         */
        public void setLogHistory(List<String> logHistory) {
            this.logHistory = logHistory;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "ErrorDTO [ " + 
                "errorId = " + errorId + ", " + 
                "errorCode = " + errorCode + ", " + 
                "message = " + message + ", " + 
                "url = " + url + ", " + 
                "logHistory = " + logHistory + 
                " ]";
        }
    }

    // DevLogin DTO
    public static class DevLoginDTO implements Serializable {

        /**
         * The ID of the user to simulate authenticating as
         */
        private String userId;


        /**
         * Creates a new instance of DevLoginDTO
         */
        public DevLoginDTO() {}

        /**
         * Get The ID of the user to simulate authenticating as
         * @return The ID of the user to simulate authenticating as
         */
        public String getUserId() {
            return userId;
        }

        /**
         * Set The ID of the user to simulate authenticating as
         * @param userId
         *            The ID of the user to simulate authenticating as
         */
        public void setUserId(String userId) {
            this.userId = userId;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "DevLoginDTO [ " + 
                "userId = " + userId + 
                " ]";
        }
    }

    // DevLoginDetails DTO
    public static class DevLoginDetailsDTO implements Serializable {

        /**
         * The ID of the user to simulate authenticating as
         */
        private String userId;

        /**
         * Description of what permissions/roles this test user is defined as having
         */
        private String description;

        /**
         * A collection of access rights this user has.
         */
        private List<String> authorities;

        /**
         * The first name of the current user.
         */
        private String firstName;

        /**
         * The last name of the current user.
         */
        private String lastName;

        /**
         * An object containing information about the lender this user is associated with.
         */
        private LiteLenderDTO lender;

        /**
         * This person&#x27;s review location name
         */
        private String reviewLocation;

        /**
         * This person&#x27;s supervisor&#x27;s name
         */
        private String reportsToName;

        /**
         * This person&#x27;s supervisor&#x27;s ID
         */
        private String reportsToId;

        /**
         * This person&#x27;s vetter&#x27;s name
         */
        private String vetterName;

        /**
         * This person&#x27;s vetter&#x27;s ID
         */
        private String vetterId;


        /**
         * Creates a new instance of DevLoginDetailsDTO
         */
        public DevLoginDetailsDTO() {}

        /**
         * Get The ID of the user to simulate authenticating as
         * @return The ID of the user to simulate authenticating as
         */
        public String getUserId() {
            return userId;
        }

        /**
         * Set The ID of the user to simulate authenticating as
         * @param userId
         *            The ID of the user to simulate authenticating as
         */
        public void setUserId(String userId) {
            this.userId = userId;
        }

        /**
         * Get Description of what permissions/roles this test user is defined as having
         * @return Description of what permissions/roles this test user is defined as having
         */
        public String getDescription() {
            return description;
        }

        /**
         * Set Description of what permissions/roles this test user is defined as having
         * @param description
         *            Description of what permissions/roles this test user is defined as having
         */
        public void setDescription(String description) {
            this.description = description;
        }

        /**
         * Get A collection of access rights this user has.
         * @return A collection of access rights this user has.
         */
        public List<String> getAuthorities() {
            return authorities;
        }

        /**
         * Set A collection of access rights this user has.
         * @param authorities
         *            A collection of access rights this user has.
         */
        public void setAuthorities(List<String> authorities) {
            this.authorities = authorities;
        }

        /**
         * Get The first name of the current user.
         * @return The first name of the current user.
         */
        public String getFirstName() {
            return firstName;
        }

        /**
         * Set The first name of the current user.
         * @param firstName
         *            The first name of the current user.
         */
        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        /**
         * Get The last name of the current user.
         * @return The last name of the current user.
         */
        public String getLastName() {
            return lastName;
        }

        /**
         * Set The last name of the current user.
         * @param lastName
         *            The last name of the current user.
         */
        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        /**
         * Get An object containing information about the lender this user is associated with.
         * @return An object containing information about the lender this user is associated with.
         */
        public LiteLenderDTO getLender() {
            return lender;
        }

        /**
         * Set An object containing information about the lender this user is associated with.
         * @param lender
         *            An object containing information about the lender this user is associated with.
         */
        public void setLender(LiteLenderDTO lender) {
            this.lender = lender;
        }

        /**
         * Get This person&#x27;s review location name
         * @return This person&#x27;s review location name
         */
        public String getReviewLocation() {
            return reviewLocation;
        }

        /**
         * Set This person&#x27;s review location name
         * @param reviewLocation
         *            This person&#x27;s review location name
         */
        public void setReviewLocation(String reviewLocation) {
            this.reviewLocation = reviewLocation;
        }

        /**
         * Get This person&#x27;s supervisor&#x27;s name
         * @return This person&#x27;s supervisor&#x27;s name
         */
        public String getReportsToName() {
            return reportsToName;
        }

        /**
         * Set This person&#x27;s supervisor&#x27;s name
         * @param reportsToName
         *            This person&#x27;s supervisor&#x27;s name
         */
        public void setReportsToName(String reportsToName) {
            this.reportsToName = reportsToName;
        }

        /**
         * Get This person&#x27;s supervisor&#x27;s ID
         * @return This person&#x27;s supervisor&#x27;s ID
         */
        public String getReportsToId() {
            return reportsToId;
        }

        /**
         * Set This person&#x27;s supervisor&#x27;s ID
         * @param reportsToId
         *            This person&#x27;s supervisor&#x27;s ID
         */
        public void setReportsToId(String reportsToId) {
            this.reportsToId = reportsToId;
        }

        /**
         * Get This person&#x27;s vetter&#x27;s name
         * @return This person&#x27;s vetter&#x27;s name
         */
        public String getVetterName() {
            return vetterName;
        }

        /**
         * Set This person&#x27;s vetter&#x27;s name
         * @param vetterName
         *            This person&#x27;s vetter&#x27;s name
         */
        public void setVetterName(String vetterName) {
            this.vetterName = vetterName;
        }

        /**
         * Get This person&#x27;s vetter&#x27;s ID
         * @return This person&#x27;s vetter&#x27;s ID
         */
        public String getVetterId() {
            return vetterId;
        }

        /**
         * Set This person&#x27;s vetter&#x27;s ID
         * @param vetterId
         *            This person&#x27;s vetter&#x27;s ID
         */
        public void setVetterId(String vetterId) {
            this.vetterId = vetterId;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "DevLoginDetailsDTO [ " + 
                "userId = " + userId + ", " + 
                "description = " + description + ", " + 
                "authorities = " + authorities + ", " + 
                "firstName = " + firstName + ", " + 
                "lastName = " + lastName + ", " + 
                "lender = " + lender + ", " + 
                "reviewLocation = " + reviewLocation + ", " + 
                "reportsToName = " + reportsToName + ", " + 
                "reportsToId = " + reportsToId + ", " + 
                "vetterName = " + vetterName + ", " + 
                "vetterId = " + vetterId + 
                " ]";
        }
    }

    // ManualSelection DTO
    public static class ManualSelectionDTO implements Serializable {

        /**
         * The ID of the Reason these loans have been selected for review.
         */
        private String selectionReason;

        /**
         * The ID of the SubReason these loans have been selected for review.
         */
        private String selectionSubReason;

        /**
         * The ID of the Review Type these loans have been selected for.
         */
        private String reviewType;

        /**
         * The ID of the Review Location these loans have been selected to be reviewed at.
         */
        private String reviewLocation;

        /**
         * A collection of objects requesting reviews, grouped by lender.
         */
        private List<ReviewRequestByLenderDTO> casesForReviewByLender;


        /**
         * Creates a new instance of ManualSelectionDTO
         */
        public ManualSelectionDTO() {}

        /**
         * Get The ID of the Reason these loans have been selected for review.
         * @return The ID of the Reason these loans have been selected for review.
         */
        public String getSelectionReason() {
            return selectionReason;
        }

        /**
         * Set The ID of the Reason these loans have been selected for review.
         * @param selectionReason
         *            The ID of the Reason these loans have been selected for review.
         */
        public void setSelectionReason(String selectionReason) {
            this.selectionReason = selectionReason;
        }

        /**
         * Get The ID of the SubReason these loans have been selected for review.
         * @return The ID of the SubReason these loans have been selected for review.
         */
        public String getSelectionSubReason() {
            return selectionSubReason;
        }

        /**
         * Set The ID of the SubReason these loans have been selected for review.
         * @param selectionSubReason
         *            The ID of the SubReason these loans have been selected for review.
         */
        public void setSelectionSubReason(String selectionSubReason) {
            this.selectionSubReason = selectionSubReason;
        }

        /**
         * Get The ID of the Review Type these loans have been selected for.
         * @return The ID of the Review Type these loans have been selected for.
         */
        public String getReviewType() {
            return reviewType;
        }

        /**
         * Set The ID of the Review Type these loans have been selected for.
         * @param reviewType
         *            The ID of the Review Type these loans have been selected for.
         */
        public void setReviewType(String reviewType) {
            this.reviewType = reviewType;
        }

        /**
         * Get The ID of the Review Location these loans have been selected to be reviewed at.
         * @return The ID of the Review Location these loans have been selected to be reviewed at.
         */
        public String getReviewLocation() {
            return reviewLocation;
        }

        /**
         * Set The ID of the Review Location these loans have been selected to be reviewed at.
         * @param reviewLocation
         *            The ID of the Review Location these loans have been selected to be reviewed at.
         */
        public void setReviewLocation(String reviewLocation) {
            this.reviewLocation = reviewLocation;
        }

        /**
         * Get A collection of objects requesting reviews, grouped by lender.
         * @return A collection of objects requesting reviews, grouped by lender.
         */
        public List<ReviewRequestByLenderDTO> getCasesForReviewByLender() {
            return casesForReviewByLender;
        }

        /**
         * Set A collection of objects requesting reviews, grouped by lender.
         * @param casesForReviewByLender
         *            A collection of objects requesting reviews, grouped by lender.
         */
        public void setCasesForReviewByLender(List<ReviewRequestByLenderDTO> casesForReviewByLender) {
            this.casesForReviewByLender = casesForReviewByLender;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "ManualSelectionDTO [ " + 
                "selectionReason = " + selectionReason + ", " + 
                "selectionSubReason = " + selectionSubReason + ", " + 
                "reviewType = " + reviewType + ", " + 
                "reviewLocation = " + reviewLocation + ", " + 
                "casesForReviewByLender = " + casesForReviewByLender + 
                " ]";
        }
    }

    // Review DTO
    public static class ReviewDTO implements Serializable {

        /**
         * The identifier of this review
         */
        private String reviewId;

        /**
         * The case number for the loan being reviewed
         */
        private String caseNumber;

        /**
         * The ID of the batch this review is a part of.  Null if it is not part of a batch.
         */
        private String batchId;

        /**
         * The human readable reference of the batch this review is a part of.  Null if it is not part of a batch.
         */
        private String batchReferenceId;

        /**
         * The type of review being performed.  For example: &#x27;Underwriting&#x27;, &#x27;Servicing&#x27;, or &#x27;Operational&#x27;
         */
        private String reviewType;

        /**
         * Short name of the business rule that triggered the need to review this loan.  For example: &#x27;Lender Monitoring&#x27;, &#x27;Random&#x27;, &#x27;Lender Self&#x27;
         */
        private String selectionReason;

        /**
         * The scope of this review (example: &#x27;Full&#x27;)
         */
        private String scope;

        /**
         * The street address for the property associated with this loan
         */
        private String propertyStreetAddress;

        /**
         * The Last, First name of the borrower
         */
        private String borrowerName;

        /**
         * The ID of the lender associated with this review
         */
        private String lenderId;

        /**
         * The name of the lender associated with this review
         */
        private String lenderName;

        /**
         * The current review level object for this loan review. Each Review will have multiple review levels (i.e. &#x27;passes&#x27;) throughout it&#x27;s life. Once this review level is completed, a new review level will be created and this one will be archived in the completedReviewLevels collectoin.
         */
        private ReviewLevelDTO currentReviewLevel;

        /**
         * The Review reference ID
         */
        private String reviewReferenceId;

        /**
         * All completed review level objects for this loan review.
         */
        private List<ReviewLevelDTO> completedReviewLevels;

        /**
         * Short name of current review level status.  For example: &#x27;Assigned&#x27;, &#x27;In Progress&#x27;, or &#x27;Awaiting Lender Response&#x27;
         */
        private String status;

        /**
         * Defect ids of the defect areas that are involved in this review (based on the review type).  The display text of these codes can be retrieved from the &#x27;defectAreaDictionary&#x27; service.
         */
        private List<String> defectAreaIds;

        /**
         * A collection of qaTree objects that represent the currently configured QA tree for each defect area at the time that the review was created.
         */
        private List<ReviewQaTreeDTO> qaTrees;

        /**
         * All documents associated with this review.
         */
        private List<DocumentDTO> documents;

        /**
         * The qa model id this review is using
         */
        private String qaModelId;

        /**
         * The endorsement date of the loan
         */
        private Date endorsementDate;

        /**
         * The establishment date of the case
         */
        private Date caseEstablishmentDate;

        /**
         * The original reveiw ID of the review being QC&#x27;ed
         */
        private String originalQcReviewId;

        /**
         * The final review level ID of the original reveiw of the review being QC&#x27;ed
         */
        private String originalQcFinalReviewLevelId;

        /**
         * The ID of the selection request that created this review. Used to gather Lender Self Report info when needed
         */
        private String selectionRequestId;

        /**
         * The name of the last lender who submitted responses. The first and last names will be retrieved from FHAC. The string will be concatinated as last, first: (e.g. &#x27;Smith, John&#x27;)
         */
        private String lastLenderName;

        /**
         * The completed Date
         */
        private Date rvwCompltdDt;

        /**
         * Date the case number was selected for review within LRS
         */
        private Date selectedDate;

        /**
         * Date the binder request was created in LRS
         */
        private Date binderRequestDate;

        /**
         * Date that the user clicked the &#x27;Recieve&#x27; button within LRS for  paper binder request or Date that the eCase binder was received automatically within LRS
         */
        private Date binderReceivedDate;


        /**
         * Creates a new instance of ReviewDTO
         */
        public ReviewDTO() {}

        /**
         * Get The identifier of this review
         * @return The identifier of this review
         */
        public String getReviewId() {
            return reviewId;
        }

        /**
         * Set The identifier of this review
         * @param reviewId
         *            The identifier of this review
         */
        public void setReviewId(String reviewId) {
            this.reviewId = reviewId;
        }

        /**
         * Get The case number for the loan being reviewed
         * @return The case number for the loan being reviewed
         */
        public String getCaseNumber() {
            return caseNumber;
        }

        /**
         * Set The case number for the loan being reviewed
         * @param caseNumber
         *            The case number for the loan being reviewed
         */
        public void setCaseNumber(String caseNumber) {
            this.caseNumber = caseNumber;
        }

        /**
         * Get The ID of the batch this review is a part of.  Null if it is not part of a batch.
         * @return The ID of the batch this review is a part of.  Null if it is not part of a batch.
         */
        public String getBatchId() {
            return batchId;
        }

        /**
         * Set The ID of the batch this review is a part of.  Null if it is not part of a batch.
         * @param batchId
         *            The ID of the batch this review is a part of.  Null if it is not part of a batch.
         */
        public void setBatchId(String batchId) {
            this.batchId = batchId;
        }

        /**
         * Get The human readable reference of the batch this review is a part of.  Null if it is not part of a batch.
         * @return The human readable reference of the batch this review is a part of.  Null if it is not part of a batch.
         */
        public String getBatchReferenceId() {
            return batchReferenceId;
        }

        /**
         * Set The human readable reference of the batch this review is a part of.  Null if it is not part of a batch.
         * @param batchReferenceId
         *            The human readable reference of the batch this review is a part of.  Null if it is not part of a batch.
         */
        public void setBatchReferenceId(String batchReferenceId) {
            this.batchReferenceId = batchReferenceId;
        }

        /**
         * Get The type of review being performed.  For example: &#x27;Underwriting&#x27;, &#x27;Servicing&#x27;, or &#x27;Operational&#x27;
         * @return The type of review being performed.  For example: &#x27;Underwriting&#x27;, &#x27;Servicing&#x27;, or &#x27;Operational&#x27;
         */
        public String getReviewType() {
            return reviewType;
        }

        /**
         * Set The type of review being performed.  For example: &#x27;Underwriting&#x27;, &#x27;Servicing&#x27;, or &#x27;Operational&#x27;
         * @param reviewType
         *            The type of review being performed.  For example: &#x27;Underwriting&#x27;, &#x27;Servicing&#x27;, or &#x27;Operational&#x27;
         */
        public void setReviewType(String reviewType) {
            this.reviewType = reviewType;
        }

        /**
         * Get Short name of the business rule that triggered the need to review this loan.  For example: &#x27;Lender Monitoring&#x27;, &#x27;Random&#x27;, &#x27;Lender Self&#x27;
         * @return Short name of the business rule that triggered the need to review this loan.  For example: &#x27;Lender Monitoring&#x27;, &#x27;Random&#x27;, &#x27;Lender Self&#x27;
         */
        public String getSelectionReason() {
            return selectionReason;
        }

        /**
         * Set Short name of the business rule that triggered the need to review this loan.  For example: &#x27;Lender Monitoring&#x27;, &#x27;Random&#x27;, &#x27;Lender Self&#x27;
         * @param selectionReason
         *            Short name of the business rule that triggered the need to review this loan.  For example: &#x27;Lender Monitoring&#x27;, &#x27;Random&#x27;, &#x27;Lender Self&#x27;
         */
        public void setSelectionReason(String selectionReason) {
            this.selectionReason = selectionReason;
        }

        /**
         * Get The scope of this review (example: &#x27;Full&#x27;)
         * @return The scope of this review (example: &#x27;Full&#x27;)
         */
        public String getScope() {
            return scope;
        }

        /**
         * Set The scope of this review (example: &#x27;Full&#x27;)
         * @param scope
         *            The scope of this review (example: &#x27;Full&#x27;)
         */
        public void setScope(String scope) {
            this.scope = scope;
        }

        /**
         * Get The street address for the property associated with this loan
         * @return The street address for the property associated with this loan
         */
        public String getPropertyStreetAddress() {
            return propertyStreetAddress;
        }

        /**
         * Set The street address for the property associated with this loan
         * @param propertyStreetAddress
         *            The street address for the property associated with this loan
         */
        public void setPropertyStreetAddress(String propertyStreetAddress) {
            this.propertyStreetAddress = propertyStreetAddress;
        }

        /**
         * Get The Last, First name of the borrower
         * @return The Last, First name of the borrower
         */
        public String getBorrowerName() {
            return borrowerName;
        }

        /**
         * Set The Last, First name of the borrower
         * @param borrowerName
         *            The Last, First name of the borrower
         */
        public void setBorrowerName(String borrowerName) {
            this.borrowerName = borrowerName;
        }

        /**
         * Get The ID of the lender associated with this review
         * @return The ID of the lender associated with this review
         */
        public String getLenderId() {
            return lenderId;
        }

        /**
         * Set The ID of the lender associated with this review
         * @param lenderId
         *            The ID of the lender associated with this review
         */
        public void setLenderId(String lenderId) {
            this.lenderId = lenderId;
        }

        /**
         * Get The name of the lender associated with this review
         * @return The name of the lender associated with this review
         */
        public String getLenderName() {
            return lenderName;
        }

        /**
         * Set The name of the lender associated with this review
         * @param lenderName
         *            The name of the lender associated with this review
         */
        public void setLenderName(String lenderName) {
            this.lenderName = lenderName;
        }

        /**
         * Get The current review level object for this loan review. Each Review will have multiple review levels (i.e. &#x27;passes&#x27;) throughout it&#x27;s life. Once this review level is completed, a new review level will be created and this one will be archived in the completedReviewLevels collectoin.
         * @return The current review level object for this loan review. Each Review will have multiple review levels (i.e. &#x27;passes&#x27;) throughout it&#x27;s life. Once this review level is completed, a new review level will be created and this one will be archived in the completedReviewLevels collectoin.
         */
        public ReviewLevelDTO getCurrentReviewLevel() {
            return currentReviewLevel;
        }

        /**
         * Set The current review level object for this loan review. Each Review will have multiple review levels (i.e. &#x27;passes&#x27;) throughout it&#x27;s life. Once this review level is completed, a new review level will be created and this one will be archived in the completedReviewLevels collectoin.
         * @param currentReviewLevel
         *            The current review level object for this loan review. Each Review will have multiple review levels (i.e. &#x27;passes&#x27;) throughout it&#x27;s life. Once this review level is completed, a new review level will be created and this one will be archived in the completedReviewLevels collectoin.
         */
        public void setCurrentReviewLevel(ReviewLevelDTO currentReviewLevel) {
            this.currentReviewLevel = currentReviewLevel;
        }

        /**
         * Get The Review reference ID
         * @return The Review reference ID
         */
        public String getReviewReferenceId() {
            return reviewReferenceId;
        }

        /**
         * Set The Review reference ID
         * @param reviewReferenceId
         *            The Review reference ID
         */
        public void setReviewReferenceId(String reviewReferenceId) {
            this.reviewReferenceId = reviewReferenceId;
        }

        /**
         * Get All completed review level objects for this loan review.
         * @return All completed review level objects for this loan review.
         */
        public List<ReviewLevelDTO> getCompletedReviewLevels() {
            return completedReviewLevels;
        }

        /**
         * Set All completed review level objects for this loan review.
         * @param completedReviewLevels
         *            All completed review level objects for this loan review.
         */
        public void setCompletedReviewLevels(List<ReviewLevelDTO> completedReviewLevels) {
            this.completedReviewLevels = completedReviewLevels;
        }

        /**
         * Get Short name of current review level status.  For example: &#x27;Assigned&#x27;, &#x27;In Progress&#x27;, or &#x27;Awaiting Lender Response&#x27;
         * @return Short name of current review level status.  For example: &#x27;Assigned&#x27;, &#x27;In Progress&#x27;, or &#x27;Awaiting Lender Response&#x27;
         */
        public String getStatus() {
            return status;
        }

        /**
         * Set Short name of current review level status.  For example: &#x27;Assigned&#x27;, &#x27;In Progress&#x27;, or &#x27;Awaiting Lender Response&#x27;
         * @param status
         *            Short name of current review level status.  For example: &#x27;Assigned&#x27;, &#x27;In Progress&#x27;, or &#x27;Awaiting Lender Response&#x27;
         */
        public void setStatus(String status) {
            this.status = status;
        }

        /**
         * Get Defect ids of the defect areas that are involved in this review (based on the review type).  The display text of these codes can be retrieved from the &#x27;defectAreaDictionary&#x27; service.
         * @return Defect ids of the defect areas that are involved in this review (based on the review type).  The display text of these codes can be retrieved from the &#x27;defectAreaDictionary&#x27; service.
         */
        public List<String> getDefectAreaIds() {
            return defectAreaIds;
        }

        /**
         * Set Defect ids of the defect areas that are involved in this review (based on the review type).  The display text of these codes can be retrieved from the &#x27;defectAreaDictionary&#x27; service.
         * @param defectAreaIds
         *            Defect ids of the defect areas that are involved in this review (based on the review type).  The display text of these codes can be retrieved from the &#x27;defectAreaDictionary&#x27; service.
         */
        public void setDefectAreaIds(List<String> defectAreaIds) {
            this.defectAreaIds = defectAreaIds;
        }

        /**
         * Get A collection of qaTree objects that represent the currently configured QA tree for each defect area at the time that the review was created.
         * @return A collection of qaTree objects that represent the currently configured QA tree for each defect area at the time that the review was created.
         */
        public List<ReviewQaTreeDTO> getQaTrees() {
            return qaTrees;
        }

        /**
         * Set A collection of qaTree objects that represent the currently configured QA tree for each defect area at the time that the review was created.
         * @param qaTrees
         *            A collection of qaTree objects that represent the currently configured QA tree for each defect area at the time that the review was created.
         */
        public void setQaTrees(List<ReviewQaTreeDTO> qaTrees) {
            this.qaTrees = qaTrees;
        }

        /**
         * Get All documents associated with this review.
         * @return All documents associated with this review.
         */
        public List<DocumentDTO> getDocuments() {
            return documents;
        }

        /**
         * Set All documents associated with this review.
         * @param documents
         *            All documents associated with this review.
         */
        public void setDocuments(List<DocumentDTO> documents) {
            this.documents = documents;
        }

        /**
         * Get The qa model id this review is using
         * @return The qa model id this review is using
         */
        public String getQaModelId() {
            return qaModelId;
        }

        /**
         * Set The qa model id this review is using
         * @param qaModelId
         *            The qa model id this review is using
         */
        public void setQaModelId(String qaModelId) {
            this.qaModelId = qaModelId;
        }

        /**
         * Get The endorsement date of the loan
         * @return The endorsement date of the loan
         */
        public Date getEndorsementDate() {
            return endorsementDate;
        }

        /**
         * Set The endorsement date of the loan
         * @param endorsementDate
         *            The endorsement date of the loan
         */
        public void setEndorsementDate(Date endorsementDate) {
            this.endorsementDate = endorsementDate;
        }

        /**
         * Get The establishment date of the case
         * @return The establishment date of the case
         */
        public Date getCaseEstablishmentDate() {
            return caseEstablishmentDate;
        }

        /**
         * Set The establishment date of the case
         * @param caseEstablishmentDate
         *            The establishment date of the case
         */
        public void setCaseEstablishmentDate(Date caseEstablishmentDate) {
            this.caseEstablishmentDate = caseEstablishmentDate;
        }

        /**
         * Get The original reveiw ID of the review being QC&#x27;ed
         * @return The original reveiw ID of the review being QC&#x27;ed
         */
        public String getOriginalQcReviewId() {
            return originalQcReviewId;
        }

        /**
         * Set The original reveiw ID of the review being QC&#x27;ed
         * @param originalQcReviewId
         *            The original reveiw ID of the review being QC&#x27;ed
         */
        public void setOriginalQcReviewId(String originalQcReviewId) {
            this.originalQcReviewId = originalQcReviewId;
        }

        /**
         * Get The final review level ID of the original reveiw of the review being QC&#x27;ed
         * @return The final review level ID of the original reveiw of the review being QC&#x27;ed
         */
        public String getOriginalQcFinalReviewLevelId() {
            return originalQcFinalReviewLevelId;
        }

        /**
         * Set The final review level ID of the original reveiw of the review being QC&#x27;ed
         * @param originalQcFinalReviewLevelId
         *            The final review level ID of the original reveiw of the review being QC&#x27;ed
         */
        public void setOriginalQcFinalReviewLevelId(String originalQcFinalReviewLevelId) {
            this.originalQcFinalReviewLevelId = originalQcFinalReviewLevelId;
        }

        /**
         * Get The ID of the selection request that created this review. Used to gather Lender Self Report info when needed
         * @return The ID of the selection request that created this review. Used to gather Lender Self Report info when needed
         */
        public String getSelectionRequestId() {
            return selectionRequestId;
        }

        /**
         * Set The ID of the selection request that created this review. Used to gather Lender Self Report info when needed
         * @param selectionRequestId
         *            The ID of the selection request that created this review. Used to gather Lender Self Report info when needed
         */
        public void setSelectionRequestId(String selectionRequestId) {
            this.selectionRequestId = selectionRequestId;
        }

        /**
         * Get The name of the last lender who submitted responses. The first and last names will be retrieved from FHAC. The string will be concatinated as last, first: (e.g. &#x27;Smith, John&#x27;)
         * @return The name of the last lender who submitted responses. The first and last names will be retrieved from FHAC. The string will be concatinated as last, first: (e.g. &#x27;Smith, John&#x27;)
         */
        public String getLastLenderName() {
            return lastLenderName;
        }

        /**
         * Set The name of the last lender who submitted responses. The first and last names will be retrieved from FHAC. The string will be concatinated as last, first: (e.g. &#x27;Smith, John&#x27;)
         * @param lastLenderName
         *            The name of the last lender who submitted responses. The first and last names will be retrieved from FHAC. The string will be concatinated as last, first: (e.g. &#x27;Smith, John&#x27;)
         */
        public void setLastLenderName(String lastLenderName) {
            this.lastLenderName = lastLenderName;
        }

        /**
         * Get The completed Date
         * @return The completed Date
         */
        public Date getRvwCompltdDt() {
            return rvwCompltdDt;
        }

        /**
         * Set The completed Date
         * @param rvwCompltdDt
         *            The completed Date
         */
        public void setRvwCompltdDt(Date rvwCompltdDt) {
            this.rvwCompltdDt = rvwCompltdDt;
        }

        /**
         * Get Date the case number was selected for review within LRS
         * @return Date the case number was selected for review within LRS
         */
        public Date getSelectedDate() {
            return selectedDate;
        }

        /**
         * Set Date the case number was selected for review within LRS
         * @param selectedDate
         *            Date the case number was selected for review within LRS
         */
        public void setSelectedDate(Date selectedDate) {
            this.selectedDate = selectedDate;
        }

        /**
         * Get Date the binder request was created in LRS
         * @return Date the binder request was created in LRS
         */
        public Date getBinderRequestDate() {
            return binderRequestDate;
        }

        /**
         * Set Date the binder request was created in LRS
         * @param binderRequestDate
         *            Date the binder request was created in LRS
         */
        public void setBinderRequestDate(Date binderRequestDate) {
            this.binderRequestDate = binderRequestDate;
        }

        /**
         * Get Date that the user clicked the &#x27;Recieve&#x27; button within LRS for  paper binder request or Date that the eCase binder was received automatically within LRS
         * @return Date that the user clicked the &#x27;Recieve&#x27; button within LRS for  paper binder request or Date that the eCase binder was received automatically within LRS
         */
        public Date getBinderReceivedDate() {
            return binderReceivedDate;
        }

        /**
         * Set Date that the user clicked the &#x27;Recieve&#x27; button within LRS for  paper binder request or Date that the eCase binder was received automatically within LRS
         * @param binderReceivedDate
         *            Date that the user clicked the &#x27;Recieve&#x27; button within LRS for  paper binder request or Date that the eCase binder was received automatically within LRS
         */
        public void setBinderReceivedDate(Date binderReceivedDate) {
            this.binderReceivedDate = binderReceivedDate;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "ReviewDTO [ " + 
                "reviewId = " + reviewId + ", " + 
                "caseNumber = " + caseNumber + ", " + 
                "batchId = " + batchId + ", " + 
                "batchReferenceId = " + batchReferenceId + ", " + 
                "reviewType = " + reviewType + ", " + 
                "selectionReason = " + selectionReason + ", " + 
                "scope = " + scope + ", " + 
                "propertyStreetAddress = " + propertyStreetAddress + ", " + 
                "borrowerName = " + borrowerName + ", " + 
                "lenderId = " + lenderId + ", " + 
                "lenderName = " + lenderName + ", " + 
                "currentReviewLevel = " + currentReviewLevel + ", " + 
                "reviewReferenceId = " + reviewReferenceId + ", " + 
                "completedReviewLevels = " + completedReviewLevels + ", " + 
                "status = " + status + ", " + 
                "defectAreaIds = " + defectAreaIds + ", " + 
                "qaTrees = " + qaTrees + ", " + 
                "documents = " + documents + ", " + 
                "qaModelId = " + qaModelId + ", " + 
                "endorsementDate = " + endorsementDate + ", " + 
                "caseEstablishmentDate = " + caseEstablishmentDate + ", " + 
                "originalQcReviewId = " + originalQcReviewId + ", " + 
                "originalQcFinalReviewLevelId = " + originalQcFinalReviewLevelId + ", " + 
                "selectionRequestId = " + selectionRequestId + ", " + 
                "lastLenderName = " + lastLenderName + ", " + 
                "rvwCompltdDt = " + rvwCompltdDt + ", " + 
                "selectedDate = " + selectedDate + ", " + 
                "binderRequestDate = " + binderRequestDate + ", " + 
                "binderReceivedDate = " + binderReceivedDate + 
                " ]";
        }
    }

    // ReviewLevel DTO
    public static class ReviewLevelDTO implements Serializable {

        /**
         * The id of review level for this loan review
         */
        private String reviewLevelId;

        /**
         * The short name of the review level this active review is currently at (e.g. Initial, Mitigation, HOC Escalation, HQ Escalation, Force Placed Indemnification, Indemnification)
         */
        private String type;

        /**
         * The review level status (e.g. Awaiting Assignement, Assigned, In Progress, Pending Batch Review, Pending Vetting, Post Vetting Review, Completed, Canceled, Exception)
         */
        private String status;

        /**
         * The review level iteration number
         */
        private String iteration;

        /**
         * The rating code given to this finding which is based on defect area, source, cause, and severity tier. Known codes: &#x27;C&#x27; = Conforming, &#x27;D&#x27; = Deficient, &#x27;M&#x27; = Mitigated, &#x27;R&#x27; = Remediated, &#x27;U&#x27; = Unacceptable. Use the ratingcodedictionary API to translate code to description.
         */
        private String ratingCode;

        /**
         * The date when the mitigation request, if any, for this review was sent to the lender
         */
        private Date dateRequestSentToLender;

        /**
         * The date when the mitigation response, if requested, is due from the lender
         */
        private Date dateResponseDueFromLender;

        /**
         * The date when this review was assigned to the reviewer
         */
        private Date reviewerDateAssigned;

        /**
         * The date when this review needs to be completed by the reviewer
         */
        private Date reviewerDateDue;

        /**
         * The date when this review level was completed by the reviewer
         */
        private Date reviewerCompletedDate;

        /**
         * The name of the location where this loan review level is being reviewed
         */
        private String reviewLocationName;

        /**
         * The name of the reviewer working on the review. The first and last names will be utilized from the personel table. The string will be concatinated as last, first: (e.g. &#x27;Smith, John&#x27;)
         */
        private String reviewerName;

        /**
         * The user id of the reviewer working on the review.
         */
        private String reviewerId;

        /**
         * The name of the original reviewer working on the review. The first and last names will be utilized from the personel table. The string will be concatinated as last, first: (e.g. &#x27;Smith, John&#x27;)
         */
        private String originalReviewerName;

        /**
         * The original user id of the reviewer working on the review.
         */
        private String originalReviewerId;

        /**
         * The user id of the last person to update the review level.
         */
        private String updatedById;

        /**
         * Flag to indicate if the reviewlevel is created for the Vetter
         */
        private String vettingInd;

        /**
         * Date indicating when the vettee acknowledged the vetting review
         */
        private Date vetteeAcknowledgedDate;

        /**
         * The review level location id
         */
        private String reviewLocationId;

        /**
         * The indemnification start date type, Time of Endorsement or Agreement
         */
        private String indemnificationStart;

        /**
         * The indemnification agreement date (i.e. the date it was signed)
         */
        private Date indemnificationAgreementDate;

        /**
         * The expiration date of indemnification
         */
        private Date indemnificationExpirationDate;

        /**
         * Indicates the Indemnification Type, which will determine the Indemnification letter template that the lender will sign if they elect the indemnification route. Potential values are &#x27;5_YEAR&#x27;, and &#x27;LIFE_OF_LOAN&#x27;
         */
        private String indemnificationTypeCode;

        /**
         * The term of the indemnification
         */
        private String termOfAgreement;

        /**
         * The QC review outcome code
         */
        private String qcOutcomeCode;

        /**
         * The date if a reviewer force escalates the review level
         */
        private Date forceEscalationDate;


        /**
         * Creates a new instance of ReviewLevelDTO
         */
        public ReviewLevelDTO() {}

        /**
         * Get The id of review level for this loan review
         * @return The id of review level for this loan review
         */
        public String getReviewLevelId() {
            return reviewLevelId;
        }

        /**
         * Set The id of review level for this loan review
         * @param reviewLevelId
         *            The id of review level for this loan review
         */
        public void setReviewLevelId(String reviewLevelId) {
            this.reviewLevelId = reviewLevelId;
        }

        /**
         * Get The short name of the review level this active review is currently at (e.g. Initial, Mitigation, HOC Escalation, HQ Escalation, Force Placed Indemnification, Indemnification)
         * @return The short name of the review level this active review is currently at (e.g. Initial, Mitigation, HOC Escalation, HQ Escalation, Force Placed Indemnification, Indemnification)
         */
        public String getType() {
            return type;
        }

        /**
         * Set The short name of the review level this active review is currently at (e.g. Initial, Mitigation, HOC Escalation, HQ Escalation, Force Placed Indemnification, Indemnification)
         * @param type
         *            The short name of the review level this active review is currently at (e.g. Initial, Mitigation, HOC Escalation, HQ Escalation, Force Placed Indemnification, Indemnification)
         */
        public void setType(String type) {
            this.type = type;
        }

        /**
         * Get The review level status (e.g. Awaiting Assignement, Assigned, In Progress, Pending Batch Review, Pending Vetting, Post Vetting Review, Completed, Canceled, Exception)
         * @return The review level status (e.g. Awaiting Assignement, Assigned, In Progress, Pending Batch Review, Pending Vetting, Post Vetting Review, Completed, Canceled, Exception)
         */
        public String getStatus() {
            return status;
        }

        /**
         * Set The review level status (e.g. Awaiting Assignement, Assigned, In Progress, Pending Batch Review, Pending Vetting, Post Vetting Review, Completed, Canceled, Exception)
         * @param status
         *            The review level status (e.g. Awaiting Assignement, Assigned, In Progress, Pending Batch Review, Pending Vetting, Post Vetting Review, Completed, Canceled, Exception)
         */
        public void setStatus(String status) {
            this.status = status;
        }

        /**
         * Get The review level iteration number
         * @return The review level iteration number
         */
        public String getIteration() {
            return iteration;
        }

        /**
         * Set The review level iteration number
         * @param iteration
         *            The review level iteration number
         */
        public void setIteration(String iteration) {
            this.iteration = iteration;
        }

        /**
         * Get The rating code given to this finding which is based on defect area, source, cause, and severity tier. Known codes: &#x27;C&#x27; = Conforming, &#x27;D&#x27; = Deficient, &#x27;M&#x27; = Mitigated, &#x27;R&#x27; = Remediated, &#x27;U&#x27; = Unacceptable. Use the ratingcodedictionary API to translate code to description.
         * @return The rating code given to this finding which is based on defect area, source, cause, and severity tier. Known codes: &#x27;C&#x27; = Conforming, &#x27;D&#x27; = Deficient, &#x27;M&#x27; = Mitigated, &#x27;R&#x27; = Remediated, &#x27;U&#x27; = Unacceptable. Use the ratingcodedictionary API to translate code to description.
         */
        public String getRatingCode() {
            return ratingCode;
        }

        /**
         * Set The rating code given to this finding which is based on defect area, source, cause, and severity tier. Known codes: &#x27;C&#x27; = Conforming, &#x27;D&#x27; = Deficient, &#x27;M&#x27; = Mitigated, &#x27;R&#x27; = Remediated, &#x27;U&#x27; = Unacceptable. Use the ratingcodedictionary API to translate code to description.
         * @param ratingCode
         *            The rating code given to this finding which is based on defect area, source, cause, and severity tier. Known codes: &#x27;C&#x27; = Conforming, &#x27;D&#x27; = Deficient, &#x27;M&#x27; = Mitigated, &#x27;R&#x27; = Remediated, &#x27;U&#x27; = Unacceptable. Use the ratingcodedictionary API to translate code to description.
         */
        public void setRatingCode(String ratingCode) {
            this.ratingCode = ratingCode;
        }

        /**
         * Get The date when the mitigation request, if any, for this review was sent to the lender
         * @return The date when the mitigation request, if any, for this review was sent to the lender
         */
        public Date getDateRequestSentToLender() {
            return dateRequestSentToLender;
        }

        /**
         * Set The date when the mitigation request, if any, for this review was sent to the lender
         * @param dateRequestSentToLender
         *            The date when the mitigation request, if any, for this review was sent to the lender
         */
        public void setDateRequestSentToLender(Date dateRequestSentToLender) {
            this.dateRequestSentToLender = dateRequestSentToLender;
        }

        /**
         * Get The date when the mitigation response, if requested, is due from the lender
         * @return The date when the mitigation response, if requested, is due from the lender
         */
        public Date getDateResponseDueFromLender() {
            return dateResponseDueFromLender;
        }

        /**
         * Set The date when the mitigation response, if requested, is due from the lender
         * @param dateResponseDueFromLender
         *            The date when the mitigation response, if requested, is due from the lender
         */
        public void setDateResponseDueFromLender(Date dateResponseDueFromLender) {
            this.dateResponseDueFromLender = dateResponseDueFromLender;
        }

        /**
         * Get The date when this review was assigned to the reviewer
         * @return The date when this review was assigned to the reviewer
         */
        public Date getReviewerDateAssigned() {
            return reviewerDateAssigned;
        }

        /**
         * Set The date when this review was assigned to the reviewer
         * @param reviewerDateAssigned
         *            The date when this review was assigned to the reviewer
         */
        public void setReviewerDateAssigned(Date reviewerDateAssigned) {
            this.reviewerDateAssigned = reviewerDateAssigned;
        }

        /**
         * Get The date when this review needs to be completed by the reviewer
         * @return The date when this review needs to be completed by the reviewer
         */
        public Date getReviewerDateDue() {
            return reviewerDateDue;
        }

        /**
         * Set The date when this review needs to be completed by the reviewer
         * @param reviewerDateDue
         *            The date when this review needs to be completed by the reviewer
         */
        public void setReviewerDateDue(Date reviewerDateDue) {
            this.reviewerDateDue = reviewerDateDue;
        }

        /**
         * Get The date when this review level was completed by the reviewer
         * @return The date when this review level was completed by the reviewer
         */
        public Date getReviewerCompletedDate() {
            return reviewerCompletedDate;
        }

        /**
         * Set The date when this review level was completed by the reviewer
         * @param reviewerCompletedDate
         *            The date when this review level was completed by the reviewer
         */
        public void setReviewerCompletedDate(Date reviewerCompletedDate) {
            this.reviewerCompletedDate = reviewerCompletedDate;
        }

        /**
         * Get The name of the location where this loan review level is being reviewed
         * @return The name of the location where this loan review level is being reviewed
         */
        public String getReviewLocationName() {
            return reviewLocationName;
        }

        /**
         * Set The name of the location where this loan review level is being reviewed
         * @param reviewLocationName
         *            The name of the location where this loan review level is being reviewed
         */
        public void setReviewLocationName(String reviewLocationName) {
            this.reviewLocationName = reviewLocationName;
        }

        /**
         * Get The name of the reviewer working on the review. The first and last names will be utilized from the personel table. The string will be concatinated as last, first: (e.g. &#x27;Smith, John&#x27;)
         * @return The name of the reviewer working on the review. The first and last names will be utilized from the personel table. The string will be concatinated as last, first: (e.g. &#x27;Smith, John&#x27;)
         */
        public String getReviewerName() {
            return reviewerName;
        }

        /**
         * Set The name of the reviewer working on the review. The first and last names will be utilized from the personel table. The string will be concatinated as last, first: (e.g. &#x27;Smith, John&#x27;)
         * @param reviewerName
         *            The name of the reviewer working on the review. The first and last names will be utilized from the personel table. The string will be concatinated as last, first: (e.g. &#x27;Smith, John&#x27;)
         */
        public void setReviewerName(String reviewerName) {
            this.reviewerName = reviewerName;
        }

        /**
         * Get The user id of the reviewer working on the review.
         * @return The user id of the reviewer working on the review.
         */
        public String getReviewerId() {
            return reviewerId;
        }

        /**
         * Set The user id of the reviewer working on the review.
         * @param reviewerId
         *            The user id of the reviewer working on the review.
         */
        public void setReviewerId(String reviewerId) {
            this.reviewerId = reviewerId;
        }

        /**
         * Get The name of the original reviewer working on the review. The first and last names will be utilized from the personel table. The string will be concatinated as last, first: (e.g. &#x27;Smith, John&#x27;)
         * @return The name of the original reviewer working on the review. The first and last names will be utilized from the personel table. The string will be concatinated as last, first: (e.g. &#x27;Smith, John&#x27;)
         */
        public String getOriginalReviewerName() {
            return originalReviewerName;
        }

        /**
         * Set The name of the original reviewer working on the review. The first and last names will be utilized from the personel table. The string will be concatinated as last, first: (e.g. &#x27;Smith, John&#x27;)
         * @param originalReviewerName
         *            The name of the original reviewer working on the review. The first and last names will be utilized from the personel table. The string will be concatinated as last, first: (e.g. &#x27;Smith, John&#x27;)
         */
        public void setOriginalReviewerName(String originalReviewerName) {
            this.originalReviewerName = originalReviewerName;
        }

        /**
         * Get The original user id of the reviewer working on the review.
         * @return The original user id of the reviewer working on the review.
         */
        public String getOriginalReviewerId() {
            return originalReviewerId;
        }

        /**
         * Set The original user id of the reviewer working on the review.
         * @param originalReviewerId
         *            The original user id of the reviewer working on the review.
         */
        public void setOriginalReviewerId(String originalReviewerId) {
            this.originalReviewerId = originalReviewerId;
        }

        /**
         * Get The user id of the last person to update the review level.
         * @return The user id of the last person to update the review level.
         */
        public String getUpdatedById() {
            return updatedById;
        }

        /**
         * Set The user id of the last person to update the review level.
         * @param updatedById
         *            The user id of the last person to update the review level.
         */
        public void setUpdatedById(String updatedById) {
            this.updatedById = updatedById;
        }

        /**
         * Get Flag to indicate if the reviewlevel is created for the Vetter
         * @return Flag to indicate if the reviewlevel is created for the Vetter
         */
        public String getVettingInd() {
            return vettingInd;
        }

        /**
         * Set Flag to indicate if the reviewlevel is created for the Vetter
         * @param vettingInd
         *            Flag to indicate if the reviewlevel is created for the Vetter
         */
        public void setVettingInd(String vettingInd) {
            this.vettingInd = vettingInd;
        }

        /**
         * Get Date indicating when the vettee acknowledged the vetting review
         * @return Date indicating when the vettee acknowledged the vetting review
         */
        public Date getVetteeAcknowledgedDate() {
            return vetteeAcknowledgedDate;
        }

        /**
         * Set Date indicating when the vettee acknowledged the vetting review
         * @param vetteeAcknowledgedDate
         *            Date indicating when the vettee acknowledged the vetting review
         */
        public void setVetteeAcknowledgedDate(Date vetteeAcknowledgedDate) {
            this.vetteeAcknowledgedDate = vetteeAcknowledgedDate;
        }

        /**
         * Get The review level location id
         * @return The review level location id
         */
        public String getReviewLocationId() {
            return reviewLocationId;
        }

        /**
         * Set The review level location id
         * @param reviewLocationId
         *            The review level location id
         */
        public void setReviewLocationId(String reviewLocationId) {
            this.reviewLocationId = reviewLocationId;
        }

        /**
         * Get The indemnification start date type, Time of Endorsement or Agreement
         * @return The indemnification start date type, Time of Endorsement or Agreement
         */
        public String getIndemnificationStart() {
            return indemnificationStart;
        }

        /**
         * Set The indemnification start date type, Time of Endorsement or Agreement
         * @param indemnificationStart
         *            The indemnification start date type, Time of Endorsement or Agreement
         */
        public void setIndemnificationStart(String indemnificationStart) {
            this.indemnificationStart = indemnificationStart;
        }

        /**
         * Get The indemnification agreement date (i.e. the date it was signed)
         * @return The indemnification agreement date (i.e. the date it was signed)
         */
        public Date getIndemnificationAgreementDate() {
            return indemnificationAgreementDate;
        }

        /**
         * Set The indemnification agreement date (i.e. the date it was signed)
         * @param indemnificationAgreementDate
         *            The indemnification agreement date (i.e. the date it was signed)
         */
        public void setIndemnificationAgreementDate(Date indemnificationAgreementDate) {
            this.indemnificationAgreementDate = indemnificationAgreementDate;
        }

        /**
         * Get The expiration date of indemnification
         * @return The expiration date of indemnification
         */
        public Date getIndemnificationExpirationDate() {
            return indemnificationExpirationDate;
        }

        /**
         * Set The expiration date of indemnification
         * @param indemnificationExpirationDate
         *            The expiration date of indemnification
         */
        public void setIndemnificationExpirationDate(Date indemnificationExpirationDate) {
            this.indemnificationExpirationDate = indemnificationExpirationDate;
        }

        /**
         * Get Indicates the Indemnification Type, which will determine the Indemnification letter template that the lender will sign if they elect the indemnification route. Potential values are &#x27;5_YEAR&#x27;, and &#x27;LIFE_OF_LOAN&#x27;
         * @return Indicates the Indemnification Type, which will determine the Indemnification letter template that the lender will sign if they elect the indemnification route. Potential values are &#x27;5_YEAR&#x27;, and &#x27;LIFE_OF_LOAN&#x27;
         */
        public String getIndemnificationTypeCode() {
            return indemnificationTypeCode;
        }

        /**
         * Set Indicates the Indemnification Type, which will determine the Indemnification letter template that the lender will sign if they elect the indemnification route. Potential values are &#x27;5_YEAR&#x27;, and &#x27;LIFE_OF_LOAN&#x27;
         * @param indemnificationTypeCode
         *            Indicates the Indemnification Type, which will determine the Indemnification letter template that the lender will sign if they elect the indemnification route. Potential values are &#x27;5_YEAR&#x27;, and &#x27;LIFE_OF_LOAN&#x27;
         */
        public void setIndemnificationTypeCode(String indemnificationTypeCode) {
            this.indemnificationTypeCode = indemnificationTypeCode;
        }

        /**
         * Get The term of the indemnification
         * @return The term of the indemnification
         */
        public String getTermOfAgreement() {
            return termOfAgreement;
        }

        /**
         * Set The term of the indemnification
         * @param termOfAgreement
         *            The term of the indemnification
         */
        public void setTermOfAgreement(String termOfAgreement) {
            this.termOfAgreement = termOfAgreement;
        }

        /**
         * Get The QC review outcome code
         * @return The QC review outcome code
         */
        public String getQcOutcomeCode() {
            return qcOutcomeCode;
        }

        /**
         * Set The QC review outcome code
         * @param qcOutcomeCode
         *            The QC review outcome code
         */
        public void setQcOutcomeCode(String qcOutcomeCode) {
            this.qcOutcomeCode = qcOutcomeCode;
        }

        /**
         * Get The date if a reviewer force escalates the review level
         * @return The date if a reviewer force escalates the review level
         */
        public Date getForceEscalationDate() {
            return forceEscalationDate;
        }

        /**
         * Set The date if a reviewer force escalates the review level
         * @param forceEscalationDate
         *            The date if a reviewer force escalates the review level
         */
        public void setForceEscalationDate(Date forceEscalationDate) {
            this.forceEscalationDate = forceEscalationDate;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "ReviewLevelDTO [ " + 
                "reviewLevelId = " + reviewLevelId + ", " + 
                "type = " + type + ", " + 
                "status = " + status + ", " + 
                "iteration = " + iteration + ", " + 
                "ratingCode = " + ratingCode + ", " + 
                "dateRequestSentToLender = " + dateRequestSentToLender + ", " + 
                "dateResponseDueFromLender = " + dateResponseDueFromLender + ", " + 
                "reviewerDateAssigned = " + reviewerDateAssigned + ", " + 
                "reviewerDateDue = " + reviewerDateDue + ", " + 
                "reviewerCompletedDate = " + reviewerCompletedDate + ", " + 
                "reviewLocationName = " + reviewLocationName + ", " + 
                "reviewerName = " + reviewerName + ", " + 
                "reviewerId = " + reviewerId + ", " + 
                "originalReviewerName = " + originalReviewerName + ", " + 
                "originalReviewerId = " + originalReviewerId + ", " + 
                "updatedById = " + updatedById + ", " + 
                "vettingInd = " + vettingInd + ", " + 
                "vetteeAcknowledgedDate = " + vetteeAcknowledgedDate + ", " + 
                "reviewLocationId = " + reviewLocationId + ", " + 
                "indemnificationStart = " + indemnificationStart + ", " + 
                "indemnificationAgreementDate = " + indemnificationAgreementDate + ", " + 
                "indemnificationExpirationDate = " + indemnificationExpirationDate + ", " + 
                "indemnificationTypeCode = " + indemnificationTypeCode + ", " + 
                "termOfAgreement = " + termOfAgreement + ", " + 
                "qcOutcomeCode = " + qcOutcomeCode + ", " + 
                "forceEscalationDate = " + forceEscalationDate + 
                " ]";
        }
    }

    // ReviewLevelInfo DTO
    public static class ReviewLevelInfoDTO implements Serializable {

        /**
         * Indicates the Indemnification Type, which will determine the Indemnification letter template that the lender will sign if they elect the indemnification route. Potential values are &#x27;5_YEAR&#x27;, and &#x27;LIFE_OF_LOAN&#x27;
         */
        private String indemnificationTypeCode;

        /**
         * For a QC review, this is the selected QC outcome code (e.g. ACCEPTABLE, MODERATE, or MATERIAL)
         */
        private String qcOutcomeCd;


        /**
         * Creates a new instance of ReviewLevelInfoDTO
         */
        public ReviewLevelInfoDTO() {}

        /**
         * Get Indicates the Indemnification Type, which will determine the Indemnification letter template that the lender will sign if they elect the indemnification route. Potential values are &#x27;5_YEAR&#x27;, and &#x27;LIFE_OF_LOAN&#x27;
         * @return Indicates the Indemnification Type, which will determine the Indemnification letter template that the lender will sign if they elect the indemnification route. Potential values are &#x27;5_YEAR&#x27;, and &#x27;LIFE_OF_LOAN&#x27;
         */
        public String getIndemnificationTypeCode() {
            return indemnificationTypeCode;
        }

        /**
         * Set Indicates the Indemnification Type, which will determine the Indemnification letter template that the lender will sign if they elect the indemnification route. Potential values are &#x27;5_YEAR&#x27;, and &#x27;LIFE_OF_LOAN&#x27;
         * @param indemnificationTypeCode
         *            Indicates the Indemnification Type, which will determine the Indemnification letter template that the lender will sign if they elect the indemnification route. Potential values are &#x27;5_YEAR&#x27;, and &#x27;LIFE_OF_LOAN&#x27;
         */
        public void setIndemnificationTypeCode(String indemnificationTypeCode) {
            this.indemnificationTypeCode = indemnificationTypeCode;
        }

        /**
         * Get For a QC review, this is the selected QC outcome code (e.g. ACCEPTABLE, MODERATE, or MATERIAL)
         * @return For a QC review, this is the selected QC outcome code (e.g. ACCEPTABLE, MODERATE, or MATERIAL)
         */
        public String getQcOutcomeCd() {
            return qcOutcomeCd;
        }

        /**
         * Set For a QC review, this is the selected QC outcome code (e.g. ACCEPTABLE, MODERATE, or MATERIAL)
         * @param qcOutcomeCd
         *            For a QC review, this is the selected QC outcome code (e.g. ACCEPTABLE, MODERATE, or MATERIAL)
         */
        public void setQcOutcomeCd(String qcOutcomeCd) {
            this.qcOutcomeCd = qcOutcomeCd;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "ReviewLevelInfoDTO [ " + 
                "indemnificationTypeCode = " + indemnificationTypeCode + ", " + 
                "qcOutcomeCd = " + qcOutcomeCd + 
                " ]";
        }
    }

    // ReviewNote DTO
    public static class ReviewNoteDTO implements Serializable {

        /**
         * The login identifier of the reviewer; the user&#x27;s HID.
         */
        private String hudId;

        /**
         * The forename of the reviewer.
         */
        private String nameFirst;

        /**
         * The family name of the reviewer.
         */
        private String nameLast;

        /**
         * Date and time when the note was last updated (or created if no updates).
         */
        private Date lastUpdated;

        /**
         * Defect area code that can be optionally associated with this note. The display text of this codes can be retrieved from the &#x27;defectAreaDictionary&#x27; service.
         */
        private String defectAreaCode;

        /**
         * The note text.
         */
        private String text;


        /**
         * Creates a new instance of ReviewNoteDTO
         */
        public ReviewNoteDTO() {}

        /**
         * Get The login identifier of the reviewer; the user&#x27;s HID.
         * @return The login identifier of the reviewer; the user&#x27;s HID.
         */
        public String getHudId() {
            return hudId;
        }

        /**
         * Set The login identifier of the reviewer; the user&#x27;s HID.
         * @param hudId
         *            The login identifier of the reviewer; the user&#x27;s HID.
         */
        public void setHudId(String hudId) {
            this.hudId = hudId;
        }

        /**
         * Get The forename of the reviewer.
         * @return The forename of the reviewer.
         */
        public String getNameFirst() {
            return nameFirst;
        }

        /**
         * Set The forename of the reviewer.
         * @param nameFirst
         *            The forename of the reviewer.
         */
        public void setNameFirst(String nameFirst) {
            this.nameFirst = nameFirst;
        }

        /**
         * Get The family name of the reviewer.
         * @return The family name of the reviewer.
         */
        public String getNameLast() {
            return nameLast;
        }

        /**
         * Set The family name of the reviewer.
         * @param nameLast
         *            The family name of the reviewer.
         */
        public void setNameLast(String nameLast) {
            this.nameLast = nameLast;
        }

        /**
         * Get Date and time when the note was last updated (or created if no updates).
         * @return Date and time when the note was last updated (or created if no updates).
         */
        public Date getLastUpdated() {
            return lastUpdated;
        }

        /**
         * Set Date and time when the note was last updated (or created if no updates).
         * @param lastUpdated
         *            Date and time when the note was last updated (or created if no updates).
         */
        public void setLastUpdated(Date lastUpdated) {
            this.lastUpdated = lastUpdated;
        }

        /**
         * Get Defect area code that can be optionally associated with this note. The display text of this codes can be retrieved from the &#x27;defectAreaDictionary&#x27; service.
         * @return Defect area code that can be optionally associated with this note. The display text of this codes can be retrieved from the &#x27;defectAreaDictionary&#x27; service.
         */
        public String getDefectAreaCode() {
            return defectAreaCode;
        }

        /**
         * Set Defect area code that can be optionally associated with this note. The display text of this codes can be retrieved from the &#x27;defectAreaDictionary&#x27; service.
         * @param defectAreaCode
         *            Defect area code that can be optionally associated with this note. The display text of this codes can be retrieved from the &#x27;defectAreaDictionary&#x27; service.
         */
        public void setDefectAreaCode(String defectAreaCode) {
            this.defectAreaCode = defectAreaCode;
        }

        /**
         * Get The note text.
         * @return The note text.
         */
        public String getText() {
            return text;
        }

        /**
         * Set The note text.
         * @param text
         *            The note text.
         */
        public void setText(String text) {
            this.text = text;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "ReviewNoteDTO [ " + 
                "hudId = " + hudId + ", " + 
                "nameFirst = " + nameFirst + ", " + 
                "nameLast = " + nameLast + ", " + 
                "lastUpdated = " + lastUpdated + ", " + 
                "defectAreaCode = " + defectAreaCode + ", " + 
                "text = " + text + 
                " ]";
        }
    }

    // ReviewNotePost DTO
    public static class ReviewNotePostDTO implements Serializable {

        /**
         * The note text.
         */
        private String text;

        /**
         * Defect area code that can be optionally associated with this note
         */
        private String defectAreaCode;


        /**
         * Creates a new instance of ReviewNotePostDTO
         */
        public ReviewNotePostDTO() {}

        /**
         * Get The note text.
         * @return The note text.
         */
        public String getText() {
            return text;
        }

        /**
         * Set The note text.
         * @param text
         *            The note text.
         */
        public void setText(String text) {
            this.text = text;
        }

        /**
         * Get Defect area code that can be optionally associated with this note
         * @return Defect area code that can be optionally associated with this note
         */
        public String getDefectAreaCode() {
            return defectAreaCode;
        }

        /**
         * Set Defect area code that can be optionally associated with this note
         * @param defectAreaCode
         *            Defect area code that can be optionally associated with this note
         */
        public void setDefectAreaCode(String defectAreaCode) {
            this.defectAreaCode = defectAreaCode;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "ReviewNotePostDTO [ " + 
                "text = " + text + ", " + 
                "defectAreaCode = " + defectAreaCode + 
                " ]";
        }
    }

    // ReviewQaTree DTO
    public static class ReviewQaTreeDTO implements Serializable {

        /**
         * The id of this qa tree
         */
        private String qaTreeId;

        /**
         * The unique 2 char identifier of this defect area. This code is related to a specific qa tree ID which was configured by an Admin and will be used throughout this review.
         */
        private String defectAreaCode;


        /**
         * Creates a new instance of ReviewQaTreeDTO
         */
        public ReviewQaTreeDTO() {}

        /**
         * Get The id of this qa tree
         * @return The id of this qa tree
         */
        public String getQaTreeId() {
            return qaTreeId;
        }

        /**
         * Set The id of this qa tree
         * @param qaTreeId
         *            The id of this qa tree
         */
        public void setQaTreeId(String qaTreeId) {
            this.qaTreeId = qaTreeId;
        }

        /**
         * Get The unique 2 char identifier of this defect area. This code is related to a specific qa tree ID which was configured by an Admin and will be used throughout this review.
         * @return The unique 2 char identifier of this defect area. This code is related to a specific qa tree ID which was configured by an Admin and will be used throughout this review.
         */
        public String getDefectAreaCode() {
            return defectAreaCode;
        }

        /**
         * Set The unique 2 char identifier of this defect area. This code is related to a specific qa tree ID which was configured by an Admin and will be used throughout this review.
         * @param defectAreaCode
         *            The unique 2 char identifier of this defect area. This code is related to a specific qa tree ID which was configured by an Admin and will be used throughout this review.
         */
        public void setDefectAreaCode(String defectAreaCode) {
            this.defectAreaCode = defectAreaCode;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "ReviewQaTreeDTO [ " + 
                "qaTreeId = " + qaTreeId + ", " + 
                "defectAreaCode = " + defectAreaCode + 
                " ]";
        }
    }

    // ReviewField DTO
    public static class ReviewFieldDTO implements Serializable {

        /**
         * The unique identifier for this field
         */
        private String fieldId;

        /**
         * Sort order of this field relative to others displayed in the same panel
         */
        private Integer order;

        /**
         * What type of UI element is used to display this?  Valid values are &#x27;date&#x27;, &#x27;boolean&#x27;, &#x27;integer&#x27;, &#x27;number&#x27;, &#x27;currency&#x27;, and &#x27;select&#x27;, and &#x27;multiselect&#x27;
         */
        private String type;

        /**
         * A collection of condition objects that the UI will use to determine if this field should be displayed (in any defect area or loan summary view).  If no conditions are included the question is displayed.
         */
        private List<ReviewConditionDTO> conditionsToDisplay;

        /**
         * Which section of the loan attributes screen this should be displayed in. Valid values are &#x27;Loan&#x27;, &#x27;Property&#x27;, and &#x27;Borrower&#x27;. If NULL the field should not be displayed in the loan summary
         */
        private String loanAttributeGroup;

        /**
         * Which defect areas, if any, should display this field in their sidebar.  Values are the defect area codes (example: LA)
         */
        private List<String> displayInDefectAreas;

        /**
         * If type=select or multiselect, this is the array of options for the user to choose from
         */
        private List<CommonDetailDTO> selectChoices;

        /**
         * If type=integer, number, or currency this is the minimum allowed value
         */
        private BigDecimal minValue;

        /**
         * If type=integer, number, or currency this is the maximum allowed value
         */
        private BigDecimal maxValue;

        /**
         * The display name of the field (The DB refers to this as FIELD_NAME)
         */
        private String name;

        /**
         * The editable value for this field which is used to determine which questions are displayed to the user
         */
        private String value;

        /**
         * Is this field editable
         */
        private Boolean isEditable;

        /**
         * The values of this field at the time of endorsement (as reported by other systems).  Read-only. For most cases this will be a collection of one value, but in the case of multiple select it will be multiple values.
         */
        private List<String> valuesAtEndorsement;

        /**
         * The values of this field at the time of underwriting (as reported by other systems).  Read-only. For most cases this will be a collection of one value, but in the case of multiple select it will be multiple values.
         */
        private List<String> valuesAtUnderwriting;


        /**
         * Creates a new instance of ReviewFieldDTO
         */
        public ReviewFieldDTO() {}

        /**
         * Get The unique identifier for this field
         * @return The unique identifier for this field
         */
        public String getFieldId() {
            return fieldId;
        }

        /**
         * Set The unique identifier for this field
         * @param fieldId
         *            The unique identifier for this field
         */
        public void setFieldId(String fieldId) {
            this.fieldId = fieldId;
        }

        /**
         * Get Sort order of this field relative to others displayed in the same panel
         * @return Sort order of this field relative to others displayed in the same panel
         */
        public Integer getOrder() {
            return order;
        }

        /**
         * Set Sort order of this field relative to others displayed in the same panel
         * @param order
         *            Sort order of this field relative to others displayed in the same panel
         */
        public void setOrder(Integer order) {
            this.order = order;
        }

        /**
         * Get What type of UI element is used to display this?  Valid values are &#x27;date&#x27;, &#x27;boolean&#x27;, &#x27;integer&#x27;, &#x27;number&#x27;, &#x27;currency&#x27;, and &#x27;select&#x27;, and &#x27;multiselect&#x27;
         * @return What type of UI element is used to display this?  Valid values are &#x27;date&#x27;, &#x27;boolean&#x27;, &#x27;integer&#x27;, &#x27;number&#x27;, &#x27;currency&#x27;, and &#x27;select&#x27;, and &#x27;multiselect&#x27;
         */
        public String getType() {
            return type;
        }

        /**
         * Set What type of UI element is used to display this?  Valid values are &#x27;date&#x27;, &#x27;boolean&#x27;, &#x27;integer&#x27;, &#x27;number&#x27;, &#x27;currency&#x27;, and &#x27;select&#x27;, and &#x27;multiselect&#x27;
         * @param type
         *            What type of UI element is used to display this?  Valid values are &#x27;date&#x27;, &#x27;boolean&#x27;, &#x27;integer&#x27;, &#x27;number&#x27;, &#x27;currency&#x27;, and &#x27;select&#x27;, and &#x27;multiselect&#x27;
         */
        public void setType(String type) {
            this.type = type;
        }

        /**
         * Get A collection of condition objects that the UI will use to determine if this field should be displayed (in any defect area or loan summary view).  If no conditions are included the question is displayed.
         * @return A collection of condition objects that the UI will use to determine if this field should be displayed (in any defect area or loan summary view).  If no conditions are included the question is displayed.
         */
        public List<ReviewConditionDTO> getConditionsToDisplay() {
            return conditionsToDisplay;
        }

        /**
         * Set A collection of condition objects that the UI will use to determine if this field should be displayed (in any defect area or loan summary view).  If no conditions are included the question is displayed.
         * @param conditionsToDisplay
         *            A collection of condition objects that the UI will use to determine if this field should be displayed (in any defect area or loan summary view).  If no conditions are included the question is displayed.
         */
        public void setConditionsToDisplay(List<ReviewConditionDTO> conditionsToDisplay) {
            this.conditionsToDisplay = conditionsToDisplay;
        }

        /**
         * Get Which section of the loan attributes screen this should be displayed in. Valid values are &#x27;Loan&#x27;, &#x27;Property&#x27;, and &#x27;Borrower&#x27;. If NULL the field should not be displayed in the loan summary
         * @return Which section of the loan attributes screen this should be displayed in. Valid values are &#x27;Loan&#x27;, &#x27;Property&#x27;, and &#x27;Borrower&#x27;. If NULL the field should not be displayed in the loan summary
         */
        public String getLoanAttributeGroup() {
            return loanAttributeGroup;
        }

        /**
         * Set Which section of the loan attributes screen this should be displayed in. Valid values are &#x27;Loan&#x27;, &#x27;Property&#x27;, and &#x27;Borrower&#x27;. If NULL the field should not be displayed in the loan summary
         * @param loanAttributeGroup
         *            Which section of the loan attributes screen this should be displayed in. Valid values are &#x27;Loan&#x27;, &#x27;Property&#x27;, and &#x27;Borrower&#x27;. If NULL the field should not be displayed in the loan summary
         */
        public void setLoanAttributeGroup(String loanAttributeGroup) {
            this.loanAttributeGroup = loanAttributeGroup;
        }

        /**
         * Get Which defect areas, if any, should display this field in their sidebar.  Values are the defect area codes (example: LA)
         * @return Which defect areas, if any, should display this field in their sidebar.  Values are the defect area codes (example: LA)
         */
        public List<String> getDisplayInDefectAreas() {
            return displayInDefectAreas;
        }

        /**
         * Set Which defect areas, if any, should display this field in their sidebar.  Values are the defect area codes (example: LA)
         * @param displayInDefectAreas
         *            Which defect areas, if any, should display this field in their sidebar.  Values are the defect area codes (example: LA)
         */
        public void setDisplayInDefectAreas(List<String> displayInDefectAreas) {
            this.displayInDefectAreas = displayInDefectAreas;
        }

        /**
         * Get If type=select or multiselect, this is the array of options for the user to choose from
         * @return If type=select or multiselect, this is the array of options for the user to choose from
         */
        public List<CommonDetailDTO> getSelectChoices() {
            return selectChoices;
        }

        /**
         * Set If type=select or multiselect, this is the array of options for the user to choose from
         * @param selectChoices
         *            If type=select or multiselect, this is the array of options for the user to choose from
         */
        public void setSelectChoices(List<CommonDetailDTO> selectChoices) {
            this.selectChoices = selectChoices;
        }

        /**
         * Get If type=integer, number, or currency this is the minimum allowed value
         * @return If type=integer, number, or currency this is the minimum allowed value
         */
        public BigDecimal getMinValue() {
            return minValue;
        }

        /**
         * Set If type=integer, number, or currency this is the minimum allowed value
         * @param minValue
         *            If type=integer, number, or currency this is the minimum allowed value
         */
        public void setMinValue(BigDecimal minValue) {
            this.minValue = minValue;
        }

        /**
         * Get If type=integer, number, or currency this is the maximum allowed value
         * @return If type=integer, number, or currency this is the maximum allowed value
         */
        public BigDecimal getMaxValue() {
            return maxValue;
        }

        /**
         * Set If type=integer, number, or currency this is the maximum allowed value
         * @param maxValue
         *            If type=integer, number, or currency this is the maximum allowed value
         */
        public void setMaxValue(BigDecimal maxValue) {
            this.maxValue = maxValue;
        }

        /**
         * Get The display name of the field (The DB refers to this as FIELD_NAME)
         * @return The display name of the field (The DB refers to this as FIELD_NAME)
         */
        public String getName() {
            return name;
        }

        /**
         * Set The display name of the field (The DB refers to this as FIELD_NAME)
         * @param name
         *            The display name of the field (The DB refers to this as FIELD_NAME)
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * Get The editable value for this field which is used to determine which questions are displayed to the user
         * @return The editable value for this field which is used to determine which questions are displayed to the user
         */
        public String getValue() {
            return value;
        }

        /**
         * Set The editable value for this field which is used to determine which questions are displayed to the user
         * @param value
         *            The editable value for this field which is used to determine which questions are displayed to the user
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * Get Is this field editable
         * @return Is this field editable
         */
        public Boolean getIsEditable() {
            return isEditable;
        }

        /**
         * Set Is this field editable
         * @param isEditable
         *            Is this field editable
         */
        public void setIsEditable(Boolean isEditable) {
            this.isEditable = isEditable;
        }

        /**
         * Get The values of this field at the time of endorsement (as reported by other systems).  Read-only. For most cases this will be a collection of one value, but in the case of multiple select it will be multiple values.
         * @return The values of this field at the time of endorsement (as reported by other systems).  Read-only. For most cases this will be a collection of one value, but in the case of multiple select it will be multiple values.
         */
        public List<String> getValuesAtEndorsement() {
            return valuesAtEndorsement;
        }

        /**
         * Set The values of this field at the time of endorsement (as reported by other systems).  Read-only. For most cases this will be a collection of one value, but in the case of multiple select it will be multiple values.
         * @param valuesAtEndorsement
         *            The values of this field at the time of endorsement (as reported by other systems).  Read-only. For most cases this will be a collection of one value, but in the case of multiple select it will be multiple values.
         */
        public void setValuesAtEndorsement(List<String> valuesAtEndorsement) {
            this.valuesAtEndorsement = valuesAtEndorsement;
        }

        /**
         * Get The values of this field at the time of underwriting (as reported by other systems).  Read-only. For most cases this will be a collection of one value, but in the case of multiple select it will be multiple values.
         * @return The values of this field at the time of underwriting (as reported by other systems).  Read-only. For most cases this will be a collection of one value, but in the case of multiple select it will be multiple values.
         */
        public List<String> getValuesAtUnderwriting() {
            return valuesAtUnderwriting;
        }

        /**
         * Set The values of this field at the time of underwriting (as reported by other systems).  Read-only. For most cases this will be a collection of one value, but in the case of multiple select it will be multiple values.
         * @param valuesAtUnderwriting
         *            The values of this field at the time of underwriting (as reported by other systems).  Read-only. For most cases this will be a collection of one value, but in the case of multiple select it will be multiple values.
         */
        public void setValuesAtUnderwriting(List<String> valuesAtUnderwriting) {
            this.valuesAtUnderwriting = valuesAtUnderwriting;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "ReviewFieldDTO [ " + 
                "fieldId = " + fieldId + ", " + 
                "order = " + order + ", " + 
                "type = " + type + ", " + 
                "conditionsToDisplay = " + conditionsToDisplay + ", " + 
                "loanAttributeGroup = " + loanAttributeGroup + ", " + 
                "displayInDefectAreas = " + displayInDefectAreas + ", " + 
                "selectChoices = " + selectChoices + ", " + 
                "minValue = " + minValue + ", " + 
                "maxValue = " + maxValue + ", " + 
                "name = " + name + ", " + 
                "value = " + value + ", " + 
                "isEditable = " + isEditable + ", " + 
                "valuesAtEndorsement = " + valuesAtEndorsement + ", " + 
                "valuesAtUnderwriting = " + valuesAtUnderwriting + 
                " ]";
        }
    }

    // ReviewQuestion DTO
    public static class ReviewQuestionDTO implements Serializable {

        /**
         * The question ID.
         */
        private String questionId;

        /**
         * The question number.
         */
        private String questionNumber;

        /**
         * The question unique reference string for this question that is a combination of questionNumber and defect area (e.g. &#x27;BA.002&#x27;). NOTE: This is generated by services when question is created.
         */
        private String questionReference;

        /**
         * The question ID of this question&#x27;s parent (if this question is a child)
         */
        private String parentQuestionId;

        /**
         * Collection of answers which will be used to test if this question can be displayed if this question is a child (i.e. has a parentQuestionId). Each answer here will be checked against the parent question&#x27;s answer with an OR condition. If there are multiple answers on the parent question, this question will display if at least one answer matches at least one parent question answer (i.e. &#x27;intersects&#x27;).
         */
        private List<String> parentQuestionConditionAnswers;

        /**
         * The order in which this question needs to be displayed
         */
        private Integer order;

        /**
         * The actual question text to display.
         */
        private String questionText;

        /**
         * The type of answer that the user will be able to select. (e.g. &#x27;single&#x27; or &#x27;multiple&#x27;)
         */
        private String answerType;

        /**
         * Used to supply potential answers for this question (e.g. For answerType of &#x27;single&#x27;: [Yes, No] or [Yes, No, NA], for answerType of &#x27;multiple&#x27;: [1, 2, 3, ...]). Note that this uses the CommonDetail object which provides a code and description for each potentialAnswer. The description will be used for display (e.g. &#x27;Standard Employment&#x27; or &#x27;Part-time and Seasonal Employment&#x27;), the code will be used by the database and services (e.g. &#x27;1&#x27;, &#x27;2&#x27;).
         */
        private List<CommonDetailDTO> potentialAnswers;

        /**
         * If the user selects this answer code, a finding will be triggered. NOTE: This relates to the answerDetail.code
         */
        private String answerToTriggerFinding;

        /**
         * A collection of condition objects that the UI will use to determine if this question should be displayed. Conditions will relate to loan/defect summary field info. If no conditions are included the question is displayed. NOTE: if this question includes a parentQuestionId and parentQuestionConditionAnswers, that will also be used to display the question.
         */
        private List<ReviewConditionDTO> conditionsToDisplay;

        /**
         * A collection of source Codes that could be selected when logging a finding. The source ID will relate to a source dictionary ojbect (e.g. &#x27;Standard Employment&#x27;, &#x27;Maximum Mortgage Amount&#x27;, etc.)
         */
        private List<String> allowableSourceCodes;

        /**
         * A collection of cause Codes that could be selected when logging a finding. The cause ID will relate to a cause dictionary ojbect (e.g. &#x27;Amount not supported by documentation&#x27;, &#x27;Amounts cannot be confirmed&#x27;, etc.)
         */
        private List<String> allowableCauseCodes;

        /**
         * A collection of severity tier Codes that could be selected when logging a finding.
         */
        private List<String> allowedSeverityCodes;

        /**
         * Collection of qa model questions for this question in the defect area.
         */
        private List<ReviewQuestionDTO> questions;


        /**
         * Creates a new instance of ReviewQuestionDTO
         */
        public ReviewQuestionDTO() {}

        /**
         * Get The question ID.
         * @return The question ID.
         */
        public String getQuestionId() {
            return questionId;
        }

        /**
         * Set The question ID.
         * @param questionId
         *            The question ID.
         */
        public void setQuestionId(String questionId) {
            this.questionId = questionId;
        }

        /**
         * Get The question number.
         * @return The question number.
         */
        public String getQuestionNumber() {
            return questionNumber;
        }

        /**
         * Set The question number.
         * @param questionNumber
         *            The question number.
         */
        public void setQuestionNumber(String questionNumber) {
            this.questionNumber = questionNumber;
        }

        /**
         * Get The question unique reference string for this question that is a combination of questionNumber and defect area (e.g. &#x27;BA.002&#x27;). NOTE: This is generated by services when question is created.
         * @return The question unique reference string for this question that is a combination of questionNumber and defect area (e.g. &#x27;BA.002&#x27;). NOTE: This is generated by services when question is created.
         */
        public String getQuestionReference() {
            return questionReference;
        }

        /**
         * Set The question unique reference string for this question that is a combination of questionNumber and defect area (e.g. &#x27;BA.002&#x27;). NOTE: This is generated by services when question is created.
         * @param questionReference
         *            The question unique reference string for this question that is a combination of questionNumber and defect area (e.g. &#x27;BA.002&#x27;). NOTE: This is generated by services when question is created.
         */
        public void setQuestionReference(String questionReference) {
            this.questionReference = questionReference;
        }

        /**
         * Get The question ID of this question&#x27;s parent (if this question is a child)
         * @return The question ID of this question&#x27;s parent (if this question is a child)
         */
        public String getParentQuestionId() {
            return parentQuestionId;
        }

        /**
         * Set The question ID of this question&#x27;s parent (if this question is a child)
         * @param parentQuestionId
         *            The question ID of this question&#x27;s parent (if this question is a child)
         */
        public void setParentQuestionId(String parentQuestionId) {
            this.parentQuestionId = parentQuestionId;
        }

        /**
         * Get Collection of answers which will be used to test if this question can be displayed if this question is a child (i.e. has a parentQuestionId). Each answer here will be checked against the parent question&#x27;s answer with an OR condition. If there are multiple answers on the parent question, this question will display if at least one answer matches at least one parent question answer (i.e. &#x27;intersects&#x27;).
         * @return Collection of answers which will be used to test if this question can be displayed if this question is a child (i.e. has a parentQuestionId). Each answer here will be checked against the parent question&#x27;s answer with an OR condition. If there are multiple answers on the parent question, this question will display if at least one answer matches at least one parent question answer (i.e. &#x27;intersects&#x27;).
         */
        public List<String> getParentQuestionConditionAnswers() {
            return parentQuestionConditionAnswers;
        }

        /**
         * Set Collection of answers which will be used to test if this question can be displayed if this question is a child (i.e. has a parentQuestionId). Each answer here will be checked against the parent question&#x27;s answer with an OR condition. If there are multiple answers on the parent question, this question will display if at least one answer matches at least one parent question answer (i.e. &#x27;intersects&#x27;).
         * @param parentQuestionConditionAnswers
         *            Collection of answers which will be used to test if this question can be displayed if this question is a child (i.e. has a parentQuestionId). Each answer here will be checked against the parent question&#x27;s answer with an OR condition. If there are multiple answers on the parent question, this question will display if at least one answer matches at least one parent question answer (i.e. &#x27;intersects&#x27;).
         */
        public void setParentQuestionConditionAnswers(List<String> parentQuestionConditionAnswers) {
            this.parentQuestionConditionAnswers = parentQuestionConditionAnswers;
        }

        /**
         * Get The order in which this question needs to be displayed
         * @return The order in which this question needs to be displayed
         */
        public Integer getOrder() {
            return order;
        }

        /**
         * Set The order in which this question needs to be displayed
         * @param order
         *            The order in which this question needs to be displayed
         */
        public void setOrder(Integer order) {
            this.order = order;
        }

        /**
         * Get The actual question text to display.
         * @return The actual question text to display.
         */
        public String getQuestionText() {
            return questionText;
        }

        /**
         * Set The actual question text to display.
         * @param questionText
         *            The actual question text to display.
         */
        public void setQuestionText(String questionText) {
            this.questionText = questionText;
        }

        /**
         * Get The type of answer that the user will be able to select. (e.g. &#x27;single&#x27; or &#x27;multiple&#x27;)
         * @return The type of answer that the user will be able to select. (e.g. &#x27;single&#x27; or &#x27;multiple&#x27;)
         */
        public String getAnswerType() {
            return answerType;
        }

        /**
         * Set The type of answer that the user will be able to select. (e.g. &#x27;single&#x27; or &#x27;multiple&#x27;)
         * @param answerType
         *            The type of answer that the user will be able to select. (e.g. &#x27;single&#x27; or &#x27;multiple&#x27;)
         */
        public void setAnswerType(String answerType) {
            this.answerType = answerType;
        }

        /**
         * Get Used to supply potential answers for this question (e.g. For answerType of &#x27;single&#x27;: [Yes, No] or [Yes, No, NA], for answerType of &#x27;multiple&#x27;: [1, 2, 3, ...]). Note that this uses the CommonDetail object which provides a code and description for each potentialAnswer. The description will be used for display (e.g. &#x27;Standard Employment&#x27; or &#x27;Part-time and Seasonal Employment&#x27;), the code will be used by the database and services (e.g. &#x27;1&#x27;, &#x27;2&#x27;).
         * @return Used to supply potential answers for this question (e.g. For answerType of &#x27;single&#x27;: [Yes, No] or [Yes, No, NA], for answerType of &#x27;multiple&#x27;: [1, 2, 3, ...]). Note that this uses the CommonDetail object which provides a code and description for each potentialAnswer. The description will be used for display (e.g. &#x27;Standard Employment&#x27; or &#x27;Part-time and Seasonal Employment&#x27;), the code will be used by the database and services (e.g. &#x27;1&#x27;, &#x27;2&#x27;).
         */
        public List<CommonDetailDTO> getPotentialAnswers() {
            return potentialAnswers;
        }

        /**
         * Set Used to supply potential answers for this question (e.g. For answerType of &#x27;single&#x27;: [Yes, No] or [Yes, No, NA], for answerType of &#x27;multiple&#x27;: [1, 2, 3, ...]). Note that this uses the CommonDetail object which provides a code and description for each potentialAnswer. The description will be used for display (e.g. &#x27;Standard Employment&#x27; or &#x27;Part-time and Seasonal Employment&#x27;), the code will be used by the database and services (e.g. &#x27;1&#x27;, &#x27;2&#x27;).
         * @param potentialAnswers
         *            Used to supply potential answers for this question (e.g. For answerType of &#x27;single&#x27;: [Yes, No] or [Yes, No, NA], for answerType of &#x27;multiple&#x27;: [1, 2, 3, ...]). Note that this uses the CommonDetail object which provides a code and description for each potentialAnswer. The description will be used for display (e.g. &#x27;Standard Employment&#x27; or &#x27;Part-time and Seasonal Employment&#x27;), the code will be used by the database and services (e.g. &#x27;1&#x27;, &#x27;2&#x27;).
         */
        public void setPotentialAnswers(List<CommonDetailDTO> potentialAnswers) {
            this.potentialAnswers = potentialAnswers;
        }

        /**
         * Get If the user selects this answer code, a finding will be triggered. NOTE: This relates to the answerDetail.code
         * @return If the user selects this answer code, a finding will be triggered. NOTE: This relates to the answerDetail.code
         */
        public String getAnswerToTriggerFinding() {
            return answerToTriggerFinding;
        }

        /**
         * Set If the user selects this answer code, a finding will be triggered. NOTE: This relates to the answerDetail.code
         * @param answerToTriggerFinding
         *            If the user selects this answer code, a finding will be triggered. NOTE: This relates to the answerDetail.code
         */
        public void setAnswerToTriggerFinding(String answerToTriggerFinding) {
            this.answerToTriggerFinding = answerToTriggerFinding;
        }

        /**
         * Get A collection of condition objects that the UI will use to determine if this question should be displayed. Conditions will relate to loan/defect summary field info. If no conditions are included the question is displayed. NOTE: if this question includes a parentQuestionId and parentQuestionConditionAnswers, that will also be used to display the question.
         * @return A collection of condition objects that the UI will use to determine if this question should be displayed. Conditions will relate to loan/defect summary field info. If no conditions are included the question is displayed. NOTE: if this question includes a parentQuestionId and parentQuestionConditionAnswers, that will also be used to display the question.
         */
        public List<ReviewConditionDTO> getConditionsToDisplay() {
            return conditionsToDisplay;
        }

        /**
         * Set A collection of condition objects that the UI will use to determine if this question should be displayed. Conditions will relate to loan/defect summary field info. If no conditions are included the question is displayed. NOTE: if this question includes a parentQuestionId and parentQuestionConditionAnswers, that will also be used to display the question.
         * @param conditionsToDisplay
         *            A collection of condition objects that the UI will use to determine if this question should be displayed. Conditions will relate to loan/defect summary field info. If no conditions are included the question is displayed. NOTE: if this question includes a parentQuestionId and parentQuestionConditionAnswers, that will also be used to display the question.
         */
        public void setConditionsToDisplay(List<ReviewConditionDTO> conditionsToDisplay) {
            this.conditionsToDisplay = conditionsToDisplay;
        }

        /**
         * Get A collection of source Codes that could be selected when logging a finding. The source ID will relate to a source dictionary ojbect (e.g. &#x27;Standard Employment&#x27;, &#x27;Maximum Mortgage Amount&#x27;, etc.)
         * @return A collection of source Codes that could be selected when logging a finding. The source ID will relate to a source dictionary ojbect (e.g. &#x27;Standard Employment&#x27;, &#x27;Maximum Mortgage Amount&#x27;, etc.)
         */
        public List<String> getAllowableSourceCodes() {
            return allowableSourceCodes;
        }

        /**
         * Set A collection of source Codes that could be selected when logging a finding. The source ID will relate to a source dictionary ojbect (e.g. &#x27;Standard Employment&#x27;, &#x27;Maximum Mortgage Amount&#x27;, etc.)
         * @param allowableSourceCodes
         *            A collection of source Codes that could be selected when logging a finding. The source ID will relate to a source dictionary ojbect (e.g. &#x27;Standard Employment&#x27;, &#x27;Maximum Mortgage Amount&#x27;, etc.)
         */
        public void setAllowableSourceCodes(List<String> allowableSourceCodes) {
            this.allowableSourceCodes = allowableSourceCodes;
        }

        /**
         * Get A collection of cause Codes that could be selected when logging a finding. The cause ID will relate to a cause dictionary ojbect (e.g. &#x27;Amount not supported by documentation&#x27;, &#x27;Amounts cannot be confirmed&#x27;, etc.)
         * @return A collection of cause Codes that could be selected when logging a finding. The cause ID will relate to a cause dictionary ojbect (e.g. &#x27;Amount not supported by documentation&#x27;, &#x27;Amounts cannot be confirmed&#x27;, etc.)
         */
        public List<String> getAllowableCauseCodes() {
            return allowableCauseCodes;
        }

        /**
         * Set A collection of cause Codes that could be selected when logging a finding. The cause ID will relate to a cause dictionary ojbect (e.g. &#x27;Amount not supported by documentation&#x27;, &#x27;Amounts cannot be confirmed&#x27;, etc.)
         * @param allowableCauseCodes
         *            A collection of cause Codes that could be selected when logging a finding. The cause ID will relate to a cause dictionary ojbect (e.g. &#x27;Amount not supported by documentation&#x27;, &#x27;Amounts cannot be confirmed&#x27;, etc.)
         */
        public void setAllowableCauseCodes(List<String> allowableCauseCodes) {
            this.allowableCauseCodes = allowableCauseCodes;
        }

        /**
         * Get A collection of severity tier Codes that could be selected when logging a finding.
         * @return A collection of severity tier Codes that could be selected when logging a finding.
         */
        public List<String> getAllowedSeverityCodes() {
            return allowedSeverityCodes;
        }

        /**
         * Set A collection of severity tier Codes that could be selected when logging a finding.
         * @param allowedSeverityCodes
         *            A collection of severity tier Codes that could be selected when logging a finding.
         */
        public void setAllowedSeverityCodes(List<String> allowedSeverityCodes) {
            this.allowedSeverityCodes = allowedSeverityCodes;
        }

        /**
         * Get Collection of qa model questions for this question in the defect area.
         * @return Collection of qa model questions for this question in the defect area.
         */
        public List<ReviewQuestionDTO> getQuestions() {
            return questions;
        }

        /**
         * Set Collection of qa model questions for this question in the defect area.
         * @param questions
         *            Collection of qa model questions for this question in the defect area.
         */
        public void setQuestions(List<ReviewQuestionDTO> questions) {
            this.questions = questions;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "ReviewQuestionDTO [ " + 
                "questionId = " + questionId + ", " + 
                "questionNumber = " + questionNumber + ", " + 
                "questionReference = " + questionReference + ", " + 
                "parentQuestionId = " + parentQuestionId + ", " + 
                "parentQuestionConditionAnswers = " + parentQuestionConditionAnswers + ", " + 
                "order = " + order + ", " + 
                "questionText = " + questionText + ", " + 
                "answerType = " + answerType + ", " + 
                "potentialAnswers = " + potentialAnswers + ", " + 
                "answerToTriggerFinding = " + answerToTriggerFinding + ", " + 
                "conditionsToDisplay = " + conditionsToDisplay + ", " + 
                "allowableSourceCodes = " + allowableSourceCodes + ", " + 
                "allowableCauseCodes = " + allowableCauseCodes + ", " + 
                "allowedSeverityCodes = " + allowedSeverityCodes + ", " + 
                "questions = " + questions + 
                " ]";
        }
    }

    // ReviewCondition DTO
    public static class ReviewConditionDTO implements Serializable {

        /**
         * The ID for this condition
         */
        private String conditionId;

        /**
         * The entity name the qa model loan attribute is referencing.
         */
        private String entityName;

        /**
         * The loan attribute id this condition is referencing.
         */
        private String attributeId;

        /**
         * The summary field that this condition will test.
         */
        private String fieldName;

        /**
         * The operator used to test the field value (e.g. &#x27;&gt;&#x27;, &#x27;&lt;&#x27;, &#x27;=&#x27;, &#x27;!=&#x27;, &#x27;isPresent&#x27;, &#x27;in&#x27;, &#x27;notIn&#x27;, &#x27;intersects&#x27;). If the &#x27;isPresent&#x27; operator is used, no comparisonValues will be given, and the field value will be inspected for a value.
         */
        private String operator;

        /**
         * A collection of values to compare against the field value. If more then one item in the colleciton, they are treated based on the operator: For example, &#x27;in&#x27; will use OR (e.g. field.value in [1, 2] could be tested for field.value = 1 OR field.value = 2). For &#x27;&gt;&#x27;, &#x27;&lt;&#x27;, &#x27;=&#x27;, &#x27;!=&#x27;, and &#x27;notIn&#x27;, AND is used (e.g. field.value &lt; [1, 2] will be tested for field.value &lt; 1 AND field.value &lt; 2). For &#x27;intersects&#x27; each value in the collection of values will use OR to check each item in the comparisonValues collection. If the operator is &#x27;&lt;&#x27; or &#x27;&gt;&#x27; the value(s) should be converted into a number.
         */
        private List<String> comparisonValues;


        /**
         * Creates a new instance of ReviewConditionDTO
         */
        public ReviewConditionDTO() {}

        /**
         * Get The ID for this condition
         * @return The ID for this condition
         */
        public String getConditionId() {
            return conditionId;
        }

        /**
         * Set The ID for this condition
         * @param conditionId
         *            The ID for this condition
         */
        public void setConditionId(String conditionId) {
            this.conditionId = conditionId;
        }

        /**
         * Get The entity name the qa model loan attribute is referencing.
         * @return The entity name the qa model loan attribute is referencing.
         */
        public String getEntityName() {
            return entityName;
        }

        /**
         * Set The entity name the qa model loan attribute is referencing.
         * @param entityName
         *            The entity name the qa model loan attribute is referencing.
         */
        public void setEntityName(String entityName) {
            this.entityName = entityName;
        }

        /**
         * Get The loan attribute id this condition is referencing.
         * @return The loan attribute id this condition is referencing.
         */
        public String getAttributeId() {
            return attributeId;
        }

        /**
         * Set The loan attribute id this condition is referencing.
         * @param attributeId
         *            The loan attribute id this condition is referencing.
         */
        public void setAttributeId(String attributeId) {
            this.attributeId = attributeId;
        }

        /**
         * Get The summary field that this condition will test.
         * @return The summary field that this condition will test.
         */
        public String getFieldName() {
            return fieldName;
        }

        /**
         * Set The summary field that this condition will test.
         * @param fieldName
         *            The summary field that this condition will test.
         */
        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        /**
         * Get The operator used to test the field value (e.g. &#x27;&gt;&#x27;, &#x27;&lt;&#x27;, &#x27;=&#x27;, &#x27;!=&#x27;, &#x27;isPresent&#x27;, &#x27;in&#x27;, &#x27;notIn&#x27;, &#x27;intersects&#x27;). If the &#x27;isPresent&#x27; operator is used, no comparisonValues will be given, and the field value will be inspected for a value.
         * @return The operator used to test the field value (e.g. &#x27;&gt;&#x27;, &#x27;&lt;&#x27;, &#x27;=&#x27;, &#x27;!=&#x27;, &#x27;isPresent&#x27;, &#x27;in&#x27;, &#x27;notIn&#x27;, &#x27;intersects&#x27;). If the &#x27;isPresent&#x27; operator is used, no comparisonValues will be given, and the field value will be inspected for a value.
         */
        public String getOperator() {
            return operator;
        }

        /**
         * Set The operator used to test the field value (e.g. &#x27;&gt;&#x27;, &#x27;&lt;&#x27;, &#x27;=&#x27;, &#x27;!=&#x27;, &#x27;isPresent&#x27;, &#x27;in&#x27;, &#x27;notIn&#x27;, &#x27;intersects&#x27;). If the &#x27;isPresent&#x27; operator is used, no comparisonValues will be given, and the field value will be inspected for a value.
         * @param operator
         *            The operator used to test the field value (e.g. &#x27;&gt;&#x27;, &#x27;&lt;&#x27;, &#x27;=&#x27;, &#x27;!=&#x27;, &#x27;isPresent&#x27;, &#x27;in&#x27;, &#x27;notIn&#x27;, &#x27;intersects&#x27;). If the &#x27;isPresent&#x27; operator is used, no comparisonValues will be given, and the field value will be inspected for a value.
         */
        public void setOperator(String operator) {
            this.operator = operator;
        }

        /**
         * Get A collection of values to compare against the field value. If more then one item in the colleciton, they are treated based on the operator: For example, &#x27;in&#x27; will use OR (e.g. field.value in [1, 2] could be tested for field.value = 1 OR field.value = 2). For &#x27;&gt;&#x27;, &#x27;&lt;&#x27;, &#x27;=&#x27;, &#x27;!=&#x27;, and &#x27;notIn&#x27;, AND is used (e.g. field.value &lt; [1, 2] will be tested for field.value &lt; 1 AND field.value &lt; 2). For &#x27;intersects&#x27; each value in the collection of values will use OR to check each item in the comparisonValues collection. If the operator is &#x27;&lt;&#x27; or &#x27;&gt;&#x27; the value(s) should be converted into a number.
         * @return A collection of values to compare against the field value. If more then one item in the colleciton, they are treated based on the operator: For example, &#x27;in&#x27; will use OR (e.g. field.value in [1, 2] could be tested for field.value = 1 OR field.value = 2). For &#x27;&gt;&#x27;, &#x27;&lt;&#x27;, &#x27;=&#x27;, &#x27;!=&#x27;, and &#x27;notIn&#x27;, AND is used (e.g. field.value &lt; [1, 2] will be tested for field.value &lt; 1 AND field.value &lt; 2). For &#x27;intersects&#x27; each value in the collection of values will use OR to check each item in the comparisonValues collection. If the operator is &#x27;&lt;&#x27; or &#x27;&gt;&#x27; the value(s) should be converted into a number.
         */
        public List<String> getComparisonValues() {
            return comparisonValues;
        }

        /**
         * Set A collection of values to compare against the field value. If more then one item in the colleciton, they are treated based on the operator: For example, &#x27;in&#x27; will use OR (e.g. field.value in [1, 2] could be tested for field.value = 1 OR field.value = 2). For &#x27;&gt;&#x27;, &#x27;&lt;&#x27;, &#x27;=&#x27;, &#x27;!=&#x27;, and &#x27;notIn&#x27;, AND is used (e.g. field.value &lt; [1, 2] will be tested for field.value &lt; 1 AND field.value &lt; 2). For &#x27;intersects&#x27; each value in the collection of values will use OR to check each item in the comparisonValues collection. If the operator is &#x27;&lt;&#x27; or &#x27;&gt;&#x27; the value(s) should be converted into a number.
         * @param comparisonValues
         *            A collection of values to compare against the field value. If more then one item in the colleciton, they are treated based on the operator: For example, &#x27;in&#x27; will use OR (e.g. field.value in [1, 2] could be tested for field.value = 1 OR field.value = 2). For &#x27;&gt;&#x27;, &#x27;&lt;&#x27;, &#x27;=&#x27;, &#x27;!=&#x27;, and &#x27;notIn&#x27;, AND is used (e.g. field.value &lt; [1, 2] will be tested for field.value &lt; 1 AND field.value &lt; 2). For &#x27;intersects&#x27; each value in the collection of values will use OR to check each item in the comparisonValues collection. If the operator is &#x27;&lt;&#x27; or &#x27;&gt;&#x27; the value(s) should be converted into a number.
         */
        public void setComparisonValues(List<String> comparisonValues) {
            this.comparisonValues = comparisonValues;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "ReviewConditionDTO [ " + 
                "conditionId = " + conditionId + ", " + 
                "entityName = " + entityName + ", " + 
                "attributeId = " + attributeId + ", " + 
                "fieldName = " + fieldName + ", " + 
                "operator = " + operator + ", " + 
                "comparisonValues = " + comparisonValues + 
                " ]";
        }
    }

    // ReviewReferral DTO
    public static class ReviewReferralDTO implements Serializable {

        /**
         * Notes for HUD (Internal) type Referral
         */
        private String hudNotes;

        /**
         * Notes for external type Referral
         */
        private String externalNotes;

        /**
         * Notes for OIG type Referral
         */
        private String oigNotes;


        /**
         * Creates a new instance of ReviewReferralDTO
         */
        public ReviewReferralDTO() {}

        /**
         * Get Notes for HUD (Internal) type Referral
         * @return Notes for HUD (Internal) type Referral
         */
        public String getHudNotes() {
            return hudNotes;
        }

        /**
         * Set Notes for HUD (Internal) type Referral
         * @param hudNotes
         *            Notes for HUD (Internal) type Referral
         */
        public void setHudNotes(String hudNotes) {
            this.hudNotes = hudNotes;
        }

        /**
         * Get Notes for external type Referral
         * @return Notes for external type Referral
         */
        public String getExternalNotes() {
            return externalNotes;
        }

        /**
         * Set Notes for external type Referral
         * @param externalNotes
         *            Notes for external type Referral
         */
        public void setExternalNotes(String externalNotes) {
            this.externalNotes = externalNotes;
        }

        /**
         * Get Notes for OIG type Referral
         * @return Notes for OIG type Referral
         */
        public String getOigNotes() {
            return oigNotes;
        }

        /**
         * Set Notes for OIG type Referral
         * @param oigNotes
         *            Notes for OIG type Referral
         */
        public void setOigNotes(String oigNotes) {
            this.oigNotes = oigNotes;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "ReviewReferralDTO [ " + 
                "hudNotes = " + hudNotes + ", " + 
                "externalNotes = " + externalNotes + ", " + 
                "oigNotes = " + oigNotes + 
                " ]";
        }
    }

    // Referral DTO
    public static class ReferralDTO implements Serializable {

        /**
         * A type
         */
        private String type;

        /**
         * Source
         */
        private String source;

        /**
         * Notes
         */
        private String notes;


        /**
         * Creates a new instance of ReferralDTO
         */
        public ReferralDTO() {}

        /**
         * Get A type
         * @return A type
         */
        public String getType() {
            return type;
        }

        /**
         * Set A type
         * @param type
         *            A type
         */
        public void setType(String type) {
            this.type = type;
        }

        /**
         * Get Source
         * @return Source
         */
        public String getSource() {
            return source;
        }

        /**
         * Set Source
         * @param source
         *            Source
         */
        public void setSource(String source) {
            this.source = source;
        }

        /**
         * Get Notes
         * @return Notes
         */
        public String getNotes() {
            return notes;
        }

        /**
         * Set Notes
         * @param notes
         *            Notes
         */
        public void setNotes(String notes) {
            this.notes = notes;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "ReferralDTO [ " + 
                "type = " + type + ", " + 
                "source = " + source + ", " + 
                "notes = " + notes + 
                " ]";
        }
    }

    // ReviewAnswer DTO
    public static class ReviewAnswerDTO implements Serializable {

        /**
         * The unique answer ID that can be used to update the answer
         */
        private String answerId;

        /**
         * The answer ID to a question for a specific loan review level
         */
        private String reviewLevelId;

        /**
         * The ID to the question this answer is for
         */
        private String questionId;

        /**
         * The qa tree ID related to this review
         */
        private String qaTreeId;

        /**
         * The actual answer detail(s) to the qustion (e.g. [{code: &#x27;Yes&#x27;, description: &#x27;Yes&#x27;}], or for a multiple choice: [{code:&#x27;1&#x27;, description: &#x27;Standard Employment&#x27;}, {code:&#x27;3&#x27;, description:&#x27;Part-time and Seasonal Employment&#x27;}])
         */
        private List<CommonDetailDTO> answers;


        /**
         * Creates a new instance of ReviewAnswerDTO
         */
        public ReviewAnswerDTO() {}

        /**
         * Get The unique answer ID that can be used to update the answer
         * @return The unique answer ID that can be used to update the answer
         */
        public String getAnswerId() {
            return answerId;
        }

        /**
         * Set The unique answer ID that can be used to update the answer
         * @param answerId
         *            The unique answer ID that can be used to update the answer
         */
        public void setAnswerId(String answerId) {
            this.answerId = answerId;
        }

        /**
         * Get The answer ID to a question for a specific loan review level
         * @return The answer ID to a question for a specific loan review level
         */
        public String getReviewLevelId() {
            return reviewLevelId;
        }

        /**
         * Set The answer ID to a question for a specific loan review level
         * @param reviewLevelId
         *            The answer ID to a question for a specific loan review level
         */
        public void setReviewLevelId(String reviewLevelId) {
            this.reviewLevelId = reviewLevelId;
        }

        /**
         * Get The ID to the question this answer is for
         * @return The ID to the question this answer is for
         */
        public String getQuestionId() {
            return questionId;
        }

        /**
         * Set The ID to the question this answer is for
         * @param questionId
         *            The ID to the question this answer is for
         */
        public void setQuestionId(String questionId) {
            this.questionId = questionId;
        }

        /**
         * Get The qa tree ID related to this review
         * @return The qa tree ID related to this review
         */
        public String getQaTreeId() {
            return qaTreeId;
        }

        /**
         * Set The qa tree ID related to this review
         * @param qaTreeId
         *            The qa tree ID related to this review
         */
        public void setQaTreeId(String qaTreeId) {
            this.qaTreeId = qaTreeId;
        }

        /**
         * Get The actual answer detail(s) to the qustion (e.g. [{code: &#x27;Yes&#x27;, description: &#x27;Yes&#x27;}], or for a multiple choice: [{code:&#x27;1&#x27;, description: &#x27;Standard Employment&#x27;}, {code:&#x27;3&#x27;, description:&#x27;Part-time and Seasonal Employment&#x27;}])
         * @return The actual answer detail(s) to the qustion (e.g. [{code: &#x27;Yes&#x27;, description: &#x27;Yes&#x27;}], or for a multiple choice: [{code:&#x27;1&#x27;, description: &#x27;Standard Employment&#x27;}, {code:&#x27;3&#x27;, description:&#x27;Part-time and Seasonal Employment&#x27;}])
         */
        public List<CommonDetailDTO> getAnswers() {
            return answers;
        }

        /**
         * Set The actual answer detail(s) to the qustion (e.g. [{code: &#x27;Yes&#x27;, description: &#x27;Yes&#x27;}], or for a multiple choice: [{code:&#x27;1&#x27;, description: &#x27;Standard Employment&#x27;}, {code:&#x27;3&#x27;, description:&#x27;Part-time and Seasonal Employment&#x27;}])
         * @param answers
         *            The actual answer detail(s) to the qustion (e.g. [{code: &#x27;Yes&#x27;, description: &#x27;Yes&#x27;}], or for a multiple choice: [{code:&#x27;1&#x27;, description: &#x27;Standard Employment&#x27;}, {code:&#x27;3&#x27;, description:&#x27;Part-time and Seasonal Employment&#x27;}])
         */
        public void setAnswers(List<CommonDetailDTO> answers) {
            this.answers = answers;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "ReviewAnswerDTO [ " + 
                "answerId = " + answerId + ", " + 
                "reviewLevelId = " + reviewLevelId + ", " + 
                "questionId = " + questionId + ", " + 
                "qaTreeId = " + qaTreeId + ", " + 
                "answers = " + answers + 
                " ]";
        }
    }

    // ReviewFinding DTO
    public static class ReviewFindingDTO implements Serializable {

        /**
         * The finding ID.
         */
        private String findingId;

        /**
         * The review level ID.
         */
        private String reviewLevelId;

        /**
         * The review level ID when the finding was created.
         */
        private String originalReviewLevelId;

        /**
         * Defect code that is involved in this review (based on the review type).  The display text of this code can be retrieved from the &#x27;defectAreaDictionary&#x27; service.
         */
        private String defectAreaCode;

        /**
         * A collection of question IDs that this finding is related to.
         */
        private List<String> questionIds;

        /**
         * The source code that was selected for this finding. The source code will relate to a source dictionary ojbect (e.g. &#x27;Standard Employment&#x27;, &#x27;Maximum Mortgage Amount&#x27;, etc.)
         */
        private String selectedSourceCode;

        /**
         * The cause code that was selected for this finding. The cause code will relate to a cause dictionary ojbect (e.g. &#x27;Amount not supported by documentation&#x27;, &#x27;Amounts cannot be confirmed&#x27;, etc.)
         */
        private String selectedCauseCode;

        /**
         * The tier (severity) that was selected for this finding.
         */
        private String selectedTierCode;

        /**
         * A note to the lender for this finding.
         */
        private String commentToLender;

        /**
         * The rating given to this finding which is based on defect area, source, cause, and severity tier. Known codes: &#x27;C&#x27; = Conforming, &#x27;D&#x27; = Deficient, &#x27;M&#x27; = Mitigated, &#x27;R&#x27; = Remediated, &#x27;U&#x27; = Unacceptable. Use the ratingcodedictionary API to translate code to description.
         */
        private String ratingCode;

        /**
         * The date when the lender responded to this finding
         */
        private Date lenderResponseDate;

        /**
         * The name of the user (lender) that responded to this finding. (e.g. &#x27;John Franklin&#x27;)
         */
        private String lenderResponderName;

        /**
         * The lender&#x27;s response to this finding.
         */
        private String lenderResponseComment;

        /**
         * A collection of files that were uploaded as response documents
         */
        private List<DocumentDTO> lenderResponseDocuments;

        /**
         * The date if the lender request response was expired
         */
        private Date lenderResponseExpiredDate;

        /**
         * The reviewer is satisfied with the lender response. When this is set to true, the services will update the ratingCode to &#x27;M&#x27;
         */
        private Boolean reviewerResponseMitigated;

        /**
         * the reviewer has decided that the lenders response is an acceptable remediation to the finding and can capture an amount. When this is set, the services will update the ratingCode to &#x27;R&#x27;
         */
        private RemediationResponseDTO reviewerResponseRemediated;

        /**
         * The reviewer is making an adjustment to the fining. Possible values will be &#x27;C&#x27;, &#x27;1&#x27;, &#x27;2&#x27;, &#x27;3&#x27;, &#x27;4&#x27;. The numbers here represent a new selectedTierCode where as the &#x27;C&#x27; represents &#x27;Conforming&#x27; no matter the current severity tier. The services will use the suggestion and update the ratingCode and selectedTierCode
         */
        private String reviewerResponseAdjusted;

        /**
         * If this finding was created as an adhoc finding, this flag will be set to true.
         */
        private Boolean isAdhoc;

        /**
         * The forename of the reviewer.
         */
        private String nameFirst;

        /**
         * The family name of the reviewer.
         */
        private String nameLast;

        /**
         * Date and time when the note was last updated (or created if no updates).
         */
        private Date lastUpdated;


        /**
         * Creates a new instance of ReviewFindingDTO
         */
        public ReviewFindingDTO() {}

        /**
         * Get The finding ID.
         * @return The finding ID.
         */
        public String getFindingId() {
            return findingId;
        }

        /**
         * Set The finding ID.
         * @param findingId
         *            The finding ID.
         */
        public void setFindingId(String findingId) {
            this.findingId = findingId;
        }

        /**
         * Get The review level ID.
         * @return The review level ID.
         */
        public String getReviewLevelId() {
            return reviewLevelId;
        }

        /**
         * Set The review level ID.
         * @param reviewLevelId
         *            The review level ID.
         */
        public void setReviewLevelId(String reviewLevelId) {
            this.reviewLevelId = reviewLevelId;
        }

        /**
         * Get The review level ID when the finding was created.
         * @return The review level ID when the finding was created.
         */
        public String getOriginalReviewLevelId() {
            return originalReviewLevelId;
        }

        /**
         * Set The review level ID when the finding was created.
         * @param originalReviewLevelId
         *            The review level ID when the finding was created.
         */
        public void setOriginalReviewLevelId(String originalReviewLevelId) {
            this.originalReviewLevelId = originalReviewLevelId;
        }

        /**
         * Get Defect code that is involved in this review (based on the review type).  The display text of this code can be retrieved from the &#x27;defectAreaDictionary&#x27; service.
         * @return Defect code that is involved in this review (based on the review type).  The display text of this code can be retrieved from the &#x27;defectAreaDictionary&#x27; service.
         */
        public String getDefectAreaCode() {
            return defectAreaCode;
        }

        /**
         * Set Defect code that is involved in this review (based on the review type).  The display text of this code can be retrieved from the &#x27;defectAreaDictionary&#x27; service.
         * @param defectAreaCode
         *            Defect code that is involved in this review (based on the review type).  The display text of this code can be retrieved from the &#x27;defectAreaDictionary&#x27; service.
         */
        public void setDefectAreaCode(String defectAreaCode) {
            this.defectAreaCode = defectAreaCode;
        }

        /**
         * Get A collection of question IDs that this finding is related to.
         * @return A collection of question IDs that this finding is related to.
         */
        public List<String> getQuestionIds() {
            return questionIds;
        }

        /**
         * Set A collection of question IDs that this finding is related to.
         * @param questionIds
         *            A collection of question IDs that this finding is related to.
         */
        public void setQuestionIds(List<String> questionIds) {
            this.questionIds = questionIds;
        }

        /**
         * Get The source code that was selected for this finding. The source code will relate to a source dictionary ojbect (e.g. &#x27;Standard Employment&#x27;, &#x27;Maximum Mortgage Amount&#x27;, etc.)
         * @return The source code that was selected for this finding. The source code will relate to a source dictionary ojbect (e.g. &#x27;Standard Employment&#x27;, &#x27;Maximum Mortgage Amount&#x27;, etc.)
         */
        public String getSelectedSourceCode() {
            return selectedSourceCode;
        }

        /**
         * Set The source code that was selected for this finding. The source code will relate to a source dictionary ojbect (e.g. &#x27;Standard Employment&#x27;, &#x27;Maximum Mortgage Amount&#x27;, etc.)
         * @param selectedSourceCode
         *            The source code that was selected for this finding. The source code will relate to a source dictionary ojbect (e.g. &#x27;Standard Employment&#x27;, &#x27;Maximum Mortgage Amount&#x27;, etc.)
         */
        public void setSelectedSourceCode(String selectedSourceCode) {
            this.selectedSourceCode = selectedSourceCode;
        }

        /**
         * Get The cause code that was selected for this finding. The cause code will relate to a cause dictionary ojbect (e.g. &#x27;Amount not supported by documentation&#x27;, &#x27;Amounts cannot be confirmed&#x27;, etc.)
         * @return The cause code that was selected for this finding. The cause code will relate to a cause dictionary ojbect (e.g. &#x27;Amount not supported by documentation&#x27;, &#x27;Amounts cannot be confirmed&#x27;, etc.)
         */
        public String getSelectedCauseCode() {
            return selectedCauseCode;
        }

        /**
         * Set The cause code that was selected for this finding. The cause code will relate to a cause dictionary ojbect (e.g. &#x27;Amount not supported by documentation&#x27;, &#x27;Amounts cannot be confirmed&#x27;, etc.)
         * @param selectedCauseCode
         *            The cause code that was selected for this finding. The cause code will relate to a cause dictionary ojbect (e.g. &#x27;Amount not supported by documentation&#x27;, &#x27;Amounts cannot be confirmed&#x27;, etc.)
         */
        public void setSelectedCauseCode(String selectedCauseCode) {
            this.selectedCauseCode = selectedCauseCode;
        }

        /**
         * Get The tier (severity) that was selected for this finding.
         * @return The tier (severity) that was selected for this finding.
         */
        public String getSelectedTierCode() {
            return selectedTierCode;
        }

        /**
         * Set The tier (severity) that was selected for this finding.
         * @param selectedTierCode
         *            The tier (severity) that was selected for this finding.
         */
        public void setSelectedTierCode(String selectedTierCode) {
            this.selectedTierCode = selectedTierCode;
        }

        /**
         * Get A note to the lender for this finding.
         * @return A note to the lender for this finding.
         */
        public String getCommentToLender() {
            return commentToLender;
        }

        /**
         * Set A note to the lender for this finding.
         * @param commentToLender
         *            A note to the lender for this finding.
         */
        public void setCommentToLender(String commentToLender) {
            this.commentToLender = commentToLender;
        }

        /**
         * Get The rating given to this finding which is based on defect area, source, cause, and severity tier. Known codes: &#x27;C&#x27; = Conforming, &#x27;D&#x27; = Deficient, &#x27;M&#x27; = Mitigated, &#x27;R&#x27; = Remediated, &#x27;U&#x27; = Unacceptable. Use the ratingcodedictionary API to translate code to description.
         * @return The rating given to this finding which is based on defect area, source, cause, and severity tier. Known codes: &#x27;C&#x27; = Conforming, &#x27;D&#x27; = Deficient, &#x27;M&#x27; = Mitigated, &#x27;R&#x27; = Remediated, &#x27;U&#x27; = Unacceptable. Use the ratingcodedictionary API to translate code to description.
         */
        public String getRatingCode() {
            return ratingCode;
        }

        /**
         * Set The rating given to this finding which is based on defect area, source, cause, and severity tier. Known codes: &#x27;C&#x27; = Conforming, &#x27;D&#x27; = Deficient, &#x27;M&#x27; = Mitigated, &#x27;R&#x27; = Remediated, &#x27;U&#x27; = Unacceptable. Use the ratingcodedictionary API to translate code to description.
         * @param ratingCode
         *            The rating given to this finding which is based on defect area, source, cause, and severity tier. Known codes: &#x27;C&#x27; = Conforming, &#x27;D&#x27; = Deficient, &#x27;M&#x27; = Mitigated, &#x27;R&#x27; = Remediated, &#x27;U&#x27; = Unacceptable. Use the ratingcodedictionary API to translate code to description.
         */
        public void setRatingCode(String ratingCode) {
            this.ratingCode = ratingCode;
        }

        /**
         * Get The date when the lender responded to this finding
         * @return The date when the lender responded to this finding
         */
        public Date getLenderResponseDate() {
            return lenderResponseDate;
        }

        /**
         * Set The date when the lender responded to this finding
         * @param lenderResponseDate
         *            The date when the lender responded to this finding
         */
        public void setLenderResponseDate(Date lenderResponseDate) {
            this.lenderResponseDate = lenderResponseDate;
        }

        /**
         * Get The name of the user (lender) that responded to this finding. (e.g. &#x27;John Franklin&#x27;)
         * @return The name of the user (lender) that responded to this finding. (e.g. &#x27;John Franklin&#x27;)
         */
        public String getLenderResponderName() {
            return lenderResponderName;
        }

        /**
         * Set The name of the user (lender) that responded to this finding. (e.g. &#x27;John Franklin&#x27;)
         * @param lenderResponderName
         *            The name of the user (lender) that responded to this finding. (e.g. &#x27;John Franklin&#x27;)
         */
        public void setLenderResponderName(String lenderResponderName) {
            this.lenderResponderName = lenderResponderName;
        }

        /**
         * Get The lender&#x27;s response to this finding.
         * @return The lender&#x27;s response to this finding.
         */
        public String getLenderResponseComment() {
            return lenderResponseComment;
        }

        /**
         * Set The lender&#x27;s response to this finding.
         * @param lenderResponseComment
         *            The lender&#x27;s response to this finding.
         */
        public void setLenderResponseComment(String lenderResponseComment) {
            this.lenderResponseComment = lenderResponseComment;
        }

        /**
         * Get A collection of files that were uploaded as response documents
         * @return A collection of files that were uploaded as response documents
         */
        public List<DocumentDTO> getLenderResponseDocuments() {
            return lenderResponseDocuments;
        }

        /**
         * Set A collection of files that were uploaded as response documents
         * @param lenderResponseDocuments
         *            A collection of files that were uploaded as response documents
         */
        public void setLenderResponseDocuments(List<DocumentDTO> lenderResponseDocuments) {
            this.lenderResponseDocuments = lenderResponseDocuments;
        }

        /**
         * Get The date if the lender request response was expired
         * @return The date if the lender request response was expired
         */
        public Date getLenderResponseExpiredDate() {
            return lenderResponseExpiredDate;
        }

        /**
         * Set The date if the lender request response was expired
         * @param lenderResponseExpiredDate
         *            The date if the lender request response was expired
         */
        public void setLenderResponseExpiredDate(Date lenderResponseExpiredDate) {
            this.lenderResponseExpiredDate = lenderResponseExpiredDate;
        }

        /**
         * Get The reviewer is satisfied with the lender response. When this is set to true, the services will update the ratingCode to &#x27;M&#x27;
         * @return The reviewer is satisfied with the lender response. When this is set to true, the services will update the ratingCode to &#x27;M&#x27;
         */
        public Boolean getReviewerResponseMitigated() {
            return reviewerResponseMitigated;
        }

        /**
         * Set The reviewer is satisfied with the lender response. When this is set to true, the services will update the ratingCode to &#x27;M&#x27;
         * @param reviewerResponseMitigated
         *            The reviewer is satisfied with the lender response. When this is set to true, the services will update the ratingCode to &#x27;M&#x27;
         */
        public void setReviewerResponseMitigated(Boolean reviewerResponseMitigated) {
            this.reviewerResponseMitigated = reviewerResponseMitigated;
        }

        /**
         * Get the reviewer has decided that the lenders response is an acceptable remediation to the finding and can capture an amount. When this is set, the services will update the ratingCode to &#x27;R&#x27;
         * @return the reviewer has decided that the lenders response is an acceptable remediation to the finding and can capture an amount. When this is set, the services will update the ratingCode to &#x27;R&#x27;
         */
        public RemediationResponseDTO getReviewerResponseRemediated() {
            return reviewerResponseRemediated;
        }

        /**
         * Set the reviewer has decided that the lenders response is an acceptable remediation to the finding and can capture an amount. When this is set, the services will update the ratingCode to &#x27;R&#x27;
         * @param reviewerResponseRemediated
         *            the reviewer has decided that the lenders response is an acceptable remediation to the finding and can capture an amount. When this is set, the services will update the ratingCode to &#x27;R&#x27;
         */
        public void setReviewerResponseRemediated(RemediationResponseDTO reviewerResponseRemediated) {
            this.reviewerResponseRemediated = reviewerResponseRemediated;
        }

        /**
         * Get The reviewer is making an adjustment to the fining. Possible values will be &#x27;C&#x27;, &#x27;1&#x27;, &#x27;2&#x27;, &#x27;3&#x27;, &#x27;4&#x27;. The numbers here represent a new selectedTierCode where as the &#x27;C&#x27; represents &#x27;Conforming&#x27; no matter the current severity tier. The services will use the suggestion and update the ratingCode and selectedTierCode
         * @return The reviewer is making an adjustment to the fining. Possible values will be &#x27;C&#x27;, &#x27;1&#x27;, &#x27;2&#x27;, &#x27;3&#x27;, &#x27;4&#x27;. The numbers here represent a new selectedTierCode where as the &#x27;C&#x27; represents &#x27;Conforming&#x27; no matter the current severity tier. The services will use the suggestion and update the ratingCode and selectedTierCode
         */
        public String getReviewerResponseAdjusted() {
            return reviewerResponseAdjusted;
        }

        /**
         * Set The reviewer is making an adjustment to the fining. Possible values will be &#x27;C&#x27;, &#x27;1&#x27;, &#x27;2&#x27;, &#x27;3&#x27;, &#x27;4&#x27;. The numbers here represent a new selectedTierCode where as the &#x27;C&#x27; represents &#x27;Conforming&#x27; no matter the current severity tier. The services will use the suggestion and update the ratingCode and selectedTierCode
         * @param reviewerResponseAdjusted
         *            The reviewer is making an adjustment to the fining. Possible values will be &#x27;C&#x27;, &#x27;1&#x27;, &#x27;2&#x27;, &#x27;3&#x27;, &#x27;4&#x27;. The numbers here represent a new selectedTierCode where as the &#x27;C&#x27; represents &#x27;Conforming&#x27; no matter the current severity tier. The services will use the suggestion and update the ratingCode and selectedTierCode
         */
        public void setReviewerResponseAdjusted(String reviewerResponseAdjusted) {
            this.reviewerResponseAdjusted = reviewerResponseAdjusted;
        }

        /**
         * Get If this finding was created as an adhoc finding, this flag will be set to true.
         * @return If this finding was created as an adhoc finding, this flag will be set to true.
         */
        public Boolean getIsAdhoc() {
            return isAdhoc;
        }

        /**
         * Set If this finding was created as an adhoc finding, this flag will be set to true.
         * @param isAdhoc
         *            If this finding was created as an adhoc finding, this flag will be set to true.
         */
        public void setIsAdhoc(Boolean isAdhoc) {
            this.isAdhoc = isAdhoc;
        }

        /**
         * Get The forename of the reviewer.
         * @return The forename of the reviewer.
         */
        public String getNameFirst() {
            return nameFirst;
        }

        /**
         * Set The forename of the reviewer.
         * @param nameFirst
         *            The forename of the reviewer.
         */
        public void setNameFirst(String nameFirst) {
            this.nameFirst = nameFirst;
        }

        /**
         * Get The family name of the reviewer.
         * @return The family name of the reviewer.
         */
        public String getNameLast() {
            return nameLast;
        }

        /**
         * Set The family name of the reviewer.
         * @param nameLast
         *            The family name of the reviewer.
         */
        public void setNameLast(String nameLast) {
            this.nameLast = nameLast;
        }

        /**
         * Get Date and time when the note was last updated (or created if no updates).
         * @return Date and time when the note was last updated (or created if no updates).
         */
        public Date getLastUpdated() {
            return lastUpdated;
        }

        /**
         * Set Date and time when the note was last updated (or created if no updates).
         * @param lastUpdated
         *            Date and time when the note was last updated (or created if no updates).
         */
        public void setLastUpdated(Date lastUpdated) {
            this.lastUpdated = lastUpdated;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "ReviewFindingDTO [ " + 
                "findingId = " + findingId + ", " + 
                "reviewLevelId = " + reviewLevelId + ", " + 
                "originalReviewLevelId = " + originalReviewLevelId + ", " + 
                "defectAreaCode = " + defectAreaCode + ", " + 
                "questionIds = " + questionIds + ", " + 
                "selectedSourceCode = " + selectedSourceCode + ", " + 
                "selectedCauseCode = " + selectedCauseCode + ", " + 
                "selectedTierCode = " + selectedTierCode + ", " + 
                "commentToLender = " + commentToLender + ", " + 
                "ratingCode = " + ratingCode + ", " + 
                "lenderResponseDate = " + lenderResponseDate + ", " + 
                "lenderResponderName = " + lenderResponderName + ", " + 
                "lenderResponseComment = " + lenderResponseComment + ", " + 
                "lenderResponseDocuments = " + lenderResponseDocuments + ", " + 
                "lenderResponseExpiredDate = " + lenderResponseExpiredDate + ", " + 
                "reviewerResponseMitigated = " + reviewerResponseMitigated + ", " + 
                "reviewerResponseRemediated = " + reviewerResponseRemediated + ", " + 
                "reviewerResponseAdjusted = " + reviewerResponseAdjusted + ", " + 
                "isAdhoc = " + isAdhoc + ", " + 
                "nameFirst = " + nameFirst + ", " + 
                "nameLast = " + nameLast + ", " + 
                "lastUpdated = " + lastUpdated + 
                " ]";
        }
    }

    // RemediationResponse DTO
    public static class RemediationResponseDTO implements Serializable {

        /**
         * The code of the remediation type
         */
        private String remediationTypeCode;

        /**
         * The amount being remediated
         */
        private BigDecimal remediationAmount;


        /**
         * Creates a new instance of RemediationResponseDTO
         */
        public RemediationResponseDTO() {}

        /**
         * Get The code of the remediation type
         * @return The code of the remediation type
         */
        public String getRemediationTypeCode() {
            return remediationTypeCode;
        }

        /**
         * Set The code of the remediation type
         * @param remediationTypeCode
         *            The code of the remediation type
         */
        public void setRemediationTypeCode(String remediationTypeCode) {
            this.remediationTypeCode = remediationTypeCode;
        }

        /**
         * Get The amount being remediated
         * @return The amount being remediated
         */
        public BigDecimal getRemediationAmount() {
            return remediationAmount;
        }

        /**
         * Set The amount being remediated
         * @param remediationAmount
         *            The amount being remediated
         */
        public void setRemediationAmount(BigDecimal remediationAmount) {
            this.remediationAmount = remediationAmount;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "RemediationResponseDTO [ " + 
                "remediationTypeCode = " + remediationTypeCode + ", " + 
                "remediationAmount = " + remediationAmount + 
                " ]";
        }
    }

    // LiteReview DTO
    public static class LiteReviewDTO implements Serializable {

        /**
         * The identifier of this review
         */
        private String reviewId;

        /**
         * The status of the review. Is it in-progress or completed.
         */
        private String status;

        /**
         * The human readable identifier of this review
         */
        private String reviewReferenceId;


        /**
         * Creates a new instance of LiteReviewDTO
         */
        public LiteReviewDTO() {}

        /**
         * Get The identifier of this review
         * @return The identifier of this review
         */
        public String getReviewId() {
            return reviewId;
        }

        /**
         * Set The identifier of this review
         * @param reviewId
         *            The identifier of this review
         */
        public void setReviewId(String reviewId) {
            this.reviewId = reviewId;
        }

        /**
         * Get The status of the review. Is it in-progress or completed.
         * @return The status of the review. Is it in-progress or completed.
         */
        public String getStatus() {
            return status;
        }

        /**
         * Set The status of the review. Is it in-progress or completed.
         * @param status
         *            The status of the review. Is it in-progress or completed.
         */
        public void setStatus(String status) {
            this.status = status;
        }

        /**
         * Get The human readable identifier of this review
         * @return The human readable identifier of this review
         */
        public String getReviewReferenceId() {
            return reviewReferenceId;
        }

        /**
         * Set The human readable identifier of this review
         * @param reviewReferenceId
         *            The human readable identifier of this review
         */
        public void setReviewReferenceId(String reviewReferenceId) {
            this.reviewReferenceId = reviewReferenceId;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "LiteReviewDTO [ " + 
                "reviewId = " + reviewId + ", " + 
                "status = " + status + ", " + 
                "reviewReferenceId = " + reviewReferenceId + 
                " ]";
        }
    }

    // Reviewer DTO
    public static class ReviewerDTO implements Serializable {

        /**
         * The identifier of the object
         */
        private String reviewerId;

        /**
         * The login identifier of the reviewer; the user&#x27;s HID.
         */
        private String hudId;

        /**
         * The forename of the reviewer.
         */
        private String nameFirst;

        /**
         * The family name of the reviewer.
         */
        private String nameLast;

        /**
         * The total number of reviews the reviewer can have assigned to them.
         */
        private Integer totalCapacity;

        /**
         * The remaining number of reviews the reviewer can have assigned to them.
         */
        private Integer remainingCapacity;

        /**
         * The ID of the geographical location where the reviewer does the review.
         */
        private String locationId;

        /**
         * The ID of the person who the reviewer reports to.
         */
        private String reportsTo;

        /**
         * The ID of the person who the reviewer&#x27;s reviews are vetted by.
         */
        private String vettedBy;

        /**
         * Status of the user; whether they are active (A), inactive (I), or terminated (T).
         */
        private String statusCode;

        /**
         * An array of date ranges when the reviewer is giong to be unavailable.
         */
        private List<ReviewerUnavailabilityDTO> unavailabilities;

        /**
         * An array of IDs for different review levels this reviewer can review.
         */
        private List<ReviewerAssignmentTypeDTO> reviewLevels;

        /**
         * An array of IDs for different review types this reviewer can review.
         */
        private List<ReviewerAssignmentTypeDTO> reviewTypes;

        /**
         * An array of IDs for different product types this reviewer can review.
         */
        private List<ReviewerAssignmentTypeDTO> productTypes;

        /**
         * An array of IDs for different selection reasons this reviewer can review.
         */
        private List<ReviewerAssignmentTypeDTO> selectionReasons;


        /**
         * Creates a new instance of ReviewerDTO
         */
        public ReviewerDTO() {}

        /**
         * Get The identifier of the object
         * @return The identifier of the object
         */
        public String getReviewerId() {
            return reviewerId;
        }

        /**
         * Set The identifier of the object
         * @param reviewerId
         *            The identifier of the object
         */
        public void setReviewerId(String reviewerId) {
            this.reviewerId = reviewerId;
        }

        /**
         * Get The login identifier of the reviewer; the user&#x27;s HID.
         * @return The login identifier of the reviewer; the user&#x27;s HID.
         */
        public String getHudId() {
            return hudId;
        }

        /**
         * Set The login identifier of the reviewer; the user&#x27;s HID.
         * @param hudId
         *            The login identifier of the reviewer; the user&#x27;s HID.
         */
        public void setHudId(String hudId) {
            this.hudId = hudId;
        }

        /**
         * Get The forename of the reviewer.
         * @return The forename of the reviewer.
         */
        public String getNameFirst() {
            return nameFirst;
        }

        /**
         * Set The forename of the reviewer.
         * @param nameFirst
         *            The forename of the reviewer.
         */
        public void setNameFirst(String nameFirst) {
            this.nameFirst = nameFirst;
        }

        /**
         * Get The family name of the reviewer.
         * @return The family name of the reviewer.
         */
        public String getNameLast() {
            return nameLast;
        }

        /**
         * Set The family name of the reviewer.
         * @param nameLast
         *            The family name of the reviewer.
         */
        public void setNameLast(String nameLast) {
            this.nameLast = nameLast;
        }

        /**
         * Get The total number of reviews the reviewer can have assigned to them.
         * @return The total number of reviews the reviewer can have assigned to them.
         */
        public Integer getTotalCapacity() {
            return totalCapacity;
        }

        /**
         * Set The total number of reviews the reviewer can have assigned to them.
         * @param totalCapacity
         *            The total number of reviews the reviewer can have assigned to them.
         */
        public void setTotalCapacity(Integer totalCapacity) {
            this.totalCapacity = totalCapacity;
        }

        /**
         * Get The remaining number of reviews the reviewer can have assigned to them.
         * @return The remaining number of reviews the reviewer can have assigned to them.
         */
        public Integer getRemainingCapacity() {
            return remainingCapacity;
        }

        /**
         * Set The remaining number of reviews the reviewer can have assigned to them.
         * @param remainingCapacity
         *            The remaining number of reviews the reviewer can have assigned to them.
         */
        public void setRemainingCapacity(Integer remainingCapacity) {
            this.remainingCapacity = remainingCapacity;
        }

        /**
         * Get The ID of the geographical location where the reviewer does the review.
         * @return The ID of the geographical location where the reviewer does the review.
         */
        public String getLocationId() {
            return locationId;
        }

        /**
         * Set The ID of the geographical location where the reviewer does the review.
         * @param locationId
         *            The ID of the geographical location where the reviewer does the review.
         */
        public void setLocationId(String locationId) {
            this.locationId = locationId;
        }

        /**
         * Get The ID of the person who the reviewer reports to.
         * @return The ID of the person who the reviewer reports to.
         */
        public String getReportsTo() {
            return reportsTo;
        }

        /**
         * Set The ID of the person who the reviewer reports to.
         * @param reportsTo
         *            The ID of the person who the reviewer reports to.
         */
        public void setReportsTo(String reportsTo) {
            this.reportsTo = reportsTo;
        }

        /**
         * Get The ID of the person who the reviewer&#x27;s reviews are vetted by.
         * @return The ID of the person who the reviewer&#x27;s reviews are vetted by.
         */
        public String getVettedBy() {
            return vettedBy;
        }

        /**
         * Set The ID of the person who the reviewer&#x27;s reviews are vetted by.
         * @param vettedBy
         *            The ID of the person who the reviewer&#x27;s reviews are vetted by.
         */
        public void setVettedBy(String vettedBy) {
            this.vettedBy = vettedBy;
        }

        /**
         * Get Status of the user; whether they are active (A), inactive (I), or terminated (T).
         * @return Status of the user; whether they are active (A), inactive (I), or terminated (T).
         */
        public String getStatusCode() {
            return statusCode;
        }

        /**
         * Set Status of the user; whether they are active (A), inactive (I), or terminated (T).
         * @param statusCode
         *            Status of the user; whether they are active (A), inactive (I), or terminated (T).
         */
        public void setStatusCode(String statusCode) {
            this.statusCode = statusCode;
        }

        /**
         * Get An array of date ranges when the reviewer is giong to be unavailable.
         * @return An array of date ranges when the reviewer is giong to be unavailable.
         */
        public List<ReviewerUnavailabilityDTO> getUnavailabilities() {
            return unavailabilities;
        }

        /**
         * Set An array of date ranges when the reviewer is giong to be unavailable.
         * @param unavailabilities
         *            An array of date ranges when the reviewer is giong to be unavailable.
         */
        public void setUnavailabilities(List<ReviewerUnavailabilityDTO> unavailabilities) {
            this.unavailabilities = unavailabilities;
        }

        /**
         * Get An array of IDs for different review levels this reviewer can review.
         * @return An array of IDs for different review levels this reviewer can review.
         */
        public List<ReviewerAssignmentTypeDTO> getReviewLevels() {
            return reviewLevels;
        }

        /**
         * Set An array of IDs for different review levels this reviewer can review.
         * @param reviewLevels
         *            An array of IDs for different review levels this reviewer can review.
         */
        public void setReviewLevels(List<ReviewerAssignmentTypeDTO> reviewLevels) {
            this.reviewLevels = reviewLevels;
        }

        /**
         * Get An array of IDs for different review types this reviewer can review.
         * @return An array of IDs for different review types this reviewer can review.
         */
        public List<ReviewerAssignmentTypeDTO> getReviewTypes() {
            return reviewTypes;
        }

        /**
         * Set An array of IDs for different review types this reviewer can review.
         * @param reviewTypes
         *            An array of IDs for different review types this reviewer can review.
         */
        public void setReviewTypes(List<ReviewerAssignmentTypeDTO> reviewTypes) {
            this.reviewTypes = reviewTypes;
        }

        /**
         * Get An array of IDs for different product types this reviewer can review.
         * @return An array of IDs for different product types this reviewer can review.
         */
        public List<ReviewerAssignmentTypeDTO> getProductTypes() {
            return productTypes;
        }

        /**
         * Set An array of IDs for different product types this reviewer can review.
         * @param productTypes
         *            An array of IDs for different product types this reviewer can review.
         */
        public void setProductTypes(List<ReviewerAssignmentTypeDTO> productTypes) {
            this.productTypes = productTypes;
        }

        /**
         * Get An array of IDs for different selection reasons this reviewer can review.
         * @return An array of IDs for different selection reasons this reviewer can review.
         */
        public List<ReviewerAssignmentTypeDTO> getSelectionReasons() {
            return selectionReasons;
        }

        /**
         * Set An array of IDs for different selection reasons this reviewer can review.
         * @param selectionReasons
         *            An array of IDs for different selection reasons this reviewer can review.
         */
        public void setSelectionReasons(List<ReviewerAssignmentTypeDTO> selectionReasons) {
            this.selectionReasons = selectionReasons;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "ReviewerDTO [ " + 
                "reviewerId = " + reviewerId + ", " + 
                "hudId = " + hudId + ", " + 
                "nameFirst = " + nameFirst + ", " + 
                "nameLast = " + nameLast + ", " + 
                "totalCapacity = " + totalCapacity + ", " + 
                "remainingCapacity = " + remainingCapacity + ", " + 
                "locationId = " + locationId + ", " + 
                "reportsTo = " + reportsTo + ", " + 
                "vettedBy = " + vettedBy + ", " + 
                "statusCode = " + statusCode + ", " + 
                "unavailabilities = " + unavailabilities + ", " + 
                "reviewLevels = " + reviewLevels + ", " + 
                "reviewTypes = " + reviewTypes + ", " + 
                "productTypes = " + productTypes + ", " + 
                "selectionReasons = " + selectionReasons + 
                " ]";
        }
    }

    // ReviewerLite DTO
    public static class ReviewerLiteDTO implements Serializable {

        /**
         * The identifier of the object
         */
        private String reviewerId;

        /**
         * The login identifier of the reviewer; the user&#x27;s HID.
         */
        private String hudId;

        /**
         * The forename of the reviewer.
         */
        private String nameFirst;

        /**
         * The family name of the reviewer.
         */
        private String nameLast;

        /**
         * The remaining number of reviews the reviewer can have assigned to them.
         */
        private Integer remainingCapacity;

        /**
         * The ID of the geographical location where the reviewer does the review.
         */
        private String locationId;

        /**
         * The ID of the person who the reviewer reports to.
         */
        private String reportsTo;

        /**
         * Status of the user; whether they are active (A), inactive (I), or terminated (T).
         */
        private String statusCode;


        /**
         * Creates a new instance of ReviewerLiteDTO
         */
        public ReviewerLiteDTO() {}

        /**
         * Get The identifier of the object
         * @return The identifier of the object
         */
        public String getReviewerId() {
            return reviewerId;
        }

        /**
         * Set The identifier of the object
         * @param reviewerId
         *            The identifier of the object
         */
        public void setReviewerId(String reviewerId) {
            this.reviewerId = reviewerId;
        }

        /**
         * Get The login identifier of the reviewer; the user&#x27;s HID.
         * @return The login identifier of the reviewer; the user&#x27;s HID.
         */
        public String getHudId() {
            return hudId;
        }

        /**
         * Set The login identifier of the reviewer; the user&#x27;s HID.
         * @param hudId
         *            The login identifier of the reviewer; the user&#x27;s HID.
         */
        public void setHudId(String hudId) {
            this.hudId = hudId;
        }

        /**
         * Get The forename of the reviewer.
         * @return The forename of the reviewer.
         */
        public String getNameFirst() {
            return nameFirst;
        }

        /**
         * Set The forename of the reviewer.
         * @param nameFirst
         *            The forename of the reviewer.
         */
        public void setNameFirst(String nameFirst) {
            this.nameFirst = nameFirst;
        }

        /**
         * Get The family name of the reviewer.
         * @return The family name of the reviewer.
         */
        public String getNameLast() {
            return nameLast;
        }

        /**
         * Set The family name of the reviewer.
         * @param nameLast
         *            The family name of the reviewer.
         */
        public void setNameLast(String nameLast) {
            this.nameLast = nameLast;
        }

        /**
         * Get The remaining number of reviews the reviewer can have assigned to them.
         * @return The remaining number of reviews the reviewer can have assigned to them.
         */
        public Integer getRemainingCapacity() {
            return remainingCapacity;
        }

        /**
         * Set The remaining number of reviews the reviewer can have assigned to them.
         * @param remainingCapacity
         *            The remaining number of reviews the reviewer can have assigned to them.
         */
        public void setRemainingCapacity(Integer remainingCapacity) {
            this.remainingCapacity = remainingCapacity;
        }

        /**
         * Get The ID of the geographical location where the reviewer does the review.
         * @return The ID of the geographical location where the reviewer does the review.
         */
        public String getLocationId() {
            return locationId;
        }

        /**
         * Set The ID of the geographical location where the reviewer does the review.
         * @param locationId
         *            The ID of the geographical location where the reviewer does the review.
         */
        public void setLocationId(String locationId) {
            this.locationId = locationId;
        }

        /**
         * Get The ID of the person who the reviewer reports to.
         * @return The ID of the person who the reviewer reports to.
         */
        public String getReportsTo() {
            return reportsTo;
        }

        /**
         * Set The ID of the person who the reviewer reports to.
         * @param reportsTo
         *            The ID of the person who the reviewer reports to.
         */
        public void setReportsTo(String reportsTo) {
            this.reportsTo = reportsTo;
        }

        /**
         * Get Status of the user; whether they are active (A), inactive (I), or terminated (T).
         * @return Status of the user; whether they are active (A), inactive (I), or terminated (T).
         */
        public String getStatusCode() {
            return statusCode;
        }

        /**
         * Set Status of the user; whether they are active (A), inactive (I), or terminated (T).
         * @param statusCode
         *            Status of the user; whether they are active (A), inactive (I), or terminated (T).
         */
        public void setStatusCode(String statusCode) {
            this.statusCode = statusCode;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "ReviewerLiteDTO [ " + 
                "reviewerId = " + reviewerId + ", " + 
                "hudId = " + hudId + ", " + 
                "nameFirst = " + nameFirst + ", " + 
                "nameLast = " + nameLast + ", " + 
                "remainingCapacity = " + remainingCapacity + ", " + 
                "locationId = " + locationId + ", " + 
                "reportsTo = " + reportsTo + ", " + 
                "statusCode = " + statusCode + 
                " ]";
        }
    }

    // ReviewerUnavailability DTO
    public static class ReviewerUnavailabilityDTO implements Serializable {

        /**
         * The unavailability id for the record.
         */
        private String unavailabilityId;

        /**
         * Date when the reviewer&#x27;s unavailability begins.
         */
        private Date from;

        /**
         * Date when the reviewer&#x27;s unavailabity ends.
         */
        private Date to;


        /**
         * Creates a new instance of ReviewerUnavailabilityDTO
         */
        public ReviewerUnavailabilityDTO() {}

        /**
         * Get The unavailability id for the record.
         * @return The unavailability id for the record.
         */
        public String getUnavailabilityId() {
            return unavailabilityId;
        }

        /**
         * Set The unavailability id for the record.
         * @param unavailabilityId
         *            The unavailability id for the record.
         */
        public void setUnavailabilityId(String unavailabilityId) {
            this.unavailabilityId = unavailabilityId;
        }

        /**
         * Get Date when the reviewer&#x27;s unavailability begins.
         * @return Date when the reviewer&#x27;s unavailability begins.
         */
        public Date getFrom() {
            return from;
        }

        /**
         * Set Date when the reviewer&#x27;s unavailability begins.
         * @param from
         *            Date when the reviewer&#x27;s unavailability begins.
         */
        public void setFrom(Date from) {
            this.from = from;
        }

        /**
         * Get Date when the reviewer&#x27;s unavailabity ends.
         * @return Date when the reviewer&#x27;s unavailabity ends.
         */
        public Date getTo() {
            return to;
        }

        /**
         * Set Date when the reviewer&#x27;s unavailabity ends.
         * @param to
         *            Date when the reviewer&#x27;s unavailabity ends.
         */
        public void setTo(Date to) {
            this.to = to;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "ReviewerUnavailabilityDTO [ " + 
                "unavailabilityId = " + unavailabilityId + ", " + 
                "from = " + from + ", " + 
                "to = " + to + 
                " ]";
        }
    }

    // ReviewerAssignmentType DTO
    public static class ReviewerAssignmentTypeDTO implements Serializable {

        /**
         * The assignment type id for the record.
         */
        private String assignmentTypeId;

        /**
         * The code of an assignment type. Could be a Selection Reason, Review Type, Loan Type, or Review Level.
         */
        private String assignmentTypeCode;


        /**
         * Creates a new instance of ReviewerAssignmentTypeDTO
         */
        public ReviewerAssignmentTypeDTO() {}

        /**
         * Get The assignment type id for the record.
         * @return The assignment type id for the record.
         */
        public String getAssignmentTypeId() {
            return assignmentTypeId;
        }

        /**
         * Set The assignment type id for the record.
         * @param assignmentTypeId
         *            The assignment type id for the record.
         */
        public void setAssignmentTypeId(String assignmentTypeId) {
            this.assignmentTypeId = assignmentTypeId;
        }

        /**
         * Get The code of an assignment type. Could be a Selection Reason, Review Type, Loan Type, or Review Level.
         * @return The code of an assignment type. Could be a Selection Reason, Review Type, Loan Type, or Review Level.
         */
        public String getAssignmentTypeCode() {
            return assignmentTypeCode;
        }

        /**
         * Set The code of an assignment type. Could be a Selection Reason, Review Type, Loan Type, or Review Level.
         * @param assignmentTypeCode
         *            The code of an assignment type. Could be a Selection Reason, Review Type, Loan Type, or Review Level.
         */
        public void setAssignmentTypeCode(String assignmentTypeCode) {
            this.assignmentTypeCode = assignmentTypeCode;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "ReviewerAssignmentTypeDTO [ " + 
                "assignmentTypeId = " + assignmentTypeId + ", " + 
                "assignmentTypeCode = " + assignmentTypeCode + 
                " ]";
        }
    }

    // ReviewerCreation DTO
    public static class ReviewerCreationDTO implements Serializable {

        /**
         * The identifier of the reviewer. This corresponds to their ID in FHA Connection.
         */
        private String hudId;

        /**
         * The ID of the geogrpahical location where the reviewer does the review.
         */
        private String locationId;

        /**
         * The ID of the person who the reviewer reports to.
         */
        private String reportsTo;

        /**
         * The forename of the reviewer.
         */
        private String nameFirst;

        /**
         * The family name of the reviewer.
         */
        private String nameLast;


        /**
         * Creates a new instance of ReviewerCreationDTO
         */
        public ReviewerCreationDTO() {}

        /**
         * Get The identifier of the reviewer. This corresponds to their ID in FHA Connection.
         * @return The identifier of the reviewer. This corresponds to their ID in FHA Connection.
         */
        public String getHudId() {
            return hudId;
        }

        /**
         * Set The identifier of the reviewer. This corresponds to their ID in FHA Connection.
         * @param hudId
         *            The identifier of the reviewer. This corresponds to their ID in FHA Connection.
         */
        public void setHudId(String hudId) {
            this.hudId = hudId;
        }

        /**
         * Get The ID of the geogrpahical location where the reviewer does the review.
         * @return The ID of the geogrpahical location where the reviewer does the review.
         */
        public String getLocationId() {
            return locationId;
        }

        /**
         * Set The ID of the geogrpahical location where the reviewer does the review.
         * @param locationId
         *            The ID of the geogrpahical location where the reviewer does the review.
         */
        public void setLocationId(String locationId) {
            this.locationId = locationId;
        }

        /**
         * Get The ID of the person who the reviewer reports to.
         * @return The ID of the person who the reviewer reports to.
         */
        public String getReportsTo() {
            return reportsTo;
        }

        /**
         * Set The ID of the person who the reviewer reports to.
         * @param reportsTo
         *            The ID of the person who the reviewer reports to.
         */
        public void setReportsTo(String reportsTo) {
            this.reportsTo = reportsTo;
        }

        /**
         * Get The forename of the reviewer.
         * @return The forename of the reviewer.
         */
        public String getNameFirst() {
            return nameFirst;
        }

        /**
         * Set The forename of the reviewer.
         * @param nameFirst
         *            The forename of the reviewer.
         */
        public void setNameFirst(String nameFirst) {
            this.nameFirst = nameFirst;
        }

        /**
         * Get The family name of the reviewer.
         * @return The family name of the reviewer.
         */
        public String getNameLast() {
            return nameLast;
        }

        /**
         * Set The family name of the reviewer.
         * @param nameLast
         *            The family name of the reviewer.
         */
        public void setNameLast(String nameLast) {
            this.nameLast = nameLast;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "ReviewerCreationDTO [ " + 
                "hudId = " + hudId + ", " + 
                "locationId = " + locationId + ", " + 
                "reportsTo = " + reportsTo + ", " + 
                "nameFirst = " + nameFirst + ", " + 
                "nameLast = " + nameLast + 
                " ]";
        }
    }

    // Location DTO
    public static class LocationDTO implements Serializable {

        /**
         * The identifier of the object
         */
        private String locationId;

        /**
         * The display name for this location.
         */
        private String name;

        /**
         * Property address for location.
         */
        private String address;

        /**
         * Extended property address for location.
         */
        private String address2;

        /**
         * City location is in.
         */
        private String city;

        /**
         * State location city is in.
         */
        private String state;

        /**
         * Zip code location city is in.
         */
        private String zipCode;

        /**
         * The monthly review capacity for this location.
         */
        private Integer capacity;

        /**
         * The name of the person to contact for information at this location.
         */
        private String contactName;

        /**
         * The phone number to call for information at this location.
         */
        private String contactPhone;

        /**
         * The specific extension to use on this line.
         */
        private String contactPhoneExtension;

        /**
         * The name of the person who owns this location.
         */
        private String locationOwner;

        /**
         * The date the location became active.
         */
        private Date activationDate;

        /**
         * The date the location was deactivated.
         */
        private Date deActivationDate;

        /**
         * Possible selection reasons allowed for location.
         */
        private List<String> allowedSelectionReasons;

        /**
         * Possible types of reviews allowed for location.
         */
        private List<String> allowedReviewTypes;

        /**
         * Possible types of loans allowed for location.
         */
        private List<String> allowedProductTypes;


        /**
         * Creates a new instance of LocationDTO
         */
        public LocationDTO() {}

        /**
         * Get The identifier of the object
         * @return The identifier of the object
         */
        public String getLocationId() {
            return locationId;
        }

        /**
         * Set The identifier of the object
         * @param locationId
         *            The identifier of the object
         */
        public void setLocationId(String locationId) {
            this.locationId = locationId;
        }

        /**
         * Get The display name for this location.
         * @return The display name for this location.
         */
        public String getName() {
            return name;
        }

        /**
         * Set The display name for this location.
         * @param name
         *            The display name for this location.
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * Get Property address for location.
         * @return Property address for location.
         */
        public String getAddress() {
            return address;
        }

        /**
         * Set Property address for location.
         * @param address
         *            Property address for location.
         */
        public void setAddress(String address) {
            this.address = address;
        }

        /**
         * Get Extended property address for location.
         * @return Extended property address for location.
         */
        public String getAddress2() {
            return address2;
        }

        /**
         * Set Extended property address for location.
         * @param address2
         *            Extended property address for location.
         */
        public void setAddress2(String address2) {
            this.address2 = address2;
        }

        /**
         * Get City location is in.
         * @return City location is in.
         */
        public String getCity() {
            return city;
        }

        /**
         * Set City location is in.
         * @param city
         *            City location is in.
         */
        public void setCity(String city) {
            this.city = city;
        }

        /**
         * Get State location city is in.
         * @return State location city is in.
         */
        public String getState() {
            return state;
        }

        /**
         * Set State location city is in.
         * @param state
         *            State location city is in.
         */
        public void setState(String state) {
            this.state = state;
        }

        /**
         * Get Zip code location city is in.
         * @return Zip code location city is in.
         */
        public String getZipCode() {
            return zipCode;
        }

        /**
         * Set Zip code location city is in.
         * @param zipCode
         *            Zip code location city is in.
         */
        public void setZipCode(String zipCode) {
            this.zipCode = zipCode;
        }

        /**
         * Get The monthly review capacity for this location.
         * @return The monthly review capacity for this location.
         */
        public Integer getCapacity() {
            return capacity;
        }

        /**
         * Set The monthly review capacity for this location.
         * @param capacity
         *            The monthly review capacity for this location.
         */
        public void setCapacity(Integer capacity) {
            this.capacity = capacity;
        }

        /**
         * Get The name of the person to contact for information at this location.
         * @return The name of the person to contact for information at this location.
         */
        public String getContactName() {
            return contactName;
        }

        /**
         * Set The name of the person to contact for information at this location.
         * @param contactName
         *            The name of the person to contact for information at this location.
         */
        public void setContactName(String contactName) {
            this.contactName = contactName;
        }

        /**
         * Get The phone number to call for information at this location.
         * @return The phone number to call for information at this location.
         */
        public String getContactPhone() {
            return contactPhone;
        }

        /**
         * Set The phone number to call for information at this location.
         * @param contactPhone
         *            The phone number to call for information at this location.
         */
        public void setContactPhone(String contactPhone) {
            this.contactPhone = contactPhone;
        }

        /**
         * Get The specific extension to use on this line.
         * @return The specific extension to use on this line.
         */
        public String getContactPhoneExtension() {
            return contactPhoneExtension;
        }

        /**
         * Set The specific extension to use on this line.
         * @param contactPhoneExtension
         *            The specific extension to use on this line.
         */
        public void setContactPhoneExtension(String contactPhoneExtension) {
            this.contactPhoneExtension = contactPhoneExtension;
        }

        /**
         * Get The name of the person who owns this location.
         * @return The name of the person who owns this location.
         */
        public String getLocationOwner() {
            return locationOwner;
        }

        /**
         * Set The name of the person who owns this location.
         * @param locationOwner
         *            The name of the person who owns this location.
         */
        public void setLocationOwner(String locationOwner) {
            this.locationOwner = locationOwner;
        }

        /**
         * Get The date the location became active.
         * @return The date the location became active.
         */
        public Date getActivationDate() {
            return activationDate;
        }

        /**
         * Set The date the location became active.
         * @param activationDate
         *            The date the location became active.
         */
        public void setActivationDate(Date activationDate) {
            this.activationDate = activationDate;
        }

        /**
         * Get The date the location was deactivated.
         * @return The date the location was deactivated.
         */
        public Date getDeActivationDate() {
            return deActivationDate;
        }

        /**
         * Set The date the location was deactivated.
         * @param deActivationDate
         *            The date the location was deactivated.
         */
        public void setDeActivationDate(Date deActivationDate) {
            this.deActivationDate = deActivationDate;
        }

        /**
         * Get Possible selection reasons allowed for location.
         * @return Possible selection reasons allowed for location.
         */
        public List<String> getAllowedSelectionReasons() {
            return allowedSelectionReasons;
        }

        /**
         * Set Possible selection reasons allowed for location.
         * @param allowedSelectionReasons
         *            Possible selection reasons allowed for location.
         */
        public void setAllowedSelectionReasons(List<String> allowedSelectionReasons) {
            this.allowedSelectionReasons = allowedSelectionReasons;
        }

        /**
         * Get Possible types of reviews allowed for location.
         * @return Possible types of reviews allowed for location.
         */
        public List<String> getAllowedReviewTypes() {
            return allowedReviewTypes;
        }

        /**
         * Set Possible types of reviews allowed for location.
         * @param allowedReviewTypes
         *            Possible types of reviews allowed for location.
         */
        public void setAllowedReviewTypes(List<String> allowedReviewTypes) {
            this.allowedReviewTypes = allowedReviewTypes;
        }

        /**
         * Get Possible types of loans allowed for location.
         * @return Possible types of loans allowed for location.
         */
        public List<String> getAllowedProductTypes() {
            return allowedProductTypes;
        }

        /**
         * Set Possible types of loans allowed for location.
         * @param allowedProductTypes
         *            Possible types of loans allowed for location.
         */
        public void setAllowedProductTypes(List<String> allowedProductTypes) {
            this.allowedProductTypes = allowedProductTypes;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "LocationDTO [ " + 
                "locationId = " + locationId + ", " + 
                "name = " + name + ", " + 
                "address = " + address + ", " + 
                "address2 = " + address2 + ", " + 
                "city = " + city + ", " + 
                "state = " + state + ", " + 
                "zipCode = " + zipCode + ", " + 
                "capacity = " + capacity + ", " + 
                "contactName = " + contactName + ", " + 
                "contactPhone = " + contactPhone + ", " + 
                "contactPhoneExtension = " + contactPhoneExtension + ", " + 
                "locationOwner = " + locationOwner + ", " + 
                "activationDate = " + activationDate + ", " + 
                "deActivationDate = " + deActivationDate + ", " + 
                "allowedSelectionReasons = " + allowedSelectionReasons + ", " + 
                "allowedReviewTypes = " + allowedReviewTypes + ", " + 
                "allowedProductTypes = " + allowedProductTypes + 
                " ]";
        }
    }

    // FHAConnectionReviewer DTO
    public static class FHAConnectionReviewerDTO implements Serializable {

        /**
         * The identifier of the reviewer
         */
        private String hudId;

        /**
         * The forename of the reviewer.
         */
        private String nameFirst;

        /**
         * The family name of the reviewer.
         */
        private String nameLast;

        /**
         * The list of authorities the user has.
         */
        private List<SimpleGrantedAuthority> roles;

        /**
         * The identifier of the lender the user is associated to.
         */
        private String lenderId;


        /**
         * Creates a new instance of FHAConnectionReviewerDTO
         */
        public FHAConnectionReviewerDTO() {}

        /**
         * Get The identifier of the reviewer
         * @return The identifier of the reviewer
         */
        public String getHudId() {
            return hudId;
        }

        /**
         * Set The identifier of the reviewer
         * @param hudId
         *            The identifier of the reviewer
         */
        public void setHudId(String hudId) {
            this.hudId = hudId;
        }

        /**
         * Get The forename of the reviewer.
         * @return The forename of the reviewer.
         */
        public String getNameFirst() {
            return nameFirst;
        }

        /**
         * Set The forename of the reviewer.
         * @param nameFirst
         *            The forename of the reviewer.
         */
        public void setNameFirst(String nameFirst) {
            this.nameFirst = nameFirst;
        }

        /**
         * Get The family name of the reviewer.
         * @return The family name of the reviewer.
         */
        public String getNameLast() {
            return nameLast;
        }

        /**
         * Set The family name of the reviewer.
         * @param nameLast
         *            The family name of the reviewer.
         */
        public void setNameLast(String nameLast) {
            this.nameLast = nameLast;
        }

        /**
         * Get The list of authorities the user has.
         * @return The list of authorities the user has.
         */
        public List<SimpleGrantedAuthority> getRoles() {
            return roles;
        }

        /**
         * Set The list of authorities the user has.
         * @param roles
         *            The list of authorities the user has.
         */
        public void setRoles(List<SimpleGrantedAuthority> roles) {
            this.roles = roles;
        }

        /**
         * Get The identifier of the lender the user is associated to.
         * @return The identifier of the lender the user is associated to.
         */
        public String getLenderId() {
            return lenderId;
        }

        /**
         * Set The identifier of the lender the user is associated to.
         * @param lenderId
         *            The identifier of the lender the user is associated to.
         */
        public void setLenderId(String lenderId) {
            this.lenderId = lenderId;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "FHAConnectionReviewerDTO [ " + 
                "hudId = " + hudId + ", " + 
                "nameFirst = " + nameFirst + ", " + 
                "nameLast = " + nameLast + ", " + 
                "roles = " + roles + ", " + 
                "lenderId = " + lenderId + 
                " ]";
        }
    }

    // ApiError DTO
    public static class ApiErrorDTO implements Serializable {

        /**
         * The exception object passed back in a dev or test envinroment only.
         */
        private ApiErrorExceptionDTO exception;

        /**
         * A collection of production friendly messages describing what went wrong at a high level.
         */
        private List<String> errorMessages;


        /**
         * Creates a new instance of ApiErrorDTO
         */
        public ApiErrorDTO() {}

        /**
         * Get The exception object passed back in a dev or test envinroment only.
         * @return The exception object passed back in a dev or test envinroment only.
         */
        public ApiErrorExceptionDTO getException() {
            return exception;
        }

        /**
         * Set The exception object passed back in a dev or test envinroment only.
         * @param exception
         *            The exception object passed back in a dev or test envinroment only.
         */
        public void setException(ApiErrorExceptionDTO exception) {
            this.exception = exception;
        }

        /**
         * Get A collection of production friendly messages describing what went wrong at a high level.
         * @return A collection of production friendly messages describing what went wrong at a high level.
         */
        public List<String> getErrorMessages() {
            return errorMessages;
        }

        /**
         * Set A collection of production friendly messages describing what went wrong at a high level.
         * @param errorMessages
         *            A collection of production friendly messages describing what went wrong at a high level.
         */
        public void setErrorMessages(List<String> errorMessages) {
            this.errorMessages = errorMessages;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "ApiErrorDTO [ " + 
                "exception = " + exception + ", " + 
                "errorMessages = " + errorMessages + 
                " ]";
        }
    }

    // ApiErrorException DTO
    public static class ApiErrorExceptionDTO implements Serializable {

        /**
         * A description of the exception targetted for the development team.
         */
        private String message;

        /**
         * Error identifier.
         */
        private String exceptionClass;

        /**
         * The stack trace of the error if available.
         */
        private List<String> stackTrace;


        /**
         * Creates a new instance of ApiErrorExceptionDTO
         */
        public ApiErrorExceptionDTO() {}

        /**
         * Get A description of the exception targetted for the development team.
         * @return A description of the exception targetted for the development team.
         */
        public String getMessage() {
            return message;
        }

        /**
         * Set A description of the exception targetted for the development team.
         * @param message
         *            A description of the exception targetted for the development team.
         */
        public void setMessage(String message) {
            this.message = message;
        }

        /**
         * Get Error identifier.
         * @return Error identifier.
         */
        public String getExceptionClass() {
            return exceptionClass;
        }

        /**
         * Set Error identifier.
         * @param exceptionClass
         *            Error identifier.
         */
        public void setExceptionClass(String exceptionClass) {
            this.exceptionClass = exceptionClass;
        }

        /**
         * Get The stack trace of the error if available.
         * @return The stack trace of the error if available.
         */
        public List<String> getStackTrace() {
            return stackTrace;
        }

        /**
         * Set The stack trace of the error if available.
         * @param stackTrace
         *            The stack trace of the error if available.
         */
        public void setStackTrace(List<String> stackTrace) {
            this.stackTrace = stackTrace;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "ApiErrorExceptionDTO [ " + 
                "message = " + message + ", " + 
                "exceptionClass = " + exceptionClass + ", " + 
                "stackTrace = " + stackTrace + 
                " ]";
        }
    }

    // CasesResponse DTO
    public static class CasesResponseDTO implements Serializable {

        /**
         * 
         */
        private List<CaseDTO> cases;

        /**
         * 
         */
        private List<String> errors;


        /**
         * Creates a new instance of CasesResponseDTO
         */
        public CasesResponseDTO() {}

        /**
         * Get 
         * @return 
         */
        public List<CaseDTO> getCases() {
            return cases;
        }

        /**
         * Set 
         * @param cases
         *            
         */
        public void setCases(List<CaseDTO> cases) {
            this.cases = cases;
        }

        /**
         * Get 
         * @return 
         */
        public List<String> getErrors() {
            return errors;
        }

        /**
         * Set 
         * @param errors
         *            
         */
        public void setErrors(List<String> errors) {
            this.errors = errors;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "CasesResponseDTO [ " + 
                "cases = " + cases + ", " + 
                "errors = " + errors + 
                " ]";
        }
    }

    // Case DTO
    public static class CaseDTO implements Serializable {

        /**
         * A unique HUD identifier for this loan.
         */
        private String caseNumber;

        /**
         * The name of the person who borrowed this loan.
         */
        private String borrower;

        /**
         * The geographical location of the home that&#x27;s borrowed for.
         */
        private String propertyAddress;

        /**
         * Information regarding  previous reviews, current reviews, or upcomming selection activity for this case.
         */
        private List<CaseActivityDTO> caseActivity;

        /**
         * Information regarding the lender of this loan.
         */
        private LiteLenderDTO lender;

        /**
         * A flag to alert us that underwriting reviews cannot be created for this case.
         */
        private Boolean underwritingReviewProhibited;

        /**
         * A flag to alert us that servicings reviews cannot be created for this case.
         */
        private Boolean servicingReviewProhibited;


        /**
         * Creates a new instance of CaseDTO
         */
        public CaseDTO() {}

        /**
         * Get A unique HUD identifier for this loan.
         * @return A unique HUD identifier for this loan.
         */
        public String getCaseNumber() {
            return caseNumber;
        }

        /**
         * Set A unique HUD identifier for this loan.
         * @param caseNumber
         *            A unique HUD identifier for this loan.
         */
        public void setCaseNumber(String caseNumber) {
            this.caseNumber = caseNumber;
        }

        /**
         * Get The name of the person who borrowed this loan.
         * @return The name of the person who borrowed this loan.
         */
        public String getBorrower() {
            return borrower;
        }

        /**
         * Set The name of the person who borrowed this loan.
         * @param borrower
         *            The name of the person who borrowed this loan.
         */
        public void setBorrower(String borrower) {
            this.borrower = borrower;
        }

        /**
         * Get The geographical location of the home that&#x27;s borrowed for.
         * @return The geographical location of the home that&#x27;s borrowed for.
         */
        public String getPropertyAddress() {
            return propertyAddress;
        }

        /**
         * Set The geographical location of the home that&#x27;s borrowed for.
         * @param propertyAddress
         *            The geographical location of the home that&#x27;s borrowed for.
         */
        public void setPropertyAddress(String propertyAddress) {
            this.propertyAddress = propertyAddress;
        }

        /**
         * Get Information regarding  previous reviews, current reviews, or upcomming selection activity for this case.
         * @return Information regarding  previous reviews, current reviews, or upcomming selection activity for this case.
         */
        public List<CaseActivityDTO> getCaseActivity() {
            return caseActivity;
        }

        /**
         * Set Information regarding  previous reviews, current reviews, or upcomming selection activity for this case.
         * @param caseActivity
         *            Information regarding  previous reviews, current reviews, or upcomming selection activity for this case.
         */
        public void setCaseActivity(List<CaseActivityDTO> caseActivity) {
            this.caseActivity = caseActivity;
        }

        /**
         * Get Information regarding the lender of this loan.
         * @return Information regarding the lender of this loan.
         */
        public LiteLenderDTO getLender() {
            return lender;
        }

        /**
         * Set Information regarding the lender of this loan.
         * @param lender
         *            Information regarding the lender of this loan.
         */
        public void setLender(LiteLenderDTO lender) {
            this.lender = lender;
        }

        /**
         * Get A flag to alert us that underwriting reviews cannot be created for this case.
         * @return A flag to alert us that underwriting reviews cannot be created for this case.
         */
        public Boolean getUnderwritingReviewProhibited() {
            return underwritingReviewProhibited;
        }

        /**
         * Set A flag to alert us that underwriting reviews cannot be created for this case.
         * @param underwritingReviewProhibited
         *            A flag to alert us that underwriting reviews cannot be created for this case.
         */
        public void setUnderwritingReviewProhibited(Boolean underwritingReviewProhibited) {
            this.underwritingReviewProhibited = underwritingReviewProhibited;
        }

        /**
         * Get A flag to alert us that servicings reviews cannot be created for this case.
         * @return A flag to alert us that servicings reviews cannot be created for this case.
         */
        public Boolean getServicingReviewProhibited() {
            return servicingReviewProhibited;
        }

        /**
         * Set A flag to alert us that servicings reviews cannot be created for this case.
         * @param servicingReviewProhibited
         *            A flag to alert us that servicings reviews cannot be created for this case.
         */
        public void setServicingReviewProhibited(Boolean servicingReviewProhibited) {
            this.servicingReviewProhibited = servicingReviewProhibited;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "CaseDTO [ " + 
                "caseNumber = " + caseNumber + ", " + 
                "borrower = " + borrower + ", " + 
                "propertyAddress = " + propertyAddress + ", " + 
                "caseActivity = " + caseActivity + ", " + 
                "lender = " + lender + ", " + 
                "underwritingReviewProhibited = " + underwritingReviewProhibited + ", " + 
                "servicingReviewProhibited = " + servicingReviewProhibited + 
                " ]";
        }
    }

    // CaseRequest DTO
    public static class CaseRequestDTO implements Serializable {

        /**
         * The ID of the type of review being performed.
         */
        private String reviewType;

        /**
         * The ID of the reason these cases are being requested.
         */
        private String selectionReason;

        /**
         * A collection of case numbers to retrieve.
         */
        private List<String> caseNumbers;


        /**
         * Creates a new instance of CaseRequestDTO
         */
        public CaseRequestDTO() {}

        /**
         * Get The ID of the type of review being performed.
         * @return The ID of the type of review being performed.
         */
        public String getReviewType() {
            return reviewType;
        }

        /**
         * Set The ID of the type of review being performed.
         * @param reviewType
         *            The ID of the type of review being performed.
         */
        public void setReviewType(String reviewType) {
            this.reviewType = reviewType;
        }

        /**
         * Get The ID of the reason these cases are being requested.
         * @return The ID of the reason these cases are being requested.
         */
        public String getSelectionReason() {
            return selectionReason;
        }

        /**
         * Set The ID of the reason these cases are being requested.
         * @param selectionReason
         *            The ID of the reason these cases are being requested.
         */
        public void setSelectionReason(String selectionReason) {
            this.selectionReason = selectionReason;
        }

        /**
         * Get A collection of case numbers to retrieve.
         * @return A collection of case numbers to retrieve.
         */
        public List<String> getCaseNumbers() {
            return caseNumbers;
        }

        /**
         * Set A collection of case numbers to retrieve.
         * @param caseNumbers
         *            A collection of case numbers to retrieve.
         */
        public void setCaseNumbers(List<String> caseNumbers) {
            this.caseNumbers = caseNumbers;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "CaseRequestDTO [ " + 
                "reviewType = " + reviewType + ", " + 
                "selectionReason = " + selectionReason + ", " + 
                "caseNumbers = " + caseNumbers + 
                " ]";
        }
    }

    // CaseActivity DTO
    public static class CaseActivityDTO implements Serializable {

        /**
         * The Review ID of a canceled, active, or completed review related to the case.
         */
        private String reviewId;

        /**
         * The Review reference ID of a canceled, active, or completed review related to the case.
         */
        private String reviewReferenceId;

        /**
         * The status description of the canceled, active, or completed review related to the case.
         */
        private String reviewStatus;

        /**
         * The date if this selection was distributed.
         */
        private Date distributionDate;

        /**
         * The date if the binder has been recieved for this selection.
         */
        private Date receivedDate;

        /**
         * The date if the binder has been requested for this selection.
         */
        private Date requestedDate;

        /**
         * The date that this selection was created.
         */
        private Date selectionDate;

        /**
         * The status of this selection. (e.g. Selected, Requested, Received, Distributed, Exception, Cancelled)
         */
        private String selectionStatus;

        /**
         * The case number for the loan selection
         */
        private String caseNumber;


        /**
         * Creates a new instance of CaseActivityDTO
         */
        public CaseActivityDTO() {}

        /**
         * Get The Review ID of a canceled, active, or completed review related to the case.
         * @return The Review ID of a canceled, active, or completed review related to the case.
         */
        public String getReviewId() {
            return reviewId;
        }

        /**
         * Set The Review ID of a canceled, active, or completed review related to the case.
         * @param reviewId
         *            The Review ID of a canceled, active, or completed review related to the case.
         */
        public void setReviewId(String reviewId) {
            this.reviewId = reviewId;
        }

        /**
         * Get The Review reference ID of a canceled, active, or completed review related to the case.
         * @return The Review reference ID of a canceled, active, or completed review related to the case.
         */
        public String getReviewReferenceId() {
            return reviewReferenceId;
        }

        /**
         * Set The Review reference ID of a canceled, active, or completed review related to the case.
         * @param reviewReferenceId
         *            The Review reference ID of a canceled, active, or completed review related to the case.
         */
        public void setReviewReferenceId(String reviewReferenceId) {
            this.reviewReferenceId = reviewReferenceId;
        }

        /**
         * Get The status description of the canceled, active, or completed review related to the case.
         * @return The status description of the canceled, active, or completed review related to the case.
         */
        public String getReviewStatus() {
            return reviewStatus;
        }

        /**
         * Set The status description of the canceled, active, or completed review related to the case.
         * @param reviewStatus
         *            The status description of the canceled, active, or completed review related to the case.
         */
        public void setReviewStatus(String reviewStatus) {
            this.reviewStatus = reviewStatus;
        }

        /**
         * Get The date if this selection was distributed.
         * @return The date if this selection was distributed.
         */
        public Date getDistributionDate() {
            return distributionDate;
        }

        /**
         * Set The date if this selection was distributed.
         * @param distributionDate
         *            The date if this selection was distributed.
         */
        public void setDistributionDate(Date distributionDate) {
            this.distributionDate = distributionDate;
        }

        /**
         * Get The date if the binder has been recieved for this selection.
         * @return The date if the binder has been recieved for this selection.
         */
        public Date getReceivedDate() {
            return receivedDate;
        }

        /**
         * Set The date if the binder has been recieved for this selection.
         * @param receivedDate
         *            The date if the binder has been recieved for this selection.
         */
        public void setReceivedDate(Date receivedDate) {
            this.receivedDate = receivedDate;
        }

        /**
         * Get The date if the binder has been requested for this selection.
         * @return The date if the binder has been requested for this selection.
         */
        public Date getRequestedDate() {
            return requestedDate;
        }

        /**
         * Set The date if the binder has been requested for this selection.
         * @param requestedDate
         *            The date if the binder has been requested for this selection.
         */
        public void setRequestedDate(Date requestedDate) {
            this.requestedDate = requestedDate;
        }

        /**
         * Get The date that this selection was created.
         * @return The date that this selection was created.
         */
        public Date getSelectionDate() {
            return selectionDate;
        }

        /**
         * Set The date that this selection was created.
         * @param selectionDate
         *            The date that this selection was created.
         */
        public void setSelectionDate(Date selectionDate) {
            this.selectionDate = selectionDate;
        }

        /**
         * Get The status of this selection. (e.g. Selected, Requested, Received, Distributed, Exception, Cancelled)
         * @return The status of this selection. (e.g. Selected, Requested, Received, Distributed, Exception, Cancelled)
         */
        public String getSelectionStatus() {
            return selectionStatus;
        }

        /**
         * Set The status of this selection. (e.g. Selected, Requested, Received, Distributed, Exception, Cancelled)
         * @param selectionStatus
         *            The status of this selection. (e.g. Selected, Requested, Received, Distributed, Exception, Cancelled)
         */
        public void setSelectionStatus(String selectionStatus) {
            this.selectionStatus = selectionStatus;
        }

        /**
         * Get The case number for the loan selection
         * @return The case number for the loan selection
         */
        public String getCaseNumber() {
            return caseNumber;
        }

        /**
         * Set The case number for the loan selection
         * @param caseNumber
         *            The case number for the loan selection
         */
        public void setCaseNumber(String caseNumber) {
            this.caseNumber = caseNumber;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "CaseActivityDTO [ " + 
                "reviewId = " + reviewId + ", " + 
                "reviewReferenceId = " + reviewReferenceId + ", " + 
                "reviewStatus = " + reviewStatus + ", " + 
                "distributionDate = " + distributionDate + ", " + 
                "receivedDate = " + receivedDate + ", " + 
                "requestedDate = " + requestedDate + ", " + 
                "selectionDate = " + selectionDate + ", " + 
                "selectionStatus = " + selectionStatus + ", " + 
                "caseNumber = " + caseNumber + 
                " ]";
        }
    }

    // ReviewRequestByLender DTO
    public static class ReviewRequestByLenderDTO implements Serializable {

        /**
         * The identifier of this lender
         */
        private String lenderId;

        /**
         * A collection of individual cases numbers to be reviewed.
         */
        private List<String> cases;

        /**
         * Attributes to be set on the batch once created.
         */
        private BatchDTO batchInfo;


        /**
         * Creates a new instance of ReviewRequestByLenderDTO
         */
        public ReviewRequestByLenderDTO() {}

        /**
         * Get The identifier of this lender
         * @return The identifier of this lender
         */
        public String getLenderId() {
            return lenderId;
        }

        /**
         * Set The identifier of this lender
         * @param lenderId
         *            The identifier of this lender
         */
        public void setLenderId(String lenderId) {
            this.lenderId = lenderId;
        }

        /**
         * Get A collection of individual cases numbers to be reviewed.
         * @return A collection of individual cases numbers to be reviewed.
         */
        public List<String> getCases() {
            return cases;
        }

        /**
         * Set A collection of individual cases numbers to be reviewed.
         * @param cases
         *            A collection of individual cases numbers to be reviewed.
         */
        public void setCases(List<String> cases) {
            this.cases = cases;
        }

        /**
         * Get Attributes to be set on the batch once created.
         * @return Attributes to be set on the batch once created.
         */
        public BatchDTO getBatchInfo() {
            return batchInfo;
        }

        /**
         * Set Attributes to be set on the batch once created.
         * @param batchInfo
         *            Attributes to be set on the batch once created.
         */
        public void setBatchInfo(BatchDTO batchInfo) {
            this.batchInfo = batchInfo;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "ReviewRequestByLenderDTO [ " + 
                "lenderId = " + lenderId + ", " + 
                "cases = " + cases + ", " + 
                "batchInfo = " + batchInfo + 
                " ]";
        }
    }

    // LenderMonitoring DTO
    public static class LenderMonitoringDTO implements Serializable {

        /**
         * The identifier of this lender
         */
        private String lenderId;

        /**
         * The ID of the location where the selected loans should be reviewed.
         */
        private String locationId;

        /**
         * The ID of the type of reviews that should be selected.
         */
        private String reviewTypeId;

        /**
         * The ID of where the initial loan documents should come from.
         */
        private String requestFromId;

        /**
         * Optional date to specify a site visit to the lender.
         */
        private Date dateOfSiteVisit;

        /**
         * The ID of the Loan Type that&#x27;s being requested for review.
         */
        private String loanTypeId;

        /**
         * The number of cases that should be selected for review.
         */
        private BigDecimal caseCount;

        /**
         * The date from which the Endorsement Dates can begin.
         */
        private Date endorsementStartDate;

        /**
         * The date to which the Endorsement Dates can end.
         */
        private Date endorsementEndDate;

        /**
         * Flag to signify if this is or is not an operational review.
         */
        private Boolean operationalReview;

        /**
         * An optional secondary ID to be applied to the Batch.
         */
        private String secondaryId;

        /**
         * Flag to signify if Operational Docs should be requested.
         */
        private Boolean requestOperationalDocuments;

        /**
         * Some optional text information.
         */
        private String operationalReviewGuidance;

        /**
         * ID of the Reviewer who is the Batch owner.
         */
        private String batchOwner;

        /**
         * A collection of Reviewers who should be placed on this Batch team.
         */
        private List<String> batchTeamMembers;


        /**
         * Creates a new instance of LenderMonitoringDTO
         */
        public LenderMonitoringDTO() {}

        /**
         * Get The identifier of this lender
         * @return The identifier of this lender
         */
        public String getLenderId() {
            return lenderId;
        }

        /**
         * Set The identifier of this lender
         * @param lenderId
         *            The identifier of this lender
         */
        public void setLenderId(String lenderId) {
            this.lenderId = lenderId;
        }

        /**
         * Get The ID of the location where the selected loans should be reviewed.
         * @return The ID of the location where the selected loans should be reviewed.
         */
        public String getLocationId() {
            return locationId;
        }

        /**
         * Set The ID of the location where the selected loans should be reviewed.
         * @param locationId
         *            The ID of the location where the selected loans should be reviewed.
         */
        public void setLocationId(String locationId) {
            this.locationId = locationId;
        }

        /**
         * Get The ID of the type of reviews that should be selected.
         * @return The ID of the type of reviews that should be selected.
         */
        public String getReviewTypeId() {
            return reviewTypeId;
        }

        /**
         * Set The ID of the type of reviews that should be selected.
         * @param reviewTypeId
         *            The ID of the type of reviews that should be selected.
         */
        public void setReviewTypeId(String reviewTypeId) {
            this.reviewTypeId = reviewTypeId;
        }

        /**
         * Get The ID of where the initial loan documents should come from.
         * @return The ID of where the initial loan documents should come from.
         */
        public String getRequestFromId() {
            return requestFromId;
        }

        /**
         * Set The ID of where the initial loan documents should come from.
         * @param requestFromId
         *            The ID of where the initial loan documents should come from.
         */
        public void setRequestFromId(String requestFromId) {
            this.requestFromId = requestFromId;
        }

        /**
         * Get Optional date to specify a site visit to the lender.
         * @return Optional date to specify a site visit to the lender.
         */
        public Date getDateOfSiteVisit() {
            return dateOfSiteVisit;
        }

        /**
         * Set Optional date to specify a site visit to the lender.
         * @param dateOfSiteVisit
         *            Optional date to specify a site visit to the lender.
         */
        public void setDateOfSiteVisit(Date dateOfSiteVisit) {
            this.dateOfSiteVisit = dateOfSiteVisit;
        }

        /**
         * Get The ID of the Loan Type that&#x27;s being requested for review.
         * @return The ID of the Loan Type that&#x27;s being requested for review.
         */
        public String getLoanTypeId() {
            return loanTypeId;
        }

        /**
         * Set The ID of the Loan Type that&#x27;s being requested for review.
         * @param loanTypeId
         *            The ID of the Loan Type that&#x27;s being requested for review.
         */
        public void setLoanTypeId(String loanTypeId) {
            this.loanTypeId = loanTypeId;
        }

        /**
         * Get The number of cases that should be selected for review.
         * @return The number of cases that should be selected for review.
         */
        public BigDecimal getCaseCount() {
            return caseCount;
        }

        /**
         * Set The number of cases that should be selected for review.
         * @param caseCount
         *            The number of cases that should be selected for review.
         */
        public void setCaseCount(BigDecimal caseCount) {
            this.caseCount = caseCount;
        }

        /**
         * Get The date from which the Endorsement Dates can begin.
         * @return The date from which the Endorsement Dates can begin.
         */
        public Date getEndorsementStartDate() {
            return endorsementStartDate;
        }

        /**
         * Set The date from which the Endorsement Dates can begin.
         * @param endorsementStartDate
         *            The date from which the Endorsement Dates can begin.
         */
        public void setEndorsementStartDate(Date endorsementStartDate) {
            this.endorsementStartDate = endorsementStartDate;
        }

        /**
         * Get The date to which the Endorsement Dates can end.
         * @return The date to which the Endorsement Dates can end.
         */
        public Date getEndorsementEndDate() {
            return endorsementEndDate;
        }

        /**
         * Set The date to which the Endorsement Dates can end.
         * @param endorsementEndDate
         *            The date to which the Endorsement Dates can end.
         */
        public void setEndorsementEndDate(Date endorsementEndDate) {
            this.endorsementEndDate = endorsementEndDate;
        }

        /**
         * Get Flag to signify if this is or is not an operational review.
         * @return Flag to signify if this is or is not an operational review.
         */
        public Boolean getOperationalReview() {
            return operationalReview;
        }

        /**
         * Set Flag to signify if this is or is not an operational review.
         * @param operationalReview
         *            Flag to signify if this is or is not an operational review.
         */
        public void setOperationalReview(Boolean operationalReview) {
            this.operationalReview = operationalReview;
        }

        /**
         * Get An optional secondary ID to be applied to the Batch.
         * @return An optional secondary ID to be applied to the Batch.
         */
        public String getSecondaryId() {
            return secondaryId;
        }

        /**
         * Set An optional secondary ID to be applied to the Batch.
         * @param secondaryId
         *            An optional secondary ID to be applied to the Batch.
         */
        public void setSecondaryId(String secondaryId) {
            this.secondaryId = secondaryId;
        }

        /**
         * Get Flag to signify if Operational Docs should be requested.
         * @return Flag to signify if Operational Docs should be requested.
         */
        public Boolean getRequestOperationalDocuments() {
            return requestOperationalDocuments;
        }

        /**
         * Set Flag to signify if Operational Docs should be requested.
         * @param requestOperationalDocuments
         *            Flag to signify if Operational Docs should be requested.
         */
        public void setRequestOperationalDocuments(Boolean requestOperationalDocuments) {
            this.requestOperationalDocuments = requestOperationalDocuments;
        }

        /**
         * Get Some optional text information.
         * @return Some optional text information.
         */
        public String getOperationalReviewGuidance() {
            return operationalReviewGuidance;
        }

        /**
         * Set Some optional text information.
         * @param operationalReviewGuidance
         *            Some optional text information.
         */
        public void setOperationalReviewGuidance(String operationalReviewGuidance) {
            this.operationalReviewGuidance = operationalReviewGuidance;
        }

        /**
         * Get ID of the Reviewer who is the Batch owner.
         * @return ID of the Reviewer who is the Batch owner.
         */
        public String getBatchOwner() {
            return batchOwner;
        }

        /**
         * Set ID of the Reviewer who is the Batch owner.
         * @param batchOwner
         *            ID of the Reviewer who is the Batch owner.
         */
        public void setBatchOwner(String batchOwner) {
            this.batchOwner = batchOwner;
        }

        /**
         * Get A collection of Reviewers who should be placed on this Batch team.
         * @return A collection of Reviewers who should be placed on this Batch team.
         */
        public List<String> getBatchTeamMembers() {
            return batchTeamMembers;
        }

        /**
         * Set A collection of Reviewers who should be placed on this Batch team.
         * @param batchTeamMembers
         *            A collection of Reviewers who should be placed on this Batch team.
         */
        public void setBatchTeamMembers(List<String> batchTeamMembers) {
            this.batchTeamMembers = batchTeamMembers;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "LenderMonitoringDTO [ " + 
                "lenderId = " + lenderId + ", " + 
                "locationId = " + locationId + ", " + 
                "reviewTypeId = " + reviewTypeId + ", " + 
                "requestFromId = " + requestFromId + ", " + 
                "dateOfSiteVisit = " + dateOfSiteVisit + ", " + 
                "loanTypeId = " + loanTypeId + ", " + 
                "caseCount = " + caseCount + ", " + 
                "endorsementStartDate = " + endorsementStartDate + ", " + 
                "endorsementEndDate = " + endorsementEndDate + ", " + 
                "operationalReview = " + operationalReview + ", " + 
                "secondaryId = " + secondaryId + ", " + 
                "requestOperationalDocuments = " + requestOperationalDocuments + ", " + 
                "operationalReviewGuidance = " + operationalReviewGuidance + ", " + 
                "batchOwner = " + batchOwner + ", " + 
                "batchTeamMembers = " + batchTeamMembers + 
                " ]";
        }
    }

    // LenderSelfReport DTO
    public static class LenderSelfReportDTO implements Serializable {

        /**
         * The ID of the Review Type these loans have been selected for.
         */
        private String reviewType;

        /**
         * A collection of IDs for applicable Defect Areas. Eg. 72CD9424-41C9-4C5D-996C-55241214EC26
         */
        private List<String> defectAreas;

        /**
         * A collection of case numbers that need to be reviewed.
         */
        private List<String> cases;

        /**
         * 
         */
        private Boolean isFraudDetected;

        /**
         * A collection of IDs for applicable Fraud Types.
         */
        private List<String> typesOfFraud;

        /**
         * A collection of IDs for applicable Fraud Participants.
         */
        private List<String> fraudParticipants;

        /**
         * A short description of the findings which causes this case to need review.
         */
        private String descriptionOfFindings;

        /**
         * A short description of the corrective actions being taken in this case.
         */
        private String descriptionOfCorrectiveActions;

        /**
         * A flag to signal that the given cases/loans are covered under settlement with HUD.
         */
        private Boolean isCoveredUnderSettlement;


        /**
         * Creates a new instance of LenderSelfReportDTO
         */
        public LenderSelfReportDTO() {}

        /**
         * Get The ID of the Review Type these loans have been selected for.
         * @return The ID of the Review Type these loans have been selected for.
         */
        public String getReviewType() {
            return reviewType;
        }

        /**
         * Set The ID of the Review Type these loans have been selected for.
         * @param reviewType
         *            The ID of the Review Type these loans have been selected for.
         */
        public void setReviewType(String reviewType) {
            this.reviewType = reviewType;
        }

        /**
         * Get A collection of IDs for applicable Defect Areas. Eg. 72CD9424-41C9-4C5D-996C-55241214EC26
         * @return A collection of IDs for applicable Defect Areas. Eg. 72CD9424-41C9-4C5D-996C-55241214EC26
         */
        public List<String> getDefectAreas() {
            return defectAreas;
        }

        /**
         * Set A collection of IDs for applicable Defect Areas. Eg. 72CD9424-41C9-4C5D-996C-55241214EC26
         * @param defectAreas
         *            A collection of IDs for applicable Defect Areas. Eg. 72CD9424-41C9-4C5D-996C-55241214EC26
         */
        public void setDefectAreas(List<String> defectAreas) {
            this.defectAreas = defectAreas;
        }

        /**
         * Get A collection of case numbers that need to be reviewed.
         * @return A collection of case numbers that need to be reviewed.
         */
        public List<String> getCases() {
            return cases;
        }

        /**
         * Set A collection of case numbers that need to be reviewed.
         * @param cases
         *            A collection of case numbers that need to be reviewed.
         */
        public void setCases(List<String> cases) {
            this.cases = cases;
        }

        /**
         * Get 
         * @return 
         */
        public Boolean getIsFraudDetected() {
            return isFraudDetected;
        }

        /**
         * Set 
         * @param isFraudDetected
         *            
         */
        public void setIsFraudDetected(Boolean isFraudDetected) {
            this.isFraudDetected = isFraudDetected;
        }

        /**
         * Get A collection of IDs for applicable Fraud Types.
         * @return A collection of IDs for applicable Fraud Types.
         */
        public List<String> getTypesOfFraud() {
            return typesOfFraud;
        }

        /**
         * Set A collection of IDs for applicable Fraud Types.
         * @param typesOfFraud
         *            A collection of IDs for applicable Fraud Types.
         */
        public void setTypesOfFraud(List<String> typesOfFraud) {
            this.typesOfFraud = typesOfFraud;
        }

        /**
         * Get A collection of IDs for applicable Fraud Participants.
         * @return A collection of IDs for applicable Fraud Participants.
         */
        public List<String> getFraudParticipants() {
            return fraudParticipants;
        }

        /**
         * Set A collection of IDs for applicable Fraud Participants.
         * @param fraudParticipants
         *            A collection of IDs for applicable Fraud Participants.
         */
        public void setFraudParticipants(List<String> fraudParticipants) {
            this.fraudParticipants = fraudParticipants;
        }

        /**
         * Get A short description of the findings which causes this case to need review.
         * @return A short description of the findings which causes this case to need review.
         */
        public String getDescriptionOfFindings() {
            return descriptionOfFindings;
        }

        /**
         * Set A short description of the findings which causes this case to need review.
         * @param descriptionOfFindings
         *            A short description of the findings which causes this case to need review.
         */
        public void setDescriptionOfFindings(String descriptionOfFindings) {
            this.descriptionOfFindings = descriptionOfFindings;
        }

        /**
         * Get A short description of the corrective actions being taken in this case.
         * @return A short description of the corrective actions being taken in this case.
         */
        public String getDescriptionOfCorrectiveActions() {
            return descriptionOfCorrectiveActions;
        }

        /**
         * Set A short description of the corrective actions being taken in this case.
         * @param descriptionOfCorrectiveActions
         *            A short description of the corrective actions being taken in this case.
         */
        public void setDescriptionOfCorrectiveActions(String descriptionOfCorrectiveActions) {
            this.descriptionOfCorrectiveActions = descriptionOfCorrectiveActions;
        }

        /**
         * Get A flag to signal that the given cases/loans are covered under settlement with HUD.
         * @return A flag to signal that the given cases/loans are covered under settlement with HUD.
         */
        public Boolean getIsCoveredUnderSettlement() {
            return isCoveredUnderSettlement;
        }

        /**
         * Set A flag to signal that the given cases/loans are covered under settlement with HUD.
         * @param isCoveredUnderSettlement
         *            A flag to signal that the given cases/loans are covered under settlement with HUD.
         */
        public void setIsCoveredUnderSettlement(Boolean isCoveredUnderSettlement) {
            this.isCoveredUnderSettlement = isCoveredUnderSettlement;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "LenderSelfReportDTO [ " + 
                "reviewType = " + reviewType + ", " + 
                "defectAreas = " + defectAreas + ", " + 
                "cases = " + cases + ", " + 
                "isFraudDetected = " + isFraudDetected + ", " + 
                "typesOfFraud = " + typesOfFraud + ", " + 
                "fraudParticipants = " + fraudParticipants + ", " + 
                "descriptionOfFindings = " + descriptionOfFindings + ", " + 
                "descriptionOfCorrectiveActions = " + descriptionOfCorrectiveActions + ", " + 
                "isCoveredUnderSettlement = " + isCoveredUnderSettlement + 
                " ]";
        }
    }

    // Exception DTO
    public static class ExceptionDTO implements Serializable {

        /**
         * The identifier of the object
         */
        private String exceptionId;

        /**
         * The case number for the loan.
         */
        private String caseNumber;

        /**
         * The legal name of the borrower.
         */
        private String borrowerName;

        /**
         * The name of the lender who issues this loan.
         */
        private String lenderName;

        /**
         * The identifier of the object of the business rule that triggered the need to review this loan.  For example: &#x27;Lender Monitoring&#x27;, &#x27;Random&#x27;, &#x27;Lender Self&#x27;
         */
        private String selectionReasonCode;

        /**
         * Short name of the business rule that triggered the need to review this loan.  For example: &#x27;Lender Monitoring&#x27;, &#x27;Random&#x27;, &#x27;Lender Self&#x27;
         */
        private String selectionReason;

        /**
         * The numeric value representing the level of risk for this loan.
         */
        private BigDecimal riskScore;

        /**
         * The identifier of the exception that needs attention on this review.
         */
        private String exceptionTypeCode;

        /**
         * The short name of the exception that needs attention on this review.
         */
        private String exceptionType;

        /**
         * The number of days that this loan has been on the exception list.
         */
        private BigDecimal daysOnList;

        /**
         * This is used to resolve assignment exceptions.
         */
        private String assignedTo;

        /**
         * This is used to resolve missing batch info exceptions.
         */
        private BatchDTO batchInfo;

        /**
         * The identifier of the review
         */
        private String reviewId;

        /**
         * The current review level object for this loan review. Each Review will have multiple review levels (i.e. &#x27;passes&#x27;) throughout it&#x27;s life. Once this review level is completed, a new review level will be created and this one will be archived in the completedReviewLevels collection.
         */
        private ReviewLevelDTO currentReviewLevel;

        /**
         * The short name of the Loan Type that&#x27;s being requested for review.
         */
        private String loanType;

        /**
         * The location where this distribution exception should be resolved to.
         */
        private String reviewLocationId;

        /**
         * Review Type of this loan.
         */
        private String reviewType;


        /**
         * Creates a new instance of ExceptionDTO
         */
        public ExceptionDTO() {}

        /**
         * Get The identifier of the object
         * @return The identifier of the object
         */
        public String getExceptionId() {
            return exceptionId;
        }

        /**
         * Set The identifier of the object
         * @param exceptionId
         *            The identifier of the object
         */
        public void setExceptionId(String exceptionId) {
            this.exceptionId = exceptionId;
        }

        /**
         * Get The case number for the loan.
         * @return The case number for the loan.
         */
        public String getCaseNumber() {
            return caseNumber;
        }

        /**
         * Set The case number for the loan.
         * @param caseNumber
         *            The case number for the loan.
         */
        public void setCaseNumber(String caseNumber) {
            this.caseNumber = caseNumber;
        }

        /**
         * Get The legal name of the borrower.
         * @return The legal name of the borrower.
         */
        public String getBorrowerName() {
            return borrowerName;
        }

        /**
         * Set The legal name of the borrower.
         * @param borrowerName
         *            The legal name of the borrower.
         */
        public void setBorrowerName(String borrowerName) {
            this.borrowerName = borrowerName;
        }

        /**
         * Get The name of the lender who issues this loan.
         * @return The name of the lender who issues this loan.
         */
        public String getLenderName() {
            return lenderName;
        }

        /**
         * Set The name of the lender who issues this loan.
         * @param lenderName
         *            The name of the lender who issues this loan.
         */
        public void setLenderName(String lenderName) {
            this.lenderName = lenderName;
        }

        /**
         * Get The identifier of the object of the business rule that triggered the need to review this loan.  For example: &#x27;Lender Monitoring&#x27;, &#x27;Random&#x27;, &#x27;Lender Self&#x27;
         * @return The identifier of the object of the business rule that triggered the need to review this loan.  For example: &#x27;Lender Monitoring&#x27;, &#x27;Random&#x27;, &#x27;Lender Self&#x27;
         */
        public String getSelectionReasonCode() {
            return selectionReasonCode;
        }

        /**
         * Set The identifier of the object of the business rule that triggered the need to review this loan.  For example: &#x27;Lender Monitoring&#x27;, &#x27;Random&#x27;, &#x27;Lender Self&#x27;
         * @param selectionReasonCode
         *            The identifier of the object of the business rule that triggered the need to review this loan.  For example: &#x27;Lender Monitoring&#x27;, &#x27;Random&#x27;, &#x27;Lender Self&#x27;
         */
        public void setSelectionReasonCode(String selectionReasonCode) {
            this.selectionReasonCode = selectionReasonCode;
        }

        /**
         * Get Short name of the business rule that triggered the need to review this loan.  For example: &#x27;Lender Monitoring&#x27;, &#x27;Random&#x27;, &#x27;Lender Self&#x27;
         * @return Short name of the business rule that triggered the need to review this loan.  For example: &#x27;Lender Monitoring&#x27;, &#x27;Random&#x27;, &#x27;Lender Self&#x27;
         */
        public String getSelectionReason() {
            return selectionReason;
        }

        /**
         * Set Short name of the business rule that triggered the need to review this loan.  For example: &#x27;Lender Monitoring&#x27;, &#x27;Random&#x27;, &#x27;Lender Self&#x27;
         * @param selectionReason
         *            Short name of the business rule that triggered the need to review this loan.  For example: &#x27;Lender Monitoring&#x27;, &#x27;Random&#x27;, &#x27;Lender Self&#x27;
         */
        public void setSelectionReason(String selectionReason) {
            this.selectionReason = selectionReason;
        }

        /**
         * Get The numeric value representing the level of risk for this loan.
         * @return The numeric value representing the level of risk for this loan.
         */
        public BigDecimal getRiskScore() {
            return riskScore;
        }

        /**
         * Set The numeric value representing the level of risk for this loan.
         * @param riskScore
         *            The numeric value representing the level of risk for this loan.
         */
        public void setRiskScore(BigDecimal riskScore) {
            this.riskScore = riskScore;
        }

        /**
         * Get The identifier of the exception that needs attention on this review.
         * @return The identifier of the exception that needs attention on this review.
         */
        public String getExceptionTypeCode() {
            return exceptionTypeCode;
        }

        /**
         * Set The identifier of the exception that needs attention on this review.
         * @param exceptionTypeCode
         *            The identifier of the exception that needs attention on this review.
         */
        public void setExceptionTypeCode(String exceptionTypeCode) {
            this.exceptionTypeCode = exceptionTypeCode;
        }

        /**
         * Get The short name of the exception that needs attention on this review.
         * @return The short name of the exception that needs attention on this review.
         */
        public String getExceptionType() {
            return exceptionType;
        }

        /**
         * Set The short name of the exception that needs attention on this review.
         * @param exceptionType
         *            The short name of the exception that needs attention on this review.
         */
        public void setExceptionType(String exceptionType) {
            this.exceptionType = exceptionType;
        }

        /**
         * Get The number of days that this loan has been on the exception list.
         * @return The number of days that this loan has been on the exception list.
         */
        public BigDecimal getDaysOnList() {
            return daysOnList;
        }

        /**
         * Set The number of days that this loan has been on the exception list.
         * @param daysOnList
         *            The number of days that this loan has been on the exception list.
         */
        public void setDaysOnList(BigDecimal daysOnList) {
            this.daysOnList = daysOnList;
        }

        /**
         * Get This is used to resolve assignment exceptions.
         * @return This is used to resolve assignment exceptions.
         */
        public String getAssignedTo() {
            return assignedTo;
        }

        /**
         * Set This is used to resolve assignment exceptions.
         * @param assignedTo
         *            This is used to resolve assignment exceptions.
         */
        public void setAssignedTo(String assignedTo) {
            this.assignedTo = assignedTo;
        }

        /**
         * Get This is used to resolve missing batch info exceptions.
         * @return This is used to resolve missing batch info exceptions.
         */
        public BatchDTO getBatchInfo() {
            return batchInfo;
        }

        /**
         * Set This is used to resolve missing batch info exceptions.
         * @param batchInfo
         *            This is used to resolve missing batch info exceptions.
         */
        public void setBatchInfo(BatchDTO batchInfo) {
            this.batchInfo = batchInfo;
        }

        /**
         * Get The identifier of the review
         * @return The identifier of the review
         */
        public String getReviewId() {
            return reviewId;
        }

        /**
         * Set The identifier of the review
         * @param reviewId
         *            The identifier of the review
         */
        public void setReviewId(String reviewId) {
            this.reviewId = reviewId;
        }

        /**
         * Get The current review level object for this loan review. Each Review will have multiple review levels (i.e. &#x27;passes&#x27;) throughout it&#x27;s life. Once this review level is completed, a new review level will be created and this one will be archived in the completedReviewLevels collection.
         * @return The current review level object for this loan review. Each Review will have multiple review levels (i.e. &#x27;passes&#x27;) throughout it&#x27;s life. Once this review level is completed, a new review level will be created and this one will be archived in the completedReviewLevels collection.
         */
        public ReviewLevelDTO getCurrentReviewLevel() {
            return currentReviewLevel;
        }

        /**
         * Set The current review level object for this loan review. Each Review will have multiple review levels (i.e. &#x27;passes&#x27;) throughout it&#x27;s life. Once this review level is completed, a new review level will be created and this one will be archived in the completedReviewLevels collection.
         * @param currentReviewLevel
         *            The current review level object for this loan review. Each Review will have multiple review levels (i.e. &#x27;passes&#x27;) throughout it&#x27;s life. Once this review level is completed, a new review level will be created and this one will be archived in the completedReviewLevels collection.
         */
        public void setCurrentReviewLevel(ReviewLevelDTO currentReviewLevel) {
            this.currentReviewLevel = currentReviewLevel;
        }

        /**
         * Get The short name of the Loan Type that&#x27;s being requested for review.
         * @return The short name of the Loan Type that&#x27;s being requested for review.
         */
        public String getLoanType() {
            return loanType;
        }

        /**
         * Set The short name of the Loan Type that&#x27;s being requested for review.
         * @param loanType
         *            The short name of the Loan Type that&#x27;s being requested for review.
         */
        public void setLoanType(String loanType) {
            this.loanType = loanType;
        }

        /**
         * Get The location where this distribution exception should be resolved to.
         * @return The location where this distribution exception should be resolved to.
         */
        public String getReviewLocationId() {
            return reviewLocationId;
        }

        /**
         * Set The location where this distribution exception should be resolved to.
         * @param reviewLocationId
         *            The location where this distribution exception should be resolved to.
         */
        public void setReviewLocationId(String reviewLocationId) {
            this.reviewLocationId = reviewLocationId;
        }

        /**
         * Get Review Type of this loan.
         * @return Review Type of this loan.
         */
        public String getReviewType() {
            return reviewType;
        }

        /**
         * Set Review Type of this loan.
         * @param reviewType
         *            Review Type of this loan.
         */
        public void setReviewType(String reviewType) {
            this.reviewType = reviewType;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "ExceptionDTO [ " + 
                "exceptionId = " + exceptionId + ", " + 
                "caseNumber = " + caseNumber + ", " + 
                "borrowerName = " + borrowerName + ", " + 
                "lenderName = " + lenderName + ", " + 
                "selectionReasonCode = " + selectionReasonCode + ", " + 
                "selectionReason = " + selectionReason + ", " + 
                "riskScore = " + riskScore + ", " + 
                "exceptionTypeCode = " + exceptionTypeCode + ", " + 
                "exceptionType = " + exceptionType + ", " + 
                "daysOnList = " + daysOnList + ", " + 
                "assignedTo = " + assignedTo + ", " + 
                "batchInfo = " + batchInfo + ", " + 
                "reviewId = " + reviewId + ", " + 
                "currentReviewLevel = " + currentReviewLevel + ", " + 
                "loanType = " + loanType + ", " + 
                "reviewLocationId = " + reviewLocationId + ", " + 
                "reviewType = " + reviewType + 
                " ]";
        }
    }

    // CurrentUser DTO
    public static class CurrentUserDTO implements Serializable {

        /**
         * LRS-specific unique identifier for the current user if they are HUD personnel
         */
        private String personnelId;

        /**
         * The login identifier of the user; the user&#x27;s HID.
         */
        private String hudId;

        /**
         * A collection of this user&#x27;s access rights.
         */
        private List<String> authorities;

        /**
         * The first name of the current user.
         */
        private String firstName;

        /**
         * The last name of the current user.
         */
        private String lastName;

        /**
         * An object containing information about the lender this user is associated with.
         */
        private LiteLenderDTO lender;

        /**
         * Default location of user.
         */
        private String locationId;

        /**
         * Collection of selection reason skill codes
         */
        private List<String> selectionReasonSkillCodes;

        /**
         * Collection of selection review type skill codes
         */
        private List<String> reviewTypeSkillCodes;

        /**
         * Collection of product type skill codes
         */
        private List<String> productTypeSkillCodes;

        /**
         * Collection of selection review level skill codes
         */
        private List<String> reviewLevelSkillCodes;


        /**
         * Creates a new instance of CurrentUserDTO
         */
        public CurrentUserDTO() {}

        /**
         * Get LRS-specific unique identifier for the current user if they are HUD personnel
         * @return LRS-specific unique identifier for the current user if they are HUD personnel
         */
        public String getPersonnelId() {
            return personnelId;
        }

        /**
         * Set LRS-specific unique identifier for the current user if they are HUD personnel
         * @param personnelId
         *            LRS-specific unique identifier for the current user if they are HUD personnel
         */
        public void setPersonnelId(String personnelId) {
            this.personnelId = personnelId;
        }

        /**
         * Get The login identifier of the user; the user&#x27;s HID.
         * @return The login identifier of the user; the user&#x27;s HID.
         */
        public String getHudId() {
            return hudId;
        }

        /**
         * Set The login identifier of the user; the user&#x27;s HID.
         * @param hudId
         *            The login identifier of the user; the user&#x27;s HID.
         */
        public void setHudId(String hudId) {
            this.hudId = hudId;
        }

        /**
         * Get A collection of this user&#x27;s access rights.
         * @return A collection of this user&#x27;s access rights.
         */
        public List<String> getAuthorities() {
            return authorities;
        }

        /**
         * Set A collection of this user&#x27;s access rights.
         * @param authorities
         *            A collection of this user&#x27;s access rights.
         */
        public void setAuthorities(List<String> authorities) {
            this.authorities = authorities;
        }

        /**
         * Get The first name of the current user.
         * @return The first name of the current user.
         */
        public String getFirstName() {
            return firstName;
        }

        /**
         * Set The first name of the current user.
         * @param firstName
         *            The first name of the current user.
         */
        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        /**
         * Get The last name of the current user.
         * @return The last name of the current user.
         */
        public String getLastName() {
            return lastName;
        }

        /**
         * Set The last name of the current user.
         * @param lastName
         *            The last name of the current user.
         */
        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        /**
         * Get An object containing information about the lender this user is associated with.
         * @return An object containing information about the lender this user is associated with.
         */
        public LiteLenderDTO getLender() {
            return lender;
        }

        /**
         * Set An object containing information about the lender this user is associated with.
         * @param lender
         *            An object containing information about the lender this user is associated with.
         */
        public void setLender(LiteLenderDTO lender) {
            this.lender = lender;
        }

        /**
         * Get Default location of user.
         * @return Default location of user.
         */
        public String getLocationId() {
            return locationId;
        }

        /**
         * Set Default location of user.
         * @param locationId
         *            Default location of user.
         */
        public void setLocationId(String locationId) {
            this.locationId = locationId;
        }

        /**
         * Get Collection of selection reason skill codes
         * @return Collection of selection reason skill codes
         */
        public List<String> getSelectionReasonSkillCodes() {
            return selectionReasonSkillCodes;
        }

        /**
         * Set Collection of selection reason skill codes
         * @param selectionReasonSkillCodes
         *            Collection of selection reason skill codes
         */
        public void setSelectionReasonSkillCodes(List<String> selectionReasonSkillCodes) {
            this.selectionReasonSkillCodes = selectionReasonSkillCodes;
        }

        /**
         * Get Collection of selection review type skill codes
         * @return Collection of selection review type skill codes
         */
        public List<String> getReviewTypeSkillCodes() {
            return reviewTypeSkillCodes;
        }

        /**
         * Set Collection of selection review type skill codes
         * @param reviewTypeSkillCodes
         *            Collection of selection review type skill codes
         */
        public void setReviewTypeSkillCodes(List<String> reviewTypeSkillCodes) {
            this.reviewTypeSkillCodes = reviewTypeSkillCodes;
        }

        /**
         * Get Collection of product type skill codes
         * @return Collection of product type skill codes
         */
        public List<String> getProductTypeSkillCodes() {
            return productTypeSkillCodes;
        }

        /**
         * Set Collection of product type skill codes
         * @param productTypeSkillCodes
         *            Collection of product type skill codes
         */
        public void setProductTypeSkillCodes(List<String> productTypeSkillCodes) {
            this.productTypeSkillCodes = productTypeSkillCodes;
        }

        /**
         * Get Collection of selection review level skill codes
         * @return Collection of selection review level skill codes
         */
        public List<String> getReviewLevelSkillCodes() {
            return reviewLevelSkillCodes;
        }

        /**
         * Set Collection of selection review level skill codes
         * @param reviewLevelSkillCodes
         *            Collection of selection review level skill codes
         */
        public void setReviewLevelSkillCodes(List<String> reviewLevelSkillCodes) {
            this.reviewLevelSkillCodes = reviewLevelSkillCodes;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "CurrentUserDTO [ " + 
                "personnelId = " + personnelId + ", " + 
                "hudId = " + hudId + ", " + 
                "authorities = " + authorities + ", " + 
                "firstName = " + firstName + ", " + 
                "lastName = " + lastName + ", " + 
                "lender = " + lender + ", " + 
                "locationId = " + locationId + ", " + 
                "selectionReasonSkillCodes = " + selectionReasonSkillCodes + ", " + 
                "reviewTypeSkillCodes = " + reviewTypeSkillCodes + ", " + 
                "productTypeSkillCodes = " + productTypeSkillCodes + ", " + 
                "reviewLevelSkillCodes = " + reviewLevelSkillCodes + 
                " ]";
        }
    }

    // TimeLimit DTO
    public static class TimeLimitDTO implements Serializable {

        /**
         * The code of the selection reason this time limit is for.
         */
        private String selectionReasonCode;

        /**
         * The number of days allowed as a time limit.
         */
        private BigDecimal days;


        /**
         * Creates a new instance of TimeLimitDTO
         */
        public TimeLimitDTO() {}

        /**
         * Get The code of the selection reason this time limit is for.
         * @return The code of the selection reason this time limit is for.
         */
        public String getSelectionReasonCode() {
            return selectionReasonCode;
        }

        /**
         * Set The code of the selection reason this time limit is for.
         * @param selectionReasonCode
         *            The code of the selection reason this time limit is for.
         */
        public void setSelectionReasonCode(String selectionReasonCode) {
            this.selectionReasonCode = selectionReasonCode;
        }

        /**
         * Get The number of days allowed as a time limit.
         * @return The number of days allowed as a time limit.
         */
        public BigDecimal getDays() {
            return days;
        }

        /**
         * Set The number of days allowed as a time limit.
         * @param days
         *            The number of days allowed as a time limit.
         */
        public void setDays(BigDecimal days) {
            this.days = days;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "TimeLimitDTO [ " + 
                "selectionReasonCode = " + selectionReasonCode + ", " + 
                "days = " + days + 
                " ]";
        }
    }

    // ReviewLevelTimeLimits DTO
    public static class ReviewLevelTimeLimitsDTO implements Serializable {

        /**
         * The human readable name of this review level.
         */
        private String reviewLevelDescription;

        /**
         * The code of this review level.
         */
        private String reviewLevelCode;

        /**
         * A collection of time limits per selection reason.
         */
        private List<TimeLimitDTO> selectionReasons;


        /**
         * Creates a new instance of ReviewLevelTimeLimitsDTO
         */
        public ReviewLevelTimeLimitsDTO() {}

        /**
         * Get The human readable name of this review level.
         * @return The human readable name of this review level.
         */
        public String getReviewLevelDescription() {
            return reviewLevelDescription;
        }

        /**
         * Set The human readable name of this review level.
         * @param reviewLevelDescription
         *            The human readable name of this review level.
         */
        public void setReviewLevelDescription(String reviewLevelDescription) {
            this.reviewLevelDescription = reviewLevelDescription;
        }

        /**
         * Get The code of this review level.
         * @return The code of this review level.
         */
        public String getReviewLevelCode() {
            return reviewLevelCode;
        }

        /**
         * Set The code of this review level.
         * @param reviewLevelCode
         *            The code of this review level.
         */
        public void setReviewLevelCode(String reviewLevelCode) {
            this.reviewLevelCode = reviewLevelCode;
        }

        /**
         * Get A collection of time limits per selection reason.
         * @return A collection of time limits per selection reason.
         */
        public List<TimeLimitDTO> getSelectionReasons() {
            return selectionReasons;
        }

        /**
         * Set A collection of time limits per selection reason.
         * @param selectionReasons
         *            A collection of time limits per selection reason.
         */
        public void setSelectionReasons(List<TimeLimitDTO> selectionReasons) {
            this.selectionReasons = selectionReasons;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "ReviewLevelTimeLimitsDTO [ " + 
                "reviewLevelDescription = " + reviewLevelDescription + ", " + 
                "reviewLevelCode = " + reviewLevelCode + ", " + 
                "selectionReasons = " + selectionReasons + 
                " ]";
        }
    }

    // Capacity DTO
    public static class CapacityDTO implements Serializable {

        /**
         * The ID of the associated Reviewer or Location
         */
        private String id;

        /**
         * A human readable and meaningful name or description.
         */
        private String display;

        /**
         * The total capacity limit of this Reviewer or Location.
         */
        private BigDecimal totalCapacity;

        /**
         * The totalCapacity minus the actual monthly assignments thus far.
         */
        private BigDecimal availableCapacity;


        /**
         * Creates a new instance of CapacityDTO
         */
        public CapacityDTO() {}

        /**
         * Get The ID of the associated Reviewer or Location
         * @return The ID of the associated Reviewer or Location
         */
        public String getId() {
            return id;
        }

        /**
         * Set The ID of the associated Reviewer or Location
         * @param id
         *            The ID of the associated Reviewer or Location
         */
        public void setId(String id) {
            this.id = id;
        }

        /**
         * Get A human readable and meaningful name or description.
         * @return A human readable and meaningful name or description.
         */
        public String getDisplay() {
            return display;
        }

        /**
         * Set A human readable and meaningful name or description.
         * @param display
         *            A human readable and meaningful name or description.
         */
        public void setDisplay(String display) {
            this.display = display;
        }

        /**
         * Get The total capacity limit of this Reviewer or Location.
         * @return The total capacity limit of this Reviewer or Location.
         */
        public BigDecimal getTotalCapacity() {
            return totalCapacity;
        }

        /**
         * Set The total capacity limit of this Reviewer or Location.
         * @param totalCapacity
         *            The total capacity limit of this Reviewer or Location.
         */
        public void setTotalCapacity(BigDecimal totalCapacity) {
            this.totalCapacity = totalCapacity;
        }

        /**
         * Get The totalCapacity minus the actual monthly assignments thus far.
         * @return The totalCapacity minus the actual monthly assignments thus far.
         */
        public BigDecimal getAvailableCapacity() {
            return availableCapacity;
        }

        /**
         * Set The totalCapacity minus the actual monthly assignments thus far.
         * @param availableCapacity
         *            The totalCapacity minus the actual monthly assignments thus far.
         */
        public void setAvailableCapacity(BigDecimal availableCapacity) {
            this.availableCapacity = availableCapacity;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "CapacityDTO [ " + 
                "id = " + id + ", " + 
                "display = " + display + ", " + 
                "totalCapacity = " + totalCapacity + ", " + 
                "availableCapacity = " + availableCapacity + 
                " ]";
        }
    }

    // Factor DTO
    public static class FactorDTO implements Serializable {

        /**
         * An unchanging unique identifier for this factor.
         */
        private String id;

        /**
         * A human readable identifier for this selection model.
         */
        private String name;

        /**
         * An extended and much more verbose explanation of what this factor consists of.
         */
        private String description;

        /**
         * A multiplier for this particular factor. This is used for saving Factors to models.
         */
        private BigDecimal weight;


        /**
         * Creates a new instance of FactorDTO
         */
        public FactorDTO() {}

        /**
         * Get An unchanging unique identifier for this factor.
         * @return An unchanging unique identifier for this factor.
         */
        public String getId() {
            return id;
        }

        /**
         * Set An unchanging unique identifier for this factor.
         * @param id
         *            An unchanging unique identifier for this factor.
         */
        public void setId(String id) {
            this.id = id;
        }

        /**
         * Get A human readable identifier for this selection model.
         * @return A human readable identifier for this selection model.
         */
        public String getName() {
            return name;
        }

        /**
         * Set A human readable identifier for this selection model.
         * @param name
         *            A human readable identifier for this selection model.
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * Get An extended and much more verbose explanation of what this factor consists of.
         * @return An extended and much more verbose explanation of what this factor consists of.
         */
        public String getDescription() {
            return description;
        }

        /**
         * Set An extended and much more verbose explanation of what this factor consists of.
         * @param description
         *            An extended and much more verbose explanation of what this factor consists of.
         */
        public void setDescription(String description) {
            this.description = description;
        }

        /**
         * Get A multiplier for this particular factor. This is used for saving Factors to models.
         * @return A multiplier for this particular factor. This is used for saving Factors to models.
         */
        public BigDecimal getWeight() {
            return weight;
        }

        /**
         * Set A multiplier for this particular factor. This is used for saving Factors to models.
         * @param weight
         *            A multiplier for this particular factor. This is used for saving Factors to models.
         */
        public void setWeight(BigDecimal weight) {
            this.weight = weight;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "FactorDTO [ " + 
                "id = " + id + ", " + 
                "name = " + name + ", " + 
                "description = " + description + ", " + 
                "weight = " + weight + 
                " ]";
        }
    }

    // SelectionModel DTO
    public static class SelectionModelDTO implements Serializable {

        /**
         * A unique, unchanging identifier for this selection model.
         */
        private String id;

        /**
         * The ID of the Selection Model Category that this model is a version of.
         */
        private String selectionModelCategory;

        /**
         * A human readable identifier for this selection model.
         */
        private String name;

        /**
         * The incremented version for this model (by name).
         */
        private Integer version;

        /**
         * An optional note field.
         */
        private String description;

        /**
         * A collection of factors that make up this model.
         */
        private List<FactorDTO> factors;

        /**
         * A top level multiplier for this selection model.
         */
        private BigDecimal selectionThreshold;

        /**
         * A top level allocation power for this selection model.
         */
        private BigDecimal allocationPercentage;

        /**
         * An enumerated condition. Either &#x27;active&#x27;, &#x27;draft&#x27;, or &#x27;archived&#x27;.
         */
        private String status;

        /**
         * An enumerated condition. Either &#x27;limited&#x27;, or &#x27;full&#x27;.
         */
        private String scope;

        /**
         * Code for an enumerated condition. Either &#x27;L&#x27;, or &#x27;F&#x27;.
         */
        private String scopeCode;

        /**
         * The review type that this selection model is for by default.
         */
        private String defaultReviewType;

        /**
         * The code of the review type that this selection model is for by default.
         */
        private String defaultReviewTypeCode;


        /**
         * Creates a new instance of SelectionModelDTO
         */
        public SelectionModelDTO() {}

        /**
         * Get A unique, unchanging identifier for this selection model.
         * @return A unique, unchanging identifier for this selection model.
         */
        public String getId() {
            return id;
        }

        /**
         * Set A unique, unchanging identifier for this selection model.
         * @param id
         *            A unique, unchanging identifier for this selection model.
         */
        public void setId(String id) {
            this.id = id;
        }

        /**
         * Get The ID of the Selection Model Category that this model is a version of.
         * @return The ID of the Selection Model Category that this model is a version of.
         */
        public String getSelectionModelCategory() {
            return selectionModelCategory;
        }

        /**
         * Set The ID of the Selection Model Category that this model is a version of.
         * @param selectionModelCategory
         *            The ID of the Selection Model Category that this model is a version of.
         */
        public void setSelectionModelCategory(String selectionModelCategory) {
            this.selectionModelCategory = selectionModelCategory;
        }

        /**
         * Get A human readable identifier for this selection model.
         * @return A human readable identifier for this selection model.
         */
        public String getName() {
            return name;
        }

        /**
         * Set A human readable identifier for this selection model.
         * @param name
         *            A human readable identifier for this selection model.
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * Get The incremented version for this model (by name).
         * @return The incremented version for this model (by name).
         */
        public Integer getVersion() {
            return version;
        }

        /**
         * Set The incremented version for this model (by name).
         * @param version
         *            The incremented version for this model (by name).
         */
        public void setVersion(Integer version) {
            this.version = version;
        }

        /**
         * Get An optional note field.
         * @return An optional note field.
         */
        public String getDescription() {
            return description;
        }

        /**
         * Set An optional note field.
         * @param description
         *            An optional note field.
         */
        public void setDescription(String description) {
            this.description = description;
        }

        /**
         * Get A collection of factors that make up this model.
         * @return A collection of factors that make up this model.
         */
        public List<FactorDTO> getFactors() {
            return factors;
        }

        /**
         * Set A collection of factors that make up this model.
         * @param factors
         *            A collection of factors that make up this model.
         */
        public void setFactors(List<FactorDTO> factors) {
            this.factors = factors;
        }

        /**
         * Get A top level multiplier for this selection model.
         * @return A top level multiplier for this selection model.
         */
        public BigDecimal getSelectionThreshold() {
            return selectionThreshold;
        }

        /**
         * Set A top level multiplier for this selection model.
         * @param selectionThreshold
         *            A top level multiplier for this selection model.
         */
        public void setSelectionThreshold(BigDecimal selectionThreshold) {
            this.selectionThreshold = selectionThreshold;
        }

        /**
         * Get A top level allocation power for this selection model.
         * @return A top level allocation power for this selection model.
         */
        public BigDecimal getAllocationPercentage() {
            return allocationPercentage;
        }

        /**
         * Set A top level allocation power for this selection model.
         * @param allocationPercentage
         *            A top level allocation power for this selection model.
         */
        public void setAllocationPercentage(BigDecimal allocationPercentage) {
            this.allocationPercentage = allocationPercentage;
        }

        /**
         * Get An enumerated condition. Either &#x27;active&#x27;, &#x27;draft&#x27;, or &#x27;archived&#x27;.
         * @return An enumerated condition. Either &#x27;active&#x27;, &#x27;draft&#x27;, or &#x27;archived&#x27;.
         */
        public String getStatus() {
            return status;
        }

        /**
         * Set An enumerated condition. Either &#x27;active&#x27;, &#x27;draft&#x27;, or &#x27;archived&#x27;.
         * @param status
         *            An enumerated condition. Either &#x27;active&#x27;, &#x27;draft&#x27;, or &#x27;archived&#x27;.
         */
        public void setStatus(String status) {
            this.status = status;
        }

        /**
         * Get An enumerated condition. Either &#x27;limited&#x27;, or &#x27;full&#x27;.
         * @return An enumerated condition. Either &#x27;limited&#x27;, or &#x27;full&#x27;.
         */
        public String getScope() {
            return scope;
        }

        /**
         * Set An enumerated condition. Either &#x27;limited&#x27;, or &#x27;full&#x27;.
         * @param scope
         *            An enumerated condition. Either &#x27;limited&#x27;, or &#x27;full&#x27;.
         */
        public void setScope(String scope) {
            this.scope = scope;
        }

        /**
         * Get Code for an enumerated condition. Either &#x27;L&#x27;, or &#x27;F&#x27;.
         * @return Code for an enumerated condition. Either &#x27;L&#x27;, or &#x27;F&#x27;.
         */
        public String getScopeCode() {
            return scopeCode;
        }

        /**
         * Set Code for an enumerated condition. Either &#x27;L&#x27;, or &#x27;F&#x27;.
         * @param scopeCode
         *            Code for an enumerated condition. Either &#x27;L&#x27;, or &#x27;F&#x27;.
         */
        public void setScopeCode(String scopeCode) {
            this.scopeCode = scopeCode;
        }

        /**
         * Get The review type that this selection model is for by default.
         * @return The review type that this selection model is for by default.
         */
        public String getDefaultReviewType() {
            return defaultReviewType;
        }

        /**
         * Set The review type that this selection model is for by default.
         * @param defaultReviewType
         *            The review type that this selection model is for by default.
         */
        public void setDefaultReviewType(String defaultReviewType) {
            this.defaultReviewType = defaultReviewType;
        }

        /**
         * Get The code of the review type that this selection model is for by default.
         * @return The code of the review type that this selection model is for by default.
         */
        public String getDefaultReviewTypeCode() {
            return defaultReviewTypeCode;
        }

        /**
         * Set The code of the review type that this selection model is for by default.
         * @param defaultReviewTypeCode
         *            The code of the review type that this selection model is for by default.
         */
        public void setDefaultReviewTypeCode(String defaultReviewTypeCode) {
            this.defaultReviewTypeCode = defaultReviewTypeCode;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "SelectionModelDTO [ " + 
                "id = " + id + ", " + 
                "selectionModelCategory = " + selectionModelCategory + ", " + 
                "name = " + name + ", " + 
                "version = " + version + ", " + 
                "description = " + description + ", " + 
                "factors = " + factors + ", " + 
                "selectionThreshold = " + selectionThreshold + ", " + 
                "allocationPercentage = " + allocationPercentage + ", " + 
                "status = " + status + ", " + 
                "scope = " + scope + ", " + 
                "scopeCode = " + scopeCode + ", " + 
                "defaultReviewType = " + defaultReviewType + ", " + 
                "defaultReviewTypeCode = " + defaultReviewTypeCode + 
                " ]";
        }
    }

    // AssignmentModel DTO
    public static class AssignmentModelDTO implements Serializable {

        /**
         * A unique, unchanging identifier for this selection model.
         */
        private String id;

        /**
         * The ID of the Selection Model Category that this model is a version of.
         */
        private String assignmentModelCategory;

        /**
         * A human readable identifier for this selection model.
         */
        private String name;

        /**
         * The incremented version for this model (by name).
         */
        private Integer version;

        /**
         * An optional note field.
         */
        private String description;

        /**
         * A collection of factors that make up this model.
         */
        private List<FactorDTO> factors;

        /**
         * A top level multiplier for this selection model.
         */
        private BigDecimal selectionThreshold;

        /**
         * A top level allocation power for this selection model.
         */
        private BigDecimal allocationPercentage;

        /**
         * An enumerated condition. Either &#x27;active&#x27;, &#x27;draft&#x27;, or &#x27;archived&#x27;.
         */
        private String status;

        /**
         * The review type that this model is for by default.
         */
        private String defaultReviewType;

        /**
         * The code of the review type that this model is for by default.
         */
        private String defaultReviewTypeCode;


        /**
         * Creates a new instance of AssignmentModelDTO
         */
        public AssignmentModelDTO() {}

        /**
         * Get A unique, unchanging identifier for this selection model.
         * @return A unique, unchanging identifier for this selection model.
         */
        public String getId() {
            return id;
        }

        /**
         * Set A unique, unchanging identifier for this selection model.
         * @param id
         *            A unique, unchanging identifier for this selection model.
         */
        public void setId(String id) {
            this.id = id;
        }

        /**
         * Get The ID of the Selection Model Category that this model is a version of.
         * @return The ID of the Selection Model Category that this model is a version of.
         */
        public String getAssignmentModelCategory() {
            return assignmentModelCategory;
        }

        /**
         * Set The ID of the Selection Model Category that this model is a version of.
         * @param assignmentModelCategory
         *            The ID of the Selection Model Category that this model is a version of.
         */
        public void setAssignmentModelCategory(String assignmentModelCategory) {
            this.assignmentModelCategory = assignmentModelCategory;
        }

        /**
         * Get A human readable identifier for this selection model.
         * @return A human readable identifier for this selection model.
         */
        public String getName() {
            return name;
        }

        /**
         * Set A human readable identifier for this selection model.
         * @param name
         *            A human readable identifier for this selection model.
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * Get The incremented version for this model (by name).
         * @return The incremented version for this model (by name).
         */
        public Integer getVersion() {
            return version;
        }

        /**
         * Set The incremented version for this model (by name).
         * @param version
         *            The incremented version for this model (by name).
         */
        public void setVersion(Integer version) {
            this.version = version;
        }

        /**
         * Get An optional note field.
         * @return An optional note field.
         */
        public String getDescription() {
            return description;
        }

        /**
         * Set An optional note field.
         * @param description
         *            An optional note field.
         */
        public void setDescription(String description) {
            this.description = description;
        }

        /**
         * Get A collection of factors that make up this model.
         * @return A collection of factors that make up this model.
         */
        public List<FactorDTO> getFactors() {
            return factors;
        }

        /**
         * Set A collection of factors that make up this model.
         * @param factors
         *            A collection of factors that make up this model.
         */
        public void setFactors(List<FactorDTO> factors) {
            this.factors = factors;
        }

        /**
         * Get A top level multiplier for this selection model.
         * @return A top level multiplier for this selection model.
         */
        public BigDecimal getSelectionThreshold() {
            return selectionThreshold;
        }

        /**
         * Set A top level multiplier for this selection model.
         * @param selectionThreshold
         *            A top level multiplier for this selection model.
         */
        public void setSelectionThreshold(BigDecimal selectionThreshold) {
            this.selectionThreshold = selectionThreshold;
        }

        /**
         * Get A top level allocation power for this selection model.
         * @return A top level allocation power for this selection model.
         */
        public BigDecimal getAllocationPercentage() {
            return allocationPercentage;
        }

        /**
         * Set A top level allocation power for this selection model.
         * @param allocationPercentage
         *            A top level allocation power for this selection model.
         */
        public void setAllocationPercentage(BigDecimal allocationPercentage) {
            this.allocationPercentage = allocationPercentage;
        }

        /**
         * Get An enumerated condition. Either &#x27;active&#x27;, &#x27;draft&#x27;, or &#x27;archived&#x27;.
         * @return An enumerated condition. Either &#x27;active&#x27;, &#x27;draft&#x27;, or &#x27;archived&#x27;.
         */
        public String getStatus() {
            return status;
        }

        /**
         * Set An enumerated condition. Either &#x27;active&#x27;, &#x27;draft&#x27;, or &#x27;archived&#x27;.
         * @param status
         *            An enumerated condition. Either &#x27;active&#x27;, &#x27;draft&#x27;, or &#x27;archived&#x27;.
         */
        public void setStatus(String status) {
            this.status = status;
        }

        /**
         * Get The review type that this model is for by default.
         * @return The review type that this model is for by default.
         */
        public String getDefaultReviewType() {
            return defaultReviewType;
        }

        /**
         * Set The review type that this model is for by default.
         * @param defaultReviewType
         *            The review type that this model is for by default.
         */
        public void setDefaultReviewType(String defaultReviewType) {
            this.defaultReviewType = defaultReviewType;
        }

        /**
         * Get The code of the review type that this model is for by default.
         * @return The code of the review type that this model is for by default.
         */
        public String getDefaultReviewTypeCode() {
            return defaultReviewTypeCode;
        }

        /**
         * Set The code of the review type that this model is for by default.
         * @param defaultReviewTypeCode
         *            The code of the review type that this model is for by default.
         */
        public void setDefaultReviewTypeCode(String defaultReviewTypeCode) {
            this.defaultReviewTypeCode = defaultReviewTypeCode;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "AssignmentModelDTO [ " + 
                "id = " + id + ", " + 
                "assignmentModelCategory = " + assignmentModelCategory + ", " + 
                "name = " + name + ", " + 
                "version = " + version + ", " + 
                "description = " + description + ", " + 
                "factors = " + factors + ", " + 
                "selectionThreshold = " + selectionThreshold + ", " + 
                "allocationPercentage = " + allocationPercentage + ", " + 
                "status = " + status + ", " + 
                "defaultReviewType = " + defaultReviewType + ", " + 
                "defaultReviewTypeCode = " + defaultReviewTypeCode + 
                " ]";
        }
    }

    // DistributionModel DTO
    public static class DistributionModelDTO implements Serializable {

        /**
         * A unique, unchanging identifier for this selection model.
         */
        private String id;

        /**
         * The ID of the Selection Model Category that this model is a version of.
         */
        private String distributionModelCategory;

        /**
         * A human readable identifier for this selection model.
         */
        private String name;

        /**
         * The incremented version for this model (by name).
         */
        private Integer version;

        /**
         * An optional note field.
         */
        private String description;

        /**
         * A collection of factors that make up this model.
         */
        private List<FactorDTO> factors;

        /**
         * A top level multiplier for this selection model.
         */
        private BigDecimal selectionThreshold;

        /**
         * A top level allocation power for this selection model.
         */
        private BigDecimal allocationPercentage;

        /**
         * An enumerated condition. Either &#x27;active&#x27;, &#x27;draft&#x27;, or &#x27;archived&#x27;.
         */
        private String status;

        /**
         * The review type that this model is for by default.
         */
        private String defaultReviewType;

        /**
         * The code of the review type that this model is for by default.
         */
        private String defaultReviewTypeCode;


        /**
         * Creates a new instance of DistributionModelDTO
         */
        public DistributionModelDTO() {}

        /**
         * Get A unique, unchanging identifier for this selection model.
         * @return A unique, unchanging identifier for this selection model.
         */
        public String getId() {
            return id;
        }

        /**
         * Set A unique, unchanging identifier for this selection model.
         * @param id
         *            A unique, unchanging identifier for this selection model.
         */
        public void setId(String id) {
            this.id = id;
        }

        /**
         * Get The ID of the Selection Model Category that this model is a version of.
         * @return The ID of the Selection Model Category that this model is a version of.
         */
        public String getDistributionModelCategory() {
            return distributionModelCategory;
        }

        /**
         * Set The ID of the Selection Model Category that this model is a version of.
         * @param distributionModelCategory
         *            The ID of the Selection Model Category that this model is a version of.
         */
        public void setDistributionModelCategory(String distributionModelCategory) {
            this.distributionModelCategory = distributionModelCategory;
        }

        /**
         * Get A human readable identifier for this selection model.
         * @return A human readable identifier for this selection model.
         */
        public String getName() {
            return name;
        }

        /**
         * Set A human readable identifier for this selection model.
         * @param name
         *            A human readable identifier for this selection model.
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * Get The incremented version for this model (by name).
         * @return The incremented version for this model (by name).
         */
        public Integer getVersion() {
            return version;
        }

        /**
         * Set The incremented version for this model (by name).
         * @param version
         *            The incremented version for this model (by name).
         */
        public void setVersion(Integer version) {
            this.version = version;
        }

        /**
         * Get An optional note field.
         * @return An optional note field.
         */
        public String getDescription() {
            return description;
        }

        /**
         * Set An optional note field.
         * @param description
         *            An optional note field.
         */
        public void setDescription(String description) {
            this.description = description;
        }

        /**
         * Get A collection of factors that make up this model.
         * @return A collection of factors that make up this model.
         */
        public List<FactorDTO> getFactors() {
            return factors;
        }

        /**
         * Set A collection of factors that make up this model.
         * @param factors
         *            A collection of factors that make up this model.
         */
        public void setFactors(List<FactorDTO> factors) {
            this.factors = factors;
        }

        /**
         * Get A top level multiplier for this selection model.
         * @return A top level multiplier for this selection model.
         */
        public BigDecimal getSelectionThreshold() {
            return selectionThreshold;
        }

        /**
         * Set A top level multiplier for this selection model.
         * @param selectionThreshold
         *            A top level multiplier for this selection model.
         */
        public void setSelectionThreshold(BigDecimal selectionThreshold) {
            this.selectionThreshold = selectionThreshold;
        }

        /**
         * Get A top level allocation power for this selection model.
         * @return A top level allocation power for this selection model.
         */
        public BigDecimal getAllocationPercentage() {
            return allocationPercentage;
        }

        /**
         * Set A top level allocation power for this selection model.
         * @param allocationPercentage
         *            A top level allocation power for this selection model.
         */
        public void setAllocationPercentage(BigDecimal allocationPercentage) {
            this.allocationPercentage = allocationPercentage;
        }

        /**
         * Get An enumerated condition. Either &#x27;active&#x27;, &#x27;draft&#x27;, or &#x27;archived&#x27;.
         * @return An enumerated condition. Either &#x27;active&#x27;, &#x27;draft&#x27;, or &#x27;archived&#x27;.
         */
        public String getStatus() {
            return status;
        }

        /**
         * Set An enumerated condition. Either &#x27;active&#x27;, &#x27;draft&#x27;, or &#x27;archived&#x27;.
         * @param status
         *            An enumerated condition. Either &#x27;active&#x27;, &#x27;draft&#x27;, or &#x27;archived&#x27;.
         */
        public void setStatus(String status) {
            this.status = status;
        }

        /**
         * Get The review type that this model is for by default.
         * @return The review type that this model is for by default.
         */
        public String getDefaultReviewType() {
            return defaultReviewType;
        }

        /**
         * Set The review type that this model is for by default.
         * @param defaultReviewType
         *            The review type that this model is for by default.
         */
        public void setDefaultReviewType(String defaultReviewType) {
            this.defaultReviewType = defaultReviewType;
        }

        /**
         * Get The code of the review type that this model is for by default.
         * @return The code of the review type that this model is for by default.
         */
        public String getDefaultReviewTypeCode() {
            return defaultReviewTypeCode;
        }

        /**
         * Set The code of the review type that this model is for by default.
         * @param defaultReviewTypeCode
         *            The code of the review type that this model is for by default.
         */
        public void setDefaultReviewTypeCode(String defaultReviewTypeCode) {
            this.defaultReviewTypeCode = defaultReviewTypeCode;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "DistributionModelDTO [ " + 
                "id = " + id + ", " + 
                "distributionModelCategory = " + distributionModelCategory + ", " + 
                "name = " + name + ", " + 
                "version = " + version + ", " + 
                "description = " + description + ", " + 
                "factors = " + factors + ", " + 
                "selectionThreshold = " + selectionThreshold + ", " + 
                "allocationPercentage = " + allocationPercentage + ", " + 
                "status = " + status + ", " + 
                "defaultReviewType = " + defaultReviewType + ", " + 
                "defaultReviewTypeCode = " + defaultReviewTypeCode + 
                " ]";
        }
    }

    // LenderSelectionAdjustment DTO
    public static class LenderSelectionAdjustmentDTO implements Serializable {

        /**
         * An object containing information about the lender that&#x27;s being suppressed.
         */
        private LiteLenderDTO lender;

        /**
         * The current count of reviews for cases by this lender.
         */
        private Integer activeReviews;

        /**
         * The date that this lender was added to the suppression list.
         */
        private Date startDate;

        /**
         * What percentage of this Lender&#x27;s loans to review.
         */
        private BigDecimal percentToReview;


        /**
         * Creates a new instance of LenderSelectionAdjustmentDTO
         */
        public LenderSelectionAdjustmentDTO() {}

        /**
         * Get An object containing information about the lender that&#x27;s being suppressed.
         * @return An object containing information about the lender that&#x27;s being suppressed.
         */
        public LiteLenderDTO getLender() {
            return lender;
        }

        /**
         * Set An object containing information about the lender that&#x27;s being suppressed.
         * @param lender
         *            An object containing information about the lender that&#x27;s being suppressed.
         */
        public void setLender(LiteLenderDTO lender) {
            this.lender = lender;
        }

        /**
         * Get The current count of reviews for cases by this lender.
         * @return The current count of reviews for cases by this lender.
         */
        public Integer getActiveReviews() {
            return activeReviews;
        }

        /**
         * Set The current count of reviews for cases by this lender.
         * @param activeReviews
         *            The current count of reviews for cases by this lender.
         */
        public void setActiveReviews(Integer activeReviews) {
            this.activeReviews = activeReviews;
        }

        /**
         * Get The date that this lender was added to the suppression list.
         * @return The date that this lender was added to the suppression list.
         */
        public Date getStartDate() {
            return startDate;
        }

        /**
         * Set The date that this lender was added to the suppression list.
         * @param startDate
         *            The date that this lender was added to the suppression list.
         */
        public void setStartDate(Date startDate) {
            this.startDate = startDate;
        }

        /**
         * Get What percentage of this Lender&#x27;s loans to review.
         * @return What percentage of this Lender&#x27;s loans to review.
         */
        public BigDecimal getPercentToReview() {
            return percentToReview;
        }

        /**
         * Set What percentage of this Lender&#x27;s loans to review.
         * @param percentToReview
         *            What percentage of this Lender&#x27;s loans to review.
         */
        public void setPercentToReview(BigDecimal percentToReview) {
            this.percentToReview = percentToReview;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "LenderSelectionAdjustmentDTO [ " + 
                "lender = " + lender + ", " + 
                "activeReviews = " + activeReviews + ", " + 
                "startDate = " + startDate + ", " + 
                "percentToReview = " + percentToReview + 
                " ]";
        }
    }

    // Field DTO
    public static class FieldDTO implements Serializable {

        /**
         * The code for the field.
         */
        private String code;

        /**
         * The description for the field.
         */
        private String description;

        /**
         * The entity name for the field.
         */
        private String entityName;

        /**
         * The field name for the field.
         */
        private String fieldName;


        /**
         * Creates a new instance of FieldDTO
         */
        public FieldDTO() {}

        /**
         * Get The code for the field.
         * @return The code for the field.
         */
        public String getCode() {
            return code;
        }

        /**
         * Set The code for the field.
         * @param code
         *            The code for the field.
         */
        public void setCode(String code) {
            this.code = code;
        }

        /**
         * Get The description for the field.
         * @return The description for the field.
         */
        public String getDescription() {
            return description;
        }

        /**
         * Set The description for the field.
         * @param description
         *            The description for the field.
         */
        public void setDescription(String description) {
            this.description = description;
        }

        /**
         * Get The entity name for the field.
         * @return The entity name for the field.
         */
        public String getEntityName() {
            return entityName;
        }

        /**
         * Set The entity name for the field.
         * @param entityName
         *            The entity name for the field.
         */
        public void setEntityName(String entityName) {
            this.entityName = entityName;
        }

        /**
         * Get The field name for the field.
         * @return The field name for the field.
         */
        public String getFieldName() {
            return fieldName;
        }

        /**
         * Set The field name for the field.
         * @param fieldName
         *            The field name for the field.
         */
        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "FieldDTO [ " + 
                "code = " + code + ", " + 
                "description = " + description + ", " + 
                "entityName = " + entityName + ", " + 
                "fieldName = " + fieldName + 
                " ]";
        }
    }

    // QaModel DTO
    public static class QaModelDTO implements Serializable {

        /**
         * The id of the qa model.
         */
        private String qaModelId;

        /**
         * The name of the qa model.
         */
        private String name;

        /**
         * The description of the qa model.
         */
        private String description;

        /**
         * The version of the qa model.
         */
        private Integer version;

        /**
         * The amount of active reviews for the qa model.
         */
        private Integer activeReviews;

        /**
         * The amount of completed reviews for the qa model.
         */
        private Integer completedReviews;

        /**
         * Whether the qa model is active.
         */
        private Boolean isActive;

        /**
         * Whether the qa model is readonly.
         */
        private Boolean isReadonly;

        /**
         * The date to which the qa model was created.
         */
        private Date createdDate;

        /**
         * The date to which the qa model was last modified.
         */
        private Date modifiedDate;

        /**
         * The date to which the qa model was activated.
         */
        private Date activatedDate;

        /**
         * The person to which the qa model was last modified.
         */
        private String modifiedBy;

        /**
         * Code for an enumerated condition. Either &#x27;active&#x27;, &#x27;draft&#x27;, or &#x27;archived&#x27;.
         */
        private String statusCode;

        /**
         * Description for an enumerated condition. Either &#x27;active&#x27;, &#x27;draft&#x27;, or &#x27;archived&#x27;.
         */
        private String status;


        /**
         * Creates a new instance of QaModelDTO
         */
        public QaModelDTO() {}

        /**
         * Get The id of the qa model.
         * @return The id of the qa model.
         */
        public String getQaModelId() {
            return qaModelId;
        }

        /**
         * Set The id of the qa model.
         * @param qaModelId
         *            The id of the qa model.
         */
        public void setQaModelId(String qaModelId) {
            this.qaModelId = qaModelId;
        }

        /**
         * Get The name of the qa model.
         * @return The name of the qa model.
         */
        public String getName() {
            return name;
        }

        /**
         * Set The name of the qa model.
         * @param name
         *            The name of the qa model.
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * Get The description of the qa model.
         * @return The description of the qa model.
         */
        public String getDescription() {
            return description;
        }

        /**
         * Set The description of the qa model.
         * @param description
         *            The description of the qa model.
         */
        public void setDescription(String description) {
            this.description = description;
        }

        /**
         * Get The version of the qa model.
         * @return The version of the qa model.
         */
        public Integer getVersion() {
            return version;
        }

        /**
         * Set The version of the qa model.
         * @param version
         *            The version of the qa model.
         */
        public void setVersion(Integer version) {
            this.version = version;
        }

        /**
         * Get The amount of active reviews for the qa model.
         * @return The amount of active reviews for the qa model.
         */
        public Integer getActiveReviews() {
            return activeReviews;
        }

        /**
         * Set The amount of active reviews for the qa model.
         * @param activeReviews
         *            The amount of active reviews for the qa model.
         */
        public void setActiveReviews(Integer activeReviews) {
            this.activeReviews = activeReviews;
        }

        /**
         * Get The amount of completed reviews for the qa model.
         * @return The amount of completed reviews for the qa model.
         */
        public Integer getCompletedReviews() {
            return completedReviews;
        }

        /**
         * Set The amount of completed reviews for the qa model.
         * @param completedReviews
         *            The amount of completed reviews for the qa model.
         */
        public void setCompletedReviews(Integer completedReviews) {
            this.completedReviews = completedReviews;
        }

        /**
         * Get Whether the qa model is active.
         * @return Whether the qa model is active.
         */
        public Boolean getIsActive() {
            return isActive;
        }

        /**
         * Set Whether the qa model is active.
         * @param isActive
         *            Whether the qa model is active.
         */
        public void setIsActive(Boolean isActive) {
            this.isActive = isActive;
        }

        /**
         * Get Whether the qa model is readonly.
         * @return Whether the qa model is readonly.
         */
        public Boolean getIsReadonly() {
            return isReadonly;
        }

        /**
         * Set Whether the qa model is readonly.
         * @param isReadonly
         *            Whether the qa model is readonly.
         */
        public void setIsReadonly(Boolean isReadonly) {
            this.isReadonly = isReadonly;
        }

        /**
         * Get The date to which the qa model was created.
         * @return The date to which the qa model was created.
         */
        public Date getCreatedDate() {
            return createdDate;
        }

        /**
         * Set The date to which the qa model was created.
         * @param createdDate
         *            The date to which the qa model was created.
         */
        public void setCreatedDate(Date createdDate) {
            this.createdDate = createdDate;
        }

        /**
         * Get The date to which the qa model was last modified.
         * @return The date to which the qa model was last modified.
         */
        public Date getModifiedDate() {
            return modifiedDate;
        }

        /**
         * Set The date to which the qa model was last modified.
         * @param modifiedDate
         *            The date to which the qa model was last modified.
         */
        public void setModifiedDate(Date modifiedDate) {
            this.modifiedDate = modifiedDate;
        }

        /**
         * Get The date to which the qa model was activated.
         * @return The date to which the qa model was activated.
         */
        public Date getActivatedDate() {
            return activatedDate;
        }

        /**
         * Set The date to which the qa model was activated.
         * @param activatedDate
         *            The date to which the qa model was activated.
         */
        public void setActivatedDate(Date activatedDate) {
            this.activatedDate = activatedDate;
        }

        /**
         * Get The person to which the qa model was last modified.
         * @return The person to which the qa model was last modified.
         */
        public String getModifiedBy() {
            return modifiedBy;
        }

        /**
         * Set The person to which the qa model was last modified.
         * @param modifiedBy
         *            The person to which the qa model was last modified.
         */
        public void setModifiedBy(String modifiedBy) {
            this.modifiedBy = modifiedBy;
        }

        /**
         * Get Code for an enumerated condition. Either &#x27;active&#x27;, &#x27;draft&#x27;, or &#x27;archived&#x27;.
         * @return Code for an enumerated condition. Either &#x27;active&#x27;, &#x27;draft&#x27;, or &#x27;archived&#x27;.
         */
        public String getStatusCode() {
            return statusCode;
        }

        /**
         * Set Code for an enumerated condition. Either &#x27;active&#x27;, &#x27;draft&#x27;, or &#x27;archived&#x27;.
         * @param statusCode
         *            Code for an enumerated condition. Either &#x27;active&#x27;, &#x27;draft&#x27;, or &#x27;archived&#x27;.
         */
        public void setStatusCode(String statusCode) {
            this.statusCode = statusCode;
        }

        /**
         * Get Description for an enumerated condition. Either &#x27;active&#x27;, &#x27;draft&#x27;, or &#x27;archived&#x27;.
         * @return Description for an enumerated condition. Either &#x27;active&#x27;, &#x27;draft&#x27;, or &#x27;archived&#x27;.
         */
        public String getStatus() {
            return status;
        }

        /**
         * Set Description for an enumerated condition. Either &#x27;active&#x27;, &#x27;draft&#x27;, or &#x27;archived&#x27;.
         * @param status
         *            Description for an enumerated condition. Either &#x27;active&#x27;, &#x27;draft&#x27;, or &#x27;archived&#x27;.
         */
        public void setStatus(String status) {
            this.status = status;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "QaModelDTO [ " + 
                "qaModelId = " + qaModelId + ", " + 
                "name = " + name + ", " + 
                "description = " + description + ", " + 
                "version = " + version + ", " + 
                "activeReviews = " + activeReviews + ", " + 
                "completedReviews = " + completedReviews + ", " + 
                "isActive = " + isActive + ", " + 
                "isReadonly = " + isReadonly + ", " + 
                "createdDate = " + createdDate + ", " + 
                "modifiedDate = " + modifiedDate + ", " + 
                "activatedDate = " + activatedDate + ", " + 
                "modifiedBy = " + modifiedBy + ", " + 
                "statusCode = " + statusCode + ", " + 
                "status = " + status + 
                " ]";
        }
    }

    // QaModelDetail DTO
    public static class QaModelDetailDTO implements Serializable {

        /**
         * The id of the qa model.
         */
        private String qaModelId;

        /**
         * The name of the qa model.
         */
        private String name;

        /**
         * The description of the qa model.
         */
        private String description;

        /**
         * The version of the qa model.
         */
        private Integer version;

        /**
         * The amount of active reviews for the qa model.
         */
        private Integer activeReviews;

        /**
         * The amount of completed reviews for the qa model.
         */
        private Integer completedReviews;

        /**
         * Whether the qa model is active.
         */
        private Boolean isActive;

        /**
         * Whether the qa model is readonly.
         */
        private Boolean isReadonly;

        /**
         * The date to which the qa model was created.
         */
        private Date createdDate;

        /**
         * The date to which the qa model was last modified.
         */
        private Date modifiedDate;

        /**
         * The date to which the qa model was activated.
         */
        private Date activatedDate;

        /**
         * The person to which the qa model was last modified.
         */
        private String modifiedBy;

        /**
         * Code for an enumerated condition. Either &#x27;active&#x27;, &#x27;draft&#x27;, or &#x27;archived&#x27;.
         */
        private String statusCode;

        /**
         * Description for an enumerated condition. Either &#x27;active&#x27;, &#x27;draft&#x27;, or &#x27;archived&#x27;.
         */
        private String status;

        /**
         * A collection of qa model defect area objects.
         */
        private List<QaModelDefectAreaDTO> defectAreas;

        /**
         * A collection of qa model categories of loan attributes
         */
        private List<QaModelCategoryDTO> categories;


        /**
         * Creates a new instance of QaModelDetailDTO
         */
        public QaModelDetailDTO() {}

        /**
         * Get The id of the qa model.
         * @return The id of the qa model.
         */
        public String getQaModelId() {
            return qaModelId;
        }

        /**
         * Set The id of the qa model.
         * @param qaModelId
         *            The id of the qa model.
         */
        public void setQaModelId(String qaModelId) {
            this.qaModelId = qaModelId;
        }

        /**
         * Get The name of the qa model.
         * @return The name of the qa model.
         */
        public String getName() {
            return name;
        }

        /**
         * Set The name of the qa model.
         * @param name
         *            The name of the qa model.
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * Get The description of the qa model.
         * @return The description of the qa model.
         */
        public String getDescription() {
            return description;
        }

        /**
         * Set The description of the qa model.
         * @param description
         *            The description of the qa model.
         */
        public void setDescription(String description) {
            this.description = description;
        }

        /**
         * Get The version of the qa model.
         * @return The version of the qa model.
         */
        public Integer getVersion() {
            return version;
        }

        /**
         * Set The version of the qa model.
         * @param version
         *            The version of the qa model.
         */
        public void setVersion(Integer version) {
            this.version = version;
        }

        /**
         * Get The amount of active reviews for the qa model.
         * @return The amount of active reviews for the qa model.
         */
        public Integer getActiveReviews() {
            return activeReviews;
        }

        /**
         * Set The amount of active reviews for the qa model.
         * @param activeReviews
         *            The amount of active reviews for the qa model.
         */
        public void setActiveReviews(Integer activeReviews) {
            this.activeReviews = activeReviews;
        }

        /**
         * Get The amount of completed reviews for the qa model.
         * @return The amount of completed reviews for the qa model.
         */
        public Integer getCompletedReviews() {
            return completedReviews;
        }

        /**
         * Set The amount of completed reviews for the qa model.
         * @param completedReviews
         *            The amount of completed reviews for the qa model.
         */
        public void setCompletedReviews(Integer completedReviews) {
            this.completedReviews = completedReviews;
        }

        /**
         * Get Whether the qa model is active.
         * @return Whether the qa model is active.
         */
        public Boolean getIsActive() {
            return isActive;
        }

        /**
         * Set Whether the qa model is active.
         * @param isActive
         *            Whether the qa model is active.
         */
        public void setIsActive(Boolean isActive) {
            this.isActive = isActive;
        }

        /**
         * Get Whether the qa model is readonly.
         * @return Whether the qa model is readonly.
         */
        public Boolean getIsReadonly() {
            return isReadonly;
        }

        /**
         * Set Whether the qa model is readonly.
         * @param isReadonly
         *            Whether the qa model is readonly.
         */
        public void setIsReadonly(Boolean isReadonly) {
            this.isReadonly = isReadonly;
        }

        /**
         * Get The date to which the qa model was created.
         * @return The date to which the qa model was created.
         */
        public Date getCreatedDate() {
            return createdDate;
        }

        /**
         * Set The date to which the qa model was created.
         * @param createdDate
         *            The date to which the qa model was created.
         */
        public void setCreatedDate(Date createdDate) {
            this.createdDate = createdDate;
        }

        /**
         * Get The date to which the qa model was last modified.
         * @return The date to which the qa model was last modified.
         */
        public Date getModifiedDate() {
            return modifiedDate;
        }

        /**
         * Set The date to which the qa model was last modified.
         * @param modifiedDate
         *            The date to which the qa model was last modified.
         */
        public void setModifiedDate(Date modifiedDate) {
            this.modifiedDate = modifiedDate;
        }

        /**
         * Get The date to which the qa model was activated.
         * @return The date to which the qa model was activated.
         */
        public Date getActivatedDate() {
            return activatedDate;
        }

        /**
         * Set The date to which the qa model was activated.
         * @param activatedDate
         *            The date to which the qa model was activated.
         */
        public void setActivatedDate(Date activatedDate) {
            this.activatedDate = activatedDate;
        }

        /**
         * Get The person to which the qa model was last modified.
         * @return The person to which the qa model was last modified.
         */
        public String getModifiedBy() {
            return modifiedBy;
        }

        /**
         * Set The person to which the qa model was last modified.
         * @param modifiedBy
         *            The person to which the qa model was last modified.
         */
        public void setModifiedBy(String modifiedBy) {
            this.modifiedBy = modifiedBy;
        }

        /**
         * Get Code for an enumerated condition. Either &#x27;active&#x27;, &#x27;draft&#x27;, or &#x27;archived&#x27;.
         * @return Code for an enumerated condition. Either &#x27;active&#x27;, &#x27;draft&#x27;, or &#x27;archived&#x27;.
         */
        public String getStatusCode() {
            return statusCode;
        }

        /**
         * Set Code for an enumerated condition. Either &#x27;active&#x27;, &#x27;draft&#x27;, or &#x27;archived&#x27;.
         * @param statusCode
         *            Code for an enumerated condition. Either &#x27;active&#x27;, &#x27;draft&#x27;, or &#x27;archived&#x27;.
         */
        public void setStatusCode(String statusCode) {
            this.statusCode = statusCode;
        }

        /**
         * Get Description for an enumerated condition. Either &#x27;active&#x27;, &#x27;draft&#x27;, or &#x27;archived&#x27;.
         * @return Description for an enumerated condition. Either &#x27;active&#x27;, &#x27;draft&#x27;, or &#x27;archived&#x27;.
         */
        public String getStatus() {
            return status;
        }

        /**
         * Set Description for an enumerated condition. Either &#x27;active&#x27;, &#x27;draft&#x27;, or &#x27;archived&#x27;.
         * @param status
         *            Description for an enumerated condition. Either &#x27;active&#x27;, &#x27;draft&#x27;, or &#x27;archived&#x27;.
         */
        public void setStatus(String status) {
            this.status = status;
        }

        /**
         * Get A collection of qa model defect area objects.
         * @return A collection of qa model defect area objects.
         */
        public List<QaModelDefectAreaDTO> getDefectAreas() {
            return defectAreas;
        }

        /**
         * Set A collection of qa model defect area objects.
         * @param defectAreas
         *            A collection of qa model defect area objects.
         */
        public void setDefectAreas(List<QaModelDefectAreaDTO> defectAreas) {
            this.defectAreas = defectAreas;
        }

        /**
         * Get A collection of qa model categories of loan attributes
         * @return A collection of qa model categories of loan attributes
         */
        public List<QaModelCategoryDTO> getCategories() {
            return categories;
        }

        /**
         * Set A collection of qa model categories of loan attributes
         * @param categories
         *            A collection of qa model categories of loan attributes
         */
        public void setCategories(List<QaModelCategoryDTO> categories) {
            this.categories = categories;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "QaModelDetailDTO [ " + 
                "qaModelId = " + qaModelId + ", " + 
                "name = " + name + ", " + 
                "description = " + description + ", " + 
                "version = " + version + ", " + 
                "activeReviews = " + activeReviews + ", " + 
                "completedReviews = " + completedReviews + ", " + 
                "isActive = " + isActive + ", " + 
                "isReadonly = " + isReadonly + ", " + 
                "createdDate = " + createdDate + ", " + 
                "modifiedDate = " + modifiedDate + ", " + 
                "activatedDate = " + activatedDate + ", " + 
                "modifiedBy = " + modifiedBy + ", " + 
                "statusCode = " + statusCode + ", " + 
                "status = " + status + ", " + 
                "defectAreas = " + defectAreas + ", " + 
                "categories = " + categories + 
                " ]";
        }
    }

    // QaModelCategory DTO
    public static class QaModelCategoryDTO implements Serializable {

        /**
         * The category name.
         */
        private String name;

        /**
         * The order of the category
         */
        private BigDecimal order;

        /**
         * Collection of qa model category loan attributes.
         */
        private List<QaModelLoanAttributeDTO> loanAttributes;


        /**
         * Creates a new instance of QaModelCategoryDTO
         */
        public QaModelCategoryDTO() {}

        /**
         * Get The category name.
         * @return The category name.
         */
        public String getName() {
            return name;
        }

        /**
         * Set The category name.
         * @param name
         *            The category name.
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * Get The order of the category
         * @return The order of the category
         */
        public BigDecimal getOrder() {
            return order;
        }

        /**
         * Set The order of the category
         * @param order
         *            The order of the category
         */
        public void setOrder(BigDecimal order) {
            this.order = order;
        }

        /**
         * Get Collection of qa model category loan attributes.
         * @return Collection of qa model category loan attributes.
         */
        public List<QaModelLoanAttributeDTO> getLoanAttributes() {
            return loanAttributes;
        }

        /**
         * Set Collection of qa model category loan attributes.
         * @param loanAttributes
         *            Collection of qa model category loan attributes.
         */
        public void setLoanAttributes(List<QaModelLoanAttributeDTO> loanAttributes) {
            this.loanAttributes = loanAttributes;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "QaModelCategoryDTO [ " + 
                "name = " + name + ", " + 
                "order = " + order + ", " + 
                "loanAttributes = " + loanAttributes + 
                " ]";
        }
    }

    // QaModelLoanAttribute DTO
    public static class QaModelLoanAttributeDTO implements Serializable {

        /**
         * The id of qa model loan attribute.
         */
        private String id;

        /**
         * The entity name the qa model loan attribute is referencing.
         */
        private String entityName;

        /**
         * The field name the qa model loan attribute is referencing.
         */
        private String fieldName;

        /**
         * The order of the category
         */
        private BigDecimal order;


        /**
         * Creates a new instance of QaModelLoanAttributeDTO
         */
        public QaModelLoanAttributeDTO() {}

        /**
         * Get The id of qa model loan attribute.
         * @return The id of qa model loan attribute.
         */
        public String getId() {
            return id;
        }

        /**
         * Set The id of qa model loan attribute.
         * @param id
         *            The id of qa model loan attribute.
         */
        public void setId(String id) {
            this.id = id;
        }

        /**
         * Get The entity name the qa model loan attribute is referencing.
         * @return The entity name the qa model loan attribute is referencing.
         */
        public String getEntityName() {
            return entityName;
        }

        /**
         * Set The entity name the qa model loan attribute is referencing.
         * @param entityName
         *            The entity name the qa model loan attribute is referencing.
         */
        public void setEntityName(String entityName) {
            this.entityName = entityName;
        }

        /**
         * Get The field name the qa model loan attribute is referencing.
         * @return The field name the qa model loan attribute is referencing.
         */
        public String getFieldName() {
            return fieldName;
        }

        /**
         * Set The field name the qa model loan attribute is referencing.
         * @param fieldName
         *            The field name the qa model loan attribute is referencing.
         */
        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        /**
         * Get The order of the category
         * @return The order of the category
         */
        public BigDecimal getOrder() {
            return order;
        }

        /**
         * Set The order of the category
         * @param order
         *            The order of the category
         */
        public void setOrder(BigDecimal order) {
            this.order = order;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "QaModelLoanAttributeDTO [ " + 
                "id = " + id + ", " + 
                "entityName = " + entityName + ", " + 
                "fieldName = " + fieldName + ", " + 
                "order = " + order + 
                " ]";
        }
    }

    // QaModelDefectArea DTO
    public static class QaModelDefectAreaDTO implements Serializable {

        /**
         * The id of the defect area.
         */
        private String defectAreaId;

        /**
         * The unique code for the defect area.
         */
        private String code;

        /**
         * The description for the defect area.
         */
        private String description;

        /**
         * The order of the defect area
         */
        private BigDecimal order;

        /**
         * The codes of review types assigned to the defect area for the qa model.
         */
        private List<String> reviewTypeCodes;


        /**
         * Creates a new instance of QaModelDefectAreaDTO
         */
        public QaModelDefectAreaDTO() {}

        /**
         * Get The id of the defect area.
         * @return The id of the defect area.
         */
        public String getDefectAreaId() {
            return defectAreaId;
        }

        /**
         * Set The id of the defect area.
         * @param defectAreaId
         *            The id of the defect area.
         */
        public void setDefectAreaId(String defectAreaId) {
            this.defectAreaId = defectAreaId;
        }

        /**
         * Get The unique code for the defect area.
         * @return The unique code for the defect area.
         */
        public String getCode() {
            return code;
        }

        /**
         * Set The unique code for the defect area.
         * @param code
         *            The unique code for the defect area.
         */
        public void setCode(String code) {
            this.code = code;
        }

        /**
         * Get The description for the defect area.
         * @return The description for the defect area.
         */
        public String getDescription() {
            return description;
        }

        /**
         * Set The description for the defect area.
         * @param description
         *            The description for the defect area.
         */
        public void setDescription(String description) {
            this.description = description;
        }

        /**
         * Get The order of the defect area
         * @return The order of the defect area
         */
        public BigDecimal getOrder() {
            return order;
        }

        /**
         * Set The order of the defect area
         * @param order
         *            The order of the defect area
         */
        public void setOrder(BigDecimal order) {
            this.order = order;
        }

        /**
         * Get The codes of review types assigned to the defect area for the qa model.
         * @return The codes of review types assigned to the defect area for the qa model.
         */
        public List<String> getReviewTypeCodes() {
            return reviewTypeCodes;
        }

        /**
         * Set The codes of review types assigned to the defect area for the qa model.
         * @param reviewTypeCodes
         *            The codes of review types assigned to the defect area for the qa model.
         */
        public void setReviewTypeCodes(List<String> reviewTypeCodes) {
            this.reviewTypeCodes = reviewTypeCodes;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "QaModelDefectAreaDTO [ " + 
                "defectAreaId = " + defectAreaId + ", " + 
                "code = " + code + ", " + 
                "description = " + description + ", " + 
                "order = " + order + ", " + 
                "reviewTypeCodes = " + reviewTypeCodes + 
                " ]";
        }
    }

    // QaModelDefectAreaDetail DTO
    public static class QaModelDefectAreaDetailDTO implements Serializable {

        /**
         * The id of the defect area.
         */
        private String defectAreaId;

        /**
         * The unique code for the defect area.
         */
        private String code;

        /**
         * The description for the defect area.
         */
        private String description;

        /**
         * The order of the defect area.
         */
        private BigDecimal order;

        /**
         * The codes of review types assigned to the defect area for the qa model.
         */
        private List<String> reviewTypeCodes;

        /**
         * Collection of qa model defect area loan attributes.
         */
        private List<QaModelLoanAttributeDTO> loanAttributes;

        /**
         * Collection of remediation types.  Note that the &#x27;code&#x27; field of this DTO will not be used for this collection.
         */
        private List<OrderedItemDTO> remediationTypes;

        /**
         * Collection of qa model defect area sources.
         */
        private List<OrderedItemDTO> sources;

        /**
         * Collection of qa model defect area causes.
         */
        private List<OrderedItemDTO> causes;

        /**
         * Collection of qa model defect area thresholds.
         */
        private List<QaModelDefectAreaThresholdDTO> thresholds;

        /**
         * Collection of qa model defect area severities.
         */
        private List<QaModelDefectAreaSeverityDTO> severities;

        /**
         * Collection of qa model questions for the defect area.
         */
        private List<ReviewQuestionDTO> questions;

        /**
         * Whether the pre-qualification question is enabled.
         */
        private Boolean enablePreQualifyQuestion;

        /**
         * The qa model pre qualify question.
         */
        private QaModelDefectAreaPreQualifyDTO preQualifyQuestion;


        /**
         * Creates a new instance of QaModelDefectAreaDetailDTO
         */
        public QaModelDefectAreaDetailDTO() {}

        /**
         * Get The id of the defect area.
         * @return The id of the defect area.
         */
        public String getDefectAreaId() {
            return defectAreaId;
        }

        /**
         * Set The id of the defect area.
         * @param defectAreaId
         *            The id of the defect area.
         */
        public void setDefectAreaId(String defectAreaId) {
            this.defectAreaId = defectAreaId;
        }

        /**
         * Get The unique code for the defect area.
         * @return The unique code for the defect area.
         */
        public String getCode() {
            return code;
        }

        /**
         * Set The unique code for the defect area.
         * @param code
         *            The unique code for the defect area.
         */
        public void setCode(String code) {
            this.code = code;
        }

        /**
         * Get The description for the defect area.
         * @return The description for the defect area.
         */
        public String getDescription() {
            return description;
        }

        /**
         * Set The description for the defect area.
         * @param description
         *            The description for the defect area.
         */
        public void setDescription(String description) {
            this.description = description;
        }

        /**
         * Get The order of the defect area.
         * @return The order of the defect area.
         */
        public BigDecimal getOrder() {
            return order;
        }

        /**
         * Set The order of the defect area.
         * @param order
         *            The order of the defect area.
         */
        public void setOrder(BigDecimal order) {
            this.order = order;
        }

        /**
         * Get The codes of review types assigned to the defect area for the qa model.
         * @return The codes of review types assigned to the defect area for the qa model.
         */
        public List<String> getReviewTypeCodes() {
            return reviewTypeCodes;
        }

        /**
         * Set The codes of review types assigned to the defect area for the qa model.
         * @param reviewTypeCodes
         *            The codes of review types assigned to the defect area for the qa model.
         */
        public void setReviewTypeCodes(List<String> reviewTypeCodes) {
            this.reviewTypeCodes = reviewTypeCodes;
        }

        /**
         * Get Collection of qa model defect area loan attributes.
         * @return Collection of qa model defect area loan attributes.
         */
        public List<QaModelLoanAttributeDTO> getLoanAttributes() {
            return loanAttributes;
        }

        /**
         * Set Collection of qa model defect area loan attributes.
         * @param loanAttributes
         *            Collection of qa model defect area loan attributes.
         */
        public void setLoanAttributes(List<QaModelLoanAttributeDTO> loanAttributes) {
            this.loanAttributes = loanAttributes;
        }

        /**
         * Get Collection of remediation types.  Note that the &#x27;code&#x27; field of this DTO will not be used for this collection.
         * @return Collection of remediation types.  Note that the &#x27;code&#x27; field of this DTO will not be used for this collection.
         */
        public List<OrderedItemDTO> getRemediationTypes() {
            return remediationTypes;
        }

        /**
         * Set Collection of remediation types.  Note that the &#x27;code&#x27; field of this DTO will not be used for this collection.
         * @param remediationTypes
         *            Collection of remediation types.  Note that the &#x27;code&#x27; field of this DTO will not be used for this collection.
         */
        public void setRemediationTypes(List<OrderedItemDTO> remediationTypes) {
            this.remediationTypes = remediationTypes;
        }

        /**
         * Get Collection of qa model defect area sources.
         * @return Collection of qa model defect area sources.
         */
        public List<OrderedItemDTO> getSources() {
            return sources;
        }

        /**
         * Set Collection of qa model defect area sources.
         * @param sources
         *            Collection of qa model defect area sources.
         */
        public void setSources(List<OrderedItemDTO> sources) {
            this.sources = sources;
        }

        /**
         * Get Collection of qa model defect area causes.
         * @return Collection of qa model defect area causes.
         */
        public List<OrderedItemDTO> getCauses() {
            return causes;
        }

        /**
         * Set Collection of qa model defect area causes.
         * @param causes
         *            Collection of qa model defect area causes.
         */
        public void setCauses(List<OrderedItemDTO> causes) {
            this.causes = causes;
        }

        /**
         * Get Collection of qa model defect area thresholds.
         * @return Collection of qa model defect area thresholds.
         */
        public List<QaModelDefectAreaThresholdDTO> getThresholds() {
            return thresholds;
        }

        /**
         * Set Collection of qa model defect area thresholds.
         * @param thresholds
         *            Collection of qa model defect area thresholds.
         */
        public void setThresholds(List<QaModelDefectAreaThresholdDTO> thresholds) {
            this.thresholds = thresholds;
        }

        /**
         * Get Collection of qa model defect area severities.
         * @return Collection of qa model defect area severities.
         */
        public List<QaModelDefectAreaSeverityDTO> getSeverities() {
            return severities;
        }

        /**
         * Set Collection of qa model defect area severities.
         * @param severities
         *            Collection of qa model defect area severities.
         */
        public void setSeverities(List<QaModelDefectAreaSeverityDTO> severities) {
            this.severities = severities;
        }

        /**
         * Get Collection of qa model questions for the defect area.
         * @return Collection of qa model questions for the defect area.
         */
        public List<ReviewQuestionDTO> getQuestions() {
            return questions;
        }

        /**
         * Set Collection of qa model questions for the defect area.
         * @param questions
         *            Collection of qa model questions for the defect area.
         */
        public void setQuestions(List<ReviewQuestionDTO> questions) {
            this.questions = questions;
        }

        /**
         * Get Whether the pre-qualification question is enabled.
         * @return Whether the pre-qualification question is enabled.
         */
        public Boolean getEnablePreQualifyQuestion() {
            return enablePreQualifyQuestion;
        }

        /**
         * Set Whether the pre-qualification question is enabled.
         * @param enablePreQualifyQuestion
         *            Whether the pre-qualification question is enabled.
         */
        public void setEnablePreQualifyQuestion(Boolean enablePreQualifyQuestion) {
            this.enablePreQualifyQuestion = enablePreQualifyQuestion;
        }

        /**
         * Get The qa model pre qualify question.
         * @return The qa model pre qualify question.
         */
        public QaModelDefectAreaPreQualifyDTO getPreQualifyQuestion() {
            return preQualifyQuestion;
        }

        /**
         * Set The qa model pre qualify question.
         * @param preQualifyQuestion
         *            The qa model pre qualify question.
         */
        public void setPreQualifyQuestion(QaModelDefectAreaPreQualifyDTO preQualifyQuestion) {
            this.preQualifyQuestion = preQualifyQuestion;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "QaModelDefectAreaDetailDTO [ " + 
                "defectAreaId = " + defectAreaId + ", " + 
                "code = " + code + ", " + 
                "description = " + description + ", " + 
                "order = " + order + ", " + 
                "reviewTypeCodes = " + reviewTypeCodes + ", " + 
                "loanAttributes = " + loanAttributes + ", " + 
                "remediationTypes = " + remediationTypes + ", " + 
                "sources = " + sources + ", " + 
                "causes = " + causes + ", " + 
                "thresholds = " + thresholds + ", " + 
                "severities = " + severities + ", " + 
                "questions = " + questions + ", " + 
                "enablePreQualifyQuestion = " + enablePreQualifyQuestion + ", " + 
                "preQualifyQuestion = " + preQualifyQuestion + 
                " ]";
        }
    }

    // QaModelDefectAreaThreshold DTO
    public static class QaModelDefectAreaThresholdDTO implements Serializable {

        /**
         * The id of this item.
         */
        private String id;

        /**
         * The source code for this item.
         */
        private String sourceCode;

        /**
         * The cause code for this item.
         */
        private String causeCode;

        /**
         * The severity code for this item.
         */
        private String severityCode;


        /**
         * Creates a new instance of QaModelDefectAreaThresholdDTO
         */
        public QaModelDefectAreaThresholdDTO() {}

        /**
         * Get The id of this item.
         * @return The id of this item.
         */
        public String getId() {
            return id;
        }

        /**
         * Set The id of this item.
         * @param id
         *            The id of this item.
         */
        public void setId(String id) {
            this.id = id;
        }

        /**
         * Get The source code for this item.
         * @return The source code for this item.
         */
        public String getSourceCode() {
            return sourceCode;
        }

        /**
         * Set The source code for this item.
         * @param sourceCode
         *            The source code for this item.
         */
        public void setSourceCode(String sourceCode) {
            this.sourceCode = sourceCode;
        }

        /**
         * Get The cause code for this item.
         * @return The cause code for this item.
         */
        public String getCauseCode() {
            return causeCode;
        }

        /**
         * Set The cause code for this item.
         * @param causeCode
         *            The cause code for this item.
         */
        public void setCauseCode(String causeCode) {
            this.causeCode = causeCode;
        }

        /**
         * Get The severity code for this item.
         * @return The severity code for this item.
         */
        public String getSeverityCode() {
            return severityCode;
        }

        /**
         * Set The severity code for this item.
         * @param severityCode
         *            The severity code for this item.
         */
        public void setSeverityCode(String severityCode) {
            this.severityCode = severityCode;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "QaModelDefectAreaThresholdDTO [ " + 
                "id = " + id + ", " + 
                "sourceCode = " + sourceCode + ", " + 
                "causeCode = " + causeCode + ", " + 
                "severityCode = " + severityCode + 
                " ]";
        }
    }

    // QaModelDefectAreaSeverity DTO
    public static class QaModelDefectAreaSeverityDTO implements Serializable {

        /**
         * The defect severity tier id of this defect area
         */
        private String id;

        /**
         * The defect severity tier code of this defect area
         */
        private String code;

        /**
         * The description for the defect severity tier code of this defect area
         */
        private String description;

        /**
         * The order of the item
         */
        private BigDecimal order;

        /**
         * The examples for the defect severity tier code of this defect area
         */
        private String examples;


        /**
         * Creates a new instance of QaModelDefectAreaSeverityDTO
         */
        public QaModelDefectAreaSeverityDTO() {}

        /**
         * Get The defect severity tier id of this defect area
         * @return The defect severity tier id of this defect area
         */
        public String getId() {
            return id;
        }

        /**
         * Set The defect severity tier id of this defect area
         * @param id
         *            The defect severity tier id of this defect area
         */
        public void setId(String id) {
            this.id = id;
        }

        /**
         * Get The defect severity tier code of this defect area
         * @return The defect severity tier code of this defect area
         */
        public String getCode() {
            return code;
        }

        /**
         * Set The defect severity tier code of this defect area
         * @param code
         *            The defect severity tier code of this defect area
         */
        public void setCode(String code) {
            this.code = code;
        }

        /**
         * Get The description for the defect severity tier code of this defect area
         * @return The description for the defect severity tier code of this defect area
         */
        public String getDescription() {
            return description;
        }

        /**
         * Set The description for the defect severity tier code of this defect area
         * @param description
         *            The description for the defect severity tier code of this defect area
         */
        public void setDescription(String description) {
            this.description = description;
        }

        /**
         * Get The order of the item
         * @return The order of the item
         */
        public BigDecimal getOrder() {
            return order;
        }

        /**
         * Set The order of the item
         * @param order
         *            The order of the item
         */
        public void setOrder(BigDecimal order) {
            this.order = order;
        }

        /**
         * Get The examples for the defect severity tier code of this defect area
         * @return The examples for the defect severity tier code of this defect area
         */
        public String getExamples() {
            return examples;
        }

        /**
         * Set The examples for the defect severity tier code of this defect area
         * @param examples
         *            The examples for the defect severity tier code of this defect area
         */
        public void setExamples(String examples) {
            this.examples = examples;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "QaModelDefectAreaSeverityDTO [ " + 
                "id = " + id + ", " + 
                "code = " + code + ", " + 
                "description = " + description + ", " + 
                "order = " + order + ", " + 
                "examples = " + examples + 
                " ]";
        }
    }

    // QaModelDefectAreaPreQualify DTO
    public static class QaModelDefectAreaPreQualifyDTO implements Serializable {

        /**
         * The pre qualify question ID.
         */
        private String id;

        /**
         * The pre qualify question unique reference string for this question (aka &#x27;hash&#x27;)
         */
        private String code;

        /**
         * The pre qualify  question text to display.
         */
        private String description;

        /**
         * Multiple options questions
         */
        private List<OptionsItemDTO> optionsQuestions;


        /**
         * Creates a new instance of QaModelDefectAreaPreQualifyDTO
         */
        public QaModelDefectAreaPreQualifyDTO() {}

        /**
         * Get The pre qualify question ID.
         * @return The pre qualify question ID.
         */
        public String getId() {
            return id;
        }

        /**
         * Set The pre qualify question ID.
         * @param id
         *            The pre qualify question ID.
         */
        public void setId(String id) {
            this.id = id;
        }

        /**
         * Get The pre qualify question unique reference string for this question (aka &#x27;hash&#x27;)
         * @return The pre qualify question unique reference string for this question (aka &#x27;hash&#x27;)
         */
        public String getCode() {
            return code;
        }

        /**
         * Set The pre qualify question unique reference string for this question (aka &#x27;hash&#x27;)
         * @param code
         *            The pre qualify question unique reference string for this question (aka &#x27;hash&#x27;)
         */
        public void setCode(String code) {
            this.code = code;
        }

        /**
         * Get The pre qualify  question text to display.
         * @return The pre qualify  question text to display.
         */
        public String getDescription() {
            return description;
        }

        /**
         * Set The pre qualify  question text to display.
         * @param description
         *            The pre qualify  question text to display.
         */
        public void setDescription(String description) {
            this.description = description;
        }

        /**
         * Get Multiple options questions
         * @return Multiple options questions
         */
        public List<OptionsItemDTO> getOptionsQuestions() {
            return optionsQuestions;
        }

        /**
         * Set Multiple options questions
         * @param optionsQuestions
         *            Multiple options questions
         */
        public void setOptionsQuestions(List<OptionsItemDTO> optionsQuestions) {
            this.optionsQuestions = optionsQuestions;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "QaModelDefectAreaPreQualifyDTO [ " + 
                "id = " + id + ", " + 
                "code = " + code + ", " + 
                "description = " + description + ", " + 
                "optionsQuestions = " + optionsQuestions + 
                " ]";
        }
    }

    // DateRange DTO
    public static class DateRangeDTO implements Serializable {

        /**
         * 
         */
        private Date startDate;

        /**
         * 
         */
        private Date endDate;


        /**
         * Creates a new instance of DateRangeDTO
         */
        public DateRangeDTO() {}

        /**
         * Get 
         * @return 
         */
        public Date getStartDate() {
            return startDate;
        }

        /**
         * Set 
         * @param startDate
         *            
         */
        public void setStartDate(Date startDate) {
            this.startDate = startDate;
        }

        /**
         * Get 
         * @return 
         */
        public Date getEndDate() {
            return endDate;
        }

        /**
         * Set 
         * @param endDate
         *            
         */
        public void setEndDate(Date endDate) {
            this.endDate = endDate;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "DateRangeDTO [ " + 
                "startDate = " + startDate + ", " + 
                "endDate = " + endDate + 
                " ]";
        }
    }

    // Job DTO
    public static class JobDTO implements Serializable {

        /**
         * The identifier of the job
         */
        private String jobId;

        /**
         * AGGREGATION, BINDER_DELIVERY_CHECK, DISTRIBUTION, EARLY_CLAIM_SELECTION, EARLY_PAYMENT_DEFAULT_SELECTION, ENDORSEMENT_SELECTION, or NATIONAL_QC_SELECTION
         */
        private String type;

        /**
         * Pending, Completed, Cancelled
         */
        private String status;

        /**
         * 
         */
        private List<JobParameterDTO> parameters;

        /**
         * 
         */
        private List<JobExecutionDTO> executions;


        /**
         * Creates a new instance of JobDTO
         */
        public JobDTO() {}

        /**
         * Get The identifier of the job
         * @return The identifier of the job
         */
        public String getJobId() {
            return jobId;
        }

        /**
         * Set The identifier of the job
         * @param jobId
         *            The identifier of the job
         */
        public void setJobId(String jobId) {
            this.jobId = jobId;
        }

        /**
         * Get AGGREGATION, BINDER_DELIVERY_CHECK, DISTRIBUTION, EARLY_CLAIM_SELECTION, EARLY_PAYMENT_DEFAULT_SELECTION, ENDORSEMENT_SELECTION, or NATIONAL_QC_SELECTION
         * @return AGGREGATION, BINDER_DELIVERY_CHECK, DISTRIBUTION, EARLY_CLAIM_SELECTION, EARLY_PAYMENT_DEFAULT_SELECTION, ENDORSEMENT_SELECTION, or NATIONAL_QC_SELECTION
         */
        public String getType() {
            return type;
        }

        /**
         * Set AGGREGATION, BINDER_DELIVERY_CHECK, DISTRIBUTION, EARLY_CLAIM_SELECTION, EARLY_PAYMENT_DEFAULT_SELECTION, ENDORSEMENT_SELECTION, or NATIONAL_QC_SELECTION
         * @param type
         *            AGGREGATION, BINDER_DELIVERY_CHECK, DISTRIBUTION, EARLY_CLAIM_SELECTION, EARLY_PAYMENT_DEFAULT_SELECTION, ENDORSEMENT_SELECTION, or NATIONAL_QC_SELECTION
         */
        public void setType(String type) {
            this.type = type;
        }

        /**
         * Get Pending, Completed, Cancelled
         * @return Pending, Completed, Cancelled
         */
        public String getStatus() {
            return status;
        }

        /**
         * Set Pending, Completed, Cancelled
         * @param status
         *            Pending, Completed, Cancelled
         */
        public void setStatus(String status) {
            this.status = status;
        }

        /**
         * Get 
         * @return 
         */
        public List<JobParameterDTO> getParameters() {
            return parameters;
        }

        /**
         * Set 
         * @param parameters
         *            
         */
        public void setParameters(List<JobParameterDTO> parameters) {
            this.parameters = parameters;
        }

        /**
         * Get 
         * @return 
         */
        public List<JobExecutionDTO> getExecutions() {
            return executions;
        }

        /**
         * Set 
         * @param executions
         *            
         */
        public void setExecutions(List<JobExecutionDTO> executions) {
            this.executions = executions;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "JobDTO [ " + 
                "jobId = " + jobId + ", " + 
                "type = " + type + ", " + 
                "status = " + status + ", " + 
                "parameters = " + parameters + ", " + 
                "executions = " + executions + 
                " ]";
        }
    }

    // JobParameter DTO
    public static class JobParameterDTO implements Serializable {

        /**
         * Parameter name
         */
        private String name;

        /**
         * Parameter value
         */
        private String value;


        /**
         * Creates a new instance of JobParameterDTO
         */
        public JobParameterDTO() {}

        /**
         * Get Parameter name
         * @return Parameter name
         */
        public String getName() {
            return name;
        }

        /**
         * Set Parameter name
         * @param name
         *            Parameter name
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * Get Parameter value
         * @return Parameter value
         */
        public String getValue() {
            return value;
        }

        /**
         * Set Parameter value
         * @param value
         *            Parameter value
         */
        public void setValue(String value) {
            this.value = value;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "JobParameterDTO [ " + 
                "name = " + name + ", " + 
                "value = " + value + 
                " ]";
        }
    }

    // JobExecution DTO
    public static class JobExecutionDTO implements Serializable {

        /**
         * The identifier of the job
         */
        private String jobExecutionId;

        /**
         * COMPLETED, FAILED, or STARTED
         */
        private String status;

        /**
         * 
         */
        private Date startDate;

        /**
         * 
         */
        private Date endDate;

        /**
         * 
         */
        private String exceptionDetails;

        /**
         * 
         */
        private String createdBy;

        /**
         * 
         */
        private String serverName;

        /**
         * 
         */
        private String serverPriority;


        /**
         * Creates a new instance of JobExecutionDTO
         */
        public JobExecutionDTO() {}

        /**
         * Get The identifier of the job
         * @return The identifier of the job
         */
        public String getJobExecutionId() {
            return jobExecutionId;
        }

        /**
         * Set The identifier of the job
         * @param jobExecutionId
         *            The identifier of the job
         */
        public void setJobExecutionId(String jobExecutionId) {
            this.jobExecutionId = jobExecutionId;
        }

        /**
         * Get COMPLETED, FAILED, or STARTED
         * @return COMPLETED, FAILED, or STARTED
         */
        public String getStatus() {
            return status;
        }

        /**
         * Set COMPLETED, FAILED, or STARTED
         * @param status
         *            COMPLETED, FAILED, or STARTED
         */
        public void setStatus(String status) {
            this.status = status;
        }

        /**
         * Get 
         * @return 
         */
        public Date getStartDate() {
            return startDate;
        }

        /**
         * Set 
         * @param startDate
         *            
         */
        public void setStartDate(Date startDate) {
            this.startDate = startDate;
        }

        /**
         * Get 
         * @return 
         */
        public Date getEndDate() {
            return endDate;
        }

        /**
         * Set 
         * @param endDate
         *            
         */
        public void setEndDate(Date endDate) {
            this.endDate = endDate;
        }

        /**
         * Get 
         * @return 
         */
        public String getExceptionDetails() {
            return exceptionDetails;
        }

        /**
         * Set 
         * @param exceptionDetails
         *            
         */
        public void setExceptionDetails(String exceptionDetails) {
            this.exceptionDetails = exceptionDetails;
        }

        /**
         * Get 
         * @return 
         */
        public String getCreatedBy() {
            return createdBy;
        }

        /**
         * Set 
         * @param createdBy
         *            
         */
        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        /**
         * Get 
         * @return 
         */
        public String getServerName() {
            return serverName;
        }

        /**
         * Set 
         * @param serverName
         *            
         */
        public void setServerName(String serverName) {
            this.serverName = serverName;
        }

        /**
         * Get 
         * @return 
         */
        public String getServerPriority() {
            return serverPriority;
        }

        /**
         * Set 
         * @param serverPriority
         *            
         */
        public void setServerPriority(String serverPriority) {
            this.serverPriority = serverPriority;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "JobExecutionDTO [ " + 
                "jobExecutionId = " + jobExecutionId + ", " + 
                "status = " + status + ", " + 
                "startDate = " + startDate + ", " + 
                "endDate = " + endDate + ", " + 
                "exceptionDetails = " + exceptionDetails + ", " + 
                "createdBy = " + createdBy + ", " + 
                "serverName = " + serverName + ", " + 
                "serverPriority = " + serverPriority + 
                " ]";
        }
    }

    // ModelDuplicator DTO
    public static class ModelDuplicatorDTO implements Serializable {

        /**
         * The new name for the duplicated model.
         */
        private String name;


        /**
         * Creates a new instance of ModelDuplicatorDTO
         */
        public ModelDuplicatorDTO() {}

        /**
         * Get The new name for the duplicated model.
         * @return The new name for the duplicated model.
         */
        public String getName() {
            return name;
        }

        /**
         * Set The new name for the duplicated model.
         * @param name
         *            The new name for the duplicated model.
         */
        public void setName(String name) {
            this.name = name;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "ModelDuplicatorDTO [ " + 
                "name = " + name + 
                " ]";
        }
    }

    // IndemAccept DTO
    public static class IndemAcceptDTO implements Serializable {

        /**
         * The identifier of the review location of the indemnification
         */
        private String reviewLocationId;

        /**
         * The indicator if the indemnification is transferrable
         */
        private Boolean indemTransferrable;


        /**
         * Creates a new instance of IndemAcceptDTO
         */
        public IndemAcceptDTO() {}

        /**
         * Get The identifier of the review location of the indemnification
         * @return The identifier of the review location of the indemnification
         */
        public String getReviewLocationId() {
            return reviewLocationId;
        }

        /**
         * Set The identifier of the review location of the indemnification
         * @param reviewLocationId
         *            The identifier of the review location of the indemnification
         */
        public void setReviewLocationId(String reviewLocationId) {
            this.reviewLocationId = reviewLocationId;
        }

        /**
         * Get The indicator if the indemnification is transferrable
         * @return The indicator if the indemnification is transferrable
         */
        public Boolean getIndemTransferrable() {
            return indemTransferrable;
        }

        /**
         * Set The indicator if the indemnification is transferrable
         * @param indemTransferrable
         *            The indicator if the indemnification is transferrable
         */
        public void setIndemTransferrable(Boolean indemTransferrable) {
            this.indemTransferrable = indemTransferrable;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "IndemAcceptDTO [ " + 
                "reviewLocationId = " + reviewLocationId + ", " + 
                "indemTransferrable = " + indemTransferrable + 
                " ]";
        }
    }

    // NameValuePair DTO
    public static class NameValuePairDTO implements Serializable {

        /**
         * The human-readable name for this variable.
         */
        private String name;

        /**
         * The value for this variable.
         */
        private String value;


        /**
         * Creates a new instance of NameValuePairDTO
         */
        public NameValuePairDTO() {}

        /**
         * Get The human-readable name for this variable.
         * @return The human-readable name for this variable.
         */
        public String getName() {
            return name;
        }

        /**
         * Set The human-readable name for this variable.
         * @param name
         *            The human-readable name for this variable.
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * Get The value for this variable.
         * @return The value for this variable.
         */
        public String getValue() {
            return value;
        }

        /**
         * Set The value for this variable.
         * @param value
         *            The value for this variable.
         */
        public void setValue(String value) {
            this.value = value;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "NameValuePairDTO [ " + 
                "name = " + name + ", " + 
                "value = " + value + 
                " ]";
        }
    }

    // EmailTemplateVersion DTO
    public static class EmailTemplateVersionDTO implements Serializable {

        /**
         * The name of the email this template belongs to.
         */
        private String emailName;

        /**
         * The category name of this email template version. Should hint at what this email is used for.
         */
        private String emailCategory;

        /**
         * An incremented counter of the templates created for this email.
         */
        private Integer version;

        /**
         * A human readable description of this email template. Private to HUD.
         */
        private String description;

        /**
         * The date that this template was last modified.
         */
        private Date lastModifiedDate;

        /**
         * The name of who had last modified this email template.
         */
        private String lastModifiedBy;

        /**
         * The date that this template was activated.
         */
        private Date activationDate;

        /**
         * State of this email template version.
         */
        private Boolean isActive;

        /**
         * A unique identifier of this Email Template.
         */
        private String id;

        /**
         * The text that will render into the subject of this email.
         */
        private String subject;

        /**
         * The text that will render into the body of this email.
         */
        private String copy;

        /**
         * A collection of variables that can be rendered into the template.
         */
        private List<NameValuePairDTO> documentVariables;


        /**
         * Creates a new instance of EmailTemplateVersionDTO
         */
        public EmailTemplateVersionDTO() {}

        /**
         * Get The name of the email this template belongs to.
         * @return The name of the email this template belongs to.
         */
        public String getEmailName() {
            return emailName;
        }

        /**
         * Set The name of the email this template belongs to.
         * @param emailName
         *            The name of the email this template belongs to.
         */
        public void setEmailName(String emailName) {
            this.emailName = emailName;
        }

        /**
         * Get The category name of this email template version. Should hint at what this email is used for.
         * @return The category name of this email template version. Should hint at what this email is used for.
         */
        public String getEmailCategory() {
            return emailCategory;
        }

        /**
         * Set The category name of this email template version. Should hint at what this email is used for.
         * @param emailCategory
         *            The category name of this email template version. Should hint at what this email is used for.
         */
        public void setEmailCategory(String emailCategory) {
            this.emailCategory = emailCategory;
        }

        /**
         * Get An incremented counter of the templates created for this email.
         * @return An incremented counter of the templates created for this email.
         */
        public Integer getVersion() {
            return version;
        }

        /**
         * Set An incremented counter of the templates created for this email.
         * @param version
         *            An incremented counter of the templates created for this email.
         */
        public void setVersion(Integer version) {
            this.version = version;
        }

        /**
         * Get A human readable description of this email template. Private to HUD.
         * @return A human readable description of this email template. Private to HUD.
         */
        public String getDescription() {
            return description;
        }

        /**
         * Set A human readable description of this email template. Private to HUD.
         * @param description
         *            A human readable description of this email template. Private to HUD.
         */
        public void setDescription(String description) {
            this.description = description;
        }

        /**
         * Get The date that this template was last modified.
         * @return The date that this template was last modified.
         */
        public Date getLastModifiedDate() {
            return lastModifiedDate;
        }

        /**
         * Set The date that this template was last modified.
         * @param lastModifiedDate
         *            The date that this template was last modified.
         */
        public void setLastModifiedDate(Date lastModifiedDate) {
            this.lastModifiedDate = lastModifiedDate;
        }

        /**
         * Get The name of who had last modified this email template.
         * @return The name of who had last modified this email template.
         */
        public String getLastModifiedBy() {
            return lastModifiedBy;
        }

        /**
         * Set The name of who had last modified this email template.
         * @param lastModifiedBy
         *            The name of who had last modified this email template.
         */
        public void setLastModifiedBy(String lastModifiedBy) {
            this.lastModifiedBy = lastModifiedBy;
        }

        /**
         * Get The date that this template was activated.
         * @return The date that this template was activated.
         */
        public Date getActivationDate() {
            return activationDate;
        }

        /**
         * Set The date that this template was activated.
         * @param activationDate
         *            The date that this template was activated.
         */
        public void setActivationDate(Date activationDate) {
            this.activationDate = activationDate;
        }

        /**
         * Get State of this email template version.
         * @return State of this email template version.
         */
        public Boolean getIsActive() {
            return isActive;
        }

        /**
         * Set State of this email template version.
         * @param isActive
         *            State of this email template version.
         */
        public void setIsActive(Boolean isActive) {
            this.isActive = isActive;
        }

        /**
         * Get A unique identifier of this Email Template.
         * @return A unique identifier of this Email Template.
         */
        public String getId() {
            return id;
        }

        /**
         * Set A unique identifier of this Email Template.
         * @param id
         *            A unique identifier of this Email Template.
         */
        public void setId(String id) {
            this.id = id;
        }

        /**
         * Get The text that will render into the subject of this email.
         * @return The text that will render into the subject of this email.
         */
        public String getSubject() {
            return subject;
        }

        /**
         * Set The text that will render into the subject of this email.
         * @param subject
         *            The text that will render into the subject of this email.
         */
        public void setSubject(String subject) {
            this.subject = subject;
        }

        /**
         * Get The text that will render into the body of this email.
         * @return The text that will render into the body of this email.
         */
        public String getCopy() {
            return copy;
        }

        /**
         * Set The text that will render into the body of this email.
         * @param copy
         *            The text that will render into the body of this email.
         */
        public void setCopy(String copy) {
            this.copy = copy;
        }

        /**
         * Get A collection of variables that can be rendered into the template.
         * @return A collection of variables that can be rendered into the template.
         */
        public List<NameValuePairDTO> getDocumentVariables() {
            return documentVariables;
        }

        /**
         * Set A collection of variables that can be rendered into the template.
         * @param documentVariables
         *            A collection of variables that can be rendered into the template.
         */
        public void setDocumentVariables(List<NameValuePairDTO> documentVariables) {
            this.documentVariables = documentVariables;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "EmailTemplateVersionDTO [ " + 
                "emailName = " + emailName + ", " + 
                "emailCategory = " + emailCategory + ", " + 
                "version = " + version + ", " + 
                "description = " + description + ", " + 
                "lastModifiedDate = " + lastModifiedDate + ", " + 
                "lastModifiedBy = " + lastModifiedBy + ", " + 
                "activationDate = " + activationDate + ", " + 
                "isActive = " + isActive + ", " + 
                "id = " + id + ", " + 
                "subject = " + subject + ", " + 
                "copy = " + copy + ", " + 
                "documentVariables = " + documentVariables + 
                " ]";
        }
    }

    // EmailTemplateVersionLite DTO
    public static class EmailTemplateVersionLiteDTO implements Serializable {

        /**
         * A unique identifier of this Email Template.
         */
        private String id;

        /**
         * An incremented counter of the templates created for this email.
         */
        private Integer version;

        /**
         * The name of the email this template belongs to.
         */
        private String emailName;

        /**
         * The date that this template was last modified.
         */
        private Date lastModifiedDate;

        /**
         * The name of who had last modified this email template.
         */
        private String lastModifiedBy;

        /**
         * The date that this template was activated.
         */
        private Date activationDate;

        /**
         * State of this email template version.
         */
        private Boolean isActive;


        /**
         * Creates a new instance of EmailTemplateVersionLiteDTO
         */
        public EmailTemplateVersionLiteDTO() {}

        /**
         * Get A unique identifier of this Email Template.
         * @return A unique identifier of this Email Template.
         */
        public String getId() {
            return id;
        }

        /**
         * Set A unique identifier of this Email Template.
         * @param id
         *            A unique identifier of this Email Template.
         */
        public void setId(String id) {
            this.id = id;
        }

        /**
         * Get An incremented counter of the templates created for this email.
         * @return An incremented counter of the templates created for this email.
         */
        public Integer getVersion() {
            return version;
        }

        /**
         * Set An incremented counter of the templates created for this email.
         * @param version
         *            An incremented counter of the templates created for this email.
         */
        public void setVersion(Integer version) {
            this.version = version;
        }

        /**
         * Get The name of the email this template belongs to.
         * @return The name of the email this template belongs to.
         */
        public String getEmailName() {
            return emailName;
        }

        /**
         * Set The name of the email this template belongs to.
         * @param emailName
         *            The name of the email this template belongs to.
         */
        public void setEmailName(String emailName) {
            this.emailName = emailName;
        }

        /**
         * Get The date that this template was last modified.
         * @return The date that this template was last modified.
         */
        public Date getLastModifiedDate() {
            return lastModifiedDate;
        }

        /**
         * Set The date that this template was last modified.
         * @param lastModifiedDate
         *            The date that this template was last modified.
         */
        public void setLastModifiedDate(Date lastModifiedDate) {
            this.lastModifiedDate = lastModifiedDate;
        }

        /**
         * Get The name of who had last modified this email template.
         * @return The name of who had last modified this email template.
         */
        public String getLastModifiedBy() {
            return lastModifiedBy;
        }

        /**
         * Set The name of who had last modified this email template.
         * @param lastModifiedBy
         *            The name of who had last modified this email template.
         */
        public void setLastModifiedBy(String lastModifiedBy) {
            this.lastModifiedBy = lastModifiedBy;
        }

        /**
         * Get The date that this template was activated.
         * @return The date that this template was activated.
         */
        public Date getActivationDate() {
            return activationDate;
        }

        /**
         * Set The date that this template was activated.
         * @param activationDate
         *            The date that this template was activated.
         */
        public void setActivationDate(Date activationDate) {
            this.activationDate = activationDate;
        }

        /**
         * Get State of this email template version.
         * @return State of this email template version.
         */
        public Boolean getIsActive() {
            return isActive;
        }

        /**
         * Set State of this email template version.
         * @param isActive
         *            State of this email template version.
         */
        public void setIsActive(Boolean isActive) {
            this.isActive = isActive;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "EmailTemplateVersionLiteDTO [ " + 
                "id = " + id + ", " + 
                "version = " + version + ", " + 
                "emailName = " + emailName + ", " + 
                "lastModifiedDate = " + lastModifiedDate + ", " + 
                "lastModifiedBy = " + lastModifiedBy + ", " + 
                "activationDate = " + activationDate + ", " + 
                "isActive = " + isActive + 
                " ]";
        }
    }

    // EmailTemplate DTO
    public static class EmailTemplateDTO implements Serializable {

        /**
         * The category name of this group of email template versions. Should hint at what this email template version is used for.
         */
        private String emailCategory;

        /**
         * A collection of &#x27;lite&#x27; email template versions for this email template.
         */
        private List<EmailTemplateVersionLiteDTO> templateVersions;


        /**
         * Creates a new instance of EmailTemplateDTO
         */
        public EmailTemplateDTO() {}

        /**
         * Get The category name of this group of email template versions. Should hint at what this email template version is used for.
         * @return The category name of this group of email template versions. Should hint at what this email template version is used for.
         */
        public String getEmailCategory() {
            return emailCategory;
        }

        /**
         * Set The category name of this group of email template versions. Should hint at what this email template version is used for.
         * @param emailCategory
         *            The category name of this group of email template versions. Should hint at what this email template version is used for.
         */
        public void setEmailCategory(String emailCategory) {
            this.emailCategory = emailCategory;
        }

        /**
         * Get A collection of &#x27;lite&#x27; email template versions for this email template.
         * @return A collection of &#x27;lite&#x27; email template versions for this email template.
         */
        public List<EmailTemplateVersionLiteDTO> getTemplateVersions() {
            return templateVersions;
        }

        /**
         * Set A collection of &#x27;lite&#x27; email template versions for this email template.
         * @param templateVersions
         *            A collection of &#x27;lite&#x27; email template versions for this email template.
         */
        public void setTemplateVersions(List<EmailTemplateVersionLiteDTO> templateVersions) {
            this.templateVersions = templateVersions;
        }


        /**
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "EmailTemplateDTO [ " + 
                "emailCategory = " + emailCategory + ", " + 
                "templateVersions = " + templateVersions + 
                " ]";
        }
    }

}
