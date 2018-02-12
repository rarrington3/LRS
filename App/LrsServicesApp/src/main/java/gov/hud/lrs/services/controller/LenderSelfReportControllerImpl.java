package gov.hud.lrs.services.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import gov.hud.lrs.common.dto.LrsApiV1DTOs.LenderSelfReportDTO;
import gov.hud.lrs.common.entity.LenderSelfReportSelectionRequest;
import gov.hud.lrs.common.entity.LenderSelfReportSelectionRequestDefect;
import gov.hud.lrs.common.entity.LenderSelfReportSelectionRequestFraudParticipantRef;
import gov.hud.lrs.common.entity.LenderSelfReportSelectionRequestFraudTypeRef;
import gov.hud.lrs.common.entity.LoanSelectionCaseSummary;
import gov.hud.lrs.common.entity.SelectionRequest;
import gov.hud.lrs.common.exception.NotFoundException;
import gov.hud.lrs.common.repository.SelectionRequestRepository;
import gov.hud.lrs.services.bizservice.WorkflowClient;

@Controller
public class LenderSelfReportControllerImpl {

	@Autowired private WorkflowClient workflowClient;
	@Autowired private SelectionRequestRepository selectionRequestRepository;

	@SuppressWarnings("rawtypes")
	@Transactional
	public ResponseEntity postLenderSelfReport(LenderSelfReportDTO lenderSelfReportDTO, HttpServletRequest request, HttpServletResponse response) {
		workflowClient.post("/api/v1/selection/lenderSelfReport", lenderSelfReportDTO, null);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	public ResponseEntity<LenderSelfReportDTO> getLenderSelfReportBySelectionRequestId(String selectionRequestId, HttpServletRequest request, HttpServletResponse response) {
		SelectionRequest selectionRequest = selectionRequestRepository.findBySelectionRequestId(selectionRequestId);
		if (selectionRequest == null) {
			throw new NotFoundException("No Selection Request for selectionRequestId " + selectionRequestId);
		}
		LenderSelfReportDTO lenderSelfReportDTO = null;
		if (selectionRequest.getLenderSelfReportSelectionRequest() != null) {
			lenderSelfReportDTO = convertLenderSelfReportSelectionRequestToLenderSelfReportDTO(selectionRequest.getLenderSelfReportSelectionRequest());
		}
		return new ResponseEntity<LenderSelfReportDTO>(lenderSelfReportDTO, HttpStatus.OK);
	}

	private LenderSelfReportDTO convertLenderSelfReportSelectionRequestToLenderSelfReportDTO(LenderSelfReportSelectionRequest lenderSelfReportSelectionRequest) {
		LenderSelfReportDTO lenderSelfReportDTO = new LenderSelfReportDTO();

		// defectAreas
		List<String> defectAreas = new ArrayList<String>();
		for (LenderSelfReportSelectionRequestDefect lenderSelfReportSelectionRequestDefect : lenderSelfReportSelectionRequest.getLenderSelfReportSelectionRequestDefects()) {
			defectAreas.add(lenderSelfReportSelectionRequestDefect.getDefect().getDefectId());
		}
		lenderSelfReportDTO.setDefectAreas(defectAreas);

		// typesOfFraud
		List<String> typesOfFraud = new ArrayList<String>();
		for (LenderSelfReportSelectionRequestFraudTypeRef lenderSelfReportSelectionRequestFraudTypeRef : lenderSelfReportSelectionRequest.getLenderSelfReportSelectionRequestFraudTypeRefs()) {
			typesOfFraud.add(lenderSelfReportSelectionRequestFraudTypeRef.getFraudTypeRef().getFraudTypeId());
		}
		lenderSelfReportDTO.setTypesOfFraud(typesOfFraud);

		// fraudParticipants
		List<String> fraudParticipants = new ArrayList<String>();
		for (LenderSelfReportSelectionRequestFraudParticipantRef lenderSelfReportSelectionRequestFraudParticipantRef : lenderSelfReportSelectionRequest.getLenderSelfReportSelectionRequestFraudParticipantRefs()) {
			fraudParticipants.add(lenderSelfReportSelectionRequestFraudParticipantRef.getFraudParticipantRef().getFraudParticipantId());
		}
		lenderSelfReportDTO.setFraudParticipants(fraudParticipants);

		// Cases
		List<String> cases = new ArrayList<String>();
		for (LoanSelectionCaseSummary loanSelectionCaseSummary : lenderSelfReportSelectionRequest.getSelectionRequest().getLoanSelectionCaseSummaries()) {
			cases.add(loanSelectionCaseSummary.getCaseNumber());
		}
		lenderSelfReportDTO.setCases(cases);

		lenderSelfReportDTO.setReviewType(lenderSelfReportSelectionRequest.getReviewTypeRef().getReviewTypeCd());
		lenderSelfReportDTO.setIsFraudDetected(lenderSelfReportSelectionRequest.getFraudDetectedInd() == 'Y');
		lenderSelfReportDTO.setDescriptionOfFindings(lenderSelfReportSelectionRequest.getFindingsDescription());
		lenderSelfReportDTO.setDescriptionOfCorrectiveActions(lenderSelfReportSelectionRequest.getCorrectiveActionsDescription());
		lenderSelfReportDTO.setIsCoveredUnderSettlement(lenderSelfReportSelectionRequest.getLoanCoveredUnderSettlemtnWithHudInd() == 'Y');

		return lenderSelfReportDTO;
	}
}
