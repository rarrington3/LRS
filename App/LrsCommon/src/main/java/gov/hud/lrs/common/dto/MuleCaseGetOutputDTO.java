package gov.hud.lrs.common.dto;

import java.io.Serializable;

import com.fasterxml.jackson.databind.JsonNode;

@SuppressWarnings("serial")
public class MuleCaseGetOutputDTO implements Serializable {

	private GetCaseResult getCaseResult;

	public GetCaseResult getGetCaseResult() {
		return getCaseResult;
	}

	public void setGetCaseResult(GetCaseResult getCaseResult) {
		this.getCaseResult = getCaseResult;
	}

	public static class GetCaseResult extends MuleResponseBase {

		private JsonNode caseDetail;

		public JsonNode getCaseDetail() {
			return caseDetail;
		}

		public void setCaseDetail(JsonNode caseDetail) {
			this.caseDetail = caseDetail;
		}

	}
}
