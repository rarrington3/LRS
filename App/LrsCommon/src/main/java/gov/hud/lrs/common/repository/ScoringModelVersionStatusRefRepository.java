package gov.hud.lrs.common.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.ScoringModelVersionStatusRef;

@Repository
public interface ScoringModelVersionStatusRefRepository extends JpaRepository<ScoringModelVersionStatusRef, String>{

	@Cacheable("ScoringModelVersionStatusRef")
	ScoringModelVersionStatusRef findByCode(String code);

}
