//DO NOT MODIFY: generated for HUD LRS 
package gov.hud.lrs.common.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class QaModelPrequalId implements java.io.Serializable {
	private String questionId;
	private String preQualPotentialAnswerCd;

	public QaModelPrequalId() {
	}


    @Column(name="QUESTION_ID", nullable=false, length=36)
	public String getQuestionId() {
		return this.questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}


    @Column(name="PRE_QUAL_POTENTIAL_ANSWER_CD", nullable=false, length=2)
	public String getPreQualPotentialAnswerCd() {
		return this.preQualPotentialAnswerCd;
	}

	public void setPreQualPotentialAnswerCd(String preQualPotentialAnswerCd) {
		this.preQualPotentialAnswerCd = preQualPotentialAnswerCd;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		} else if ((other == null)) {
			return false;
		} else if (!(other instanceof QaModelPrequalId)) {
			return false;
		}
		QaModelPrequalId castOther = (QaModelPrequalId)other; 
		return ( (this.getQuestionId()==castOther.getQuestionId()) || ( this.getQuestionId()!=null && castOther.getQuestionId()!=null && this.getQuestionId().equals(castOther.getQuestionId()) ) )
 && ( (this.getPreQualPotentialAnswerCd()==castOther.getPreQualPotentialAnswerCd()) || ( this.getPreQualPotentialAnswerCd()!=null && castOther.getPreQualPotentialAnswerCd()!=null && this.getPreQualPotentialAnswerCd().equals(castOther.getPreQualPotentialAnswerCd()) ) );
	}

	public int hashCode() {
		int result = 17;
		result = 37 * result + ( getQuestionId() == null ? 0 : this.getQuestionId().hashCode() );
		result = 37 * result + ( getPreQualPotentialAnswerCd() == null ? 0 : this.getPreQualPotentialAnswerCd().hashCode() );
		return result;
	}
}
