//DO NOT MODIFY: generated for HUD LRS 
package gov.hud.lrs.common.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class LenderSelfReportSelectionRequestFraudTypeRefId implements java.io.Serializable {
	private String selectionRequestId;
	private String fraudTypeId;

	public LenderSelfReportSelectionRequestFraudTypeRefId() {
	}


    @Column(name="SELECTION_REQUEST_ID", nullable=false, length=36)
	public String getSelectionRequestId() {
		return this.selectionRequestId;
	}

	public void setSelectionRequestId(String selectionRequestId) {
		this.selectionRequestId = selectionRequestId;
	}


    @Column(name="FRAUD_TYPE_ID", nullable=false, length=36)
	public String getFraudTypeId() {
		return this.fraudTypeId;
	}

	public void setFraudTypeId(String fraudTypeId) {
		this.fraudTypeId = fraudTypeId;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		} else if ((other == null)) {
			return false;
		} else if (!(other instanceof LenderSelfReportSelectionRequestFraudTypeRefId)) {
			return false;
		}
		LenderSelfReportSelectionRequestFraudTypeRefId castOther = (LenderSelfReportSelectionRequestFraudTypeRefId)other; 
		return ( (this.getSelectionRequestId()==castOther.getSelectionRequestId()) || ( this.getSelectionRequestId()!=null && castOther.getSelectionRequestId()!=null && this.getSelectionRequestId().equals(castOther.getSelectionRequestId()) ) )
 && ( (this.getFraudTypeId()==castOther.getFraudTypeId()) || ( this.getFraudTypeId()!=null && castOther.getFraudTypeId()!=null && this.getFraudTypeId().equals(castOther.getFraudTypeId()) ) );
	}

	public int hashCode() {
		int result = 17;
		result = 37 * result + ( getSelectionRequestId() == null ? 0 : this.getSelectionRequestId().hashCode() );
		result = 37 * result + ( getFraudTypeId() == null ? 0 : this.getFraudTypeId().hashCode() );
		return result;
	}
}
