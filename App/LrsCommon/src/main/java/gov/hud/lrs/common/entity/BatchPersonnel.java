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
@Table(name="BATCH_PERSONNEL")
@SuppressWarnings("serial")
public class BatchPersonnel implements java.io.Serializable {
	private BatchPersonnelId id;
	private Batch batch;
	private Personnel personnel;
	private String createdBy;
	private String updatedBy;
	private Date createdTs;
	private Date updatedTs;

	public BatchPersonnel() {
	}

    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="batchId", column=@Column(name="BATCH_ID", nullable=false, length=36) ), 
        @AttributeOverride(name="personnelId", column=@Column(name="PERSONNEL_ID", nullable=false, length=36) ) } )
	public BatchPersonnelId getId() {
		return this.id;
	}

	public void setId(BatchPersonnelId id) {
		this.id = id;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="BATCH_ID", nullable=false, insertable=false, updatable=false)
	public Batch getBatch() {
		return this.batch;
	}

	public void setBatch(Batch batch) {
		this.batch = batch;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="PERSONNEL_ID", nullable=false, insertable=false, updatable=false)
	public Personnel getPersonnel() {
		return this.personnel;
	}

	public void setPersonnel(Personnel personnel) {
		this.personnel = personnel;
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
