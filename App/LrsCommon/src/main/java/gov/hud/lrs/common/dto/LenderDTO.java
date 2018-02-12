package gov.hud.lrs.common.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class LenderDTO implements Serializable {

	private String lenderId;
	private String name;
	private AdminContactDTO adminContact;

	public AdminContactDTO getAdminContact() {
		return adminContact;
	}

	public void setAdminContact(AdminContactDTO adminContact) {
		this.adminContact = adminContact;
	}

	public String getLenderId() {
		return lenderId;
	}

	public void setLenderId(String lenderId) {
		this.lenderId = lenderId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public class AdminContactDTO {
		
		private String firstName;
		private String middleInitial;
		private String lastName;
		private String phoneNumber;
		private String faxNumber;
		private String email;
		private String secondaryEmail;
		
		public String getFirstName() {
			return firstName;
		}
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}
		public String getMiddleInitial() {
			return middleInitial;
		}
		public void setMiddleInitial(String middleInitial) {
			this.middleInitial = middleInitial;
		}
		public String getLastName() {
			return lastName;
		}
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
		public String getPhoneNumber() {
			return phoneNumber;
		}
		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}
		public String getFaxNumber() {
			return faxNumber;
		}
		public void setFaxNumber(String faxNumber) {
			this.faxNumber = faxNumber;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getSecondaryEmail() {
			return secondaryEmail;
		}
		public void setSecondaryEmail(String secondaryEmail) {
			this.secondaryEmail = secondaryEmail;
		}
	}

}
