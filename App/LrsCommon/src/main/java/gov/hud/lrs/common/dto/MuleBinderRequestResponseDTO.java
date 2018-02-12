package gov.hud.lrs.common.dto;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class MuleBinderRequestResponseDTO implements Serializable {

	private EsbExceptionDTO esbException;
	private String statusCode;
	private String statusMessage;

	public EsbExceptionDTO getEsbException() {
		return esbException;
	}

	public void setEsbException(EsbExceptionDTO esbException) {
		this.esbException = esbException;
	}
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
	
	public static class EsbExceptionDTO implements Serializable {
	
		private String auditCorrelationId;
		private Date serviceRequestTimestamp;
		private String sourceSystem;
		private String errorCode;
		private String errorMessage;
		private String shortDescription;

		public EsbExceptionDTO() {
		}

		public String getAuditCorrelationId() {
			return auditCorrelationId;
		}

		public void setAuditCorrelationId(String auditCorrelationId) {
			this.auditCorrelationId = auditCorrelationId;
		}

		public Date getServiceRequestTimestamp() {
			return serviceRequestTimestamp;
		}

		public void setServiceRequestTimestamp(Date serviceRequestTimestamp) {
			this.serviceRequestTimestamp = serviceRequestTimestamp;
		}

		public String getSourceSystem() {
			return sourceSystem;
		}

		public void setSourceSystem(String sourceSystem) {
			this.sourceSystem = sourceSystem;
		}

		public String getErrorCode() {
			return errorCode;
		}

		public void setErrorCode(String errorCode) {
			this.errorCode = errorCode;
		}

		public String getErrorMessage() {
			return errorMessage;
		}

		public void setErrorMessage(String errorMessage) {
			this.errorMessage = errorMessage;
		}

		public String getShortDescription() {
			return shortDescription;
		}

		public void setShortDescription(String shortDescription) {
			this.shortDescription = shortDescription;
		}

	}
}
