package gov.hud.lrs.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.hud.lrs.common.entity.UnderwriterIncreasedSelection;

public interface UnderwriterIncreasedSelectionRepository extends JpaRepository<UnderwriterIncreasedSelection, String> {

	UnderwriterIncreasedSelection findByUnderwriterId(String undrwrtingMtgee5);

}
