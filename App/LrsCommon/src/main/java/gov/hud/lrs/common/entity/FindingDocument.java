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
@Table(name="FINDING_DOCUMENT")
@SuppressWarnings("serial")
public class FindingDocument implements java.io.Serializable {
	private String findingDocumentId;
	private Document document;
	private RvwLvlFinding rvwLvlFinding;
	private String createdBy;
	private String updatedBy;
	private Date createdTs;
	private Date updatedTs;

	public FindingDocument() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")

    @Column(name="FINDING_DOCUMENT_ID", unique=true, nullable=false, length=36)
	public String getFindingDocumentId() {
		return this.findingDocumentId;
	}

	public void setFindingDocumentId(String findingDocumentId) {
		this.findingDocumentId = findingDocumentId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="DOCUMENT_ID", nullable=false)
	public Document getDocument() {
		return this.document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="FINDING_ID", nullable=false)
	public RvwLvlFinding getRvwLvlFinding() {
		return this.rvwLvlFinding;
	}

	public void setRvwLvlFinding(RvwLvlFinding rvwLvlFinding) {
		this.rvwLvlFinding = rvwLvlFinding;
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

}
