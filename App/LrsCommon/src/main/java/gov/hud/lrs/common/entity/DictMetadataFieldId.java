//DO NOT MODIFY: generated for HUD LRS 
package gov.hud.lrs.common.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class DictMetadataFieldId implements java.io.Serializable {
	private String entityName;
	private String fieldName;

	public DictMetadataFieldId() {
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

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		} else if ((other == null)) {
			return false;
		} else if (!(other instanceof DictMetadataFieldId)) {
			return false;
		}
		DictMetadataFieldId castOther = (DictMetadataFieldId)other; 
		return ( (this.getEntityName()==castOther.getEntityName()) || ( this.getEntityName()!=null && castOther.getEntityName()!=null && this.getEntityName().equals(castOther.getEntityName()) ) )
 && ( (this.getFieldName()==castOther.getFieldName()) || ( this.getFieldName()!=null && castOther.getFieldName()!=null && this.getFieldName().equals(castOther.getFieldName()) ) );
	}

	public int hashCode() {
		int result = 17;
		result = 37 * result + ( getEntityName() == null ? 0 : this.getEntityName().hashCode() );
		result = 37 * result + ( getFieldName() == null ? 0 : this.getFieldName().hashCode() );
		return result;
	}
}
