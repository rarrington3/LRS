package gov.hud.lrs.common.repository;

import org.springframework.data.repository.CrudRepository;

import gov.hud.lrs.common.entity.SelectionRequest;

public interface SelectionRequestRepository extends CrudRepository<SelectionRequest, String> {
    SelectionRequest findBySelectionRequestId(String selectionRequestId);
}
