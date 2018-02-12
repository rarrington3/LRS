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
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="JOB")
@SuppressWarnings("serial")
public class Job implements java.io.Serializable {

	private String jobId;
	private JobTypeRef jobTypeRef;
	private JobStatusRef jobStatusRef;
	private WorkflowServerInfo workflowServerInfo;
	private String createdBy;
	private Date createdTs;
	private String updatedBy;
	private Date updatedTs;
	private Character autoSelectionIndicator;
	private Set<JobExecution> jobExecutions = new HashSet<JobExecution>(0);
	private Set<JobParameter> jobParameters = new HashSet<JobParameter>(0);

	public Job() {
	}

    @Id
    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")
    @GeneratedValue(generator="generator")
    @Column(name="JOB_ID", unique=true, nullable=false, length=36)
	public String getJobId() {
		return this.jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="JOB_TYPE_ID", nullable=false)
	public JobTypeRef getJobTypeRef() {
		return this.jobTypeRef;
	}

	public void setJobTypeRef(JobTypeRef jobTypeRef) {
		this.jobTypeRef = jobTypeRef;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="JOB_STATUS_ID", nullable=false)
	public JobStatusRef getJobStatusRef() {
		return this.jobStatusRef;
	}

	public void setJobStatusRef(JobStatusRef jobStatusRef) {
		this.jobStatusRef = jobStatusRef;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CREATED_BY_SERVER_ID", nullable=true)
	public WorkflowServerInfo getWorkflowServerInfo() {
		return this.workflowServerInfo;
	}

	public void setWorkflowServerInfo(WorkflowServerInfo workflowServerInfo) {
		this.workflowServerInfo = workflowServerInfo;
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

	@Column(name="AUTO_SELECTION_INDICATOR", nullable=false, length=1)
	public Character getAutoSelectionIndicator() {
		return this.autoSelectionIndicator;
	}

	public void setAutoSelectionIndicator(Character autoSelectionIndicator) {
		this.autoSelectionIndicator = autoSelectionIndicator;
	}
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="job")
	public Set<JobExecution> getJobExecutions() {
		return this.jobExecutions;
	}

	public void setJobExecutions(Set<JobExecution> jobExecutions) {
		this.jobExecutions = jobExecutions;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="job")
	public Set<JobParameter> getJobParameters() {
		return this.jobParameters;
	}

	public void setJobParameters(Set<JobParameter> jobParameters) {
		this.jobParameters = jobParameters;
	}

}
