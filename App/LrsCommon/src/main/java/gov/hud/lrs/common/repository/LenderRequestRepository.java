package gov.hud.lrs.common.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.LenderRequest;
import gov.hud.lrs.common.entity.ReviewLevel;
import gov.hud.lrs.common.enumeration.LenderRequestStatusCodes;

@Repository
public interface LenderRequestRepository extends JpaRepository<LenderRequest, String> {

	LenderRequest findByReviewLevelReviewLevelId(String reviewLevelId);

	LenderRequest findByReviewLevel(ReviewLevel reviewLevel);

	@Query(
		value =
			"SELECT LR.* FROM LENDER_REQUEST LR " +
			"INNER JOIN LENDER_REQUEST_STATUS_REF LRS ON (LRS.LENDER_REQUEST_STATUS_ID = LR.LENDER_REQUEST_STATUS_ID) " +
			"INNER JOIN REVIEW_LEVEL RL ON (RL.REVIEW_LEVEL_ID = LR.REVIEW_LEVEL_ID) " +
			"WHERE " +
				"(LRS.CODE = '" + LenderRequestStatusCodes.IN_PROGRESS + "') AND " +
				"(RL.REVIEW_ID = ?1) ",
		nativeQuery = true
	)
	LenderRequest findActiveByReviewId(String reviewId);

	@Query(
		value =
			"SELECT LR.* FROM LENDER_REQUEST LR " +
			"INNER JOIN LENDER_REQUEST_STATUS_REF LRS ON (LRS.LENDER_REQUEST_STATUS_ID = LR.LENDER_REQUEST_STATUS_ID) " +
			"WHERE " +
				"(LRS.CODE = '" + LenderRequestStatusCodes.IN_PROGRESS + "') AND " +
				"(LR.REVIEW_LEVEL_ID = ?1) ",
		nativeQuery = true
	)
	LenderRequest findActiveByReviewLevelId(String reviewLevelId);

	@Query(
		value =
			"SELECT LR.* FROM LENDER_REQUEST LR " +
			"INNER JOIN LENDER_REQUEST_STATUS_REF LRS ON (LRS.LENDER_REQUEST_STATUS_ID = LR.LENDER_REQUEST_STATUS_ID) " +
			"INNER JOIN REVIEW_LEVEL RL ON (RL.REVIEW_LEVEL_ID = LR.REVIEW_LEVEL_ID) " +
			"INNER JOIN RVW_LVL_FINDING F ON (F.REVIEW_LEVEL_ID = RL.REVIEW_LEVEL_ID) " +
			"WHERE " +
				"(LRS.CODE = '" + LenderRequestStatusCodes.IN_PROGRESS + "') AND " +
				"(F.FINDING_ID = ?1) ",
		nativeQuery = true
	)
	LenderRequest findActiveByFindingId(String findingId);

	List<LenderRequest> findByDueDateLessThanAndLenderRequestStatusRefCode(Date date, String code);

	LenderRequest findByReviewLevelReviewReviewIdAndLenderRequestStatusRefCode(String reviewId, String lenderRequestStatusCode);

	List<LenderRequest> findByReviewLevelReviewReviewIdAndLenderRequestStatusRefCodeIn(String reviewId, List<String> lenderRequestStatusCodes);

}
