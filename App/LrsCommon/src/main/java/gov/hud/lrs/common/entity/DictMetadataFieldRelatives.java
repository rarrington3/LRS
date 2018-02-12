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
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="DICT_METADATA_FIELD_RELATIVES")
@SuppressWarnings("serial")
public class DictMetadataFieldRelatives implements java.io.Serializable {
	private DictMetadataFieldRelativesId id;
	private DictMetadataField dictMetadataField;
	private String createdBy;
	private String updatedBy;
	private Date createdTs;
	private Date updatedTs;
	private String relatedDbColumn;

	public DictMetadataFieldRelatives() {
	}

    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="entityName", column=@Column(name="ENTITY_NAME", nullable=false, length=60) ), 
        @AttributeOverride(name="fieldName", column=@Column(name="FIELD_NAME", nullable=false, length=60) ), 
        @AttributeOverride(name="relationshipType", column=@Column(name="RELATIONSHIP_TYPE", nullable=false, length=16) ) } )
	public DictMetadataFieldRelativesId getId() {
		return this.id;
	}

	public void setId(DictMetadataFieldRelativesId id) {
		this.id = id;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumns( { 
        @JoinColumn(name="ENTITY_NAME", referencedColumnName="ENTITY_NAME", nullable=false, insertable=false, updatable=false), 
        @JoinColumn(name="FIELD_NAME", referencedColumnName="FIELD_NAME", nullable=false, insertable=false, updatable=false) } )
	public DictMetadataField getDictMetadataField() {
		return this.dictMetadataField;
	}

	public void setDictMetadataField(DictMetadataField dictMetadataField) {
		this.dictMetadataField = dictMetadataField;
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

    
    @Column(name="RELATED_DB_COLUMN", length=30)
	public String getRelatedDbColumn() {
		return this.relatedDbColumn;
	}

	public void setRelatedDbColumn(String relatedDbColumn) {
		this.relatedDbColumn = relatedDbColumn;
	}

}
