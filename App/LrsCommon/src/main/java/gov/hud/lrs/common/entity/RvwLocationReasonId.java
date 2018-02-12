//DO NOT MODIFY: generated for HUD LRS 
package gov.hud.lrs.common.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class RvwLocationReasonId implements java.io.Serializable {
	private String reviewLocationId;
	private String selectionReasonId;

	public RvwLocationReasonId() {
	}


    @Column(name="REVIEW_LOCATION_ID", nullable=false, length=36)
	public String getReviewLocationId() {
		return this.reviewLocationId;
	}

	public void setReviewLocationId(String reviewLocationId) {
		this.reviewLocationId = reviewLocationId;
	}


    @Column(name="SELECTION_REASON_ID", nullable=false, length=36)
	public String getSelectionReasonId() {
		return this.selectionReasonId;
	}

	public void setSelectionReasonId(String selectionReasonId) {
		this.selectionReasonId = selectionReasonId;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		} else if ((other == null)) {
			return false;
		} else if (!(other instanceof RvwLocationReasonId)) {
			return false;
		}
		RvwLocationReasonId castOther = (RvwLocationReasonId)other; 
		return ( (this.getReviewLocationId()==castOther.getReviewLocationId()) || ( this.getReviewLocationId()!=null && castOther.getReviewLocationId()!=null && this.getReviewLocationId().equals(castOther.getReviewLocationId()) ) )
 && ( (this.getSelectionReasonId()==castOther.getSelectionReasonId()) || ( this.getSelectionReasonId()!=null && castOther.getSelectionReasonId()!=null && this.getSelectionReasonId().equals(castOther.getSelectionReasonId()) ) );
	}

	public int hashCode() {
		int result = 17;
		result = 37 * result + ( getReviewLocationId() == null ? 0 : this.getReviewLocationId().hashCode() );
		result = 37 * result + ( getSelectionReasonId() == null ? 0 : this.getSelectionReasonId().hashCode() );
		return result;
	}
}
