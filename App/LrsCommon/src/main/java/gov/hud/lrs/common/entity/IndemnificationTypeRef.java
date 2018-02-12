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
@Table(name="INDEMNIFICATION_TYPE_REF", uniqueConstraints = @UniqueConstraint(columnNames="CODE"))
@SuppressWarnings("serial")
public class IndemnificationTypeRef implements java.io.Serializable {
	private String indemnificationTypeId;
	private String description;
	private String code;
	private Set<ReviewLevel> reviewLevels = new HashSet<ReviewLevel>(0);

	public IndemnificationTypeRef() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")

    @Column(name="INDEMNIFICATION_TYPE_ID", unique=true, nullable=false, length=36)
	public String getIndemnificationTypeId() {
		return this.indemnificationTypeId;
	}

	public void setIndemnificationTypeId(String indemnificationTypeId) {
		this.indemnificationTypeId = indemnificationTypeId;
	}


    @Column(name="DESCRIPTION", nullable=false, length=30)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


    @Column(name="CODE", unique=true, nullable=false, length=30)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="indemnificationTypeRef")
	public Set<ReviewLevel> getReviewLevels() {
		return this.reviewLevels;
	}

	public void setReviewLevels(Set<ReviewLevel> reviewLevels) {
		this.reviewLevels = reviewLevels;
	}

}
