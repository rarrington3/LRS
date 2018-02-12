package gov.hud.lrs.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.hud.lrs.common.entity.Defect;
import gov.hud.lrs.common.entity.DefectRemedyType;

public interface DefectRemedyTypeRepository extends JpaRepository<DefectRemedyType, String> {

	List<DefectRemedyType> findByDefectQaModelQaModelId(String qaModelId);

	List<DefectRemedyType> findByDefect(Defect defect);

	DefectRemedyType findByDefectAndCode(Defect defect, String code);

	long deleteByDefect(Defect defect);

}
