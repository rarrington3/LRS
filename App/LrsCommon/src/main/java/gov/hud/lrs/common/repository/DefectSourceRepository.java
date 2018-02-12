package gov.hud.lrs.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.Defect;
import gov.hud.lrs.common.entity.DefectSource;

@Repository
public interface DefectSourceRepository extends JpaRepository<DefectSource, String> {

	List<DefectSource> findByDefectQaModelQaModelId(String qaModelId);

	List<DefectSource> findByDefect(Defect defect);

	DefectSource findByDefectAndDefectSourceCd(Defect defect, String code);

	long deleteByDefect(Defect defect);

}
