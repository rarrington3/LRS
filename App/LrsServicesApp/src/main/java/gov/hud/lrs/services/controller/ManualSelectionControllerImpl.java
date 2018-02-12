package gov.hud.lrs.services.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import gov.hud.lrs.common.dto.LrsApiV1DTOs.ManualSelectionDTO;
import gov.hud.lrs.services.bizservice.WorkflowClient;

@Controller
public class ManualSelectionControllerImpl {

	@Autowired private WorkflowClient workflowClient;

	@SuppressWarnings("rawtypes")
	public ResponseEntity postManualSelection(ManualSelectionDTO manualSelectionDTO, HttpServletRequest request, HttpServletResponse response) {
		workflowClient.post("/api/v1/selection/manual", manualSelectionDTO, null);
		return new ResponseEntity(HttpStatus.OK);
	}

}
