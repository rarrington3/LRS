//DO NOT MODIFY: generated for HUD LRS 
package gov.hud.lrs.common.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class QatreeQstnRulesId implements java.io.Serializable {
	private String reviewTypeId;
	private String selectionReasonId;

	public QatreeQstnRulesId() {
	}


    @Column(name="REVIEW_TYPE_ID", nullable=false, length=36)
	public String getReviewTypeId() {
		return this.reviewTypeId;
	}

	public void setReviewTypeId(String reviewTypeId) {
		this.reviewTypeId = reviewTypeId;
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
		} else if (!(other instanceof QatreeQstnRulesId)) {
			return false;
		}
		QatreeQstnRulesId castOther = (QatreeQstnRulesId)other; 
		return ( (this.getReviewTypeId()==castOther.getReviewTypeId()) || ( this.getReviewTypeId()!=null && castOther.getReviewTypeId()!=null && this.getReviewTypeId().equals(castOther.getReviewTypeId()) ) )
 && ( (this.getSelectionReasonId()==castOther.getSelectionReasonId()) || ( this.getSelectionReasonId()!=null && castOther.getSelectionReasonId()!=null && this.getSelectionReasonId().equals(castOther.getSelectionReasonId()) ) );
	}

	public int hashCode() {
		int result = 17;
		result = 37 * result + ( getReviewTypeId() == null ? 0 : this.getReviewTypeId().hashCode() );
		result = 37 * result + ( getSelectionReasonId() == null ? 0 : this.getSelectionReasonId().hashCode() );
		return result;
	}
}
