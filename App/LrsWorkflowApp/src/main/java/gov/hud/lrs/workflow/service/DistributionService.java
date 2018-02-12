package gov.hud.lrs.workflow.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Multimap;

import gov.hud.lrs.common.entity.Job;
import gov.hud.lrs.common.entity.LoanSelection;
import gov.hud.lrs.common.entity.ProductTypeRef;
import gov.hud.lrs.common.entity.ReviewLocation;
import gov.hud.lrs.common.entity.ReviewTypeRef;
import gov.hud.lrs.common.entity.ScoringModelVersion;
import gov.hud.lrs.common.entity.ScoringModelVersionFactor;
import gov.hud.lrs.common.entity.SelectionReason;
import gov.hud.lrs.common.enumeration.LoanSelectionStatusCodes;
import gov.hud.lrs.common.enumeration.ScoringModelTypeCodes;
import gov.hud.lrs.common.repository.LoanSelectionRepository;
import gov.hud.lrs.common.repository.LoanSelectionRepositoryCustom.ReviewLocationIdLoanSelectionCount;
import gov.hud.lrs.common.repository.LocationAssignmentTypeRepositoryCustom.ReviewLocationIdCode;
import gov.hud.lrs.common.repository.LocationAssignmentTypeRepositoryImpl;
import gov.hud.lrs.common.repository.ProductTypeRefRepository;
import gov.hud.lrs.common.repository.ReviewLocationRepository;
import gov.hud.lrs.common.repository.ReviewTypeRefRepository;
import gov.hud.lrs.common.repository.ScoringModelVersionRepository;
import gov.hud.lrs.common.repository.SelectionReasonRepository;
import gov.hud.lrs.common.service.CommonExceptionService;
import gov.hud.lrs.common.service.CommonLoanSelectionService;
import gov.hud.lrs.common.util.DateUtils;
import gov.hud.lrs.common.util.Util;

@Service
public class DistributionService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final String JOB_LOG_FILE_FORMAT = "yyyy.MM.dd-HH.mm.ss";

	@Value("${lrs.batchSize}")
	private int BATCH_SIZE;

	@Autowired private LoanSelectionRepository loanSelectionRepository;
	@Autowired private LocationAssignmentTypeRepositoryImpl locationAssignmentTypeRepositoryImpl;
	@Autowired private ProductTypeRefRepository productTypeRefRepository;
	@Autowired private ReviewLocationRepository reviewLocationRepository;
	@Autowired private ReviewTypeRefRepository reviewTypeRefRepository;
	@Autowired private ScoringModelVersionRepository scoringModelVersionRepository;
	@Autowired private SelectionReasonRepository selectionReasonRepository;

	@Autowired private CommonExceptionService commonExceptionService;
	@Autowired private CommonLoanSelectionService commonLoanSelectionService;
	@Autowired private RulesService rulesService;

	@PersistenceContext(unitName = "lrs") private EntityManager entityManager;

	@Transactional
	public void distributeLoanSelections(Job job) {
		MDC.put("logFileName", "distributeLoanSelections-" + new SimpleDateFormat(JOB_LOG_FILE_FORMAT).format(new Date()) +  "-" + job.getJobId());

		logger.debug("Starting distribution job " + job.getJobId());

		logger.debug("Loading loan selections ready for distribution...");
		List<LoanSelection> loanSelections = entityManager.createQuery(
			"select distinct ls from LoanSelection ls " +
			"join fetch ls.loanSelectionCaseSummary lscs " +
			"left join ls.batch b " +
			"left join b.reviewLocation brl " +
			"left join fetch ls.qcReview qcr " +
//			"left join fetch qcr.reviewLocation " +	// TODO: which rev loc to fetch?
			"where " +
				"(ls.loanSelectionStatusRef.code = '" + LoanSelectionStatusCodes.SELECTED + "') and " +
				"(ls.reviewLocation is null) and " +
				// either it's not part of a batch, *or* it's part of a batch that *does* have a location (skip batches in dist overflow exception)
				"((b is null) or (brl is not null)) ",
			LoanSelection.class
		)
		.getResultList();

		logger.debug("...loaded " + loanSelections.size() + " loan selections");

		List<ReviewLocation> reviewLocations = reviewLocationRepository.findAll();
		Map<String, ReviewLocation> reviewLocationIdToReviewLocation = Util.index(reviewLocations, rl -> rl.getReviewLocationId());

		// Get the counts for the current month
		List<ReviewLocationIdLoanSelectionCount> reviewLocationIdLoanSelectionCountsCurrentMonth = loanSelectionRepository.findReviewLocationLoanSelectionCounts(DateUtils.getCurrentMonth());
		//Get the counts for the prior month
		List<ReviewLocationIdLoanSelectionCount> reviewLocationIdLoanSelectionCountsPriorMonth = loanSelectionRepository.findReviewLocationLoanSelectionCounts(DateUtils.getCurrentMonth()-1);

		logger.debug("Loading review location data...");

		// reviewLocationId -> remaining capacity (total - used) for the currentMonth
		Map<String, Integer> reviewLocationIdToRemainingCapacityCurrentMonth = new HashMap<String, Integer>();
		for (ReviewLocationIdLoanSelectionCount reviewIdLoanSelectionCount : reviewLocationIdLoanSelectionCountsCurrentMonth) {
			Integer capacity = reviewLocationIdToReviewLocation.get(reviewIdLoanSelectionCount.reviewLocationId).getCommittedMonthlyCapacity();
			reviewLocationIdToRemainingCapacityCurrentMonth.put(reviewIdLoanSelectionCount.reviewLocationId, capacity - reviewIdLoanSelectionCount.loanSelectionCount);
		}

		// reviewLocationId -> remaining capacity (total - used) for the priorMonth
		Map<String, Integer> reviewLocationIdToRemainingCapacityPriorMonth = new HashMap<String, Integer>();
		for (ReviewLocationIdLoanSelectionCount reviewIdLoanSelectionCount : reviewLocationIdLoanSelectionCountsPriorMonth) {
			Integer capacity = reviewLocationIdToReviewLocation.get(reviewIdLoanSelectionCount.reviewLocationId).getCommittedMonthlyCapacity();
			reviewLocationIdToRemainingCapacityPriorMonth.put(reviewIdLoanSelectionCount.reviewLocationId, capacity - reviewIdLoanSelectionCount.loanSelectionCount);
		}

		Multimap<String, String> reviewLocationIdToReviewTypeCodes = buildReviewLocationIdToCodesMultimap(
			locationAssignmentTypeRepositoryImpl.getAllLocationsAndReviewTypes()
		);

		Multimap<String, String> reviewLocationIdToProductTypeCodes  =  buildReviewLocationIdToCodesMultimap(
			locationAssignmentTypeRepositoryImpl.getAllLocationsAndProductTypes()
		);

		Multimap<String, String> reviewLocationIdToSelectionReasonCodes = buildReviewLocationIdToCodesMultimap(
			locationAssignmentTypeRepositoryImpl.getAllLocationsAndSelectionReasonTypes()
		);

		logger.debug("Review location capacities and skills:");
		List<ReviewTypeRef> reviewTypes = reviewTypeRefRepository.findAll();
		List<ProductTypeRef> productTypes = productTypeRefRepository.findAll();
		List<SelectionReason> selectionReasons = selectionReasonRepository.findAll();

		Map<String, String> shortSelectionReasonCodes = ImmutableMap.<String, String>builder()
			.put("TEST_CASE", "TEST")
			.put("FHA_MANUAL", "FHA")
			.put("LENDER_SELF_REPORT", "SELF")
			.put("OIG_AUDIT", "OIG")
			.put("OTHER_NON_PERFORMING", "OTHR")
			.put("MONTHLY_RISK_THRESHOLD", "RISK")
			.put("LENDER_INCREASED_SAMPLING", "LINC")
			.put("UNDERWRITER_DECREASED_SAMPLING", "UINC")
			.put("MONTHLY_RANDOM", "RAND")
			.put("EARLY_CLAIM", "EC")
			.put("EARLY_PAYMENT_DEFAULT", "EPD")
			.put("NATIONAL_QC", "NQC")
			.put("REVIEW_LOCATION_QC", "RQC")
			.put("LENDER_MONITORING", "LMON")
			.build();

		String line = "";
		line += String.format("HoC-Div  PCa  CCa");
		for (ReviewTypeRef reviewType : reviewTypes) {
			line += String.format("%5s", reviewType.getReviewTypeCd());
		}

		for (ProductTypeRef productType : productTypes) {
			line += String.format("%5s", productType.getProductTypeCd());
		}

		for (SelectionReason selectionReason : selectionReasons) {
			String shortSelectionReasonCode = shortSelectionReasonCodes.get(selectionReason.getCode());
			line += String.format("%5s", (shortSelectionReasonCode != null) ? shortSelectionReasonCode : "??");
		}
		logger.debug(line);

		for (ReviewLocation reviewLocation : reviewLocations) {
			line = "";

			line += String.format("%7s %4d %4d", reviewLocation.getLocationName(), reviewLocationIdToRemainingCapacityPriorMonth.get(reviewLocation.getReviewLocationId()), reviewLocationIdToRemainingCapacityCurrentMonth.get(reviewLocation.getReviewLocationId()));
			//line += String.format("    ", reviewLocationIdToRemainingCapacityCurrentMonth.get(reviewLocation.getReviewLocationId()));

			for (ReviewTypeRef reviewType : reviewTypes) {
				line += reviewLocationIdToReviewTypeCodes.containsEntry(reviewLocation.getReviewLocationId(), reviewType.getReviewTypeCd()) ? "    *" : "    -";
			}

			for (ProductTypeRef productType : productTypes) {
				line += reviewLocationIdToProductTypeCodes.containsEntry(reviewLocation.getReviewLocationId(), productType.getProductTypeCd()) ? "    *" : "    -";
			}

			for (SelectionReason selectionReason : selectionReasons) {
				line += reviewLocationIdToSelectionReasonCodes.containsEntry(reviewLocation.getReviewLocationId(), selectionReason.getCode()) ? "    *" : "    -";
			}

			logger.debug(line);
		}

		ScoringModelVersion currentDistributionModelVersion = scoringModelVersionRepository.findSingleActiveByScoringModelTypeCode(ScoringModelTypeCodes.DISTRIBUTION);

		logger.debug("Using distribution scoring model version " + currentDistributionModelVersion.getModelVerNum());
		logger.debug("Scoring factors: ");
		Map<String, Double> factorNameToWeight = new HashMap<String, Double>();
		for (ScoringModelVersionFactor scoringModelVersionFactor : currentDistributionModelVersion.getScoringModelVersionFactors()) {
			factorNameToWeight.put(scoringModelVersionFactor.getScoringFactor().getFactorAttributeName(), scoringModelVersionFactor.getWeight().doubleValue());
			logger.debug("\t" + scoringModelVersionFactor.getScoringFactor().getFactorAttributeName() + ": " + scoringModelVersionFactor.getWeight().doubleValue());
		}

		// prepare rules parameters for each location.
		List<DistributionRulesParameters> distributionRulesParametersList = new ArrayList<DistributionRulesParameters>();
		for (ReviewLocation reviewLocation: reviewLocations) {
			DistributionRulesParameters distributionRulesParameters = new DistributionRulesParameters();
			distributionRulesParameters.setReviewLocationId(reviewLocation.getReviewLocationId());
			distributionRulesParameters.setFactorNameToWeight(factorNameToWeight);
			distributionRulesParameters.setReviewLocationIdToProductTypeCodes(reviewLocationIdToProductTypeCodes);
			distributionRulesParameters.setReviewLocationIdToReviewTypeCodes(reviewLocationIdToReviewTypeCodes);
			distributionRulesParameters.setReviewLocationIdToSelectionReasonCodes(reviewLocationIdToSelectionReasonCodes);
			distributionRulesParametersList.add(distributionRulesParameters);
		}

		Map<String, Integer> reviewLocationIdToDistributedCount = new HashMap<String, Integer>();

		logger.debug("Scoring all loan selections against all locations");

		// score each review location for each loan selection
		int batchCount = 0;
		for (LoanSelection loanSelection : loanSelections) {
			// reset the rules parameters for this particular loan selection
			boolean isAutoSelected = loanSelection.getAutoSelectionIndicator().equals('Y') ? true : false;
			for (DistributionRulesParameters distributionRulesParameters: distributionRulesParametersList) {
				distributionRulesParameters.setScore(0.0);
				distributionRulesParameters.getFactorNameToScore().clear();
				distributionRulesParameters.setProductTypeCode(loanSelection.getProductTypeRef().getProductTypeCd());
				distributionRulesParameters.setReviewTypeCode(loanSelection.getReviewTypeRef().getReviewTypeCd());
				distributionRulesParameters.setSelectionReasonCode(loanSelection.getSelectionReason().getCode());
				distributionRulesParameters.setPreferredReviewLocationId(
					(loanSelection.getPreferredReviewLocation() != null) ?
						loanSelection.getPreferredReviewLocation().getReviewLocationId() :
						null
				);
				// TODO: which review level location do we use?
				//distributionRulesParameters.setQcReviewLocationId(loanSelection.getQcReview()...);
				if (isAutoSelected) {
					distributionRulesParameters.setRemainingCapacity(
							reviewLocationIdToRemainingCapacityPriorMonth.get(distributionRulesParameters.getReviewLocationId())
							);
				} else {
					distributionRulesParameters.setRemainingCapacity(
							reviewLocationIdToRemainingCapacityCurrentMonth.get(distributionRulesParameters.getReviewLocationId())
							);
				}
			}

			rulesService.fireRules(distributionRulesParametersList);

			// sort by score
			distributionRulesParametersList.sort((x, y) -> -new Double(x.getScore()).compareTo(new Double(y.getScore())));

			double modelScoreThreshold = currentDistributionModelVersion.getModelScoreThreshold();

			logger.debug("Scoring all locations for case " + loanSelection.getCaseNumber() + " / selection ID " + loanSelection.getSelectionId() + " (threshold " + modelScoreThreshold + ")");
			for (DistributionRulesParameters distributionRulesParameters: distributionRulesParametersList) {
				ReviewLocation reviewLocation = reviewLocationIdToReviewLocation.get(distributionRulesParameters.getReviewLocationId());
				logger.debug("\t" + reviewLocation.getLocationName() + ": " + distributionRulesParameters.getScore());
				for (Map.Entry<String, Double> entry : distributionRulesParameters.getFactorNameToScore().entrySet()) {
					logger.debug("\t\t" + entry.getKey() + ": " + entry.getValue());
				}
			}

			DistributionRulesParameters selected = null;
			for (DistributionRulesParameters distributionRulesParameters : distributionRulesParametersList) {
				int remainingCapacity = 0;
				if (isAutoSelected) {
					remainingCapacity = reviewLocationIdToRemainingCapacityPriorMonth.get(distributionRulesParameters.getReviewLocationId());
				} else {
					remainingCapacity = reviewLocationIdToRemainingCapacityCurrentMonth.get(distributionRulesParameters.getReviewLocationId());
				}
				if (
					(distributionRulesParameters.getPreferredReviewLocationId() != null) ||
					(
						(distributionRulesParameters.getScore() >= modelScoreThreshold) &&
						(remainingCapacity > 0)
					)
				) {
					selected = distributionRulesParameters;
					break;
				}
			}

			if (selected != null) {
				ReviewLocation reviewLocation = reviewLocationIdToReviewLocation.get(selected.getReviewLocationId());

				logger.debug("Distributing to review location " + reviewLocation.getLocationName() + " with score " + selected.getScore());
				commonLoanSelectionService.distributeLoanSelection(loanSelection, reviewLocation, isAutoSelected);

				if (isAutoSelected) {
					reviewLocationIdToRemainingCapacityPriorMonth.put(
							reviewLocation.getReviewLocationId(),
							reviewLocationIdToRemainingCapacityPriorMonth.get(reviewLocation.getReviewLocationId()) - 1
							);
				} else {
					reviewLocationIdToRemainingCapacityCurrentMonth.put(
							reviewLocation.getReviewLocationId(),
							reviewLocationIdToRemainingCapacityCurrentMonth.get(reviewLocation.getReviewLocationId()) - 1
							);
				}

				Integer thisLocationDistributedCount = reviewLocationIdToDistributedCount.get(reviewLocation.getReviewLocationId());
				if (thisLocationDistributedCount == null) {
					thisLocationDistributedCount = new Integer(0);
				}
				reviewLocationIdToDistributedCount.put(reviewLocation.getReviewLocationId(), thisLocationDistributedCount + 1);

			} else {
				logger.debug("Couldn't distribute LoanSelection " + loanSelection.getSelectionId() + "; creating exception");;

				commonExceptionService.createLoanSelectionDistributionException(loanSelection);
			}
			logger.debug("");

			++batchCount;
			if (batchCount >= BATCH_SIZE) {
				batchCount = 0;
				entityManager.flush();
			}
		}

		distributeLoanSelectionsWithReviewLocations();

		logger.debug("Distribution count by location:");
		for (ReviewLocation reviewLocation : reviewLocations) {
			Integer count = reviewLocationIdToDistributedCount.get(reviewLocation.getReviewLocationId());
			logger.debug(String.format("\t%7s: %d", reviewLocation.getLocationName(), ((count != null) ? count : 0)));
		}

		logger.debug("Finished distribution job " + job.getJobId());

		MDC.remove("logFileName");
	}

	private Multimap<String, String> buildReviewLocationIdToCodesMultimap(List<ReviewLocationIdCode> reviewLocationIdCodes) {
		Multimap<String, String> multimap = ArrayListMultimap.create();
		for (ReviewLocationIdCode reviewLocationIdCode : reviewLocationIdCodes) {
			multimap.put(reviewLocationIdCode.reviewLocationId, reviewLocationIdCode.code);
		}

		return multimap;
	}

	private void distributeLoanSelectionsWithReviewLocations() {
		Query query = entityManager.createNativeQuery(
			"UPDATE LOAN_SELECTION " +
			"SET LOAN_SELECTION_STATUS_ID = (SELECT LSSSUB.LOAN_SELECTION_STATUS_ID FROM LOAN_SELECTION_STATUS_REF LSSSUB WHERE (LSSSUB.CODE = '" + LoanSelectionStatusCodes.DISTRIBUTED + "')) " +
			"WHERE (REVIEW_LOCATION_ID IS NOT NULL) AND " +
			"(LOAN_SELECTION_STATUS_ID = (SELECT LSSSUB1.LOAN_SELECTION_STATUS_ID FROM LOAN_SELECTION_STATUS_REF LSSSUB1 WHERE (LSSSUB1.CODE = '" + LoanSelectionStatusCodes.SELECTED + "'))) "
		);
		int numUpdated = query.executeUpdate();
		logger.debug("Marked " + numUpdated + " LoanSelections with already-specified ReviewLocations as DISTRIBUTED");
	}

}
