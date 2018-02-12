package gov.hud.lrs.common.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import gov.hud.lrs.common.config.GetCaseNamingStrategy;
import gov.hud.lrs.common.dto.MuleCaseGetDetailDTO;
import gov.hud.lrs.common.dto.MuleCaseGetOutputDTO;
import gov.hud.lrs.common.dto.MuleCaseGetOutputDTO.GetCaseResult;
import gov.hud.lrs.common.dto.MuleUniverseGetCaseDetailDTO;
import gov.hud.lrs.common.dto.MuleUniverseGetOutputDTO;
import gov.hud.lrs.common.dto.MuleUniverseGetOutputDTO.GetUniverseResult;
import gov.hud.lrs.common.entity.LoanSelectionCaseSummary;
import gov.hud.lrs.common.entity.LoanTypeRef;
import gov.hud.lrs.common.entity.ReviewTypeRef;
import gov.hud.lrs.common.security.FhacUser;
import gov.hud.lrs.common.security.SecurityService;
import gov.hud.lrs.common.util.StringFunctionsUtil;

@Service
public class CaseUniverseService {

	private Logger logger = LoggerFactory.getLogger(CaseUniverseService.class);

	@Value("${lrs.mule.monthly.uri}") private String muleMonthlyUri;
	@Value("${lrs.mule.monthly.rootUri}") private String muleMonthlyRootUri;
	@Value("${lrs.mule.monthly.useOAuth}") private boolean muleMonthlyUseOAuth;

	@Value("${lrs.mule.ec.uri}") private String muleEcUri;
	@Value("${lrs.mule.ec.rootUri}") private String muleEcRootUri;
	@Value("${lrs.mule.ec.useOAuth}") private boolean muleEcUseOAuth;

	@Value("${lrs.mule.epd.uri}") private String muleEpdUri;
	@Value("${lrs.mule.epd.rootUri}") private String muleEpdRootUri;
	@Value("${lrs.mule.epd.useOAuth}") private boolean muleEpdUseOAuth;

	@Value("${lrs.mule.lenderMonitoring.uri}") private String muleLenderMonitoringUri;
	@Value("${lrs.mule.lenderMonitoring.rootUri}") private String muleLenderMonitoringRootUri;
	@Value("${lrs.mule.lenderMonitoring.useOAuth}") private boolean muleLenderMonitoringUseOAuth;

	@Value("${lrs.mule.case.uri}") private String muleCaseUri;
	@Value("${lrs.mule.case.rootUri}") private String muleCaseRootUri;
	@Value("${lrs.mule.case.useOAuth}") private boolean muleCaseUseOAuth;

	@Value("${lrs.mule.batchCase.uri}") private String muleBatchCaseUri;
	@Value("${lrs.mule.batchCase.rootUri}") private String muleBatchCaseRootUri;
	@Value("${lrs.mule.batchCase.useOAuth}") private boolean muleBatchCaseUseOAuth;
	@Value("${lrs.mule.batchCase.batchSize}") private int muleBatchCaseSize;
	
	@Value("${lrs.mule.piiMask}") private boolean piiMask;

	@Autowired private SecurityService securityService;

	@Autowired private MuleClient muleClient;

	// could probably have an app-wide bean for this so we don't have to duplicate between here and dev mule service
	private ObjectMapper camelCaseToLowerCaseWithUnderscoresObjectMapper = new ObjectMapper();

	public static final String DATE_FORMAT_STRING = "yyyy-MM-dd";
	private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_STRING);

	private Executor executor = Executors.newFixedThreadPool(5);

	public CaseUniverseService() {
		camelCaseToLowerCaseWithUnderscoresObjectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
	}

	public List<String> getMonthlyEndorsementUniverseCaseNumbers(Date startDate, Date endDate) {
		Map<String, String> queryParameters = new HashMap<String, String>();
		queryParameters.put("start_date", dateFormat.format(startDate));
		queryParameters.put("end_date", dateFormat.format(endDate));
		ResponseEntity<MuleUniverseGetOutputDTO> responseEntity = muleClient.get(
			muleMonthlyRootUri + muleMonthlyUri,
			queryParameters,
			MuleUniverseGetOutputDTO.class,
			muleMonthlyUseOAuth
		);

		MuleUniverseGetOutputDTO muleUniverseGetOutputDTO = responseEntity.getBody();
		GetUniverseResult getUniverseResult = null;
		String statusCode = "";
		String statusMessage = "";
		if (muleUniverseGetOutputDTO != null) {
			getUniverseResult = muleUniverseGetOutputDTO.getGetUniverseResult();
			if (getUniverseResult != null) {
				statusCode = getUniverseResult.getStatusCode();
				statusMessage = getUniverseResult.getStatusMessage();
			}
		}

		if (!statusCode.equals("0")) {
			throw new RuntimeException("Mule get endorsement universe for " + startDate + " to " + endDate + " failed status code " + statusCode + ": " + statusMessage);
		}

		return parseCaseNumbers(responseEntity);
	}

	public List<String> getEarlyClaimUniverseCaseNumbers(Date startDate, Date endDate) {
		Map<String, String> queryParameters = new HashMap<String, String>();
		queryParameters.put("start_date", dateFormat.format(startDate));
		queryParameters.put("end_date", dateFormat.format(endDate));
		ResponseEntity<MuleUniverseGetOutputDTO> responseEntity = muleClient.get(
			muleEcRootUri + muleEcUri,
			queryParameters,
			MuleUniverseGetOutputDTO.class,
			muleEcUseOAuth
		);

		MuleUniverseGetOutputDTO muleUniverseGetOutputDTO = responseEntity.getBody();
		GetUniverseResult getUniverseResult = null;
		String statusCode = "";
		String statusMessage = "";
		if (muleUniverseGetOutputDTO != null) {
			getUniverseResult = muleUniverseGetOutputDTO.getGetUniverseResult();
			if (getUniverseResult != null) {
				statusCode = getUniverseResult.getStatusCode();
				statusMessage = getUniverseResult.getStatusMessage();
			}
		}

		if (!statusCode.equals("0")) {
			throw new RuntimeException("Mule get early claim universe for " + startDate + " to " + endDate + " failed with status code " + statusCode + ": " + statusMessage);
		}

		return parseCaseNumbers(responseEntity);
	}

	public List<String> getEarlyPendingDefaultUniverseCaseNumbers(Date startDate, Date endDate) {
		Map<String, String> queryParameters = new HashMap<String, String>();
		queryParameters.put("start_date", dateFormat.format(startDate));
		queryParameters.put("end_date", dateFormat.format(endDate));
		ResponseEntity<MuleUniverseGetOutputDTO> responseEntity = muleClient.get(
			muleEpdRootUri + muleEpdUri,
			queryParameters,
			MuleUniverseGetOutputDTO.class,
			muleEpdUseOAuth
		);

		MuleUniverseGetOutputDTO muleUniverseGetOutputDTO = responseEntity.getBody();
		GetUniverseResult getUniverseResult = null;
		String statusCode = "";
		String statusMessage = "";
		if (muleUniverseGetOutputDTO != null) {
			getUniverseResult = muleUniverseGetOutputDTO.getGetUniverseResult();
			if (getUniverseResult != null) {
				statusCode = getUniverseResult.getStatusCode();
				statusMessage = getUniverseResult.getStatusMessage();
			}
		}

		if (!statusCode.equals("0")) {
			throw new RuntimeException("Mule get early payment default universe for " + startDate + " to " + endDate + " failed with status code " + statusCode + ": " + statusMessage);
		}

		return parseCaseNumbers(responseEntity);
	}

	public List<String> getLenderMonitoringUniverseCaseNumbers(String lenderId, Date startDate, Date endDate, LoanTypeRef loanTypeRef, ReviewTypeRef reviewTypeRef) {
		Map<String, String> queryParameters = new HashMap<String, String>();
		queryParameters.put("mortgee_id", lenderId);
		queryParameters.put("review_type", reviewTypeRef.getDescription());
		queryParameters.put("start_date", dateFormat.format(startDate));
		queryParameters.put("end_date", dateFormat.format(endDate));
		queryParameters.put("Loan_Type", loanTypeRef.getCode());

		ResponseEntity<MuleUniverseGetOutputDTO> responseEntity = muleClient.get(
			muleLenderMonitoringRootUri + muleLenderMonitoringUri,
			queryParameters,
			MuleUniverseGetOutputDTO.class,
			muleLenderMonitoringUseOAuth
		);

		MuleUniverseGetOutputDTO muleUniverseGetOutputDTO = responseEntity.getBody();
		GetUniverseResult getUniverseResult = null;
		String statusCode = "";
		String statusMessage = "";
		if (muleUniverseGetOutputDTO != null) {
			getUniverseResult = muleUniverseGetOutputDTO.getGetUniverseResult();
			if (getUniverseResult != null) {
				statusCode = getUniverseResult.getStatusCode();
				statusMessage = getUniverseResult.getStatusMessage();
			}
		}

		if (!statusCode.equals("0")) {
			throw new RuntimeException("Mule get lender monitoring universe for lenderId " + lenderId + " failed with status code " + statusCode + ": " + statusMessage);
		}

		return parseCaseNumbers(responseEntity);
	}

	private List<String> parseCaseNumbers(ResponseEntity<MuleUniverseGetOutputDTO> responseEntity) {
		if (!responseEntity.getStatusCode().is2xxSuccessful()) {
			return new ArrayList<String>();
		}

		MuleUniverseGetOutputDTO dto = responseEntity.getBody();
		if (!dto.getGetUniverseResult().getStatusCode().equals("0")) {
			return new ArrayList<String>();
		}

		List<String> caseNumbers = new ArrayList<String>();
		for (MuleUniverseGetCaseDetailDTO caseDetail : dto.getGetUniverseResult().getCaseDetail()) {
			caseNumbers.add(caseDetail.getCase_number());
		}
		return caseNumbers;
	}

	public LoanSelectionCaseSummary getLoanSelectionCaseSummary(String caseNumber) {
		Map<String, String> queryParameters = new HashMap<String, String>();
		queryParameters.put("caseid", caseNumber);

		ResponseEntity<MuleCaseGetOutputDTO> responseEntity;
		try {
			responseEntity = muleClient.get(
				muleCaseRootUri + muleCaseUri,
				queryParameters,
				MuleCaseGetOutputDTO.class,
				muleCaseUseOAuth
		);
		} catch (HttpClientErrorException e) {
			logger.error("Mule get case for case number " + caseNumber + " failed with error: " + e.getMessage());
			return null;
		}

		MuleCaseGetOutputDTO muleCaseGetOutputDTO = responseEntity.getBody();
		GetCaseResult getCaseResult = null;
		String statusCode = "";
		String statusMessage = "";
		if (muleCaseGetOutputDTO != null) {
			getCaseResult = muleCaseGetOutputDTO.getGetCaseResult();
			if (getCaseResult != null) {
				statusCode = getCaseResult.getStatusCode();
				statusMessage = getCaseResult.getStatusMessage();
			}
		}

		if (!statusCode.equals("0")) {
			logger.error("Mule get case for case number " + caseNumber + " failed with status code " + statusCode + ": " + statusMessage);
			return null;
		}

		try {
			ObjectMapper mapper = new ObjectMapper();
			ObjectMapper mapperCustom = new ObjectMapper();
			mapperCustom.setPropertyNamingStrategy(new GetCaseNamingStrategy());
			MuleCaseGetDetailDTO caseDetail = mapper.treeToValue(muleCaseGetOutputDTO.getGetCaseResult().getCaseDetail(), MuleCaseGetDetailDTO.class);
			if (piiMask) {
				caseDetail = removePii(caseDetail);
			}
			LoanSelectionCaseSummary loanSelectionCaseSummary  = mapper.readValue(mapperCustom.writeValueAsString(caseDetail),LoanSelectionCaseSummary.class);
			loanSelectionCaseSummary.setCaseNumber(StringFunctionsUtil.caseNumberPad(loanSelectionCaseSummary.getCaseNumber()));
			return loanSelectionCaseSummary;
		} catch (Exception e) {
			logger.error("Mule get case for case number " + caseNumber + " failed with error: " + e.getMessage());
			return null;
		}
	}

	public List<LoanSelectionCaseSummary> getLoanSelectionCaseSummaries(List<String> caseNumbers) {
		logger.debug("Loading " + caseNumbers.size() + " cases from Mule");

		FhacUser fhacUser = securityService.getFhacUser();

		CompletionService<List<LoanSelectionCaseSummary>> completionService = new ExecutorCompletionService<List<LoanSelectionCaseSummary>>(executor);
		int remainingFutures = 0;
		for (int start = 0; start < caseNumbers.size(); start += muleBatchCaseSize) {
			int s = start;	// local copy necessary for use in callable
			int end = Math.min(caseNumbers.size(), start + muleBatchCaseSize);

			completionService.submit(new Callable<List<LoanSelectionCaseSummary>>() {
				public List<LoanSelectionCaseSummary> call() {
					securityService.setFhacUser(fhacUser);
					logger.debug("Loading cases " + s + " to " + end + " of " + caseNumbers.size());
					return getLoanSelectionCaseSummariesImpl(caseNumbers.subList(s, end));
				}
			});

			remainingFutures++;
		}

		List<LoanSelectionCaseSummary> loanSelectionCaseSummaries = new ArrayList<LoanSelectionCaseSummary>();
		while (remainingFutures > 0) {
			try {
				Future<List<LoanSelectionCaseSummary>> future = completionService.take();
				loanSelectionCaseSummaries.addAll(future.get());
			} catch (Throwable t) {
				throw new RuntimeException(t);
			}

			remainingFutures--;
		}

		return loanSelectionCaseSummaries;
	}

	private List<LoanSelectionCaseSummary> getLoanSelectionCaseSummariesImpl(List<String> caseNumbers) {
		Map<String, String> queryParameters = new HashMap<String, String>();
		queryParameters.put("caseid", String.join(",", caseNumbers));
		ResponseEntity<MuleCaseGetOutputDTO[]> responseEntity = muleClient.get(
			muleBatchCaseRootUri + muleBatchCaseUri,
			queryParameters,
			MuleCaseGetOutputDTO[].class,
			muleBatchCaseUseOAuth
		);

		MuleCaseGetOutputDTO[] muleCaseGetOutputDTOs = responseEntity.getBody();
		List<LoanSelectionCaseSummary> loanSelectionCaseSummaries = new ArrayList<LoanSelectionCaseSummary>(muleCaseGetOutputDTOs.length);
		ObjectMapper mapper = new ObjectMapper();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		mapper.setDateFormat(df);
		ObjectMapper mapperCustom = new ObjectMapper();
		mapperCustom.setPropertyNamingStrategy(new GetCaseNamingStrategy());
		for (MuleCaseGetOutputDTO muleCaseGetOutputDTO : muleCaseGetOutputDTOs) {
			if (muleCaseGetOutputDTO.getGetCaseResult().getStatusCode().equals("0")) {
				try {
					MuleCaseGetDetailDTO caseDetail = mapper.treeToValue(muleCaseGetOutputDTO.getGetCaseResult().getCaseDetail(), MuleCaseGetDetailDTO.class);
					if (piiMask) {
						caseDetail = removePii(caseDetail);
					}
					LoanSelectionCaseSummary loanSelectionCaseSummary  = mapper.readValue(mapperCustom.writeValueAsString(caseDetail),LoanSelectionCaseSummary.class);
					loanSelectionCaseSummary.setCaseNumber(StringFunctionsUtil.caseNumberPad(loanSelectionCaseSummary.getCaseNumber()));
					loanSelectionCaseSummaries.add(loanSelectionCaseSummary);
				} catch (Exception e) {
					logger.error("Couldn't map case " + muleCaseGetOutputDTO.getGetCaseResult().getCaseDetail().get("case_number").textValue(), e);
				}
			} else {
				logger.error("Mule get cases error " + muleCaseGetOutputDTO.getGetCaseResult().getStatusCode() + ": "
					+ muleCaseGetOutputDTO.getGetCaseResult().getStatusMessage() + " "
					+ muleCaseGetOutputDTO.getGetCaseResult().getCaseDetail().get("case_nbr").textValue()
				);
			}
		}
		return loanSelectionCaseSummaries;
	}
	
	private MuleCaseGetDetailDTO removePii(MuleCaseGetDetailDTO muleCaseGetDetailDTO) {
		muleCaseGetDetailDTO.setBorr_1_name("BILL BORROWER");
		muleCaseGetDetailDTO.setBorr_1_ssn("***-**-****");
		muleCaseGetDetailDTO.setBorr_2_name("BRANDY BORROWER");
		muleCaseGetDetailDTO.setBorr_2_ssn("***-**-****");
		muleCaseGetDetailDTO.setBorr_brth_dt("1900-01-01T00:00:00");
		muleCaseGetDetailDTO.setGift_ltr_tin("***-**-****");
		muleCaseGetDetailDTO.setGift_ltr_2_tin("***-**-****");
		muleCaseGetDetailDTO.setProp_addr_1("1 ABBY LANE");
		muleCaseGetDetailDTO.setProp_addr_2("");
		muleCaseGetDetailDTO.setLoan_officer("OFFICER WIGGUM");
		muleCaseGetDetailDTO.setOrgntng_mtgee_nmls_id("000000000000");
		muleCaseGetDetailDTO.setLoan_officer_nmls("OFFICER W");
		muleCaseGetDetailDTO.setAppraiser_name("APP RAISER");
		return muleCaseGetDetailDTO;
	}

}
