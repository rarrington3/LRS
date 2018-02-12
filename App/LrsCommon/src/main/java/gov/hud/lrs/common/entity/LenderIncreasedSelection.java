//DO NOT MODIFY: generated for HUD LRS 
package gov.hud.lrs.common.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name="LENDER_INCREASED_SELECTION")
@SuppressWarnings("serial")
public class LenderIncreasedSelection implements java.io.Serializable {
	private String mtgee5;
	private Lender lender;
	private String comments;
	private Date effectiveDate;
	private Integer targetFwdPct;
	private Integer targetHecmPct;
	private String createdBy;
	private String updatedBy;
	private Date createdTs;
	private Date updatedTs;

	public LenderIncreasedSelection() {
	}

    @GenericGenerator(name="generator", strategy="foreign", parameters=@Parameter(name="property", value="lender"))@Id @GeneratedValue(generator="generator")
    
    @Column(name="MTGEE5", unique=true, nullable=false, length=5)
	public String getMtgee5() {
		return this.mtgee5;
	}

	public void setMtgee5(String mtgee5) {
		this.mtgee5 = mtgee5;
	}

@OneToOne(fetch=FetchType.LAZY)@PrimaryKeyJoinColumn
	public Lender getLender() {
		return this.lender;
	}

	public void setLender(Lender lender) {
		this.lender = lender;
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

    
    @Column(name="TARGET_FWD_PCT")
	public Integer getTargetFwdPct() {
		return this.targetFwdPct;
	}

	public void setTargetFwdPct(Integer targetFwdPct) {
		this.targetFwdPct = targetFwdPct;
	}

    
    @Column(name="TARGET_HECM_PCT")
	public Integer getTargetHecmPct() {
		return this.targetHecmPct;
	}

	public void setTargetHecmPct(Integer targetHecmPct) {
		this.targetHecmPct = targetHecmPct;
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
