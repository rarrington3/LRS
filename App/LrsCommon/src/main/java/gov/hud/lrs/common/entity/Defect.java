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
@Table(name="DEFECT")
@SuppressWarnings("serial")
public class Defect implements java.io.Serializable {
	private String defectId;
	private QaModel qaModel;
	private String defectCd;
	private String description;
	private Integer relativeOrder;
	private String createdBy;
	private String updatedBy;
	private Date createdTs;
	private Date updatedTs;
	private Set<Qatree> qatrees = new HashSet<Qatree>(0);
	private Set<DefectCause> defectCauses = new HashSet<DefectCause>(0);
	private Set<LenderSelfReportSelectionRequestDefect> lenderSelfReportSelectionRequestDefects = new HashSet<LenderSelfReportSelectionRequestDefect>(0);
	private Set<DefectRemedyType> defectRemedyTypes = new HashSet<DefectRemedyType>(0);
	private Set<DictMetadataFieldDefect> dictMetadataFieldDefects = new HashSet<DictMetadataFieldDefect>(0);
	private Set<ReviewTypeDefect> reviewTypeDefects = new HashSet<ReviewTypeDefect>(0);
	private Set<RvwLvlFinding> rvwLvlFindings = new HashSet<RvwLvlFinding>(0);
	private Set<DefectSource> defectSources = new HashSet<DefectSource>(0);
	private Set<FindingRatingRule> findingRatingRules = new HashSet<FindingRatingRule>(0);
	private Set<QatreeOutcome> qatreeOutcomes = new HashSet<QatreeOutcome>(0);
	private Set<DefectSeverity> defectSeverities = new HashSet<DefectSeverity>(0);
	private Set<RvwLvlDefect> rvwLvlDefects = new HashSet<RvwLvlDefect>(0);

	public Defect() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")
    
    @Column(name="DEFECT_ID", unique=true, nullable=false, length=36)
	public String getDefectId() {
		return this.defectId;
	}

	public void setDefectId(String defectId) {
		this.defectId = defectId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="QA_MODEL_ID", nullable=false)
	public QaModel getQaModel() {
		return this.qaModel;
	}

	public void setQaModel(QaModel qaModel) {
		this.qaModel = qaModel;
	}

    
    @Column(name="DEFECT_CD", nullable=false, length=16)
	public String getDefectCd() {
		return this.defectCd;
	}

	public void setDefectCd(String defectCd) {
		this.defectCd = defectCd;
	}

    
    @Column(name="DESCRIPTION", length=150)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

    
    @Column(name="RELATIVE_ORDER")
	public Integer getRelativeOrder() {
		return this.relativeOrder;
	}

	public void setRelativeOrder(Integer relativeOrder) {
		this.relativeOrder = relativeOrder;
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

	@OneToMany(fetch=FetchType.LAZY, mappedBy="defect")
	public Set<Qatree> getQatrees() {
		return this.qatrees;
	}

	public void setQatrees(Set<Qatree> qatrees) {
		this.qatrees = qatrees;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="defect")
	public Set<DefectCause> getDefectCauses() {
		return this.defectCauses;
	}

	public void setDefectCauses(Set<DefectCause> defectCauses) {
		this.defectCauses = defectCauses;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="defect")
	public Set<LenderSelfReportSelectionRequestDefect> getLenderSelfReportSelectionRequestDefects() {
		return this.lenderSelfReportSelectionRequestDefects;
	}

	public void setLenderSelfReportSelectionRequestDefects(Set<LenderSelfReportSelectionRequestDefect> lenderSelfReportSelectionRequestDefects) {
		this.lenderSelfReportSelectionRequestDefects = lenderSelfReportSelectionRequestDefects;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="defect")
	public Set<DefectRemedyType> getDefectRemedyTypes() {
		return this.defectRemedyTypes;
	}

	public void setDefectRemedyTypes(Set<DefectRemedyType> defectRemedyTypes) {
		this.defectRemedyTypes = defectRemedyTypes;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="defect")
	public Set<DictMetadataFieldDefect> getDictMetadataFieldDefects() {
		return this.dictMetadataFieldDefects;
	}

	public void setDictMetadataFieldDefects(Set<DictMetadataFieldDefect> dictMetadataFieldDefects) {
		this.dictMetadataFieldDefects = dictMetadataFieldDefects;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="defect")
	public Set<ReviewTypeDefect> getReviewTypeDefects() {
		return this.reviewTypeDefects;
	}

	public void setReviewTypeDefects(Set<ReviewTypeDefect> reviewTypeDefects) {
		this.reviewTypeDefects = reviewTypeDefects;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="defect")
	public Set<RvwLvlFinding> getRvwLvlFindings() {
		return this.rvwLvlFindings;
	}

	public void setRvwLvlFindings(Set<RvwLvlFinding> rvwLvlFindings) {
		this.rvwLvlFindings = rvwLvlFindings;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="defect")
	public Set<DefectSource> getDefectSources() {
		return this.defectSources;
	}

	public void setDefectSources(Set<DefectSource> defectSources) {
		this.defectSources = defectSources;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="defect")
	public Set<FindingRatingRule> getFindingRatingRules() {
		return this.findingRatingRules;
	}

	public void setFindingRatingRules(Set<FindingRatingRule> findingRatingRules) {
		this.findingRatingRules = findingRatingRules;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="defect")
	public Set<QatreeOutcome> getQatreeOutcomes() {
		return this.qatreeOutcomes;
	}

	public void setQatreeOutcomes(Set<QatreeOutcome> qatreeOutcomes) {
		this.qatreeOutcomes = qatreeOutcomes;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="defect")
	public Set<DefectSeverity> getDefectSeverities() {
		return this.defectSeverities;
	}

	public void setDefectSeverities(Set<DefectSeverity> defectSeverities) {
		this.defectSeverities = defectSeverities;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="defect")
	public Set<RvwLvlDefect> getRvwLvlDefects() {
		return this.rvwLvlDefects;
	}

	public void setRvwLvlDefects(Set<RvwLvlDefect> rvwLvlDefects) {
		this.rvwLvlDefects = rvwLvlDefects;
	}

}
