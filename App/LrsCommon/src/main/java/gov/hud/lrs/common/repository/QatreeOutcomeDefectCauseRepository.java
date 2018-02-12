package gov.hud.lrs.common.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.Defect;
import gov.hud.lrs.common.entity.QatreeOutcomeDefectCause;
import gov.hud.lrs.common.entity.QatreeOutcomeDefectCauseId;

@Repository
public interface QatreeOutcomeDefectCauseRepository extends CrudRepository<QatreeOutcomeDefectCause, QatreeOutcomeDefectCauseId> {

	List<QatreeOutcomeDefectCause> findByIdQatreeOutcomeId(String qatreeOutcomeId);
	
	long deleteByDefectCauseDefect(Defect defect);

}
