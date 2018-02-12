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
@Table(name="JOB_PARAMETER")
@SuppressWarnings("serial")
public class JobParameter implements java.io.Serializable {

	public static final String DATE_FORMAT = "MM/dd/yyyy";

	public static final String START_DATE = "Start Date";
	public static final String END_DATE = "End Date";
	public static final String AUTOMATIC_DATE = "Automatic Date";
	public static final String THROTTLING = "Throttling";
	public static final String REFRESH_LENDER_BATCH_SIZE = "REFRESH_LENDER_BATCH_SIZE";
	public static final String SELECTION_REQUEST_ID = "SELECTION_REQUEST_ID";

	private String jobParameterId;
	private Job job;
	private String name;
	private String value;
	private String createdBy;
	private Date createdTs;
	private String updatedBy;
	private Date updatedTs;

	public JobParameter() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")

    @Column(name="JOB_PARAMETER_ID", unique=true, nullable=false, length=36)
	public String getJobParameterId() {
		return this.jobParameterId;
	}

	public void setJobParameterId(String jobParameterId) {
		this.jobParameterId = jobParameterId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="JOB_ID", nullable=false)
	public Job getJob() {
		return this.job;
	}

	public void setJob(Job job) {
		this.job = job;
	}


    @Column(name="NAME", nullable=false, length=100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}


    @Column(name="VALUE", nullable=false, length=100)
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
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
