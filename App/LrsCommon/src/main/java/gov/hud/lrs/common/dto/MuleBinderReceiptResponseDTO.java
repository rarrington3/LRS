package gov.hud.lrs.common.dto;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class MuleBinderReceiptResponseDTO implements Serializable {
	private String statusCode;
	private String statusMessage;
	private List<CaseBinderDTO> caseBinders = null;

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public List<CaseBinderDTO> getCaseBinders() {
		return caseBinders;
	}

	public void setCaseBinders(List<CaseBinderDTO> caseBinders) {
		this.caseBinders = caseBinders;
	}

	public static class CaseBinderDTO implements Serializable {

		public static final String EBINDER_CASE_ELECTRONIC = "Electronic";
		public static final String RECEIVE_DATE_NOT_APPLICABLE = "N/A";

		private String caseNumber;
		private String ebinderCase;
		private String hoc;
		private String receiveDate;

		public String getCaseNumber() {
			return caseNumber;
		}

		public void setCaseNumber(String caseNumber) {
			this.caseNumber = caseNumber;
		}

		public String getEbinderCase() {
			return ebinderCase;
		}

		public void setEbinderCase(String ebinderCase) {
			this.ebinderCase = ebinderCase;
		}

		public String getHoc() {
			return hoc;
		}

		public void setHoc(String hoc) {
			this.hoc = hoc;
		}

		public String getReceiveDate() {
			return receiveDate;
		}

		public void setReceiveDate(String receiveDate) {
			this.receiveDate = receiveDate;
		}

	}

}
