package gov.hud.lrs.common.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.ReviewLevelReassignmentReasonRef;

@Repository
public interface ReviewLevelReassignmentReasonRefRepository extends JpaRepository<ReviewLevelReassignmentReasonRef, String> {

	@Cacheable("ReviewLevelReassignmentReasonRef")
	public ReviewLevelReassignmentReasonRef findByCode(String ReasonCode);

}
