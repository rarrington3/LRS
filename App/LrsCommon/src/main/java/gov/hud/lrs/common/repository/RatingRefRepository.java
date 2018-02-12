package gov.hud.lrs.common.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.RatingRef;

@Repository
public interface RatingRefRepository extends JpaRepository<RatingRef, String> {

	@Cacheable("RatingRef")
	RatingRef findByCode(String code);

	@Query(value =
		"SELECT R.* FROM RATING_REF R " +
		"INNER JOIN ( " +
			"SELECT RSUB.RATING_ID, ROW_NUMBER() OVER (ORDER BY RSUB.RANK_ORDER ASC, RLF.CREATED_TS DESC) AS MIN_RANK_ORDER " +
			"FROM RATING_REF RSUB " +
			"INNER JOIN RVW_LVL_FINDING RLF ON (RLF.RATING_ID = RSUB.RATING_ID) " +
			"WHERE RLF.REVIEW_LEVEL_ID = ?1 " +
		") RMIN ON (RMIN.RATING_ID = R.RATING_ID) " +
		"WHERE MIN_RANK_ORDER = 1 ",
		nativeQuery = true
	)
	public RatingRef findByReviewLevelIdAndMinRankOrder(String reviewLevelId);

}
