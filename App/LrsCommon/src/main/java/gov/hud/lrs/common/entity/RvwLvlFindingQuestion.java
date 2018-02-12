//DO NOT MODIFY: generated for HUD LRS 
package gov.hud.lrs.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="RVW_LVL_FINDING_QUESTION")
@SuppressWarnings("serial")
public class RvwLvlFindingQuestion implements java.io.Serializable {
	private String findingQuestionId;
	private QatreeQuestion qatreeQuestion;
	private RvwLvlFinding rvwLvlFinding;

	public RvwLvlFindingQuestion() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")
    
    @Column(name="FINDING_QUESTION_ID", unique=true, nullable=false, length=36)
	public String getFindingQuestionId() {
		return this.findingQuestionId;
	}

	public void setFindingQuestionId(String findingQuestionId) {
		this.findingQuestionId = findingQuestionId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="QUESTION_ID")
	public QatreeQuestion getQatreeQuestion() {
		return this.qatreeQuestion;
	}

	public void setQatreeQuestion(QatreeQuestion qatreeQuestion) {
		this.qatreeQuestion = qatreeQuestion;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="FINDING_ID")
	public RvwLvlFinding getRvwLvlFinding() {
		return this.rvwLvlFinding;
	}

	public void setRvwLvlFinding(RvwLvlFinding rvwLvlFinding) {
		this.rvwLvlFinding = rvwLvlFinding;
	}

}
