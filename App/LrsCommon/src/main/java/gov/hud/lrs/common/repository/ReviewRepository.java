package gov.hud.lrs.common.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.Review;
import gov.hud.lrs.common.entity.ReviewTypeRef;
import gov.hud.lrs.common.enumeration.LenderRequestStatusCodes;
import gov.hud.lrs.common.enumeration.ReviewLevelStatusCodes;
import gov.hud.lrs.common.enumeration.ReviewStatusCodes;
import gov.hud.lrs.common.enumeration.SelectionReasonCodes;

@Repository
public interface ReviewRepository extends ReviewRepositoryCustom, JpaRepository<Review, String> {

	@Query(
		value =
			"SELECT R.* " +
			"FROM REVIEW R " +
			"INNER JOIN REVIEW_STATUS_REF RS ON (RS.REVIEW_STATUS_ID = R.REVIEW_STATUS_ID) " +
			"WHERE EXISTS ( " +
				// review level
				"SELECT * FROM REVIEW_LEVEL RL " +
				// review level status
				"INNER JOIN REVIEW_LEVEL_STATUS_REF RLS ON (RLS.REVIEW_LEVEL_STATUS_ID = RL.REVIEW_LEVEL_STATUS_ID) " +
				// vetting review level (optional)
				"LEFT JOIN REVIEW_LEVEL VRL ON (" +
					"(VRL.VETTING_IND = 'Y') AND " +
					"(VRL.REVIEW_LEVEL_TYPE_ID = RL.REVIEW_LEVEL_TYPE_ID) AND " +
					"(VRL.ITERATION_NUMBER = RL.ITERATION_NUMBER) AND " +
					"(VRL.REVIEW_ID = RL.REVIEW_ID) " +
				") " +
				// vetting review level status (optional)
				"LEFT JOIN REVIEW_LEVEL_STATUS_REF VRLS ON (VRLS.REVIEW_LEVEL_STATUS_ID = VRL.REVIEW_LEVEL_STATUS_ID) " +
				// pending lender response (optional)
				"LEFT JOIN LENDER_REQUEST LR ON (LR.REVIEW_LEVEL_ID = RL.REVIEW_LEVEL_ID) " +
				// pending lender response status (optional)
				"LEFT JOIN LENDER_REQUEST_STATUS_REF LRS ON (LRS.LENDER_REQUEST_STATUS_ID = LR.LENDER_REQUEST_STATUS_ID) " +
				"WHERE " +
					// for this review
					"(RL.REVIEW_ID = R.REVIEW_ID) AND " +
					"(" +
						// either review level is active and assigned to me OR...
						"(RLS.CODE IN (" +
							"'" + ReviewLevelStatusCodes.ASSIGNED + "', " +
							"'" + ReviewLevelStatusCodes.IN_PROGRESS + "', " +
							"'" + ReviewLevelStatusCodes.PENDING_BATCH_REVIEW + "' " +
							") AND " +
							"(RL.REVIEWER_PERSONNEL_ID = ?1) " +
						") OR (" +
							// vetting (complete or exception) AND
							"(VRLS.CODE IN ('" + ReviewLevelStatusCodes.COMPLETED + "', '" + ReviewLevelStatusCodes.EXCEPTION + "')) AND " +
							// needs acknowledgement
							"(VRL.VETTEE_ACKNOWLEDGEMENT_DT IS NULL) AND " +
							// get the record for the vettee but not the vetter
							"(RL.VETTING_IND != 'Y' AND RL.REVIEWER_PERSONNEL_ID = ?1) " +
						") OR (" +
							// active pending lender response
							"(LRS.CODE IN ('" + LenderRequestStatusCodes.IN_PROGRESS + "')) AND " +
							"( " +
								// review level is assigned to me and not a vetted review level
								"(RL.VETTING_IND != 'Y' AND RL.REVIEWER_PERSONNEL_ID = ?1) " +
								"OR " +
								// vetted review level for the vettee
								"(RL.REVIEW_LEVEL_ID = " +
									"(" +
										"SELECT RL.REVIEW_LEVEL_ID " +
										"FROM REVIEW_LEVEL RLRESPVETTER " +
										"WHERE " +
											"(RLRESPVETTER.REVIEW_LEVEL_TYPE_ID = RL.REVIEW_LEVEL_TYPE_ID) AND " +
											"(RLRESPVETTER.ITERATION_NUMBER = RL.ITERATION_NUMBER) AND " +
											"(RLRESPVETTER.REVIEW_ID = RL.REVIEW_ID) AND " + 
											"(RLRESPVETTER.REVIEWER_PERSONNEL_ID = ?1) AND " +
											"(RLRESPVETTER.VETTING_IND != 'Y')" +
									") " +
								") " +
							") " +
						")" +
					")" +
			") ",
		nativeQuery = true
	)
	List<Review> findActiveAndVettingByReviewerPersonnelId(String reviewerPersonnelId);

	@Query(
		value =
			"SELECT R.* " +
			"FROM REVIEW R " +
			"INNER JOIN REVIEW_LEVEL RL ON (RL.REVIEW_ID = R.REVIEW_ID) " +
			"INNER JOIN REVIEW_LEVEL_STATUS_REF RLS ON (RLS.REVIEW_LEVEL_STATUS_ID = RL.REVIEW_LEVEL_STATUS_ID) " +
			"LEFT JOIN LENDER_REQUEST LR ON (LR.REVIEW_LEVEL_ID = RL.REVIEW_LEVEL_ID) " +
			"LEFT JOIN LENDER_REQUEST_STATUS_REF LRS ON (LRS.LENDER_REQUEST_STATUS_ID = LR.LENDER_REQUEST_STATUS_ID) " +
			"WHERE " +
				"(RLS.CODE IN (" +
					"'" + ReviewLevelStatusCodes.ASSIGNED + "', " +
					"'" + ReviewLevelStatusCodes.IN_PROGRESS + "', " +
					"'" + ReviewLevelStatusCodes.PENDING_BATCH_REVIEW + "' " +
				") OR (" +
					"LRS.CODE IN ('" + LenderRequestStatusCodes.IN_PROGRESS + "') " +
				")) " +
				"AND (RL.REVIEW_LOCATION_ID = ?1) ",
		nativeQuery = true
	)
	List<Review> findActiveByReviewLocationId(String reviewLocationId);

	List<Review> findByCaseNumber(String caseNumber);

	List<Review> findByCaseNumberAndReviewTypeRefIn(String caseNumber, List<ReviewTypeRef> reviewTypeRef);
	
	List<Review> findByCaseNumberAndReviewStatusRefCodeAndLoanSelectionReviewTypeRef(String caseNumber, String reviewStatusRefCode, ReviewTypeRef reviewTypeRef);

	List<Review> findByLenderLenderIdAndReviewStatusRefCode(String lenderId, String reviewStatusCode);

	@Query(
		value =
			"SELECT R.* " +
			"FROM REVIEW R " +
			"INNER JOIN REVIEW_STATUS_REF RS ON (RS.REVIEW_STATUS_ID = R.REVIEW_STATUS_ID) " +
			"INNER JOIN REVIEW_LEVEL RL ON " +
				"(RL.REVIEW_ID = R.REVIEW_ID) AND " +
				"(RL.CREATED_TS = (SELECT MAX(CREATED_TS) FROM REVIEW_LEVEL RLSUB WHERE RLSUB.REVIEW_ID = RL.REVIEW_ID)) " +
			"INNER JOIN REVIEW_LEVEL_TYPE_REF RLT ON (RLT.REVIEW_LEVEL_TYPE_ID = RL.REVIEW_LEVEL_TYPE_ID) " +
			"WHERE " +
				"(RS.CODE IN (" +
					"'" + ReviewStatusCodes.AWAITING_ASSIGNMENT + "', " +
					"'" + ReviewStatusCodes.ASSIGNED + "', " +
					"'" + ReviewStatusCodes.UNDER_REVIEW + "' " +
				")) AND " +
				"(R.MTGEE5 = ?1)",
		nativeQuery = true
	)
	List<Review> findLenderSubmittedReviewsPendingAtFHA(String lenderId);

	@Query(
		value =
			"SELECT DISTINCT R.* " +
			"FROM REVIEW R " +
			"INNER JOIN REVIEW_LEVEL RL ON R.REVIEW_ID = RL.REVIEW_ID " +
			"INNER JOIN REVIEW_STATUS_REF RS ON R.REVIEW_STATUS_ID = RS.REVIEW_STATUS_ID " +
			"INNER JOIN PERSONNEL P ON RL.REVIEWER_PERSONNEL_ID = P.PERSONNEL_ID " +
			"WHERE (:startDate <= R.RVW_COMPLTD_DT) AND (R.RVW_COMPLTD_DT <= :endDate) AND " +
			"RL.REVIEWER_PERSONNEL_ID = :reviewerPersonenelID AND RS.CODE = '" + ReviewStatusCodes.COMPLETED + "'",
		nativeQuery = true
	)
	List<Review> findReviewerCompletedReviews(
			@Param("reviewerPersonenelID") String reviewerPersonenelID,
			@Param("startDate") Date startDate,
			@Param("endDate") Date endDate
	);
	
	@Query(
		value =
			"SELECT R.* " +
			"FROM REVIEW R " +
			"INNER JOIN" +
				"(SELECT DISTINCT R.REVIEW_ID FROM REVIEW R " +
				"INNER JOIN REVIEW_LEVEL RL ON R.REVIEW_ID = RL.REVIEW_ID " +
				"INNER JOIN REVIEW_STATUS_REF RS ON R.REVIEW_STATUS_ID = RS.REVIEW_STATUS_ID " +
				"INNER JOIN PERSONNEL P ON RL.REVIEWER_PERSONNEL_ID = P.PERSONNEL_ID " +
				"WHERE (RL.CREATED_TS = (SELECT MAX(RLSUB.CREATED_TS) FROM REVIEW_LEVEL RLSUB WHERE RLSUB.REVIEW_ID = R.REVIEW_ID)) AND " +
				      "(:startDate <= R.RVW_COMPLTD_DT) AND (R.RVW_COMPLTD_DT <= :endDate) AND " +
				      "RL.REVIEW_LOCATION_ID = :reviewLocationId AND RS.CODE = '" + ReviewStatusCodes.COMPLETED + "' ) A " +
			"ON (R.REVIEW_ID = A.REVIEW_ID)",
		nativeQuery = true
	)
	List<Review> getReviewMyCompletedLocationByLocationId(
			@Param("reviewLocationId") String reviewLocationId,
			@Param("startDate") Date startDate,
			@Param("endDate") Date endDate
	);

	@Query(value = "SELECT R.* FROM REVIEW R INNER JOIN LOAN_SELECTION_CASE_SUMMARY LSCS ON (LSCS.SELECTION_ID = R.SELECTION_ID) WHERE (LSCS.SELECTION_REQUEST_ID = ?1)", nativeQuery = true)
	List<Review> findBySelectionRequestId(String selectionRequestId);

	@Query(
		value =
			"SELECT R.QA_MODEL_ID, COUNT(DISTINCT R.REVIEW_ID) " +
			"FROM REVIEW R " +
			"INNER JOIN REVIEW_STATUS_REF RS ON (RS.REVIEW_STATUS_ID = R.REVIEW_STATUS_ID) " +
			"WHERE (RS.CODE NOT IN ( " +
				"'" + ReviewStatusCodes.COMPLETED + "', " +
				"'" + ReviewStatusCodes.EXCEPTION + "', " +
				"'" + ReviewStatusCodes.CANCELLED + "' " +
			")) " +
			"GROUP BY R.QA_MODEL_ID",
		nativeQuery = true
	)
	List<Object[]> findActiveReviewCountsPerQaModel();

	@Query(
		value =
			"SELECT R.QA_MODEL_ID, COUNT(DISTINCT R.REVIEW_ID) " +
			"FROM REVIEW R " +
			"INNER JOIN REVIEW_STATUS_REF RS ON (RS.REVIEW_STATUS_ID = R.REVIEW_STATUS_ID) " +
			"WHERE (RS.CODE IN ( " +
				"'" + ReviewStatusCodes.COMPLETED + "', " +
				"'" + ReviewStatusCodes.EXCEPTION + "', " +
				"'" + ReviewStatusCodes.CANCELLED + "' " +
			")) " +
			"GROUP BY R.QA_MODEL_ID",
		nativeQuery = true
	)
	List<Object[]> findCompletedReviewCountsPerQaModel();

	@Query(
		value =
			"SELECT COUNT(DISTINCT R.REVIEW_ID) " +
			"FROM REVIEW R " +
			"INNER JOIN REVIEW_STATUS_REF RS ON (RS.REVIEW_STATUS_ID = R.REVIEW_STATUS_ID) " +
			"WHERE " +
				"(RS.CODE NOT IN ( " +
					"'" + ReviewStatusCodes.COMPLETED + "', " +
					"'" + ReviewStatusCodes.EXCEPTION + "', " +
					"'" + ReviewStatusCodes.CANCELLED + "' " +
				")) AND " +
				"(R.QA_MODEL_ID = ?1) ",
		nativeQuery = true
	)
	Integer findActiveReviewCountByQaModelId(String qaModelId);

	@Query(
		value =
			"SELECT COUNT(DISTINCT R.REVIEW_ID) " +
			"FROM REVIEW R " +
			"INNER JOIN REVIEW_STATUS_REF RS ON (RS.REVIEW_STATUS_ID = R.REVIEW_STATUS_ID) " +
			"WHERE " +
				"(RS.CODE IN ( " +
					"'" + ReviewStatusCodes.COMPLETED + "', " +
					"'" + ReviewStatusCodes.EXCEPTION + "', " +
					"'" + ReviewStatusCodes.CANCELLED + "' " +
				")) AND " +
				"(R.QA_MODEL_ID = ?1) ",
		nativeQuery = true
	)
	Integer findCompletedReviewCountByQaModelId(String qaModelId);

	@Query(
		value =
			"SELECT R.* " +
			"FROM REVIEW R " +
			"INNER JOIN REVIEW_STATUS_REF RS ON (RS.REVIEW_STATUS_ID = R.REVIEW_STATUS_ID) " +
			"INNER JOIN REVIEW_LEVEL RL ON (RL.REVIEW_ID = R.REVIEW_ID) " +
			"WHERE " +
				"(RL.CREATED_TS = (SELECT MAX(RLSUB.CREATED_TS) FROM REVIEW_LEVEL RLSUB WHERE RLSUB.REVIEW_ID = R.REVIEW_ID)) AND " +
				"(RL.REVIEW_LOCATION_ID = :reviewLocationId) AND " +
				"(RS.CODE IN (:reviewStatusRefCodes)) ",
			nativeQuery = true
		)
		List<Review> findByReviewLocationIdAndReviewStatusRefCodeIn(
			@Param("reviewLocationId") String reviewLocationId,
			@Param("reviewStatusRefCodes") List<String> reviewStatusRefCodes
		);

	@Query(
		value =
			"SELECT DISTINCT CASE_NUMBER " +
			"FROM REVIEW R " +
			"INNER JOIN REVIEW_STATUS_REF RS ON (RS.REVIEW_STATUS_ID = R.REVIEW_STATUS_ID) " +
			"WHERE " +
				"(RS.CODE = " + "'" + ReviewStatusCodes.COMPLETED + "') AND" +
				"(R.CASE_NUMBER IN (?1)) AND" +
				"(R.REVIEW_TYPE_ID = ?2) ",
		nativeQuery = true
	)
	List<String> findReviewedCases(List<String> selectCases, String reviewTypeId);

	@Query(
		value =
			"SELECT TOP 1 R.* FROM REVIEW R " +
			"INNER JOIN REVIEW_TYPE_REF RT ON (RT.REVIEW_TYPE_ID = R.REVIEW_TYPE_ID) " +
			"INNER JOIN REVIEW_STATUS_REF RS ON (RS.REVIEW_STATUS_ID = R.REVIEW_STATUS_ID) " +
			"INNER JOIN SELECTION_REASON SR ON (SR.SELECTION_REASON_ID = R.SELECTION_REASON_ID) " +
			"WHERE " +
				"(R.CASE_NUMBER = ?1) AND " +
				"(RT.REVIEW_TYPE_CD = ?2) AND " +
				"(RS.CODE = '" + ReviewStatusCodes.COMPLETED + "') AND " +
				"(SR.CODE NOT IN (" +
					"'" + SelectionReasonCodes.NATIONAL_QC + "', " +
					"'" + SelectionReasonCodes.REVIEW_LOCATION_QC + "'" +
				")) " +
			"ORDER BY R.CREATED_TS DESC",
		nativeQuery = true
	)
	Review findLatestNonQcByCaseNumberAndReviewTypeCode(String caseNumber, String reviewTypeCode);

	@Query(
		value =
			"SELECT R.* FROM REVIEW R " +
			"INNER JOIN REVIEW_TYPE_REF RT ON (RT.REVIEW_TYPE_ID = R.REVIEW_TYPE_ID) " +
			"INNER JOIN REVIEW_STATUS_REF RS ON (RS.REVIEW_STATUS_ID = R.REVIEW_STATUS_ID) " +
			"INNER JOIN SELECTION_REASON SR ON (SR.SELECTION_REASON_ID = R.SELECTION_REASON_ID) " +
			"LEFT JOIN REVIEW RNEW ON (RNEW.CASE_NUMBER = R.CASE_NUMBER) AND (RNEW.CREATED_TS > R.CREATED_TS) " +
			"WHERE " +
				"(?1 <= R.RVW_COMPLTD_DT) AND (R.RVW_COMPLTD_DT < ?2) AND " +
				"(?1 <= RNEW.RVW_COMPLTD_DT) AND (RNEW.RVW_COMPLTD_DT < ?2) AND " +
				"(RNEW) AND " +
				"(RS.CODE = '" + ReviewStatusCodes.COMPLETED + "') AND " +
				"(SR.CODE NOT IN (" +
					"'" + SelectionReasonCodes.NATIONAL_QC + "', " +
					"'" + SelectionReasonCodes.REVIEW_LOCATION_QC + "'" +
				"))",
		nativeQuery = true
	)
	List<Review> findCompletedNonQcByCompletedDtBetween(Date startDate, Date endDate);

}
