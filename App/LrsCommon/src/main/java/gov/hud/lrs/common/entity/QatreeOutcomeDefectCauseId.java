//DO NOT MODIFY: generated for HUD LRS 
package gov.hud.lrs.common.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class QatreeOutcomeDefectCauseId implements java.io.Serializable {
	private String qatreeOutcomeId;
	private String defectCauseId;

	public QatreeOutcomeDefectCauseId() {
	}


    @Column(name="QATREE_OUTCOME_ID", nullable=false, length=36)
	public String getQatreeOutcomeId() {
		return this.qatreeOutcomeId;
	}

	public void setQatreeOutcomeId(String qatreeOutcomeId) {
		this.qatreeOutcomeId = qatreeOutcomeId;
	}


    @Column(name="DEFECT_CAUSE_ID", nullable=false, length=36)
	public String getDefectCauseId() {
		return this.defectCauseId;
	}

	public void setDefectCauseId(String defectCauseId) {
		this.defectCauseId = defectCauseId;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		} else if ((other == null)) {
			return false;
		} else if (!(other instanceof QatreeOutcomeDefectCauseId)) {
			return false;
		}
		QatreeOutcomeDefectCauseId castOther = (QatreeOutcomeDefectCauseId)other; 
		return ( (this.getQatreeOutcomeId()==castOther.getQatreeOutcomeId()) || ( this.getQatreeOutcomeId()!=null && castOther.getQatreeOutcomeId()!=null && this.getQatreeOutcomeId().equals(castOther.getQatreeOutcomeId()) ) )
 && ( (this.getDefectCauseId()==castOther.getDefectCauseId()) || ( this.getDefectCauseId()!=null && castOther.getDefectCauseId()!=null && this.getDefectCauseId().equals(castOther.getDefectCauseId()) ) );
	}

	public int hashCode() {
		int result = 17;
		result = 37 * result + ( getQatreeOutcomeId() == null ? 0 : this.getQatreeOutcomeId().hashCode() );
		result = 37 * result + ( getDefectCauseId() == null ? 0 : this.getDefectCauseId().hashCode() );
		return result;
	}
}
