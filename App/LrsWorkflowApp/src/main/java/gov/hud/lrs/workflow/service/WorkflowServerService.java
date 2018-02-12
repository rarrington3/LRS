package gov.hud.lrs.workflow.service;

import java.net.InetAddress;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import gov.hud.lrs.common.entity.JobExecution;
import gov.hud.lrs.common.entity.WorkflowServerInfo;
import gov.hud.lrs.common.repository.JobExecutionRepository;
import gov.hud.lrs.common.repository.WorkflowServerInfoRepository;
import gov.hud.lrs.common.security.SecurityService;
import gov.hud.lrs.common.util.DateUtils;

@Service
public class WorkflowServerService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static int NUMBER_OF_MINUTES_SERVER_UNAVAILABLE = 2;
	
	@Value("${lrs.jobs.workflowServer.accepatableUnavailability}") private int workFlowServerAcceptableUnavailability;
	
	@Autowired private SecurityService securityService;
	@Autowired private WorkflowServerInfoRepository workflowServerInfoRepository;
	@Autowired private JobExecutionRepository jobExecutionRepository;
	
	
	public void updateWorkFlowHeartBeat () {
		InetAddress ip = null;
		try {
			ip = InetAddress.getLocalHost();
		}catch (Throwable t) {
			throw new RuntimeException(t);
		}
		String hostName = ip.getHostName();
		
		List<WorkflowServerInfo> wfServersInfo = workflowServerInfoRepository.findAll();
		wfServersInfo.sort((x, y) -> -new Integer(x.getServerPriority()).compareTo(new Integer(y.getServerPriority())));
		WorkflowServerInfo currentServerInfo = workflowServerInfoRepository.findByServerName(ip.getHostName());
		Date now = new Date();
		if (currentServerInfo == null) {
			int lowestPriority = 0;
			if (wfServersInfo != null && wfServersInfo.size() > 0) {
				lowestPriority = workflowServerInfoRepository.getPriorityMaxNumber ();
			}
			currentServerInfo = new WorkflowServerInfo ();
			currentServerInfo.setServerName(hostName);
			currentServerInfo.setActive('Y');
			currentServerInfo.setServerPriority(lowestPriority + 10);
			currentServerInfo.setCreatedBy(securityService.getUserId());
			currentServerInfo.setHeartbeatTs(now);
			currentServerInfo.setCreatedTs(now);
			currentServerInfo.setUpdatedBy(securityService.getUserId());
			currentServerInfo.setUpdatedTs(now);
		} else {
			currentServerInfo.setHeartbeatTs(now);
			// Check the highest priority servers are up or not. If not switch the priority.
			int currentServerPriority = currentServerInfo.getServerPriority();
			WorkflowServerInfo nextHighestPriorityServerInfo = workflowServerInfoRepository.getNextHighestPriorityServerInfo (currentServerPriority);
			if (nextHighestPriorityServerInfo != null) {
				long minutes = DateUtils.minutesBetween(now, nextHighestPriorityServerInfo.getHeartbeatTs());
				if (minutes > NUMBER_OF_MINUTES_SERVER_UNAVAILABLE) {
					boolean switchServer = true;
					// Check is there any Job Executions running.
					List<JobExecution> currentJobExecutionsList = jobExecutionRepository.findAllStartedJobExecutions ();
					for (JobExecution currentJobExecution : currentJobExecutionsList) {
						Date timeStamp = currentJobExecution.getUpdatedTs();
						if (timeStamp == null) {
							timeStamp = currentJobExecution.getCreatedTs();
						}
						long jobRunningTime = DateUtils.minutesBetween(now, timeStamp);
						if (jobRunningTime < workFlowServerAcceptableUnavailability) {
							switchServer = false;
						}
					}
					if (switchServer) {
						// Switch the priorities
						logger.info("Current server Info : Name " + currentServerInfo.getServerName() + " Priority : " + currentServerInfo.getServerPriority() + " HeartbeatTsS : " + currentServerInfo.getHeartbeatTs());
						logger.info("Remote server Info : Name " + nextHighestPriorityServerInfo.getServerName() + " Priority : " + nextHighestPriorityServerInfo.getServerPriority()+ " HeartbeatTsS : " + nextHighestPriorityServerInfo.getHeartbeatTs());
						logger.info("Switching the server priorities : " + nextHighestPriorityServerInfo.getServerName() + " AND " + currentServerInfo.getServerName());
						currentServerInfo.setServerPriority(nextHighestPriorityServerInfo.getServerPriority());
						currentServerInfo.setUpdatedTs(now);
						nextHighestPriorityServerInfo.setServerPriority(currentServerPriority);
						nextHighestPriorityServerInfo.setUpdatedTs(now);
						workflowServerInfoRepository.save(nextHighestPriorityServerInfo);
					}
				}
			}
		}
		workflowServerInfoRepository.save(currentServerInfo);
	}
	
	public boolean isHigherPriorityServerAvailable () {
		InetAddress ip = null;
		try {
			ip = InetAddress.getLocalHost();
		}catch (Throwable t) {
			throw new RuntimeException(t);
		}
		String hostName = ip.getHostName();
		List<WorkflowServerInfo> wfServersInfo = workflowServerInfoRepository.findAll();
		WorkflowServerInfo currentServerInfo = workflowServerInfoRepository.findByServerName(hostName);

		boolean priorServerAvailable = false;
		for (WorkflowServerInfo wfServerInfo: wfServersInfo) {
			if (!currentServerInfo.getServerName().equals(wfServerInfo.getServerName()) &&
					wfServerInfo.getServerPriority() < currentServerInfo.getServerPriority()) {
				logger.info("High Priority Server : " + wfServerInfo.getServerName() + " with Priority : " + wfServerInfo.getServerPriority() + " is available to run the jobs");
				priorServerAvailable =true;
				break;
			}
		}
		return priorServerAvailable;
	}
	
	public WorkflowServerInfo getCurrentServerInfo () {
		InetAddress ip = null;
		try {
			ip = InetAddress.getLocalHost();
		}catch (Throwable t) {
			throw new RuntimeException(t);
		}
		return workflowServerInfoRepository.findByServerName(ip.getHostName());
	}
}
