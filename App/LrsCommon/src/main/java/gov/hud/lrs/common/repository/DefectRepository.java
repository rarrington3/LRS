package gov.hud.lrs.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.Defect;
import gov.hud.lrs.common.entity.QaModel;

@Repository
public interface DefectRepository extends JpaRepository<Defect, String> {

	List<Defect> findByQaModelQaModelId(String qaModelId);

	@Query(
		value = "SELECT D.* FROM DEFECT D WHERE (D.QA_MODEL_ID = ?1) AND EXISTS (SELECT * FROM REVIEW_TYPE_DEFECT RTD WHERE (RTD.DEFECT_ID = D.DEFECT_ID) AND (RTD.REVIEW_TYPE_ID = ?2))",
		nativeQuery = true
	)
	List<Defect> findByQaModelIdAndReviewTypeId(String qaModelId, String reviewTypeId);

	Defect findByDefectCdAndQaModel(String defectCd, QaModel qaModel);

	Defect findByDefectCd(String defectCd);

	List<Defect> findByDefectCdIn(List<String> defectCds);

	List<Defect> findByDefectIdIn(List<String> defectIds);

	@Query(
		value = 
			"SELECT D.* FROM DEFECT D WHERE " +
				"(D.QA_MODEL_ID = ?1) AND " +
				"(DEFECT_ID NOT IN (SELECT DEFECT_ID FROM QATREE_QUESTION Q INNER JOIN QATREE QT ON Q.QATREE_ID = QT.QATREE_ID WHERE QT.QA_MODEL_ID = ?1))",
		nativeQuery = true
	)
	List<Defect> findNoQuestionDefectByQaModelId(String qaModelId);
}
