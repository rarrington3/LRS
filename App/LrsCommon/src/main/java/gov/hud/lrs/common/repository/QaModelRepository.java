package gov.hud.lrs.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.QaModel;
import gov.hud.lrs.common.entity.QaModelStatusRef;
import gov.hud.lrs.common.enumeration.QaModelStatusCodes;

@Repository
public interface QaModelRepository extends JpaRepository<QaModel, String> {

	QaModel findTopByOrderByCreatedTsDesc();
	
	QaModel findTopByQaModelStatusRef(QaModelStatusRef ref);
	
	@Query(
		value =
			"SELECT QM.* FROM QA_MODEL QM " +
			"INNER JOIN QA_MODEL_STATUS_REF QMS ON (QMS.QA_MODEL_STATUS_ID = QM.QA_MODEL_STATUS_ID) " +
			"WHERE (QMS.CODE = '" + QaModelStatusCodes.ACTIVE + "') ",
		nativeQuery = true
	)
	QaModel findActive();

}
