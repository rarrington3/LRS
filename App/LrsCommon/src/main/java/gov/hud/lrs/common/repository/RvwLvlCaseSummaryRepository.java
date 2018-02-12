package gov.hud.lrs.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.RvwLvlCaseSummary;

@Repository
public interface RvwLvlCaseSummaryRepository extends JpaRepository<RvwLvlCaseSummary, String>, RvwLvlCaseSummaryRepositoryCustom {
	RvwLvlCaseSummary findByReviewLevelId(String reviewLevelId);
}
