//DO NOT MODIFY: generated for HUD LRS 
package gov.hud.lrs.common.entity;

import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="QATREE_QSTN_RULES")
@SuppressWarnings("serial")
public class QatreeQstnRules implements java.io.Serializable {
	private QatreeQstnRulesId id;
	private ReviewTypeRef reviewTypeRef;
	private SelectionReason selectionReason;
	private String qatreeId;
	private String createdBy;
	private String updatedBy;
	private Date createdTs;
	private Date updatedTs;

	public QatreeQstnRules() {
	}

    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="reviewTypeId", column=@Column(name="REVIEW_TYPE_ID", nullable=false, length=36) ), 
        @AttributeOverride(name="selectionReasonId", column=@Column(name="SELECTION_REASON_ID", nullable=false, length=36) ) } )
	public QatreeQstnRulesId getId() {
		return this.id;
	}

	public void setId(QatreeQstnRulesId id) {
		this.id = id;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="REVIEW_TYPE_ID", nullable=false, insertable=false, updatable=false)
	public ReviewTypeRef getReviewTypeRef() {
		return this.reviewTypeRef;
	}

	public void setReviewTypeRef(ReviewTypeRef reviewTypeRef) {
		this.reviewTypeRef = reviewTypeRef;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="SELECTION_REASON_ID", nullable=false, insertable=false, updatable=false)
	public SelectionReason getSelectionReason() {
		return this.selectionReason;
	}

	public void setSelectionReason(SelectionReason selectionReason) {
		this.selectionReason = selectionReason;
	}

    
    @Column(name="QATREE_ID", length=36)
	public String getQatreeId() {
		return this.qatreeId;
	}

	public void setQatreeId(String qatreeId) {
		this.qatreeId = qatreeId;
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
