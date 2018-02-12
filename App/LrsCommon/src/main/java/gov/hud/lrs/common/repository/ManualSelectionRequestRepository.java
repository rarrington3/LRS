package gov.hud.lrs.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.ManualSelectionRequest;

@Repository
public interface ManualSelectionRequestRepository extends JpaRepository<ManualSelectionRequest, String> {
}
