package gov.hud.lrs.common.repository;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

import gov.hud.lrs.common.entity.LrsGenLookup;
import gov.hud.lrs.common.entity.LrsGenLookupId;

public interface LrsGenLookupRepository extends CrudRepository<LrsGenLookup, LrsGenLookupId> {

	@Cacheable("LrsGenLookup")
	List<LrsGenLookup> findByIdLookupEntityAndIdLookupField(String lookupEntity, String lookupField);
	
	@Cacheable("LrsGenLookup")
	List<LrsGenLookup> findByIdLookupField(String lookupField);
	
	@Cacheable("LrsGenLookup")
	LrsGenLookup findByIdLookupFieldAndIdLookupCode(String lookupField, String lookupCode);
	
	@Cacheable("LrsGenLookup")
	List<LrsGenLookup> findByIdLookupFieldAndIdLookupCodeIn(String lookupField, List<String> lookupCodes);

	@Cacheable("LrsGenLookup")
	List<LrsGenLookup> findByIdLookupEntityAndIdLookupCode(String lookupEntity, String lookupCode);
}
