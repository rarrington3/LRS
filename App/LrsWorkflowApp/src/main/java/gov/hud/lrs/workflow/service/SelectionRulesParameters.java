package gov.hud.lrs.workflow.service;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import gov.hud.lrs.common.entity.LenderIncreasedSelection;
import gov.hud.lrs.common.entity.LenderSuppression;
import gov.hud.lrs.common.entity.LoanSelectionCaseSummary;
import gov.hud.lrs.common.entity.ReviewTypeRef;
import gov.hud.lrs.common.entity.UnderwriterIncreasedSelection;
import gov.hud.lrs.common.enumeration.ReviewTypeCodes;
import gov.hud.lrs.common.enumeration.ScoringModelCodes;
import gov.hud.lrs.workflow.service.SelectionService.ScoringModelContext;

@SuppressWarnings("serial")
public class SelectionRulesParameters implements Serializable {

	private ScoringModelContext scoringModelContext;
	private Map<String, ScoringModelContext> scoringModelCodeToContext;
	private LoanSelectionCaseSummary loanSelectionCaseSummary;
	private Map<String, LenderSuppression> lenderIdToSuppression;
	private Map<String, LenderIncreasedSelection> lenderIdToIncreasedSelection;
	private Map<String, UnderwriterIncreasedSelection> underwriterIdToIncreasedSelection;
	private Set<String> existingUnderwritingLoanSelectionCaseNumbers;
	private SecureRandom secureRandom;
	private Map<String, Double> factorNameToWeight;
	private Map<String, Double> factorNameToScore = new HashMap<String, Double>();
	private double score;
    private ReviewTypeRef reviewTypeRef;

	public Map<String, ScoringModelContext> getScoringModelCodeToContext() {
		return scoringModelCodeToContext;
	}

	public void setScoringModelCodeToContext(
			Map<String, ScoringModelContext> scoringModelCodeToContext) {
		this.scoringModelCodeToContext = scoringModelCodeToContext;
	}

	public ScoringModelContext getScoringModelContext() {
		return scoringModelContext;
	}

	public void setScoringModelContext(SelectionService.ScoringModelContext scoringModelContext) {
		this.scoringModelContext = scoringModelContext;
	}

	public LoanSelectionCaseSummary getLoanSelectionCaseSummary() {
		return loanSelectionCaseSummary;
	}

	public void setLoanSelectionCaseSummary(LoanSelectionCaseSummary loanSelectionCaseSummary) {
		this.loanSelectionCaseSummary = loanSelectionCaseSummary;
	}

	public void setSecureRandom(SecureRandom secureRandom) {
		this.secureRandom = secureRandom;
	}

	public void setLenderIdToSuppression(Map<String, LenderSuppression> lenderIdToSuppression) {
		this.lenderIdToSuppression = lenderIdToSuppression;
	}

	public Map<String, LenderIncreasedSelection> getLenderIdToIncreasedSelection() {
		return lenderIdToIncreasedSelection;
	}

	public void setLenderIdToIncreasedSelection(Map<String, LenderIncreasedSelection> lenderIdToIncreasedSelection) {
		this.lenderIdToIncreasedSelection = lenderIdToIncreasedSelection;
	}

	public Map<String, UnderwriterIncreasedSelection> getUnderwriterIdToIncreasedSelection() {
		return underwriterIdToIncreasedSelection;
	}

	public void setUnderwriterIdToIncreasedSelection(Map<String, UnderwriterIncreasedSelection> underwriterIdToIncreasedSelection) {
		this.underwriterIdToIncreasedSelection = underwriterIdToIncreasedSelection;
	}

	public Map<String, Double> getFactorNameToWeight() {
		return factorNameToWeight;
	}

	public void setFactorNameToWeight(Map<String, Double> factorNameToWeight) {
		this.factorNameToWeight = factorNameToWeight;
	}

	public Map<String, Double> getFactorNameToScore() {
		return factorNameToScore;
	}

	public void setFactorNameToScore(Map<String, Double> factorNameToScore) {
		this.factorNameToScore = factorNameToScore;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public ReviewTypeRef getReviewTypeRef() {
		return reviewTypeRef;
	}

	public void setReviewTypeRef(ReviewTypeRef reviewTypeRef) {
		this.reviewTypeRef = reviewTypeRef;
	}

	public Set<String> getExistingUnderwritingLoanSelectionCaseNumberss() {
		return existingUnderwritingLoanSelectionCaseNumbers;
	}

	public void setExistingUnderwritingLoanSelectionCaseNumbers(Set<String> existingUnderwritingLoanSelectionCaseNumbers) {
		this.existingUnderwritingLoanSelectionCaseNumbers = existingUnderwritingLoanSelectionCaseNumbers;
	}

	public boolean hasFactor(String factorName) {
		return factorNameToWeight.containsKey(factorName);
	}

	public void addFactorScore(String factorName) {
		addFactorScore(factorName, 1.0);
	}

	public void addFactorScore(String factorName, double factorScore) {
		Double weight = factorNameToWeight.get(factorName);
		if (weight == null) {
			throw new RuntimeException("Unknown factor: " + factorName);
		}

		if (factorNameToScore.containsKey(factorName)) {
			throw new RuntimeException("Factor " + factorName + " already scored");
		}

		double weightedFactorScore = factorScore * weight;
		factorNameToScore.put(factorName, weightedFactorScore);
		score += weightedFactorScore;
	}

	public int d100() {
		return secureRandom.nextInt(100) + 1;
	}

	public boolean lenderSuppressed() {
		String lenderId = null;
		if (reviewTypeRef.getReviewTypeCd().equals(ReviewTypeCodes.SERVICING)) {
			lenderId = loanSelectionCaseSummary.getSrvcrMtgee5A43();
		} else if (
			reviewTypeRef.getReviewTypeCd().equals(ReviewTypeCodes.UNDERWRITING) ||
			reviewTypeRef.getReviewTypeCd().equals(ReviewTypeCodes.COMPREHENSIVE)
		)  {
			lenderId = loanSelectionCaseSummary.getUndrwrtingMtgee5();
		} else {
			throw new RuntimeException("Unhandled review type code: " + reviewTypeRef.getReviewTypeCd());
		}

		if (lenderId == null) {
			return false;
		}

		return lenderIdToSuppression.containsKey(lenderId);
	}

	public boolean previouslySelectedforUnderwritingReview() {
		return existingUnderwritingLoanSelectionCaseNumbers.contains(loanSelectionCaseSummary.getCaseNumber());
	}

	public boolean notPreviouslySelectedforUnderwritingReview() {
		return !previouslySelectedforUnderwritingReview();
	}

	private double getScore(String scoringModelCode) {
		ScoringModelContext scoringModelContext = scoringModelCodeToContext.get(scoringModelCode);
		if (scoringModelContext == null) {
			throw new RuntimeException("Scoring model " + scoringModelCode + " hasn't been populated. This means the selection model dependency code has a bug.");
		}

		String caseNumber = loanSelectionCaseSummary.getCaseNumber();
		Integer index = scoringModelContext.caseNumberToModelScoreIndex.get(caseNumber);
		if (index == null) {
			throw new RuntimeException("There is no score for case number " + caseNumber + " in model " + scoringModelCode);
		}

		return scoringModelContext.modelScores.get(index);
	}

	private double getPercentile(String scoringModelCode) {
		ScoringModelContext scoringModelContext = scoringModelCodeToContext.get(scoringModelCode);
		if (scoringModelContext == null) {
			throw new RuntimeException("Scoring model " + scoringModelCode + " hasn't been populated. This means the selection model dependency code has a bug.");
		}

		String caseNumber = loanSelectionCaseSummary.getCaseNumber();
		Integer index = scoringModelContext.caseNumberToModelScoreIndex.get(caseNumber);
		if (index == null) {
			throw new RuntimeException("There is no score for case number " + caseNumber + " in model " + scoringModelCode);
		}

		return ((double)(index + 1) / (double)scoringModelContext.modelScores.size()) * 100.0;
	}

	private boolean aboveThreshold(String scoringModelCode) {
		double threshold = scoringModelCodeToContext.get(scoringModelCode).scoringModelVersion.getModelScoreThreshold();
		return (getScore() > threshold);
	}

	public double getDefectProbabilityForwardScore() {
		return getScore(ScoringModelCodes.DEFECT_PROBABILITY_FWD);
	}

	public double getDefectProbabilityForwardPercentile() {
		return getPercentile(ScoringModelCodes.DEFECT_PROBABILITY_FWD);
	}

	public boolean defectProbabilityForwardScoreAboveThreshold() {
		return aboveThreshold(ScoringModelCodes.DEFECT_PROBABILITY_FWD);
	}

	public double getDefectProbabilityReverseScore() {
		return getScore(ScoringModelCodes.DEFECT_PROBABILITY_REV);
	}

	public double getDefectProbabilityReversePercentile() {
		return getPercentile(ScoringModelCodes.DEFECT_PROBABILITY_REV);
	}

	public boolean defectProbabilityReverseScoreAboveThreshold() {
		return aboveThreshold(ScoringModelCodes.DEFECT_PROBABILITY_REV);
	}

	public double getLenderMonitoringForwardServicingScore() {
		return getScore(ScoringModelCodes.LENDER_MONITORING_FWD_SERVICING);
	}

	public double getLenderMonitoringForwardServicingPercentile() {
		return getPercentile(ScoringModelCodes.LENDER_MONITORING_FWD_SERVICING);
	}

	public double getLenderMonitoringForwardUnderwritingScore() {
		return getScore(ScoringModelCodes.LENDER_MONITORING_FWD_UNDERWRITING);
	}

	public double getLenderMonitoringForwardUnderwritingPercentile() {
		return getPercentile(ScoringModelCodes.LENDER_MONITORING_FWD_UNDERWRITING);
	}

	public double getLenderMonitoringReverseServicingScore() {
		return getScore(ScoringModelCodes.LENDER_MONITORING_REV_SERVICING);
	}

	public double getLenderMonitoringReverseServicingPercentile() {
		return getPercentile(ScoringModelCodes.LENDER_MONITORING_REV_SERVICING);
	}

	public double getLenderMonitoringReverseUnderwritingScore() {
		return getScore(ScoringModelCodes.LENDER_MONITORING_REV_UNDERWRITING);
	}

	public double getLenderMonitoringReverseUnderwritingPercentile() {
		return getPercentile(ScoringModelCodes.LENDER_MONITORING_REV_UNDERWRITING);
	}

	public boolean defectProbabilityForwardPercentileWithinLenderIncreasedSelectionForwardPercent() {
		String lenderId = null;
		if (reviewTypeRef.getReviewTypeCd().equals(ReviewTypeCodes.SERVICING)) {
			lenderId = loanSelectionCaseSummary.getSrvcrMtgee5A43();
		} else if (
			reviewTypeRef.getReviewTypeCd().equals(ReviewTypeCodes.UNDERWRITING) ||
			reviewTypeRef.getReviewTypeCd().equals(ReviewTypeCodes.COMPREHENSIVE)
		)  {
			lenderId = loanSelectionCaseSummary.getUndrwrtingMtgee5();
		}
		if (lenderId == null) {
			return false;
		}
		LenderIncreasedSelection lenderIncreasedSelection = lenderIdToIncreasedSelection.get(lenderId);
		if (lenderIncreasedSelection == null) {
			return false;
		}
		return getDefectProbabilityForwardPercentile() >= lenderIncreasedSelection.getTargetFwdPct();
	}

	public boolean defectProbabilityReversePercentileWithinLenderIncreasedSelectionHecmPercent() {
		String lenderId = null;
		if (reviewTypeRef.getReviewTypeCd().equals(ReviewTypeCodes.SERVICING)) {
			lenderId = loanSelectionCaseSummary.getSrvcrMtgee5A43();
		} else if (
			reviewTypeRef.getReviewTypeCd().equals(ReviewTypeCodes.UNDERWRITING) ||
			reviewTypeRef.getReviewTypeCd().equals(ReviewTypeCodes.COMPREHENSIVE)
		)  {
			lenderId = loanSelectionCaseSummary.getUndrwrtingMtgee5();
		}
		if (lenderId == null) {
			return false;
		}
		LenderIncreasedSelection lenderIncreasedSelection = lenderIdToIncreasedSelection.get(lenderId);
		if (lenderIncreasedSelection == null) {
			return false;
		}
		return getDefectProbabilityReversePercentile() >= lenderIncreasedSelection.getTargetHecmPct();
	}

	public boolean defectProbabilityForwardPercentileWithinUnderwriterIncreasedSelectionForwardPercent() {
		String underwriterId = loanSelectionCaseSummary.getUnderwriterId();
		if (underwriterId == null) {
			return false;
		}
		UnderwriterIncreasedSelection underwriterIncreasedSelection = underwriterIdToIncreasedSelection.get(underwriterId);
		if (underwriterIncreasedSelection == null) {
			return false;
		}
		return getDefectProbabilityForwardPercentile() >= underwriterIncreasedSelection.getFwdPercent();
	}

	public boolean defectProbabilityReversePercentileWithinUnderwriterIncreasedSelectionHecmPercent() {
		String underwriterId = loanSelectionCaseSummary.getUnderwriterId();
		if (underwriterId == null) {
			return false;
		}
		UnderwriterIncreasedSelection underwriterIncreasedSelection = underwriterIdToIncreasedSelection.get(underwriterId);
		if (underwriterIncreasedSelection == null) {
			return false;
		}
		return getDefectProbabilityReversePercentile() >= underwriterIncreasedSelection.getHecmPercent();
	}

}
