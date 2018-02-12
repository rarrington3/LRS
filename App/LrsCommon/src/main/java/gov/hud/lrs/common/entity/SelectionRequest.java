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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="SELECTION_REQUEST")
@SuppressWarnings("serial")
public class SelectionRequest implements java.io.Serializable {
	private String selectionRequestId;
	private SelectionRequestTypeRef selectionRequestTypeRef;
	private String createdBy;
	private Date createdTs;
	private String updatedBy;
	private Date updatedTs;
	private Set<LoanSelectionCaseSummary> loanSelectionCaseSummaries = new HashSet<LoanSelectionCaseSummary>(0);
	private Set<Batch> batches = new HashSet<Batch>(0);
	private ManualSelectionRequest manualSelectionRequest;
	private LenderSelfReportSelectionRequest lenderSelfReportSelectionRequest;
	private UniverseSelectionRequest universeSelectionRequest;
	private LenderMonitoringSelectionRequest lenderMonitoringSelectionRequest;

	public SelectionRequest() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")
    
    @Column(name="SELECTION_REQUEST_ID", unique=true, nullable=false, length=36)
	public String getSelectionRequestId() {
		return this.selectionRequestId;
	}

	public void setSelectionRequestId(String selectionRequestId) {
		this.selectionRequestId = selectionRequestId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="SELECTION_REQUEST_TYPE_ID", nullable=false)
	public SelectionRequestTypeRef getSelectionRequestTypeRef() {
		return this.selectionRequestTypeRef;
	}

	public void setSelectionRequestTypeRef(SelectionRequestTypeRef selectionRequestTypeRef) {
		this.selectionRequestTypeRef = selectionRequestTypeRef;
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

	@OneToMany(fetch=FetchType.LAZY, mappedBy="selectionRequest")
	public Set<LoanSelectionCaseSummary> getLoanSelectionCaseSummaries() {
		return this.loanSelectionCaseSummaries;
	}

	public void setLoanSelectionCaseSummaries(Set<LoanSelectionCaseSummary> loanSelectionCaseSummaries) {
		this.loanSelectionCaseSummaries = loanSelectionCaseSummaries;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="selectionRequest")
	public Set<Batch> getBatches() {
		return this.batches;
	}

	public void setBatches(Set<Batch> batches) {
		this.batches = batches;
	}

@OneToOne(fetch=FetchType.LAZY, mappedBy="selectionRequest")
	public ManualSelectionRequest getManualSelectionRequest() {
		return this.manualSelectionRequest;
	}

	public void setManualSelectionRequest(ManualSelectionRequest manualSelectionRequest) {
		this.manualSelectionRequest = manualSelectionRequest;
	}

@OneToOne(fetch=FetchType.LAZY, mappedBy="selectionRequest")
	public LenderSelfReportSelectionRequest getLenderSelfReportSelectionRequest() {
		return this.lenderSelfReportSelectionRequest;
	}

	public void setLenderSelfReportSelectionRequest(LenderSelfReportSelectionRequest lenderSelfReportSelectionRequest) {
		this.lenderSelfReportSelectionRequest = lenderSelfReportSelectionRequest;
	}

@OneToOne(fetch=FetchType.LAZY, mappedBy="selectionRequest")
	public UniverseSelectionRequest getUniverseSelectionRequest() {
		return this.universeSelectionRequest;
	}

	public void setUniverseSelectionRequest(UniverseSelectionRequest universeSelectionRequest) {
		this.universeSelectionRequest = universeSelectionRequest;
	}

@OneToOne(fetch=FetchType.LAZY, mappedBy="selectionRequest")
	public LenderMonitoringSelectionRequest getLenderMonitoringSelectionRequest() {
		return this.lenderMonitoringSelectionRequest;
	}

	public void setLenderMonitoringSelectionRequest(LenderMonitoringSelectionRequest lenderMonitoringSelectionRequest) {
		this.lenderMonitoringSelectionRequest = lenderMonitoringSelectionRequest;
	}

}
