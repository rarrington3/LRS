package gov.hud.lrs.common.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="JOB_EXECUTION_STATUS_REF", uniqueConstraints = @UniqueConstraint(columnNames="CODE"))
@SuppressWarnings("serial")
public class JobExecutionStatusRef implements Serializable {

	public static final String STARTED = "STARTED";
	public static final String COMPLETED = "COMPLETED";
	public static final String FAILED = "FAILED";

	private String jobExecutionStatusId;
	private String code;
	private String description;

	public JobExecutionStatusRef() {
	}

    @Id
    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")
    @GeneratedValue(generator="generator")
    @Column(name="JOB_EXECUTION_STATUS_ID", unique=true, nullable=false, length=36)
	public String getJobExecutionStatusId() {
		return this.jobExecutionStatusId;
	}

	public void setJobExecutionStatusId(String jobExecutionStatusId) {
		this.jobExecutionStatusId = jobExecutionStatusId;
	}

    @Column(name="CODE", unique=true, nullable=false, length=50)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

    @Column(name="DESCRIPTION", nullable=false, length=50)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
