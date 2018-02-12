//DO NOT MODIFY: generated for HUD LRS 
package gov.hud.lrs.common.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class LenderSelfReportSelectionRequestDefectTypeRefId implements java.io.Serializable {
	private String selectionRequestId;
	private String defectTypeId;

	public LenderSelfReportSelectionRequestDefectTypeRefId() {
	}


    @Column(name="SELECTION_REQUEST_ID", nullable=false, length=36)
	public String getSelectionRequestId() {
		return this.selectionRequestId;
	}

	public void setSelectionRequestId(String selectionRequestId) {
		this.selectionRequestId = selectionRequestId;
	}


    @Column(name="DEFECT_TYPE_ID", nullable=false, length=36)
	public String getDefectTypeId() {
		return this.defectTypeId;
	}

	public void setDefectTypeId(String defectTypeId) {
		this.defectTypeId = defectTypeId;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		} else if ((other == null)) {
			return false;
		} else if (!(other instanceof LenderSelfReportSelectionRequestDefectTypeRefId)) {
			return false;
		}
		LenderSelfReportSelectionRequestDefectTypeRefId castOther = (LenderSelfReportSelectionRequestDefectTypeRefId)other; 
		return ( (this.getSelectionRequestId()==castOther.getSelectionRequestId()) || ( this.getSelectionRequestId()!=null && castOther.getSelectionRequestId()!=null && this.getSelectionRequestId().equals(castOther.getSelectionRequestId()) ) )
 && ( (this.getDefectTypeId()==castOther.getDefectTypeId()) || ( this.getDefectTypeId()!=null && castOther.getDefectTypeId()!=null && this.getDefectTypeId().equals(castOther.getDefectTypeId()) ) );
	}

	public int hashCode() {
		int result = 17;
		result = 37 * result + ( getSelectionRequestId() == null ? 0 : this.getSelectionRequestId().hashCode() );
		result = 37 * result + ( getDefectTypeId() == null ? 0 : this.getDefectTypeId().hashCode() );
		return result;
	}
}
