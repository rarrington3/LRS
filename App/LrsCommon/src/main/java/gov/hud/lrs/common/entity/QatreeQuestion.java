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
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name="QATREE_QUESTION")
@SuppressWarnings("serial")
public class QatreeQuestion implements java.io.Serializable {
	private String questionId;
	private Qatree qatree;
	private QatreeOutcome qatreeOutcome;
	private QuestionReference parentQuestionReference;
	private QuestionReference questionReference;
	private String questionNumber;
	private String questionReferenceString;
	private String createdBy;
	private String updatedBy;
	private Date createdTs;
	private Date updatedTs;
	private Integer orderNumber;
	private String parentQuestionConditionAnswer;
	private Set<QatreeQstnCondition> qatreeQstnConditions = new HashSet<QatreeQstnCondition>(0);
	private Set<Answer> answers = new HashSet<Answer>(0);

	public QatreeQuestion() {
	}

    @GenericGenerator(name="generator", strategy="foreign", parameters=@Parameter(name="property", value="questionReference"))@Id @GeneratedValue(generator="generator")
    
    @Column(name="QUESTION_ID", unique=true, nullable=false, length=36)
	public String getQuestionId() {
		return this.questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
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
    @JoinColumn(name="QATREE_OUTCOME_ID")
	public QatreeOutcome getQatreeOutcome() {
		return this.qatreeOutcome;
	}

	public void setQatreeOutcome(QatreeOutcome qatreeOutcome) {
		this.qatreeOutcome = qatreeOutcome;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="PARENT_QUESTION_ID")
	public QuestionReference getParentQuestionReference() {
		return this.parentQuestionReference;
	}

	public void setParentQuestionReference(QuestionReference parentQuestionReference) {
		this.parentQuestionReference = parentQuestionReference;
	}

@OneToOne(fetch=FetchType.LAZY)@PrimaryKeyJoinColumn
	public QuestionReference getQuestionReference() {
		return this.questionReference;
	}

	public void setQuestionReference(QuestionReference questionReference) {
		this.questionReference = questionReference;
	}

    
    @Column(name="QUESTION_NUMBER", length=10)
	public String getQuestionNumber() {
		return this.questionNumber;
	}

	public void setQuestionNumber(String questionNumber) {
		this.questionNumber = questionNumber;
	}

    
    @Column(name="QUESTION_REFERENCE", length=16)
	public String getQuestionReferenceString() {
		return this.questionReferenceString;
	}

	public void setQuestionReferenceString(String questionReferenceString) {
		this.questionReferenceString = questionReferenceString;
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

    
    @Column(name="ORDER_NUMBER")
	public Integer getOrderNumber() {
		return this.orderNumber;
	}

	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}

    
    @Column(name="PARENT_QUESTION_CONDITION_ANSWER", length=30)
	public String getParentQuestionConditionAnswer() {
		return this.parentQuestionConditionAnswer;
	}

	public void setParentQuestionConditionAnswer(String parentQuestionConditionAnswer) {
		this.parentQuestionConditionAnswer = parentQuestionConditionAnswer;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="qatreeQuestion")
	public Set<QatreeQstnCondition> getQatreeQstnConditions() {
		return this.qatreeQstnConditions;
	}

	public void setQatreeQstnConditions(Set<QatreeQstnCondition> qatreeQstnConditions) {
		this.qatreeQstnConditions = qatreeQstnConditions;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="qatreeQuestion")
	public Set<Answer> getAnswers() {
		return this.answers;
	}

	public void setAnswers(Set<Answer> answers) {
		this.answers = answers;
	}

}
