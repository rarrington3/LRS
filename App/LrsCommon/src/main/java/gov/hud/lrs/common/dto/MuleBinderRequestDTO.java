package gov.hud.lrs.common.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class MuleBinderRequestDTO implements Serializable {

	private String caseNumber;
	private String reason;
	private String hoc;
	private String user;
	
	public String getCaseNumber() {
		return caseNumber;
	}
	public void setCaseNumber(String caseNumber) {
		this.caseNumber = caseNumber;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getHoc() {
		return hoc;
	}
	public void setHoc(String hoc) {
		this.hoc = hoc;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
}