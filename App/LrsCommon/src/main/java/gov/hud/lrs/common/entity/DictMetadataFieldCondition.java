//DO NOT MODIFY: generated for HUD LRS
package gov.hud.lrs.common.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="DICT_METADATA_FIELD_CONDITION")
@SuppressWarnings("serial")
public class DictMetadataFieldCondition implements java.io.Serializable {
	private String metadataFieldCondId;
	private DictMetadataField conditionDictMetadataField;
	private DictMetadataField dictMetadataField;
	private String createdBy;
	private String updatedBy;
	private Date createdTs;
	private Date updatedTs;
	private String comparisonValues;
	private String conditionOperator;

	public DictMetadataFieldCondition() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")

    @Column(name="METADATA_FIELD_COND_ID", unique=true, nullable=false, length=36)
	public String getMetadataFieldCondId() {
		return this.metadataFieldCondId;
	}

	public void setMetadataFieldCondId(String metadataFieldCondId) {
		this.metadataFieldCondId = metadataFieldCondId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumns( {
        @JoinColumn(name="CONDITION_ENTITY_NAME", referencedColumnName="ENTITY_NAME", nullable=false),
        @JoinColumn(name="CONDITION_FIELD_NAME", referencedColumnName="FIELD_NAME", nullable=false) } )
	public DictMetadataField getConditionDictMetadataField() {
		return this.conditionDictMetadataField;
	}

	public void setConditionDictMetadataField(DictMetadataField conditionDictMetadataField) {
		this.conditionDictMetadataField = conditionDictMetadataField;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumns( {
        @JoinColumn(name="ENTITY_NAME", referencedColumnName="ENTITY_NAME", nullable=false),
        @JoinColumn(name="FIELD_NAME", referencedColumnName="FIELD_NAME", nullable=false) } )
	public DictMetadataField getDictMetadataField() {
		return this.dictMetadataField;
	}

	public void setDictMetadataField(DictMetadataField dictMetadataField) {
		this.dictMetadataField = dictMetadataField;
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


    @Column(name="COMPARISON_VALUES", length=200)
	public String getComparisonValues() {
		return this.comparisonValues;
	}

	public void setComparisonValues(String comparisonValues) {
		this.comparisonValues = comparisonValues;
	}


    @Column(name="CONDITION_OPERATOR", length=16)
	public String getConditionOperator() {
		return this.conditionOperator;
	}

	public void setConditionOperator(String conditionOperator) {
		this.conditionOperator = conditionOperator;
	}

}
