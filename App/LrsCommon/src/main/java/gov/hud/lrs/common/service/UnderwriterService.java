package gov.hud.lrs.common.service;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.LiteLenderDTO;
import gov.hud.lrs.common.dto.MuleUnderwriterSearchResultDTO;
import gov.hud.lrs.common.dto.MuleUnderwriterSearchResultDTO.UnderwriterProperties;

@Service
public class UnderwriterService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${lrs.mule.underwriter.uri}") private String muleUnderwriterUri;
	@Value("${lrs.mule.underwriter.rootUri}") private String muleUnderwriterRootUri;
	@Value("${lrs.mule.underwriter.useOAuth}") private boolean muleUnderwriterUseOAuth;
	@Value("${lrs.mule.piiMask}") private boolean piiMask;

	@Autowired private MuleClient muleClient;

	public LiteLenderDTO getUnderwriter(String underwriterId) {
		Map<String, String> queryParameters = new HashMap<String, String>();
		queryParameters.put("id", underwriterId);
		ResponseEntity<MuleUnderwriterSearchResultDTO> responseEntity;
		try {
			responseEntity = muleClient.get(
				muleUnderwriterRootUri + muleUnderwriterUri,
				queryParameters,
				MuleUnderwriterSearchResultDTO.class,
				muleUnderwriterUseOAuth
			);
		} catch (HttpClientErrorException e) {
			return null;
		}

		MuleUnderwriterSearchResultDTO muleUnderwriterSearchResultsDTO = responseEntity.getBody();
		UnderwriterProperties underwriterProperties = null;
		String statusCode = "";
		String statusMessage = "";
		if (muleUnderwriterSearchResultsDTO != null) {
			underwriterProperties = muleUnderwriterSearchResultsDTO.getProperties();
			if (underwriterProperties != null) {
				statusCode = underwriterProperties.getStatusCode();
				statusMessage = underwriterProperties.getStatusMessage();
			}
		}

		if (!statusCode.equals("0")) {
			logger.debug("Mule get underwriter for underwriterId " + underwriterId+ " failed with status code " + statusCode + ": " + statusMessage);
			return null;
		}

		LiteLenderDTO lenderDTO = new LiteLenderDTO();
		lenderDTO.setLenderId(muleUnderwriterSearchResultsDTO.getProperties().getUnderwriterId());
		if (piiMask) {
			lenderDTO.setName("JOE, UNDERWRITER");
		} else {
			lenderDTO.setName(muleUnderwriterSearchResultsDTO.getProperties().getUnderwriterName());
		}

		return lenderDTO;
	}

}
