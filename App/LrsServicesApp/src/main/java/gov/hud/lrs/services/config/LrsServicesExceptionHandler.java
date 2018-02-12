package gov.hud.lrs.services.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.google.common.collect.ImmutableMap;

import gov.hud.lrs.common.dto.LrsApiV1DTOs.ApiErrorDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.ApiErrorExceptionDTO;
import gov.hud.lrs.common.exception.BadRequestException;
import gov.hud.lrs.common.exception.ClientException;
import gov.hud.lrs.common.exception.ConflictException;
import gov.hud.lrs.common.exception.ForbiddenException;
import gov.hud.lrs.common.exception.NotFoundException;
import gov.hud.lrs.common.exception.UnauthorizedException;

@ControllerAdvice
public class LrsServicesExceptionHandler {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${lrs.devMode}") private String devMode;
	@Value("${lrs.returnExceptions}") private String returnExceptions;

	@SuppressWarnings("rawtypes")
	private static final Map<Class, HttpStatus> exceptionClassToHttpStatus = new ImmutableMap
		.Builder<Class, HttpStatus>()
		.put(BadRequestException.class, HttpStatus.BAD_REQUEST)
		.put(ConflictException.class, HttpStatus.CONFLICT)
		.put(NotFoundException.class, HttpStatus.GONE)	// this intentional, we generally use 410 GONE instead of 404 NOT FOUND
		.put(UnauthorizedException.class, HttpStatus.UNAUTHORIZED)
		.put(ForbiddenException.class, HttpStatus.FORBIDDEN)
		.build();

	private ApiErrorDTO createApiErrorDTO(Exception exception, WebRequest webRequest, List<String> errorMessages) {
		logger.error("Caught exception during " + webRequest.getDescription(false), exception);

		ApiErrorDTO apiErrorDTO = new ApiErrorDTO();

		if (returnExceptions.equals("true")) {
			ApiErrorExceptionDTO apiErrorExceptionDTO = new ApiErrorExceptionDTO();
			apiErrorExceptionDTO.setExceptionClass(exception.getClass().getName());
			apiErrorExceptionDTO.setMessage(exception.getMessage());
			List<String> stackTraceList = new ArrayList<String>();
			for (int i = 0; i < exception.getStackTrace().length; i++) {
				final StackTraceElement stackTraceElement = exception.getStackTrace()[i];
				stackTraceList.add(stackTraceElement.getClassName() + "." + stackTraceElement.getFileName() + " (" + stackTraceElement.getFileName() + ":" + stackTraceElement.getLineNumber() + ")");
			}
			apiErrorExceptionDTO.setStackTrace(stackTraceList);
			apiErrorDTO.setException(apiErrorExceptionDTO);
		}

		apiErrorDTO.setErrorMessages(errorMessages);

		return apiErrorDTO;
	}

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<ApiErrorDTO> handleAnyException(Exception exception, WebRequest webRequest) {
		ApiErrorDTO apiErrorDTO = createApiErrorDTO(exception, webRequest, null);
		return new ResponseEntity<ApiErrorDTO>(apiErrorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler({ ClientException.class })
	public ResponseEntity<ApiErrorDTO> handleBadRequestException(ClientException clientException, WebRequest webRequest) {
		ApiErrorDTO apiErrorDTO = createApiErrorDTO(clientException, webRequest, clientException.getErrorMessages());
		HttpStatus httpStatus = exceptionClassToHttpStatus.get(clientException.getClass());
		return new ResponseEntity<ApiErrorDTO>(apiErrorDTO, (httpStatus != null) ? httpStatus : HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
