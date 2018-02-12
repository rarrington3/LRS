package gov.hud.lrs.workflow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import gov.hud.lrs.common.dto.LrsApiV1DTOs.LenderMonitoringDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.LenderSelfReportDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.ManualSelectionDTO;
import gov.hud.lrs.workflow.service.SelectionService;

@RestController
@RequestMapping("/api/v1/selection")
public class SelectionController {

	@Autowired private SelectionService selectionService;

	@RequestMapping(value = "/manual", method = RequestMethod.POST)
	public void createManualSelectionRequest(@RequestBody ManualSelectionDTO manualSelectionDTO) {
		selectionService.createManualSelectionRequest(manualSelectionDTO);
	}

	@RequestMapping(value = "/lenderMonitoring", method = RequestMethod.POST)
	public void createLenderMonitoringRequest(@RequestBody LenderMonitoringDTO lenderMonitoringDTO) {
		selectionService.createLenderMonitoringSelectionRequest(lenderMonitoringDTO);
	}

	@RequestMapping(value = "/lenderSelfReport", method = RequestMethod.POST)
	public void createLenderSelfReport(@RequestBody LenderSelfReportDTO lenderSelfReportDTO) {
		selectionService.createLenderSelfReportSelectionRequest(lenderSelfReportDTO);
	}

}
