package gov.hud.lrs.common.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.Personnel;
import gov.hud.lrs.common.entity.PersonnelUnavailability;

@Repository
public interface PersonnelUnavailabilityRepository extends CrudRepository<PersonnelUnavailability, String> {
	void deleteByPersonnel(Personnel personnel);
	void deleteByPersonnelUnavailabilityIdNotIn(List<String> personnelUnavailabilityIds);
	void deleteByPersonnelUnavailabilityIdNotInAndPersonnelIn(List<String> personnelUnavailabilityIds, Personnel personnel);
}
