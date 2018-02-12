package gov.hud.lrs.services.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.util.StringUtils;

import gov.hud.lrs.common.util.StringFunctionsUtil;

public class LrsCorsFilter extends ChannelProcessingFilter {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		HttpServletRequest request = (HttpServletRequest) req;
		String origin = request.getHeader("Origin");
		
		response.addHeader("Access-Control-Allow-Origin", StringFunctionsUtil.sanitizeText(origin));
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.addHeader("Access-Control-Max-Age", "10");
		response.addHeader("Access-Control-Expose-Headers", "X-CSRF-TOKEN");
		response.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD");

		String headers = request.getHeader("Access-Control-Request-Headers");

		if (!StringUtils.isEmpty(headers)) {
			response.addHeader("Access-Control-Allow-Headers", StringFunctionsUtil.sanitizeText(headers));
		}

		if (request.getMethod().equals("OPTIONS")) {
			try {
				response.getWriter().print("OK");
				response.getWriter().flush();
			} catch (IOException ioe) {
				logger.error(ioe.getMessage());
			}
		} else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {
	}
}
