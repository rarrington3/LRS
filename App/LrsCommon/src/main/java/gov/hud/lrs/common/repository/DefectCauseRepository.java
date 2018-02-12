package gov.hud.lrs.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.Defect;
import gov.hud.lrs.common.entity.DefectCause;

@Repository
public interface DefectCauseRepository extends JpaRepository<DefectCause, String> {

	List<DefectCause> findByDefectQaModelQaModelId(String qaModelId);

	List<DefectCause> findByDefect(Defect defect);

	DefectCause findByDefectAndDefectCauseCd(Defect defect, String code);

	long deleteByDefect(Defect defect);

}
