package gov.hud.lrs.workflow.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import gov.hud.lrs.common.dto.FhacUserDTO;
import gov.hud.lrs.common.security.FhacUser;
import gov.hud.lrs.common.security.SecurityService;
import gov.hud.lrs.common.service.FhacUserService;

@Component
public class UsernameHeaderFilter extends OncePerRequestFilter {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${lrs.siteMinderUserIdHeader}")
	private String siteMinderUserIdHeader;

	@Autowired
	private FhacUserService fhacUserService;

	@Autowired
	private SecurityService securityService;

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String userId = request.getHeader(siteMinderUserIdHeader);
		if (userId == null) {
			logger.debug("User ID not found in request, rejecting");
			filterChain.doFilter(request, response);
			return;
		}

		FhacUserDTO fhacUserDTO;
		try {
			fhacUserDTO = fhacUserService.getFhacUser(userId);
		} catch (Throwable t) {
			throw new ServletException("Couldn't get FHAC user for username " + userId, t);
		}
		if (fhacUserDTO == null) {
			throw new ServletException("No FHAC user for userId" + userId);
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

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		AntPathMatcher pathMatcher = new AntPathMatcher();
		List<String> excludeUrlPatterns = new ArrayList<String>();
		excludeUrlPatterns.add("/api/v1/unprotected/**");
		return excludeUrlPatterns.stream().anyMatch(p -> pathMatcher.match(p, request.getPathInfo()));
	}

}
