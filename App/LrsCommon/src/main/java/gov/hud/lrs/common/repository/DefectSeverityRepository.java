package gov.hud.lrs.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.Defect;
import gov.hud.lrs.common.entity.DefectSeverity;

@Repository
public interface DefectSeverityRepository extends JpaRepository<DefectSeverity, String> {

	List<DefectSeverity> findByDefectQaModelQaModelId(String qaModelId);

	List<DefectSeverity> findByDefect(Defect defect);

	DefectSeverity findByDefectAndSeverityTierCd(Defect defect, int severityTierCd);

	long deleteByDefect(Defect defect);

}
