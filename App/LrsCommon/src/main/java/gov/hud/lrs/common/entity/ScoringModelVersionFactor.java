//DO NOT MODIFY: generated for HUD LRS 
package gov.hud.lrs.common.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="SCORING_MODEL_VERSION_FACTOR")
@SuppressWarnings("serial")
public class ScoringModelVersionFactor implements java.io.Serializable {
	private ScoringModelVersionFactorId id;
	private ScoringFactor scoringFactor;
	private ScoringModelVersion scoringModelVersion;
	private String fieldName;
	private String comments;
	private String entityName;
	private BigDecimal weight;
	private String createdBy;
	private String updatedBy;
	private Date createdTs;
	private Date updatedTs;

	public ScoringModelVersionFactor() {
	}

    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="scoringFactorId", column=@Column(name="SCORING_FACTOR_ID", nullable=false, length=36) ), 
        @AttributeOverride(name="scoringModelVersionId", column=@Column(name="SCORING_MODEL_VERSION_ID", nullable=false, length=36) ) } )
	public ScoringModelVersionFactorId getId() {
		return this.id;
	}

	public void setId(ScoringModelVersionFactorId id) {
		this.id = id;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="SCORING_FACTOR_ID", nullable=false, insertable=false, updatable=false)
	public ScoringFactor getScoringFactor() {
		return this.scoringFactor;
	}

	public void setScoringFactor(ScoringFactor scoringFactor) {
		this.scoringFactor = scoringFactor;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="SCORING_MODEL_VERSION_ID", nullable=false, insertable=false, updatable=false)
	public ScoringModelVersion getScoringModelVersion() {
		return this.scoringModelVersion;
	}

	public void setScoringModelVersion(ScoringModelVersion scoringModelVersion) {
		this.scoringModelVersion = scoringModelVersion;
	}

    
    @Column(name="FIELD_NAME", length=60)
	public String getFieldName() {
		return this.fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

    
    @Column(name="COMMENTS")
	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

    
    @Column(name="ENTITY_NAME", length=60)
	public String getEntityName() {
		return this.entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

    
    @Column(name="WEIGHT", nullable=false, precision=10, scale=5)
	public BigDecimal getWeight() {
		return this.weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
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
