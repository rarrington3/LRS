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
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="BINDER_DELIVERY_LOCATION_REF")
@SuppressWarnings("serial")
public class BinderDeliveryLocationRef implements java.io.Serializable {
	private String binderDeliveryLocationId;
	private String code;
	private String description;
	private Set<ReviewLocation> reviewLocations = new HashSet<ReviewLocation>(0);

	public BinderDeliveryLocationRef() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")

    @Column(name="BINDER_DELIVERY_LOCATION_ID", unique=true, nullable=false, length=36)
	public String getBinderDeliveryLocationId() {
		return this.binderDeliveryLocationId;
	}

	public void setBinderDeliveryLocationId(String binderDeliveryLocationId) {
		this.binderDeliveryLocationId = binderDeliveryLocationId;
	}


    @Column(name="CODE", nullable=false, length=16)
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

	@OneToMany(fetch=FetchType.LAZY, mappedBy="binderDeliveryLocationRef")
	public Set<ReviewLocation> getReviewLocations() {
		return this.reviewLocations;
	}

	public void setReviewLocations(Set<ReviewLocation> reviewLocations) {
		this.reviewLocations = reviewLocations;
	}

}
