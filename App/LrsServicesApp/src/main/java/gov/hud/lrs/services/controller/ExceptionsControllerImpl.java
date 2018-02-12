package gov.hud.lrs.services.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.google.common.collect.ImmutableList;

import gov.hud.lrs.common.dto.LrsApiV1DTOs.ExceptionDTO;
import gov.hud.lrs.common.entity.Batch;
import gov.hud.lrs.common.entity.LoanSelection;
import gov.hud.lrs.common.entity.ReviewLevel;
import gov.hud.lrs.common.entity.ReviewProcessException;
import gov.hud.lrs.common.entity.ReviewProcessExceptionTypeRef;
import gov.hud.lrs.common.enumeration.CancellationReasonCodes;
import gov.hud.lrs.common.enumeration.ReviewProcessExceptionTypeCodes;
import gov.hud.lrs.common.enumeration.Roles;
import gov.hud.lrs.common.exception.BadRequestException;
import gov.hud.lrs.common.exception.ConflictException;
import gov.hud.lrs.common.exception.ForbiddenException;
import gov.hud.lrs.common.exception.NotFoundException;
import gov.hud.lrs.common.repository.ReviewProcessExceptionRepository;
import gov.hud.lrs.common.security.SecurityService;
import gov.hud.lrs.common.service.CommonBatchService;
import gov.hud.lrs.common.service.CommonExceptionService;
import gov.hud.lrs.common.service.CommonLoanSelectionService;
import gov.hud.lrs.common.service.CommonReviewService;
import gov.hud.lrs.services.bizservice.ExceptionService;

@Controller
public class ExceptionsControllerImpl {

	@Autowired private ReviewProcessExceptionRepository reviewProcessExceptionRepository;

	@Autowired private CommonExceptionService commonExceptionService;
	@Autowired private ExceptionService exceptionService;
	@Autowired private SecurityService securityService;
	@Autowired private CommonLoanSelectionService commonLoanSelectionService;
	@Autowired private CommonReviewService commonReviewService;
	@Autowired private CommonBatchService commonBatchService;

	public ResponseEntity<List<ExceptionDTO>> getExceptions(HttpServletRequest request, HttpServletResponse response) {
		return new ResponseEntity<List<ExceptionDTO>>(exceptionService.getExceptionDTOs(), HttpStatus.OK);
	}

	public ResponseEntity<ExceptionDTO> getExceptionByExceptionId(String exceptionId, HttpServletRequest request, HttpServletResponse response) {
		ReviewProcessException exception = reviewProcessExceptionRepository.findOne(exceptionId);
		if (exception == null) {
			throw new NotFoundException("No ReviewProcessException for exceptionId " + exceptionId);
		}

		exceptionLocationCheck(exception);
		exceptionTypeCheck(exception.getReviewProcessExceptionTypeRef());

		ExceptionDTO exceptionDTO = exceptionService.convertExceptionToExceptionDTO(exception);
		return new ResponseEntity<ExceptionDTO>(exceptionDTO, HttpStatus.OK);
	}

	public ResponseEntity<String> putException(String exceptionId, ExceptionDTO exceptionDTO, HttpServletRequest request, HttpServletResponse response) {
		ReviewProcessException exception = reviewProcessExceptionRepository.findOne(exceptionId);
		if (exception == null) {
			throw new NotFoundException("ReviewProcessException for exceptionId " + exceptionId + " is not found.");
		}
		if (exception.getResolvedInd() == 'Y') {
			throw new ConflictException("ReviewProcessException " + exceptionId + " has already been resolved.");
		}
		exceptionLocationCheck(exception);
		exceptionTypeCheck(exception.getReviewProcessExceptionTypeRef());

		commonExceptionService.handleException(exception, exceptionDTO);

		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	public ResponseEntity deleteException(String exceptionId, HttpServletRequest request, HttpServletResponse response) {
		// This method is called by "Cancel Review" from UI
		ReviewProcessException exception = reviewProcessExceptionRepository.findOne(exceptionId);
		if (exception == null) {
			throw new NotFoundException("No ReviewProcessException for exceptionId " + exceptionId);
		}
		if (exception.getResolvedInd() == 'Y') {
			throw new ConflictException("ReviewProcessException " + exceptionId + " has already been resolved.");
		}

		exceptionLocationCheck(exception);
		exceptionTypeCheck(exception.getReviewProcessExceptionTypeRef());

		if (ImmutableList.of(
				ReviewProcessExceptionTypeCodes.LOAN_SELECTION_DISTRIBUTION,
				ReviewProcessExceptionTypeCodes.BINDER_REQUEST_ERROR,
				ReviewProcessExceptionTypeCodes.BINDER_REQUEST_PAST_DUE
			).contains(exception.getReviewProcessExceptionTypeRef().getCode())) {
			// Change the Loan selection status to "CANCELLED"
			LoanSelection loanSelection = exception.getLoanSelection();
			if (loanSelection != null) {
				commonLoanSelectionService.cancelLoanSelection(exception.getLoanSelection());
			}

		} else if (ImmutableList.of(
			ReviewProcessExceptionTypeCodes.BATCH_ASSIGNMENT,
				ReviewProcessExceptionTypeCodes.BATCH_DISTRIBUTION
			).contains(exception.getReviewProcessExceptionTypeRef().getCode())) {
			// If this Exception belongs to Batch the handle all batch related entries.
			Batch batch = exception.getBatch();
			if (batch != null) {
				commonBatchService.cancelBatch(batch.getBatchId());
			}

		} else if (ImmutableList.of(
				ReviewProcessExceptionTypeCodes.REVIEW_LEVEL_ASSIGNMENT,
				ReviewProcessExceptionTypeCodes.HQ_ESCALATION
			).contains(exception.getReviewProcessExceptionTypeRef().getCode())) {
			// Change the Review and any active review level status to "CANCELLED"
			ReviewLevel reviewLevel = exception.getReviewLevel();
			if (reviewLevel != null) {
				commonReviewService.cancelReview(reviewLevel.getReview(), CancellationReasonCodes.OTHER);
			}
		}

		commonExceptionService.markExceptionResolved(exception);

		return new ResponseEntity(HttpStatus.OK);
	}

	public void exceptionLocationCheck(ReviewProcessException exception) {
		List<String> roles = securityService.getRoles();
		// HQ_ADMIN has access to all locations. Only check when it's not HQ_ADMIN
		if (!roles.contains(Roles.ROLE_HQ_ADMIN)) {
			LoanSelection loanSelection = exception.getLoanSelection();
			String reviewLocationId = "";
			if (loanSelection != null) {
				reviewLocationId = loanSelection.getReviewLocation().getReviewLocationId();
			} else if (exception.getBatch() != null) {
				reviewLocationId = exception.getBatch().getReviewLocation().getReviewLocationId();
			} else if (exception.getReviewLevel() != null) {
				reviewLocationId = exception.getReviewLevel().getReviewLocation().getReviewLocationId();
			} else {
				throw new RuntimeException("Exception " + exception.getReviewProcessExceptionId() + " had loanSelection, batch, and review all null");
			}
			if (!securityService.getReviewLocation().getReviewLocationId().equalsIgnoreCase(reviewLocationId)) {
				throw new BadRequestException("If user has the role of Review Location Admin or Program Assistant, requests can only be created or updated for the same location.");
			}
		}
	}

	public void exceptionLocationCheckPost(ExceptionDTO exceptionDTO) {
		List<String> roles = securityService.getRoles();
		// HQ_ADMIN has access to all locations. Only check when it's not HQ_ADMIN
		if (!roles.contains(Roles.ROLE_HQ_ADMIN)) {
			if (!securityService.getReviewLocation().getReviewLocationId().equalsIgnoreCase(exceptionDTO.getReviewLocationId())) {
				throw new BadRequestException("If user has the role of Review Location Admin or Program Assistant, requests can only be created or updated for the same location.");
			}
		}
	}

	public void exceptionLocationCheck(String reviewLocationId) {
		List<String> roles = securityService.getRoles();
		// HQ_ADMIN has access to all locations. Only check when it's not
		// HQ_ADMIN
		if (!roles.contains(Roles.ROLE_HQ_ADMIN)) {
			if (!securityService.getReviewLocation().getReviewLocationId().equalsIgnoreCase(reviewLocationId)) {
				throw new BadRequestException("If user has the role of Review Location Admin or Program Assistant, requests can only be created or updated for the same location.");
			}
		}
	}

	public void exceptionTypeCheck(ReviewProcessExceptionTypeRef exceptionType) {
		List<String> roles = securityService.getRoles();
		if (roles.contains(Roles.ROLE_HQ_ADMIN)) {
			return; // HQ_ADMIN has rights to all exceptions

		} else if (roles.contains(Roles.ROLE_REVIEW_LOCATION_ADMIN)) {
			if (!ImmutableList.of(
				ReviewProcessExceptionTypeCodes.BATCH_ASSIGNMENT,
				ReviewProcessExceptionTypeCodes.REVIEW_LEVEL_ASSIGNMENT,
				ReviewProcessExceptionTypeCodes.BINDER_REQUEST_ERROR,
				ReviewProcessExceptionTypeCodes.BINDER_REQUEST_PAST_DUE,
				ReviewProcessExceptionTypeCodes.HQ_ESCALATION
			).contains(exceptionType.getCode())) {
				throw new ForbiddenException("Exceptions can not be created or updated by a Review Location Admin.");
			}

		} else if (roles.contains(Roles.ROLE_PROGRAM_ASSISTANT)) {
			if (!exceptionType.getCode().equalsIgnoreCase(ReviewProcessExceptionTypeCodes.BINDER_REQUEST_PAST_DUE)) {
				throw new ForbiddenException("Exceptions can not be created or updated by a Program Assistant.");
			}
		}
	}

}
