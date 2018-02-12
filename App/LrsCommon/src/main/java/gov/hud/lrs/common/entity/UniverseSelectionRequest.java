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
@Table(name="UNIVERSE_SELECTION_REQUEST")
@SuppressWarnings("serial")
public class UniverseSelectionRequest implements java.io.Serializable {
	private String selectionRequestId;
	private Job job;
	private SelectionRequest selectionRequest;
	private UniverseRef universeRef;
	private Date startDt;
	private Date endDt;
	private String createdBy;
	private Date createdTs;
	private String updatedBy;
	private Date updatedTs;
	private Date completedTs;

	public UniverseSelectionRequest() {
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
    @JoinColumn(name="JOB_ID", nullable=false)
	public Job getJob() {
		return this.job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

@OneToOne(fetch=FetchType.LAZY)@PrimaryKeyJoinColumn
	public SelectionRequest getSelectionRequest() {
		return this.selectionRequest;
	}

	public void setSelectionRequest(SelectionRequest selectionRequest) {
		this.selectionRequest = selectionRequest;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="UNIVERSE_ID", nullable=false)
	public UniverseRef getUniverseRef() {
		return this.universeRef;
	}

	public void setUniverseRef(UniverseRef universeRef) {
		this.universeRef = universeRef;
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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="COMPLETED_TS", length=23)
	public Date getCompletedTs() {
		return this.completedTs;
	}

	public void setCompletedTs(Date completedTs) {
		this.completedTs = completedTs;
	}

}
