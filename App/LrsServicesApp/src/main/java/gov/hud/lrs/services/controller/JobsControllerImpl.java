package gov.hud.lrs.services.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import gov.hud.lrs.common.dto.LrsApiV1DTOs.DateRangeDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.JobDTO;
import gov.hud.lrs.services.bizservice.WorkflowClient;

@Controller
public class JobsControllerImpl {

	@Autowired private WorkflowClient workflowClient;

	public ResponseEntity<JobDTO[]> getJobs(HttpServletRequest request, HttpServletResponse response) {
		return new ResponseEntity<JobDTO[]>(
			workflowClient.get("/api/v1/jobs", JobDTO[].class).getBody(),
			HttpStatus.OK
		);
	}

	public ResponseEntity<JobDTO[]> getJobPending(HttpServletRequest request, HttpServletResponse response) {
		return new ResponseEntity<JobDTO[]>(
			workflowClient.get("/api/v1/jobs/pending", JobDTO[].class).getBody(),
			HttpStatus.OK
		);
	}

	public ResponseEntity<JobDTO> getJobByJobId(String jobId, HttpServletRequest request, HttpServletResponse response) {
		return new ResponseEntity<JobDTO>(
			workflowClient.get("/api/v1/jobs/" + jobId, JobDTO.class).getBody(),
			HttpStatus.OK
		);
	}

	public ResponseEntity<JobDTO> postJobEndorsementSelection(DateRangeDTO dateRangeDTO, HttpServletRequest request, HttpServletResponse response) {
		return new ResponseEntity<JobDTO>(
			workflowClient.post("/api/v1/jobs/endorsementSelection", dateRangeDTO, JobDTO.class).getBody(),
			HttpStatus.OK
		);
	}

	public ResponseEntity<JobDTO> postJobEarlyClaimSelection(DateRangeDTO dateRangeDTO, HttpServletRequest request, HttpServletResponse response) {
		return new ResponseEntity<JobDTO>(
			workflowClient.post("/api/v1/jobs/earlyClaimSelection", dateRangeDTO, JobDTO.class).getBody(),
			HttpStatus.OK
		);
	}

	public ResponseEntity<JobDTO> postJobEarlyPaymentDefaultSelection(DateRangeDTO dateRangeDTO, HttpServletRequest request, HttpServletResponse response) {
		return new ResponseEntity<JobDTO>(
			workflowClient.post("/api/v1/jobs/earlyPaymentDefaultSelection", dateRangeDTO, JobDTO.class).getBody(),
			HttpStatus.OK
		);
	}

	public ResponseEntity<JobDTO> postJobNationalQcSelection(DateRangeDTO dateRangeDTO, HttpServletRequest request, HttpServletResponse response) {
		return new ResponseEntity<JobDTO>(
			workflowClient.post("/api/v1/jobs/nationalQcSelection", dateRangeDTO, JobDTO.class).getBody(),
			HttpStatus.OK
		);
	}

	public ResponseEntity<JobDTO> postJobAggregation(HttpServletRequest request, HttpServletResponse response) {
		return new ResponseEntity<JobDTO>(
			workflowClient.post("/api/v1/jobs/aggregation", null, JobDTO.class).getBody(),
			HttpStatus.OK
		);
	}

	public ResponseEntity<JobDTO> postJobLenderMonitoringEmail(HttpServletRequest request, HttpServletResponse response) {
		return new ResponseEntity<JobDTO>(
			workflowClient.post("/api/v1/jobs/lenderMonitoringEmail", null, JobDTO.class).getBody(),
			HttpStatus.OK
		);
	}

	public ResponseEntity<JobDTO> postJobDailyCombinedLenderEmail(HttpServletRequest request, HttpServletResponse response) {
		return new ResponseEntity<JobDTO>(
			workflowClient.post("/api/v1/jobs/dailyCombinedLenderEmail", null, JobDTO.class).getBody(),
			HttpStatus.OK
		);
	}

	public ResponseEntity<JobDTO> postJobDistribution(HttpServletRequest request, HttpServletResponse response) {
		return new ResponseEntity<JobDTO>(
			workflowClient.post("/api/v1/jobs/distribution", null, JobDTO.class).getBody(),
			HttpStatus.OK
		);
	}

	public ResponseEntity<JobDTO> postJobBinderRequest(HttpServletRequest request, HttpServletResponse response) {
		return new ResponseEntity<JobDTO>(
			workflowClient.post("/api/v1/jobs/binderRequest", null, JobDTO.class).getBody(),
			HttpStatus.OK
		);
	}

	public ResponseEntity<JobDTO> postJobBinderRequestThrottled(HttpServletRequest request, HttpServletResponse response) {
		return new ResponseEntity<JobDTO>(
		    workflowClient.post("/api/v1/jobs/binderRequest/throttled", null, JobDTO.class).getBody(),
		    HttpStatus.OK
				);
	}

	public ResponseEntity<JobDTO> postJobBinderReceipt(HttpServletRequest request, HttpServletResponse response) {
		return new ResponseEntity<JobDTO>(
			workflowClient.post("/api/v1/jobs/binderReceipt", null, JobDTO.class).getBody(),
			HttpStatus.OK
		);
	}

	// bleh, the codegen strips off the trailing "s" since it thinks it's creating a single object
	public ResponseEntity<JobDTO> postJobLateBinder(HttpServletRequest request, HttpServletResponse response) {
		return new ResponseEntity<JobDTO>(
			workflowClient.post("/api/v1/jobs/lateBinders", null, JobDTO.class).getBody(),
			HttpStatus.OK
		);
	}

	public ResponseEntity<JobDTO> postJobLateLenderRequest(HttpServletRequest request, HttpServletResponse response) {
		return new ResponseEntity<JobDTO>(
			workflowClient.post("/api/v1/jobs/lateLenderRequests", null, JobDTO.class).getBody(),
			HttpStatus.OK
		);
	}

	public ResponseEntity<Void> postJobSendEmail(HttpServletRequest request, HttpServletResponse response) {
		workflowClient.post("/api/v1/jobs/sendEmail", null, Void.class);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	public ResponseEntity<Void> postJobRefreshLender(HttpServletRequest request, HttpServletResponse response) {
		workflowClient.post("/api/v1/jobs/refreshLenders", null, Void.class);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	public ResponseEntity<Void> postJobRunPending(HttpServletRequest request, HttpServletResponse response) {
		workflowClient.post("/api/v1/jobs/runPending", null, Void.class);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
