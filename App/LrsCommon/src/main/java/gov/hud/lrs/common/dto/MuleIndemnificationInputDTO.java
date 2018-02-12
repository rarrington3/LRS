package gov.hud.lrs.common.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class MuleIndemnificationInputDTO implements Serializable {

	private String caseNumber;
	private MuleIndemnificationDocumentDTO documents;
	private String mortgagee;
	private String billTo;
	private String agreementDate;
	private Integer term;
	private String expirationDate;
	private String startDate;
	private Boolean lenderEndorsed;
	private Boolean lenderExecutedAgreement;
	private Boolean indemnificationAgreementTransferable;
	private String qadLrsFileNumber;
	private String docketNumber;
	private String user;

	public String getCaseNumber() {
		return caseNumber;
	}

	public void setCaseNumber(String caseNumber) {
		this.caseNumber = caseNumber;
	}

	public MuleIndemnificationDocumentDTO getDocuments() {
		return documents;
	}

	public void setDocuments(MuleIndemnificationDocumentDTO documents) {
		this.documents = documents;
	}

	public String getMortgagee() {
		return mortgagee;
	}

	public void setMortgagee(String mortgagee) {
		this.mortgagee = mortgagee;
	}

	public String getBillTo() {
		return billTo;
	}

	public void setBillTo(String billTo) {
		this.billTo = billTo;
	}

	public String getAgreementDate() {
		return agreementDate;
	}

	public void setAgreementDate(String agreementDate) {
		this.agreementDate = agreementDate;
	}

	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public Boolean getLenderEndorsed() {
		return lenderEndorsed;
	}

	public void setLenderEndorsed(Boolean lenderEndorsed) {
		this.lenderEndorsed = lenderEndorsed;
	}

	public Boolean getLenderExecutedAgreement() {
		return lenderExecutedAgreement;
	}

	public void setLenderExecutedAgreement(Boolean lenderExecutedAgreement) {
		this.lenderExecutedAgreement = lenderExecutedAgreement;
	}

	public Boolean getIndemnificationAgreementTransferable() {
		return indemnificationAgreementTransferable;
	}

	public void setIndemnificationAgreementTransferable(Boolean indemnificationAgreementTransferable) {
		this.indemnificationAgreementTransferable = indemnificationAgreementTransferable;
	}

	public String getQadLrsFileNumber() {
		return qadLrsFileNumber;
	}

	public void setQadLrsFileNumber(String qadLrsFileNumber) {
		this.qadLrsFileNumber = qadLrsFileNumber;
	}

	public String getDocketNumber() {
		return docketNumber;
	}

	public void setDocketNumber(String docketNumber) {
		this.docketNumber = docketNumber;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

}
