//DO NOT MODIFY: generated for HUD LRS 
package gov.hud.lrs.common.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class DictMetadataFieldModelId implements java.io.Serializable {
	private String entityName;
	private String fieldName;
	private String qaModelId;

	public DictMetadataFieldModelId() {
	}


    @Column(name="ENTITY_NAME", nullable=false, length=60)
	public String getEntityName() {
		return this.entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}


    @Column(name="FIELD_NAME", nullable=false, length=60)
	public String getFieldName() {
		return this.fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}


    @Column(name="QA_MODEL_ID", nullable=false, length=36)
	public String getQaModelId() {
		return this.qaModelId;
	}

	public void setQaModelId(String qaModelId) {
		this.qaModelId = qaModelId;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		} else if ((other == null)) {
			return false;
		} else if (!(other instanceof DictMetadataFieldModelId)) {
			return false;
		}
		DictMetadataFieldModelId castOther = (DictMetadataFieldModelId)other; 
		return ( (this.getEntityName()==castOther.getEntityName()) || ( this.getEntityName()!=null && castOther.getEntityName()!=null && this.getEntityName().equals(castOther.getEntityName()) ) )
 && ( (this.getFieldName()==castOther.getFieldName()) || ( this.getFieldName()!=null && castOther.getFieldName()!=null && this.getFieldName().equals(castOther.getFieldName()) ) )
 && ( (this.getQaModelId()==castOther.getQaModelId()) || ( this.getQaModelId()!=null && castOther.getQaModelId()!=null && this.getQaModelId().equals(castOther.getQaModelId()) ) );
	}

	public int hashCode() {
		int result = 17;
		result = 37 * result + ( getEntityName() == null ? 0 : this.getEntityName().hashCode() );
		result = 37 * result + ( getFieldName() == null ? 0 : this.getFieldName().hashCode() );
		result = 37 * result + ( getQaModelId() == null ? 0 : this.getQaModelId().hashCode() );
		return result;
	}
}
