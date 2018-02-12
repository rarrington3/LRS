package gov.hud.lrs.services.bizservice;

import java.lang.reflect.Constructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.hud.lrs.common.dto.LrsApiV1DTOs.ApiErrorDTO;
import gov.hud.lrs.common.security.SecurityService;

@Service
public class WorkflowClient {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${lrs.workflowUri}") private String workflowUri;

	@Value("${lrs.siteMinderUserIdHeader}") private String siteMinderUserIdHeader;

	@Autowired private SecurityService securityService;

	private RestTemplate restTemplate = new RestTemplate();

	public <RequestBody, ResponseBody> ResponseEntity<ResponseBody> exchange(
		String uri,
		HttpMethod method,
		RequestBody requestBody,
		Class<ResponseBody> responseBodyClass
	) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set(siteMinderUserIdHeader, securityService.getUserId());

		String fullUri = workflowUri + uri;
		logger.debug("Calling workflow: " + method + " " + fullUri);

		ResponseEntity<ResponseBody> responseEntity;
		try {
			responseEntity = restTemplate.exchange(
				fullUri,
				method,
				new HttpEntity<RequestBody>(requestBody, headers),
				responseBodyClass
			);

		} catch (HttpStatusCodeException e) {
			throw translateResponseBodyToException(e.getResponseBodyAsString());

		} catch (Exception e) {
			throw new RuntimeException("Workflow call " + method + " " + fullUri + " failed", e);
		}

		logger.debug("Workflow call " + method + " " + fullUri + " returned " + responseEntity.getStatusCodeValue());

		return responseEntity;
	}

	public <RequestBody, ResponseBody> ResponseEntity<ResponseBody> get(String uri, Class<ResponseBody> responseBodyClass) {
		return exchange(uri, HttpMethod.GET, null, responseBodyClass);
	}

	public <RequestBody, ResponseBody> ResponseEntity<ResponseBody> put(String uri, RequestBody requestBody, Class<ResponseBody> responseBodyClass) {
		return exchange(uri, HttpMethod.PUT, requestBody, responseBodyClass);
	}

	public <RequestBody, ResponseBody> ResponseEntity<ResponseBody> post(String uri, RequestBody requestBody, Class<ResponseBody> responseBodyClass) {
		return exchange(uri, HttpMethod.POST, requestBody, responseBodyClass);
	}

	private RuntimeException translateResponseBodyToException(String responseBody) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			ApiErrorDTO apiErrorDTO = mapper.readValue(responseBody, ApiErrorDTO.class);
			Class<?> clazz = Class.forName(apiErrorDTO.getException().getExceptionClass());
			Constructor<?> constructor = clazz.getConstructor(String.class);
			RuntimeException customException = (RuntimeException) constructor.newInstance(apiErrorDTO.getException().getMessage());

			if (apiErrorDTO.getException().getStackTrace() != null) {
				StackTraceElement[] stackTrace = new StackTraceElement[apiErrorDTO.getException().getStackTrace().size()];
				int i = 0;
				for (String trace : apiErrorDTO.getException().getStackTrace()) {
					stackTrace[i] = new StackTraceElement(
						trace.substring(0, trace.indexOf("(") - 1),
						"",
						trace.substring(trace.indexOf("(") + 1, trace.indexOf(":")),
						Integer.parseInt(trace.substring(trace.indexOf(":") + 1, trace.indexOf(")")))
					);
					i++;
				}
				customException.setStackTrace(stackTrace);
				mergeStackTraces(customException);
			}
		
			return customException;
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	 private void mergeStackTraces(Throwable error) {
		 StackTraceElement[] currentStack = new Throwable().getStackTrace();
	     StackTraceElement[] oldStack = error.getStackTrace();
	     StackTraceElement[] newStack = new StackTraceElement[oldStack.length + currentStack.length + 1];
	     System.arraycopy(oldStack, 0, newStack, 0, oldStack.length);
	     newStack[oldStack.length] =  new StackTraceElement("<<< END REMOTE SERVICE CALL >>>", "", "", -3);
	     System.arraycopy(currentStack, 0, newStack, oldStack.length + 1, currentStack.length);
	     error.setStackTrace(newStack);
	 }

}
