package gov.hud.lrs.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.DictMetadataField;
import gov.hud.lrs.common.entity.DictMetadataFieldId;

@Repository
public interface DictMetadataFieldRepository extends JpaRepository<DictMetadataField, DictMetadataFieldId> {

	List<DictMetadataField> findByIdEntityNameOrderByIdFieldName(String entityName);

	DictMetadataField findByIdEntityNameAndIdFieldName(String entityName, String fieldName);

}
