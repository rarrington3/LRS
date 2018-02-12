package gov.hud.lrs.common.repository;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import gov.hud.lrs.common.entity.ScoringModelTypeRef;

public interface ScoringModelTypeRefRepository extends JpaRepository<ScoringModelTypeRef, String> {

	@Cacheable("ScoringModelTypeRef")
	public ScoringModelTypeRef findByCode(String code);
	
	public ScoringModelTypeRef findByCodeIn(List<String> codes);

}
