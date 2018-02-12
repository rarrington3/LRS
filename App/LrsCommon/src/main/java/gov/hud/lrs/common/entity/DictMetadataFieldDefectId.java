//DO NOT MODIFY: generated for HUD LRS 
package gov.hud.lrs.common.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class DictMetadataFieldDefectId implements java.io.Serializable {
	private String entityName;
	private String fieldName;
	private String defectId;

	public DictMetadataFieldDefectId() {
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


    @Column(name="DEFECT_ID", nullable=false, length=36)
	public String getDefectId() {
		return this.defectId;
	}

	public void setDefectId(String defectId) {
		this.defectId = defectId;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		} else if ((other == null)) {
			return false;
		} else if (!(other instanceof DictMetadataFieldDefectId)) {
			return false;
		}
		DictMetadataFieldDefectId castOther = (DictMetadataFieldDefectId)other; 
		return ( (this.getEntityName()==castOther.getEntityName()) || ( this.getEntityName()!=null && castOther.getEntityName()!=null && this.getEntityName().equals(castOther.getEntityName()) ) )
 && ( (this.getFieldName()==castOther.getFieldName()) || ( this.getFieldName()!=null && castOther.getFieldName()!=null && this.getFieldName().equals(castOther.getFieldName()) ) )
 && ( (this.getDefectId()==castOther.getDefectId()) || ( this.getDefectId()!=null && castOther.getDefectId()!=null && this.getDefectId().equals(castOther.getDefectId()) ) );
	}

	public int hashCode() {
		int result = 17;
		result = 37 * result + ( getEntityName() == null ? 0 : this.getEntityName().hashCode() );
		result = 37 * result + ( getFieldName() == null ? 0 : this.getFieldName().hashCode() );
		result = 37 * result + ( getDefectId() == null ? 0 : this.getDefectId().hashCode() );
		return result;
	}
}
