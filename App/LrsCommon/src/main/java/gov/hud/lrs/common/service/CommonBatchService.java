package gov.hud.lrs.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.hud.lrs.common.entity.Batch;
import gov.hud.lrs.common.entity.BatchPersonnel;
import gov.hud.lrs.common.entity.BatchPersonnelId;
import gov.hud.lrs.common.entity.BatchStatusRef;
import gov.hud.lrs.common.entity.BinderRequest;
import gov.hud.lrs.common.entity.Lender;
import gov.hud.lrs.common.entity.LoanSelection;
import gov.hud.lrs.common.entity.LoanSelectionPending;
import gov.hud.lrs.common.entity.Personnel;
import gov.hud.lrs.common.entity.Review;
import gov.hud.lrs.common.entity.ReviewLevelIterationTimeframe;
import gov.hud.lrs.common.entity.ReviewLocation;
import gov.hud.lrs.common.entity.ReviewProcessException;
import gov.hud.lrs.common.entity.ReviewTypeRef;
import gov.hud.lrs.common.entity.SelectionReason;
import gov.hud.lrs.common.entity.SelectionRequest;
import gov.hud.lrs.common.enumeration.BinderRequestStatusCodes;
import gov.hud.lrs.common.enumeration.CancellationReasonCodes;
import gov.hud.lrs.common.enumeration.ReviewStatusCodes;
import gov.hud.lrs.common.enumeration.ReviewLevelTypeCodes;
import gov.hud.lrs.common.exception.BadRequestException;
import gov.hud.lrs.common.exception.ConflictException;
import gov.hud.lrs.common.exception.NotFoundException;
import gov.hud.lrs.common.repository.BatchPersonnelRepository;
import gov.hud.lrs.common.repository.BatchRepository;
import gov.hud.lrs.common.repository.BatchStatusRefRepository;
import gov.hud.lrs.common.repository.LoanSelectionPendingRepository;
import gov.hud.lrs.common.repository.PersonnelRepository;
import gov.hud.lrs.common.repository.ReviewLevelIterationTimeframeRepository;
import gov.hud.lrs.common.repository.ReviewLevelTypeRefRepository;
import gov.hud.lrs.common.repository.ReviewLocationRepository;
import gov.hud.lrs.common.security.SecurityService;
import gov.hud.lrs.common.service.BinderRequestService;

@Service
public class CommonBatchService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired private BatchRepository batchRepository;
	@Autowired private BatchPersonnelRepository batchPersonnelRepository;
	@Autowired private BatchStatusRefRepository batchStatusRefRepository;
	@Autowired private LoanSelectionPendingRepository loanSelectionPendingRepository;
	@Autowired private PersonnelRepository personnelRepository;
	@Autowired private ReviewLevelIterationTimeframeRepository reviewLevelIterationTimeframeRepository;
	@Autowired private ReviewLevelTypeRefRepository reviewLevelTypeRefRepository;
	@Autowired private ReviewLocationRepository reviewLocationRepository;

	@Autowired private CommonExceptionService commonExceptionService;
	@Autowired private CommonReviewService commonReviewService;
	@Autowired private CommonLoanSelectionService commonLoanSelectionService;
	@Autowired private BinderRequestService binderRequestService;
	@Autowired private SecurityService securityService;

	@Transactional
	public Batch createBatch(
		SelectionRequest selectionRequest,
		Lender lender,
		ReviewTypeRef reviewTypeRef,
		SelectionReason selectionReason,
		ReviewLocation reviewLocation,
		boolean operationalReviewRequested,
		boolean operationalReviewDocumentsRequested,
		String operationalReviewGuidance,
		String secondaryReference
	) {
		Batch batch = new Batch();
		batch.setSelectionRequest(selectionRequest);
		batch.setBatchStatusRef(batchStatusRefRepository.findByCode(BatchStatusRef.SELECTED));
		if (reviewLocation != null) {
			batch.setReviewLocation(reviewLocation);
			batch.setBatchStatusRef(batchStatusRefRepository.findByCode(BatchStatusRef.DISTRIBUTED));
		}
		batch.setLender(lender);
		batch.setSelectionReason(selectionReason);
		batch.setReviewTypeRef(reviewTypeRef);
		batch.setReviewLevelTypeRef(reviewLevelTypeRefRepository.findByReviewLevelCd(ReviewLevelTypeCodes.INITIAL));
		batch.setIterationNumber((short)1);
		Date now = new Date();
		String userId = securityService.getUserId();
		batch.setCreatedBy(userId);
		batch.setCreatedTs(now);
		batch.setUpdatedBy(userId);
		batch.setUpdatedTs(now);
		batchRepository.save(batch);
		
		setupBatchOperationalReviewFields(
			batch,
			operationalReviewRequested,
			operationalReviewDocumentsRequested,
			operationalReviewGuidance,
			secondaryReference
		);

		return batchRepository.save(batch);
	}

	@Transactional
	public void setupBatchOperationalReviewFields(
		Batch batch,
		boolean operationalReviewRequested,
		boolean operationalReviewDocumentsRequested,
		String operationalReviewGuidance,
		String secondaryReference
	) {
		if (operationalReviewRequested) {
			batch.setOperationalReviewInd('Y');
			batch.setReceivedOperationalDocumentsInd('N');
			batch.setSecondaryReference(secondaryReference);
			batch.setOperationalReviewGuidance(operationalReviewGuidance);

			if (operationalReviewDocumentsRequested) {
				batch.setRequestOperationalDocumentsInd('Y');
				ReviewLevelIterationTimeframe binderRequestReviewLevelIterationTimeframe = reviewLevelIterationTimeframeRepository.findByConsolidatedSelectionReasonCodeAndReviewLevelTypeRefReviewLevelCd(
					batch.getSelectionReason().getConsolidatedSelectionReason().getCode(),
					ReviewLevelTypeCodes.BINDER_REQUEST
				);
				batch.setOperationalDocumentsDueDt(commonReviewService.calcBinderRequestDueDate(binderRequestReviewLevelIterationTimeframe));
			} else {
				batch.setRequestOperationalDocumentsInd('N');
			}

		} else {
			batch.setOperationalReviewInd('N');
		}

		batch.setUpdatedBy(securityService.getUserId());
		batch.setUpdatedTs(new Date());
	}

	@Transactional
	public Batch distributeBatch(Batch batch, String reviewLocationId) {
		ReviewLocation reviewLocation = reviewLocationRepository.findOne(reviewLocationId);
		if (reviewLocation == null) {
			throw new BadRequestException("No ReviewLocation for id " + reviewLocationId);
		}
		batch.setReviewLocation(reviewLocation);
		batch.setBatchStatusRef(batchStatusRefRepository.findByCode(BatchStatusRef.DISTRIBUTED));
		batch.setUpdatedBy(securityService.getUserId());
		batch.setUpdatedTs(new Date());

		return batchRepository.save(batch);
	}

	@Transactional
	public void assignBatch(Batch batch, String batchOwnerPersonnelId, List<String> batchTeamPersonnelIds) {
		String userId = securityService.getUserId();
		Date now = new Date();

		Personnel batchOwner = personnelRepository.findOne(batchOwnerPersonnelId);
		if (batchOwner == null) {
			throw new BadRequestException("No batch owner Personnel for personnelId " + batchOwnerPersonnelId);
		}
		if (!batchOwner.getReviewLocation().getReviewLocationId().equals(batch.getReviewLocation().getReviewLocationId())) {
			throw new ConflictException(
				"Batch owner " + batchOwner.getPersonnelId() + " is at a different location (" + batchOwner.getReviewLocation().getReviewLocationId() + ") than " +
				"batch (" + batch.getReviewLocation().getReviewLocationId() + ")"
			);
		}
		batch.setOwnerPersonnel(batchOwner);
		batch.setOriginalOwnerPersonnel(batchOwner);
		batch.setBatchStatusRef(batchStatusRefRepository.findByCode(BatchStatusRef.ASSIGNED));
		batch.setUpdatedBy(userId);
		batch.setUpdatedTs(now);
		batch = batchRepository.save(batch);

		if ((batchTeamPersonnelIds != null) && (batchTeamPersonnelIds.size() < 1)) {
			throw new BadRequestException("At least one batch team member is required");
		}

		List<Personnel> batchTeamMemberPersonnelLists = personnelRepository.findByPersonnelIdIn(batchTeamPersonnelIds);
		if (batchTeamMemberPersonnelLists.size() != batchTeamPersonnelIds.size()) {
			throw new BadRequestException("One or more batch team members have invalid personnelIds");
		}

		for (Personnel personnel : batchTeamMemberPersonnelLists) {
			if (!personnel.getReviewLocation().getReviewLocationId().equals(batch.getReviewLocation().getReviewLocationId())) {
				throw new ConflictException(
					"Batch team member " + personnel.getPersonnelId() + " is at a different location (" + personnel.getReviewLocation().getReviewLocationId() + ") than " +
					"batch (" + batch.getReviewLocation().getReviewLocationId() + ")"
				);
			}
			BatchPersonnel batchPersonnel = new BatchPersonnel();
			BatchPersonnelId batchPersonnelId = new BatchPersonnelId();
			batchPersonnelId.setBatchId(batch.getBatchId());
			batchPersonnelId.setPersonnelId(personnel.getPersonnelId());
			batchPersonnel.setId(batchPersonnelId);
			batchPersonnel.setPersonnel(personnel);
			batchPersonnel.setCreatedBy(userId);
			batchPersonnel.setCreatedTs(now);
			batchPersonnel.setUpdatedBy(userId);
			batchPersonnel.setUpdatedTs(now);
			batchPersonnel = batchPersonnelRepository.save(batchPersonnel);
			batch.getBatchPersonnels().add(batchPersonnel);
		}

		// necessary because subsequent assignments in the same transaction will not take this one into account
		batchPersonnelRepository.flush();
	}

	@Transactional
	public void cancelBatch(String batchId) {
		Batch batch = batchRepository.findOne(batchId);
		if (batch == null) {
			throw new NotFoundException("Batch: " + batchId + " doesn't exist");
		}
		String statusCode = batch.getBatchStatusRef().getCode();
		if (
			statusCode.equals(BatchStatusRef.COMPLETED) ||
			statusCode.equals(BatchStatusRef.CANCELLED)
		) {
			throw new ConflictException("Batch " + batchId + " has status " + statusCode);
		}

		logger.debug("Cancelling batch: "
			+ batch.getBatchReferenceId()
			+ " (Batch Id: " + batchId
			+ ", Selection Pendings: " + batch.getLoanSelectionPendings().size()
			+ ", Loan Selections: " + batch.getLoanSelections().size()
			+ ", Reviews: " + batch.getReviews().size()
			+ ", Exceptions: " + batch.getReviewProcessExceptions().size()
			+ ")");

		for (LoanSelectionPending loanSelectionPending : batch.getLoanSelectionPendings()) {
			loanSelectionPendingRepository.delete(loanSelectionPending);
		}

		for (LoanSelection loanSelection : batch.getLoanSelections()) {
			commonLoanSelectionService.cancelLoanSelection(loanSelection);

			for (ReviewProcessException exception : loanSelection.getReviewProcessExceptions()) {
				commonExceptionService.markExceptionResolved(exception);
			}

			for (BinderRequest binderRequest : loanSelection.getBinderRequests()) {
				statusCode = binderRequest.getBinderRequestStatusRef().getCode();
				if (
					statusCode.equals(BinderRequestStatusCodes.REQUESTED) || 
					statusCode.equals(BinderRequestStatusCodes.EXCEPTION)
				) {
					binderRequestService.cancelBinderRequest(binderRequest.getBinderRequestId());
				}
			}
		}

		for (Review review : batch.getReviews()) {
			statusCode = review.getReviewStatusRef().getCode();
			if (
				!statusCode.equals(ReviewStatusCodes.COMPLETED) &&
				!statusCode.equals(ReviewStatusCodes.CANCELLED)
			) {
				commonReviewService.cancelReview(review, CancellationReasonCodes.OTHER);
			} else {
				logger.debug("Review was already completed or cancelled...");
			}
		}

		for (ReviewProcessException exception : batch.getReviewProcessExceptions()) {
			commonExceptionService.markExceptionResolved(exception);
		}

		batch.setBatchStatusRef(batchStatusRefRepository.findByCode(BatchStatusRef.CANCELLED));
		batch.setUpdatedBy(securityService.getUserId());
		batch.setUpdatedTs(new Date());
		batch = batchRepository.save(batch);

		logger.debug("Done Cancelling Batch: " + batch.getBatchReferenceId());
	}

}
