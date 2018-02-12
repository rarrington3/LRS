package gov.hud.lrs.services.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.hud.lrs.common.dto.LrsApiV1DTOs.ApiErrorDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.ApiErrorExceptionDTO;
import gov.hud.lrs.common.exception.UnauthorizedException;

@Component
public class FilterExceptionHandlerFilter extends OncePerRequestFilter {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 	throws ServletException, IOException {
		try {
			filterChain.doFilter(request, response);
		} catch (RuntimeException e) {
			logger.error("", e);

			if (e instanceof PreAuthenticatedCredentialsNotFoundException) {
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
			} else if (e instanceof UnauthorizedException) {
				logger.debug("UnauthorizedException: " + e.getMessage());
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
				ApiErrorDTO apiErrorDTO = new ApiErrorDTO();
				ApiErrorExceptionDTO apiErrorExceptionDTO = new ApiErrorExceptionDTO();
				apiErrorExceptionDTO.setExceptionClass(UnauthorizedException.class.getName());
				apiErrorExceptionDTO.setMessage(e.getMessage());
				apiErrorDTO.setException(apiErrorExceptionDTO);
				List<String> errorMessages = new ArrayList<String>();
				errorMessages.add(e.getMessage());
				apiErrorDTO.setErrorMessages(errorMessages);
				ObjectMapper mapper = new ObjectMapper();
				response.getWriter().write(mapper.writeValueAsString(apiErrorDTO));
			} else {
				response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			}
		}
	}
}