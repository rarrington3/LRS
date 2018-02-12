package gov.hud.lrs.services.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import gov.hud.lrs.common.dto.LrsApiV1DTOs.LenderMonitoringDTO;
import gov.hud.lrs.services.bizservice.WorkflowClient;

@Controller
public class LenderMonitoringControllerImpl {

	@Autowired private WorkflowClient workflowClient;

	@SuppressWarnings("rawtypes")
	public ResponseEntity postLenderMonitoring(LenderMonitoringDTO lenderMonitoringDTO, HttpServletRequest request, HttpServletResponse response) {
		workflowClient.post("/api/v1/selection/lenderMonitoring", lenderMonitoringDTO, null);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

}