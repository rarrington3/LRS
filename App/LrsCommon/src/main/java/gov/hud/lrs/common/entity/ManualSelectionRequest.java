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
@Table(name="MANUAL_SELECTION_REQUEST")
@SuppressWarnings("serial")
public class ManualSelectionRequest implements java.io.Serializable {
	private String selectionRequestId;
	private ReviewLocation reviewLocation;
	private ReviewTypeRef reviewTypeRef;
	private SelectionReason selectionReason;
	private SelectionRequest selectionRequest;
	private SelectionSubReasonRef selectionSubReasonRef;
	private String createdBy;
	private Date createdTs;
	private String updatedBy;
	private Date updatedTs;

	public ManualSelectionRequest() {
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

@OneToOne(fetch=FetchType.LAZY)@PrimaryKeyJoinColumn
	public SelectionRequest getSelectionRequest() {
		return this.selectionRequest;
	}

	public void setSelectionRequest(SelectionRequest selectionRequest) {
		this.selectionRequest = selectionRequest;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="SELECTION_SUB_REASON_ID")
	public SelectionSubReasonRef getSelectionSubReasonRef() {
		return this.selectionSubReasonRef;
	}

	public void setSelectionSubReasonRef(SelectionSubReasonRef selectionSubReasonRef) {
		this.selectionSubReasonRef = selectionSubReasonRef;
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
