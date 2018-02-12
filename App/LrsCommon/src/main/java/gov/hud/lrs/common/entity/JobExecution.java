//DO NOT MODIFY: generated for HUD LRS
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
@Table(name="JOB_EXECUTION")
@SuppressWarnings("serial")
public class JobExecution implements java.io.Serializable {
	private String jobExecutionId;
	private Job job;
	private JobExecutionStatusRef jobExecutionStatusRef;
	private WorkflowServerInfo workflowServerInfo;
	private String exceptionDetails;
	private Date endedDt;
	private String createdBy;
	private Date createdTs;
	private String updatedBy;
	private Date updatedTs;

	public JobExecution() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")

    @Column(name="JOB_EXECUTION_ID", unique=true, nullable=false, length=36)
	public String getJobExecutionId() {
		return this.jobExecutionId;
	}

	public void setJobExecutionId(String jobExecutionId) {
		this.jobExecutionId = jobExecutionId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="JOB_ID", nullable=false)
	public Job getJob() {
		return this.job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="JOB_EXECUTION_STATUS_ID", nullable=false)
	public JobExecutionStatusRef getJobExecutionStatusRef() {
		return this.jobExecutionStatusRef;
	}

	public void setJobExecutionStatusRef(JobExecutionStatusRef jobExecutionStatusRef) {
		this.jobExecutionStatusRef = jobExecutionStatusRef;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PROCESSED_BY_SERVER_ID", nullable=true)
	public WorkflowServerInfo getWorkflowServerInfo() {
		return this.workflowServerInfo;
	}

	public void setWorkflowServerInfo(WorkflowServerInfo workflowServerInfo) {
		this.workflowServerInfo = workflowServerInfo;
	}

	@Column(name="EXCEPTION_DETAILS")
	public String getExceptionDetails() {
		return this.exceptionDetails;
	}

	public void setExceptionDetails(String exceptionDetails) {
		this.exceptionDetails = exceptionDetails;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="ENDED_DT", length=23)
	public Date getEndedDt() {
		return this.endedDt;
	}

	public void setEndedDt(Date endedDt) {
		this.endedDt = endedDt;
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

}
