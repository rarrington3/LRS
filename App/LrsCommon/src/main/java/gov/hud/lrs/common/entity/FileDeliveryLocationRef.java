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
@Table(name="FILE_DELIVERY_LOCATION_REF", uniqueConstraints = @UniqueConstraint(columnNames="CODE"))
@SuppressWarnings("serial")
public class FileDeliveryLocationRef implements java.io.Serializable {
	private String fileDeliveryLocationId;
	private String code;
	private String description;
	private Set<LenderMonitoringSelectionRequest> lenderMonitoringSelectionRequests = new HashSet<LenderMonitoringSelectionRequest>(0);

	public FileDeliveryLocationRef() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")

    @Column(name="FILE_DELIVERY_LOCATION_ID", unique=true, nullable=false, length=36)
	public String getFileDeliveryLocationId() {
		return this.fileDeliveryLocationId;
	}

	public void setFileDeliveryLocationId(String fileDeliveryLocationId) {
		this.fileDeliveryLocationId = fileDeliveryLocationId;
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

	@OneToMany(fetch=FetchType.LAZY, mappedBy="fileDeliveryLocationRef")
	public Set<LenderMonitoringSelectionRequest> getLenderMonitoringSelectionRequests() {
		return this.lenderMonitoringSelectionRequests;
	}

	public void setLenderMonitoringSelectionRequests(Set<LenderMonitoringSelectionRequest> lenderMonitoringSelectionRequests) {
		this.lenderMonitoringSelectionRequests = lenderMonitoringSelectionRequests;
	}

}
