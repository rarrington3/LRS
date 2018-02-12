//DO NOT MODIFY: generated for HUD LRS 
package gov.hud.lrs.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="FINDING_RATING_RULE")
@SuppressWarnings("serial")
public class FindingRatingRule implements java.io.Serializable {
	private String findingRatingRuleId;
	private Defect defect;
	private DefectCause defectCause;
	private DefectSource defectSource;
	private int unacceptableRatingThreshold;

	public FindingRatingRule() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")
    
    @Column(name="FINDING_RATING_RULE_ID", unique=true, nullable=false, length=36)
	public String getFindingRatingRuleId() {
		return this.findingRatingRuleId;
	}

	public void setFindingRatingRuleId(String findingRatingRuleId) {
		this.findingRatingRuleId = findingRatingRuleId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="DEFECT_ID", nullable=false)
	public Defect getDefect() {
		return this.defect;
	}

	public void setDefect(Defect defect) {
		this.defect = defect;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="DEFECT_CAUSE_ID", nullable=false)
	public DefectCause getDefectCause() {
		return this.defectCause;
	}

	public void setDefectCause(DefectCause defectCause) {
		this.defectCause = defectCause;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="DEFECT_SOURCE_ID", nullable=false)
	public DefectSource getDefectSource() {
		return this.defectSource;
	}

	public void setDefectSource(DefectSource defectSource) {
		this.defectSource = defectSource;
	}

    
    @Column(name="UNACCEPTABLE_RATING_THRESHOLD", nullable=false)
	public int getUnacceptableRatingThreshold() {
		return this.unacceptableRatingThreshold;
	}

	public void setUnacceptableRatingThreshold(int unacceptableRatingThreshold) {
		this.unacceptableRatingThreshold = unacceptableRatingThreshold;
	}

}
