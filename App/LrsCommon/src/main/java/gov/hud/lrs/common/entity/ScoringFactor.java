//DO NOT MODIFY: generated for HUD LRS
package gov.hud.lrs.common.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="SCORING_FACTOR")
@SuppressWarnings("serial")
public class ScoringFactor implements java.io.Serializable {
	private String scoringFactorId;
	private ScoringModel scoringModel;
	private String factorAttributeName;
	private String description;
	private String comments;
	private String technicalReference;
	private Set<ScoringModelVersionFactor> scoringModelVersionFactors = new HashSet<ScoringModelVersionFactor>(0);

	public ScoringFactor() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")

    @Column(name="SCORING_FACTOR_ID", unique=true, nullable=false, length=36)
	public String getScoringFactorId() {
		return this.scoringFactorId;
	}

	public void setScoringFactorId(String scoringFactorId) {
		this.scoringFactorId = scoringFactorId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="SCORING_MODEL_ID")
	public ScoringModel getScoringModel() {
		return this.scoringModel;
	}

	public void setScoringModel(ScoringModel scoringModel) {
		this.scoringModel = scoringModel;
	}


    @Column(name="FACTOR_ATTRIBUTE_NAME", nullable=false, length=100)
	public String getFactorAttributeName() {
		return this.factorAttributeName;
	}

	public void setFactorAttributeName(String factorAttributeName) {
		this.factorAttributeName = factorAttributeName;
	}


    @Column(name="DESCRIPTION", length=256)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


    @Column(name="COMMENTS", length=256)
	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}


    @Column(name="TECHNICAL_REFERENCE", length=50)
	public String getTechnicalReference() {
		return this.technicalReference;
	}

	public void setTechnicalReference(String technicalReference) {
		this.technicalReference = technicalReference;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="scoringFactor")
	public Set<ScoringModelVersionFactor> getScoringModelVersionFactors() {
		return this.scoringModelVersionFactors;
	}

	public void setScoringModelVersionFactors(Set<ScoringModelVersionFactor> scoringModelVersionFactors) {
		this.scoringModelVersionFactors = scoringModelVersionFactors;
	}

}
