package gov.hud.lrs.common.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import gov.hud.lrs.common.entity.Personnel;
import gov.hud.lrs.common.entity.ReviewLocation;
import gov.hud.lrs.common.enumeration.PersonnelStatusCodes;
import gov.hud.lrs.common.enumeration.Roles;
import gov.hud.lrs.common.repository.PersonnelRepository;

@Service
public class SecurityService {

	@Autowired private PersonnelRepository personnelRepository;

	private final FhacUser systemUser;
	private final FhacUser testUser;
	private final FhacUser monitorUser;

	public SecurityService() {
		systemUser = new FhacUser();
		systemUser.setFirstName("LRS");
		systemUser.setLastName("SYS");
		systemUser.setUserId("LRSSYS");
		systemUser.setRoles(Arrays.asList(Roles.ROLE_SYSTEM));

		testUser= new FhacUser();
		testUser.setFirstName("TEST");
		testUser.setLastName("TEST");
		testUser.setUserId("TEST");
		testUser.setRoles(new ArrayList<String>());	// none
		
		monitorUser= new FhacUser();
		monitorUser.setFirstName("LRS");
		monitorUser.setLastName("MONITOR");
		monitorUser.setUserId("LRSMONITOR");
		monitorUser.setRoles(Arrays.asList(Roles.ROLE_MONITOR_READ_ONLY));	
	}

	public boolean isAuthenticated() {
		return SecurityContextHolder.getContext().getAuthentication() != null;
	}

	public void setFhacUser(FhacUser fhacUser) {
		SecurityContextHolder.getContext().setAuthentication(new FhacUserAuthentication(fhacUser));
	}

	// used for batch jobs and other system tasks that are invoked automatically
	// not that this is *not* used for jobs that are manually triggered by a user; the regular FHAC user is propagated in such actions
	public void setSystemUser() {
		setFhacUser(systemUser);
	}

	// only ever used for integration tests
	public void setTestUser() {
		setFhacUser(testUser);
	}
	
	// use to monitor the heart beat 
	public void setMonitorUser() {
		setFhacUser(monitorUser);
	}

	public String getUserId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			throw new RuntimeException("We don't have any authentication for the current user. Are you calling this from a REST client and not providing the auth header?");
		}
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

	public FhacUser getFhacUser() {
		FhacUserAuthentication fhacUserAuthentication = (FhacUserAuthentication)SecurityContextHolder.getContext().getAuthentication();
		if (fhacUserAuthentication == null) {
			throw new RuntimeException("We don't have any authentication for the current user. Are you calling this from a REST client and not providing the auth header?");
		}
		return fhacUserAuthentication.getFhacUser();
	}

	public String getFullName() {
		FhacUserAuthentication fhacUserAuthentication = (FhacUserAuthentication)SecurityContextHolder.getContext().getAuthentication();
		if (fhacUserAuthentication == null) {
			throw new RuntimeException("We don't have any authentication for the current user. Are you calling this from a REST client and not providing the auth header?");
		}
		FhacUser fhacUser = fhacUserAuthentication.getFhacUser();
		return fhacUser.getLastName() + ", " + fhacUser.getFirstName();
	}
	
	public String getLenderId() {
		return getFhacUser().getLenderId();
	}

	public Personnel getPersonnel() {
		String userId = getUserId();
		Personnel personnel = personnelRepository.findByLoginCredential(userId);
		if (personnel == null) {
			throw new RuntimeException("No active Personnel for loginCredential " + userId);
		}
		return personnel;
	}

	public ReviewLocation getReviewLocation() {
		Personnel personnel = getPersonnel();
		if (personnel == null) {
			return null;
		}
		return personnel.getReviewLocation();
	}

	public String getReviewLocationId() {
		Personnel personnel = getPersonnel();
		if (personnel == null) {
			return null;
		}
		return personnel.getReviewLocation().getReviewLocationId();	// should not cause a DB roundtrip
	}

	public boolean hasRole(String role) {
		return SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority(role.toString()));
	}

	public boolean isActive(String userId) {
		Personnel personnel = personnelRepository.findByLoginCredentialAndPersonnelStatusRefCode(userId, PersonnelStatusCodes.ACTIVE);
		if (personnel != null) {
			return true;
		}
		return false;
	}
	
	public List<String> getRoles() {
		List<String> roles = new ArrayList<String>();
		for (GrantedAuthority grantedAuthority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
			roles.add(grantedAuthority.getAuthority());
		}
		return roles;
	}

	public boolean isLender() {
		return (
			(hasRole(Roles.ROLE_INDEMNIFIER)) ||
        	(hasRole(Roles.ROLE_RESPONSE_COORDINATOR)) ||
        	(hasRole(Roles.ROLE_LRS_READ_ONLY))
        );
	}

}
