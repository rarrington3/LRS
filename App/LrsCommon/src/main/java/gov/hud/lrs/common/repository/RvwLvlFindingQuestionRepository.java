package gov.hud.lrs.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.QatreeQuestion;
import gov.hud.lrs.common.entity.RvwLvlFinding;
import gov.hud.lrs.common.entity.RvwLvlFindingQuestion;

@Repository
public interface RvwLvlFindingQuestionRepository extends JpaRepository<RvwLvlFindingQuestion, String> {

	void deleteByRvwLvlFinding(RvwLvlFinding rvwLvlFinding);
	void deleteByRvwLvlFindingAndQatreeQuestion(RvwLvlFinding rvwLvlFinding,QatreeQuestion qatreeQuestion);

}
