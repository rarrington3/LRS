//DO NOT MODIFY: generated for HUD LRS 
package gov.hud.lrs.common.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class QatreeOutcomeDefectSourceId implements java.io.Serializable {
	private String qatreeOutcomeId;
	private String defectSourceId;

	public QatreeOutcomeDefectSourceId() {
	}


    @Column(name="QATREE_OUTCOME_ID", nullable=false, length=36)
	public String getQatreeOutcomeId() {
		return this.qatreeOutcomeId;
	}

	public void setQatreeOutcomeId(String qatreeOutcomeId) {
		this.qatreeOutcomeId = qatreeOutcomeId;
	}


    @Column(name="DEFECT_SOURCE_ID", nullable=false, length=36)
	public String getDefectSourceId() {
		return this.defectSourceId;
	}

	public void setDefectSourceId(String defectSourceId) {
		this.defectSourceId = defectSourceId;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		} else if ((other == null)) {
			return false;
		} else if (!(other instanceof QatreeOutcomeDefectSourceId)) {
			return false;
		}
		QatreeOutcomeDefectSourceId castOther = (QatreeOutcomeDefectSourceId)other; 
		return ( (this.getQatreeOutcomeId()==castOther.getQatreeOutcomeId()) || ( this.getQatreeOutcomeId()!=null && castOther.getQatreeOutcomeId()!=null && this.getQatreeOutcomeId().equals(castOther.getQatreeOutcomeId()) ) )
 && ( (this.getDefectSourceId()==castOther.getDefectSourceId()) || ( this.getDefectSourceId()!=null && castOther.getDefectSourceId()!=null && this.getDefectSourceId().equals(castOther.getDefectSourceId()) ) );
	}

	public int hashCode() {
		int result = 17;
		result = 37 * result + ( getQatreeOutcomeId() == null ? 0 : this.getQatreeOutcomeId().hashCode() );
		result = 37 * result + ( getDefectSourceId() == null ? 0 : this.getDefectSourceId().hashCode() );
		return result;
	}
}
