//DO NOT MODIFY: generated for HUD LRS 
package gov.hud.lrs.common.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="UNDERWRITER_INCREASED_SELECTION")
@SuppressWarnings("serial")
public class UnderwriterIncreasedSelection implements java.io.Serializable {
	private String underwriterId;
	private String comments;
	private Date effectiveDate;
	private Short fwdPercent;
	private Short hecmPercent;
	private String createdBy;
	private String updatedBy;
	private Date createdTs;
	private Date updatedTs;

	public UnderwriterIncreasedSelection() {
	}

    @Id 
    
    @Column(name="UNDERWRITER_ID", unique=true, nullable=false, length=16)
	public String getUnderwriterId() {
		return this.underwriterId;
	}

	public void setUnderwriterId(String underwriterId) {
		this.underwriterId = underwriterId;
	}

    
    @Column(name="COMMENTS")
	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="EFFECTIVE_DATE", length=23)
	public Date getEffectiveDate() {
		return this.effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

    
    @Column(name="FWD_PERCENT", precision=3, scale=0)
	public Short getFwdPercent() {
		return this.fwdPercent;
	}

	public void setFwdPercent(Short fwdPercent) {
		this.fwdPercent = fwdPercent;
	}

    
    @Column(name="HECM_PERCENT", precision=3, scale=0)
	public Short getHecmPercent() {
		return this.hecmPercent;
	}

	public void setHecmPercent(Short hecmPercent) {
		this.hecmPercent = hecmPercent;
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
