package gov.hud.lrs.workflow.integration.service;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockReset;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.hud.lrs.common.dto.LrsApiV1DTOs.ManualSelectionDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.ReviewRequestByLenderDTO;
import gov.hud.lrs.common.entity.LoanSelectionCaseSummary;
import gov.hud.lrs.common.entity.LoanSelectionPending;
import gov.hud.lrs.common.entity.ReviewLocation;
import gov.hud.lrs.common.repository.LoanSelectionPendingRepository;
import gov.hud.lrs.common.repository.ReviewLocationRepository;
import gov.hud.lrs.common.service.CaseUniverseService;
import gov.hud.lrs.workflow.integration.LrsWorkflowConstantsIT;
import gov.hud.lrs.workflow.integration.MockDataUtility;
import gov.hud.lrs.workflow.service.SelectionService;

@Service
public class SelectionServiceIT {

	@MockBean(name = "caseUniverseService", reset = MockReset.BEFORE)
	public CaseUniverseService caseUniverseService;

	@Autowired SelectionService selectionService;
	@Autowired MockDataUtility mockDataUtility;

	@Autowired LoanSelectionPendingRepository loanSelectionPendingRepository;
	@Autowired ReviewLocationRepository reviewLocationRepository;

	@SuppressWarnings("unchecked")
	@Transactional
	public void executeCreateManualSelectionRequest(String caseNumber) {
		List<LoanSelectionCaseSummary> loanSelectionCaseSummaries = new ArrayList<LoanSelectionCaseSummary>();
		LoanSelectionCaseSummary loanSelectionCaseSummary = mockDataUtility.createLoanSelectionCaseSummary(caseNumber, "R");
		loanSelectionCaseSummaries.add(loanSelectionCaseSummary);
		when(caseUniverseService.getLoanSelectionCaseSummaries(any(List.class))).thenReturn(loanSelectionCaseSummaries);

		ManualSelectionDTO manualSelectionDTO = new ManualSelectionDTO();
		List<ReviewRequestByLenderDTO> reviewRequestByLenderDTOs = new ArrayList<ReviewRequestByLenderDTO>();
		ReviewRequestByLenderDTO reviewRequestByLenderDTO = new ReviewRequestByLenderDTO();
		List<String> cases = new ArrayList<String>();
		cases.add(caseNumber);
		reviewRequestByLenderDTO.setCases(cases);
		reviewRequestByLenderDTO.setLenderId(LrsWorkflowConstantsIT.LENDER_ID);
		reviewRequestByLenderDTOs.add(reviewRequestByLenderDTO);
		manualSelectionDTO.setCasesForReviewByLender(reviewRequestByLenderDTOs);
		ReviewLocation reviewLocation = reviewLocationRepository.findByLocationName("SNA-PUD");
		manualSelectionDTO.setReviewLocation(reviewLocation.getReviewLocationId());
		manualSelectionDTO.setReviewType("U");
		manualSelectionDTO.setSelectionReason("FHA_MANUAL");
		manualSelectionDTO.setSelectionSubReason("IAPR");

		selectionService.createManualSelectionRequest(manualSelectionDTO);
		LoanSelectionPending loanSelectionPending = null;
		List<LoanSelectionPending> loanSelectionPendings = loanSelectionPendingRepository.findAll();
		for(LoanSelectionPending lsp : loanSelectionPendings) {
			if(lsp.getCaseNumber().equals(caseNumber)) {
				loanSelectionPending = lsp;
				break;
			}
		}
		assertNotNull(loanSelectionPending);
	}
}
