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
@Table(name="SCORING_MODEL_VERSION_STATUS_REF", uniqueConstraints = @UniqueConstraint(columnNames="CODE"))
@SuppressWarnings("serial")
public class ScoringModelVersionStatusRef implements java.io.Serializable {
	private String scoringModelVersionStatusId;
	private String code;
	private String description;
	private Set<ScoringModelVersion> scoringModelVersions = new HashSet<ScoringModelVersion>(0);

	public ScoringModelVersionStatusRef() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")

    @Column(name="SCORING_MODEL_VERSION_STATUS_ID", unique=true, nullable=false, length=36)
	public String getScoringModelVersionStatusId() {
		return this.scoringModelVersionStatusId;
	}

	public void setScoringModelVersionStatusId(String scoringModelVersionStatusId) {
		this.scoringModelVersionStatusId = scoringModelVersionStatusId;
	}


    @Column(name="CODE", unique=true, nullable=false, length=30)
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

	@OneToMany(fetch=FetchType.LAZY, mappedBy="scoringModelVersionStatusRef")
	public Set<ScoringModelVersion> getScoringModelVersions() {
		return this.scoringModelVersions;
	}

	public void setScoringModelVersions(Set<ScoringModelVersion> scoringModelVersions) {
		this.scoringModelVersions = scoringModelVersions;
	}

}
