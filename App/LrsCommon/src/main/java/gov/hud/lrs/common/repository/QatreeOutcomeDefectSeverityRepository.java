package gov.hud.lrs.common.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.Defect;
import gov.hud.lrs.common.entity.QatreeOutcomeDefectSeverity;
import gov.hud.lrs.common.entity.QatreeOutcomeDefectSeverityId;

@Repository
public interface QatreeOutcomeDefectSeverityRepository extends CrudRepository<QatreeOutcomeDefectSeverity, QatreeOutcomeDefectSeverityId> {

	List<QatreeOutcomeDefectSeverity> findByIdQatreeOutcomeId(String qatreeOutcomeId);

	long deleteByDefectSeverityDefect(Defect defect);
}
