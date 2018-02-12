package gov.hud.lrs.common.dto;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class MuleBinderReceiptDTO implements Serializable {

	private List<String> caseNumbers = null;

	public List<String> getCaseNumbers() {
		return caseNumbers;
	}

	public void setCaseNumbers(List<String> caseNumbers) {
		this.caseNumbers = caseNumbers;
	}

}
