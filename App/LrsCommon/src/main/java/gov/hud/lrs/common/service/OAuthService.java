package gov.hud.lrs.common.service;

import java.time.Instant;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import gov.hud.lrs.common.dto.MuleCookieDTO;

@Service
public class OAuthService {

	private static final int OAUTH_TOKEN_MAX_ATTEMPTS = 3;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${lrs.mule.oauth.audit.sourceSystem}")
	private String auditSourceSystem;

	@Value("${lrs.mule.oauth.audit.authenticationId}")
	private String auditAuthenticationId;

	@Value("${lrs.mule.oauth.audit.tenantId}")
	private String auditTenantId;

	@Value("${lrs.mule.oauth.audit.locale}")
	private String auditLocale;

	@Value("${lrs.mule.logBody}")
	private boolean logBody;

	private static String oauthToken;
	private static Instant oauthTokenRefresh;

	private static String muleCookieUri;
	private static String muleAuthUri;
	private static String muleUserID;
	private static String muleUserPassword;
	private static String muleClientID;
	private static String scopeStr;
	private static String muleRedirectUri;

	private Object lock = new Object();

	private RestTemplate restTemplate = new RestTemplate();

	@Value("${lrs.mule.oauth.cookieUri}")
	public void setMuleCookieUri(String muleCookieUri) {
		OAuthService.muleCookieUri = muleCookieUri;
	}

	@Value("${lrs.mule.oauth.authUri}")
	public void setMuleAuthUri(String muleAuthUri) {
		OAuthService.muleAuthUri = muleAuthUri;
	}

	@Value("${lrs.mule.oauth.userID}")
	public void setMuleUserID(String muleUserID) {
		OAuthService.muleUserID = muleUserID;
	}

	@Value("${lrs.mule.oauth.userPassword}")
	public void setMuleUserPassword(String muleUserPassword) {
		OAuthService.muleUserPassword = muleUserPassword;
	}

	@Value("${lrs.mule.oauth.clientID}")
	public void setMuleClientID(String muleClientID) {
		OAuthService.muleClientID = muleClientID;
	}

	@Value("${lrs.mule.oauth.redirectUri}")
	public void setMuleRedirectUri(String muleRedirectUri) {
		OAuthService.muleRedirectUri = muleRedirectUri;
	}

	@Value("${lrs.mule.oauth.scope}")
	public void setScopeStr(String scopeStr) {
		OAuthService.scopeStr = scopeStr;
	}

	public String getToken() {
		if ((oauthToken != null) && Instant.now().isBefore(oauthTokenRefresh)) {
			return oauthToken;
		}

		logger.debug("Waiting on oauth token lock");
		synchronized(lock) {
			if ((oauthToken != null) && Instant.now().isBefore(oauthTokenRefresh)) {
				return oauthToken;
			}

			for (int attempt = 0; attempt < OAUTH_TOKEN_MAX_ATTEMPTS; ++attempt) {
				logger.debug("Refreshing Mule oauth token attempt #" + attempt);

				try {
					logger.debug("Getting oauth cookie...");
					HttpHeaders headers = new HttpHeaders();
					headers.setContentType(MediaType.APPLICATION_JSON);
					headers.set("X-OpenAM-Username", muleUserID);
					headers.set("X-OpenAM-Password", muleUserPassword);
					ResponseEntity<MuleCookieDTO> cookieEntity = restTemplate.exchange(
						muleCookieUri,
						HttpMethod.POST,
						new HttpEntity<RequestBody>(null, headers),
						MuleCookieDTO.class
					);
					MuleCookieDTO cookieDTO = cookieEntity.getBody();
					if (cookieDTO.getTokenId() == null) {
						throw new RuntimeException("Error requesting mule cookie");
					}
					logger.debug("...got oauth cookie");

					logger.debug("Getting oauth token...");
					headers = new HttpHeaders();
					headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
					headers.set("Cookie", "iPlanetDirectoryPro=" + cookieDTO.getTokenId());
					String bodyStr =
						"response_type=id_token token" +
						"&save_consent=1" +
						"&decision=Allow" +
						"&scope=openid profile " + scopeStr.replace(",", " ") +
						"&nonce=" + Math.abs(new Random().nextInt()) +
						"&client_id=" +  muleClientID +
						"&redirect_uri=" + muleRedirectUri
					;
					ResponseEntity<String> responseEntity = restTemplate.exchange(
						muleAuthUri,
						HttpMethod.POST,
						new HttpEntity<String>(bodyStr, headers),
						String.class
					);
					if (responseEntity.getHeaders().getLocation() == null) {
						throw new RuntimeException("Error getting mule token");
					}

					// Enable after the location header is fixed (QAICSSUP-721)
					// MultiValueMap<String, String> locationParams = UriComponentsBuilder.fromUri(responseEntity.getHeaders().getLocation()).build().getQueryParams();
					// String tokenStrNew = locationParams.getFirst("access_token");
					// String expireStr = locationParams.getFirst("expires_in");
					// logger.debug(tokenStrNew);
					// logger.debug(expireStr);
					// if (tokenStrNew == null) {
					//	throw new RuntimeException("No access token found in location: " + responseEntity.getHeaders().getLocation().toString());
					// }
					// if (expireStr == null) {
					// 	throw new RuntimeException("Invalid expiration in location: " + responseEntity.getHeaders().getLocation().toString());
					// }

					// Before the location header is fixed (QAICSSUP-721), temporarily treat it as pure String
					// Delete after the location header is fixed
					String locationStr = responseEntity.getHeaders().getLocation().toString();
					int tokenPosition = locationStr.indexOf("#access_token=");
					if (tokenPosition < 0) {
						throw new RuntimeException("No access token found!");
					}
					String tokenStrNew = locationStr.substring(tokenPosition + 14, tokenPosition + 50);

					int expPosition = locationStr.indexOf("&expires_in=");
					if (expPosition < 0) {
						throw new RuntimeException("Invalid expiration!");
					}
					String expireStr = locationStr.substring(expPosition + 12);

					oauthToken = tokenStrNew;
					oauthTokenRefresh = Instant.now().plusSeconds(Long.parseLong(expireStr)).minusSeconds(120); // 120 seconds safety buffer

					logger.debug("...got new token, expires on " + expireStr + ", we will refresh on " + oauthTokenRefresh.toString());

					return oauthToken;
				} catch (Throwable t) {
					logger.error("Error trying to refresh oauth token", t);
				}
			}
		}

		throw new RuntimeException("Couldn't refresh oauth token after " + OAUTH_TOKEN_MAX_ATTEMPTS + " attempts");
	}

}
