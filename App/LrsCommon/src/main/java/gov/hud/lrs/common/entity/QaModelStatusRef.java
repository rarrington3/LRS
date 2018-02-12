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
@Table(name="QA_MODEL_STATUS_REF", uniqueConstraints = @UniqueConstraint(columnNames="CODE"))
@SuppressWarnings("serial")
public class QaModelStatusRef implements java.io.Serializable {
	private String qaModelStatusId;
	private String description;
	private String code;
	private Set<QaModel> qaModels = new HashSet<QaModel>(0);

	public QaModelStatusRef() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")

    @Column(name="QA_MODEL_STATUS_ID", unique=true, nullable=false, length=36)
	public String getQaModelStatusId() {
		return this.qaModelStatusId;
	}

	public void setQaModelStatusId(String qaModelStatusId) {
		this.qaModelStatusId = qaModelStatusId;
	}


    @Column(name="DESCRIPTION", nullable=false, length=30)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


    @Column(name="CODE", unique=true, nullable=false, length=1)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="qaModelStatusRef")
	public Set<QaModel> getQaModels() {
		return this.qaModels;
	}

	public void setQaModels(Set<QaModel> qaModels) {
		this.qaModels = qaModels;
	}

}
