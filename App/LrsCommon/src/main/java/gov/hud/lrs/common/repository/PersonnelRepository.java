package gov.hud.lrs.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.Personnel;
import gov.hud.lrs.common.enumeration.PersonnelStatusCodes;

@Repository
public interface PersonnelRepository extends JpaRepository<Personnel, String> {

	@Query(
		value =
			"SELECT P.* FROM PERSONNEL P " +
			"INNER JOIN PERSONNEL_STATUS_REF PS ON PS.PERSONNEL_STATUS_ID = P.PERSONNEL_STATUS_ID " +
			"WHERE (PS.CODE = '" + PersonnelStatusCodes.ACTIVE + "') ",
		nativeQuery = true
	)
	List<Personnel> findActive();

	@Query(
		value =
			"SELECT P.* FROM PERSONNEL P " +
			"INNER JOIN PERSONNEL_STATUS_REF PS ON PS.PERSONNEL_STATUS_ID = P.PERSONNEL_STATUS_ID " +
			"WHERE (PS.CODE = '" + PersonnelStatusCodes.ACTIVE + "') AND (P.REVIEW_LOCATION_ID = ?1) ",
		nativeQuery = true
	)
	List<Personnel> findActiveByReviewLocationId(String reviewLocationId);

	@Query(
		value =
			"SELECT P.* FROM PERSONNEL P " +
			"INNER JOIN (" +
				"SELECT DISTINCT PSUB.PERSONNEL_ID FROM PERSONNEL PSUB " +
				"INNER JOIN PERSONNEL_STATUS_REF PS ON PS.PERSONNEL_STATUS_ID = PSUB.PERSONNEL_STATUS_ID " +
				"LEFT JOIN PERSONNEL_UNAVAILABILITY PU ON PSUB.PERSONNEL_ID = PU.PERSONNEL_ID " +
				"WHERE (PS.CODE = '" + PersonnelStatusCodes.ACTIVE + "') AND " +
				"((PU.UNAVAIL_START_TS IS NULL) OR " +
				"NOT EXISTS " +
					"(SELECT 1 FROM PERSONNEL_UNAVAILABILITY PUSUB " +
					"WHERE PUSUB.PERSONNEL_ID = PU.PERSONNEL_ID AND " +
					"CAST(PUSUB.UNAVAIL_START_TS AS DATE) <= CAST (CURRENT_TIMESTAMP AS DATE) AND " +
					"CAST(PUSUB.UNAVAIL_END_TS AS DATE) >= CAST(CURRENT_TIMESTAMP AS DATE)))" +
			") B " +
			"ON (P.PERSONNEL_ID = B.PERSONNEL_ID) AND (P.REVIEW_LOCATION_ID = ?1) ",
		nativeQuery = true
	)
	List<Personnel> findActiveAvailableByReviewLocationId(String reviewLocationId);

	@Query(
		value =
			"SELECT P.* FROM PERSONNEL P " +
			"INNER JOIN PERSONNEL_STATUS_REF PS ON PS.PERSONNEL_STATUS_ID = P.PERSONNEL_STATUS_ID " +
			"WHERE (PS.CODE IN ('" + PersonnelStatusCodes.ACTIVE + "', '" + PersonnelStatusCodes.INACTIVE + "')) ",
		nativeQuery = true
	)
	List<Personnel> findActiveInactive();

	@Query(
		value =
			"SELECT P.* FROM PERSONNEL P " +
			"INNER JOIN PERSONNEL_STATUS_REF PS ON PS.PERSONNEL_STATUS_ID = P.PERSONNEL_STATUS_ID " +
			"WHERE (PS.CODE IN ('" + PersonnelStatusCodes.ACTIVE + "', '" + PersonnelStatusCodes.INACTIVE + "')) AND (P.REVIEW_LOCATION_ID = ?1) ",
		nativeQuery = true
	)
	List<Personnel> findActiveInactiveByReviewLocationId(String reviewLocationId);

	@Query(
		value =
			"SELECT P.* FROM PERSONNEL P " +
			"INNER JOIN PERSONNEL_STATUS_REF PS ON PS.PERSONNEL_STATUS_ID = P.PERSONNEL_STATUS_ID " +
			"WHERE (PS.CODE = '" + PersonnelStatusCodes.TERMINATED + "') ",
		nativeQuery = true
	)
	List<Personnel> findTerminated();

	@Query(
		value =
			"SELECT P.* " +
			"FROM PERSONNEL P " +
			"INNER JOIN BATCH_PERSONNEL BP ON (BP.PERSONNEL_ID = P.PERSONNEL_ID) " +
			"INNER JOIN PERSONNEL_STATUS_REF RF ON RF.PERSONNEL_STATUS_ID = P.PERSONNEL_STATUS_ID " +
			"WHERE (BP.BATCH_ID = ?1) AND (RF.CODE = 'A')", //TODO: Replace 'A' with Static String ( https://jira.cynergy.com/jira/browse/HUDLRS-2508 )
		nativeQuery = true
	)
	List<Personnel> getActiveByBatchId(String batchId);

	@Query(
		value =
			"SELECT COUNT(*) " +
			"FROM PERSONNEL P " +
			"INNER JOIN PERSONNEL_STATUS_REF PS ON (P.PERSONNEL_STATUS_ID = PS.PERSONNEL_STATUS_ID) " +
			"WHERE (P.REPORTS_TO_PERSONNEL_ID = :personnelId OR P.VETTING_PERSONNEL_ID = :personnelId) " +
			"AND (PS.CODE = 'A')",
		nativeQuery = true
	)
	int findReportingPersonnelCount(@Param("personnelId")String personnelId);

	Personnel findByLoginCredential(String loginCredential);
	Personnel findByLoginCredentialAndPersonnelStatusRefCode(String loginCredential, String code);
	List<Personnel> findByVettingPersonnel(Personnel vettingPersonnel);
	List<Personnel> findByReportsToPersonnel(Personnel reportsToPersonnel);
	List<Personnel> findByPersonnelIdIn(List<String> personnelIds);
	
}
