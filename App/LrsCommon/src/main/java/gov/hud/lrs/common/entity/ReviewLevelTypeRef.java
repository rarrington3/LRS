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
@Table(name="REVIEW_LEVEL_TYPE_REF")
@SuppressWarnings("serial")
public class ReviewLevelTypeRef implements java.io.Serializable {
	private String reviewLevelTypeId;
	private String reviewLevelCd;
	private String description;
	private int ordinal;
	private Set<ReviewLevel> reviewLevels = new HashSet<ReviewLevel>(0);
	private Set<ReviewLevelIterationTimeframe> reviewLevelIterationTimeframes = new HashSet<ReviewLevelIterationTimeframe>(0);
	private Set<Batch> batches = new HashSet<Batch>(0);

	public ReviewLevelTypeRef() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")

    @Column(name="REVIEW_LEVEL_TYPE_ID", unique=true, nullable=false, length=36)
	public String getReviewLevelTypeId() {
		return this.reviewLevelTypeId;
	}

	public void setReviewLevelTypeId(String reviewLevelTypeId) {
		this.reviewLevelTypeId = reviewLevelTypeId;
	}


    @Column(name="REVIEW_LEVEL_CD", nullable=false, length=4)
	public String getReviewLevelCd() {
		return this.reviewLevelCd;
	}

	public void setReviewLevelCd(String reviewLevelCd) {
		this.reviewLevelCd = reviewLevelCd;
	}


    @Column(name="DESCRIPTION", length=50)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


    @Column(name="ORDINAL", nullable=false)
	public int getOrdinal() {
		return this.ordinal;
	}

	public void setOrdinal(int ordinal) {
		this.ordinal = ordinal;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="reviewLevelTypeRef")
	public Set<ReviewLevel> getReviewLevels() {
		return this.reviewLevels;
	}

	public void setReviewLevels(Set<ReviewLevel> reviewLevels) {
		this.reviewLevels = reviewLevels;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="reviewLevelTypeRef")
	public Set<ReviewLevelIterationTimeframe> getReviewLevelIterationTimeframes() {
		return this.reviewLevelIterationTimeframes;
	}

	public void setReviewLevelIterationTimeframes(Set<ReviewLevelIterationTimeframe> reviewLevelIterationTimeframes) {
		this.reviewLevelIterationTimeframes = reviewLevelIterationTimeframes;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="reviewLevelTypeRef")
	public Set<Batch> getBatches() {
		return this.batches;
	}

	public void setBatches(Set<Batch> batches) {
		this.batches = batches;
	}

}
