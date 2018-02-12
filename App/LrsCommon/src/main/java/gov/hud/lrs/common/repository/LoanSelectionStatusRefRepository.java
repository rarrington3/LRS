package gov.hud.lrs.common.repository;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

import gov.hud.lrs.common.entity.LoanSelectionStatusRef;

public interface LoanSelectionStatusRefRepository extends CrudRepository<LoanSelectionStatusRef, String> {
	
	@Cacheable("LoanSelectionStatusRef")
	public LoanSelectionStatusRef findByCode(String code);
	
	@Cacheable("LoanSelectionStatusRef")
	List<LoanSelectionStatusRef> findByCodeIsIn(List<String> codes);
}
