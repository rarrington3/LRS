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
@Table(name="DICT_METADATA_FIELD_SUBJECT_AREA")
@SuppressWarnings("serial")
public class DictMetadataFieldSubjectArea implements java.io.Serializable {
	private String dictMetadataFieldSubjectAreaId;
	private QaModel qaModel;
	private String subjectArea;
	private int displayOrder;
	private String createdBy;
	private String updatedBy;
	private Date createdTs;
	private Date updatedTs;
	private Set<DictMetadataFieldModel> dictMetadataFieldModels = new HashSet<DictMetadataFieldModel>(0);

	public DictMetadataFieldSubjectArea() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")

    @Column(name="DICT_METADATA_FIELD_SUBJECT_AREA_ID", unique=true, nullable=false, length=36)
	public String getDictMetadataFieldSubjectAreaId() {
		return this.dictMetadataFieldSubjectAreaId;
	}

	public void setDictMetadataFieldSubjectAreaId(String dictMetadataFieldSubjectAreaId) {
		this.dictMetadataFieldSubjectAreaId = dictMetadataFieldSubjectAreaId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="QA_MODEL_ID", nullable=false)
	public QaModel getQaModel() {
		return this.qaModel;
	}

	public void setQaModel(QaModel qaModel) {
		this.qaModel = qaModel;
	}


    @Column(name="SUBJECT_AREA", nullable=false, length=60)
	public String getSubjectArea() {
		return this.subjectArea;
	}

	public void setSubjectArea(String subjectArea) {
		this.subjectArea = subjectArea;
	}


    @Column(name="DISPLAY_ORDER", nullable=false)
	public int getDisplayOrder() {
		return this.displayOrder;
	}

	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}


    @Column(name="CREATED_BY", length=6)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


    @Column(name="UPDATED_BY", length=6)
	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATED_TS", length=23)
	public Date getCreatedTs() {
		return this.createdTs;
	}

	public void setCreatedTs(Date createdTs) {
		this.createdTs = createdTs;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="UPDATED_TS", length=23)
	public Date getUpdatedTs() {
		return this.updatedTs;
	}

	public void setUpdatedTs(Date updatedTs) {
		this.updatedTs = updatedTs;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="dictMetadataFieldSubjectArea")
	public Set<DictMetadataFieldModel> getDictMetadataFieldModels() {
		return this.dictMetadataFieldModels;
	}

	public void setDictMetadataFieldModels(Set<DictMetadataFieldModel> dictMetadataFieldModels) {
		this.dictMetadataFieldModels = dictMetadataFieldModels;
	}

}
