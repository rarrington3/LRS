package gov.hud.lrs.workflow.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.Multimap;

@SuppressWarnings("serial")
public class DistributionRulesParameters implements Serializable {

	public String reviewLocationId;
	public String preferredReviewLocationId;
	public String qcReviewLocationId;
	public String reviewTypeCode;
	public String productTypeCode;
	public String selectionReasonCode;
	public int capacity;
	public int usedCapacity;
	public int remainingCapacity;
	public double score = 0;
	public Map<String, Double> factorNameToWeight;
	public Map<String, Double> factorNameToScore = new HashMap<String, Double>();;
	public Multimap<String, String> reviewLocationIdToReviewTypeCodes;
	public Multimap<String, String> reviewLocationIdToProductTypeCodes;
	public Multimap<String, String> reviewLocationIdToSelectionReasonCodes;

	public String getReviewLocationId() {
		return reviewLocationId;
	}

	public void setReviewLocationId(String reviewLocationId) {
		this.reviewLocationId = reviewLocationId;
	}

	public String getPreferredReviewLocationId() {
		return preferredReviewLocationId;
	}

	public void setPreferredReviewLocationId(String preferredReviewLocationId) {
		this.preferredReviewLocationId = preferredReviewLocationId;
	}

	public String getQcReviewLocationId() {
		return qcReviewLocationId;
	}

	public void setQcReviewLocationId(String qcReviewLocationId) {
		this.qcReviewLocationId = qcReviewLocationId;
	}

	public String getReviewTypeCode() {
		return reviewTypeCode;
	}

	public void setReviewTypeCode(String reviewTypeCode) {
		this.reviewTypeCode = reviewTypeCode;
	}

	public String getProductTypeCode() {
		return productTypeCode;
	}

	public void setProductTypeCode(String productTypeCode) {
		this.productTypeCode = productTypeCode;
	}

	public String getSelectionReasonCode() {
		return selectionReasonCode;
	}

	public void setSelectionReasonCode(String selectionReasonCode) {
		this.selectionReasonCode = selectionReasonCode;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getUsedCapacity() {
		return usedCapacity;
	}

	public void setUsedCapacity(int usedCapacity) {
		this.usedCapacity = usedCapacity;
	}

	public int getRemainingCapacity() {
		return remainingCapacity;
	}

	public void setRemainingCapacity(int remainingCapacity) {
		this.remainingCapacity = remainingCapacity;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public void addFactorScore(String factorName) {	// score is implied to be one, so just multiplied by weight
		addFactorScore(factorName, 1.0);
	}

	public void addFactorScore(String factorName, double score) {
		Double weight = factorNameToWeight.get(factorName);
		if (weight == null) {
			throw new RuntimeException("No such factor: " + factorName);
		}

		double factorScore = weight * score;
		factorNameToScore.put(factorName, factorScore);

		this.score = this.score + factorScore;
	}

	public Map<String, Double> getFactorNamesAndWeights() {
		return factorNameToWeight;
	}

	public void setFactorNameToWeight(Map<String, Double> factorNamesAndWeights) {
		this.factorNameToWeight = factorNamesAndWeights;
	}

	public Double getFactorNameToWeight(String factorName) {
		return factorNameToWeight.get(factorName);
	}

	public Map<String, Double> getFactorNameToScore() {
		return factorNameToScore;
	}

	public Multimap<String, String> getReviewLocationIdToReviewTypeCodes() {
		return reviewLocationIdToReviewTypeCodes;
	}

	public void setReviewLocationIdToReviewTypeCodes(Multimap<String, String> reviewLocationIdToReviewTypeCodes) {
		this.reviewLocationIdToReviewTypeCodes = reviewLocationIdToReviewTypeCodes;
	}

	public Multimap<String, String> getReviewLocationIdToProductTypeCodes() {
		return reviewLocationIdToProductTypeCodes;
	}

	public void setReviewLocationIdToProductTypeCodes(Multimap<String, String> reviewLocationIdToProductTypeCodes) {
		this.reviewLocationIdToProductTypeCodes = reviewLocationIdToProductTypeCodes;
	}

	public Multimap<String, String> getReviewLocationIdToSelectionReasonCodes() {
		return reviewLocationIdToSelectionReasonCodes;
	}

	public void setReviewLocationIdToSelectionReasonCodes(
			Multimap<String, String> reviewLocationIdToSelectionReasonCodes) {
		this.reviewLocationIdToSelectionReasonCodes = reviewLocationIdToSelectionReasonCodes;
	}

	public boolean reviewLocationContainsReviewType(String reviewLocationId, String reviewTypeCode) {
		return reviewLocationIdToReviewTypeCodes.containsEntry(reviewLocationId, reviewTypeCode);
	}

	public boolean reviewLocationContainsProductType(String reviewLocationId, String productTypeCode) {
		return reviewLocationIdToProductTypeCodes.containsEntry(reviewLocationId, productTypeCode);
	}

	public boolean reviewLocationContainsSelectionReason(String reviewLocationId, String selectionResaonCode) {
		return reviewLocationIdToSelectionReasonCodes.containsEntry(reviewLocationId, selectionResaonCode);
	}

}
