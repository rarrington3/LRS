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
@Table(name="FRAUD_TYPE_REF")
@SuppressWarnings("serial")
public class FraudTypeRef implements java.io.Serializable {
	private String fraudTypeId;
	private String fraudTypeDescription;
	private Set<LenderSelfReportSelectionRequestFraudTypeRef> lenderSelfReportSelectionRequestFraudTypeRefs = new HashSet<LenderSelfReportSelectionRequestFraudTypeRef>(0);

	public FraudTypeRef() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")

    @Column(name="FRAUD_TYPE_ID", unique=true, nullable=false, length=36)
	public String getFraudTypeId() {
		return this.fraudTypeId;
	}

	public void setFraudTypeId(String fraudTypeId) {
		this.fraudTypeId = fraudTypeId;
	}


    @Column(name="FRAUD_TYPE_DESCRIPTION", nullable=false, length=30)
	public String getFraudTypeDescription() {
		return this.fraudTypeDescription;
	}

	public void setFraudTypeDescription(String fraudTypeDescription) {
		this.fraudTypeDescription = fraudTypeDescription;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="fraudTypeRef")
	public Set<LenderSelfReportSelectionRequestFraudTypeRef> getLenderSelfReportSelectionRequestFraudTypeRefs() {
		return this.lenderSelfReportSelectionRequestFraudTypeRefs;
	}

	public void setLenderSelfReportSelectionRequestFraudTypeRefs(Set<LenderSelfReportSelectionRequestFraudTypeRef> lenderSelfReportSelectionRequestFraudTypeRefs) {
		this.lenderSelfReportSelectionRequestFraudTypeRefs = lenderSelfReportSelectionRequestFraudTypeRefs;
	}

}
