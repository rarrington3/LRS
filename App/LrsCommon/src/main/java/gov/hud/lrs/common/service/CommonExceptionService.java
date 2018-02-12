package gov.hud.lrs.common.service;

import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.hud.lrs.common.dto.LrsApiV1DTOs.BatchDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.ExceptionDTO;
import gov.hud.lrs.common.entity.Batch;
import gov.hud.lrs.common.entity.BinderRequest;
import gov.hud.lrs.common.entity.LoanSelection;
import gov.hud.lrs.common.entity.LoanSelectionPending;
import gov.hud.lrs.common.entity.Review;
import gov.hud.lrs.common.entity.ReviewLevel;
import gov.hud.lrs.common.entity.ReviewProcessException;
import gov.hud.lrs.common.enumeration.BinderRequestStatusCodes;
import gov.hud.lrs.common.enumeration.LoanSelectionStatusCodes;
import gov.hud.lrs.common.enumeration.ReviewLevelStatusCodes;
import gov.hud.lrs.common.enumeration.ReviewProcessExceptionTypeCodes;
import gov.hud.lrs.common.enumeration.ReviewStatusCodes;
import gov.hud.lrs.common.exception.BadRequestException;
import gov.hud.lrs.common.repository.BinderRequestRepository;
import gov.hud.lrs.common.repository.BinderRequestStatusRefRepository;
import gov.hud.lrs.common.repository.LoanSelectionRepository;
import gov.hud.lrs.common.repository.LoanSelectionStatusRefRepository;
import gov.hud.lrs.common.repository.ReviewLevelRepository;
import gov.hud.lrs.common.repository.ReviewLevelStatusRefRepository;
import gov.hud.lrs.common.repository.ReviewProcessExceptionRepository;
import gov.hud.lrs.common.repository.ReviewProcessExceptionTypeRefRepository;
import gov.hud.lrs.common.repository.ReviewRepository;
import gov.hud.lrs.common.repository.ReviewStatusRefRepository;
import gov.hud.lrs.common.security.SecurityService;

@Service
public class CommonExceptionService {

	private Logger logger = LoggerFactory.getLogger(CommonExceptionService.class);

	@Autowired private BinderRequestRepository binderRequestRepository;
	@Autowired private BinderRequestStatusRefRepository binderRequestStatusRefRepository;
	@Autowired private LoanSelectionRepository loanSelectionRepository;
	@Autowired private LoanSelectionStatusRefRepository loanSelectionStatusRefRepository;
	@Autowired private ReviewRepository reviewRepository;
	@Autowired private ReviewLevelRepository reviewLevelRepository;
	@Autowired private ReviewLevelStatusRefRepository reviewLevelStatusRefRepository;
	@Autowired private ReviewProcessExceptionRepository reviewProcessExceptionRepository;
	@Autowired private ReviewProcessExceptionTypeRefRepository reviewProcessExceptionTypeRefRepository;
	@Autowired private ReviewStatusRefRepository reviewStatusRefRepository;

	@Autowired private CommonBatchService commonBatchService;
	@Autowired private CommonLoanSelectionService commonLoanSelectionService;
	@Autowired private CommonReviewService commonReviewService;
	@Autowired private SecurityService securityService;

	@PersistenceContext(unitName = "lrs") EntityManager entityManager;

	@Transactional
	public ReviewProcessException createLoanSelectionDistributionException(LoanSelection loanSelection) {
		String userId = securityService.getUserId();
		Date now = new Date();

		loanSelection.setLoanSelectionStatusRef(loanSelectionStatusRefRepository.findByCode(LoanSelectionStatusCodes.EXCEPTION));
		loanSelection.setUpdatedBy(userId);
		loanSelection.setUpdatedTs(now);
		loanSelection = loanSelectionRepository.save(loanSelection);

		ReviewProcessException exception = new ReviewProcessException();
		exception.setReviewProcessExceptionTypeRef(reviewProcessExceptionTypeRefRepository.findByCode(ReviewProcessExceptionTypeCodes.LOAN_SELECTION_DISTRIBUTION));
		exception.setLoanSelection(loanSelection);
		exception.setResolvedInd('N');
		exception.setCreatedBy(userId);
		exception.setCreatedTs(now);
		exception = reviewProcessExceptionRepository.save(exception);

		logger.debug(ReviewProcessExceptionTypeCodes.LOAN_SELECTION_DISTRIBUTION + " exception " + exception.getReviewProcessExceptionId() + " created for case number " + loanSelection.getCaseNumber());
		return exception;
	}

	@Transactional
	public ReviewProcessException createReviewLevelAssignmentException(ReviewLevel reviewLevel) {
		return createReviewLevelExceptionImpl(reviewLevel, ReviewProcessExceptionTypeCodes.REVIEW_LEVEL_ASSIGNMENT, ReviewStatusCodes.EXCEPTION);
	}

	@Transactional
	public ReviewProcessException createHqEscalationException(ReviewLevel reviewLevel) {
		return createReviewLevelExceptionImpl(reviewLevel, ReviewProcessExceptionTypeCodes.HQ_ESCALATION, ReviewStatusCodes.EXCEPTION);
	}

	@Transactional
	public ReviewProcessException createVettingConfirmationException(ReviewLevel reviewLevel) {
		// For vetting during exception, according to the lender, the review level is still under review by FHA
		// Once the Vettee acknowledges, it will be itterated and turned into officially back under review by FHA
		return createReviewLevelExceptionImpl(reviewLevel, ReviewProcessExceptionTypeCodes.VETTING_ACKNOWLEDGEMENT, ReviewStatusCodes.UNDER_REVIEW);
	}

	private ReviewProcessException createReviewLevelExceptionImpl(
		ReviewLevel reviewLevel,
		String reviewProcessExceptionTypeCode,
		String reviewStatusCode
	) {
		String userId = securityService.getUserId();
		Date now = new Date();

		Review review = reviewLevel.getReview();
		review.setReviewStatusRef(reviewStatusRefRepository.findByCode(reviewStatusCode));
		review.setUpdatedBy(userId);
		review.setUpdatedTs(now);
		review = reviewRepository.save(review);

		reviewLevel.setReviewLevelStatusRef(reviewLevelStatusRefRepository.findByCode(ReviewLevelStatusCodes.EXCEPTION));
		reviewLevel.setUpdatedBy(userId);
		reviewLevel.setUpdatedTs(now);
		reviewLevel = reviewLevelRepository.save(reviewLevel);

		ReviewProcessException exception = new ReviewProcessException();
		exception.setReviewProcessExceptionTypeRef(reviewProcessExceptionTypeRefRepository.findByCode(reviewProcessExceptionTypeCode));
		exception.setReviewLevel(reviewLevel);
		exception.setResolvedInd('N');
		exception.setCreatedBy(userId);
		exception.setCreatedTs(now);
		exception = reviewProcessExceptionRepository.save(exception);

		logger.debug(reviewProcessExceptionTypeCode + " exception " + exception.getReviewProcessExceptionId() + " created for case number " + review.getCaseNumber());
		return exception;
	}

	@Transactional
	public ReviewProcessException createBatchDistributionException(Batch batch) {
		return createBatchExceptionImpl(batch, ReviewProcessExceptionTypeCodes.BATCH_DISTRIBUTION);
	}

	@Transactional
	public ReviewProcessException createBatchAssignmentException(Batch batch) {
		return createBatchExceptionImpl(batch, ReviewProcessExceptionTypeCodes.BATCH_ASSIGNMENT);
	}

	private ReviewProcessException createBatchExceptionImpl(Batch batch, String reviewProcessExceptionTypeCode) {
		ReviewProcessException exception = new ReviewProcessException();
		exception.setReviewProcessExceptionTypeRef(reviewProcessExceptionTypeRefRepository.findByCode(reviewProcessExceptionTypeCode));
		exception.setBatch(batch);
		exception.setResolvedInd('N');
		exception.setCreatedBy(securityService.getUserId());
		exception.setCreatedTs(new Date());
		exception = reviewProcessExceptionRepository.save(exception);

		logger.debug(reviewProcessExceptionTypeCode + " exception " + exception.getReviewProcessExceptionId() + " created for batch " + batch.getBatchId() + "; " + batch.getBatchReferenceId());
		return exception;
	}

	@Transactional
	public ReviewProcessException createBinderRequestErrorException(LoanSelection loanSelection) {
		String userId = securityService.getUserId();
		Date now = new Date();

		loanSelection.setLoanSelectionStatusRef(loanSelectionStatusRefRepository.findByCode(LoanSelectionStatusCodes.EXCEPTION));
		loanSelection.setUpdatedBy(userId);
		loanSelection.setUpdatedTs(now);
		loanSelection = loanSelectionRepository.save(loanSelection);

		ReviewProcessException exception = new ReviewProcessException();
		exception.setReviewProcessExceptionTypeRef(reviewProcessExceptionTypeRefRepository.findByCode(ReviewProcessExceptionTypeCodes.BINDER_REQUEST_ERROR));
		exception.setLoanSelection(loanSelection);
		exception.setResolvedInd('N');
		exception.setCreatedBy(userId);
		exception.setCreatedTs(now);
		exception = reviewProcessExceptionRepository.save(exception);

		logger.debug(ReviewProcessExceptionTypeCodes.BINDER_REQUEST_ERROR + " exception " + exception.getReviewProcessExceptionId() + " created for case number " + loanSelection.getCaseNumber());
		return exception;
	}

	@Transactional
	public ReviewProcessException createBinderPastDueException(BinderRequest binderRequest) {
		String userId = securityService.getUserId();
		Date now = new Date();

		LoanSelection loanSelection = binderRequest.getLoanSelection();
		loanSelection.setLoanSelectionStatusRef(loanSelectionStatusRefRepository.findByCode(LoanSelectionStatusCodes.EXCEPTION));
		loanSelection.setUpdatedBy(userId);
		loanSelection.setUpdatedTs(now);
		loanSelection = loanSelectionRepository.save(loanSelection);

		// update the binder request status to Exception after processing
		binderRequest.setBinderRequestStatusRef(binderRequestStatusRefRepository.findByCode(BinderRequestStatusCodes.EXCEPTION));
		binderRequest.setUpdatedBy(userId);
		binderRequest.setUpdatedTs(now);
		binderRequest = binderRequestRepository.save(binderRequest);

		ReviewProcessException exception = new ReviewProcessException();
		exception.setReviewProcessExceptionTypeRef(reviewProcessExceptionTypeRefRepository.findByCode(ReviewProcessExceptionTypeCodes.BINDER_REQUEST_PAST_DUE));
		exception.setLoanSelection(loanSelection);
		exception.setResolvedInd('N');
		exception.setCreatedBy(userId);
		exception.setCreatedTs(now);
		exception = reviewProcessExceptionRepository.save(exception);

		logger.debug(ReviewProcessExceptionTypeCodes.BINDER_REQUEST_PAST_DUE + " exception " + exception.getReviewProcessExceptionId() + " created for case number " + loanSelection.getCaseNumber() + "; binder request " + binderRequest.getBinderRequestId());

		return exception;
	}

	@Transactional
	public void handleException(ReviewProcessException exception, ExceptionDTO exceptionDTO) {
		String exceptionTypeCode = exception.getReviewProcessExceptionTypeRef().getCode();
		if (exceptionTypeCode.equals(ReviewProcessExceptionTypeCodes.LOAN_SELECTION_DISTRIBUTION)) {
			if (exceptionDTO.getReviewLocationId() == null) {
				throw new BadRequestException("reviewLocationId is required");
			}
			commonLoanSelectionService.distributeLoanSelection(exception.getLoanSelection(), exceptionDTO.getReviewLocationId(), false);

		} else if (exceptionTypeCode.equals(ReviewProcessExceptionTypeCodes.BATCH_DISTRIBUTION)) {
			if (exceptionDTO.getReviewLocationId() == null) {
				throw new BadRequestException("reviewLocationId is required");
			}

			Batch batch = exception.getBatch();
			batch = commonBatchService.distributeBatch(batch, exceptionDTO.getReviewLocationId());

			for (LoanSelectionPending loanSelectionPending : batch.getLoanSelectionPendings()) {
				commonLoanSelectionService.distributeLoanSelectionPending(loanSelectionPending, batch.getReviewLocation());
			}

			for (LoanSelection loanSelection : batch.getLoanSelections()) {
				commonLoanSelectionService.distributeLoanSelection(loanSelection, batch.getReviewLocation(), false);
			}

			createBatchAssignmentException(batch);

		} else if (exceptionTypeCode.equals(ReviewProcessExceptionTypeCodes.REVIEW_LEVEL_ASSIGNMENT)) {
			commonReviewService.assignReviewLevel(exception.getReviewLevel(), exceptionDTO.getAssignedTo());

		} else if (exceptionTypeCode.equals(ReviewProcessExceptionTypeCodes.BATCH_ASSIGNMENT)) {
			Batch batch = exception.getBatch();

			BatchDTO batchDTO = exceptionDTO.getBatchInfo();
			if (batchDTO == null) {
				throw new BadRequestException("BatchDTO is required");
			}

			if (batchDTO.getBatchOwner() == null) {
				throw new BadRequestException("batchOwner is required");
			}
			if ((batchDTO.getBatchTeamMembers() == null) || batchDTO.getBatchTeamMembers().isEmpty()) {
				throw new BadRequestException("batchTeamMembers is required and must be non-empty");
			}
			commonBatchService.assignBatch(batch, batchDTO.getBatchOwner(), batchDTO.getBatchTeamMembers());

			commonBatchService.setupBatchOperationalReviewFields(
				batch,
				Boolean.TRUE.equals(batchDTO.getRequestOperationalReview()),
				Boolean.TRUE.equals(batchDTO.getRequestOperationalDocuments()),
				batchDTO.getOperationalReviewGuidance(),
				batchDTO.getSecondaryId()
			);


		} else {
			throw new RuntimeException("Unhandled ReviewLevelExceptionTypeRef code: " + exceptionTypeCode + " for exception " + exception.getReviewProcessExceptionId());
		}

		markExceptionResolved(exception);
	}

	@Transactional
	public void markExceptionResolved(ReviewProcessException exception) {
		exception.setResolvedInd('Y');
		exception.setUpdatedBy(securityService.getUserId());
		exception.setUpdatedTs(new Date());
		exception = reviewProcessExceptionRepository.save(exception);
	}

}
