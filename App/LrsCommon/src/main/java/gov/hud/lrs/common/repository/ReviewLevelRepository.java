package gov.hud.lrs.common.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.Review;
import gov.hud.lrs.common.entity.ReviewLevel;
import gov.hud.lrs.common.entity.ReviewLevelTypeRef;
import gov.hud.lrs.common.enumeration.ReviewLevelStatusCodes;
import gov.hud.lrs.common.enumeration.ReviewLevelTypeCodes;

@Repository
public interface ReviewLevelRepository extends JpaRepository<ReviewLevel, String> {

	static final String ACTIVE_STATUS_CODE_SQL =
		"(RLS.CODE IN (" +
			"'" + ReviewLevelStatusCodes.ASSIGNED + "', " +
			"'" + ReviewLevelStatusCodes.IN_PROGRESS + "', " +
			"'" + ReviewLevelStatusCodes.PENDING_BATCH_REVIEW + "'" +
		"))";
	
	static final String ACTIVE_OR_EXCEPTION_STATUS_CODE_SQL =
			"(RLS.CODE IN (" +
				"'" + ReviewLevelStatusCodes.ASSIGNED + "', " +
				"'" + ReviewLevelStatusCodes.IN_PROGRESS + "', " +
				"'" + ReviewLevelStatusCodes.PENDING_BATCH_REVIEW + "'," +
				"'" + ReviewLevelStatusCodes.EXCEPTION + "'" +
			"))";

	@Query(
		value =
			"SELECT RL.* FROM REVIEW_LEVEL RL " +
			"INNER JOIN REVIEW_LEVEL_STATUS_REF RLS ON (RLS.REVIEW_LEVEL_STATUS_ID = RL.REVIEW_LEVEL_STATUS_ID) " +
			"WHERE (RL.REVIEW_ID = ?1) AND " + ACTIVE_STATUS_CODE_SQL,
		nativeQuery = true
	)
	ReviewLevel findActiveByReviewId(String reviewId);

	@Query(
		value =
			"SELECT RL.* FROM REVIEW_LEVEL RL " +
			"INNER JOIN REVIEW_LEVEL_STATUS_REF RLS ON (RLS.REVIEW_LEVEL_STATUS_ID = RL.REVIEW_LEVEL_STATUS_ID) " +
			"WHERE (RL.REVIEWER_PERSONNEL_ID = ?1) AND " + ACTIVE_STATUS_CODE_SQL,
		nativeQuery = true
	)
	List<ReviewLevel> findActiveByPersonnelId(String personnelId);
	@Query(
		value =
			"SELECT RL.* FROM REVIEW_LEVEL RL WHERE " +
				"(RL.CREATED_TS = (SELECT MAX(RLSUB.CREATED_TS) FROM REVIEW_LEVEL RLSUB WHERE (RLSUB.REVIEW_ID = RL.REVIEW_ID))) AND " +
				"(RL.REVIEW_ID = ?1) ",
		nativeQuery = true
	)
	ReviewLevel findCurrentByReviewId(String reviewId);

	@Query(
		value =
			"SELECT RL.* FROM REVIEW_LEVEL RL WHERE (RL.CREATED_TS = (" +
				"SELECT MAX(RLSUB.CREATED_TS) " +
				"FROM REVIEW_LEVEL RLSUB " +
					"INNER JOIN REVIEW_LEVEL_TYPE_REF RLTYPE ON RLSUB.REVIEW_LEVEL_TYPE_ID = RLTYPE.REVIEW_LEVEL_TYPE_ID WHERE " +
					"(RLSUB.REVIEW_ID = RL.REVIEW_ID) AND " +
					"(RLTYPE.REVIEW_LEVEL_CD <> '" + ReviewLevelTypeCodes.INDEMNIFICATION + "') AND " +
					"(RLSUB.CREATED_TS < ?1)" +
			")) " +
			"AND RL.REVIEW_ID = ?2",
		nativeQuery = true
	)
	ReviewLevel findLastNonIndemCreatedBefore(Date date, String reviewId);

	ReviewLevel findByReviewReviewIdAndReviewLevelStatusRefCode(String reviewId, String reviewLevelStatusCode);

	@Query(
		value =
			"SELECT TOP(1) UPDATED_BY_LIST.UPDATED_BY, UPDATED_BY_LIST.UPDATED_TS FROM ( " +
			"SELECT UPDATED_BY, UPDATED_TS FROM REVIEW WHERE REVIEW_ID = :reviewId AND UPDATED_BY LIKE 'M%' " +
			"UNION " +
			"SELECT UPDATED_BY, UPDATED_TS FROM REVIEW_LEVEL WHERE REVIEW_LEVEL_ID = :reviewLevelId AND UPDATED_BY LIKE 'M%' " +
			"UNION " +
			"SELECT UPDATED_BY, UPDATED_TS FROM RVW_LVL_CASE_SUMMARY WHERE REVIEW_LEVEL_ID = :reviewLevelId AND UPDATED_BY LIKE 'M%' " +
			"UNION " +
			"SELECT UPDATED_BY, UPDATED_TS FROM RVW_LVL_FINDING WHERE REVIEW_LEVEL_ID = :reviewLevelId AND UPDATED_BY LIKE 'M%' " +
			"UNION " +
			"SELECT FD.UPDATED_BY, FD.UPDATED_TS FROM RVW_LVL_FINDING RVF, FINDING_DOCUMENT FD " +
			"WHERE RVF.FINDING_ID = FD.FINDING_ID AND RVF.REVIEW_LEVEL_ID = :reviewLevelId AND FD.UPDATED_BY LIKE 'M%' " +
			"UNION " +
			"SELECT UPDATED_BY, UPDATED_TS FROM NOTE WHERE REVIEW_LEVEL_ID = :reviewLevelId AND UPDATED_BY LIKE 'M%' " +
			"UNION " +
			"SELECT UPDATED_BY, UPDATED_TS FROM DOCUMENT WHERE REVIEW_LEVEL_ID = :reviewLevelId AND UPDATED_BY LIKE 'M%' " +
			") UPDATED_BY_LIST ORDER BY UPDATED_TS DESC ",
			nativeQuery = true
		)
	List<Object[]> findLastActionByLender(@Param("reviewId")String reviewId,  @Param("reviewLevelId")String reviewLevelId);

	List<ReviewLevel> findByReviewReviewIdAndReviewLevelStatusRefCodeIn(String reviewId, List<String> reviewLevelStatusCodes);
	
	ReviewLevel findTopByReviewBatchBatchIdAndReviewLevelStatusRefCodeNotInOrderByReviewLevelTypeRefOrdinal(String batchId, List<String> reviewLevelStatusCodes);
	
	@Query(
			value =
				"SELECT RL.* FROM REVIEW_LEVEL RL " +
				"INNER JOIN REVIEW_LEVEL_STATUS_REF RLS ON (RLS.REVIEW_LEVEL_STATUS_ID = RL.REVIEW_LEVEL_STATUS_ID) " +
				"WHERE (RL.REVIEW_ID = ?1) AND " + ACTIVE_OR_EXCEPTION_STATUS_CODE_SQL,
			nativeQuery = true
		)
	ReviewLevel findActiveOrExceptionByReviewId(String reviewId);

	ReviewLevel findTopByReviewAndReviewLevelTypeRefOrderByIterationNumberDesc(Review review, ReviewLevelTypeRef reviewLevelType);
	
}