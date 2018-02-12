package gov.hud.lrs.common.repository;

import org.springframework.data.repository.CrudRepository;

import gov.hud.lrs.common.entity.Job;
import gov.hud.lrs.common.entity.UniverseSelectionRequest;

public interface UniverseSelectionRequestRepository extends CrudRepository<UniverseSelectionRequest, String> {

	UniverseSelectionRequest findByJob(Job job);

}
