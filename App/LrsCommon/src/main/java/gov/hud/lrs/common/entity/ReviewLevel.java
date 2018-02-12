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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="REVIEW_LEVEL")
@SuppressWarnings("serial")
public class ReviewLevel implements java.io.Serializable {

	private String reviewLevelId;
	private IndemnificationTypeRef indemnificationTypeRef;
	private Personnel originalReviewerPersonnel;
	private Personnel reviewerPersonnel;
	private RatingRef ratingRef;
	private Review review;
	private ReviewLevelReassignmentReasonRef reviewLevelReassignmentReasonRef;
	private ReviewLevelStatusRef reviewLevelStatusRef;
	private ReviewLevelTypeRef reviewLevelTypeRef;
	private ReviewLocation reviewLocation;
	private Date assignDt;
	private Date compltDt;
	private Date forceEscalationDt;
	private short iterationNumber;
	private String notes;
	private Date rvwStartedDtTm;
	private String createdBy;
	private String updatedBy;
	private Date createdTs;
	private Date updatedTs;
	private Date dueDate;
	private char vettingInd;
	private Date vetteeAcknowledgementDt;
	private char vettingRequiredInd;
	private Indemnification indemnification;
	private LenderRequest lenderRequest;
	private Set<RvwLvlDefect> rvwLvlDefects = new HashSet<RvwLvlDefect>(0);
	private Set<ReviewProcessException> reviewProcessExceptions = new HashSet<ReviewProcessException>(0);
	private Set<Document> documents = new HashSet<Document>(0);
	private Set<RvwLvlAudit> rvwLvlAudits = new HashSet<RvwLvlAudit>(0);
	private Set<RvwLvlFinding> rvwLvlFindings = new HashSet<RvwLvlFinding>(0);
	private Set<Answer> answers = new HashSet<Answer>(0);
	private RvwLvlCaseSummary rvwLvlCaseSummary;

	public ReviewLevel() {
	}

	@Id
	@GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")
	@GeneratedValue(generator="generator")
	@Column(name="REVIEW_LEVEL_ID", unique=true, nullable=false, length=36)
	public String getReviewLevelId() {
		return this.reviewLevelId;
	}

	public void setReviewLevelId(String reviewLevelId) {
		this.reviewLevelId = reviewLevelId;
	}

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="INDEMNIFICATION_TYPE_ID")
	public IndemnificationTypeRef getIndemnificationTypeRef() {
		return this.indemnificationTypeRef;
	}

	public void setIndemnificationTypeRef(IndemnificationTypeRef indemnificationTypeRef) {
		this.indemnificationTypeRef = indemnificationTypeRef;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ORIGINAL_REVIEWER_PERSONNEL_ID")
	public Personnel getOriginalReviewerPersonnel() {
		return this.originalReviewerPersonnel;
	}

	public void setOriginalReviewerPersonnel(Personnel originalReviewerPersonnel) {
		this.originalReviewerPersonnel = originalReviewerPersonnel;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="REVIEWER_PERSONNEL_ID")
	public Personnel getReviewerPersonnel() {
		return this.reviewerPersonnel;
	}

	public void setReviewerPersonnel(Personnel reviewerPersonnel) {
		this.reviewerPersonnel = reviewerPersonnel;
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
	@JoinColumn(name="REVIEW_ID", nullable=false)
	public Review getReview() {
		return this.review;
	}

	public void setReview(Review review) {
		this.review = review;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="REASSIGNMENT_REASON_ID")
	public ReviewLevelReassignmentReasonRef getReviewLevelReassignmentReasonRef() {
		return this.reviewLevelReassignmentReasonRef;
	}

	public void setReviewLevelReassignmentReasonRef(ReviewLevelReassignmentReasonRef reviewLevelReassignmentReasonRef) {
		this.reviewLevelReassignmentReasonRef = reviewLevelReassignmentReasonRef;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="REVIEW_LEVEL_STATUS_ID", nullable=false)
	public ReviewLevelStatusRef getReviewLevelStatusRef() {
		return this.reviewLevelStatusRef;
	}

	public void setReviewLevelStatusRef(ReviewLevelStatusRef reviewLevelStatusRef) {
		this.reviewLevelStatusRef = reviewLevelStatusRef;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="REVIEW_LEVEL_TYPE_ID")
	public ReviewLevelTypeRef getReviewLevelTypeRef() {
		return this.reviewLevelTypeRef;
	}

	public void setReviewLevelTypeRef(ReviewLevelTypeRef reviewLevelTypeRef) {
		this.reviewLevelTypeRef = reviewLevelTypeRef;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="REVIEW_LOCATION_ID")
	public ReviewLocation getReviewLocation() {
		return this.reviewLocation;
	}

	public void setReviewLocation(ReviewLocation reviewLocation) {
		this.reviewLocation = reviewLocation;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="ASSIGN_DT", length=23)
	public Date getAssignDt() {
		return this.assignDt;
	}

	public void setAssignDt(Date assignDt) {
		this.assignDt = assignDt;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="COMPLT_DT", length=23)
	public Date getCompltDt() {
		return this.compltDt;
	}

	public void setCompltDt(Date compltDt) {
		this.compltDt = compltDt;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="FORCE_ESCALATION_DT", length=23)
	public Date getForceEscalationDt() {
		return this.forceEscalationDt;
	}

	public void setForceEscalationDt(Date forceEscalationDt) {
		this.forceEscalationDt = forceEscalationDt;
	}

	@Column(name="ITERATION_NUMBER", nullable=false, precision=3, scale=0)
	public short getIterationNumber() {
		return this.iterationNumber;
	}

	public void setIterationNumber(short iterationNumber) {
		this.iterationNumber = iterationNumber;
	}

	@Column(name="NOTES")
	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="RVW_STARTED_DT_TM", length=23)
	public Date getRvwStartedDtTm() {
		return this.rvwStartedDtTm;
	}

	public void setRvwStartedDtTm(Date rvwStartedDtTm) {
		this.rvwStartedDtTm = rvwStartedDtTm;
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DUE_DATE", length=23)
	public Date getDueDate() {
		return this.dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	@Column(name="VETTING_IND", nullable=false, length=1)
	public char getVettingInd() {
		return this.vettingInd;
	}

	public void setVettingInd(char vettingInd) {
		this.vettingInd = vettingInd;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="VETTEE_ACKNOWLEDGEMENT_DT", length=23)
	public Date getVetteeAcknowledgementDt() {
		return this.vetteeAcknowledgementDt;
	}

	public void setVetteeAcknowledgementDt(Date vetteeAcknowledgementDt) {
		this.vetteeAcknowledgementDt = vetteeAcknowledgementDt;
	}

	@Column(name="VETTING_REQUIRED_IND", nullable=false, length=1)
	public char getVettingRequiredInd() {
		return this.vettingRequiredInd;
	}

	public void setVettingRequiredInd(char vettingRequiredInd) {
		this.vettingRequiredInd = vettingRequiredInd;
	}

	@OneToOne(fetch=FetchType.LAZY, mappedBy="reviewLevel")
	public Indemnification getIndemnification() {
		return this.indemnification;
	}

	public void setIndemnification(Indemnification indemnification) {
		this.indemnification = indemnification;
	}

	@OneToOne(fetch=FetchType.LAZY, mappedBy="reviewLevel")
	public LenderRequest getLenderRequest() {
		return this.lenderRequest;
	}

	public void setLenderRequest(LenderRequest lenderRequest) {
		this.lenderRequest = lenderRequest;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="reviewLevel")
	public Set<RvwLvlDefect> getRvwLvlDefects() {
		return this.rvwLvlDefects;
	}

	public void setRvwLvlDefects(Set<RvwLvlDefect> rvwLvlDefects) {
		this.rvwLvlDefects = rvwLvlDefects;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="reviewLevel")
	public Set<ReviewProcessException> getReviewProcessExceptions() {
		return this.reviewProcessExceptions;
	}

	public void setReviewProcessExceptions(Set<ReviewProcessException> reviewProcessExceptions) {
		this.reviewProcessExceptions = reviewProcessExceptions;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="reviewLevel")
	public Set<Document> getDocuments() {
		return this.documents;
	}

	public void setDocuments(Set<Document> documents) {
		this.documents = documents;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="reviewLevel")
	public Set<RvwLvlAudit> getRvwLvlAudits() {
		return this.rvwLvlAudits;
	}

	public void setRvwLvlAudits(Set<RvwLvlAudit> rvwLvlAudits) {
		this.rvwLvlAudits = rvwLvlAudits;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="reviewLevel")
	public Set<RvwLvlFinding> getRvwLvlFindings() {
		return this.rvwLvlFindings;
	}

	public void setRvwLvlFindings(Set<RvwLvlFinding> rvwLvlFindings) {
		this.rvwLvlFindings = rvwLvlFindings;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="reviewLevel")
	public Set<Answer> getAnswers() {
		return this.answers;
	}

	public void setAnswers(Set<Answer> answers) {
		this.answers = answers;
	}

	@OneToOne(fetch=FetchType.LAZY, mappedBy="reviewLevel")
	public RvwLvlCaseSummary getRvwLvlCaseSummary() {
		return this.rvwLvlCaseSummary;
	}

	public void setRvwLvlCaseSummary(RvwLvlCaseSummary rvwLvlCaseSummary) {
		this.rvwLvlCaseSummary = rvwLvlCaseSummary;
	}

}
