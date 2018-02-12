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
@Table(name="CANCELLATION_REASON_REF")
@SuppressWarnings("serial")
public class CancellationReasonRef implements java.io.Serializable {
	private String cancellationReasonId;
	private String code;
	private String description;
	private Set<Review> reviews = new HashSet<Review>(0);

	public CancellationReasonRef() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")

    @Column(name="CANCELLATION_REASON_ID", unique=true, nullable=false, length=36)
	public String getCancellationReasonId() {
		return this.cancellationReasonId;
	}

	public void setCancellationReasonId(String cancellationReasonId) {
		this.cancellationReasonId = cancellationReasonId;
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

	@OneToMany(fetch=FetchType.LAZY, mappedBy="cancellationReasonRef")
	public Set<Review> getReviews() {
		return this.reviews;
	}

	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	}

}
