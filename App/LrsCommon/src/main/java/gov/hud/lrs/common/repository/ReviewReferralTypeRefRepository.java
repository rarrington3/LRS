package gov.hud.lrs.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.hud.lrs.common.entity.ReviewReferralTypeRef;

public interface ReviewReferralTypeRefRepository extends JpaRepository<ReviewReferralTypeRef, String> {

	ReviewReferralTypeRef findByCode(String type);

}
