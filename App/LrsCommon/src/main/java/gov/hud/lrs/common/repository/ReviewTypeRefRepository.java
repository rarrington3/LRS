package gov.hud.lrs.common.repository;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.ReviewTypeRef;

@Repository
public interface ReviewTypeRefRepository extends JpaRepository<ReviewTypeRef, String> {

	@Override
	@Cacheable("ReviewTypeRef")
	List<ReviewTypeRef> findAll();
	
	@Cacheable("ReviewTypeRef")
	ReviewTypeRef findByReviewTypeCd(String reviewTypeCd);

}
