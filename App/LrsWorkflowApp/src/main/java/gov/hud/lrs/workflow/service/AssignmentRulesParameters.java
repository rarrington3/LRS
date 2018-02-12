package gov.hud.lrs.workflow.service;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import gov.hud.lrs.common.entity.Batch;
import gov.hud.lrs.common.entity.BatchStatusRef;
import gov.hud.lrs.common.entity.Personnel;
import gov.hud.lrs.common.entity.PersonnelUnavailability;
import gov.hud.lrs.common.entity.ReviewLevel;
import gov.hud.lrs.common.enumeration.ReviewLevelTypeCodes;
import gov.hud.lrs.common.enumeration.ReviewTypeCodes;
import gov.hud.lrs.common.enumeration.SelectionReasonCodes;

@SuppressWarnings("serial")
public class AssignmentRulesParameters implements Serializable {

	private Personnel personnel;
	private ReviewLevel currentReviewLevel;
	private List<ReviewLevel> previousReviewLevels;
	private List<ReviewLevel> originalReviewReviewLevels;
	private List<Personnel> batchPersonnel;
	private Map<String, List<String>> reviewTypeCodeByPersonnelId;
	private Map<String, List<String>> reviewLevelTypeCodeByPersonnelId;
	private Map<String, List<String>> productTypeCodeByPersonnelId;
	private Map<String, List<String>> selectionReasonCodeByPersonnelId;
	private int remainingCapacity;
	private Map<String, Double> factorWeightByName;
	private Map<String, Double> factorScoreByName = new HashMap<String, Double>();
	private Double score = new Double(0.0);
	private boolean manualAssign;

	public Personnel getPersonnel() {
		return personnel;
	}

	public void setPersonnel(Personnel personnel) {
		this.personnel = personnel;
	}

	public ReviewLevel getCurrentReviewLevel() {
		return currentReviewLevel;
	}

	public void setCurrentReviewLevel(ReviewLevel currentReviewLevel) {
		this.currentReviewLevel = currentReviewLevel;
	}

	public List<Personnel> getBatchPersonnel() {
		return batchPersonnel;
	}

	public void setBatchPersonnel(List<Personnel> batchPersonnel) {
		this.batchPersonnel = batchPersonnel;
	}

	public List<ReviewLevel> getPreviousReviewLevels() {
		return previousReviewLevels;
	}

	public void setPreviousReviewLevels(List<ReviewLevel> previousReviewLevels) {
		this.previousReviewLevels = previousReviewLevels;
	}

	public List<ReviewLevel> getOriginalReviewReviewLevels() {
		return originalReviewReviewLevels;
	}

	public void setOriginalReviewReviewLevels(List<ReviewLevel> originalReviewReviewLevels) {
		this.originalReviewReviewLevels = originalReviewReviewLevels;
	}

	public Map<String, List<String>> getReviewTypeCodeByPersonnelId() {
		return reviewTypeCodeByPersonnelId;
	}

	public void setReviewTypeCodeByPersonnelId(Map<String, List<String>> reviewTypeCodeByPersonnelId) {
		this.reviewTypeCodeByPersonnelId = reviewTypeCodeByPersonnelId;
	}

	public Map<String, List<String>> getReviewLevelTypeCodeByPersonnelId() {
		return reviewLevelTypeCodeByPersonnelId;
	}

	public void setReviewLevelTypeCodeByPersonnelId(Map<String, List<String>> reviewLevelTypeCodeByPersonnelId) {
		this.reviewLevelTypeCodeByPersonnelId = reviewLevelTypeCodeByPersonnelId;
	}

	public Map<String, List<String>> getProductTypeCodeByPersonnelId() {
		return productTypeCodeByPersonnelId;
	}

	public void setProductTypeCodeByPersonnelId(Map<String, List<String>> productTypeCodeByPersonnelId) {
		this.productTypeCodeByPersonnelId = productTypeCodeByPersonnelId;
	}

	public Map<String, List<String>> getSelectionReasonCodeByPersonnelId() {
		return selectionReasonCodeByPersonnelId;
	}

	public void setSelectionReasonCodeByPersonnelId(Map<String, List<String>> selectionReasonCodeByPersonnelId) {
		this.selectionReasonCodeByPersonnelId = selectionReasonCodeByPersonnelId;
	}

	public int getRemainingCapacity() {
		return remainingCapacity;
	}

	public void setRemainingCapacity(int remainingCapacity) {
		this.remainingCapacity = remainingCapacity;
	}

	public Map<String, Double> getFactorWeightByName() {
		return factorWeightByName;
	}

	public void setFactorWeightByName(Map<String, Double> factorWeightByName) {
		this.factorWeightByName = factorWeightByName;
	}

	public Map<String, Double> getFactorScoreByName() {
		return factorScoreByName;
	}

	public void setFactorScoreByName(Map<String, Double> factorScoreByName) {
		this.factorScoreByName = factorScoreByName;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public boolean isManualAssign() {
		return manualAssign;
	}

	public void setManualAssign(boolean manualAssign) {
		this.manualAssign = manualAssign;
	}

	public void addFactorScore(String factorName) {	// score is implied to be one, so just multiplied by weight
		addFactorScore(factorName, 1.0);
	}

	public void addFactorScore(String factorName, double score) {
		Double weight = factorWeightByName.get(factorName);
		if (weight == null) {
			throw new RuntimeException("No such factor: " + factorName);
		}

		double factorScore = weight * score;
		factorScoreByName.put(factorName, factorScore);

		this.score = this.score + factorScore;
	}

	public boolean personnelHasReviewType() {
		String reviewTypeCode = currentReviewLevel.getReview().getReviewTypeRef().getReviewTypeCd();
		List<String> reviewTypeCodes = reviewTypeCodeByPersonnelId.get(personnel.getPersonnelId());
		return (reviewTypeCodes != null) && reviewTypeCodes.contains(reviewTypeCode);
	}

	public boolean personnelHasReviewLevelType() {
		String reviewLevelCode = currentReviewLevel.getReviewLevelTypeRef().getReviewLevelCd();
		if (reviewLevelCode.equals(ReviewLevelTypeCodes.MITIGATION)) {
			reviewLevelCode = ReviewLevelTypeCodes.INITIAL;
		}
		if (selectionReasonIsNationalQCOrReviewLocationQC () && reviewLevelCode.equals(ReviewLevelTypeCodes.INITIAL)) {
			return true;
		}
		List<String> reviewLevelCodes = reviewLevelTypeCodeByPersonnelId.get(personnel.getPersonnelId());
		return (reviewLevelCodes != null) && reviewLevelCodes.contains(reviewLevelCode);
	}

	public boolean personnelHasProductType() {
		if (ReviewTypeCodes.OPERATIONAL.equalsIgnoreCase(currentReviewLevel.getReview().getReviewTypeRef().getReviewTypeCd())) {
			return true;
		}
		String productTypeCode = currentReviewLevel.getReview().getProductTypeRef().getProductTypeCd();
		List<String> productTypeCodes = productTypeCodeByPersonnelId.get(personnel.getPersonnelId());
		return (productTypeCodes != null) && productTypeCodes.contains(productTypeCode);
	}

	public boolean personnelHasSelectionReason() {
		String selectionReasonCode = currentReviewLevel.getReview().getSelectionReason().getCode();
		List<String> selectionReasonCodes = selectionReasonCodeByPersonnelId.get(personnel.getPersonnelId());
		return (selectionReasonCodes != null) && selectionReasonCodes.contains(selectionReasonCode);
	}

	public boolean reviewLevelIsMitigation1AndReviewerUnavailableAndUnavailablityEndsIn7Days() {
		return
			currentReviewLevel.getReviewLevelTypeRef().getReviewLevelCd().equals(ReviewLevelTypeCodes.MITIGATION) &&
			(currentReviewLevel.getIterationNumber() == 1) &&
			!(personnel.getAvailabilityInd() == 'Y') &&
			(getCurrentUnavailabilityPeriod() < 7);
	}

	public boolean reviewLevelIsMitigationAndReviewerIsPreviousReviewLevelReviewer() {
		return
			currentReviewLevel.getReviewLevelTypeRef().getReviewLevelCd().equals(ReviewLevelTypeCodes.MITIGATION) &&
			previousReviewLevelReviewer();
	}
	
	public boolean reviewLevelIsMitigationAndReviewerIsNotPreviousReviewLevelReviewer() {
		return
			currentReviewLevel.getReviewLevelTypeRef().getReviewLevelCd().equals(ReviewLevelTypeCodes.MITIGATION) &&
			!previousReviewLevelReviewer();
	}

	private static final String REVIEW_LOCATION_HQ = "HQ";

	public boolean reviewLevelIsHQEscalationAndReviewerLocationIsHQ() {
		return
			currentReviewLevel.getReviewLevelTypeRef().getReviewLevelCd().equals(ReviewLevelTypeCodes.HQ_ESCALATION) &&
			currentReviewLevel.getReviewLocation().getLocationName().equals(REVIEW_LOCATION_HQ);
	}

	public boolean reviewerLocationIsSameAndReviewLevelIsNotHQEscalation() {
		return
			currentReviewLevel.getReviewLocation().getLocationName().equals(personnel.getReviewLocation().getLocationName()) &&
			!currentReviewLevel.getReviewLevelTypeRef().getReviewLevelCd().equals(ReviewLevelTypeCodes.HQ_ESCALATION);
	}

	public boolean batchExistsAndReviewerNotInBatch() {
		if (manualAssign) {
			return false;
		}
		
		Batch batch = currentReviewLevel.getReview().getBatch();
		if (batch == null) {
			// vaccuously false: no batch means we don't apply the -5000 penalty
			return false;
		}

		if (
			!(batch.getBatchStatusRef().equals(BatchStatusRef.UNDER_BATCH_REVIEW)) &&	// going to the batch owner who may not be part of the team and so we don't want to apply the penalty
			!ReviewTypeCodes.OPERATIONAL.equalsIgnoreCase(currentReviewLevel.getReview().getReviewTypeRef().getReviewTypeCd()) &&	// doesn't apply for OPER reviews
			(
				// batch team rule only applies to INIT and MITG levels
				ReviewLevelTypeCodes.INITIAL.equalsIgnoreCase(currentReviewLevel.getReviewLevelTypeRef().getReviewLevelCd()) ||
				ReviewLevelTypeCodes.MITIGATION.equalsIgnoreCase(currentReviewLevel.getReviewLevelTypeRef().getReviewLevelCd())
			)
		) {
			boolean found = false;
			for (Personnel p : batchPersonnel) {
				if (personnel.getPersonnelId().equals(p.getPersonnelId())) {
					found = true;
				}
			}
			return !found;
		}

		return false;
	}

	public boolean selectionReasonIsNationalQCOrReviewLocationQC() {
		String selReasonCode = currentReviewLevel.getReview().getSelectionReason().getCode();
		return (selReasonCode.equals(SelectionReasonCodes.NATIONAL_QC) || selReasonCode.equals(SelectionReasonCodes.REVIEW_LOCATION_QC));
	}

	/**
	 * Check all the previous review levels whether this reviewer is the original reviewer of any review levels.
	 * @return
	 */
	public boolean reviewerIsOriginalReviewerForPreviousReviewLevel() {
		for (ReviewLevel reviewLevel : originalReviewReviewLevels) {
			if (
				(reviewLevel.getReviewerPersonnel() != null) &&
				reviewLevel.getReviewerPersonnel().getPersonnelId().equals(personnel.getPersonnelId())
			) {
				return true;
			}
		}
		return false;
	}

	public boolean reviewLevelIsEscalationOrHqEscalationIterationGreaterThan1() {
		String reviewLevelCd = currentReviewLevel.getReviewLevelTypeRef().getReviewLevelCd();
		return ((reviewLevelCd.equals(ReviewLevelTypeCodes.HQ_ESCALATION) || reviewLevelCd.equals(ReviewLevelTypeCodes.ESCALATION))
			&& (currentReviewLevel.getIterationNumber() > 1));
	}

	public boolean reviewLevelIsEscalation1() {
		String reviewLevelCd = currentReviewLevel.getReviewLevelTypeRef().getReviewLevelCd();
		return (reviewLevelCd.equals(ReviewLevelTypeCodes.ESCALATION) && (currentReviewLevel.getIterationNumber() == 1));
	}
	
	public boolean previousReviewLevelReviewerReportsToThisReviewer() {
		if (!previousReviewLevels.isEmpty()) {
			ReviewLevel previousReviewLevel = previousReviewLevels.get(previousReviewLevels.size() - 1);
			Personnel reportsToPersonnel = previousReviewLevel.getReviewerPersonnel().getReportsToPersonnel();
			if (reportsToPersonnel != null) {
				return reportsToPersonnel.getPersonnelId().equals(personnel.getPersonnelId());
			}
		}
		return false;
	}

	public boolean previousReviewLevelReviewer() {
		if (!previousReviewLevels.isEmpty()) {
			ReviewLevel previousReviewLevel = previousReviewLevels.get(previousReviewLevels.size() - 1);
			return personnel.getPersonnelId().equals(previousReviewLevel.getReviewerPersonnel().getPersonnelId());
		}
		return false;
	}

	public int getCurrentUnavailabilityPeriod() {
		Date now = new Date();
		for (PersonnelUnavailability perosnnelUnavailability : personnel.getPersonnelUnavailabilities()) {
			// Check the current date is between unavilable start and end dates
		   if (now.after(perosnnelUnavailability.getUnavailStartTs()) && now.before(perosnnelUnavailability.getUnavailEndTs())) {
			   // Calculate number of days
			   long diff = now.getTime() - perosnnelUnavailability.getUnavailEndTs().getTime();
			   int days = (int)TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
			   return days;
		   }
		}
		return 0;
	}

}
