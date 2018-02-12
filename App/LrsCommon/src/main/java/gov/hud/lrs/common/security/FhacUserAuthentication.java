package gov.hud.lrs.common.security;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

@SuppressWarnings("serial")
public class FhacUserAuthentication implements Authentication {

	private final FhacUser fhacUser;
	private boolean authenticated = true;

	public FhacUserAuthentication(FhacUser fhacUser) {
		this.fhacUser = fhacUser;
	}

	@Override
	public String getName() {
		return fhacUser.getUserId();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return fhacUser.getAuthorities();
	}

	@Override
	public String getCredentials() {
		return fhacUser.getPassword();
	}

	@Override
	public FhacUser getDetails() {
		return fhacUser;
	}

	@Override
	public String getPrincipal() {
		return fhacUser.getUserId();
	}

	@Override
	public boolean isAuthenticated() {
		return authenticated;
	}

	@Override
	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}

	public FhacUser getFhacUser() {
		return fhacUser;
	}

}
