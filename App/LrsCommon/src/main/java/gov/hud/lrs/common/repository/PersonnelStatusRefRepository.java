package gov.hud.lrs.common.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

import gov.hud.lrs.common.entity.PersonnelStatusRef;

public interface PersonnelStatusRefRepository extends CrudRepository<PersonnelStatusRef, String> {
	
	@Cacheable("PersonnelStatusRef")
	public PersonnelStatusRef findByCode(String code);
}
