package gov.hud.lrs.common.repository;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.FraudParticipantRef;

@Repository
public interface FraudParticipantRefRepository extends CrudRepository<FraudParticipantRef, String> {

	@Cacheable("FraudParticipantRef")
	FraudParticipantRef findByFraudParticipantDescription(String fraudParticipant);
	
	@Cacheable("FraudParticipantRef")
	List<FraudParticipantRef> findByFraudParticipantIdIn(List<String> fraudParticipantIds);

}
