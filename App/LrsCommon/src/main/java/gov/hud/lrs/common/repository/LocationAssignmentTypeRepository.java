package gov.hud.lrs.common.repository;

import org.springframework.data.repository.CrudRepository;

import gov.hud.lrs.common.entity.LocationAssignmentType;

public interface LocationAssignmentTypeRepository extends CrudRepository<LocationAssignmentType, String> {
	void deleteByReviewLocationReviewLocationId(String reviewLocationId);
}
