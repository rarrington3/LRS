//DO NOT MODIFY: generated for HUD LRS
package gov.hud.lrs.common.entity;

import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="SCORING_MODEL_VERSION", uniqueConstraints = @UniqueConstraint(columnNames={"SCORING_MODEL_ID", "MODEL_NAME"}))
@SuppressWarnings("serial")
public class ScoringModelVersion implements java.io.Serializable {
	private String scoringModelVersionId;
	private ReviewScopeRef reviewScopeRef;
	private ReviewTypeRef reviewTypeRef;
	private ScoringModel scoringModel;
	private ScoringModelVersionStatusRef scoringModelVersionStatusRef;
	private short modelVerNum;
	private float modelScoreThreshold;
	private String createdBy;
	private String updatedBy;
	private Date createdTs;
	private Date updatedTs;
	private String modelName;
	private String description;
	private int allocationPct;
	private Set<ModelScore> modelScores = new HashSet<ModelScore>(0);
	private Set<ScoringModelVersionFactor> scoringModelVersionFactors = new HashSet<ScoringModelVersionFactor>(0);

	public ScoringModelVersion() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")

    @Column(name="SCORING_MODEL_VERSION_ID", unique=true, nullable=false, length=36)
	public String getScoringModelVersionId() {
		return this.scoringModelVersionId;
	}

	public void setScoringModelVersionId(String scoringModelVersionId) {
		this.scoringModelVersionId = scoringModelVersionId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="REVIEW_SCOPE_ID")
	public ReviewScopeRef getReviewScopeRef() {
		return this.reviewScopeRef;
	}

	public void setReviewScopeRef(ReviewScopeRef reviewScopeRef) {
		this.reviewScopeRef = reviewScopeRef;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="DEFAULT_REVIEW_TYPE_ID")
	public ReviewTypeRef getReviewTypeRef() {
		return this.reviewTypeRef;
	}

	public void setReviewTypeRef(ReviewTypeRef reviewTypeRef) {
		this.reviewTypeRef = reviewTypeRef;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="SCORING_MODEL_ID", nullable=false)
	public ScoringModel getScoringModel() {
		return this.scoringModel;
	}

	public void setScoringModel(ScoringModel scoringModel) {
		this.scoringModel = scoringModel;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="SCORING_MODEL_VERSION_STATUS_ID", nullable=false)
	public ScoringModelVersionStatusRef getScoringModelVersionStatusRef() {
		return this.scoringModelVersionStatusRef;
	}

	public void setScoringModelVersionStatusRef(ScoringModelVersionStatusRef scoringModelVersionStatusRef) {
		this.scoringModelVersionStatusRef = scoringModelVersionStatusRef;
	}


    @Column(name="MODEL_VER_NUM", nullable=false, precision=3, scale=0)
	public short getModelVerNum() {
		return this.modelVerNum;
	}

	public void setModelVerNum(short modelVerNum) {
		this.modelVerNum = modelVerNum;
	}


    @Column(name="MODEL_SCORE_THRESHOLD", nullable=false)
	public float getModelScoreThreshold() {
		return this.modelScoreThreshold;
	}

	public void setModelScoreThreshold(float modelScoreThreshold) {
		this.modelScoreThreshold = modelScoreThreshold;
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


    @Column(name="MODEL_NAME", nullable=false, length=100)
	public String getModelName() {
		return this.modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}


    @Column(name="DESCRIPTION", nullable=false, length=100)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


    @Column(name="ALLOCATION_PCT", nullable=false)
	public int getAllocationPct() {
		return this.allocationPct;
	}

	public void setAllocationPct(int allocationPct) {
		this.allocationPct = allocationPct;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="scoringModelVersion")
	public Set<ModelScore> getModelScores() {
		return this.modelScores;
	}

	public void setModelScores(Set<ModelScore> modelScores) {
		this.modelScores = modelScores;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="scoringModelVersion")
	public Set<ScoringModelVersionFactor> getScoringModelVersionFactors() {
		return this.scoringModelVersionFactors;
	}

	public void setScoringModelVersionFactors(Set<ScoringModelVersionFactor> scoringModelVersionFactors) {
		this.scoringModelVersionFactors = scoringModelVersionFactors;
	}

}
