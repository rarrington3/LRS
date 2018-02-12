package gov.hud.lrs.workflow.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import gov.hud.lrs.common.entity.Job;
import gov.hud.lrs.common.entity.JobParameter;
import gov.hud.lrs.common.entity.JobTypeRef;
import gov.hud.lrs.common.security.SecurityService;

@Service
public class SchedulingService {

	@Value("${lrs.jobs.monthlyEndorsement.dayOfMonth}") private Integer monthlyEndorsementDayOfMonth;
	@Value("${lrs.jobs.earlyClaim.dayOfMonth}") private Integer earlyClaimDayOfMonth;
	@Value("${lrs.jobs.earlyPaymentDefault.dayOfMonth}") private Integer earlyPaymentDefaultDayOfMonth;
	@Value("${lrs.jobs.nationalQC.dayOfMonth}") private Integer nationalQCDefaultDayOfMonth;
	@Value("${lrs.jobs.remainingMonthlyCapacity.dayOfMonth}") private Integer remainingMonthlyCapacityDefaultDayOfMonth;
	@Value("${lrs.jobs.lenderRefresh.batchSize}") private String lenderRefreshBatchSize;
	@Value("${lrs.jobs.binderDowntimeStartHour}") private int binderDowntimeStartHour;
	@Value("${lrs.jobs.binderDowntimeEndHour}") private int binderDowntimeEndHour;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired private JobService jobService;
	@Autowired private SecurityService securityService;
	@Autowired private WorkflowServerService workflowServerService;

	@PersistenceContext private EntityManager entityManager;

	private DateTimeFormatter jobParameterDateTimeFormatter = DateTimeFormatter.ofPattern(JobParameter.DATE_FORMAT);

	/*
	 * Get the ending date of the case selection period based on the automaticDayOfMonth.  The current configured automaticDateOfMonth is 1, so the if condition is always true, 
	 * and the ending date of case selection is always first day of the current month. 
	 * Originally, the automaticDayOfMonth was configured to 15.  Therefore, if the current date is after automaticDayOfMonth, the ending date of case
	 * selection period will be the 15th of the current month, else, the ending date of case selection period will the 15th of the prior month.
	 * For example, for ENDORSEMENT_SELECTION, if today is 5/26/2017, we'll get cases for one month period ending 5/15/2017.  If today is 5/12/2017, 
	 * we'll get cases for the month period ending 4/15/2017. 
	 */
	private LocalDate getMonthlyAutomaticDate(int automaticDayOfMonth) {
		LocalDate now = LocalDate.now();
		if (now.getDayOfMonth() >= automaticDayOfMonth) {
			return LocalDate.of(now.getYear(), now.getMonth(), automaticDayOfMonth);
		} else {
			LocalDate lastMonth = now.minusMonths(1);
			return LocalDate.of(lastMonth.getYear(), lastMonth.getMonth(), automaticDayOfMonth);
		}
	}

	@Scheduled(cron = "${lrs.jobs.spawner.cron}")
	public void spawnDailyMonthlyJobs() {
		MDC.put("logFileName", "job");

		securityService.setSystemUser();

		logger.debug("Spawning any needed daily/monthly jobs");

		// automatic monthly endorsement selection
		LocalDate automaticDate = getMonthlyAutomaticDate(monthlyEndorsementDayOfMonth);
		createAutomaticJobIfNeeded(
			JobTypeRef.ENDORSEMENT_SELECTION,
			automaticDate,
			new String[] {
				JobParameter.START_DATE, jobParameterDateTimeFormatter.format(automaticDate.minusMonths(2)),
				JobParameter.END_DATE, jobParameterDateTimeFormatter.format(automaticDate.minusMonths(1)),
			}
		);

		// automatic monthly early claim selection
		automaticDate = getMonthlyAutomaticDate(earlyClaimDayOfMonth);
		createAutomaticJobIfNeeded(
			JobTypeRef.EARLY_CLAIM_SELECTION,
			automaticDate,
			new String[] {
				JobParameter.START_DATE, jobParameterDateTimeFormatter.format(automaticDate.minusMonths(1).minusYears(2)),
				JobParameter.END_DATE, jobParameterDateTimeFormatter.format(automaticDate.minusMonths(1)),
			}
		);

		// automatic monthly early payment default selection
		automaticDate = getMonthlyAutomaticDate(earlyPaymentDefaultDayOfMonth);
		createAutomaticJobIfNeeded(
			JobTypeRef.EARLY_PAYMENT_DEFAULT_SELECTION,
			automaticDate,
			new String[] {
				JobParameter.START_DATE, jobParameterDateTimeFormatter.format(automaticDate.minusMonths(1).minusYears(2)),
				JobParameter.END_DATE, jobParameterDateTimeFormatter.format(automaticDate.minusMonths(1)),
			}
		);

		// automatic monthly national QC selection
		automaticDate = getMonthlyAutomaticDate(nationalQCDefaultDayOfMonth);
		createAutomaticJobIfNeeded(
			JobTypeRef.NATIONAL_QC_SELECTION,
			automaticDate,
			new String[] {
				JobParameter.START_DATE, jobParameterDateTimeFormatter.format(automaticDate.minusMonths(2)),
				JobParameter.END_DATE, jobParameterDateTimeFormatter.format(automaticDate.minusMonths(1)),
			}
		);

		// daily aggregation
		automaticDate = LocalDate.now();
		createAutomaticJobIfNeeded(JobTypeRef.AGGREGATION, automaticDate);

		// daily distribution
		automaticDate = LocalDate.now();
		createAutomaticJobIfNeeded(JobTypeRef.DISTRIBUTION, automaticDate);

		// automatic monthly fill remaining capacity
		automaticDate = getMonthlyAutomaticDate(remainingMonthlyCapacityDefaultDayOfMonth);
		createAutomaticJobIfNeeded(JobTypeRef.REMAINING_MONTHLY_CAPACITY, automaticDate);
				
		// daily binder requests
		automaticDate = LocalDate.now();
		createAutomaticJobIfNeeded(
			JobTypeRef.BINDER_REQUEST,
			automaticDate,
			new String[] { JobParameter.THROTTLING, "true" }
		);

		// daily binder receipt
		automaticDate = LocalDate.now();
		createAutomaticJobIfNeeded(JobTypeRef.BINDER_RECEIPT, automaticDate);

		// daily late binders
		automaticDate = LocalDate.now();
		createAutomaticJobIfNeeded(JobTypeRef.LATE_BINDERS, automaticDate);

		// daily late lender requests
		automaticDate = LocalDate.now();
		createAutomaticJobIfNeeded(JobTypeRef.LATE_LENDER_REQUESTS, automaticDate);

		// daily lender monitoring email
		automaticDate = LocalDate.now();
		createAutomaticJobIfNeeded(JobTypeRef.LENDER_MONITORING_EMAIL, automaticDate);

		// daily combined lender email
		automaticDate = LocalDate.now();
		createAutomaticJobIfNeeded(JobTypeRef.DAILY_COMBINED_LENDER_EMAIL, automaticDate);

		logger.debug("Done spawning jobs");

		MDC.remove("logFileName");
	}

	@Scheduled(cron = "${lrs.jobs.runner.cron}")
	public void runPendingJobs() {
		MDC.put("logFileName", "job");

		logger.debug("Running pending jobs");

		securityService.setSystemUser();

		List<Job> jobs = jobService.getPendingJobs();
		logger.debug("Found " + jobs.size() + " pending jobs to run");

		jobs.sort((j1, j2) -> j1.getCreatedTs().compareTo(j2.getCreatedTs()));
		for (Job job : jobs) {
			if (ImmutableList.of(JobTypeRef.BINDER_REQUEST, JobTypeRef.BINDER_RECEIPT).contains(job.getJobTypeRef().getCode())) {
				LocalTime now = LocalTime.now();
				LocalTime binderDowntimeStart = LocalTime.of(binderDowntimeStartHour, 0, 0);
				LocalTime binderDowntimeEnd = LocalTime.of(binderDowntimeEndHour, 0, 0);
				boolean inBinderDowntime;
				if (binderDowntimeStartHour < binderDowntimeEndHour) {
					inBinderDowntime = now.isAfter(binderDowntimeStart) && now.isBefore(binderDowntimeEnd);
	 			} else {
	 				inBinderDowntime = !(now.isAfter(binderDowntimeEnd) && now.isBefore(binderDowntimeStart));
				}

				if (inBinderDowntime) {
					logger.debug("Skipping " + job.getJobTypeRef().getCode() + " job " + job.getJobId() + ", binder services are in downtime");
					continue;
				}
				
			}
			if (ImmutableList.of(JobTypeRef.SEND_EMAIL, JobTypeRef.REFRESH_LENDERS).contains(job.getJobTypeRef().getCode())){
				logger.debug("Skipping " + job.getJobTypeRef().getCode() + " job " + job.getJobId() + ", because this job is running on its own schdule");
				continue;
			}
			
			if (!workflowServerService.isHigherPriorityServerAvailable()) {
				logger.debug("Running " + job.getJobTypeRef().getCode() + " job " + job.getJobId());
				jobService.runJob(job);
			}
		}
		logger.debug("Done running pending jobs");

		MDC.remove("logFileName");
	}

	@Scheduled(cron = "${lrs.jobs.mail.cron}")
	public void sendEmail() {
		if (!workflowServerService.isHigherPriorityServerAvailable()) {
			if (securityService.getFhacUser() == null) {
				securityService.setSystemUser();
			}
			List<Job> sendEmailPendingJobs = jobService.getPendingJob(JobTypeRef.SEND_EMAIL);
			if (sendEmailPendingJobs != null && sendEmailPendingJobs.size() > 0) {
				jobService.runJob(sendEmailPendingJobs.get(0));
			} else {
				// create send email Job
				LocalDate automaticDate = LocalDate.now();
				createAutomaticJobIfNeeded(JobTypeRef.SEND_EMAIL, automaticDate);
			}
		}
	}

	@Scheduled(cron = "${lrs.jobs.lenderRefresh.cron}")
	public void refreshLenders() {
		if (!workflowServerService.isHigherPriorityServerAvailable()) {
			if (securityService.getFhacUser() == null) {
				securityService.setSystemUser();
			}
			List<Job> refreshLenderPendingJobs = jobService.getPendingJob(JobTypeRef.REFRESH_LENDERS);
			if (refreshLenderPendingJobs != null && refreshLenderPendingJobs.size() > 0) {
				jobService.runJob(refreshLenderPendingJobs.get(0));
			} else {
				// create Refresh lender Job
				LocalDate automaticDate = LocalDate.now();
				createAutomaticJobIfNeeded(
						JobTypeRef.REFRESH_LENDERS,
						automaticDate,
						new String[] { JobParameter.REFRESH_LENDER_BATCH_SIZE, lenderRefreshBatchSize }
						);
			}
		}
	}
	
	@Scheduled(cron = "${lrs.jobs.workflowheartbeat.cron}")
	public void updateWorkFlowHeartBeat() {
		securityService.setSystemUser();
		workflowServerService.updateWorkFlowHeartBeat();
	}
	
	private void createAutomaticJobIfNeeded(String jobTypeCode, LocalDate automaticDate) {
		createAutomaticJobIfNeeded(jobTypeCode, automaticDate, new String[] {});
	}

	/*
	 * Check if there's already a pending job for the specified jobTypeCode which has ending date the same as automaticDate.  If yes, do not schedule the
	 * same job again.  Otherwise, create a pending job for the specified jobTypeCode.
	*/
	private void createAutomaticJobIfNeeded(String jobTypeCode, LocalDate automaticDate, String[] parameters) {
		if (workflowServerService.isHigherPriorityServerAvailable()){
			return;
		}
		Integer count = (Integer)entityManager.createNativeQuery(
			"SELECT COUNT(*) " +
			"FROM JOB J " +
			"INNER JOIN JOB_TYPE_REF JT ON (JT.JOB_TYPE_ID = J.JOB_TYPE_ID) " +
			"WHERE " +
				"(JT.CODE = :jobTypeCode) AND " +
				"(EXISTS (SELECT * FROM JOB_PARAMETER JP WHERE (JP.JOB_ID = J.JOB_ID) AND (JP.NAME = :jobParameterName) AND (JP.VALUE = :jobParameterValue))) "
		)
		.setParameter("jobTypeCode", jobTypeCode)
		.setParameter("jobParameterName", JobParameter.AUTOMATIC_DATE)
		.setParameter("jobParameterValue", jobParameterDateTimeFormatter.format(automaticDate))
		.getSingleResult();

		if (count.equals(0)) {
			String[] parametersWithAutomaticDate = new String[parameters.length + 2];
			int i = 0;
			for (; i < parameters.length; i++) {
				parametersWithAutomaticDate[i] = parameters[i];
			}
			parametersWithAutomaticDate[i] = JobParameter.AUTOMATIC_DATE;
			parametersWithAutomaticDate[i + 1] = jobParameterDateTimeFormatter.format(automaticDate);
			Job job = jobService.createJob(jobTypeCode, parametersWithAutomaticDate, true);

			logger.debug("Created automatic " + job.getJobTypeRef().getCode() + " job " + job.getJobId() + " for date " + automaticDate + " with parameters: ");
			for (int p = 0; p < parametersWithAutomaticDate.length; p += 2) {
				logger.debug("\t" + parametersWithAutomaticDate[p] + ": " + parametersWithAutomaticDate[p + 1]);
			}
		}
	}

}
