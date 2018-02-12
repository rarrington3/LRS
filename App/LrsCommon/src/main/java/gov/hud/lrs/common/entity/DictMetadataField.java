//DO NOT MODIFY: generated for HUD LRS 
package gov.hud.lrs.common.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="DICT_METADATA_FIELD", uniqueConstraints = @UniqueConstraint(columnNames={"ENTITY_NAME", "DISPLAY_NAME"}))
@SuppressWarnings("serial")
public class DictMetadataField implements java.io.Serializable {
	private DictMetadataFieldId id;
	private String dbColumn;
	private String description;
	private String createdBy;
	private String updatedBy;
	private Date createdTs;
	private Date updatedTs;
	private Character userEditableInd;
	private String uiControlType;
	private Long fieldMinValue;
	private Long fieldMaxValue;
	private Integer displayOrder;
	private String displayName;
	private Set<DictMetadataFieldCondition> conditionDictMetadataFieldConditions = new HashSet<DictMetadataFieldCondition>(0);
	private Set<DictMetadataFieldRelatives> dictMetadataFieldRelatives = new HashSet<DictMetadataFieldRelatives>(0);
	private Set<DictMetadataFieldCondition> dictMetadataFieldConditions = new HashSet<DictMetadataFieldCondition>(0);

	public DictMetadataField() {
	}

    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="entityName", column=@Column(name="ENTITY_NAME", nullable=false, length=60) ), 
        @AttributeOverride(name="fieldName", column=@Column(name="FIELD_NAME", nullable=false, length=60) ) } )
	public DictMetadataFieldId getId() {
		return this.id;
	}

	public void setId(DictMetadataFieldId id) {
		this.id = id;
	}

    
    @Column(name="DB_COLUMN", nullable=false, length=128)
	public String getDbColumn() {
		return this.dbColumn;
	}

	public void setDbColumn(String dbColumn) {
		this.dbColumn = dbColumn;
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

    
    @Column(name="USER_EDITABLE_IND", length=1)
	public Character getUserEditableInd() {
		return this.userEditableInd;
	}

	public void setUserEditableInd(Character userEditableInd) {
		this.userEditableInd = userEditableInd;
	}

    
    @Column(name="UI_CONTROL_TYPE", length=20)
	public String getUiControlType() {
		return this.uiControlType;
	}

	public void setUiControlType(String uiControlType) {
		this.uiControlType = uiControlType;
	}

    
    @Column(name="FIELD_MIN_VALUE", precision=10, scale=0)
	public Long getFieldMinValue() {
		return this.fieldMinValue;
	}

	public void setFieldMinValue(Long fieldMinValue) {
		this.fieldMinValue = fieldMinValue;
	}

    
    @Column(name="FIELD_MAX_VALUE", precision=10, scale=0)
	public Long getFieldMaxValue() {
		return this.fieldMaxValue;
	}

	public void setFieldMaxValue(Long fieldMaxValue) {
		this.fieldMaxValue = fieldMaxValue;
	}

    
    @Column(name="DISPLAY_ORDER")
	public Integer getDisplayOrder() {
		return this.displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

    
    @Column(name="DISPLAY_NAME", length=60)
	public String getDisplayName() {
		return this.displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="conditionDictMetadataField")
	public Set<DictMetadataFieldCondition> getConditionDictMetadataFieldConditions() {
		return this.conditionDictMetadataFieldConditions;
	}

	public void setConditionDictMetadataFieldConditions(Set<DictMetadataFieldCondition> conditionDictMetadataFieldConditions) {
		this.conditionDictMetadataFieldConditions = conditionDictMetadataFieldConditions;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="dictMetadataField")
	public Set<DictMetadataFieldRelatives> getDictMetadataFieldRelatives() {
		return this.dictMetadataFieldRelatives;
	}

	public void setDictMetadataFieldRelatives(Set<DictMetadataFieldRelatives> dictMetadataFieldRelatives) {
		this.dictMetadataFieldRelatives = dictMetadataFieldRelatives;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="dictMetadataField")
	public Set<DictMetadataFieldCondition> getDictMetadataFieldConditions() {
		return this.dictMetadataFieldConditions;
	}

	public void setDictMetadataFieldConditions(Set<DictMetadataFieldCondition> dictMetadataFieldConditions) {
		this.dictMetadataFieldConditions = dictMetadataFieldConditions;
	}

}
