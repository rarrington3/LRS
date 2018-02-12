package gov.hud.lrs.workflow.integration;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import gov.hud.lrs.common.dto.MuleBinderRequestResponseDTO;
import gov.hud.lrs.common.entity.Job;
import gov.hud.lrs.common.entity.LoanSelectionCaseSummary;
import gov.hud.lrs.common.repository.JobStatusRefRepository;
import gov.hud.lrs.common.repository.JobTypeRefRepository;
import gov.hud.lrs.common.security.SecurityService;

@Component
public class MockDataUtility {

	@Autowired SecurityService securityService;

	@Autowired JobStatusRefRepository jobStatusRefRepository;
	@Autowired JobTypeRefRepository jobTypeRefRepository;

	public Job createJob(String jobStatusCode, String jobTypeCode, Character autoSelectionIndicator) {
		Job job = new Job();
		job.setJobId(UUID.randomUUID().toString());
		job.setJobStatusRef(jobStatusRefRepository.findByCode(jobStatusCode));
		job.setJobTypeRef(jobTypeRefRepository.findByCode(jobTypeCode));
		job.setAutoSelectionIndicator(autoSelectionIndicator);
		String userId = securityService.getUserId();
		job.setCreatedBy(userId);
		job.setUpdatedBy(userId);
		Date now = new Date();
		job.setCreatedTs(now);
		job.setUpdatedTs(now);
		return job;
	}

	public ResponseEntity<MuleBinderRequestResponseDTO> createMuleBinderRequestResponseDTO(
		String statusCode,
		String statusMessage,
		String esbErrorCode,
		String esbErrorMessage
	) {
		MuleBinderRequestResponseDTO response = new MuleBinderRequestResponseDTO();
		response.setStatusCode(statusCode);
		response.setStatusMessage(statusMessage);
		MuleBinderRequestResponseDTO.EsbExceptionDTO esbExceptionDTO = new MuleBinderRequestResponseDTO.EsbExceptionDTO();
		esbExceptionDTO.setErrorCode(esbErrorCode);
		esbExceptionDTO.setErrorMessage(esbErrorMessage);
		response.setEsbException(esbExceptionDTO);
		ResponseEntity<MuleBinderRequestResponseDTO> responseEntity = new ResponseEntity<MuleBinderRequestResponseDTO>(response, HttpStatus.OK);
		return responseEntity;
	}

	public LoanSelectionCaseSummary createLoanSelectionCaseSummary(String caseNumber, String productType) {
		LoanSelectionCaseSummary loanSelectionCaseSummary = new LoanSelectionCaseSummary();
		loanSelectionCaseSummary.setCaseNumber(caseNumber);
		loanSelectionCaseSummary.setUndrwrtingMtgee5(LrsWorkflowConstantsIT.LENDER_ID);
		loanSelectionCaseSummary.setProdType(productType);
		return loanSelectionCaseSummary;
	}

}
