package gov.hud.lrs.workflow.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import gov.hud.lrs.common.entity.BatchStatusRef;
import gov.hud.lrs.common.entity.Job;
import gov.hud.lrs.common.enumeration.LoanSelectionStatusCodes;
import gov.hud.lrs.common.enumeration.ReviewStatusCodes;
import gov.hud.lrs.common.enumeration.ReviewTypeCodes;
import gov.hud.lrs.common.enumeration.SelectionReasonCodes;
import gov.hud.lrs.common.security.SecurityService;

@Service
public class AggregationService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private final String JOB_LOG_FILE_FORMAT = "yyyy.MM.dd-HH.mm.ss";

	@Value("${lrs.jobs.batchSize}") private int batchSize;

	@Autowired private SecurityService securityService;

	@Autowired private PlatformTransactionManager transactionManager;

	@PersistenceContext(unitName = "lrs") private EntityManager entityManager;

	// "duplicate" is defined as:
	// - worse priority (higher number)
	// - same priority, but created later
	// - same priority, same created date, but higher id (arbitrary tiebreaker)
	// - loan selection for same case # already exists
	// - review already for same case # exists

	private class DuplicateLoanSelectionPending {
		public String selectionId;
		public String caseNumber;
		public String selectionReasonDescription;
		public String selectionRequestId;
		public String selectionRequestTypeDescription;
	}

	@Transactional
	public void aggregatePendingSelections(Job job) {
		MDC.put("logFileName", "aggregatePendingSelections-" + new SimpleDateFormat(JOB_LOG_FILE_FORMAT).format(new Date()) +  "-" + job.getJobId());
		logger.info("Aggregating duplicate LoanSelectionPendings");

		DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
		transactionDefinition.setIsolationLevel(TransactionDefinition.ISOLATION_SERIALIZABLE);	// we must ignore phantom reads since we select then delete
		TransactionStatus transactionStatus = transactionManager.getTransaction(transactionDefinition);
		try {
			List<DuplicateLoanSelectionPending> duplicateLoanSelectionPendings = findDuplicates();
			logger.info("Found " + duplicateLoanSelectionPendings.size() + " duplicates:");

			for (DuplicateLoanSelectionPending duplicateLoanSelectionPending : duplicateLoanSelectionPendings) {
				logger.info(
					"\t" +
					"Case: " + duplicateLoanSelectionPending.caseNumber + "; " +
					"Selection Reason: " + duplicateLoanSelectionPending.selectionReasonDescription + "; " +
					"Selection Request ID: " + duplicateLoanSelectionPending.selectionRequestId+ "; " +
					"Selection Request Type: " + duplicateLoanSelectionPending.selectionRequestTypeDescription
				);
			}

			for (int start = 0; start < duplicateLoanSelectionPendings.size(); start += batchSize) {
				int end = Math.min(duplicateLoanSelectionPendings.size(), start + batchSize);
				logger.debug("Deleting duplicates " + (start + 1) + " to " + end);

				List<String> selectionIds = duplicateLoanSelectionPendings
					.subList(start, end)
					.stream()
					.map(d -> d.selectionId)
					.collect(Collectors.toList())
				;

				entityManager.createNativeQuery("DELETE FROM MODEL_SCORE WHERE SELECTION_ID IN (:selectionIds)").setParameter("selectionIds", selectionIds).executeUpdate();
				entityManager.createNativeQuery("DELETE FROM LOAN_SELECTION_PENDING WHERE SELECTION_ID IN (:selectionIds)").setParameter("selectionIds", selectionIds).executeUpdate();
				entityManager.createNativeQuery("DELETE FROM LOAN_SELECTION_CASE_SUMMARY WHERE SELECTION_ID IN (:selectionIds)").setParameter("selectionIds", selectionIds).executeUpdate();

				transactionManager.commit(transactionStatus);
				transactionStatus = transactionManager.getTransaction(null);
			}

			convertLoanSelectionPendingsToLoanSelections();

			cancelOrphanedBatches();

			transactionManager.commit(transactionStatus);

		} catch (Throwable t) {
			transactionManager.rollback(transactionStatus);
			throw t;
		}

		logger.debug("Finished aggregating duplicates");
		MDC.remove("logFileName");
	}

	private List<DuplicateLoanSelectionPending> findDuplicates() {
		Query query = entityManager.createNativeQuery(
			"SELECT " +
				"LSP.SELECTION_ID, " +
				"LSP.CASE_NUMBER, " +
				"SR.DESCRIPTION AS SELECTION_REASON_DESCRIPTION, " +
				"SRQ.SELECTION_REQUEST_ID, " +
				"SRQT.DESCRIPTION AS SELECTION_REQUEST_TYPE_DESCRIPTION " +
			"FROM LOAN_SELECTION_PENDING LSP " +
			"INNER JOIN LOAN_SELECTION_CASE_SUMMARY LSCS ON (LSCS.SELECTION_ID = LSP.SELECTION_ID) " +
			"INNER JOIN SELECTION_REQUEST SRQ ON (SRQ.SELECTION_REQUEST_ID = LSCS.SELECTION_REQUEST_ID) " +
			"INNER JOIN SELECTION_REQUEST_TYPE_REF SRQT ON (SRQT.SELECTION_REQUEST_TYPE_ID = SRQ.SELECTION_REQUEST_TYPE_ID) " +
			"INNER JOIN SELECTION_REASON SR ON (SR.SELECTION_REASON_ID = LSP.SELECTION_REASON_ID) " +
			"INNER JOIN REVIEW_TYPE_REF RT ON (RT.REVIEW_TYPE_ID = LSP.REVIEW_TYPE_ID) " +
			"WHERE " +
				// same case number/review type already selected
				"(EXISTS (" +
					"SELECT * FROM LOAN_SELECTION LS " +
					"INNER JOIN REVIEW_TYPE_REF LSRT ON (LSRT.REVIEW_TYPE_ID = LS.REVIEW_TYPE_ID) " +
					"INNER JOIN LOAN_SELECTION_STATUS_REF LSS ON (LSS.LOAN_SELECTION_STATUS_ID = LS.LOAN_SELECTION_STATUS_ID) " +
					"WHERE " +
						"(LS.SELECTION_ID <> LSP.SELECTION_ID) AND " +
						"(LS.CASE_NUMBER = LSP.CASE_NUMBER) AND " +
						"(LSS.CODE NOT IN ('" + LoanSelectionStatusCodes.CANCELLED + "')" +
						") AND " +
							"((LS.SELECTION_ID IN (SELECT R.SELECTION_ID FROM REVIEW R " +
							"INNER JOIN REVIEW_STATUS_REF RSF ON (RSF.REVIEW_STATUS_ID = R.REVIEW_STATUS_ID) " +
							"WHERE " +
							"((RSF.CODE NOT IN ('" + ReviewStatusCodes.COMPLETED + "', '" + ReviewStatusCodes.CANCELLED + "') AND " +
								"SR.CODE IN ('" + SelectionReasonCodes.NATIONAL_QC + "', '" +
								SelectionReasonCodes.FHA_MANUAL + "', '" +
								SelectionReasonCodes.TEST_CASE + "', '" +
								SelectionReasonCodes.OIG_AUDIT + "', '" +
								SelectionReasonCodes.LENDER_SELF_REPORT + "', '" +
								SelectionReasonCodes.REVIEW_LOCATION_QC + "')) " +
							"OR " +
							"(RSF.CODE NOT IN ('" + ReviewStatusCodes.CANCELLED + "') AND " +
								"SR.CODE NOT IN ('" + SelectionReasonCodes.NATIONAL_QC + "', '" +
								SelectionReasonCodes.FHA_MANUAL + "', '" +
								SelectionReasonCodes.TEST_CASE + "', '" +
								SelectionReasonCodes.OIG_AUDIT + "', '" +
								SelectionReasonCodes.LENDER_SELF_REPORT + "', '" +
								SelectionReasonCodes.REVIEW_LOCATION_QC + "'))" +
							"))) OR LS.SELECTION_ID NOT IN (SELECT R.SELECTION_ID FROM REVIEW R WHERE R.SELECTION_ID IS NOT NULL)" +
						") AND " +
						"(" +
							"(RT.REVIEW_TYPE_ID = LSRT.REVIEW_TYPE_ID) OR " +
							"(RT.REVIEW_TYPE_CD = '" + ReviewTypeCodes.COMPREHENSIVE + "') OR " +
							"(LSRT.REVIEW_TYPE_CD = '" + ReviewTypeCodes.COMPREHENSIVE + "') " +
						") " +
				// higher priority pending selection
				")) " +
				"OR (EXISTS ( " +
					"SELECT * FROM LOAN_SELECTION_PENDING LSPSUB " +
					"INNER JOIN SELECTION_REASON SRSUB ON (SRSUB.SELECTION_REASON_ID = LSPSUB.SELECTION_REASON_ID) " +
					"INNER JOIN REVIEW_TYPE_REF RTSUB ON (RTSUB.REVIEW_TYPE_ID = LSPSUB.REVIEW_TYPE_ID) " +
					"WHERE " +
						// not selected
						"(NOT EXISTS (SELECT * FROM LOAN_SELECTION LS WHERE LS.SELECTION_ID = LSPSUB.SELECTION_ID)) AND " +
						// same case number
						"(LSPSUB.CASE_NUMBER = LSP.CASE_NUMBER) AND " +
						// same review type
						"(" +
							"(RT.REVIEW_TYPE_ID = RTSUB.REVIEW_TYPE_ID) OR " +
							"(RT.REVIEW_TYPE_CD ='" + ReviewTypeCodes.COMPREHENSIVE + "') OR " +
							"(RTSUB.REVIEW_TYPE_CD = '" + ReviewTypeCodes.COMPREHENSIVE + "') " +
						") AND " +
						// higher priority (lower number = higher priority), with earlier created as tie-breaker
						"(" +
							"(SRSUB.PRIORITY < SR.PRIORITY) OR  " +
							"((SRSUB.PRIORITY = SR.PRIORITY) AND (LSPSUB.CREATED_TS < LSP.CREATED_TS)) " +
						")" +
					"))"
		);

		@SuppressWarnings("unchecked")
		List<Object[]> resultList = query.getResultList();

		List<DuplicateLoanSelectionPending> duplicateLoanSelectionPendings = new ArrayList<DuplicateLoanSelectionPending>();
		for (Object[] result : resultList) {
			DuplicateLoanSelectionPending duplicateLoanSelectionPending = new DuplicateLoanSelectionPending();
			duplicateLoanSelectionPending.selectionId = (String)result[0];
			duplicateLoanSelectionPending.caseNumber = (String)result[1];
			duplicateLoanSelectionPending.selectionReasonDescription = (String)result[2];
			duplicateLoanSelectionPending.selectionRequestId = (String)result[3];
			duplicateLoanSelectionPending.selectionRequestTypeDescription = (String)result[4];
			duplicateLoanSelectionPendings.add(duplicateLoanSelectionPending);
		}

		return duplicateLoanSelectionPendings;
	}

	/**
	 * Strictly this method is used for lender monitoring selection de-dup before staging the loans selection pending table.
	 * @param caseNumber
	 * @param reviewType
	 * @return
	 */
	public boolean isDuplicateLoanLenderMonitoringLoan(String caseNumber, String reviewType) {
		String reviewTypeStr = "(LSRT.REVIEW_TYPE_CD IN ('" + reviewType + "', '"+ReviewTypeCodes.COMPREHENSIVE + "')) ";
		if (reviewType.equals(ReviewTypeCodes.COMPREHENSIVE)) {
			reviewTypeStr = "(LSRT.REVIEW_TYPE_CD IN ('" + reviewType + "', '" + ReviewTypeCodes.UNDERWRITING + "', '" + ReviewTypeCodes.SERVICING + "')) ";
		}
		Query query = entityManager.createNativeQuery(
					"SELECT LS.CASE_NUMBER, LS.MTGEE5, LS.REVIEW_TYPE_ID, LS.LOAN_SELECTION_STATUS_ID FROM LOAN_SELECTION LS " +
					"INNER JOIN REVIEW_TYPE_REF LSRT ON (LSRT.REVIEW_TYPE_ID = LS.REVIEW_TYPE_ID) " +
					"INNER JOIN LOAN_SELECTION_STATUS_REF LSS ON (LSS.LOAN_SELECTION_STATUS_ID = LS.LOAN_SELECTION_STATUS_ID) " +
					"WHERE " +
						"(LS.CASE_NUMBER = '" + caseNumber + "') AND " +
						reviewTypeStr + " AND " +
						"(LSS.CODE NOT IN ('" + LoanSelectionStatusCodes.CANCELLED + "'))" +
						" AND " +
							"(" +
								"(LS.SELECTION_ID IN (SELECT R.SELECTION_ID FROM REVIEW R " +
									"INNER JOIN REVIEW_STATUS_REF RSF ON (RSF.REVIEW_STATUS_ID = R.REVIEW_STATUS_ID) " +
									"WHERE " +
									"(RSF.CODE NOT IN ('" + ReviewStatusCodes.CANCELLED + "'))" +
									") " +
								")" +
								" OR " +
								"(" +
									"LS.SELECTION_ID NOT IN (SELECT R.SELECTION_ID FROM REVIEW R WHERE R.SELECTION_ID IS NOT NULL)" +
								")" +
							")"

		);

		@SuppressWarnings("unchecked")
		List<Object[]> resultList = query.getResultList();

		return resultList != null && resultList.size() > 0;
	}

	private void convertLoanSelectionPendingsToLoanSelections() {
		int numLoanSelectionsCreated = entityManager.createNativeQuery(
			"INSERT INTO LOAN_SELECTION( " +
				"SELECTION_ID, " +
				"CASE_NUMBER, " +
				"AUTO_SELECTION_INDICATOR, " +
				"MTGEE5, " +
				"LOAN_SELECTION_STATUS_ID, " +
				"BATCH_ID, " +
				"REVIEW_TYPE_ID, " +
				"PRODUCT_TYPE_ID, " +
				"SELECTION_REASON_ID, " +
				"REVIEW_SCOPE_ID, " +
				"REVIEW_LOCATION_ID, " +
				"SELECTION_DT, " +
				"QC_REVIEW_ID, " +
				"BORR_NAME, " +
				"ENDRSMNT_DT, " +
				"PROP_ADDR_1, " +
				"SELECTION_REQUEST_ID, " +
				"CS_ESTAB_DT, " +
				"CREATED_BY, " +
				"CREATED_TS " +
			") " +
			"SELECT " +
				"LSP.SELECTION_ID, " +
				"LSP.CASE_NUMBER, " +
				"LSP.AUTO_SELECTION_INDICATOR, " +
				"LSP.MTGEE5, " +
				"(SELECT LOAN_SELECTION_STATUS_ID FROM LOAN_SELECTION_STATUS_REF WHERE CODE = 'SELECTED'), " +
				"LSP.BATCH_ID, " +
				"LSP.REVIEW_TYPE_ID, " +
				"LSP.PRODUCT_TYPE_ID, " +
				"LSP.SELECTION_REASON_ID, " +
				"LSP.REVIEW_SCOPE_ID, " +
				"LSP.REVIEW_LOCATION_ID, " +
				"CURRENT_TIMESTAMP, " +
				"LSP.QC_REVIEW_ID, " +
				"LSP.BORR_NAME, " +
				"LSP.ENDRSMNT_DT, " +
				"LSP.PROP_ADDR_1, " +
				"LSP.SELECTION_REQUEST_ID, " +
				"LSP.CS_ESTAB_DT, " +
				":userId, " +
				"CURRENT_TIMESTAMP " +
			"FROM LOAN_SELECTION_PENDING LSP"
		)
		.setParameter("userId", securityService.getUserId())
		.executeUpdate();
		logger.debug("Created " + numLoanSelectionsCreated + " LoanSelections");

		int numLoanSelectionPendingsDeleted = entityManager.createNativeQuery(
			"DELETE FROM LOAN_SELECTION_PENDING WHERE SELECTION_ID IN (SELECT SELECTION_ID FROM LOAN_SELECTION)"
		).executeUpdate();
		logger.debug("Deleted " + numLoanSelectionPendingsDeleted + " LOAN_SELECTION_PENDING records");
	}

	private void cancelOrphanedBatches() {
		Query query = entityManager.createNativeQuery(
			"UPDATE BATCH " +
			"SET BATCH_STATUS_ID = (SELECT BAR.BATCH_STATUS_ID FROM BATCH_STATUS_REF BAR WHERE (BAR.CODE = '" + BatchStatusRef.CANCELLED + "')), " +
				"UPDATED_BY = '" + securityService.getUserId() + "', " +
				"UPDATED_TS = GETDATE() " +
			"WHERE BATCH_ID IN (SELECT BA.BATCH_ID FROM BATCH BA " +
				"WHERE NOT EXISTS  (SELECT * FROM LOAN_SELECTION_PENDING LSP WHERE (BA.BATCH_ID = LSP.BATCH_ID)) " +
				"AND NOT EXISTS (SELECT * FROM LOAN_SELECTION LS WHERE (BA.BATCH_ID = LS.BATCH_ID)) " +
				"AND NOT EXISTS (SELECT * FROM REVIEW RVW WHERE (BA.BATCH_ID = RVW.BATCH_ID)) " +
				"AND BA.OPERATIONAL_REVIEW_IND != 'Y' " +
				"AND BATCH_STATUS_ID NOT IN (SELECT BAR.BATCH_STATUS_ID FROM BATCH_STATUS_REF BAR WHERE (BAR.CODE = '" + BatchStatusRef.CANCELLED + "')))"
		);

		int numUpdated = query.executeUpdate();
		logger.debug("Cancelled " + numUpdated + " orphaned batches");
	}

}