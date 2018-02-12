//DO NOT MODIFY: generated for HUD LRS
package gov.hud.lrs.common.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="FRAUD_PARTICIPANT_REF")
@SuppressWarnings("serial")
public class FraudParticipantRef implements java.io.Serializable {
	private String fraudParticipantId;
	private String fraudParticipantDescription;
	private Set<LenderSelfReportSelectionRequestFraudParticipantRef> lenderSelfReportSelectionRequestFraudParticipantRefs = new HashSet<LenderSelfReportSelectionRequestFraudParticipantRef>(0);

	public FraudParticipantRef() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")

    @Column(name="FRAUD_PARTICIPANT_ID", unique=true, nullable=false, length=36)
	public String getFraudParticipantId() {
		return this.fraudParticipantId;
	}

	public void setFraudParticipantId(String fraudParticipantId) {
		this.fraudParticipantId = fraudParticipantId;
	}


    @Column(name="FRAUD_PARTICIPANT_DESCRIPTION", nullable=false, length=30)
	public String getFraudParticipantDescription() {
		return this.fraudParticipantDescription;
	}

	public void setFraudParticipantDescription(String fraudParticipantDescription) {
		this.fraudParticipantDescription = fraudParticipantDescription;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="fraudParticipantRef")
	public Set<LenderSelfReportSelectionRequestFraudParticipantRef> getLenderSelfReportSelectionRequestFraudParticipantRefs() {
		return this.lenderSelfReportSelectionRequestFraudParticipantRefs;
	}

	public void setLenderSelfReportSelectionRequestFraudParticipantRefs(Set<LenderSelfReportSelectionRequestFraudParticipantRef> lenderSelfReportSelectionRequestFraudParticipantRefs) {
		this.lenderSelfReportSelectionRequestFraudParticipantRefs = lenderSelfReportSelectionRequestFraudParticipantRefs;
	}

}
