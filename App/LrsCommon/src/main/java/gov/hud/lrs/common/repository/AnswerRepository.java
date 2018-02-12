package gov.hud.lrs.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import gov.hud.lrs.common.entity.Answer;

public interface AnswerRepository extends JpaRepository<Answer, String> {

	List<Answer> findByReviewLevelReviewLevelIdAndQatreeQuestionQatreeQatreeId(String reviewLevelId, String qatreeId);

	List<Answer> findByReviewLevelReviewLevelId(String reviewLevelId);

}
