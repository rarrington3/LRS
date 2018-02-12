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
@Table(name="UNIVERSE_REF")
@SuppressWarnings("serial")
public class UniverseRef implements java.io.Serializable {
	private String universeId;
	private String code;
	private String description;
	private Set<UniverseSelectionRequest> universeSelectionRequests = new HashSet<UniverseSelectionRequest>(0);

	public UniverseRef() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")

    @Column(name="UNIVERSE_ID", unique=true, nullable=false, length=36)
	public String getUniverseId() {
		return this.universeId;
	}

	public void setUniverseId(String universeId) {
		this.universeId = universeId;
	}


    @Column(name="CODE", nullable=false, length=4)
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

	@OneToMany(fetch=FetchType.LAZY, mappedBy="universeRef")
	public Set<UniverseSelectionRequest> getUniverseSelectionRequests() {
		return this.universeSelectionRequests;
	}

	public void setUniverseSelectionRequests(Set<UniverseSelectionRequest> universeSelectionRequests) {
		this.universeSelectionRequests = universeSelectionRequests;
	}

}
