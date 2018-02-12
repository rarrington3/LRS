//DO NOT MODIFY: generated for HUD LRS 
package gov.hud.lrs.common.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class RvwLvlAuditId implements java.io.Serializable {
	private Date auditTmstmp;
	private String reviewLvlId;

	public RvwLvlAuditId() {
	}


    @Column(name="AUDIT_TMSTMP", nullable=false, length=23)
	public Date getAuditTmstmp() {
		return this.auditTmstmp;
	}

	public void setAuditTmstmp(Date auditTmstmp) {
		this.auditTmstmp = auditTmstmp;
	}


    @Column(name="REVIEW_LVL_ID", nullable=false, length=36)
	public String getReviewLvlId() {
		return this.reviewLvlId;
	}

	public void setReviewLvlId(String reviewLvlId) {
		this.reviewLvlId = reviewLvlId;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		} else if ((other == null)) {
			return false;
		} else if (!(other instanceof RvwLvlAuditId)) {
			return false;
		}
		RvwLvlAuditId castOther = (RvwLvlAuditId)other; 
		return ( (this.getAuditTmstmp()==castOther.getAuditTmstmp()) || ( this.getAuditTmstmp()!=null && castOther.getAuditTmstmp()!=null && this.getAuditTmstmp().equals(castOther.getAuditTmstmp()) ) )
 && ( (this.getReviewLvlId()==castOther.getReviewLvlId()) || ( this.getReviewLvlId()!=null && castOther.getReviewLvlId()!=null && this.getReviewLvlId().equals(castOther.getReviewLvlId()) ) );
	}

	public int hashCode() {
		int result = 17;
		result = 37 * result + ( getAuditTmstmp() == null ? 0 : this.getAuditTmstmp().hashCode() );
		result = 37 * result + ( getReviewLvlId() == null ? 0 : this.getReviewLvlId().hashCode() );
		return result;
	}
}
