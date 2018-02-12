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
@Table(name="REVIEW_SCOPE_REF")
@SuppressWarnings("serial")
public class ReviewScopeRef implements java.io.Serializable {
	private String reviewScopeId;
	private String code;
	private String description;
	private Set<ScoringModelVersion> scoringModelVersions = new HashSet<ScoringModelVersion>(0);
	private Set<Review> reviews = new HashSet<Review>(0);

	public ReviewScopeRef() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")
    
    @Column(name="REVIEW_SCOPE_ID", unique=true, nullable=false, length=36)
	public String getReviewScopeId() {
		return this.reviewScopeId;
	}

	public void setReviewScopeId(String reviewScopeId) {
		this.reviewScopeId = reviewScopeId;
	}

    
    @Column(name="CODE", nullable=false, length=1)
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

	@OneToMany(fetch=FetchType.LAZY, mappedBy="reviewScopeRef")
	public Set<ScoringModelVersion> getScoringModelVersions() {
		return this.scoringModelVersions;
	}

	public void setScoringModelVersions(Set<ScoringModelVersion> scoringModelVersions) {
		this.scoringModelVersions = scoringModelVersions;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="reviewScopeRef")
	public Set<Review> getReviews() {
		return this.reviews;
	}

	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	}

}
