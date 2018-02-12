package gov.hud.lrs.common.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.Defect;
import gov.hud.lrs.common.entity.DictMetadataFieldDefect;
import gov.hud.lrs.common.entity.DictMetadataFieldDefectId;

@Repository
public interface DictMetadataFieldDefectRepository extends JpaRepository<DictMetadataFieldDefect, DictMetadataFieldDefectId> {

	List<DictMetadataFieldDefect> findByDefect(Defect defect);

	List<DictMetadataFieldDefect> findByIdEntityNameAndIdFieldNameAndDefectIn(String entityName, String fieldName, Set<Defect> defectSet);

	long deleteByDefect(Defect defect);

}
