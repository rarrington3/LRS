package gov.hud.lrs.common.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class MuleAuditHeaderDTO implements Serializable {

	private String auditCorrelationId;
	private String serviceRequestTimestamp;
	private String sourceSystem;
	private String endUserId;
	private String authenticationId;
	private String tenantId;
	private String locale;

	public String getAuditCorrelationId() {
		return auditCorrelationId;
	}

	public void setAuditCorrelationId(String auditCorrelationId) {
		this.auditCorrelationId = auditCorrelationId;
	}

	public String getServiceRequestTimestamp() {
		return serviceRequestTimestamp;
	}

	public void setServiceRequestTimestamp(String serviceRequestTimestamp) {
		this.serviceRequestTimestamp = serviceRequestTimestamp;
	}

	public String getSourceSystem() {
		return sourceSystem;
	}

	public void setSourceSystem(String sourceSystem) {
		this.sourceSystem = sourceSystem;
	}

	public String getEndUserId() {
		return endUserId;
	}

	public void setEndUserId(String endUserId) {
		this.endUserId = endUserId;
	}

	public String getAuthenticationId() {
		return authenticationId;
	}

	public void setAuthenticationId(String authenticationId) {
		this.authenticationId = authenticationId;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

}
