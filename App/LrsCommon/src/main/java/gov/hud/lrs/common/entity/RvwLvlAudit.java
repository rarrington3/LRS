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
@Table(name="RVW_LVL_AUDIT")
@SuppressWarnings("serial")
public class RvwLvlAudit implements java.io.Serializable {
	private RvwLvlAuditId id;
	private ReviewLevel reviewLevel;
	private Date indemDate;
	private String indemType;
	private String notes;
	private String rvwrPrsnnlId;
	private String createdBy;
	private String updatedBy;
	private Date createdTs;
	private Date updatedTs;

	public RvwLvlAudit() {
	}

    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="auditTmstmp", column=@Column(name="AUDIT_TMSTMP", nullable=false, length=23) ), 
        @AttributeOverride(name="reviewLvlId", column=@Column(name="REVIEW_LVL_ID", nullable=false, length=36) ) } )
	public RvwLvlAuditId getId() {
		return this.id;
	}

	public void setId(RvwLvlAuditId id) {
		this.id = id;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="REVIEW_LVL_ID", nullable=false, insertable=false, updatable=false)
	public ReviewLevel getReviewLevel() {
		return this.reviewLevel;
	}

	public void setReviewLevel(ReviewLevel reviewLevel) {
		this.reviewLevel = reviewLevel;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="INDEM_DATE", length=23)
	public Date getIndemDate() {
		return this.indemDate;
	}

	public void setIndemDate(Date indemDate) {
		this.indemDate = indemDate;
	}

    
    @Column(name="INDEM_TYPE", length=16)
	public String getIndemType() {
		return this.indemType;
	}

	public void setIndemType(String indemType) {
		this.indemType = indemType;
	}

    
    @Column(name="NOTES")
	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

    
    @Column(name="RVWR_PRSNNL_ID", length=36)
	public String getRvwrPrsnnlId() {
		return this.rvwrPrsnnlId;
	}

	public void setRvwrPrsnnlId(String rvwrPrsnnlId) {
		this.rvwrPrsnnlId = rvwrPrsnnlId;
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
