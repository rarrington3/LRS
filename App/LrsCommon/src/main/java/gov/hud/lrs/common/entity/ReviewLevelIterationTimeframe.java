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
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="REVIEW_LEVEL_ITERATION_TIMEFRAME", uniqueConstraints = @UniqueConstraint(columnNames={"CONSOLIDATED_SELECTION_REASON_ID", "REVIEW_LEVEL_TYPE_ID"}))
@SuppressWarnings("serial")
public class ReviewLevelIterationTimeframe implements java.io.Serializable {
	private String reviewLevelIterationTimeframeId;
	private ConsolidatedSelectionReason consolidatedSelectionReason;
	private ReviewLevelTypeRef reviewLevelTypeRef;
	private Integer iterationNumber;
	private Integer reviewDaysIteration1;
	private Integer reviewDaysIteration2;
	private Integer reviewDaysIteration3;
	private Integer reviewDaysIteration4;
	private Integer reviewDaysIteration5;
	private Integer responseDaysIteration1;
	private Integer responseDaysIteration2;
	private Integer responseDaysIteration3;
	private Integer responseDaysIteration4;
	private Integer responseDaysIteration5;
	private Integer reviewDaysIteration6;
	private String createdBy;
	private Date createdTs;
	private String updatedBy;
	private Date updatedTs;

	public ReviewLevelIterationTimeframe() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")

    @Column(name="REVIEW_LEVEL_ITERATION_TIMEFRAME_ID", unique=true, nullable=false, length=36)
	public String getReviewLevelIterationTimeframeId() {
		return this.reviewLevelIterationTimeframeId;
	}

	public void setReviewLevelIterationTimeframeId(String reviewLevelIterationTimeframeId) {
		this.reviewLevelIterationTimeframeId = reviewLevelIterationTimeframeId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="CONSOLIDATED_SELECTION_REASON_ID", nullable=false)
	public ConsolidatedSelectionReason getConsolidatedSelectionReason() {
		return this.consolidatedSelectionReason;
	}

	public void setConsolidatedSelectionReason(ConsolidatedSelectionReason consolidatedSelectionReason) {
		this.consolidatedSelectionReason = consolidatedSelectionReason;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="REVIEW_LEVEL_TYPE_ID", nullable=false)
	public ReviewLevelTypeRef getReviewLevelTypeRef() {
		return this.reviewLevelTypeRef;
	}

	public void setReviewLevelTypeRef(ReviewLevelTypeRef reviewLevelTypeRef) {
		this.reviewLevelTypeRef = reviewLevelTypeRef;
	}


    @Column(name="ITERATION_NUMBER")
	public Integer getIterationNumber() {
		return this.iterationNumber;
	}

	public void setIterationNumber(Integer iterationNumber) {
		this.iterationNumber = iterationNumber;
	}


    @Column(name="REVIEW_DAYS_ITERATION_1")
	public Integer getReviewDaysIteration1() {
		return this.reviewDaysIteration1;
	}

	public void setReviewDaysIteration1(Integer reviewDaysIteration1) {
		this.reviewDaysIteration1 = reviewDaysIteration1;
	}


    @Column(name="REVIEW_DAYS_ITERATION_2")
	public Integer getReviewDaysIteration2() {
		return this.reviewDaysIteration2;
	}

	public void setReviewDaysIteration2(Integer reviewDaysIteration2) {
		this.reviewDaysIteration2 = reviewDaysIteration2;
	}


    @Column(name="REVIEW_DAYS_ITERATION_3")
	public Integer getReviewDaysIteration3() {
		return this.reviewDaysIteration3;
	}

	public void setReviewDaysIteration3(Integer reviewDaysIteration3) {
		this.reviewDaysIteration3 = reviewDaysIteration3;
	}


    @Column(name="REVIEW_DAYS_ITERATION_4")
	public Integer getReviewDaysIteration4() {
		return this.reviewDaysIteration4;
	}

	public void setReviewDaysIteration4(Integer reviewDaysIteration4) {
		this.reviewDaysIteration4 = reviewDaysIteration4;
	}


    @Column(name="REVIEW_DAYS_ITERATION_5")
	public Integer getReviewDaysIteration5() {
		return this.reviewDaysIteration5;
	}

	public void setReviewDaysIteration5(Integer reviewDaysIteration5) {
		this.reviewDaysIteration5 = reviewDaysIteration5;
	}


    @Column(name="RESPONSE_DAYS_ITERATION_1")
	public Integer getResponseDaysIteration1() {
		return this.responseDaysIteration1;
	}

	public void setResponseDaysIteration1(Integer responseDaysIteration1) {
		this.responseDaysIteration1 = responseDaysIteration1;
	}


    @Column(name="RESPONSE_DAYS_ITERATION_2")
	public Integer getResponseDaysIteration2() {
		return this.responseDaysIteration2;
	}

	public void setResponseDaysIteration2(Integer responseDaysIteration2) {
		this.responseDaysIteration2 = responseDaysIteration2;
	}


    @Column(name="RESPONSE_DAYS_ITERATION_3")
	public Integer getResponseDaysIteration3() {
		return this.responseDaysIteration3;
	}

	public void setResponseDaysIteration3(Integer responseDaysIteration3) {
		this.responseDaysIteration3 = responseDaysIteration3;
	}


    @Column(name="RESPONSE_DAYS_ITERATION_4")
	public Integer getResponseDaysIteration4() {
		return this.responseDaysIteration4;
	}

	public void setResponseDaysIteration4(Integer responseDaysIteration4) {
		this.responseDaysIteration4 = responseDaysIteration4;
	}


    @Column(name="RESPONSE_DAYS_ITERATION_5")
	public Integer getResponseDaysIteration5() {
		return this.responseDaysIteration5;
	}

	public void setResponseDaysIteration5(Integer responseDaysIteration5) {
		this.responseDaysIteration5 = responseDaysIteration5;
	}


    @Column(name="REVIEW_DAYS_ITERATION_6")
	public Integer getReviewDaysIteration6() {
		return this.reviewDaysIteration6;
	}

	public void setReviewDaysIteration6(Integer reviewDaysIteration6) {
		this.reviewDaysIteration6 = reviewDaysIteration6;
	}


    @Column(name="CREATED_BY", nullable=false, length=6)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATED_TS", nullable=false, length=23)
	public Date getCreatedTs() {
		return this.createdTs;
	}

	public void setCreatedTs(Date createdTs) {
		this.createdTs = createdTs;
	}


    @Column(name="UPDATED_BY", length=6)
	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
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
