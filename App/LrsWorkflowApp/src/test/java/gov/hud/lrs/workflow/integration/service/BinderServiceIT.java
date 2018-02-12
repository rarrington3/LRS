package gov.hud.lrs.workflow.integration.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockReset;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.hud.lrs.common.dto.MuleBinderRequestDTO;
import gov.hud.lrs.common.dto.MuleBinderRequestResponseDTO;
import gov.hud.lrs.common.entity.BinderRequest;
import gov.hud.lrs.common.entity.JobStatusRef;
import gov.hud.lrs.common.entity.JobTypeRef;
import gov.hud.lrs.common.entity.LoanSelection;
import gov.hud.lrs.common.entity.LoanSelectionCaseSummary;
import gov.hud.lrs.common.entity.Review;
import gov.hud.lrs.common.entity.ReviewLevel;
import gov.hud.lrs.common.enumeration.BinderRequestSourceCodes;
import gov.hud.lrs.common.enumeration.BinderRequestStatusCodes;
import gov.hud.lrs.common.enumeration.LoanSelectionStatusCodes;
import gov.hud.lrs.common.enumeration.ReviewLevelStatusCodes;
import gov.hud.lrs.common.enumeration.ReviewLevelTypeCodes;
import gov.hud.lrs.common.enumeration.ReviewStatusCodes;
import gov.hud.lrs.common.repository.LoanSelectionRepository;
import gov.hud.lrs.common.repository.ReviewRepository;
import gov.hud.lrs.common.service.CaseUniverseService;
import gov.hud.lrs.common.service.MuleClient;
import gov.hud.lrs.workflow.integration.MockDataUtility;
import gov.hud.lrs.workflow.service.BinderService;

@Service
public class BinderServiceIT {

	@MockBean(name = "muleClient", reset = MockReset.BEFORE)
	public MuleClient muleClient;

	@MockBean(name = "caseUniverseService", reset = MockReset.BEFORE)
	public CaseUniverseService caseUniverseService;

	@Autowired private MockDataUtility mockDataUtility;
	@Autowired private BinderService binderService;

	@Autowired private LoanSelectionRepository loanSelectionRepository;
	@Autowired private ReviewRepository reviewRepository;

	@Transactional
	public void executeRequestBinders(String caseNumber) {
		when(muleClient.post(any(String.class), any(MuleBinderRequestDTO.class), eq(MuleBinderRequestResponseDTO.class), any(Boolean.class)))
			.thenReturn(mockDataUtility.createMuleBinderRequestResponseDTO(
				"0",
				"LRS Update successfully completed, Binder has already been requested.",
				"0",
				"Success")
			);
		binderService.requestBinders(false, mockDataUtility.createJob(JobStatusRef.PENDING, JobTypeRef.BINDER_REQUEST, 'N'));
		List<LoanSelection> loanSelections = loanSelectionRepository.findByCaseNumber(caseNumber);
		LoanSelection loanSelection = loanSelections.iterator().next();
		BinderRequest binderRequest = loanSelection.getBinderRequests().iterator().next();
		assertThat(binderRequest.getBinderRequestStatusRef().getCode(), equalTo(BinderRequestStatusCodes.REQUESTED));
		assertThat(binderRequest.getBinderRequestSourceRef().getCode(), equalTo(BinderRequestSourceCodes.LENDER));
		assertThat(binderRequest.getLoanSelection().getLoanSelectionStatusRef().getCode(), equalTo(LoanSelectionStatusCodes.REQUESTED));

	}

	@Transactional
	public void executeRetryRequestBinders(String caseNumber) {
		when(muleClient.post(any(String.class), any(MuleBinderRequestDTO.class), eq(MuleBinderRequestResponseDTO.class), any(Boolean.class)))
			.thenReturn(mockDataUtility.createMuleBinderRequestResponseDTO(
				"207",
				"(CASE RESTORE NOT PROCESSED DUE TO ERROR(S) CASE RESTORE IS ALREADY IN PROGRESS)",
				"207",
				"(CASE RESTORE NOT PROCESSED DUE TO ERROR(S) CASE RESTORE IS ALREADY IN PROGRESS)")
			);
		binderService.requestBinders(false, mockDataUtility.createJob(JobStatusRef.PENDING, JobTypeRef.BINDER_REQUEST, 'N'));
		List<LoanSelection> loanSelections = loanSelectionRepository.findByCaseNumber(caseNumber);
		LoanSelection loanSelection = loanSelections.iterator().next();
		assertThat(loanSelection.getLoanSelectionStatusRef().getCode(), equalTo(LoanSelectionStatusCodes.DISTRIBUTED));
		assertThat(loanSelection.getBinderRequests().size(), equalTo(0));
	}

	@Transactional
	public void executeReceiveBinder(String caseNumber) {
		LoanSelectionCaseSummary loanSelectionCaseSummary = mockDataUtility.createLoanSelectionCaseSummary(caseNumber, "R");
		when(caseUniverseService.getLoanSelectionCaseSummary(any(String.class))).thenReturn(loanSelectionCaseSummary);

		List<LoanSelection> requestedLoanSelections = loanSelectionRepository.findByCaseNumber(caseNumber);
		LoanSelection requestedLoanSelection = requestedLoanSelections.iterator().next();
		String binderRequestId = requestedLoanSelection.getBinderRequests().iterator().next().getBinderRequestId();
		binderService.receiveBinder(binderRequestId, null);

		List<Review> reviews = reviewRepository.findByCaseNumber(caseNumber);
		Review review = reviews.iterator().next();
		assertThat(review.getReviewStatusRef().getCode(), equalTo(ReviewStatusCodes.ASSIGNED));
		ReviewLevel reviewLevel = review.getReviewLevels().iterator().next();
		assertThat(reviewLevel.getReviewLevelTypeRef().getReviewLevelCd(), equalTo(ReviewLevelTypeCodes.INITIAL));
		assertThat(reviewLevel.getReviewLevelStatusRef().getCode(), equalTo(ReviewLevelStatusCodes.ASSIGNED));
	}

}
