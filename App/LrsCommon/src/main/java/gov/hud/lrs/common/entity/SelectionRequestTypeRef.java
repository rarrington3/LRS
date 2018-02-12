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
@Table(name="SELECTION_REQUEST_TYPE_REF", uniqueConstraints = @UniqueConstraint(columnNames="CODE"))
@SuppressWarnings("serial")
public class SelectionRequestTypeRef implements java.io.Serializable {

	public static final String UNIVERSE = "UNIVERSE";
	public static final String LENDER_SELF_REPORT = "LENDER_SELF_REPORT";
	public static final String LENDER_MONITORING = "LENDER_MONITORING";
	public static final String FHA_MANUAL = "FHA_MANUAL";

	private String selectionRequestTypeId;
	private String code;
	private String description;
	private Set<SelectionRequest> selectionRequests = new HashSet<SelectionRequest>(0);

	public SelectionRequestTypeRef() {
	}

    @Id
    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")
    @GeneratedValue(generator="generator")
	@Column(name="SELECTION_REQUEST_TYPE_ID", unique=true, nullable=false, length=36)
	public String getSelectionRequestTypeId() {
		return this.selectionRequestTypeId;
	}

	public void setSelectionRequestTypeId(String selectionRequestTypeId) {
		this.selectionRequestTypeId = selectionRequestTypeId;
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

	@OneToMany(fetch=FetchType.LAZY, mappedBy="selectionRequestTypeRef")
	public Set<SelectionRequest> getSelectionRequests() {
		return this.selectionRequests;
	}

	public void setSelectionRequests(Set<SelectionRequest> selectionRequests) {
		this.selectionRequests = selectionRequests;
	}

}
