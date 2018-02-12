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
@Table(name="ANSWER")
@SuppressWarnings("serial")
public class Answer implements java.io.Serializable {
	private String answerId;
	private QatreeQuestion qatreeQuestion;
	private ReviewLevel reviewLevel;
	private String answer;
	private String createdBy;
	private String updatedBy;
	private Date createdTs;
	private Date updatedTs;

	public Answer() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")
    
    @Column(name="ANSWER_ID", unique=true, nullable=false, length=36)
	public String getAnswerId() {
		return this.answerId;
	}

	public void setAnswerId(String answerId) {
		this.answerId = answerId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="QUESTION_ID", nullable=false)
	public QatreeQuestion getQatreeQuestion() {
		return this.qatreeQuestion;
	}

	public void setQatreeQuestion(QatreeQuestion qatreeQuestion) {
		this.qatreeQuestion = qatreeQuestion;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="REVIEW_LEVEL_ID", nullable=false)
	public ReviewLevel getReviewLevel() {
		return this.reviewLevel;
	}

	public void setReviewLevel(ReviewLevel reviewLevel) {
		this.reviewLevel = reviewLevel;
	}

    
    @Column(name="ANSWER", length=16)
	public String getAnswer() {
		return this.answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
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
