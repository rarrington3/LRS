package gov.hud.lrs.common.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.JobExecution;
import gov.hud.lrs.common.entity.JobExecutionStatusRef;

@Repository
public interface JobExecutionRepository extends JpaRepository<JobExecution, String> {

	@Query(value =
		"SELECT JE.* FROM JOB_EXECUTION JE " +
		"INNER JOIN JOB J ON (J.JOB_ID = JE.JOB_ID) " +
		"INNER JOIN JOB_TYPE_REF JTR ON (JTR.JOB_TYPE_ID = J.JOB_TYPE_ID) " +
		"WHERE " +
			"(JE.STARTED_DT = (SELECT MAX(JESUB.STARTED_DT) FROM JOB_EXECUTION JESUB WHERE (JESUB.JOB_ID = J.JOB_ID))) " +
			"(JTR.CODE = ?1) AND " +
			"(J.TARGET_DT = ?2)",
		nativeQuery = true
	)
	JobExecution findMostRecentByJobJobTypeRefCodeAndJobTargetDt(String jobTypeRefCode, Date jobTargetDt);
	
	@Query(value =
			"SELECT JE.* FROM JOB_EXECUTION JE " +
			"INNER JOIN JOB_EXECUTION_STATUS_REF JESR ON (JE.JOB_EXECUTION_STATUS_ID = JESR.JOB_EXECUTION_STATUS_ID) " +
			"INNER JOIN WORKFLOW_SERVER_INFO WSI ON (JE.PROCESSED_BY_SERVER_ID = WSI.SERVER_ID) " +
			"WHERE " +
				"JESR.CODE = '" + JobExecutionStatusRef.STARTED + "'" + " AND " + 
				"WSI.SERVER_NAME = ?1 ",
			nativeQuery = true
	)
	List<JobExecution> findStartedJobsByWorkflowServerName(String serverName);
	
	@Query(value =
			"SELECT JE.* FROM JOB_EXECUTION JE " +
			"INNER JOIN JOB_EXECUTION_STATUS_REF JESR ON (JE.JOB_EXECUTION_STATUS_ID = JESR.JOB_EXECUTION_STATUS_ID) " +
			"WHERE " +
				"JESR.CODE = '" + JobExecutionStatusRef.STARTED + "'",
			nativeQuery = true
	)
	List<JobExecution> findAllStartedJobExecutions();
}
