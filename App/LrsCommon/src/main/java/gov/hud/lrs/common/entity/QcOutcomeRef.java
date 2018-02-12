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
@Table(name="QC_OUTCOME_REF", uniqueConstraints = @UniqueConstraint(columnNames="CODE"))
@SuppressWarnings("serial")
public class QcOutcomeRef implements java.io.Serializable {
	private String qcOutcomeId;
	private String code;
	private String description;
	private Set<Review> reviews = new HashSet<Review>(0);

	public QcOutcomeRef() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")

    @Column(name="QC_OUTCOME_ID", unique=true, nullable=false, length=36)
	public String getQcOutcomeId() {
		return this.qcOutcomeId;
	}

	public void setQcOutcomeId(String qcOutcomeId) {
		this.qcOutcomeId = qcOutcomeId;
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

	@OneToMany(fetch=FetchType.LAZY, mappedBy="qcOutcomeRef")
	public Set<Review> getReviews() {
		return this.reviews;
	}

	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	}

}
