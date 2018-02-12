package gov.hud.lrs.common.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class MuleUniverseGetOutputDTO implements Serializable {

	private GetUniverseResult getUniverseResult;

	public GetUniverseResult getGetUniverseResult() {
		return getUniverseResult;
	}

	public void setGetUniverseResult(GetUniverseResult getUniverseResult) {
		this.getUniverseResult = getUniverseResult;
	}

	public static class GetUniverseResult extends MuleResponseBase {
	
		public GetUniverseResult() {
		}

		private MuleUniverseGetCaseDetailDTO[] caseDetail;

		public MuleUniverseGetCaseDetailDTO[] getCaseDetail() {
			return caseDetail;
		}

		public void setCaseDetail(MuleUniverseGetCaseDetailDTO[] caseDetail) {
			this.caseDetail = caseDetail;
		}
	}

}
