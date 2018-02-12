package gov.hud.lrs.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.DictMetadataFieldModel;
import gov.hud.lrs.common.entity.DictMetadataFieldModelId;
import gov.hud.lrs.common.entity.DictMetadataFieldSubjectArea;
import gov.hud.lrs.common.entity.QaModel;

@Repository
public interface DictMetadataFieldModelRepository extends JpaRepository<DictMetadataFieldModel, DictMetadataFieldModelId> {

	List<DictMetadataFieldModel> findByIdEntityName(String entityName);

	DictMetadataFieldModel findByIdEntityNameAndIdFieldName(String entityName, String fieldName);

	DictMetadataFieldModel findByQaModelAndIdEntityNameAndIdFieldName(QaModel qaModel, String entityName, String fieldName);

	List<DictMetadataFieldModel> findByQaModelAndDictMetadataFieldSubjectArea(QaModel qaModel, DictMetadataFieldSubjectArea subjectArea);

	long deleteByIdQaModelId(String qaModelId);

}
