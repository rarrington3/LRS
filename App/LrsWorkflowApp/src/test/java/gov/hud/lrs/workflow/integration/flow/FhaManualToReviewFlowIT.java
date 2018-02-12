package gov.hud.lrs.workflow.integration.flow;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;

import gov.hud.lrs.common.repository.LoanSelectionCaseSummaryRepository;
import gov.hud.lrs.common.repository.LoanSelectionPendingRepository;
import gov.hud.lrs.common.repository.LoanSelectionRepository;
import gov.hud.lrs.common.repository.ManualSelectionRequestRepository;
import gov.hud.lrs.common.repository.SelectionRequestRepository;
import gov.hud.lrs.workflow.integration.LrsWorkflowAbstractIT;
import gov.hud.lrs.workflow.integration.LrsWorkflowConstantsIT;
import gov.hud.lrs.workflow.integration.MockDataUtility;
import gov.hud.lrs.workflow.integration.service.AggregationServiceIT;
import gov.hud.lrs.workflow.integration.service.BinderServiceIT;
import gov.hud.lrs.workflow.integration.service.DistributionServiceIT;
import gov.hud.lrs.workflow.integration.service.SelectionServiceIT;
import gov.hud.lrs.workflow.service.AggregationService;

public class FhaManualToReviewFlowIT extends LrsWorkflowAbstractIT {

	@Autowired BinderServiceIT binderServiceIT;
	@Autowired SelectionServiceIT selectionServiceIT;
	@Autowired AggregationServiceIT aggregationServiceIT;
	@Autowired DistributionServiceIT distributionServiceIT;

	@Autowired MockDataUtility mockDataUtility;

	@Autowired SelectionRequestRepository selectionRequestRepository;
	@Autowired ManualSelectionRequestRepository manualSelectionRequestRepository;
	@Autowired LoanSelectionPendingRepository loanSelectionPendingRepository;
	@Autowired LoanSelectionRepository loanSelectionRepository;
	@Autowired LoanSelectionCaseSummaryRepository loanSelectionCaseSummaryRepository;

	@Autowired AggregationService aggregationService;

	@Test
	@Commit
	public void testFhaManualToReviewFlow() {
		// create FHA manual request --> aggregation --> distribution --> binder request (request from lender) --> receive binder --> validate that review was created and assigned
		selectionServiceIT.executeCreateManualSelectionRequest(LrsWorkflowConstantsIT.FHA_MANUAL_TO_REVIEW_FLOW_CASE_NUMBER);
		aggregationServiceIT.executeAggregatePendingSelections(LrsWorkflowConstantsIT.FHA_MANUAL_TO_REVIEW_FLOW_CASE_NUMBER);
		distributionServiceIT.executeDistributeLoanSelections(LrsWorkflowConstantsIT.FHA_MANUAL_TO_REVIEW_FLOW_CASE_NUMBER);
		binderServiceIT.executeRequestBinders(LrsWorkflowConstantsIT.FHA_MANUAL_TO_REVIEW_FLOW_CASE_NUMBER);
		binderServiceIT.executeReceiveBinder(LrsWorkflowConstantsIT.FHA_MANUAL_TO_REVIEW_FLOW_CASE_NUMBER);
	}

}
