package gov.hud.lrs.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.QatreeQuestion;
import gov.hud.lrs.common.entity.QuestionReference;

@Repository
public interface QatreeQuestionRepository extends JpaRepository<QatreeQuestion, String> {
	
	List<QatreeQuestion> findByQuestionIdIn(List<String> questionIds);
	List<QatreeQuestion> findByQatreeDefectDefectId(String defectId);
	List<QatreeQuestion> findByParentQuestionReference(QuestionReference parentQuestionReference);
	QatreeQuestion findByQatreeDefectDefectIdAndQuestionReferenceString(String defectId, String questionReferenceStr);
	
	@Query(value = "SELECT * FROM QATREE_QUESTION Q WHERE Q.QATREE_ID IN (SELECT QATREE_ID FROM QATREE WHERE DEFECT_ID = ?1)", nativeQuery = true)
	List<QatreeQuestion> findByDefectId(String defectId);

}
