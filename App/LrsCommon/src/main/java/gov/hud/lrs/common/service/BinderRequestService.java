package gov.hud.lrs.common.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import gov.hud.lrs.common.dto.MuleBinderReceiptDTO;
import gov.hud.lrs.common.dto.MuleBinderReceiptResponseDTO;
import gov.hud.lrs.common.dto.MuleBinderReceiptResponseDTO.CaseBinderDTO;
import gov.hud.lrs.common.dto.MuleBinderRequestDTO;
import gov.hud.lrs.common.dto.MuleBinderRequestResponseDTO;
import gov.hud.lrs.common.entity.BinderRequest;
import gov.hud.lrs.common.entity.ReviewLocation;
import gov.hud.lrs.common.enumeration.BinderRequestStatusCodes;
import gov.hud.lrs.common.exception.NotFoundException;
import gov.hud.lrs.common.repository.BinderRequestRepository;
import gov.hud.lrs.common.repository.BinderRequestStatusRefRepository;
import gov.hud.lrs.common.security.SecurityService;
import gov.hud.lrs.common.util.StringFunctionsUtil;


@Service
public class BinderRequestService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired private BinderRequestRepository binderRequestRepository;
	@Autowired private BinderRequestStatusRefRepository binderRequestStatusRefRepository;
	@Autowired private SecurityService securityService;

	private static final String QAD = "QAD";
	private static final String PUD = "PUD";
	private static final String REQUESTED_STATUS_CODE = "0";
	private static final String WITH_HUD_PAPER_STATUS_CODE = "203";
	private static final String WITH_HUD_ELECTRONIC_STATUS_CODE = "204";
	private static final List<String> RETRY_STATUS_CODES = ImmutableList.of(
		"207",
		"208",
		"999"
	);

	@Value("${lrs.mule.binderRequest.uri}") private String muleBinderRequestUri;
	@Value("${lrs.mule.binderRequest.rootUri}") private String muleBinderRequestRootUri;
	@Value("${lrs.mule.binderRequest.useOAuth}") private boolean muleBinderRequestUseOAuth;
	@Value("${lrs.mule.binderRequest.userid}") private String muleBinderRequestUserId;

	@Value("${lrs.mule.binderReceipt.uri}") private String muleBinderReceiptUri;
	@Value("${lrs.mule.binderReceipt.rootUri}") private String muleBinderReceiptRootUri;
	@Value("${lrs.mule.binderReceipt.useOAuth}") private boolean muleBinderReceiptUseOAuth;

	@Autowired private MuleClient muleClient;

	public enum BinderRequestResult {

		RETRY,
		EXCEPTION,
		REQUESTED,
		WITH_HUD_PAPER,
		WITH_HUD_ELECTRONIC

	}

	public BinderRequestResult requestBinder(String caseNumber, ReviewLocation reviewLocation) {
		MuleBinderRequestDTO binderRequestDTO = new MuleBinderRequestDTO();
		binderRequestDTO.setCaseNumber(StringFunctionsUtil.caseNumberPad(caseNumber));
		if (
			reviewLocation.getLocationName().equals("HQ") ||	// HQ is always considered QAD
			reviewLocation.getLocationName().endsWith(QAD)
		) {
			binderRequestDTO.setReason(QAD);
		} else if (reviewLocation.getLocationName().endsWith(PUD)) {
			binderRequestDTO.setReason(PUD);
		} else {
			throw new RuntimeException("ReviewLocation " + reviewLocation.getReviewLocationId() + "'s name was not HQ and did not end with " + QAD + " or " + PUD);
		}
		binderRequestDTO.setHoc(reviewLocation.getBinderDeliveryLocationRef().getCode());
		binderRequestDTO.setUser(muleBinderRequestUserId);

		BinderRequestResult binderRequestResult = null;
		try {
			ResponseEntity<MuleBinderRequestResponseDTO> responseEntity;
			responseEntity = muleClient.post(
				muleBinderRequestRootUri + muleBinderRequestUri,
				binderRequestDTO,
				MuleBinderRequestResponseDTO.class,
				muleBinderRequestUseOAuth
			);

			MuleBinderRequestResponseDTO muleBinderRequestResponseDTO = responseEntity.getBody();

			String statusCode = muleBinderRequestResponseDTO.getStatusCode();
			String statusMessage = muleBinderRequestResponseDTO.getStatusMessage();
			logger.debug("Mule binder request for case number " + caseNumber + " returned status code " + statusCode + ": " + statusMessage);
			if (REQUESTED_STATUS_CODE.equals(statusCode)) {
				binderRequestResult = BinderRequestResult.REQUESTED;
			} else if (WITH_HUD_PAPER_STATUS_CODE.equals(statusCode)) {
				binderRequestResult = BinderRequestResult.WITH_HUD_PAPER;
			} else if (WITH_HUD_ELECTRONIC_STATUS_CODE.equals(statusCode)) {
				binderRequestResult = BinderRequestResult.WITH_HUD_ELECTRONIC;
			} else if (RETRY_STATUS_CODES.contains(statusCode)) {
				binderRequestResult = BinderRequestResult.RETRY;
			} else {
				binderRequestResult = BinderRequestResult.EXCEPTION;
			}

		} catch (Exception e) {
			logger.error("Mule binder request for case number " + caseNumber + " failed " + e.getMessage() + "; retry later");
			binderRequestResult = BinderRequestResult.RETRY;
		}

		return binderRequestResult;
	}

	// TODO: this leaks some of the mule-specific data structures, but this one isn't terrible
	public List<CaseBinderDTO> checkBindersReceipt(List<String> caseNumbers) {
		MuleBinderReceiptDTO muleBinderReceiptDTO = new MuleBinderReceiptDTO();
		List<String> caseNumbersTrimmed = caseNumbers.stream().map(x -> StringFunctionsUtil.caseNumberTrim(x)).collect(Collectors.<String> toList());
		muleBinderReceiptDTO.setCaseNumbers(caseNumbersTrimmed);

		ResponseEntity<MuleBinderReceiptResponseDTO> responseEntity = muleClient.post(
			muleBinderReceiptRootUri + muleBinderReceiptUri,
			muleBinderReceiptDTO,
			MuleBinderReceiptResponseDTO.class,
			muleBinderReceiptUseOAuth
		);

		MuleBinderReceiptResponseDTO muleBinderReceiptResponseDTO = responseEntity.getBody();
		String statusCode = "";
		String statusMessage = "";
		if (muleBinderReceiptResponseDTO != null) {
			statusCode = muleBinderReceiptResponseDTO.getStatusCode();
			statusMessage = muleBinderReceiptResponseDTO.getStatusMessage();
		}

		if (!statusCode.equals("0")) {
			throw new RuntimeException("Mule binder receipt check for case numbers " + caseNumbers + " failed status code " + statusCode + ": " + statusMessage);
		}

		return responseEntity.getBody().getCaseBinders();
	}

	public BinderRequest cancelBinderRequest(String binderRequestId) {
		BinderRequest binderRequest = binderRequestRepository.findOne(binderRequestId);
		if (binderRequest == null) {
			throw new NotFoundException("BinderReqest " + binderRequestId + " not found");
		}

		Date now = new Date();
		binderRequest.setBinderRequestStatusRef(binderRequestStatusRefRepository.findByCode(BinderRequestStatusCodes.CANCELLED));
		binderRequest.setUpdatedBy(securityService.getUserId());
		binderRequest.setUpdatedTs(now);

		return binderRequestRepository.save(binderRequest);
	}
}
