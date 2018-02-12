//DO NOT MODIFY: generated for HUD LRS 
package gov.hud.lrs.common.entity;

import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="RVW_LVL_DEFECT")
@SuppressWarnings("serial")
public class RvwLvlDefect implements java.io.Serializable {
	private RvwLvlDefectId id;
	private Defect defect;
	private Review review;
	private ReviewLevel reviewLevel;
	private String defectCd;
	private String comments;
	private String createdBy;
	private String updatedBy;
	private Date createdTs;
	private Date updatedTs;

	public RvwLvlDefect() {
	}

    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="reviewLevelId", column=@Column(name="REVIEW_LEVEL_ID", nullable=false, length=36) ), 
        @AttributeOverride(name="defectId", column=@Column(name="DEFECT_ID", nullable=false, length=36) ) } )
	public RvwLvlDefectId getId() {
		return this.id;
	}

	public void setId(RvwLvlDefectId id) {
		this.id = id;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="DEFECT_ID", nullable=false, insertable=false, updatable=false)
	public Defect getDefect() {
		return this.defect;
	}

	public void setDefect(Defect defect) {
		this.defect = defect;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="REVIEW_ID")
	public Review getReview() {
		return this.review;
	}

	public void setReview(Review review) {
		this.review = review;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="REVIEW_LEVEL_ID", nullable=false, insertable=false, updatable=false)
	public ReviewLevel getReviewLevel() {
		return this.reviewLevel;
	}

	public void setReviewLevel(ReviewLevel reviewLevel) {
		this.reviewLevel = reviewLevel;
	}

    
    @Column(name="DEFECT_CD", nullable=false, length=16)
	public String getDefectCd() {
		return this.defectCd;
	}

	public void setDefectCd(String defectCd) {
		this.defectCd = defectCd;
	}

    
    @Column(name="COMMENTS")
	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
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
