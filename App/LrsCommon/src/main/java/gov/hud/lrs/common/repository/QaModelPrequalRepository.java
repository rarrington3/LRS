package gov.hud.lrs.common.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.QaModelPrequal;
import gov.hud.lrs.common.entity.QaModelPrequalId;

@Repository
public interface QaModelPrequalRepository extends CrudRepository<QaModelPrequal, QaModelPrequalId> {
	long countByQuestionReferenceQuestionId(String questionId);
	List<QaModelPrequal> findByQuestionReferenceQuestionId(String questionId);
}
