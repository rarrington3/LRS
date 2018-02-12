package gov.hud.lrs.workflow.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import gov.hud.lrs.common.dto.LrsApiV1DTOs.DateRangeDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.JobDTO;
import gov.hud.lrs.common.entity.Job;
import gov.hud.lrs.common.exception.NotFoundException;
import gov.hud.lrs.common.security.SecurityService;
import gov.hud.lrs.workflow.service.AsyncService;
import gov.hud.lrs.workflow.service.JobService;
import gov.hud.lrs.workflow.service.SchedulingService;

@RestController
@RequestMapping("/api/v1/jobs")
public class JobsController {

	@Autowired private AsyncService asyncService;
	@Autowired private JobService jobService;
	@Autowired private SchedulingService schedulingService;
	@Autowired private SecurityService securityService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}

	@InitBinder("dateRangeDTO")
	protected void initBinderReceiptDTOBinder(WebDataBinder binder) {
		binder.setAllowedFields(new String[] { "startDate", "endDate" });
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<JobDTO> get() {
		List<Job> jobs = jobService.getJobs();

		return jobs
			.stream()
			.map(x -> jobService.convertJobToJobDTO(x))
			.collect(Collectors.toList())
		;
	}

	@RequestMapping(value = "/pending", method = RequestMethod.GET)
	public List<JobDTO> getPending() {
		List<Job> activeJobs = jobService.getPendingJobs();

		return activeJobs
			.stream()
			.map(x -> jobService.convertJobToJobDTO(x))
			.collect(Collectors.toList())
		;
	}

	@RequestMapping(value = "/{jobId}", method = RequestMethod.GET)
	public JobDTO getJobByJobId(@PathVariable String jobId) {
		Job job = jobService.getJob(jobId);
		if (job == null) {
			throw new NotFoundException("No Job with jobId " + jobId);
		}

		return jobService.convertJobToJobDTO(job);
	}

	@RequestMapping(value = "/endorsementSelection", method = RequestMethod.POST)
	public JobDTO endorsementSelection(@RequestBody DateRangeDTO dateRangeDTO) {
		Job job = jobService.runEndorsementSelectionJob(dateRangeDTO.getStartDate(), dateRangeDTO.getEndDate());
		return jobService.convertJobToJobDTO(job);
	}

	@RequestMapping(value = "/earlyClaimSelection", method = RequestMethod.POST)
	public JobDTO earlyClaimSelection(@RequestBody DateRangeDTO dateRangeDTO) {
		Job job = jobService.runEarlyClaimSelectionJob(dateRangeDTO.getStartDate(), dateRangeDTO.getEndDate());
		return jobService.convertJobToJobDTO(job);
	}

	@RequestMapping(value = "/earlyPaymentDefaultSelection", method = RequestMethod.POST)
	public JobDTO earlyPaymentDefaultSelection(@RequestBody DateRangeDTO dateRangeDTO) {
		Job job = jobService.runEarlyPaymentDefaultSelectionJob(dateRangeDTO.getStartDate(), dateRangeDTO.getEndDate());
		return jobService.convertJobToJobDTO(job);
	}

	@RequestMapping(value = "/nationalQcSelection", method = RequestMethod.POST)
	public JobDTO nationalQcSelection(@RequestBody DateRangeDTO dateRangeDTO) {
		Job job = jobService.runNationalQcSelectionJob(dateRangeDTO.getStartDate(), dateRangeDTO.getEndDate());
		return jobService.convertJobToJobDTO(job);
	}

	@RequestMapping(value = "/aggregation", method = RequestMethod.POST)
	public JobDTO aggregation() {
		Job job = jobService.runAggregationJob();
		return jobService.convertJobToJobDTO(job);
	}

	@RequestMapping(value = "/distribution", method = RequestMethod.POST)
	public JobDTO distribution() {
		Job job = jobService.runDistributionJob();
		return jobService.convertJobToJobDTO(job);
	}

	@RequestMapping(value = "/binderRequest", method = RequestMethod.POST)
	public JobDTO requestBinders() {
		Job job = jobService.runBinderRequestJob(false);
		return jobService.convertJobToJobDTO(job);
	}

	@RequestMapping(value = "/binderRequest/throttled", method = RequestMethod.POST)
	public JobDTO requestBindersThrottled() {
		Job job = jobService.runBinderRequestJob(true);
		return jobService.convertJobToJobDTO(job);
	}

	@RequestMapping(value = "/binderReceipt", method = RequestMethod.POST)
	public JobDTO binderReceipt() {
		Job job = jobService.runBinderReceiptJob();
		return jobService.convertJobToJobDTO(job);
	}

	@RequestMapping(value = "/lateBinders", method = RequestMethod.POST)
	public JobDTO lateBinders() {
		Job job = jobService.runLateBindersJob();
		return jobService.convertJobToJobDTO(job);
	}

	@RequestMapping(value = "/lateLenderRequests", method = RequestMethod.POST)
	public JobDTO lateLenderRequests() {
		Job job = jobService.runLateLenderRequests();
		return jobService.convertJobToJobDTO(job);
	}

	@RequestMapping(value = "/fillRemainingMonthlyCapacity", method = RequestMethod.POST)
	public JobDTO fillMonthlyCapacity() {
		Job job = jobService.runFillRemainingMonthlyCapacityJob();
		return jobService.convertJobToJobDTO(job);
	}

	@RequestMapping(value = "/lenderMonitoringEmail", method = RequestMethod.POST)
	public JobDTO lenderMonitoringEmail() {
		Job job = jobService.runLenderMonitoringEmailJob();
		return jobService.convertJobToJobDTO(job);
	}

	@RequestMapping(value = "/dailyCombinedLenderEmail", method = RequestMethod.POST)
	public JobDTO dailyCombinedLenderEmail() {
		Job job = jobService.runDailyCombinedLenderEmailJob();
		return jobService.convertJobToJobDTO(job);
	}

	@RequestMapping(value = "/sendEmail", method = RequestMethod.POST)
	public void sendEmail() {
		schedulingService.sendEmail();
	}

	@RequestMapping(value = "/refreshLenders", method = RequestMethod.POST)
	public void lenderRefresh() {
		schedulingService.refreshLenders();
	}

	@RequestMapping(value = "/runPending", method = RequestMethod.POST)
	public void runPending() {
		asyncService.call(
			new Callable<Void>() {
				@Override
				public Void call() {
					schedulingService.runPendingJobs();
					return null;
				}
			},
			securityService.getFhacUser()
		);
	}

}
