package gov.hud.lrs.workflow.config;

import org.kie.api.KieServices;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEnvironment;
import org.kie.api.runtime.manager.RuntimeEnvironmentBuilder;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.spring.factorybeans.RuntimeManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import gov.hud.lrs.workflow.task.SubmitReviewLevelTask;

@Configuration
public class JbpmConfig {

	@Bean
	public RuntimeEnvironment runtimeEnvironment() throws Exception {
		RuntimeEnvironment runtimeEnvironment = RuntimeEnvironmentBuilder
			.Factory
			.get()
			.newEmptyBuilder()
			.addAsset(KieServices.Factory.get().getResources().newClassPathResource("processes/ReviewProcess.bpmn2"), ResourceType.BPMN2)
			.persistence(false)
			.get()
		;

		return runtimeEnvironment;
	}

	@Bean(destroyMethod = "close")
	public RuntimeManager runtimeManager(RuntimeEnvironment runtimeEnvironment) throws Exception {
		RuntimeManagerFactoryBean runtimeManagerFactoryBean = new RuntimeManagerFactoryBean();
		runtimeManagerFactoryBean.setIdentifier("spring-rm");
		runtimeManagerFactoryBean.setRuntimeEnvironment(runtimeEnvironment);

		return (RuntimeManager)runtimeManagerFactoryBean.getObject();
	}

	@Bean
	public KieSession kieSession(RuntimeManager runtimeManager, SubmitReviewLevelTask submitReviewLevelTask) throws Exception {
		KieSession kieSession = runtimeManager.getRuntimeEngine(null).getKieSession();
		kieSession.getWorkItemManager().registerWorkItemHandler("SubmitReviewLevelTask", submitReviewLevelTask);

		return kieSession;
	}

}
