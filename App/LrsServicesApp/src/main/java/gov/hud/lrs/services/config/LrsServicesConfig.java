package gov.hud.lrs.services.config;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.guava.GuavaCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import com.google.common.cache.CacheBuilder;

import gov.hud.lrs.common.service.MuleClient;

import com.fasterxml.jackson.databind.SerializationFeature;

@Configuration
@EnableTransactionManagement
@EnableCaching
public class LrsServicesConfig extends WebMvcConfigurerAdapter {

	@Value("${lrs.cors.allowedOrigin}")
	private String allowedOrigin;

	@Value("${lrs.fhacUserCacheMinutes}")
	private long fhacUserCacheMinutes;

	@Value("${lrs.lenderCacheMinutes}")
	private long lenderCacheMinutes;

	@Bean
	public FilterRegistrationBean corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		List<String> origins = Arrays.asList(allowedOrigin.split("\\s*,\\s*"));
		for (String origin : origins) {
			config.addAllowedOrigin(origin);
		}
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/**", config);
		FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
		bean.setOrder(0);
		return bean;
	}

	/**
	 * Filter registration bean for user name logging filter. Includes the
	 * logged in user id in logged messages.
	 *
	 * @return FilterRegistrationBean
	 */
	@Bean
	public FilterRegistrationBean userLoggingFilter() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(new UserLoggingFilter());
		registration.setOrder(Integer.MAX_VALUE);
		return registration;
	}

	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver resolver = new SessionLocaleResolver();
		resolver.setDefaultLocale(Locale.US);
		return resolver;
	}

	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:messages");
		return messageSource;
	}

	@Bean
	public Jackson2ObjectMapperBuilder jacksonBuilder() {
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
		builder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		return builder;
	}

	@Bean(name = "cacheManager")
	public CacheManager cacheManager() {
		SimpleCacheManager cacheManager = new SimpleCacheManager();
		cacheManager.setCaches(Arrays.asList(
			new ConcurrentMapCache("BatchStatusRef"),
			new ConcurrentMapCache("BinderRequestSourceRef"),
			new ConcurrentMapCache("BinderRequestStatusRef"),
			new ConcurrentMapCache("DocumentTypeRef"),
			new ConcurrentMapCache("EmailStatusRef"),
			new ConcurrentMapCache("FileDeliveryLocationRef"),
			new ConcurrentMapCache("FraudParticipantRef"),
			new ConcurrentMapCache("FraudTypeRef"),
			new ConcurrentMapCache("IndemnificationTypeRef"),
			new ConcurrentMapCache("JobExecutionStatusRef"),
			new ConcurrentMapCache("JobStatusRef"),
			new ConcurrentMapCache("JobTypeRef"),
			new ConcurrentMapCache("LenderRequestStatusRef"),
			new ConcurrentMapCache("LoanSelectionStatusRef"),
			new ConcurrentMapCache("LoanTypeRef"),
			new ConcurrentMapCache("LrsGenLookup"),
			new ConcurrentMapCache("NoteTypeRef"),
			new ConcurrentMapCache("PersonnelStatusRef"),
			new ConcurrentMapCache("ProductTypeRef"),
			new ConcurrentMapCache("QaModelStatusRef"),
			new ConcurrentMapCache("QcOutcomeRef"),
			new ConcurrentMapCache("RatingRef"),
			new ConcurrentMapCache("ReviewLevelReassignmentReasonRef"),
			new ConcurrentMapCache("ReviewLevelStatusRef"),
			new ConcurrentMapCache("ReviewLevelTypeRef"),
			new ConcurrentMapCache("ReviewProcessExceptionTypeRef"),
			new ConcurrentMapCache("ReviewScopeRef"),
			new ConcurrentMapCache("ReviewStatusRef"),
			new ConcurrentMapCache("ReviewTypeRef"),
			new ConcurrentMapCache("ScoringFactor"),
			new ConcurrentMapCache("ScoringModelTypeRef"),
			new ConcurrentMapCache("ScoringModelVersionStatusRef"),
			new ConcurrentMapCache("SelectionRequestTypeRef"),
			new ConcurrentMapCache("SelectionSubReasonRef"),
			new ConcurrentMapCache("UniverseRef"),
			new GuavaCache(
				"FhacUser",
				CacheBuilder
					.newBuilder()
					.expireAfterWrite(fhacUserCacheMinutes, TimeUnit.MINUTES)
					.build()
			)
		));
		return cacheManager;
	}

	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		AntPathMatcher matcher = new AntPathMatcher();
		matcher.setCaseSensitive(false);
		configurer.setPathMatcher(matcher);
	}

	@Bean
	public MuleClient muleClient() {
		return new MuleClient();
	}

	@Bean(name = "lenderServiceMuleClient")
	public MuleClient lenderServiceMuleClient() {
		return new MuleClient(0, 5000);
	}

}
