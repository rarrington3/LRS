package gov.hud.lrs.common.dto;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class MuleLenderSearchResultsDTO implements Serializable {

	private MuleLenderSearchResults lenderSearchResults;

	public MuleLenderSearchResults getLenderSearchResults() {
		return lenderSearchResults;
	}

	public void setLenderSearchResults(MuleLenderSearchResults lenderSearchResults) {
		this.lenderSearchResults = lenderSearchResults;
	}

	public class MuleLenderSearchResults extends MuleResponseBase {

		private String sourceSystem;
		private List<MuleLenderDTO> lenders = null;

		public String getSourceSystem() {
			return sourceSystem;
		}

		public void setSourceSystem(String sourceSystem) {
			this.sourceSystem = sourceSystem;
		}

		public List<MuleLenderDTO> getLenders() {
			return lenders;
		}

		public void setLenders(List<MuleLenderDTO> lenders) {
			this.lenders = lenders;
		}
	}
}
