package gov.hud.lrs.common.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.Defect;
import gov.hud.lrs.common.entity.QatreeOutcomeDefectSource;
import gov.hud.lrs.common.entity.QatreeOutcomeDefectSourceId;

@Repository
public interface QatreeOutcomeDefectSourceRepository extends CrudRepository<QatreeOutcomeDefectSource, QatreeOutcomeDefectSourceId> {

	List<QatreeOutcomeDefectSource> findByIdQatreeOutcomeId(String qatreeOutcomeId);
	
	long deleteByDefectSourceDefect(Defect defect);
	
}
