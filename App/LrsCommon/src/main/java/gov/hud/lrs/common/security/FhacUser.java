package gov.hud.lrs.common.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@SuppressWarnings("serial")
public class FhacUser implements UserDetails, Serializable {
	private String userId;
	private String firstName;
	private String lastName;
	private String lenderId;
	private List<String> roles;
	private List<SimpleGrantedAuthority> authorities;

	@Override
	public String getUsername() {
		return userId;
	}
	
	@Override
	public String getPassword() {
		return "";
	}

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

	public String getLenderId() {
		return lenderId;
	}

	public void setLenderId(String lenderId) {
		this.lenderId = lenderId;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
		authorities = new ArrayList<SimpleGrantedAuthority>(roles.size());
		for (String role : roles) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
	}

	@Override
	public List<SimpleGrantedAuthority> getAuthorities() {
		return authorities;		
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
