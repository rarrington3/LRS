//DO NOT MODIFY: generated for HUD LRS
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
@Table(name="SELECTION_SUB_REASON_REF", uniqueConstraints = @UniqueConstraint(columnNames="CODE"))
@SuppressWarnings("serial")
public class SelectionSubReasonRef implements java.io.Serializable {
	private String selectionSubReasonId;
	private String code;
	private String description;
	private Set<ManualSelectionRequest> manualSelectionRequests = new HashSet<ManualSelectionRequest>(0);

	public SelectionSubReasonRef() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")

    @Column(name="SELECTION_SUB_REASON_ID", unique=true, nullable=false, length=36)
	public String getSelectionSubReasonId() {
		return this.selectionSubReasonId;
	}

	public void setSelectionSubReasonId(String selectionSubReasonId) {
		this.selectionSubReasonId = selectionSubReasonId;
	}


    @Column(name="CODE", unique=true, nullable=false, length=4)
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

	@OneToMany(fetch=FetchType.LAZY, mappedBy="selectionSubReasonRef")
	public Set<ManualSelectionRequest> getManualSelectionRequests() {
		return this.manualSelectionRequests;
	}

	public void setManualSelectionRequests(Set<ManualSelectionRequest> manualSelectionRequests) {
		this.manualSelectionRequests = manualSelectionRequests;
	}

}
