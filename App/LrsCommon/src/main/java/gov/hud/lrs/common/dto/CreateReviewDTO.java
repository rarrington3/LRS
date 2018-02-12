package gov.hud.lrs.common.dto;

public class CreateReviewDTO {

	// for a given review, only one of these should be set
	private String selectionId;
	private String batchId;

	/**
	 * @return the selectionId
	 */
	public String getSelectionId() {
		return selectionId;
	}

	/**
	 * @param selectionId the selectionId to set
	 */
	public void setSelectionId(String selectionId) {
		this.selectionId = selectionId;
	}

	/**
	 * @return the batchId
	 */
	public String getBatchId() {
		return batchId;
	}

	/**
	 * @param batchId the batchId to set
	 */
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

}
