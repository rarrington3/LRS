package gov.hud.lrs.common.service;

import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;

import gov.hud.lrs.common.dto.MuleAuditHeaderDTO;
import gov.hud.lrs.common.util.Util;

@Service
public class MuleClient {

	private static final String MULE_AUDIT_DATE_FORMAT_STRING = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

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

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired private OAuthService oauthService;

	private RestTemplate restTemplate;
	private ObjectMapper objectMapper = new ObjectMapper();

	public MuleClient() {
		this(0, 0);
	}

	public MuleClient(int connectTimeoutMilliseconds, int readTimeoutMilliseconds) {
		HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		httpComponentsClientHttpRequestFactory.setConnectionRequestTimeout(connectTimeoutMilliseconds);
		httpComponentsClientHttpRequestFactory.setReadTimeout(readTimeoutMilliseconds);
		restTemplate = new RestTemplate(httpComponentsClientHttpRequestFactory);
	}

	public <RequestBody_, ResponseBody> ResponseEntity<ResponseBody> exchange(	// RequestBody_ to avoid conflicts with the actual RequestBody class
		String uriStr,
		HttpMethod method,
		Map<String, String> requestParameters,
		RequestBody_ requestBody,
		Class<ResponseBody> responseBodyClass,
		boolean useOAuth
	) {
		// TODO: individual mule users need to pass an audit correlation ID
		String auditCorrelationId = UUID.randomUUID().toString();

		if ((requestParameters != null) && (requestParameters.size() > 0)) {
			char sep = '?';
			for (Map.Entry<String, String> entry : requestParameters.entrySet()) {
				uriStr += sep + entry.getKey() + "=" + entry.getValue();
				sep = '&';
			}
		}

		logger.debug("[AudID " + auditCorrelationId + "] calling mule: " + method + " " + uriStr + " (" + (useOAuth ? "oauth" : "no oauth") + ")");

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(ImmutableList.of(MediaType.APPLICATION_JSON));
		if (!method.matches(HttpMethod.GET.toString())) {
			headers.setContentType(MediaType.APPLICATION_JSON);
		}

		ResponseEntity<ResponseBody> responseEntity;
		String oauthToken = null;
		if (useOAuth) {
			oauthToken = oauthService.getToken();
			headers.set("Authorization", "Bearer " + oauthToken);
			MuleAuditHeaderDTO muleAuditHeaderDTO = createMuleAuditHeaderDTO(auditCorrelationId);
			try {
				headers.set("serviceConsumerData", objectMapper.writeValueAsString(muleAuditHeaderDTO));
			} catch (JsonProcessingException e) {
				throw new RuntimeException(e);
			}
		}

		if (logBody && (requestBody != null)) {
			logger.debug("[AudID " + auditCorrelationId + "] request body: " + Util.toCleansedJson(requestBody));
		}

		try {
			URI uri = new URI(uriStr);			
			responseEntity = restTemplate.exchange(
				uri,
				method,
				new HttpEntity<RequestBody_>(requestBody, headers),
				responseBodyClass
			);
			logger.debug("[AudID " + auditCorrelationId + "] mule call " + method + " " + uriStr + " returned " + responseEntity.getStatusCodeValue());

		} catch (HttpServerErrorException e) {
			throw new RuntimeException("***MULE ERROR*** " + method + " " + uriStr + " failed with " + e.getRawStatusCode() + " with body: " + e.getResponseBodyAsString(), e);

		} catch (HttpClientErrorException e) {
			logger.error("***MULE ERROR*** " + method + " " + uriStr + " failed with " + e.getRawStatusCode() + " with body: " + e.getResponseBodyAsString(), e);
/*
			// retrying service calls really scares me
			// I know it's probably fine, but I want to leave this out unless we absolutely can't get oauth to work any other way
			if (e.getStatusCode().equals(HttpStatus.BAD_REQUEST) && e.getResponseBodyAsString().startsWith("{\"error_description\":\"Access Token not valid\"")) {
				if (++tokenRetryCount <= OAUTH_TOKEN_MAX_RETRIES) {
					// Retry obtaining token
					continue TOKEN_RETRY_LOOP;
				}
			}
*/
			// propagate; caller must handle this
			throw e;

		} catch (Exception e) {
			throw new RuntimeException("***MULE ERROR*** " + method + " " + uriStr + " failed", e);
		}

		if (logBody) {
			String cleansedResponseBody = Util.toCleansedJson(responseEntity.getBody());
			logger.debug("[AudID " + auditCorrelationId + "] response body: " + cleansedResponseBody);
		}

		return responseEntity;
	}

	public <ResponseType> ResponseEntity<ResponseType> get(
		String uri,
		Map<String, String> requestParameters,
		Class<ResponseType> responseTypeClass,
		boolean useOAuth
	) {
		return exchange(uri, HttpMethod.GET, requestParameters, null, responseTypeClass, useOAuth);
	}

	public <RequestType, ResponseType> ResponseEntity<ResponseType> put(
		String uri,
		RequestType body,
		Class<ResponseType> responseTypeClass,
		boolean useOAuth
	) {
		return exchange(uri, HttpMethod.PUT, null, body, responseTypeClass, useOAuth);
	}

	public <RequestType, ResponseType> ResponseEntity<ResponseType> post(
		String uri,
		RequestType body,
		Class<ResponseType> responseTypeClass,
		boolean useOAuth
	) {
		return exchange(uri, HttpMethod.POST, null, body, responseTypeClass, useOAuth);
	}

	private MuleAuditHeaderDTO createMuleAuditHeaderDTO(String auditCorrelationId) {
		MuleAuditHeaderDTO muleAuditHeaderDTO = new MuleAuditHeaderDTO();
		muleAuditHeaderDTO.setAuditCorrelationId(auditCorrelationId);
		DateFormat dateFormat = new SimpleDateFormat(MULE_AUDIT_DATE_FORMAT_STRING);
		muleAuditHeaderDTO.setServiceRequestTimestamp(dateFormat.format(new Date()));
		muleAuditHeaderDTO.setSourceSystem(auditSourceSystem);
		muleAuditHeaderDTO.setAuthenticationId(auditAuthenticationId);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		muleAuditHeaderDTO.setEndUserId(authentication == null ? auditAuthenticationId : authentication.getName());
		muleAuditHeaderDTO.setTenantId(auditTenantId);
		muleAuditHeaderDTO.setLocale(auditLocale);

		return muleAuditHeaderDTO;
	}

}