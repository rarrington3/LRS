//DO NOT MODIFY: generated for HUD LRS
package gov.hud.lrs.common.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="QATREE_QSTN_CONDITION")
@SuppressWarnings("serial")
public class QatreeQstnCondition implements java.io.Serializable {
	private String qatreeQstnConditionId;
	private Qatree qatree;
	private QatreeQuestion qatreeQuestion;
	private String entityName;
	private String fieldName;
	private String comparisonValues;
	private String conditionOperator;
	private String createdBy;
	private String updatedBy;
	private Date createdTs;
	private Date updatedTs;

	public QatreeQstnCondition() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")

    @Column(name="QATREE_QSTN_CONDITION_ID", unique=true, nullable=false, length=36)
	public String getQatreeQstnConditionId() {
		return this.qatreeQstnConditionId;
	}

	public void setQatreeQstnConditionId(String qatreeQstnConditionId) {
		this.qatreeQstnConditionId = qatreeQstnConditionId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="QATREE_ID", nullable=false)
	public Qatree getQatree() {
		return this.qatree;
	}

	public void setQatree(Qatree qatree) {
		this.qatree = qatree;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="QUESTION_ID", nullable=false)
	public QatreeQuestion getQatreeQuestion() {
		return this.qatreeQuestion;
	}

	public void setQatreeQuestion(QatreeQuestion qatreeQuestion) {
		this.qatreeQuestion = qatreeQuestion;
	}


    @Column(name="ENTITY_NAME", nullable=false, length=60)
	public String getEntityName() {
		return this.entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}


    @Column(name="FIELD_NAME", nullable=false, length=60)
	public String getFieldName() {
		return this.fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
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
