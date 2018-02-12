package gov.hud.lrs.common.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.RvwLvlFindingQuestion;

@Repository
public interface ReviewLevelFindingQuestionRepository extends CrudRepository<RvwLvlFindingQuestion, String> {

}
