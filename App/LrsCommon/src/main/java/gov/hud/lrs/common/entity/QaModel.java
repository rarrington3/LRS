//DO NOT MODIFY: generated for HUD LRS
package gov.hud.lrs.common.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="QA_MODEL")
@SuppressWarnings("serial")
public class QaModel implements java.io.Serializable {
	private String qaModelId;
	private QaModelStatusRef qaModelStatusRef;
	private String modelName;
	private Integer versionNum;
	private String description;
	private String createdBy;
	private Date createdTs;
	private String updatedBy;
	private Date updatedTs;
	private Date activationDate;
	private Set<DictMetadataFieldModel> dictMetadataFieldModels = new HashSet<DictMetadataFieldModel>(0);
	private Set<DictMetadataFieldSubjectArea> dictMetadataFieldSubjectAreas = new HashSet<DictMetadataFieldSubjectArea>(0);
	private Set<Qatree> qatrees = new HashSet<Qatree>(0);
	private Set<Defect> defects = new HashSet<Defect>(0);
	private Set<Review> reviews = new HashSet<Review>(0);

	public QaModel() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")

    @Column(name="QA_MODEL_ID", unique=true, nullable=false, length=36)
	public String getQaModelId() {
		return this.qaModelId;
	}

	public void setQaModelId(String qaModelId) {
		this.qaModelId = qaModelId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="QA_MODEL_STATUS_ID", nullable=false)
	public QaModelStatusRef getQaModelStatusRef() {
		return this.qaModelStatusRef;
	}

	public void setQaModelStatusRef(QaModelStatusRef qaModelStatusRef) {
		this.qaModelStatusRef = qaModelStatusRef;
	}


    @Column(name="MODEL_NAME", length=30)
	public String getModelName() {
		return this.modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}


    @Column(name="VERSION_NUM")
	public Integer getVersionNum() {
		return this.versionNum;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}


    @Column(name="DESCRIPTION")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


    @Column(name="CREATED_BY", length=6)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATED_TS", length=23)
	public Date getCreatedTs() {
		return this.createdTs;
	}

	public void setCreatedTs(Date createdTs) {
		this.createdTs = createdTs;
	}


    @Column(name="UPDATED_BY", length=6)
	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="UPDATED_TS", length=23)
	public Date getUpdatedTs() {
		return this.updatedTs;
	}

	public void setUpdatedTs(Date updatedTs) {
		this.updatedTs = updatedTs;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="ACTIVATION_DATE", length=23)
	public Date getActivationDate() {
		return this.activationDate;
	}

	public void setActivationDate(Date activationDate) {
		this.activationDate = activationDate;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="qaModel")
	public Set<DictMetadataFieldModel> getDictMetadataFieldModels() {
		return this.dictMetadataFieldModels;
	}

	public void setDictMetadataFieldModels(Set<DictMetadataFieldModel> dictMetadataFieldModels) {
		this.dictMetadataFieldModels = dictMetadataFieldModels;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="qaModel")
	public Set<DictMetadataFieldSubjectArea> getDictMetadataFieldSubjectAreas() {
		return this.dictMetadataFieldSubjectAreas;
	}

	public void setDictMetadataFieldSubjectAreas(Set<DictMetadataFieldSubjectArea> dictMetadataFieldSubjectAreas) {
		this.dictMetadataFieldSubjectAreas = dictMetadataFieldSubjectAreas;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="qaModel")
	public Set<Qatree> getQatrees() {
		return this.qatrees;
	}

	public void setQatrees(Set<Qatree> qatrees) {
		this.qatrees = qatrees;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="qaModel")
	public Set<Defect> getDefects() {
		return this.defects;
	}

	public void setDefects(Set<Defect> defects) {
		this.defects = defects;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="qaModel")
	public Set<Review> getReviews() {
		return this.reviews;
	}

	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	}

}
