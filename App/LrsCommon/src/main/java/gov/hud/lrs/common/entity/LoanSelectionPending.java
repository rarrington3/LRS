//DO NOT MODIFY: generated for HUD LRS
package gov.hud.lrs.common.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="LOAN_SELECTION_PENDING", uniqueConstraints = @UniqueConstraint(columnNames={"SELECTION_ID", "SELECTION_REASON_ID"}))
@SuppressWarnings("serial")
public class LoanSelectionPending implements java.io.Serializable {
	private String loanSelectionPendingId;
	private Batch batch;
	private Lender lender;
	private LoanSelectionCaseSummary loanSelectionCaseSummary;
	private ProductTypeRef productTypeRef;
	private Review qcReview;
	private ReviewLocation reviewLocation;
	private ReviewTypeRef reviewTypeRef;
	private SelectionReason selectionReason;
	private ReviewScopeRef reviewScopeRef;
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
	private String caseNumber;
	private Character autoSelectionIndicator;

	public LoanSelectionPending() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")

    @Column(name="LOAN_SELECTION_PENDING_ID", unique=true, nullable=false, length=36)
	public String getLoanSelectionPendingId() {
		return this.loanSelectionPendingId;
	}

	public void setLoanSelectionPendingId(String loanSelectionPendingId) {
		this.loanSelectionPendingId = loanSelectionPendingId;
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

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="SELECTION_ID", nullable=false)
	public LoanSelectionCaseSummary getLoanSelectionCaseSummary() {
		return this.loanSelectionCaseSummary;
	}

	public void setLoanSelectionCaseSummary(LoanSelectionCaseSummary loanSelectionCaseSummary) {
		this.loanSelectionCaseSummary = loanSelectionCaseSummary;
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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="SELECTION_DT", nullable=false, length=23)
	public Date getSelectionDt() {
		return this.selectionDt;
	}

	public void setSelectionDt(Date selectionDt) {
		this.selectionDt = selectionDt;
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


    @Column(name="CASE_NUMBER", nullable=false, length=11)
	public String getCaseNumber() {
		return this.caseNumber;
	}

	public void setCaseNumber(String caseNumber) {
		this.caseNumber = caseNumber;
	}

	@Column(name="AUTO_SELECTION_INDICATOR", nullable=false, length=1)
	public Character getAutoSelectionIndicator() {
		return this.autoSelectionIndicator;
	}

	public void setAutoSelectionIndicator(Character autoSelectionIndicator) {
		this.autoSelectionIndicator = autoSelectionIndicator;
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
}
