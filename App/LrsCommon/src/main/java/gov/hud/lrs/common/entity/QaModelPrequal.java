//DO NOT MODIFY: generated for HUD LRS 
package gov.hud.lrs.common.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="QA_MODEL_PREQUAL")
@SuppressWarnings("serial")
public class QaModelPrequal implements java.io.Serializable {
	private QaModelPrequalId id;
	private QuestionReference questionReference;
	private String questionRefIdCsv;
	private String description;

	public QaModelPrequal() {
	}

    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="questionId", column=@Column(name="QUESTION_ID", nullable=false, length=36) ), 
        @AttributeOverride(name="preQualPotentialAnswerCd", column=@Column(name="PRE_QUAL_POTENTIAL_ANSWER_CD", nullable=false, length=2) ) } )
	public QaModelPrequalId getId() {
		return this.id;
	}

	public void setId(QaModelPrequalId id) {
		this.id = id;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="QUESTION_ID", nullable=false, insertable=false, updatable=false)
	public QuestionReference getQuestionReference() {
		return this.questionReference;
	}

	public void setQuestionReference(QuestionReference questionReference) {
		this.questionReference = questionReference;
	}

    
    @Column(name="QUESTION_REF_ID_CSV", length=125)
	public String getQuestionRefIdCsv() {
		return this.questionRefIdCsv;
	}

	public void setQuestionRefIdCsv(String questionRefIdCsv) {
		this.questionRefIdCsv = questionRefIdCsv;
	}

    
    @Column(name="DESCRIPTION", nullable=false, length=50)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
