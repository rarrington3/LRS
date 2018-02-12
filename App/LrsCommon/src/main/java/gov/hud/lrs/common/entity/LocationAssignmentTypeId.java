//DO NOT MODIFY: generated for HUD LRS 
package gov.hud.lrs.common.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class LocationAssignmentTypeId implements java.io.Serializable {
	private String reviewLocationId;
	private String assignmentTypeCd;

	public LocationAssignmentTypeId() {
	}


    @Column(name="REVIEW_LOCATION_ID", nullable=false, length=36)
	public String getReviewLocationId() {
		return this.reviewLocationId;
	}

	public void setReviewLocationId(String reviewLocationId) {
		this.reviewLocationId = reviewLocationId;
	}


    @Column(name="ASSIGNMENT_TYPE_CD", nullable=false, length=16)
	public String getAssignmentTypeCd() {
		return this.assignmentTypeCd;
	}

	public void setAssignmentTypeCd(String assignmentTypeCd) {
		this.assignmentTypeCd = assignmentTypeCd;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		} else if ((other == null)) {
			return false;
		} else if (!(other instanceof LocationAssignmentTypeId)) {
			return false;
		}
		LocationAssignmentTypeId castOther = (LocationAssignmentTypeId)other; 
		return ( (this.getReviewLocationId()==castOther.getReviewLocationId()) || ( this.getReviewLocationId()!=null && castOther.getReviewLocationId()!=null && this.getReviewLocationId().equals(castOther.getReviewLocationId()) ) )
 && ( (this.getAssignmentTypeCd()==castOther.getAssignmentTypeCd()) || ( this.getAssignmentTypeCd()!=null && castOther.getAssignmentTypeCd()!=null && this.getAssignmentTypeCd().equals(castOther.getAssignmentTypeCd()) ) );
	}

	public int hashCode() {
		int result = 17;
		result = 37 * result + ( getReviewLocationId() == null ? 0 : this.getReviewLocationId().hashCode() );
		result = 37 * result + ( getAssignmentTypeCd() == null ? 0 : this.getAssignmentTypeCd().hashCode() );
		return result;
	}
}
