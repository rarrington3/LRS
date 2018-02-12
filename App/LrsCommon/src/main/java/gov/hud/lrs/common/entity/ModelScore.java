package gov.hud.lrs.common.entity;

import java.io.Serializable;
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
@Table(name="MODEL_SCORE", uniqueConstraints = @UniqueConstraint(columnNames={"SELECTION_ID", "SCORING_MODEL_VERSION_ID"}))
@SuppressWarnings("serial")
public class ModelScore implements Serializable {

	private String modelScoreId;
	private LoanSelectionCaseSummary loanSelectionCaseSummary;
	private ScoringModelVersion scoringModelVersion;
	private float modelScore;
	private String createdBy;
	private String updatedBy;
	private Date createdTs;
	private Date updatedTs;
	private Character autoSelectionIndicator;

	public ModelScore() {
	}

	@Id
    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")
    @GeneratedValue(generator="generator")
    @Column(name="MODEL_SCORE_ID", unique=true, nullable=false, length=36)
	public String getModelScoreId() {
		return this.modelScoreId;
	}

	public void setModelScoreId(String modelScoreId) {
		this.modelScoreId = modelScoreId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="SELECTION_ID", nullable=false)
	public LoanSelectionCaseSummary getLoanSelectionCaseSummary() {
		return this.loanSelectionCaseSummary;
	}

	public void setLoanSelectionCaseSummary(LoanSelectionCaseSummary loanSelectionCaseSummary) {
		this.loanSelectionCaseSummary = loanSelectionCaseSummary;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="SCORING_MODEL_VERSION_ID", nullable=false)
	public ScoringModelVersion getScoringModelVersion() {
		return this.scoringModelVersion;
	}

	public void setScoringModelVersion(ScoringModelVersion scoringModelVersion) {
		this.scoringModelVersion = scoringModelVersion;
	}

    @Column(name="MODEL_SCORE")
	public float getModelScore() {
		return this.modelScore;
	}

	public void setModelScore(float modelScore) {
		this.modelScore = modelScore;
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
	
	@Column(name="AUTO_SELECTION_INDICATOR", nullable=false, length=1)
	public Character getAutoSelectionIndicator() {
		return this.autoSelectionIndicator;
	}

	public void setAutoSelectionIndicator(Character autoSelectionIndicator) {
		this.autoSelectionIndicator = autoSelectionIndicator;
	}

}
