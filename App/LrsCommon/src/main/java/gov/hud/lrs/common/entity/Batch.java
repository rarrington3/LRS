package gov.hud.lrs.common.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="BATCH")
@SuppressWarnings("serial")
public class Batch implements Serializable {

	private String batchId;
	private BatchStatusRef batchStatusRef;
	private Lender lender;
	private Personnel originalOwnerPersonnel;
	private Personnel ownerPersonnel;
	private ReviewLevelTypeRef reviewLevelTypeRef;
	private ReviewLocation reviewLocation;
	private SelectionReason selectionReason;
	private ReviewTypeRef reviewTypeRef;
	private SelectionRequest selectionRequest;
	private String batchReferenceId;
	private char operationalReviewInd;
	private Character requestOperationalDocumentsInd;
	private Date operationalDocumentsDueDt;
	private String secondaryReference;
	private String operationalReviewGuidance;
	private Character receivedOperationalDocumentsInd;
	private short iterationNumber;
	private String createdBy;
	private Date createdTs;
	private String updatedBy;
	private Date updatedTs;

	private Set<Document> documents = new HashSet<Document>(0);
	private Set<BatchPersonnel> batchPersonnels = new HashSet<BatchPersonnel>(0);
	private Set<Review> reviews = new HashSet<Review>(0);
	private Set<ReviewProcessException> reviewProcessExceptions = new HashSet<ReviewProcessException>(0);
	private Set<LoanSelection> loanSelections = new HashSet<LoanSelection>(0);
	private Set<LoanSelectionPending> loanSelectionPendings = new HashSet<LoanSelectionPending>(0);

	public Batch() {
	}

	@Id
    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")
    @GeneratedValue(generator="generator")
    @Column(name="BATCH_ID", unique=true, nullable=false, length=36)
	public String getBatchId() {
		return this.batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="BATCH_STATUS_ID", nullable=false)
	public BatchStatusRef getBatchStatusRef() {
		return this.batchStatusRef;
	}

	public void setBatchStatusRef(BatchStatusRef batchStatusRef) {
		this.batchStatusRef = batchStatusRef;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="MTGEE5", nullable=false)
	public Lender getLender() {
		return this.lender;
	}

	public void setLender(Lender lender) {
		this.lender = lender;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ORIGINAL_OWNER_PERSONNEL_ID")
	public Personnel getOriginalOwnerPersonnel() {
		return this.originalOwnerPersonnel;
	}

	public void setOriginalOwnerPersonnel(Personnel originalOwnerPersonnel) {
		this.originalOwnerPersonnel = originalOwnerPersonnel;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="OWNER_PERSONNEL_ID")
	public Personnel getOwnerPersonnel() {
		return this.ownerPersonnel;
	}

	public void setOwnerPersonnel(Personnel ownerPersonnel) {
		this.ownerPersonnel = ownerPersonnel;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="REVIEW_LEVEL_TYPE_ID", nullable=false)
	public ReviewLevelTypeRef getReviewLevelTypeRef() {
		return this.reviewLevelTypeRef;
	}

	public void setReviewLevelTypeRef(ReviewLevelTypeRef reviewLevelTypeRef) {
		this.reviewLevelTypeRef = reviewLevelTypeRef;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="REVIEW_LOCATION_ID")
	public ReviewLocation getReviewLocation() {
		return this.reviewLocation;
	}

	public void setReviewLocation(ReviewLocation reviewLocation) {
		this.reviewLocation = reviewLocation;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="SELECTION_REASON_ID", nullable=false)
	public SelectionReason getSelectionReason() {
		return this.selectionReason;
	}

	public void setSelectionReason(SelectionReason selectionReason) {
		this.selectionReason = selectionReason;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="REVIEW_TYPE_ID", nullable=false)
	public ReviewTypeRef getReviewTypeRef() {
		return this.reviewTypeRef;
	}

	public void setReviewTypeRef(ReviewTypeRef reviewTypeRef) {
		this.reviewTypeRef = reviewTypeRef;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="SELECTION_REQUEST_ID", nullable=false)
	public SelectionRequest getSelectionRequest() {
		return this.selectionRequest;
	}

	public void setSelectionRequest(SelectionRequest selectionRequest) {
		this.selectionRequest = selectionRequest;
	}


    @Column(name="CREATED_BY", nullable=false, length=6)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


    @Column(name="UPDATED_BY", length=6)
	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATED_TS", nullable=false, length=23)
	public Date getCreatedTs() {
		return this.createdTs;
	}

	public void setCreatedTs(Date createdTs) {
		this.createdTs = createdTs;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="UPDATED_TS", length=23)
	public Date getUpdatedTs() {
		return this.updatedTs;
	}

	public void setUpdatedTs(Date updatedTs) {
		this.updatedTs = updatedTs;
	}

    @Column(name="BATCH_REFERENCE_ID", insertable=false, updatable=false, length=16)
	public String getBatchReferenceId() {
		return this.batchReferenceId;
	}

	public void setBatchReferenceId(String batchReferenceId) {
		this.batchReferenceId = batchReferenceId;
	}

    @Column(name="OPERATIONAL_REVIEW_IND", nullable=false, length=1)
	public char getOperationalReviewInd() {
		return this.operationalReviewInd;
	}

	public void setOperationalReviewInd(char operationalReviewInd) {
		this.operationalReviewInd = operationalReviewInd;
	}

    @Column(name="REQUEST_OPERATIONAL_DOCUMENTS_IND", length=1)
	public Character getRequestOperationalDocumentsInd() {
		return this.requestOperationalDocumentsInd;
	}

	public void setRequestOperationalDocumentsInd(Character requestOperationalDocumentsInd) {
		this.requestOperationalDocumentsInd = requestOperationalDocumentsInd;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="OPERATIONAL_DOCUMENTS_DUE_DT", length=23)
	public Date getOperationalDocumentsDueDt() {
		return this.operationalDocumentsDueDt;
	}

	public void setOperationalDocumentsDueDt(Date operationalDocumentsDueDt) {
		this.operationalDocumentsDueDt = operationalDocumentsDueDt;
	}

    @Column(name="SECONDARY_REFERENCE", length=100)
	public String getSecondaryReference() {
		return this.secondaryReference;
	}

	public void setSecondaryReference(String secondaryReference) {
		this.secondaryReference = secondaryReference;
	}

    @Column(name="OPERATIONAL_REVIEW_GUIDANCE")
	public String getOperationalReviewGuidance() {
		return this.operationalReviewGuidance;
	}

	public void setOperationalReviewGuidance(String operationalReviewGuidance) {
		this.operationalReviewGuidance = operationalReviewGuidance;
	}

    @Column(name="RECEIVED_OPERATIONAL_DOCUMENTS_IND", length=1)
	public Character getReceivedOperationalDocumentsInd() {
		return this.receivedOperationalDocumentsInd;
	}

	public void setReceivedOperationalDocumentsInd(Character receivedOperationalDocumentsInd) {
		this.receivedOperationalDocumentsInd = receivedOperationalDocumentsInd;
	}

    @Column(name="ITERATION_NUMBER", nullable=false)
	public short getIterationNumber() {
		return this.iterationNumber;
	}

	public void setIterationNumber(short iterationNumber) {
		this.iterationNumber = iterationNumber;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="batch")
	public Set<Document> getDocuments() {
		return this.documents;
	}

	public void setDocuments(Set<Document> documents) {
		this.documents = documents;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="batch")
	public Set<BatchPersonnel> getBatchPersonnels() {
		return this.batchPersonnels;
	}

	public void setBatchPersonnels(Set<BatchPersonnel> batchPersonnels) {
		this.batchPersonnels = batchPersonnels;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="batch")
	public Set<Review> getReviews() {
		return this.reviews;
	}

	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="batch")
	public Set<ReviewProcessException> getReviewProcessExceptions() {
		return this.reviewProcessExceptions;
	}

	public void setReviewProcessExceptions(Set<ReviewProcessException> reviewProcessExceptions) {
		this.reviewProcessExceptions = reviewProcessExceptions;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="batch")
	public Set<LoanSelection> getLoanSelections() {
		return this.loanSelections;
	}

	public void setLoanSelections(Set<LoanSelection> loanSelections) {
		this.loanSelections = loanSelections;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="batch")
	public Set<LoanSelectionPending> getLoanSelectionPendings() {
		return this.loanSelectionPendings;
	}

	public void setLoanSelectionPendings(Set<LoanSelectionPending> loanSelectionPendings) {
		this.loanSelectionPendings = loanSelectionPendings;
	}

}
