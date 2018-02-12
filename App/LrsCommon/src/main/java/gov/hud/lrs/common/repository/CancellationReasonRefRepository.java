package gov.hud.lrs.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.hud.lrs.common.entity.CancellationReasonRef;

public interface CancellationReasonRefRepository extends JpaRepository<CancellationReasonRef, String> {

	public CancellationReasonRef findByCode(String code);

}
