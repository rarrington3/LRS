package gov.hud.lrs.services.bizservice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ImmutableList;

import gov.hud.lrs.common.dto.LrsApiV1DTOs.ExceptionDTO;
import gov.hud.lrs.common.entity.Batch;
import gov.hud.lrs.common.entity.Lender;
import gov.hud.lrs.common.entity.LenderMonitoringSelectionRequest;
import gov.hud.lrs.common.entity.LoanSelection;
import gov.hud.lrs.common.entity.ManualSelectionRequest;
import gov.hud.lrs.common.entity.ProductTypeRef;
import gov.hud.lrs.common.entity.Review;
import gov.hud.lrs.common.entity.ReviewLevel;
import gov.hud.lrs.common.entity.ReviewLocation;
import gov.hud.lrs.common.entity.ReviewProcessException;
import gov.hud.lrs.common.entity.ReviewTypeRef;
import gov.hud.lrs.common.entity.SelectionReason;
import gov.hud.lrs.common.entity.SelectionRequest;
import gov.hud.lrs.common.entity.SelectionRequestTypeRef;
import gov.hud.lrs.common.enumeration.ReviewProcessExceptionTypeCodes;
import gov.hud.lrs.common.enumeration.Roles;
import gov.hud.lrs.common.enumeration.SelectionReasonCodes;
import gov.hud.lrs.common.exception.ForbiddenException;
import gov.hud.lrs.common.repository.LenderMonitoringSelectionRequestRepository;
import gov.hud.lrs.common.repository.ManualSelectionRequestRepository;
import gov.hud.lrs.common.repository.ReviewProcessExceptionRepository;
import gov.hud.lrs.common.repository.SelectionReasonRepository;
import gov.hud.lrs.common.security.SecurityService;

@Service
public class ExceptionService {

	@Autowired private LenderMonitoringSelectionRequestRepository lenderMonitoringSelectionRequestRepository;
	@Autowired private ManualSelectionRequestRepository manualSelectionRequestRepository;
	@Autowired private ReviewProcessExceptionRepository reviewProcessExceptionRepository;
	@Autowired private SelectionReasonRepository selectionReasonRepository;

	@Autowired private BatchService batchService;
	@Autowired private ReviewService reviewService;
	@Autowired private SecurityService securityService;

	@PersistenceContext private EntityManager entityManager;

	@Transactional(readOnly = true)
	public List<ExceptionDTO> getExceptionDTOs() {
		List<String> roles = securityService.getRoles();

		List<ReviewProcessException> exceptions;
		if (roles.contains(Roles.ROLE_HQ_ADMIN)) {
			exceptions = entityManager.createQuery(
				"select e from ReviewProcessException e " +
				"left join fetch e.loanSelection ls " +
				"left join fetch ls.lender " +
				"left join fetch ls.selectionReason " +
				"left join fetch ls.productTypeRef " +
				"left join fetch ls.reviewTypeRef " +
				"left join fetch e.batch b " +
				"left join fetch b.lender " +
				"left join fetch b.selectionRequest bsr " +
				"left join fetch bsr.selectionRequestTypeRef " +
				"left join fetch e.reviewLevel rl " +
				"left join fetch rl.review r " +
				"left join fetch r.lender " +
				"left join fetch r.loanSelection rls " +
				"left join fetch r.selectionReason " +
				"left join fetch r.productTypeRef " +
				"left join fetch r.reviewTypeRef " +
				"where (e.resolvedInd = 'N') ",
				ReviewProcessException.class
			)
			.getResultList();

		} else if (roles.contains(Roles.ROLE_REVIEW_LOCATION_ADMIN)) {
			exceptions = reviewProcessExceptionRepository.findUnresolvedByReviewLocationIdAndReviewProcessExceptionTypeCodeIn(
				securityService.getReviewLocation().getReviewLocationId(),
				ImmutableList.of(
					ReviewProcessExceptionTypeCodes.BATCH_ASSIGNMENT,
					ReviewProcessExceptionTypeCodes.REVIEW_LEVEL_ASSIGNMENT,
					ReviewProcessExceptionTypeCodes.BINDER_REQUEST_ERROR,
					ReviewProcessExceptionTypeCodes.BINDER_REQUEST_PAST_DUE,
					ReviewProcessExceptionTypeCodes.VETTING_ACKNOWLEDGEMENT
				)
			);

		} else if (roles.contains(Roles.ROLE_PROGRAM_ASSISTANT)) {
			exceptions = reviewProcessExceptionRepository.findUnresolvedByReviewLocationIdAndReviewProcessExceptionTypeCodeIn(
				securityService.getReviewLocation().getReviewLocationId(),
				ImmutableList.of(
					ReviewProcessExceptionTypeCodes.BINDER_REQUEST_PAST_DUE,
					ReviewProcessExceptionTypeCodes.BINDER_REQUEST_ERROR
				)
			);
		} else {
			throw new ForbiddenException("Must have HQ_ADMIN, REVIEW_LOCATION_ADMIN, of PROGRAM_ASSISTANT to access exceptions");
		}

		return convertExceptionsToExceptionDTOs(exceptions);
	}

	public ExceptionDTO convertExceptionToExceptionDTO(ReviewProcessException exception) {
		return convertExceptionsToExceptionDTOs(ImmutableList.of(exception)).get(0);
	}

	public List<ExceptionDTO> convertExceptionsToExceptionDTOs(List<ReviewProcessException> exceptions) {
		List<ExceptionDTO> exceptionDTOs = new ArrayList<ExceptionDTO>();
		for (ReviewProcessException exception : exceptions) {
			ExceptionDTO exceptionDTO = new ExceptionDTO();
			exceptionDTO.setExceptionId(exception.getReviewProcessExceptionId());
			exceptionDTO.setExceptionType(exception.getReviewProcessExceptionTypeRef().getDescription());
			exceptionDTO.setExceptionTypeCode(exception.getReviewProcessExceptionTypeRef().getCode());

			Lender lender = null;
			SelectionReason selectionReason = null;
			ReviewLocation reviewLocation = null;
			ProductTypeRef productType = null;
			ReviewTypeRef reviewType = null;

			if (exception.getLoanSelection() != null) {
				LoanSelection loanSelection = exception.getLoanSelection();
				exceptionDTO.setCaseNumber(loanSelection.getCaseNumber());
				exceptionDTO.setBorrowerName(loanSelection.getBorrName());
				lender = loanSelection.getLender();
				selectionReason = loanSelection.getSelectionReason();
				productType = loanSelection.getProductTypeRef();
				reviewType = loanSelection.getReviewTypeRef();

			} else if (exception.getBatch() != null) {
				Batch batch = exception.getBatch();
				lender = batch.getLender();
				SelectionRequest selectionRequest = batch.getSelectionRequest();
				String selectionRequestTypeCode = selectionRequest.getSelectionRequestTypeRef().getCode();

				if (selectionRequestTypeCode.equals(SelectionRequestTypeRef.FHA_MANUAL)) {
					// an fha manual selection can actually have one of four selection reasons: test, fha manual, oig audit, or national qc
					ManualSelectionRequest manualSelectionRequest = manualSelectionRequestRepository.findOne(selectionRequest.getSelectionRequestId());
					if (manualSelectionRequest != null) {
						selectionReason = manualSelectionRequest.getSelectionReason();
						reviewLocation = manualSelectionRequest.getReviewLocation();
						reviewType = manualSelectionRequest.getReviewTypeRef();
					}

				} else if (selectionRequestTypeCode.equals(SelectionRequestTypeRef.LENDER_MONITORING)) {
					LenderMonitoringSelectionRequest lenderMonitoringSelectionRequest = lenderMonitoringSelectionRequestRepository.findOne(selectionRequest.getSelectionRequestId());
					selectionReason = selectionReasonRepository.findByCode(SelectionReasonCodes.LENDER_MONITORING);
					reviewLocation = lenderMonitoringSelectionRequest.getReviewLocation();
					reviewType = lenderMonitoringSelectionRequest.getReviewTypeRef();
				} else if (selectionRequestTypeCode.equals(SelectionRequestTypeRef.LENDER_SELF_REPORT)) {
					selectionReason = selectionReasonRepository.findByCode(SelectionReasonCodes.LENDER_SELF_REPORT);
				}

				// no other selection request types can produce a batch

				// no product type for batch since there are multiple reviews

				exceptionDTO.setBatchInfo(batchService.convertBatchToBatchDTO(batch));

			} else if (exception.getReviewLevel() != null) {
				ReviewLevel reviewLevel = exception.getReviewLevel();
				Review review = reviewLevel.getReview();
				lender = review.getLender();
				exceptionDTO.setCaseNumber(review.getCaseNumber());
				exceptionDTO.setReviewId(review.getReviewId());
				exceptionDTO.setCurrentReviewLevel(reviewService.convertReviewLevelToReviewLevelDTO(reviewLevel));

				if (review.getLoanSelection() != null) {
					exceptionDTO.setBorrowerName(review.getLoanSelection().getBorrName());
				}

				selectionReason = review.getSelectionReason();
				productType = review.getProductTypeRef();
				reviewType = review.getReviewTypeRef();

			} else {
				throw new RuntimeException("Exception " + exception.getReviewProcessExceptionId() + " had loanSelection, batch, and review all null");
			}

			if (productType != null) {
				exceptionDTO.setLoanType(productType.getDescription());
			}

			if (reviewType != null) {
				exceptionDTO.setReviewType(reviewType.getDescription());
			}

			if (lender != null) {
				exceptionDTO.setLenderName(lender.getName());
			}

			if (selectionReason != null) {
				exceptionDTO.setSelectionReasonCode(selectionReason.getCode());
				exceptionDTO.setSelectionReason(selectionReason.getDescription());
			}

			if (reviewLocation != null) {
				exceptionDTO.setReviewLocationId(reviewLocation.getReviewLocationId());
			}

			final int MILLISECONDS_PER_DAY = 86400000;
			long days = ((new Date()).getTime() - exception.getCreatedTs().getTime()) / MILLISECONDS_PER_DAY;
			exceptionDTO.setDaysOnList(new BigDecimal(days));

			exceptionDTOs.add(exceptionDTO);
		}

		return exceptionDTOs;
	}

}
