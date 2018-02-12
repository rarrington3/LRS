//DO NOT MODIFY: generated for HUD LRS 
package gov.hud.lrs.common.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class LrsGenLookupId implements java.io.Serializable {
	private String lookupEntity;
	private String lookupField;
	private String lookupCode;

	public LrsGenLookupId() {
	}


    @Column(name="LOOKUP_ENTITY", nullable=false, length=100)
	public String getLookupEntity() {
		return this.lookupEntity;
	}

	public void setLookupEntity(String lookupEntity) {
		this.lookupEntity = lookupEntity;
	}


    @Column(name="LOOKUP_FIELD", nullable=false, length=100)
	public String getLookupField() {
		return this.lookupField;
	}

	public void setLookupField(String lookupField) {
		this.lookupField = lookupField;
	}


    @Column(name="LOOKUP_CODE", nullable=false, length=40)
	public String getLookupCode() {
		return this.lookupCode;
	}

	public void setLookupCode(String lookupCode) {
		this.lookupCode = lookupCode;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		} else if ((other == null)) {
			return false;
		} else if (!(other instanceof LrsGenLookupId)) {
			return false;
		}
		LrsGenLookupId castOther = (LrsGenLookupId)other; 
		return ( (this.getLookupEntity()==castOther.getLookupEntity()) || ( this.getLookupEntity()!=null && castOther.getLookupEntity()!=null && this.getLookupEntity().equals(castOther.getLookupEntity()) ) )
 && ( (this.getLookupField()==castOther.getLookupField()) || ( this.getLookupField()!=null && castOther.getLookupField()!=null && this.getLookupField().equals(castOther.getLookupField()) ) )
 && ( (this.getLookupCode()==castOther.getLookupCode()) || ( this.getLookupCode()!=null && castOther.getLookupCode()!=null && this.getLookupCode().equals(castOther.getLookupCode()) ) );
	}

	public int hashCode() {
		int result = 17;
		result = 37 * result + ( getLookupEntity() == null ? 0 : this.getLookupEntity().hashCode() );
		result = 37 * result + ( getLookupField() == null ? 0 : this.getLookupField().hashCode() );
		result = 37 * result + ( getLookupCode() == null ? 0 : this.getLookupCode().hashCode() );
		return result;
	}
}
