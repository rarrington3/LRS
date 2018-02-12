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
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="LENDER_REQUEST")
@SuppressWarnings("serial")
public class LenderRequest implements java.io.Serializable {

	private String lenderRequestId;
	private LenderRequestStatusRef lenderRequestStatusRef;
	private ReviewLevel reviewLevel;
	private Date requestedDate;
	private Date dueDate;
	private String createdBy;
	private String updatedBy;
	private Date createdTs;
	private Date updatedTs;

	public LenderRequest() {
	}

	@GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")

	@Column(name="LENDER_REQUEST_ID", unique=true, nullable=false, length=36)
	public String getLenderRequestId() {
		return this.lenderRequestId;
	}

	public void setLenderRequestId(String lenderRequestId) {
		this.lenderRequestId = lenderRequestId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="LENDER_REQUEST_STATUS_ID", nullable=false)
	public LenderRequestStatusRef getLenderRequestStatusRef() {
		return this.lenderRequestStatusRef;
	}

	public void setLenderRequestStatusRef(LenderRequestStatusRef lenderRequestStatusRef) {
		this.lenderRequestStatusRef = lenderRequestStatusRef;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="REVIEW_LEVEL_ID", nullable=false)
	public ReviewLevel getReviewLevel() {
		return this.reviewLevel;
	}

	public void setReviewLevel(ReviewLevel reviewLevel) {
		this.reviewLevel = reviewLevel;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="REQUESTED_DATE", nullable=false, length=23)
	public Date getRequestedDate() {
		return this.requestedDate;
	}

	public void setRequestedDate(Date requestedDate) {
		this.requestedDate = requestedDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DUE_DATE", nullable=false, length=23)
	public Date getDueDate() {
		return this.dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
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

}
