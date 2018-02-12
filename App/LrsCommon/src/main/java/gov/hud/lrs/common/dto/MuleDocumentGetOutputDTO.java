package gov.hud.lrs.common.dto;

@SuppressWarnings("serial")
public class MuleDocumentGetOutputDTO extends MuleResponseBase {
	
	private String documentType;
	private String documentKey;
	private String documentName;
	private String document;
	
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	public String getDocumentKey() {
		return documentKey;
	}
	public void setDocumentKey(String documentKey) {
		this.documentKey = documentKey;
	}
	public String getDocumentName() {
		return documentName;
	}
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	public String getDocument() {
		return document;
	}
	public void setDocument(String document) {
		this.document = document;
	}
	
}
