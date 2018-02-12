package gov.hud.lrs.common.repository;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.FraudTypeRef;

@Repository
public interface FraudTypeRefRepository extends CrudRepository<FraudTypeRef, String> {

	@Cacheable("FraudTypeRef")
	FraudTypeRef findByFraudTypeDescription(String fraudType);

	@Cacheable("FraudTypeRef")
	List<FraudTypeRef> findByFraudTypeIdIn(List<String> fraudTypeIds);

}
