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
@Table(name="NOTE")
@SuppressWarnings("serial")
public class Note implements java.io.Serializable {
	private String noteId;
	private NoteTypeRef noteTypeRef;
	private Personnel personnel;
	private String reviewId;
	private String reviewLevelId;
	private String findingId;
	private String associatedDefectCd;
	private String createdBy;
	private Date createdTs;
	private String updatedBy;
	private Date updatedTs;
	private String noteText;

	public Note() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")
    
    @Column(name="NOTE_ID", unique=true, nullable=false, length=36)
	public String getNoteId() {
		return this.noteId;
	}

	public void setNoteId(String noteId) {
		this.noteId = noteId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="NOTE_TYPE_ID")
	public NoteTypeRef getNoteTypeRef() {
		return this.noteTypeRef;
	}

	public void setNoteTypeRef(NoteTypeRef noteTypeRef) {
		this.noteTypeRef = noteTypeRef;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="PERSONNEL_ID")
	public Personnel getPersonnel() {
		return this.personnel;
	}

	public void setPersonnel(Personnel personnel) {
		this.personnel = personnel;
	}

    
    @Column(name="REVIEW_ID", length=36)
	public String getReviewId() {
		return this.reviewId;
	}

	public void setReviewId(String reviewId) {
		this.reviewId = reviewId;
	}

    
    @Column(name="REVIEW_LEVEL_ID", length=36)
	public String getReviewLevelId() {
		return this.reviewLevelId;
	}

	public void setReviewLevelId(String reviewLevelId) {
		this.reviewLevelId = reviewLevelId;
	}

    
    @Column(name="FINDING_ID", length=36)
	public String getFindingId() {
		return this.findingId;
	}

	public void setFindingId(String findingId) {
		this.findingId = findingId;
	}

    
    @Column(name="ASSOCIATED_DEFECT_CD", length=16)
	public String getAssociatedDefectCd() {
		return this.associatedDefectCd;
	}

	public void setAssociatedDefectCd(String associatedDefectCd) {
		this.associatedDefectCd = associatedDefectCd;
	}

    
    @Column(name="CREATED_BY", length=6)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATED_TS", length=23)
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

    
    @Column(name="NOTE_TEXT")
	public String getNoteText() {
		return this.noteText;
	}

	public void setNoteText(String noteText) {
		this.noteText = noteText;
	}

}
