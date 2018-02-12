package gov.hud.lrs.services.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.WebUtils;

import gov.hud.lrs.common.dto.FhacUserDTO;
import gov.hud.lrs.common.exception.UnauthorizedException;
import gov.hud.lrs.common.security.FhacUser;
import gov.hud.lrs.common.security.SecurityService;
import gov.hud.lrs.common.service.FhacUserService;

@Component
public class DevAuthenticationFilter extends GenericFilterBean {

	private static final String DEV_COOKIE_NAME = "lrs_dev_userid";

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private FhacUserService fhacUserService;

	@Autowired
	private SecurityService securityService;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		if (securityService.isAuthenticated()) {
			filterChain.doFilter(request, response);
			return;
		}

		HttpServletRequest httpRequest = (HttpServletRequest) request;

		Cookie cookie = WebUtils.getCookie(httpRequest, DEV_COOKIE_NAME);
		if (cookie == null) {
			logger.debug(DEV_COOKIE_NAME + " cookie not found.");
			filterChain.doFilter(request, response);
			return;
		}

		String userId = cookie.getValue();

		FhacUserDTO fhacUserDTO = fhacUserService.getFhacUser(userId);
		if (fhacUserDTO == null) {
			logger.debug("No FHAC user for username " + userId);
			filterChain.doFilter(request, response);
			return;
		} else if (!fhacUserService.isLender(fhacUserDTO) && !securityService.isActive(userId)) {
			logger.debug("FHAC user is inactive " + userId);
			throw new UnauthorizedException("We're sorry, you are currently not authorized to log in at this time. Make sure that your supervisor has configured your account in the LRS staff management screen.");
		}

		FhacUser fhacUser = new FhacUser();
		fhacUser.setUserId(fhacUserDTO.getUserId());
		fhacUser.setFirstName(fhacUserDTO.getFirstName());
		fhacUser.setLastName(fhacUserDTO.getLastName());
		fhacUser.setLenderId(fhacUserDTO.getLenderId());
		fhacUser.setRoles(fhacUserDTO.getRoles());

		securityService.setFhacUser(fhacUser);

		filterChain.doFilter(request, response);
	}
}
