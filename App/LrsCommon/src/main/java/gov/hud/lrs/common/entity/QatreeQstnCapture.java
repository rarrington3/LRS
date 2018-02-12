//DO NOT MODIFY: generated for HUD LRS 
package gov.hud.lrs.common.entity;

import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="QATREE_QSTN_CAPTURE")
@SuppressWarnings("serial")
public class QatreeQstnCapture implements java.io.Serializable {
	private QatreeQstnCaptureId id;
	private DictMetadataEntity dictMetadataEntity;
	private DictMetadataField dictMetadataField;
	private Qatree qatree;
	private String createdBy;
	private String updatedBy;
	private Date createdTs;
	private Date updatedTs;

	public QatreeQstnCapture() {
	}

    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="qatreeId", column=@Column(name="QATREE_ID", nullable=false, length=36) ), 
        @AttributeOverride(name="questionId", column=@Column(name="QUESTION_ID", nullable=false, length=36) ), 
        @AttributeOverride(name="entityName", column=@Column(name="ENTITY_NAME", nullable=false, length=60) ), 
        @AttributeOverride(name="fieldName", column=@Column(name="FIELD_NAME", nullable=false, length=60) ) } )
	public QatreeQstnCaptureId getId() {
		return this.id;
	}

	public void setId(QatreeQstnCaptureId id) {
		this.id = id;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ENTITY_NAME", nullable=false, insertable=false, updatable=false)
	public DictMetadataEntity getDictMetadataEntity() {
		return this.dictMetadataEntity;
	}

	public void setDictMetadataEntity(DictMetadataEntity dictMetadataEntity) {
		this.dictMetadataEntity = dictMetadataEntity;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumns( { 
        @JoinColumn(name="ENTITY_NAME", referencedColumnName="ENTITY_NAME", nullable=false, insertable=false, updatable=false), 
        @JoinColumn(name="FIELD_NAME", referencedColumnName="FIELD_NAME", nullable=false, insertable=false, updatable=false) } )
	public DictMetadataField getDictMetadataField() {
		return this.dictMetadataField;
	}

	public void setDictMetadataField(DictMetadataField dictMetadataField) {
		this.dictMetadataField = dictMetadataField;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="QATREE_ID", nullable=false, insertable=false, updatable=false)
	public Qatree getQatree() {
		return this.qatree;
	}

	public void setQatree(Qatree qatree) {
		this.qatree = qatree;
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

}
