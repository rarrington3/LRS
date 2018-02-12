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
@Table(name="SCORING_MODEL_TYPE_REF", uniqueConstraints = @UniqueConstraint(columnNames="CODE"))
@SuppressWarnings("serial")
public class ScoringModelTypeRef implements java.io.Serializable {
	private String scoringModelTypeId;
	private String code;
	private String description;
	private Set<ScoringModel> scoringModels = new HashSet<ScoringModel>(0);

	public ScoringModelTypeRef() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")

    @Column(name="SCORING_MODEL_TYPE_ID", unique=true, nullable=false, length=36)
	public String getScoringModelTypeId() {
		return this.scoringModelTypeId;
	}

	public void setScoringModelTypeId(String scoringModelTypeId) {
		this.scoringModelTypeId = scoringModelTypeId;
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

	@OneToMany(fetch=FetchType.LAZY, mappedBy="scoringModelTypeRef")
	public Set<ScoringModel> getScoringModels() {
		return this.scoringModels;
	}

	public void setScoringModels(Set<ScoringModel> scoringModels) {
		this.scoringModels = scoringModels;
	}

}
