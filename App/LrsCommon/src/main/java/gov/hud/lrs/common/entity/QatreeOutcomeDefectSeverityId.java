//DO NOT MODIFY: generated for HUD LRS 
package gov.hud.lrs.common.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class QatreeOutcomeDefectSeverityId implements java.io.Serializable {
	private String qatreeOutcomeId;
	private String defectSeverityId;

	public QatreeOutcomeDefectSeverityId() {
	}


    @Column(name="QATREE_OUTCOME_ID", nullable=false, length=36)
	public String getQatreeOutcomeId() {
		return this.qatreeOutcomeId;
	}

	public void setQatreeOutcomeId(String qatreeOutcomeId) {
		this.qatreeOutcomeId = qatreeOutcomeId;
	}


    @Column(name="DEFECT_SEVERITY_ID", nullable=false, length=36)
	public String getDefectSeverityId() {
		return this.defectSeverityId;
	}

	public void setDefectSeverityId(String defectSeverityId) {
		this.defectSeverityId = defectSeverityId;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		} else if ((other == null)) {
			return false;
		} else if (!(other instanceof QatreeOutcomeDefectSeverityId)) {
			return false;
		}
		QatreeOutcomeDefectSeverityId castOther = (QatreeOutcomeDefectSeverityId)other; 
		return ( (this.getQatreeOutcomeId()==castOther.getQatreeOutcomeId()) || ( this.getQatreeOutcomeId()!=null && castOther.getQatreeOutcomeId()!=null && this.getQatreeOutcomeId().equals(castOther.getQatreeOutcomeId()) ) )
 && ( (this.getDefectSeverityId()==castOther.getDefectSeverityId()) || ( this.getDefectSeverityId()!=null && castOther.getDefectSeverityId()!=null && this.getDefectSeverityId().equals(castOther.getDefectSeverityId()) ) );
	}

	public int hashCode() {
		int result = 17;
		result = 37 * result + ( getQatreeOutcomeId() == null ? 0 : this.getQatreeOutcomeId().hashCode() );
		result = 37 * result + ( getDefectSeverityId() == null ? 0 : this.getDefectSeverityId().hashCode() );
		return result;
	}
}
