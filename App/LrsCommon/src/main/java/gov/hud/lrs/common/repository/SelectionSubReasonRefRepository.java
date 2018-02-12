package gov.hud.lrs.common.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import gov.hud.lrs.common.entity.SelectionSubReasonRef;

public interface SelectionSubReasonRefRepository extends JpaRepository<SelectionSubReasonRef, String> {

	@Cacheable("SelectionSubReasonRef")
	SelectionSubReasonRef findByCode(String code);

}
