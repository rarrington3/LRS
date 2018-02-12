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
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="REVIEW_LEVEL_STATUS_REF", uniqueConstraints = @UniqueConstraint(columnNames="CODE"))
@SuppressWarnings("serial")
public class ReviewLevelStatusRef implements java.io.Serializable {
	private String reviewLevelStatusId;
	private String code;
	private String description;
	private Set<ReviewLevel> reviewLevels = new HashSet<ReviewLevel>(0);

	public ReviewLevelStatusRef() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")

    @Column(name="REVIEW_LEVEL_STATUS_ID", unique=true, nullable=false, length=36)
	public String getReviewLevelStatusId() {
		return this.reviewLevelStatusId;
	}

	public void setReviewLevelStatusId(String reviewLevelStatusId) {
		this.reviewLevelStatusId = reviewLevelStatusId;
	}


    @Column(name="CODE", unique=true, nullable=false, length=30)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}


    @Column(name="DESCRIPTION", nullable=false, length=50)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="reviewLevelStatusRef")
	public Set<ReviewLevel> getReviewLevels() {
		return this.reviewLevels;
	}

	public void setReviewLevels(Set<ReviewLevel> reviewLevels) {
		this.reviewLevels = reviewLevels;
	}

}
