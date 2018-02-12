//DO NOT MODIFY: generated for HUD LRS 
package gov.hud.lrs.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="excluded_review")
@SuppressWarnings("serial")
public class ExcludedReview implements java.io.Serializable {
	private String caseNumber;

	public ExcludedReview() {
	}

    @Id 
    
    @Column(name="CASE_NUMBER", nullable=false, length=11)
	public String getCaseNumber() {
		return this.caseNumber;
	}

	public void setCaseNumber(String caseNumber) {
		this.caseNumber = caseNumber;
	}

}
