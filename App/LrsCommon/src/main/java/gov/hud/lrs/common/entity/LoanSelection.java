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
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name="LOAN_SELECTION")
@SuppressWarnings("serial")
public class LoanSelection implements java.io.Serializable {
	private String selectionId;
	private Batch batch;
	private Lender lender;
	private LoanSelectionCaseSummary loanSelectionCaseSummary;
	private LoanSelectionStatusRef loanSelectionStatusRef;
	private ProductTypeRef productTypeRef;
	private Review qcReview;
	private ReviewLocation preferredReviewLocation;
	private ReviewLocation reviewLocation;
	private ReviewTypeRef reviewTypeRef;
	private SelectionReason selectionReason;
	private ReviewScopeRef reviewScopeRef;
	private String caseNumber;
	private Date distributionDt;
	private Date dueDate;
	private Date receivedDt;
	private String rqstDocsSourceCd;
	private Date requestedDtTm;
	private Date selectionDt;
	private String borrName;
	private Date csEstabDt;
	private Date endrsmntDt;
	private String propAddr1;
	private SelectionRequest selectionRequest;
	private String createdBy;
	private String updatedBy;
	private Date createdTs;
	private Date updatedTs;
	private Character autoSelectionIndicator;
	private Set<Review> reviews = new HashSet<Review>(0);
	private Set<BinderRequest> binderRequests = new HashSet<BinderRequest>(0);
	private Set<ReviewProcessException> reviewProcessExceptions = new HashSet<ReviewProcessException>(0);

	public LoanSelection() {
	}

    @GenericGenerator(name="generator", strategy="foreign", parameters=@Parameter(name="property", value="loanSelectionCaseSummary"))@Id @GeneratedValue(generator="generator")

    @Column(name="SELECTION_ID", unique=true, nullable=false, length=36)
	public String getSelectionId() {
		return this.selectionId;
	}

	public void setSelectionId(String selectionId) {
		this.selectionId = selectionId;
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
    @JoinColumn(name="MTGEE5", nullable=false)
	public Lender getLender() {
		return this.lender;
	}

	public void setLender(Lender lender) {
		this.lender = lender;
	}

	@OneToOne(fetch=FetchType.LAZY, optional=false)@PrimaryKeyJoinColumn
	public LoanSelectionCaseSummary getLoanSelectionCaseSummary() {
		return this.loanSelectionCaseSummary;
	}

	public void setLoanSelectionCaseSummary(LoanSelectionCaseSummary loanSelectionCaseSummary) {
		this.loanSelectionCaseSummary = loanSelectionCaseSummary;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="LOAN_SELECTION_STATUS_ID", nullable=false)
	public LoanSelectionStatusRef getLoanSelectionStatusRef() {
		return this.loanSelectionStatusRef;
	}

	public void setLoanSelectionStatusRef(LoanSelectionStatusRef loanSelectionStatusRef) {
		this.loanSelectionStatusRef = loanSelectionStatusRef;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="PRODUCT_TYPE_ID", nullable=false)
	public ProductTypeRef getProductTypeRef() {
		return this.productTypeRef;
	}

	public void setProductTypeRef(ProductTypeRef productTypeRef) {
		this.productTypeRef = productTypeRef;
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
    @JoinColumn(name="PREFERRED_REVIEW_LOCATION_ID")
	public ReviewLocation getPreferredReviewLocation() {
		return this.preferredReviewLocation;
	}

	public void setPreferredReviewLocation(ReviewLocation preferredReviewLocation) {
		this.preferredReviewLocation = preferredReviewLocation;
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

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="REVIEW_SCOPE_ID", nullable=false)
	public ReviewScopeRef getReviewScopeRef() {
		return this.reviewScopeRef;
	}

	public void setReviewScopeRef(ReviewScopeRef reviewScopeRef) {
		this.reviewScopeRef = reviewScopeRef;
	}

    @Column(name="CASE_NUMBER", nullable=false, length=11)
	public String getCaseNumber() {
		return this.caseNumber;
	}

	public void setCaseNumber(String caseNumber) {
		this.caseNumber = caseNumber;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="DISTRIBUTION_DT", length=23)
	public Date getDistributionDt() {
		return this.distributionDt;
	}

	public void setDistributionDt(Date distributionDt) {
		this.distributionDt = distributionDt;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="DUE_DATE", length=23)
	public Date getDueDate() {
		return this.dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="RECEIVED_DT", length=23)
	public Date getReceivedDt() {
		return this.receivedDt;
	}

	public void setReceivedDt(Date receivedDt) {
		this.receivedDt = receivedDt;
	}


    @Column(name="RQST_DOCS_SOURCE_CD", length=16)
	public String getRqstDocsSourceCd() {
		return this.rqstDocsSourceCd;
	}

	public void setRqstDocsSourceCd(String rqstDocsSourceCd) {
		this.rqstDocsSourceCd = rqstDocsSourceCd;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="REQUESTED_DT_TM", length=23)
	public Date getRequestedDtTm() {
		return this.requestedDtTm;
	}

	public void setRequestedDtTm(Date requestedDtTm) {
		this.requestedDtTm = requestedDtTm;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="SELECTION_DT", length=23)
	public Date getSelectionDt() {
		return this.selectionDt;
	}

	public void setSelectionDt(Date selectionDt) {
		this.selectionDt = selectionDt;
	}
	
	@Column(name="BORR_NAME", length=100)
	public String getBorrName() {
		return borrName;
	}

	public void setBorrName(String borrName) {
		this.borrName = borrName;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CS_ESTAB_DT", length=23)
	public Date getCsEstabDt() {
		return this.csEstabDt;
	}

	public void setCsEstabDt(Date csEstabDt) {
		this.csEstabDt = csEstabDt;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="ENDRSMNT_DT", length=23)
	public Date getEndrsmntDt() {
		return this.endrsmntDt;
	}

	public void setEndrsmntDt(Date endrsmntDt) {
		this.endrsmntDt = endrsmntDt;
	}
	
	@Column(name="PROP_ADDR_1", length=100)
	public String getPropAddr1() {
		return this.propAddr1;
	}

	public void setPropAddr1(String propAddr1) {
		this.propAddr1 = propAddr1;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="SELECTION_REQUEST_ID", nullable=false)
	public SelectionRequest getSelectionRequest() {
		return this.selectionRequest;
	}

	public void setSelectionRequest(SelectionRequest selectionRequest) {
		this.selectionRequest = selectionRequest;
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

	@Column(name="AUTO_SELECTION_INDICATOR", nullable=false, length=1)
	public Character getAutoSelectionIndicator() {
		return this.autoSelectionIndicator;
	}

	public void setAutoSelectionIndicator(Character autoSelectionIndicator) {
		this.autoSelectionIndicator = autoSelectionIndicator;
	}
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="loanSelection")
	public Set<Review> getReviews() {
		return this.reviews;
	}

	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="loanSelection")
	public Set<BinderRequest> getBinderRequests() {
		return this.binderRequests;
	}

	public void setBinderRequests(Set<BinderRequest> binderRequests) {
		this.binderRequests = binderRequests;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="loanSelection")
	public Set<ReviewProcessException> getReviewProcessExceptions() {
		return this.reviewProcessExceptions;
	}

	public void setReviewProcessExceptions(Set<ReviewProcessException> reviewProcessExceptions) {
		this.reviewProcessExceptions = reviewProcessExceptions;
	}

}
