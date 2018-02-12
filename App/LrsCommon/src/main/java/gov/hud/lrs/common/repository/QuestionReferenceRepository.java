package gov.hud.lrs.common.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.QuestionReference;

@Repository
public interface QuestionReferenceRepository extends CrudRepository<QuestionReference, String> {
}
