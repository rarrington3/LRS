package gov.hud.lrs.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.hud.lrs.common.entity.ReviewReferralSourceRef;

public interface ReviewReferralSourceRefRepository extends JpaRepository<ReviewReferralSourceRef, String> {

	ReviewReferralSourceRef findByCode(String source);

}
