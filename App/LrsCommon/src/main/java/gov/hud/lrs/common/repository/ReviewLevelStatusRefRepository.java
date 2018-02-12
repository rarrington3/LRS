package gov.hud.lrs.common.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

import gov.hud.lrs.common.entity.ReviewLevelStatusRef;

public interface ReviewLevelStatusRefRepository extends CrudRepository<ReviewLevelStatusRef, String> {
	
	@Cacheable("ReviewLevelStatusRef")
	public ReviewLevelStatusRef findByCode(String code);
}
