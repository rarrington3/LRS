package gov.hud.lrs.services.bizservice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.hud.lrs.common.dto.FhacUserDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.DocumentDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.RemediationResponseDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.ReviewFindingDTO;
import gov.hud.lrs.common.entity.Defect;
import gov.hud.lrs.common.entity.DefectCause;
import gov.hud.lrs.common.entity.DefectRemedyType;
import gov.hud.lrs.common.entity.DefectSeverity;
import gov.hud.lrs.common.entity.DefectSource;
import gov.hud.lrs.common.entity.Document;
import gov.hud.lrs.common.entity.FindingDocument;
import gov.hud.lrs.common.entity.Note;
import gov.hud.lrs.common.entity.NoteTypeRef;
import gov.hud.lrs.common.entity.Personnel;
import gov.hud.lrs.common.entity.QatreeQuestion;
import gov.hud.lrs.common.entity.RatingRef;
import gov.hud.lrs.common.entity.Review;
import gov.hud.lrs.common.entity.ReviewLevel;
import gov.hud.lrs.common.entity.RvwLvlFinding;
import gov.hud.lrs.common.entity.RvwLvlFindingQuestion;
import gov.hud.lrs.common.enumeration.LenderRequestStatusCodes;
import gov.hud.lrs.common.enumeration.NoteTypeCodes;
import gov.hud.lrs.common.enumeration.RatingCodes;
import gov.hud.lrs.common.exception.NotFoundException;
import gov.hud.lrs.common.repository.DefectCauseRepository;
import gov.hud.lrs.common.repository.DefectRemedyTypeRepository;
import gov.hud.lrs.common.repository.DefectRepository;
import gov.hud.lrs.common.repository.DefectSeverityRepository;
import gov.hud.lrs.common.repository.DefectSourceRepository;
import gov.hud.lrs.common.repository.DocumentRepository;
import gov.hud.lrs.common.repository.FindingDocumentRepository;
import gov.hud.lrs.common.repository.FindingRatingRuleRepository;
import gov.hud.lrs.common.repository.NoteRepository;
import gov.hud.lrs.common.repository.NoteTypeRefRepository;
import gov.hud.lrs.common.repository.PersonnelRepository;
import gov.hud.lrs.common.repository.QatreeQuestionRepository;
import gov.hud.lrs.common.repository.RatingRefRepository;
import gov.hud.lrs.common.repository.ReviewLevelRepository;
import gov.hud.lrs.common.repository.ReviewRepository;
import gov.hud.lrs.common.repository.RvwLvlFindingQuestionRepository;
import gov.hud.lrs.common.repository.RvwLvlFindingRepository;
import gov.hud.lrs.common.security.SecurityService;
import gov.hud.lrs.common.service.CommonReviewService;
import gov.hud.lrs.common.service.FhacUserService;
import gov.hud.lrs.common.util.DateUtils;

@Service
public class FindingService {

	@Autowired private DefectRepository defectRepository;
	@Autowired private DefectSourceRepository defectSourceRepository;
	@Autowired private DefectCauseRepository defectCauseRepository;
	@Autowired private DefectSeverityRepository defectSeverityRepository;
	@Autowired private DefectRemedyTypeRepository defectRemedyTypeRepository;
	@Autowired private DocumentRepository documentRepository;
	@Autowired private FindingRatingRuleRepository findingRatingRuleRepository;
	@Autowired private NoteRepository noteRepository;
	@Autowired private NoteTypeRefRepository noteTypeRefRepository;
	@Autowired private PersonnelRepository personnelRepository;
	@Autowired private QatreeQuestionRepository qatreeQuestionRepository;
	@Autowired private RatingRefRepository ratingRefRepository;
	@Autowired private RvwLvlFindingRepository rvwLvlFindingRepository;
	@Autowired private ReviewRepository reviewRepository;
	@Autowired private ReviewLevelRepository reviewLevelRepository;
	@Autowired private RvwLvlFindingQuestionRepository rvwLvlFindingQuestionRepository;
	@Autowired private FindingDocumentRepository findingDocumentRepository;

	@Autowired private CommonReviewService commonReviewService;
	@Autowired private SecurityService securityService;
	@Autowired private FhacUserService fhacUserService;

	public void updateReviewLevelFindingLender(String reviewId, String findingId, ReviewFindingDTO reviewFindingDTO) {
		RvwLvlFinding rvwLvlFinding = rvwLvlFindingRepository.findOne(findingId);
		if (rvwLvlFinding == null) {
			throw new NotFoundException("RvwLvlFinding " + findingId + " doesn't exist");
		}

		NoteTypeRef lenderCommentsNoteType = noteTypeRefRepository.findByNoteTypeCd(NoteTypeCodes.COMMENTS_FROM_LENDER);

		String userId = securityService.getUserId();
		Date now = new Date();

		Note lenderComments = noteRepository.findTopByNoteTypeRefAndFindingIdOrderByUpdatedTsDesc(lenderCommentsNoteType, rvwLvlFinding.getFindingId());
		if (lenderComments == null) {
			lenderComments = new Note();
			lenderComments.setNoteTypeRef(lenderCommentsNoteType);
			lenderComments.setCreatedBy(userId);
			lenderComments.setCreatedTs(now);
			lenderComments.setReviewLevelId(rvwLvlFinding.getReviewLevel().getReviewLevelId());
			lenderComments.setFindingId(rvwLvlFinding.getFindingId());
		}

		lenderComments.setNoteText(reviewFindingDTO.getLenderResponseComment());
		lenderComments.setUpdatedBy(userId);
		lenderComments.setUpdatedTs(now);
		noteRepository.save(lenderComments);

		List<DocumentDTO> lenderResponseDocuments = reviewFindingDTO.getLenderResponseDocuments();
		for (DocumentDTO documentDto : lenderResponseDocuments) {
			if (findingDocumentRepository.findByFindingIdAndDocumentId(findingId, documentDto.getDocumentId()) == null) {
				Document document = documentRepository.findOne(documentDto.getDocumentId());
				if (document != null) {
					FindingDocument findingDocument = new FindingDocument();
					findingDocument.setDocument(document);
					findingDocument.setRvwLvlFinding(rvwLvlFinding);
					findingDocument.setCreatedBy(userId);
					findingDocument.setCreatedTs(now);
					findingDocument.setUpdatedBy(userId);
					findingDocument.setUpdatedTs(now);
					findingDocument = findingDocumentRepository.save(findingDocument);
				}
			}
		}

		// Save last action lender name
		Review review = reviewRepository.findOne(reviewId);
		review.setLastLenderName(securityService.getFullName());
		reviewRepository.save(review);
	}


	@Transactional
	public ReviewFindingDTO createRvwLvlFinding(ReviewFindingDTO findingDTO, String reviewLevelId) {
		ReviewLevel reviewLevel = reviewLevelRepository.findOne(reviewLevelId);
		commonReviewService.markReviewLevelInProgress(reviewLevel);

		RvwLvlFinding rvwLvlFinding = new RvwLvlFinding();
		rvwLvlFinding.setReviewLevel(reviewLevel);
		rvwLvlFinding.setOriginalReviewLevel(reviewLevel);
		Defect defect = defectRepository.findByDefectCdAndQaModel(findingDTO.getDefectAreaCode(), reviewLevel.getReview().getQaModel());
		rvwLvlFinding.setDefect(defect);
		rvwLvlFinding.setDefectCd(defect.getDefectCd());
		DefectSource defectSource = defectSourceRepository.findByDefectAndDefectSourceCd(defect, findingDTO.getSelectedSourceCode());
		rvwLvlFinding.setDefectSource(defectSource);
		rvwLvlFinding.setDefectSourceCd(defectSource.getDefectSourceCd());
		DefectCause defectCause = defectCauseRepository.findByDefectAndDefectCauseCd(defect, findingDTO.getSelectedCauseCode());
		rvwLvlFinding.setDefectCause(defectCause);
		rvwLvlFinding.setDefectCauseCd(defectCause.getDefectCauseCd());

		if (findingDTO.getSelectedTierCode() != null) {
			DefectSeverity defectSeverity = defectSeverityRepository.findByDefectAndSeverityTierCd(defect, Integer.parseInt(findingDTO.getSelectedTierCode()));
			rvwLvlFinding.setDefectSeverity(defectSeverity);
			rvwLvlFinding.setSeverityTierCd(defectSeverity.getSeverityTierCd());
		}

		rvwLvlFinding.setNotes(findingDTO.getCommentToLender());
		String username = securityService.getUserId();
		rvwLvlFinding.setCreatedBy(username);
		rvwLvlFinding.setUpdatedBy(username);
		Date now = new Date();
		rvwLvlFinding.setCreatedTs(now);
		rvwLvlFinding.setUpdatedTs(now);

		// calculate finding rating
		String reviewerResponse = findingDTO.getReviewerResponseAdjusted();
		if (reviewerResponse != null) {
			// Rescind finding
			rvwLvlFinding.setRecissionInd('Y');
			if (reviewerResponse.equalsIgnoreCase("C")) {
				rvwLvlFinding.setRatingRef(ratingRefRepository.findByCode(RatingCodes.CONFORMING));
			} else {
				// Accept new severity tier
				DefectSeverity newDefectSeverity = defectSeverityRepository.findByDefectAndSeverityTierCd(defect, Integer.parseInt(reviewerResponse));
				rvwLvlFinding.setDefectSeverity(newDefectSeverity);
				rvwLvlFinding.setSeverityTierCd(newDefectSeverity.getSeverityTierCd());
				rvwLvlFinding.setRatingRef(getFindingRating(rvwLvlFinding));
			}

		} else if (findingDTO.getReviewerResponseMitigated() != null && findingDTO.getReviewerResponseMitigated()) {
			// Mitigated finding
			rvwLvlFinding.setRatingRef(ratingRefRepository.findByCode(RatingCodes.MITIGATED));

		} else if (findingDTO.getReviewerResponseRemediated() != null) {
			// Remediated finding
			DefectRemedyType defectRemedyType = defectRemedyTypeRepository.findByDefectAndCode(defect, findingDTO.getReviewerResponseRemediated().getRemediationTypeCode());
			rvwLvlFinding.setDefectRemedyType(defectRemedyType);
			rvwLvlFinding.setRemedyAmount(findingDTO.getReviewerResponseRemediated().getRemediationAmount().intValue());
			rvwLvlFinding.setRemediedDt(new Date());
			rvwLvlFinding.setRatingRef(ratingRefRepository.findByCode(RatingCodes.REMEDIATED));

		} else {
			// Regular finding
			rvwLvlFinding.setRatingRef(getFindingRating(rvwLvlFinding));
		}

		// save now so we can link to subobjects
		// this merely gens the ID and puts in session, no DB write yet
		rvwLvlFinding = rvwLvlFindingRepository.save(rvwLvlFinding);

		if (findingDTO.getIsAdhoc() == null || findingDTO.getIsAdhoc() == false) {
			Set<RvwLvlFindingQuestion> rvwLvlFindingQuestions = rvwLvlFinding.getRvwLvlFindingQuestions();
			List<QatreeQuestion> qatreeQuestions = qatreeQuestionRepository.findByQuestionIdIn(findingDTO.getQuestionIds());
			for (QatreeQuestion qatreeQuestion : qatreeQuestions) {
				RvwLvlFindingQuestion rvwLvlFindingQuestion = new RvwLvlFindingQuestion();
				rvwLvlFindingQuestion.setQatreeQuestion(qatreeQuestion);
				rvwLvlFindingQuestion.setRvwLvlFinding(rvwLvlFinding);
				rvwLvlFindingQuestion = rvwLvlFindingQuestionRepository.save(rvwLvlFindingQuestion);
				rvwLvlFindingQuestions.add(rvwLvlFindingQuestion);
			}
		}

		// flush before calling the calculateReviewLevelRating, otherwise ReviewLevel will be set to stale/incorrect status.
		rvwLvlFindingRepository.flush();

		reviewLevel.setRatingRef(calculateReviewLevelRating(reviewLevel));

		return convertRvwLvlFindingToReviewFindingDTO(rvwLvlFinding);
	}

	@Transactional
	public ReviewFindingDTO updateRvwLvlFinding(ReviewFindingDTO findingDTO, String findingId) {
		RvwLvlFinding rvwLvlFinding = rvwLvlFindingRepository.findOne(findingId);
		if (rvwLvlFinding == null) {
			throw new RuntimeException("No RvwLvlFinding for findingId " + findingId);
		}

		ReviewLevel reviewLevel = rvwLvlFinding.getReviewLevel();
		commonReviewService.markReviewLevelInProgress(reviewLevel);

		DefectSeverity defectSeverity = defectSeverityRepository.findByDefectAndSeverityTierCd(rvwLvlFinding.getDefect(), Integer.parseInt(findingDTO.getSelectedTierCode()));
		rvwLvlFinding.setDefectSeverity(defectSeverity);
		rvwLvlFinding.setSeverityTierCd(defectSeverity.getSeverityTierCd());
		rvwLvlFinding.setNotes(findingDTO.getCommentToLender());
		rvwLvlFinding.setUpdatedBy(securityService.getUserId());
		rvwLvlFinding.setUpdatedTs(new Date());

		String reviewerResponse = findingDTO.getReviewerResponseAdjusted();
		if (reviewerResponse != null) {
			// Rescind finding
			rvwLvlFinding.setRecissionInd('Y');
			if (reviewerResponse.equalsIgnoreCase("C")) {
				rvwLvlFinding.setRatingRef(ratingRefRepository.findByCode(RatingCodes.CONFORMING));
			} else {
				// Accept new severity tier
				DefectSeverity newDefectSeverity = defectSeverityRepository.findByDefectAndSeverityTierCd(rvwLvlFinding.getDefect(), Integer.parseInt(reviewerResponse));
				rvwLvlFinding.setDefectSeverity(newDefectSeverity);
				rvwLvlFinding.setSeverityTierCd(newDefectSeverity.getSeverityTierCd());
				rvwLvlFinding.setRatingRef(getFindingRating(rvwLvlFinding));
			}

		} else if (findingDTO.getReviewerResponseMitigated() != null && findingDTO.getReviewerResponseMitigated()) {
			// Mitigated finding
			rvwLvlFinding.setRatingRef(ratingRefRepository.findByCode(RatingCodes.MITIGATED));

		} else if (findingDTO.getReviewerResponseRemediated() != null) {
			// Remediated finding
			Defect defect = defectRepository.findByDefectCdAndQaModel(findingDTO.getDefectAreaCode(), reviewLevel.getReview().getQaModel());
			DefectRemedyType defectRemedyType = defectRemedyTypeRepository.findByDefectAndCode(defect, findingDTO.getReviewerResponseRemediated().getRemediationTypeCode());
			rvwLvlFinding.setDefectRemedyType(defectRemedyType);
			rvwLvlFinding.setRemedyAmount(findingDTO.getReviewerResponseRemediated().getRemediationAmount().intValue());
			rvwLvlFinding.setRemediedDt(new Date());
			rvwLvlFinding.setRatingRef(ratingRefRepository.findByCode(RatingCodes.REMEDIATED));

		} else {
			// Regular finding
			rvwLvlFinding.setRatingRef(getFindingRating(rvwLvlFinding));
		}

		rvwLvlFinding = rvwLvlFindingRepository.save(rvwLvlFinding);
		//flush before calling the calculateReviewLevelRating, otherwise ReviewLevel will be set to stale/incorrect status.
		rvwLvlFindingRepository.flush();

		reviewLevel.setRatingRef(calculateReviewLevelRating(reviewLevel));

		// Update questions in the DB if the questionsIds in the findingDTO has changed
		final Set<RvwLvlFindingQuestion> rvwLvlFindingQuestions = rvwLvlFinding.getRvwLvlFindingQuestions();

		// get all the questionIds of a finding in DB
		List<String> questionIdsFromDB = new ArrayList<String>();
		for (RvwLvlFindingQuestion rvwLvlFindingQuestion: rvwLvlFindingQuestions) {
			questionIdsFromDB.add(rvwLvlFindingQuestion.getQatreeQuestion().getQuestionId());
		}

		// QuestionIds that are in the findingDTO
		// Ensure questions passed in from DTO, If none, could be adhoc finding,
		// or all questions removed from finding (will be deleted soon...)
		final List<String> questionIdsFromDTO = findingDTO.getQuestionIds() != null ? findingDTO.getQuestionIds() : new ArrayList<String>();

		// work on copy as we need unmodified original later
		List<String> questionsToBeAddedToDB =  new ArrayList<String>(questionIdsFromDTO);

		// remove the questions from the list that are already in the DB
		questionsToBeAddedToDB.removeAll(questionIdsFromDB);

		if (!questionsToBeAddedToDB.isEmpty()) {
			List<QatreeQuestion> qatreeQuestionsToBeAddedToDB = qatreeQuestionRepository.findByQuestionIdIn(questionsToBeAddedToDB);

			// add the new qatreeQuestions to the DB
			for (QatreeQuestion qatreeQuestion : qatreeQuestionsToBeAddedToDB) {
				RvwLvlFindingQuestion rvwLvlFindingQuestion = new RvwLvlFindingQuestion();
				rvwLvlFindingQuestion.setQatreeQuestion(qatreeQuestion);
				rvwLvlFindingQuestion.setRvwLvlFinding(rvwLvlFinding);
				rvwLvlFindingQuestion = rvwLvlFindingQuestionRepository.save(rvwLvlFindingQuestion);
				rvwLvlFindingQuestions.add(rvwLvlFindingQuestion);
			}
			rvwLvlFinding.setRvwLvlFindingQuestions(rvwLvlFindingQuestions);
		}

		// remove questions from DB that are NOT in the fidningDTO
		questionIdsFromDB.removeAll(questionIdsFromDTO);
		if (!questionIdsFromDB.isEmpty()) {
			List<QatreeQuestion> qatreeQuestionsFromDB = qatreeQuestionRepository.findByQuestionIdIn(questionIdsFromDB);
			for (QatreeQuestion qatreeQuestion : qatreeQuestionsFromDB) {
				rvwLvlFindingQuestionRepository.deleteByRvwLvlFindingAndQatreeQuestion(rvwLvlFinding, qatreeQuestion);
			}
		}

		return convertRvwLvlFindingToReviewFindingDTO(rvwLvlFinding);
	}

	public RatingRef getFindingRating(RvwLvlFinding finding) {
		// Lookup threshold
		Integer threshold = findingRatingRuleRepository.findThresholdByRules(
			finding.getDefect().getDefectId(),
			finding.getDefectSource().getDefectSourceId(),
			finding.getDefectCause().getDefectCauseId()
		);
		if (threshold != null && finding.getDefectSeverity() != null) {
			if (finding.getDefectSeverity().getSeverityTierCd() <= threshold) {
				return ratingRefRepository.findByCode(RatingCodes.UNACCEPTABLE);
			} else {
				return ratingRefRepository.findByCode(RatingCodes.DEFICIENT);
			}
		} else {
			// No threshold is found or no selected severity  yet
			return ratingRefRepository.findByCode(RatingCodes.DEFICIENT);
		}
	}

	@Transactional
	public void deleteRvwLvlFinding(String findingId) {
		RvwLvlFinding rvwLvlFinding = rvwLvlFindingRepository.findOne(findingId);
		if (rvwLvlFinding == null) {
			throw new NotFoundException("No RvwLvlFinding for findingId " + findingId);
		}

		ReviewLevel reviewLevel = rvwLvlFinding.getReviewLevel();

		rvwLvlFindingQuestionRepository.deleteByRvwLvlFinding(rvwLvlFinding);
		rvwLvlFindingRepository.delete(findingId);

		// we must do this before calling calc rating, otherwise the "deleted" finding will show up in that query
		rvwLvlFindingRepository.flush();

		reviewLevel.setRatingRef(calculateReviewLevelRating(reviewLevel));
	}

	public List<ReviewFindingDTO> convertRvwLvlFindingsToReviewFindingDTOs(List<RvwLvlFinding> rvwLvlFindings) {
		// TODO: make this fast by loading all db values out of loop and indexing
		List<ReviewFindingDTO> reviewFindingDTOs = new ArrayList<ReviewFindingDTO>();
		for (RvwLvlFinding rvwLvlFinding : rvwLvlFindings) {
			reviewFindingDTOs.add(convertRvwLvlFindingToReviewFindingDTO(rvwLvlFinding));
		}

		return reviewFindingDTOs;
	}

	public ReviewFindingDTO convertRvwLvlFindingToReviewFindingDTO(RvwLvlFinding rvwLvlFinding) {
		ReviewFindingDTO reviewFindingDTO = new ReviewFindingDTO();

		reviewFindingDTO.setFindingId(rvwLvlFinding.getFindingId());

		Personnel personnel = personnelRepository.findByLoginCredential(rvwLvlFinding.getUpdatedBy());
		if (personnel != null) {
			reviewFindingDTO.setNameFirst(personnel.getFirstName() +
				(StringUtils.isNotBlank(personnel.getMiddleName()) ? " " + personnel.getMiddleName() : ""));
			reviewFindingDTO.setNameLast(personnel.getLastName());
		}

		reviewFindingDTO.setLastUpdated(DateUtils.convertDateToNoonUtcDate(rvwLvlFinding.getUpdatedTs()));
		reviewFindingDTO.setReviewLevelId(rvwLvlFinding.getReviewLevel().getReviewLevelId());
		reviewFindingDTO.setDefectAreaCode(rvwLvlFinding.getDefect().getDefectCd());
		reviewFindingDTO.setSelectedSourceCode(rvwLvlFinding.getDefectSource().getDefectSourceCd());
		reviewFindingDTO.setSelectedCauseCode(rvwLvlFinding.getDefectCause().getDefectCauseCd());

		if (rvwLvlFinding.getDefectSeverity() != null) {
			reviewFindingDTO.setSelectedTierCode(String.valueOf(rvwLvlFinding.getDefectSeverity().getSeverityTierCd()));
		}

		reviewFindingDTO.setCommentToLender(rvwLvlFinding.getNotes());
		reviewFindingDTO.setOriginalReviewLevelId(rvwLvlFinding.getOriginalReviewLevel().getReviewLevelId());
		if (rvwLvlFinding.getRatingRef() != null) {
			reviewFindingDTO.setRatingCode(rvwLvlFinding.getRatingRef().getCode());
		}

		Set<RvwLvlFindingQuestion> rvwLvlFindingQuestions = rvwLvlFinding.getRvwLvlFindingQuestions();
		if (!rvwLvlFindingQuestions.isEmpty()) {
			List<String> questionIds = new ArrayList<String>();
			for (RvwLvlFindingQuestion rvwLvlFindingQuestion : rvwLvlFindingQuestions) {
				QatreeQuestion q = rvwLvlFindingQuestion.getQatreeQuestion();
				questionIds.add(q.getQuestionId());
			}
			reviewFindingDTO.setQuestionIds(questionIds);
			reviewFindingDTO.setIsAdhoc(false);
		} else {
			reviewFindingDTO.setIsAdhoc(true);
		}

		Date lenderResponseDate = null;
		String lenderUsername = null;
		NoteTypeRef reviewNoteTypeRef = noteTypeRefRepository.findByNoteTypeCd(NoteTypeCodes.COMMENTS_FROM_LENDER);
		Note noteLatest = noteRepository.findTopByNoteTypeRefAndFindingIdOrderByUpdatedTsDesc(reviewNoteTypeRef, rvwLvlFinding.getFindingId());
		if (noteLatest != null) {
			reviewFindingDTO.setLenderResponseComment(noteLatest.getNoteText());
			lenderResponseDate = noteLatest.getUpdatedTs();
			lenderUsername = noteLatest.getUpdatedBy();
		}

		// Populate list of files
		List<DocumentDTO> documentDTOs = new ArrayList<DocumentDTO>();
		List<Document> documents = documentRepository.findByFindingId(rvwLvlFinding.getFindingId());
		for (Document document : documents) {
			DocumentDTO documentDTO = new DocumentDTO();
			documentDTO.setDocumentId(document.getDocumentId());
			documentDTO.setFileName(document.getFileName());
			documentDTOs.add(documentDTO);

			// Getting the later one of document or note
			if (lenderResponseDate == null || document.getUpdatedTs().after(lenderResponseDate)) {
				lenderResponseDate = document.getUpdatedTs();
				lenderUsername = document.getUpdatedBy();
			}
		}
		reviewFindingDTO.setLenderResponseDocuments(documentDTOs);

		// Determine response date and name
		reviewFindingDTO.setLenderResponseDate(DateUtils.convertDateToNoonUtcDate(lenderResponseDate));
		if (lenderUsername != null) {
			FhacUserDTO fhacUserDTO = fhacUserService.getFhacUser(lenderUsername);
			if (fhacUserDTO != null) {
				reviewFindingDTO.setLenderResponderName(fhacUserDTO.getLastName() + ", " + fhacUserDTO.getFirstName());
			}
		}

		// Remediation info
		if (rvwLvlFinding.getDefectRemedyType() != null) {
			RemediationResponseDTO remediationResponseDto = new RemediationResponseDTO();
			remediationResponseDto.setRemediationTypeCode(rvwLvlFinding.getDefectRemedyType().getCode());
			remediationResponseDto.setRemediationAmount(new BigDecimal(rvwLvlFinding.getRemedyAmount()));
			reviewFindingDTO.setReviewerResponseRemediated(remediationResponseDto);
		}

		// Expired?
		if (rvwLvlFinding.getReviewLevel().getLenderRequest() != null &&
			rvwLvlFinding.getReviewLevel().getLenderRequest().getLenderRequestStatusRef().getCode().equals(LenderRequestStatusCodes.EXPIRED)) {
			reviewFindingDTO.setLenderResponseExpiredDate(rvwLvlFinding.getReviewLevel().getLenderRequest().getUpdatedTs());
		}

		return reviewFindingDTO;
	}

	private RatingRef calculateReviewLevelRating(ReviewLevel reviewLevel) {
		RatingRef minRatingRef = ratingRefRepository.findByReviewLevelIdAndMinRankOrder(reviewLevel.getReviewLevelId());
		if (minRatingRef == null) {
			return ratingRefRepository.findByCode(RatingCodes.CONFORMING);
		} else {
			return minRatingRef;
		}
	}

}
