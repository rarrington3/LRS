package gov.hud.lrs.services.bizservice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ImmutableList;

import gov.hud.lrs.common.dto.LrsApiV1DTOs.BatchDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.CaseActivityDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.DocumentDTO;
import gov.hud.lrs.common.entity.Batch;
import gov.hud.lrs.common.entity.BatchPersonnel;
import gov.hud.lrs.common.entity.BatchStatusRef;
import gov.hud.lrs.common.entity.Document;
import gov.hud.lrs.common.entity.Lender;
import gov.hud.lrs.common.entity.LoanSelection;
import gov.hud.lrs.common.entity.LoanSelectionPending;
import gov.hud.lrs.common.entity.Review;
import gov.hud.lrs.common.enumeration.LoanSelectionStatusCodes;
import gov.hud.lrs.common.enumeration.ReviewTypeCodes;
import gov.hud.lrs.common.exception.BadRequestException;
import gov.hud.lrs.common.exception.ConflictException;
import gov.hud.lrs.common.exception.NotFoundException;
import gov.hud.lrs.common.repository.BatchRepository;
import gov.hud.lrs.common.repository.DocumentRepository;
import gov.hud.lrs.common.security.SecurityService;
import gov.hud.lrs.common.service.CommonReviewService;
import gov.hud.lrs.common.service.DocumentRepositoryService;
import gov.hud.lrs.common.util.DateUtils;

@Service
public class BatchService {

	@Autowired private DocumentRepository documentRepository;
	@Autowired private BatchRepository batchRepository;

	@Autowired private CommonReviewService commonReviewService;
	@Autowired private DocumentRepositoryService documentRepositoryService;
	@Autowired private ReviewService reviewService;
	@Autowired private SecurityService securityService;

	@Autowired private WorkflowClient workflowClient;

	public List<Batch> getReviewerBatches() {
		return batchRepository.findByOwnerOrBatchTeamMemberPersonnelId(securityService.getPersonnel().getPersonnelId());
	}

	public List<Batch> getReviewerBatchesCompleted() {
		return batchRepository.findByOwnerOrBatchTeamMemberPersonnelIdCompleted(securityService.getPersonnel().getPersonnelId());
	}
	
	public List<Batch> getLocationBatches(String locationId) {
		// For the user's own location, we want all batches belonging to that location, owned by the user, or where the user is a team member
		if (securityService.getPersonnel().getReviewLocation().getReviewLocationId().equals(locationId)) {
			return batchRepository.findByOwnerOrBatchTeamMemberOrReviewLocation(securityService.getPersonnel().getPersonnelId(), locationId);
		} else {
			return batchRepository.findByReviewLocation(locationId);
		}
	}

	public List<Batch> getLocationBatchesCompleted(String locationId) {
		// For the user's own location, we want all batches belonging to that location, owned by the user, or where the user is a team member
		if (securityService.getPersonnel().getReviewLocation().getReviewLocationId().equals(locationId)) {
			return batchRepository.findByOwnerOrBatchTeamMemberOrReviewLocationCompleted(securityService.getPersonnel().getPersonnelId(), locationId);
		} else {
			return batchRepository.findByReviewLocationCompleted(locationId);
		}
	}
	
	public List<Batch> getLenderBatches() {
		List<String> batchStatusCodes = new ArrayList<String>();
		batchStatusCodes.add(BatchStatusRef.COMPLETED);
		batchStatusCodes.add(BatchStatusRef.CANCELLED);

		return batchRepository.findByLenderLenderIdAndBatchStatusRefCodeNotIn(securityService.getLenderId(), batchStatusCodes);
	}

	public List<Batch> getLenderBatchesCompleted() {
		List<String> batchStatusCodes = new ArrayList<String>();
		batchStatusCodes.add(BatchStatusRef.COMPLETED);
		
		return batchRepository.findByLenderLenderIdAndBatchStatusRefCodeIn(securityService.getLenderId(), batchStatusCodes);
	}
	
	public Batch getLenderBatchById(String batchId) {
		Batch batch = batchRepository.findOne(batchId);
		if (batch == null) {
			throw new NotFoundException("Unknown batch: " + batchId);
		}

		return batch;
	}

	@Transactional
	public Review submitOperationalReviewDocuments(String batchId) {
		Batch batch = batchRepository.findOne(batchId);
		if (batch == null) {
			throw new BadRequestException("No Batch for batchId " + batchId);
		}
		if (
			(batch.getRequestOperationalDocumentsInd() == null) &&
			batch.getRequestOperationalDocumentsInd().equals('N')
		) {
			throw new ConflictException("Operational documents for Batch " + batchId + " were not requested");
		}
		if (
			(batch.getReceivedOperationalDocumentsInd() != null) &&
			batch.getReceivedOperationalDocumentsInd().equals('Y')
		) {
			throw new ConflictException("Operational documents for Batch " + batchId + " has already been received");
		}

		List<Document> documents = documentRepository.findByBatch(batch);
		// TODO: are documents required? do they have to have at least one?

		batch.setReceivedOperationalDocumentsInd('Y');

		Review review = commonReviewService.createOperationalReview(batch);
		commonReviewService.assignReviewLevel(review.getReviewLevels().iterator().next(), batch.getOwnerPersonnel());

		for (Document document : documents) {
			documentRepositoryService.uploadDocumentFile(document);
		}

		return review;
	}

	public void submitBatch(String batchId) {
		workflowClient.put("/api/v1/batches/" + batchId + "/submit", null, null);
	}

	public void submitLenderBatch(String batchId) {
		workflowClient.put("/api/v1/batches/" + batchId + "/submit/lender", null, null);
	}

	public void cancelBatch(Batch batch) {
		cancelBatch(batch.getBatchId());
	}

	public void cancelBatch(String batchId) {
		workflowClient.put("/api/v1/batches/" + batchId + "/cancel", null, null);
	}

	public List<BatchDTO> convertBatchesToBatchDTOs(Collection<Batch> batches) {
		List<BatchDTO> batchDTOs = new ArrayList<BatchDTO>();
		for (Batch batch : batches) {
			batchDTOs.add(convertBatchToBatchDTO(batch));
		}
		return batchDTOs;
	}

	public BatchDTO convertBatchToBatchDTO(Batch batch) {
		BatchDTO batchDTO = new BatchDTO();
		batchDTO.setBatchId(batch.getBatchId());
		batchDTO.setBatchNumber(batch.getBatchReferenceId());
		batchDTO.setStatus(batch.getBatchStatusRef().getDescription());
		batchDTO.setCreationDate(DateUtils.convertDateToNoonUtcDate(batch.getCreatedTs()));
		batchDTO.setSelectionReason(batch.getSelectionReason().getDescription());
		batchDTO.setReviewType(batch.getReviewTypeRef().getDescription());
		batchDTO.setReviewLevel(batch.getReviewLevelTypeRef().getDescription());
		batchDTO.setIteration(String.valueOf(batch.getIterationNumber()));
		batchDTO.setRequestOperationalReview(batch.getOperationalReviewInd() == 'Y');
		batchDTO.setRequestOperationalDocuments(new Character('Y').equals(batch.getRequestOperationalDocumentsInd()));
		batchDTO.setOperationalDocumentsDueDate(DateUtils.convertDateToNoonUtcDate(batch.getOperationalDocumentsDueDt()));
		batchDTO.setSecondaryId(batch.getSecondaryReference());
		batchDTO.setOperationalReviewGuidance(batch.getOperationalReviewGuidance());
		batchDTO.setCaseCount(BigDecimal.valueOf(batch.getLoanSelectionPendings().size() + batch.getLoanSelections().size()));
		
		if (batch.getOriginalOwnerPersonnel() != null) {
			batchDTO.setBatchOwner(batch.getOwnerPersonnel().getPersonnelId());
		}

		Lender lender = batch.getLender();
		batchDTO.setLenderId(lender.getLenderId());
		batchDTO.setLenderName(lender.getName());

		// PERF TODO: this is slow and results in lots of sql round trips. make faster.
		List<String> batchTeamMembers = new ArrayList<String>();
		for (BatchPersonnel batchPersonnel : batch.getBatchPersonnels()) {
			batchTeamMembers.add(batchPersonnel.getPersonnel().getPersonnelId());
		}
		batchDTO.setBatchTeamMembers(batchTeamMembers);
		batchDTO.setSecondaryId(batch.getSecondaryReference());

		List<Review> reviewsToConvert = new ArrayList<Review>();
		for (Review review : batch.getReviews()) {
			if (!review.getReviewTypeRef().getReviewTypeCd().equals(ReviewTypeCodes.OPERATIONAL)) {
				reviewsToConvert.add(review);
			} else {
				batchDTO.setOperationalReview(reviewService.convertReviewToReviewDTO(review));
			}
		}
		batchDTO.setReviews(reviewService.convertReviewsToReviewDTOs(reviewsToConvert, false));

		List<CaseActivityDTO> outstandingCaseActivities = new ArrayList<CaseActivityDTO>();
		for (LoanSelectionPending loanSelectionPending : batch.getLoanSelectionPendings()) {
			CaseActivityDTO caseActivityDto = new CaseActivityDTO();
			caseActivityDto.setSelectionDate(loanSelectionPending.getSelectionDt());
			caseActivityDto.setSelectionStatus("Pending Selection");
			caseActivityDto.setCaseNumber(loanSelectionPending.getCaseNumber());
			outstandingCaseActivities.add(caseActivityDto);
		}
		for (LoanSelection loanSelection : batch.getLoanSelections()) {
			String loanSelectionStatusCode = loanSelection.getLoanSelectionStatusRef().getCode();
			if (LoanSelectionStatusCodes.SELECTED.equals(loanSelectionStatusCode) ||
					LoanSelectionStatusCodes.DISTRIBUTED.equals(loanSelectionStatusCode) ||
					LoanSelectionStatusCodes.REQUESTED.equals(loanSelectionStatusCode) ||
					LoanSelectionStatusCodes.EXCEPTION.equals(loanSelectionStatusCode)
					) {
				CaseActivityDTO caseActivityDto = new CaseActivityDTO();
				caseActivityDto.setDistributionDate(loanSelection.getDistributionDt());
				caseActivityDto.setReceivedDate(loanSelection.getReceivedDt());
				caseActivityDto.setRequestedDate(loanSelection.getRequestedDtTm());
				caseActivityDto.setSelectionDate(loanSelection.getSelectionDt());
				caseActivityDto.setSelectionStatus(loanSelection.getLoanSelectionStatusRef().getDescription());
				caseActivityDto.setCaseNumber(loanSelection.getCaseNumber());
				outstandingCaseActivities.add(caseActivityDto);
			}
		}
		batchDTO.setOutstandingCaseActivity(outstandingCaseActivities);

		List<DocumentDTO> operationalDocuments = new ArrayList<DocumentDTO>();
		for (Document document : batch.getDocuments()) {
			DocumentDTO documentDTO = new DocumentDTO();
			documentDTO.setDocumentId(document.getDocumentId());
			documentDTO.setDocumentType(document.getDocumentTypeRef().getDescription());
			documentDTO.setFileName(document.getFileName());
			operationalDocuments.add(documentDTO);
		}
		batchDTO.setOperationalDocuments(operationalDocuments);

		return batchDTO;
	}

}
