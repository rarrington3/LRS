package gov.hud.lrs.common.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

import gov.hud.lrs.common.entity.ScoringFactor;

public interface ScoringFactorRepository extends CrudRepository<ScoringFactor, String> {
	
	@Cacheable("ScoringFactor")
	Iterable<ScoringFactor> findAll();
}
