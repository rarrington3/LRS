//DO NOT MODIFY: generated for HUD LRS 
package gov.hud.lrs.common.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class BatchPersonnelId implements java.io.Serializable {
	private String batchId;
	private String personnelId;

	public BatchPersonnelId() {
	}


    @Column(name="BATCH_ID", nullable=false, length=36)
	public String getBatchId() {
		return this.batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}


    @Column(name="PERSONNEL_ID", nullable=false, length=36)
	public String getPersonnelId() {
		return this.personnelId;
	}

	public void setPersonnelId(String personnelId) {
		this.personnelId = personnelId;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		} else if ((other == null)) {
			return false;
		} else if (!(other instanceof BatchPersonnelId)) {
			return false;
		}
		BatchPersonnelId castOther = (BatchPersonnelId)other; 
		return ( (this.getBatchId()==castOther.getBatchId()) || ( this.getBatchId()!=null && castOther.getBatchId()!=null && this.getBatchId().equals(castOther.getBatchId()) ) )
 && ( (this.getPersonnelId()==castOther.getPersonnelId()) || ( this.getPersonnelId()!=null && castOther.getPersonnelId()!=null && this.getPersonnelId().equals(castOther.getPersonnelId()) ) );
	}

	public int hashCode() {
		int result = 17;
		result = 37 * result + ( getBatchId() == null ? 0 : this.getBatchId().hashCode() );
		result = 37 * result + ( getPersonnelId() == null ? 0 : this.getPersonnelId().hashCode() );
		return result;
	}
}
