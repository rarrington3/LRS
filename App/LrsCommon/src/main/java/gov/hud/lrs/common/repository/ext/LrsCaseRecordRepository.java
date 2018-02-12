package gov.hud.lrs.common.repository.ext;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.hud.lrs.common.entity.ext.LrsCaseRecord;

public interface LrsCaseRecordRepository extends JpaRepository<LrsCaseRecord, String> {

	@Query(value = "SELECT CASE_NUMBER FROM LRS_CASE_RECORD WHERE (ENDRSMNT_DT >= ?1) and (ENDRSMNT_DT <= ?2)", nativeQuery = true)
	List<String> findCaseNumbersByEndrsmntDtBetweenInclusive(Date startEndrsmntDt, Date endEndrsmntDt);

	@Query(value = "SELECT CASE_NUMBER FROM LRS_CASE_RECORD WHERE (UNDRWRTING_MTGEE5 = ?1) AND (ENDRSMNT_DT >= ?2) and (ENDRSMNT_DT <= ?3)", nativeQuery = true)
	List<String> findCaseNumbersByLenderIdAndEndrsmntDtBetweenInclusiveLenderMonitoringRequest(String lenderId, Date startEndrsmntDt, Date endEndrsmntDt);

	LrsCaseRecord findByCaseNumber(String caseNumber);

	List<LrsCaseRecord> findByCaseNumberIn(List<String> caseNumbers);

}
