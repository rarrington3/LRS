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
@Table(name="BINDER_REQUEST_STATUS_REF")
@SuppressWarnings("serial")
public class BinderRequestStatusRef implements java.io.Serializable {
	private String binderRequestStatusId;
	private String code;
	private String description;
	private Set<BinderRequest> binderRequests = new HashSet<BinderRequest>(0);

	public BinderRequestStatusRef() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")

    @Column(name="BINDER_REQUEST_STATUS_ID", unique=true, nullable=false, length=36)
	public String getBinderRequestStatusId() {
		return this.binderRequestStatusId;
	}

	public void setBinderRequestStatusId(String binderRequestStatusId) {
		this.binderRequestStatusId = binderRequestStatusId;
	}


    @Column(name="CODE", nullable=false, length=30)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}


    @Column(name="DESCRIPTION", nullable=false, length=30)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="binderRequestStatusRef")
	public Set<BinderRequest> getBinderRequests() {
		return this.binderRequests;
	}

	public void setBinderRequests(Set<BinderRequest> binderRequests) {
		this.binderRequests = binderRequests;
	}

}
