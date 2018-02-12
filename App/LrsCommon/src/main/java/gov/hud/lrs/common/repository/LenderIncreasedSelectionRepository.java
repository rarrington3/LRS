package gov.hud.lrs.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.hud.lrs.common.entity.LenderIncreasedSelection;

public interface LenderIncreasedSelectionRepository extends JpaRepository<LenderIncreasedSelection, String>{
	LenderIncreasedSelection findByMtgee5 (String undrwrtingMtgee5);
}
