package gov.hud.lrs.workflow.service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.hud.lrs.common.entity.Email;
import gov.hud.lrs.common.entity.EmailTemplateVersion;
import gov.hud.lrs.common.entity.Job;
import gov.hud.lrs.common.entity.Lender;
import gov.hud.lrs.common.entity.LenderMonitoringSelectionRequest;
import gov.hud.lrs.common.enumeration.EmailStatusCodes;
import gov.hud.lrs.common.enumeration.EmailTemplates;
import gov.hud.lrs.common.enumeration.LenderRequestStatusCodes;
import gov.hud.lrs.common.repository.BatchRepository;
import gov.hud.lrs.common.repository.EmailRepository;
import gov.hud.lrs.common.repository.EmailStatusRefRepository;
import gov.hud.lrs.common.repository.EmailTemplateVersionRepository;
import gov.hud.lrs.common.repository.LenderMonitoringSelectionRequestRepository;
import gov.hud.lrs.common.security.SecurityService;
import gov.hud.lrs.common.service.CommonEmailService;
import gov.hud.lrs.common.service.LenderService;

@Service
public class EmailService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static final char Y = 'Y';
	private static final String LINE_BREAK = "<br/>";

	@Value("${lrs.mail.retryCount}") private short retryCount;
	@Value("${lrs.jobs.email.recordCreatedByHour}") private int recordCreatedByHour;
	@Value("${lrs.jobs.email.recordCreatedByMinute}") private int recordCreatedByMinute;

	@Autowired EmailRepository emailRepository;
	@Autowired EmailStatusRefRepository emailStatusRefRepository;
	@Autowired EmailTemplateVersionRepository emailTemplateVersionRepository;
	@Autowired LenderMonitoringSelectionRequestRepository lenderMonitoringSelectionRequestRepository;
	@Autowired BatchRepository batchRepository;

	@Autowired CommonEmailService commonEmailService;
	@Autowired SecurityService securityService;
	@Autowired LenderService lenderService;

	@PersistenceContext(unitName = "lrs") private EntityManager entityManager;

	@Transactional
	public void sendEmail(Job job) {
		MDC.put("logFileName", "sendEmail");
		String userId = securityService.getUserId();

		List<Email> pendingEmails = emailRepository.findByEmailStatusRefCode(EmailStatusCodes.PENDING);
		logger.debug((pendingEmails != null ? pendingEmails.size() : "0") + " pending emails found");
		for (Email email : pendingEmails) {
			Date now = new Date();
			MimeMessage message = commonEmailService.buildMimeMessage(email);
			email.setUpdatedBy(userId);
			email.setUpdatedTs(now);
			if (message != null && (email.getTries() < retryCount)) {
				short tries = (short) (email.getTries() + 1);
				email.setTries(tries);
				try {
					commonEmailService.sendMail(email.getEmailId(), message);
					email.setSentTs(now);
					email.setEmailStatusRef(emailStatusRefRepository.findByCode(EmailStatusCodes.SENT));
					logger.debug("Attempt " + tries + " to send message " + email.getEmailId() + " was successful!");
				} catch (RuntimeException e) {
					logger.error("Attempt " + tries + " to send message " + email.getEmailId() + " failed: " + e.getMessage());
					if (tries == retryCount) {
						logger.error("\tReached max number of retries; updating status to cancelled.");
						email.setEmailStatusRef(emailStatusRefRepository.findByCode(EmailStatusCodes.CANCELLED));
					}
				}
				logger.debug("\tTo Address: " + email.getToAddress() + "; Cc Address: " + email.getCcAddress() + "; Subject: " + email.getSubject());
			} else {
				logger.error("\tReached max number of retries; updating status to cancelled.");
				email.setEmailStatusRef(emailStatusRefRepository.findByCode(EmailStatusCodes.CANCELLED));
			}
		}
		logger.debug("Done sending emails");
		MDC.remove("logFileName");
	}

	@Transactional
	public void generateLenderMonitoringEmail(Job job) {
		String logFileName = "generateLenderMonitoringEmail";
		MDC.put("logFileName", logFileName);
		logger.debug(job.getJobId() + ": " + "Start generating lender monitoring email records");

		Timestamp endTime = Timestamp.valueOf(getRecordCreatedBy());
		Timestamp startTime = Timestamp.valueOf(getRecordCreatedBy().minusDays(1));
		EmailTemplateVersion emailTemplateVersion =
			emailTemplateVersionRepository.findByEmailTemplateCodeAndActiveInd(EmailTemplates.LENDER_MONITORING, Y);
		if (emailTemplateVersion == null) {
			throw new RuntimeException("Active lender monitoring email template not found.");
		}
		String subject = emailTemplateVersion.getSubject();
		String body = emailTemplateVersion.getBody();
		body = body.replaceAll(System.lineSeparator(), LINE_BREAK);

		List<LenderMonitoringSelectionRequest> lenderMonitoringSelectionRequests = lenderMonitoringSelectionRequestRepository.findByCreatedTsBetween(startTime, endTime);
		logger.debug("Found " + lenderMonitoringSelectionRequests.size() == null ? "0" : lenderMonitoringSelectionRequests.size() +
			" lender monitoring requests created between " + startTime + " and " + endTime);
		if (lenderMonitoringSelectionRequests != null && lenderMonitoringSelectionRequests.size() > 0) {
			for (LenderMonitoringSelectionRequest lenderMonitoringSelectionRequest : lenderMonitoringSelectionRequests) {
				Lender lender = lenderMonitoringSelectionRequest.getLender();
				try {
					if (lender.getEmail() != null) {
						String tokenReplacedSubject = commonEmailService.generateEmailText(subject, lender, lenderMonitoringSelectionRequest, startTime, endTime, false);
						String tokenReplacedBody = commonEmailService.generateEmailText(body, lender, lenderMonitoringSelectionRequest, startTime, endTime, true);
						Email email = commonEmailService.createEmail(
							tokenReplacedSubject,
							tokenReplacedBody,
							lender.getEmail(),
							lender.getSecondaryEmail(),
							emailTemplateVersion.getEmailTemplate()
						);
						emailRepository.save(email);
						logger.debug("Lender Monitoring Email " + email.getEmailId() + " added to queue");
						logger.debug("\tTo Address: " + email.getToAddress() + "; Cc Address: " + email.getCcAddress() + "; Subject: " + email.getSubject());
					} else {
						throw new RuntimeException("Admin contact email for lender " + lender.getLenderId() + " does not exist.");
					}
				} catch (RuntimeException e) {
					logger.error(job.getJobId() + ": " + "Unable to create lender monitoring email for lender " + lender.getLenderId()  + " " + e);
				}
			}
		}

		logger.debug(job.getJobId() + ": " + "Finished generating lender monitoring email records");
		MDC.remove("logFileName");
	}

	@Transactional
	public void generateDailyCombinedEmail(Job job) {
		String logFileName = "generateDailyCombinedEmail";
		MDC.put("logFileName", logFileName);
		logger.debug(job.getJobId() + ": " + "Start generating daily combined lender email records");

		Timestamp endTime = Timestamp.valueOf(getRecordCreatedBy());
		Timestamp startTime = Timestamp.valueOf(getRecordCreatedBy().minusDays(1));
		EmailTemplateVersion emailTemplateVersion =
			emailTemplateVersionRepository.findByEmailTemplateCodeAndActiveInd(EmailTemplates.DAILY_LENDER, Y);
		if (emailTemplateVersion == null) {
			throw new RuntimeException("Active daily combined lender email template not found.");
		}
		String subject = emailTemplateVersion.getSubject();
		String body = emailTemplateVersion.getBody();
		body = body.replaceAll(System.lineSeparator(), LINE_BREAK);

		Query query = entityManager.createNativeQuery(
			"SELECT DISTINCT LS.MTGEE5 FROM LENDER_REQUEST LR " +
			"INNER JOIN REVIEW_LEVEL RL ON (LR.REVIEW_LEVEL_ID = RL.REVIEW_LEVEL_ID) " +
			"INNER JOIN REVIEW R ON (RL.REVIEW_ID = R.REVIEW_ID) " +
			"INNER JOIN LOAN_SELECTION LS ON (R.SELECTION_ID = LS.SELECTION_ID) " +
			"INNER JOIN LENDER_REQUEST_STATUS_REF LRSR ON (LR.LENDER_REQUEST_STATUS_ID = LRSR.LENDER_REQUEST_STATUS_ID) " +
			"WHERE " +
				"(LR.REQUESTED_DATE BETWEEN :startTime AND :endTime) AND " +
				"(LRSR.CODE = '" + LenderRequestStatusCodes.IN_PROGRESS + "')"
		);
		query.setParameter("startTime", startTime);
		query.setParameter("endTime", endTime);

		@SuppressWarnings("unchecked")
		List<String> resultList = query.getResultList();
		logger.debug("Found " + resultList.size() == null ? "0" : resultList.size() +
			" lender response requests created between " + startTime + " and " + endTime);
		if (resultList != null && resultList.size() > 0) {
			for (String lenderId : resultList) {
				try {
					Lender lender = lenderService.getLender(lenderId);
					if (lender == null) {
						throw new RuntimeException("Lender " + lenderId + " does not exist.");
					}
					if (lender.getEmail() != null) {
						String tokenReplacedSubject = commonEmailService.generateEmailText(subject, lender, null, startTime, endTime, false);
						String tokenReplacedBody = commonEmailService.generateEmailText(body, lender, null, startTime, endTime, true);
						Email email = commonEmailService.createEmail(
							tokenReplacedSubject,
							tokenReplacedBody,
							lender.getEmail(),
							lender.getSecondaryEmail(),
							emailTemplateVersion.getEmailTemplate()
						);
						emailRepository.save(email);
						logger.debug("Daily Combined Email " + email.getEmailId() + " added to queue");
						logger.debug("\tTo Address: " + email.getToAddress() + "; Cc Address: " + email.getCcAddress() + "; Subject: " + email.getSubject());
					} else {
						throw new RuntimeException(job.getJobId() + ": " + "Admin contact email for lender " + lenderId + " does not exist.");
					}
				} catch (RuntimeException e) {
					logger.error(job.getJobId() + ": " + "Unable to create daily combined lender email for lender " + lenderId + " " + e);
				}
			}
		}

		logger.debug(job.getJobId() + ": " + "Finished generating daily combined lender email records");
		MDC.remove("logFileName");
	}

	private LocalDateTime getRecordCreatedBy() {
		LocalDate today = LocalDate.now();
		LocalTime recordCreatedBy = LocalTime.of(recordCreatedByHour, recordCreatedByMinute);
		return LocalDateTime.of(today, recordCreatedBy);
	}

}
