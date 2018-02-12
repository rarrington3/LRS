package gov.hud.lrs.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.hud.lrs.common.entity.Indemnification;
import gov.hud.lrs.common.entity.ReviewLevel;

public interface IndemnificationRepository extends JpaRepository<Indemnification, String> {

	@Query(value = " SELECT COALESCE(MAX(INDM.INDEMNIFICATION_AGREEMENT_NUMBER) + 1, 100001)  FROM INDEMNIFICATION INDM", nativeQuery = true)
	public long findNextIndemnificationAgreementNumber();

	public Indemnification findByReviewLevel(ReviewLevel reviewLevel);

}
