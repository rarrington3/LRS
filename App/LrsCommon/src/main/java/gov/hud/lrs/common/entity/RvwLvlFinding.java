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
@Table(name="RVW_LVL_FINDING")
@SuppressWarnings("serial")
public class RvwLvlFinding implements java.io.Serializable {
	private String findingId;
	private Defect defect;
	private DefectCause defectCause;
	private DefectRemedyType defectRemedyType;
	private DefectSeverity defectSeverity;
	private DefectSource defectSource;
	private RatingRef ratingRef;
	private ReviewLevel reviewLevel;
	private ReviewLevel originalReviewLevel;
	private String defectCd;
	private String defectSourceCd;
	private String defectCauseCd;
	private int severityTierCd;
	private String notes;
	private Character recissionInd;
	private Date remediedDt;
	private Integer remedyAmount;
	private String createdBy;
	private String updatedBy;
	private Date createdTs;
	private Date updatedTs;
	private Set<FindingDocument> findingDocuments = new HashSet<FindingDocument>(0);
	private Set<RvwLvlFindingQuestion> rvwLvlFindingQuestions = new HashSet<RvwLvlFindingQuestion>(0);

	public RvwLvlFinding() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")
    
    @Column(name="FINDING_ID", unique=true, nullable=false, length=36)
	public String getFindingId() {
		return this.findingId;
	}

	public void setFindingId(String findingId) {
		this.findingId = findingId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="DEFECT_ID", nullable=false)
	public Defect getDefect() {
		return this.defect;
	}

	public void setDefect(Defect defect) {
		this.defect = defect;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="DEFECT_CAUSE_ID", nullable=false)
	public DefectCause getDefectCause() {
		return this.defectCause;
	}

	public void setDefectCause(DefectCause defectCause) {
		this.defectCause = defectCause;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="DEFECT_REMEDY_TYPE_ID")
	public DefectRemedyType getDefectRemedyType() {
		return this.defectRemedyType;
	}

	public void setDefectRemedyType(DefectRemedyType defectRemedyType) {
		this.defectRemedyType = defectRemedyType;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="DEFECT_SEVERITY_ID")
	public DefectSeverity getDefectSeverity() {
		return this.defectSeverity;
	}

	public void setDefectSeverity(DefectSeverity defectSeverity) {
		this.defectSeverity = defectSeverity;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="DEFECT_SOURCE_ID", nullable=false)
	public DefectSource getDefectSource() {
		return this.defectSource;
	}

	public void setDefectSource(DefectSource defectSource) {
		this.defectSource = defectSource;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="RATING_ID", nullable=false)
	public RatingRef getRatingRef() {
		return this.ratingRef;
	}

	public void setRatingRef(RatingRef ratingRef) {
		this.ratingRef = ratingRef;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="REVIEW_LEVEL_ID", nullable=false)
	public ReviewLevel getReviewLevel() {
		return this.reviewLevel;
	}

	public void setReviewLevel(ReviewLevel reviewLevel) {
		this.reviewLevel = reviewLevel;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ORIGINAL_REVIEW_LEVEL_ID", nullable=false)
	public ReviewLevel getOriginalReviewLevel() {
		return this.originalReviewLevel;
	}

	public void setOriginalReviewLevel(ReviewLevel originalReviewLevel) {
		this.originalReviewLevel = originalReviewLevel;
	}

    
    @Column(name="DEFECT_CD", nullable=false, length=16)
	public String getDefectCd() {
		return this.defectCd;
	}

	public void setDefectCd(String defectCd) {
		this.defectCd = defectCd;
	}

    
    @Column(name="DEFECT_SOURCE_CD", nullable=false, length=16)
	public String getDefectSourceCd() {
		return this.defectSourceCd;
	}

	public void setDefectSourceCd(String defectSourceCd) {
		this.defectSourceCd = defectSourceCd;
	}

    
    @Column(name="DEFECT_CAUSE_CD", nullable=false, length=16)
	public String getDefectCauseCd() {
		return this.defectCauseCd;
	}

	public void setDefectCauseCd(String defectCauseCd) {
		this.defectCauseCd = defectCauseCd;
	}

    
    @Column(name="SEVERITY_TIER_CD", nullable=false)
	public int getSeverityTierCd() {
		return this.severityTierCd;
	}

	public void setSeverityTierCd(int severityTierCd) {
		this.severityTierCd = severityTierCd;
	}

    
    @Column(name="NOTES")
	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

    
    @Column(name="RECISSION_IND", length=1)
	public Character getRecissionInd() {
		return this.recissionInd;
	}

	public void setRecissionInd(Character recissionInd) {
		this.recissionInd = recissionInd;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="REMEDIED_DT", length=23)
	public Date getRemediedDt() {
		return this.remediedDt;
	}

	public void setRemediedDt(Date remediedDt) {
		this.remediedDt = remediedDt;
	}

    
    @Column(name="REMEDY_AMOUNT")
	public Integer getRemedyAmount() {
		return this.remedyAmount;
	}

	public void setRemedyAmount(Integer remedyAmount) {
		this.remedyAmount = remedyAmount;
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

	@OneToMany(fetch=FetchType.LAZY, mappedBy="rvwLvlFinding")
	public Set<FindingDocument> getFindingDocuments() {
		return this.findingDocuments;
	}

	public void setFindingDocuments(Set<FindingDocument> findingDocuments) {
		this.findingDocuments = findingDocuments;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="rvwLvlFinding")
	public Set<RvwLvlFindingQuestion> getRvwLvlFindingQuestions() {
		return this.rvwLvlFindingQuestions;
	}

	public void setRvwLvlFindingQuestions(Set<RvwLvlFindingQuestion> rvwLvlFindingQuestions) {
		this.rvwLvlFindingQuestions = rvwLvlFindingQuestions;
	}

}
