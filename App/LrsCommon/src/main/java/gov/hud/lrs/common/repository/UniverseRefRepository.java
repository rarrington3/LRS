package gov.hud.lrs.common.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

import gov.hud.lrs.common.entity.UniverseRef;

public interface UniverseRefRepository extends CrudRepository<UniverseRef, String> {

	@Cacheable("UniverseRef")
	UniverseRef findByCode(String code);

}
