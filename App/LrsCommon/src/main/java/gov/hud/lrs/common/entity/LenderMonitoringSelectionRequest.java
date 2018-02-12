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
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name="LENDER_MONITORING_SELECTION_REQUEST")
@SuppressWarnings("serial")
public class LenderMonitoringSelectionRequest implements java.io.Serializable {
	private String selectionRequestId;
	private FileDeliveryLocationRef fileDeliveryLocationRef;
	private Lender lender;
	private LoanTypeRef loanTypeRef;
	private ReviewLocation reviewLocation;
	private ReviewTypeRef reviewTypeRef;
	private SelectionRequest selectionRequest;
	private Date siteVisitDt;
	private String productTypeId;
	private Integer caseCount;
	private Date startDt;
	private Date endDt;
	private String createdBy;
	private Date createdTs;
	private String updatedBy;
	private Date updatedTs;

	public LenderMonitoringSelectionRequest() {
	}

    @GenericGenerator(name="generator", strategy="foreign", parameters=@Parameter(name="property", value="selectionRequest"))@Id @GeneratedValue(generator="generator")
    
    @Column(name="SELECTION_REQUEST_ID", unique=true, nullable=false, length=36)
	public String getSelectionRequestId() {
		return this.selectionRequestId;
	}

	public void setSelectionRequestId(String selectionRequestId) {
		this.selectionRequestId = selectionRequestId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="FILE_DELIVERY_LOCATION_ID")
	public FileDeliveryLocationRef getFileDeliveryLocationRef() {
		return this.fileDeliveryLocationRef;
	}

	public void setFileDeliveryLocationRef(FileDeliveryLocationRef fileDeliveryLocationRef) {
		this.fileDeliveryLocationRef = fileDeliveryLocationRef;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="LENDER_ID")
	public Lender getLender() {
		return this.lender;
	}

	public void setLender(Lender lender) {
		this.lender = lender;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="LOAN_TYPE_REF_ID")
	public LoanTypeRef getLoanTypeRef() {
		return this.loanTypeRef;
	}

	public void setLoanTypeRef(LoanTypeRef loanTypeRef) {
		this.loanTypeRef = loanTypeRef;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="REVIEW_LOCATION_ID", nullable=false)
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

@OneToOne(fetch=FetchType.LAZY)@PrimaryKeyJoinColumn
	public SelectionRequest getSelectionRequest() {
		return this.selectionRequest;
	}

	public void setSelectionRequest(SelectionRequest selectionRequest) {
		this.selectionRequest = selectionRequest;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="SITE_VISIT_DT", length=23)
	public Date getSiteVisitDt() {
		return this.siteVisitDt;
	}

	public void setSiteVisitDt(Date siteVisitDt) {
		this.siteVisitDt = siteVisitDt;
	}

    
    @Column(name="PRODUCT_TYPE_ID", length=36)
	public String getProductTypeId() {
		return this.productTypeId;
	}

	public void setProductTypeId(String productTypeId) {
		this.productTypeId = productTypeId;
	}

    
    @Column(name="CASE_COUNT")
	public Integer getCaseCount() {
		return this.caseCount;
	}

	public void setCaseCount(Integer caseCount) {
		this.caseCount = caseCount;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="START_DT", nullable=false, length=23)
	public Date getStartDt() {
		return this.startDt;
	}

	public void setStartDt(Date startDt) {
		this.startDt = startDt;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="END_DT", nullable=false, length=23)
	public Date getEndDt() {
		return this.endDt;
	}

	public void setEndDt(Date endDt) {
		this.endDt = endDt;
	}

    
    @Column(name="CREATED_BY", nullable=false, length=6)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATED_TS", nullable=false, length=23)
	public Date getCreatedTs() {
		return this.createdTs;
	}

	public void setCreatedTs(Date createdTs) {
		this.createdTs = createdTs;
	}

    
    @Column(name="UPDATED_BY", length=6)
	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="UPDATED_TS", length=23)
	public Date getUpdatedTs() {
		return this.updatedTs;
	}

	public void setUpdatedTs(Date updatedTs) {
		this.updatedTs = updatedTs;
	}

}
