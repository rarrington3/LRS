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
@Table(name="JOB_STATUS_REF", uniqueConstraints = @UniqueConstraint(columnNames="CODE"))
@SuppressWarnings("serial")
public class JobStatusRef implements Serializable {

	public static final String PENDING = "PENDING";
	public static final String COMPLETED = "COMPLETED";
	public static final String CANCELLED = "CANCELLED";

	private String jobStatusId;
	private String code;
	private String description;

	public JobStatusRef() {
	}

    @Id
    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")
    @GeneratedValue(generator="generator")
    @Column(name="JOB_STATUS_ID", unique=true, nullable=false, length=36)
	public String getJobStatusId() {
		return this.jobStatusId;
	}

	public void setJobStatusId(String jobStatusId) {
		this.jobStatusId = jobStatusId;
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
