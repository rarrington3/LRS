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
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name="LENDER_SELF_REPORT_SELECTION_REQUEST")
@SuppressWarnings("serial")
public class LenderSelfReportSelectionRequest implements java.io.Serializable {
	private String selectionRequestId;
	private SelectionRequest selectionRequest;
	private ReviewTypeRef reviewTypeRef;
	private String findingsDescription;
	private String correctiveActionsDescription;
	private char fraudDetectedInd;
	private char loanCoveredUnderSettlemtnWithHudInd;
	private String createdBy;
	private Date createdTs;
	private String updatedBy;
	private Date updatedTs;
	private Set<LenderSelfReportSelectionRequestFraudTypeRef> lenderSelfReportSelectionRequestFraudTypeRefs = new HashSet<LenderSelfReportSelectionRequestFraudTypeRef>(0);
	private Set<LenderSelfReportSelectionRequestDefect> lenderSelfReportSelectionRequestDefects = new HashSet<LenderSelfReportSelectionRequestDefect>(0);
	private Set<LenderSelfReportSelectionRequestFraudParticipantRef> lenderSelfReportSelectionRequestFraudParticipantRefs = new HashSet<LenderSelfReportSelectionRequestFraudParticipantRef>(0);

	public LenderSelfReportSelectionRequest() {
	}

	@Id
    @GenericGenerator(name="generator", strategy="foreign", parameters=@Parameter(name="property", value="selectionRequest"))
    @GeneratedValue(generator="generator")
    @Column(name="SELECTION_REQUEST_ID", unique=true, nullable=false, length=36)
	public String getSelectionRequestId() {
		return this.selectionRequestId;
	}

	public void setSelectionRequestId(String selectionRequestId) {
		this.selectionRequestId = selectionRequestId;
	}

	@OneToOne(fetch=FetchType.LAZY)
	@PrimaryKeyJoinColumn
	public SelectionRequest getSelectionRequest() {
		return this.selectionRequest;
	}

	public void setSelectionRequest(SelectionRequest selectionRequest) {
		this.selectionRequest = selectionRequest;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REVIEW_TYPE_ID")
	public ReviewTypeRef getReviewTypeRef() {
		return reviewTypeRef;
	}

	public void setReviewTypeRef(ReviewTypeRef reviewTypeRef) {
		this.reviewTypeRef = reviewTypeRef;
	}

    @Column(name="FINDINGS_DESCRIPTION")
	public String getFindingsDescription() {
		return this.findingsDescription;
	}

	public void setFindingsDescription(String findingsDescription) {
		this.findingsDescription = findingsDescription;
	}


    @Column(name="CORRECTIVE_ACTIONS_DESCRIPTION")
	public String getCorrectiveActionsDescription() {
		return this.correctiveActionsDescription;
	}

	public void setCorrectiveActionsDescription(String correctiveActionsDescription) {
		this.correctiveActionsDescription = correctiveActionsDescription;
	}

    @Column(name="CREATED_BY", nullable=false, length=6)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATED_TS", nullable=false, length=23)
	public Date getCreatedTs() {
		return this.createdTs;
	}

	public void setCreatedTs(Date createdTs) {
		this.createdTs = createdTs;
	}

    @Column(name="UPDATED_BY", length=6)
	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="UPDATED_TS", length=23)
	public Date getUpdatedTs() {
		return this.updatedTs;
	}

	public void setUpdatedTs(Date updatedTs) {
		this.updatedTs = updatedTs;
	}

    @Column(name="FRAUD_DETECTED_IND", nullable=false, length=1)
	public char getFraudDetectedInd() {
		return this.fraudDetectedInd;
	}

	public void setFraudDetectedInd(char fraudDetectedInd) {
		this.fraudDetectedInd = fraudDetectedInd;
	}

    @Column(name="LOAN_COVERED_UNDER_SETTLEMTN_WITH_HUD_IND", nullable=false, length=1)
	public char getLoanCoveredUnderSettlemtnWithHudInd() {
		return this.loanCoveredUnderSettlemtnWithHudInd;
	}

	public void setLoanCoveredUnderSettlemtnWithHudInd(char loanCoveredUnderSettlemtnWithHudInd) {
		this.loanCoveredUnderSettlemtnWithHudInd = loanCoveredUnderSettlemtnWithHudInd;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="lenderSelfReportSelectionRequest")
	public Set<LenderSelfReportSelectionRequestFraudTypeRef> getLenderSelfReportSelectionRequestFraudTypeRefs() {
		return this.lenderSelfReportSelectionRequestFraudTypeRefs;
	}

	public void setLenderSelfReportSelectionRequestFraudTypeRefs(Set<LenderSelfReportSelectionRequestFraudTypeRef> lenderSelfReportSelectionRequestFraudTypeRefs) {
		this.lenderSelfReportSelectionRequestFraudTypeRefs = lenderSelfReportSelectionRequestFraudTypeRefs;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="lenderSelfReportSelectionRequest")
	public Set<LenderSelfReportSelectionRequestDefect> getLenderSelfReportSelectionRequestDefects() {
		return this.lenderSelfReportSelectionRequestDefects;
	}

	public void setLenderSelfReportSelectionRequestDefects(Set<LenderSelfReportSelectionRequestDefect> lenderSelfReportSelectionRequestDefects) {
		this.lenderSelfReportSelectionRequestDefects = lenderSelfReportSelectionRequestDefects;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="lenderSelfReportSelectionRequest")
	public Set<LenderSelfReportSelectionRequestFraudParticipantRef> getLenderSelfReportSelectionRequestFraudParticipantRefs() {
		return this.lenderSelfReportSelectionRequestFraudParticipantRefs;
	}

	public void setLenderSelfReportSelectionRequestFraudParticipantRefs(Set<LenderSelfReportSelectionRequestFraudParticipantRef> lenderSelfReportSelectionRequestFraudParticipantRefs) {
		this.lenderSelfReportSelectionRequestFraudParticipantRefs = lenderSelfReportSelectionRequestFraudParticipantRefs;
	}

}
