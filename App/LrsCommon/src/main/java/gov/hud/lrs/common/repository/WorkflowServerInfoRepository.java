package gov.hud.lrs.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.hud.lrs.common.entity.WorkflowServerInfo;

public interface WorkflowServerInfoRepository extends JpaRepository<WorkflowServerInfo, String> {

	WorkflowServerInfo findByServerName(String serverName);
	
	List<WorkflowServerInfo> findAll();
	
	@Query(
			value =
				"select MAX(SERVER_PRIORITY) from WORKFLOW_SERVER_INFO ",
			nativeQuery = true
		)
		int getPriorityMaxNumber ();
	
	@Query(
			value =
				"SELECT TOP 1 * FROM  WORKFLOW_SERVER_INFO  WHERE SERVER_PRIORITY < ?1 ORDER BY SERVER_PRIORITY DESC",
			nativeQuery = true
		)
	WorkflowServerInfo getNextHighestPriorityServerInfo (Integer priority);
}
