//DO NOT MODIFY: generated for HUD LRS
package gov.hud.lrs.common.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="REVIEW_REFERRAL_SOURCE_REF")
@SuppressWarnings("serial")
public class ReviewReferralSourceRef implements java.io.Serializable {
	private String reviewReferralSourceRefId;
	private String code;
	private String description;
	private Set<ReviewReferral> reviewReferrals = new HashSet<ReviewReferral>(0);

	public ReviewReferralSourceRef() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")

    @Column(name="REVIEW_REFERRAL_SOURCE_REF_ID", unique=true, nullable=false, length=36)
	public String getReviewReferralSourceRefId() {
		return this.reviewReferralSourceRefId;
	}

	public void setReviewReferralSourceRefId(String reviewReferralSourceRefId) {
		this.reviewReferralSourceRefId = reviewReferralSourceRefId;
	}


    @Column(name="CODE", nullable=false, length=16)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}


    @Column(name="DESCRIPTION", nullable=false, length=30)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="reviewReferralSourceRef")
	public Set<ReviewReferral> getReviewReferrals() {
		return this.reviewReferrals;
	}

	public void setReviewReferrals(Set<ReviewReferral> reviewReferrals) {
		this.reviewReferrals = reviewReferrals;
	}

}
