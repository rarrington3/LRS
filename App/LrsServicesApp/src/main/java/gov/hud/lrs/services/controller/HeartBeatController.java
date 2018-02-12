package gov.hud.lrs.services.controller;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import gov.hud.lrs.common.security.SecurityService;
import gov.hud.lrs.services.bizservice.WorkflowClient;

@Controller
public class HeartBeatController {
	@PersistenceContext private EntityManager entityManager;
	@Autowired private WorkflowClient workflowClient;
	@Autowired private SecurityService securityService;

	private static final String dbPingSQL = "SELECT 1";

	@RequestMapping(value = "/api/v1/unprotected/heartbeat", method = RequestMethod.GET)
	public ResponseEntity<Void> checkLrsService(HttpServletRequest request, HttpServletResponse response) {
		this.checkDB();
		this.checkWorkflow();

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	private void checkDB() {
		try {
			entityManager.createNativeQuery(dbPingSQL).getResultList();
		} catch (Throwable t) {
			throw new RuntimeException("Error accessing the Database " + t);
		}
	}

	private void checkWorkflow() {
		try {
			securityService.setMonitorUser();
			workflowClient.get("/api/v1/unprotected/heartbeat", null);
		} catch (Throwable t) {
			throw new RuntimeException("Error accessing the the Workflow " + t);
		}
	}
}
