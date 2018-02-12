package gov.hud.lrs.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.hud.lrs.common.entity.Review;
import gov.hud.lrs.common.entity.ReviewReferral;
import gov.hud.lrs.common.entity.ReviewReferralTypeRef;

public interface ReviewReferralRepository extends JpaRepository<ReviewReferral, String> {

	List<ReviewReferral> findByReview(Review review);

	ReviewReferral findByReviewAndReviewReferralTypeRef(Review review, ReviewReferralTypeRef reviewReferralTypeRef);
	
	void deleteByReview(Review review);

}
