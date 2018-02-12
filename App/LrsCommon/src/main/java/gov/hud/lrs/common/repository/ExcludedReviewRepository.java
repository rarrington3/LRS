package gov.hud.lrs.common.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.hud.lrs.common.entity.ExcludedReview;

public interface ExcludedReviewRepository extends JpaRepository<ExcludedReview, String> {

	@Query(
			value = "SELECT Distinct(ER.CASE_NUMBER) FROM EXCLUDED_REVIEW ER", nativeQuery = true
		)
	Set<String> findAllCaseNumbers ();
}
