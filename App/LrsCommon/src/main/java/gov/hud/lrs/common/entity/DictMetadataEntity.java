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
@Table(name="DICT_METADATA_ENTITY")
@SuppressWarnings("serial")
public class DictMetadataEntity implements java.io.Serializable {
	private String entityName;
	private String dbViewOrTable;
	private String description;
	private String createdBy;
	private String updatedBy;
	private Date createdTs;
	private Date updatedTs;

	public DictMetadataEntity() {
	}

    @Id 
    
    @Column(name="ENTITY_NAME", unique=true, nullable=false, length=60)
	public String getEntityName() {
		return this.entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

    
    @Column(name="DB_VIEW_OR_TABLE", nullable=false, length=128)
	public String getDbViewOrTable() {
		return this.dbViewOrTable;
	}

	public void setDbViewOrTable(String dbViewOrTable) {
		this.dbViewOrTable = dbViewOrTable;
	}

    
    @Column(name="DESCRIPTION")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
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
