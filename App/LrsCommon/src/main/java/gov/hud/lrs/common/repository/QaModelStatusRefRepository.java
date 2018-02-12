package gov.hud.lrs.common.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

import gov.hud.lrs.common.entity.QaModelStatusRef;

public interface QaModelStatusRefRepository extends CrudRepository<QaModelStatusRef, String> {

	@Cacheable("QaModelStatusRef")
	QaModelStatusRef findByCode(String code);

}
