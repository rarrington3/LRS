package gov.hud.lrs.workflow.service;

import java.io.PrintWriter;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;

import gov.hud.lrs.common.dto.LrsApiV1DTOs.BatchDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.LenderMonitoringDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.LenderSelfReportDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.ManualSelectionDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.ReviewRequestByLenderDTO;
import gov.hud.lrs.common.entity.Batch;
import gov.hud.lrs.common.entity.BatchPersonnel;
import gov.hud.lrs.common.entity.BatchStatusRef;
import gov.hud.lrs.common.entity.Defect;
import gov.hud.lrs.common.entity.FileDeliveryLocationRef;
import gov.hud.lrs.common.entity.FraudParticipantRef;
import gov.hud.lrs.common.entity.FraudTypeRef;
import gov.hud.lrs.common.entity.Job;
import gov.hud.lrs.common.entity.JobParameter;
import gov.hud.lrs.common.entity.JobTypeRef;
import gov.hud.lrs.common.entity.Lender;
import gov.hud.lrs.common.entity.LenderIncreasedSelection;
import gov.hud.lrs.common.entity.LenderMonitoringSelectionRequest;
import gov.hud.lrs.common.entity.LenderSelfReportSelectionRequest;
import gov.hud.lrs.common.entity.LenderSelfReportSelectionRequestDefect;
import gov.hud.lrs.common.entity.LenderSelfReportSelectionRequestDefectId;
import gov.hud.lrs.common.entity.LenderSelfReportSelectionRequestFraudParticipantRef;
import gov.hud.lrs.common.entity.LenderSelfReportSelectionRequestFraudParticipantRefId;
import gov.hud.lrs.common.entity.LenderSelfReportSelectionRequestFraudTypeRef;
import gov.hud.lrs.common.entity.LenderSelfReportSelectionRequestFraudTypeRefId;
import gov.hud.lrs.common.entity.LenderSuppression;
import gov.hud.lrs.common.entity.LoanSelection;
import gov.hud.lrs.common.entity.LoanSelectionCaseSummary;
import gov.hud.lrs.common.entity.LoanTypeRef;
import gov.hud.lrs.common.entity.ManualSelectionRequest;
import gov.hud.lrs.common.entity.ModelScore;
import gov.hud.lrs.common.entity.Personnel;
import gov.hud.lrs.common.entity.QaModel;
import gov.hud.lrs.common.entity.Review;
import gov.hud.lrs.common.entity.ReviewLevel;
import gov.hud.lrs.common.entity.ReviewLocation;
import gov.hud.lrs.common.entity.ReviewScopeRef;
import gov.hud.lrs.common.entity.ReviewTypeRef;
import gov.hud.lrs.common.entity.ScoringFactor;
import gov.hud.lrs.common.entity.ScoringModel;
import gov.hud.lrs.common.entity.ScoringModelVersion;
import gov.hud.lrs.common.entity.ScoringModelVersionFactor;
import gov.hud.lrs.common.entity.SelectionReason;
import gov.hud.lrs.common.entity.SelectionRequest;
import gov.hud.lrs.common.entity.SelectionRequestTypeRef;
import gov.hud.lrs.common.entity.SelectionSubReasonRef;
import gov.hud.lrs.common.entity.UnderwriterIncreasedSelection;
import gov.hud.lrs.common.entity.UniverseRef;
import gov.hud.lrs.common.entity.UniverseSelectionRequest;
import gov.hud.lrs.common.enumeration.LoanSelectionStatusCodes;
import gov.hud.lrs.common.enumeration.ReviewScopeCodes;
import gov.hud.lrs.common.enumeration.ReviewStatusCodes;
import gov.hud.lrs.common.enumeration.ReviewTypeCodes;
import gov.hud.lrs.common.enumeration.ScoringModelTypeCodes;
import gov.hud.lrs.common.enumeration.SelectionReasonCodes;
import gov.hud.lrs.common.enumeration.UniverseCodes;
import gov.hud.lrs.common.exception.BadRequestException;
import gov.hud.lrs.common.exception.ConflictException;
import gov.hud.lrs.common.repository.BatchStatusRefRepository;
import gov.hud.lrs.common.repository.DefectRepository;
import gov.hud.lrs.common.repository.ExcludedReviewRepository;
import gov.hud.lrs.common.repository.FileDeliveryLocationRefRepository;
import gov.hud.lrs.common.repository.FraudParticipantRefRepository;
import gov.hud.lrs.common.repository.FraudTypeRefRepository;
import gov.hud.lrs.common.repository.LenderIncreasedSelectionRepository;
import gov.hud.lrs.common.repository.LenderMonitoringSelectionRequestRepository;
import gov.hud.lrs.common.repository.LenderSelfReportSelectionRequestDefectRepository;
import gov.hud.lrs.common.repository.LenderSelfReportSelectionRequestFraudParticipantRefRepository;
import gov.hud.lrs.common.repository.LenderSelfReportSelectionRequestFraudTypeRefRepository;
import gov.hud.lrs.common.repository.LenderSelfReportSelectionRequestRepository;
import gov.hud.lrs.common.repository.LenderSuppressionRepository;
import gov.hud.lrs.common.repository.LoanSelectionCaseSummaryRepository;
import gov.hud.lrs.common.repository.LoanSelectionRepository;
import gov.hud.lrs.common.repository.LoanSelectionRepositoryCustom.ReviewLocationIdLoanSelectionCount;
import gov.hud.lrs.common.repository.LoanSelectionStatusRefRepository;
import gov.hud.lrs.common.repository.LoanTypeRefRepository;
import gov.hud.lrs.common.repository.ManualSelectionRequestRepository;
import gov.hud.lrs.common.repository.ModelScoreRepository;
import gov.hud.lrs.common.repository.PersonnelRepository;
import gov.hud.lrs.common.repository.QaModelRepository;
import gov.hud.lrs.common.repository.ReviewLocationRepository;
import gov.hud.lrs.common.repository.ReviewRepository;
import gov.hud.lrs.common.repository.ReviewScopeRefRepository;
import gov.hud.lrs.common.repository.ReviewTypeRefRepository;
import gov.hud.lrs.common.repository.ScoringModelVersionRepository;
import gov.hud.lrs.common.repository.SelectionReasonRepository;
import gov.hud.lrs.common.repository.SelectionRequestRepository;
import gov.hud.lrs.common.repository.SelectionRequestTypeRefRepository;
import gov.hud.lrs.common.repository.SelectionSubReasonRefRepository;
import gov.hud.lrs.common.repository.UnderwriterIncreasedSelectionRepository;
import gov.hud.lrs.common.repository.UniverseRefRepository;
import gov.hud.lrs.common.repository.UniverseSelectionRequestRepository;
import gov.hud.lrs.common.security.SecurityService;
import gov.hud.lrs.common.service.CaseUniverseService;
import gov.hud.lrs.common.service.CommonBatchService;
import gov.hud.lrs.common.service.CommonExceptionService;
import gov.hud.lrs.common.service.CommonLoanSelectionService;
import gov.hud.lrs.common.service.CommonReviewService;
import gov.hud.lrs.common.service.LenderService;
import gov.hud.lrs.common.util.DateUtils;
import gov.hud.lrs.common.util.Util;

@Service
public class SelectionService {

	private Logger logger = LoggerFactory.getLogger(SelectionService.class);
	private final String JOB_LOG_FILE_FORMAT = "yyyy.MM.dd-HH.mm.ss";
	
	private SecureRandom secureRandom = new SecureRandom();

	@Value("${lrs.jobs.batchSize}") private int batchSize;
	@Value("${lrs.log.directory.jboss.root}") private String jbossLogRoot;
	@Value("${lrs.log.directory.workflow}") private String workflowLogDir;
	@Value("${lrs.jobs.remainingMonthlyCapacity.numberOfMonthsData}") private int fillRemainingCapRetrieveNumOfMonthsData;

	@Autowired private BatchStatusRefRepository batchStatusRefRepository;
	@Autowired private LoanSelectionCaseSummaryRepository loanSelectionCaseSummaryRepository;
	@Autowired private UniverseSelectionRequestRepository universeSelectionRequestRepository;
	@Autowired private SelectionRequestRepository selectionRequestRepository;
	@Autowired private SelectionRequestTypeRefRepository selectionRequestTypeRefRepository;
	@Autowired private ModelScoreRepository modelScoreRepository;
	@Autowired private ScoringModelVersionRepository scoringModelVersionRepository;
	@Autowired private UnderwriterIncreasedSelectionRepository underwriterIncreasedSelectionRepository;
	@Autowired private LenderIncreasedSelectionRepository lenderIncreasedSelectionRepository;
	@Autowired private LenderSuppressionRepository lenderSuppressionRepository;
	@Autowired private LenderMonitoringSelectionRequestRepository lenderMonitoringSelectionRequestRepository;
	@Autowired private ReviewLocationRepository reviewLocationRepository;
	@Autowired private LoanSelectionRepository loanSelectionRepository;
	@Autowired private PersonnelRepository personnelRepository;
	@Autowired private ReviewTypeRefRepository reviewTypeRefRepository;
	@Autowired private SelectionReasonRepository selectionReasonRepository;
	@Autowired private SelectionSubReasonRefRepository selectionSubReasonRefRepository;
	@Autowired private ManualSelectionRequestRepository manualSelectionRequestRepository;
	@Autowired private FileDeliveryLocationRefRepository fileDeliveryLocationRefRepository;
	@Autowired private LoanTypeRefRepository loanTypeRefRepository;
	@Autowired private DefectRepository defectRepository;
	@Autowired private FraudTypeRefRepository fraudTypeRefRepository;
	@Autowired private FraudParticipantRefRepository fraudParticipantRefRepository;
	@Autowired private LenderSelfReportSelectionRequestRepository lenderSelfReportSelectionRequestRepository;
	@Autowired private LenderSelfReportSelectionRequestDefectRepository lenderSelfReportSelectionRequestDefectRepository;
	@Autowired private LenderSelfReportSelectionRequestFraudTypeRefRepository lenderSelfReportSelectionRequestFraudTypeRefRepository;
	@Autowired private LenderSelfReportSelectionRequestFraudParticipantRefRepository lenderSelfReportSelectionRequestFraudParticipantRefRepository;
	@Autowired private LoanSelectionStatusRefRepository loanSelectionStatusRefRepository;
	@Autowired private QaModelRepository qaModelRepository;
	@Autowired private ReviewRepository reviewRepository;
	@Autowired private ReviewScopeRefRepository reviewScopeRefRepository;
	@Autowired private UniverseRefRepository universeRefRepository;
	@Autowired private ExcludedReviewRepository excludedReviewRepository;

	@Autowired private AssignmentService assignmentService;
	@Autowired private CaseUniverseService caseUniverseService;
	@Autowired private CommonBatchService commonBatchService;
	@Autowired private CommonExceptionService commonExceptionService;
	@Autowired private CommonLoanSelectionService commonLoanSelectionService;
	@Autowired private CommonReviewService commonReviewService;
	@Autowired private JobService jobService;
	@Autowired private LenderService lenderService;
	@Autowired private RulesService rulesService;
	@Autowired private SecurityService securityService;
	@Autowired private AggregationService aggregationService;

	@PersistenceContext private EntityManager entityManager;

	public static class ScoringModelContext {
		ScoringModelVersion scoringModelVersion;
		public ArrayList<Double> modelScores = new ArrayList<Double>();
		public Map<String, Integer> caseNumberToModelScoreIndex = new HashMap<String, Integer>();
		public boolean createLoanSelectionPendings = false;
	}

	@Transactional
	public void runUniverseSelectionModel(String universeRefCode, Date startDate, Date endDate, Job job) {
		this.runUniverseSelectionModel(universeRefCode, startDate, endDate, job, null);
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public void runUniverseSelectionModel(String universeCode, Date startDate, Date endDate, Job job, Integer maxCasesToSelect) {
		MDC.put("logFileName", "universeSelection-" + universeCode + "-" + new SimpleDateFormat(JOB_LOG_FILE_FORMAT).format(new Date()) +  "-" + job.getJobId());

		logger.debug("Starting " + universeCode + " selection job " + job.getJobId() + " for date range [ " + startDate + ", " + endDate + " ]");

		UniverseSelectionRequest universeSelectionRequest = createUniverseSelectionRequest(universeCode, startDate, endDate, job);
		SelectionRequest selectionRequest = universeSelectionRequest.getSelectionRequest();

		logger.debug("Retrieving LoanSelectionCaseSummary records...");
		String scoringModelTypeCode;
		Set<String> excludeCaseNumbers = excludedReviewRepository.findAllCaseNumbers();
		List<LoanSelectionCaseSummary> loanSelectionCaseSummaries;
		if (universeCode.equalsIgnoreCase(UniverseCodes.ENDORSEMENT)) {
			List<String> caseNumbers = caseUniverseService.getMonthlyEndorsementUniverseCaseNumbers(startDate, endDate);
			caseNumbers.removeAll(excludeCaseNumbers);
			loanSelectionCaseSummaries = caseUniverseService.getLoanSelectionCaseSummaries(caseNumbers);
			scoringModelTypeCode = ScoringModelTypeCodes.ENDORSEMENT_SELECTION;

		} else if (universeCode.equalsIgnoreCase(UniverseCodes.EARLY_CLAIM)) {
			List<String> caseNumbers = caseUniverseService.getEarlyClaimUniverseCaseNumbers(startDate, endDate);
			caseNumbers.removeAll(excludeCaseNumbers);
			loanSelectionCaseSummaries = caseUniverseService.getLoanSelectionCaseSummaries(caseNumbers);
			scoringModelTypeCode = ScoringModelTypeCodes.EARLY_CLAIM_SELECTION;

		} else if (universeCode.equalsIgnoreCase(UniverseCodes.EARLY_PAYMENT_DEFAULT)) {
			List<String> caseNumbers = caseUniverseService.getEarlyPendingDefaultUniverseCaseNumbers(startDate, endDate);
			caseNumbers.removeAll(excludeCaseNumbers);
			loanSelectionCaseSummaries = caseUniverseService.getLoanSelectionCaseSummaries(caseNumbers);
			scoringModelTypeCode = ScoringModelTypeCodes.EARLY_PAYMENT_DEFAULT_SELECTION;

		} else if (universeCode.equalsIgnoreCase(UniverseCodes.NATIONAL_QC)) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			// all completed, non-qc cases in this date range, using most recent review for each case number
			loanSelectionCaseSummaries = (List<LoanSelectionCaseSummary>)entityManager.createNativeQuery(
				"SELECT LSCS.* " +
				"FROM LOAN_SELECTION_CASE_SUMMARY LSCS " +
				"INNER JOIN REVIEW R ON (R.SELECTION_ID = LSCS.SELECTION_ID) " +
				"INNER JOIN REVIEW_STATUS_REF RS ON (RS.REVIEW_STATUS_ID = R.REVIEW_STATUS_ID) " +
				"INNER JOIN SELECTION_REASON SR ON (SR.SELECTION_REASON_ID = R.SELECTION_REASON_ID) " +
				"WHERE " +
					"(RS.CODE = '" + ReviewStatusCodes.COMPLETED + "') AND " +
					"(SR.CODE NOT IN ( " +
						"'" + SelectionReasonCodes.NATIONAL_QC + "', " +
						"'" + SelectionReasonCodes.REVIEW_LOCATION_QC + "' " +
					")) AND " +
					"(R.RVW_COMPLTD_DT >= :startDate) AND " +
					"(R.RVW_COMPLTD_DT <= :endDate) AND " +
					"(NOT EXISTS ( " +
						"SELECT * FROM REVIEW R2 " +
						"INNER JOIN SELECTION_REASON SR2 ON (SR2.SELECTION_REASON_ID = R2.SELECTION_REASON_ID) " +
						"WHERE " +
							"(R2.CASE_NUMBER = R.CASE_NUMBER) AND " +
							"(R2.REVIEW_STATUS_ID = R.REVIEW_STATUS_ID) AND " +
							"(SR2.CODE NOT IN ( " +
								"'" + SelectionReasonCodes.NATIONAL_QC + "', " +
								"'" + SelectionReasonCodes.REVIEW_LOCATION_QC + "' " +
							")) AND " +
							"(R2.RVW_COMPLTD_DT >= :startDate) AND " +
							"(R2.RVW_COMPLTD_DT <= :endDate) AND " +
							"(R2.RVW_COMPLTD_DT > R.RVW_COMPLTD_DT) " +
					")) ",
				LoanSelectionCaseSummary.class
			)
			.setParameter("startDate", dateFormat.format(startDate))
			.setParameter("endDate", dateFormat.format(endDate))
			.getResultList();
			scoringModelTypeCode = ScoringModelTypeCodes.NATIONAL_QC_SELECTION;

		} else {
			throw new RuntimeException("Unhandled UniverseRef code: " + universeCode);
		}

		logger.debug("...retrieved " + loanSelectionCaseSummaries.size() + " LoanSelectionCaseSummary records");

		List<ScoringModelVersion> scoringModelVersions = scoringModelVersionRepository.findMultipleActiveByScoringModelTypeCode(scoringModelTypeCode);

		scoreLoanSelectionCaseSummaries(
			loanSelectionCaseSummaries,
			scoringModelVersions,
			selectionRequest,
			maxCasesToSelect,
			job
		);

		for (ScoringModelVersion scoringModelVersion : scoringModelVersions) {
			createLoanSelectionPendingsFromModelScores(universeSelectionRequest.getSelectionRequestId(), scoringModelVersion);

			boolean isNationalQc = scoringModelVersion.getScoringModel().getScoringModelTypeRef().getCode().equals(ScoringModelTypeCodes.NATIONAL_QC_SELECTION);
			if (isNationalQc) {
				entityManager.createNativeQuery(
					"UPDATE LSP " +
					"SET LSP.QC_REVIEW_ID = R.REVIEW_ID, " +
					"LSP.REVIEW_SCOPE_ID = R.REVIEW_SCOPE_ID " +
					"FROM LOAN_SELECTION_PENDING LSP " +
					"INNER JOIN REVIEW R ON (R.CASE_NUMBER = LSP.CASE_NUMBER) " +
					"INNER JOIN REVIEW_STATUS_REF RS ON (RS.REVIEW_STATUS_ID = R.REVIEW_STATUS_ID) " +
					"INNER JOIN SELECTION_REASON SR ON (SR.SELECTION_REASON_ID = R.SELECTION_REASON_ID) " +
					"INNER JOIN LOAN_SELECTION_CASE_SUMMARY LSCS ON (LSCS.SELECTION_ID = LSP.SELECTION_ID) " +
					"INNER JOIN MODEL_SCORE MS ON (MS.SELECTION_ID = LSCS.SELECTION_ID) " +
					"INNER JOIN SCORING_MODEL_VERSION SMV ON (SMV.SCORING_MODEL_VERSION_ID = MS.SCORING_MODEL_VERSION_ID) " +
					"WHERE " +
						"(RS.CODE = '" + ReviewStatusCodes.COMPLETED + "') AND " +
						"(SR.CODE NOT IN ( " +
							"'" + SelectionReasonCodes.NATIONAL_QC + "', " +
							"'" + SelectionReasonCodes.REVIEW_LOCATION_QC + "' " +
						")) AND " +
						"(R.RVW_COMPLTD_DT >= :startDate) AND " +
						"(R.RVW_COMPLTD_DT <= :endDate) AND " +
						"(NOT EXISTS ( " +
							"SELECT * FROM REVIEW R2 " +
							"INNER JOIN SELECTION_REASON SR2 ON (SR2.SELECTION_REASON_ID = R2.SELECTION_REASON_ID) " +
							"WHERE " +
								"(R2.CASE_NUMBER = R.CASE_NUMBER) AND " +
								"(R2.REVIEW_STATUS_ID = R.REVIEW_STATUS_ID) AND " +
								"(SR2.CODE NOT IN ( " +
									"'" + SelectionReasonCodes.NATIONAL_QC + "', " +
									"'" + SelectionReasonCodes.REVIEW_LOCATION_QC + "' " +
								")) AND " +
								"(R2.RVW_COMPLTD_DT >= :startDate) AND " +
								"(R2.RVW_COMPLTD_DT <= :endDate) AND " +
								"(R2.RVW_COMPLTD_DT > R.RVW_COMPLTD_DT) " +
						")) AND " +
						"(LSCS.SELECTION_REQUEST_ID = :selectionRequestId) AND " +
						"(SMV.SCORING_MODEL_VERSION_ID = :scoringModelVersionId) "
				)
				.setParameter("startDate", startDate)
				.setParameter("endDate", endDate)
				.setParameter("selectionRequestId", selectionRequest.getSelectionRequestId())
				.setParameter("scoringModelVersionId", scoringModelVersion.getScoringModelVersionId())
				.executeUpdate();
			}
		}

		logger.debug("Finished universe selection job " + job.getJobId());

		MDC.remove("logFileName");
	}

	@Transactional
	public void runLenderMonitoringSelectionModel(String selectionRequestId, Job job) {
		MDC.put("logFileName", "lenderMonitoring-" + new SimpleDateFormat(JOB_LOG_FILE_FORMAT).format(new Date()) + "-" + selectionRequestId);

		logger.debug("Selecting lenders for LenderMonitoringSelectionRequest " + selectionRequestId);

		LenderMonitoringSelectionRequest lenderMonitoringSelectionRequest = lenderMonitoringSelectionRequestRepository.findOne(selectionRequestId);
		SelectionRequest selectionRequest = lenderMonitoringSelectionRequest.getSelectionRequest();

		logger.debug("Retrieving case numbers...");
		List<String> caseNumbers = caseUniverseService.getLenderMonitoringUniverseCaseNumbers(
			lenderMonitoringSelectionRequest.getLender().getLenderId(),
			lenderMonitoringSelectionRequest.getStartDt(),
			lenderMonitoringSelectionRequest.getEndDt(),
			lenderMonitoringSelectionRequest.getLoanTypeRef(),
			lenderMonitoringSelectionRequest.getReviewTypeRef()
		);
		logger.debug("...retrieved " + caseNumbers.size() + " case numbers");
		caseNumbers.removeAll(excludedReviewRepository.findAllCaseNumbers());

		logger.debug("Retrieving LoanSelectionCaseSummary records...");
		List<LoanSelectionCaseSummary> loanSelectionCaseSummaries = caseUniverseService.getLoanSelectionCaseSummaries(caseNumbers);
		logger.debug("...retrieved " + loanSelectionCaseSummaries + " LoanSelectionCaseSummary records");

		logger.debug("Loading active lender monitoring scoring model for review type " + lenderMonitoringSelectionRequest.getReviewTypeRef().getReviewTypeCd() + " and loan type " + lenderMonitoringSelectionRequest.getLoanTypeRef().getCode());
		ScoringModelVersion scoringModelVersion = scoringModelVersionRepository.findActiveByScoringModelTypeCodeAndReviewTypeCodeAndLoanTypeCode(
			ScoringModelTypeCodes.LENDER_MONITORING_SELECTION,
			lenderMonitoringSelectionRequest.getReviewTypeRef().getReviewTypeCd(),
			lenderMonitoringSelectionRequest.getLoanTypeRef().getCode()
		);
		// we need a list since processCaseNumbers takes a list
		List<ScoringModelVersion> scoringModelVersions = ImmutableList.of(scoringModelVersion);

		scoreLoanSelectionCaseSummaries(
			loanSelectionCaseSummaries,
			scoringModelVersions,
			selectionRequest,
			lenderMonitoringSelectionRequest.getCaseCount(),
			job
		);

		createLoanSelectionPendingsFromModelScores(selectionRequest.getSelectionRequestId(), scoringModelVersion);

		// update all pending loan selections to have the specified review location
		entityManager.createNativeQuery(
			"UPDATE LSP SET LSP.REVIEW_LOCATION_ID = :reviewLocationId " +
			"FROM LOAN_SELECTION_PENDING LSP " +
			"INNER JOIN LOAN_SELECTION_CASE_SUMMARY LSCS ON (LSCS.SELECTION_ID = LSP.SELECTION_ID) " +
			"WHERE (LSCS.SELECTION_REQUEST_ID = :selectionRequestId)"
		)
		.setParameter("reviewLocationId", lenderMonitoringSelectionRequest.getReviewLocation().getReviewLocationId())
		.setParameter("selectionRequestId", selectionRequestId)
		.executeUpdate();

		entityManager.flush();

		logger.debug("Finished LenderMonitoringSelectionRequest " + lenderMonitoringSelectionRequest.getSelectionRequestId());

		MDC.remove("logFileName");
	}

	private UniverseSelectionRequest createUniverseSelectionRequest(String universeRefCode, Date startDate, Date endDate, Job job) {
		UniverseRef universeRef = universeRefRepository.findByCode(universeRefCode);
		if (universeRef == null) {
			throw new RuntimeException("No UniverseRef for code " + universeRefCode);
		}

		String userId = securityService.getUserId();
		Date now = new Date();

		SelectionRequest selectionRequest = new SelectionRequest();
		selectionRequest.setSelectionRequestTypeRef(selectionRequestTypeRefRepository.findByCode(SelectionRequestTypeRef.UNIVERSE));
		selectionRequest.setCreatedBy(userId);
		selectionRequest.setCreatedTs(now);
		selectionRequest = selectionRequestRepository.save(selectionRequest);

		UniverseSelectionRequest universeSelectionRequest = new UniverseSelectionRequest();
		universeSelectionRequest.setUniverseRef(universeRef);
		universeSelectionRequest.setStartDt(startDate);
		universeSelectionRequest.setEndDt(endDate);
		universeSelectionRequest.setJob(job);
		universeSelectionRequest.setSelectionRequest(selectionRequest);
		universeSelectionRequest.setCreatedBy(userId);
		universeSelectionRequest.setCreatedTs(now);
		universeSelectionRequest = universeSelectionRequestRepository.save(universeSelectionRequest);

		selectionRequest.setUniverseSelectionRequest(universeSelectionRequest);

		return universeSelectionRequest;
	}

	private Iterable<ScoringModelContext> buildScoringModelContextList(List<ScoringModelVersion> scoringModelVersions) {
		ArrayList<ScoringModelContext> scoringModelContexts = new ArrayList<ScoringModelContext>();

		// initially populate the context list with the scoring models from the universe, next we'll add dependencies
		for (ScoringModelVersion scoringModelVersion : scoringModelVersions) {
			logger.debug("Adding initial scoring model " + scoringModelVersion.getScoringModel().getDescription() + ", version " + scoringModelVersion.getModelVerNum());

			ScoringModelContext scoringModelContext = new ScoringModelContext();
			scoringModelContext.scoringModelVersion = scoringModelVersion;
			scoringModelContext.createLoanSelectionPendings = true;
			scoringModelContexts.add(scoringModelContext);
		}

		// scan each model for factors referencing another scoring model and add that referenced model to the list (if not already added)
		// we add new model depdencies to the *start* of the list to ensure they're run before they're dependents
		// this means our model scan must iterate back-to-front to ensure we hit any newly-added dependent models

		for (int index = scoringModelContexts.size() - 1; index >= 0; /* index = nextIndex */ ) {
			int nextIndex = index - 1;	// store next index since it might change

			ScoringModelContext scoringModelContext = scoringModelContexts.get(index);
			ScoringModelVersion scoringModelVersion = scoringModelContext.scoringModelVersion;
			ScoringModel scoringModel = scoringModelVersion.getScoringModel();

			logger.debug("Scanning factors for scoring model " + scoringModel.getDescription());
			for (ScoringModelVersionFactor scoringModelVersionFactor : scoringModelVersion.getScoringModelVersionFactors()) {
				ScoringFactor factor = scoringModelVersionFactor.getScoringFactor();
				logger.debug("\tFactor: " + factor.getFactorAttributeName() + ", weight " + scoringModelVersionFactor.getWeight());
				ScoringModel factorScoringModel = factor.getScoringModel();

				if (factorScoringModel != null) {
					logger.debug("\tFactor " + factor.getFactorAttributeName() + " references scoring model " + factorScoringModel.getDescription());

					// check if this model is already in our list
					String factorScoringModelCode = factorScoringModel.getCode();
					boolean found = false;
					for (int referencedIndex = 0; referencedIndex < scoringModelContexts.size(); referencedIndex++) {
						String existingScoringModelCode = scoringModelContexts.get(referencedIndex).scoringModelVersion.getScoringModel().getCode();

						// is this model already in the list?
						if (existingScoringModelCode.equals(factorScoringModelCode)) {
							found = true;

							// [index] depends on [referencedIndex], so [index] must come *after* [referencedIndex] in the list
							// check if this is not the case
							if (index < referencedIndex) {
								logger.debug("\t\tReferenced scoring model " + factorScoringModel.getDescription() + " is already in the list, but comes after " + scoringModel.getDescription() + "; rearranging");
								// move [index] to be right after [referencedIndex]
								scoringModelContexts.add(referencedIndex + 1, scoringModelContext);
								scoringModelContexts.remove(index);
								// we must update index for subsequent loop iterations
								index = referencedIndex + 1;
								// index i remains unchanged since we've already loaded the object and the list hasn't changed size
							} else {
								logger.debug("\t\tReferenced scoring model " + factorScoringModel.getDescription() + " is already in the list, no reordering needed");
							}

							break;
						}
					}
					if (!found) {
						logger.debug("\t\tReferenced scoring model " + factorScoringModel.getDescription() + " is not in list; adding");
						ScoringModelContext referencedScoringModelContext = new ScoringModelContext();
						referencedScoringModelContext.scoringModelVersion = scoringModelVersionRepository.findActiveByScoringModelId(factorScoringModel.getScoringModelId());
						scoringModelContexts.add(0, referencedScoringModelContext);
						// adjust indices since we've just added an element
						index++;
						nextIndex++;
					}
				}
			}

			index = nextIndex;
		}
		logger.debug("...done building scoring model list");

		return scoringModelContexts;
	}

	private void scoreLoanSelectionCaseSummaries(
		List<LoanSelectionCaseSummary> loanSelectionCaseSummaries,
		List<ScoringModelVersion> scoringModelVersions,
		SelectionRequest selectionRequest,
		Integer maxCasesToSelect,
		Job job
	) {
		String userId = securityService.getUserId();
		Date now = new Date();

		logger.debug("Building scoring model list...");
		Iterable<ScoringModelContext> scoringModelContexts = buildScoringModelContextList(scoringModelVersions);
		Map<String, ScoringModelContext> scoringModelCodeToContext = Maps.uniqueIndex(scoringModelContexts, ctx -> ctx.scoringModelVersion.getScoringModel().getCode());

		logger.debug("Final scoring model version list:");
		for (ScoringModelContext scoringModelContext : scoringModelContexts) {
			ScoringModelVersion scoringModelVersion = scoringModelContext.scoringModelVersion;
			logger.debug("\t" + scoringModelVersion.getScoringModel().getDescription() + ": " + scoringModelVersion.getModelVerNum() + (scoringModelContext.createLoanSelectionPendings ? "*" : ""));
		}

		logger.debug("Loading existing underwriting case numbers");
		List<String> caseNumbers = Util.map(loanSelectionCaseSummaries, lscs -> lscs.getCaseNumber());
		Set<String> existingUnderwritingLoanSelectionCaseNumbers = new HashSet<String>();
		for (int start = 0; start < caseNumbers.size(); ) {
			int end = Math.min(start + 500, caseNumbers.size());

			existingUnderwritingLoanSelectionCaseNumbers.addAll(
				entityManager.createQuery(
					"select ls.caseNumber from LoanSelection ls where " +
						"(ls.reviewTypeRef.reviewTypeCd = '" + ReviewTypeCodes.UNDERWRITING + "') and " +
						"(ls.caseNumber in (:caseNumbers))",
					String.class
				)
				.setParameter("caseNumbers", caseNumbers.subList(start, end))
				.getResultList()
			);

			start = end;
		}

		logger.debug("Loading lender suppressions");
		Map<String, LenderSuppression> lenderIdToSuppression = Util.index(lenderSuppressionRepository.findAll(), x-> x.getMtgee5());

		logger.debug("Loading increased lender selections");
		Map<String, LenderIncreasedSelection> lenderIdToIncreasedSelection = Util.index(
			lenderIncreasedSelectionRepository.findAll(),
			l -> l.getMtgee5()
		);
		logger.debug("Loading increased underwriter selections");
		Map<String, UnderwriterIncreasedSelection> underwriterIdToIncreasedSelection = Maps.uniqueIndex(
			underwriterIncreasedSelectionRepository.findAll(),
			u -> u.getUnderwriterId()
		);

		logger.debug("Running all scoring models");
		// we lazily insert LSCS records as needed
		// this tracks the case numbers we've created so we don't insert twice
		Map<String, Boolean> caseNumberToCreatedLoanSelectionCaseSummary = new HashMap<String, Boolean>();
		List<SelectionRulesParameters> selectionRulesParametersList = new ArrayList<SelectionRulesParameters>(loanSelectionCaseSummaries.size());
		int batchCount = 0;
		int totalSelected = 0;
		for (ScoringModelContext scoringModelContext : scoringModelContexts) {
			ScoringModelVersion scoringModelVersion = scoringModelContext.scoringModelVersion;

			logger.debug("Running scoring model " + scoringModelVersion.getScoringModel().getDescription() + ", version " + scoringModelVersion.getModelVerNum() + ":");

			// factor name -> weight
			Map<String, Double> factorNameToWeight = new HashMap<String, Double>();
			for (ScoringModelVersionFactor scoringModelVersionFactor : scoringModelVersion.getScoringModelVersionFactors()) {
				factorNameToWeight.put(scoringModelVersionFactor.getScoringFactor().getFactorAttributeName(), scoringModelVersionFactor.getWeight().doubleValue());
				logger.debug("\t" + scoringModelVersionFactor.getScoringFactor().getFactorAttributeName() + ": " + scoringModelVersionFactor.getWeight().doubleValue());
			}

			selectionRulesParametersList.clear();
			ReviewTypeRef reviewType = scoringModelVersion.getReviewTypeRef();
			for (LoanSelectionCaseSummary loanSelectionCaseSummary : loanSelectionCaseSummaries) {
				SelectionRulesParameters selectionRulesParameters = new SelectionRulesParameters();
				selectionRulesParameters.setReviewTypeRef(reviewType);
				selectionRulesParameters.setScoringModelContext(scoringModelContext);
				selectionRulesParameters.setScoringModelCodeToContext(scoringModelCodeToContext);
				selectionRulesParameters.setLoanSelectionCaseSummary(loanSelectionCaseSummary);
				selectionRulesParameters.setFactorNameToWeight(factorNameToWeight);
				selectionRulesParameters.setLenderIdToSuppression(lenderIdToSuppression);
				selectionRulesParameters.setLenderIdToIncreasedSelection(lenderIdToIncreasedSelection);
				selectionRulesParameters.setUnderwriterIdToIncreasedSelection(underwriterIdToIncreasedSelection);
				selectionRulesParameters.setSecureRandom(secureRandom);
				selectionRulesParameters.setExistingUnderwritingLoanSelectionCaseNumbers(existingUnderwritingLoanSelectionCaseNumbers);
				selectionRulesParametersList.add(selectionRulesParameters);
			}

			rulesService.fireRules(selectionRulesParametersList);

			// sort scores descending
			selectionRulesParametersList.sort((x, y) ->
				(x.getScore() < y.getScore()) ? 1 :
					((x.getScore() > y.getScore()) ? -1 : 0)
			);

			// write csv file
			String fileName = jbossLogRoot + "/" + 
				workflowLogDir + "/" + 
				scoringModelVersion.getScoringModel().getCode() + "-" +  
				new SimpleDateFormat(JOB_LOG_FILE_FORMAT).format(new Date()) + "-" + 
				job.getJobId() + ".csv";
			
			PrintWriter printWriter = null;
			try {
				printWriter = new PrintWriter(fileName);

				String factorNameCsv = String.join(",", Util.map(scoringModelVersion.getScoringModelVersionFactors(), smv -> smv.getScoringFactor().getFactorAttributeName()));;
				printWriter.write("Case Number,Score," + factorNameCsv + "\n");
				for (SelectionRulesParameters selectionRulesParameters : selectionRulesParametersList) {
					StringBuilder factorCsv = new StringBuilder();
					factorCsv.append(selectionRulesParameters.getLoanSelectionCaseSummary().getCaseNumber());
					factorCsv.append(",");
					factorCsv.append(selectionRulesParameters.getScore());
					for (ScoringModelVersionFactor scoringModelVersionFactor : scoringModelVersion.getScoringModelVersionFactors()) {
						factorCsv.append(",");
						Double score = selectionRulesParameters.getFactorNameToScore().get(scoringModelVersionFactor.getScoringFactor().getFactorAttributeName());
						if (score != null) {
							factorCsv.append(score);
						}
					}
					printWriter.write(factorCsv + "\n");
				}
			} catch (Exception e) {
				logger.error("Couldn't open file " + fileName, e);
				throw new RuntimeException(e);
			} finally {
				if (printWriter != null) {
					printWriter.close();
				}
			}

			double scoreThreshold = (double)scoringModelVersion.getModelScoreThreshold();
			double percentThreshold = (double)scoringModelVersion.getAllocationPct();
			logger.debug("Scanning scores, threshold " + scoreThreshold + ", allocation percent " + scoringModelVersion.getAllocationPct());
			int totalSelectedForThisModel = 0;
			for (int i = 0; i < selectionRulesParametersList.size(); i++) {
				SelectionRulesParameters selectionRulesParameters = selectionRulesParametersList.get(i);
				double score = selectionRulesParameters.getScore();
				scoringModelContext.modelScores.add(score);
				LoanSelectionCaseSummary loanSelectionCaseSummary = selectionRulesParameters.getLoanSelectionCaseSummary();
				scoringModelContext.caseNumberToModelScoreIndex.put(loanSelectionCaseSummary.getCaseNumber(), i);

				double percent = ((double)(i + 1) / (double)selectionRulesParametersList.size()) * 100.0;
				logger.debug(
					"\tCase " + loanSelectionCaseSummary.getCaseNumber() + " (" + (i+1) + "/" + selectionRulesParametersList.size() + "): " +
					"score " + score + " (" + ((score >= scoringModelVersion.getModelScoreThreshold()) ? "pass" : "fail") + "), " +
					"percent " + percent + " (" + ((percent <= percentThreshold) ? "pass" : "fail") + ")"
				);
				for (Map.Entry<String, Double> entry : selectionRulesParameters.getFactorNameToScore().entrySet()) {
					logger.debug("\t\t" + entry.getKey() + ": " + entry.getValue());
				}

				if (
					scoringModelContext.createLoanSelectionPendings &&
					(score >= scoreThreshold) &&
					(percent <= percentThreshold) &&
					(
						(maxCasesToSelect == null) ||
						(totalSelected < maxCasesToSelect)
					)
				) {
					// Check the duplicate loans for lender monitoring
					boolean proceed = true;
					LenderMonitoringSelectionRequest lenderMonitoringSelectionRequest = selectionRequest.getLenderMonitoringSelectionRequest();
					if (maxCasesToSelect != null && 
							lenderMonitoringSelectionRequest != null &&  
							aggregationService.isDuplicateLoanLenderMonitoringLoan(loanSelectionCaseSummary.getCaseNumber(), 
									lenderMonitoringSelectionRequest.getReviewTypeRef().getReviewTypeCd())) {
						logger.debug("Case : " + loanSelectionCaseSummary.getCaseNumber() + "is already selected");
						proceed = false;
					}
					
					if (proceed) {
						// insert the LSCS record if not already done
						if (caseNumberToCreatedLoanSelectionCaseSummary.get(loanSelectionCaseSummary.getCaseNumber()) == null) {
							if (scoringModelVersion.getScoringModel().getScoringModelTypeRef().getCode().equals(ScoringModelTypeCodes.NATIONAL_QC_SELECTION)) {
								loanSelectionCaseSummary = commonLoanSelectionService.cloneLoanSelectionCaseSummary(loanSelectionCaseSummary, selectionRequest);
							} else {
								loanSelectionCaseSummary.setSelectionRequest(selectionRequest);
								loanSelectionCaseSummary = loanSelectionCaseSummaryRepository.save(loanSelectionCaseSummary);
							}
							caseNumberToCreatedLoanSelectionCaseSummary.put(loanSelectionCaseSummary.getCaseNumber(), true);

							ModelScore modelScore = new ModelScore();
							modelScore.setScoringModelVersion(scoringModelVersion);
							modelScore.setLoanSelectionCaseSummary(loanSelectionCaseSummary);
							modelScore.setModelScore((float)score);
							modelScore.setCreatedBy(userId);
							modelScore.setCreatedTs(now);
							modelScore.setAutoSelectionIndicator(job.getAutoSelectionIndicator());
							modelScore = modelScoreRepository.save(modelScore);

							++totalSelectedForThisModel;
							++totalSelected;
						}

						++batchCount;
						if (batchCount >= batchSize) {
							batchCount = 0;
							entityManager.flush();
						}
					}
				}
			}

			logger.debug(totalSelectedForThisModel + " cases selected for scoring model " + scoringModelVersion.getScoringModel().getDescription() + ", version " + scoringModelVersion.getModelVerNum() + ":");
		}

		logger.debug(totalSelected + " cases selected for all scoring models");

		entityManager.flush();
	}

	private void createLoanSelectionPendingsFromModelScores(String selectionRequestId, ScoringModelVersion scoringModelVersion) {
		logger.debug("Create pending loan selection records for scoring model " + scoringModelVersion.getScoringModel().getDescription() + ", version " + scoringModelVersion.getModelVerNum());

		String userId = securityService.getUserId();

		// it's unlikely, but possible that these cases reference non-existant lenders, so insert dummy records for any that do
		// underwriting lenders
		int dummyLendersCount = entityManager.createNativeQuery(
			"INSERT INTO LENDER(LENDER_ID, NAME, ACTIVE_IND, DUMMY_IND, CREATED_BY, CREATED_TS) " +
			"SELECT " +
				"LSCS.UNDRWRTING_MTGEE5, " +
				"'<Unknown Lender ' + CAST(LSCS.UNDRWRTING_MTGEE5 AS CHAR) + '>', " +
				"'Y', " +
				"'Y', " +
				":userId, " +
				"CURRENT_TIMESTAMP " +
			"FROM (SELECT DISTINCT UNDRWRTING_MTGEE5 FROM LOAN_SELECTION_CASE_SUMMARY WHERE (SELECTION_REQUEST_ID = :selectionRequestId) AND (UNDRWRTING_MTGEE5 IS NOT NULL)) LSCS " +
			"LEFT JOIN LENDER L ON (L.LENDER_ID = LSCS.UNDRWRTING_MTGEE5) " +
			"WHERE (L.LENDER_ID IS NULL)"
		)
		.setParameter("selectionRequestId", selectionRequestId)
		.setParameter("userId", userId)
		.executeUpdate();

		// servicing lenders
		dummyLendersCount += entityManager.createNativeQuery(
			"INSERT INTO LENDER(LENDER_ID, NAME, ACTIVE_IND, DUMMY_IND, CREATED_BY, CREATED_TS) " +
			"SELECT " +
				"LSCS.SRVCR_MTGEE5_A43, " +
				"'<Unknown Lender ' + CAST(LSCS.SRVCR_MTGEE5_A43 AS CHAR) + '>', " +
				"'Y', " +
				"'Y', " +
				":userId, " +
				"CURRENT_TIMESTAMP " +
			"FROM (SELECT DISTINCT SRVCR_MTGEE5_A43 FROM LOAN_SELECTION_CASE_SUMMARY WHERE (SELECTION_REQUEST_ID = :selectionRequestId) AND (SRVCR_MTGEE5_A43 IS NOT NULL)) LSCS " +
			"LEFT JOIN LENDER L ON (L.LENDER_ID = LSCS.SRVCR_MTGEE5_A43) " +
			"WHERE (L.LENDER_ID IS NULL)"
		)
		.setParameter("selectionRequestId", selectionRequestId)
		.setParameter("userId", userId)
		.executeUpdate();

		if (dummyLendersCount > 0) {
			logger.debug("Created " + dummyLendersCount + " dummy LENDER records");
		}

		String createLoanSelectionPendingSql =
			"INSERT INTO LOAN_SELECTION_PENDING(" +
				"LOAN_SELECTION_PENDING_ID, " +
				"SELECTION_ID, " +
				"CASE_NUMBER, " +
				"AUTO_SELECTION_INDICATOR, " +
				"MTGEE5, " +
				"REVIEW_TYPE_ID, " +
				"PRODUCT_TYPE_ID, " +
				"SELECTION_REASON_ID, " +
				"REVIEW_SCOPE_ID, " +
				"BATCH_ID, " +
				"BORR_NAME, " +
				"ENDRSMNT_DT, " +
				"PROP_ADDR_1, " +
				"CS_ESTAB_DT, " +
				"SELECTION_REQUEST_ID, " +
				"SELECTION_DT, " +
				"CREATED_BY, " +
				"CREATED_TS";
		createLoanSelectionPendingSql +=
			") " +
			"SELECT " +
				"NEWID(), " +
				"MS.SELECTION_ID, " +
				"LSCS.CASE_NUMBER, " +
				"MS.AUTO_SELECTION_INDICATOR, " +
				"L.LENDER_ID, " +
				"SMV.DEFAULT_REVIEW_TYPE_ID, " +
				"(SELECT PRODUCT_TYPE_ID FROM PRODUCT_TYPE_REF WHERE (PRODUCT_TYPE_CD = LSCS.PROD_TYPE)), " +
				"SR.SELECTION_REASON_ID, " +
				"SMV.REVIEW_SCOPE_ID, " +
				"B.BATCH_ID, " +
				"LSCS.BORR_1_NAME, " +
				"LSCS.ENDRSMNT_DT, " +
				"LSCS.PROP_ADDR_1, " +
				"LSCS.CS_ESTAB_DT, " +
				"LSCS.SELECTION_REQUEST_ID, " +
				"CURRENT_TIMESTAMP, " +
				":userId, " +
				"CURRENT_TIMESTAMP ";
		createLoanSelectionPendingSql +=
			"FROM MODEL_SCORE MS " +
			"INNER JOIN LOAN_SELECTION_CASE_SUMMARY LSCS ON (LSCS.SELECTION_ID = MS.SELECTION_ID) " +
			"INNER JOIN SCORING_MODEL_VERSION SMV ON (SMV.SCORING_MODEL_VERSION_ID = MS.SCORING_MODEL_VERSION_ID) " +
			"INNER JOIN SCORING_MODEL SM ON (SM.SCORING_MODEL_ID = SMV.SCORING_MODEL_ID) " +
			"INNER JOIN SELECTION_REASON SR ON (SR.SELECTION_REASON_ID = SM.SELECTION_REASON_ID) " +
			"INNER JOIN REVIEW_TYPE_REF RT ON (RT.REVIEW_TYPE_ID = SMV.DEFAULT_REVIEW_TYPE_ID) " +
			"LEFT JOIN BATCH B ON B.SELECTION_REQUEST_ID = LSCS.SELECTION_REQUEST_ID " +
			"INNER JOIN LENDER L ON (L.LENDER_ID = (CASE " +
				"WHEN (RT.REVIEW_TYPE_CD IN ('" + ReviewTypeCodes.UNDERWRITING + "', '" + ReviewTypeCodes.COMPREHENSIVE + "')) THEN LSCS.UNDRWRTING_MTGEE5 " +
				"WHEN (RT.REVIEW_TYPE_CD = '" + ReviewTypeCodes.SERVICING + "') THEN LSCS.SRVCR_MTGEE5_A43 " +
			"END)) ";
		createLoanSelectionPendingSql +=
			"WHERE " +
				"(LSCS.SELECTION_REQUEST_ID = :selectionRequestId) AND " +
				"(SMV.SCORING_MODEL_VERSION_ID = :scoringModelVersionId) " +
				"ORDER BY MS.MODEL_SCORE DESC ";

		int loanSelectionPendingCount = entityManager.createNativeQuery(createLoanSelectionPendingSql)
		.setParameter("selectionRequestId", selectionRequestId)
		.setParameter("userId", userId)
		.setParameter("scoringModelVersionId", scoringModelVersion.getScoringModelVersionId())
		.executeUpdate();

		logger.debug("Created " + loanSelectionPendingCount + " LOAN_SELECTION_PENDING records");
		entityManager.flush();
	}

	@Transactional
	public void fillRemainingMonthlyCapacity(Job job) {
		int totalAvailableCapacity = this.availableCapacity(job);
		if(totalAvailableCapacity > 0) {
			final Calendar calendar = Calendar.getInstance();
	        final Date today = calendar.getTime();
			calendar.add(Calendar.MONTH, -fillRemainingCapRetrieveNumOfMonthsData);
			final Date startDate = calendar.getTime();
			UniverseRef universeRef = universeRefRepository.findByCode(UniverseCodes.ENDORSEMENT);
			runUniverseSelectionModel(universeRef.getCode(), startDate, today, job, totalAvailableCapacity);
		}
	}

	private int availableCapacity(Job job) {
		List<ReviewLocation> reviewLocations = reviewLocationRepository.findAll();
		Map<String, ReviewLocation> reviewLocationByReviewLocationId = Maps.uniqueIndex(reviewLocations, rl -> rl.getReviewLocationId());
		boolean isAutoJob = job.getAutoSelectionIndicator().equals('Y') ? true : false;
		List<ReviewLocationIdLoanSelectionCount> reviewLocationIdLoanSelectionCounts = null;
		if (isAutoJob) {
			reviewLocationIdLoanSelectionCounts = loanSelectionRepository.findReviewLocationLoanSelectionCounts(DateUtils.getCurrentMonth()-1);
		} else {
			reviewLocationIdLoanSelectionCounts = loanSelectionRepository.findReviewLocationLoanSelectionCounts(DateUtils.getCurrentMonth());
		}

		// reviewLocationId -> remaining capacity (total - used)
		HashMap<String, Integer> remainingCapacityByReviewLocationId = new HashMap<String, Integer>();
		for (ReviewLocationIdLoanSelectionCount reviewIdLoanSelectionCount : reviewLocationIdLoanSelectionCounts) {
			Integer capacity = reviewLocationByReviewLocationId.get(reviewIdLoanSelectionCount.reviewLocationId).getCommittedMonthlyCapacity();
		    remainingCapacityByReviewLocationId.put(reviewIdLoanSelectionCount.reviewLocationId, capacity - reviewIdLoanSelectionCount.loanSelectionCount);
		}

		int availCapacity = 0;
		for (Integer count: remainingCapacityByReviewLocationId.values()) {
			availCapacity += count;
		}

		logger.debug("Total Available Capacity at al Review Locations : " + availCapacity);
		return availCapacity;
	}

	@Transactional
	public ManualSelectionRequest createManualSelectionRequest(ManualSelectionDTO manualSelectionDTO) {
		String userId = securityService.getUserId();
		Date now = new Date();

		if (manualSelectionDTO.getReviewType() == null) {
			throw new BadRequestException("Review type is required");
		}
		ReviewTypeRef reviewType = reviewTypeRefRepository.findByReviewTypeCd(manualSelectionDTO.getReviewType());
		if (reviewType == null) {
			throw new BadRequestException("No ReviewTypeRef for reviewTypeId " + manualSelectionDTO.getReviewType());
		}

		if (manualSelectionDTO.getSelectionReason() == null) {
			throw new BadRequestException("selectionReason is required");
		}
		if (!(
			manualSelectionDTO.getSelectionReason().equals(SelectionReasonCodes.FHA_MANUAL) ||
			manualSelectionDTO.getSelectionReason().equals(SelectionReasonCodes.OIG_AUDIT) ||
			manualSelectionDTO.getSelectionReason().equals(SelectionReasonCodes.REVIEW_LOCATION_QC) ||
			manualSelectionDTO.getSelectionReason().equals(SelectionReasonCodes.TEST_CASE)
		)) {
			throw new BadRequestException("FHA Manual selection must have selection reason: FHA_MANUAL, OIG_AUDIT, REVIEW_LOCATION_QC, or TEST_CASE");
		}
		SelectionReason selectionReason = selectionReasonRepository.findByCode(manualSelectionDTO.getSelectionReason());
		if (selectionReason == null) {
			throw new RuntimeException("No SelectionReason for code " + manualSelectionDTO.getSelectionReason());
		}

		SelectionSubReasonRef selectionSubReason = null;
		if (manualSelectionDTO.getSelectionSubReason() != null) {
			selectionSubReason = selectionSubReasonRefRepository.findByCode(manualSelectionDTO.getSelectionSubReason());
			if (selectionSubReason == null) {
				throw new BadRequestException("No SelectionSubReasonRef for code " + manualSelectionDTO.getSelectionSubReason());
			}
		}

		if (manualSelectionDTO.getReviewLocation() == null) {
			throw new BadRequestException("Review location is required");
		}
		ReviewLocation reviewLocation = reviewLocationRepository.findOne(manualSelectionDTO.getReviewLocation());
		if (reviewLocation == null) {
			throw new BadRequestException("No ReviewLocation with reviewLocationId " + manualSelectionDTO.getReviewLocation());
		}

		SelectionRequestTypeRef manualSelectionRequestType = selectionRequestTypeRefRepository.findByCode(SelectionRequestTypeRef.FHA_MANUAL);
		if (manualSelectionRequestType == null) {
			throw new RuntimeException("No SelectionRequestTypeRef for code " + SelectionRequestTypeRef.FHA_MANUAL);
		}

		SelectionRequest selectionRequest = new SelectionRequest();
		selectionRequest.setSelectionRequestTypeRef(manualSelectionRequestType);
		selectionRequest.setCreatedBy(userId);
		selectionRequest.setCreatedTs(now);
		selectionRequest = selectionRequestRepository.save(selectionRequest);

		ManualSelectionRequest manualSelectionRequest = new ManualSelectionRequest();
		manualSelectionRequest.setSelectionRequest(selectionRequest);
		manualSelectionRequest.setSelectionReason(selectionReason);
		manualSelectionRequest.setSelectionSubReasonRef(selectionSubReason);
		manualSelectionRequest.setReviewTypeRef(reviewType);
		manualSelectionRequest.setReviewLocation(reviewLocation);
		manualSelectionRequest.setCreatedBy(userId);
		manualSelectionRequest.setCreatedTs(now);
		manualSelectionRequest = manualSelectionRequestRepository.save(manualSelectionRequest);

		selectionRequest.setManualSelectionRequest(manualSelectionRequest);

		ReviewScopeRef reviewScopeRef = null;
		if (selectionReason.getCode().equals(SelectionReasonCodes.OIG_AUDIT)) {
			reviewScopeRef = reviewScopeRefRepository.findByCode(ReviewScopeCodes.LIMITED);
		} else {
			reviewScopeRef = reviewScopeRefRepository.findByCode(ReviewScopeCodes.FULL);
		}

		// an accumulator for all test case reviews so we can assign them right now once created
		List<Review> testCaseReviews = new ArrayList<Review>();

		for (ReviewRequestByLenderDTO reviewRequestByLenderDTO : manualSelectionDTO.getCasesForReviewByLender()) {
			Batch batch = null;
			List<Personnel> batchTeamPersonnel = null;

			BatchDTO batchDTO = reviewRequestByLenderDTO.getBatchInfo();
			if (batchDTO != null) {
				Lender lender = lenderService.getLender(reviewRequestByLenderDTO.getLenderId());
				if (lender == null) {
					throw new BadRequestException("Lender " + reviewRequestByLenderDTO.getLenderId() + " doesn't exist");
				}

				if (selectionReason.getCode().equals(SelectionReasonCodes.REVIEW_LOCATION_QC)) {
					throw new ConflictException("Batches aren't allowed for review location QC");
				}

				batch = commonBatchService.createBatch(
					selectionRequest,
					lender,
					reviewType,
					selectionReason,
					reviewLocation,
					(batchDTO.getRequestOperationalReview() != null) && batchDTO.getRequestOperationalReview(),
					(batchDTO.getRequestOperationalDocuments() != null) && batchDTO.getRequestOperationalDocuments(),
					batchDTO.getOperationalReviewGuidance(),
					batchDTO.getSecondaryId()
				);

				if (batchDTO.getBatchOwner() == null) {
					throw new BadRequestException("Batch owner is required");
				}
				if ((batchDTO.getBatchTeamMembers() == null) || batchDTO.getBatchTeamMembers().isEmpty()) {
					throw new BadRequestException("At least one batch team member is required");
				}
				commonBatchService.assignBatch(batch, batchDTO.getBatchOwner(), batchDTO.getBatchTeamMembers());

				if (
					(batch.getOperationalReviewInd() == 'Y') &&
					(batch.getRequestOperationalDocumentsInd() == 'N')
				) {
					Review review = commonReviewService.createOperationalReview(batch);
					commonReviewService.assignReviewLevel(review.getReviewLevels().iterator().next(), batch.getOwnerPersonnel());
				}

				// used below when assigning reviews
				batchTeamPersonnel = new ArrayList<Personnel>();
				for (BatchPersonnel batchPersonnel : batch.getBatchPersonnels()) {	// TODO: rename Personnels -> Personnel in reveng.xml
					batchTeamPersonnel.add(batchPersonnel.getPersonnel());
				}
			}

			if (selectionReason.getCode().equals(SelectionReasonCodes.TEST_CASE)) {
				List<Review> batchReviews = new ArrayList<Review>();

				List<LoanSelectionCaseSummary> loanSelectionCaseSummaries = caseUniverseService.getLoanSelectionCaseSummaries(reviewRequestByLenderDTO.getCases());
				Map<String, LoanSelectionCaseSummary> loanSelectionCaseSummaryIndex = Maps.uniqueIndex(loanSelectionCaseSummaries, l -> l.getCaseNumber());

				for (String caseNumber : reviewRequestByLenderDTO.getCases()) {
					LoanSelectionCaseSummary loanSelectionCaseSummary = loanSelectionCaseSummaryIndex.get(caseNumber);
					if (loanSelectionCaseSummary == null) {
						throw new BadRequestException("Case " + caseNumber + " doesn't exist");
					}

					loanSelectionCaseSummary = commonLoanSelectionService.createLoanSelectionCaseSummary(loanSelectionCaseSummary, selectionRequest);

					LoanSelection loanSelection = commonLoanSelectionService.createLoanSelection(
						loanSelectionCaseSummary,
						batch,
						reviewType,
						selectionReason,
						reviewScopeRef,
						reviewLocation,
						null
					);
					loanSelection.setLoanSelectionStatusRef(loanSelectionStatusRefRepository.findByCode(LoanSelectionStatusCodes.RECEIVED));

					Review review = commonReviewService.createReview(loanSelection);

					if (batch != null) {
						ReviewLevel reviewLevel = review.getReviewLevels().iterator().next();
						List<Personnel> passingPersonnel = assignmentService.runAssignmentModel(reviewLevel, batchTeamPersonnel, null);
						if (!passingPersonnel.isEmpty()) {
							commonReviewService.assignReviewLevel(reviewLevel, passingPersonnel.get(0));
						} else {
							commonExceptionService.createReviewLevelAssignmentException(reviewLevel);
						}
					}

					testCaseReviews.add(review);
				}
				if (batch != null) {
					// batch test case goes right to UNDER REVIEW
					batchReviews.addAll(testCaseReviews);
					batch.setBatchStatusRef(batchStatusRefRepository.findByCode(BatchStatusRef.UNDER_REVIEW));
				}

			} else if (selectionReason.getCode().equals(SelectionReasonCodes.REVIEW_LOCATION_QC)) {
				//for each selected case, get the most recent completed non-QC review, clone loanSelectionCaseSummary and loanSelection
				for (String caseNumber : reviewRequestByLenderDTO.getCases()) {
					// PERF TODO: move out of loop, a lot of this could be done as single set-based queries
					Review completedReview = reviewRepository.findLatestNonQcByCaseNumberAndReviewTypeCode(caseNumber, manualSelectionDTO.getReviewType());

					reviewScopeRef = completedReview.getReviewScopeRef();

					LoanSelectionCaseSummary loanSelectionCaseSummary = commonLoanSelectionService.cloneLoanSelectionCaseSummary(
						completedReview.getLoanSelection().getLoanSelectionCaseSummary(),
						selectionRequest
					);

					LoanSelection loanSelection = commonLoanSelectionService.createLoanSelection(
						loanSelectionCaseSummary,
						batch,
						reviewType,
						selectionReason,
						reviewScopeRef,
						reviewLocation,
						completedReview
					);
					loanSelection.setLoanSelectionStatusRef(loanSelectionStatusRefRepository.findByCode(LoanSelectionStatusCodes.RECEIVED));

					Review review = commonReviewService.createReview(loanSelection);

					testCaseReviews.add(review);

					// qc is never batch, so no need to add
				}
			} else {
				List<LoanSelectionCaseSummary> loanSelectionCaseSummaries = caseUniverseService.getLoanSelectionCaseSummaries(reviewRequestByLenderDTO.getCases());
				Map<String, LoanSelectionCaseSummary> loanSelectionCaseSummaryIndex = Maps.uniqueIndex(loanSelectionCaseSummaries, l -> l.getCaseNumber());

				for (String caseNumber : reviewRequestByLenderDTO.getCases()) {
					LoanSelectionCaseSummary loanSelectionCaseSummary = loanSelectionCaseSummaryIndex.get(caseNumber);
					if (loanSelectionCaseSummary == null) {
						throw new BadRequestException("Case " + caseNumber + " doesn't exist");
					}

					loanSelectionCaseSummary = commonLoanSelectionService.cloneLoanSelectionCaseSummary(
						loanSelectionCaseSummary,
						selectionRequest
					);

					commonLoanSelectionService.createLoanSelectionPending(
						loanSelectionCaseSummary,
						batch,
						reviewType,
						selectionReason,
						reviewScopeRef,
						reviewLocation
					);
				}
			}
		}

		// run assignment models on test case reviews
		if (
			selectionReason.getCode().equals(SelectionReasonCodes.TEST_CASE) ||
			selectionReason.getCode().equals(SelectionReasonCodes.REVIEW_LOCATION_QC)
		) {
			for (Review review : testCaseReviews) {	// TODO: do this is as a batch, instead of one-by-one
				ReviewLevel reviewLevel = review.getReviewLevels().iterator().next();
				List<Personnel> personnel = assignmentService.runAssignmentModelForCurrentReviewLocation(reviewLevel);
				if (!personnel.isEmpty()) {
					commonReviewService.assignReviewLevel(reviewLevel, personnel.get(0));
				} else {
					commonExceptionService.createReviewLevelAssignmentException(reviewLevel);
				}
			}
		}
		return manualSelectionRequest;
	}

	@Transactional
	public LenderMonitoringSelectionRequest createLenderMonitoringSelectionRequest(LenderMonitoringDTO lenderMonitoringDTO) {
		if (lenderMonitoringDTO.getLenderId() == null) {
			throw new BadRequestException("Lender ID is required");
		}
		Lender lender = lenderService.getLender(lenderMonitoringDTO.getLenderId());
		if (lender == null) {
			throw new BadRequestException("No lender with lenderId " + lenderMonitoringDTO.getLenderId());
		}

		if (lenderMonitoringDTO.getLocationId() == null) {
			throw new BadRequestException("Review location is required");
		}
		ReviewLocation reviewLocation = reviewLocationRepository.findOne(lenderMonitoringDTO.getLocationId());
		if (reviewLocation == null) {
			throw new BadRequestException("No ReviewLocation with reviewLocationId " + lenderMonitoringDTO.getLocationId());
		}
		String reviewLocationId = reviewLocation.getReviewLocationId();

		if (lenderMonitoringDTO.getReviewTypeId() == null) {
			throw new BadRequestException("Review type is required");
		}
		ReviewTypeRef reviewType = reviewTypeRefRepository.findByReviewTypeCd(lenderMonitoringDTO.getReviewTypeId());
		if (reviewType == null) {
			throw new BadRequestException("No ReviewTypeRef for reviewTypeId " + lenderMonitoringDTO.getReviewTypeId());
		}

		if (lenderMonitoringDTO.getRequestFromId() == null) {
			throw new BadRequestException("File delivery location ID is required");
		}
		FileDeliveryLocationRef fileDeliveryLocation = fileDeliveryLocationRefRepository.findByCode(lenderMonitoringDTO.getRequestFromId());
		if (fileDeliveryLocation == null) {
			throw new BadRequestException("No FileDeliveryLocationRef for fileDeliveryLocationId " + lenderMonitoringDTO.getRequestFromId());
		}

		if (lenderMonitoringDTO.getLoanTypeId() == null) {
			throw new BadRequestException("File delivery location ID is required");
		}
		LoanTypeRef loanType = loanTypeRefRepository.findByCode(lenderMonitoringDTO.getLoanTypeId());
		if (loanType == null) {
			throw new BadRequestException("No LoanTypeRef for loanTypeId " + lenderMonitoringDTO.getLoanTypeId());
		}

		if (lenderMonitoringDTO.getCaseCount() == null) {
			throw new BadRequestException("Case count is required");
		}
		if (lenderMonitoringDTO.getCaseCount().intValue() <= 0) {
			throw new BadRequestException("Case count must be > 0");
		}

		if (lenderMonitoringDTO.getEndorsementStartDate() == null) {
			throw new BadRequestException("Start date is required");
		}
		if (lenderMonitoringDTO.getEndorsementEndDate() == null) {
			throw new BadRequestException("End date is required");
		}
		if (lenderMonitoringDTO.getEndorsementStartDate().compareTo(lenderMonitoringDTO.getEndorsementEndDate()) > 0) {
			throw new BadRequestException("Start date must be >= end date");
		}

		if (lenderMonitoringDTO.getOperationalReview() == null) {
			throw new BadRequestException("Operational review is required");
		}

		if (lenderMonitoringDTO.getBatchOwner() == null) {
			throw new BadRequestException("Batch owner is required");
		}
		Personnel batchOwner = personnelRepository.findOne(lenderMonitoringDTO.getBatchOwner());
		if (batchOwner == null) {
			throw new BadRequestException("No batch owner Personnel with personnelId " + lenderMonitoringDTO.getBatchOwner());
		}
		if (!batchOwner.getReviewLocation().getReviewLocationId().equals(reviewLocationId)) {
			throw new BadRequestException("Batch owner " + batchOwner.getPersonnelId() + " has different location (" + batchOwner.getReviewLocation().getReviewLocationId() + ") than review location (" + reviewLocationId + ")");
		}

		if ((lenderMonitoringDTO.getBatchTeamMembers() == null) || (lenderMonitoringDTO.getBatchTeamMembers().size() < 1)) {
			throw new BadRequestException("Must have at least one batch team member");
		}
		List<Personnel> batchTeamMembers = personnelRepository.findByPersonnelIdIn(lenderMonitoringDTO.getBatchTeamMembers());
		if (batchTeamMembers.size() != lenderMonitoringDTO.getBatchTeamMembers().size()) {
			throw new BadRequestException("One or more batch team members have invalid personnelIds");
		}
		for (Personnel batchTeamMember : batchTeamMembers) {
			if (!batchTeamMember.getReviewLocation().getReviewLocationId().equals(reviewLocationId)) {
				throw new BadRequestException("Batch team member " + batchTeamMember.getPersonnelId() + " has different location (" + batchTeamMember.getReviewLocation().getReviewLocationId() + ") than review location (" + reviewLocationId + ")");
			}
		}

		String username = securityService.getUserId();
		Date now = new Date();

		SelectionRequest selectionRequest = new SelectionRequest();
		selectionRequest.setSelectionRequestTypeRef(selectionRequestTypeRefRepository.findByCode(SelectionRequestTypeRef.LENDER_MONITORING));
		selectionRequest.setCreatedBy(username);
		selectionRequest.setCreatedTs(now);
		selectionRequest = selectionRequestRepository.save(selectionRequest);

		LenderMonitoringSelectionRequest lenderMonitoringSelectionRequest = new LenderMonitoringSelectionRequest();
		lenderMonitoringSelectionRequest.setSelectionRequest(selectionRequest);
		lenderMonitoringSelectionRequest.setLender(lender);
		lenderMonitoringSelectionRequest.setReviewLocation(reviewLocation);
		lenderMonitoringSelectionRequest.setReviewTypeRef(reviewType);
		lenderMonitoringSelectionRequest.setFileDeliveryLocationRef(fileDeliveryLocation);
		lenderMonitoringSelectionRequest.setLoanTypeRef(loanType);
		lenderMonitoringSelectionRequest.setSiteVisitDt(lenderMonitoringDTO.getDateOfSiteVisit());
		lenderMonitoringSelectionRequest.setCaseCount(lenderMonitoringDTO.getCaseCount().intValue());
		lenderMonitoringSelectionRequest.setStartDt(lenderMonitoringDTO.getEndorsementStartDate());
		lenderMonitoringSelectionRequest.setEndDt(lenderMonitoringDTO.getEndorsementEndDate());
		lenderMonitoringSelectionRequest.setCreatedBy(username);
		lenderMonitoringSelectionRequest.setCreatedTs(now);
		lenderMonitoringSelectionRequest = lenderMonitoringSelectionRequestRepository.save(lenderMonitoringSelectionRequest);

		selectionRequest.setLenderMonitoringSelectionRequest(lenderMonitoringSelectionRequest);

		Batch batch = commonBatchService.createBatch(
			selectionRequest,
			lender,
			reviewType,
			selectionReasonRepository.findByCode(SelectionReasonCodes.LENDER_MONITORING),
			reviewLocation,
			(lenderMonitoringDTO.getOperationalReview() != null) && lenderMonitoringDTO.getOperationalReview(),
			(lenderMonitoringDTO.getRequestOperationalDocuments() != null) && lenderMonitoringDTO.getRequestOperationalDocuments(),
			lenderMonitoringDTO.getOperationalReviewGuidance(),
			lenderMonitoringDTO.getSecondaryId()
		);

		commonBatchService.assignBatch(batch, lenderMonitoringDTO.getBatchOwner(), lenderMonitoringDTO.getBatchTeamMembers());

		if (
			(batch.getOperationalReviewInd() == 'Y') &&
			(batch.getRequestOperationalDocumentsInd() == 'N')
		) {
			Review review = commonReviewService.createOperationalReview(batch);
			commonReviewService.assignReviewLevel(review.getReviewLevels().iterator().next(), batch.getOwnerPersonnel());
		}

		jobService.createJob(
			JobTypeRef.LENDER_MONITORING_SELECTION,
			new String[] { JobParameter.SELECTION_REQUEST_ID, selectionRequest.getSelectionRequestId() },
			false
		);

		return lenderMonitoringSelectionRequest;
	}

	@Transactional
	public LenderSelfReportSelectionRequest createLenderSelfReportSelectionRequest(LenderSelfReportDTO lenderSelfReportDTO) {
		if (lenderSelfReportDTO.getReviewType() == null) {
			throw new BadRequestException("reviewType is required");
		}
		ReviewTypeRef reviewType = reviewTypeRefRepository.findByReviewTypeCd(lenderSelfReportDTO.getReviewType());
		if (reviewType == null) {
			throw new BadRequestException("Unknown review type: " + lenderSelfReportDTO.getReviewType());
		}
		if (reviewType.getReviewTypeCd().equals(ReviewTypeCodes.OPERATIONAL)) {
			throw new BadRequestException("Cannot create operational self reports");
		}

		QaModel activeQaModel = qaModelRepository.findActive();

		List<Defect> defects = null;
		if ((lenderSelfReportDTO.getDefectAreas() != null) && !lenderSelfReportDTO.getDefectAreas().isEmpty()) {
			defects = defectRepository.findByDefectIdIn(lenderSelfReportDTO.getDefectAreas());
			if (defects.size() != lenderSelfReportDTO.getDefectAreas().size()) {
				throw new BadRequestException("One or more defectAreas had an unknown ID");
			}
			for (Defect defect : defects) {
				if (!defect.getQaModel().getQaModelId().equals(activeQaModel.getQaModelId())) {
					throw new BadRequestException("Defect " + defect.getDefectId() + " is not part of active QA model " + activeQaModel.getQaModelId());
				}
			}
		}

		if (lenderSelfReportDTO.getIsFraudDetected() == null) {
			lenderSelfReportDTO.setIsFraudDetected(false);
		}

		List<FraudTypeRef> fraudTypes = null;
		if ((lenderSelfReportDTO.getTypesOfFraud() != null) && !lenderSelfReportDTO.getTypesOfFraud().isEmpty()) {
			fraudTypes = fraudTypeRefRepository.findByFraudTypeIdIn(lenderSelfReportDTO.getTypesOfFraud());
			if (fraudTypes.size() != lenderSelfReportDTO.getTypesOfFraud().size()) {
				throw new BadRequestException("One or more typesOfFraud had an unknown ID");
			}
		}

		List<FraudParticipantRef> fraudParticipants = null;
		if (lenderSelfReportDTO.getFraudParticipants() != null && !lenderSelfReportDTO.getFraudParticipants().isEmpty()) {
			fraudParticipants = fraudParticipantRefRepository.findByFraudParticipantIdIn(lenderSelfReportDTO.getFraudParticipants());
			if (fraudParticipants.size() != lenderSelfReportDTO.getFraudParticipants().size()) {
				throw new BadRequestException("One or more fraudParticipants had an unknown ID");
			}
		}

		if (lenderSelfReportDTO.getIsCoveredUnderSettlement() == null) {
			lenderSelfReportDTO.setIsCoveredUnderSettlement(false);
		}

		if ((lenderSelfReportDTO.getCases() == null) || (lenderSelfReportDTO.getCases().size() < 1)) {
			throw new BadRequestException("At least one case is required");
		}

		// at this point we could check to see if any of these cases already have a review
		// they'll get filtered out later on in selection, but if we do it now and notify the lender then they'll know immediately

		List<LoanSelectionCaseSummary> loanSelectionCaseSummaries = caseUniverseService.getLoanSelectionCaseSummaries(lenderSelfReportDTO.getCases());
		Map<String, LoanSelectionCaseSummary> loanSelectionCaseSummaryIndex = Maps.uniqueIndex(loanSelectionCaseSummaries, l -> l.getCaseNumber());
		for (String caseNumber : lenderSelfReportDTO.getCases()) {
			LoanSelectionCaseSummary loanSelectionCaseSummary = loanSelectionCaseSummaryIndex.get(caseNumber);
			if (loanSelectionCaseSummary == null) {
				throw new BadRequestException("Case " + caseNumber + " doesn't exist");
			}
		}

		String userId = securityService.getUserId();
		Date now = new Date();

		SelectionRequest selectionRequest = new SelectionRequest();
		selectionRequest.setSelectionRequestTypeRef(selectionRequestTypeRefRepository.findByCode(SelectionRequestTypeRef.LENDER_SELF_REPORT));
		selectionRequest.setCreatedBy(userId);
		selectionRequest.setCreatedTs(now);
		selectionRequest = selectionRequestRepository.save(selectionRequest);

		LenderSelfReportSelectionRequest lenderSelfReportSelectionRequest = new LenderSelfReportSelectionRequest();
		lenderSelfReportSelectionRequest.setReviewTypeRef(reviewType);
		lenderSelfReportSelectionRequest.setSelectionRequest(selectionRequest);
		lenderSelfReportSelectionRequest.setFraudDetectedInd(lenderSelfReportDTO.getIsFraudDetected() ? 'Y' : 'N');
		lenderSelfReportSelectionRequest.setFindingsDescription(lenderSelfReportDTO.getDescriptionOfFindings());
		lenderSelfReportSelectionRequest.setCorrectiveActionsDescription(lenderSelfReportDTO.getDescriptionOfCorrectiveActions());
		lenderSelfReportSelectionRequest.setLoanCoveredUnderSettlemtnWithHudInd(lenderSelfReportDTO.getIsCoveredUnderSettlement() ? 'Y' : 'N');
		lenderSelfReportSelectionRequest.setCreatedBy(userId);
		lenderSelfReportSelectionRequest.setCreatedTs(now);
		lenderSelfReportSelectionRequest = lenderSelfReportSelectionRequestRepository.save(lenderSelfReportSelectionRequest);

		selectionRequest.setLenderSelfReportSelectionRequest(lenderSelfReportSelectionRequest);

		for (Defect defect : Util.emptyIfNull(defects)) {
			LenderSelfReportSelectionRequestDefect lenderSelfReportSelectionRequestDefect = new LenderSelfReportSelectionRequestDefect();
			LenderSelfReportSelectionRequestDefectId lenderSelfReportSelectionRequestDefectId = new LenderSelfReportSelectionRequestDefectId();
			lenderSelfReportSelectionRequestDefectId.setSelectionRequestId(lenderSelfReportSelectionRequest.getSelectionRequestId());
			lenderSelfReportSelectionRequestDefectId.setDefectId(defect.getDefectId());
			lenderSelfReportSelectionRequestDefect.setId(lenderSelfReportSelectionRequestDefectId);
			lenderSelfReportSelectionRequestDefect.setCreatedBy(userId);
			lenderSelfReportSelectionRequestDefect.setCreatedTs(now);
			lenderSelfReportSelectionRequestDefectRepository.save(lenderSelfReportSelectionRequestDefect);
		}

		for (FraudTypeRef fraudType : Util.emptyIfNull(fraudTypes)) {
			LenderSelfReportSelectionRequestFraudTypeRef lenderSelfReportSelectionRequestFraudType = new LenderSelfReportSelectionRequestFraudTypeRef();
			LenderSelfReportSelectionRequestFraudTypeRefId lenderSelfReportSelectionRequestFraudTypeId = new LenderSelfReportSelectionRequestFraudTypeRefId();
			lenderSelfReportSelectionRequestFraudTypeId.setSelectionRequestId(lenderSelfReportSelectionRequest.getSelectionRequestId());
			lenderSelfReportSelectionRequestFraudTypeId.setFraudTypeId(fraudType.getFraudTypeId());
			lenderSelfReportSelectionRequestFraudType.setId(lenderSelfReportSelectionRequestFraudTypeId);
			lenderSelfReportSelectionRequestFraudType.setCreatedBy(userId);
			lenderSelfReportSelectionRequestFraudType.setCreatedTs(now);
			lenderSelfReportSelectionRequestFraudTypeRefRepository.save(lenderSelfReportSelectionRequestFraudType);
		}

		for (FraudParticipantRef fraudParticipant : Util.emptyIfNull(fraudParticipants)) {
			LenderSelfReportSelectionRequestFraudParticipantRef lenderSelfReportSelectionRequestFraudParticipant = new LenderSelfReportSelectionRequestFraudParticipantRef();
			LenderSelfReportSelectionRequestFraudParticipantRefId lenderSelfReportSelectionRequestFraudParticipantId = new LenderSelfReportSelectionRequestFraudParticipantRefId();
			lenderSelfReportSelectionRequestFraudParticipantId.setSelectionRequestId(lenderSelfReportSelectionRequest.getSelectionRequestId());
			lenderSelfReportSelectionRequestFraudParticipantId.setFraudParticipantId(fraudParticipant.getFraudParticipantId());
			lenderSelfReportSelectionRequestFraudParticipant.setId(lenderSelfReportSelectionRequestFraudParticipantId);
			lenderSelfReportSelectionRequestFraudParticipant.setCreatedBy(userId);
			lenderSelfReportSelectionRequestFraudParticipant.setCreatedTs(now);
			lenderSelfReportSelectionRequestFraudParticipantRefRepository.save(lenderSelfReportSelectionRequestFraudParticipant);
		}

		SelectionReason lenderSelfReportSelectionReason = selectionReasonRepository.findByCode(SelectionReasonCodes.LENDER_SELF_REPORT);

		ReviewScopeRef limitedReviewScope = reviewScopeRefRepository.findByCode(ReviewScopeCodes.LIMITED);

		// we're allowed to have multiple lenders in a single self report, so group by lender ID and create multiple batches
		Multimap<String, LoanSelectionCaseSummary> lenderIdToLoanSelectionCaseSummaries = Util.multiindex(
			loanSelectionCaseSummaries,
			reviewType.getReviewTypeCd().equals(ReviewTypeCodes.UNDERWRITING) ?
				lscs -> lscs.getUndrwrtingMtgee5() :
				lscs -> lscs.getSrvcrMtgee5A43()
		);

		for (String lenderId : lenderIdToLoanSelectionCaseSummaries.keySet()) {
			Lender lender = lenderService.getOrCreateDummyLender(lenderId);
			Collection<LoanSelectionCaseSummary> lenderLoanSelectionCaseSummaries = lenderIdToLoanSelectionCaseSummaries.get(lenderId);
			for (LoanSelectionCaseSummary loanSelectionCaseSummary : lenderLoanSelectionCaseSummaries) {
				loanSelectionCaseSummary = commonLoanSelectionService.cloneLoanSelectionCaseSummary(loanSelectionCaseSummary, selectionRequest);
			}

			Batch batch = null;
			if (lenderLoanSelectionCaseSummaries.size() > 1) {
				batch = commonBatchService.createBatch(
					selectionRequest,
					lender,
					reviewType,
					lenderSelfReportSelectionReason,
					null,	// review location
					false,	// op review
					false,	// op review docs
					null,	// op guidance,
					null	// secondary id
				);

				commonExceptionService.createBatchDistributionException(batch);
			}

			for (LoanSelectionCaseSummary loanSelectionCaseSummary : lenderIdToLoanSelectionCaseSummaries.get(lenderId)) {
				loanSelectionCaseSummary = commonLoanSelectionService.cloneLoanSelectionCaseSummary(
					loanSelectionCaseSummary,
					selectionRequest
				);

				commonLoanSelectionService.createLoanSelectionPending(
					loanSelectionCaseSummary,
					batch,
					reviewType,
					lenderSelfReportSelectionReason,
					limitedReviewScope,
					null	// location
				);
			}
		}

		return lenderSelfReportSelectionRequest;
	}

}
