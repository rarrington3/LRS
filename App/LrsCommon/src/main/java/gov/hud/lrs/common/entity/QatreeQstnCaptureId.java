//DO NOT MODIFY: generated for HUD LRS 
package gov.hud.lrs.common.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class QatreeQstnCaptureId implements java.io.Serializable {
	private String qatreeId;
	private String questionId;
	private String entityName;
	private String fieldName;

	public QatreeQstnCaptureId() {
	}


    @Column(name="QATREE_ID", nullable=false, length=36)
	public String getQatreeId() {
		return this.qatreeId;
	}

	public void setQatreeId(String qatreeId) {
		this.qatreeId = qatreeId;
	}


    @Column(name="QUESTION_ID", nullable=false, length=36)
	public String getQuestionId() {
		return this.questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
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

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		} else if ((other == null)) {
			return false;
		} else if (!(other instanceof QatreeQstnCaptureId)) {
			return false;
		}
		QatreeQstnCaptureId castOther = (QatreeQstnCaptureId)other; 
		return ( (this.getQatreeId()==castOther.getQatreeId()) || ( this.getQatreeId()!=null && castOther.getQatreeId()!=null && this.getQatreeId().equals(castOther.getQatreeId()) ) )
 && ( (this.getQuestionId()==castOther.getQuestionId()) || ( this.getQuestionId()!=null && castOther.getQuestionId()!=null && this.getQuestionId().equals(castOther.getQuestionId()) ) )
 && ( (this.getEntityName()==castOther.getEntityName()) || ( this.getEntityName()!=null && castOther.getEntityName()!=null && this.getEntityName().equals(castOther.getEntityName()) ) )
 && ( (this.getFieldName()==castOther.getFieldName()) || ( this.getFieldName()!=null && castOther.getFieldName()!=null && this.getFieldName().equals(castOther.getFieldName()) ) );
	}

	public int hashCode() {
		int result = 17;
		result = 37 * result + ( getQatreeId() == null ? 0 : this.getQatreeId().hashCode() );
		result = 37 * result + ( getQuestionId() == null ? 0 : this.getQuestionId().hashCode() );
		result = 37 * result + ( getEntityName() == null ? 0 : this.getEntityName().hashCode() );
		result = 37 * result + ( getFieldName() == null ? 0 : this.getFieldName().hashCode() );
		return result;
	}
}
