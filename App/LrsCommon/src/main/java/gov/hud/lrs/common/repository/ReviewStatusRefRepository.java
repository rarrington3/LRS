package gov.hud.lrs.common.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

import gov.hud.lrs.common.entity.ReviewStatusRef;

public interface ReviewStatusRefRepository extends CrudRepository<ReviewStatusRef, String> {
	
	@Cacheable("ReviewStatusRef")
	public ReviewStatusRef findByCode(String code);
}
