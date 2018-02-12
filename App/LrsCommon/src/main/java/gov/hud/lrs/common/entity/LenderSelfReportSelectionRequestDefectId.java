//DO NOT MODIFY: generated for HUD LRS 
package gov.hud.lrs.common.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class LenderSelfReportSelectionRequestDefectId implements java.io.Serializable {
	private String selectionRequestId;
	private String defectId;

	public LenderSelfReportSelectionRequestDefectId() {
	}


    @Column(name="SELECTION_REQUEST_ID", nullable=false, length=36)
	public String getSelectionRequestId() {
		return this.selectionRequestId;
	}

	public void setSelectionRequestId(String selectionRequestId) {
		this.selectionRequestId = selectionRequestId;
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
		} else if (!(other instanceof LenderSelfReportSelectionRequestDefectId)) {
			return false;
		}
		LenderSelfReportSelectionRequestDefectId castOther = (LenderSelfReportSelectionRequestDefectId)other; 
		return ( (this.getSelectionRequestId()==castOther.getSelectionRequestId()) || ( this.getSelectionRequestId()!=null && castOther.getSelectionRequestId()!=null && this.getSelectionRequestId().equals(castOther.getSelectionRequestId()) ) )
 && ( (this.getDefectId()==castOther.getDefectId()) || ( this.getDefectId()!=null && castOther.getDefectId()!=null && this.getDefectId().equals(castOther.getDefectId()) ) );
	}

	public int hashCode() {
		int result = 17;
		result = 37 * result + ( getSelectionRequestId() == null ? 0 : this.getSelectionRequestId().hashCode() );
		result = 37 * result + ( getDefectId() == null ? 0 : this.getDefectId().hashCode() );
		return result;
	}
}
