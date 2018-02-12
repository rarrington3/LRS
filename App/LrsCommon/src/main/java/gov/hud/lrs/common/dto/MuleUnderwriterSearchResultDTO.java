package gov.hud.lrs.common.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class MuleUnderwriterSearchResultDTO implements Serializable {

	private UnderwriterProperties properties;

	public UnderwriterProperties getProperties() {
		return properties;
	}

	public void setProperties(UnderwriterProperties properties) {
		this.properties = properties;
	}

	public class UnderwriterProperties extends MuleResponseBase {

		private String underwriterId;
		private String underwriterName;
		private String houseNumber;
		private String streetName;
		private String streetType;
		private String city;
		private String state;
		private String zip;
		private String lender;
		private String status;

		public String getUnderwriterId() {
			return underwriterId;
		}

		public void setUnderwriterId(String underwriterId) {
			this.underwriterId = underwriterId;
		}

		public String getUnderwriterName() {
			return underwriterName;
		}

		public void setUnderwriterName(String underwriterName) {
			this.underwriterName = underwriterName;
		}

		public String getHouseNumber() {
			return houseNumber;
		}

		public void setHouseNumber(String houseNumber) {
			this.houseNumber = houseNumber;
		}

		public String getStreetName() {
			return streetName;
		}

		public void setStreetName(String streetName) {
			this.streetName = streetName;
		}

		public String getStreetType() {
			return streetType;
		}

		public void setStreetType(String streetType) {
			this.streetType = streetType;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}

		public String getZip() {
			return zip;
		}

		public void setZip(String zip) {
			this.zip = zip;
		}

		public String getLender() {
			return lender;
		}

		public void setLender(String lender) {
			this.lender = lender;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

	}

}
