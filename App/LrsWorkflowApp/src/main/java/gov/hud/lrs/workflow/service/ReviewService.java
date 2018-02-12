package gov.hud.lrs.workflow.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.process.WorkflowProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ImmutableList;

import gov.hud.lrs.common.entity.Batch;
import gov.hud.lrs.common.entity.BatchStatusRef;
import gov.hud.lrs.common.entity.Indemnification;
import gov.hud.lrs.common.entity.IndemnificationTypeRef;
import gov.hud.lrs.common.entity.Job;
import gov.hud.lrs.common.entity.Lender;
import gov.hud.lrs.common.entity.LenderRequest;
import gov.hud.lrs.common.entity.Personnel;
import gov.hud.lrs.common.entity.QcOutcomeRef;
import gov.hud.lrs.common.entity.RatingRef;
import gov.hud.lrs.common.entity.Review;
import gov.hud.lrs.common.entity.ReviewLevel;
import gov.hud.lrs.common.entity.ReviewLevelTypeRef;
import gov.hud.lrs.common.entity.ReviewLocation;
import gov.hud.lrs.common.entity.ReviewProcessException;
import gov.hud.lrs.common.entity.RvwLvlFinding;
import gov.hud.lrs.common.enumeration.LenderRequestStatusCodes;
import gov.hud.lrs.common.enumeration.RatingCodes;
import gov.hud.lrs.common.enumeration.ReviewLevelStatusCodes;
import gov.hud.lrs.common.enumeration.ReviewLevelTypeCodes;
import gov.hud.lrs.common.enumeration.ReviewProcessExceptionTypeCodes;
import gov.hud.lrs.common.enumeration.ReviewStatusCodes;
import gov.hud.lrs.common.enumeration.ReviewTypeCodes;
import gov.hud.lrs.common.enumeration.SelectionReasonCodes;
import gov.hud.lrs.common.exception.BadRequestException;
import gov.hud.lrs.common.exception.ConflictException;
import gov.hud.lrs.common.exception.NotFoundException;
import gov.hud.lrs.common.repository.BatchRepository;
import gov.hud.lrs.common.repository.BatchStatusRefRepository;
import gov.hud.lrs.common.repository.IndemnificationRepository;
import gov.hud.lrs.common.repository.IndemnificationTypeRefRepository;
import gov.hud.lrs.common.repository.LenderRequestRepository;
import gov.hud.lrs.common.repository.LenderRequestStatusRefRepository;
import gov.hud.lrs.common.repository.PersonnelRepository;
import gov.hud.lrs.common.repository.QcOutcomeRefRepository;
import gov.hud.lrs.common.repository.RatingRefRepository;
import gov.hud.lrs.common.repository.ReviewLevelRepository;
import gov.hud.lrs.common.repository.ReviewLevelStatusRefRepository;
import gov.hud.lrs.common.repository.ReviewLevelTypeRefRepository;
import gov.hud.lrs.common.repository.ReviewLocationRepository;
import gov.hud.lrs.common.repository.ReviewProcessExceptionRepository;
import gov.hud.lrs.common.repository.ReviewRepository;
import gov.hud.lrs.common.repository.ReviewStatusRefRepository;
import gov.hud.lrs.common.security.SecurityService;
import gov.hud.lrs.common.service.CommonExceptionService;
import gov.hud.lrs.common.service.CommonReviewService;
import gov.hud.lrs.common.service.DocumentTemplateService;
import gov.hud.lrs.common.service.IndemnificationService;
import gov.hud.lrs.common.service.LenderService;

@Service
public class ReviewService {

	private Logger logger = LoggerFactory.getLogger(ReviewService.class);
	private final String JOB_LOG_FILE_FORMAT = "yyyy.MM.dd-HH.mm.ss";

	@Autowired private BatchRepository batchRepository;
	@Autowired private BatchStatusRefRepository batchStatusRefRepository;
	@Autowired private IndemnificationRepository indemnificationRepository;
	@Autowired private IndemnificationTypeRefRepository indemnificationTypeRefRepository;
	@Autowired private LenderRequestRepository lenderRequestRepository;
	@Autowired private LenderRequestStatusRefRepository lenderRequestStatusRefRepository;
	@Autowired private PersonnelRepository personnelRepository;
	@Autowired private QcOutcomeRefRepository qcOutcomeRefRepository;
	@Autowired private RatingRefRepository ratingRefRepository;
	@Autowired private ReviewProcessExceptionRepository reviewProcessExceptionRepository;
	@Autowired private ReviewStatusRefRepository reviewStatusRefRepository;
	@Autowired private ReviewLevelRepository reviewLevelRepository;
	@Autowired private ReviewLevelTypeRefRepository reviewLevelTypeRefRepository;
	@Autowired private ReviewLevelStatusRefRepository reviewLevelStatusRefRepository;
	@Autowired private ReviewLocationRepository reviewLocationRepository;
	@Autowired private ReviewRepository reviewRepository;

	@Autowired private AssignmentService assignmentService;
	@Autowired private CommonExceptionService commonExceptionService;
	@Autowired private CommonReviewService commonReviewService;
	@Autowired private DocumentTemplateService documentTemplateService;
	@Autowired private IndemnificationService indemnificationService;
	@Autowired private LenderService lenderService;
	@Autowired private SecurityService securityService;

	@Autowired private KieSession kieSession;

	public void submitReviewLevelWorkflow(String reviewLevelId, String indemnificationTypeCode) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("reviewLevelId", reviewLevelId);
		parameters.put("indemnificationTypeCode", indemnificationTypeCode);

		WorkflowProcessInstance processInstance;
		processInstance = (WorkflowProcessInstance)kieSession.startProcess("gov.hud.lrs.ReviewProcess", parameters);
		if (processInstance == null) {
			throw new RuntimeException("Couldn't create ReviewProcess for ReviewLevel " + reviewLevelId);
		}

		kieSession.signalEvent("SubmitReviewLevel", reviewLevelId, processInstance.getId());
	}

	@Transactional
	public void submitReviewLevel(String reviewLevelId, String indemnificationTypeCode, String qcOutcomeCode) {
		ReviewLevel reviewLevel = reviewLevelRepository.findOne(reviewLevelId);
		if (reviewLevel == null) {
			throw new NotFoundException("ReviewLevel " + reviewLevelId + " doesn't exist");
		}
		if (!reviewLevel.getReviewLevelStatusRef().getCode().equals(ReviewLevelStatusCodes.IN_PROGRESS)) {
			throw new ConflictException("ReviewLevel " + reviewLevel.getReviewLevelId() + " must have status IN_PROGRESS (is currently " + reviewLevel.getReviewLevelStatusRef().getCode() + ")");
		}

		Review review = reviewLevel.getReview();

		IndemnificationTypeRef indemnificationType = indemnificationTypeRefRepository.findByCode(indemnificationTypeCode);

		if ((reviewLevel.getRatingRef().getCode().equals(RatingCodes.UNACCEPTABLE) && indemnificationType == null) && !review.getReviewTypeRef().getReviewTypeCd().equals(ReviewTypeCodes.OPERATIONAL)) {
			throw new BadRequestException("Unknown IndemnificationTypeRef: " + indemnificationTypeCode);
		}

		QcOutcomeRef qcOutcome = null;
		if (qcOutcomeCode != null) {
			qcOutcome = qcOutcomeRefRepository.findByCode(qcOutcomeCode);
			if (qcOutcome == null) {
				throw new BadRequestException("Unknown QcOutcomeRef code: " + qcOutcomeCode);
			}
		}

		// vetting
		Personnel vetter = securityService.getPersonnel().getVettingPersonnel();
		if ((vetter != null) && (reviewLevel.getVettingInd() == 'N')) {	// make sure we don't vet a vetting review level
			commonReviewService.completeReviewLevel(reviewLevel, indemnificationType, qcOutcome);

			ReviewLevel newReviewLevel = commonReviewService.createVettingReviewLevel(reviewLevel);

			commonReviewService.assignReviewLevel(newReviewLevel, vetter);

			// vetting reviews start immediately (UNDER_REVIEW) because it's possible that there's no "actions" that need to be taken by the vetter
			// that would flip the status from ASSIGNED to UNDER_REVIEW
			// having status UNDER_REVIEW is required to submit a review level
			review.setReviewStatusRef(reviewStatusRefRepository.findByCode(ReviewStatusCodes.UNDER_REVIEW));
			newReviewLevel.setReviewLevelStatusRef(reviewLevelStatusRefRepository.findByCode(ReviewLevelStatusCodes.IN_PROGRESS));
			newReviewLevel.setRvwStartedDtTm(new Date());

			return;
		}

		Batch batch = review.getBatch();
		if ((batch != null) && !review.getReviewTypeRef().getReviewTypeCd().equals(ReviewTypeCodes.OPERATIONAL)) {
			logger.debug("ReviewLevel " + reviewLevelId + ": part of batch, assigning to batch owner and marking as PENDING_BATCH_REVIEW");

			reviewLevel.setIndemnificationTypeRef(indemnificationType);
			Personnel batchOwner = batch.getOwnerPersonnel();
			commonReviewService.assignReviewLevel(reviewLevel, batchOwner);

			// we must do this *after* assigning the review level, since assignReviewLevel sets the status to ASSIGNED
			reviewLevel.setReviewLevelStatusRef(reviewLevelStatusRefRepository.findByCode(ReviewLevelStatusCodes.PENDING_BATCH_REVIEW));
			reviewLevelRepository.flush();

			// TODO: race condition here: rare, but could force a commit after the review status update to avoid
			if (commonReviewService.isBatchReadyForReview(batch) && (batchRepository.findActiveReviewLevelCountByBatchIdAndReviewIdNot(batch.getBatchId(), review.getReviewId()) == 0)) {
				logger.debug("ReviewLevel " + reviewLevelId + ": all review levels in batch are done, marking batch as UNDER_BATCH_REVIEW");
				batch.setBatchStatusRef(batchStatusRefRepository.findByCode(BatchStatusRef.UNDER_BATCH_REVIEW));
			}

			return;
		}

		commonReviewService.completeReviewLevel(reviewLevel, indemnificationType, qcOutcome);

		// qc (initial)
		String reviewLevelTypeCode = reviewLevel.getReviewLevelTypeRef().getReviewLevelCd();
		String selectionReasonCode = review.getSelectionReason().getCode();
		if (
			reviewLevelTypeCode.equals(ReviewLevelTypeCodes.INITIAL) &&
			(selectionReasonCode.equals(SelectionReasonCodes.REVIEW_LOCATION_QC) || selectionReasonCode.equals(SelectionReasonCodes.NATIONAL_QC))
		) {
			ReviewLevel newReviewLevel = commonReviewService.createEscalatedReviewLevel(reviewLevel);
			commonReviewService.assignReviewLevel(newReviewLevel, reviewLevel.getReviewerPersonnel());

			// escalated qc should start immediately (UNDER_REVIEW) because it's possible that there's no "actions" that need to be taken
			// that would flip the status from ASSIGNED to UNDER_REVIEW
			// having status UNDER_REVIEW is required to submit a review level
			review.setReviewStatusRef(reviewStatusRefRepository.findByCode(ReviewStatusCodes.UNDER_REVIEW));
			newReviewLevel.setReviewLevelStatusRef(reviewLevelStatusRefRepository.findByCode(ReviewLevelStatusCodes.IN_PROGRESS));
			newReviewLevel.setRvwStartedDtTm(new Date());

			return;
		}

		// regular review, no special case
		String reviewLevelRatingCode = reviewLevel.getRatingRef().getCode();
		if (ImmutableList.of(RatingCodes.CONFORMING, RatingCodes.DEFICIENT, RatingCodes.MITIGATED, RatingCodes.REMEDIATED).contains(reviewLevelRatingCode)) {
			logger.debug("ReviewLevel " + reviewLevel.getReviewLevelId() + " (Case: " + review.getCaseNumber() + ") rating is acceptable; completing review");
			commonReviewService.completeReview(review);

		} else if (reviewLevelRatingCode.equals(RatingCodes.UNACCEPTABLE)) {
			logger.debug("ReviewLevel " + reviewLevel.getReviewLevelId() + " (Case: " + review.getCaseNumber() + ") rating is unacceptable");

			if (
				selectionReasonCode.equals(SelectionReasonCodes.REVIEW_LOCATION_QC) ||
				selectionReasonCode.equals(SelectionReasonCodes.NATIONAL_QC)
			) {
				Lender lender = lenderService.getLender(review.getLender().getLenderId());
				if (lender.getActiveInd() == ('N')) {
					logger.debug("QC review: lender is not active anymore");
					commonExceptionService.createHqEscalationException(reviewLevel);
					return;
				}

				Review qcReview = review.getQcReview();
				if (qcReview != null && (qcReview.getMrbReferralInd() == ('Y') || qcReview.getManagementDecisionInd() == ('Y'))) {
					logger.debug("previous review ended in MRB Referral or Management Decision");
					commonReviewService.completeReviewLevel(reviewLevel);
					commonReviewService.completeReview(review);
					return;
				}
			}

			if (commonReviewService.hasAttemptsRemaining(reviewLevel)) {
				logger.debug("ReviewLevel " + reviewLevel.getReviewLevelId() + " (Case: " + review.getCaseNumber() + ") has attempts remaining; creating LenderRequest");
				commonReviewService.createLenderRequest(reviewLevel);

			} else if (
				reviewLevelTypeCode.equals(ReviewLevelTypeCodes.MITIGATION) ||
				reviewLevelTypeCode.equals(ReviewLevelTypeCodes.ESCALATION)
			) {
				logger.debug("Escalating ReviewLevel " + reviewLevel.getReviewLevelId() + " (Case: " + review.getCaseNumber() + "); creating new ReviewLevel");
				ReviewLevel newReviewLevel = commonReviewService.createEscalatedReviewLevel(reviewLevel);
				scoreAndAssignReviewLevel(newReviewLevel);

			} else if (reviewLevelTypeCode.equals(ReviewLevelTypeCodes.HQ_ESCALATION)) {
				logger.debug("ReviewLevel " + reviewLevel.getReviewLevelId() + " (Case: " + review.getCaseNumber() + ") is out of attempts at HQES; creating Exception");
				commonExceptionService.createHqEscalationException(reviewLevel);

			} else {
				throw new RuntimeException("Unhandled ReviewLevelType " + reviewLevel.getReviewLevelTypeRef().getReviewLevelCd() + " (Case: " + review.getCaseNumber() + ") for ReviewLevel " + reviewLevel.getReviewLevelId());
			}

		} else {
			throw new RuntimeException("Unhandled Rating code " + reviewLevel.getRatingRef().getCode() + " (Case: " + review.getCaseNumber() + ") for ReviewLevel " + reviewLevel.getReviewLevelId());
		}
	}

	@Transactional
	public void submitLenderResponse(String reviewLevelId) {
		LenderRequest lenderRequest = lenderRequestRepository.findByReviewLevelReviewLevelId(reviewLevelId);
		if (lenderRequest == null) {
			throw new NotFoundException("No LenderRequest for reviewLevelId " + reviewLevelId);
		}
		if (!lenderRequest.getLenderRequestStatusRef().getCode().equals(LenderRequestStatusCodes.IN_PROGRESS)) {
			throw new ConflictException("LenderRequest " + lenderRequest.getLenderRequestId() + " must have status IN_PROGRESS (currently " + lenderRequest.getLenderRequestStatusRef().getCode() + ")");
		}

		ReviewLevel reviewLevel = lenderRequest.getReviewLevel();
		Review review = reviewLevel.getReview();
		if ((review.getBatch() != null) && !review.getReviewTypeRef().getReviewTypeCd().equals(ReviewTypeCodes.OPERATIONAL)) {
			throw new ConflictException("LenderRequest " + lenderRequest.getLenderRequestId() + " is part of a batch; cannot submit individually");
		}
		review.setLastLenderName(securityService.getFullName());
		reviewRepository.save(review);

		commonReviewService.completeLenderRequest(lenderRequest);

		if ((reviewLevel.getVettingInd() == 'Y') && (reviewLevel.getVetteeAcknowledgementDt() == null)) {
			commonExceptionService.createVettingConfirmationException(reviewLevel);
		} else {
			// note that it's impossible to escalate from lender submit, since there will always be "one more" review level remaining
			ReviewLevel newReviewLevel = commonReviewService.createNextIterationReviewLevel(reviewLevel);
			scoreAndAssignReviewLevel(newReviewLevel);
		}
	}

	@Transactional
	public void submitBatch(String batchId) {
		Batch batch = batchRepository.findOne(batchId);
		if (batch == null) {
			throw new NotFoundException("No Batch for batchId " + batchId);
		}
		logger.debug("Submitting batch for " + batch.getBatchReferenceId());
		if (!batch.getBatchStatusRef().getCode().equals(BatchStatusRef.UNDER_BATCH_REVIEW)) {
			throw new ConflictException("Batch " + batch.getBatchReferenceId() + " doesn't have status " + BatchStatusRef.UNDER_BATCH_REVIEW);
		}		
		if (!commonReviewService.isBatchReadyForReview(batch)) {
			throw new ConflictException("Batch " + batch.getBatchReferenceId() + " does not have all reviews created yet");
		}

		String userId = securityService.getUserId();
		Date now = new Date();

		boolean anyPendingLenderResponse = false;
		ReviewLevelTypeRef escalatedReviewLevelType = null;

		for (Review review : batch.getReviews()) {
			if (
				review.getReviewStatusRef().getCode().equals(ReviewStatusCodes.COMPLETED) ||
				review.getReviewStatusRef().getCode().equals(ReviewStatusCodes.CANCELLED) ||
				review.getReviewTypeRef().getReviewTypeCd().equals(ReviewTypeCodes.OPERATIONAL)
			) {
				// skip completed/cancelled/operational reviews
				continue;
			}

			// PERF TODO: move out of loop and index
			ReviewLevel reviewLevel = reviewLevelRepository.findActiveByReviewId(review.getReviewId());
			if (
				!reviewLevel.getReviewLevelTypeRef().getReviewLevelTypeId().equals(batch.getReviewLevelTypeRef().getReviewLevelTypeId()) ||
				(reviewLevel.getIterationNumber() != batch.getIterationNumber())
			) {
				logger.debug("Review " + review.getReviewId() + " is on a different reivew level (" + reviewLevel.getReviewLevelTypeRef().getDescription() + "/" + reviewLevel.getIterationNumber() + ") than batch; skipping");
				continue;
			}
			if (!reviewLevel.getReviewLevelStatusRef().getCode().equals(ReviewLevelStatusCodes.PENDING_BATCH_REVIEW)) {
				// just a sanity check, this state should never happen with a review level that's in-sync with the batch
				throw new ConflictException("ReviewLevel " + reviewLevel.getReviewLevelId() + " doesn't have status " + ReviewLevelStatusCodes.PENDING_BATCH_REVIEW);
			}

			commonReviewService.completeReviewLevel(reviewLevel);
			
			// Switch back to the review level personnel with original reviewer of the Review level (if available)
			if (reviewLevel.getOriginalReviewerPersonnel() != null) {
				reviewLevel.setReviewerPersonnel(reviewLevel.getOriginalReviewerPersonnel());
				reviewLevel.setReviewLocation(reviewLevel.getOriginalReviewerPersonnel().getReviewLocation());
			}

			reviewLevel = reviewLevelRepository.save(reviewLevel);
			
			String reviewLevelRatingCode = reviewLevel.getRatingRef().getCode();
			if (
				reviewLevelRatingCode.equals(RatingCodes.CONFORMING) ||
				reviewLevelRatingCode.equals(RatingCodes.DEFICIENT) ||
				reviewLevelRatingCode.equals(RatingCodes.MITIGATED) ||
				reviewLevelRatingCode.equals(RatingCodes.REMEDIATED)
			) {
				logger.debug("ReviewLevel " + reviewLevel.getReviewLevelId() + " (Case: " + review.getCaseNumber() + ") rating is acceptable; closing out review");
				commonReviewService.completeReview(review);

			} else if (reviewLevelRatingCode.equals(RatingCodes.UNACCEPTABLE)) {
				logger.debug("ReviewLevel " + reviewLevel.getReviewLevelId() + " (Case: " + review.getCaseNumber() + ") rating is unacceptable");

				String reviewLevelTypeCode = reviewLevel.getReviewLevelTypeRef().getReviewLevelCd();

				if (commonReviewService.hasAttemptsRemaining(reviewLevel)) {
					logger.debug("ReviewLevel " + reviewLevel.getReviewLevelId() + " (Case: " + review.getCaseNumber() + ") has attempts remaining; creating LenderRequest");
					commonReviewService.createLenderRequest(reviewLevel);
					anyPendingLenderResponse = true;

				} else if (
					reviewLevelTypeCode.equals(ReviewLevelTypeCodes.MITIGATION) ||
					reviewLevelTypeCode.equals(ReviewLevelTypeCodes.ESCALATION)
				) {
					logger.debug("Escalating ReviewLevel " + reviewLevel.getReviewLevelId() + " (Case: " + review.getCaseNumber() + "); creating new ReviewLevel");
					ReviewLevel newReviewLevel = commonReviewService.createEscalatedReviewLevel(reviewLevel);
					scoreAndAssignReviewLevel(newReviewLevel);
					escalatedReviewLevelType = newReviewLevel.getReviewLevelTypeRef();

				} else if (reviewLevelTypeCode.equals(ReviewLevelTypeCodes.HQ_ESCALATION)) {
					logger.debug("ReviewLevel " + reviewLevel.getReviewLevelId() + " (Case: " + review.getCaseNumber() + ") is out of attempts at HQES; creating Exception");
					commonExceptionService.createHqEscalationException(reviewLevel);
					// we treat this as a terminal state

				} else {
					throw new RuntimeException("Unhandled ReviewLevelType " + reviewLevel.getReviewLevelTypeRef().getReviewLevelCd() + " (Case: " + review.getCaseNumber() + ") for ReviewLevel " + reviewLevel.getReviewLevelId());
				}

			} else {
				throw new RuntimeException("Unhandled Rating " + reviewLevel.getRatingRef().getCode() + " (Case: " + review.getCaseNumber() + ") for ReviewLevel " + reviewLevel.getReviewLevelId());
			}
		}

		// sanity check: only one of these should ever be true
		if (anyPendingLenderResponse && (escalatedReviewLevelType != null)) {
			throw new RuntimeException("Batch " + batch.getBatchReferenceId() + ": not all reviews went to the same next step; this should never happen");
		}

		if (anyPendingLenderResponse) {
			logger.debug("At least one review in batch " + batch.getBatchReferenceId() + " had unacceptable findings, moving to lender response");
			batch.setBatchStatusRef(batchStatusRefRepository.findByCode(BatchStatusRef.PENDING_LENDER_RESPONSE));

		} else if (escalatedReviewLevelType != null) {
			logger.debug("At least one review in batch " + batch.getBatchReferenceId() + " had was escalated, moving batch to " + escalatedReviewLevelType.getDescription());
			batch.setReviewLevelTypeRef(escalatedReviewLevelType);
			batch.setIterationNumber((short)1);
			batch.setBatchStatusRef(batchStatusRefRepository.findByCode(BatchStatusRef.UNDER_REVIEW));
			//assign batch ower to current batch owner's supervisor
			Personnel batchOwnerSupervisor = batch.getOwnerPersonnel().getReportsToPersonnel();
			if (batchOwnerSupervisor != null) {
				batch.setOwnerPersonnel(batchOwnerSupervisor);
			}

		} else {
			logger.debug("All reviews in batch " + batch.getBatchReferenceId() + " completed, closing out batch");
			batch.setBatchStatusRef(batchStatusRefRepository.findByCode(BatchStatusRef.COMPLETED));
		}
		batch.setUpdatedBy(userId);
		batch.setUpdatedTs(now);
		batch = batchRepository.save(batch);
	}

	@Transactional
	public void submitLenderBatch(String batchId) {
		Batch batch = batchRepository.findOne(batchId);
		if (batch == null) {
			throw new NotFoundException("Batch " + batchId + " doesn't exist");
		}
		if (!batch.getBatchStatusRef().getCode().equals(BatchStatusRef.PENDING_LENDER_RESPONSE)) {
			throw new ConflictException("Batch " + batch.getBatchReferenceId() + " doesn't have status " + BatchStatusRef.PENDING_LENDER_RESPONSE);
		}

		Date now = new Date();
		String userId = securityService.getUserId();

		ReviewLevelTypeRef newReviewLevelType = null;
		Short newIterationNumber = null;
		for (Review review : batch.getReviews()) {
			if (
				review.getReviewStatusRef().getCode().equals(ReviewStatusCodes.COMPLETED) ||
				review.getReviewStatusRef().getCode().equals(ReviewStatusCodes.CANCELLED) ||
				review.getReviewTypeRef().getReviewTypeCd().equals(ReviewTypeCodes.OPERATIONAL)
			) {
				// skip completed/cancelled/operational reviews
				continue;
			}

			// PERF TODO: move out of loop and index
			ReviewLevel reviewLevel = reviewLevelRepository.findCurrentByReviewId(review.getReviewId());
			if (
				!reviewLevel.getReviewLevelTypeRef().getReviewLevelTypeId().equals(batch.getReviewLevelTypeRef().getReviewLevelTypeId()) ||
				(reviewLevel.getIterationNumber() != batch.getIterationNumber())
			) {
				logger.debug("Review " + review.getReviewId() + " is on a different reivew level (" + reviewLevel.getReviewLevelTypeRef().getDescription() + "/" + reviewLevel.getIterationNumber() + ") than batch; skipping");
				continue;
			}

			// PERF TODO: move out of loop and index
			LenderRequest lenderRequest = lenderRequestRepository.findActiveByReviewId(review.getReviewId());
			if (!lenderRequest.getLenderRequestStatusRef().getCode().equals(LenderRequestStatusCodes.IN_PROGRESS)) {
				// sanity check, should never happen
				throw new RuntimeException("LenderRequest " + lenderRequest.getLenderRequestId() + " doesn't have status IN_PROGRESS (currently " + lenderRequest.getLenderRequestStatusRef().getCode() + ")");
			}

			commonReviewService.completeLenderRequest(lenderRequest);

			if ((reviewLevel.getVettingInd() == 'Y') && (reviewLevel.getVetteeAcknowledgementDt() == null)) {
				commonExceptionService.createVettingConfirmationException(reviewLevel);
			} else {
				ReviewLevel newReviewLevel = commonReviewService.createNextIterationReviewLevel(lenderRequest.getReviewLevel());
				scoreAndAssignReviewLevel(newReviewLevel);

				newReviewLevelType = newReviewLevel.getReviewLevelTypeRef();
				newIterationNumber = newReviewLevel.getIterationNumber();
			}
		}

		// if all reviews were vetting and pending acknowledgement, then no new review levels will actually be created, so this check is needed
		if (newReviewLevelType != null) {
			batch.setReviewLevelTypeRef(newReviewLevelType);
			batch.setIterationNumber(newIterationNumber);
		}
		batch.setBatchStatusRef(batchStatusRefRepository.findByCode(BatchStatusRef.UNDER_REVIEW));
		batch.setUpdatedBy(userId);
		batch.setUpdatedTs(now);
	}

	@Transactional
	public void expireLenderRequests(Job job) {
		MDC.put("logFileName", "expireLenderRequests-" + new SimpleDateFormat(JOB_LOG_FILE_FORMAT).format(new Date()) +  "-" + job.getJobId());

		logger.debug("Loading late lender requests...");

		Date now = new Date();

		List<LenderRequest> expiredLenderRequests = lenderRequestRepository.findByDueDateLessThanAndLenderRequestStatusRefCode(now, LenderRequestStatusCodes.IN_PROGRESS);

		logger.debug("...found " + expiredLenderRequests.size() + " late lender requests");

		String userId = securityService.getUserId();

		BatchStatusRef underReviewBatchStatus = batchStatusRefRepository.findByCode(BatchStatusRef.UNDER_BATCH_REVIEW);

		for (LenderRequest lenderRequest : expiredLenderRequests) {

			// TODO: load out of loop and index
			ReviewLevel reviewLevel = lenderRequest.getReviewLevel();
			Review review = reviewLevel.getReview();

			logger.debug("Expiring lender request " + lenderRequest.getLenderRequestId() + " (Case: " + review.getCaseNumber() + ")");

			lenderRequest.setLenderRequestStatusRef(lenderRequestStatusRefRepository.findByCode(LenderRequestStatusCodes.EXPIRED));
			lenderRequest.setUpdatedBy(userId);
			lenderRequest.setUpdatedTs(now);

			review.setReviewStatusRef(reviewStatusRefRepository.findByCode(ReviewStatusCodes.AWAITING_ASSIGNMENT));
			review.setUpdatedBy(userId);
			review.setUpdatedTs(now);

			String reviewLevelTypeCode = reviewLevel.getReviewLevelTypeRef().getReviewLevelCd();
			ReviewLevel newReviewLevel = null;
			if (reviewLevelTypeCode.equals(ReviewLevelTypeCodes.INITIAL)) {
				newReviewLevel = commonReviewService.createEscalatedReviewLevel(reviewLevel);
			} else {
				newReviewLevel = commonReviewService.createNextIterationReviewLevel(reviewLevel);
			}

			Batch batch = review.getBatch();
			if ((batch != null) && !review.getReviewTypeRef().getReviewTypeCd().equals(ReviewTypeCodes.OPERATIONAL)) {
				logger.debug("Part of batch, assigning to batch owner");
				// expired batch reviews are special: they *always* go straight to the batch owner, never back to the lender, never to one of the batch reviewers
				commonReviewService.assignReviewLevel(newReviewLevel, batch.getOwnerPersonnel());
				newReviewLevel.setReviewLevelStatusRef(reviewLevelStatusRefRepository.findByCode(ReviewLevelStatusCodes.PENDING_BATCH_REVIEW));

				// we assume that if *any* lender request has expired for a given batch, *all* lender requests have expired for that batch
				batch.setBatchStatusRef(underReviewBatchStatus);
				batch.setReviewLevelTypeRef(newReviewLevel.getReviewLevelTypeRef());
				batch.setIterationNumber(newReviewLevel.getIterationNumber());

			} else if (reviewLevelTypeCode.equals(ReviewLevelTypeCodes.INITIAL)) {
				// New escalation review level always has lender iteration left. Create lender response request.
				newReviewLevel.setReviewerPersonnel(reviewLevel.getReviewerPersonnel());
				newReviewLevel.setAssignDt(new Date());
				commonReviewService.completeReviewLevel(newReviewLevel);
				commonReviewService.createLenderRequest(newReviewLevel);
			} else {
				scoreAndAssignReviewLevel(newReviewLevel);
			}
		}

		logger.debug("Done expiring lender requests");
		MDC.remove("logFileName");
	}

	// personnelId is optional: if provided, tries to assign to that person, if null, chooses using assignment scoring model
	@Transactional
	public Personnel reassign(String reviewLevelId, String personnelId, String reassignmentReasonCode) {
		ReviewLevel reviewLevel = reviewLevelRepository.findOne(reviewLevelId);
		if (reviewLevel == null) {
			throw new RuntimeException("Review Level ID " + reviewLevelId + "not found");
		}

		List<Personnel> personnelList = null;
		List<Personnel> excludePersonnelList = null;
		if (personnelId != null) {
			Personnel personnel = personnelRepository.findOne(personnelId);
			if (personnel == null) {
				throw new BadRequestException("No Personnel with personnelId " + personnelId);
			}

			personnelList = new ArrayList<Personnel>(1);
			personnelList.add(personnel);

			logger.debug("Attempting to reassign ReviewLevel " + reviewLevelId + " to " + personnel.getPersonnelId());
		} else {
			Personnel excludePersonnel = reviewLevel.getReviewerPersonnel();
			logger.debug("Attempting to reassign ReviewLevel " + reviewLevelId + " with no specific person specified (excluding self " + excludePersonnel.getPersonnelId() + ")");
			excludePersonnelList = new ArrayList<Personnel>(1);
			excludePersonnelList.add(excludePersonnel);
		}

		if (reviewLevel.getReviewerPersonnel() == null) {
			throw new BadRequestException("No Personnel with personnelId " + personnelId);
		}

		List<Personnel> eligiblePersonnel = assignmentService.runAssignmentModel(reviewLevel, personnelList, excludePersonnelList, true);
		if (eligiblePersonnel.isEmpty()) {
			logger.debug("Assignment model failed, creating exception");
			commonExceptionService.createReviewLevelAssignmentException(reviewLevel);
			return null;
		}

		Personnel selectedPersonnel = eligiblePersonnel.get(0);
		logger.debug("Assignment model passed, assigning to " + selectedPersonnel.getPersonnelId());
		commonReviewService.reassignReviewLevel(reviewLevel, selectedPersonnel, reassignmentReasonCode, true);

		return selectedPersonnel;
	}

	@Transactional
	public void lenderIndemnification(String reviewLevelId) {
		LenderRequest lenderRequest = lenderRequestRepository.findByReviewLevelReviewLevelId(reviewLevelId);
		if (lenderRequest == null) {
			throw new NotFoundException("No LenderRequest for reviewLevelId " + reviewLevelId);
		}
		if (!lenderRequest.getLenderRequestStatusRef().getCode().equals(LenderRequestStatusCodes.IN_PROGRESS)) {
			throw new ConflictException("LenderRequest " + lenderRequest.getLenderRequestId() + " must have status IN_PROGRESS (currently " + lenderRequest.getLenderRequestStatusRef().getCode() + ")");
		}

		ReviewLevel reviewLevel = lenderRequest.getReviewLevel();

		commonReviewService.completeLenderRequest(lenderRequest);

		ReviewLevel newReviewLevel = commonReviewService.createIndemnificationReviewLevel(
			reviewLevel,
			reviewLevelTypeRefRepository.findByReviewLevelCd(ReviewLevelTypeCodes.INDEMNIFICATION),
			reviewLevel.getIndemnificationTypeRef()
		);

		scoreAndAssignReviewLevel(newReviewLevel);

		Indemnification indemnification = indemnificationService.createIndemnification(newReviewLevel);

		indemnificationService.lenderSignIndemnification(indemnification);
	}

	@Transactional
	public void forceIndemnification(String reviewId, String indemnificationTypeCode) {
		ReviewLevel reviewLevel = reviewLevelRepository.findActiveOrExceptionByReviewId(reviewId);
		if (reviewLevel == null) {
			throw new NotFoundException("No active review level found for review: " + reviewId);
		}
		if (
			!reviewLevel.getReviewLevelTypeRef().getReviewLevelCd().equalsIgnoreCase(ReviewLevelTypeCodes.HQ_ESCALATION) &&
			!reviewLevel.getReviewLevelTypeRef().getReviewLevelCd().equalsIgnoreCase(ReviewLevelTypeCodes.ESCALATION)
		) {
			throw new ConflictException("Review level " + reviewLevel.getReviewLevelId() +	" must be ESCALATION or HQ_ESCALATION");
		}

		reviewLevel.setRatingRef(ratingRefRepository.findByCode(RatingCodes.UNACCEPTABLE));
		commonReviewService.completeReviewLevel(reviewLevel);

		IndemnificationTypeRef indemnificationType = indemnificationTypeRefRepository.findByCode(indemnificationTypeCode);
		if (indemnificationType == null) {
			throw new BadRequestException("Unknown IndemnificationTypeRef code: " + indemnificationTypeCode);
		}

		ReviewLevel newReviewLevel = commonReviewService.createIndemnificationReviewLevel(
			reviewLevel,
			reviewLevelTypeRefRepository.findByReviewLevelCd(ReviewLevelTypeCodes.FORCE_INDEMNIFICATION),
			indemnificationType
		);

		// always assign forced indem to current user
		commonReviewService.assignReviewLevel(newReviewLevel, securityService.getPersonnel());

		indemnificationService.createIndemnification(newReviewLevel);

		for (ReviewProcessException exception : reviewProcessExceptionRepository.findByReviewLevelReview(reviewLevel.getReview())) {
			commonExceptionService.markExceptionResolved(exception);
		}
	}

	@Transactional
	public void indemnificationReviewerSubmit(String reviewLevelId, String reviewLocationId, boolean indemnificationTransferrable) {
		ReviewLevel reviewLevel = reviewLevelRepository.findOne(reviewLevelId);
		if (reviewLevel == null) {
			throw new NotFoundException("No ReviewLevel for reviewLevelId " + reviewLevelId);
		}

		if (
			!reviewLevel.getReviewLevelTypeRef().getReviewLevelCd().equals(ReviewLevelTypeCodes.INDEMNIFICATION) &&
			!reviewLevel.getReviewLevelTypeRef().getReviewLevelCd().equals(ReviewLevelTypeCodes.FORCE_INDEMNIFICATION)
		) {
			throw new ConflictException("ReviewLevel " + reviewLevelId + " is not in indemnification");
		}

		ReviewLocation reviewLocation = reviewLocationRepository.findOne(reviewLocationId);
		if (reviewLocation == null) {
			throw new BadRequestException("No ReviewLocation for reviewLocationId " + reviewLocationId);
		}

		Indemnification indemnification = indemnificationRepository.findByReviewLevel(reviewLevel);
		if (indemnification == null) {
			throw new RuntimeException("ReviewLevel " + reviewLevel.getReviewLevelId() + " has indemnification status, but no corresponding Indemnification record");
		}

		String userId = securityService.getUserId();
		Date now = new Date();

		boolean forcedIndemnification = reviewLevel.getReviewLevelTypeRef().getReviewLevelCd().equals(ReviewLevelTypeCodes.FORCE_INDEMNIFICATION) ? true : false;

		if (!forcedIndemnification) {
			RatingRef remediatedRating = ratingRefRepository.findByCode(RatingCodes.REMEDIATED);
			reviewLevel.setRatingRef(remediatedRating);
			// PERF TODO: this should be done as a single set-based query
			for (RvwLvlFinding rvwLvlFinding : reviewLevel.getRvwLvlFindings()) {
				if (rvwLvlFinding.getRatingRef().getCode().equals(RatingCodes.UNACCEPTABLE)) {
					rvwLvlFinding.setRatingRef(remediatedRating);
					rvwLvlFinding.setUpdatedBy(userId);
					rvwLvlFinding.setUpdatedTs(now);
				}
			}
		}

		commonReviewService.completeReviewLevel(reviewLevel);
		Review review = reviewLevel.getReview();
		review.setReviewStatusRef(reviewStatusRefRepository.findByCode(ReviewStatusCodes.COMPLETED));
		review.setRvwCompltdDt(now);
		review.setUpdatedTs(now);

		indemnificationService.reviewerSignIndemnification(indemnification, reviewLocation, indemnificationTransferrable);

		byte[] pdfBytes = documentTemplateService.generateIndemnificationPdf(indemnification, forcedIndemnification);

		indemnificationService.sendIndemnificationToHud(indemnification, pdfBytes);
	}

	@Transactional
	public void indemnificationReviewerReject(String reviewLevelId) {
		ReviewLevel reviewLevel = reviewLevelRepository.findOne(reviewLevelId);
		if (reviewLevel == null) {
			throw new NotFoundException("No ReviewLevel for reviewLevelId " + reviewLevelId);
		}
		if (!reviewLevel.getReviewLevelTypeRef().getReviewLevelCd().equals(ReviewLevelTypeCodes.INDEMNIFICATION)) {
			throw new ConflictException("ReviewLevel " + reviewLevelId + " is not in lender-requested indemnification");
		}

		String statusCode = reviewLevel.getReviewLevelStatusRef().getCode();
		if (
			!statusCode.equalsIgnoreCase(ReviewLevelStatusCodes.ASSIGNED) &&
			!statusCode.equalsIgnoreCase(ReviewLevelStatusCodes.IN_PROGRESS)
		) {
			throw new RuntimeException("ReviewLevel " + reviewLevel.getReviewLevelId() + " has status " + statusCode);
		}

		Review review = reviewLevel.getReview();

		ReviewLevel previousReviewLevel = reviewLevelRepository.findLastNonIndemCreatedBefore(reviewLevel.getCreatedTs(), review.getReviewId());

		commonReviewService.completeReviewLevel(reviewLevel);

		review.setReviewStatusRef(reviewStatusRefRepository.findByCode(ReviewStatusCodes.PENDING_LENDER_RESPONSE));

		// TODO: rejecting a forced indem has problems, recall email thread
		ReviewLevel newReviewLevel = commonReviewService.createNextIterationReviewLevel(previousReviewLevel);

		// try to assign to me
		Personnel personnel = securityService.getPersonnel();
		boolean passed = assignmentService.runAssignmentModelForPersonnel(newReviewLevel, personnel);
		if (passed) {
			commonReviewService.assignReviewLevel(newReviewLevel, personnel);
		} else {
			commonExceptionService.createReviewLevelAssignmentException(newReviewLevel);
		}
		logger.debug("Rejecting indemnification for case number " + review.getCaseNumber() + "; created new review level " + newReviewLevel.getReviewLevelId());
	}

	@Transactional
	public void forceEscalation(String reviewId) {
		final ReviewLevel reviewLevel = reviewLevelRepository.findActiveByReviewId(reviewId);
		if (reviewLevel == null) {
			throw new NotFoundException("No active review level found for review: " + reviewId);
		}

		if (
			!reviewLevel.getReviewLevelTypeRef().getReviewLevelCd().equalsIgnoreCase(ReviewLevelTypeCodes.MITIGATION) &&
			!reviewLevel.getReviewLevelTypeRef().getReviewLevelCd().equalsIgnoreCase(ReviewLevelTypeCodes.ESCALATION)
		) {
			throw new ConflictException("Review level " + reviewLevel.getReviewLevelId() +	" must be MITIGATION or ESCALATION");
		}

		reviewLevel.setRatingRef(ratingRefRepository.findByCode(RatingCodes.UNACCEPTABLE));
		reviewLevel.setForceEscalationDt(new Date());
		commonReviewService.completeReviewLevel(reviewLevel);

		ReviewLevel newReviewLevel = commonReviewService.createEscalatedReviewLevel(reviewLevel);

		scoreAndAssignReviewLevel(newReviewLevel);
		reviewLevelRepository.flush();

		Batch batch = reviewLevel.getReview().getBatch();
		if (batch != null) {
			List<String> reviewLevelStatusCodes = new ArrayList<String>();
			reviewLevelStatusCodes.add(BatchStatusRef.COMPLETED);
			reviewLevelStatusCodes.add(BatchStatusRef.CANCELLED);
			ReviewLevel lowestNonCompleteReviewLevel =
				reviewLevelRepository.findTopByReviewBatchBatchIdAndReviewLevelStatusRefCodeNotInOrderByReviewLevelTypeRefOrdinal(batch.getBatchId(), reviewLevelStatusCodes);

			if (!batch.getReviewLevelTypeRef().getReviewLevelCd().equals(lowestNonCompleteReviewLevel.getReviewLevelTypeRef().getReviewLevelCd())) {
				batch.setReviewLevelTypeRef(lowestNonCompleteReviewLevel.getReviewLevelTypeRef());
				batch.setIterationNumber(lowestNonCompleteReviewLevel.getIterationNumber());
				Personnel batchOwnerSupervisor = batch.getOwnerPersonnel().getReportsToPersonnel();
				if (batchOwnerSupervisor != null) {
					batch.setOwnerPersonnel(batchOwnerSupervisor);
				}
				batch = batchRepository.save(batch);
			}
		}
	}

	@Transactional
	public void acknowledgeVetting(String reviewLevelId) {
		ReviewLevel reviewLevel = reviewLevelRepository.findOne(reviewLevelId);
		if (reviewLevel == null) {
			throw new NotFoundException("ReviewLevel " + reviewLevelId + " doesn't exist");
		}
		if (reviewLevel.getVettingInd() != 'Y') {
			throw new ConflictException("ReviewLevel " + reviewLevel.getReviewLevelId() + " is not a vetting review level");
		}
		if (reviewLevel.getVetteeAcknowledgementDt() != null) {
			throw new ConflictException("Vetting has already been acknowledged for ReviewLevel " + reviewLevel.getReviewLevelId());
		}

		Date now = new Date();
		reviewLevel.setReviewLevelStatusRef(reviewLevelStatusRefRepository.findByCode(ReviewLevelStatusCodes.COMPLETED));
		reviewLevel.setVetteeAcknowledgementDt(now);
		reviewLevel.setUpdatedBy(securityService.getUserId());
		reviewLevel.setUpdatedTs(now);
		reviewLevel = reviewLevelRepository.save(reviewLevel);

		ReviewProcessException vettingAcknowledgementException = reviewProcessExceptionRepository.findByReviewLevelAndReviewProcessExceptionTypeRefCodeAndResolvedInd(
			reviewLevel,
			ReviewProcessExceptionTypeCodes.VETTING_ACKNOWLEDGEMENT,
			'N'
		);
		if (vettingAcknowledgementException != null) {
			commonExceptionService.markExceptionResolved(vettingAcknowledgementException);

			ReviewLevel newReviewLevel = commonReviewService.createNextIterationReviewLevel(reviewLevel);

			scoreAndAssignReviewLevel(newReviewLevel);
		}
	}

	private void scoreAndAssignReviewLevel(ReviewLevel reviewLevel) {
		List<Personnel> personnelList = assignmentService.runAssignmentModelForCurrentReviewLocation(reviewLevel);
		if (!personnelList.isEmpty()) {
			commonReviewService.assignReviewLevel(reviewLevel, personnelList.get(0));
		} else {
			commonExceptionService.createReviewLevelAssignmentException(reviewLevel);
		}
	}

}
