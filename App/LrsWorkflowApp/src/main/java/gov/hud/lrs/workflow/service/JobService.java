package gov.hud.lrs.workflow.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ImmutableList;

import gov.hud.lrs.common.dto.LrsApiV1DTOs.JobDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.JobExecutionDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.JobParameterDTO;
import gov.hud.lrs.common.entity.Job;
import gov.hud.lrs.common.entity.JobExecution;
import gov.hud.lrs.common.entity.JobExecutionStatusRef;
import gov.hud.lrs.common.entity.JobParameter;
import gov.hud.lrs.common.entity.JobStatusRef;
import gov.hud.lrs.common.entity.JobTypeRef;
import gov.hud.lrs.common.enumeration.UniverseCodes;
import gov.hud.lrs.common.repository.JobExecutionRepository;
import gov.hud.lrs.common.repository.JobExecutionStatusRefRepository;
import gov.hud.lrs.common.repository.JobParameterRepository;
import gov.hud.lrs.common.repository.JobRepository;
import gov.hud.lrs.common.repository.JobStatusRefRepository;
import gov.hud.lrs.common.repository.JobTypeRefRepository;
import gov.hud.lrs.common.security.SecurityService;
import gov.hud.lrs.common.service.LenderService;
import gov.hud.lrs.common.util.Util;

@Service
public class JobService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired private JobRepository jobRepository;
	@Autowired private JobStatusRefRepository jobStatusRefRepository;
	@Autowired private JobTypeRefRepository jobTypeRefRepository;
	@Autowired private JobExecutionRepository jobExecutionRepository;
	@Autowired private JobExecutionStatusRefRepository jobExecutionStatusRefRepository;
	@Autowired private JobParameterRepository jobParameterRepository;

	@Autowired private AggregationService aggregationService;
	@Autowired private AsyncService asyncService;
	@Autowired private BinderService binderService;
	@Autowired private EmailService emailService;
	@Autowired private JobService jobService;
	@Autowired private ReviewService reviewService;
	@Autowired private SecurityService securityService;
	@Autowired private SelectionService selectionService;
	@Autowired private DistributionService distributionService;
	@Autowired private WorkflowServerService workflowServerService;
	@Autowired private LenderService lenderService;

	@PersistenceContext private EntityManager entityManager;

	private Map<String, JobExecution> currentJobExecutions = new ConcurrentHashMap<String, JobExecution>();

	private DateFormat jobParameterDateFormat = new SimpleDateFormat(JobParameter.DATE_FORMAT);

	public Job getJob(String jobId) {
		return jobRepository.findOne(jobId);
	}

	@Transactional(readOnly = true)
	public List<Job> getJobs() {
		return entityManager.createQuery(
			"select distinct j from Job j " +
			"join fetch j.jobStatusRef " +
			"join fetch j.jobTypeRef " +
			"left join fetch j.jobParameters " +
			"left join fetch j.jobExecutions je " +
			"left join fetch je.jobExecutionStatusRef " +
			"order by j.createdTs desc ",
			Job.class
		)
		.setMaxResults(250)
		.getResultList();
	}

	@Transactional(readOnly = true)
	public List<Job> getPendingJobs() {
		return entityManager.createQuery(
			"select distinct j from Job j " +
			"join fetch j.jobStatusRef " +
			"join fetch j.jobTypeRef " +
			"left join fetch j.jobParameters " +
			"left join fetch j.jobExecutions je " +
			"left join fetch je.jobExecutionStatusRef " +
			"where j.jobStatusRef.code = :jobStatusCode " +
			"order by j.createdTs desc ",
			Job.class
		)
		.setParameter("jobStatusCode", JobStatusRef.PENDING)
		.getResultList();
	}

	@Transactional(readOnly = true)
	public List<Job> getPendingJob(String jobTypeCode) {
		return entityManager.createQuery(
			"select distinct j from Job j " +
			"join fetch j.jobStatusRef " +
			"join fetch j.jobTypeRef " +
			"left join fetch j.jobParameters " +
			"left join fetch j.jobExecutions je " +
			"left join fetch je.jobExecutionStatusRef " +
			"where j.jobStatusRef.code = :jobStatusCode and " +
			"j.jobTypeRef.code = :jobTypeCode " +
			"order by j.createdTs desc ",
			Job.class
		)
		.setParameter("jobStatusCode", JobStatusRef.PENDING)
		.setParameter("jobTypeCode", jobTypeCode)
		.getResultList();
	}
	
	@Transactional
	public Job createJob(String jobTypeCode) {
		return createJob(jobTypeCode, new String[] {}, false);
	}

	@Transactional
	public Job createJob(String jobTypeCode, String[] parameters, boolean isAutoSelectedJob) {
		return createJob(jobTypeRefRepository.findByCode(jobTypeCode), parameters, isAutoSelectedJob);
	}

	@Transactional
	public Job createJob(JobTypeRef jobType, String[] parameters, boolean isAutoSelectedJob) {	// parameters is [key1, value1, key2, value2, ...]
		String userId = securityService.getUserId();
		Date now = new Date();

		Job job = new Job();
		job.setJobTypeRef(jobType);
		job.setJobStatusRef(jobStatusRefRepository.findByCode(JobStatusRef.PENDING));
		job.setWorkflowServerInfo(workflowServerService.getCurrentServerInfo());
		job.setCreatedBy(userId);
		job.setCreatedTs(now);
		if (isAutoSelectedJob) {
			job.setAutoSelectionIndicator('Y');
		} else {
			job.setAutoSelectionIndicator('N');
		}
		job = jobRepository.save(job);

		if (parameters != null) {
			if ((parameters.length % 2) != 0) {
				throw new RuntimeException("Parameters must have an even number of elements");
			}
			for (int i = 0; i < parameters.length; i+= 2) {
				String name = parameters[i];
				String value = parameters[i + 1];
				JobParameter jobParameter = new JobParameter();
				jobParameter.setJob(job);
				jobParameter.setName(name);
				jobParameter.setValue(value);
				jobParameter.setCreatedBy(userId);
				jobParameter.setCreatedTs(now);
				jobParameter = jobParameterRepository.save(jobParameter);
				job.getJobParameters().add(jobParameter);
			}
		}

		return job;
	}

	@Transactional
	public JobExecution createJobExecution(Job job) {
		String userId = securityService.getUserId();
		Date now = new Date();

		JobExecution jobExecution = new JobExecution();
		jobExecution.setJob(job);
		jobExecution.setJobExecutionStatusRef(jobExecutionStatusRefRepository.findByCode(JobExecutionStatusRef.STARTED));
		jobExecution.setWorkflowServerInfo(workflowServerService.getCurrentServerInfo());
		jobExecution.setCreatedBy(userId);
		jobExecution.setCreatedTs(now);
		jobExecution = jobExecutionRepository.save(jobExecution);
		job.getJobExecutions().add(jobExecution);

		return jobExecution;
	}

	public Future<Void> runJobAsync(Job job) {
		return asyncService.call(
			new Callable<Void>() {
				@Override
				public Void call() throws Exception {
					// we call this through jobService to get the @Transactional AOP proxy behavior
					jobService.runJob(job);
					return null;
				}
			},
			securityService.getFhacUser()
		);
	}

	public void runJob(Job job) {
		if (currentJobExecutions.containsKey(job.getJobId())) {
			throw new RuntimeException("Job " + job.getJobId() + " is already executing");
		}

		JobExecution jobExecution = jobService.createJobExecution(job);
		currentJobExecutions.put(job.getJobId(), jobExecution);

		try {
			Map<String, String> parameters = Util.index(job.getJobParameters(), p -> p.getName(), p -> p.getValue());

			Date startDate = null;
			String startDateString = parameters.get(JobParameter.START_DATE);
			if (startDateString != null) {
				startDate = jobParameterDateFormat.parse(startDateString);
			}

			Date endDate = null;
			String endDateString = parameters.get(JobParameter.END_DATE);
			if (endDateString != null) {
				endDate = jobParameterDateFormat.parse(endDateString);
			}

			switch (job.getJobTypeRef().getCode()) {
				case JobTypeRef.ENDORSEMENT_SELECTION: {
					if ((startDate == null) || (endDate == null)) {
						throw new RuntimeException("Start and end dates are required");
					}
					selectionService.runUniverseSelectionModel(UniverseCodes.ENDORSEMENT, startDate, endDate, job);
					break;
				}

				case JobTypeRef.EARLY_CLAIM_SELECTION: {
					if ((startDate == null) || (endDate == null)) {
						throw new RuntimeException("Start and end dates are required");
					}
					selectionService.runUniverseSelectionModel(UniverseCodes.EARLY_CLAIM, startDate, endDate, job);
					break;
				}

				case JobTypeRef.EARLY_PAYMENT_DEFAULT_SELECTION: {
					if ((startDate == null) || (endDate == null)) {
						throw new RuntimeException("Start and end dates are required");
					}
					selectionService.runUniverseSelectionModel(UniverseCodes.EARLY_PAYMENT_DEFAULT, startDate, endDate, job);
					break;
				}

				case JobTypeRef.NATIONAL_QC_SELECTION: {
					if ((startDate == null) || (endDate == null)) {
						throw new RuntimeException("Start and end dates are required");
					}
					selectionService.runUniverseSelectionModel(UniverseCodes.NATIONAL_QC, startDate, endDate, job);
					break;
				}

				case JobTypeRef.LENDER_MONITORING_SELECTION: {
					String selectionRequestId = parameters.get(JobParameter.SELECTION_REQUEST_ID);
					if (selectionRequestId == null) {
						throw new RuntimeException("Selection request ID is required");
					}
					selectionService.runLenderMonitoringSelectionModel(selectionRequestId, job);
					break;
				}

				case JobTypeRef.AGGREGATION: {
					aggregationService.aggregatePendingSelections(job);
					break;
				}

				case JobTypeRef.LENDER_MONITORING_EMAIL: {
					emailService.generateLenderMonitoringEmail(job);
					break;
				}

				case JobTypeRef.DAILY_COMBINED_LENDER_EMAIL: {
					emailService.generateDailyCombinedEmail(job);
					break;
				}

				case JobTypeRef.DISTRIBUTION: {
					distributionService.distributeLoanSelections(job);
					break;
				}

				case JobTypeRef.BINDER_REQUEST: {
					String throttlingString = parameters.get(JobParameter.THROTTLING);
					if (throttlingString == null) {
						throw new RuntimeException("Throttling parameter is required");
					}
					boolean throttling = throttlingString.equals("true");
					binderService.requestBinders(throttling, job);
					break;
				}

				case JobTypeRef.BINDER_RECEIPT: {
					binderService.checkBinderReceipts(job);
					break;
				}

				case JobTypeRef.LATE_LENDER_REQUESTS: {
					reviewService.expireLenderRequests(job);
					break;
				}

				case JobTypeRef.LATE_BINDERS: {
					binderService.handleLateBinders(job);
					break;
				}

				case JobTypeRef.REMAINING_MONTHLY_CAPACITY: {
					selectionService.fillRemainingMonthlyCapacity(job);
					break;
				}
				
				case JobTypeRef.SEND_EMAIL: {
					emailService.sendEmail(job);
					break;
				}

				case JobTypeRef.REFRESH_LENDERS: {
					String lenderRefreshBatchSize = parameters.get(JobParameter.REFRESH_LENDER_BATCH_SIZE);
					if (lenderRefreshBatchSize == null) {
						throw new RuntimeException("LenderRefreshBatchSize parameter is required");
					}
					int lenderRefreshBatchSizeIntValue = new Integer(lenderRefreshBatchSize).intValue();
					lenderService.refreshLenders(lenderRefreshBatchSizeIntValue, job);
					break;
				}
				
				default: {
					throw new RuntimeException("Unhandled JobTypeRef code: " + job.getJobTypeRef().getCode());
				}
			}

			jobService.updateJobExecutionCompleted(jobExecution);

		} catch (Throwable t) {
			jobService.updateJobExecutionFailed(jobExecution);

			logger.error("Job " + job.getJobId() + " failed", t);

			throw new RuntimeException(t);

		} finally {
			currentJobExecutions.remove(job.getJobId());
		}
	}

	// this is really a private method; don't use
	@Transactional
	public void updateJobExecutionCompleted(JobExecution jobExecution) {
		String userId = securityService.getUserId();
		Date now = new Date();

		jobExecution.setJobExecutionStatusRef(jobExecutionStatusRefRepository.findByCode(JobExecutionStatusRef.COMPLETED));
		jobExecution.setEndedDt(now);
		jobExecution.setUpdatedBy(userId);
		jobExecution.setUpdatedTs(now);
		jobExecution = jobExecutionRepository.save(jobExecution);

		Job job = jobExecution.getJob();
		// If the job is send email or refresh lender job then don't update the status to complete because these job's are not daily/monthly jobs.
		if (!ImmutableList.of(JobTypeRef.SEND_EMAIL, JobTypeRef.REFRESH_LENDERS).contains(job.getJobTypeRef().getCode())) {
			job.setJobStatusRef(jobStatusRefRepository.findByCode(JobStatusRef.COMPLETED));
		}
		job.setUpdatedBy(userId);
		job.setUpdatedTs(now);
		job = jobRepository.save(job);
	}

	@Transactional
	public void updateJobExecutionFailed(JobExecution jobExecution) {
		Date now = new Date();
		jobExecution.setJobExecutionStatusRef(jobExecutionStatusRefRepository.findByCode(JobExecutionStatusRef.FAILED));
		jobExecution.setEndedDt(now);
		jobExecution.setUpdatedBy(securityService.getUserId());
		jobExecution.setUpdatedTs(now);
		jobExecution = jobExecutionRepository.save(jobExecution);
	}

	public JobDTO convertJobToJobDTO(Job job) {
		JobDTO jobDTO = new JobDTO();
		jobDTO.setJobId(job.getJobId());
		jobDTO.setType(job.getJobTypeRef().getDescription());
		jobDTO.setStatus(job.getJobStatusRef().getDescription());

		List<JobParameterDTO> jobParameterDTOs = new ArrayList<JobParameterDTO>(job.getJobParameters().size());
		for (JobParameter jobParameter : job.getJobParameters()) {
			JobParameterDTO jobParameterDTO = new JobParameterDTO();
			jobParameterDTO.setName(jobParameter.getName());
			jobParameterDTO.setValue(jobParameter.getValue());
			jobParameterDTOs.add(jobParameterDTO);
		}
		jobDTO.setParameters(jobParameterDTOs);

		List<JobExecutionDTO> jobExecutionDTOs = new ArrayList<JobExecutionDTO>();
		for (JobExecution jobExecution : job.getJobExecutions()) {
			JobExecutionDTO jobExecutionDTO = new JobExecutionDTO();
			jobExecutionDTO.setJobExecutionId(jobExecution.getJobExecutionId());
			jobExecutionDTO.setStatus(jobExecution.getJobExecutionStatusRef().getDescription());
			jobExecutionDTO.setStartDate(jobExecution.getCreatedTs());
			jobExecutionDTO.setEndDate(jobExecution.getEndedDt());
			if (jobExecution.getWorkflowServerInfo() != null) {
				jobExecutionDTO.setServerName(jobExecution.getWorkflowServerInfo().getServerName());
				jobExecutionDTO.setServerPriority(jobExecution.getWorkflowServerInfo().getServerPriority().toString());
			}
			jobExecutionDTO.setCreatedBy(jobExecution.getCreatedBy());
			if (jobExecution.getExceptionDetails() != null) {
				jobExecutionDTO.setExceptionDetails(jobExecution.getExceptionDetails().toString());
			}
			jobExecutionDTOs.add(jobExecutionDTO);
		}
		jobDTO.setExecutions(jobExecutionDTOs);

		return jobDTO;
	}

	public Job runEndorsementSelectionJob(Date startDate, Date endDate) {
		Job job = createJob(
			jobTypeRefRepository.findByCode(JobTypeRef.ENDORSEMENT_SELECTION),
			new String[] {
				JobParameter.START_DATE, jobParameterDateFormat.format(startDate),
				JobParameter.END_DATE, jobParameterDateFormat.format(endDate),
			},
			false
		);
		runJobAsync(job);

		return job;
	}

	public Job runEarlyClaimSelectionJob(Date startDate, Date endDate) {
		Job job = createJob(
			jobTypeRefRepository.findByCode(JobTypeRef.EARLY_CLAIM_SELECTION),
			new String[] {
				JobParameter.START_DATE, jobParameterDateFormat.format(startDate),
				JobParameter.END_DATE, jobParameterDateFormat.format(endDate),
			},
			false
		);
		runJobAsync(job);

		return job;
	}

	public Job runEarlyPaymentDefaultSelectionJob(Date startDate, Date endDate) {
		Job job = createJob(
			jobTypeRefRepository.findByCode(JobTypeRef.EARLY_PAYMENT_DEFAULT_SELECTION),
			new String[] {
				JobParameter.START_DATE, jobParameterDateFormat.format(startDate),
				JobParameter.END_DATE, jobParameterDateFormat.format(endDate),
			},
			false
		);
		runJobAsync(job);

		return job;
	}

	public Job runNationalQcSelectionJob(Date startDate, Date endDate) {
		Job job = createJob(
			jobTypeRefRepository.findByCode(JobTypeRef.NATIONAL_QC_SELECTION),
			new String[] {
				JobParameter.START_DATE, jobParameterDateFormat.format(startDate),
				JobParameter.END_DATE, jobParameterDateFormat.format(endDate),
			},
			false
		);
		runJobAsync(job);

		return job;
	}

	public Job runAggregationJob() {
		Job job = createJob(JobTypeRef.AGGREGATION);
		runJobAsync(job);

		return job;
	}

	public Job runDistributionJob() {
		Job job = createJob(JobTypeRef.DISTRIBUTION);
		runJobAsync(job);

		return job;
	}

	public Job runLateBindersJob() {
		Job job = createJob(JobTypeRef.LATE_BINDERS);
		runJobAsync(job);

		return job;
	}

	public Job runBinderRequestJob(boolean throttling) {
		Job job = createJob(
			jobTypeRefRepository.findByCode(JobTypeRef.BINDER_REQUEST),
			new String[] { JobParameter.THROTTLING, throttling ? "true" : "false" },
			false
		);
		runJobAsync(job);

		return job;
	}

	public Job runBinderReceiptJob() {
		Job job = createJob(JobTypeRef.BINDER_RECEIPT);
		runJobAsync(job);

		return job;
	}

	public Job runFillRemainingMonthlyCapacityJob() {
		Job job = createJob(JobTypeRef.REMAINING_MONTHLY_CAPACITY);
		runJob(job);

		return job;
	}

	public Job runLateLenderRequests() {
		Job job = createJob(JobTypeRef.LATE_LENDER_REQUESTS);
		runJobAsync(job);

		return job;
	}

	public Job runLenderMonitoringEmailJob() {
		Job job = createJob(JobTypeRef.LENDER_MONITORING_EMAIL);
		runJobAsync(job);

		return job;
	}

	public Job runDailyCombinedLenderEmailJob() {
		Job job = createJob(JobTypeRef.DAILY_COMBINED_LENDER_EMAIL);
		runJobAsync(job);

		return job;
	}
}
