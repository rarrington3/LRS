package gov.hud.lrs.services.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import gov.hud.lrs.common.dto.FhacUserDTO;
import gov.hud.lrs.common.exception.UnauthorizedException;
import gov.hud.lrs.common.security.FhacUser;
import gov.hud.lrs.common.security.SecurityService;
import gov.hud.lrs.common.service.FhacUserService;

@Component
public class SiteMinderAuthenticationFilter extends GenericFilterBean {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${lrs.siteMinderUserIdHeader}")
	private String siteMinderUserIdHeader;

	@Autowired
	private FhacUserService fhacUserService;

	@Autowired
	private SecurityService securityService;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		String userId = ((HttpServletRequest)request).getHeader(siteMinderUserIdHeader);
		if (userId == null) {
			filterChain.doFilter(request, response);
			return;
		}

		logger.debug("Successful pre-authentication with SM_USER header");

		FhacUserDTO fhacUserDTO = fhacUserService.getFhacUser(userId);
		if (fhacUserDTO == null) {
			throw new ServletException("No FHAC user for userId " + userId);
		} else if (!fhacUserService.isLender(fhacUserDTO) && !securityService.isActive(userId)) {
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
