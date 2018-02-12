package gov.hud.lrs.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.hud.lrs.common.entity.Defect;
import gov.hud.lrs.common.entity.Qatree;

public interface QatreeRepository extends JpaRepository<Qatree, String> {
	
	Qatree findByDefect(Defect defect);

	@Query(
		value =
			"SELECT Q.* FROM QATREE Q " +
			"INNER JOIN REVIEW_TYPE_DEFECT RT ON (RT.DEFECT_ID = Q.DEFECT_ID) " +
			"WHERE (Q.QA_MODEL_ID = ?1) AND (RT.REVIEW_TYPE_ID = ?2) ",
		nativeQuery = true
	)
	List<Qatree> findByQaModelIdAndReviewTypeId(String qaModelId, String reviewTypeId);
	
	long deleteByDefect(Defect defect);

}
