package gov.hud.lrs.services.bizservice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ImmutableList;

import gov.hud.lrs.common.dto.LrsApiV1DTOs.CommonDetailDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.OptionsItemDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.OrderedItemDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.QaModelCategoryDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.QaModelDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.QaModelDefectAreaDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.QaModelDefectAreaDetailDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.QaModelDefectAreaPreQualifyDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.QaModelDefectAreaSeverityDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.QaModelDefectAreaThresholdDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.QaModelDetailDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.QaModelLoanAttributeDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.ReviewAnswerDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.ReviewConditionDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.ReviewQuestionDTO;
import gov.hud.lrs.common.entity.Answer;
import gov.hud.lrs.common.entity.Defect;
import gov.hud.lrs.common.entity.DefectCause;
import gov.hud.lrs.common.entity.DefectRemedyType;
import gov.hud.lrs.common.entity.DefectSeverity;
import gov.hud.lrs.common.entity.DefectSource;
import gov.hud.lrs.common.entity.DictMetadataField;
import gov.hud.lrs.common.entity.DictMetadataFieldDefect;
import gov.hud.lrs.common.entity.DictMetadataFieldDefectId;
import gov.hud.lrs.common.entity.DictMetadataFieldModel;
import gov.hud.lrs.common.entity.DictMetadataFieldModelId;
import gov.hud.lrs.common.entity.DictMetadataFieldSubjectArea;
import gov.hud.lrs.common.entity.FindingRatingRule;
import gov.hud.lrs.common.entity.LrsGenLookup;
import gov.hud.lrs.common.entity.Personnel;
import gov.hud.lrs.common.entity.QaModel;
import gov.hud.lrs.common.entity.QaModelPrequal;
import gov.hud.lrs.common.entity.QaModelPrequalId;
import gov.hud.lrs.common.entity.Qatree;
import gov.hud.lrs.common.entity.QatreeOutcome;
import gov.hud.lrs.common.entity.QatreeOutcomeDefectCause;
import gov.hud.lrs.common.entity.QatreeOutcomeDefectCauseId;
import gov.hud.lrs.common.entity.QatreeOutcomeDefectSeverity;
import gov.hud.lrs.common.entity.QatreeOutcomeDefectSeverityId;
import gov.hud.lrs.common.entity.QatreeOutcomeDefectSource;
import gov.hud.lrs.common.entity.QatreeOutcomeDefectSourceId;
import gov.hud.lrs.common.entity.QatreeQstnCondition;
import gov.hud.lrs.common.entity.QatreeQuestion;
import gov.hud.lrs.common.entity.QuestionReference;
import gov.hud.lrs.common.entity.ReviewLevel;
import gov.hud.lrs.common.entity.ReviewTypeDefect;
import gov.hud.lrs.common.entity.ReviewTypeDefectId;
import gov.hud.lrs.common.entity.ReviewTypeRef;
import gov.hud.lrs.common.enumeration.QaModelStatusCodes;
import gov.hud.lrs.common.exception.BadRequestException;
import gov.hud.lrs.common.exception.ConflictException;
import gov.hud.lrs.common.exception.NotFoundException;
import gov.hud.lrs.common.repository.AnswerRepository;
import gov.hud.lrs.common.repository.DefectCauseRepository;
import gov.hud.lrs.common.repository.DefectRemedyTypeRepository;
import gov.hud.lrs.common.repository.DefectRepository;
import gov.hud.lrs.common.repository.DefectSeverityRepository;
import gov.hud.lrs.common.repository.DefectSourceRepository;
import gov.hud.lrs.common.repository.DictMetadataFieldDefectRepository;
import gov.hud.lrs.common.repository.DictMetadataFieldModelRepository;
import gov.hud.lrs.common.repository.DictMetadataFieldRepository;
import gov.hud.lrs.common.repository.DictMetadataFieldSubjectAreaRepository;
import gov.hud.lrs.common.repository.FindingRatingRuleRepository;
import gov.hud.lrs.common.repository.LrsGenLookupRepository;
import gov.hud.lrs.common.repository.PersonnelRepository;
import gov.hud.lrs.common.repository.QaModelPrequalRepository;
import gov.hud.lrs.common.repository.QaModelRepository;
import gov.hud.lrs.common.repository.QaModelRepositoryCustom;
import gov.hud.lrs.common.repository.QaModelStatusRefRepository;
import gov.hud.lrs.common.repository.QatreeOutcomeDefectCauseRepository;
import gov.hud.lrs.common.repository.QatreeOutcomeDefectSeverityRepository;
import gov.hud.lrs.common.repository.QatreeOutcomeDefectSourceRepository;
import gov.hud.lrs.common.repository.QatreeOutcomeRepository;
import gov.hud.lrs.common.repository.QatreeQstnConditionRepository;
import gov.hud.lrs.common.repository.QatreeQuestionRepository;
import gov.hud.lrs.common.repository.QatreeRepository;
import gov.hud.lrs.common.repository.QuestionReferenceRepository;
import gov.hud.lrs.common.repository.ReviewLevelRepository;
import gov.hud.lrs.common.repository.ReviewRepository;
import gov.hud.lrs.common.repository.ReviewTypeDefectRepository;
import gov.hud.lrs.common.repository.ReviewTypeRefRepository;
import gov.hud.lrs.common.security.SecurityService;
import gov.hud.lrs.common.service.CommonReviewService;
import gov.hud.lrs.common.util.DateUtils;
import gov.hud.lrs.common.util.Util;

@Service
public class QaService {

	private static int NUM_TIERS = 4;
	private static final String YES = "YES";
	private static final String NO = "NO";
	private static final String PRE_QUAL_RESPONSE_TYPE = "Multiple";
	private static final String PRE_QUAL_ANSWER_VALUE = "QA_MODEL_PREQUAL";

	@Autowired private AnswerRepository answerRepository;
	@Autowired private DictMetadataFieldRepository dictMetadataFieldRepository;
	@Autowired private QaModelRepository qaModelRepository;
	@Autowired private QaModelRepositoryCustom qaModelCustomRepository;
	@Autowired private ReviewTypeDefectRepository reviewTypeDefectRepository;
	@Autowired private DefectRepository defectRepository;
	@Autowired private DefectCauseRepository defectCauseRepository;
	@Autowired private DefectSourceRepository defectSourceRepository;
	@Autowired private DefectSeverityRepository defectSeverityRepository;
	@Autowired private FindingRatingRuleRepository findingRatingRuleRepository;
	@Autowired private DictMetadataFieldDefectRepository dictMetadataFieldDefectRepository;
	@Autowired private DictMetadataFieldModelRepository dictMetadataFieldModelRepository;
	@Autowired private QatreeQuestionRepository qatreeQuestionRepository;
	@Autowired private PersonnelRepository personnelRepository;
	@Autowired private ReviewTypeRefRepository reviewTypeRefRepository;
	@Autowired private QatreeOutcomeRepository qatreeOutcomeRepository;
	@Autowired private QatreeOutcomeDefectCauseRepository qatreeOutcmCausesRepository;
	@Autowired private QatreeOutcomeDefectSourceRepository qatreeOutcmSourcesRepository;
	@Autowired private QatreeOutcomeDefectSeverityRepository qatreeOutcmSeverityRepository;
	@Autowired private QuestionReferenceRepository questionReferenceRepository;
	@Autowired private QatreeQstnConditionRepository qatreeQstnConditionRepository;
	@Autowired private QaModelPrequalRepository qaModelPrequalRepository;
	@Autowired private QatreeRepository qatreeRepository;
	@Autowired private DefectRemedyTypeRepository defectRemedyTypeRepository;
	@Autowired private LrsGenLookupRepository lrsGenLookupRepository;
	@Autowired private ReviewRepository reviewRepository;
	@Autowired private QaModelStatusRefRepository qaModelStatusRefRepository;
	@Autowired private DictMetadataFieldSubjectAreaRepository dictMetadataFieldSubjectAreaRepository;
	@Autowired private ReviewLevelRepository reviewLevelRepository;

	@Autowired private CommonReviewService commonReviewService;
	@Autowired private DictionaryService dictionaryService;
	@Autowired private SecurityService securityService;

	@PersistenceContext private EntityManager entityManager;

	public List<DictMetadataField> getReviewLevelFields() {
		return dictMetadataFieldRepository.findByIdEntityNameOrderByIdFieldName("Case Summary for Review Level");
	}

	public List<QaModelDTO> getQaModels() {
		List<QaModel> qaModels = qaModelRepository.findAll();

		Map<String, Integer> activeReviewCountByQaModelId = Util.index(
			reviewRepository.findActiveReviewCountsPerQaModel(),
			idCount -> (String)idCount[0],
			idCount -> (Integer)idCount[1]
		);

		Map<String, Integer> completedReviewCountByQaModelId = Util.index(
			reviewRepository.findCompletedReviewCountsPerQaModel(),
			idCount -> (String)idCount[0],
			idCount -> (Integer)idCount[1]
		);

		List<QaModelDTO> qaModelDTOs = new ArrayList<QaModelDTO>(qaModels.size());

		for (QaModel qaModel : qaModels) {
			QaModelDTO qaModelDTO = new QaModelDTO();
			qaModelDTO.setQaModelId(qaModel.getQaModelId());
			qaModelDTO.setName(qaModel.getModelName());
			qaModelDTO.setDescription(qaModel.getDescription());
			qaModelDTO.setVersion(qaModel.getVersionNum());
			qaModelDTO.setCreatedDate(DateUtils.convertDateToNoonUtcDate(qaModel.getCreatedTs()));
			qaModelDTO.setModifiedDate(DateUtils.convertDateToNoonUtcDate(qaModel.getUpdatedTs()));
			Personnel modifier = personnelRepository.findByLoginCredential(qaModel.getUpdatedBy());
			if (modifier != null) {
				qaModelDTO.setModifiedBy(modifier.getFirstName() +
					(StringUtils.isNotBlank(modifier.getMiddleName()) ? " " + modifier.getMiddleName() + " " : " ") +
					modifier.getLastName());
			}
			Integer activeReviewCount = activeReviewCountByQaModelId.get(qaModel.getQaModelId());
			Integer completedReviewCount = completedReviewCountByQaModelId.get(qaModel.getQaModelId());
			qaModelDTO.setActiveReviews((activeReviewCount != null) ? activeReviewCount : 0);
			qaModelDTO.setCompletedReviews((completedReviewCount != null) ? completedReviewCount : 0);
			if (qaModelDTO.getActiveReviews() > 0 || qaModelDTO.getCompletedReviews() > 0) {
				qaModelDTO.setIsReadonly(true);
			}
			qaModelDTO.setActivatedDate(DateUtils.convertDateToNoonUtcDate(qaModel.getActivationDate()));
			if (qaModel.getQaModelStatusRef().getCode().equals(QaModelStatusCodes.ACTIVE)) {
				qaModelDTO.setIsActive(true);
			} else {
				qaModelDTO.setIsActive(false);
			}
			qaModelDTO.setStatusCode(qaModel.getQaModelStatusRef().getCode());
			qaModelDTO.setStatus(qaModel.getQaModelStatusRef().getDescription());

			qaModelDTOs.add(qaModelDTO);
		}

		return qaModelDTOs;
	}

	@Transactional
	public QaModel duplicateQaModel(String qaModelId, String qaModelName) {
		QaModel qaModel = qaModelRepository.findOne(qaModelId);
		if (qaModel == null) {
			throw new NotFoundException("QA Model " + qaModelId + " is not found");
		}
		String userId = securityService.getUserId();
		qaModelCustomRepository.callSpQaModelDuplicate(
			qaModelId,
			qaModelName,
			"NEW QA MODEL BASED ON " + qaModel.getDescription(),
			userId
		);
		qaModel = qaModelRepository.findTopByOrderByCreatedTsDesc();	// TODO: would be better if the SP could return the ID directly
		return qaModel;
	}

	@Transactional
	public QaModel updateQaModel(String qaModelId, QaModelDetailDTO qaModelDetailDTO) {
		QaModel qaModel = qaModelRepository.findOne(qaModelId);
		if (qaModel == null) {
			throw new NotFoundException("QA Model " + qaModelId + " is not found");
		}

		qaModel.setModelName(qaModelDetailDTO.getName());
		qaModel.setDescription(qaModelDetailDTO.getDescription());
		qaModel.setVersionNum(qaModelDetailDTO.getVersion());
		// Use isActive flag to activate a model
		if (qaModelDetailDTO.getIsActive()) {
			qaModel.setActivationDate(new Date());
		}

		// TODO: sync with client, we shouldn't have to do this mapping manually
		String qaModelStatusCode;
		if (qaModelDetailDTO.getStatus().equalsIgnoreCase("active")) {
			qaModelStatusCode = QaModelStatusCodes.ACTIVE;
		} else if (qaModelDetailDTO.getStatus().equalsIgnoreCase("draft")) {
			qaModelStatusCode = QaModelStatusCodes.DRAFT;
		} else if (qaModelDetailDTO.getStatus().equalsIgnoreCase("archived")) {
			qaModelStatusCode = QaModelStatusCodes.ARCHIVED;
		} else {
			throw new BadRequestException("Unknown status: " + qaModelDetailDTO.getStatus());
		}

		qaModel.setQaModelStatusRef(qaModelStatusRefRepository.findByCode(qaModelStatusCode));
		String userId = securityService.getUserId();
		qaModel.setUpdatedBy(userId);
		Date now = new Date();
		qaModel.setUpdatedTs(now);
		qaModel = qaModelRepository.save(qaModel);

		// Save defects. Handle three cases: existing, non existing, and orphan
		List<String> defectCodes = new ArrayList<String>();
		for (QaModelDefectAreaDTO defectDTO : qaModelDetailDTO.getDefectAreas()) {
			// Build code list for orphan check
			defectCodes.add(defectDTO.getCode());

			Defect defect = defectRepository.findByDefectCdAndQaModel(defectDTO.getCode(), qaModel);
			// Defect exists in back end, update review type and order
			if (defect != null) {
				// update defect order
				defect.setRelativeOrder(defectDTO.getOrder().intValue());
				defect.setUpdatedBy(userId);
				defect.setUpdatedTs(new Date());
				defect = defectRepository.save(defect);
				// Clear review types and create new ones
				reviewTypeDefectRepository.deleteByIdDefectId(defectDTO.getDefectAreaId());
				for (String reviewCode : defectDTO.getReviewTypeCodes()) {
					ReviewTypeDefectId tyepId = new ReviewTypeDefectId();
					tyepId.setDefectId(defectDTO.getDefectAreaId());
					tyepId.setReviewTypeId(reviewTypeRefRepository.findByReviewTypeCd(reviewCode).getReviewTypeId());
					ReviewTypeDefect reviewTypeDefect = new ReviewTypeDefect();
					reviewTypeDefect.setId(tyepId);
					reviewTypeDefect.setCreatedBy(userId);
					reviewTypeDefect.setCreatedTs(new Date());
					reviewTypeDefect.setUpdatedBy(userId);
					reviewTypeDefect.setUpdatedTs(new Date());
					reviewTypeDefectRepository.save(reviewTypeDefect);
				}
			}
			// Defect does not exist in back end
			else {
				// save defect
				Defect newDefect = new Defect();
				newDefect.setQaModel(qaModel);
				newDefect.setDefectCd(defectDTO.getCode());
				newDefect.setDescription(defectDTO.getDescription());
				newDefect.setRelativeOrder(defectDTO.getOrder().intValue());
				newDefect.setCreatedBy(userId);
				newDefect.setCreatedTs(new Date());
				newDefect.setUpdatedBy(userId);
				newDefect.setUpdatedTs(new Date());
				newDefect = defectRepository.save(newDefect);
				// Create review types
				for (String reviewCode : defectDTO.getReviewTypeCodes()) {
					ReviewTypeDefectId tyepId = new ReviewTypeDefectId();
					tyepId.setDefectId(newDefect.getDefectId());
					tyepId.setReviewTypeId(
							reviewTypeRefRepository.findByReviewTypeCd(reviewCode).getReviewTypeId());
					ReviewTypeDefect reviewTypeDefect = new ReviewTypeDefect();
					reviewTypeDefect.setId(tyepId);
					reviewTypeDefect.setCreatedBy(userId);
					reviewTypeDefect.setCreatedTs(new Date());
					reviewTypeDefect.setUpdatedBy(userId);
					reviewTypeDefect.setUpdatedTs(new Date());
					reviewTypeDefectRepository.save(reviewTypeDefect);
				}

				// Create default severities with null description and null example tier1 through tier4
				for (int tier=1; tier<=NUM_TIERS; tier++) {
					DefectSeverity defectSeverity = new DefectSeverity();
					defectSeverity.setDefect(newDefect);
					defectSeverity.setDefectCd(newDefect.getDefectCd());
					defectSeverity.setSeverityTierCd(tier);
					defectSeverity.setRelativeOrder(tier);
					defectSeverity.setCreatedBy(userId);
					defectSeverity.setCreatedTs(now);
					defectSeverity.setUpdatedBy(userId);
					defectSeverity.setUpdatedTs(now);
					defectSeverityRepository.save(defectSeverity);
				}
			}
		}
		// handle orphaned defects
		List<Defect> defects = dictionaryService.getDefectAreasByQaModel(qaModelId);
		for (Defect defect : defects) {
			if (!defectCodes.contains(defect.getDefectCd())) {
				deleteDefectEntities(defect);
				defectRepository.delete(defect);
			}
		}

		// Save QA model loan attributes
		// Clear existing attributes
		dictMetadataFieldModelRepository.deleteByIdQaModelId(qaModelId);
		dictMetadataFieldSubjectAreaRepository.deleteByQaModel(qaModel);
		// Add new ones
		for (QaModelCategoryDTO categoryDTO : qaModelDetailDTO.getCategories()) {
			DictMetadataFieldSubjectArea modelSubjectArea = new DictMetadataFieldSubjectArea();
			modelSubjectArea.setQaModel(qaModel);
			modelSubjectArea.setSubjectArea(categoryDTO.getName() == null ? "" : categoryDTO.getName());
			modelSubjectArea.setDisplayOrder(categoryDTO.getOrder().intValue());
			modelSubjectArea = dictMetadataFieldSubjectAreaRepository.save(modelSubjectArea);
			for (QaModelLoanAttributeDTO fieldDTO : categoryDTO.getLoanAttributes()) {
				DictMetadataFieldModelId fieldId = new DictMetadataFieldModelId();
				fieldId.setEntityName(fieldDTO.getEntityName());
				fieldId.setFieldName(fieldDTO.getFieldName());
				fieldId.setQaModelId(qaModel.getQaModelId());
				DictMetadataFieldModel modelField = new DictMetadataFieldModel();
				modelField.setQaModel(qaModel);
				modelField.setId(fieldId);
				modelField.setDictMetadataFieldSubjectArea(modelSubjectArea);
				modelField.setOrderNumber(fieldDTO.getOrder().intValue());
				modelField.setCreatedBy(userId);
				modelField.setCreatedTs(new Date());
				modelField.setUpdatedBy(userId);
				modelField.setUpdatedTs(new Date());
				dictMetadataFieldModelRepository.save(modelField);
			}
		}

		return qaModel;
	}

	@Transactional
	public void activateQaModel(String qaModelId) {
		QaModel qaModel = qaModelRepository.findOne(qaModelId);
		if (qaModel == null) {
			throw new NotFoundException("QA Model " + qaModelId + " is not found");
		}

		String userId = securityService.getUserId();
		Date now = new Date();

		QaModel activeQaModel = qaModelRepository.findActive();

		// model is the active one, do nothing
		if ((activeQaModel != null) && activeQaModel.getQaModelId().equals(qaModel.getQaModelId())) {
			return;
		}

		// Check if all defect areas has questions
		List<Defect> defectsWithNoQuestions = defectRepository.findNoQuestionDefectByQaModelId(qaModelId);
		if (defectsWithNoQuestions != null && defectsWithNoQuestions.size() > 0) {
			throw new ConflictException(ImmutableList.of("Defect area " + defectsWithNoQuestions.get(0).getDescription() + " has no questions."));
		}

		// make the model active
		qaModel.setActivationDate(new Date());
		qaModel.setQaModelStatusRef(qaModelStatusRefRepository.findByCode(QaModelStatusCodes.ACTIVE));
		qaModel.setUpdatedBy(userId);
		qaModel.setUpdatedTs(now);
		qaModelRepository.save(qaModel);

		// update original active model
		if (activeQaModel != null) {
			int activeReviews = reviewRepository.findActiveReviewCountByQaModelId(activeQaModel.getQaModelId());
			int completedReviews = reviewRepository.findCompletedReviewCountByQaModelId(activeQaModel.getQaModelId());
			if (activeReviews > 0 || completedReviews > 0) {
				activeQaModel.setQaModelStatusRef(qaModelStatusRefRepository.findByCode(QaModelStatusCodes.ARCHIVED));
			}
			else {
				activeQaModel.setQaModelStatusRef(qaModelStatusRefRepository.findByCode(QaModelStatusCodes.DRAFT));
			}
			activeQaModel.setUpdatedBy(userId);
			activeQaModel.setUpdatedTs(now);
			qaModelRepository.save(activeQaModel);
		}
	}

	@Transactional
	public void deleteQaModel(String qaModelId) {
		QaModel qaModel = qaModelRepository.findOne(qaModelId);
		if (qaModel == null) {
			throw new NotFoundException("QA Model " + qaModelId + " is not found");
		}

		// Delete QA Model loan attributes
		dictMetadataFieldModelRepository.deleteByIdQaModelId(qaModelId);
		dictMetadataFieldSubjectAreaRepository.deleteByQaModel(qaModel);

		List<Defect> defectAreas = dictionaryService.getDefectAreasByQaModel(qaModelId);
		for (Defect defect : defectAreas) {
			deleteDefectEntities(defect);
			defectRepository.delete(defect);
		}
		qaModelRepository.delete(qaModelId);
	}

	public QaModelDefectAreaDTO convertDefectToQaModelDefectAreaDTO(Defect defect) {
		QaModelDefectAreaDTO qaModelDefectAreaDTO = new QaModelDefectAreaDTO();
		qaModelDefectAreaDTO.setDefectAreaId(defect.getDefectId());
		qaModelDefectAreaDTO.setCode(defect.getDefectCd());
		qaModelDefectAreaDTO.setDescription(defect.getDescription());
		qaModelDefectAreaDTO.setOrder(new BigDecimal(defect.getRelativeOrder()));
		List<String> reviewTypeCodes = new ArrayList<String>();
		for (ReviewTypeDefect reviewTypeDefect : reviewTypeDefectRepository.findByIdDefectId(defect.getDefectId())) {
			ReviewTypeRef reviewTypeRef = reviewTypeRefRepository.findOne(reviewTypeDefect.getId().getReviewTypeId());
			reviewTypeCodes.add(String.valueOf(reviewTypeRef.getReviewTypeCd()));
		}
		qaModelDefectAreaDTO.setReviewTypeCodes(reviewTypeCodes);

		return qaModelDefectAreaDTO;
	}

	public QaModelDefectAreaDetailDTO convertDefectToQaModelDefectAreaDetailDTO(Defect defect) {
		QaModelDefectAreaDetailDTO qaModelDefectAreaDetailDTO = new QaModelDefectAreaDetailDTO();
		qaModelDefectAreaDetailDTO.setDefectAreaId(defect.getDefectId());
		qaModelDefectAreaDetailDTO.setCode(defect.getDefectCd());
		qaModelDefectAreaDetailDTO.setDescription(defect.getDescription());
		qaModelDefectAreaDetailDTO.setOrder(new BigDecimal(defect.getRelativeOrder()));

		// review types
		List<String> reviewTypeCodes = new ArrayList<String>();
		for (ReviewTypeDefect reviewTypeDefect : defect.getReviewTypeDefects()) {
			ReviewTypeRef reviewTypeRef = reviewTypeRefRepository.findOne(reviewTypeDefect.getId().getReviewTypeId());
			reviewTypeCodes.add(String.valueOf(reviewTypeRef.getReviewTypeCd()));
		}
		qaModelDefectAreaDetailDTO.setReviewTypeCodes(reviewTypeCodes);

		// defect area loan attributes
		List<QaModelLoanAttributeDTO> defectLoanAttributes = new ArrayList<QaModelLoanAttributeDTO>();
		for (DictMetadataFieldDefect defectField : dictMetadataFieldDefectRepository.findByDefect(defect)) {
			QaModelLoanAttributeDTO fieldDTO = new QaModelLoanAttributeDTO();
			fieldDTO.setId(defectField.getId().getEntityName() + " " + defectField.getId().getFieldName());
			fieldDTO.setEntityName(defectField.getId().getEntityName());
			fieldDTO.setFieldName(defectField.getId().getFieldName());
			fieldDTO.setOrder(new BigDecimal(defectField.getOrderNumber()));
			defectLoanAttributes.add(fieldDTO);
		}
		qaModelDefectAreaDetailDTO.setLoanAttributes(defectLoanAttributes);

		// source
		List<OrderedItemDTO> sourceDTOs = new ArrayList<OrderedItemDTO>();
		for (DefectSource defectSource : defectSourceRepository.findByDefect(defect)) {
			OrderedItemDTO sourceDTO = new OrderedItemDTO();
			sourceDTO.setId(defectSource.getDefectSourceId());
			sourceDTO.setCode(defectSource.getDefectSourceCd());
			sourceDTO.setDescription(defectSource.getDescription());
			sourceDTO.setOrder(new BigDecimal(defectSource.getRelativeOrder()));
			sourceDTOs.add(sourceDTO);
		}
		qaModelDefectAreaDetailDTO.setSources(sourceDTOs);
		// cause
		List<OrderedItemDTO> causeDTOs = new ArrayList<OrderedItemDTO>();
		for (DefectCause defectCause : defectCauseRepository.findByDefect(defect)) {
			OrderedItemDTO causeDTO = new OrderedItemDTO();
			causeDTO.setId(defectCause.getDefectCauseCd());
			causeDTO.setCode(defectCause.getDefectCauseCd());
			causeDTO.setDescription(defectCause.getDescription());
			causeDTO.setOrder(new BigDecimal(defectCause.getRelativeOrder()));
			causeDTOs.add(causeDTO);
		}
		qaModelDefectAreaDetailDTO.setCauses(causeDTOs);
		// severity
		List<QaModelDefectAreaSeverityDTO> severityDTOs = new ArrayList<QaModelDefectAreaSeverityDTO>();
		for (DefectSeverity defectSeverity : defectSeverityRepository.findByDefect(defect)) {
			QaModelDefectAreaSeverityDTO severityDTO = new QaModelDefectAreaSeverityDTO();
			severityDTO.setId(defectSeverity.getDefectSeverityId());
			severityDTO.setCode(String.valueOf(defectSeverity.getSeverityTierCd()));
			severityDTO.setDescription(defectSeverity.getDescription());
			severityDTO.setOrder(new BigDecimal(defectSeverity.getRelativeOrder()));
			severityDTO.setExamples(defectSeverity.getExampleText());
			severityDTOs.add(severityDTO);
		}
		qaModelDefectAreaDetailDTO.setSeverities(severityDTOs);
		// threshold
		List<QaModelDefectAreaThresholdDTO> thresholdDTOs = new ArrayList<QaModelDefectAreaThresholdDTO>();
		for (FindingRatingRule findingRatingRule : findingRatingRuleRepository.findByDefect(defect)) {
			QaModelDefectAreaThresholdDTO thresholdDTO = new QaModelDefectAreaThresholdDTO();
			thresholdDTO.setId(findingRatingRule.getFindingRatingRuleId());
			thresholdDTO.setCauseCode(findingRatingRule.getDefectCause().getDefectCauseCd());
			thresholdDTO.setSourceCode(findingRatingRule.getDefectSource().getDefectSourceCd());
			thresholdDTO.setSeverityCode(String.valueOf(findingRatingRule.getUnacceptableRatingThreshold()));
			thresholdDTOs.add(thresholdDTO);
		}
		qaModelDefectAreaDetailDTO.setThresholds(thresholdDTOs);
		// remedy type
		List<OrderedItemDTO> remedyTypeDTOs = new ArrayList<OrderedItemDTO>();
		for (DefectRemedyType remedyType : defectRemedyTypeRepository.findByDefect(defect)) {
			OrderedItemDTO remedyTypeDTO = new OrderedItemDTO();
			remedyTypeDTO.setId(remedyType.getDefectRemedyTypeId());
			remedyTypeDTO.setCode(remedyType.getCode());
			remedyTypeDTO.setDescription(remedyType.getDescription());
			remedyTypeDTO.setOrder(new BigDecimal(remedyType.getOrderNumber()));
			remedyTypeDTOs.add(remedyTypeDTO);
		}
		qaModelDefectAreaDetailDTO.setRemediationTypes(remedyTypeDTOs);

		// questions
		Qatree qatree = qatreeRepository.findByDefect(defect);
		if (qatree == null) { // brand new defect area
			qaModelDefectAreaDetailDTO.setEnablePreQualifyQuestion(false);
		}
		else {
			qaModelDefectAreaDetailDTO.setEnablePreQualifyQuestion(qatree.getEnablePreQualQuestionInd() == 'Y');
		}
		List<ReviewQuestionDTO> questionDTOs = new ArrayList<ReviewQuestionDTO>();
		for (QatreeQuestion question : qatreeQuestionRepository.findByDefectId(defect.getDefectId())) {
			// preQualifying Question
			if (question.getQuestionReference().getResponseType().equals(PRE_QUAL_RESPONSE_TYPE)) {
				QuestionReference preQualifyQuestionReference = question.getQuestionReference();
				QaModelDefectAreaPreQualifyDTO preQualifyDTO = new QaModelDefectAreaPreQualifyDTO();
				preQualifyDTO.setId(question.getQuestionId());
				preQualifyDTO.setDescription(preQualifyQuestionReference.getQuestionDescription());
				if ((question.getQuestionReferenceString() != null) && question.getQuestionReferenceString().contains(".")) {
					preQualifyDTO.setCode(question.getQuestionReferenceString().split("\\.")[1]);
				}
				List<OptionsItemDTO> items = new ArrayList<OptionsItemDTO>();
				for (QaModelPrequal incomeSource : qaModelPrequalRepository.findByQuestionReferenceQuestionId(question.getQuestionId())) {
					OptionsItemDTO sourceDTO = new OptionsItemDTO();
					sourceDTO.setCode(incomeSource.getId().getPreQualPotentialAnswerCd());
					sourceDTO.setDescription(incomeSource.getDescription());
					sourceDTO.setOptions(new ArrayList<String>(Arrays.asList(incomeSource.getQuestionRefIdCsv().split(","))));
					items.add(sourceDTO);
				}
				preQualifyDTO.setOptionsQuestions(items);
				qaModelDefectAreaDetailDTO.setPreQualifyQuestion(preQualifyDTO);

				List<QatreeQuestion> prequalChildQuestions = qatreeQuestionRepository.findByParentQuestionReference(question.getQuestionReference());
				if (prequalChildQuestions != null) {
					for (QatreeQuestion childQuestion : prequalChildQuestions) {
						addNestedQuestions(questionDTOs, childQuestion);
					}
				}
			} else {
				// only add root level questions here, descendant questions will be added recursively
				if (question.getParentQuestionReference() == null) {
					addNestedQuestions(questionDTOs, question);
				}
			}
		}
		qaModelDefectAreaDetailDTO.setQuestions(questionDTOs);

		return qaModelDefectAreaDetailDTO;
	}

	@Transactional
	public Defect createDefect(String qaModelId, QaModelDefectAreaDTO qaModelDefectAreaDTO) {
		QaModel qaModel = qaModelRepository.findOne(qaModelId);
		if (qaModel == null) {
			throw new NotFoundException("QA Model " + qaModelId + " is not found");
		}
		if (defectRepository.findByDefectCdAndQaModel(qaModelDefectAreaDTO.getCode(), qaModel) != null) {
			throw new ConflictException("Defect area code " + qaModelDefectAreaDTO.getCode() + " for QA model " + qaModelId + " already exists");
		}

		String userId = securityService.getUserId();
		Date now = new Date();

		// create defect
		Defect defect = new Defect();
		defect.setQaModel(qaModel);
		defect.setDefectCd(qaModelDefectAreaDTO.getCode());
		defect.setDescription(qaModelDefectAreaDTO.getDescription());
		defect.setRelativeOrder(qaModelDefectAreaDTO.getOrder().intValue());
		defect.setCreatedBy(userId);
		defect.setCreatedTs(now);
		defect.setUpdatedBy(userId);
		defect.setUpdatedTs(now);
		defect = defectRepository.save(defect);

		// create review types
		for (String reviewTypeCode : qaModelDefectAreaDTO.getReviewTypeCodes()) {
			ReviewTypeDefectId reviewTypeDefectId = new ReviewTypeDefectId();
			reviewTypeDefectId.setDefectId(defect.getDefectId());
			// this is inefficient with the query inside the loop, but it's likely only ever going to be one review type per defect
			reviewTypeDefectId.setReviewTypeId(reviewTypeRefRepository.findByReviewTypeCd(reviewTypeCode).getReviewTypeId());
			ReviewTypeDefect reviewTypeDefect = new ReviewTypeDefect();
			reviewTypeDefect.setId(reviewTypeDefectId);
			reviewTypeDefect.setCreatedBy(userId);
			reviewTypeDefect.setCreatedTs(now);
			reviewTypeDefect.setUpdatedBy(userId);
			reviewTypeDefect.setUpdatedTs(now);
			reviewTypeDefectRepository.save(reviewTypeDefect);
		}

		// Create default severities with null description and null example tier1 through tier4
		for (int tier=1; tier<=NUM_TIERS; tier++) {
			DefectSeverity defectSeverity = new DefectSeverity();
			defectSeverity.setDefect(defect);
			defectSeverity.setDefectCd(defect.getDefectCd());
			defectSeverity.setSeverityTierCd(tier);
			defectSeverity.setRelativeOrder(tier);
			defectSeverity.setCreatedBy(userId);
			defectSeverity.setCreatedTs(now);
			defectSeverity.setUpdatedBy(userId);
			defectSeverity.setUpdatedTs(now);
			defectSeverityRepository.save(defectSeverity);
		}

		return defect;
	}

	@Transactional
	public Defect updateDefect(String qaModelId, String defectId, QaModelDefectAreaDetailDTO qaModelDefectAreaDetailDTO) {
		QaModel qaModel = qaModelRepository.findOne(qaModelId);
		if (qaModel == null) {
			throw new NotFoundException("QA Model " + qaModelId + " is not found");
		}

		Defect defect = defectRepository.findOne(defectId);
		if (defect == null) {
			throw new NotFoundException("Defect area code " + defectId + " for QA model " + qaModelId + " is not found");
		}

		// delete existing defect area elements first
		deleteDefectEntities(defect);

		String userId = securityService.getUserId();
		Date now = new Date();

		// update defect area
		defect.setDefectCd(qaModelDefectAreaDetailDTO.getCode());
		defect.setDescription(qaModelDefectAreaDetailDTO.getDescription());
		defect.setRelativeOrder(qaModelDefectAreaDetailDTO.getOrder().intValue());
		defect.setUpdatedBy(userId);
		defect.setUpdatedTs(now);
		defect = defectRepository.save(defect);

		// Create review types
		for (String reviewCode : qaModelDefectAreaDetailDTO.getReviewTypeCodes()) {
			ReviewTypeDefectId tyepId = new ReviewTypeDefectId();
			tyepId.setDefectId(defectId);
			tyepId.setReviewTypeId(reviewTypeRefRepository.findByReviewTypeCd(reviewCode).getReviewTypeId());
			ReviewTypeDefect reviewTypeDefect = new ReviewTypeDefect();
			reviewTypeDefect.setId(tyepId);
			reviewTypeDefect.setCreatedBy(userId);
			reviewTypeDefect.setCreatedTs(now);
			reviewTypeDefect.setUpdatedBy(userId);
			reviewTypeDefect.setUpdatedTs(now);
			reviewTypeDefectRepository.save(reviewTypeDefect);
		}

		// defect area loan attributes
		for (QaModelLoanAttributeDTO loanAttributeDTO: qaModelDefectAreaDetailDTO.getLoanAttributes()) {
			DictMetadataFieldDefectId id = new DictMetadataFieldDefectId();
			id.setEntityName(loanAttributeDTO.getEntityName());
			id.setFieldName(loanAttributeDTO.getFieldName());
			id.setDefectId(defect.getDefectId());
			DictMetadataFieldDefect defectField = new DictMetadataFieldDefect();
			defectField.setId(id);
			defectField.setDefect(defect);
			defectField.setDefectCd(defect.getDefectCd());
			defectField.setOrderNumber(loanAttributeDTO.getOrder().intValue());
			defectField.setCreatedBy(userId);
			defectField.setCreatedTs(now);
			defectField.setUpdatedBy(userId);
			defectField.setUpdatedTs(now);
			defectField = dictMetadataFieldDefectRepository.save(defectField);
		}

		// source
		for(OrderedItemDTO dto : qaModelDefectAreaDetailDTO.getSources()) {
			DefectSource defectSource = new DefectSource();
			defectSource.setDefect(defect);
			defectSource.setDefectCd(defect.getDefectCd());
			defectSource.setDefectSourceCd(dto.getCode());
			defectSource.setDescription(dto.getDescription());
			defectSource.setRelativeOrder(dto.getOrder().intValue());
			defectSource.setCreatedBy(userId);
			defectSource.setCreatedTs(now);
			defectSource.setUpdatedBy(userId);
			defectSource.setUpdatedTs(now);
			defectSourceRepository.save(defectSource);
		}

		// cause
		for(OrderedItemDTO dto : qaModelDefectAreaDetailDTO.getCauses()) {
			DefectCause defectCause = new DefectCause();
			defectCause.setDefect(defect);
			defectCause.setDefectCd(defect.getDefectCd());
			defectCause.setDefectCauseCd(dto.getCode());
			defectCause.setDescription(dto.getDescription());
			defectCause.setRelativeOrder(dto.getOrder().intValue());
			defectCause.setCreatedBy(userId);
			defectCause.setCreatedTs(now);
			defectCause.setUpdatedBy(userId);
			defectCause.setUpdatedTs(now);
			defectCauseRepository.save(defectCause);
		}

		// severity
		for (QaModelDefectAreaSeverityDTO dto : qaModelDefectAreaDetailDTO.getSeverities()) {
			DefectSeverity defectSeverity = new DefectSeverity();
			defectSeverity.setDefect(defect);
			defectSeverity.setDefectCd(defect.getDefectCd());
			defectSeverity.setSeverityTierCd(Integer.parseInt(dto.getCode()));
			defectSeverity.setDescription(dto.getDescription());
			defectSeverity.setExampleText(dto.getExamples());
			defectSeverity.setRelativeOrder(dto.getOrder().intValue());
			defectSeverity.setCreatedBy(userId);
			defectSeverity.setCreatedTs(now);
			defectSeverity.setUpdatedBy(userId);
			defectSeverity.setUpdatedTs(now);
			defectSeverityRepository.save(defectSeverity);
		}

		// threshold
		for (QaModelDefectAreaThresholdDTO dto : qaModelDefectAreaDetailDTO.getThresholds()) {
			FindingRatingRule findingRatineRule = new FindingRatingRule();
			findingRatineRule.setDefect(defect);
			findingRatineRule.setDefectCause(defectCauseRepository.findByDefectAndDefectCauseCd(defect, dto.getCauseCode()));
			findingRatineRule.setDefectSource(defectSourceRepository.findByDefectAndDefectSourceCd(defect, dto.getSourceCode()));
			findingRatineRule.setUnacceptableRatingThreshold(Integer.parseInt(dto.getSeverityCode()));
			findingRatineRule = findingRatingRuleRepository.save(findingRatineRule);
		}

		// remedy type
		for (OrderedItemDTO dto : qaModelDefectAreaDetailDTO.getRemediationTypes()) {
			DefectRemedyType remedyType = new DefectRemedyType();
			remedyType.setDefect(defect);
			remedyType.setDescription(dto.getDescription());
			remedyType.setCode(dto.getCode());
			remedyType.setOrderNumber(dto.getOrder() != null ? dto.getOrder().intValue(): 0);
			remedyType.setCreatedBy(userId);
			remedyType.setCreatedTs(now);
			remedyType.setUpdatedBy(userId);
			remedyType.setUpdatedTs(now);
			defectRemedyTypeRepository.save(remedyType);
		}

		// questions
		Qatree qatree = new Qatree();
		qatree.setQaModel(qaModel);
		qatree.setDefect(defect);
		qatree.setEnablePreQualQuestionInd(
			(qaModelDefectAreaDetailDTO.getEnablePreQualifyQuestion() == null || !qaModelDefectAreaDetailDTO.getEnablePreQualifyQuestion()) ? 'N' : 'Y'
		);
		qatree.setCreatedBy(userId);
		qatree.setCreatedTs(now);
		qatree.setUpdatedBy(userId);
		qatree.setUpdatedTs(now);
		qatree = qatreeRepository.save(qatree);

		for (ReviewQuestionDTO reviewQuestionDTO : qaModelDefectAreaDetailDTO.getQuestions()) {
			saveQaTreeQuestion(reviewQuestionDTO, userId, qaModelDefectAreaDetailDTO, qatree, defect, null);
		}

		// create prequal if applicable
		if (qaModelDefectAreaDetailDTO.getEnablePreQualifyQuestion()) {
			QaModelDefectAreaPreQualifyDTO prequalDTO =  qaModelDefectAreaDetailDTO.getPreQualifyQuestion();

			// create prequal question reference
			QuestionReference reference = new QuestionReference();
			reference.setQuestionDescription(prequalDTO.getDescription());
			reference.setResponseType(PRE_QUAL_RESPONSE_TYPE);
			reference.setAnswerValues(PRE_QUAL_ANSWER_VALUE);
			reference.setCreatedBy(userId);
			reference.setCreatedTs(now);
			reference.setUpdatedBy(userId);
			reference.setUpdatedTs(now);
			reference = questionReferenceRepository.save(reference);

			// create prequal qatree question
			QatreeQuestion question = new QatreeQuestion();
			question.setQatree(qatree);
			question.setQuestionReference(reference);
			question.setQuestionNumber(prequalDTO.getCode());
			question.setQuestionReferenceString(prequalDTO.getCode());
			question.setCreatedBy(userId);
			question.setCreatedTs(now);
			question.setUpdatedBy(userId);
			question.setUpdatedTs(now);
			question = qatreeQuestionRepository.save(question);
			Map<String, StringJoiner> childQuestionMap = new HashMap<String, StringJoiner>();
			// create answers
			for (OptionsItemDTO dto : prequalDTO.getOptionsQuestions()) {
				QaModelPrequalId prequalId = new QaModelPrequalId();
				prequalId.setQuestionId(reference.getQuestionId());
				prequalId.setPreQualPotentialAnswerCd(dto.getCode());
				QaModelPrequal prequal = new QaModelPrequal();
				prequal.setId(prequalId);
				prequal.setDescription(dto.getDescription());
				StringJoiner sj = new StringJoiner(",");
				for (String questionReference : dto.getOptions()) {
					sj.add(questionReference);
					if (childQuestionMap.get(questionReference) != null) {
						childQuestionMap.get(questionReference).add(dto.getCode());
					}
					else {
						StringJoiner codeList = new StringJoiner(",");
						codeList.add(dto.getCode());
						childQuestionMap.put(questionReference, codeList);
					}
				}
				prequal.setQuestionRefIdCsv(sj.toString());
				qaModelPrequalRepository.save(prequal);
			}
			for (Map.Entry<String, StringJoiner> questionReferenceEntry : childQuestionMap.entrySet()) {
				QatreeQuestion childQuestion = qatreeQuestionRepository.findByQatreeDefectDefectIdAndQuestionReferenceString(defectId, questionReferenceEntry.getKey());
				if (childQuestion != null) {
					childQuestion.setParentQuestionReference(reference);
					childQuestion.setParentQuestionConditionAnswer(questionReferenceEntry.getValue().toString());
					qatreeQuestionRepository.save(childQuestion);
				}
			}
		}

		return defect;
	}

	@Transactional
	public void deleteDefect(String defectId) {
		Defect defect = defectRepository.findOne(defectId);
		if (defect == null) {
			throw new NotFoundException("Defect " + defectId + " is not found");
		}
		deleteDefectEntities(defect);
		defectRepository.delete(defect);
	}

	private void deleteDefectEntities(Defect defect) {

		// Delete review types
		reviewTypeDefectRepository.deleteByIdDefectId(defect.getDefectId());

		// delete defect area loan attributes
		dictMetadataFieldDefectRepository.deleteByDefect(defect);

		// delete QA tree questions recursively
		for (QatreeQuestion question : qatreeQuestionRepository.findByQatreeDefectDefectId(defect.getDefectId())) {
			// Only delete root question
			if (question.getParentQuestionReference() == null) {
				deleteQuestionArtifacts(question);
			}
		}

		// delete outcome
		for (QatreeOutcome outcome : qatreeOutcomeRepository.findByDefect(defect)) {
			qatreeOutcmCausesRepository.delete(outcome.getQatreeOutcomeDefectCauses());
			qatreeOutcmSourcesRepository.delete(outcome.getQatreeOutcomeDefectSources());
			qatreeOutcmSeverityRepository.delete(outcome.getQatreeOutcomeDefectSeverities());
			// qatreeOutcomeRepository.delete(outcome);
		}
		qatreeOutcmCausesRepository.deleteByDefectCauseDefect(defect);
		qatreeOutcmSourcesRepository.deleteByDefectSourceDefect(defect);
		qatreeOutcmSeverityRepository.deleteByDefectSeverityDefect(defect);
		qatreeOutcomeRepository.deleteByDefect(defect);

		// delete defect severity, defect source, defect cause, threshold, and remedy type
		findingRatingRuleRepository.deleteByDefect(defect);
		defectCauseRepository.deleteByDefect(defect);
		defectSourceRepository.deleteByDefect(defect);
		defectSeverityRepository.deleteByDefect(defect);
		defectRemedyTypeRepository.deleteByDefect(defect);

		// delete QA tree
		qatreeRepository.deleteByDefect(defect);
	}

	@Transactional
	private void saveQaTreeQuestion(
		ReviewQuestionDTO questionDTO,
		String userId,
		QaModelDefectAreaDetailDTO qaModelDefectAreaDetailDTO,
		Qatree qatree,
		Defect defect,
		QatreeQuestion parentQuestion
	) {
		// create question reference
		QuestionReference reference = new QuestionReference();
		reference.setQuestionDescription(questionDTO.getQuestionText());
		reference.setValueToTriggerAction(questionDTO.getAnswerToTriggerFinding());
		reference.setResponseType(questionDTO.getAnswerType());
		if (questionDTO.getPotentialAnswers() == null || questionDTO.getPotentialAnswers().size() == 0) {
			// Set default potential answers
			questionDTO.setPotentialAnswers(getDefaultPotentialAnswers());
		}
		// determine answer values by comparing two list/set of codes being the same
		Set<String> potentialAnswersSet = new HashSet<String>();
		for (CommonDetailDTO dto : questionDTO.getPotentialAnswers()) {
			potentialAnswersSet.add(dto.getCode());
		}
		for (LrsGenLookup genLookup : lrsGenLookupRepository.findByIdLookupEntityAndIdLookupCode("QUESTION_REFERENCE", questionDTO.getPotentialAnswers().get(0).getCode())) {
			Set<String> lrsGenLookupSet = new HashSet<String>();
			for (LrsGenLookup lrsGenLookup : lrsGenLookupRepository.findByIdLookupEntityAndIdLookupField("QUESTION_REFERENCE", genLookup.getId().getLookupField())) {
				lrsGenLookupSet.add(lrsGenLookup.getId().getLookupCode());
			}
			if (potentialAnswersSet.containsAll(lrsGenLookupSet) && lrsGenLookupSet.containsAll(potentialAnswersSet)) {
				reference.setAnswerValues(genLookup.getId().getLookupField());
				break;
			}
		}
		reference.setCreatedBy(userId);
		reference.setCreatedTs(new Date());
		reference.setUpdatedBy(userId);
		reference.setUpdatedTs(new Date());
		reference = questionReferenceRepository.save(reference);

		// create outcome
		QatreeOutcome outcome = new QatreeOutcome();
		outcome.setDefect(defect);
		outcome.setSourceFilterQuestionId(reference.getQuestionId());
		outcome.setCreatedBy(userId);
		outcome.setCreatedTs(new Date());
		outcome.setUpdatedBy(userId);
		outcome.setUpdatedTs(new Date());
		outcome = qatreeOutcomeRepository.save(outcome);

		// create possible cause
		if (questionDTO.getAllowableCauseCodes() != null) {
			for (String code : questionDTO.getAllowableCauseCodes()) {
				DefectCause defectCause = defectCauseRepository.findByDefectAndDefectCauseCd(defect, code);
				QatreeOutcomeDefectCauseId id = new QatreeOutcomeDefectCauseId();
				id.setQatreeOutcomeId(outcome.getQatreeOutcomeId());
				id.setDefectCauseId(defectCause.getDefectCauseId());
				QatreeOutcomeDefectCause cause = new QatreeOutcomeDefectCause();
				cause.setId(id);
				cause.setDefectCause(defectCause);
				cause.setCreatedBy(userId);
				cause.setCreatedTs(new Date());
				cause.setUpdatedBy(userId);
				cause.setUpdatedTs(new Date());
				qatreeOutcmCausesRepository.save(cause);
			}
		}
		// create possible source
		if (questionDTO.getAllowableSourceCodes() != null) {
			for (String code : questionDTO.getAllowableSourceCodes()) {
				DefectSource defectSource = defectSourceRepository.findByDefectAndDefectSourceCd(defect, code);
				QatreeOutcomeDefectSourceId id = new QatreeOutcomeDefectSourceId();
				id.setQatreeOutcomeId(outcome.getQatreeOutcomeId());
				id.setDefectSourceId(defectSource.getDefectSourceId());
				QatreeOutcomeDefectSource source = new QatreeOutcomeDefectSource();
				source.setId(id);
				source.setDefectSource(defectSource);
				source.setCreatedBy(userId);
				source.setCreatedTs(new Date());
				source.setUpdatedBy(userId);
				source.setUpdatedTs(new Date());
				qatreeOutcmSourcesRepository.save(source);
			}
		}
		// create possible severity
		if (questionDTO.getAllowedSeverityCodes() != null) {
			for (String severityCode : questionDTO.getAllowedSeverityCodes()) {
				DefectSeverity defectSeverity = defectSeverityRepository.findByDefectAndSeverityTierCd(defect, Integer.parseInt(severityCode));
				QatreeOutcomeDefectSeverityId id = new QatreeOutcomeDefectSeverityId();
				id.setQatreeOutcomeId(outcome.getQatreeOutcomeId());
				id.setDefectSeverityId(defectSeverity.getDefectSeverityId());
				QatreeOutcomeDefectSeverity severity = new QatreeOutcomeDefectSeverity();
				severity.setId(id);
				severity.setDefectSeverity(defectSeverity);
				severity.setCreatedBy(userId);
				severity.setCreatedTs(new Date());
				severity.setUpdatedBy(userId);
				severity.setUpdatedTs(new Date());
				qatreeOutcmSeverityRepository.save(severity);
			}
		}

		// create qatree question
		QatreeQuestion question = new QatreeQuestion();
		question.setQuestionReference(reference);
		question.setQatree(qatree);
		question.setQatreeOutcome(outcome);
		question.setQuestionNumber(questionDTO.getQuestionNumber());
		if (questionDTO.getQuestionReference() == null) {
			question.setQuestionReferenceString(questionDTO.getQuestionNumber() + "." + defect.getDefectCd());
		}
		else {
			question.setQuestionReferenceString(questionDTO.getQuestionReference());
		}
		question.setOrderNumber(questionDTO.getOrder());
		if (parentQuestion != null) {
			question.setParentQuestionReference(parentQuestion.getQuestionReference());
		}
		if (questionDTO.getParentQuestionConditionAnswers() != null) {
			StringJoiner sj = new StringJoiner(",");
			for (String option : questionDTO.getParentQuestionConditionAnswers()) {
				sj.add(option);
			}
			question.setParentQuestionConditionAnswer(sj.toString());
		}
		question.setCreatedBy(userId);
		question.setCreatedTs(new Date());
		question.setUpdatedBy(userId);
		question.setUpdatedTs(new Date());
		question = qatreeQuestionRepository.save(question);

		if (questionDTO.getConditionsToDisplay() != null) {
			for (ReviewConditionDTO conditionDTO : questionDTO.getConditionsToDisplay()) {
				QatreeQstnCondition condition = new QatreeQstnCondition();
				condition.setQatreeQuestion(question);
				condition.setQatree(qatree);
				condition.setConditionOperator(conditionDTO.getOperator());
				StringJoiner strJoiner = new StringJoiner(", ");
				for (String option : conditionDTO.getComparisonValues()) {
					strJoiner.add(option);
				}
				condition.setComparisonValues(strJoiner.toString());
				condition.setFieldName(conditionDTO.getFieldName());
				condition.setEntityName(conditionDTO.getEntityName());
				condition.setCreatedBy(userId);
				condition.setCreatedTs(new Date());
				condition.setUpdatedBy(userId);
				condition.setUpdatedTs(new Date());
				qatreeQstnConditionRepository.save(condition);
			}
		}

		// create child questions recursively
		if (questionDTO.getQuestions() != null) {
			for (ReviewQuestionDTO childQuestionDTO : questionDTO.getQuestions()) {
				saveQaTreeQuestion(childQuestionDTO, userId, qaModelDefectAreaDetailDTO, qatree, defect, question);
			}
		}
	}

	private void deleteQuestionArtifacts(QatreeQuestion question) {
		// delete child questions if any
		List<QatreeQuestion> childQuestions = qatreeQuestionRepository.findByParentQuestionReference(question.getQuestionReference());
		if (childQuestions != null) {
			for (QatreeQuestion childQuestion : childQuestions) {
				deleteQuestionArtifacts(childQuestion);
			}
		}

		qatreeQstnConditionRepository.delete(question.getQatreeQstnConditions());
		QuestionReference questionReference = question.getQuestionReference();
		// preQualify question
		if (questionReference.getQaModelPrequals() != null) {
			qaModelPrequalRepository.delete(questionReference.getQaModelPrequals());
		}
		qatreeQuestionRepository.delete(question);
		questionReferenceRepository.delete(questionReference);
	}

	private void addNestedQuestions(List<ReviewQuestionDTO> questionDTOs, QatreeQuestion qatreeQuestion) {
		ReviewQuestionDTO questionDTO = convertQatreeQuestionToReviewQuestionDTO(qatreeQuestion);
		List<QatreeQuestion> qatreeQuestions = qatreeQuestionRepository.findByParentQuestionReference(qatreeQuestion.getQuestionReference());
		if (qatreeQuestions != null) {
			List<ReviewQuestionDTO> childQuestionDTOs = new ArrayList<ReviewQuestionDTO>();
			for (QatreeQuestion childQuestion : qatreeQuestions) {
				addNestedQuestions(childQuestionDTOs, childQuestion);
			}
			questionDTO.setQuestions(childQuestionDTOs);
		}
		questionDTOs.add(questionDTO);
	}

	public QaModelDetailDTO convertQaModelToQaModelDetailDTO(QaModel qaModel) {
		QaModelDetailDTO dto = new QaModelDetailDTO();
		dto.setQaModelId(qaModel.getQaModelId());
		dto.setName(qaModel.getModelName());
		dto.setDescription(qaModel.getDescription());
		dto.setVersion(qaModel.getVersionNum());
		dto.setCreatedDate(DateUtils.convertDateToNoonUtcDate(qaModel.getCreatedTs()));
		dto.setModifiedDate(DateUtils.convertDateToNoonUtcDate(qaModel.getUpdatedTs()));
		Personnel modifier = personnelRepository.findByLoginCredential(qaModel.getUpdatedBy());
		if (modifier != null) {
			dto.setModifiedBy(modifier.getFirstName() +
				(StringUtils.isNotBlank(modifier.getMiddleName()) ? " " + modifier.getMiddleName() + " " : " ") +
				modifier.getLastName());
		}
		int activeReviews = reviewRepository.findActiveReviewCountByQaModelId(qaModel.getQaModelId());
		int completedReviews = reviewRepository.findCompletedReviewCountByQaModelId(qaModel.getQaModelId());
		dto.setActiveReviews(activeReviews);
		dto.setCompletedReviews(completedReviews);
		if (activeReviews > 0 || completedReviews > 0) {
			dto.setIsReadonly(true);
		}
		dto.setActivatedDate(DateUtils.convertDateToNoonUtcDate(qaModel.getActivationDate()));
		if (qaModel.getQaModelStatusRef().getCode().equals(QaModelStatusCodes.ACTIVE)) {
			dto.setIsActive(true);
		} else {
			dto.setIsActive(false);
		}
		dto.setStatusCode(qaModel.getQaModelStatusRef().getCode());
		dto.setStatus(qaModel.getQaModelStatusRef().getDescription());

		// get Defect area
		List<QaModelDefectAreaDTO> defectAreas = new ArrayList<QaModelDefectAreaDTO>();
		for (Defect defect : dictionaryService.getDefectAreasByQaModel(qaModel.getQaModelId())) {
			QaModelDefectAreaDTO defectAreaDTO = new QaModelDefectAreaDTO();
			defectAreaDTO.setDefectAreaId(defect.getDefectId());
			defectAreaDTO.setCode(defect.getDefectCd());
			defectAreaDTO.setDescription(defect.getDescription());
			defectAreaDTO.setOrder(new BigDecimal(defect.getRelativeOrder()));
			List<String> reviewTypeCodes = new ArrayList<String>();
			for (ReviewTypeDefect reviewTypeDefect : reviewTypeDefectRepository.findByIdDefectId(defect.getDefectId())) {
				ReviewTypeRef reviewTypeRef = reviewTypeRefRepository.findOne(reviewTypeDefect.getId().getReviewTypeId());
				reviewTypeCodes.add(String.valueOf(reviewTypeRef.getReviewTypeCd()));
			}
			defectAreaDTO.setReviewTypeCodes(reviewTypeCodes);
			defectAreas.add(defectAreaDTO);
		}
		dto.setDefectAreas(defectAreas);

		// Retrieve QA Model loan attributes
		List<QaModelCategoryDTO> loanAttributesCategories = new ArrayList<QaModelCategoryDTO>();
		for (DictMetadataFieldSubjectArea subjectArea : dictMetadataFieldSubjectAreaRepository.findByQaModel(qaModel)) {
			QaModelCategoryDTO categoryDTO = new QaModelCategoryDTO();
			categoryDTO.setName(subjectArea.getSubjectArea());
			categoryDTO.setOrder(new BigDecimal(subjectArea.getDisplayOrder()));

			List<QaModelLoanAttributeDTO> loanAttributesDTOs = new ArrayList<QaModelLoanAttributeDTO>();
			for (DictMetadataFieldModel modelField : dictMetadataFieldModelRepository.findByQaModelAndDictMetadataFieldSubjectArea(qaModel, subjectArea)) {
				QaModelLoanAttributeDTO fieldDTO = new QaModelLoanAttributeDTO();
				fieldDTO.setEntityName(modelField.getId().getEntityName());
				fieldDTO.setFieldName(modelField.getId().getFieldName());
				if (modelField.getOrderNumber() != null ) {
					fieldDTO.setOrder(new BigDecimal(modelField.getOrderNumber()));
				}
				loanAttributesDTOs.add(fieldDTO);
			}
			categoryDTO.setLoanAttributes(loanAttributesDTOs);
			loanAttributesCategories.add(categoryDTO);
		}
		dto.setCategories(loanAttributesCategories);

		return dto;
	}

	public List<ReviewQuestionDTO> getQatreeQuestions(String qatreeId) {
		Qatree qatree = qatreeRepository.findOne(qatreeId);

		// for each question we need to return the possible answers, conditions
		// to display the question, and the allowable outcomes (sources, causes,
		// and severities)
		List<ReviewQuestionDTO> reviewQuestionDTOs = new ArrayList<ReviewQuestionDTO>();
		for (QatreeQuestion qatreeQuestion : qatree.getQatreeQuestions()) {
			reviewQuestionDTOs.add(convertQatreeQuestionToReviewQuestionDTO(qatreeQuestion));
		}
		// sort questions by order
		Collections.sort(reviewQuestionDTOs, (rq1, rq2) -> ((rq1.getOrder() != null) ? rq1.getOrder() : 999999) - ((rq2.getOrder() != null) ? rq2.getOrder() : 999999));
		return reviewQuestionDTOs;
	}

	public ReviewQuestionDTO convertQatreeQuestionToReviewQuestionDTO(QatreeQuestion qatreeQuestion) {

		List<LrsGenLookup> potentialAnswers = lrsGenLookupRepository.findByIdLookupField(qatreeQuestion.getQuestionReference().getAnswerValues());

		ReviewQuestionDTO reviewQuestionDTO = new ReviewQuestionDTO();
		reviewQuestionDTO.setQuestionId(qatreeQuestion.getQuestionId());
		if (qatreeQuestion.getQuestionNumber() != null && !qatreeQuestion.getQuestionNumber().equals("")) {
			reviewQuestionDTO.setQuestionNumber(qatreeQuestion.getQuestionNumber());
		}
		reviewQuestionDTO.setQuestionReference(qatreeQuestion.getQuestionReferenceString());
		reviewQuestionDTO.setOrder(qatreeQuestion.getOrderNumber());
		if (qatreeQuestion.getParentQuestionReference() != null) {
			reviewQuestionDTO.setParentQuestionId(qatreeQuestion.getParentQuestionReference().getQuestionId());
		}
		reviewQuestionDTO.setQuestionText(qatreeQuestion.getQuestionReference().getQuestionDescription());
		reviewQuestionDTO.setAnswerToTriggerFinding(qatreeQuestion.getQuestionReference().getValueToTriggerAction());
		reviewQuestionDTO.setAnswerType(qatreeQuestion.getQuestionReference().getResponseType());

		List<ReviewConditionDTO> reviewConditionDTOs = new ArrayList<ReviewConditionDTO>();
		for (QatreeQstnCondition qatreeQstnCondition : qatreeQstnConditionRepository.findByQatreeQuestionQuestionId(qatreeQuestion.getQuestionId())) {
			ReviewConditionDTO reviewConditionDTO = new ReviewConditionDTO();
			reviewConditionDTO.setConditionId(qatreeQstnCondition.getQatreeQstnConditionId());
			reviewConditionDTO.setAttributeId(qatreeQstnCondition.getEntityName() + " " + qatreeQstnCondition.getFieldName());
			reviewConditionDTO.setEntityName(qatreeQstnCondition.getEntityName());
			reviewConditionDTO.setFieldName(qatreeQstnCondition.getFieldName());
			reviewConditionDTO.setOperator(qatreeQstnCondition.getConditionOperator());
			reviewConditionDTO.setComparisonValues(Arrays.asList(qatreeQstnCondition.getComparisonValues().replaceAll("\\s+", "").split(",")));
			reviewConditionDTOs.add(reviewConditionDTO);
		}
		reviewQuestionDTO.setConditionsToDisplay(reviewConditionDTOs);

		List<CommonDetailDTO> answerDTOs = new ArrayList<CommonDetailDTO>();
		if (!qatreeQuestion.getQuestionReference().getQaModelPrequals().isEmpty()) {
			// Prequal question. Get potential answer from QA_MODEL_PREQUAL table
			for (QaModelPrequal qaModelPrequal : qatreeQuestion.getQuestionReference().getQaModelPrequals()) {
				CommonDetailDTO answerDTO = new CommonDetailDTO();
				answerDTO.setCode(qaModelPrequal.getId().getPreQualPotentialAnswerCd());
				answerDTO.setDescription(qaModelPrequal.getDescription());
				answerDTOs.add(answerDTO);
			}
		} else if (potentialAnswers != null) {
			for (LrsGenLookup answer : potentialAnswers) {
				CommonDetailDTO answerDTO = new CommonDetailDTO();
				answerDTO.setCode(answer.getId().getLookupCode());
				answerDTO.setDescription(answer.getLookupDescription());
				answerDTOs.add(answerDTO);
			}
		}
		reviewQuestionDTO.setPotentialAnswers(answerDTOs);

		QatreeOutcome qatreeOutcome = qatreeQuestion.getQatreeOutcome();
		if (qatreeOutcome != null) {
			List<String> allowableCauseCodes = new ArrayList<String>();
			for (QatreeOutcomeDefectCause qatreeOutcomeDefectCause : qatreeOutcmCausesRepository.findByIdQatreeOutcomeId(qatreeOutcome.getQatreeOutcomeId())) {
				allowableCauseCodes.add(qatreeOutcomeDefectCause.getDefectCause().getDefectCauseCd());
			}
			reviewQuestionDTO.setAllowableCauseCodes(allowableCauseCodes);
			List<String> sources = new ArrayList<String>();
			for (QatreeOutcomeDefectSource qatreeOutcomeDefectSource : qatreeOutcmSourcesRepository.findByIdQatreeOutcomeId(qatreeOutcome.getQatreeOutcomeId())) {
				sources.add(qatreeOutcomeDefectSource.getDefectSource().getDefectSourceCd());
			}
			reviewQuestionDTO.setAllowableSourceCodes(sources);
			List<String> severities = new ArrayList<String>();
			for (QatreeOutcomeDefectSeverity qatreeOutcomeDefectSeverity : qatreeOutcmSeverityRepository.findByIdQatreeOutcomeId(qatreeOutcome.getQatreeOutcomeId())) {
				severities.add(String.valueOf(qatreeOutcomeDefectSeverity.getDefectSeverity().getSeverityTierCd()));
			}
			reviewQuestionDTO.setAllowedSeverityCodes(severities);
		}

		if (qatreeQuestion.getParentQuestionConditionAnswer() != null) {
			reviewQuestionDTO.setParentQuestionConditionAnswers(Arrays.asList(qatreeQuestion.getParentQuestionConditionAnswer().split(",")));
		}

		return reviewQuestionDTO;
	}

	public List<ReviewAnswerDTO> getQatreeAnswers(String reviewId, String reviewLevelId, String qatreeId) {
		List<ReviewAnswerDTO> reviewAnswerDTOs = new ArrayList<ReviewAnswerDTO>();

		// find answers for question-answer tree at review level
		List<Answer> answers = answerRepository.findByReviewLevelReviewLevelIdAndQatreeQuestionQatreeQatreeId(reviewLevelId, qatreeId);
		for (Answer answer : answers) {
			// convert answer to answer list to accommodate multi-select
			List<String> answerStrings = Arrays.asList(answer.getAnswer().replaceAll("\\s+", "").split(","));

			// find answer descriptions for answers
			QatreeQuestion question = qatreeQuestionRepository.findOne(answer.getQatreeQuestion().getQuestionId());

			// Prequal question. Get answer from QA_MODEL_PREQUAL table
			ReviewAnswerDTO reviewAnswerDTO;
			if (!question.getQuestionReference().getQaModelPrequals().isEmpty()) {
				reviewAnswerDTO = convertAnswerToReviewAnswerDTO(answer, null);
				List<CommonDetailDTO> answerDTOs = new ArrayList<CommonDetailDTO>();
				for (String answerString : answerStrings) {
					if ((answerString != null) && !answerString.equals("")) {
						QaModelPrequalId id = new QaModelPrequalId();
						id.setPreQualPotentialAnswerCd(answerString);
						id.setQuestionId(question.getQuestionId());
						QaModelPrequal qaModelPrequal = qaModelPrequalRepository.findOne(id);

						CommonDetailDTO answerDTO = new CommonDetailDTO();
						answerDTO.setCode(answerString);
						answerDTO.setDescription(qaModelPrequal.getDescription());
						answerDTOs.add(answerDTO);
					}
				}
				reviewAnswerDTO.setAnswers(answerDTOs);

			} else {
				List<LrsGenLookup> answerLookups = lrsGenLookupRepository.findByIdLookupFieldAndIdLookupCodeIn(question.getQuestionReference().getAnswerValues(), answerStrings);
				reviewAnswerDTO = convertAnswerToReviewAnswerDTO(answer, answerLookups);
			}

			reviewAnswerDTOs.add(reviewAnswerDTO);
		}

		return reviewAnswerDTOs;
	}

	@Transactional
	public void deleteQatreeAnswer(String answerId) {
		if (answerRepository.exists(answerId)) {
			answerRepository.delete(answerId);
		}
	}

	@Transactional
	public ReviewAnswerDTO createQatreeAnswer(ReviewAnswerDTO reviewAnswerDTO, String reviewLevelId, String qatreeId) {
		List<String> userAnswers = new ArrayList<String>();
		for (CommonDetailDTO answerDTO : reviewAnswerDTO.getAnswers()) {
			userAnswers.add(answerDTO.getCode());
		}

		ReviewLevel reviewLevel = reviewLevelRepository.findOne(reviewLevelId);
		commonReviewService.markReviewLevelInProgress(reviewLevel);

		Answer answer = new Answer();
		answer.setReviewLevel(reviewLevel);
		answer.setQatreeQuestion(qatreeQuestionRepository.findOne(reviewAnswerDTO.getQuestionId()));
		answer.setAnswer(StringUtils.join(userAnswers, ','));	// save answers as csv
		String username = securityService.getUserId();
		answer.setCreatedBy(username);
		answer.setUpdatedBy(username);
		Date now = new Date();
		answer.setCreatedTs(now);
		answer.setUpdatedTs(now);
		answer = answerRepository.save(answer);

		ReviewAnswerDTO savedReviewAnswerDTO = convertAnswerToReviewAnswerDTO(answer, null);
		savedReviewAnswerDTO.setAnswers(reviewAnswerDTO.getAnswers());

		return savedReviewAnswerDTO;
	}

	@Transactional
	public ReviewAnswerDTO updateQatreeAnswer(ReviewAnswerDTO reviewAnswerDTO, String reviewLevelId, String qaTreeId, String answerId) {
		ReviewLevel reviewLevel = reviewLevelRepository.findOne(reviewLevelId);
		commonReviewService.markReviewLevelInProgress(reviewLevel);

		List<String> userAnswers = new ArrayList<String>();
		for (CommonDetailDTO answerDetail : reviewAnswerDTO.getAnswers()) {
			userAnswers.add(answerDetail.getCode());
		}

		// update existing answer fields
		Answer answer = answerRepository.findOne(answerId);
		answer.setAnswer(StringUtils.join(userAnswers, ','));	// save answers as single comma separated value
		answer.setUpdatedBy(securityService.getUserId());
		answer.setUpdatedTs(new Date());

		ReviewAnswerDTO updatedReviewAnswerDTO = convertAnswerToReviewAnswerDTO(answer, null);
		updatedReviewAnswerDTO.setAnswers(reviewAnswerDTO.getAnswers());

		return updatedReviewAnswerDTO;
	}

	public ReviewAnswerDTO convertAnswerToReviewAnswerDTO(Answer answer, List<LrsGenLookup> answerLookups) {
		ReviewAnswerDTO reviewAnswerDTO = new ReviewAnswerDTO();
		reviewAnswerDTO.setAnswerId(answer.getAnswerId());
		reviewAnswerDTO.setQaTreeId(answer.getQatreeQuestion().getQatree().getQatreeId());
		reviewAnswerDTO.setQuestionId(answer.getQatreeQuestion().getQuestionId());
		reviewAnswerDTO.setReviewLevelId(answer.getReviewLevel().getReviewLevelId());
		if (answerLookups != null) {
			List<CommonDetailDTO> answerDTOs = new ArrayList<CommonDetailDTO>();
			for (LrsGenLookup answerLookup : answerLookups) {
				CommonDetailDTO answerDTO = new CommonDetailDTO();
				answerDTO.setCode(answerLookup.getId().getLookupCode());
				answerDTO.setDescription(answerLookup.getLookupDescription());
				answerDTOs.add(answerDTO);
			}
			reviewAnswerDTO.setAnswers(answerDTOs);
		}
		return reviewAnswerDTO;
	}

	private List<CommonDetailDTO> getDefaultPotentialAnswers() {
		List<CommonDetailDTO> list = new ArrayList<CommonDetailDTO>();
		CommonDetailDTO noAnsw = new CommonDetailDTO();
		noAnsw.setCode(NO);
		noAnsw.setDescription(NO);

		CommonDetailDTO yesAnsw = new CommonDetailDTO();
		yesAnsw.setCode(YES);
		yesAnsw.setDescription(YES);

		list.add(noAnsw);
		list.add(yesAnsw);

		return list;
	}

}