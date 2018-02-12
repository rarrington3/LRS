package gov.hud.lrs.common.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="BATCH_STATUS_REF", uniqueConstraints = @UniqueConstraint(columnNames="CODE"))
@SuppressWarnings("serial")
public class BatchStatusRef implements java.io.Serializable {

	public static final String SELECTED = "SELECTED";
	public static final String DISTRIBUTED = "DISTRIBUTED";
	public static final String ASSIGNED = "ASSIGNED";
	public static final String REQUESTED = "REQUESTED";
	public static final String UNDER_REVIEW = "UNDER_REVIEW";
	public static final String UNDER_BATCH_REVIEW = "UNDER_BATCH_REVIEW";
	public static final String PENDING_LENDER_RESPONSE = "PENDING_LENDER_RESPONSE";
	public static final String COMPLETED = "COMPLETED";
	public static final String CANCELLED = "CANCELLED";

	private String batchStatusId;
	private String code;
	private String description;
	private Set<Batch> batches = new HashSet<Batch>(0);

	public BatchStatusRef() {
	}

	@Id
	@GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")
	@GeneratedValue(generator="generator")
	@Column(name="BATCH_STATUS_ID", unique=true, nullable=false, length=36)
	public String getBatchStatusId() {
		return this.batchStatusId;
	}

	public void setBatchStatusId(String batchStatusId) {
		this.batchStatusId = batchStatusId;
	}

	@Column(name="CODE", unique=true, nullable=false, length=30)
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

	@OneToMany(fetch=FetchType.LAZY, mappedBy="batchStatusRef")
	public Set<Batch> getBatches() {
		return this.batches;
	}

	public void setBatches(Set<Batch> batches) {
		this.batches = batches;
	}

}
