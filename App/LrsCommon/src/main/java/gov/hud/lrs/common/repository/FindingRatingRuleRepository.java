package gov.hud.lrs.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.Defect;
import gov.hud.lrs.common.entity.FindingRatingRule;

@Repository
public interface FindingRatingRuleRepository extends JpaRepository<FindingRatingRule, String> {

	@Query(
		value =
			"SELECT UNACCEPTABLE_RATING_THRESHOLD FROM FINDING_RATING_RULE WHERE " +
				"(DEFECT_ID = ?1) AND " +
				"(DEFECT_SOURCE_iD = ?2) AND " +
				"(DEFECT_CAUSE_ID =?3) ",
		nativeQuery = true
	)
	public Integer findThresholdByRules(String defectId, String defectSourceId, String defectCauseId);
	
	List<FindingRatingRule> findByDefect(Defect defect);
	
	long deleteByDefect(Defect defect);

}
