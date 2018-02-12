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
@Table(name="DICT_METADATA_FIELD_DEFECT")
@SuppressWarnings("serial")
public class DictMetadataFieldDefect implements java.io.Serializable {
	private DictMetadataFieldDefectId id;
	private Defect defect;
	private DictMetadataField dictMetadataField;
	private String defectCd;
	private String createdBy;
	private String updatedBy;
	private Date createdTs;
	private Date updatedTs;
	private Integer orderNumber;

	public DictMetadataFieldDefect() {
	}

    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="entityName", column=@Column(name="ENTITY_NAME", nullable=false, length=60) ), 
        @AttributeOverride(name="fieldName", column=@Column(name="FIELD_NAME", nullable=false, length=60) ), 
        @AttributeOverride(name="defectId", column=@Column(name="DEFECT_ID", nullable=false, length=36) ) } )
	public DictMetadataFieldDefectId getId() {
		return this.id;
	}

	public void setId(DictMetadataFieldDefectId id) {
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
    @JoinColumns( { 
        @JoinColumn(name="ENTITY_NAME", referencedColumnName="ENTITY_NAME", nullable=false, insertable=false, updatable=false), 
        @JoinColumn(name="FIELD_NAME", referencedColumnName="FIELD_NAME", nullable=false, insertable=false, updatable=false) } )
	public DictMetadataField getDictMetadataField() {
		return this.dictMetadataField;
	}

	public void setDictMetadataField(DictMetadataField dictMetadataField) {
		this.dictMetadataField = dictMetadataField;
	}

    
    @Column(name="DEFECT_CD", nullable=false, length=16)
	public String getDefectCd() {
		return this.defectCd;
	}

	public void setDefectCd(String defectCd) {
		this.defectCd = defectCd;
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

    
    @Column(name="ORDER_NUMBER")
	public Integer getOrderNumber() {
		return this.orderNumber;
	}

	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}

}
