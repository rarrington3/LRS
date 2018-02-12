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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="QUESTION_REFERENCE")
@SuppressWarnings("serial")
public class QuestionReference implements java.io.Serializable {
	private String questionId;
	private String questionDescription;
	private String valueToTriggerAction;
	private String responseType;
	private String answerValues;
	private String createdBy;
	private Date createdTs;
	private String updatedBy;
	private Date updatedTs;
	private Set<QaModelPrequal> qaModelPrequals = new HashSet<QaModelPrequal>(0);

	public QuestionReference() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")

    @Column(name="QUESTION_ID", unique=true, nullable=false, length=36)
	public String getQuestionId() {
		return this.questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}


    @Column(name="QUESTION_DESCRIPTION", length=2000)
	public String getQuestionDescription() {
		return this.questionDescription;
	}

	public void setQuestionDescription(String questionDescription) {
		this.questionDescription = questionDescription;
	}


    @Column(name="VALUE_TO_TRIGGER_ACTION", length=10)
	public String getValueToTriggerAction() {
		return this.valueToTriggerAction;
	}

	public void setValueToTriggerAction(String valueToTriggerAction) {
		this.valueToTriggerAction = valueToTriggerAction;
	}


    @Column(name="RESPONSE_TYPE", length=16)
	public String getResponseType() {
		return this.responseType;
	}

	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}


    @Column(name="ANSWER_VALUES", length=36)
	public String getAnswerValues() {
		return this.answerValues;
	}

	public void setAnswerValues(String answerValues) {
		this.answerValues = answerValues;
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

	@OneToMany(fetch=FetchType.LAZY, mappedBy="questionReference")
	public Set<QaModelPrequal> getQaModelPrequals() {
		return this.qaModelPrequals;
	}

	public void setQaModelPrequals(Set<QaModelPrequal> qaModelPrequals) {
		this.qaModelPrequals = qaModelPrequals;
	}

}
