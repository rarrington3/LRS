package gov.hud.lrs.workflow.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;

import gov.hud.lrs.common.entity.Batch;
import gov.hud.lrs.common.entity.Personnel;
import gov.hud.lrs.common.entity.Review;
import gov.hud.lrs.common.entity.ReviewLevel;
import gov.hud.lrs.common.entity.ReviewLocation;
import gov.hud.lrs.common.entity.ScoringModelVersion;
import gov.hud.lrs.common.entity.ScoringModelVersionFactor;
import gov.hud.lrs.common.enumeration.ScoringModelTypeCodes;
import gov.hud.lrs.common.repository.PersonnelAssignmentTypeRepository;
import gov.hud.lrs.common.repository.PersonnelAssignmentTypeRepositoryCustom.PersonnelIdCode;
import gov.hud.lrs.common.repository.PersonnelRepository;
import gov.hud.lrs.common.repository.ReviewRepository;
import gov.hud.lrs.common.repository.ReviewRepositoryCustom.PersonnelIdReviewCount;
import gov.hud.lrs.common.repository.ScoringModelVersionRepository;
import gov.hud.lrs.common.util.Util;

@Service
public class AssignmentService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final String JOB_LOG_FILE_FORMAT = "yyyy.MM.dd-HH.mm.ss";

	@Autowired private PersonnelRepository personnelRepository;
	@Autowired private ReviewRepository reviewRepository;
	@Autowired private ScoringModelVersionRepository scoringModelVersionRepository;
	@Autowired private PersonnelAssignmentTypeRepository personnelAssignmentTypeRepository;
	@Autowired private RulesService rulesService;

	// all runAssignmentModel methods return a sorted list of personnel/scores (highest score first) of all personnel with passing scores
	public List<Personnel> runAssignmentModelForCurrentReviewLocation(ReviewLevel reviewLevel) {
		return runAssignmentModel(reviewLevel, null, null);
	}

	public List<Personnel> runAssignmentModelForReviewLocation(ReviewLevel reviewLevel, String reviewLocationId) {
		List<Personnel> personnelList = personnelRepository.findActiveAvailableByReviewLocationId(reviewLocationId);
		return runAssignmentModel(reviewLevel, personnelList, null, true);
	}

	public List<Personnel> runAssignmentModelForAllActivePersonnel(ReviewLevel reviewLevel) {
		List<Personnel> personnelList = personnelRepository.findActive();
		return runAssignmentModel(reviewLevel, personnelList, null, true);
	}

	public boolean runAssignmentModelForPersonnel(ReviewLevel reviewLevel, Personnel personnel) {
		List<Personnel> personnelList = new ArrayList<Personnel>(1);
		personnelList.add(personnel);
		List<Personnel> assignmentScores = runAssignmentModel(reviewLevel, personnelList, (List<Personnel>)null);
		return !assignmentScores.isEmpty();
	}

	public List<Personnel> runAssignmentModel(ReviewLevel reviewLevel, List<Personnel> personnelList, List<Personnel> excludePersonnelList) {
		return runAssignmentModel(reviewLevel, personnelList, excludePersonnelList, false);
	}
	public List<Personnel> runAssignmentModel(ReviewLevel reviewLevel, List<Personnel> personnelList, List<Personnel> excludePersonnelList, boolean includeNonBatchPersonnels) {
		MDC.put("logFileName", "assignment-" + new SimpleDateFormat(JOB_LOG_FILE_FORMAT).format(new Date()) + "-" + reviewLevel.getReviewLevelId());

		Review review = reviewLevel.getReview();
		ReviewLocation reviewLocation = reviewLevel.getReviewLocation();

		logger.debug("Running assignment model for ReviewLevel " + reviewLevel.getReviewLevelId() + " (Case: " + review.getCaseNumber() + ")");

		List<ReviewLevel> sortedReviewLevels = new ArrayList<ReviewLevel>(review.getReviewLevels());
		sortedReviewLevels.sort((rl1, rl2) -> rl1.getCreatedTs().compareTo(rl2.getCreatedTs()));	// sort by created date
		int reviewLevelIndex = 0;
		for (; reviewLevelIndex < sortedReviewLevels.size(); reviewLevelIndex++) {
			if (sortedReviewLevels.get(reviewLevelIndex).getReviewLevelId().equals(reviewLevel.getReviewLevelId())) {
				break;
			}
		}
		List<ReviewLevel> previousReviewLevels = new ArrayList<ReviewLevel>();
		for (ReviewLevel allReviewLevel : sortedReviewLevels) {
			if (!(allReviewLevel.getReviewLevelId().equals(reviewLevel.getReviewLevelId())) && allReviewLevel.getVettingInd() != 'Y') {
				previousReviewLevels.add(allReviewLevel);
			}
		}
		
		List<ReviewLevel> originalReviewReviewLevels = new ArrayList<ReviewLevel>();
		Review qcReview = review.getQcReview();
		if (qcReview != null) {
			originalReviewReviewLevels = new ArrayList<ReviewLevel>(qcReview.getReviewLevels());
		}

		if (personnelList == null) {
			personnelList = personnelRepository.findActiveAvailableByReviewLocationId(reviewLocation.getReviewLocationId());
		}

		// Remove the exclude personnel list from the personnel list.
		if (excludePersonnelList != null) {
			for (Personnel excludedPersonnel : excludePersonnelList) {
				int i = 0;
				while (i < personnelList.size()) {
					Personnel personnel = personnelList.get(i);
					if (personnel.getPersonnelId().equals(excludedPersonnel.getPersonnelId())) {
						personnelList.remove(i);
						// don't increment "i" since we've removed the element so the remaining elements have slid down
					} else {
						i++;
					}
				}
			}
		}

		Map<String, Personnel> personnelByPersonnelId = Maps.uniqueIndex(personnelList, personnel -> personnel.getPersonnelId());
		List<String> personnelIds = Util.map(personnelList, p -> p.getPersonnelId());
		if (personnelIds.isEmpty()) {
			logger.debug("No personnel given for consideration, returning empty list");
			return new ArrayList<Personnel>();
		}

		logger.debug("Found " + personnelList.size() + " Personnel for consideration");
		// note that this returns *all* personnel, including those with zero reviews
		List<PersonnelIdReviewCount> personnelIdReviewCounts = reviewRepository.findPersonnelIdReviewCountsByPersonnelIdIn(personnelIds);

		// personnel id-> remaining capacity
		Map<String, Integer> remainingCapacityByPersonnelId = new HashMap<String, Integer>();
		logger.debug("Calculating personnel remaining capacities");
		for (PersonnelIdReviewCount personnelIdReviewCount : personnelIdReviewCounts) {
			Integer capacity = personnelByPersonnelId.get(personnelIdReviewCount.personnelId).getReviewerCapacity();
			int remainingCapacity = capacity - personnelIdReviewCount.reviewCount;
			remainingCapacityByPersonnelId.put(personnelIdReviewCount.personnelId, remainingCapacity);
			logger.debug("\tPersonnel " + personnelIdReviewCount.personnelId + ": " + remainingCapacity);
		}

		// load and map skills

		// personnel id-> review type codes
		List<PersonnelIdCode> personnelIdReviewTypeCodes = personnelAssignmentTypeRepository.findPersonnelReviewTypeCodesByPersonnelIdIn(personnelIds);
		Map<String, List<String>> reviewTypeCodeByPersonnelId = buildCodesByPersonnelIdMap(personnelIdReviewTypeCodes);

		// personnel id-> review level type codes
		List<PersonnelIdCode> personnelIdReviewLevelTypeCodes = personnelAssignmentTypeRepository.findPersonnelReviewLevelTypeCodesByPersonnelIdIn(personnelIds);
		Map<String, List<String>> reviewLevelTypeCodeByPersonnelId = buildCodesByPersonnelIdMap(personnelIdReviewLevelTypeCodes);

		// personnel id-> product type codes
		List<PersonnelIdCode> personnelIdProductTypeCodes = personnelAssignmentTypeRepository.findPersonnelProductTypeCodesByPersonnelIdIn(personnelIds);
		Map<String, List<String>> productTypeCodeByPersonnelId = buildCodesByPersonnelIdMap(personnelIdProductTypeCodes);

		// personnel id-> selection reason codes
		List<PersonnelIdCode> personnelIdSelectionReasonCodes = personnelAssignmentTypeRepository.findPersonnelSelectionReasonCodesByPersonnelIdIn(personnelIds);
		Map<String, List<String>> selectionReasonCodeByPersonnelId = buildCodesByPersonnelIdMap(personnelIdSelectionReasonCodes);

		ScoringModelVersion currentAssignmentModelVersion = scoringModelVersionRepository.findSingleActiveByScoringModelTypeCode(ScoringModelTypeCodes.ASSIGNMENT);
		logger.debug("Using assignment scoring model version " + currentAssignmentModelVersion.getScoringModelVersionId());

		// factor name -> weight
		Map<String, Double> factorWeightByName = new HashMap<String, Double>();
		for (ScoringModelVersionFactor scoringModelVersionFactor : currentAssignmentModelVersion.getScoringModelVersionFactors()) {
			factorWeightByName.put(scoringModelVersionFactor.getScoringFactor().getFactorAttributeName(), scoringModelVersionFactor.getWeight().doubleValue());
		}

		Batch batch = review.getBatch();
		List<Personnel> batchPersonnel = null;
		if (batch != null && !includeNonBatchPersonnels) {
			batchPersonnel = personnelRepository.getActiveByBatchId(batch.getBatchId());
		}

		// prepare rules parameters for each personnelList
		List<AssignmentRulesParameters> assignmentRulesParametersList = new ArrayList<AssignmentRulesParameters>();
		for (Personnel personnel : personnelList) {
			AssignmentRulesParameters assignmentRulesParameters = new AssignmentRulesParameters();
			if (!includeNonBatchPersonnels) {
				assignmentRulesParameters.setBatchPersonnel(batchPersonnel);
			}
			else {
				assignmentRulesParameters.setBatchPersonnel(new ArrayList<Personnel>());
				assignmentRulesParameters.setManualAssign(true);
			}
			assignmentRulesParameters.setPreviousReviewLevels(previousReviewLevels);
			assignmentRulesParameters.setOriginalReviewReviewLevels(originalReviewReviewLevels);
			assignmentRulesParameters.setPersonnel(personnel);
			assignmentRulesParameters.setCurrentReviewLevel(reviewLevel);
			assignmentRulesParameters.setReviewTypeCodeByPersonnelId(reviewTypeCodeByPersonnelId);
			assignmentRulesParameters.setReviewLevelTypeCodeByPersonnelId(reviewLevelTypeCodeByPersonnelId);
			assignmentRulesParameters.setProductTypeCodeByPersonnelId(productTypeCodeByPersonnelId);
			assignmentRulesParameters.setSelectionReasonCodeByPersonnelId(selectionReasonCodeByPersonnelId);
			assignmentRulesParameters.setRemainingCapacity(remainingCapacityByPersonnelId.get(personnel.getPersonnelId()));
			assignmentRulesParameters.setFactorWeightByName(factorWeightByName);
			assignmentRulesParametersList.add(assignmentRulesParameters);
		}

		logger.debug("Firing assignment rules");
		rulesService.fireRules(assignmentRulesParametersList);

		assignmentRulesParametersList.sort((p1, p2) -> -p1.getScore().compareTo(p2.getScore()));

		double modelScoreThreshold = currentAssignmentModelVersion.getModelScoreThreshold();

		logger.debug("Assignment scores (threshold " + modelScoreThreshold + "):");
		for (AssignmentRulesParameters assignmentRulesParameters : assignmentRulesParametersList) {
			logger.debug("\tPersonnel " + assignmentRulesParameters.getPersonnel().getPersonnelId() + " (" + assignmentRulesParameters.getPersonnel().getLoginCredential() + "): " + assignmentRulesParameters.getScore());
			for (Map.Entry<String, Double> entry : assignmentRulesParameters.getFactorScoreByName().entrySet()) {
				logger.debug("\t\t" + entry.getKey() + ": " + entry.getValue());
			}
		}

		List<Personnel> passingPersonnel = new ArrayList<Personnel>();
		for (AssignmentRulesParameters assignmentRulesParameters : assignmentRulesParametersList) {
			if (assignmentRulesParameters.getScore() >= modelScoreThreshold) {
				Personnel personnel = assignmentRulesParameters.getPersonnel();
				logger.debug("Selected " + personnel.getPersonnelId() + " (" + personnel.getLoginCredential() + ") with score " + assignmentRulesParameters.getScore() + " for assignment");

				passingPersonnel.add(personnel);
			}
		}

		logger.debug(passingPersonnel.size() + " personnel passed threshold");

		MDC.remove("logFileName");

		return passingPersonnel;
	}

	private Map<String, List<String>> buildCodesByPersonnelIdMap(List<PersonnelIdCode> personnelIdCodes) {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		for (PersonnelIdCode personnelIdCode : personnelIdCodes) {
			String personnelId = personnelIdCode.personnelId;
			String code = personnelIdCode.code;

			List<String> codeList = map.get(personnelId);
			if (codeList == null) {
				codeList = new ArrayList<String>();
				map.put(personnelId, codeList);
			}
			codeList.add(code);
		}

		return map;
	}

}
