package gov.hud.lrs.common.service;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.hud.lrs.common.entity.Answer;
import gov.hud.lrs.common.entity.Batch;
import gov.hud.lrs.common.entity.BatchPersonnel;
import gov.hud.lrs.common.entity.BatchPersonnelId;
import gov.hud.lrs.common.entity.BatchStatusRef;
import gov.hud.lrs.common.entity.Document;
import gov.hud.lrs.common.entity.IndemnificationTypeRef;
import gov.hud.lrs.common.entity.LenderRequest;
import gov.hud.lrs.common.entity.LoanSelection;
import gov.hud.lrs.common.entity.LoanSelectionPending;
import gov.hud.lrs.common.entity.LoanSelectionCaseSummary;
import gov.hud.lrs.common.entity.Personnel;
import gov.hud.lrs.common.entity.QcOutcomeRef;
import gov.hud.lrs.common.entity.Review;
import gov.hud.lrs.common.entity.ReviewLevel;
import gov.hud.lrs.common.entity.ReviewLevelIterationTimeframe;
import gov.hud.lrs.common.entity.ReviewLevelReassignmentReasonRef;
import gov.hud.lrs.common.entity.ReviewLevelTypeRef;
import gov.hud.lrs.common.entity.ReviewLocation;
import gov.hud.lrs.common.entity.ReviewProcessException;
import gov.hud.lrs.common.entity.RvwLvlCaseSummary;
import gov.hud.lrs.common.entity.RvwLvlFinding;
import gov.hud.lrs.common.entity.RvwLvlFindingQuestion;
import gov.hud.lrs.common.entity.SelectionRequest;
import gov.hud.lrs.common.entity.SelectionRequestTypeRef;
import gov.hud.lrs.common.enumeration.IndemnificationTypeCodes;
import gov.hud.lrs.common.enumeration.LenderRequestStatusCodes;
import gov.hud.lrs.common.enumeration.LoanSelectionStatusCodes;
import gov.hud.lrs.common.enumeration.LoanTypeCodes;
import gov.hud.lrs.common.enumeration.RatingCodes;
import gov.hud.lrs.common.enumeration.ReviewLevelStatusCodes;
import gov.hud.lrs.common.enumeration.ReviewLevelTypeCodes;
import gov.hud.lrs.common.enumeration.ReviewScopeCodes;
import gov.hud.lrs.common.enumeration.ReviewStatusCodes;
import gov.hud.lrs.common.enumeration.ReviewTypeCodes;
import gov.hud.lrs.common.enumeration.SelectionReasonCodes;
import gov.hud.lrs.common.exception.BadRequestException;
import gov.hud.lrs.common.exception.ConflictException;
import gov.hud.lrs.common.exception.NotFoundException;
import gov.hud.lrs.common.repository.AnswerRepository;
import gov.hud.lrs.common.repository.BatchPersonnelRepository;
import gov.hud.lrs.common.repository.BatchRepository;
import gov.hud.lrs.common.repository.BatchStatusRefRepository;
import gov.hud.lrs.common.repository.CancellationReasonRefRepository;
import gov.hud.lrs.common.repository.DocumentRepository;
import gov.hud.lrs.common.repository.LenderRequestRepository;
import gov.hud.lrs.common.repository.LenderRequestStatusRefRepository;
import gov.hud.lrs.common.repository.LoanSelectionStatusRefRepository;
import gov.hud.lrs.common.repository.PersonnelRepository;
import gov.hud.lrs.common.repository.QaModelRepository;
import gov.hud.lrs.common.repository.RatingRefRepository;
import gov.hud.lrs.common.repository.ReviewLevelIterationTimeframeRepository;
import gov.hud.lrs.common.repository.ReviewLevelReassignmentReasonRefRepository;
import gov.hud.lrs.common.repository.ReviewLevelRepository;
import gov.hud.lrs.common.repository.ReviewLevelStatusRefRepository;
import gov.hud.lrs.common.repository.ReviewLevelTypeRefRepository;
import gov.hud.lrs.common.repository.ReviewLocationRepository;
import gov.hud.lrs.common.repository.ReviewProcessExceptionRepository;
import gov.hud.lrs.common.repository.ReviewRepository;
import gov.hud.lrs.common.repository.ReviewScopeRefRepository;
import gov.hud.lrs.common.repository.ReviewStatusRefRepository;
import gov.hud.lrs.common.repository.ReviewTypeRefRepository;
import gov.hud.lrs.common.repository.RvwLvlCaseSummaryRepository;
import gov.hud.lrs.common.repository.RvwLvlFindingQuestionRepository;
import gov.hud.lrs.common.repository.RvwLvlFindingRepository;
import gov.hud.lrs.common.repository.SelectionReasonRepository;
import gov.hud.lrs.common.security.SecurityService;

@Service
public class CommonReviewService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired private AnswerRepository answerRepository;
	@Autowired private CancellationReasonRefRepository cancellationReasonRefRepository;
	@Autowired private DocumentRepositoryService documentRepositoryService;
	@Autowired private LenderRequestRepository lenderRequestRepository;
	@Autowired private LenderRequestStatusRefRepository lenderRequestStatusRefRepository;
	@Autowired private PersonnelRepository personnelRepository;
	@Autowired private QaModelRepository qaModelRepository;
	@Autowired private RatingRefRepository ratingRefRepository;
	@Autowired private ReviewRepository reviewRepository;
	@Autowired private ReviewScopeRefRepository reviewScopeRefRepository;
	@Autowired private ReviewStatusRefRepository reviewStatusRefRepository;
	@Autowired private LoanSelectionStatusRefRepository loanSelectionStatusRefRepository;
	@Autowired private ReviewTypeRefRepository reviewTypeRefRepository;
	@Autowired private ReviewLevelRepository reviewLevelRepository;
	@Autowired private ReviewLevelReassignmentReasonRefRepository reviewLevelReassignmentReasonRefRepository;
	@Autowired private ReviewLevelStatusRefRepository reviewLevelStatusRefRepository;
	@Autowired private ReviewLevelTypeRefRepository reviewLevelTypeRefRepository;
	@Autowired private ReviewLevelIterationTimeframeRepository reviewLevelIterationTimeframeRepository;
	@Autowired private ReviewLocationRepository reviewLocationRepository;
	@Autowired private ReviewProcessExceptionRepository reviewProcessExceptionRepository;
	@Autowired private RvwLvlCaseSummaryRepository rvwLvlCaseSummaryRepository;
	@Autowired private RvwLvlFindingRepository rvwLvlFindingRepository;
	@Autowired private RvwLvlFindingQuestionRepository rvwLvlFindingQuestionRepository;
	@Autowired private SelectionReasonRepository selectionReasonRepository;
	@Autowired private BatchRepository batchRepository;
	@Autowired private BatchStatusRefRepository batchStatusRefRepository;
	@Autowired private BatchPersonnelRepository batchPersonnelRepository;

	@Autowired private CommonExceptionService commonExceptionService;
	@Autowired private DocumentRepository documentRepository;
	@Autowired private SecurityService securityService;

	@Transactional
	public Review createReview(LoanSelection loanSelection) {
		return createReviewImpl(loanSelection, null);
	}

	@Transactional
	public Review createOperationalReview(Batch batch) {
		return createReviewImpl(null, batch);
	}

	// pass either loanSelection *or* batch (for operational review), but not both
	private Review createReviewImpl(LoanSelection loanSelection, Batch batch) {
		String userId = securityService.getUserId();
		Date now = new Date();

		Review review = new Review();

		if (loanSelection != null) {
			// case review
			review.setCaseNumber(loanSelection.getCaseNumber());
			review.setLoanSelection(loanSelection);
			review.setBatch(loanSelection.getBatch());
			review.setLender(loanSelection.getLender());
			review.setReviewTypeRef(loanSelection.getReviewTypeRef());
			review.setProductTypeRef(loanSelection.getProductTypeRef());
			review.setSelectionReason(loanSelection.getSelectionReason());
			review.setReviewScopeRef(loanSelection.getReviewScopeRef());
			Review qcReview = loanSelection.getQcReview();
			if (qcReview == null) {
				review.setQaModel(qaModelRepository.findActive());
			} else {
				review.setQaModel(qcReview.getQaModel());
				review.setQcReview(qcReview);
			}

		} else if (batch != null) {
			// operational review
			review.setBatch(batch);
			review.setLender(batch.getLender());
			review.setReviewTypeRef(reviewTypeRefRepository.findByReviewTypeCd(ReviewTypeCodes.OPERATIONAL));
			// no product type for batch
			SelectionRequest selectionRequest = batch.getSelectionRequest();
			String selectionRequestTypeCode = selectionRequest.getSelectionRequestTypeRef().getCode();
			if (selectionRequestTypeCode.equals(SelectionRequestTypeRef.FHA_MANUAL)) {
				review.setSelectionReason(selectionRequest.getManualSelectionRequest().getSelectionReason());
			} else if (selectionRequestTypeCode.equals(SelectionRequestTypeRef.LENDER_MONITORING)) {
				review.setSelectionReason(selectionReasonRepository.findByCode(SelectionReasonCodes.LENDER_MONITORING));
			} else if (selectionRequestTypeCode.equals(SelectionRequestTypeRef.LENDER_SELF_REPORT)) {
				review.setSelectionReason(selectionReasonRepository.findByCode(SelectionReasonCodes.LENDER_SELF_REPORT));
			} else {
				throw new RuntimeException("Unhandled selectionRequestTypeCode: " + selectionRequestTypeCode);
			}
			review.setReviewScopeRef(reviewScopeRefRepository.findByCode(ReviewScopeCodes.FULL));	// operational reviews are always full scope
			review.setQaModel(qaModelRepository.findActive());

		} else {
			throw new RuntimeException("Specify either loanSelection or batch, but both both");
		}
		review.setReviewStatusRef(reviewStatusRefRepository.findByCode(ReviewStatusCodes.AWAITING_ASSIGNMENT));

		review.setMrbReferralInd('N');
		review.setManagementDecisionInd('N');
		review.setCreatedBy(userId);
		review.setCreatedTs(now);
		review.setUpdatedBy(userId);
		review.setUpdatedTs(now);
		review = reviewRepository.save(review);

		createInitialReviewLevel(review, (loanSelection != null) ? loanSelection.getReviewLocation() : batch.getReviewLocation());

		logger.debug("Created Review: " + review.getReviewId() + " (Case: " + review.getCaseNumber() + ", By: " + userId + ")");

		return review;
	}

	@Transactional
	public ReviewLevel createInitialReviewLevel(Review review, ReviewLocation reviewLocation) {
		ReviewLevel reviewLevel = createReviewLevelImpl(
			review,
			reviewLevelTypeRefRepository.findByReviewLevelCd(ReviewLevelTypeCodes.INITIAL),
			(short)1,
			reviewLocation,
			false,
			null
		);

		LoanSelection loanSelection = review.getLoanSelection();
		if (loanSelection != null) {
			createRvwLvlCaseSummary(reviewLevel, loanSelection);
		}

		return reviewLevel;
	}

	@Transactional
	public ReviewLevel createNextIterationReviewLevel(ReviewLevel currentReviewLevel) {
		ReviewLevelTypeRef reviewLevelType;
		short iteration;
		if (currentReviewLevel.getReviewLevelTypeRef().getReviewLevelCd().equals(ReviewLevelTypeCodes.INITIAL)) {
			// special case INIT -> MITG: init always has a single review level and lender request, no "bookends" like the others
			reviewLevelType = reviewLevelTypeRefRepository.findByReviewLevelCd(ReviewLevelTypeCodes.MITIGATION);
			iteration = 1;
		} else {
			reviewLevelType = currentReviewLevel.getReviewLevelTypeRef();
			iteration = (short)(currentReviewLevel.getIterationNumber() + 1);
		}

		ReviewLevel newReviewLevel = createReviewLevelImpl(
			currentReviewLevel.getReview(),
			reviewLevelType,
			iteration,
			currentReviewLevel.getReviewLocation(),
			false,
			currentReviewLevel.getIndemnificationTypeRef()
		);

		cloneRvwLvlFindings(currentReviewLevel, newReviewLevel);
		cloneRvwLvlCaseSummary(currentReviewLevel, newReviewLevel);

		newReviewLevel.setRatingRef(currentReviewLevel.getRatingRef());

		return newReviewLevel;
	}

	@Transactional
	public ReviewLevel createEscalatedReviewLevel(ReviewLevel currentReviewLevel) {
		String currentReviewLevelTypeCode = currentReviewLevel.getReviewLevelTypeRef().getReviewLevelCd();
		String newReviewLevelTypeCode;
		ReviewLocation newReviewLocation;
		if (currentReviewLevelTypeCode.equals(ReviewLevelTypeCodes.INITIAL)) {
			newReviewLevelTypeCode = ReviewLevelTypeCodes.MITIGATION;
			newReviewLocation = currentReviewLevel.getReviewLocation();
		} else if (currentReviewLevelTypeCode.equals(ReviewLevelTypeCodes.MITIGATION)) {
			newReviewLevelTypeCode = ReviewLevelTypeCodes.ESCALATION;
			newReviewLocation = currentReviewLevel.getReviewLocation();
		} else if (currentReviewLevelTypeCode.equals(ReviewLevelTypeCodes.ESCALATION)) {
			newReviewLevelTypeCode = ReviewLevelTypeCodes.HQ_ESCALATION;
			newReviewLocation = reviewLocationRepository.findByLocationName("HQ");	// TODO: should be a constant somewhere (also throughout the code), and dangerous since we can't guarantee location names
			if (newReviewLocation == null) {
				throw new RuntimeException("No HQ ReivewLocation");
			}
		} else if (currentReviewLevelTypeCode.equals(ReviewLevelTypeCodes.HQ_ESCALATION)) {
			throw new RuntimeException("Attempting to escalate ReviewLevel " + currentReviewLevel.getReviewLevelId() + " past HQ_ESCALATION");
		} else {
			throw new RuntimeException("Unhandled ReviewLevelType code: " + currentReviewLevelTypeCode + " for ReviewLevel " + currentReviewLevel.getReviewLevelId());
		}

		ReviewLevel newReviewLevel = createReviewLevelImpl(
			currentReviewLevel.getReview(),
			reviewLevelTypeRefRepository.findByReviewLevelCd(newReviewLevelTypeCode),
			(short)1,
			newReviewLocation,
			false,
			currentReviewLevel.getIndemnificationTypeRef()
		);

		cloneRvwLvlFindings(currentReviewLevel, newReviewLevel);
		cloneRvwLvlCaseSummary(currentReviewLevel, newReviewLevel);
		newReviewLevel.setRatingRef(currentReviewLevel.getRatingRef());

		return newReviewLevel;
	}

	@Transactional
	public ReviewLevel createVettingReviewLevel(ReviewLevel currentReviewLevel) {
		currentReviewLevel.setVettingRequiredInd('Y');
		currentReviewLevel.setUpdatedBy(securityService.getUserId());
		currentReviewLevel.setUpdatedTs(new Date());
		currentReviewLevel = reviewLevelRepository.save(currentReviewLevel);

		ReviewLevel newReviewLevel = createReviewLevelImpl(
			currentReviewLevel.getReview(),
			currentReviewLevel.getReviewLevelTypeRef(),
			currentReviewLevel.getIterationNumber(),
			currentReviewLevel.getReviewLocation(),
			true,
			currentReviewLevel.getIndemnificationTypeRef()
		);

		cloneRvwLvlFindings(currentReviewLevel, newReviewLevel);
		cloneRvwLvlCaseSummary(currentReviewLevel, newReviewLevel);
		cloneAnswers(currentReviewLevel, newReviewLevel);
		newReviewLevel.setRatingRef(currentReviewLevel.getRatingRef());

		return newReviewLevel;
	}

	@Transactional
	public ReviewLevel createIndemnificationReviewLevel(ReviewLevel currentReviewLevel, ReviewLevelTypeRef reviewLevelType, IndemnificationTypeRef indemnificationType) {
		ReviewLevel previousIndemLevel = reviewLevelRepository.findTopByReviewAndReviewLevelTypeRefOrderByIterationNumberDesc(currentReviewLevel.getReview(), reviewLevelType);
		short newIterationLevel = (previousIndemLevel == null)? 1 : (short)(previousIndemLevel.getIterationNumber() + 1);
		ReviewLevel newReviewLevel = createReviewLevelImpl(
			currentReviewLevel.getReview(),
			reviewLevelType,
			newIterationLevel,
			currentReviewLevel.getReviewLocation(),
			false,
			indemnificationType
		);

		cloneRvwLvlFindings(currentReviewLevel, newReviewLevel);
		cloneRvwLvlCaseSummary(currentReviewLevel, newReviewLevel);
		newReviewLevel.setRatingRef(currentReviewLevel.getRatingRef());
		return newReviewLevel;
	}

	private ReviewLevel createReviewLevelImpl(
		Review review,
		ReviewLevelTypeRef reviewLevelType,
		short iteration,
		ReviewLocation reviewLocation,
		boolean vetting,
		IndemnificationTypeRef indemnificationType
	) {
		String userId = securityService.getUserId();
		Date now = new Date();

		ReviewLevel reviewLevel = new ReviewLevel();
		reviewLevel.setReview(review);
		reviewLevel.setReviewLevelTypeRef(reviewLevelType);
		reviewLevel.setReviewLevelStatusRef(reviewLevelStatusRefRepository.findByCode(ReviewLevelStatusCodes.AWAITING_ASSIGNMENT));
		reviewLevel.setReviewLocation(reviewLocation);
		reviewLevel.setRatingRef(ratingRefRepository.findByCode(RatingCodes.CONFORMING));
		reviewLevel.setIterationNumber(iteration);
		ReviewLevelIterationTimeframe reviewLevelIterationTimeframe = reviewLevelIterationTimeframeRepository.findBySelectionReasonIdAndReviewLevelTypeId(
			review.getSelectionReason().getSelectionReasonId(),
			reviewLevelType.getReviewLevelTypeId()
		);
		if (reviewLevelIterationTimeframe == null) {
			throw new RuntimeException("No ReviewLevelIterationTimeframe for SelectionReason " + review.getSelectionReason().getSelectionReasonId() + " and ReviewLevelTypeRef " + reviewLevelType.getReviewLevelTypeId());
		}
		reviewLevel.setDueDate(calcReviewLevelDueDate(reviewLevelIterationTimeframe, iteration));
		reviewLevel.setVettingInd(vetting ? 'Y' : 'N');
		reviewLevel.setCreatedBy(userId);
		reviewLevel.setCreatedTs(now);
		reviewLevel = reviewLevelRepository.save(reviewLevel);

		review.setReviewStatusRef(reviewStatusRefRepository.findByCode(ReviewStatusCodes.AWAITING_ASSIGNMENT));
		if (indemnificationType != null) {
			reviewLevel.setIndemnificationTypeRef(indemnificationType);
		}
		review.setUpdatedBy(userId);
		review.setUpdatedTs(now);;
		review.getReviewLevels().add(reviewLevel);
		review = reviewRepository.save(review);

		logger.debug("Created Review Level for Review: " + review.getReviewId() + " (Case: " + review.getCaseNumber() + ", Type: " + reviewLevelType.getDescription() + ", Iteration: " + iteration + ", By: " + userId + ")");

		return reviewLevel;
	}

	@Transactional
	public LenderRequest createLenderRequest(ReviewLevel reviewLevel) {
		String userId = securityService.getUserId();
		Date now = new Date();

		Review review = reviewLevel.getReview();
		review.setReviewStatusRef(reviewStatusRefRepository.findByCode(ReviewStatusCodes.PENDING_LENDER_RESPONSE));
		review = reviewRepository.save(review);

		LenderRequest lenderRequest = new LenderRequest();
		lenderRequest.setReviewLevel(reviewLevel);
		lenderRequest.setLenderRequestStatusRef(lenderRequestStatusRefRepository.findByCode(LenderRequestStatusCodes.IN_PROGRESS));
		lenderRequest.setRequestedDate(now);
		ReviewLevelIterationTimeframe reviewLevelIterationTimeframe = reviewLevelIterationTimeframeRepository.findBySelectionReasonIdAndReviewLevelTypeId(
			review.getSelectionReason().getSelectionReasonId(),
			reviewLevel.getReviewLevelTypeRef().getReviewLevelTypeId()
		);
		if (reviewLevelIterationTimeframe == null) {
			throw new RuntimeException("No ReviewLevelIterationTimeframe for SelectionReason " + review.getSelectionReason().getSelectionReasonId() + " and ReviewLevelTypeRef " + reviewLevel.getReviewLevelTypeRef().getReviewLevelTypeId());
		}
		lenderRequest.setDueDate(calcLenderRequestDueDueDate(reviewLevelIterationTimeframe, reviewLevel.getIterationNumber()));
		lenderRequest.setCreatedBy(userId);
		lenderRequest.setCreatedTs(now);
		lenderRequest = lenderRequestRepository.save(lenderRequest);

		return lenderRequest;
	}

	@Transactional
	public void reassignReviewLevel(ReviewLevel reviewLevel, String personnelId, String reassignmentReasonCode) {
		Personnel personnel = personnelRepository.findOne(personnelId);
		if (personnel == null) {
			throw new BadRequestException("Personnel " + personnelId + " doesn't exist");
		}

		reassignReviewLevel(reviewLevel, personnel, reassignmentReasonCode, true);
	}

	@Transactional
	public void reassignReviewLevel(ReviewLevel reviewLevel, Personnel personnel, String reassignmentReasonCode, boolean isReassign) {
		assignReviewLevelImpl(reviewLevel, personnel, reassignmentReasonCode, isReassign);
	}

	@Transactional
	public void assignReviewLevel(ReviewLevel reviewLevel, String personnelId) {
		Personnel personnel = personnelRepository.findOne(personnelId);
		if (personnel == null) {
			throw new BadRequestException("Personnel " + personnelId + " doesn't exist");
		}

		assignReviewLevel(reviewLevel, personnel);
	}

	@Transactional
	public void assignReviewLevel(ReviewLevel reviewLevel, Personnel personnel) {
		assignReviewLevelImpl(reviewLevel, personnel, null, false);
	}

	private void assignReviewLevelImpl(ReviewLevel reviewLevel, Personnel personnel, String reassignmentReasonCode, boolean isReassign) {
		String userId = securityService.getUserId();
		Date now = new Date();

		Review review = reviewLevel.getReview();
		if (review.getOriginalReviewerPersonnel() == null) {
			review.setOriginalReviewerPersonnel(personnel);
			review.setOrigAssignDt(now);
		}
		review.setReviewStatusRef(reviewStatusRefRepository.findByCode(ReviewStatusCodes.ASSIGNED));
		review.setUpdatedBy(userId);
		review.setUpdatedTs(now);
		review = reviewRepository.save(review);

		reviewLevel.setAssignDt(now);
		reviewLevel.setReviewerPersonnel(personnel);
		if (reviewLevel.getOriginalReviewerPersonnel() == null || isReassign) {
			reviewLevel.setOriginalReviewerPersonnel(personnel);
		}
		if (!ReviewLevelStatusCodes.IN_PROGRESS.equals(reviewLevel.getReviewLevelStatusRef().getCode())) {
			reviewLevel.setReviewLevelStatusRef(reviewLevelStatusRefRepository.findByCode(ReviewLevelStatusCodes.ASSIGNED));
		}
		ReviewLevelIterationTimeframe reviewLevelIterationTimeframe = reviewLevelIterationTimeframeRepository.findBySelectionReasonIdAndReviewLevelTypeId(
			review.getSelectionReason().getSelectionReasonId(),
			reviewLevel.getReviewLevelTypeRef().getReviewLevelTypeId()
		);
		if (reviewLevelIterationTimeframe == null) {
			throw new RuntimeException("No ReviewLevelIterationTimeframe for SelectionReason " + review.getSelectionReason().getSelectionReasonId() + " and ReviewLevelTypeRef " + reviewLevel.getReviewLevelTypeRef().getReviewLevelTypeId());
		}

		reviewLevel.setDueDate(calcReviewLevelDueDate(reviewLevelIterationTimeframe, reviewLevel.getIterationNumber()));
		reviewLevel.setReviewLocation(personnel.getReviewLocation());
		if (reassignmentReasonCode != null) {
			ReviewLevelReassignmentReasonRef reviewLevelReassignmentReasonRef = reviewLevelReassignmentReasonRefRepository.findByCode(reassignmentReasonCode);
			if (reviewLevelReassignmentReasonRef == null) {
				throw new BadRequestException("ReviewLevelReassignmentReasonRef for code " + reassignmentReasonCode + " doesn't exist");
			}
			reviewLevel.setReviewLevelReassignmentReasonRef(reviewLevelReassignmentReasonRef);
		}
		reviewLevel.setUpdatedBy(userId);
		reviewLevel.setUpdatedTs(now);
		reviewLevel = reviewLevelRepository.save(reviewLevel);

		// Add new reviewer to batch personnel if applicable
		// Check if review belongs to batch 
		Batch batch = review.getBatch();
		if (batch != null) {
			// Check if reviewer is already batch member. If not add reviewer as batch member
			BatchPersonnelId id = new BatchPersonnelId();
			id.setBatchId(batch.getBatchId());
			id.setPersonnelId(personnel.getPersonnelId());
			if (batchPersonnelRepository.findOne(id) == null) {
				BatchPersonnel batchPersonnel = new BatchPersonnel();
				batchPersonnel.setId(id);
				batchPersonnel.setCreatedBy(userId);
				batchPersonnel.setCreatedTs(now);
				batchPersonnel.setUpdatedBy(userId);
				batchPersonnel.setUpdatedTs(now);
				batchPersonnelRepository.save(batchPersonnel);
			}
		}
		
		reviewLevelRepository.flush();	// important for multiple sequential assignments
		logger.debug("Assigned Review Level for review: " + review.getReviewId() + " to " + personnel.getLoginCredential() + " (ID: " + personnel.getPersonnelId() + ", Case: " + review.getCaseNumber() + ", By " + userId + " )");
	}

	@Transactional
	public void completeReviewLevel(ReviewLevel reviewLevel) {
		completeReviewLevel(reviewLevel, null, null);
	}

	@Transactional
	public void completeReviewLevel(ReviewLevel reviewLevel, IndemnificationTypeRef indemnificationType, QcOutcomeRef qcOutcome) {
		String userId = securityService.getUserId();
		Date now = new Date();

		reviewLevel.setReviewLevelStatusRef(reviewLevelStatusRefRepository.findByCode(ReviewLevelStatusCodes.COMPLETED));
		if (indemnificationType != null) {
			// this check is necessary since during batch submit we will have null indem type
			// in that case the indem type will have already been set on the reviewer submit and we don't want to overwrite it
			reviewLevel.setIndemnificationTypeRef(indemnificationType);
		}
		reviewLevel.setCompltDt(now);
		reviewLevel.setUpdatedBy(userId);
		reviewLevel.setUpdatedTs(now);
		reviewLevel = reviewLevelRepository.save(reviewLevel);

		Review review = reviewLevel.getReview();
		review.setReviewStatusRef(reviewStatusRefRepository.findByCode(ReviewStatusCodes.UNDER_REVIEW));
		review.setSummaryRatingRef(reviewLevel.getRatingRef());
		if (qcOutcome != null) {
			review.setQcOutcomeRef(qcOutcome);
		}
		review.setUpdatedBy(userId);
		review.setUpdatedTs(now);
		review = reviewRepository.save(review);
	}

	@Transactional
	public void completeReview(Review review) {
		review.setReviewStatusRef(reviewStatusRefRepository.findByCode(ReviewStatusCodes.COMPLETED));
		review.setRvwCompltdDt(new Date());
		review.setUpdatedBy(securityService.getUserId());
		review.setUpdatedTs(new Date());
		review = reviewRepository.save(review);

		logger.debug("Completed Review: " + review.getReviewId() + " (Case: " + review.getCaseNumber() + ", By: " + securityService.getUserId() + ")");
	}

	@Transactional
	public void completeLenderRequest(LenderRequest lenderRequest) {
		String userId = securityService.getUserId();
		Date now = new Date();

		lenderRequest.setLenderRequestStatusRef(lenderRequestStatusRefRepository.findByCode(LenderRequestStatusCodes.COMPLETED));
		lenderRequest.setUpdatedBy(userId);
		lenderRequest.setUpdatedTs(now);
		lenderRequest = lenderRequestRepository.save(lenderRequest);

		ReviewLevel reviewLevel = lenderRequest.getReviewLevel();
		Review review = reviewLevel.getReview();
		review.setReviewStatusRef(reviewStatusRefRepository.findByCode(ReviewStatusCodes.AWAITING_ASSIGNMENT));
		review.setUpdatedBy(userId);
		review.setUpdatedTs(now);
		review = reviewRepository.save(review);

		for (Document document : documentRepository.findByReviewLevelId(reviewLevel.getReviewLevelId())) {
			documentRepositoryService.uploadDocumentFile(document);
		}
	}

	@Transactional
	public void managementDecision(String reviewId) {
		Review review = reviewRepository.findOne(reviewId);
		if (review == null) {
			throw new NotFoundException("Review " + reviewId + " doesn't exist");
		}

		String statusCode = review.getReviewStatusRef().getCode();
		if (!(
			statusCode.equals(ReviewStatusCodes.ASSIGNED) ||
			statusCode.equals(ReviewStatusCodes.UNDER_REVIEW) ||
			statusCode.equals(ReviewStatusCodes.EXCEPTION)
		)) {
			throw new ConflictException("Review " + review.getReviewId() + " must have status ASSIGNED, UNDER_REVIEW, or EXCEPTION (has status " + statusCode);
		}

		ReviewLevel reviewLevel = reviewLevelRepository.findActiveOrExceptionByReviewId(review.getReviewId());
		reviewLevel.setRatingRef(ratingRefRepository.findByCode(RatingCodes.UNACCEPTABLE));
		completeReviewLevel(reviewLevel);

		review.setManagementDecisionInd('Y');
		completeReview(review);

		for (ReviewProcessException exception : reviewProcessExceptionRepository.findByReviewLevelReview(review)) {
			commonExceptionService.markExceptionResolved(exception);
		}
	}

	@Transactional
	public void mrbReferral(String reviewId) {
		Review review = reviewRepository.findOne(reviewId);
		if (review == null) {
			throw new NotFoundException("No Review for reviewId " + reviewId);
		}

		String statusCode = review.getReviewStatusRef().getCode();
		if (!(
			statusCode.equals(ReviewStatusCodes.ASSIGNED) ||
			statusCode.equals(ReviewStatusCodes.UNDER_REVIEW) ||
			statusCode.equals(ReviewStatusCodes.EXCEPTION)
		)) {
			throw new ConflictException("Review " + review.getReviewId() + " must have status ASSIGNED, UNDER_REVIEW, or EXCEPTION (has status " + statusCode);
		}

		ReviewLevel reviewLevel = reviewLevelRepository.findActiveOrExceptionByReviewId(review.getReviewId());
		reviewLevel.setRatingRef(ratingRefRepository.findByCode(RatingCodes.UNACCEPTABLE));
		completeReviewLevel(reviewLevel);

		review.setMrbReferralInd('Y');
		completeReview(review);

		for (ReviewProcessException exception : reviewProcessExceptionRepository.findByReviewLevelReview(review)) {
			commonExceptionService.markExceptionResolved(exception);
		}
	}

	@Transactional
	public void cancelReview(String reviewId, String cancellationReasonCode) {
		Review review = reviewRepository.findOne(reviewId);
		if (review == null) {
			throw new NotFoundException("Review: " + reviewId + " doesn't exist");
		}

		cancelReview(review, cancellationReasonCode);
	}

	@Transactional
	public void cancelReview(Review review, String cancellationReasonCode) {
		if (cancellationReasonCode == null || cancellationReasonCode.equals("")) {
			throw new RuntimeException("Cancellation reason code is required");
		}
		String statusCode = review.getReviewStatusRef().getCode();
		if (
			statusCode.equals(ReviewStatusCodes.COMPLETED) ||
			statusCode.equals(ReviewStatusCodes.CANCELLED)
		) {
			throw new ConflictException("Review " + review.getReviewId() + " has status " + statusCode);
		}

		String userId = securityService.getUserId();
		Date now = new Date();

		// cancel active review level
		ReviewLevel reviewLevel = reviewLevelRepository.findActiveByReviewId(review.getReviewId());
		if (reviewLevel != null) {
			// it's okay for there to be no active review level; it could be with a lender
			reviewLevel.setReviewLevelStatusRef(reviewLevelStatusRefRepository.findByCode(ReviewLevelStatusCodes.CANCELLED));
			reviewLevel.setUpdatedBy(userId);
			reviewLevel.setUpdatedTs(now);
			reviewLevel.setCompltDt(now);
		}

		// cancel active lender request
		LenderRequest activeLenderRequest = lenderRequestRepository.findActiveByReviewId(review.getReviewId());
		if (activeLenderRequest != null) {
			activeLenderRequest.setLenderRequestStatusRef(lenderRequestStatusRefRepository.findByCode(LenderRequestStatusCodes.CANCELLED));
			activeLenderRequest.setUpdatedBy(userId);
			activeLenderRequest.setUpdatedTs(now);
		}

		// cancel review
		review.setReviewStatusRef(reviewStatusRefRepository.findByCode(ReviewStatusCodes.CANCELLED));
		review.setCancellationReasonRef(cancellationReasonRefRepository.findByCode(cancellationReasonCode));
		review.setUpdatedBy(userId);
		review.setUpdatedTs(now);
		review.setRvwCompltdDt(now);
		
		//cancel LoanSelection
		LoanSelection loanSelection = review.getLoanSelection();
		if (loanSelection != null) {
			loanSelection.setLoanSelectionStatusRef(loanSelectionStatusRefRepository.findByCode(LoanSelectionStatusCodes.CANCELLED));
			loanSelection.setUpdatedBy(userId);
			loanSelection.setUpdatedTs(now);
		}
		
		// Update the batch status if this review belongs to the batch.
		Batch batch = review.getBatch();
		if ((batch != null) && !review.getReviewTypeRef().getReviewTypeCd().equals(ReviewTypeCodes.OPERATIONAL)) {
			reviewLevelRepository.flush();
			if (isBatchReadyForReview(batch) && (batchRepository.findActiveReviewLevelCountByBatchIdAndReviewIdNot(batch.getBatchId(), review.getReviewId()) == 0)) {
				logger.debug("All review levels in batch (" + batch.getBatchReferenceId() + ") are done, marking batch as UNDER_BATCH_REVIEW");
				batch.setBatchStatusRef(batchStatusRefRepository.findByCode(BatchStatusRef.UNDER_BATCH_REVIEW));
			}
		}
		
		for (ReviewProcessException exception : reviewProcessExceptionRepository.findByReviewLevelReview(review)) {
			commonExceptionService.markExceptionResolved(exception);
		}

		logger.debug("Canceled Review: " + review.getReviewId() + " (Case: " + review.getCaseNumber() + ", Reason Code: " + cancellationReasonCode + ", By: " + userId + ")");
	}

	public boolean isBatchReadyForReview(Batch batch) {
		// Check to make sure all loan selections have been transitioned into reviews
		// It's possible a binder request for one of the selections lags behind others
		// The batch can't be ready to review until all reviews are created
		Set<Review> reviews = batch.getReviews();
		Set<LoanSelectionPending> loanSelectionPendings = batch.getLoanSelectionPendings();
		Set<LoanSelection> loanSelections = batch.getLoanSelections();
		int numLoanSelections = loanSelectionPendings.size() + loanSelections.size();
		int numValidReviews = 0;
		for (Review review : reviews) {
			if (!review.getReviewTypeRef().getReviewTypeCd().equals(ReviewTypeCodes.OPERATIONAL)) {
				numValidReviews++;
			}
		}
		return (numLoanSelections == numValidReviews);
	}

	public boolean hasAttemptsRemaining(ReviewLevel reviewLevel) {
		// TODO: we load this twice in a couple of sequences (has attempts then calc due date)
		// should probably cache it (but careful because it's not static data)
		ReviewLevelIterationTimeframe reviewLevelIterationTimeframe = reviewLevelIterationTimeframeRepository.findBySelectionReasonIdAndReviewLevelTypeId(
			reviewLevel.getReview().getSelectionReason().getSelectionReasonId(),
			reviewLevel.getReviewLevelTypeRef().getReviewLevelTypeId()
		);
		if (reviewLevelIterationTimeframe == null) {
			throw new RuntimeException("No ReviewLevelIterationTimeframe for ReviewLevel " + reviewLevel.getReviewLevelId());
		}

		int days;
		switch (reviewLevel.getIterationNumber()) {
			case 1: days = reviewLevelIterationTimeframe.getReviewDaysIteration2(); break;
			case 2: days = reviewLevelIterationTimeframe.getReviewDaysIteration3(); break;
			case 3: days = reviewLevelIterationTimeframe.getReviewDaysIteration4(); break;
			case 4: days = reviewLevelIterationTimeframe.getReviewDaysIteration5(); break;
			case 5: days = reviewLevelIterationTimeframe.getReviewDaysIteration6(); break;
			case 6: days = 0;
			default: {
				throw new RuntimeException("Unhandled iterationNumber " + reviewLevel.getIterationNumber() + " for ReviewLevelIterationTimeframe " + reviewLevelIterationTimeframe.getReviewLevelIterationTimeframeId());
			}
		}

		return reviewLevel.getReviewLevelTypeRef().getReviewLevelCd().equals(ReviewLevelTypeCodes.INITIAL) || (days > 0);
	}

	private Date calcReviewLevelDueDate(ReviewLevelIterationTimeframe reviewLevelIterationTimeframe, short iterationNumber) {
		int days;
		switch (iterationNumber) {
			case 1: days = reviewLevelIterationTimeframe.getReviewDaysIteration1(); break;
			case 2: days = reviewLevelIterationTimeframe.getReviewDaysIteration2(); break;
			case 3: days = reviewLevelIterationTimeframe.getReviewDaysIteration3(); break;
			case 4: days = reviewLevelIterationTimeframe.getReviewDaysIteration4(); break;
			case 5: days = reviewLevelIterationTimeframe.getReviewDaysIteration5(); break;
			case 6: days = reviewLevelIterationTimeframe.getReviewDaysIteration6(); break;
			default: {
				throw new RuntimeException("Unhandled iterationNumber " + iterationNumber + " for ReviewLevelIterationTimeframe " + reviewLevelIterationTimeframe.getReviewLevelIterationTimeframeId());
			}
		}

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + days);

		return calendar.getTime();
	}

	private Date calcLenderRequestDueDueDate(ReviewLevelIterationTimeframe reviewLevelIterationTimeframe, short iterationNumber) {
		int days;
		switch (iterationNumber) {
			case 1: days = reviewLevelIterationTimeframe.getResponseDaysIteration1(); break;
			case 2: days = reviewLevelIterationTimeframe.getResponseDaysIteration2(); break;
			case 3: days = reviewLevelIterationTimeframe.getResponseDaysIteration3(); break;
			case 4: days = reviewLevelIterationTimeframe.getResponseDaysIteration4(); break;
			case 5: days = reviewLevelIterationTimeframe.getResponseDaysIteration5(); break;
			default: {
				throw new RuntimeException("Unhandled iterationNumber " + iterationNumber + " for ReviewLevelIterationTimeframe " + reviewLevelIterationTimeframe.getReviewLevelIterationTimeframeId());
			}
		}

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + days);

		return calendar.getTime();
	}

	public Date calcBinderRequestDueDate(ReviewLevelIterationTimeframe reviewLevelIterationTimeframe) {
		return calcLenderRequestDueDueDate(reviewLevelIterationTimeframe, (short)1);	// there is no notion of iterations for binder requet
	}

	public String getIndemnificationStart(String reviewLevelId) {
		String indemnificationStart = null;
		ReviewLevel reviewLevel = reviewLevelRepository.findOne(reviewLevelId);
		Review review = reviewLevel.getReview();
		String reviewType = review.getReviewTypeRef().getReviewTypeCd();

		if (reviewType.equals(ReviewTypeCodes.UNDERWRITING) || reviewType.equals(ReviewTypeCodes.COMPREHENSIVE)) {
			indemnificationStart = "Endorsement";
		} else if (reviewType.equals(ReviewTypeCodes.SERVICING)) {
			indemnificationStart = "Agreement";
		} else {
			throw new RuntimeException("Unhandled review type " + reviewType);
		}

		return indemnificationStart;
	}

	public Date getIndemnificationStartDate(String reviewLevelId) {
		ReviewLevel reviewLevel = reviewLevelRepository.findOne(reviewLevelId);
		LoanSelectionCaseSummary loanSelectionCaseSummary = reviewLevel.getReview().getLoanSelection().getLoanSelectionCaseSummary();
		String indemnificationStart = getIndemnificationStart(reviewLevel.getReviewLevelId());

		if (indemnificationStart.equals("Agreement")) {
			return new Date();
		} else {
			// Indemnification cases must have been endorsed.
			if (loanSelectionCaseSummary.getEndrsmntDt() == null) {
			 	throw new RuntimeException("Case: " + reviewLevel.getReview().getCaseNumber() + " does not have an endorsement date");
			}
			return loanSelectionCaseSummary.getEndrsmntDt();
		}
	}

	public Integer getIndemnificationTerm(String reviewLevelId) {
		ReviewLevel reviewLevel = reviewLevelRepository.findOne(reviewLevelId);
		// Expiration date = Start date + (
		//    if 5 YEAR then 60 
		//    else if HECM then 360
		//    else LOAN_SELECTION_CASE_SUMMARY.TERM )
		if (reviewLevel.getIndemnificationTypeRef().getCode().equals(IndemnificationTypeCodes.FIVE_YEAR)) {
			return 60;
		} else {
			LoanSelectionCaseSummary loanSelectionCaseSummary = reviewLevel.getReview().getLoanSelection().getLoanSelectionCaseSummary();
			if (loanSelectionCaseSummary.getLoanType().equals(LoanTypeCodes.HECM)) {
				return 360;
			} else {
				return loanSelectionCaseSummary.getTerm();
			}
		}
	}

	public Date getIndemnificationExpirationDate(String reviewLevelId, Date startDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		Integer term = getIndemnificationTerm(reviewLevelId);
		calendar.add(Calendar.MONTH, term);
		return calendar.getTime();
	}

	public String getReviewerDivision() {
		String locationName = securityService.getReviewLocation().getLocationName();
		String divisionName = "";
		if (locationName.equals("HQ")) {
			divisionName = locationName;
		} else {
			int index = locationName.indexOf("-");
			if (index == -1) {
				throw new RuntimeException("Review Location is invalid.");
			}
			divisionName = locationName.substring(index+1);
		}

		return divisionName;
	}

	@Transactional
	public void markReviewLevelInProgress(ReviewLevel reviewLevel) {
		// set review level status to In Progress on first answer save
		if (reviewLevel.getReviewLevelStatusRef().getCode().equalsIgnoreCase(ReviewLevelStatusCodes.ASSIGNED)) {
			Date now = new Date();
			String userId = securityService.getUserId();

			reviewLevel.setReviewLevelStatusRef(reviewLevelStatusRefRepository.findByCode(ReviewLevelStatusCodes.IN_PROGRESS));
			reviewLevel.setRvwStartedDtTm(now);
			reviewLevel.setUpdatedBy(userId);
			reviewLevel.setUpdatedTs(now);
			reviewLevel = reviewLevelRepository.save(reviewLevel);

			Review review = reviewLevel.getReview();
			review.setReviewStatusRef(reviewStatusRefRepository.findByCode(ReviewStatusCodes.UNDER_REVIEW));
			review.setRvwStartedDtTm(now);
			review.setUpdatedBy(userId);
			review.setUpdatedTs(now);
			review = reviewRepository.save(review);
		}
	}

	private void cloneAnswers(ReviewLevel currentReviewLevel, ReviewLevel newReviewLevel) {
		String userId = securityService.getUserId();
		Date now = new Date();

		for (Answer answer : currentReviewLevel.getAnswers()) {
			Answer clonedAnswer = new Answer();
			clonedAnswer.setReviewLevel(newReviewLevel);
			clonedAnswer.setQatreeQuestion(answer.getQatreeQuestion());
			clonedAnswer.setAnswer(answer.getAnswer());
			clonedAnswer.setCreatedBy(userId);
			clonedAnswer.setCreatedTs(now);
			clonedAnswer = answerRepository.save(clonedAnswer);

			newReviewLevel.getAnswers().add(clonedAnswer);
		}
	}

	private void cloneRvwLvlFindings(ReviewLevel currentReviewLevel, ReviewLevel newReviewLevel) {
		String userId = securityService.getUserId();
		Date now = new Date();

		// PERF TODO: this is better done as a set-based SQL operation
		for(RvwLvlFinding rvwLvlFinding : currentReviewLevel.getRvwLvlFindings()) {
			RvwLvlFinding clonedRvwLvlFinding = new RvwLvlFinding();
			clonedRvwLvlFinding.setReviewLevel(newReviewLevel);
			clonedRvwLvlFinding.setOriginalReviewLevel(rvwLvlFinding.getOriginalReviewLevel());
			clonedRvwLvlFinding.setDefect(rvwLvlFinding.getDefect());
			clonedRvwLvlFinding.setDefectCd(rvwLvlFinding.getDefectCd());
			clonedRvwLvlFinding.setDefectSource(rvwLvlFinding.getDefectSource());
			clonedRvwLvlFinding.setDefectSourceCd(rvwLvlFinding.getDefectSourceCd());
			clonedRvwLvlFinding.setDefectCause(rvwLvlFinding.getDefectCause());
			clonedRvwLvlFinding.setDefectCauseCd(rvwLvlFinding.getDefectCauseCd());
			clonedRvwLvlFinding.setDefectSeverity(rvwLvlFinding.getDefectSeverity());
			clonedRvwLvlFinding.setSeverityTierCd(rvwLvlFinding.getSeverityTierCd());
			clonedRvwLvlFinding.setRatingRef(rvwLvlFinding.getRatingRef());
			clonedRvwLvlFinding.setDefectRemedyType(rvwLvlFinding.getDefectRemedyType());
			clonedRvwLvlFinding.setRecissionInd(rvwLvlFinding.getRecissionInd());
			clonedRvwLvlFinding.setRemediedDt(rvwLvlFinding.getRemediedDt());
			clonedRvwLvlFinding.setRemedyAmount(rvwLvlFinding.getRemedyAmount());
			clonedRvwLvlFinding.setCreatedBy(userId);
			clonedRvwLvlFinding.setCreatedTs(now);
			clonedRvwLvlFinding = rvwLvlFindingRepository.save(clonedRvwLvlFinding);

			// Vetter findings have some special rules to ensure the vetter can make updates
			if (currentReviewLevel.getVettingRequiredInd() == 'Y') {

				// Need to persist the vettee's comment to lender
				clonedRvwLvlFinding.setNotes(rvwLvlFinding.getNotes());

				// New Vettee adhoc findings need to set original review level to match the vetter's level
				String reviewLevelTypeCode = currentReviewLevel.getReviewLevelTypeRef().getReviewLevelCd();
				if (
					!reviewLevelTypeCode.equals(ReviewLevelTypeCodes.INITIAL) &&
					rvwLvlFinding.getOriginalReviewLevel().getReviewLevelId() == rvwLvlFinding.getReviewLevel().getReviewLevelId()
				) {
					clonedRvwLvlFinding.setOriginalReviewLevel(newReviewLevel);
				}
			}

			for (RvwLvlFindingQuestion rvwLvlFindingQuestion : rvwLvlFinding.getRvwLvlFindingQuestions()) {
				RvwLvlFindingQuestion clonedRvwLvlFindingQuestion = new RvwLvlFindingQuestion();
				clonedRvwLvlFindingQuestion.setQatreeQuestion(rvwLvlFindingQuestion.getQatreeQuestion());
				clonedRvwLvlFindingQuestion.setRvwLvlFinding(clonedRvwLvlFinding);
				clonedRvwLvlFindingQuestion = rvwLvlFindingQuestionRepository.save(clonedRvwLvlFindingQuestion);
				clonedRvwLvlFinding.getRvwLvlFindingQuestions().add(clonedRvwLvlFindingQuestion);
			}

			newReviewLevel.getRvwLvlFindings().add(clonedRvwLvlFinding);
		}
		//rvwLvlFindingRepository.flush();
	}

	private RvwLvlCaseSummary createRvwLvlCaseSummary(ReviewLevel reviewLevel, LoanSelection loanSelection) {
		LoanSelectionCaseSummary loanSelectionCaseSummary = loanSelection.getLoanSelectionCaseSummary();
		RvwLvlCaseSummary rvwLvlCaseSummary = new RvwLvlCaseSummary();
		rvwLvlCaseSummary.setReviewLevel(reviewLevel);

		rvwLvlCaseSummary.setAddtnl10pctIplUsageInd(loanSelectionCaseSummary.getAddtnl10pctIplUsageInd());
		rvwLvlCaseSummary.setAdpCode(loanSelectionCaseSummary.getAdpCode());
		rvwLvlCaseSummary.setAmortTypCd(loanSelectionCaseSummary.getAmortTypCd());
		rvwLvlCaseSummary.setApplicationDate(loanSelectionCaseSummary.getApplicationDate());
		rvwLvlCaseSummary.setAppraiserName(loanSelectionCaseSummary.getAppraiserName());
		rvwLvlCaseSummary.setAprslCmpltnDt(loanSelectionCaseSummary.getAprslCmpltnDt());
		rvwLvlCaseSummary.setArmInd(loanSelectionCaseSummary.getArmInd());
		rvwLvlCaseSummary.setArmIndxExpctdRt(loanSelectionCaseSummary.getArmIndxExpctdRt());
		rvwLvlCaseSummary.setAssetsAfterClosingUw(loanSelectionCaseSummary.getAssetsAfterClosingUw());
		rvwLvlCaseSummary.setAssumedLoanInd(loanSelectionCaseSummary.getAssumedLoanInd());
		rvwLvlCaseSummary.setBackToWorkInd(loanSelectionCaseSummary.getBackToWorkInd());
		rvwLvlCaseSummary.setBldgTyp(loanSelectionCaseSummary.getBldgTyp());
		rvwLvlCaseSummary.setBnkrptcyAnyInd(loanSelectionCaseSummary.getBnkrptcyAnyInd());
		rvwLvlCaseSummary.setBnkrptcyCd(loanSelectionCaseSummary.getBnkrptcyCd());
		rvwLvlCaseSummary.setBnkrptcyChptr13Ind(loanSelectionCaseSummary.getBnkrptcyChptr13Ind());
		rvwLvlCaseSummary.setBnkrptcyChptr7Ind(loanSelectionCaseSummary.getBnkrptcyChptr7Ind());
		rvwLvlCaseSummary.setBnkrptcyDt(loanSelectionCaseSummary.getBnkrptcyDt());
		rvwLvlCaseSummary.setBorr1FirstTimeBuyerInd(loanSelectionCaseSummary.getBorr1FirstTimeBuyerInd());
		rvwLvlCaseSummary.setBorr1Name(loanSelectionCaseSummary.getBorr1Name());
		rvwLvlCaseSummary.setBorr1RentingInd(loanSelectionCaseSummary.getBorr1RentingInd());
		rvwLvlCaseSummary.setBorr1SelfEmplInd(loanSelectionCaseSummary.getBorr1SelfEmplInd());
		rvwLvlCaseSummary.setBorr1Ssn(loanSelectionCaseSummary.getBorr1Ssn());
		rvwLvlCaseSummary.setBorr2Name(loanSelectionCaseSummary.getBorr2Name());
		rvwLvlCaseSummary.setBorr2Ssn(loanSelectionCaseSummary.getBorr2Ssn());
		rvwLvlCaseSummary.setBorrBrthDt(loanSelectionCaseSummary.getBorrBrthDt());
		rvwLvlCaseSummary.setBorrHsngExpEndrs(loanSelectionCaseSummary.getBorrHsngExpEndrs());
		rvwLvlCaseSummary.setBorrPaidClosingCosts(loanSelectionCaseSummary.getBorrPaidClosingCosts());
		rvwLvlCaseSummary.setBorrReqdInvestToClose(loanSelectionCaseSummary.getBorrReqdInvestToClose());
		rvwLvlCaseSummary.setBorrTyp(loanSelectionCaseSummary.getBorrTyp());
		rvwLvlCaseSummary.setBuildingOnOwnLandInd(loanSelectionCaseSummary.getBuildingOnOwnLandInd());
		rvwLvlCaseSummary.setCaseNumber(loanSelectionCaseSummary.getCaseNumber());
		rvwLvlCaseSummary.setCashoutRefiInd(loanSelectionCaseSummary.getCashoutRefiInd());
		rvwLvlCaseSummary.setClaimType(loanSelectionCaseSummary.getClaimType());
		rvwLvlCaseSummary.setClsngDt(loanSelectionCaseSummary.getClsngDt());
		rvwLvlCaseSummary.setCombinedLoanToValuePct(loanSelectionCaseSummary.getCombinedLoanToValuePct());
		rvwLvlCaseSummary.setConstCd(loanSelectionCaseSummary.getConstCd());
		rvwLvlCaseSummary.setCurrentAtEndorseInd(loanSelectionCaseSummary.getCurrentAtEndorseInd());
		rvwLvlCaseSummary.setDateOfPriorSale(loanSelectionCaseSummary.getDateOfPriorSale());
		rvwLvlCaseSummary.setDcsnCdEndrs(loanSelectionCaseSummary.getDcsnCdEndrs());
		rvwLvlCaseSummary.setDefaultEpisodeExistsInd(loanSelectionCaseSummary.getDefaultEpisodeExistsInd());
		rvwLvlCaseSummary.setDisasterInd(loanSelectionCaseSummary.getDisasterInd());
		rvwLvlCaseSummary.setEffDateAprslUpdate(loanSelectionCaseSummary.getEffDateAprslUpdate());
		rvwLvlCaseSummary.setEndrsBynd60DaysCloseInd(loanSelectionCaseSummary.getEndrsBynd60DaysCloseInd());
		rvwLvlCaseSummary.setEndrsmntDt(loanSelectionCaseSummary.getEndrsmntDt());
		rvwLvlCaseSummary.setEscrowAmount(loanSelectionCaseSummary.getEscrowAmount());
		rvwLvlCaseSummary.setExpectedRate(loanSelectionCaseSummary.getLeExpectedRate());
		rvwLvlCaseSummary.setFctryFbrct(loanSelectionCaseSummary.getFctryFbrct());
		rvwLvlCaseSummary.setFicoDecisionScoreEndrs(loanSelectionCaseSummary.getFicoDecisionScoreEndrs());
		rvwLvlCaseSummary.setFlippingCategory2Ind(loanSelectionCaseSummary.getFlippingCategory2Ind());
		rvwLvlCaseSummary.setFrclsrInd(loanSelectionCaseSummary.getFrclsrInd());
		rvwLvlCaseSummary.setFrclsrStrtDt(loanSelectionCaseSummary.getFrclsrStrtDt());
		rvwLvlCaseSummary.setFtInEps3mnthDelqDt(loanSelectionCaseSummary.getFtInEps3mnthDelqDt());
		rvwLvlCaseSummary.setGiftLtr2Amt(loanSelectionCaseSummary.getGiftLtr2Amt());
		rvwLvlCaseSummary.setGiftLtr2Source(loanSelectionCaseSummary.getGiftLtr2Source());
		rvwLvlCaseSummary.setGiftLtr2Tin(loanSelectionCaseSummary.getGiftLtr2Tin());
		rvwLvlCaseSummary.setGiftLtrAmt(loanSelectionCaseSummary.getGiftLtrAmt());
		rvwLvlCaseSummary.setGiftLtrSrc(loanSelectionCaseSummary.getGiftLtrSrc());
		rvwLvlCaseSummary.setGiftLtrTin(loanSelectionCaseSummary.getGiftLtrTin());
		rvwLvlCaseSummary.setHecmCounselCertNo(loanSelectionCaseSummary.getHecmCounselCertNo());
		rvwLvlCaseSummary.setHecmCounselDt(loanSelectionCaseSummary.getHecmCounselDt());
		rvwLvlCaseSummary.setHecmPrncplLmt(loanSelectionCaseSummary.getHecmPrncplLmt());
		rvwLvlCaseSummary.setHudReoRepairAmt(loanSelectionCaseSummary.getHudReoRepairAmt());
		rvwLvlCaseSummary.setInitDisbursementLimit(loanSelectionCaseSummary.getInitDisbursementLimit());
		rvwLvlCaseSummary.setInsurAppInTimeInd(loanSelectionCaseSummary.getInsurAppInTimeInd());
		rvwLvlCaseSummary.setIntRt(loanSelectionCaseSummary.getIntRt());
		rvwLvlCaseSummary.setInvest2ndResidInd(loanSelectionCaseSummary.getInvest2ndResidInd());
		rvwLvlCaseSummary.setLastServicingMrtgXferDt(loanSelectionCaseSummary.getLastServicingMrtgXferDt());
		rvwLvlCaseSummary.setLoanOfficer(loanSelectionCaseSummary.getLoanOfficer());
		rvwLvlCaseSummary.setLoanOfficerNmls(loanSelectionCaseSummary.getLoanOfficerNmls());
		rvwLvlCaseSummary.setLoanPrps(loanSelectionCaseSummary.getLoanPrps());
		rvwLvlCaseSummary.setLoanType(loanSelectionCaseSummary.getLoanType());
		rvwLvlCaseSummary.setLossmitCd(loanSelectionCaseSummary.getLossmitCd());
		rvwLvlCaseSummary.setMandatoryObligBorrAmt(loanSelectionCaseSummary.getMandatoryObligBorrAmt());
		rvwLvlCaseSummary.setMandatoryObligLendAmt(loanSelectionCaseSummary.getMandatoryObligLendAmt());
		rvwLvlCaseSummary.setManUwStretchRatioInd(loanSelectionCaseSummary.getManUwStretchRatioInd());
		rvwLvlCaseSummary.setMarriedToNbsInd(loanSelectionCaseSummary.getMarriedToNbsInd());
		rvwLvlCaseSummary.setMaxClaimAmt(loanSelectionCaseSummary.getMaxClaimAmt());
		rvwLvlCaseSummary.setMaxRate(loanSelectionCaseSummary.getMaxRate());
		rvwLvlCaseSummary.setMiscAusDcsnCd(loanSelectionCaseSummary.getMiscAusDcsnCd());
		rvwLvlCaseSummary.setMndtryOblgtnsAmt(loanSelectionCaseSummary.getMndtryOblgtnsAmt());
		rvwLvlCaseSummary.setMnfctrdHusngInd(loanSelectionCaseSummary.getMnfctrdHusngInd());
		rvwLvlCaseSummary.setMortExcldFncdMip(loanSelectionCaseSummary.getMortExcldFncdMip());
		rvwLvlCaseSummary.setNonOccupyingBorrInd(loanSelectionCaseSummary.getNonOccupyingBorrInd());
		rvwLvlCaseSummary.setNumLivingUnits(loanSelectionCaseSummary.getNumLivingUnits());
		rvwLvlCaseSummary.setOcpncyStsCd(loanSelectionCaseSummary.getOcpncyStsCd());
		rvwLvlCaseSummary.setOldstUnpdDt(loanSelectionCaseSummary.getOldstUnpdDt());
		rvwLvlCaseSummary.setOrgntngMtgeeNmlsId(loanSelectionCaseSummary.getOrgntngMtgeeNmlsId());
		rvwLvlCaseSummary.setOriginationFee(loanSelectionCaseSummary.getOriginationFee());
		rvwLvlCaseSummary.setOrigMrtgAmt(loanSelectionCaseSummary.getOrigMrtgAmt());
		rvwLvlCaseSummary.setPaymentPlan(loanSelectionCaseSummary.getPaymentPlan());
		rvwLvlCaseSummary.setPdStrmlnFlg(loanSelectionCaseSummary.getPdStrmlnFlg());
		rvwLvlCaseSummary.setPriceOfPriorSale(loanSelectionCaseSummary.getPriceOfPriorSale());
		rvwLvlCaseSummary.setPriorSaleWithinLast3yrInd(loanSelectionCaseSummary.getPriorSaleWithinLast3yrInd());
		rvwLvlCaseSummary.setProductTypeCd(loanSelection.getProductTypeRef().getProductTypeCd());
		rvwLvlCaseSummary.setPropAddr1(loanSelectionCaseSummary.getPropAddr1());
		rvwLvlCaseSummary.setPropAddr2(loanSelectionCaseSummary.getPropAddr2());
		rvwLvlCaseSummary.setPropAddrCity(loanSelectionCaseSummary.getPropAddrCity());
		rvwLvlCaseSummary.setPropAddrSt(loanSelectionCaseSummary.getPropAddrSt());
		rvwLvlCaseSummary.setPropAddrZip(loanSelectionCaseSummary.getPropAddrZip());
		rvwLvlCaseSummary.setPrprtyAprslVl(loanSelectionCaseSummary.getPrprtyAprslVl());
		rvwLvlCaseSummary.setQualifiedMrtgPointsAndFees(loanSelectionCaseSummary.getQualifiedMrtgPointsAndFees());
		rvwLvlCaseSummary.setRatioFixTeiEndrs(loanSelectionCaseSummary.getRatioFixTeiEndrs());
		rvwLvlCaseSummary.setRatioLoanToVlNew(loanSelectionCaseSummary.getRatioLoanToVlNew());
		rvwLvlCaseSummary.setRatioTotPmtToTotIncEndrs(loanSelectionCaseSummary.getRatioTotPmtToTotIncEndrs());
		rvwLvlCaseSummary.setRcvSaleDt(loanSelectionCaseSummary.getRcvSaleDt());
		rvwLvlCaseSummary.setRefinanceInd(loanSelectionCaseSummary.getRefinanceInd());
		rvwLvlCaseSummary.setReo100DownPmtProgInd(loanSelectionCaseSummary.getReo100DownPmtProgInd());
		rvwLvlCaseSummary.setRepairCompletionDate(loanSelectionCaseSummary.getRepairCompletionDate());
		rvwLvlCaseSummary.setReviewTypeCd(loanSelection.getReviewTypeRef().getReviewTypeCd().charAt(0));
		rvwLvlCaseSummary.setRfncCd(loanSelectionCaseSummary.getRfncCd());
		rvwLvlCaseSummary.setSalePriceGtrAcqCostInd(loanSelectionCaseSummary.getSalePriceGtrAcqCostInd());
		rvwLvlCaseSummary.setSalesPrice(loanSelectionCaseSummary.getSalesPrice());
		rvwLvlCaseSummary.setSecondaryFinanceAmt(loanSelectionCaseSummary.getSecondaryFinanceAmt());
		rvwLvlCaseSummary.setSecondaryFinanceExistsInd(loanSelectionCaseSummary.getSecondaryFinanceExistsInd());
		rvwLvlCaseSummary.setSellerCntrbtn(loanSelectionCaseSummary.getSellerCntrbtn());
		rvwLvlCaseSummary.setSellerCntrbtnPcnt(loanSelectionCaseSummary.getSellerCntrbtnPcnt());
		rvwLvlCaseSummary.setSiteType(loanSelectionCaseSummary.getSiteType());
		rvwLvlCaseSummary.setSoaCd(loanSelectionCaseSummary.getSoaCd());
		rvwLvlCaseSummary.setSpecialProgram(loanSelectionCaseSummary.getSpecialProgram());
		rvwLvlCaseSummary.setTaxesInsrncFrstYrAmt(loanSelectionCaseSummary.getTaxesInsrncFrstYrAmt());
		rvwLvlCaseSummary.setTenYrRateLockInd(loanSelectionCaseSummary.getTenYrRateLockInd());
		rvwLvlCaseSummary.setTermTypCd(loanSelectionCaseSummary.getTermTypCd());
		rvwLvlCaseSummary.setTotalRequiredFundsToClose(loanSelectionCaseSummary.getTotalRequiredFundsToClose());
		rvwLvlCaseSummary.setTotAssetsEndrs(loanSelectionCaseSummary.getTotAssetsEndrs());
		rvwLvlCaseSummary.setTotClsngCstsEndrs(loanSelectionCaseSummary.getTotClsngCstsEndrs());
		rvwLvlCaseSummary.setTotFixedPymtEndrs(loanSelectionCaseSummary.getTotFixedPymtEndrs());
		rvwLvlCaseSummary.setTotMnthlyEffIncm(loanSelectionCaseSummary.getTotMnthlyEffIncm());
		rvwLvlCaseSummary.setTotMnthlyMtgPymtEndrs(loanSelectionCaseSummary.getTotMnthlyMtgPymtEndrs());
		rvwLvlCaseSummary.setUnderwritingMethod(loanSelectionCaseSummary.getUnderwritingMethod());
		rvwLvlCaseSummary.setYearBuilt(loanSelectionCaseSummary.getYearBuilt());
		rvwLvlCaseSummary.setCreatedBy(securityService.getUserId());
		rvwLvlCaseSummary.setCreatedTs(new Date());

		rvwLvlCaseSummary = rvwLvlCaseSummaryRepository.save(rvwLvlCaseSummary);

		reviewLevel.setRvwLvlCaseSummary(rvwLvlCaseSummary);

		return rvwLvlCaseSummary;
	}

	private void cloneRvwLvlCaseSummary(ReviewLevel currentReviewLevel, ReviewLevel newReviewLevel) {

		// If no case summary, don't even try (e.g. Operational reviews)
		if (currentReviewLevel.getRvwLvlCaseSummary() == null) {
			return;
		}

		RvwLvlCaseSummary rvwLvlCaseSummary = currentReviewLevel.getRvwLvlCaseSummary();
		RvwLvlCaseSummary clonedRvwLvlCaseSummary = new RvwLvlCaseSummary();
		clonedRvwLvlCaseSummary.setReviewLevel(newReviewLevel);

		clonedRvwLvlCaseSummary.setAddtnl10pctIplUsageInd(rvwLvlCaseSummary.getAddtnl10pctIplUsageInd());
		clonedRvwLvlCaseSummary.setAdpCode(rvwLvlCaseSummary.getAdpCode());
		clonedRvwLvlCaseSummary.setAmortTypCd(rvwLvlCaseSummary.getAmortTypCd());
		clonedRvwLvlCaseSummary.setApplicationDate(rvwLvlCaseSummary.getApplicationDate());
		clonedRvwLvlCaseSummary.setAppraiserName(rvwLvlCaseSummary.getAppraiserName());
		clonedRvwLvlCaseSummary.setAprslCmpltnDt(rvwLvlCaseSummary.getAprslCmpltnDt());
		clonedRvwLvlCaseSummary.setArmInd(rvwLvlCaseSummary.getArmInd());
		clonedRvwLvlCaseSummary.setArmIndxExpctdRt(rvwLvlCaseSummary.getArmIndxExpctdRt());
		clonedRvwLvlCaseSummary.setAssetsAfterClosingUw(rvwLvlCaseSummary.getAssetsAfterClosingUw());
		clonedRvwLvlCaseSummary.setAssumedLoanInd(rvwLvlCaseSummary.getAssumedLoanInd());
		clonedRvwLvlCaseSummary.setBackToWorkInd(rvwLvlCaseSummary.getBackToWorkInd());
		clonedRvwLvlCaseSummary.setBldgTyp(rvwLvlCaseSummary.getBldgTyp());
		clonedRvwLvlCaseSummary.setBnkrptcyAnyInd(rvwLvlCaseSummary.getBnkrptcyAnyInd());
		clonedRvwLvlCaseSummary.setBnkrptcyCd(rvwLvlCaseSummary.getBnkrptcyCd());
		clonedRvwLvlCaseSummary.setBnkrptcyChptr13Ind(rvwLvlCaseSummary.getBnkrptcyChptr13Ind());
		clonedRvwLvlCaseSummary.setBnkrptcyChptr7Ind(rvwLvlCaseSummary.getBnkrptcyChptr7Ind());
		clonedRvwLvlCaseSummary.setBnkrptcyDt(rvwLvlCaseSummary.getBnkrptcyDt());
		clonedRvwLvlCaseSummary.setBorr1FirstTimeBuyerInd(rvwLvlCaseSummary.getBorr1FirstTimeBuyerInd());
		clonedRvwLvlCaseSummary.setBorr1Name(rvwLvlCaseSummary.getBorr1Name());
		clonedRvwLvlCaseSummary.setBorr1RentingInd(rvwLvlCaseSummary.getBorr1RentingInd());
		clonedRvwLvlCaseSummary.setBorr1SelfEmplInd(rvwLvlCaseSummary.getBorr1SelfEmplInd());
		clonedRvwLvlCaseSummary.setBorr1Ssn(rvwLvlCaseSummary.getBorr1Ssn());
		clonedRvwLvlCaseSummary.setBorr2Name(rvwLvlCaseSummary.getBorr2Name());
		clonedRvwLvlCaseSummary.setBorr2Ssn(rvwLvlCaseSummary.getBorr2Ssn());
		clonedRvwLvlCaseSummary.setBorrBrthDt(rvwLvlCaseSummary.getBorrBrthDt());
		clonedRvwLvlCaseSummary.setBorrHsngExpEndrs(rvwLvlCaseSummary.getBorrHsngExpEndrs());
		clonedRvwLvlCaseSummary.setBorrPaidClosingCosts(rvwLvlCaseSummary.getBorrPaidClosingCosts());
		clonedRvwLvlCaseSummary.setBorrReqdInvestToClose(rvwLvlCaseSummary.getBorrReqdInvestToClose());
		clonedRvwLvlCaseSummary.setBorrTyp(rvwLvlCaseSummary.getBorrTyp());
		clonedRvwLvlCaseSummary.setBuildingOnOwnLandInd(rvwLvlCaseSummary.getBuildingOnOwnLandInd());
		clonedRvwLvlCaseSummary.setCaseNumber(rvwLvlCaseSummary.getCaseNumber());
		clonedRvwLvlCaseSummary.setCashoutRefiInd(rvwLvlCaseSummary.getCashoutRefiInd());
		clonedRvwLvlCaseSummary.setClaimType(rvwLvlCaseSummary.getClaimType());
		clonedRvwLvlCaseSummary.setClsngDt(rvwLvlCaseSummary.getClsngDt());
		clonedRvwLvlCaseSummary.setCombinedLoanToValuePct(rvwLvlCaseSummary.getCombinedLoanToValuePct());
		clonedRvwLvlCaseSummary.setConstCd(rvwLvlCaseSummary.getConstCd());
		clonedRvwLvlCaseSummary.setCreatedBy(rvwLvlCaseSummary.getCreatedBy());
		clonedRvwLvlCaseSummary.setCreatedTs(rvwLvlCaseSummary.getCreatedTs());
		clonedRvwLvlCaseSummary.setCurrentAtEndorseInd(rvwLvlCaseSummary.getCurrentAtEndorseInd());
		clonedRvwLvlCaseSummary.setDateOfPriorSale(rvwLvlCaseSummary.getDateOfPriorSale());
		clonedRvwLvlCaseSummary.setDcsnCdEndrs(rvwLvlCaseSummary.getDcsnCdEndrs());
		clonedRvwLvlCaseSummary.setDefaultEpisodeExistsInd(rvwLvlCaseSummary.getDefaultEpisodeExistsInd());
		clonedRvwLvlCaseSummary.setDisasterInd(rvwLvlCaseSummary.getDisasterInd());
		clonedRvwLvlCaseSummary.setEffDateAprslUpdate(rvwLvlCaseSummary.getEffDateAprslUpdate());
		clonedRvwLvlCaseSummary.setEndrsBynd60DaysCloseInd(rvwLvlCaseSummary.getEndrsBynd60DaysCloseInd());
		clonedRvwLvlCaseSummary.setEndrsmntDt(rvwLvlCaseSummary.getEndrsmntDt());
		clonedRvwLvlCaseSummary.setEscrowAmount(rvwLvlCaseSummary.getEscrowAmount());
		clonedRvwLvlCaseSummary.setExpectedRate(rvwLvlCaseSummary.getExpectedRate());
		clonedRvwLvlCaseSummary.setFctryFbrct(rvwLvlCaseSummary.getFctryFbrct());
		clonedRvwLvlCaseSummary.setFicoDecisionScoreEndrs(rvwLvlCaseSummary.getFicoDecisionScoreEndrs());
		clonedRvwLvlCaseSummary.setFlippingCategory2Ind(rvwLvlCaseSummary.getFlippingCategory2Ind());
		clonedRvwLvlCaseSummary.setFrclsrInd(rvwLvlCaseSummary.getFrclsrInd());
		clonedRvwLvlCaseSummary.setFrclsrStrtDt(rvwLvlCaseSummary.getFrclsrStrtDt());
		clonedRvwLvlCaseSummary.setFtInEps3mnthDelqDt(rvwLvlCaseSummary.getFtInEps3mnthDelqDt());
		clonedRvwLvlCaseSummary.setGiftLtr2Amt(rvwLvlCaseSummary.getGiftLtr2Amt());
		clonedRvwLvlCaseSummary.setGiftLtr2Source(rvwLvlCaseSummary.getGiftLtr2Source());
		clonedRvwLvlCaseSummary.setGiftLtr2Tin(rvwLvlCaseSummary.getGiftLtr2Tin());
		clonedRvwLvlCaseSummary.setGiftLtrAmt(rvwLvlCaseSummary.getGiftLtrAmt());
		clonedRvwLvlCaseSummary.setGiftLtrSrc(rvwLvlCaseSummary.getGiftLtrSrc());
		clonedRvwLvlCaseSummary.setGiftLtrTin(rvwLvlCaseSummary.getGiftLtrTin());
		clonedRvwLvlCaseSummary.setHecmCounselCertNo(rvwLvlCaseSummary.getHecmCounselCertNo());
		clonedRvwLvlCaseSummary.setHecmCounselDt(rvwLvlCaseSummary.getHecmCounselDt());
		clonedRvwLvlCaseSummary.setHecmPrncplLmt(rvwLvlCaseSummary.getHecmPrncplLmt());
		clonedRvwLvlCaseSummary.setHudReoRepairAmt(rvwLvlCaseSummary.getHudReoRepairAmt());
		clonedRvwLvlCaseSummary.setInitDisbursementLimit(rvwLvlCaseSummary.getInitDisbursementLimit());
		clonedRvwLvlCaseSummary.setInsurAppInTimeInd(rvwLvlCaseSummary.getInsurAppInTimeInd());
		clonedRvwLvlCaseSummary.setIntRt(rvwLvlCaseSummary.getIntRt());
		clonedRvwLvlCaseSummary.setInvest2ndResidInd(rvwLvlCaseSummary.getInvest2ndResidInd());
		clonedRvwLvlCaseSummary.setLastServicingMrtgXferDt(rvwLvlCaseSummary.getLastServicingMrtgXferDt());
		clonedRvwLvlCaseSummary.setLoanOfficer(rvwLvlCaseSummary.getLoanOfficer());
		clonedRvwLvlCaseSummary.setLoanOfficerNmls(rvwLvlCaseSummary.getLoanOfficerNmls());
		clonedRvwLvlCaseSummary.setLoanPrps(rvwLvlCaseSummary.getLoanPrps());
		clonedRvwLvlCaseSummary.setLoanType(rvwLvlCaseSummary.getLoanType());
		clonedRvwLvlCaseSummary.setLossmitCd(rvwLvlCaseSummary.getLossmitCd());
		clonedRvwLvlCaseSummary.setMandatoryObligBorrAmt(rvwLvlCaseSummary.getMandatoryObligBorrAmt());
		clonedRvwLvlCaseSummary.setMandatoryObligLendAmt(rvwLvlCaseSummary.getMandatoryObligLendAmt());
		clonedRvwLvlCaseSummary.setManUwStretchRatioInd(rvwLvlCaseSummary.getManUwStretchRatioInd());
		clonedRvwLvlCaseSummary.setMarriedToNbsInd(rvwLvlCaseSummary.getMarriedToNbsInd());
		clonedRvwLvlCaseSummary.setMaxClaimAmt(rvwLvlCaseSummary.getMaxClaimAmt());
		clonedRvwLvlCaseSummary.setMaxRate(rvwLvlCaseSummary.getMaxRate());
		clonedRvwLvlCaseSummary.setMiscAusDcsnCd(rvwLvlCaseSummary.getMiscAusDcsnCd());
		clonedRvwLvlCaseSummary.setMndtryOblgtnsAmt(rvwLvlCaseSummary.getMndtryOblgtnsAmt());
		clonedRvwLvlCaseSummary.setMnfctrdHusngInd(rvwLvlCaseSummary.getMnfctrdHusngInd());
		clonedRvwLvlCaseSummary.setMortExcldFncdMip(rvwLvlCaseSummary.getMortExcldFncdMip());
		clonedRvwLvlCaseSummary.setNonOccupyingBorrInd(rvwLvlCaseSummary.getNonOccupyingBorrInd());
		clonedRvwLvlCaseSummary.setNumLivingUnits(rvwLvlCaseSummary.getNumLivingUnits());
		clonedRvwLvlCaseSummary.setOcpncyStsCd(rvwLvlCaseSummary.getOcpncyStsCd());
		clonedRvwLvlCaseSummary.setOldstUnpdDt(rvwLvlCaseSummary.getOldstUnpdDt());
		clonedRvwLvlCaseSummary.setOrgntngMtgeeNmlsId(rvwLvlCaseSummary.getOrgntngMtgeeNmlsId());
		clonedRvwLvlCaseSummary.setOriginationFee(rvwLvlCaseSummary.getOriginationFee());
		clonedRvwLvlCaseSummary.setOrigMrtgAmt(rvwLvlCaseSummary.getOrigMrtgAmt());
		clonedRvwLvlCaseSummary.setPaymentPlan(rvwLvlCaseSummary.getPaymentPlan());
		clonedRvwLvlCaseSummary.setPdStrmlnFlg(rvwLvlCaseSummary.getPdStrmlnFlg());
		clonedRvwLvlCaseSummary.setPriceOfPriorSale(rvwLvlCaseSummary.getPriceOfPriorSale());
		clonedRvwLvlCaseSummary.setPriorSaleWithinLast3yrInd(rvwLvlCaseSummary.getPriorSaleWithinLast3yrInd());
		clonedRvwLvlCaseSummary.setPropAddr1(rvwLvlCaseSummary.getPropAddr1());
		clonedRvwLvlCaseSummary.setPropAddr2(rvwLvlCaseSummary.getPropAddr2());
		clonedRvwLvlCaseSummary.setPropAddrCity(rvwLvlCaseSummary.getPropAddrCity());
		clonedRvwLvlCaseSummary.setPropAddrSt(rvwLvlCaseSummary.getPropAddrSt());
		clonedRvwLvlCaseSummary.setPropAddrZip(rvwLvlCaseSummary.getPropAddrZip());
		clonedRvwLvlCaseSummary.setPrprtyAprslVl(rvwLvlCaseSummary.getPrprtyAprslVl());
		clonedRvwLvlCaseSummary.setQualifiedMrtgPointsAndFees(rvwLvlCaseSummary.getQualifiedMrtgPointsAndFees());
		clonedRvwLvlCaseSummary.setRatioFixTeiEndrs(rvwLvlCaseSummary.getRatioFixTeiEndrs());
		clonedRvwLvlCaseSummary.setRatioLoanToVlNew(rvwLvlCaseSummary.getRatioLoanToVlNew());
		clonedRvwLvlCaseSummary.setRatioTotPmtToTotIncEndrs(rvwLvlCaseSummary.getRatioTotPmtToTotIncEndrs());
		clonedRvwLvlCaseSummary.setRcvSaleDt(rvwLvlCaseSummary.getRcvSaleDt());
		clonedRvwLvlCaseSummary.setRefinanceInd(rvwLvlCaseSummary.getRefinanceInd());
		clonedRvwLvlCaseSummary.setReo100DownPmtProgInd(rvwLvlCaseSummary.getReo100DownPmtProgInd());
		clonedRvwLvlCaseSummary.setRepairCompletionDate(rvwLvlCaseSummary.getRepairCompletionDate());
		clonedRvwLvlCaseSummary.setReviewTypeCd(rvwLvlCaseSummary.getReviewTypeCd());
		clonedRvwLvlCaseSummary.setRfncCd(rvwLvlCaseSummary.getRfncCd());
		clonedRvwLvlCaseSummary.setSalePriceGtrAcqCostInd(rvwLvlCaseSummary.getSalePriceGtrAcqCostInd());
		clonedRvwLvlCaseSummary.setSalesPrice(rvwLvlCaseSummary.getSalesPrice());
		clonedRvwLvlCaseSummary.setSecondaryFinanceAmt(rvwLvlCaseSummary.getSecondaryFinanceAmt());
		clonedRvwLvlCaseSummary.setSecondaryFinanceExistsInd(rvwLvlCaseSummary.getSecondaryFinanceExistsInd());
		clonedRvwLvlCaseSummary.setSellerCntrbtn(rvwLvlCaseSummary.getSellerCntrbtn());
		clonedRvwLvlCaseSummary.setSellerCntrbtnPcnt(rvwLvlCaseSummary.getSellerCntrbtnPcnt());
		clonedRvwLvlCaseSummary.setSiteType(rvwLvlCaseSummary.getSiteType());
		clonedRvwLvlCaseSummary.setSoaCd(rvwLvlCaseSummary.getSoaCd());
		clonedRvwLvlCaseSummary.setSpecialProgram(rvwLvlCaseSummary.getSpecialProgram());
		clonedRvwLvlCaseSummary.setTaxesInsrncFrstYrAmt(rvwLvlCaseSummary.getTaxesInsrncFrstYrAmt());
		clonedRvwLvlCaseSummary.setTenYrRateLockInd(rvwLvlCaseSummary.getTenYrRateLockInd());
		clonedRvwLvlCaseSummary.setTermTypCd(rvwLvlCaseSummary.getTermTypCd());
		clonedRvwLvlCaseSummary.setTotalRequiredFundsToClose(rvwLvlCaseSummary.getTotalRequiredFundsToClose());
		clonedRvwLvlCaseSummary.setTotAssetsEndrs(rvwLvlCaseSummary.getTotAssetsEndrs());
		clonedRvwLvlCaseSummary.setTotClsngCstsEndrs(rvwLvlCaseSummary.getTotClsngCstsEndrs());
		clonedRvwLvlCaseSummary.setTotFixedPymtEndrs(rvwLvlCaseSummary.getTotFixedPymtEndrs());
		clonedRvwLvlCaseSummary.setTotMnthlyEffIncm(rvwLvlCaseSummary.getTotMnthlyEffIncm());
		clonedRvwLvlCaseSummary.setTotMnthlyMtgPymtEndrs(rvwLvlCaseSummary.getTotMnthlyMtgPymtEndrs());
		clonedRvwLvlCaseSummary.setUnderwritingMethod(rvwLvlCaseSummary.getUnderwritingMethod());
		clonedRvwLvlCaseSummary.setUpdatedBy(rvwLvlCaseSummary.getUpdatedBy());
		clonedRvwLvlCaseSummary.setUpdatedTs(rvwLvlCaseSummary.getUpdatedTs());
		clonedRvwLvlCaseSummary.setYearBuilt(rvwLvlCaseSummary.getYearBuilt());

		clonedRvwLvlCaseSummary.setCreatedBy(securityService.getUserId());
		clonedRvwLvlCaseSummary.setCreatedTs(new Date());

		clonedRvwLvlCaseSummary = rvwLvlCaseSummaryRepository.save(clonedRvwLvlCaseSummary);

		newReviewLevel.setRvwLvlCaseSummary(clonedRvwLvlCaseSummary);
	}

}
