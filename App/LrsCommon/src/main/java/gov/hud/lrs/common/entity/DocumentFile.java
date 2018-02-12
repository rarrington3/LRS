//DO NOT MODIFY: generated for HUD LRS
package gov.hud.lrs.common.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="DOCUMENT_FILE")
@SuppressWarnings("serial")
public class DocumentFile implements java.io.Serializable {
	private String documentFileId;
	private byte[] documentFile;
	private Set<Document> documents = new HashSet<Document>(0);

	public DocumentFile() {
	}

    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")@Id @GeneratedValue(generator="generator")

    @Column(name="DOCUMENT_FILE_ID", unique=true, nullable=false, length=36)
	public String getDocumentFileId() {
		return this.documentFileId;
	}

	public void setDocumentFileId(String documentFileId) {
		this.documentFileId = documentFileId;
	}


    @Column(name="DOCUMENT_FILE", nullable=false)
	public byte[] getDocumentFile() {
		return this.documentFile;
	}

	public void setDocumentFile(byte[] documentFile) {
		this.documentFile = documentFile;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="documentFile")
	public Set<Document> getDocuments() {
		return this.documents;
	}

	public void setDocuments(Set<Document> documents) {
		this.documents = documents;
	}

}
