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
@Table(name="DOCUMENT")
@SuppressWarnings("serial")
public class Document implements java.io.Serializable {
	private String documentId;
	private Batch batch;
	private DocumentFile documentFile;
	private DocumentTypeRef documentTypeRef;
	private Review review;
	private ReviewLevel reviewLevel;
	private String fileName;
	private String createdBy;
	private String updatedBy;
	private Date createdTs;
	private Date updatedTs;
	private String mediaType;
	private Set<FindingDocument> findingDocuments = new HashSet<FindingDocument>(0);

	public Document() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")
    
    @Column(name="DOCUMENT_ID", unique=true, nullable=false, length=36)
	public String getDocumentId() {
		return this.documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="BATCH_ID")
	public Batch getBatch() {
		return this.batch;
	}

	public void setBatch(Batch batch) {
		this.batch = batch;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="DOCUMENT_FILE_ID")
	public DocumentFile getDocumentFile() {
		return this.documentFile;
	}

	public void setDocumentFile(DocumentFile documentFile) {
		this.documentFile = documentFile;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="DOCUMENT_TYPE_ID", nullable=false)
	public DocumentTypeRef getDocumentTypeRef() {
		return this.documentTypeRef;
	}

	public void setDocumentTypeRef(DocumentTypeRef documentTypeRef) {
		this.documentTypeRef = documentTypeRef;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="REVIEW_ID")
	public Review getReview() {
		return this.review;
	}

	public void setReview(Review review) {
		this.review = review;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="REVIEW_LEVEL_ID")
	public ReviewLevel getReviewLevel() {
		return this.reviewLevel;
	}

	public void setReviewLevel(ReviewLevel reviewLevel) {
		this.reviewLevel = reviewLevel;
	}

    
    @Column(name="FILE_NAME", nullable=false, length=100)
	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
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

    
    @Column(name="MEDIA_TYPE", nullable=false, length=100)
	public String getMediaType() {
		return this.mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="document")
	public Set<FindingDocument> getFindingDocuments() {
		return this.findingDocuments;
	}

	public void setFindingDocuments(Set<FindingDocument> findingDocuments) {
		this.findingDocuments = findingDocuments;
	}

}
