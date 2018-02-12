package gov.hud.lrs.common.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.QcOutcomeRef;

@Repository
public interface QcOutcomeRefRepository extends JpaRepository<QcOutcomeRef, String>{

	@Cacheable("QcOutcomeRef")
	QcOutcomeRef findByCode(String code);
}
