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
@Table(name="REVIEW_TYPE_REF")
@SuppressWarnings("serial")
public class ReviewTypeRef implements java.io.Serializable {
	private String reviewTypeId;
	private String reviewTypeCd;
	private String description;
	private Set<Review> reviews = new HashSet<Review>(0);
	private Set<QatreeQstnRules> qatreeQstnRuleses = new HashSet<QatreeQstnRules>(0);
	private Set<ReviewTypeDefect> reviewTypeDefects = new HashSet<ReviewTypeDefect>(0);
	private Set<LoanSelectionPending> loanSelectionPendings = new HashSet<LoanSelectionPending>(0);
	private Set<ManualSelectionRequest> manualSelectionRequests = new HashSet<ManualSelectionRequest>(0);
	private Set<LoanSelection> loanSelections = new HashSet<LoanSelection>(0);
	private Set<ScoringModel> scoringModels = new HashSet<ScoringModel>(0);
	private Set<ScoringModelVersion> scoringModelVersions = new HashSet<ScoringModelVersion>(0);
	private Set<Batch> batches = new HashSet<Batch>(0);
	private Set<LenderMonitoringSelectionRequest> lenderMonitoringSelectionRequests = new HashSet<LenderMonitoringSelectionRequest>(0);

	public ReviewTypeRef() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")
    
    @Column(name="REVIEW_TYPE_ID", unique=true, nullable=false, length=36)
	public String getReviewTypeId() {
		return this.reviewTypeId;
	}

	public void setReviewTypeId(String reviewTypeId) {
		this.reviewTypeId = reviewTypeId;
	}

    
    @Column(name="REVIEW_TYPE_CD", length=1)
	public String getReviewTypeCd() {
		return this.reviewTypeCd;
	}

	public void setReviewTypeCd(String reviewTypeCd) {
		this.reviewTypeCd = reviewTypeCd;
	}

    
    @Column(name="DESCRIPTION", length=50)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="reviewTypeRef")
	public Set<Review> getReviews() {
		return this.reviews;
	}

	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="reviewTypeRef")
	public Set<QatreeQstnRules> getQatreeQstnRuleses() {
		return this.qatreeQstnRuleses;
	}

	public void setQatreeQstnRuleses(Set<QatreeQstnRules> qatreeQstnRuleses) {
		this.qatreeQstnRuleses = qatreeQstnRuleses;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="reviewTypeRef")
	public Set<ReviewTypeDefect> getReviewTypeDefects() {
		return this.reviewTypeDefects;
	}

	public void setReviewTypeDefects(Set<ReviewTypeDefect> reviewTypeDefects) {
		this.reviewTypeDefects = reviewTypeDefects;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="reviewTypeRef")
	public Set<LoanSelectionPending> getLoanSelectionPendings() {
		return this.loanSelectionPendings;
	}

	public void setLoanSelectionPendings(Set<LoanSelectionPending> loanSelectionPendings) {
		this.loanSelectionPendings = loanSelectionPendings;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="reviewTypeRef")
	public Set<ManualSelectionRequest> getManualSelectionRequests() {
		return this.manualSelectionRequests;
	}

	public void setManualSelectionRequests(Set<ManualSelectionRequest> manualSelectionRequests) {
		this.manualSelectionRequests = manualSelectionRequests;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="reviewTypeRef")
	public Set<LoanSelection> getLoanSelections() {
		return this.loanSelections;
	}

	public void setLoanSelections(Set<LoanSelection> loanSelections) {
		this.loanSelections = loanSelections;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="reviewTypeRef")
	public Set<ScoringModel> getScoringModels() {
		return this.scoringModels;
	}

	public void setScoringModels(Set<ScoringModel> scoringModels) {
		this.scoringModels = scoringModels;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="reviewTypeRef")
	public Set<ScoringModelVersion> getScoringModelVersions() {
		return this.scoringModelVersions;
	}

	public void setScoringModelVersions(Set<ScoringModelVersion> scoringModelVersions) {
		this.scoringModelVersions = scoringModelVersions;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="reviewTypeRef")
	public Set<Batch> getBatches() {
		return this.batches;
	}

	public void setBatches(Set<Batch> batches) {
		this.batches = batches;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="reviewTypeRef")
	public Set<LenderMonitoringSelectionRequest> getLenderMonitoringSelectionRequests() {
		return this.lenderMonitoringSelectionRequests;
	}

	public void setLenderMonitoringSelectionRequests(Set<LenderMonitoringSelectionRequest> lenderMonitoringSelectionRequests) {
		this.lenderMonitoringSelectionRequests = lenderMonitoringSelectionRequests;
	}

}
