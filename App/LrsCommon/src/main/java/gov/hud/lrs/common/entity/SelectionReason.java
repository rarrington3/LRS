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
@Table(name="SELECTION_REASON", uniqueConstraints = @UniqueConstraint(columnNames="CODE"))
@SuppressWarnings("serial")
public class SelectionReason implements java.io.Serializable {
	private String selectionReasonId;
	private ConsolidatedSelectionReason consolidatedSelectionReason;
	private ScoringModel scoringModel;
	private Character batchesAllowedInd;
	private String capacityHandlingRule;
	private Short priority;
	private String processingCycle;
	private Character requireFullQatreeInd;
	private String selectReviewType;
	private String samplingMethod;
	private String createdBy;
	private String updatedBy;
	private Date createdTs;
	private Date updatedTs;
	private String description;
	private Character countsTwdMnthlyCapacityInd;
	private Character automatedInd;
	private String code;
	private Set<ManualSelectionRequest> manualSelectionRequests = new HashSet<ManualSelectionRequest>(0);
	private Set<LoanSelection> loanSelections = new HashSet<LoanSelection>(0);
	private Set<QatreeQstnRules> qatreeQstnRuleses = new HashSet<QatreeQstnRules>(0);
	private Set<ScoringModel> scoringModels = new HashSet<ScoringModel>(0);
	private Set<RvwLocationReason> rvwLocationReasons = new HashSet<RvwLocationReason>(0);
	private Set<Review> reviews = new HashSet<Review>(0);
	private Set<LoanSelectionPending> loanSelectionPendings = new HashSet<LoanSelectionPending>(0);

	public SelectionReason() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")

    @Column(name="SELECTION_REASON_ID", unique=true, nullable=false, length=36)
	public String getSelectionReasonId() {
		return this.selectionReasonId;
	}

	public void setSelectionReasonId(String selectionReasonId) {
		this.selectionReasonId = selectionReasonId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="CONSOLIDATED_SELECTION_REASON_ID", nullable=false)
	public ConsolidatedSelectionReason getConsolidatedSelectionReason() {
		return this.consolidatedSelectionReason;
	}

	public void setConsolidatedSelectionReason(ConsolidatedSelectionReason consolidatedSelectionReason) {
		this.consolidatedSelectionReason = consolidatedSelectionReason;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="SCORING_MODEL_ID")
	public ScoringModel getScoringModel() {
		return this.scoringModel;
	}

	public void setScoringModel(ScoringModel scoringModel) {
		this.scoringModel = scoringModel;
	}


    @Column(name="BATCHES_ALLOWED_IND", length=1)
	public Character getBatchesAllowedInd() {
		return this.batchesAllowedInd;
	}

	public void setBatchesAllowedInd(Character batchesAllowedInd) {
		this.batchesAllowedInd = batchesAllowedInd;
	}


    @Column(name="CAPACITY_HANDLING_RULE", length=16)
	public String getCapacityHandlingRule() {
		return this.capacityHandlingRule;
	}

	public void setCapacityHandlingRule(String capacityHandlingRule) {
		this.capacityHandlingRule = capacityHandlingRule;
	}


    @Column(name="PRIORITY", precision=3, scale=0)
	public Short getPriority() {
		return this.priority;
	}

	public void setPriority(Short priority) {
		this.priority = priority;
	}


    @Column(name="PROCESSING_CYCLE", length=16)
	public String getProcessingCycle() {
		return this.processingCycle;
	}

	public void setProcessingCycle(String processingCycle) {
		this.processingCycle = processingCycle;
	}


    @Column(name="REQUIRE_FULL_QATREE_IND", length=1)
	public Character getRequireFullQatreeInd() {
		return this.requireFullQatreeInd;
	}

	public void setRequireFullQatreeInd(Character requireFullQatreeInd) {
		this.requireFullQatreeInd = requireFullQatreeInd;
	}


    @Column(name="SELECT_REVIEW_TYPE", length=16)
	public String getSelectReviewType() {
		return this.selectReviewType;
	}

	public void setSelectReviewType(String selectReviewType) {
		this.selectReviewType = selectReviewType;
	}


    @Column(name="SAMPLING_METHOD", length=16)
	public String getSamplingMethod() {
		return this.samplingMethod;
	}

	public void setSamplingMethod(String samplingMethod) {
		this.samplingMethod = samplingMethod;
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


    @Column(name="DESCRIPTION", length=50)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


    @Column(name="COUNTS_TWD_MNTHLY_CAPACITY_IND", length=1)
	public Character getCountsTwdMnthlyCapacityInd() {
		return this.countsTwdMnthlyCapacityInd;
	}

	public void setCountsTwdMnthlyCapacityInd(Character countsTwdMnthlyCapacityInd) {
		this.countsTwdMnthlyCapacityInd = countsTwdMnthlyCapacityInd;
	}


    @Column(name="AUTOMATED_IND", length=1)
	public Character getAutomatedInd() {
		return this.automatedInd;
	}

	public void setAutomatedInd(Character automatedInd) {
		this.automatedInd = automatedInd;
	}


    @Column(name="CODE", unique=true, nullable=false, length=30)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="selectionReason")
	public Set<ManualSelectionRequest> getManualSelectionRequests() {
		return this.manualSelectionRequests;
	}

	public void setManualSelectionRequests(Set<ManualSelectionRequest> manualSelectionRequests) {
		this.manualSelectionRequests = manualSelectionRequests;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="selectionReason")
	public Set<LoanSelection> getLoanSelections() {
		return this.loanSelections;
	}

	public void setLoanSelections(Set<LoanSelection> loanSelections) {
		this.loanSelections = loanSelections;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="selectionReason")
	public Set<QatreeQstnRules> getQatreeQstnRuleses() {
		return this.qatreeQstnRuleses;
	}

	public void setQatreeQstnRuleses(Set<QatreeQstnRules> qatreeQstnRuleses) {
		this.qatreeQstnRuleses = qatreeQstnRuleses;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="selectionReason")
	public Set<ScoringModel> getScoringModels() {
		return this.scoringModels;
	}

	public void setScoringModels(Set<ScoringModel> scoringModels) {
		this.scoringModels = scoringModels;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="selectionReason")
	public Set<RvwLocationReason> getRvwLocationReasons() {
		return this.rvwLocationReasons;
	}

	public void setRvwLocationReasons(Set<RvwLocationReason> rvwLocationReasons) {
		this.rvwLocationReasons = rvwLocationReasons;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="selectionReason")
	public Set<Review> getReviews() {
		return this.reviews;
	}

	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="selectionReason")
	public Set<LoanSelectionPending> getLoanSelectionPendings() {
		return this.loanSelectionPendings;
	}

	public void setLoanSelectionPendings(Set<LoanSelectionPending> loanSelectionPendings) {
		this.loanSelectionPendings = loanSelectionPendings;
	}

}
