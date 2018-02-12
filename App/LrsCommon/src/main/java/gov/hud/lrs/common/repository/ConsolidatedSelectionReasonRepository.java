package gov.hud.lrs.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.hud.lrs.common.entity.ConsolidatedSelectionReason;

public interface ConsolidatedSelectionReasonRepository extends JpaRepository<ConsolidatedSelectionReason, String> {

	ConsolidatedSelectionReason findByCode(String code);

}
