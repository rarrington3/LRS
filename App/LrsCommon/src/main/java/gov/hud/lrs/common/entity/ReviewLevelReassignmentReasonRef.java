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
@Table(name="REVIEW_LEVEL_REASSIGNMENT_REASON_REF")
@SuppressWarnings("serial")
public class ReviewLevelReassignmentReasonRef implements java.io.Serializable {
	private String reassignmentReasonId;
	private String code;
	private String description;
	private Set<ReviewLevel> reviewLevels = new HashSet<ReviewLevel>(0);

	public ReviewLevelReassignmentReasonRef() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")

    @Column(name="REASSIGNMENT_REASON_ID", unique=true, nullable=false, length=36)
	public String getReassignmentReasonId() {
		return this.reassignmentReasonId;
	}

	public void setReassignmentReasonId(String reassignmentReasonId) {
		this.reassignmentReasonId = reassignmentReasonId;
	}


    @Column(name="CODE", nullable=false, length=16)
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

	@OneToMany(fetch=FetchType.LAZY, mappedBy="reviewLevelReassignmentReasonRef")
	public Set<ReviewLevel> getReviewLevels() {
		return this.reviewLevels;
	}

	public void setReviewLevels(Set<ReviewLevel> reviewLevels) {
		this.reviewLevels = reviewLevels;
	}

}
