package gov.hud.lrs.common.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableMap;

import gov.hud.lrs.common.entity.Batch;
import gov.hud.lrs.common.entity.Email;
import gov.hud.lrs.common.entity.EmailTemplate;
import gov.hud.lrs.common.entity.Lender;
import gov.hud.lrs.common.entity.LenderMonitoringSelectionRequest;
import gov.hud.lrs.common.enumeration.EmailStatusCodes;
import gov.hud.lrs.common.enumeration.LenderRequestStatusCodes;
import gov.hud.lrs.common.enumeration.ReviewTypeCodes;
import gov.hud.lrs.common.repository.EmailStatusRefRepository;
import gov.hud.lrs.common.security.SecurityService;

@Service
public class CommonEmailService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String OPEN_UNORDERED_LIST = "<ul>";
	private static final String OPEN_LIST_ITEM = "<li>";
	private static final String CLOSE_UNORDERED_LIST = "</ul>";
	private static final String CLOSE_LIST_ITEM = "</li>";
	private static final String OPEN_HTML = "<html><body>";
	private static final String CLOSE_HTML = "</body></html>";
	private static final String OPEN_ANCHOR = "<a href=\"";
	private static final String OPEN_ANCHOR_TARGET = "\" target=\"_new\">";
	private static final String CLOSE_ANCHOR = "</a>";
	private static final String LENDER_ID = "[Lender ID]";
	private static final String LENDER_NAME = "[Lender Name]";
	private static final String RESPONSE_REQUEST_COUNT = "[Response Request Count]";
	private static final String LENDER_ACTIVE_REVIEW_LINK = "[Lender Active Reviews Link]";
	private static final String REVIEW_LOCATION = "[Review Location]";
	private static final String REVIEW_TYPE = "[Review Type]";
	private static final String FILE_DELIVERY_LOCATION = "[File Delivery Location]";
	private static final String DATE_OF_SITE_VISIT = "[Date Of Site Visit]";
	private static final String LOAN_TYPE = "[Loan Type]";
	private static final String CASE_COUNT = "[Case Count]";
	private static final String OPERATIONAL_REVIEW_GUIDANCE = "[Operational Review Guidance]";
	private static final String LENDER_BATCH_SUMMARY_SCREEN_LINK = "[Lender Batch Summary Screen Link]";
	private static final String DATE_FORMAT_STRING = "MM-dd-yyy";
	private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_STRING);

	@Value("${lrs.devMode}") private boolean devMode;
	@Value("${lrs.template.link.protocolAndHost}") private String protocolAndHost;
	@Value("${lrs.template.link.lenderActiveReviewsSummary}") private String lenderActiveReviewsSummaryPath;
	@Value("${lrs.template.link.lenderActiveReviewsBatches}") private String lenderActiveReviewsBatchesPath;

	@Autowired private EmailStatusRefRepository emailStatusRefRepository;

	@Autowired private SecurityService securityService;

	@Autowired private JavaMailSenderImpl mailSenderImpl;

	@PersistenceContext(unitName = "lrs") private EntityManager entityManager;

	private final Map<String, String> previewEmailDefaultValues;

	public CommonEmailService(
		@Value("${lrs.template.link.internalActiveReviewsSummary}") String internalActiveReviewsSummaryPath,
		@Value("${lrs.template.link.internalActiveReviewsBatches}") String internalActiveReviewsBatchesPath,
		@Value("${lrs.template.link.protocolAndHost}") String protocolAndHost
	) {
		previewEmailDefaultValues = new ImmutableMap.Builder<String, String>()
			.put(LENDER_ID, "99999")
			.put(LENDER_NAME, "The Village")
			.put(
				RESPONSE_REQUEST_COUNT, OPEN_UNORDERED_LIST +
				OPEN_LIST_ITEM + "Underwriting - 6" + CLOSE_LIST_ITEM +
				OPEN_LIST_ITEM + "Servicing - 2" + CLOSE_LIST_ITEM +
				OPEN_LIST_ITEM + "Operational - 3" + CLOSE_LIST_ITEM +
				OPEN_LIST_ITEM + "Comprehensive - 2" + CLOSE_LIST_ITEM +
				CLOSE_UNORDERED_LIST
			)
			.put(LENDER_ACTIVE_REVIEW_LINK, OPEN_ANCHOR + protocolAndHost + internalActiveReviewsSummaryPath + OPEN_ANCHOR_TARGET + "My Active Reviews" + CLOSE_ANCHOR)
			.put(REVIEW_LOCATION, "HQ")
			.put(REVIEW_TYPE, "Underwriting")
			.put(FILE_DELIVERY_LOCATION, "FHA")
			.put(DATE_OF_SITE_VISIT, "TBD")
			.put(LOAN_TYPE, "Forward")
			.put(CASE_COUNT, "50")
			.put(OPERATIONAL_REVIEW_GUIDANCE, "Operational Review Guidance: Lorem ipsum dolor sit amet, quo ut suas doming, eos ex idque fuisset theophrastus. Ex eos corpora fierent. Ea nam facilisis inciderint consectetuer, debitis facilisis in duo. No tritani sensibus pri, sit nisl quaeque cu. Nec te forensibus efficiantur, no quo enim delicata.")
			.put(LENDER_BATCH_SUMMARY_SCREEN_LINK, OPEN_ANCHOR + protocolAndHost + internalActiveReviewsBatchesPath + OPEN_ANCHOR_TARGET + "Batch ID: 2017-99999-001" + CLOSE_ANCHOR)
			.build();
	}

	public void sendMail(String emailId, MimeMessage message) {
		mailSenderImpl.getSession().setDebug(devMode);
		if (message != null) {
			try {
				mailSenderImpl.send(message);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	public MimeMessage buildMimeMessage(Email email) {
		MimeMessage message = null;
		try {
			message = mailSenderImpl.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
			helper.setValidateAddresses(true);
			if (email.getToAddress() != null && email.getToAddress().length() > 0) {
				helper.setTo(new String[]{ email.getToAddress() });
			}
			if (email.getCcAddress() != null && email.getCcAddress().length() > 0) {
				helper.setCc(new String[]{ email.getCcAddress() });
			}
			helper.setText(email.getBody(), true);
			helper.setSubject(email.getSubject());
		} catch (MessagingException e) {
			logger.error("Unable to build email message for " + email.getEmailId() + " to send: " + e.getMessage());
			return null;
		}
		return message;
	}

	public String generateEmailText(String tokenText, Lender lender, LenderMonitoringSelectionRequest lenderMonitoringSelectionRequest, Timestamp startTime, Timestamp endTime, boolean isHTML) {
		Map<String, String> placeholderValues = new HashMap<String, String>();
		if (tokenText.contains(LENDER_ID) || tokenText.contains(LENDER_NAME)) {
			placeholderValues.put(LENDER_ID, lender.getLenderId());
			placeholderValues.put(LENDER_NAME, lender.getName());
		}

		if (tokenText.contains(RESPONSE_REQUEST_COUNT)) {
			Query query = entityManager.createNativeQuery(
				"SELECT COUNT(LR.LENDER_REQUEST_ID) AS LENDER_REQUEST_COUNT, RTR.REVIEW_TYPE_CD FROM LENDER_REQUEST LR " +
				"INNER JOIN REVIEW_LEVEL RL ON (LR.REVIEW_LEVEL_ID = RL.REVIEW_LEVEL_ID) " +
				"INNER JOIN REVIEW R ON (RL.REVIEW_ID = R.REVIEW_ID) " +
				"INNER JOIN LOAN_SELECTION LS ON (R.SELECTION_ID = LS.SELECTION_ID) " +
				"INNER JOIN LENDER_REQUEST_STATUS_REF LRSR ON (LR.LENDER_REQUEST_STATUS_ID = LRSR.LENDER_REQUEST_STATUS_ID) " +
				"INNER JOIN REVIEW_TYPE_REF RTR ON (RTR.REVIEW_TYPE_ID = R.REVIEW_TYPE_ID) " +
				"WHERE " +
					"(LR.REQUESTED_DATE BETWEEN :startTime AND :endTime) AND " +
					"(LRSR.CODE = '" + LenderRequestStatusCodes.IN_PROGRESS + "') AND " +
					"(LS.MTGEE5 = '" + lender.getLenderId() + "') " +
					"GROUP BY RTR.REVIEW_TYPE_CD"
			);
			query.setParameter("startTime", startTime);
			query.setParameter("endTime", endTime);

			@SuppressWarnings("unchecked")
			List<Object[]> resultList = query.getResultList();
			StringBuilder responseRequestCount = new StringBuilder("");
			if (resultList != null && resultList.size() > 0) {
				responseRequestCount.append(OPEN_UNORDERED_LIST);
				for (Object[] result : resultList) {
					int count = (Integer)result[0];
					String reviewTypeCode = Character.toString((Character)result[1]);
					switch (reviewTypeCode) {
						case ReviewTypeCodes.UNDERWRITING: {
							responseRequestCount.append(OPEN_LIST_ITEM);
							responseRequestCount.append("Underwriting - " + count);
							responseRequestCount.append(CLOSE_LIST_ITEM);
							break;
						}

						case ReviewTypeCodes.SERVICING: {
							responseRequestCount.append(OPEN_LIST_ITEM);
							responseRequestCount.append("Servicing - " + count);
							responseRequestCount.append(CLOSE_LIST_ITEM);
							break;
						}

						case ReviewTypeCodes.OPERATIONAL: {
							responseRequestCount.append(OPEN_LIST_ITEM);
							responseRequestCount.append("Operational - " + count);
							responseRequestCount.append(CLOSE_LIST_ITEM);
							break;
						}

						case ReviewTypeCodes.COMPREHENSIVE: {
							responseRequestCount.append(OPEN_LIST_ITEM);
							responseRequestCount.append("Comprehensive - " + count);
							responseRequestCount.append(CLOSE_LIST_ITEM);
							break;
						}
					}
				}
				responseRequestCount.append(CLOSE_UNORDERED_LIST);
			}
			placeholderValues.put(RESPONSE_REQUEST_COUNT, responseRequestCount.toString());
		}

		if (tokenText.contains(LENDER_ACTIVE_REVIEW_LINK)) {
			placeholderValues.put(LENDER_ACTIVE_REVIEW_LINK,
				OPEN_ANCHOR + protocolAndHost + lenderActiveReviewsSummaryPath + OPEN_ANCHOR_TARGET + "My Active Reviews" + CLOSE_ANCHOR);
		}

		if (lenderMonitoringSelectionRequest != null &&
			(
				tokenText.contains(REVIEW_LOCATION) ||
				tokenText.contains(REVIEW_TYPE) ||
				tokenText.contains(FILE_DELIVERY_LOCATION) ||
				tokenText.contains(DATE_OF_SITE_VISIT) ||
				tokenText.contains(LOAN_TYPE) ||
				tokenText.contains(CASE_COUNT)
			)
		) {
			placeholderValues.put(REVIEW_LOCATION, lenderMonitoringSelectionRequest.getReviewLocation().getLocationName());
			placeholderValues.put(REVIEW_TYPE, lenderMonitoringSelectionRequest.getReviewTypeRef().getDescription());
			placeholderValues.put(FILE_DELIVERY_LOCATION, lenderMonitoringSelectionRequest.getFileDeliveryLocationRef().getDescription());
			placeholderValues.put(DATE_OF_SITE_VISIT, lenderMonitoringSelectionRequest.getSiteVisitDt() != null ? dateFormat.format(lenderMonitoringSelectionRequest.getSiteVisitDt()) : "TBD"); // if no value show TBD
			placeholderValues.put(LOAN_TYPE, lenderMonitoringSelectionRequest.getLoanTypeRef().getDescription());
			placeholderValues.put(CASE_COUNT, Integer.toString(lenderMonitoringSelectionRequest.getCaseCount()));
		}

		if (lenderMonitoringSelectionRequest != null &&
			(
				tokenText.contains(OPERATIONAL_REVIEW_GUIDANCE) ||
				tokenText.contains(LENDER_BATCH_SUMMARY_SCREEN_LINK)
			)
		) {
			Set<Batch> batches = lenderMonitoringSelectionRequest.getSelectionRequest().getBatches(); // should return one for lender monitoring
			Batch batch = batches.iterator().next();
			placeholderValues.put(OPERATIONAL_REVIEW_GUIDANCE, batch.getOperationalReviewGuidance());
			placeholderValues.put(LENDER_BATCH_SUMMARY_SCREEN_LINK,
				OPEN_ANCHOR + protocolAndHost + lenderActiveReviewsBatchesPath + OPEN_ANCHOR_TARGET + "Batch ID: " + batch.getBatchReferenceId() + CLOSE_ANCHOR);
		}

		tokenText = replacePlaceholderValues(placeholderValues, tokenText);

		if (isHTML) {
			tokenText = OPEN_HTML + tokenText + CLOSE_HTML;
		}
		return tokenText;
	}

	public String generateTestEmailText(String tokenText) {
		return replacePlaceholderValues(previewEmailDefaultValues, tokenText);
	}

	private String replacePlaceholderValues(Map<String, String> placeholderValues, String tokenText) {
		for (Map.Entry<String, String> placeholderValue : placeholderValues.entrySet()) {
			String placeholder = placeholderValue.getKey();
			String value = placeholderValue.getValue() == null ? "" : placeholderValue.getValue();
			tokenText = tokenText.replace(placeholder, value);
		}
		return tokenText;
	}

	public Email createEmail(String subject, String body, String toAddress, String ccAddress, EmailTemplate emailTemplate) {
		String userId = securityService.getUserId();
		Date now = new Date();
		Email email = new Email();
		email.setSubject(subject);
		email.setBody(body);
		email.setToAddress(toAddress);
		email.setCcAddress(ccAddress);
		email.setEmailStatusRef(emailStatusRefRepository.findByCode(EmailStatusCodes.PENDING));
		email.setTries((short) 0);
		email.setCreatedBy(userId);
		email.setCreatedTs(now);
		email.setUpdatedBy(userId);
		email.setUpdatedTs(now);
		email.setEmailTemplate(emailTemplate);
		return email;
	}

}
