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
@Table(name="INDEMNIFICATION", uniqueConstraints = @UniqueConstraint(columnNames="REVIEW_LEVEL_ID"))
@SuppressWarnings("serial")
public class Indemnification implements java.io.Serializable {
	private String indemnificationId;
	private ReviewLevel reviewLevel;
	private ReviewLocation reviewLocation;
	private Date startDate;
	private String createdBy;
	private String updatedBy;
	private Date createdTs;
	private Date updatedTs;
	private Character transferrableInd;
	private long indemnificationAgreementNumber;
	private String lenderSignerUserId;
	private String lenderSignerName;
	private Date lenderSignedDate;
	private String fhaSignerHudId;
	private String fhaSignerName;
	private String fhaSignerDivision;
	private Date fhaSignedDate;
	private Date expirationDate;

	public Indemnification() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")

    @Column(name="INDEMNIFICATION_ID", unique=true, nullable=false, length=36)
	public String getIndemnificationId() {
		return this.indemnificationId;
	}

	public void setIndemnificationId(String indemnificationId) {
		this.indemnificationId = indemnificationId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="REVIEW_LEVEL_ID", unique=true, nullable=false)
	public ReviewLevel getReviewLevel() {
		return this.reviewLevel;
	}

	public void setReviewLevel(ReviewLevel reviewLevel) {
		this.reviewLevel = reviewLevel;
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
    @Column(name="START_DATE", length=23)
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
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


    @Column(name="TRANSFERRABLE_IND", length=1)
	public Character getTransferrableInd() {
		return this.transferrableInd;
	}

	public void setTransferrableInd(Character transferrableInd) {
		this.transferrableInd = transferrableInd;
	}


    @Column(name="INDEMNIFICATION_AGREEMENT_NUMBER", nullable=false)
	public long getIndemnificationAgreementNumber() {
		return this.indemnificationAgreementNumber;
	}

	public void setIndemnificationAgreementNumber(long indemnificationAgreementNumber) {
		this.indemnificationAgreementNumber = indemnificationAgreementNumber;
	}


    @Column(name="LENDER_SIGNER_USER_ID", length=6)
	public String getLenderSignerUserId() {
		return this.lenderSignerUserId;
	}

	public void setLenderSignerUserId(String lenderSignerUserId) {
		this.lenderSignerUserId = lenderSignerUserId;
	}


    @Column(name="LENDER_SIGNER_NAME", length=100)
	public String getLenderSignerName() {
		return this.lenderSignerName;
	}

	public void setLenderSignerName(String lenderSignerName) {
		this.lenderSignerName = lenderSignerName;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="LENDER_SIGNED_DATE", length=23)
	public Date getLenderSignedDate() {
		return this.lenderSignedDate;
	}

	public void setLenderSignedDate(Date lenderSignedDate) {
		this.lenderSignedDate = lenderSignedDate;
	}

	@Column(name="FHA_SIGNER_HUD_ID", length=6)
	public String getFhaSignerHudId() {
		return this.fhaSignerHudId;
	}

	public void setFhaSignerHudId(String fhaSignerHudId) {
		this.fhaSignerHudId = fhaSignerHudId;
	}

    @Column(name="FHA_SIGNER_NAME", length=100)
	public String getFhaSignerName() {
		return this.fhaSignerName;
	}

	public void setFhaSignerName(String fhaSignerName) {
		this.fhaSignerName = fhaSignerName;
	}


    @Column(name="FHA_SIGNER_DIVISION", length=3)
	public String getFhaSignerDivision() {
		return this.fhaSignerDivision;
	}

	public void setFhaSignerDivision(String fhaSignerDivision) {
		this.fhaSignerDivision = fhaSignerDivision;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="FHA_SIGNED_DATE", length=23)
	public Date getFhaSignedDate() {
		return this.fhaSignedDate;
	}

	public void setFhaSignedDate(Date fhaSignedDate) {
		this.fhaSignedDate = fhaSignedDate;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="EXPIRATION_DATE", length=23)
	public Date getExpirationDate() {
		return this.expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

}
