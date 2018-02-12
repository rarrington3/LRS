package gov.hud.lrs.workflow.integration.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.hud.lrs.common.entity.JobStatusRef;
import gov.hud.lrs.common.entity.JobTypeRef;
import gov.hud.lrs.common.entity.LoanSelection;
import gov.hud.lrs.common.enumeration.LoanSelectionStatusCodes;
import gov.hud.lrs.common.repository.LoanSelectionRepository;
import gov.hud.lrs.workflow.integration.MockDataUtility;
import gov.hud.lrs.workflow.service.AggregationService;

@Service
public class AggregationServiceIT {

	@Autowired MockDataUtility mockDataUtility;

	@Autowired AggregationService aggregationService;

	@Autowired LoanSelectionRepository loanSelectionRepository;

	@Transactional
	public void executeAggregatePendingSelections(String caseNumber) {
		aggregationService.aggregatePendingSelections(mockDataUtility.createJob(JobStatusRef.PENDING, JobTypeRef.AGGREGATION, 'N'));
		List<LoanSelection> loanSelections = loanSelectionRepository.findByCaseNumber(caseNumber);
		LoanSelection loanSelection = loanSelections.iterator().next();
		assertThat(loanSelection.getLoanSelectionStatusRef().getCode(), equalTo(LoanSelectionStatusCodes.SELECTED));
	}

}
