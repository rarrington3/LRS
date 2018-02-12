package gov.hud.lrs.common.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.Defect;
import gov.hud.lrs.common.entity.QatreeOutcome;

@Repository
public interface QatreeOutcomeRepository extends CrudRepository<QatreeOutcome, String> {

	List<QatreeOutcome> findByDefect(Defect defect);
	
	long deleteByDefect(Defect defect);
}
