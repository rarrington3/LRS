package gov.hud.lrs.common.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;

import gov.hud.lrs.common.dto.FhacUserDTO;
import gov.hud.lrs.common.dto.MuleAuthorizationDTO;
import gov.hud.lrs.common.dto.MuleAuthorizationsSearchResultsDTO;
import gov.hud.lrs.common.entity.Personnel;
import gov.hud.lrs.common.repository.PersonnelRepository;

@Service
public class FhacUserService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${lrs.mule.authorizations.uri}") private String muleAuthorizationsUri;
	@Value("${lrs.mule.authorizations.rootUri}") private String muleAuthorizationsRootUri;
	@Value("${lrs.mule.authorizations.useOAuth}") private boolean muleAuthorizationsUseOAuth;
	@Value("${lrs.mule.piiMask}") private boolean piiMask;
	
	@Autowired private PersonnelRepository personnelRepository;
	@Autowired private MuleClient muleClient;

	private static final Map<String, String> ROLE_MAP = new ImmutableMap.Builder<String, String>()
		.put("Reviewer", "ROLE_REVIEWER")
		.put("Read Only", "ROLE_LRS_READ_ONLY")
		.put("HQ Admin", "ROLE_HQ_ADMIN")
		.put("Review Location", "ROLE_REVIEW_LOCATION_ADMIN")
		.put("Program Assistant", "ROLE_PROGRAM_ASSISTANT")
		.put("Indemnification Submission", "ROLE_INDEMNIFIER")
		.put("Response Submission", "ROLE_RESPONSE_COORDINATOR")
		.put("ROLE_SYSTEM", "ROLE_SYSTEM")
		.put("Monitor", "ROLE_MONITOR_READ_ONLY")
		.build();

	@Cacheable("FhacUser")
	public FhacUserDTO getFhacUser(String userId) {
		ResponseEntity<MuleAuthorizationsSearchResultsDTO> responseEntity;
		try {
			responseEntity = muleClient.get(
				muleAuthorizationsRootUri + muleAuthorizationsUri + "?userId=" + userId + "&applicationName=LRS",
				null,
				MuleAuthorizationsSearchResultsDTO.class,
				muleAuthorizationsUseOAuth
			);
		} catch (HttpClientErrorException e) {
			return null;
		}

		MuleAuthorizationsSearchResultsDTO muleAuthorizationsSearchResultsDTO = responseEntity.getBody();
		String statusCode = "";
		String statusMessage = "";
		if (muleAuthorizationsSearchResultsDTO != null) {
			statusCode = muleAuthorizationsSearchResultsDTO.getStatusCode();
			statusMessage = muleAuthorizationsSearchResultsDTO.getStatusMessage();
		}

		if (!statusCode.equals("0")) {
			logger.debug("Mule get FHAC user " + userId + " failed with status code " + statusCode + ": " + statusMessage);
			return null;
		}

		FhacUserDTO fhacUserDTO = new FhacUserDTO();
		fhacUserDTO.setUserId(muleAuthorizationsSearchResultsDTO.getUserId());
		if (piiMask) {
			Personnel personnel = personnelRepository.findByLoginCredential(userId);
			if (personnel == null) {
				fhacUserDTO.setFirstName("JANE");
				fhacUserDTO.setLastName("MULE");
			} else {
				fhacUserDTO.setFirstName(personnel.getFirstName().substring(0, 3) + "***");
				fhacUserDTO.setLastName(personnel.getPersonnelId().substring(0, 8) + "-"
					+ personnel.getLastName().substring(personnel.getLastName().length() - 3));
			}
		} else {
			fhacUserDTO.setFirstName(muleAuthorizationsSearchResultsDTO.getFirstName());
			fhacUserDTO.setLastName(muleAuthorizationsSearchResultsDTO.getLastName());
		}
		String lenderId = null;
		String agencyIdTruncated = null;
		List<String> roles = new ArrayList<String>();

		MuleAuthorizationDTO[] muleAuthorizationDTOs;
		try {
			muleAuthorizationDTOs = new ObjectMapper().treeToValue(muleAuthorizationsSearchResultsDTO.getSecurityAuthorizations(), MuleAuthorizationDTO[].class);
		} catch (JsonProcessingException e) {
			logger.error("Mule get FHAC user " + userId + " failed. Cannot get authorization", e);
			return null;
		}

		for (MuleAuthorizationDTO muleAuthorizationDTO : muleAuthorizationDTOs) {
			String lrsAuthorizationName = ROLE_MAP.get(muleAuthorizationDTO.getAuthorizationName());
			if (lrsAuthorizationName != null) {
				roles.add(lrsAuthorizationName);
				logger.debug("Known authorization name: " + muleAuthorizationDTO.getAuthorizationName() + " (LRS Role: " + lrsAuthorizationName + ")");
			} else {
				logger.debug("Unknown authorization name: " + muleAuthorizationDTO.getAuthorizationName());
			}

			// Running locally agency ID is null for non lenders. Running with live Mule: agency ID is 9999999999
			if (muleAuthorizationDTO.getAgencyId() != null) {
				// lender ID comes from agency ID in the list of authorizations
				// for LRS users, all authorizations are *supposed* to have the same agency ID, but we double check
				// NOTE: LRS stores lenderId as 5 digit
				agencyIdTruncated = muleAuthorizationDTO.getAgencyId().substring(0, 5);

				logger.debug("Agency ID: " + muleAuthorizationDTO.getAgencyId() + " (truncated for LRS: " + agencyIdTruncated + ")");

				if (lenderId == null) {
					lenderId = agencyIdTruncated;
				} else if (!lenderId.equals(agencyIdTruncated)) {
					logger.debug("FHAC user " + userId + " has different truncated agency id (lender id): " + agencyIdTruncated);
				}
			}
		}
		fhacUserDTO.setRoles(roles);
		fhacUserDTO.setLenderId(lenderId);

		return fhacUserDTO;
	}

	public boolean isLender(FhacUserDTO fhacUserDTO) {
		String lenderId = fhacUserDTO.getLenderId();
		// Running locally lender ID is null for non lenders. Running with live Mule: lender ID is 99999 for not lenders
		return lenderId != null && !lenderId.equals("99999");
	}

}
