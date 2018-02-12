package gov.hud.lrs.workflow.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;

import gov.hud.lrs.common.dto.MuleBinderReceiptResponseDTO.CaseBinderDTO;
import gov.hud.lrs.common.entity.Batch;
import gov.hud.lrs.common.entity.BatchStatusRef;
import gov.hud.lrs.common.entity.BinderRequest;
import gov.hud.lrs.common.entity.BinderRequestSourceRef;
import gov.hud.lrs.common.entity.BinderRequestStatusRef;
import gov.hud.lrs.common.entity.Job;
import gov.hud.lrs.common.entity.LoanSelection;
import gov.hud.lrs.common.entity.LoanSelectionCaseSummary;
import gov.hud.lrs.common.entity.LoanSelectionStatusRef;
import gov.hud.lrs.common.entity.Personnel;
import gov.hud.lrs.common.entity.Review;
import gov.hud.lrs.common.entity.ReviewLevel;
import gov.hud.lrs.common.entity.ReviewLevelIterationTimeframe;
import gov.hud.lrs.common.entity.ReviewLocation;
import gov.hud.lrs.common.entity.ReviewProcessException;
import gov.hud.lrs.common.entity.SelectionRequestTypeRef;
import gov.hud.lrs.common.enumeration.BinderRequestSourceCodes;
import gov.hud.lrs.common.enumeration.BinderRequestStatusCodes;
import gov.hud.lrs.common.enumeration.LoanSelectionStatusCodes;
import gov.hud.lrs.common.enumeration.ReviewLevelTypeCodes;
import gov.hud.lrs.common.enumeration.ReviewProcessExceptionTypeCodes;
import gov.hud.lrs.common.enumeration.ReviewTypeCodes;
import gov.hud.lrs.common.exception.ConflictException;
import gov.hud.lrs.common.exception.NotFoundException;
import gov.hud.lrs.common.repository.BatchRepository;
import gov.hud.lrs.common.repository.BatchStatusRefRepository;
import gov.hud.lrs.common.repository.BinderRequestRepository;
import gov.hud.lrs.common.repository.BinderRequestSourceRefRepository;
import gov.hud.lrs.common.repository.BinderRequestStatusRefRepository;
import gov.hud.lrs.common.repository.LoanSelectionCaseSummaryRepository;
import gov.hud.lrs.common.repository.LoanSelectionRepository;
import gov.hud.lrs.common.repository.LoanSelectionStatusRefRepository;
import gov.hud.lrs.common.repository.ReviewLevelIterationTimeframeRepository;
import gov.hud.lrs.common.repository.ReviewLocationRepository;
import gov.hud.lrs.common.repository.ReviewProcessExceptionRepository;
import gov.hud.lrs.common.security.SecurityService;
import gov.hud.lrs.common.service.BinderRequestService;
import gov.hud.lrs.common.service.CaseUniverseService;
import gov.hud.lrs.common.service.BinderRequestService.BinderRequestResult;
import gov.hud.lrs.common.service.CommonExceptionService;
import gov.hud.lrs.common.service.CommonLoanSelectionService;
import gov.hud.lrs.common.service.CommonReviewService;
import gov.hud.lrs.common.util.DateUtils;
import gov.hud.lrs.common.util.StringFunctionsUtil;
import gov.hud.lrs.common.util.Util;

@Service
public class BinderService {

	private Logger logger = LoggerFactory.getLogger(BinderService.class);
	private static final String JOB_LOG_FILE_FORMAT = "yyyy.MM.dd-HH.mm.ss";
	private static final int THROTTLE_CAP_DIVIDER = 30;

	@Value("${lrs.mule.binderReceipt.batchSize}") private int MULE_BINDER_RECEIPT_BATCH_SIZE;

	@Autowired private BatchRepository batchRepository;
	@Autowired private BatchStatusRefRepository batchStatusRefRepository;
	@Autowired private BinderRequestRepository binderRequestRepository;
	@Autowired private BinderRequestStatusRefRepository binderRequestStatusRefRepository;
	@Autowired private BinderRequestSourceRefRepository binderRequestSourceRefRepository;
	@Autowired private LoanSelectionRepository loanSelectionRepository;
	@Autowired private LoanSelectionStatusRefRepository loanSelectionStatusRefRepository;
	@Autowired private ReviewLevelIterationTimeframeRepository reviewLevelIterationTimeframeRepository;
	@Autowired private ReviewLocationRepository reviewLocationRepository;
	@Autowired private ReviewProcessExceptionRepository reviewProcessExceptionRepository;
	@Autowired private LoanSelectionCaseSummaryRepository loanSelectionCaseSummaryRepository;

	@Autowired private AssignmentService assignmentService;
	@Autowired private BinderRequestService binderRequestService;
	@Autowired private CommonExceptionService commonExceptionService;
	@Autowired private CommonReviewService commonReviewService;
	@Autowired private SecurityService securityService;
	@Autowired private CaseUniverseService caseUniverseService;
	@Autowired private CommonLoanSelectionService commonLoanSelectionService;

	@PersistenceContext(unitName = "lrs") private EntityManager entityManager;

	@Autowired private PlatformTransactionManager transactionManager;

	@Transactional
	public void requestBinders(boolean requestedThrottling, Job job) {
		MDC.put("logFileName", "requestBinders-" + new SimpleDateFormat(JOB_LOG_FILE_FORMAT).format(new Date()) +  "-" + job.getJobId());
		logger.debug("Starting binder request job");

		String userId = securityService.getUserId();
		Date now = new Date();
		boolean currentThrottling = requestedThrottling;

		// Get all loans whose status is DISTRIBUTED from the loan selection table.
		List<LoanSelection> loanSelections = loanSelectionRepository.findReadyForBinderRequest();
		logger.debug("Found " + loanSelections.size() + " DISTRIBUTED loans ready for binder request");

		Query query = entityManager.createNativeQuery(
			"SELECT LSCS.SELECTION_ID, SRT.CODE " +
			"FROM LOAN_SELECTION LS " +
			"INNER JOIN LOAN_SELECTION_STATUS_REF LSS ON (LSS.LOAN_SELECTION_STATUS_ID = LS.LOAN_SELECTION_STATUS_ID) " +
			"INNER JOIN LOAN_SELECTION_CASE_SUMMARY LSCS ON (LSCS.SELECTION_ID = LS.SELECTION_ID) " +
			"INNER JOIN SELECTION_REQUEST SR ON (SR.SELECTION_REQUEST_ID = LSCS.SELECTION_REQUEST_ID) " +
			"INNER JOIN SELECTION_REQUEST_TYPE_REF SRT ON (SRT.SELECTION_REQUEST_TYPE_ID = SR.SELECTION_REQUEST_TYPE_ID) " +
			"WHERE (LSS.CODE = :loanSelectionStatusCode) "
		);
		query.setParameter("loanSelectionStatusCode",  LoanSelectionStatusCodes.DISTRIBUTED);
		@SuppressWarnings("unchecked")
		List<Object[]> loanSelectionId_selectionRequestTypeCodes = query.getResultList();
		Map<String, String> selectionRequestTypeCodeByLoanSelectionId = Util.index(loanSelectionId_selectionRequestTypeCodes, x -> x[0].toString(), x -> x[1].toString());

		List<ReviewLevelIterationTimeframe> bndrReviewLevelIterationTimeframes = reviewLevelIterationTimeframeRepository.findByReviewLevelTypeRefReviewLevelCd(ReviewLevelTypeCodes.BINDER_REQUEST);
		Map<String, ReviewLevelIterationTimeframe> bndrReviewLevelIterationTimeframeByConsolidatedSelectionReasonCode = Maps.uniqueIndex(
			bndrReviewLevelIterationTimeframes,
			rt -> rt.getConsolidatedSelectionReason().getCode()
		);

		List<ReviewLocation> reviewLocations = reviewLocationRepository.findAll();
		Map<String, ReviewLocation> reviewLocationByReviewLocationId = Maps.uniqueIndex(reviewLocations, rl -> rl.getReviewLocationId());

		// reviewLocationId -> daily throttled capacity (1/30th of monthly capacity)
		Map<String, Integer> throttledCapacityRemainingByReviewLocationId = new HashMap<String, Integer>();
		for (ReviewLocation reviewLocation : reviewLocations) {
			throttledCapacityRemainingByReviewLocationId.put(reviewLocation.getReviewLocationId(), reviewLocation.getCommittedMonthlyCapacity() / THROTTLE_CAP_DIVIDER);
		}

		for (LoanSelection loanSelection : loanSelections) {
			// since we're only accessing location ID, this should not cause a DB query
			ReviewLocation reviewLocation = reviewLocationByReviewLocationId.get(loanSelection.getReviewLocation().getReviewLocationId());

			String selectionReqType = selectionRequestTypeCodeByLoanSelectionId.get(loanSelection.getSelectionId());

			boolean partOfBatch = (loanSelection.getBatch() != null); // PERF TODO: check that this isn't actually bringing in the batch record, but rather checking BATCH_ID to be null

			// Apply throttling for this current selection only if requested from the job,
			// and if the selection is a Universe request type (i.e. not lender monitoring,
			// FHA manual, or self report), and if this selection is not part of a batch
			currentThrottling = (
				requestedThrottling &&
				!selectionReqType.equals(SelectionRequestTypeRef.FHA_MANUAL) &&
				!selectionReqType.equals(SelectionRequestTypeRef.LENDER_MONITORING) &&
				!selectionReqType.equals(SelectionRequestTypeRef.LENDER_SELF_REPORT) &&
				!partOfBatch
			);

			int throttledCapacityRemaining = throttledCapacityRemainingByReviewLocationId.get(reviewLocation.getReviewLocationId());
			if (currentThrottling) {
				// Get the number of binders requested today for this location
				if (throttledCapacityRemaining <= 0) {
					logger.debug("Skipping case number " + loanSelection.getCaseNumber() + " (LoanSelection " + loanSelection.getSelectionId() + "): reached throttled daily capacity for location " + reviewLocation.getLocationName());
					continue;
				}
			}

			logger.debug("Requesting binder for case number " + loanSelection.getCaseNumber() + " (Location: " + reviewLocation.getLocationName() + ", Loan Selection: " + loanSelection.getSelectionId() + ", Selection Type: " + selectionReqType + ", Batch?: " + partOfBatch + ", current throttling: " + currentThrottling + ")");

			BinderRequestResult binderRequestResult = null;
			// If review type is SERVICING don't call Mule Binder Request and create binder request record.
			if (ReviewTypeCodes.SERVICING.equals(loanSelection.getReviewTypeRef().getReviewTypeCd())) {
				binderRequestResult = BinderRequestResult.REQUESTED;
			} else {
				binderRequestResult = binderRequestService.requestBinder(
						loanSelection.getCaseNumber(),
						reviewLocation
						);
			}

			if (
				BinderRequestResult.REQUESTED.equals(binderRequestResult) ||
				BinderRequestResult.WITH_HUD_PAPER.equals(binderRequestResult) ||
				BinderRequestResult.WITH_HUD_ELECTRONIC.equals(binderRequestResult)
			) {
				logger.debug("\tSuccess, creating BinderRequest");

				BinderRequest binderRequest = new BinderRequest();

				BinderRequestStatusRef binderRequestStatus = binderRequestStatusRefRepository.findByCode(BinderRequestStatusCodes.REQUESTED);
				LoanSelectionStatusRef loanSelectionStatus = loanSelectionStatusRefRepository.findByCode(LoanSelectionStatusCodes.REQUESTED);

				BinderRequestSourceRef binderRequestSource = null;
				if (BinderRequestResult.REQUESTED.equals(binderRequestResult)) {
					binderRequestSource = binderRequestSourceRefRepository.findByCode(BinderRequestSourceCodes.LENDER);
				} else {
					// binder already at HUD, request from records center
					binderRequestSource = binderRequestSourceRefRepository.findByCode(BinderRequestSourceCodes.RECORD_CENTER);
				}

				LoanSelectionCaseSummary loanSelectionCaseSummary = null;
				if (BinderRequestResult.WITH_HUD_ELECTRONIC.equals(binderRequestResult)) {
					loanSelectionCaseSummary = updateLoanSelectionCaseSummary(loanSelection);
					if (loanSelectionCaseSummary == null) {
						logger.error("\t\tskipping this case number");
						continue;
					}
				}

				binderRequest.setBinderRequestStatusRef(binderRequestStatus);
				binderRequest.setLoanSelection(loanSelection);
				binderRequest.setCaseNumber(loanSelection.getCaseNumber());
				binderRequest.setBinderRequestSourceRef(binderRequestSource);
				binderRequest.setRequestedDate(now);
				ReviewLevelIterationTimeframe binderReviewLevelIterationTimeframe = bndrReviewLevelIterationTimeframeByConsolidatedSelectionReasonCode.get(
					loanSelection.getSelectionReason().getConsolidatedSelectionReason().getCode()	// this should not result in a database roundtrip if we cache the ref tables properly
				);
				binderRequest.setDueDate(commonReviewService.calcBinderRequestDueDate(binderReviewLevelIterationTimeframe));
				binderRequest.setIsElectronicInd(BinderRequestResult.WITH_HUD_ELECTRONIC.equals(binderRequestResult) ? 'Y' : 'N');
				binderRequest.setCreatedBy(userId);
				binderRequest.setCreatedTs(now);
				binderRequest = binderRequestRepository.save(binderRequest);

				loanSelection.setLoanSelectionStatusRef(loanSelectionStatus);
				loanSelection.setRequestedDtTm(now);
				loanSelection.setUpdatedBy(userId);
				loanSelection.setUpdatedTs(now);
				loanSelection = loanSelectionRepository.save(loanSelection);

				if (BinderRequestResult.WITH_HUD_ELECTRONIC.equals(binderRequestResult)) {
					try {
						receiveBinder(binderRequest.getBinderRequestId(), loanSelectionCaseSummary);
					} catch (Exception e) {
						logger.error("Unable to automatically receive eCase binder for binder request " + binderRequest.getBinderRequestId() + ", (Case Number: " + binderRequest.getCaseNumber() + ")");
					}
				}

				// again, manual selections bypass throttling and do not count towards the daily total
				if (currentThrottling) {
					throttledCapacityRemainingByReviewLocationId.put(reviewLocation.getReviewLocationId(), throttledCapacityRemaining - 1);
				}
			} else if (BinderRequestResult.EXCEPTION.equals(binderRequestResult)) {
				logger.debug("\tError requesting binder, creating binder request exception");
				commonExceptionService.createBinderRequestErrorException(loanSelection);
			} else if (BinderRequestResult.RETRY.equals(binderRequestResult)) {
				// don't do anything, the loan selection should remain in DISTRTIBUTED so that it can be reprocessed
			} else {
				throw new RuntimeException("Unhandled binder request response status " + binderRequestResult + " for case number " + loanSelection.getCaseNumber());
			}
		}

		binderRequestRepository.flush();

		// update any batches in ASSIGNED state to REQUESTED if has outstanding binders
		BatchStatusRef requestedBatchStatus = batchStatusRefRepository.findByCode(BatchStatusRef.REQUESTED);
		for (Batch batch : batchRepository.findInAssignedStatusWithAnyBindersNotReceived()) {
			batch.setBatchStatusRef(requestedBatchStatus);
			batch.setUpdatedBy(userId);
			batch.setUpdatedTs(now);
			batch = batchRepository.save(batch);
		}

		logger.debug("Finished binder request job");

		MDC.remove("logFileName");
	}

	@Transactional
	public void checkBinderReceipts(Job job) {
		MDC.put("logFileName", "checkBinderReceipts-" + new SimpleDateFormat(JOB_LOG_FILE_FORMAT).format(new Date()) +  "-" + job.getJobId());
		logger.debug("Checking binder reciepts");

		List<BinderRequest> binderRequests = binderRequestRepository.findByBinderRequestStatusRefCodeAndReviewTypeNotIn(BinderRequestStatusCodes.REQUESTED, ReviewTypeCodes.SERVICING);
		List<String> binderRequestPastDueExceptionType = new ArrayList<String>(Arrays.asList(ReviewProcessExceptionTypeCodes.BINDER_REQUEST_PAST_DUE));
		List<ReviewProcessException> binderRequestsPastDue = reviewProcessExceptionRepository.findByReviewProcessExceptionTypeRefCodeInAndResolvedInd(binderRequestPastDueExceptionType, 'N');
		if (binderRequestsPastDue != null) {
			for (ReviewProcessException pastDueException : binderRequestsPastDue) {
				LoanSelection loanSelection = pastDueException.getLoanSelection();
				if (!ReviewTypeCodes.SERVICING.equals(loanSelection.getReviewTypeRef().getReviewTypeCd())) {
					binderRequests.addAll(loanSelection.getBinderRequests());
				}
			}
		}
		logger.debug("Found " + binderRequests.size() + " BinderRequests awaiting receipt");

		// Get the "RECEIVED" loan selection status ref object.
		LoanSelectionStatusRef receivedLoanSelectionStatus = loanSelectionStatusRefRepository.findByCode(LoanSelectionStatusCodes.RECEIVED);
		BinderRequestStatusRef receivedBinderRequestStatus = binderRequestStatusRefRepository.findByCode(BinderRequestStatusCodes.RECEIVED);

		String userId = securityService.getUserId();
		Date now = new Date();

		for (int start = 0; start < binderRequests.size(); start += MULE_BINDER_RECEIPT_BATCH_SIZE) {
			TransactionStatus transactionStatus = transactionManager.getTransaction(null);
			int end = Math.min(binderRequests.size(), start + MULE_BINDER_RECEIPT_BATCH_SIZE);
			List<BinderRequest> binderRequestsSubList = binderRequests.subList(start, end);

			Map<String, BinderRequest> binderRequestByCaseNumber = Maps.uniqueIndex(binderRequestsSubList, x -> StringFunctionsUtil.caseNumberTrim(x.getCaseNumber()));
			List<String> caseNumbers = new ArrayList<String>(binderRequestByCaseNumber.keySet());

			logger.debug("Calling mule to check case numbers: " + caseNumbers);

			List<CaseBinderDTO> caseBinderDTOs = null;
			try {
				caseBinderDTOs = binderRequestService.checkBindersReceipt(caseNumbers);
			} catch (Throwable t) {
				logger.debug("\tError checking binder receipts: " + t.getMessage());
				logger.debug("\tSkipping this case number batch");
				continue;
			}

			logger.debug("Received " + caseBinderDTOs.size() + " case binders");

			try {
				for (CaseBinderDTO caseBinderDTO : caseBinderDTOs) {
					logger.debug("\tCase number " + StringFunctionsUtil.caseNumberPad(caseBinderDTO.getCaseNumber()) + ": received on " + caseBinderDTO.getReceiveDate() + ", HOC " + caseBinderDTO.getHoc() + ", paper/ecase: " + caseBinderDTO.getEbinderCase());

					// check if the case has been received or not
					if (
						!caseBinderDTO.getReceiveDate().equals(CaseBinderDTO.RECEIVE_DATE_NOT_APPLICABLE) &&
						caseBinderDTO.getEbinderCase().equals(CaseBinderDTO.EBINDER_CASE_ELECTRONIC) // we only consume electronic binder receipts through this channel
					) {
						Date receivedDate = null;
						String receiveDateString = caseBinderDTO.getReceiveDate();
						if (receiveDateString != null && receiveDateString.length() == 8) {
							int year = Integer.valueOf(receiveDateString.substring(0, 4));
							int month = Integer.valueOf(receiveDateString.substring(4, 6));
							int day = Integer.valueOf(receiveDateString.substring(6, 8));
							receivedDate = DateUtils.convertDateToNoonUtcDate(year, month, day);
						} else {
							logger.debug("\t\tReceived invalid date string: " + caseBinderDTO.getReceiveDate() + ", skipping this case number");
							continue;
						}

						// If Received update the status of requested binder record.
						BinderRequest binderRequest = binderRequestByCaseNumber.get(StringFunctionsUtil.caseNumberTrim(caseBinderDTO.getCaseNumber()));
						if (binderRequest == null) {
							logger.debug("\t\tNo BinderRequest with case number " + StringFunctionsUtil.caseNumberPad(caseBinderDTO.getCaseNumber()) + "; skipping this result");
							continue;
						}

						LoanSelectionCaseSummary loanSelectionCaseSummary = updateLoanSelectionCaseSummary(binderRequest.getLoanSelection());
						if (loanSelectionCaseSummary == null) {
							logger.error("\t\tskipping this case number");
							continue;
						}

						binderRequest.setBinderRequestStatusRef(receivedBinderRequestStatus);
						binderRequest.setIsElectronicInd('Y');
						binderRequest.setUpdatedBy(userId);
						binderRequest.setUpdatedTs(now);
						binderRequest = binderRequestRepository.save(binderRequest);

						// If Received update the status of Loan selection.
						LoanSelection loanSelection = binderRequest.getLoanSelection();
						loanSelection.setLoanSelectionStatusRef(receivedLoanSelectionStatus);
						loanSelection.setReceivedDt(receivedDate);
						loanSelection.setUpdatedBy(userId);
						loanSelection.setUpdatedTs(now);
						loanSelection = loanSelectionRepository.save(loanSelection);

						// Create Review and Review level records.
						Review review = commonReviewService.createReview(loanSelection);
						ReviewLevel reviewLevel = review.getReviewLevels().iterator().next();

						// Resolve binder past due exception
						ReviewProcessException binderPastDueException =
							reviewProcessExceptionRepository.findByLoanSelectionSelectionIdAndResolvedInd(binderRequest.getLoanSelection().getSelectionId(), 'N');
						if (binderPastDueException != null) {
							commonExceptionService.markExceptionResolved(binderPastDueException);
						}

						List<Personnel> personnelList = assignmentService.runAssignmentModelForCurrentReviewLocation(reviewLevel);
						if (!personnelList.isEmpty()) {
							commonReviewService.assignReviewLevel(reviewLevel, personnelList.get(0));
						} else {
							commonExceptionService.createReviewLevelAssignmentException(reviewLevel);
						}
					}
				}

			} catch (Throwable t) {
				transactionManager.rollback(transactionStatus);
				throw t;
			}
			transactionManager.commit(transactionStatus);
		}

		TransactionStatus transactionStatus = transactionManager.getTransaction(null);

		// update any batches in binder request state to under review if all binders received
		BatchStatusRef underReviewBatchStatus = batchStatusRefRepository.findByCode(BatchStatusRef.UNDER_REVIEW);
		for (Batch batch : batchRepository.findInBinderRequestStatusWithAllBindersReceived()) {
			batch.setBatchStatusRef(underReviewBatchStatus);
			batch.setUpdatedBy(userId);
			batch.setUpdatedTs(now);
			batch = batchRepository.save(batch);
		}

		transactionManager.commit(transactionStatus);

		MDC.remove("logFileName");
	}

	@Transactional
	public BinderRequest receiveBinder(String binderRequestId, LoanSelectionCaseSummary updatedLoanSelectionCaseSummary) {
		BinderRequest binderRequest = binderRequestRepository.findOne(binderRequestId);
		if (binderRequest == null) {
			throw new NotFoundException("Binder " + binderRequestId + " is not found");
		}

		LoanSelectionCaseSummary loanSelectionCaseSummary = updatedLoanSelectionCaseSummary;
		if (loanSelectionCaseSummary == null) {
			loanSelectionCaseSummary = updateLoanSelectionCaseSummary(binderRequest.getLoanSelection());
			if (loanSelectionCaseSummary == null) {
				throw new ConflictException("Unable to update loan selection case summary for case number " + binderRequest.getCaseNumber());
			}
		}

		LoanSelectionStatusRef receivedLoanSelectionStatus = loanSelectionStatusRefRepository.findByCode(LoanSelectionStatusCodes.RECEIVED);
		BinderRequestStatusRef receivedBinderRequestStatus = binderRequestStatusRefRepository.findByCode(BinderRequestStatusCodes.RECEIVED);

		String userId = securityService.getUserId();
		Date now = new Date();

		LoanSelection loanSelection = binderRequest.getLoanSelection();
		loanSelection.setLoanSelectionCaseSummary(loanSelectionCaseSummary);
		loanSelection.setLoanSelectionStatusRef(receivedLoanSelectionStatus);
		loanSelection.setBorrName(loanSelectionCaseSummary.getBorr1Name());
		loanSelection.setCsEstabDt(loanSelectionCaseSummary.getCsEstabDt());
		loanSelection.setEndrsmntDt(loanSelectionCaseSummary.getEndrsmntDt());
		loanSelection.setPropAddr1(loanSelectionCaseSummary.getPropAddr1());
		loanSelection.setSelectionRequest(loanSelectionCaseSummary.getSelectionRequest());
		loanSelection.setReceivedDt(now);
		loanSelection.setUpdatedBy(userId);
		loanSelection.setUpdatedTs(now);
		loanSelection = loanSelectionRepository.save(loanSelection);

		binderRequest.setBinderRequestStatusRef(receivedBinderRequestStatus);
		binderRequest.setUpdatedTs(now);
		binderRequest = binderRequestRepository.save(binderRequest);

		Review review = commonReviewService.createReview(loanSelection);
		ReviewLevel reviewLevel = review.getReviewLevels().iterator().next();

		List<Personnel> personnel = assignmentService.runAssignmentModelForCurrentReviewLocation(reviewLevel);
		if (!personnel.isEmpty()) {
			commonReviewService.assignReviewLevel(reviewLevel, personnel.get(0));
		} else {
			commonExceptionService.createReviewLevelAssignmentException(reviewLevel);
		}

		// if this was the last outstanding binder request for this batch, then move the batch REQUESTED to UNDER_REVIEW
		Batch batch = loanSelection.getBatch();
		if ((batch != null) && (batch.getBatchStatusRef().getCode().equals(BatchStatusRef.REQUESTED))) {
			Integer outstandingBinderRequestCount = (Integer)entityManager
				.createNativeQuery(
					"SELECT COUNT(*) FROM BINDER_REQUEST BR " +
					"INNER JOIN BINDER_REQUEST_STATUS_REF BRS ON (BRS.BINDER_REQUEST_STATUS_ID = BR.BINDER_REQUEST_STATUS_ID) " +
					"INNER JOIN LOAN_SELECTION LS ON (LS.SELECTION_ID = BR.SELECTION_ID) " +
					"WHERE " +
						"(BRS.CODE <> 'RECEIVED') AND " +
						"(LS.BATCH_ID = :batchId) AND " +
						"(LS.SELECTION_ID <> :loanSelectionId) "
				)
				.setParameter("batchId", batch.getBatchId())
				.setParameter("loanSelectionId", loanSelection.getSelectionId())
				.getSingleResult()
			;

			if (outstandingBinderRequestCount.equals(0)) {
				batch.setBatchStatusRef(batchStatusRefRepository.findByCode(BatchStatusRef.UNDER_REVIEW));
				batch.setUpdatedBy(userId);
				batch.setUpdatedTs(now);
				batch = batchRepository.save(batch);
			}
		}

		ReviewProcessException reviewProcessException =
			reviewProcessExceptionRepository.findByLoanSelectionSelectionIdAndResolvedInd(binderRequest.getLoanSelection().getSelectionId(), 'N');
		if (reviewProcessException != null) {
			commonExceptionService.markExceptionResolved(reviewProcessException);
		}
		return binderRequest;
	}

	@Transactional
	public void handleLateBinders(Job job) {
		MDC.put("logFileName", "handleLateBinders-" + new SimpleDateFormat(JOB_LOG_FILE_FORMAT).format(new Date()) +  "-" + job.getJobId());

		logger.debug("Checking for late binder requests...");
		Date now = new Date();

		// get the expired binder requests that are not processed yet
		List<BinderRequest> binderRequests = binderRequestRepository.findByDueDateLessThanAndBinderRequestStatusRefCode(now, BinderRequestStatusCodes.REQUESTED);
		logger.debug("...found " + binderRequests.size() + " late binder requests");

		for (BinderRequest binderRequest : binderRequests) {
			logger.debug("\tLate binder for case number " + binderRequest.getLoanSelection().getCaseNumber() + " : was due on " + binderRequest.getDueDate());
			commonExceptionService.createBinderPastDueException(binderRequest);
		}

		logger.debug("Checking for batches with late operational review documents...");
		// get late operational doc requests
		List<Batch> batches = entityManager.createQuery(
			"select b from Batch b where" +
				"(b.requestOperationalDocumentsInd = 'Y') and " +
				"(operationalDocumentsDueDt < :now) and " +
				"(receivedOperationalDocumentsInd = 'N') ",
			Batch.class
		)
		.setParameter("now", now)
		.getResultList();
		logger.debug("...found " + batches.size() + " batches with late operational review documents");

		for (Batch batch : batches) {
			logger.debug("\tLate batch " + batch.getBatchId());
			batch.setReceivedOperationalDocumentsInd('Y');
			Review review = commonReviewService.createOperationalReview(batch);
			commonReviewService.assignReviewLevel(review.getReviewLevels().iterator().next(), batch.getOwnerPersonnel());
		}

		logger.debug("Done processing late binders");

		MDC.remove("logFileName");
	}

	private LoanSelectionCaseSummary updateLoanSelectionCaseSummary(LoanSelection loanSelection) {
		LoanSelectionCaseSummary loanSelectionCaseSummary = null;
		try {
			LoanSelectionCaseSummary updatedLoanSelectionCaseSummary = caseUniverseService.getLoanSelectionCaseSummary(loanSelection.getCaseNumber());
			loanSelectionCaseSummary = commonLoanSelectionService.cloneLoanSelectionCaseSummary(loanSelection.getLoanSelectionCaseSummary(), updatedLoanSelectionCaseSummary);
			String userId = securityService.getUserId();
			Date now = new Date();
			loanSelectionCaseSummary.setUpdatedBy(userId);
			loanSelectionCaseSummary.setUpdatedTs(now);
			loanSelectionCaseSummaryRepository.save(loanSelectionCaseSummary);
			logger.debug("Updated loan selection case summary for case number " + loanSelection.getCaseNumber() + " during binder request/receive");
		} catch (Exception e) {
			logger.error("Unable to update loan selection case summary for case number " + loanSelection.getCaseNumber() + " during binder request/receive");
		}
		return loanSelectionCaseSummary;
	}

}