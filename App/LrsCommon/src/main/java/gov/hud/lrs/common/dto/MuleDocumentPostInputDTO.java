package gov.hud.lrs.common.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class MuleDocumentPostInputDTO implements Serializable {

	private String documentType;
	private String documentKey;
	private String documentName;
	private String documentMimeType;
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
	public String getDocumentMimeType() {
		return documentMimeType;
	}
	public void setDocumentMimeType(String documentMimeType) {
		this.documentMimeType = documentMimeType;
	}
	public String getDocument() {
		return document;
	}
	public void setDocument(String document) {
		this.document = document;
	}
	
}
