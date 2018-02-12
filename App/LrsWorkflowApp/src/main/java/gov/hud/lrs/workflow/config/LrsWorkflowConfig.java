package gov.hud.lrs.workflow.config;

import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.guava.GuavaCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.cache.CacheBuilder;

import gov.hud.lrs.common.service.MuleClient;

@Configuration
public class LrsWorkflowConfig extends WebMvcConfigurerAdapter {

	@Value("${lrs.fhacUserCacheMinutes}")
	private long fhacUserCacheMinutes;

	@Value("${lrs.lenderCacheMinutes}")
	private long lenderCacheMinutes;

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

	@Bean
	public MuleClient muleClient() {
		return new MuleClient();
	}

	@Bean(name = "lenderServiceMuleClient")
	public MuleClient lenderServiceMuleClient() {
		return new MuleClient(0, 5000);
	}

	@Bean
	Executor executor() {
		return Executors.newFixedThreadPool(10);
	}

	@Bean
	KieContainer kieContainer() {
		KieServices kieServices = KieServices.Factory.get();
		return kieServices.getKieClasspathContainer();
	}

}
