package gov.hud.lrs.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.ScoringModel;
import gov.hud.lrs.common.entity.ScoringModelVersion;
import gov.hud.lrs.common.enumeration.ScoringModelVersionStatusCodes;

@Repository
public interface ScoringModelVersionRepository extends JpaRepository<ScoringModelVersion, String> {

	static final String activeByScoringModelTypeCodeSql =
		"SELECT SMV.* " +
		"FROM SCORING_MODEL_VERSION SMV " +
		"INNER JOIN SCORING_MODEL_VERSION_STATUS_REF SMVS ON (SMVS.SCORING_MODEL_VERSION_STATUS_ID = SMV.SCORING_MODEL_VERSION_STATUS_ID) " +
		"INNER JOIN SCORING_MODEL SM ON (SM.SCORING_MODEL_ID = SMV.SCORING_MODEL_ID) " +
		"INNER JOIN SCORING_MODEL_TYPE_REF SMT ON (SMT.SCORING_MODEL_TYPE_ID = SM.SCORING_MODEL_TYPE_ID) " +
		"WHERE " +
			"(SMVS.CODE = '" + ScoringModelVersionStatusCodes.ACTIVE + "') AND " +
			"(SMT.CODE = ?1) "
	;

	@Query(value = activeByScoringModelTypeCodeSql, nativeQuery = true)
	ScoringModelVersion findSingleActiveByScoringModelTypeCode(String scoringModelTypeCode);

	@Query(value = activeByScoringModelTypeCodeSql, nativeQuery = true)
	List<ScoringModelVersion> findMultipleActiveByScoringModelTypeCode(String scoringModelTypeCode);

	@Query(value =
		"SELECT SMV.* " +
		"FROM SCORING_MODEL_VERSION SMV " +
		"INNER JOIN SCORING_MODEL_VERSION_STATUS_REF SMVS ON (SMVS.SCORING_MODEL_VERSION_STATUS_ID = SMV.SCORING_MODEL_VERSION_STATUS_ID) " +
		"INNER JOIN SCORING_MODEL SM ON (SM.SCORING_MODEL_ID = SMV.SCORING_MODEL_ID) " +
		"INNER JOIN SCORING_MODEL_TYPE_REF SMT ON (SMT.SCORING_MODEL_TYPE_ID = SM.SCORING_MODEL_TYPE_ID) " +
		"INNER JOIN REVIEW_TYPE_REF RT ON (RT.REVIEW_TYPE_ID = SM.REVIEW_TYPE_ID) " +
		"INNER JOIN LOAN_TYPE_REF LT ON (LT.LOAN_TYPE_REF_ID = SM.LOAN_TYPE_REF_ID) " +
		"WHERE " +
			"(SMVS.CODE = '" + ScoringModelVersionStatusCodes.ACTIVE + "') AND " +
			"(SMT.CODE = ?1) AND " +
			"(RT.REVIEW_TYPE_CD = ?2) AND " +
			"(LT.CODE = ?3) ",
		nativeQuery = true
	)
	ScoringModelVersion findActiveByScoringModelTypeCodeAndReviewTypeCodeAndLoanTypeCode(String scoringModelTypeCode, String reviewTypeCode, String loanTypeCode);

	@Query(value = "SELECT MAX(MODEL_VER_NUM) FROM SCORING_MODEL_VERSION WHERE SCORING_MODEL_ID = ?1", nativeQuery = true)
	int getMaxVersion(String scoringModelVersionId);

	List<ScoringModelVersion> findByScoringModelScoringModelTypeRefCode(String scoringModelTypeCode);

	List<ScoringModelVersion> findByScoringModelScoringModelTypeRefCodeIn(List<String> scoringModelTypeCodes);

	@Query(value =
		"SELECT SMV.* " +
		"FROM SCORING_MODEL_VERSION SMV " +
		"INNER JOIN SCORING_MODEL_VERSION_STATUS_REF SMVS ON (SMVS.SCORING_MODEL_VERSION_STATUS_ID = SMV.SCORING_MODEL_VERSION_STATUS_ID) " +
		"WHERE " +
			"(SMVS.CODE = '" + ScoringModelVersionStatusCodes.ACTIVE + "') AND " +
			"(SMV.SCORING_MODEL_ID = ?1) ",
		nativeQuery = true
	)
	ScoringModelVersion findActiveByScoringModelId(String scoringModelId);

	ScoringModelVersion findTopByScoringModelAndModelName(ScoringModel scoringModel, String modelName);
}
