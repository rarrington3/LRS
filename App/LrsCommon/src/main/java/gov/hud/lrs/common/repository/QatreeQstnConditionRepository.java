package gov.hud.lrs.common.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.QatreeQstnCondition;

@Repository
public interface QatreeQstnConditionRepository extends CrudRepository<QatreeQstnCondition, String> {
	List<QatreeQstnCondition> findByQatreeQuestionQuestionId(String questionId);
}
