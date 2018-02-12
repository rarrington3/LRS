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
@Table(name="LENDER_SELF_REPORT_SELECTION_REQUEST_DEFECT")
@SuppressWarnings("serial")
public class LenderSelfReportSelectionRequestDefect implements java.io.Serializable {
	private LenderSelfReportSelectionRequestDefectId id;
	private Defect defect;
	private LenderSelfReportSelectionRequest lenderSelfReportSelectionRequest;
	private String createdBy;
	private Date createdTs;
	private String updatedBy;
	private Date updatedTs;

	public LenderSelfReportSelectionRequestDefect() {
	}

    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="selectionRequestId", column=@Column(name="SELECTION_REQUEST_ID", nullable=false, length=36) ), 
        @AttributeOverride(name="defectId", column=@Column(name="DEFECT_ID", nullable=false, length=36) ) } )
	public LenderSelfReportSelectionRequestDefectId getId() {
		return this.id;
	}

	public void setId(LenderSelfReportSelectionRequestDefectId id) {
		this.id = id;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="DEFECT_ID", nullable=false, insertable=false, updatable=false)
	public Defect getDefect() {
		return this.defect;
	}

	public void setDefect(Defect defect) {
		this.defect = defect;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="SELECTION_REQUEST_ID", nullable=false, insertable=false, updatable=false)
	public LenderSelfReportSelectionRequest getLenderSelfReportSelectionRequest() {
		return this.lenderSelfReportSelectionRequest;
	}

	public void setLenderSelfReportSelectionRequest(LenderSelfReportSelectionRequest lenderSelfReportSelectionRequest) {
		this.lenderSelfReportSelectionRequest = lenderSelfReportSelectionRequest;
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
