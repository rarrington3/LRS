package gov.hud.lrs.workflow.integration;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import gov.hud.lrs.common.security.SecurityService;

@SpringBootTest(classes = LrsWorkflowApplicationIT.class)
@ActiveProfiles("integrationtest")
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public abstract class LrsWorkflowAbstractIT {

	@Autowired private SecurityService securityService;

	@BeforeClass
	public static void setUpOnce() {
	}

	@Before
	public void setup() {
		securityService.setTestUser();
	}

	@Before
	public void setupMocks() {
	}

}