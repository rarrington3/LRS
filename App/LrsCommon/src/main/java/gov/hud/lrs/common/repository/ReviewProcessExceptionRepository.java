package gov.hud.lrs.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.Review;
import gov.hud.lrs.common.entity.ReviewLevel;
import gov.hud.lrs.common.entity.ReviewProcessException;

@Repository
public interface ReviewProcessExceptionRepository extends CrudRepository<ReviewProcessException, String> {

	List<ReviewProcessException> findByResolvedInd(char resolvedInd);

	List<ReviewProcessException> findByReviewProcessExceptionTypeRefCodeInAndResolvedInd(List<String> reviewProcessExceptionTypeRefCodes, char ind);

	@Query(
		value =
			"SELECT E.* " +
			"FROM REVIEW_PROCESS_EXCEPTION E " +
			"INNER JOIN REVIEW_PROCESS_EXCEPTION_TYPE_REF ET ON (ET.REVIEW_PROCESS_EXCEPTION_TYPE_ID = E.REVIEW_PROCESS_EXCEPTION_TYPE_ID) " +
			"LEFT JOIN LOAN_SELECTION LS ON (LS.SELECTION_ID = E.SELECTION_ID) " +
			"LEFT JOIN REVIEW_LEVEL RL ON (RL.REVIEW_LEVEL_ID = E.REVIEW_LEVEL_ID) " +
			"LEFT JOIN BATCH B ON (B.BATCH_ID = E.BATCH_ID) " +
			"INNER JOIN REVIEW_LOCATION RLOC ON ( " +
				"(RLOC.REVIEW_LOCATION_ID = LS.REVIEW_LOCATION_ID) OR " +
				"(RLOC.REVIEW_LOCATION_ID = RL.REVIEW_LOCATION_ID) OR " +
				"(RLOC.REVIEW_LOCATION_ID = B.REVIEW_LOCATION_ID) " +
			") " +
			"WHERE " +
				"(RLOC.REVIEW_LOCATION_ID = ?1) AND " +
				"(ET.CODE IN (?2)) AND " +
				"(E.RESOLVED_IND <> 'Y') ",
		nativeQuery = true
	)
	List<ReviewProcessException> findUnresolvedByReviewLocationIdAndReviewProcessExceptionTypeCodeIn(String reviewLocationId, List<String> reviewProcessExceptionTypeCodes);

	ReviewProcessException findByReviewLevelAndReviewProcessExceptionTypeRefCodeAndResolvedInd(ReviewLevel reviewLevel, String reviewProcessExceptionTypeCode, char resolvedInd);

	List<ReviewProcessException> findByReviewLevelReview(Review review);
	
	ReviewProcessException findByLoanSelectionSelectionIdAndResolvedInd(String selectionId, char ind);

}
