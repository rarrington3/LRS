//DO NOT MODIFY: generated for HUD LRS 
package gov.hud.lrs.common.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class ReviewTypeDefectId implements java.io.Serializable {
	private String reviewTypeId;
	private String defectId;

	public ReviewTypeDefectId() {
	}


    @Column(name="REVIEW_TYPE_ID", nullable=false, length=36)
	public String getReviewTypeId() {
		return this.reviewTypeId;
	}

	public void setReviewTypeId(String reviewTypeId) {
		this.reviewTypeId = reviewTypeId;
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
		} else if (!(other instanceof ReviewTypeDefectId)) {
			return false;
		}
		ReviewTypeDefectId castOther = (ReviewTypeDefectId)other; 
		return ( (this.getReviewTypeId()==castOther.getReviewTypeId()) || ( this.getReviewTypeId()!=null && castOther.getReviewTypeId()!=null && this.getReviewTypeId().equals(castOther.getReviewTypeId()) ) )
 && ( (this.getDefectId()==castOther.getDefectId()) || ( this.getDefectId()!=null && castOther.getDefectId()!=null && this.getDefectId().equals(castOther.getDefectId()) ) );
	}

	public int hashCode() {
		int result = 17;
		result = 37 * result + ( getReviewTypeId() == null ? 0 : this.getReviewTypeId().hashCode() );
		result = 37 * result + ( getDefectId() == null ? 0 : this.getDefectId().hashCode() );
		return result;
	}
}
