package gov.hud.lrs.common.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import gov.hud.lrs.common.enumeration.LenderRequestStatusCodes;
import gov.hud.lrs.common.enumeration.ReviewLevelStatusCodes;

@SuppressWarnings("unchecked")
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

	@PersistenceContext(unitName = "lrs")
	private EntityManager entityManager;

	public List<PersonnelIdReviewCount> convertResultListToPersonnelIdReviewCountList(List<Object[]> resultList) {
		List<PersonnelIdReviewCount> personnelIdReviewCounts = new ArrayList<PersonnelIdReviewCount>();
		for (Object[] result: resultList) {
			PersonnelIdReviewCount personnelIdReviewCount = new PersonnelIdReviewCount();
			personnelIdReviewCount.personnelId = (String)result[0];
			personnelIdReviewCount.reviewCount = ((Number)result[1]).intValue();
			personnelIdReviewCounts.add(personnelIdReviewCount);
		}

		return personnelIdReviewCounts;
	}

	@Override
	public List<PersonnelIdReviewCount> findPersonnelIdReviewCounts() {
		List<Object[]> resultList = entityManager.createNativeQuery(
			"SELECT P.PERSONNEL_ID, COUNT(RAGG.REVIEWER_PERSONNEL_ID) " +
			"FROM PERSONNEL P " +
			"LEFT JOIN ( " +
				"SELECT RL.REVIEWER_PERSONNEL_ID " +
				"FROM REVIEW R " +
				"INNER JOIN REVIEW_LEVEL RL ON (RL.REVIEW_ID = R.REVIEW_ID) " +
				"INNER JOIN REVIEW_LEVEL_STATUS_REF RLS ON (RLS.REVIEW_LEVEL_STATUS_ID = RL.REVIEW_LEVEL_STATUS_ID) " +
				"INNER JOIN LOAN_SELECTION LS ON (LS.SELECTION_ID = R.SELECTION_ID) " +
				"INNER JOIN SELECTION_REASON SR ON (LS.SELECTION_REASON_ID = SR.SELECTION_REASON_ID) " +
				"WHERE " +
					"(RLS.CODE NOT IN ( " +
						"'" + ReviewLevelStatusCodes.COMPLETED + "', " +
						"'" + ReviewLevelStatusCodes.CANCELLED + "', " +
						"'" + ReviewLevelStatusCodes.EXCEPTION + "' " +
						") " +
						"OR (EXISTS(SELECT * FROM LENDER_REQUEST LR " +
						  "INNER JOIN LENDER_REQUEST_STATUS_REF LRSR ON (LR.LENDER_REQUEST_STATUS_ID = LRSR.LENDER_REQUEST_STATUS_ID) " +
						  "WHERE (LR.REVIEW_LEVEL_ID = RL.REVIEW_LEVEL_ID AND (LRSR.CODE IN ('" + LenderRequestStatusCodes.IN_PROGRESS + "')))))" +
					") " +
			") RAGG ON (RAGG.REVIEWER_PERSONNEL_ID = P.PERSONNEL_ID) " +
			"GROUP BY P.PERSONNEL_ID "
		).getResultList();

		return convertResultListToPersonnelIdReviewCountList(resultList);
	}

	@Override
	public List<PersonnelIdReviewCount> findPersonnelIdReviewCountsByReviewLocationId(String reviewLocationId) {
		List<Object[]> resultList = entityManager.createNativeQuery(
			"SELECT P.PERSONNEL_ID, COUNT(RAGG.REVIEWER_PERSONNEL_ID) " +
			"FROM PERSONNEL P " +
			"LEFT JOIN ( " +
				"SELECT RL.REVIEWER_PERSONNEL_ID " +
				"FROM REVIEW R " +
				"INNER JOIN REVIEW_LEVEL RL ON (RL.REVIEW_ID = R.REVIEW_ID) " +
				"INNER JOIN REVIEW_LEVEL_STATUS_REF RLS ON (RLS.REVIEW_LEVEL_STATUS_ID = RL.REVIEW_LEVEL_STATUS_ID) " +
				"INNER JOIN LOAN_SELECTION LS ON (LS.SELECTION_ID = R.SELECTION_ID) " +
				"INNER JOIN SELECTION_REASON SR ON (LS.SELECTION_REASON_ID = SR.SELECTION_REASON_ID) " +
				"WHERE " +
					"(RLS.CODE NOT IN ( " +
						"'" + ReviewLevelStatusCodes.COMPLETED + "', " +
						"'" + ReviewLevelStatusCodes.CANCELLED + "', " +
						"'" + ReviewLevelStatusCodes.EXCEPTION + "' " +
						") " +
						"OR (EXISTS(SELECT * FROM LENDER_REQUEST LR " +
						  "INNER JOIN LENDER_REQUEST_STATUS_REF LRSR ON (LR.LENDER_REQUEST_STATUS_ID = LRSR.LENDER_REQUEST_STATUS_ID) " +
						  "WHERE (LR.REVIEW_LEVEL_ID = RL.REVIEW_LEVEL_ID AND (LRSR.CODE IN ('" + LenderRequestStatusCodes.IN_PROGRESS + "')))))" +
					") " +
			") RAGG ON (RAGG.REVIEWER_PERSONNEL_ID = P.PERSONNEL_ID) " +
			"WHERE (P.REVIEW_LOCATION_ID = :reviewLocationId) " +
			"GROUP BY P.PERSONNEL_ID "
		)
		.setParameter("reviewLocationId", reviewLocationId)
		.getResultList();

		return convertResultListToPersonnelIdReviewCountList(resultList);
	}

	@Override
	public List<PersonnelIdReviewCount> findPersonnelIdReviewCountsByPersonnelIdIn(List<String> personnelIds) {
		List<Object[]> resultList = new ArrayList<Object[]>();
		for (int start = 0; start < personnelIds.size(); ) {
			int end = Math.min(start + 500, personnelIds.size());
			resultList.addAll(entityManager
				.createNativeQuery(
					"SELECT P.PERSONNEL_ID, COUNT(RAGG.REVIEWER_PERSONNEL_ID) " +
					"FROM PERSONNEL P " +
					"LEFT JOIN ( " +
						"SELECT RL.REVIEWER_PERSONNEL_ID " +
						"FROM REVIEW R " +
						"INNER JOIN REVIEW_LEVEL RL ON (RL.REVIEW_ID = R.REVIEW_ID) " +
						"INNER JOIN REVIEW_LEVEL_STATUS_REF RLS ON (RLS.REVIEW_LEVEL_STATUS_ID = RL.REVIEW_LEVEL_STATUS_ID) " +
						"INNER JOIN LOAN_SELECTION LS ON (LS.SELECTION_ID = R.SELECTION_ID) " +
						"INNER JOIN SELECTION_REASON SR ON (LS.SELECTION_REASON_ID = SR.SELECTION_REASON_ID) " +
						"WHERE " +
							"(RLS.CODE NOT IN ( " +
								"'" + ReviewLevelStatusCodes.COMPLETED + "', " +
								"'" + ReviewLevelStatusCodes.CANCELLED + "', " +
								"'" + ReviewLevelStatusCodes.EXCEPTION + "' " +
							    ") " +
								"OR (EXISTS(SELECT * FROM LENDER_REQUEST LR " +
								  "INNER JOIN LENDER_REQUEST_STATUS_REF LRSR ON (LR.LENDER_REQUEST_STATUS_ID = LRSR.LENDER_REQUEST_STATUS_ID) " +
								  "WHERE (LR.REVIEW_LEVEL_ID = RL.REVIEW_LEVEL_ID AND (LRSR.CODE IN ('" + LenderRequestStatusCodes.IN_PROGRESS + "')))))" +
							") " +
					") RAGG ON (RAGG.REVIEWER_PERSONNEL_ID = P.PERSONNEL_ID) " +
					"WHERE (P.PERSONNEL_ID IN (:personnelIds)) " +
					"GROUP BY P.PERSONNEL_ID "
				)
				.setParameter("personnelIds", personnelIds.subList(start, end))
				.getResultList()
			);

			start = end;
		}

		return convertResultListToPersonnelIdReviewCountList(resultList);
	}
}
