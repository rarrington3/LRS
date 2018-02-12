package gov.hud.lrs.services.bizservice;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import gov.hud.lrs.common.entity.ConsolidatedSelectionReason;
import gov.hud.lrs.common.entity.Defect;
import gov.hud.lrs.common.entity.DefectCause;
import gov.hud.lrs.common.entity.DefectRemedyType;
import gov.hud.lrs.common.entity.DefectSeverity;
import gov.hud.lrs.common.entity.DefectSource;
import gov.hud.lrs.common.entity.FileDeliveryLocationRef;
import gov.hud.lrs.common.entity.FraudParticipantRef;
import gov.hud.lrs.common.entity.FraudTypeRef;
import gov.hud.lrs.common.entity.ProductTypeRef;
import gov.hud.lrs.common.entity.QaModel;
import gov.hud.lrs.common.entity.Qatree;
import gov.hud.lrs.common.entity.RatingRef;
import gov.hud.lrs.common.entity.ReviewLevelTypeRef;
import gov.hud.lrs.common.entity.ReviewProcessExceptionTypeRef;
import gov.hud.lrs.common.entity.ReviewTypeRef;
import gov.hud.lrs.common.entity.ScoringFactor;
import gov.hud.lrs.common.entity.SelectionReason;
import gov.hud.lrs.common.entity.SelectionSubReasonRef;
import gov.hud.lrs.common.enumeration.ReviewLevelTypeCodes;
import gov.hud.lrs.common.entity.LoanTypeRef;
import gov.hud.lrs.common.repository.ConsolidatedSelectionReasonRepository;
import gov.hud.lrs.common.repository.DefectCauseRepository;
import gov.hud.lrs.common.repository.DefectRemedyTypeRepository;
import gov.hud.lrs.common.repository.DefectRepository;
import gov.hud.lrs.common.repository.DefectSeverityRepository;
import gov.hud.lrs.common.repository.DefectSourceRepository;
import gov.hud.lrs.common.repository.FileDeliveryLocationRefRepository;
import gov.hud.lrs.common.repository.FraudParticipantRefRepository;
import gov.hud.lrs.common.repository.FraudTypeRefRepository;
import gov.hud.lrs.common.repository.ProductTypeRefRepository;
import gov.hud.lrs.common.repository.QaModelRepository;
import gov.hud.lrs.common.repository.QatreeRepository;
import gov.hud.lrs.common.repository.RatingRefRepository;
import gov.hud.lrs.common.repository.ReviewLevelTypeRefRepository;
import gov.hud.lrs.common.repository.ReviewProcessExceptionTypeRefRepository;
import gov.hud.lrs.common.repository.ReviewTypeRefRepository;
import gov.hud.lrs.common.repository.ScoringFactorRepository;
import gov.hud.lrs.common.repository.SelectionReasonRepository;
import gov.hud.lrs.common.repository.SelectionSubReasonRefRepository;
import gov.hud.lrs.common.repository.LoanTypeRefRepository;

@Service
public class DictionaryService {

	@Autowired private DefectRepository defectRepository;
	@Autowired private DefectSourceRepository defectSourceRepository;
	@Autowired private DefectCauseRepository defectCauseRepository;
	@Autowired private DefectSeverityRepository defectSeverityRepository;
	@Autowired private SelectionReasonRepository selectionReasonRepository;
	@Autowired private ReviewTypeRefRepository reviewTypeRefRepository;
	@Autowired private ProductTypeRefRepository productTypeRefRepository;
	@Autowired private ReviewLevelTypeRefRepository reviewLevelTypeRefRepository;
	@Autowired private RatingRefRepository ratingRefRepository;
	@Autowired private FraudTypeRefRepository fraudTypeRefRepository;
	@Autowired private FraudParticipantRefRepository fraudParticipantRefRepository;
	@Autowired private DefectRemedyTypeRepository defectRemedyTypeRepository;
	@Autowired private QatreeRepository qatreeRepository;
	@Autowired private SelectionSubReasonRefRepository selectionSubReasonRefRepository;
	@Autowired private FileDeliveryLocationRefRepository fileDeliveryLocationRefRepository;
	@Autowired private ReviewProcessExceptionTypeRefRepository reviewProcessExceptionTypeRefRepository;
	@Autowired private QaModelRepository qaModelRepository;
	@Autowired private LoanTypeRefRepository loanTypeRefRepository;
	@Autowired private ScoringFactorRepository scoringFactorRepository;
	@Autowired private ConsolidatedSelectionReasonRepository consolidatedSelectionReasonRepository;

	public List<Defect> getDefectAreasFromActiveQaModel() {
		QaModel activeModel = qaModelRepository.findActive();
		return defectRepository.findByQaModelQaModelId(activeModel.getQaModelId());
	}

	public List<Defect> getDefectAreasByQaModel(String qaModelId) {
		return defectRepository.findByQaModelQaModelId(qaModelId);
	}

	public List<DefectCause> getDefectCausesByQaModel(String qaModelId) {
		return defectCauseRepository.findByDefectQaModelQaModelId(qaModelId);
	}

	public List<DefectSource> getDefectSourcesByQaModel(String qaModelId) {
		return defectSourceRepository.findByDefectQaModelQaModelId(qaModelId);
	}

	public List<DefectSeverity> getDefectSeveritiesByQaModel(String qaModelId) {
		return defectSeverityRepository.findByDefectQaModelQaModelId(qaModelId);
	}

	public List<DefectRemedyType> getDefectRemedyTypeByQaTreeId(String qaTreeId) {
		Qatree qatree = qatreeRepository.findOne(qaTreeId);
		return defectRemedyTypeRepository.findByDefect(qatree.getDefect());
	}

	public List<LoanTypeRef> getAllLoanTypes() {
		return Lists.newArrayList(loanTypeRefRepository.findAll());
	}

	public List<ProductTypeRef> getAllProductTypes() {
		return Lists.newArrayList(productTypeRefRepository.findAll());
	}

	public List<ReviewTypeRef> getAllReviewTypes() {
		return Lists.newArrayList(reviewTypeRefRepository.findAll());
	}

	public List<SelectionReason> getAllSelectionReasons() {
		return Lists.newArrayList(selectionReasonRepository.findAll());
	}

	public List<ConsolidatedSelectionReason> getAllConsolidatedSelectionReasons() {
		return Lists.newArrayList(consolidatedSelectionReasonRepository.findAll());
	}

	public List<SelectionSubReasonRef> getAllSelectionSubReasons() {
		return selectionSubReasonRefRepository.findAll();
	}

	public List<ReviewLevelTypeRef> getAllReviewLevelTypes() {
		return Lists.newArrayList(reviewLevelTypeRefRepository.findAll());
	}

	public List<FileDeliveryLocationRef> getAllFileDeliveryLocations() {
		return fileDeliveryLocationRefRepository.findAll();
	}

	public List<ReviewProcessExceptionTypeRef> getAllExceptionTypes() {
		return reviewProcessExceptionTypeRefRepository.findAll();
	}

	public List<RatingRef> getAllRatings() {
		return Lists.newArrayList(ratingRefRepository.findAll());
	}

	public List<FraudTypeRef> getAllFraudTypes() {
		return Lists.newArrayList(fraudTypeRefRepository.findAll());
	}

	public List<FraudParticipantRef> getAllFraudParticipants() {
		return Lists.newArrayList(fraudParticipantRefRepository.findAll());
	}

	public List<LoanTypeRef> getAllLenderLoanTypes() {
		return Lists.newArrayList(loanTypeRefRepository.findAll());
	}

	public List<ScoringFactor> getAllScoringFactors() {
		return Lists.newArrayList(scoringFactorRepository.findAll());
	}

	public List<ReviewLevelTypeRef> getStaffManagementReviewLevels() {
		List<String> excludedReviewLevelCodes = new ArrayList<String>();
		excludedReviewLevelCodes.add(ReviewLevelTypeCodes.BINDER_REQUEST);
		excludedReviewLevelCodes.add(ReviewLevelTypeCodes.MITIGATION);
		return Lists.newArrayList(reviewLevelTypeRefRepository.findByReviewLevelCdNotIn(excludedReviewLevelCodes));
	}

}