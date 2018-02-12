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
@Table(name="LENDER_REQUEST_STATUS_REF", uniqueConstraints = @UniqueConstraint(columnNames="CODE"))
@SuppressWarnings("serial")
public class LenderRequestStatusRef implements java.io.Serializable {
	private String lenderRequestStatusId;
	private String code;
	private String description;
	private Set<LenderRequest> lenderRequests = new HashSet<LenderRequest>(0);

	public LenderRequestStatusRef() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")

    @Column(name="LENDER_REQUEST_STATUS_ID", unique=true, nullable=false, length=36)
	public String getLenderRequestStatusId() {
		return this.lenderRequestStatusId;
	}

	public void setLenderRequestStatusId(String lenderRequestStatusId) {
		this.lenderRequestStatusId = lenderRequestStatusId;
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

	@OneToMany(fetch=FetchType.LAZY, mappedBy="lenderRequestStatusRef")
	public Set<LenderRequest> getLenderRequests() {
		return this.lenderRequests;
	}

	public void setLenderRequests(Set<LenderRequest> lenderRequests) {
		this.lenderRequests = lenderRequests;
	}

}
