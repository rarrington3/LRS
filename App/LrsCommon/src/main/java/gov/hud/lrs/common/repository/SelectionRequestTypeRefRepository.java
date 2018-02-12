package gov.hud.lrs.common.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

import gov.hud.lrs.common.entity.SelectionRequestTypeRef;

public interface SelectionRequestTypeRefRepository extends CrudRepository<SelectionRequestTypeRef, String> {
	
	@Cacheable("SelectionRequestTypeRef")
	public SelectionRequestTypeRef findByCode(String code);
}
