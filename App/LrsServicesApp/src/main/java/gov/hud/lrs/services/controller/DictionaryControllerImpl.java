package gov.hud.lrs.services.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.google.common.collect.ImmutableList;

import gov.hud.lrs.common.dto.LrsApiV1DTOs.CommonDetailDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.ConsolidatedSelectionReasonDictionaryDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.RatingCodeDictionaryDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.SelectionReasonDictionaryDTO;
import gov.hud.lrs.common.entity.CancellationReasonRef;
import gov.hud.lrs.common.entity.ConsolidatedSelectionReason;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.FactorDTO;
import gov.hud.lrs.common.entity.FileDeliveryLocationRef;
import gov.hud.lrs.common.entity.FraudParticipantRef;
import gov.hud.lrs.common.entity.FraudTypeRef;
import gov.hud.lrs.common.entity.ProductTypeRef;
import gov.hud.lrs.common.entity.RatingRef;
import gov.hud.lrs.common.entity.ReviewLevelReassignmentReasonRef;
import gov.hud.lrs.common.entity.ReviewLevelTypeRef;
import gov.hud.lrs.common.entity.ReviewProcessExceptionTypeRef;
import gov.hud.lrs.common.entity.ReviewTypeRef;
import gov.hud.lrs.common.entity.ScoringFactor;
import gov.hud.lrs.common.entity.ScoringModel;
import gov.hud.lrs.common.entity.SelectionReason;
import gov.hud.lrs.common.entity.SelectionSubReasonRef;
import gov.hud.lrs.common.entity.LoanTypeRef;
import gov.hud.lrs.common.enumeration.ScoringModelTypeCodes;
import gov.hud.lrs.common.repository.CancellationReasonRefRepository;
import gov.hud.lrs.common.repository.ReviewLevelReassignmentReasonRefRepository;
import gov.hud.lrs.common.repository.ScoringModelRepository;
import gov.hud.lrs.services.bizservice.DictionaryService;

@Controller
public class DictionaryControllerImpl {

	@Autowired private DictionaryService dictionaryService;
	@Autowired private ScoringModelRepository scoringModelRepository;
	@Autowired private ReviewLevelReassignmentReasonRefRepository reviewLevelReassignmentReasonRefRepository;
	@Autowired private CancellationReasonRefRepository cancellationReasonRefRepository;

	public ResponseEntity<List<CommonDetailDTO>> getDictionaryLoanTypes(HttpServletRequest request, HttpServletResponse response) {
		List<LoanTypeRef> loanTypes = dictionaryService.getAllLoanTypes();
		List<CommonDetailDTO> dictLoanTypes = new ArrayList<CommonDetailDTO>();
		for (LoanTypeRef item : loanTypes) {
			CommonDetailDTO dto = new CommonDetailDTO();
			dto.setCode(item.getCode());
			dto.setDescription(item.getDescription());
			dictLoanTypes.add(dto);
		}
		return new ResponseEntity<List<CommonDetailDTO>>(dictLoanTypes, HttpStatus.OK);
	}

	public ResponseEntity<List<CommonDetailDTO>> getDictionaryProductTypes(HttpServletRequest request, HttpServletResponse response) {
		List<ProductTypeRef> productTypes = dictionaryService.getAllProductTypes();
		List<CommonDetailDTO> dictProductTypes = new ArrayList<CommonDetailDTO>();
		for (ProductTypeRef item : productTypes) {
			CommonDetailDTO dto = new CommonDetailDTO();
			dto.setCode(item.getProductTypeCd());
			dto.setDescription(item.getDescription());
			dictProductTypes.add(dto);
		}
		return new ResponseEntity<List<CommonDetailDTO>>(dictProductTypes, HttpStatus.OK);
	}

	public ResponseEntity<List<SelectionReasonDictionaryDTO>> getDictionarySelectionReasons(HttpServletRequest request, HttpServletResponse response) {
		List<SelectionReason> selectionReasons = dictionaryService.getAllSelectionReasons();
		List<SelectionReasonDictionaryDTO> dictSelectionReasons = new ArrayList<SelectionReasonDictionaryDTO>();
		for (SelectionReason item : selectionReasons) {
			SelectionReasonDictionaryDTO dto = new SelectionReasonDictionaryDTO();
			dto.setCode(item.getCode());
			dto.setDescription(item.getDescription());
			dto.setProcessingCycle(item.getProcessingCycle());
			dictSelectionReasons.add(dto);
		}
		return new ResponseEntity<List<SelectionReasonDictionaryDTO>>(dictSelectionReasons, HttpStatus.OK);
	}

	public ResponseEntity<List<CommonDetailDTO>> getDictionarySelectionSubreasons(HttpServletRequest request, HttpServletResponse response) {
		List<SelectionSubReasonRef> selectionSubReasons = dictionaryService.getAllSelectionSubReasons();
		List<CommonDetailDTO> dictSelectionSubReasons = new ArrayList<CommonDetailDTO>();
		for (SelectionSubReasonRef item : selectionSubReasons) {
			CommonDetailDTO dto = new CommonDetailDTO();
			dto.setCode(item.getCode());
			dto.setDescription(item.getDescription());
			dictSelectionSubReasons.add(dto);
		}
		return new ResponseEntity<List<CommonDetailDTO>>(dictSelectionSubReasons, HttpStatus.OK);
	}

	public ResponseEntity<List<CommonDetailDTO>> getDictionaryReviewTypes(HttpServletRequest request, HttpServletResponse response) {
		List<ReviewTypeRef> reviewTypes = dictionaryService.getAllReviewTypes();
		List<CommonDetailDTO> dictReviewTypes = new ArrayList<CommonDetailDTO>();
		for (ReviewTypeRef item : reviewTypes) {
			CommonDetailDTO dto = new CommonDetailDTO();
			dto.setCode(item.getReviewTypeCd());
			dto.setDescription(item.getDescription());
			dictReviewTypes.add(dto);
		}
		return new ResponseEntity<List<CommonDetailDTO>>(dictReviewTypes, HttpStatus.OK);
	}

	public ResponseEntity<List<CommonDetailDTO>> getDictionaryReviewLevels(HttpServletRequest request, HttpServletResponse response) {
		List<ReviewLevelTypeRef> reviewLevelTypes = dictionaryService.getStaffManagementReviewLevels();
		List<CommonDetailDTO> dictReviewLevels = new ArrayList<CommonDetailDTO>();
		for (ReviewLevelTypeRef item : reviewLevelTypes) {
			CommonDetailDTO dto = new CommonDetailDTO();
			dto.setCode(item.getReviewLevelCd());
			dto.setDescription(item.getDescription());
			dictReviewLevels.add(dto);
		}
		return new ResponseEntity<List<CommonDetailDTO>>(dictReviewLevels, HttpStatus.OK);
	}

	public ResponseEntity<List<CommonDetailDTO>> getDictionaryDocumentLocations(HttpServletRequest request, HttpServletResponse response) {
		List<CommonDetailDTO> dictDocumentLocations = new ArrayList<CommonDetailDTO>();
		for (FileDeliveryLocationRef item : dictionaryService.getAllFileDeliveryLocations()) {
			CommonDetailDTO dto = new CommonDetailDTO();
			dto.setCode(item.getCode());
			dto.setDescription(item.getDescription());
			dictDocumentLocations.add(dto);
		}
		return new ResponseEntity<List<CommonDetailDTO>>(dictDocumentLocations, HttpStatus.OK);
	}

	public ResponseEntity<List<CommonDetailDTO>> getDictionaryExceptionTypes(HttpServletRequest request, HttpServletResponse response) {
		List<CommonDetailDTO> exceptionTypes = new ArrayList<CommonDetailDTO>();
		for (ReviewProcessExceptionTypeRef item : dictionaryService.getAllExceptionTypes()) {
			CommonDetailDTO dto = new CommonDetailDTO();
			dto.setCode(item.getCode());
			dto.setDescription(item.getDescription());
			exceptionTypes.add(dto);
		}
		return new ResponseEntity<List<CommonDetailDTO>>(exceptionTypes, HttpStatus.OK);
	}


	public ResponseEntity<List<RatingCodeDictionaryDTO>> getDictionaryRatingCodes(HttpServletRequest request, HttpServletResponse response) {
		List<RatingRef> ratings = dictionaryService.getAllRatings();
		List<RatingCodeDictionaryDTO> dictRatings = new ArrayList<RatingCodeDictionaryDTO>();
		for (RatingRef item : ratings) {
			RatingCodeDictionaryDTO dto = new RatingCodeDictionaryDTO();
			dto.setRatingCode(item.getCode());
			dto.setDescription(item.getDescription());
			dto.setRankOrder(new BigDecimal(item.getRankOrder()));
			dictRatings.add(dto);
		}
		return new ResponseEntity<List<RatingCodeDictionaryDTO>>(dictRatings, HttpStatus.OK);
	}

	public ResponseEntity<List<CommonDetailDTO>> getDictionaryFraudTypes(HttpServletRequest request, HttpServletResponse response) {
		List<FraudTypeRef> fraudTypes = dictionaryService.getAllFraudTypes();
		List<CommonDetailDTO> dictFraudTypes = new ArrayList<CommonDetailDTO>();
		for (FraudTypeRef item : fraudTypes) {
			CommonDetailDTO dto = new CommonDetailDTO();
			dto.setCode(item.getFraudTypeId());
			dto.setDescription(item.getFraudTypeDescription());
			dictFraudTypes.add(dto);
		}
		return new ResponseEntity<List<CommonDetailDTO>>(dictFraudTypes, HttpStatus.OK);
	}

	public ResponseEntity<List<CommonDetailDTO>> getDictionaryFraudParticipants(HttpServletRequest request, HttpServletResponse response) {
		List<FraudParticipantRef> fraudParticipants = dictionaryService.getAllFraudParticipants();
		List<CommonDetailDTO> dictFraudParticipants = new ArrayList<CommonDetailDTO>();
		for (FraudParticipantRef item : fraudParticipants) {
			CommonDetailDTO dto = new CommonDetailDTO();
			dto.setCode(item.getFraudParticipantId());
			dto.setDescription(item.getFraudParticipantDescription());
			dictFraudParticipants.add(dto);
		}
		return new ResponseEntity<List<CommonDetailDTO>>(dictFraudParticipants, HttpStatus.OK);
	}

	public ResponseEntity<List<CommonDetailDTO>> getDictionaryAssignmentModelCategories(HttpServletRequest request, HttpServletResponse response) {
		List<CommonDetailDTO> dictionaryAssignmentScoringModelCategories = getScoringModelDictionary(ScoringModelTypeCodes.ASSIGNMENT);
		return new ResponseEntity<List<CommonDetailDTO>>(dictionaryAssignmentScoringModelCategories, HttpStatus.OK);
	}

	public ResponseEntity<List<CommonDetailDTO>> getDictionarySelectionModelCategories(HttpServletRequest request, HttpServletResponse response) {
		List<CommonDetailDTO> dictionaryAssignmentScoringModelCategories = getScoringModelDictionary(ScoringModelTypeCodes.SELECTION_CODES);
		return new ResponseEntity<List<CommonDetailDTO>>(dictionaryAssignmentScoringModelCategories, HttpStatus.OK);
	}

	public ResponseEntity<List<CommonDetailDTO>> getDictionaryDistributionModelCategories(HttpServletRequest request, HttpServletResponse response) {
		List<CommonDetailDTO> dictionaryDistributionScoringModelCategories = getScoringModelDictionary(ScoringModelTypeCodes.DISTRIBUTION);
		return new ResponseEntity<List<CommonDetailDTO>>(dictionaryDistributionScoringModelCategories, HttpStatus.OK);
	}

	private List<CommonDetailDTO> getScoringModelDictionary(String scoringModelTypeCode) {
		return getScoringModelDictionary(ImmutableList.of(scoringModelTypeCode));
	}
	private List<CommonDetailDTO> getScoringModelDictionary(List<String> scoringModelTypeCodes) {
		List<CommonDetailDTO> dictionaryScoringModels = new ArrayList<CommonDetailDTO>();
		for (ScoringModel scoringModel : scoringModelRepository.findByScoringModelTypeRefCodeIn(scoringModelTypeCodes)) {
			CommonDetailDTO dto = new CommonDetailDTO();
			dto.setDescription(scoringModel.getDescription());
			dto.setCode(scoringModel.getScoringModelId());
			dictionaryScoringModels.add(dto);
		}
		return dictionaryScoringModels;
	}

	public ResponseEntity<List<CommonDetailDTO>> getDictionaryReassignmentReasons(HttpServletRequest request, HttpServletResponse response) {
		List<CommonDetailDTO> dictionaryReassignmentReasons = new ArrayList<CommonDetailDTO>();
		for (ReviewLevelReassignmentReasonRef reviewLevelReassignmentReason : reviewLevelReassignmentReasonRefRepository.findAll()) {
			CommonDetailDTO commonDetailDTO = new CommonDetailDTO();
			commonDetailDTO.setCode(reviewLevelReassignmentReason.getCode());
			commonDetailDTO.setDescription(reviewLevelReassignmentReason.getDescription());
			dictionaryReassignmentReasons.add(commonDetailDTO);
		}
		return new ResponseEntity<List<CommonDetailDTO>>(dictionaryReassignmentReasons, HttpStatus.OK);
	}

	public ResponseEntity<?> getDictionaryReviewCancelReasons(HttpServletRequest request, HttpServletResponse response) {
		List<CommonDetailDTO> dictionaryReviewCancelReasons = new ArrayList<CommonDetailDTO>();
		for (CancellationReasonRef cancellationReason : cancellationReasonRefRepository.findAll()) {
			CommonDetailDTO commonDetailDTO = new CommonDetailDTO();
			commonDetailDTO.setCode(cancellationReason.getCode());
			commonDetailDTO.setDescription(cancellationReason.getDescription());
			dictionaryReviewCancelReasons.add(commonDetailDTO);
		}
		return new ResponseEntity<List<CommonDetailDTO>>(dictionaryReviewCancelReasons, HttpStatus.OK);
	}

	public ResponseEntity<List<CommonDetailDTO>> getDictionaryLenderLoanTypes(HttpServletRequest request, HttpServletResponse response) {
		List<LoanTypeRef> loanTypes = dictionaryService.getAllLenderLoanTypes();
		List<CommonDetailDTO> dictLoanTypes = new ArrayList<CommonDetailDTO>();
		for (LoanTypeRef item : loanTypes) {
			CommonDetailDTO dto = new CommonDetailDTO();
			dto.setCode(item.getCode());
			dto.setDescription(item.getDescription());
			dictLoanTypes.add(dto);
		}
		return new ResponseEntity<List<CommonDetailDTO>>(dictLoanTypes, HttpStatus.OK);
	}

	public ResponseEntity<List<ConsolidatedSelectionReasonDictionaryDTO>> getDictionaryConsolidatedSelectionReasons(HttpServletRequest request, HttpServletResponse response) {
		List<ConsolidatedSelectionReason> consolidatedSelectionReasons = dictionaryService.getAllConsolidatedSelectionReasons();
		List<ConsolidatedSelectionReasonDictionaryDTO> dictConolidatedSelectionReasons = new ArrayList<ConsolidatedSelectionReasonDictionaryDTO>();
		for (ConsolidatedSelectionReason item : consolidatedSelectionReasons) {
			ConsolidatedSelectionReasonDictionaryDTO dto = new ConsolidatedSelectionReasonDictionaryDTO();
			dto.setCode(item.getCode());
			dto.setDescription(item.getDescription());
			dictConolidatedSelectionReasons.add(dto);
		}
		return new ResponseEntity<List<ConsolidatedSelectionReasonDictionaryDTO>>(dictConolidatedSelectionReasons, HttpStatus.OK);
	}

	public ResponseEntity<List<FactorDTO>> getDictionaryFactors(HttpServletRequest request, HttpServletResponse response) {
		List<ScoringFactor> scoringFactors = dictionaryService.getAllScoringFactors();
		List<FactorDTO> dictScoringFactors = new ArrayList<FactorDTO>();
		for (ScoringFactor factor : scoringFactors) {
			FactorDTO factorDto = new FactorDTO();
			factorDto.setId(factor.getScoringFactorId());
			factorDto.setName(factor.getFactorAttributeName());
			factorDto.setDescription(factor.getDescription());
			dictScoringFactors.add(factorDto);
		}
		return new ResponseEntity<List<FactorDTO>>(dictScoringFactors, HttpStatus.OK);
	}

}
