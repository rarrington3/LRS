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
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="SCORING_MODEL", uniqueConstraints = @UniqueConstraint(columnNames="CODE"))
@SuppressWarnings("serial")
public class ScoringModel implements java.io.Serializable {
	private String scoringModelId;
	private LoanTypeRef loanTypeRef;
	private ReviewTypeRef reviewTypeRef;
	private ScoringModelTypeRef scoringModelTypeRef;
	private SelectionReason selectionReason;
	private String description;
	private String createdBy;
	private String updatedBy;
	private Date createdTs;
	private Date updatedTs;
	private Integer orderNumber;
	private String code;
	private Set<ScoringFactor> scoringFactors = new HashSet<ScoringFactor>(0);
	private Set<SelectionReason> selectionReasons = new HashSet<SelectionReason>(0);
	private Set<ScoringModelVersion> scoringModelVersions = new HashSet<ScoringModelVersion>(0);

	public ScoringModel() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")

    @Column(name="SCORING_MODEL_ID", unique=true, nullable=false, length=36)
	public String getScoringModelId() {
		return this.scoringModelId;
	}

	public void setScoringModelId(String scoringModelId) {
		this.scoringModelId = scoringModelId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="LOAN_TYPE_REF_ID")
	public LoanTypeRef getLoanTypeRef() {
		return this.loanTypeRef;
	}

	public void setLoanTypeRef(LoanTypeRef loanTypeRef) {
		this.loanTypeRef = loanTypeRef;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="REVIEW_TYPE_ID")
	public ReviewTypeRef getReviewTypeRef() {
		return this.reviewTypeRef;
	}

	public void setReviewTypeRef(ReviewTypeRef reviewTypeRef) {
		this.reviewTypeRef = reviewTypeRef;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="SCORING_MODEL_TYPE_ID")
	public ScoringModelTypeRef getScoringModelTypeRef() {
		return this.scoringModelTypeRef;
	}

	public void setScoringModelTypeRef(ScoringModelTypeRef scoringModelTypeRef) {
		this.scoringModelTypeRef = scoringModelTypeRef;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="SELECTION_REASON_ID")
	public SelectionReason getSelectionReason() {
		return this.selectionReason;
	}

	public void setSelectionReason(SelectionReason selectionReason) {
		this.selectionReason = selectionReason;
	}


    @Column(name="DESCRIPTION", length=100)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
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


    @Column(name="ORDER_NUMBER")
	public Integer getOrderNumber() {
		return this.orderNumber;
	}

	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}


    @Column(name="CODE", unique=true, nullable=false, length=50)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="scoringModel")
	public Set<ScoringFactor> getScoringFactors() {
		return this.scoringFactors;
	}

	public void setScoringFactors(Set<ScoringFactor> scoringFactors) {
		this.scoringFactors = scoringFactors;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="scoringModel")
	public Set<SelectionReason> getSelectionReasons() {
		return this.selectionReasons;
	}

	public void setSelectionReasons(Set<SelectionReason> selectionReasons) {
		this.selectionReasons = selectionReasons;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="scoringModel")
	public Set<ScoringModelVersion> getScoringModelVersions() {
		return this.scoringModelVersions;
	}

	public void setScoringModelVersions(Set<ScoringModelVersion> scoringModelVersions) {
		this.scoringModelVersions = scoringModelVersions;
	}

}
