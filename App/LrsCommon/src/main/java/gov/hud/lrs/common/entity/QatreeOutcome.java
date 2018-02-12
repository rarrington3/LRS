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
@Table(name="QATREE_OUTCOME")
@SuppressWarnings("serial")
public class QatreeOutcome implements java.io.Serializable {
	private String qatreeOutcomeId;
	private Defect defect;
	private String createdBy;
	private String updatedBy;
	private Date createdTs;
	private Date updatedTs;
	private String sourceFilterQuestionId;
	private Set<QatreeOutcomeDefectSource> qatreeOutcomeDefectSources = new HashSet<QatreeOutcomeDefectSource>(0);
	private Set<QatreeQuestion> qatreeQuestions = new HashSet<QatreeQuestion>(0);
	private Set<QatreeOutcomeDefectSeverity> qatreeOutcomeDefectSeverities = new HashSet<QatreeOutcomeDefectSeverity>(0);
	private Set<QatreeOutcomeDefectCause> qatreeOutcomeDefectCauses = new HashSet<QatreeOutcomeDefectCause>(0);

	public QatreeOutcome() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")

    @Column(name="QATREE_OUTCOME_ID", unique=true, nullable=false, length=36)
	public String getQatreeOutcomeId() {
		return this.qatreeOutcomeId;
	}

	public void setQatreeOutcomeId(String qatreeOutcomeId) {
		this.qatreeOutcomeId = qatreeOutcomeId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="DEFECT_ID")
	public Defect getDefect() {
		return this.defect;
	}

	public void setDefect(Defect defect) {
		this.defect = defect;
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


    @Column(name="SOURCE_FILTER_QUESTION_ID", length=36)
	public String getSourceFilterQuestionId() {
		return this.sourceFilterQuestionId;
	}

	public void setSourceFilterQuestionId(String sourceFilterQuestionId) {
		this.sourceFilterQuestionId = sourceFilterQuestionId;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="qatreeOutcome")
	public Set<QatreeOutcomeDefectSource> getQatreeOutcomeDefectSources() {
		return this.qatreeOutcomeDefectSources;
	}

	public void setQatreeOutcomeDefectSources(Set<QatreeOutcomeDefectSource> qatreeOutcomeDefectSources) {
		this.qatreeOutcomeDefectSources = qatreeOutcomeDefectSources;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="qatreeOutcome")
	public Set<QatreeQuestion> getQatreeQuestions() {
		return this.qatreeQuestions;
	}

	public void setQatreeQuestions(Set<QatreeQuestion> qatreeQuestions) {
		this.qatreeQuestions = qatreeQuestions;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="qatreeOutcome")
	public Set<QatreeOutcomeDefectSeverity> getQatreeOutcomeDefectSeverities() {
		return this.qatreeOutcomeDefectSeverities;
	}

	public void setQatreeOutcomeDefectSeverities(Set<QatreeOutcomeDefectSeverity> qatreeOutcomeDefectSeverities) {
		this.qatreeOutcomeDefectSeverities = qatreeOutcomeDefectSeverities;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="qatreeOutcome")
	public Set<QatreeOutcomeDefectCause> getQatreeOutcomeDefectCauses() {
		return this.qatreeOutcomeDefectCauses;
	}

	public void setQatreeOutcomeDefectCauses(Set<QatreeOutcomeDefectCause> qatreeOutcomeDefectCauses) {
		this.qatreeOutcomeDefectCauses = qatreeOutcomeDefectCauses;
	}

}
