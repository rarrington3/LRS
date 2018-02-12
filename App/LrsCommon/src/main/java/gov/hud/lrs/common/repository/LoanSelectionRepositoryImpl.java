package gov.hud.lrs.common.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import gov.hud.lrs.common.enumeration.LoanSelectionStatusCodes;

public class LoanSelectionRepositoryImpl implements LoanSelectionRepositoryCustom {

	@PersistenceContext(unitName = "lrs")
	private EntityManager entityManager;

	public List<ReviewLocationIdLoanSelectionCount> convertResultListToIdLoanSelectionCountList(List<Object[]> resultList) {
		List<ReviewLocationIdLoanSelectionCount> reviewLocationIdLoanSelectionCounts = new ArrayList<ReviewLocationIdLoanSelectionCount>();
		for (Object[] result: resultList) {
			ReviewLocationIdLoanSelectionCount reviewLocationIdLoanSelectionCount = new ReviewLocationIdLoanSelectionCount();
			reviewLocationIdLoanSelectionCount.reviewLocationId = (String)result[0];
			reviewLocationIdLoanSelectionCount.loanSelectionCount = ((Number)result[1]).intValue();
			reviewLocationIdLoanSelectionCounts.add(reviewLocationIdLoanSelectionCount);
		}

		return reviewLocationIdLoanSelectionCounts;
	}

	@Override
	public List<ReviewLocationIdLoanSelectionCount> findReviewLocationLoanSelectionCounts(int month) {
		Query query = entityManager.createNativeQuery(
			"SELECT RL.REVIEW_LOCATION_ID, COUNT(LSAGG.REVIEW_LOCATION_ID) AS NUM_LOANS " +
			"FROM REVIEW_LOCATION RL " +
			"LEFT JOIN (" +
				"SELECT LS.REVIEW_LOCATION_ID " +
				"FROM LOAN_SELECTION LS " +
				"INNER JOIN SELECTION_REASON SR ON (LS.SELECTION_REASON_ID = SR.SELECTION_REASON_ID) " +
				"INNER JOIN LOAN_SELECTION_STATUS_REF LSS ON (LSS.LOAN_SELECTION_STATUS_ID = LS.LOAN_SELECTION_STATUS_ID) " +
				"WHERE " +
					"(LSS.CODE NOT IN ( " +
						"'" + LoanSelectionStatusCodes.COMPLETED + "', " +
						"'" + LoanSelectionStatusCodes.SELECTED  + "', " +
						"'" + LoanSelectionStatusCodes.CANCELLED + "', " +
						"'" + LoanSelectionStatusCodes.EXCEPTION + "' " +
					")) AND " +
					"(MONTH(DISTRIBUTION_DT) = :month) AND " +
					"(SR.COUNTS_TWD_MNTHLY_CAPACITY_IND = 'Y') " +
			") LSAGG ON (LSAGG.REVIEW_LOCATION_ID = RL.REVIEW_LOCATION_ID) " +
			"GROUP BY RL.REVIEW_LOCATION_ID"
		);

		query.setParameter("month", month);
		@SuppressWarnings("unchecked")
		List<Object[]> resultList = query.getResultList();

		return convertResultListToIdLoanSelectionCountList(resultList);
	}

	@Override
	public Integer findReviewLocationLoanSelectionCountsByReviewLocationId(String reviewLocationId, int month) {
		Query query = entityManager.createNativeQuery(
			"SELECT COUNT(*) " +
			"FROM LOAN_SELECTION LS " +
			"INNER JOIN SELECTION_REASON SR ON (LS.SELECTION_REASON_ID = SR.SELECTION_REASON_ID) " +
			"INNER JOIN LOAN_SELECTION_STATUS_REF LSS ON (LSS.LOAN_SELECTION_STATUS_ID = LS.LOAN_SELECTION_STATUS_ID) " +
			"WHERE " +
				"(LS.REVIEW_LOCATION_ID = :reviewLocationId) AND " +
				"(LSS.CODE NOT IN ( " +
					"'" + LoanSelectionStatusCodes.COMPLETED + "', " +
					"'" + LoanSelectionStatusCodes.SELECTED  + "', " +
					"'" + LoanSelectionStatusCodes.CANCELLED + "', " +
					"'" + LoanSelectionStatusCodes.EXCEPTION + "' " +
				")) AND" +
				"(MONTH(DISTRIBUTION_DT) = :month) AND " +
				"(SR.COUNTS_TWD_MNTHLY_CAPACITY_IND = 'Y') "
		);

		query.setParameter("reviewLocationId", reviewLocationId);
		query.setParameter("month", month);
		return (Integer) query.getSingleResult();
	}

}
