package gov.hud.lrs.common.dto;

import com.fasterxml.jackson.databind.JsonNode;

@SuppressWarnings("serial")
public class MuleAuthorizationsSearchResultsDTO extends MuleResponseBase {

	private String userId;
	private String firstName;
	private String lastName;
	private String applicationName;
	private String userStatus;
	private JsonNode securityAuthorizations;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public JsonNode getSecurityAuthorizations() {
		return securityAuthorizations;
	}

	public void setSecurityAuthorizations(JsonNode securityAuthorizations) {
		this.securityAuthorizations = securityAuthorizations;
	}
}
