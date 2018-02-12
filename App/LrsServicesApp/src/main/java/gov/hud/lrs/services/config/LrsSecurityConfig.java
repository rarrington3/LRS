package gov.hud.lrs.services.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;

import gov.hud.lrs.services.security.DevAuthenticationFilter;
import gov.hud.lrs.services.security.SiteMinderAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class LrsSecurityConfig extends WebSecurityConfigurerAdapter {
	// these are to prevent spring from auto-registering our filters in the normal (non-security) filter chain
	// by default, spring does this for all filter @Components as part of the component scan
	// this is troublesome for us because we must not run these filters on certain endpoints, such as /devmule
	// creating these dummy beans disables this behavior
	// http://stackoverflow.com/questions/28421966/prevent-spring-boot-from-registering-a-servlet-filter
	// https://github.com/spring-projects/spring-boot/issues/2173
	@Bean
	public FilterRegistrationBean siteMinderAuthenticationFilterRegistration(SiteMinderAuthenticationFilter filter) {
	    FilterRegistrationBean registration = new FilterRegistrationBean(filter);
	    registration.setEnabled(false);
	    return registration;
	}
	@Bean
	public FilterRegistrationBean devAuthenticationFilterRegistration(DevAuthenticationFilter filter) {
	    FilterRegistrationBean registration = new FilterRegistrationBean(filter);
	    registration.setEnabled(false);
	    return registration;
	}
	@Bean
	public FilterRegistrationBean filterExceptionHandlerFilterRegistration(FilterExceptionHandlerFilter filter) {
	    FilterRegistrationBean registration = new FilterRegistrationBean(filter);
	    registration.setEnabled(false);
	    return registration;
	}

	@Autowired
	private SiteMinderAuthenticationFilter siteMinderAuthenticationFilter;

	@Autowired
	private DevAuthenticationFilter devAuthenticationFilter;

	@Autowired
	private FilterExceptionHandlerFilter filterExceptionHandlerFilter;

	public LrsSecurityConfig() {
		super(true);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/api/v1/config/**", "/api/v1/devLogin/**", "/api/v1/devMule/**", "/api/v1/unprotected/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// No session will be created or used by Spring Security
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.addFilterBefore(new LrsCorsFilter(), ChannelProcessingFilter.class);

		// TODO: only add siteminderauth in prod, and only add devauth in dev, or have dedicated settings for these
		// https://jira.cynergy.com/jira/browse/HUDLRS-2139
		http.addFilterBefore(filterExceptionHandlerFilter, RequestHeaderAuthenticationFilter.class)
			.addFilterBefore(devAuthenticationFilter, RequestHeaderAuthenticationFilter.class)
			.addFilterBefore(siteMinderAuthenticationFilter, RequestHeaderAuthenticationFilter.class)
			.authorizeRequests().anyRequest().authenticated();
	}

}
