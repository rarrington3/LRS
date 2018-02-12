//DO NOT MODIFY: generated for HUD LRS
package gov.hud.lrs.common.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="REVIEW_REFERRAL", uniqueConstraints = @UniqueConstraint(columnNames={"REVIEW_ID", "REVIEW_REFERRAL_TYPE_REF_ID"}))
@SuppressWarnings("serial")
public class ReviewReferral implements java.io.Serializable {
	private String reviewReferralId;
	private Review review;
	private ReviewReferralSourceRef reviewReferralSourceRef;
	private ReviewReferralTypeRef reviewReferralTypeRef;
	private String notes;
	private Date referralTmstmp;
	private String createdBy;
	private String updatedBy;
	private Date createdTs;
	private Date updatedTs;

	public ReviewReferral() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")

    @Column(name="REVIEW_REFERRAL_ID", unique=true, nullable=false, length=36)
	public String getReviewReferralId() {
		return this.reviewReferralId;
	}

	public void setReviewReferralId(String reviewReferralId) {
		this.reviewReferralId = reviewReferralId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="REVIEW_ID", nullable=false)
	public Review getReview() {
		return this.review;
	}

	public void setReview(Review review) {
		this.review = review;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="REVIEW_REFERRAL_SOURCE_REF_ID")
	public ReviewReferralSourceRef getReviewReferralSourceRef() {
		return this.reviewReferralSourceRef;
	}

	public void setReviewReferralSourceRef(ReviewReferralSourceRef reviewReferralSourceRef) {
		this.reviewReferralSourceRef = reviewReferralSourceRef;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="REVIEW_REFERRAL_TYPE_REF_ID")
	public ReviewReferralTypeRef getReviewReferralTypeRef() {
		return this.reviewReferralTypeRef;
	}

	public void setReviewReferralTypeRef(ReviewReferralTypeRef reviewReferralTypeRef) {
		this.reviewReferralTypeRef = reviewReferralTypeRef;
	}


    @Column(name="NOTES")
	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="REFERRAL_TMSTMP", length=23)
	public Date getReferralTmstmp() {
		return this.referralTmstmp;
	}

	public void setReferralTmstmp(Date referralTmstmp) {
		this.referralTmstmp = referralTmstmp;
	}


    @Column(name="CREATED_BY", length=6)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


    @Column(name="UPDATED_BY", length=6)
	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATED_TS", length=23)
	public Date getCreatedTs() {
		return this.createdTs;
	}

	public void setCreatedTs(Date createdTs) {
		this.createdTs = createdTs;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="UPDATED_TS", length=23)
	public Date getUpdatedTs() {
		return this.updatedTs;
	}

	public void setUpdatedTs(Date updatedTs) {
		this.updatedTs = updatedTs;
	}

}
