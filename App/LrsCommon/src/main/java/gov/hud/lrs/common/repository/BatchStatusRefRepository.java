package gov.hud.lrs.common.repository;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

import gov.hud.lrs.common.entity.BatchStatusRef;

public interface BatchStatusRefRepository extends CrudRepository<BatchStatusRef, String> {
	
	@Cacheable("BatchStatusRef")
	public BatchStatusRef findByCode(String code);
	
	@Cacheable("BatchStatusRef")
	public List<BatchStatusRef> findByCodeIn(List<String> code);
}
