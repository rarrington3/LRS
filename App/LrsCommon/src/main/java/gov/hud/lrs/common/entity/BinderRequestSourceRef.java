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
@Table(name="BINDER_REQUEST_SOURCE_REF", uniqueConstraints = @UniqueConstraint(columnNames="CODE"))
@SuppressWarnings("serial")
public class BinderRequestSourceRef implements java.io.Serializable {
	private String binderRequestSourceId;
	private String code;
	private String description;
	private Set<BinderRequest> binderRequests = new HashSet<BinderRequest>(0);

	public BinderRequestSourceRef() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")

    @Column(name="BINDER_REQUEST_SOURCE_ID", unique=true, nullable=false, length=36)
	public String getBinderRequestSourceId() {
		return this.binderRequestSourceId;
	}

	public void setBinderRequestSourceId(String binderRequestSourceId) {
		this.binderRequestSourceId = binderRequestSourceId;
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

	@OneToMany(fetch=FetchType.LAZY, mappedBy="binderRequestSourceRef")
	public Set<BinderRequest> getBinderRequests() {
		return this.binderRequests;
	}

	public void setBinderRequests(Set<BinderRequest> binderRequests) {
		this.binderRequests = binderRequests;
	}

}
