package gov.hud.lrs.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.RvwLvlFinding;

@Repository
public interface RvwLvlFindingRepository extends JpaRepository<RvwLvlFinding, String> {

	List<RvwLvlFinding> findByReviewLevelReviewLevelId(String reviewLevelId);

}
