package gov.hud.lrs.workflow.integration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@Profile("integrationtest")
@ComponentScan(basePackages = { "gov.hud.lrs" })
public class LrsWorkflowApplicationIT extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(LrsWorkflowApplicationIT.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder springApplicationBuilder) {
		return springApplicationBuilder.sources(LrsWorkflowApplicationIT.class);
	}

}
