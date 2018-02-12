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
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="REVIEW_PROCESS_EXCEPTION")
@SuppressWarnings("serial")
public class ReviewProcessException implements java.io.Serializable {
	private String reviewProcessExceptionId;
	private Batch batch;
	private LoanSelection loanSelection;
	private ReviewLevel reviewLevel;
	private ReviewProcessExceptionTypeRef reviewProcessExceptionTypeRef;
	private String createdBy;
	private String updatedBy;
	private Date createdTs;
	private Date updatedTs;
	private char resolvedInd;

	public ReviewProcessException() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")
    
    @Column(name="REVIEW_PROCESS_EXCEPTION_ID", unique=true, nullable=false, length=36)
	public String getReviewProcessExceptionId() {
		return this.reviewProcessExceptionId;
	}

	public void setReviewProcessExceptionId(String reviewProcessExceptionId) {
		this.reviewProcessExceptionId = reviewProcessExceptionId;
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
    @JoinColumn(name="SELECTION_ID")
	public LoanSelection getLoanSelection() {
		return this.loanSelection;
	}

	public void setLoanSelection(LoanSelection loanSelection) {
		this.loanSelection = loanSelection;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="REVIEW_LEVEL_ID")
	public ReviewLevel getReviewLevel() {
		return this.reviewLevel;
	}

	public void setReviewLevel(ReviewLevel reviewLevel) {
		this.reviewLevel = reviewLevel;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="REVIEW_PROCESS_EXCEPTION_TYPE_ID", nullable=false)
	public ReviewProcessExceptionTypeRef getReviewProcessExceptionTypeRef() {
		return this.reviewProcessExceptionTypeRef;
	}

	public void setReviewProcessExceptionTypeRef(ReviewProcessExceptionTypeRef reviewProcessExceptionTypeRef) {
		this.reviewProcessExceptionTypeRef = reviewProcessExceptionTypeRef;
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

    
    @Column(name="RESOLVED_IND", nullable=false, length=1)
	public char getResolvedInd() {
		return this.resolvedInd;
	}

	public void setResolvedInd(char resolvedInd) {
		this.resolvedInd = resolvedInd;
	}

}
