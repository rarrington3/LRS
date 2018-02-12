package gov.hud.lrs.common.entity;

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
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="BINDER_REQUEST")
@SuppressWarnings("serial")
public class BinderRequest implements java.io.Serializable {

	private String binderRequestId;
	private BinderRequestSourceRef binderRequestSourceRef;
	private BinderRequestStatusRef binderRequestStatusRef;
	private LoanSelection loanSelection;
	private String caseNumber;
	private Date requestedDate;
	private Date dueDate;
	private String createdBy;
	private String updatedBy;
	private Date createdTs;
	private Date updatedTs;
	private Character isElectronicInd;
	private Date lenderSentDate;

	public BinderRequest() {
	}

	@Id
	@GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")
	@GeneratedValue(generator="generator")
	@Column(name="BINDER_REQUEST_ID", unique=true, nullable=false, length=36)
	public String getBinderRequestId() {
		return this.binderRequestId;
	}

	public void setBinderRequestId(String binderRequestId) {
		this.binderRequestId = binderRequestId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="BINDER_REQUEST_SOURCE_ID", nullable=false)
	public BinderRequestSourceRef getBinderRequestSourceRef() {
		return this.binderRequestSourceRef;
	}

	public void setBinderRequestSourceRef(BinderRequestSourceRef binderRequestSourceRef) {
		this.binderRequestSourceRef = binderRequestSourceRef;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="BINDER_REQUEST_STATUS_ID", nullable=false)
	public BinderRequestStatusRef getBinderRequestStatusRef() {
		return this.binderRequestStatusRef;
	}

	public void setBinderRequestStatusRef(BinderRequestStatusRef binderRequestStatusRef) {
		this.binderRequestStatusRef = binderRequestStatusRef;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="SELECTION_ID", nullable=false)
	public LoanSelection getLoanSelection() {
		return this.loanSelection;
	}

	public void setLoanSelection(LoanSelection loanSelection) {
		this.loanSelection = loanSelection;
	}


	@Column(name="CASE_NUMBER", length=11)
	public String getCaseNumber() {
		return this.caseNumber;
	}

	public void setCaseNumber(String caseNumber) {
		this.caseNumber = caseNumber;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="REQUESTED_DATE", nullable=false, length=23)
	public Date getRequestedDate() {
		return this.requestedDate;
	}

	public void setRequestedDate(Date requestedDate) {
		this.requestedDate = requestedDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DUE_DATE", length=23)
	public Date getDueDate() {
		return this.dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	@Column(name="CREATED_BY", nullable=false, length=6)
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
	@Column(name="CREATED_TS", nullable=false, length=23)
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

	@Column(name="IS_ELECTRONIC_IND", length=1)
	public Character getIsElectronicInd() {
		return this.isElectronicInd;
	}

	public void setIsElectronicInd(Character isElectronicInd) {
		this.isElectronicInd = isElectronicInd;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="LENDER_SENT_DATE", length=23)
	public Date getLenderSentDate() {
		return this.lenderSentDate;
	}

	public void setLenderSentDate(Date lenderSentDate) {
		this.lenderSentDate = lenderSentDate;
	}

}
