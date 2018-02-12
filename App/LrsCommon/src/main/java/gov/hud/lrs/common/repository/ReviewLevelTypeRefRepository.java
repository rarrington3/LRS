package gov.hud.lrs.common.repository;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.ReviewLevelTypeRef;

@Repository
public interface ReviewLevelTypeRefRepository extends JpaRepository<ReviewLevelTypeRef, String> {

	@Cacheable("ReviewLevelTypeRef")
	List<ReviewLevelTypeRef> findAll();

	@Cacheable("ReviewLevelTypeRef")
	ReviewLevelTypeRef findByReviewLevelCd(String reviewLevelCd);

	List<ReviewLevelTypeRef> findByReviewLevelCdNotIn(List<String> reviewLeveTypeCodes);

}
