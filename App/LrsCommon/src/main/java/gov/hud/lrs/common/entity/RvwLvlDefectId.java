//DO NOT MODIFY: generated for HUD LRS 
package gov.hud.lrs.common.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class RvwLvlDefectId implements java.io.Serializable {
	private String reviewLevelId;
	private String defectId;

	public RvwLvlDefectId() {
	}


    @Column(name="REVIEW_LEVEL_ID", nullable=false, length=36)
	public String getReviewLevelId() {
		return this.reviewLevelId;
	}

	public void setReviewLevelId(String reviewLevelId) {
		this.reviewLevelId = reviewLevelId;
	}


    @Column(name="DEFECT_ID", nullable=false, length=36)
	public String getDefectId() {
		return this.defectId;
	}

	public void setDefectId(String defectId) {
		this.defectId = defectId;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		} else if ((other == null)) {
			return false;
		} else if (!(other instanceof RvwLvlDefectId)) {
			return false;
		}
		RvwLvlDefectId castOther = (RvwLvlDefectId)other; 
		return ( (this.getReviewLevelId()==castOther.getReviewLevelId()) || ( this.getReviewLevelId()!=null && castOther.getReviewLevelId()!=null && this.getReviewLevelId().equals(castOther.getReviewLevelId()) ) )
 && ( (this.getDefectId()==castOther.getDefectId()) || ( this.getDefectId()!=null && castOther.getDefectId()!=null && this.getDefectId().equals(castOther.getDefectId()) ) );
	}

	public int hashCode() {
		int result = 17;
		result = 37 * result + ( getReviewLevelId() == null ? 0 : this.getReviewLevelId().hashCode() );
		result = 37 * result + ( getDefectId() == null ? 0 : this.getDefectId().hashCode() );
		return result;
	}
}
