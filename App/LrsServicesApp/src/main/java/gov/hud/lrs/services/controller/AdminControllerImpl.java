package gov.hud.lrs.services.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import gov.hud.lrs.common.dto.LrsApiV1DTOs.AssignmentModelDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.CommonDetailDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.DistributionModelDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.EmailTemplateDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.EmailTemplateVersionDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.EmailTemplateVersionLiteDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.FactorDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.FieldDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.LenderSelectionAdjustmentDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.ModelDuplicatorDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.QaModelDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.QaModelDefectAreaDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.QaModelDefectAreaDetailDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.QaModelDetailDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.ReviewLevelTimeLimitsDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.SelectionModelDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.TimeLimitDTO;
import gov.hud.lrs.common.entity.Defect;
import gov.hud.lrs.common.entity.DictMetadataField;
import gov.hud.lrs.common.entity.EmailTemplate;
import gov.hud.lrs.common.entity.EmailTemplateVersion;
import gov.hud.lrs.common.entity.QaModel;
import gov.hud.lrs.common.entity.ReviewLevelTypeRef;
import gov.hud.lrs.common.entity.ScoringFactor;
import gov.hud.lrs.common.entity.ScoringModelVersion;
import gov.hud.lrs.common.entity.ScoringModelVersionFactor;
import gov.hud.lrs.common.entity.ScoringModelVersionFactorId;
import gov.hud.lrs.common.enumeration.ScoringModelTypeCodes;
import gov.hud.lrs.common.enumeration.ScoringModelVersionStatusCodes;
import gov.hud.lrs.common.exception.NotFoundException;
import gov.hud.lrs.common.repository.DefectRepository;
import gov.hud.lrs.common.repository.EmailTemplateRepository;
import gov.hud.lrs.common.repository.QaModelRepository;
import gov.hud.lrs.common.repository.ReviewScopeRefRepository;
import gov.hud.lrs.common.repository.ReviewTypeRefRepository;
import gov.hud.lrs.common.repository.ScoringModelVersionFactorRepository;
import gov.hud.lrs.common.repository.ScoringModelVersionRepository;
import gov.hud.lrs.common.repository.ScoringModelVersionStatusRefRepository;
import gov.hud.lrs.common.security.SecurityService;
import gov.hud.lrs.services.bizservice.EmailTemplateService;
import gov.hud.lrs.services.bizservice.IncreasedSelectionService;
import gov.hud.lrs.services.bizservice.LenderSuppressionService;
import gov.hud.lrs.services.bizservice.QaService;
import gov.hud.lrs.services.bizservice.ScoringModelService;
import gov.hud.lrs.services.bizservice.TimeframeService;
import gov.hud.lrs.services.bizservice.DictionaryService;

@Controller
public class AdminControllerImpl {

	@Autowired private ScoringModelVersionRepository scoringModelVersionRepository;
	@Autowired private ScoringModelVersionFactorRepository scoringModelVersionFactorRepository;
	@Autowired private ReviewScopeRefRepository reviewScopeRefRepository;
	@Autowired private ReviewTypeRefRepository reviewTypeRefRepository;
	@Autowired private ScoringModelVersionStatusRefRepository scoringModelVersionStatusRefRepository;
	@Autowired private QaModelRepository qaModelRepository;
	@Autowired private DefectRepository defectRepository;
	@Autowired private EmailTemplateRepository emailTemplateRepository;

	@Autowired private LenderSuppressionService lenderSuppressionService;
	@Autowired private TimeframeService timeframeService;
	@Autowired private IncreasedSelectionService increasedSelectionService;
	@Autowired private QaService qaService;
	@Autowired private ScoringModelService scoringModelService;
	@Autowired private SecurityService securityService;
	@Autowired private EmailTemplateService emailTemplateService;
	@Autowired private DictionaryService dictionaryService;

	public ResponseEntity<List<LenderSelectionAdjustmentDTO>> getAdminLenderSuppressions(HttpServletRequest request, HttpServletResponse response) {
		List<LenderSelectionAdjustmentDTO> lenderSuppressions = lenderSuppressionService.getLenderSuppressions();
		return new ResponseEntity<List<LenderSelectionAdjustmentDTO>>(lenderSuppressions, HttpStatus.OK);
	}

	public ResponseEntity<Void> postAdminLenderSuppression(String lenderId, HttpServletRequest request, HttpServletResponse response) {
		lenderSuppressionService.createLenderSuppression(lenderId);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	public ResponseEntity<Void> deleteAdminLenderSuppression(String lenderId, HttpServletRequest request, HttpServletResponse response) {
		lenderSuppressionService.deleteLenderSuppression(lenderId);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	public ResponseEntity<List<ReviewLevelTimeLimitsDTO>> getAdminReviewLevelReviewerTimeLimits(HttpServletRequest request, HttpServletResponse response) {
		List<ReviewLevelTimeLimitsDTO> reviewerTimeLimitDTOs = timeframeService.getReviewTimeframe();
		return new ResponseEntity<List<ReviewLevelTimeLimitsDTO>>(reviewerTimeLimitDTOs, HttpStatus.OK);
	}

	public ResponseEntity<List<ReviewLevelTimeLimitsDTO>> getAdminReviewLevelLenderResponseTimeLimits(HttpServletRequest request, HttpServletResponse response) {
		List<ReviewLevelTimeLimitsDTO> responseTimeLimitDTOs = timeframeService.getResponseTimeframe();
		return new ResponseEntity<List<ReviewLevelTimeLimitsDTO>>(responseTimeLimitDTOs, HttpStatus.OK);
	}

	public ResponseEntity<Void> putAdminReviewLevelReviewerTimeLimit(
		String reviewLevelCode,
		TimeLimitDTO timeLimitDTO,
		HttpServletRequest request,
		HttpServletResponse response
	) {
		timeframeService.updateReviewerTimeframe(reviewLevelCode, timeLimitDTO);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	public ResponseEntity<Void> putAdminReviewLevelLenderResponseTimeLimit(
		String reviewLevelCode,
		TimeLimitDTO timeLimitDTO,
		HttpServletRequest request,
		HttpServletResponse response
	) {
		timeframeService.updateResponseTimeframe(reviewLevelCode, timeLimitDTO);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	public  ResponseEntity<List<LenderSelectionAdjustmentDTO>> getAdminLenderSelectionIncreases(
		HttpServletRequest request,
		HttpServletResponse response
	) {
		List<LenderSelectionAdjustmentDTO> allSelectLenders = increasedSelectionService.getLenderIncreasedSelections();
		return new ResponseEntity<List<LenderSelectionAdjustmentDTO>>(allSelectLenders, HttpStatus.OK);
	}

	public ResponseEntity<Void> postAdminLenderSelectionIncrease(
		String lenderId,
		HttpServletRequest request,
		HttpServletResponse response
	) {
		increasedSelectionService.createLenderIncreasedSelection(lenderId);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	public ResponseEntity<Void> putAdminLenderSelectionIncrease(
		String lenderId,
		LenderSelectionAdjustmentDTO lenderSelectionAdjustmentDTO,
		HttpServletRequest request,
		HttpServletResponse response
	) {
		increasedSelectionService.updateLenderIncreasedSelection(lenderId, lenderSelectionAdjustmentDTO.getPercentToReview().shortValue());
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	public ResponseEntity<Void> deleteAdminLenderSelectionIncrease(
		String lenderId,
		HttpServletRequest request,
		HttpServletResponse response
	) {
		increasedSelectionService.deleteLenderIncreasedSelection(lenderId);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	public  ResponseEntity<List<LenderSelectionAdjustmentDTO>> getAdminUnderwriterSelectionIncreases(
		HttpServletRequest request,
		HttpServletResponse response
	) {
		List<LenderSelectionAdjustmentDTO> allSelectLenders = increasedSelectionService.getUnderwriterIncreasedSelections();
		return new ResponseEntity<List<LenderSelectionAdjustmentDTO>>(allSelectLenders, HttpStatus.OK);
	}

	public ResponseEntity<String> postAdminUnderwriterSelectionIncrease(
		String underwriterId,
		HttpServletRequest request,
		HttpServletResponse response
	) {
		increasedSelectionService.createUnderwriterIncreasedSelection(underwriterId);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	public ResponseEntity<String> putAdminUnderwriterSelectionIncrease(
		String lenderId,
		LenderSelectionAdjustmentDTO lenderSelectionAdjustmentDTO,
		HttpServletRequest request,
		HttpServletResponse response
	) {
		increasedSelectionService.updateUnderwriterIncreasedSelection(lenderId, lenderSelectionAdjustmentDTO.getPercentToReview().shortValue());
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	public ResponseEntity<String> deleteAdminUnderwriterSelectionIncrease(String lenderId, HttpServletRequest request, HttpServletResponse response) {
		increasedSelectionService.deleteUnderwriterIncreasedSelection(lenderId);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	public ResponseEntity<List<FieldDTO>> getAdminFields(
		HttpServletRequest request,
		HttpServletResponse response
	) {
		List<DictMetadataField> reviewLevelFields = qaService.getReviewLevelFields();
		List<FieldDTO> fieldDTOs = new ArrayList<FieldDTO>();
		int counter = 0;	// TODO: what does this do? Why do we need a code?
		for (DictMetadataField field : reviewLevelFields) {
			FieldDTO dto = new FieldDTO();
			dto.setCode(String.valueOf(++counter));
			dto.setEntityName(field.getId().getEntityName());
			dto.setFieldName(field.getId().getFieldName());
			dto.setDescription(field.getDescription());
			fieldDTOs.add(dto);
		}
		return new ResponseEntity<List<FieldDTO>>(fieldDTOs, HttpStatus.OK);
	}

	public ResponseEntity<List<QaModelDTO>> getAdminQaModels(HttpServletRequest request, HttpServletResponse response) {
		List<QaModelDTO> qaModelDTOs = qaService.getQaModels();
		return new ResponseEntity<List<QaModelDTO>>(qaModelDTOs, HttpStatus.OK);
	}

	public ResponseEntity<QaModelDetailDTO> getAdminQaModelByQaModelId(
		String qaModelId,
		HttpServletRequest request,
		HttpServletResponse response
	) {
		QaModel qaModel = qaModelRepository.findOne(qaModelId);
		if (qaModel == null) {
			throw new NotFoundException("QA Model " + qaModelId + " is not found");
		}

		QaModelDetailDTO qaModelDetailDTO = qaService.convertQaModelToQaModelDetailDTO(qaModel);

		return new ResponseEntity<QaModelDetailDTO>(qaModelDetailDTO, HttpStatus.OK);
	}

	public ResponseEntity<QaModelDetailDTO> putAdminQaModel(
		String qaModelId,
		QaModelDetailDTO data,
		HttpServletRequest request,
		HttpServletResponse response
	) {
		QaModel qaModel = qaService.updateQaModel(qaModelId, data);
		QaModelDetailDTO qaModelDetailDTO = qaService.convertQaModelToQaModelDetailDTO(qaModel);

		return new ResponseEntity<QaModelDetailDTO>(qaModelDetailDTO, HttpStatus.OK);
	}

	public ResponseEntity<Void> putAdminQaModelActivate(
		String qaModelId,
		HttpServletRequest request,
		HttpServletResponse response
	) {
		qaService.activateQaModel(qaModelId);

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	public ResponseEntity<QaModelDetailDTO> putAdminQaModelDuplicate(
		String qaModelId,
		ModelDuplicatorDTO modelDuplicatorDTO,
		HttpServletRequest request,
		HttpServletResponse response
	) {
		QaModel qaModel = qaService.duplicateQaModel(qaModelId, modelDuplicatorDTO.getName());
		QaModelDetailDTO qaModelDetailDTO = qaService.convertQaModelToQaModelDetailDTO(qaModel);

		return new ResponseEntity<QaModelDetailDTO>(qaModelDetailDTO, HttpStatus.OK);
	}

	public ResponseEntity<Void> deleteAdminQaModel(
		String qaModelId,
		HttpServletRequest request,
		HttpServletResponse response
	) {
		qaService.deleteQaModel(qaModelId);

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	public ResponseEntity<QaModelDefectAreaDetailDTO> getAdminQaModelDefectAreaByDefectAreaId(
		String qaModelId,
		String defectId,
		HttpServletRequest request,
		HttpServletResponse response
	) {
		Defect defect = defectRepository.findOne(defectId);
		if (defect == null) {
			throw new NotFoundException("Defect " + defectId + " is not found");
		}
		QaModelDefectAreaDetailDTO qaModelDefectAreaDetailDTO = qaService.convertDefectToQaModelDefectAreaDetailDTO(defect);

		return new ResponseEntity<QaModelDefectAreaDetailDTO>(qaModelDefectAreaDetailDTO, HttpStatus.OK);
	}

	public ResponseEntity<QaModelDefectAreaDTO> postAdminQaModelDefectArea(
		String qaModelId,
		QaModelDefectAreaDTO qaModelDefectAreaDTO,
		HttpServletRequest request,
		HttpServletResponse response
	) {
		Defect defect = qaService.createDefect(qaModelId, qaModelDefectAreaDTO);
		QaModelDefectAreaDTO updatedQaModelDefectAreaDTO = qaService.convertDefectToQaModelDefectAreaDTO(defect);

		return new ResponseEntity<QaModelDefectAreaDTO>(updatedQaModelDefectAreaDTO, HttpStatus.OK);
	}

	public ResponseEntity<QaModelDefectAreaDetailDTO> putAdminQaModelDefectArea(
		String qaModelId,
		String defectId,
		QaModelDefectAreaDetailDTO data,
		HttpServletRequest request,
		HttpServletResponse response
	) {
		Defect defect = qaService.updateDefect(qaModelId, defectId, data);
		QaModelDefectAreaDetailDTO qaModelDefectAreaDetailDTO = qaService.convertDefectToQaModelDefectAreaDetailDTO(defect);

		return new ResponseEntity<QaModelDefectAreaDetailDTO>(qaModelDefectAreaDetailDTO, HttpStatus.OK);
	}

	public ResponseEntity<Void> deleteAdminQaModelDefectArea(
		String qaModelId,
		String defectId,
		HttpServletRequest request,
		HttpServletResponse response
	) {
		qaService.deleteDefect(defectId);

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	public ResponseEntity<List<AssignmentModelDTO>> getAdminAssignmentModels(HttpServletRequest request, HttpServletResponse response) {
		List<AssignmentModelDTO> allAssignmentModels = new ArrayList<AssignmentModelDTO>();
		for (ScoringModelVersion scoringModelVersion : scoringModelVersionRepository.findByScoringModelScoringModelTypeRefCode(ScoringModelTypeCodes.ASSIGNMENT)) {
			AssignmentModelDTO assignmentModelDTO = getAssignmentModelDTO(scoringModelVersion);
			allAssignmentModels.add(assignmentModelDTO);
		}
		return new ResponseEntity<List<AssignmentModelDTO>>(allAssignmentModels, HttpStatus.OK);
	}

	public ResponseEntity<List<DistributionModelDTO>> getAdminDistributionModels(HttpServletRequest request, HttpServletResponse response) {
		List<DistributionModelDTO> allDistributionModels = new ArrayList<DistributionModelDTO>();
		for (ScoringModelVersion scoringModelVersion : scoringModelVersionRepository.findByScoringModelScoringModelTypeRefCode(ScoringModelTypeCodes.DISTRIBUTION)) {
			DistributionModelDTO distributionModelDTO = getDistributionModelDTO(scoringModelVersion);
			allDistributionModels.add(distributionModelDTO);
		}
		allDistributionModels.sort((p1, p2) -> p1.getVersion().compareTo(p2.getVersion()));
		return new ResponseEntity<List<DistributionModelDTO>>(allDistributionModels, HttpStatus.OK);
	}

	public ResponseEntity<List<SelectionModelDTO>> getAdminSelectionModels(HttpServletRequest request, HttpServletResponse response) {

		List<SelectionModelDTO> allSelectionModels = new ArrayList<SelectionModelDTO>();
		for (ScoringModelVersion scoringModelVersion : scoringModelVersionRepository.findByScoringModelScoringModelTypeRefCodeIn(ScoringModelTypeCodes.SELECTION_CODES)) {
			SelectionModelDTO selectionModelDTO = getSelectionModelDTO(scoringModelVersion);
			allSelectionModels.add(selectionModelDTO);
		}
		return new ResponseEntity<List<SelectionModelDTO>>(allSelectionModels, HttpStatus.OK);
	}

	private SelectionModelDTO getSelectionModelDTO(ScoringModelVersion scoringModelVersion) {
		SelectionModelDTO selectionModelDTO = new SelectionModelDTO();
		Set<ScoringModelVersionFactor> scoringModelVersionFactors = scoringModelVersion.getScoringModelVersionFactors();
		selectionModelDTO.setId(scoringModelVersion.getScoringModelVersionId());
		selectionModelDTO.setSelectionModelCategory(scoringModelVersion.getScoringModel().getScoringModelId());

		List<FactorDTO> factors = new ArrayList<FactorDTO>();
		for (ScoringModelVersionFactor scoringModelVersionFactor : scoringModelVersionFactors) {
			FactorDTO factorDTO = new FactorDTO();
			if (scoringModelVersionFactor.getId() != null) {
				factorDTO.setId(scoringModelVersionFactor.getId().getScoringFactorId());
			}
			if (scoringModelVersionFactor.getScoringFactor() != null) {
				factorDTO.setName(scoringModelVersionFactor.getScoringFactor().getFactorAttributeName());
				factorDTO.setDescription(scoringModelVersionFactor.getScoringFactor().getDescription());
			}
			factorDTO.setWeight(scoringModelVersionFactor.getWeight());
			factors.add(factorDTO);
		}
		selectionModelDTO.setFactors(factors);

		selectionModelDTO.setName(scoringModelVersion.getModelName());
		selectionModelDTO.setDescription(scoringModelVersion.getDescription());
		selectionModelDTO.setSelectionThreshold(BigDecimal.valueOf(scoringModelVersion.getModelScoreThreshold()));
		selectionModelDTO.setStatus(scoringModelVersion.getScoringModelVersionStatusRef().getCode().toLowerCase());
		selectionModelDTO.setVersion((int) scoringModelVersion.getModelVerNum());
		selectionModelDTO.setAllocationPercentage(new BigDecimal(scoringModelVersion.getAllocationPct()));
		if (scoringModelVersion.getReviewScopeRef() != null) {
			selectionModelDTO.setScope(scoringModelVersion.getReviewScopeRef().getDescription());
			selectionModelDTO.setScopeCode(scoringModelVersion.getReviewScopeRef().getCode());
		}
		if (scoringModelVersion.getReviewTypeRef() != null) {
			selectionModelDTO.setDefaultReviewType(scoringModelVersion.getReviewTypeRef().getDescription());
			selectionModelDTO.setDefaultReviewTypeCode(scoringModelVersion.getReviewTypeRef().getReviewTypeCd());
		}

		return selectionModelDTO;
	}

	public ResponseEntity<SelectionModelDTO> putAdminSelectionModelDuplicate(
		String selectionModelVersionId,
		ModelDuplicatorDTO modelDuplicatorDTO,
		HttpServletRequest request,
		HttpServletResponse response
	) {
		ScoringModelVersion savedSscoringModelVersion = scoringModelService.createDuplicateScoringModelVersion(selectionModelVersionId, modelDuplicatorDTO.getName());
		SelectionModelDTO selectionModelDTO = getSelectionModelDTO(savedSscoringModelVersion);

		return new ResponseEntity<SelectionModelDTO>(selectionModelDTO, HttpStatus.OK);
	}

	public ResponseEntity<DistributionModelDTO> putAdminDistributionModelDuplicate(
		String distributionModelVersionId,
		ModelDuplicatorDTO modelDuplicatorDTO,
		HttpServletRequest request,
		HttpServletResponse response
	) {
		ScoringModelVersion scoringModelVersion = scoringModelService.createDuplicateScoringModelVersion(distributionModelVersionId, modelDuplicatorDTO.getName());
		DistributionModelDTO distributionModelDTO = getDistributionModelDTO(scoringModelVersion);

		return new ResponseEntity<DistributionModelDTO>(distributionModelDTO, HttpStatus.OK);
	}

	public ResponseEntity<AssignmentModelDTO> putAdminAssignmentModel(
		String assignmentModelVersionId,
		AssignmentModelDTO data,
		HttpServletRequest request,
		HttpServletResponse response
	) {
		ScoringModelVersion scoringModelVersion = scoringModelVersionRepository.findOne(assignmentModelVersionId);
		String userId = securityService.getUserId();
		Date now = new Date();
		if ((data != null) && (data.getSelectionThreshold() != null) && data.getStatus().equalsIgnoreCase(ScoringModelVersionStatusCodes.ACTIVE)) {
			ScoringModelVersion activeScoringModelVersion = scoringModelVersionRepository.findActiveByScoringModelId(scoringModelVersion.getScoringModel().getScoringModelId());
			if (activeScoringModelVersion != null) {
				activeScoringModelVersion.setScoringModelVersionStatusRef(scoringModelVersionStatusRefRepository.findByCode(ScoringModelVersionStatusCodes.ARCHIVED));
				activeScoringModelVersion.setUpdatedTs(now);
				activeScoringModelVersion.setUpdatedBy(userId);
				activeScoringModelVersion = scoringModelVersionRepository.save(activeScoringModelVersion);
			}
		}
		if (data.getSelectionThreshold() != null) {
			scoringModelVersion.setModelScoreThreshold(data.getSelectionThreshold().floatValue());
		}
		scoringModelVersion.setUpdatedTs(now);
		scoringModelVersion.setUpdatedBy(userId);
		scoringModelVersion.setScoringModelVersionStatusRef(scoringModelVersionStatusRefRepository.findByCode(data.getStatus()));
		scoringModelVersion.setModelName(data.getName());
		scoringModelVersion.setDescription(data.getDescription());
		if (data.getAllocationPercentage() != null) {
			scoringModelVersion.setAllocationPct(data.getAllocationPercentage().intValue());
		}
		if (data.getDefaultReviewTypeCode() != null) {
			scoringModelVersion.setReviewTypeRef(reviewTypeRefRepository.findByReviewTypeCd(data.getDefaultReviewTypeCode()));
		}

		Set<ScoringModelVersionFactor> scoringModelVersionFactors = scoringModelVersion.getScoringModelVersionFactors();
		scoringModelVersionFactorRepository.delete(scoringModelVersionFactors);
		Set<ScoringModelVersionFactor> updatedScoringModelVersionFactors = new HashSet<ScoringModelVersionFactor>();
		List<FactorDTO> factors = data.getFactors();
		for (FactorDTO factorDTO : factors) {
			ScoringModelVersionFactor scoringModelVersionFactor = new ScoringModelVersionFactor();

			ScoringModelVersionFactorId scoringModelVersionFactorId = new ScoringModelVersionFactorId();
			scoringModelVersionFactorId.setScoringFactorId(factorDTO.getId());
			scoringModelVersionFactorId.setScoringModelVersionId(scoringModelVersion.getScoringModelVersionId());
			scoringModelVersionFactor.setId(scoringModelVersionFactorId);
			scoringModelVersionFactor.setUpdatedBy(userId);
			scoringModelVersionFactor.setUpdatedTs(now);
			scoringModelVersionFactor.setWeight(factorDTO.getWeight());
			ScoringFactor scoringFactor = new ScoringFactor();
			scoringFactor.setScoringFactorId(factorDTO.getId());
			scoringFactor.setFactorAttributeName(factorDTO.getName());
			scoringFactor.setDescription(factorDTO.getDescription());
			scoringModelVersionFactor.setScoringFactor(scoringFactor);
			updatedScoringModelVersionFactors.add(scoringModelVersionFactor);
		}
		scoringModelVersionFactorRepository.save(updatedScoringModelVersionFactors);
		scoringModelVersion.setScoringModelVersionFactors(updatedScoringModelVersionFactors);
		ScoringModelVersion savedScoringModelVersion = scoringModelVersionRepository.save(scoringModelVersion);

		AssignmentModelDTO assignmentModelDTO = getAssignmentModelDTO(savedScoringModelVersion);

		return new ResponseEntity<AssignmentModelDTO>(assignmentModelDTO, HttpStatus.OK);
	}

	public ResponseEntity<DistributionModelDTO> putAdminDistributionModel(
		String distributionModelVersionId,
		DistributionModelDTO data,
		HttpServletRequest request,
		HttpServletResponse response
	) {
		ScoringModelVersion scoringModelVersion = scoringModelVersionRepository.findOne(distributionModelVersionId);
		String userId = securityService.getUserId();
		Date now = new Date();
		if ((data != null && data.getSelectionThreshold() != null) && data.getStatus().equalsIgnoreCase(ScoringModelVersionStatusCodes.ACTIVE)) {
			ScoringModelVersion activeScoringModelVersion = scoringModelVersionRepository.findActiveByScoringModelId(scoringModelVersion.getScoringModel().getScoringModelId());
			if (activeScoringModelVersion != null) {
				activeScoringModelVersion.setScoringModelVersionStatusRef(scoringModelVersionStatusRefRepository.findByCode(ScoringModelVersionStatusCodes.ARCHIVED));
				activeScoringModelVersion.setUpdatedTs(now);
				activeScoringModelVersion.setUpdatedBy(userId);
				activeScoringModelVersion = scoringModelVersionRepository.save(activeScoringModelVersion);
			}
		}
			if (data.getSelectionThreshold() != null) {
				scoringModelVersion.setModelScoreThreshold(data.getSelectionThreshold().floatValue());
			}
			scoringModelVersion.setUpdatedTs(now);
			scoringModelVersion.setUpdatedBy(userId);
			scoringModelVersion.setScoringModelVersionStatusRef(scoringModelVersionStatusRefRepository.findByCode(data.getStatus()));
			scoringModelVersion.setModelName(data.getName());
			scoringModelVersion.setDescription(data.getDescription());
			if (data.getAllocationPercentage() != null) {
				scoringModelVersion.setAllocationPct(data.getAllocationPercentage().intValue());
			}
			if (data.getDefaultReviewTypeCode() != null) {
				scoringModelVersion.setReviewTypeRef(reviewTypeRefRepository.findByReviewTypeCd(data.getDefaultReviewTypeCode()));
			}

		Set<ScoringModelVersionFactor> scoringModelVersionFactors = scoringModelVersion.getScoringModelVersionFactors();
		scoringModelVersionFactorRepository.delete(scoringModelVersionFactors);
		Set<ScoringModelVersionFactor> updatedScoringModelFactors = new HashSet<ScoringModelVersionFactor>();
		List<FactorDTO> factors = data.getFactors();
		for (FactorDTO factorDTO : factors) {
			ScoringModelVersionFactor scoringModelVersionFactor = new ScoringModelVersionFactor();

			ScoringModelVersionFactorId scoringModelVersionFactorId = new ScoringModelVersionFactorId();
			scoringModelVersionFactorId.setScoringFactorId(factorDTO.getId());
			scoringModelVersionFactorId.setScoringModelVersionId(scoringModelVersion.getScoringModelVersionId());
			scoringModelVersionFactor.setId(scoringModelVersionFactorId);
			scoringModelVersionFactor.setUpdatedBy(userId);
			scoringModelVersionFactor.setUpdatedTs(now);
			scoringModelVersionFactor.setWeight(factorDTO.getWeight());
			ScoringFactor scoringFactor = new ScoringFactor();
			scoringFactor.setScoringFactorId(factorDTO.getId());
			scoringFactor.setFactorAttributeName(factorDTO.getName());
			scoringFactor.setDescription(factorDTO.getDescription());
			scoringModelVersionFactor.setScoringFactor(scoringFactor);
			updatedScoringModelFactors.add(scoringModelVersionFactor);
		}
		scoringModelVersionFactorRepository.save(updatedScoringModelFactors);
		scoringModelVersion.setScoringModelVersionFactors(updatedScoringModelFactors);
		ScoringModelVersion savedScoringModelVersion = scoringModelVersionRepository.save(scoringModelVersion);

		DistributionModelDTO distributionModelDTO = getDistributionModelDTO(savedScoringModelVersion);

		return new ResponseEntity<DistributionModelDTO>(distributionModelDTO, HttpStatus.OK);
	}

	@Transactional
	public ResponseEntity<SelectionModelDTO> putAdminSelectionModel(
		String selectionModelVersionId,
		SelectionModelDTO data,
		HttpServletRequest request,
		HttpServletResponse response
	) {
		ScoringModelVersion scoringModelVersion = scoringModelVersionRepository.findOne(selectionModelVersionId);
		String userId = securityService.getUserId();
		Date now = new Date();
		if ((data != null && data.getSelectionThreshold() != null) && data.getStatus().equalsIgnoreCase(ScoringModelVersionStatusCodes.ACTIVE)) {
			ScoringModelVersion activeScoringModelVersion = scoringModelVersionRepository.findActiveByScoringModelId(scoringModelVersion.getScoringModel().getScoringModelId());
			if (activeScoringModelVersion != null) {
				activeScoringModelVersion.setScoringModelVersionStatusRef(scoringModelVersionStatusRefRepository.findByCode(ScoringModelVersionStatusCodes.ARCHIVED));
				activeScoringModelVersion.setUpdatedTs(now);
				activeScoringModelVersion.setUpdatedBy(userId);
				scoringModelVersionRepository.save(activeScoringModelVersion);
			}
		}
			if (data.getSelectionThreshold() != null) {
				scoringModelVersion.setModelScoreThreshold(data.getSelectionThreshold().floatValue());
			}
			scoringModelVersion.setUpdatedTs(now);
			scoringModelVersion.setUpdatedBy(userId);
			scoringModelVersion.setScoringModelVersionStatusRef(scoringModelVersionStatusRefRepository.findByCode(data.getStatus()));
			scoringModelVersion.setModelName(data.getName());
			scoringModelVersion.setDescription(data.getDescription());
			if (data.getAllocationPercentage() != null) {
				scoringModelVersion.setAllocationPct(data.getAllocationPercentage().intValue());
			}
			if (data.getScopeCode() != null) {
				scoringModelVersion.setReviewScopeRef(reviewScopeRefRepository.findByCode(data.getScopeCode()));
			}
			if (data.getDefaultReviewTypeCode() != null) {
				scoringModelVersion.setReviewTypeRef(reviewTypeRefRepository.findByReviewTypeCd(data.getDefaultReviewTypeCode()));
			}

		Set<ScoringModelVersionFactor> scoringModelVersionFactors = scoringModelVersion.getScoringModelVersionFactors();
		scoringModelVersionFactorRepository.delete(scoringModelVersionFactors);
		Set<ScoringModelVersionFactor> updatedScoringModelFactors = new HashSet<ScoringModelVersionFactor>();
		List<FactorDTO> factors = data.getFactors();
		for (FactorDTO factorDTO : factors) {
			ScoringModelVersionFactor scoringModelVersionFactor = new ScoringModelVersionFactor();

			ScoringModelVersionFactorId scoringModelVersionFactorId = new ScoringModelVersionFactorId();
			scoringModelVersionFactorId.setScoringFactorId(factorDTO.getId());
			scoringModelVersionFactorId.setScoringModelVersionId(scoringModelVersion.getScoringModelVersionId());
			scoringModelVersionFactor.setId(scoringModelVersionFactorId);
			scoringModelVersionFactor.setUpdatedBy(userId);
			scoringModelVersionFactor.setUpdatedTs(now);
			scoringModelVersionFactor.setWeight(factorDTO.getWeight());
			ScoringFactor scoringFactor = new ScoringFactor();
			scoringFactor.setScoringFactorId(factorDTO.getId());
			scoringFactor.setFactorAttributeName(factorDTO.getName());
			scoringFactor.setDescription(factorDTO.getDescription());
			scoringModelVersionFactor.setScoringFactor(scoringFactor);
			updatedScoringModelFactors.add(scoringModelVersionFactor);
		}
		scoringModelVersionFactorRepository.save(updatedScoringModelFactors);
		scoringModelVersion.setScoringModelVersionFactors(updatedScoringModelFactors);
		ScoringModelVersion savedScoringModelVersion = scoringModelVersionRepository.save(scoringModelVersion);

		SelectionModelDTO selectionModelDTO = getSelectionModelDTO(savedScoringModelVersion);

		return new ResponseEntity<SelectionModelDTO>(selectionModelDTO, HttpStatus.OK);
	}

	public ResponseEntity<Void> deleteAdminSelectionModel(String selectionModelVersionId, HttpServletRequest request, HttpServletResponse response) {
		scoringModelService.archiveScoringModelVersion(selectionModelVersionId);

		return new ResponseEntity<Void> (HttpStatus.OK);
	}

	public ResponseEntity<Void> deleteAdminDistributionModel(String distributionModelVersionId, HttpServletRequest request, HttpServletResponse response) {
		scoringModelService.archiveScoringModelVersion(distributionModelVersionId);

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	public ResponseEntity<Void> deleteAdminAssignmentModel(String assignmentModelVersionId, HttpServletRequest request, HttpServletResponse response) {
		scoringModelService.archiveScoringModelVersion(assignmentModelVersionId);

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	public ResponseEntity<AssignmentModelDTO> putAdminAssignmentModelDuplicate(
		String assignmentModelVersionId,
		ModelDuplicatorDTO modelDuplicatorDTO,
		HttpServletRequest request,
		HttpServletResponse response
	) {
		ScoringModelVersion scoringModelVersion = scoringModelService.createDuplicateScoringModelVersion(assignmentModelVersionId, modelDuplicatorDTO.getName());
		AssignmentModelDTO assignmentModelDTO = getAssignmentModelDTO(scoringModelVersion);

		return new ResponseEntity<AssignmentModelDTO>(assignmentModelDTO, HttpStatus.OK);
	}

	public ResponseEntity<DistributionModelDTO> getAdminDistributionModelById(String distributionModelVersionId, HttpServletRequest request, HttpServletResponse response) {
		ScoringModelVersion scoringModelVersion = scoringModelVersionRepository.findOne(distributionModelVersionId);
		DistributionModelDTO distributionModelDTO = getDistributionModelDTO(scoringModelVersion);

		return new ResponseEntity<DistributionModelDTO>(distributionModelDTO, HttpStatus.OK);
	}

	public ResponseEntity<SelectionModelDTO> getAdminSelectionModelById(String selectionModelVersionId, HttpServletRequest request, HttpServletResponse response) {
		ScoringModelVersion scoringModelVersion = scoringModelVersionRepository.findOne(selectionModelVersionId);
		SelectionModelDTO selectionModelDTO = getSelectionModelDTO(scoringModelVersion);

		return new ResponseEntity<SelectionModelDTO>(selectionModelDTO, HttpStatus.OK);
	}

	public ResponseEntity<AssignmentModelDTO> getAdminAssignmentModelById(String assignmentModelVersionId, HttpServletRequest request, HttpServletResponse response) {
		ScoringModelVersion scoringModelVersion = scoringModelVersionRepository.findOne(assignmentModelVersionId);
		AssignmentModelDTO assignmentModelDTO = getAssignmentModelDTO(scoringModelVersion);

		return new ResponseEntity<AssignmentModelDTO>(assignmentModelDTO, HttpStatus.OK);
	}

	private DistributionModelDTO getDistributionModelDTO(ScoringModelVersion scoringModelVersion) {
		DistributionModelDTO distributionModelDTO = new DistributionModelDTO();

		Set<ScoringModelVersionFactor> scoringModelVersionFactors = scoringModelVersion.getScoringModelVersionFactors();
		distributionModelDTO.setId(scoringModelVersion.getScoringModelVersionId());
		distributionModelDTO.setDistributionModelCategory(scoringModelVersion.getScoringModel().getScoringModelId());

		List<FactorDTO> factors = new ArrayList<FactorDTO>();
		for (ScoringModelVersionFactor scoringModelVersionFactor : scoringModelVersionFactors) {
			FactorDTO factorDTO = new FactorDTO();
			if (scoringModelVersionFactor.getId() != null) {
				factorDTO.setId(scoringModelVersionFactor.getId().getScoringFactorId());
			}
			if (scoringModelVersionFactor.getScoringFactor() != null) {
				factorDTO.setName(scoringModelVersionFactor.getScoringFactor().getFactorAttributeName());
				factorDTO.setDescription(scoringModelVersionFactor.getScoringFactor().getDescription());
			}
			factorDTO.setWeight(scoringModelVersionFactor.getWeight());
			factors.add(factorDTO);
		}
		distributionModelDTO.setFactors(factors);

		distributionModelDTO.setName(scoringModelVersion.getModelName());
		distributionModelDTO.setDescription(scoringModelVersion.getDescription());
		distributionModelDTO.setSelectionThreshold(BigDecimal.valueOf(scoringModelVersion.getModelScoreThreshold()));
		distributionModelDTO.setStatus(scoringModelVersion.getScoringModelVersionStatusRef().getCode().toLowerCase());
		distributionModelDTO.setVersion(Integer.valueOf(scoringModelVersion.getModelVerNum()));
		distributionModelDTO.setAllocationPercentage(new BigDecimal(scoringModelVersion.getAllocationPct()));
		if (scoringModelVersion.getReviewTypeRef() != null) {
			distributionModelDTO.setDefaultReviewType(scoringModelVersion.getReviewTypeRef().getDescription());
			distributionModelDTO.setDefaultReviewTypeCode(scoringModelVersion.getReviewTypeRef().getReviewTypeCd());
		}

		return distributionModelDTO;
	}

	private AssignmentModelDTO getAssignmentModelDTO(ScoringModelVersion scoringModelVersion) {
		AssignmentModelDTO assignmentModelDTO = new AssignmentModelDTO();
		Set<ScoringModelVersionFactor> scoringModelVersionFactors = scoringModelVersion.getScoringModelVersionFactors();
		assignmentModelDTO.setId(scoringModelVersion.getScoringModelVersionId());
		assignmentModelDTO.setAssignmentModelCategory(scoringModelVersion.getScoringModel().getScoringModelId());

		List<FactorDTO> factors = new ArrayList<FactorDTO>();
		for (ScoringModelVersionFactor scoringModelVersionFactor : scoringModelVersionFactors) {
			FactorDTO factorDTO = new FactorDTO();
			if (scoringModelVersionFactor.getId() != null) {
				factorDTO.setId(scoringModelVersionFactor.getId().getScoringFactorId());
			}
			if (scoringModelVersionFactor.getScoringFactor() != null) {
				factorDTO.setName(scoringModelVersionFactor.getScoringFactor().getFactorAttributeName());
				factorDTO.setDescription(scoringModelVersionFactor.getScoringFactor().getDescription());
			}
			factorDTO.setWeight(scoringModelVersionFactor.getWeight());
			factors.add(factorDTO);
		}
		assignmentModelDTO.setFactors(factors);

		assignmentModelDTO.setName(scoringModelVersion.getModelName());
		assignmentModelDTO.setDescription(scoringModelVersion.getDescription());
		assignmentModelDTO.setSelectionThreshold(BigDecimal.valueOf(scoringModelVersion.getModelScoreThreshold()));
		assignmentModelDTO.setStatus(scoringModelVersion.getScoringModelVersionStatusRef().getCode().toLowerCase());
		assignmentModelDTO.setVersion(Integer.valueOf(scoringModelVersion.getModelVerNum()));
		assignmentModelDTO.setAllocationPercentage(new BigDecimal(scoringModelVersion.getAllocationPct()));
		if (scoringModelVersion.getReviewTypeRef() != null) {
			assignmentModelDTO.setDefaultReviewType(scoringModelVersion.getReviewTypeRef().getDescription());
			assignmentModelDTO.setDefaultReviewTypeCode(scoringModelVersion.getReviewTypeRef().getReviewTypeCd());
		}

		return assignmentModelDTO;
	}

	public ResponseEntity<List<CommonDetailDTO>> getAdminStaffManagementReviewLevels(HttpServletRequest request, HttpServletResponse response) {
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

	public ResponseEntity< List<EmailTemplateDTO>> getAdminEmails(HttpServletRequest request, HttpServletResponse response) {
		List<EmailTemplateDTO> emailTemplateDTOs = new ArrayList<EmailTemplateDTO>();

		for(EmailTemplate emailTemplate : emailTemplateRepository.findAll()) {
			EmailTemplateDTO emailTemplateDto = new EmailTemplateDTO();
			emailTemplateDto.setEmailCategory(emailTemplate.getDescription());
			List<EmailTemplateVersionLiteDTO> templateVersionDTOs = new ArrayList<EmailTemplateVersionLiteDTO>();
			for(EmailTemplateVersion templateVersion : emailTemplateService.getAllEmailTemplateVersions(emailTemplate)) {
				templateVersionDTOs.add(emailTemplateService.convertEmailTemplateVersionToEmailTemplateVersionLiteDTO(templateVersion));
			}
			emailTemplateDto.setTemplateVersions(templateVersionDTOs);
			emailTemplateDTOs.add(emailTemplateDto);
		}

		return new ResponseEntity<List<EmailTemplateDTO>>(emailTemplateDTOs, HttpStatus.OK);
	}

	public ResponseEntity<EmailTemplateVersionDTO> getAdminEmailByTemplateId(String templateId, HttpServletRequest request,	HttpServletResponse response) {
		EmailTemplateVersionDTO dto = emailTemplateService.convertEmailTemplateVersionToEmailTemplateVersionDTO(emailTemplateService.getEmailTemplateVersion(templateId));
		return new ResponseEntity<EmailTemplateVersionDTO>(dto, HttpStatus.OK);
	}

	public ResponseEntity<?> putAdminEmail(String templateId, EmailTemplateVersionDTO data, HttpServletRequest request, HttpServletResponse response) {
		EmailTemplateVersionDTO dto = emailTemplateService.convertEmailTemplateVersionToEmailTemplateVersionDTO(emailTemplateService.updateAdminEmailTemplateVersion(templateId,data));
		return new ResponseEntity<EmailTemplateVersionDTO>(dto, HttpStatus.OK);
	}

	public ResponseEntity<EmailTemplateVersionDTO> putAdminEmailDuplicate(String templateId, ModelDuplicatorDTO data, HttpServletRequest request, HttpServletResponse response) {
		EmailTemplateVersionDTO dto = emailTemplateService.convertEmailTemplateVersionToEmailTemplateVersionDTO(emailTemplateService.duplicateEmailTempateVersion(templateId,data.getName()));
		return new ResponseEntity<EmailTemplateVersionDTO>(dto, HttpStatus.OK);
	}

	public ResponseEntity<Void> putAdminEmailActivate(String templateId, HttpServletRequest request, HttpServletResponse response) {
		emailTemplateService.activateEmailTemplateVersion(templateId);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	public ResponseEntity<Void> postAdminEmailSendTest(String templateId, String emailAddress, HttpServletRequest request, HttpServletResponse response) {
		emailTemplateService.sendTestEmail(templateId, emailAddress);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
