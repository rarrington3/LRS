//DO NOT MODIFY: generated for HUD LRS 
package gov.hud.lrs.common.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class ScoringModelVersionFactorId implements java.io.Serializable {
	private String scoringFactorId;
	private String scoringModelVersionId;

	public ScoringModelVersionFactorId() {
	}


    @Column(name="SCORING_FACTOR_ID", nullable=false, length=36)
	public String getScoringFactorId() {
		return this.scoringFactorId;
	}

	public void setScoringFactorId(String scoringFactorId) {
		this.scoringFactorId = scoringFactorId;
	}


    @Column(name="SCORING_MODEL_VERSION_ID", nullable=false, length=36)
	public String getScoringModelVersionId() {
		return this.scoringModelVersionId;
	}

	public void setScoringModelVersionId(String scoringModelVersionId) {
		this.scoringModelVersionId = scoringModelVersionId;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		} else if ((other == null)) {
			return false;
		} else if (!(other instanceof ScoringModelVersionFactorId)) {
			return false;
		}
		ScoringModelVersionFactorId castOther = (ScoringModelVersionFactorId)other; 
		return ( (this.getScoringFactorId()==castOther.getScoringFactorId()) || ( this.getScoringFactorId()!=null && castOther.getScoringFactorId()!=null && this.getScoringFactorId().equals(castOther.getScoringFactorId()) ) )
 && ( (this.getScoringModelVersionId()==castOther.getScoringModelVersionId()) || ( this.getScoringModelVersionId()!=null && castOther.getScoringModelVersionId()!=null && this.getScoringModelVersionId().equals(castOther.getScoringModelVersionId()) ) );
	}

	public int hashCode() {
		int result = 17;
		result = 37 * result + ( getScoringFactorId() == null ? 0 : this.getScoringFactorId().hashCode() );
		result = 37 * result + ( getScoringModelVersionId() == null ? 0 : this.getScoringModelVersionId().hashCode() );
		return result;
	}
}
