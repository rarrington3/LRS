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
@Table(name="LOAN_TYPE_REF")
@SuppressWarnings("serial")
public class LoanTypeRef implements java.io.Serializable {
	private String loanTypeRefId;
	private String code;
	private String description;
	private Set<LenderMonitoringSelectionRequest> lenderMonitoringSelectionRequests = new HashSet<LenderMonitoringSelectionRequest>(0);
	private Set<ScoringModel> scoringModels = new HashSet<ScoringModel>(0);

	public LoanTypeRef() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")

    @Column(name="LOAN_TYPE_REF_ID", unique=true, nullable=false, length=36)
	public String getLoanTypeRefId() {
		return this.loanTypeRefId;
	}

	public void setLoanTypeRefId(String loanTypeRefId) {
		this.loanTypeRefId = loanTypeRefId;
	}


    @Column(name="CODE", nullable=false, length=16)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}


    @Column(name="DESCRIPTION", nullable=false, length=30)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="loanTypeRef")
	public Set<LenderMonitoringSelectionRequest> getLenderMonitoringSelectionRequests() {
		return this.lenderMonitoringSelectionRequests;
	}

	public void setLenderMonitoringSelectionRequests(Set<LenderMonitoringSelectionRequest> lenderMonitoringSelectionRequests) {
		this.lenderMonitoringSelectionRequests = lenderMonitoringSelectionRequests;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="loanTypeRef")
	public Set<ScoringModel> getScoringModels() {
		return this.scoringModels;
	}

	public void setScoringModels(Set<ScoringModel> scoringModels) {
		this.scoringModels = scoringModels;
	}

}
