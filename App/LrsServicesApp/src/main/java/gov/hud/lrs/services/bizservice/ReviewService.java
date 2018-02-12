package gov.hud.lrs.services.bizservice;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ImmutableList;

import gov.hud.lrs.common.dto.LrsApiV1DTOs.CommonDetailDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.DocumentDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.IndemAcceptDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.LiteReviewDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.ReviewConditionDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.ReviewDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.ReviewFieldDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.ReviewLevelDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.ReviewLevelInfoDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.ReviewNoteDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.ReviewNotePostDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.ReviewQaTreeDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.ReviewReferralDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.SimpleSelectionDTO;
import gov.hud.lrs.common.entity.Defect;
import gov.hud.lrs.common.entity.DictMetadataField;
import gov.hud.lrs.common.entity.DictMetadataFieldCondition;
import gov.hud.lrs.common.entity.DictMetadataFieldDefect;
import gov.hud.lrs.common.entity.DictMetadataFieldModel;
import gov.hud.lrs.common.entity.DictMetadataFieldRelatives;
import gov.hud.lrs.common.entity.Document;
import gov.hud.lrs.common.entity.Indemnification;
import gov.hud.lrs.common.entity.IndemnificationTypeRef;
import gov.hud.lrs.common.entity.Lender;
import gov.hud.lrs.common.entity.LenderRequest;
import gov.hud.lrs.common.entity.LoanSelection;
import gov.hud.lrs.common.entity.LrsGenLookup;
import gov.hud.lrs.common.entity.Note;
import gov.hud.lrs.common.entity.NoteTypeRef;
import gov.hud.lrs.common.entity.Personnel;
import gov.hud.lrs.common.entity.QaModel;
import gov.hud.lrs.common.entity.Qatree;
import gov.hud.lrs.common.entity.Review;
import gov.hud.lrs.common.entity.ReviewLevel;
import gov.hud.lrs.common.entity.ReviewReferral;
import gov.hud.lrs.common.entity.ReviewReferralTypeRef;
import gov.hud.lrs.common.entity.SelectionRequest;
import gov.hud.lrs.common.enumeration.NoteTypeCodes;
import gov.hud.lrs.common.enumeration.ReviewFieldUIControlCodes;
import gov.hud.lrs.common.enumeration.ReviewLevelStatusCodes;
import gov.hud.lrs.common.enumeration.ReviewLevelTypeCodes;
import gov.hud.lrs.common.enumeration.ReviewReferralTypeCodes;
import gov.hud.lrs.common.enumeration.ReviewStatusCodes;
import gov.hud.lrs.common.exception.BadRequestException;
import gov.hud.lrs.common.exception.ConflictException;
import gov.hud.lrs.common.exception.ForbiddenException;
import gov.hud.lrs.common.exception.NotFoundException;
import gov.hud.lrs.common.repository.DefectRepository;
import gov.hud.lrs.common.repository.DictMetadataFieldDefectRepository;
import gov.hud.lrs.common.repository.DictMetadataFieldModelRepository;
import gov.hud.lrs.common.repository.DictMetadataFieldRepository;
import gov.hud.lrs.common.repository.IndemnificationTypeRefRepository;
import gov.hud.lrs.common.repository.LoanSelectionCaseSummaryRepository;
import gov.hud.lrs.common.repository.LrsGenLookupRepository;
import gov.hud.lrs.common.repository.NoteRepository;
import gov.hud.lrs.common.repository.NoteTypeRefRepository;
import gov.hud.lrs.common.repository.ReviewLevelRepository;
import gov.hud.lrs.common.repository.ReviewReferralRepository;
import gov.hud.lrs.common.repository.ReviewReferralTypeRefRepository;
import gov.hud.lrs.common.repository.ReviewRepository;
import gov.hud.lrs.common.repository.RvwLvlCaseSummaryRepository;
import gov.hud.lrs.common.security.SecurityService;
import gov.hud.lrs.common.service.CommonReviewService;
import gov.hud.lrs.common.util.DateUtils;

@Service
public class ReviewService {

	private static final String RVW_LVL_CASE_SUMMARY_ENTITY_NAME = "Case Summary for Review Level";
	private static final String LOAN_SELECTION_CASE_SUMMARY_ENTITY_NAME = "Case Summary for Selection";
	private static final String VETTING_COMPLETED_STATUS = "Vetting Completed";
	private static final String VETTING_COMPLETED_EXCEPTION_STATUS = "Vetting Exception";
	private static final String PENDING_VETTING_STATUS = "Pending Vetting";

	//2017-01-22
	private static final String DATE_FORMAT_STRING = "yyyy-MM-dd";
	private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_STRING);

	private Logger logger = LoggerFactory.getLogger(ReviewService.class);

	@Autowired private DictMetadataFieldRepository dictMetadataFieldRepository;
	@Autowired private DictMetadataFieldModelRepository dictMetadataFieldModelRepository;
	@Autowired private LrsGenLookupRepository lrsGenLookupRepository;
	@Autowired private NoteRepository noteRepository;
	@Autowired private NoteTypeRefRepository noteTypeRefRepository;
	@Autowired private ReviewLevelRepository reviewLevelRepository;
	@Autowired private ReviewRepository reviewRepository;
	@Autowired private RvwLvlCaseSummaryRepository rvwLvlCaseSummaryRepository;
	@Autowired private LoanSelectionCaseSummaryRepository loanSelectionCaseSummaryRepository;
	@Autowired private DefectRepository defectRepository;
	@Autowired private ReviewReferralRepository reviewReferralRepository;
	@Autowired private ReviewReferralTypeRefRepository reviewReferralTypeRefRepository;
	@Autowired private DictMetadataFieldDefectRepository dictMetadataFieldDefectRepository;
	@Autowired private IndemnificationTypeRefRepository indemnificationTypeRefRepository;

	@Autowired private SecurityService securityService;
	@Autowired private CommonReviewService commonReviewService;

	@Autowired private WorkflowClient workflowClient;

	@PersistenceContext EntityManager entityManager;

	@Transactional(readOnly = true)
	public List<ReviewDTO> getActiveReviewDTOs() {

		List<Review> reviews = entityManager.createQuery(
			// this is not complete, but it's a start
			// there are still lots of things we could preload and several queries in the DTO conversion that need to be moved out of the loop
			"select distinct r from Review r " +
			"join fetch r.reviewStatusRef " +
			"join fetch r.reviewTypeRef " +
			"join fetch r.reviewScopeRef " +
			"join fetch r.selectionReason " +
			"join fetch r.reviewLevels rl " +
			"join fetch rl.reviewLevelTypeRef " +
			"join fetch rl.reviewLevelStatusRef " +
			"join fetch rl.ratingRef " +
			"left join fetch rl.rvwLvlCaseSummary " +
			"left join fetch rl.lenderRequest " +
			"left join fetch rl.indemnification " +
			"left join fetch rl.reviewerPersonnel " +
			"left join fetch r.batch " +
			"left join fetch r.loanSelection ls " +
			"left join fetch r.qaModel " +
			"left join fetch r.qcReview qc " +
			"left join fetch r.documents " +
			"where r.reviewStatusRef.code in (:reviewStatusCodes)",
			Review.class
		)
		.setParameter("reviewStatusCodes", ImmutableList.of(
			ReviewStatusCodes.ASSIGNED,
			ReviewStatusCodes.UNDER_REVIEW,
			ReviewStatusCodes.PENDING_LENDER_RESPONSE
		))
		.getResultList();

		return convertReviewsToReviewDTOsLight(reviews);
	}

	public List<ReviewDTO> getMyActiveReviewDTOs() {
		return convertReviewsToReviewDTOs(reviewRepository.findActiveAndVettingByReviewerPersonnelId(securityService.getPersonnel().getPersonnelId()), false);
	}

	public List<ReviewDTO> getMyLocationActiveReviewDTOs() {
		return convertReviewsToReviewDTOs(reviewRepository.findActiveByReviewLocationId(securityService.getReviewLocation().getReviewLocationId()), true);
	}

	public List<Review> getMyLenderReviews() {
		String lenderId = securityService.getFhacUser().getLenderId();
		if (lenderId == null) {
			throw new ForbiddenException("Only lenders can access this");
		}
		List<Review> lenderReviewList = reviewRepository.findByLenderLenderIdAndReviewStatusRefCode(lenderId, ReviewStatusCodes.PENDING_LENDER_RESPONSE);
		//Get the lender submitted reviews that are pending/under review at FHA and add them to the list
		lenderReviewList.addAll(reviewRepository.findLenderSubmittedReviewsPendingAtFHA(lenderId));

		return lenderReviewList;
	}

	public List<Review> getMyCompletedLenderReviews() {
		String lenderId = securityService.getFhacUser().getLenderId();
		if (lenderId == null) {
			throw new ForbiddenException("Only lenders can access this");
		}

		return reviewRepository.findByLenderLenderIdAndReviewStatusRefCode(lenderId, ReviewStatusCodes.COMPLETED);
	}

	public List<ReviewFieldDTO> getReviewLevelFields(String reviewId, String reviewLevelId) {
		Review review = reviewRepository.findOne(reviewId);
		QaModel qaModel = review.getQaModel();
		List<DictMetadataField> caseSummaryFields = dictMetadataFieldRepository.findByIdEntityNameOrderByIdFieldName(RVW_LVL_CASE_SUMMARY_ENTITY_NAME);
		List<DictMetadataField> loanSelectioncaseSummaryFields = dictMetadataFieldRepository.findByIdEntityNameOrderByIdFieldName(LOAN_SELECTION_CASE_SUMMARY_ENTITY_NAME);
		List<String> loanSelectionCaseSummaryFieldNames = new ArrayList<String>();
		for (DictMetadataField field : loanSelectioncaseSummaryFields) {
			if (null != field.getId().getFieldName()) {
				loanSelectionCaseSummaryFieldNames.add(field.getId().getFieldName());
			}
		}
		// for each field we need to return the possible answers for
		// select/multi-select, conditions to display the field, defect areas
		// the field should be displayed in, and values (updateables, at
		// endorsement, and at underwriting)
		List<ReviewFieldDTO> reviewFieldDTOs = new ArrayList<ReviewFieldDTO>();
		for (DictMetadataField field : caseSummaryFields) {
			DictMetadataFieldModel modelField = dictMetadataFieldModelRepository.findByQaModelAndIdEntityNameAndIdFieldName(qaModel, field.getId().getEntityName(), field.getId().getFieldName());

			List<LrsGenLookup> possibleAnswers = new ArrayList<LrsGenLookup>();
			if (
				field.getUiControlType().equalsIgnoreCase(ReviewFieldUIControlCodes.SELECT)	||
				field.getUiControlType().equalsIgnoreCase(ReviewFieldUIControlCodes.MULTISELECT) ||
				field.getUiControlType().equalsIgnoreCase(ReviewFieldUIControlCodes.BOOLEAN)
			) {
				possibleAnswers.addAll(lrsGenLookupRepository.findByIdLookupEntityAndIdLookupField(field.getId().getEntityName(), field.getId().getFieldName()));
			}

			// field value from review level case summary
			String fieldValue = null;
			if (field.getDbColumn() != null) {
				fieldValue = rvwLvlCaseSummaryRepository.findValueByReviewLevelId(reviewLevelId, field.getDbColumn());
			}

            Boolean isEditable = false;
			if (field.getUserEditableInd() == 'Y') {
				isEditable = true;
			}

			// field values at time of endorsement and underwriting from loan selection case summary
			List<String> endorsementValue = new ArrayList<String>();
			List<String> underwritingValue = new ArrayList<String>();
			for (DictMetadataFieldRelatives relative : field.getDictMetadataFieldRelatives()) {
				if (loanSelectionCaseSummaryFieldNames.contains(relative.getId().getFieldName())) {
					String value = loanSelectionCaseSummaryRepository.findValueBySelectionId(relative.getRelatedDbColumn(), review.getLoanSelection().getSelectionId());
					if (value != null) {
						if (!"DATE".equals(field.getUiControlType())) {
							value = value.replaceAll("\\s+", "");
						}
						List<String> relatedValues = new ArrayList<String>(Arrays.asList(value.split(",")));
						if ("At Endorsement".equalsIgnoreCase(relative.getId().getRelationshipType())) {
							endorsementValue.addAll(relatedValues);
						} else if ("At Underwriting".equalsIgnoreCase(relative.getId().getRelationshipType())) {
							underwritingValue.addAll(relatedValues);
						}
					}
				}
			}

			List<String> defectIds = new ArrayList<String>();
			for (DictMetadataFieldDefect dictMetadataFieldDefect : dictMetadataFieldDefectRepository.findByIdEntityNameAndIdFieldNameAndDefectIn(
					field.getId().getEntityName(),
					field.getId().getFieldName(),
					qaModel.getDefects())) {
				defectIds.add(dictMetadataFieldDefect.getId().getDefectId());
			}

			reviewFieldDTOs.add(
				convertDictMetadataFieldToReviewFieldDTO(
					field,
					modelField == null ? null : modelField.getDictMetadataFieldSubjectArea().getSubjectArea(),
					possibleAnswers,
					fieldValue,
					endorsementValue,
					underwritingValue,
                    isEditable,
					defectIds
				)
			);
		}
		return reviewFieldDTOs;
	}

	@Transactional
	public ReviewFieldDTO updateReviewLevelField(String reviewId, String reviewLevelId, ReviewFieldDTO reviewFieldDTO) {
		DictMetadataField dictMetadataField = dictMetadataFieldRepository.findByIdEntityNameAndIdFieldName(RVW_LVL_CASE_SUMMARY_ENTITY_NAME, reviewFieldDTO.getFieldId());
		if (dictMetadataField == null) {
			throw new BadRequestException("No DictMetadataField for entityName " + RVW_LVL_CASE_SUMMARY_ENTITY_NAME + " and fieldName " + reviewFieldDTO.getFieldId());
		}

		rvwLvlCaseSummaryRepository.updateValueByReviewLevelId(reviewLevelId, dictMetadataField.getDbColumn(), reviewFieldDTO.getValue(), securityService.getUserId());

		ReviewLevel reviewLevel = reviewLevelRepository.findOne(reviewLevelId);
		commonReviewService.markReviewLevelInProgress(reviewLevel);

		return reviewFieldDTO;
	}

	public List<ReviewNoteDTO> getReviewNotes(String reviewId) {
		NoteTypeRef reviewNoteTypeRef = noteTypeRefRepository.findByNoteTypeCd(NoteTypeCodes.REVIEW);
		Iterable<Note> noteEntities = noteRepository.findByNoteTypeRefAndReviewId(reviewNoteTypeRef, reviewId);

		List<ReviewNoteDTO> notes = new ArrayList<ReviewNoteDTO>();
		noteEntities.forEach((noteEntity) -> {
			notes.add(convertNoteToReviewNoteDTO(noteEntity));
		});
		return notes;
	}

	@Transactional
	public ReviewNoteDTO saveReviewNote(ReviewNotePostDTO reviewNotePostDTO, String reviewId) {
		Review review = reviewRepository.findOne(reviewId);
		if (review.getReviewStatusRef().getCode().equalsIgnoreCase(ReviewStatusCodes.COMPLETED)) {
			throw new ConflictException("Can't save note for already-completed Review " + reviewId);
		}

		Note note = new Note();
		note.setNoteTypeRef(noteTypeRefRepository.findByNoteTypeCd(NoteTypeCodes.REVIEW));
		note.setPersonnel(securityService.getPersonnel());
		note.setReviewId(reviewId);
		note.setAssociatedDefectCd(reviewNotePostDTO.getDefectAreaCode());
		String username = securityService.getUserId();
		note.setCreatedBy(username);
		note.setUpdatedBy(username);
		Date now = new Date();
		note.setCreatedTs(now);
		note.setUpdatedTs(now);
		note.setNoteText(reviewNotePostDTO.getText());
		note = noteRepository.save(note);

		return convertNoteToReviewNoteDTO(note);
	}

	private ReviewConditionDTO dictMetadataFieldConditionToReviewConditionDTO(DictMetadataFieldCondition dictMetadataFieldCondition) {
		ReviewConditionDTO reviewConditionDTO = new ReviewConditionDTO();
		reviewConditionDTO.setConditionId(dictMetadataFieldCondition.getMetadataFieldCondId());
		reviewConditionDTO.setFieldName(dictMetadataFieldCondition.getConditionDictMetadataField().getId().getFieldName());
		reviewConditionDTO.setOperator(dictMetadataFieldCondition.getConditionOperator());
		String csvComparisonValues = dictMetadataFieldCondition.getComparisonValues().replaceAll("\\s+", "");
		reviewConditionDTO.setComparisonValues(Arrays.asList(csvComparisonValues.split(",")));

		return reviewConditionDTO;
	}

	public ReviewNoteDTO convertNoteToReviewNoteDTO(Note note) {
		ReviewNoteDTO reviewNoteDTO = new ReviewNoteDTO();
		reviewNoteDTO.setLastUpdated(DateUtils.convertDateToNoonUtcDate(note.getUpdatedTs()));
		reviewNoteDTO.setDefectAreaCode(note.getAssociatedDefectCd());
		reviewNoteDTO.setText(note.getNoteText());

		Personnel personnel = note.getPersonnel();
		reviewNoteDTO.setHudId(personnel.getLoginCredential());
		reviewNoteDTO.setNameLast(personnel.getLastName());
		reviewNoteDTO.setNameFirst(personnel.getFirstName() +
			(StringUtils.isNotBlank(personnel.getMiddleName()) ? " " + personnel.getMiddleName() : ""));

		return reviewNoteDTO;
	}

	public LiteReviewDTO reviewToLiteReviewDtoXform(Review review) {
		LiteReviewDTO LiteReviewDTO = new LiteReviewDTO();
		LiteReviewDTO.setReviewId(review.getReviewId());
		LiteReviewDTO.setReviewReferenceId(review.getReviewReferenceId());
		LiteReviewDTO.setStatus(review.getReviewStatusRef().getDescription());

		return LiteReviewDTO;
	}

	private void removeReviewerNameAndLocation(ReviewDTO reviewDto) {
		ReviewLevelDTO currentReviewLevel = reviewDto.getCurrentReviewLevel();
		currentReviewLevel.setReviewerName(null);
		currentReviewLevel.setReviewLocationName(null);

		List<ReviewLevelDTO> completedReviewLevels =  reviewDto.getCompletedReviewLevels();
		for (ReviewLevelDTO rvwl : completedReviewLevels) {
			rvwl.setReviewerName(null);
			rvwl.setReviewLocationName(null);
		}

		List<ReviewLevelDTO> vettedReviewLevels = reviewDto.getCompletedReviewLevels().stream()
			.filter(reviewLevelDTO -> reviewLevelDTO.getVettingInd().equals("Y")).collect(Collectors.toList());
		for (ReviewLevelDTO vettedReviewLevel : vettedReviewLevels) {
			reviewDto.getCompletedReviewLevels().removeIf(reviewLevelDTO -> reviewLevelDTO.getIteration().equals(vettedReviewLevel.getIteration()) &&
					reviewLevelDTO.getType().equals(vettedReviewLevel.getType()) && !reviewLevelDTO.getVettingInd().equals("Y"));
		}
	}

	public void updateConfirmVetting(String reviewId, String reviewLevelId) {
		workflowClient.put("/api/v1/reviews/" + reviewId + "/levels/" + reviewLevelId + "/acknowledgeVetting", null, null);
	}

	public void submitReviewLevel(String reviewId, String reviewLevelId, ReviewLevelInfoDTO reviewLevelSubmitDTO) {
		workflowClient.put("/api/v1/reviews/" + reviewId + "/levels/" + reviewLevelId + "/submit", reviewLevelSubmitDTO, null);
	}

	public void submitLenderResponse(String reviewId, String reviewLevelId) {
		workflowClient.put("/api/v1/reviews/" + reviewId + "/levels/" + reviewLevelId + "/lenderSubmit", null, null);
	}

	public void managementDecision(String reviewId) {
		checkReviewLevelType(reviewId, ReviewLevelTypeCodes.HQ_ESCALATION);
		workflowClient.put("/api/v1/reviews/" + reviewId + "/managementDecision", null, null);
	}

	public void mrbReferral(String reviewId) {
		checkReviewLevelType(reviewId, ReviewLevelTypeCodes.HQ_ESCALATION);
		workflowClient.put("/api/v1/reviews/" + reviewId + "/mrbReferral", null, null);
	}

	public void forceIndemnification(String reviewId, ReviewLevelInfoDTO data) {
		String indemType = data.getIndemnificationTypeCode();
		workflowClient.put("/api/v1/reviews/" + reviewId + "/forceIndemnification" + "?indemType=" + indemType, null, null);
	}

	public void forceEscalation(String reviewId) {
		workflowClient.put("/api/v1/reviews/" + reviewId + "/forceEscalation", null, null);
	}

	public void cancelReview(Review review, String reasonCode) {
		cancelReview(review.getReviewId(), reasonCode);
	}

	public void cancelReview(String reviewId, String reasonCode) {
		workflowClient.put("/api/v1/reviews/" + reviewId + "/cancel", reasonCode, null);
	}

	public void lenderSubmitIndemnification(String reviewId, String reviewLevelId) {
		workflowClient.put("/api/v1/reviews/" + reviewId + "/levels/" + reviewLevelId + "/indemnification/lenderSubmit", null, null);
	}

	public void reassignReviewLevel(String reviewId, String reviewLevelId, SimpleSelectionDTO simpleSelectionDTO) {
		workflowClient.put("/api/v1/reviews/" + reviewId + "/levels/" + reviewLevelId + "/reassign", simpleSelectionDTO, String.class);
		// TODO: could easily check the return value and throw an exception or notify the client that assignment failed
	}

	public void indemReviewerSubmit(String reviewLevelId, IndemAcceptDTO indemAcceptDTO) {
		logger.debug("###in ReviewService.indemReviewerSubmit");
		workflowClient.put("/api/v1/reviews/" + reviewLevelId + "/indemReviewerSubmit", indemAcceptDTO, null);
		System.out.println("returned from mule indem call");
	}

	public void indemReviewerReject(String reviewId, String reviewLevelId) {
		logger.debug("###in ReviewService.indemReviewerReject");
		workflowClient.put("/api/v1/reviews/" + reviewLevelId + "/indemReviewerReject", null, null);
	}

	public List<Review> getReviewerCompletedReviews(String startDateString, String endDateString) {
		final Calendar calendar = Calendar.getInstance();
		final Date today = calendar.getTime();
		calendar.add(Calendar.YEAR, -1);
		final Date oneYearsAgo = calendar.getTime();

		Date startDate = null;
		Date endDate = null;
		try {
			if (startDateString != null && startDateString.trim().length()>0) {
				startDate = dateFormat.parse(startDateString);
			} else {
				startDate = oneYearsAgo;
			}
			if (endDateString != null && endDateString.trim().length()>0) {
				endDate = dateFormat.parse(endDateString);
			} else {
				endDate = today;
			}
		}  catch (Exception e) {
			logger.error("Unable to parse the input dates " +
					"startDate: " + startDateString + ", endDate: " + endDateString +	e.getMessage());
			throw new RuntimeException(e);
		}
		return reviewRepository.findReviewerCompletedReviews(securityService.getPersonnel().getPersonnelId(),
				DateUtils.getDateWithZeroHour(startDate), DateUtils.getDateWithLastHour(endDate));
	}

	public List<Review> getReviewMyCompletedLocationByLocationId(String reviewLocationId, String startDateString, String endDateString) {
		final Calendar calendar = Calendar.getInstance();
        final Date today = calendar.getTime();
		calendar.add(Calendar.YEAR, -1);
		final Date oneYearsAgo = calendar.getTime();

		Date startDate = null;
		Date endDate = null;
		try {
			if (startDateString != null && startDateString.trim().length()>0) {
				startDate = dateFormat.parse(startDateString);
			} else {
				startDate = oneYearsAgo;
			}
			if (endDateString != null && endDateString.trim().length()>0) {
				endDate = dateFormat.parse(endDateString);
			} else {
				endDate = today;
			}
		}  catch (Exception e) {
			logger.error("Unable to parse the input dates " +
					"startDate: " + startDateString + ", endDate: " + endDateString +	e.getMessage());
			throw new RuntimeException(e);
		}
		return reviewRepository.getReviewMyCompletedLocationByLocationId(reviewLocationId,
				DateUtils.getDateWithZeroHour(startDate), DateUtils.getDateWithLastHour(endDate));
	}

	@Transactional
	public ReviewReferralDTO updateReferral(String reviewId, ReviewReferralDTO reviewReferralDTO) {
		if (reviewId == null) {
			throw new BadRequestException("Review Id is required");
		}
		if (reviewReferralDTO == null) {
			throw new BadRequestException("No referrals were received");
		}
		Review review = reviewRepository.findOne(reviewId);
		if (review == null) {
			throw new BadRequestException("The review ID: " + reviewId + "was not found");
		}

		Date now = new Date();

		ReviewReferral oigReviewReferral = reviewReferralRepository.findByReviewAndReviewReferralTypeRef(review,
				reviewReferralTypeRefRepository.findByCode(ReviewReferralTypeCodes.OIG));
		if (reviewReferralDTO.getOigNotes() != null) {
			if (oigReviewReferral == null) {
				ReviewReferral reviewReferral = setValues(review, reviewReferralDTO, ReviewReferralTypeCodes.OIG);
				reviewReferralRepository.save(reviewReferral);
			} else if (oigReviewReferral != null) {
				oigReviewReferral.setNotes(reviewReferralDTO.getOigNotes());
				oigReviewReferral.setReferralTmstmp(now);
				reviewReferralRepository.save(oigReviewReferral);
			}
		} else if (oigReviewReferral != null) {
			reviewReferralRepository.delete(oigReviewReferral);
		}

		ReviewReferral extReviewReferral = reviewReferralRepository.findByReviewAndReviewReferralTypeRef(review,
				reviewReferralTypeRefRepository.findByCode(ReviewReferralTypeCodes.EXT));
		if (reviewReferralDTO.getExternalNotes() != null) {
			if (extReviewReferral == null) {
				ReviewReferral reviewReferral = setValues(review, reviewReferralDTO, ReviewReferralTypeCodes.EXT);
				reviewReferralRepository.save(reviewReferral);
			} else if (extReviewReferral != null) {
				extReviewReferral.setNotes(reviewReferralDTO.getExternalNotes());
				extReviewReferral.setReferralTmstmp(now);
				reviewReferralRepository.save(extReviewReferral);
			}
		} else if (extReviewReferral != null) {
			reviewReferralRepository.delete(extReviewReferral);
		}

		ReviewReferral hudReviewReferral = reviewReferralRepository.findByReviewAndReviewReferralTypeRef(review,
				reviewReferralTypeRefRepository.findByCode(ReviewReferralTypeCodes.HUD));
		if (reviewReferralDTO.getHudNotes() != null) {
			if (hudReviewReferral == null) {
				ReviewReferral reviewReferral = setValues(review, reviewReferralDTO, ReviewReferralTypeCodes.HUD);
				reviewReferralRepository.save(reviewReferral);
			} else if (hudReviewReferral != null) {
				hudReviewReferral.setNotes(reviewReferralDTO.getHudNotes());
				hudReviewReferral.setReferralTmstmp(now);
				reviewReferralRepository.save(hudReviewReferral);
			}
		} else if (hudReviewReferral != null) {
			reviewReferralRepository.delete(hudReviewReferral);
		}
		return convertReviewReferralsToReferralDTO(reviewReferralRepository.findByReview(review));
	}

	private ReviewReferral setValues(Review review, ReviewReferralDTO reviewReferralDTO, String typecode) {
		ReviewReferral reviewReferral = new ReviewReferral();
		Date now = new Date();
		String userId = securityService.getUserId();
		reviewReferral.setReview(review);
		ReviewReferralTypeRef reviewReferralTypeRef = reviewReferralTypeRefRepository.findByCode(typecode);
		reviewReferral.setReviewReferralTypeRef(reviewReferralTypeRef);
		if (ReviewReferralTypeCodes.OIG.equals(typecode)) {
			reviewReferral.setNotes(reviewReferralDTO.getOigNotes());
		} else if (ReviewReferralTypeCodes.EXT.equals(typecode)) {
			reviewReferral.setNotes(reviewReferralDTO.getExternalNotes());
		} else if (ReviewReferralTypeCodes.HUD.equals(typecode)) {
			reviewReferral.setNotes(reviewReferralDTO.getHudNotes());
		}
		reviewReferral.setReferralTmstmp(now);
		reviewReferral.setCreatedBy(userId);
		reviewReferral.setUpdatedBy(userId);
		reviewReferral.setCreatedTs(now);
		reviewReferral.setUpdatedTs(now);
		return reviewReferral;
	}

	public List<ReviewReferral> getReviewReferral(String reviewId) {
		if (reviewId == null) {
			throw new BadRequestException("Review Id is required");
		}
		if (reviewRepository.findOne(reviewId) == null) {
			throw new BadRequestException("Review does not exist");
		}
		return reviewReferralRepository.findByReview(reviewRepository.findOne(reviewId));
	}

	@Transactional
	public void deleteReviewReferral(String reviewId) {
		if (reviewId == null) {
			throw new BadRequestException("Review Id is required");
		}
		Review review = reviewRepository.findOne(reviewId);
		if (review == null) {
			throw new BadRequestException("Review does not exist");
		}
		List<ReviewReferral> reviewReferrals = reviewReferralRepository.findByReview(review);
		reviewReferralRepository.delete(reviewReferrals);
	}

	public ReviewReferralDTO convertReviewReferralsToReferralDTO(List<ReviewReferral> reviewReferrals) {
		ReviewReferralDTO reviewReferralDTO = new ReviewReferralDTO();
		for (ReviewReferral reviewReferral : reviewReferrals) {
			if (reviewReferral.getReviewReferralTypeRef() != null && reviewReferral.getReviewReferralTypeRef().getCode().equals(ReviewReferralTypeCodes.OIG)) {
				reviewReferralDTO.setOigNotes(reviewReferral.getNotes());
			}
			if (reviewReferral.getReviewReferralTypeRef() != null && reviewReferral.getReviewReferralTypeRef().getCode().equals(ReviewReferralTypeCodes.EXT)) {
				reviewReferralDTO.setExternalNotes(reviewReferral.getNotes());
			}
			if (reviewReferral.getReviewReferralTypeRef() != null && reviewReferral.getReviewReferralTypeRef().getCode().equals(ReviewReferralTypeCodes.HUD)) {
				reviewReferralDTO.setHudNotes(reviewReferral.getNotes());
			}
		}
		return reviewReferralDTO;
	}

	public List<ReviewDTO> convertReviewsToReviewDTOsLight(Collection<Review> reviews) {
		List<ReviewDTO> reviewDTOs = new ArrayList<ReviewDTO>(reviews.size());

		for (Review review : reviews) {
			// review
			ReviewDTO reviewDTO = new ReviewDTO();
			reviewDTO.setReviewId(review.getReviewId());
			reviewDTO.setCaseNumber(review.getCaseNumber());
			reviewDTO.setReviewReferenceId(review.getReviewReferenceId());
			if (review.getBatch() != null) {
				reviewDTO.setBatchId(review.getBatch().getBatchId());
				reviewDTO.setBatchReferenceId(review.getBatch().getBatchReferenceId());
			}
			reviewDTO.setScope(review.getReviewScopeRef().getCode());
			reviewDTO.setStatus(review.getReviewStatusRef().getDescription());
			reviewDTO.setReviewType(review.getReviewTypeRef().getDescription());
			reviewDTO.setSelectionReason(review.getSelectionReason().getDescription());
			reviewDTO.setRvwCompltdDt(DateUtils.convertDateToNoonUtcDate(review.getRvwCompltdDt()));

			if (review.getLoanSelection() != null) {
				LoanSelection loanSelection = review.getLoanSelection();
				reviewDTO.setBorrowerName(loanSelection.getBorrName());
				reviewDTO.setEndorsementDate(DateUtils.convertDateToNoonUtcDate(loanSelection.getEndrsmntDt()));
				reviewDTO.setPropertyStreetAddress(loanSelection.getPropAddr1());
				reviewDTO.setCaseEstablishmentDate(DateUtils.convertDateToNoonUtcDate(loanSelection.getCsEstabDt()));
			}
			Lender lender = review.getLender();
			if (lender != null) {
				reviewDTO.setLenderId(lender.getLenderId());
				reviewDTO.setLenderName(lender.getName());
			}

			QaModel qaModel = review.getQaModel();
			reviewDTO.setQaModelId(qaModel.getQaModelId());

			if (review.getQcReview() != null) {
				reviewDTO.setOriginalQcReviewId(review.getQcReview().getReviewId());
				ReviewLevel originalFinalReviewLevel = review.getQcReview().getReviewLevels().stream().max((x, y) -> x.getCreatedTs().compareTo(y.getCreatedTs())).get();
				reviewDTO.setOriginalQcFinalReviewLevelId(originalFinalReviewLevel.getReviewLevelId());
			}

			// review levels
			reviewDTO.setCompletedReviewLevels(new ArrayList<ReviewLevelDTO>());
			List<ReviewLevel> sortedReviewLevels = new ArrayList<ReviewLevel>(review.getReviewLevels());
			sortedReviewLevels.sort(Comparator.comparing(ReviewLevel::getCreatedTs));
			for (int i = 0; i < sortedReviewLevels.size(); i++) {
				ReviewLevel reviewLevel = sortedReviewLevels.get(i);
				ReviewLevelDTO reviewLevelDTO = convertReviewLevelToReviewLevelDTO(reviewLevel);

				if (i < sortedReviewLevels.size() - 1) {
					reviewDTO.getCompletedReviewLevels().add(reviewLevelDTO);
				} else {
					reviewDTO.setCurrentReviewLevel(reviewLevelDTO);
					String cd = reviewLevel.getReviewLevelStatusRef().getCode();
					if (
						cd.equals(ReviewLevelStatusCodes.COMPLETED) ||
						cd.equals(ReviewLevelStatusCodes.CANCELLED) ||
						cd.equals(ReviewLevelStatusCodes.EXCEPTION)
					) {
						reviewDTO.getCompletedReviewLevels().add(reviewLevelDTO);
					}
				}
			}
			reviewDTO = removeCompletedVettingReviewLevels(reviewDTO);
			reviewDTOs.add(reviewDTO);
		}

		return reviewDTOs;
	}

	public List<ReviewDTO> convertReviewsToReviewDTOs(Collection<Review> reviews, boolean isForTeamTab) {
		List<ReviewDTO> reviewDTOs = new ArrayList<ReviewDTO>(reviews.size());

		for (Review review : reviews) {
			// review
			ReviewDTO reviewDTO = new ReviewDTO();
			reviewDTO.setReviewId(review.getReviewId());
			reviewDTO.setCaseNumber(review.getCaseNumber());
			reviewDTO.setReviewReferenceId(review.getReviewReferenceId());
			if (review.getBatch() != null) {
				reviewDTO.setBatchId(review.getBatch().getBatchId());
				reviewDTO.setBatchReferenceId(review.getBatch().getBatchReferenceId());
			}
			reviewDTO.setScope(review.getReviewScopeRef().getCode());
			reviewDTO.setStatus(review.getReviewStatusRef().getDescription());
			reviewDTO.setReviewType(review.getReviewTypeRef().getDescription());
			reviewDTO.setSelectionReason(review.getSelectionReason().getDescription());
			reviewDTO.setRvwCompltdDt(DateUtils.convertDateToNoonUtcDate(review.getRvwCompltdDt()));

			if (review.getLoanSelection() != null) {
				LoanSelection loanSelection = review.getLoanSelection();
				reviewDTO.setBorrowerName(loanSelection.getBorrName());
				reviewDTO.setEndorsementDate(DateUtils.convertDateToNoonUtcDate(loanSelection.getEndrsmntDt()));
				reviewDTO.setPropertyStreetAddress(loanSelection.getPropAddr1());
				reviewDTO.setCaseEstablishmentDate(DateUtils.convertDateToNoonUtcDate(loanSelection.getCsEstabDt()));
				reviewDTO.setSelectionRequestId(loanSelection.getSelectionRequest().getSelectionRequestId());
				reviewDTO.setSelectedDate(DateUtils.convertDateToNoonUtcDate(loanSelection.getSelectionDt()));
				reviewDTO.setBinderRequestDate(DateUtils.convertDateToNoonUtcDate(loanSelection.getRequestedDtTm()));
				reviewDTO.setBinderReceivedDate(DateUtils.convertDateToNoonUtcDate(loanSelection.getReceivedDt()));
			}
			else {
				// Operational review, no selection and case number in review record
				SelectionRequest selectionRequest = review.getBatch().getSelectionRequest();
				reviewDTO.setSelectionRequestId(selectionRequest.getSelectionRequestId());
				reviewDTO.setSelectedDate(DateUtils.convertDateToNoonUtcDate(selectionRequest.getCreatedTs()));
			}

			Lender lender = review.getLender();
			if (lender != null) {
				reviewDTO.setLenderId(lender.getLenderId());
				reviewDTO.setLenderName(lender.getName());
			}
			reviewDTO.setLastLenderName(review.getLastLenderName());

			QaModel qaModel = review.getQaModel();
			reviewDTO.setQaModelId(qaModel.getQaModelId());

			if (review.getQcReview() != null) {
				reviewDTO.setOriginalQcReviewId(review.getQcReview().getReviewId());
				ReviewLevel originalFinalReviewLevel = review.getQcReview().getReviewLevels().stream().max((x, y) -> x.getCreatedTs().compareTo(y.getCreatedTs())).get();
				reviewDTO.setOriginalQcFinalReviewLevelId(originalFinalReviewLevel.getReviewLevelId());
			}

			// review levels
			reviewDTO.setCompletedReviewLevels(new ArrayList<ReviewLevelDTO>());
			List<ReviewLevel> sortedReviewLevels = new ArrayList<ReviewLevel>(review.getReviewLevels());
			sortedReviewLevels.sort(Comparator.comparing(ReviewLevel::getCreatedTs));
			for (int i = 0; i < sortedReviewLevels.size(); i++) {
				ReviewLevel reviewLevel = sortedReviewLevels.get(i);
				ReviewLevelDTO reviewLevelDTO = convertReviewLevelToReviewLevelDTO(reviewLevel);

				if (i < sortedReviewLevels.size() - 1) {
					reviewDTO.getCompletedReviewLevels().add(reviewLevelDTO);
				} else {
					reviewDTO.setCurrentReviewLevel(reviewLevelDTO);
					String cd = reviewLevel.getReviewLevelStatusRef().getCode();
					if (
						cd.equals(ReviewLevelStatusCodes.COMPLETED) ||
						cd.equals(ReviewLevelStatusCodes.CANCELLED) ||
						cd.equals(ReviewLevelStatusCodes.EXCEPTION)
					) {
						reviewDTO.getCompletedReviewLevels().add(reviewLevelDTO);
					}
				}
			}
			// defect codes & qa trees
			List<String> defectIds = new ArrayList<String>();
			List<ReviewQaTreeDTO> reviewQaTreeDTOs = new ArrayList<ReviewQaTreeDTO>();

			for (Defect defect : defectRepository.findByQaModelIdAndReviewTypeId(qaModel.getQaModelId(), review.getReviewTypeRef().getReviewTypeId())) {
				defectIds.add(defect.getDefectId());

				for (Qatree qatree : qaModel.getQatrees()) {
					if (qatree.getDefect().getDefectId() == defect.getDefectId()) {
						ReviewQaTreeDTO reviewQaTreeDTO = new ReviewQaTreeDTO();
						reviewQaTreeDTO.setQaTreeId(qatree.getQatreeId());
						reviewQaTreeDTO.setDefectAreaCode(qatree.getDefect().getDefectCd());
						reviewQaTreeDTOs.add(reviewQaTreeDTO);
						break;
					}
				}
			}
			reviewDTO.setDefectAreaIds(defectIds);
			reviewDTO.setQaTrees(reviewQaTreeDTOs);

			// documents
			// PERF TODO: we can avoid queries in the inner loop by load documents for all reviews at the top and indexing
			Set<Document> documents = review.getDocuments();
			List<DocumentDTO> documentDTOs = new ArrayList<DocumentDTO>();
			for (Document document : documents) {
				DocumentDTO documentDTO = new DocumentDTO();
				documentDTO.setDocumentId(document.getDocumentId());
				documentDTO.setFileName(document.getFileName());
				documentDTOs.add(documentDTO);
			}
			reviewDTO.setDocuments(documentDTOs);
			if (isForTeamTab) {
				reviewDTO = removeCompletedVettingReviewLevels(reviewDTO);
			} else {
				reviewDTO = removeVettingReviewLevels(reviewDTO);
			}
			reviewDTOs.add(reviewDTO);
		}

		return reviewDTOs;
	}

	public ReviewDTO getReviewByReviewId(String reviewId) {
		Review review = reviewRepository.findOne(reviewId);
		if (review == null) {
			throw new NotFoundException("No Review for reviewId " + reviewId);
		}

		ReviewDTO reviewDTO = convertReviewToReviewDTO(review);
		reviewDTO = removeVettingReviewLevels(reviewDTO);
		return reviewDTO;
	}

	private ReviewDTO removeCompletedVettingReviewLevels(ReviewDTO reviewDTO) {
		ReviewLevelDTO unacknowledgedReviewLevel = reviewDTO.getCompletedReviewLevels().stream()
			.filter(reviewLevelDTO ->
				(reviewLevelDTO.getVettingInd().equals("Y")) &&
				(reviewLevelDTO.getVetteeAcknowledgedDate() == null)
			)
			.findFirst().orElse(null);
		if (unacknowledgedReviewLevel != null) {
			ReviewLevelDTO unacknowledgedVetteeReviewLevel = reviewDTO.getCompletedReviewLevels().stream()
				.filter(reviewLevelDTO ->
					(reviewLevelDTO.getIteration().equals(unacknowledgedReviewLevel.getIteration())) &&
					(reviewLevelDTO.getType().equals(unacknowledgedReviewLevel.getType())) &&
					(!reviewLevelDTO.getVettingInd().equals("Y"))
				)
				.findFirst().orElse(null);
			ListIterator<ReviewLevelDTO> iterator = reviewDTO.getCompletedReviewLevels().listIterator(reviewDTO.getCompletedReviewLevels().size());
			iterator.previous();
			if (
				(unacknowledgedVetteeReviewLevel != null) &&
				(unacknowledgedReviewLevel.getReviewLevelId().equals(reviewDTO.getCurrentReviewLevel().getReviewLevelId()))
			) {
				iterator.remove();
				unacknowledgedVetteeReviewLevel.setStatus(VETTING_COMPLETED_STATUS);
				reviewDTO.setCurrentReviewLevel(unacknowledgedVetteeReviewLevel);
			}
		}
		return reviewDTO;
	}

	private ReviewDTO removeVettingReviewLevels(ReviewDTO reviewDTO) {
		final String personnelId = securityService.isLender() ? null : securityService.getPersonnel().getPersonnelId();

		// remove any vettee review levels
		// for the vettee, remove review levels that have been acknowledged
		// needed so that the ui does not show two cards per review level type when displaying a finding
		List<ReviewLevelDTO> vettedReviewLevels = reviewDTO.getCompletedReviewLevels().stream()
			.filter(reviewLevelDTO ->
				(reviewLevelDTO.getVettingInd().equals("Y"))
			)
			.collect(Collectors.toList());
		for (ReviewLevelDTO vettedReviewLevel : vettedReviewLevels) {
			ListIterator<ReviewLevelDTO> iterator = reviewDTO.getCompletedReviewLevels().listIterator();
			while (iterator.hasNext()) {
				ReviewLevelDTO reviewLevel = iterator.next();
				if (
					(reviewLevel.getIteration().equals(vettedReviewLevel.getIteration())) &&
					(reviewLevel.getType().equals(vettedReviewLevel.getType())) &&
					(!reviewLevel.getVettingInd().equals("Y")) &&
					(
						(!reviewLevel.getReviewerId().equals(personnelId)) ||
						(
							(reviewLevel.getReviewerId().equals(personnelId)) &&
							(vettedReviewLevel.getVetteeAcknowledgedDate() != null)
						)
					)
				) {
					iterator.remove();
					break;
				}
			}
		}
		// for the vettee, discard any review levels created after the vetted review level if it has not been acknowledged
		// needed so that when an escalated review level is created, the vettee will still have the ability to acknowledge a vetted review level
		ReviewLevelDTO unacknowledgedReviewLevel = reviewDTO.getCompletedReviewLevels().stream()
			.filter(reviewLevelDTO ->
				(reviewLevelDTO.getVettingInd().equals("Y")) &&
				(reviewLevelDTO.getVetteeAcknowledgedDate() == null)
			)
			.findFirst().orElse(null);
		if (unacknowledgedReviewLevel != null) {
			ReviewLevelDTO unacknowledgedVetteeReviewLevel = reviewDTO.getCompletedReviewLevels().stream()
				.filter(reviewLevelDTO ->
					(reviewLevelDTO.getIteration().equals(unacknowledgedReviewLevel.getIteration())) &&
					(reviewLevelDTO.getType().equals(unacknowledgedReviewLevel.getType())) &&
					(!reviewLevelDTO.getVettingInd().equals("Y"))
				)
				.findFirst().orElse(null);
			if (
				(unacknowledgedVetteeReviewLevel != null) &&
				(unacknowledgedVetteeReviewLevel.getReviewerId().equals(personnelId))
			) {
				reviewDTO.setCurrentReviewLevel(unacknowledgedReviewLevel);
				ListIterator<ReviewLevelDTO> iterator = reviewDTO.getCompletedReviewLevels().listIterator(reviewDTO.getCompletedReviewLevels().size());
				while (iterator.hasPrevious()) {
					ReviewLevelDTO reviewLevel = iterator.previous();
					if (reviewLevel.getReviewLevelId().equals(unacknowledgedReviewLevel.getReviewLevelId())) {
						break;
					} else {
						iterator.remove();
					}
				}
			}
		}
		return reviewDTO;
	}

	public ReviewDTO convertReviewToReviewDTO(Review review) {
		return convertReviewsToReviewDTOs(ImmutableList.of(review), false).get(0);
	}

	public ReviewDTO convertReviewToReviewDTORemoveNameAndLocation(Review review) {
		ReviewDTO reviewDTO = convertReviewToReviewDTO(review);
		removeReviewerNameAndLocation(reviewDTO);
		return reviewDTO;
	}

	public List<ReviewDTO> convertReviewsToReviewDTOsRemoveNameAndLocation(List<Review> reviews) {
		List<ReviewDTO> reviewDTOs = new ArrayList<ReviewDTO>();
		for (Review review : reviews) {
			ReviewDTO reviewDTO = convertReviewToReviewDTO(review);
			removeReviewerNameAndLocation(reviewDTO);
			reviewDTOs.add(reviewDTO);
		}

		return reviewDTOs;
	}

	public ReviewLevelDTO convertReviewLevelToReviewLevelDTO(ReviewLevel reviewLevel) {
		Review review = reviewLevel.getReview();
		ReviewLevelDTO reviewLevelDTO = new ReviewLevelDTO();
		reviewLevelDTO.setReviewLevelId(reviewLevel.getReviewLevelId());

		reviewLevelDTO.setIteration(String.valueOf(reviewLevel.getIterationNumber()));
		reviewLevelDTO.setType(reviewLevel.getReviewLevelTypeRef().getDescription());

		if (reviewLevel.getIndemnificationTypeRef() != null) {
			reviewLevelDTO.setIndemnificationTypeCode(reviewLevel.getIndemnificationTypeRef().getCode());
		}

		// special cases for vetting reviews
		if (
			(reviewLevel.getVettingInd() == 'Y') &&
			(reviewLevel.getVetteeAcknowledgementDt() == null) &&
			(
				ReviewLevelStatusCodes.COMPLETED.equals(reviewLevel.getReviewLevelStatusRef().getCode()) ||
				ReviewStatusCodes.PENDING_LENDER_RESPONSE.equals(reviewLevel.getReview().getReviewStatusRef().getCode())
			)
		) {
			reviewLevelDTO.setStatus(VETTING_COMPLETED_STATUS);
		} else if ((reviewLevel.getVettingInd() == 'Y') &&
			(reviewLevel.getVetteeAcknowledgementDt() == null) &&
			(ReviewLevelStatusCodes.IN_PROGRESS.equals(reviewLevel.getReviewLevelStatusRef().getCode()))
		) {
			reviewLevelDTO.setStatus(PENDING_VETTING_STATUS);
		} else if ((reviewLevel.getVettingInd() == 'Y') &&
			(reviewLevel.getVetteeAcknowledgementDt() == null) &&
			(ReviewLevelStatusCodes.EXCEPTION.equals(reviewLevel.getReviewLevelStatusRef().getCode()))
		) {
			reviewLevelDTO.setStatus(VETTING_COMPLETED_EXCEPTION_STATUS);
		} else {
			reviewLevelDTO.setStatus(reviewLevel.getReviewLevelStatusRef().getDescription());
		}

		if (reviewLevel.getRatingRef() != null) {
			reviewLevelDTO.setRatingCode(reviewLevel.getRatingRef().getCode());
		}
		reviewLevelDTO.setReviewerDateAssigned(DateUtils.convertDateToNoonUtcDate(reviewLevel.getAssignDt()));
		reviewLevelDTO.setReviewerDateDue(DateUtils.convertDateToNoonUtcDate(reviewLevel.getDueDate()));
		if (reviewLevel.getReviewLocation() != null) {
			reviewLevelDTO.setReviewLocationName(reviewLevel.getReviewLocation().getLocationName());
		}
		if (reviewLevel.getReviewerPersonnel() != null) {
			reviewLevelDTO.setReviewerName(
				reviewLevel.getReviewerPersonnel().getFirstName() +
				(StringUtils.isNotBlank(reviewLevel.getReviewerPersonnel().getMiddleName()) ? " " + reviewLevel.getReviewerPersonnel().getMiddleName() + " " : " ") +
				reviewLevel.getReviewerPersonnel().getLastName());
			reviewLevelDTO.setReviewerId(reviewLevel.getReviewerPersonnel().getPersonnelId());
		}
		if (reviewLevel.getOriginalReviewerPersonnel() != null) {
			reviewLevelDTO.setOriginalReviewerName(
				reviewLevel.getOriginalReviewerPersonnel().getFirstName() +
				(StringUtils.isNotBlank(reviewLevel.getOriginalReviewerPersonnel().getMiddleName()) ? " " + reviewLevel.getOriginalReviewerPersonnel().getMiddleName() + " " : " ") +
				reviewLevel.getOriginalReviewerPersonnel().getLastName());
			reviewLevelDTO.setOriginalReviewerId(reviewLevel.getOriginalReviewerPersonnel().getPersonnelId());
		}

		LenderRequest lenderRequest = reviewLevel.getLenderRequest();
		if (lenderRequest != null) {
			reviewLevelDTO.setDateRequestSentToLender(DateUtils.convertDateToNoonUtcDate(lenderRequest.getRequestedDate()));
			reviewLevelDTO.setDateResponseDueFromLender(DateUtils.convertDateToNoonUtcDate(lenderRequest.getDueDate()));
		}
		reviewLevelDTO.setReviewLocationId(reviewLevel.getReviewLocation().getReviewLocationId());

		Indemnification indemnification = reviewLevel.getIndemnification();
		if (indemnification != null) {
			Date indemnificationStartDate = null;
			Date agreementDate = null;
			String indemnificationStart = commonReviewService.getIndemnificationStart(reviewLevel.getReviewLevelId());
			reviewLevelDTO.setIndemnificationStart(indemnificationStart);

			if (ReviewStatusCodes.COMPLETED.equals(review.getReviewStatusRef().getCode())) {
				// Indem review completed, get the value created when submitted
				indemnificationStartDate = indemnification.getStartDate();
				agreementDate = indemnification.getFhaSignedDate();
			} else {
				// Indem not submitted, get the calculated start date and agreement date
				indemnificationStartDate = commonReviewService.getIndemnificationStartDate(reviewLevel.getReviewLevelId());
				agreementDate = new Date();
			}
			
			reviewLevelDTO.setIndemnificationAgreementDate(DateUtils.convertDateToNoonUtcDate(agreementDate));

			// Only retrieve indem expiration if we have a start date
			if (indemnificationStartDate != null) {
				Date expirationDate = commonReviewService.getIndemnificationExpirationDate(reviewLevel.getReviewLevelId(), indemnificationStartDate);
				reviewLevelDTO.setIndemnificationExpirationDate(DateUtils.convertDateToNoonUtcDate(expirationDate));
			}

			int term = 0;
			if (reviewLevel.getIndemnificationTypeRef() != null) {
				term = commonReviewService.getIndemnificationTerm(reviewLevel.getReviewLevelId());
				reviewLevelDTO.setTermOfAgreement(String.valueOf(term));
			}
		}

		reviewLevelDTO.setVetteeAcknowledgedDate(DateUtils.convertDateToNoonUtcDate(reviewLevel.getVetteeAcknowledgementDt()));
		reviewLevelDTO.setVettingInd(Character.toString(reviewLevel.getVettingInd()));
		if (review.getQcOutcomeRef() != null) {
			reviewLevelDTO.setQcOutcomeCode(review.getQcOutcomeRef().getCode());
		}

		reviewLevelDTO.setReviewerCompletedDate(DateUtils.convertDateToNoonUtcDate(reviewLevel.getCompltDt()));
		reviewLevelDTO.setUpdatedById(reviewLevel.getUpdatedBy());

		if (reviewLevel.getForceEscalationDt() != null) {
			reviewLevelDTO.setForceEscalationDate(DateUtils.convertDateToNoonUtcDate(reviewLevel.getForceEscalationDt()));
		}
		
		return reviewLevelDTO;
	}

 	public ReviewFieldDTO convertDictMetadataFieldToReviewFieldDTO(
 	 		DictMetadataField dictMetadataField,
 	 		String attributeGroup,
 	 		List<LrsGenLookup> possibleAnswers,
 	 		String value,
 	 		List<String> endorsementValue,
 	 		List<String> underwritingValue,
            Boolean isEditable,
 	 		List<String> defectIds
 		) {
		ReviewFieldDTO reviewFieldDTO = new ReviewFieldDTO();
		reviewFieldDTO.setFieldId(dictMetadataField.getId().getFieldName());
		reviewFieldDTO.setLoanAttributeGroup(attributeGroup);
        reviewFieldDTO.setIsEditable(isEditable);
		reviewFieldDTO.setMaxValue(
			dictMetadataField.getFieldMaxValue() == null ? null : new BigDecimal(dictMetadataField.getFieldMaxValue())
		);
		reviewFieldDTO.setMinValue(
			dictMetadataField.getFieldMinValue() == null ? null : new BigDecimal(dictMetadataField.getFieldMinValue())
		);
		reviewFieldDTO.setName(dictMetadataField.getDisplayName());
		reviewFieldDTO.setOrder(dictMetadataField.getDisplayOrder());
		reviewFieldDTO.setType(dictMetadataField.getUiControlType());

		reviewFieldDTO.setValue(value);
		reviewFieldDTO.setValuesAtEndorsement(endorsementValue);
		reviewFieldDTO.setValuesAtUnderwriting(underwritingValue);

		if (possibleAnswers != null) {
			List<CommonDetailDTO> answerDetails = new ArrayList<CommonDetailDTO>();
			for (LrsGenLookup answer : possibleAnswers) {
				CommonDetailDTO commonDTO = new CommonDetailDTO();
				commonDTO.setCode(answer.getId().getLookupCode());
				commonDTO.setDescription(answer.getLookupDescription());
				answerDetails.add(commonDTO);
			}
			reviewFieldDTO.setSelectChoices(answerDetails);
		}

		reviewFieldDTO.setDisplayInDefectAreas(defectIds);

		List<ReviewConditionDTO> questionConditions = dictMetadataField
			.getDictMetadataFieldConditions()
			.stream()
			.map(c -> dictMetadataFieldConditionToReviewConditionDTO(c))
			.collect(Collectors.<ReviewConditionDTO> toList());
		reviewFieldDTO.setConditionsToDisplay(questionConditions);

		return reviewFieldDTO;
	}

	public void checkReviewLevelType(String reviewId, String reviewLevelTypeCode) {
		ReviewLevel reviewLevel = reviewLevelRepository.findActiveOrExceptionByReviewId(reviewId);
		if (reviewLevel == null) {
			throw new ConflictException("Review " + reviewId + " does not have an active or exception review level.");
		} else if (!reviewLevel.getReviewLevelTypeRef().getReviewLevelCd().equals(reviewLevelTypeCode)) {
			throw new ConflictException("Active or exception review level " + reviewLevel.getReviewLevelId() + " is not in HQ Escalation.");
		}
	}

	public void updateReviewLevelIndemnificationType(String reviewLevelId, String indemnificationTypeCode ) {
		ReviewLevel reviewLevel = reviewLevelRepository.findOne(reviewLevelId);
		IndemnificationTypeRef indemnificationTypeRef = indemnificationTypeRefRepository.findByCode(indemnificationTypeCode);
		if (indemnificationTypeRef == null) {
			throw new ConflictException("indemnificationTypeCode " + indemnificationTypeCode + " is invalid");
		}

		reviewLevel.setIndemnificationTypeRef(indemnificationTypeRef);
		reviewLevelRepository.save(reviewLevel);
	}
}