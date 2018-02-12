package gov.hud.lrs.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.ReviewLocation;

@Repository
public interface ReviewLocationRepository extends JpaRepository<ReviewLocation, String> {
	ReviewLocation findByLocationName(String loacationName);
	ReviewLocation findByReviewLocationId(String reviewLocationId);
}
