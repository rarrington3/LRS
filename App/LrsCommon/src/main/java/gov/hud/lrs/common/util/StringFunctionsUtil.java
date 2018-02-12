package gov.hud.lrs.common.util;

public class StringFunctionsUtil {

	public static String sanitizeText(String text) {
		if (text != null) {
			return text.replace('\n', '_').replace('\r', '_');
		} else {
			return text;
		}
	}
	
	public static String buildAddress(String addr1, String addr2, String city, String state, String zip) {
		String address = (addr1 == null ? "" : addr1 + ", ") + (addr2 == null ? "" : addr2 + ", ")
				+ (city == null ? "" : city + ", ") + (state == null ? "" : state + " ") + (zip == null ? "" : zip);
		return address;
	}
	
	public static String caseNumberTrim(String caseNumber) {
		if (caseNumber != null) {
			return caseNumber.trim().replace("-", "");
		} else {
			return caseNumber;
		}
	}

	public static String caseNumberPad(String caseNumber) {
		if (caseNumber == null || caseNumber.trim().length() != 10) {
			return caseNumber;
		} else {
			String caseNum = caseNumber.trim();
			return caseNum.substring(0, 3) + "-" + caseNum.substring(3);
		}
	}
}
