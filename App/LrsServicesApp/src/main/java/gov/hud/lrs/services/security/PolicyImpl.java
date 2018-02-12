package gov.hud.lrs.services.security;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableList;

import gov.hud.lrs.common.dto.LrsApiV1DTOs.BatchDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.BinderDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.LenderMonitoringDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.ManualSelectionDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.ReviewRequestByLenderDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.ReviewerCreationDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.ReviewerDTO;
import gov.hud.lrs.common.entity.Personnel;
import gov.hud.lrs.common.entity.Review;
import gov.hud.lrs.common.entity.Batch;
import gov.hud.lrs.common.entity.BatchStatusRef;
import gov.hud.lrs.common.entity.BinderRequest;
import gov.hud.lrs.common.entity.LenderRequest;
import gov.hud.lrs.common.entity.ReviewLevel;
import gov.hud.lrs.common.entity.ReviewTypeRef;
import gov.hud.lrs.common.enumeration.ReviewLevelStatusCodes;
import gov.hud.lrs.common.enumeration.ReviewLevelTypeCodes;
import gov.hud.lrs.common.enumeration.ReviewTypeCodes;
import gov.hud.lrs.common.enumeration.Roles;
import gov.hud.lrs.common.exception.BadRequestException;
import gov.hud.lrs.common.exception.ConflictException;
import gov.hud.lrs.common.exception.ForbiddenException;
import gov.hud.lrs.common.exception.NotFoundException;
import gov.hud.lrs.common.repository.BatchRepository;
import gov.hud.lrs.common.repository.BinderRequestRepository;
import gov.hud.lrs.common.repository.LenderRequestRepository;
import gov.hud.lrs.common.repository.PersonnelAssignmentTypeRepository;
import gov.hud.lrs.common.repository.PersonnelRepository;
import gov.hud.lrs.common.repository.ReviewLevelRepository;
import gov.hud.lrs.common.repository.ReviewLocationRepository;
import gov.hud.lrs.common.repository.ReviewRepository;
import gov.hud.lrs.common.repository.ReviewTypeRefRepository;
import gov.hud.lrs.common.security.SecurityService;

@Component
public class PolicyImpl {
	private static final String ESCALATION_SKILL = "escalationSkill";
	private static final String HQ_ESCALATION_SKILL = "hqEscalationSkill";
	private static final String ANY_ESCALATION_SKILL = "anyEscalationSkill";
	private static final String FORCED_INDEMNIFICATION_SKILL = "forcedIndemnificationSkill";
	private static final String INDEMNIFICATION_SKILL = "indemnificationSkill";

	@Value("${lrs.devMode}") private String devModeFlag;

	@Autowired private ReviewLevelRepository reviewLevelRepository;
	@Autowired private PersonnelRepository personnelRepository;
	@Autowired private ReviewTypeRefRepository reviewTypeRefRepository;
	@Autowired private PersonnelAssignmentTypeRepository personnelAssignmentTypeRepository;
	@Autowired private BinderRequestRepository binderRequestRepository;
	@Autowired private ReviewRepository reviewRepository;
	@Autowired private LenderRequestRepository lenderRequestRepository;
	@Autowired private BatchRepository batchRepository;
	@Autowired private ReviewLocationRepository reviewLocationRepository;

	@Autowired private SecurityService securityService;

	public void validate(ArrayList<String> policies, Object data, String reviewId, String reviewLevelId, String reviewerId, String binderId, String batchId) {
		if (policies.contains("devMode")) {
			validateDevMode();
		}

		if (policies.contains("activeReviewLevelAssignedToUser")) {
			validateActiveReviewLevelAssignedToUser(reviewId);
		}

		if (policies.contains("activeReviewLevel")) {
			if (reviewLevelId == null) {
				reviewLevelId = reviewLevelRepository.findActiveByReviewId(reviewId).getReviewLevelId();
			}
			validateActiveReviewLevel(reviewLevelId);

		}

		if (policies.contains("reviewerChainOfCommand")) {
			String reviewerLocationId = null;
			if (data instanceof ReviewerDTO) {
				ReviewerDTO dto = (ReviewerDTO) data;
				reviewerLocationId = dto.getLocationId();
				validateReviewerChainOfCommand(reviewerLocationId);
			} else if (data instanceof ReviewerCreationDTO) {
				ReviewerCreationDTO dto = (ReviewerCreationDTO) data;
				reviewerLocationId = dto.getLocationId();
				validateReviewerChainOfCommand(reviewerLocationId);
			} else if (data == null && StringUtils.isNotBlank(reviewerId)) {
				validateReviewerChainOfCommandForId(reviewerId);
			} else {
				throw new BadRequestException();
			}
		}
		if (policies.contains("reviewerSupervisorGeographyChainOfCommand")) {
			String reviewerLocationId, reportsToPersonnelId;
			reviewerLocationId = reportsToPersonnelId = null;
			if (data instanceof ReviewerDTO) {
				ReviewerDTO dto = (ReviewerDTO) data;
				reviewerLocationId = dto.getLocationId();
				reportsToPersonnelId = dto.getReportsTo();
			} else if (data instanceof ReviewerCreationDTO) {
				ReviewerCreationDTO dto = (ReviewerCreationDTO) data;
				reviewerLocationId = dto.getLocationId();
				reportsToPersonnelId = dto.getReportsTo();
			} else {
				throw new BadRequestException();
			}
			validateReviewerSupervisorGeographyChainOfCommand(reviewerLocationId, reportsToPersonnelId);
		}
		if (policies.contains("reviewerVetterGeography")) {
			String reviewerLocationId, vettedByPersonnelId;
			reviewerLocationId = vettedByPersonnelId = null;
			if (data instanceof ReviewerDTO) {
				ReviewerDTO dto = (ReviewerDTO) data;
				reviewerLocationId = dto.getLocationId();
				vettedByPersonnelId = dto.getVettedBy();
			} else {
				throw new BadRequestException();
			}
			validateReviewerVetterGeography(reviewerLocationId, vettedByPersonnelId);
		}
		if (policies.contains("requestGeography")) {
			String requestReviewLocationId = null;
			if (data instanceof ManualSelectionDTO) {
				ManualSelectionDTO dto = (ManualSelectionDTO) data;
				requestReviewLocationId = dto.getReviewLocation();
			} else if (data instanceof LenderMonitoringDTO) {
				LenderMonitoringDTO dto = (LenderMonitoringDTO) data;
				requestReviewLocationId = dto.getLocationId();
			} else if (data instanceof BinderDTO) {
				BinderDTO dto = (BinderDTO) data;
				requestReviewLocationId = dto.getReviewLocationId();
			} else if (binderId != null) {
				BinderRequest binder = binderRequestRepository.findOne(binderId);
				requestReviewLocationId = binder.getLoanSelection().getReviewLocation().getReviewLocationId();
			} else {
				throw new BadRequestException();
			}
			validateRequestGeography(requestReviewLocationId);
		}
		if (policies.contains("batchOwnerGeographyAndSkill")) {
			String requestReviewLocationId, batchOwnerId;
			requestReviewLocationId = batchOwnerId = null;
			if (data instanceof ManualSelectionDTO) {
				ManualSelectionDTO dto = (ManualSelectionDTO) data;
				requestReviewLocationId = dto.getReviewLocation();
				for (ReviewRequestByLenderDTO requestDto : dto.getCasesForReviewByLender()) {
					BatchDTO batchDto = requestDto.getBatchInfo();
					if (batchDto != null) {
						batchOwnerId = batchDto.getBatchOwner();
						validateBatchOwnerGeographyAndSkill(requestReviewLocationId, batchOwnerId);
					}
				}
			} else if (data instanceof LenderMonitoringDTO) {
				LenderMonitoringDTO dto = (LenderMonitoringDTO) data;
				requestReviewLocationId = dto.getLocationId();
				batchOwnerId = dto.getBatchOwner();
				if (batchOwnerId != null) {
					validateBatchOwnerGeographyAndSkill(requestReviewLocationId, batchOwnerId);
				}
			} else {
				throw new BadRequestException();
			}
		}
		if (policies.contains("batchParticipantsGeography")) {
			String requestReviewLocationId = null;
			List<String> batchParticipantIds = null;
			if (data instanceof ManualSelectionDTO) {
				ManualSelectionDTO dto = (ManualSelectionDTO) data;
				requestReviewLocationId = dto.getReviewLocation();
				for (ReviewRequestByLenderDTO requestDto : dto.getCasesForReviewByLender()) {
					BatchDTO batchDto = requestDto.getBatchInfo();
					if ((batchDto != null) && (batchParticipantIds != null)) {
						batchParticipantIds = new ArrayList<String>();
						batchParticipantIds.addAll(batchDto.getBatchTeamMembers());
						validateBatchParticipantsGeography(requestReviewLocationId, batchParticipantIds);
					}
				}
			} else if (data instanceof LenderMonitoringDTO) {
				LenderMonitoringDTO dto = (LenderMonitoringDTO) data;
				requestReviewLocationId = dto.getLocationId();
				batchParticipantIds = new ArrayList<String>();
				batchParticipantIds = dto.getBatchTeamMembers();
				if (batchParticipantIds != null) {
					validateBatchParticipantsGeography(requestReviewLocationId, batchParticipantIds);
				}
			} else {
				throw new BadRequestException();
			}
		}
		if (policies.contains("reviewForLender")) {
			validateReviewForLender(reviewId);
		}
		if (policies.contains("activeLenderRequest")) {
			validateActiveLenderRequest(reviewLevelId);
		}
		if (policies.contains("lenderBatchOwnerActive")) {
			validateLenderBatchOwnerActive(batchId);
		}
		if (policies.contains("lenderBatchOwner")) {
			validateLenderBatchOwner(batchId);
		}
		if (policies.contains("batchOwner")) {
			validateBatchOwner(reviewId, batchId);
		}

		if (policies.contains(ESCALATION_SKILL)) {
			validateHasEscalationSkill();
		}

		if (policies.contains(HQ_ESCALATION_SKILL)) {
			validateHasHqEscalationSkill();
		}

		if (policies.contains(ANY_ESCALATION_SKILL)) {
			validateHasAnyEscalationSkill();
		}

		if (policies.contains(FORCED_INDEMNIFICATION_SKILL)) {
			validateHasForcedIndemnificationSkill();
		}

		if (policies.contains(INDEMNIFICATION_SKILL)) {
			validateHasIndemnificationSkill();
		}
	}

	public void validateDevMode() {
		if (!Boolean.valueOf(devModeFlag)) {
			throw new BadRequestException("Attemped to access a development service but dev mode is not enabled.");
		}
	}

	public void validateActiveReviewLevelAssignedToUser(String reviewId) {
		Personnel loggedInUser = securityService.getPersonnel();
		ReviewLevel activeReviewLevel = reviewLevelRepository.findActiveByReviewId(reviewId);
		if (activeReviewLevel == null) {
			throw new RuntimeException("Review " + reviewId + " does not have an active review level.");	// this is a data error
		}
		if (!activeReviewLevel.getReviewerPersonnel().getPersonnelId().equals(loggedInUser.getPersonnelId())) {
			throw new BadRequestException("Active review level " + activeReviewLevel.getReviewLevelId() + " is not assigned to the logged in user.");
		}
	}

	public void validateActiveReviewLevel(String reviewLevelId) {
		ReviewLevel reviewLevel = reviewLevelRepository.findOne(reviewLevelId);
		if (reviewLevel == null) {
			throw new BadRequestException("ReviewLevel " + reviewLevelId + " does not exist.");
		}
		if (!ImmutableList.of(
			ReviewLevelStatusCodes.ASSIGNED,
			ReviewLevelStatusCodes.IN_PROGRESS,
			ReviewLevelStatusCodes.PENDING_BATCH_REVIEW
		).contains(reviewLevel.getReviewLevelStatusRef().getCode())) {
			throw new BadRequestException("Review level " + reviewLevelId + " is not active.");
		}
	}

	public void validateReviewerChainOfCommandForId(String reviewerId) {
		Personnel reviewer = personnelRepository.findOne(reviewerId);
		validateReviewerChainOfCommand(reviewer.getReviewLocation().getReviewLocationId());
	}

	public void validateReviewerChainOfCommand(String reviewerLocationId) {
		if (
			!securityService.hasRole(Roles.ROLE_HQ_ADMIN) &&
			!securityService.getReviewLocation().getReviewLocationId().equals(reviewerLocationId)
		) {
			throw new ForbiddenException("Can only operate on reviewers in the same location.");
		}
	}

	public void validateReviewerSupervisorGeographyChainOfCommand(String reviewerLocationId, String reportsToPersonnelId) {
		if (!reviewLocationRepository.findOne(reviewerLocationId).getLocationName().equals("HQ")) {
			if (StringUtils.isNotBlank(reportsToPersonnelId)) {
				Personnel supervisor = personnelRepository.findOne(reportsToPersonnelId);
				if (
					(!reviewerLocationId.equals(supervisor.getReviewLocation().getReviewLocationId())	&&
					!"HQ".equalsIgnoreCase(supervisor.getReviewLocation().getLocationName()))
				) {
					throw new ForbiddenException("Can only operator on reviewers at location or at HQ.");
				}
			}
		}
	}

	public void validateReviewerVetterGeography(String reviewerLocationId, String vettedByPersonnelId) {
		if (StringUtils.isNotBlank(vettedByPersonnelId)) {
			Personnel vetter = personnelRepository.findOne(vettedByPersonnelId);
			if (!reviewerLocationId.equals(vetter.getReviewLocation().getReviewLocationId())) {
				throw new BadRequestException("Reviewers can only be vetted by users in the same location.");
			}
		}
	}

	public void validateRequestGeography(String requestReviewLocationId) {
		if (!securityService.hasRole(Roles.ROLE_HQ_ADMIN)) {
			Personnel loggedInUser = securityService.getPersonnel();
			if (!loggedInUser.getReviewLocation().getReviewLocationId().equals(requestReviewLocationId)) {
				throw new ForbiddenException("If user has the role of Review Location Admin, requests can only be created or updated for the same location.");
			}
		}
	}

	public void validateBatchOwnerGeographyAndSkill(String requestReviewLocationId, String batchOwnerId) {
		if (StringUtils.isNotBlank(batchOwnerId)) {
			Personnel batchOwner = personnelRepository.findOne(batchOwnerId);
			if (!requestReviewLocationId.equals(batchOwner.getReviewLocation().getReviewLocationId())) {
				throw new BadRequestException("Batch owners must be in the same review location as the request.");
			}

			ReviewTypeRef operational = reviewTypeRefRepository.findByReviewTypeCd(ReviewTypeCodes.OPERATIONAL);
			if (personnelAssignmentTypeRepository.findByPersonnelAndAssignmentTypeAdminAssignmentTypeRefId(batchOwner, operational.getReviewTypeId()) == null) {
				throw new BadRequestException("Batch owners must have the Operational Review Type assignment type.");
			}
		}
	}

	public void validateBatchParticipantsGeography(String requestReviewLocationId, List<String> batchParticipantIds) {
		if (batchParticipantIds != null) {
			for (String participantId : batchParticipantIds) {
				Personnel batchParticipant = personnelRepository.findOne(participantId);
				if (!requestReviewLocationId.equals(batchParticipant.getReviewLocation().getReviewLocationId())) {
					throw new BadRequestException("Batch participants must be in the same review location as the request.");
				}
			}
		}
	}

	public void validateReviewForLender(String reviewId) {
		Review review = reviewRepository.findOne(reviewId);
		if (review == null) {
			throw new NotFoundException("Review " + reviewId + " does not exist.");
		}
        if (securityService.isLender()) {
			String lenderId = securityService.getFhacUser().getLenderId();
			if (!lenderId.equals(review.getLender().getLenderId())) {
				throw new ForbiddenException("Lender user must match the lender responsible for the case.");
			}
        }
	}

	public void validateActiveLenderRequest(String reviewLevelId) {
		LenderRequest lenderRequest = lenderRequestRepository.findActiveByReviewLevelId(reviewLevelId);
		if (lenderRequest == null) {
			throw new ConflictException("Review level " + reviewLevelId + " does not have an active lender request.");
		}
	}

	public void validateLenderBatchOwnerActive(String batchId) {
		Batch batch = commonValidateLenderBatchOwner(batchId);
		if (!BatchStatusRef.PENDING_LENDER_RESPONSE.equals(batch.getBatchStatusRef().getCode())) {
			throw new ConflictException("Batch " + batchId + " does not have a status of pending lender response.");
		}
	}

	public void validateLenderBatchOwner(String batchId) {
		commonValidateLenderBatchOwner(batchId);
	}

	public void validateBatchOwner(String reviewId, String batchId) {
		Batch batch = null;
		if (batchId == null) {
			Review review = reviewRepository.findOne(reviewId);
			if (review == null) {
				throw new NotFoundException("Review " + reviewId + " does not exist.");
			}
			batch = review.getBatch();
		} else {
			batch = batchRepository.findOne(batchId);
		}
		if (batch == null) {
			throw new NotFoundException("Batch " + batchId + " does not exist.");
		}
		String batchOwnerId = batch.getOwnerPersonnel().getPersonnelId();
		Personnel loggedInUser = securityService.getPersonnel();
		if (!batchOwnerId.equals(loggedInUser.getPersonnelId())) {
			throw new BadRequestException("Personnel : " + batchOwnerId + " is not batch owner to the batch :" + batchId);
		}
	}
	
	private Batch commonValidateLenderBatchOwner(String batchId) {
		Batch batch = batchRepository.findOne(batchId);
		if (batch == null) {
			throw new NotFoundException("Batch " + batchId + " does not exist.");
		}
		if (securityService.isLender()) {
			String lenderId = securityService.getFhacUser().getLenderId();
			if (!lenderId.equals(batch.getLender().getLenderId())) {
				throw new ForbiddenException("Lender " + lenderId + " is not the lender responsible for the batch.");
			}
		}
		return batch;
	}

	public void validateHasEscalationSkill() {
		String personnelId = securityService.getPersonnel().getPersonnelId();
		int count = personnelAssignmentTypeRepository.countByPersonnelIdAndReviewLevelType(personnelId, ReviewLevelTypeCodes.ESCALATION);
		if (count != 1) {
			throw new ForbiddenException("Reviewer " + personnelId + " does not have the Escalation skill.");
		}
	}

	public void validateHasHqEscalationSkill() {
		String personnelId = securityService.getPersonnel().getPersonnelId();
		int count = personnelAssignmentTypeRepository.countByPersonnelIdAndReviewLevelType(personnelId, ReviewLevelTypeCodes.HQ_ESCALATION);
		if (count != 1) {
			throw new ForbiddenException("Reviewer " + personnelId + " does not have the HQ Escalation skill.");
		}
	}

	public void validateHasAnyEscalationSkill() {
		String personnelId = securityService.getPersonnel().getPersonnelId();
		int escalationCount = personnelAssignmentTypeRepository.countByPersonnelIdAndReviewLevelType(personnelId, ReviewLevelTypeCodes.ESCALATION);
		int hqEscalationCount = personnelAssignmentTypeRepository.countByPersonnelIdAndReviewLevelType(personnelId, ReviewLevelTypeCodes.HQ_ESCALATION);
		if ((escalationCount + hqEscalationCount) < 1) {
			throw new ForbiddenException("Reviewer " + personnelId + " does not have the Escalation or HQ Escalation skill.");
		}
	}

	public void validateHasForcedIndemnificationSkill() {
		String personnelId = securityService.getPersonnel().getPersonnelId();
		int count = personnelAssignmentTypeRepository.countByPersonnelIdAndReviewLevelType(personnelId, ReviewLevelTypeCodes.FORCE_INDEMNIFICATION);
		if (count != 1) {
			throw new ForbiddenException("Reviewer " + personnelId + " does not have the Forced Indemnification skill.");
		}
	}

	public void validateHasIndemnificationSkill() {
		String personnelId = securityService.getPersonnel().getPersonnelId();
		int count = personnelAssignmentTypeRepository.countByPersonnelIdAndReviewLevelType(personnelId, ReviewLevelTypeCodes.INDEMNIFICATION);
		if (count != 1) {
			throw new ForbiddenException("Reviewer " + personnelId + " does not have the Indemnification skill.");
		}
	}
}
