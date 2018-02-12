package gov.hud.lrs.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="JOB_TYPE_REF", uniqueConstraints = @UniqueConstraint(columnNames="CODE"))
@SuppressWarnings("serial")
public class JobTypeRef implements java.io.Serializable {

	public static final String ENDORSEMENT_SELECTION = "ENDORSEMENT_SELECTION";
	public static final String EARLY_CLAIM_SELECTION = "EARLY_CLAIM_SELECTION";
	public static final String EARLY_PAYMENT_DEFAULT_SELECTION = "EARLY_PAYMENT_DEFAULT_SELECTION";
	public static final String NATIONAL_QC_SELECTION = "NATIONAL_QC_SELECTION";
	public static final String LENDER_MONITORING_SELECTION = "LENDER_MONITORING_SELECTION";
	public static final String AGGREGATION = "AGGREGATION";
	public static final String DISTRIBUTION = "DISTRIBUTION";
	public static final String LATE_BINDERS = "LATE_BINDERS";
	public static final String BINDER_REQUEST = "BINDER_REQUEST";
	public static final String BINDER_RECEIPT = "BINDER_RECEIPT";
	public static final String REMAINING_MONTHLY_CAPACITY = "REMAINING_MONTHLY_CAPACITY";
	public static final String LATE_LENDER_REQUESTS = "LATE_LENDER_REQUESTS";
	public static final String LENDER_MONITORING_EMAIL = "LENDER_MONITORING_EMAIL";
	public static final String DAILY_COMBINED_LENDER_EMAIL = "DAILY_COMBINED_LENDER_EMAIL";
	public static final String REFRESH_LENDERS = "REFRESH_LENDERS";
	public static final String SEND_EMAIL = "SEND_EMAIL";

	private String jobTypeId;
	private String code;
	private String description;

	public JobTypeRef() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")

    @Column(name="JOB_TYPE_ID", unique=true, nullable=false, length=36)
	public String getJobTypeId() {
		return this.jobTypeId;
	}

	public void setJobTypeId(String jobTypeId) {
		this.jobTypeId = jobTypeId;
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
