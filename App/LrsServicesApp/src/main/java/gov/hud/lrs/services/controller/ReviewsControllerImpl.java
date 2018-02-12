package gov.hud.lrs.services.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import gov.hud.lrs.common.dto.LrsApiV1DTOs.BatchDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.CommonDetailDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.DefectAreaCauseDictionaryDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.DefectAreaDictionaryDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.DefectAreaSeverityDictionaryDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.DefectAreaSourceDictionaryDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.DocumentDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.IndemAcceptDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.ReviewAnswerDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.ReviewDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.ReviewFieldDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.ReviewFindingDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.ReviewLevelInfoDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.ReviewNoteDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.ReviewNotePostDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.ReviewQuestionDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.ReviewReferralDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.SimpleSelectionDTO;
import gov.hud.lrs.common.entity.Batch;
import gov.hud.lrs.common.entity.Defect;
import gov.hud.lrs.common.entity.DefectCause;
import gov.hud.lrs.common.entity.DefectRemedyType;
import gov.hud.lrs.common.entity.DefectSeverity;
import gov.hud.lrs.common.entity.DefectSource;
import gov.hud.lrs.common.entity.Document;
import gov.hud.lrs.common.entity.Review;
import gov.hud.lrs.common.entity.ReviewTypeDefect;
import gov.hud.lrs.common.entity.ReviewTypeRef;
import gov.hud.lrs.common.entity.RvwLvlFinding;
import gov.hud.lrs.common.enumeration.DocumentTypeCodes;
import gov.hud.lrs.common.enumeration.Roles;
import gov.hud.lrs.common.exception.NotFoundException;
import gov.hud.lrs.common.repository.BatchRepository;
import gov.hud.lrs.common.repository.ReviewRepository;
import gov.hud.lrs.common.repository.ReviewTypeDefectRepository;
import gov.hud.lrs.common.repository.ReviewTypeRefRepository;
import gov.hud.lrs.common.repository.RvwLvlFindingRepository;
import gov.hud.lrs.common.security.SecurityService;
import gov.hud.lrs.common.repository.DocumentTypeRefRepository;
import gov.hud.lrs.common.repository.PersonnelRepository;
import gov.hud.lrs.common.service.DocumentTemplateService;
import gov.hud.lrs.services.bizservice.BatchService;
import gov.hud.lrs.services.bizservice.DictionaryService;
import gov.hud.lrs.services.bizservice.DocumentService;
import gov.hud.lrs.services.bizservice.FindingService;
import gov.hud.lrs.services.bizservice.QaService;
import gov.hud.lrs.services.bizservice.ReviewService;

@Controller
public class ReviewsControllerImpl {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired private BatchRepository batchRepository;
	@Autowired private ReviewRepository reviewRepository;
	@Autowired private ReviewTypeDefectRepository reviewTypeDefectRepository;
	@Autowired private ReviewTypeRefRepository reviewTypeRefRepository;
	@Autowired private RvwLvlFindingRepository rvwLvlFindingRepository;
	@Autowired private DocumentTypeRefRepository documentTypeRefRepository;
	@Autowired private PersonnelRepository personnelRepository;
	
	@Autowired private BatchService batchService;
	@Autowired private DictionaryService dictionaryService;
	@Autowired private DocumentService documentService;
	@Autowired private DocumentTemplateService documentTemplateService;
	@Autowired private FindingService findingService;
	@Autowired private QaService qaService;
	@Autowired private ReviewService reviewService;
	@Autowired private SecurityService securityService;

	public ResponseEntity<List<ReviewDTO>> getReviews(HttpServletRequest request, HttpServletResponse response
	) {
		return new ResponseEntity<List<ReviewDTO>>(reviewService.getActiveReviewDTOs(), HttpStatus.OK);
	}

	public ResponseEntity<List<ReviewDTO>> getReviewMy(HttpServletRequest request, HttpServletResponse response) {
		return new ResponseEntity<List<ReviewDTO>>(reviewService.getMyActiveReviewDTOs(), HttpStatus.OK);
	}

	public ResponseEntity<List<ReviewDTO>> getReviewTeam(HttpServletRequest request, HttpServletResponse response) {
		return new ResponseEntity<List<ReviewDTO>>(reviewService.getMyLocationActiveReviewDTOs(), HttpStatus.OK);
	}

	public ResponseEntity<ReviewDTO> getReviewByReviewId(String reviewId, HttpServletRequest request, HttpServletResponse response) {
		return new ResponseEntity<ReviewDTO>(reviewService.getReviewByReviewId(reviewId), HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	public ResponseEntity<?> postReviewLevelSubmit(
		String reviewId,
		String reviewLevelId,
		ReviewLevelInfoDTO reviewLevelInfoDTO,
		HttpServletRequest request,
		HttpServletResponse response
	) {
		reviewService.submitReviewLevel(reviewId, reviewLevelId, reviewLevelInfoDTO);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	public ResponseEntity<Void> putReviewLevelLenderSubmit(
		String reviewId,
		String reviewLevelId,
		HttpServletRequest request,
		HttpServletResponse response
	) {
		reviewService.submitLenderResponse(reviewId, reviewLevelId);

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	public ResponseEntity<String> postReviewManagementDecision(String reviewId, HttpServletRequest request, HttpServletResponse response) {
		reviewService.managementDecision(reviewId);

		return new ResponseEntity<String>(HttpStatus.OK);
	}

	public ResponseEntity<String> postReviewMrbReferral(String reviewId, HttpServletRequest request, HttpServletResponse response) {
		reviewService.mrbReferral(reviewId);

		return new ResponseEntity<String>(HttpStatus.OK);
	}

	public ResponseEntity<String> postReviewLevelIndemnificationReviewerForceSubmit(String reviewId, String reviewLevelId, ReviewLevelInfoDTO reviewLevelInfoDTO, HttpServletRequest request, HttpServletResponse response) {
		reviewService.forceIndemnification(reviewId, reviewLevelInfoDTO);
		return new ResponseEntity<String>(HttpStatus.OK);
	}


	public ResponseEntity<String> postReviewForceEscalation(String reviewId, HttpServletRequest request, HttpServletResponse response) {
		reviewService.forceEscalation(reviewId);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	public ResponseEntity<ReviewNoteDTO> postReviewNote(String reviewId, ReviewNotePostDTO noteDto, HttpServletRequest request, HttpServletResponse response) {
		ReviewNoteDTO note = reviewService.saveReviewNote(noteDto, reviewId);
		return new ResponseEntity<ReviewNoteDTO>(note, HttpStatus.CREATED);
	}

	public ResponseEntity<List<ReviewNoteDTO>> getReviewNotes(String reviewId, HttpServletRequest request, HttpServletResponse response) {
		List<ReviewNoteDTO> reviewNotes = reviewService.getReviewNotes(reviewId);
		return new ResponseEntity<List<ReviewNoteDTO>>(reviewNotes, HttpStatus.OK);
	}

	public ResponseEntity<List<ReviewFieldDTO>> getReviewLevelFields(String reviewId, String reviewLevelId, HttpServletRequest request, HttpServletResponse response) {
		List<ReviewFieldDTO> reviewLevelFields = reviewService.getReviewLevelFields(reviewId, reviewLevelId);
		return new ResponseEntity<List<ReviewFieldDTO>>(reviewLevelFields, HttpStatus.OK);
	}

	public ResponseEntity<ReviewFieldDTO> putReviewLevelFieldUpdateField(
		String reviewId,
		String reviewLevelId,
		ReviewFieldDTO reviewFieldDTO,
		HttpServletRequest request,
		HttpServletResponse response
	) {
		ReviewFieldDTO reviewLevelField = reviewService.updateReviewLevelField(reviewId, reviewLevelId, reviewFieldDTO);
		return new ResponseEntity<ReviewFieldDTO>(reviewLevelField, HttpStatus.OK);
	}

	public ResponseEntity<ReviewFindingDTO> postReviewLevelFinding(
		String reviewId,
		String reviewLevelId,
		ReviewFindingDTO reviewFindingDTO,
		HttpServletRequest request,
		HttpServletResponse response
	) {
		ReviewFindingDTO createdReviewFindingDTO = findingService.createRvwLvlFinding(reviewFindingDTO, reviewLevelId);
		return new ResponseEntity<ReviewFindingDTO>(createdReviewFindingDTO, HttpStatus.OK);
	}

	public ResponseEntity<List<ReviewFindingDTO>> getReviewLevelFindings(
		String reviewId,
		String reviewLevelId,
		HttpServletRequest request,
		HttpServletResponse response
	) {
		List<RvwLvlFinding> rvwLvlFindings = rvwLvlFindingRepository.findByReviewLevelReviewLevelId(reviewLevelId);

		List<ReviewFindingDTO> reviewFindingDTOs = new ArrayList<ReviewFindingDTO>();
		for (RvwLvlFinding rvwLvlFinding : rvwLvlFindings) {
			reviewFindingDTOs.add(findingService.convertRvwLvlFindingToReviewFindingDTO(rvwLvlFinding));
		}

		return new ResponseEntity<List<ReviewFindingDTO>>(reviewFindingDTOs, HttpStatus.OK);
	}

	public ResponseEntity<ReviewFindingDTO> putReviewLevelFinding(
		String reviewId,
		String reviewLevelId,
		String findingId,
		ReviewFindingDTO reviewFindingDTO,
		HttpServletRequest request,
		HttpServletResponse response
	) {
		ReviewFindingDTO updatedReviewFindingDTO = findingService.updateRvwLvlFinding(reviewFindingDTO, findingId);
		return new ResponseEntity<ReviewFindingDTO>(updatedReviewFindingDTO, HttpStatus.OK);
	}

	public ResponseEntity<String> deleteReviewLevelFinding(
		String reviewId,
		String reviewLevelId,
		String findingId,
		HttpServletRequest request,
		HttpServletResponse response
	) {
		findingService.deleteRvwLvlFinding(findingId);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	public ResponseEntity<ReviewFindingDTO> getReviewLevelFindingByFindingId(
		String reviewId,
		String reviewLevelId,
		String findingId,
		HttpServletRequest request,
		HttpServletResponse response
	) {
		RvwLvlFinding rvwLvlFinding = rvwLvlFindingRepository.findOne(findingId);
		if (rvwLvlFinding == null) {
			throw new NotFoundException("No RvwLvlFinding for findingId " + findingId);
		}

		ReviewFindingDTO reviewFindingDTO = findingService.convertRvwLvlFindingToReviewFindingDTO(rvwLvlFinding);

		return new ResponseEntity<ReviewFindingDTO>(reviewFindingDTO, HttpStatus.OK);
	}

	public ResponseEntity<byte[]> getReviewLevelFindingLenderResponseDocumentByDocumentId(
       String reviewId,
       String reviewLevelId,
       String findingId,
       String documentId,
       HttpServletRequest request,
       HttpServletResponse response
	) {
		Document document = documentService.getLenderResponseDocument(findingId, documentId);
		byte[] bytes = documentService.getDocumentFile(document);
		String fileName = null;

		try {
			fileName = URLEncoder.encode(document.getFileName(), "UTF-8");
		} catch(Exception e) {
			throw new RuntimeException("Unable to encode filename: " + document.getFileName());
		}

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("content-type", document.getMediaType());
		httpHeaders.add("content-length", String.valueOf(bytes.length));
		httpHeaders.add("content-disposition", "attachment; filename=" + fileName);
		httpHeaders.add("cache-control", "no-cache, no-store, must-revalidate");
		httpHeaders.add("pragma", "no-cache");
		httpHeaders.add("expires", "0");

		return new ResponseEntity<byte[]>(bytes, httpHeaders, HttpStatus.OK);
	}

	public ResponseEntity<DocumentDTO> postReviewLevelFindingLenderResponseDocument(
       String reviewId,
       String reviewLevelId,
       String findingId,
       HttpServletRequest request,
       HttpServletResponse response
	) {
		MultipartFile file = ((MultipartHttpServletRequest)request).getFile("file");

		byte[] bytes = null;
		try {
			bytes = file.getBytes();
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}

		Document document = documentService.createLenderResponseDocument(reviewId, findingId, file.getOriginalFilename(), bytes);

		DocumentDTO documentDTO = new DocumentDTO();
		documentDTO.setDocumentId(document.getDocumentId());
		documentDTO.setDocumentType(document.getDocumentTypeRef().getCode());
		documentDTO.setFileName(document.getFileName());

		return new ResponseEntity<DocumentDTO>(documentDTO, HttpStatus.OK);
	}

	public ResponseEntity<Void> deleteReviewLevelFindingLenderResponseDocument(
		String reviewId,
		String reviewLevelId,
		String findingId,
		String documentId,
		HttpServletRequest request,
		HttpServletResponse response
	) {
		documentService.deleteLenderResponseDocument(reviewId, findingId, documentId);

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	public ResponseEntity<byte[]> getReviewLevelIndemnificationPrePopulated(
		String reviewId,
		String reviewLevelId,
		HttpServletRequest request,
		HttpServletResponse response
	) {
		byte[] bytes = documentTemplateService.generatePrePopulatedIndemnificationPdf(reviewLevelId);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("content-type", MediaType.APPLICATION_PDF_VALUE);
		httpHeaders.add("content-length", String.valueOf(bytes.length));
		httpHeaders.add("content-disposition", "attachment; filename=Indem_Agreement_For_" + reviewLevelId + "_PrePopulated.pdf");
		httpHeaders.add("cache-control", "no-cache, no-store, must-revalidate");
		httpHeaders.add("pragma", "no-cache");
		httpHeaders.add("expires", "0");

		return new ResponseEntity<byte[]>(bytes, httpHeaders, HttpStatus.OK);
    }

	public ResponseEntity<Void> putReviewLevelIndemnificationLenderSubmit(
		String reviewId,
		String reviewLevelId,
		HttpServletRequest request,
		HttpServletResponse response
	) {
		reviewService.lenderSubmitIndemnification(reviewId, reviewLevelId);

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	public ResponseEntity<Void> postReviewBatchOperationalDocument(String batchId, HttpServletRequest request, HttpServletResponse response) {
		MultipartFile file = ((MultipartHttpServletRequest)request).getFile("file");

		byte[] bytes = null;
		try {
			bytes = file.getBytes();
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}

		documentService.createOperationalReviewDocument(batchId, file.getOriginalFilename(), bytes);

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	public ResponseEntity<Void> deleteReviewBatchOperationalDocument(
		String batchId,
		String documentId,
		HttpServletRequest request,
		HttpServletResponse response
	) {
		documentService.deleteOperationalReviewDocument(documentId);

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	public ResponseEntity<byte[]> getReviewBatchOperationalDocumentByDocumentId(
		String batchId,
		String documentId,
		HttpServletRequest request,
		HttpServletResponse response
	) {
		Document document = documentService.getOperationalReviewDocument(batchId, documentId);
		byte[] bytes = documentService.getDocumentFile(document);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("content-type", document.getMediaType());
		httpHeaders.add("content-length", String.valueOf(bytes.length));
		httpHeaders.add("content-disposition", document.getFileName());
		httpHeaders.add("cache-control", "no-cache, no-store, must-revalidate");
		httpHeaders.add("pragma", "no-cache");
		httpHeaders.add("expires", "0");

		return new ResponseEntity<byte[]>(bytes, httpHeaders, HttpStatus.OK);
	}

	public ResponseEntity<List<BatchDTO>> getReviewBatches(HttpServletRequest request, HttpServletResponse response) {
		List<Batch> batches = batchService.getReviewerBatches();
		List<BatchDTO> batchDTOs = batchService.convertBatchesToBatchDTOs(batches);
		return new ResponseEntity<List<BatchDTO>>(batchDTOs, HttpStatus.OK);
	}

	public ResponseEntity<List<BatchDTO>> getReviewMyCompletedBatches(HttpServletRequest request, HttpServletResponse response) {
		List<Batch> batches = batchService.getReviewerBatchesCompleted();
		List<BatchDTO> batchDTOs = batchService.convertBatchesToBatchDTOs(batches);
		return new ResponseEntity<List<BatchDTO>>(batchDTOs, HttpStatus.OK);
	}
	
	public ResponseEntity<List<BatchDTO>> getReviewBatchLender(HttpServletRequest request, HttpServletResponse response)  {
		List<Batch> batches = batchService.getLenderBatches();
		List<BatchDTO> batchDTOs = batchService.convertBatchesToBatchDTOs(batches);
		return new ResponseEntity<List<BatchDTO>>(batchDTOs, HttpStatus.OK);
	}

	public ResponseEntity<List<BatchDTO>> getReviewMyCompletedBatchLender(HttpServletRequest request, HttpServletResponse response)  {
		List<Batch> batches = batchService.getLenderBatchesCompleted();
		List<BatchDTO> batchDTOs = batchService.convertBatchesToBatchDTOs(batches);
		return new ResponseEntity<List<BatchDTO>>(batchDTOs, HttpStatus.OK);
	}
	
	public ResponseEntity<List<BatchDTO>> getReviewBatchLocationByLocationId(String locationId, HttpServletRequest request, HttpServletResponse response)  {
		String reviewLocationId = securityService.getPersonnel().getReviewLocation().getReviewLocationId();
		if (securityService.hasRole(Roles.ROLE_HQ_ADMIN) || (securityService.hasRole(Roles.ROLE_REVIEW_LOCATION_ADMIN) && reviewLocationId.equals(locationId))) {
			List<Batch> batches = batchService.getLocationBatches(locationId);
			List<BatchDTO> batchDTOs = batchService.convertBatchesToBatchDTOs(batches);
			return new ResponseEntity<List<BatchDTO>>(batchDTOs, HttpStatus.OK);
		} else {
			throw new RuntimeException("User is not authorized to view batches by location " + locationId);
		}
	}

	public ResponseEntity<List<BatchDTO>> getReviewMyCompletedBatchLocationByLocationId(String locationId, HttpServletRequest request, HttpServletResponse response)  {
		String reviewLocationId = securityService.getPersonnel().getReviewLocation().getReviewLocationId();
		if (securityService.hasRole(Roles.ROLE_HQ_ADMIN) || (securityService.hasRole(Roles.ROLE_REVIEW_LOCATION_ADMIN) && reviewLocationId.equals(locationId))) {
			List<Batch> batches = batchService.getLocationBatchesCompleted(locationId);
			List<BatchDTO> batchDTOs = batchService.convertBatchesToBatchDTOs(batches);
			return new ResponseEntity<List<BatchDTO>>(batchDTOs, HttpStatus.OK);
		} else {
			throw new RuntimeException("User is not authorized to view completed batches by location " + locationId);
		}
	}
	
	public ResponseEntity<BatchDTO> getReviewBatchByBatchId(String batchId, HttpServletRequest request, HttpServletResponse response) {
		Batch batch = batchRepository.findOne(batchId);
		if (batch == null) {
			throw new RuntimeException("No Batch for batchId " + batchId);
		}
		BatchDTO batchDTO = batchService.convertBatchToBatchDTO(batch);
		return new ResponseEntity<BatchDTO>(batchDTO, HttpStatus.OK);
	}

	public ResponseEntity<BatchDTO> getReviewBatchLenderByBatchId(String batchId, HttpServletRequest request, HttpServletResponse response) {
		Batch batch = batchService.getLenderBatchById(batchId);
		if (batch == null) {
			throw new RuntimeException("No Lender Batch for batchId " + batchId);
		}
		BatchDTO batchDTO = batchService.convertBatchToBatchDTO(batch);
		return new ResponseEntity<BatchDTO>(batchDTO, HttpStatus.OK);
	}

	public ResponseEntity<Void> postReviewBatchSubmit(String batchId, HttpServletRequest request, HttpServletResponse response) {
		batchService.submitBatch(batchId);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	public ResponseEntity<Void> putReviewBatchCancel(String batchId, HttpServletRequest request, HttpServletResponse response) {
		batchService.cancelBatch(batchId);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	public ResponseEntity<Void> postReviewBatchSubmitLender(String batchId, HttpServletRequest request, HttpServletResponse response) {
		batchService.submitLenderBatch(batchId);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	public ResponseEntity<Void> postReviewBatchSubmitOperationalDocument(String batchId, HttpServletRequest request, HttpServletResponse response) {
		batchService.submitOperationalReviewDocuments(batchId);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	public ResponseEntity<List<ReviewQuestionDTO>> getReviewQaTreeQuestions(String qatreeId, HttpServletRequest request, HttpServletResponse response) {
		List<ReviewQuestionDTO> qatreeQuestions = qaService.getQatreeQuestions(qatreeId);
		return new ResponseEntity<List<ReviewQuestionDTO>>(qatreeQuestions, HttpStatus.OK);
	}

	public ResponseEntity<ReviewAnswerDTO> postReviewLevelQaTreeAnswer(
		String reviewId,
		String reviewLevelId,
		String qaTreeId,
		ReviewAnswerDTO reviewAnswerDTO,
		HttpServletRequest request,
		HttpServletResponse response
	) {
		ReviewAnswerDTO qatreeAnswer = qaService.createQatreeAnswer(reviewAnswerDTO, reviewLevelId, qaTreeId);
		return new ResponseEntity<ReviewAnswerDTO>(qatreeAnswer, HttpStatus.OK);
	}

	public ResponseEntity<List<ReviewAnswerDTO>> getReviewLevelQaTreeAnswers(String reviewId, String reviewLevelId, String qaTreeId, HttpServletRequest request, HttpServletResponse response) {
		List<ReviewAnswerDTO> qatreeAnswers = qaService.getQatreeAnswers(reviewId, reviewLevelId, qaTreeId);
		return new ResponseEntity<List<ReviewAnswerDTO>>(qatreeAnswers, HttpStatus.OK);
	}

	public ResponseEntity<ReviewAnswerDTO> putReviewLevelQaTreeAnswer(
		String reviewId,
		String reviewLevelId,
		String qaTreeId,
		String answerId,
		ReviewAnswerDTO reviewAnswerDTO,
		HttpServletRequest request,
		HttpServletResponse response
	) {
		ReviewAnswerDTO qatreeAnswer = qaService.updateQatreeAnswer(reviewAnswerDTO, reviewLevelId, qaTreeId, answerId);
		return new ResponseEntity<ReviewAnswerDTO>(qatreeAnswer, HttpStatus.OK);
	}

	public ResponseEntity<String> deleteReviewLevelQaTreeAnswer(
		String reviewId,
		String reviewLevelId,
		String qaTreeId,
		String answerId,
		HttpServletRequest request,
		HttpServletResponse response
	) {
		qaService.deleteQatreeAnswer(answerId);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	public ResponseEntity<List<DefectAreaDictionaryDTO>> getReviewDictionaryQaModelActiveDefectAreas(HttpServletRequest request, HttpServletResponse response) {
		List<Defect> defectAreas = dictionaryService.getDefectAreasFromActiveQaModel();
		List<DefectAreaDictionaryDTO> dictDefectAreas = new ArrayList<DefectAreaDictionaryDTO>();
		for (Defect defect : defectAreas) {
			DefectAreaDictionaryDTO dto = new DefectAreaDictionaryDTO();
			dto.setDefectAreaId(defect.getDefectId());
			dto.setDefectAreaCode(defect.getDefectCd());
			dto.setTitle(defect.getDescription());
			dto.setOrder(defect.getRelativeOrder());
			List<String> reviewTypeCodes = new ArrayList<String>();
			for (ReviewTypeDefect reviewTypeDefect : reviewTypeDefectRepository.findByIdDefectId(defect.getDefectId())) {
				ReviewTypeRef reviewTypeRef = reviewTypeRefRepository.findOne(reviewTypeDefect.getId().getReviewTypeId());
				reviewTypeCodes.add(String.valueOf(reviewTypeRef.getReviewTypeCd()));
			}
			dto.setReviewTypeCodes(reviewTypeCodes);
			dictDefectAreas.add(dto);
		}
		return new ResponseEntity<List<DefectAreaDictionaryDTO>>(dictDefectAreas, HttpStatus.OK);
	}

	public ResponseEntity<List<DefectAreaDictionaryDTO>> getReviewDictionaryQaModelDefectAreas(String qaModelId, HttpServletRequest request, HttpServletResponse response) {
		List<Defect> defectAreas = dictionaryService.getDefectAreasByQaModel(qaModelId);
		List<DefectAreaDictionaryDTO> dictDefectAreas = new ArrayList<DefectAreaDictionaryDTO>();
		for (Defect item : defectAreas) {
			DefectAreaDictionaryDTO dto = new DefectAreaDictionaryDTO();
			dto.setDefectAreaId(item.getDefectId());
			dto.setDefectAreaCode(item.getDefectCd());
			dto.setTitle(item.getDescription());
			dto.setOrder(item.getRelativeOrder());
			dictDefectAreas.add(dto);
		}
		return new ResponseEntity<List<DefectAreaDictionaryDTO>>(dictDefectAreas, HttpStatus.OK);
	}

	public ResponseEntity<List<DefectAreaCauseDictionaryDTO>> getReviewDictionaryQaModelDefectAreaCauses(String qaModelId, HttpServletRequest request, HttpServletResponse response) {
		List<DefectCause> defectCauses = dictionaryService.getDefectCausesByQaModel(qaModelId);
		List<DefectAreaCauseDictionaryDTO> dictDefectAreaCauses = new ArrayList<DefectAreaCauseDictionaryDTO>();
		for (DefectCause defectCause : defectCauses) {
			DefectAreaCauseDictionaryDTO dto = new DefectAreaCauseDictionaryDTO();
			dto.setDefectAreaCode(defectCause.getDefect().getDefectCd());
			dto.setDefectCauseCode(defectCause.getDefectCauseCd());
			dto.setDescription(defectCause.getDescription());
			dictDefectAreaCauses.add(dto);
		}
		return new ResponseEntity<List<DefectAreaCauseDictionaryDTO>>(dictDefectAreaCauses, HttpStatus.OK);
	}

	public ResponseEntity<List<DefectAreaSourceDictionaryDTO>> getReviewDictionaryQaModelDefectAreaSources(
		String qaModelId,
		HttpServletRequest request,
		HttpServletResponse response
	) {
		List<DefectSource> defectSources = dictionaryService.getDefectSourcesByQaModel(qaModelId);
		List<DefectAreaSourceDictionaryDTO> dictDefectAreaSources = new ArrayList<DefectAreaSourceDictionaryDTO>();
		for (DefectSource defectSource : defectSources) {
			DefectAreaSourceDictionaryDTO dto = new DefectAreaSourceDictionaryDTO();
			dto.setDefectAreaCode(defectSource.getDefect().getDefectCd());
			dto.setDefectSourceCode(defectSource.getDefectSourceCd());
			dto.setDescription(defectSource.getDescription());
			dictDefectAreaSources.add(dto);
		}
		return new ResponseEntity<List<DefectAreaSourceDictionaryDTO>>(dictDefectAreaSources, HttpStatus.OK);
	}

	public ResponseEntity<List<DefectAreaSeverityDictionaryDTO>> getReviewDictionaryQaModelDefectAreaSeverities(
		String qaModelId,
		HttpServletRequest request,
		HttpServletResponse response
	) {
		List<DefectSeverity> defectSeverities = dictionaryService.getDefectSeveritiesByQaModel(qaModelId);
		List<DefectAreaSeverityDictionaryDTO> dictDefectAreaSeverities = new ArrayList<DefectAreaSeverityDictionaryDTO>();
		for (DefectSeverity defectSeverity : defectSeverities) {
			DefectAreaSeverityDictionaryDTO defectAreaSeverityDictionaryDTO = new DefectAreaSeverityDictionaryDTO();
			defectAreaSeverityDictionaryDTO.setDefectAreaCode(defectSeverity.getDefectCd());
			defectAreaSeverityDictionaryDTO.setDefectSeverityTierCode(String.valueOf(defectSeverity.getSeverityTierCd()));
			defectAreaSeverityDictionaryDTO.setDescription(defectSeverity.getDescription());
			dictDefectAreaSeverities.add(defectAreaSeverityDictionaryDTO);
		}
		return new ResponseEntity<List<DefectAreaSeverityDictionaryDTO>>(dictDefectAreaSeverities, HttpStatus.OK);
	}

	public ResponseEntity<List<CommonDetailDTO>> getReviewDictionaryQaModelDefectAreaRemediationTypes(
		String qaModelId,
		String qaTreeId,
		HttpServletRequest request,
		HttpServletResponse response
	) {
		List<DefectRemedyType> defectRemedyTypeRefs = dictionaryService.getDefectRemedyTypeByQaTreeId(qaTreeId);
		List<CommonDetailDTO> remediationTypes = new ArrayList<CommonDetailDTO>();
		for (DefectRemedyType defectRemedyType : defectRemedyTypeRefs) {
			CommonDetailDTO dto = new CommonDetailDTO();
			dto.setCode(defectRemedyType.getCode());
			dto.setDescription(defectRemedyType.getDescription());
			remediationTypes.add(dto);
		}
		return new ResponseEntity<List<CommonDetailDTO>>(remediationTypes, HttpStatus.OK);
	}

	public ResponseEntity<ReviewDTO> getReviewLender(String reviewId, HttpServletRequest request, HttpServletResponse response) {
		Review review = reviewRepository.findOne(reviewId);
		if (review == null) {
			throw new NotFoundException("No Review with reviewId " + reviewId + " for current lender");
		}
		ReviewDTO reviewDTO = reviewService.convertReviewToReviewDTORemoveNameAndLocation(review);
		return new ResponseEntity<ReviewDTO>(reviewDTO, HttpStatus.OK);
	}

	public ResponseEntity<List<ReviewDTO>> getReviewMyLender(HttpServletRequest request, HttpServletResponse response) {
		List<Review> reviews = reviewService.getMyLenderReviews();
		List<ReviewDTO> reviewDTOs = reviewService.convertReviewsToReviewDTOsRemoveNameAndLocation(reviews);
		return new ResponseEntity<List<ReviewDTO>>(reviewDTOs, HttpStatus.OK);
	}

	public ResponseEntity<List<ReviewDTO>> getReviewMyCompletedLender(
		String startDate,
		String endDate,
		HttpServletRequest request,
		HttpServletResponse response
	) {
		logger.debug("startDate: " + startDate + ", endDate: " + endDate);
		List<Review> reviews = reviewService.getMyCompletedLenderReviews();
		List<ReviewDTO> reviewDTOs = reviewService.convertReviewsToReviewDTOsRemoveNameAndLocation(reviews);
		return new ResponseEntity<List<ReviewDTO>>(reviewDTOs, HttpStatus.OK);
	}

	public ResponseEntity<List<ReviewFindingDTO>> getReviewLevelFindingLender(
		String reviewId,
		String reviewLevelId,
		HttpServletRequest request,
		HttpServletResponse response
	) {
		List<ReviewFindingDTO> reviewFindingDTOs = findingService.convertRvwLvlFindingsToReviewFindingDTOs(
			rvwLvlFindingRepository.findByReviewLevelReviewLevelId(reviewLevelId)
		);
		return new ResponseEntity<List<ReviewFindingDTO>>(reviewFindingDTOs, HttpStatus.OK);
	}

	public ResponseEntity<ReviewFindingDTO> getReviewLevelFindingLender(
		String reviewId,
		String reviewLevelId,
		String findingId,
		HttpServletRequest request,
		HttpServletResponse response
	) {
		RvwLvlFinding rvwLvlFinding = rvwLvlFindingRepository.findOne(findingId);
		if (rvwLvlFinding == null) {
			throw new NotFoundException("No RvwLvlFinding for findingId " + findingId);
		}

		ReviewFindingDTO reviewFindingDTO = findingService.convertRvwLvlFindingToReviewFindingDTO(rvwLvlFinding);

		return new ResponseEntity<ReviewFindingDTO>(reviewFindingDTO, HttpStatus.OK);
	}

	public ResponseEntity<Void> putReviewLevelFindingLender(
		String reviewId,
		String reviewLevelId,
		String findingId,
		ReviewFindingDTO reviewFindingDTO,
		HttpServletRequest request,
		HttpServletResponse response
	) {
		findingService.updateReviewLevelFindingLender(reviewId, findingId, reviewFindingDTO);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	public ResponseEntity<String> postReviewLevelConfirmVetting(
		String reviewId,
		String reviewLevelId,
		HttpServletRequest request,
		HttpServletResponse response
	) {
		reviewService.updateConfirmVetting(reviewId, reviewLevelId);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	public ResponseEntity<Void> putReviewLevelReassign(
		String reviewId,
		String reviewLevelId,
		SimpleSelectionDTO simpleSelectionDTO,
		HttpServletRequest request,
		HttpServletResponse response
	) {
		reviewService.reassignReviewLevel(reviewId, reviewLevelId, simpleSelectionDTO);

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	public ResponseEntity<ReviewReferralDTO> putReviewReferral(String reviewId, ReviewReferralDTO reviewReferralDTO, HttpServletRequest request, HttpServletResponse response) {
		reviewReferralDTO = reviewService.updateReferral(reviewId, reviewReferralDTO);
		return new ResponseEntity<ReviewReferralDTO>(reviewReferralDTO, HttpStatus.OK);
	}

	public ResponseEntity<ReviewReferralDTO> getReviewReferral(String reviewId, HttpServletRequest request, HttpServletResponse response) {
		ReviewReferralDTO reviewReferralDTO = new ReviewReferralDTO();
		if (reviewService.getReviewReferral(reviewId) != null) {
			reviewReferralDTO = reviewService.convertReviewReferralsToReferralDTO(reviewService.getReviewReferral(reviewId));
		}
		return new ResponseEntity<ReviewReferralDTO>(reviewReferralDTO, HttpStatus.OK);
	}

	public ResponseEntity<Void> deleteReviewReferral(String reviewId, HttpServletRequest request, HttpServletResponse response) {
		reviewService.deleteReviewReferral(reviewId);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	public ResponseEntity<?> putReviewLevelIndemnificationReviewerAccept(
		String reviewId,
		String reviewLevelId,
		IndemAcceptDTO indemAcceptDTO,
		HttpServletRequest request,
		HttpServletResponse response
	) {
		logger.debug("calling ReviewsController.putReviewIndemnificationLetterReviewerAccept");
		reviewService.indemReviewerSubmit(reviewLevelId, indemAcceptDTO);

		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}


	@SuppressWarnings("rawtypes")
	public ResponseEntity<?> putReviewLevelIndemnificationReviewerReject(
		String reviewId,
		String reviewLevelId,
		HttpServletRequest request,
		HttpServletResponse response
	) {
		logger.debug("calling ReviewsController.putReviewIndemnificationLetterReviewerReject");
		reviewService.indemReviewerReject(reviewId, reviewLevelId);

		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	private HttpHeaders getHttpHeaders(String reviewLevelId) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("content-type", MediaType.APPLICATION_PDF_VALUE);
		httpHeaders.add("content-disposition", "attachment; filename=Indem_Agreement_For_" + reviewLevelId + "_PrePopulated.pdf");
		httpHeaders.add("cache-control", "no-cache, no-store, must-revalidate");
		httpHeaders.add("pragma", "no-cache");
		httpHeaders.add("expires", "0");
		return httpHeaders;
	}
	public ResponseEntity<byte[]> getReviewLevelIndemnificationPreSignedLender(
	    	String reviewId,
	    	String reviewLevelId,
	    	HttpServletRequest request,
	    	HttpServletResponse response
	) {

		byte[] bytes = documentTemplateService.generatePreSignedIndemnificationPdf(reviewLevelId, true, false);

		HttpHeaders httpHeaders = getHttpHeaders(reviewLevelId);
		httpHeaders.add("content-length", String.valueOf(bytes.length));

		return new ResponseEntity<byte[]>(bytes, httpHeaders, HttpStatus.OK);

	}

	public ResponseEntity<byte[]> getReviewLevelIndemnificationPreSignedReviewer(
	    	String reviewId,
	    	String reviewLevelId,
	    	HttpServletRequest request,
	    	HttpServletResponse response
	) {

		byte[] bytes = documentTemplateService.generatePreSignedIndemnificationPdf(reviewLevelId, false, true);

		HttpHeaders httpHeaders = getHttpHeaders(reviewLevelId);
		httpHeaders.add("content-length", String.valueOf(bytes.length));

		return new ResponseEntity<byte[]>(bytes, httpHeaders, HttpStatus.OK);

	}

	public ResponseEntity<byte[]> getReviewLevelIndemnificationPreSignedBoth(
	    	String reviewId,
	    	String reviewLevelId,
	    	HttpServletRequest request,
	    	HttpServletResponse response
	) {

		byte[] bytes = documentTemplateService.generatePreSignedIndemnificationPdf(reviewLevelId, true, true);

		HttpHeaders httpHeaders = getHttpHeaders(reviewLevelId);;
		httpHeaders.add("content-length", String.valueOf(bytes.length));

		return new ResponseEntity<byte[]>(bytes, httpHeaders, HttpStatus.OK);

	}

	public ResponseEntity<byte[]> getReviewLevelIndemnificationFinal(
			String reviewId,
			String reviewLevelId,
			HttpServletRequest request,
			HttpServletResponse response
		) {
			Document document = new Document();
			Review review = reviewRepository.findOne(reviewId);
			String fileName = review.getCaseNumber() + "_" + reviewLevelId + "_indemnification.pdf";
			document.setFileName(fileName);
			document.setDocumentTypeRef(documentTypeRefRepository.findByCode(DocumentTypeCodes.INDEMNIFICATION));
			document.setReview(review);

			byte[] bytes = documentService.getDocumentFile(document);

			HttpHeaders httpHeaders = new HttpHeaders();

			httpHeaders.add("content-type", MediaType.APPLICATION_PDF_VALUE);
			httpHeaders.add("content-length", String.valueOf(bytes.length));
			httpHeaders.add("content-disposition", fileName);
			httpHeaders.add("cache-control", "no-cache, no-store, must-revalidate");
			httpHeaders.add("pragma", "no-cache");
			httpHeaders.add("expires", "0");

			return new ResponseEntity<byte[]>(bytes, httpHeaders, HttpStatus.OK);
	}


	public ResponseEntity<Void> putReviewLevelCancel(
		String reviewId,
		String reviewLevelId,
		SimpleSelectionDTO simpleSelectionDTO,
		HttpServletRequest request,
		HttpServletResponse response
	) {
		reviewService.cancelReview(reviewId, simpleSelectionDTO.getReasonCode());
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	public ResponseEntity<List<ReviewDTO>> getReviewMyCompleted(
		String startDate,
		String endDate,
		HttpServletRequest request,
		HttpServletResponse response
	) {
		logger.debug("startDate: " + startDate + ", endDate: " + endDate);

		List<ReviewDTO> reviewDTOs = new ArrayList<ReviewDTO>();
		for(Review review: reviewService.getReviewerCompletedReviews(startDate, endDate)) {
			reviewDTOs.add(reviewService.convertReviewToReviewDTO(review));
		}

		return new ResponseEntity<List<ReviewDTO>>(reviewDTOs, HttpStatus.OK);
	}

	public ResponseEntity<List<ReviewDTO>> getReviewMyCompletedLocationByLocationId(
		String locationId,
		String startDate,
		String endDate,
		HttpServletRequest request,
		HttpServletResponse response
	) {
		logger.debug("locationId: " + locationId + ", startDate: " + startDate + ", endDate: " + endDate);
		
		List<ReviewDTO> reviewDTOs = new ArrayList<ReviewDTO>();
		if (securityService.hasRole(Roles.ROLE_HQ_ADMIN) || personnelRepository.findByReportsToPersonnel(securityService.getPersonnel()).size() > 0 ) {
			for(Review review: reviewService.getReviewMyCompletedLocationByLocationId(locationId, startDate, endDate)) {
				reviewDTOs.add(reviewService.convertReviewToReviewDTO(review));
			}
		}

		return new ResponseEntity<List<ReviewDTO>>(reviewDTOs, HttpStatus.OK);
	}
	
	public ResponseEntity<String> putReviewLevelIndemnificationType(
		String reviewId, 
		String reviewLevelId, 
		ReviewLevelInfoDTO data, 
		HttpServletRequest request,
		HttpServletResponse response
	) {
		reviewService.updateReviewLevelIndemnificationType(reviewLevelId, data.getIndemnificationTypeCode());
		return new ResponseEntity<String>(HttpStatus.OK);
	}

}
