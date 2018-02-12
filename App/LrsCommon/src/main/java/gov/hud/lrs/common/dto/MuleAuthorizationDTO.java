package gov.hud.lrs.common.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class MuleAuthorizationDTO implements Serializable {

	private String agencyId;
	private String authorizationName;
	private String insuranceType;
	private String roleCode;

	public MuleAuthorizationDTO() {
	}

	public String getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}

	public String getAuthorizationName() {
		return authorizationName;
	}

	public void setAuthorizationName(String authorizationName) {
		this.authorizationName = authorizationName;
	}

	public String getInsuranceType() {
		return insuranceType;
	}

	public void setInsuranceType(String insuranceType) {
		this.insuranceType = insuranceType;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

}
