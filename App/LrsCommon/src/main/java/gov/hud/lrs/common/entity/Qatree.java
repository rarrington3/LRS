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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="QATREE")
@SuppressWarnings("serial")
public class Qatree implements java.io.Serializable {
	private String qatreeId;
	private Defect defect;
	private QaModel qaModel;
	private String createdBy;
	private String updatedBy;
	private Date createdTs;
	private Date updatedTs;
	private char enablePreQualQuestionInd;
	private Set<QatreeQstnCondition> qatreeQstnConditions = new HashSet<QatreeQstnCondition>(0);
	private Set<QatreeQuestion> qatreeQuestions = new HashSet<QatreeQuestion>(0);

	public Qatree() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")

    @Column(name="QATREE_ID", unique=true, nullable=false, length=36)
	public String getQatreeId() {
		return this.qatreeId;
	}

	public void setQatreeId(String qatreeId) {
		this.qatreeId = qatreeId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="DEFECT_ID")
	public Defect getDefect() {
		return this.defect;
	}

	public void setDefect(Defect defect) {
		this.defect = defect;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="QA_MODEL_ID", nullable=false)
	public QaModel getQaModel() {
		return this.qaModel;
	}

	public void setQaModel(QaModel qaModel) {
		this.qaModel = qaModel;
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


    @Column(name="ENABLE_PRE_QUAL_QUESTION_IND", nullable=false, length=1)
	public char getEnablePreQualQuestionInd() {
		return this.enablePreQualQuestionInd;
	}

	public void setEnablePreQualQuestionInd(char enablePreQualQuestionInd) {
		this.enablePreQualQuestionInd = enablePreQualQuestionInd;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="qatree")
	public Set<QatreeQstnCondition> getQatreeQstnConditions() {
		return this.qatreeQstnConditions;
	}

	public void setQatreeQstnConditions(Set<QatreeQstnCondition> qatreeQstnConditions) {
		this.qatreeQstnConditions = qatreeQstnConditions;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="qatree")
	public Set<QatreeQuestion> getQatreeQuestions() {
		return this.qatreeQuestions;
	}

	public void setQatreeQuestions(Set<QatreeQuestion> qatreeQuestions) {
		this.qatreeQuestions = qatreeQuestions;
	}

}
