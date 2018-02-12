//DO NOT MODIFY: generated for HUD LRS 
package gov.hud.lrs.common.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class LenderSelfReportSelectionRequestFraudParticipantRefId implements java.io.Serializable {
	private String selectionRequestId;
	private String fraudParticipantId;

	public LenderSelfReportSelectionRequestFraudParticipantRefId() {
	}


    @Column(name="SELECTION_REQUEST_ID", nullable=false, length=36)
	public String getSelectionRequestId() {
		return this.selectionRequestId;
	}

	public void setSelectionRequestId(String selectionRequestId) {
		this.selectionRequestId = selectionRequestId;
	}


    @Column(name="FRAUD_PARTICIPANT_ID", nullable=false, length=36)
	public String getFraudParticipantId() {
		return this.fraudParticipantId;
	}

	public void setFraudParticipantId(String fraudParticipantId) {
		this.fraudParticipantId = fraudParticipantId;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		} else if ((other == null)) {
			return false;
		} else if (!(other instanceof LenderSelfReportSelectionRequestFraudParticipantRefId)) {
			return false;
		}
		LenderSelfReportSelectionRequestFraudParticipantRefId castOther = (LenderSelfReportSelectionRequestFraudParticipantRefId)other; 
		return ( (this.getSelectionRequestId()==castOther.getSelectionRequestId()) || ( this.getSelectionRequestId()!=null && castOther.getSelectionRequestId()!=null && this.getSelectionRequestId().equals(castOther.getSelectionRequestId()) ) )
 && ( (this.getFraudParticipantId()==castOther.getFraudParticipantId()) || ( this.getFraudParticipantId()!=null && castOther.getFraudParticipantId()!=null && this.getFraudParticipantId().equals(castOther.getFraudParticipantId()) ) );
	}

	public int hashCode() {
		int result = 17;
		result = 37 * result + ( getSelectionRequestId() == null ? 0 : this.getSelectionRequestId().hashCode() );
		result = 37 * result + ( getFraudParticipantId() == null ? 0 : this.getFraudParticipantId().hashCode() );
		return result;
	}
}
