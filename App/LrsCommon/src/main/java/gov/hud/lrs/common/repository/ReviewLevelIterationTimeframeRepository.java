package gov.hud.lrs.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.ReviewLevelIterationTimeframe;

@Repository
public interface ReviewLevelIterationTimeframeRepository extends JpaRepository<ReviewLevelIterationTimeframe, String> {

	@Query(
		value =
			"SELECT T.* FROM REVIEW_LEVEL_ITERATION_TIMEFRAME T " +
			"INNER JOIN SELECTION_REASON SR ON (SR.CONSOLIDATED_SELECTION_REASON_ID = T.CONSOLIDATED_SELECTION_REASON_ID) " +
			"WHERE " +
				"(SR.SELECTION_REASON_ID = ?1) AND " +
				"(T.REVIEW_LEVEL_TYPE_ID = ?2) ",
		nativeQuery = true
	)
	ReviewLevelIterationTimeframe findBySelectionReasonIdAndReviewLevelTypeId(String selectionReasonId, String reviewLevelTypeId);

	ReviewLevelIterationTimeframe findByConsolidatedSelectionReasonCodeAndReviewLevelTypeRefReviewLevelCd(String consolidatedSelectionReasonCode, String reviewLevelTypeCd);

	List<ReviewLevelIterationTimeframe> findByReviewLevelTypeRefReviewLevelCd(String code);

	List<ReviewLevelIterationTimeframe> findByReviewLevelTypeRefReviewLevelCdNotIn(List<String> reviewLevelTypeCodes);

}