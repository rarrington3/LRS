package gov.hud.lrs.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.DictMetadataFieldSubjectArea;
import gov.hud.lrs.common.entity.QaModel;

@Repository
public interface DictMetadataFieldSubjectAreaRepository extends JpaRepository<DictMetadataFieldSubjectArea, String> {

	List<DictMetadataFieldSubjectArea> findByQaModel(QaModel qaModel);

	long deleteByQaModel(QaModel qaModel);
}
