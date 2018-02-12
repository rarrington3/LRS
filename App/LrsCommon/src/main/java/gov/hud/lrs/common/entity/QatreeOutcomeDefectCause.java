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
@Table(name="QATREE_OUTCOME_DEFECT_CAUSE")
@SuppressWarnings("serial")
public class QatreeOutcomeDefectCause implements java.io.Serializable {
	private QatreeOutcomeDefectCauseId id;
	private DefectCause defectCause;
	private QatreeOutcome qatreeOutcome;
	private String createdBy;
	private String updatedBy;
	private Date createdTs;
	private Date updatedTs;

	public QatreeOutcomeDefectCause() {
	}

    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="qatreeOutcomeId", column=@Column(name="QATREE_OUTCOME_ID", nullable=false, length=36) ), 
        @AttributeOverride(name="defectCauseId", column=@Column(name="DEFECT_CAUSE_ID", nullable=false, length=36) ) } )
	public QatreeOutcomeDefectCauseId getId() {
		return this.id;
	}

	public void setId(QatreeOutcomeDefectCauseId id) {
		this.id = id;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="DEFECT_CAUSE_ID", nullable=false, insertable=false, updatable=false)
	public DefectCause getDefectCause() {
		return this.defectCause;
	}

	public void setDefectCause(DefectCause defectCause) {
		this.defectCause = defectCause;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="QATREE_OUTCOME_ID", nullable=false, insertable=false, updatable=false)
	public QatreeOutcome getQatreeOutcome() {
		return this.qatreeOutcome;
	}

	public void setQatreeOutcome(QatreeOutcome qatreeOutcome) {
		this.qatreeOutcome = qatreeOutcome;
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
