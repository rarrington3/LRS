//DO NOT MODIFY: generated for HUD LRS 
package gov.hud.lrs.common.entity;

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
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="REVIEW", uniqueConstraints = @UniqueConstraint(columnNames="SELECTION_ID"))
@SuppressWarnings("serial")
public class Review implements java.io.Serializable {
	private String reviewId;
	private Batch batch;
	private CancellationReasonRef cancellationReasonRef;
	private Lender lender;
	private LoanSelection loanSelection;
	private Personnel originalReviewerPersonnel;
	private ProductTypeRef productTypeRef;
	private QaModel qaModel;
	private QcOutcomeRef qcOutcomeRef;
	private RatingRef summaryRatingRef;
	private Review qcReview;
	private ReviewScopeRef reviewScopeRef;
	private ReviewStatusRef reviewStatusRef;
	private ReviewTypeRef reviewTypeRef;
	private SelectionReason selectionReason;
	private String caseNumber;
	private Date origMitRqstDt;
	private Date origAssignDt;
	private Date rvwCompltdDt;
	private Date rvwStartedDtTm;
	private String createdBy;
	private String updatedBy;
	private Date createdTs;
	private Date updatedTs;
	private String reviewReferenceId;
	private Date dueDate;
	private String qcOutcomeCd;
	private char mrbReferralInd;
	private String lastLenderName;
	private Set<ReviewReferral> reviewReferrals = new HashSet<ReviewReferral>(0);
	private Set<RvwLvlDefect> rvwLvlDefects = new HashSet<RvwLvlDefect>(0);
	private Set<ReviewLevel> reviewLevels = new HashSet<ReviewLevel>(0);
	private Set<Document> documents = new HashSet<Document>(0);
	private char managementDecisionInd;

	public Review() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")
    
    @Column(name="REVIEW_ID", unique=true, nullable=false, length=36)
	public String getReviewId() {
		return this.reviewId;
	}

	public void setReviewId(String reviewId) {
		this.reviewId = reviewId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="BATCH_ID")
	public Batch getBatch() {
		return this.batch;
	}

	public void setBatch(Batch batch) {
		this.batch = batch;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="CANCELLATION_REASON_ID")
	public CancellationReasonRef getCancellationReasonRef() {
		return this.cancellationReasonRef;
	}

	public void setCancellationReasonRef(CancellationReasonRef cancellationReasonRef) {
		this.cancellationReasonRef = cancellationReasonRef;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="MTGEE5")
	public Lender getLender() {
		return this.lender;
	}

	public void setLender(Lender lender) {
		this.lender = lender;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="SELECTION_ID", unique=true)
	public LoanSelection getLoanSelection() {
		return this.loanSelection;
	}

	public void setLoanSelection(LoanSelection loanSelection) {
		this.loanSelection = loanSelection;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ORIGINAL_REVIEWER_PERSONNEL_ID")
	public Personnel getOriginalReviewerPersonnel() {
		return this.originalReviewerPersonnel;
	}

	public void setOriginalReviewerPersonnel(Personnel originalReviewerPersonnel) {
		this.originalReviewerPersonnel = originalReviewerPersonnel;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="PRODUCT_TYPE_ID")
	public ProductTypeRef getProductTypeRef() {
		return this.productTypeRef;
	}

	public void setProductTypeRef(ProductTypeRef productTypeRef) {
		this.productTypeRef = productTypeRef;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="QA_MODEL_ID", nullable=false)
	public QaModel getQaModel() {
		return this.qaModel;
	}

	public void setQaModel(QaModel qaModel) {
		this.qaModel = qaModel;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="QC_OUTCOME_ID")
	public QcOutcomeRef getQcOutcomeRef() {
		return this.qcOutcomeRef;
	}

	public void setQcOutcomeRef(QcOutcomeRef qcOutcomeRef) {
		this.qcOutcomeRef = qcOutcomeRef;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="SUMMARY_RATING_ID")
	public RatingRef getSummaryRatingRef() {
		return this.summaryRatingRef;
	}

	public void setSummaryRatingRef(RatingRef summaryRatingRef) {
		this.summaryRatingRef = summaryRatingRef;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="QC_REVIEW_ID")
	public Review getQcReview() {
		return this.qcReview;
	}

	public void setQcReview(Review qcReview) {
		this.qcReview = qcReview;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="REVIEW_SCOPE_ID", nullable=false)
	public ReviewScopeRef getReviewScopeRef() {
		return this.reviewScopeRef;
	}

	public void setReviewScopeRef(ReviewScopeRef reviewScopeRef) {
		this.reviewScopeRef = reviewScopeRef;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="REVIEW_STATUS_ID", nullable=false)
	public ReviewStatusRef getReviewStatusRef() {
		return this.reviewStatusRef;
	}

	public void setReviewStatusRef(ReviewStatusRef reviewStatusRef) {
		this.reviewStatusRef = reviewStatusRef;
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
    @JoinColumn(name="SELECTION_REASON_ID", nullable=false)
	public SelectionReason getSelectionReason() {
		return this.selectionReason;
	}

	public void setSelectionReason(SelectionReason selectionReason) {
		this.selectionReason = selectionReason;
	}

    
    @Column(name="CASE_NUMBER", length=11)
	public String getCaseNumber() {
		return this.caseNumber;
	}

	public void setCaseNumber(String caseNumber) {
		this.caseNumber = caseNumber;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="ORIG_MIT_RQST_DT", length=23)
	public Date getOrigMitRqstDt() {
		return this.origMitRqstDt;
	}

	public void setOrigMitRqstDt(Date origMitRqstDt) {
		this.origMitRqstDt = origMitRqstDt;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="ORIG_ASSIGN_DT", length=23)
	public Date getOrigAssignDt() {
		return this.origAssignDt;
	}

	public void setOrigAssignDt(Date origAssignDt) {
		this.origAssignDt = origAssignDt;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="RVW_COMPLTD_DT", length=23)
	public Date getRvwCompltdDt() {
		return this.rvwCompltdDt;
	}

	public void setRvwCompltdDt(Date rvwCompltdDt) {
		this.rvwCompltdDt = rvwCompltdDt;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="RVW_STARTED_DT_TM", length=23)
	public Date getRvwStartedDtTm() {
		return this.rvwStartedDtTm;
	}

	public void setRvwStartedDtTm(Date rvwStartedDtTm) {
		this.rvwStartedDtTm = rvwStartedDtTm;
	}

    
    @Column(name="CREATED_BY", length=6)
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
    @Column(name="CREATED_TS", length=23)
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

		
		
    
    @Column(name="REVIEW_REFERENCE_ID", insertable=false, updatable=false, length=20)
	public String getReviewReferenceId() {
		return this.reviewReferenceId;
	}

	public void setReviewReferenceId(String reviewReferenceId) {
		this.reviewReferenceId = reviewReferenceId;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="DUE_DATE", length=23)
	public Date getDueDate() {
		return this.dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

    
    @Column(name="QC_OUTCOME_CD", length=2)
	public String getQcOutcomeCd() {
		return this.qcOutcomeCd;
	}

	public void setQcOutcomeCd(String qcOutcomeCd) {
		this.qcOutcomeCd = qcOutcomeCd;
	}

    
    @Column(name="MRB_REFERRAL_IND", nullable=false, length=1)
	public char getMrbReferralInd() {
		return this.mrbReferralInd;
	}

	public void setMrbReferralInd(char mrbReferralInd) {
		this.mrbReferralInd = mrbReferralInd;
	}

	@Column(name="LAST_LENDER_NAME", length=202)
	public String getLastLenderName() {
		return this.lastLenderName;
	}

	public void setLastLenderName(String lastLenderName) {
		this.lastLenderName = lastLenderName;
	}
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="review")
	public Set<ReviewReferral> getReviewReferrals() {
		return this.reviewReferrals;
	}

	public void setReviewReferrals(Set<ReviewReferral> reviewReferrals) {
		this.reviewReferrals = reviewReferrals;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="review")
	public Set<RvwLvlDefect> getRvwLvlDefects() {
		return this.rvwLvlDefects;
	}

	public void setRvwLvlDefects(Set<RvwLvlDefect> rvwLvlDefects) {
		this.rvwLvlDefects = rvwLvlDefects;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="review")
	public Set<ReviewLevel> getReviewLevels() {
		return this.reviewLevels;
	}

	public void setReviewLevels(Set<ReviewLevel> reviewLevels) {
		this.reviewLevels = reviewLevels;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="review")
	public Set<Document> getDocuments() {
		return this.documents;
	}

	public void setDocuments(Set<Document> documents) {
		this.documents = documents;
	}
	
	@Column(name="MANAGEMENT_DECISION_IND", nullable=false, length=1)
	public char getManagementDecisionInd() {
		return managementDecisionInd;
	}

	public void setManagementDecisionInd(char managementDecisionInd) {
		this.managementDecisionInd = managementDecisionInd;
	}


}
